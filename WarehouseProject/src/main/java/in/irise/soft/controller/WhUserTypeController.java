package in.irise.soft.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.irise.soft.model.WhUserType;
import in.irise.soft.service.IWhUserTypeService;
import in.irise.soft.util.EmailUtil;
import in.irise.soft.util.WhUserTypeUtil;
import in.irise.soft.view.WhUserTypeExcelView;
import in.irise.soft.view.WhUserTypePdfView;

@Controller
@RequestMapping("/wh")
public class WhUserTypeController {
	@Autowired
	private IWhUserTypeService service;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private ServletContext sc;
	
	@Autowired
	private WhUserTypeUtil util;

	//1. show reg page
	@GetMapping("/register")
	public String showRegister() {
		return "WhUserTypeRegister";
	}

	//2. save 
	@PostMapping("/save")
	public String saveWhUserType(
			@ModelAttribute WhUserType whUserType,
			Model model
			) 
	{
		//call service for insert
		Integer id = service.saveWhUserType(whUserType);
		
		if(id!=null) {
			String text="Code is:" +whUserType.getUserCode()+
					", Type is :" + whUserType.getUserType();
			emailUtil.send(
					whUserType.getUserEmail(), "Welcome to warehouse USer!", 
					text);
		}
		
		//send message to UI
		model.addAttribute("message", 
				new StringBuffer()
				.append("Warehouse User '")
				.append(id)
				.append("' saved successfully")
				.toString()
				);

		return "WhUserTypeRegister";
	}

	//3. display all .../all?page=0
	@GetMapping("/all")
	public String getAllWhUserTypes(
			@PageableDefault(page = 0,size = 3)Pageable pageable,
			Model model) 
	{
		getCommonSetupForDataPage(model,pageable);
		return "WhUserTypeData";
	}

	//4. delete one
	@GetMapping("/delete")
	public String deleteWhUserType(
			@RequestParam("id") Integer wid,
			Model model
			) 
	{
		String message = null;
		if(service.isWhUserTypeExist(wid)) {
			service.deleteWhUserType(wid);
			message = "Warehouse User '"+wid+"' Deleted";
		} else {
			message = "Warehouse User '"+wid+"' not exist";
		}
		getCommonSetupForDataPage(model,PageRequest.of(0, 3));
		model.addAttribute("message", message);

		return "WhUserTypeData";
	}

	/**
	 * This method contains code for showing data 
	 * at WhUserTypeData.html
	 * 
	 */
	private void getCommonSetupForDataPage(Model model,Pageable pageable) {
		//call service to fetch 
		Page<WhUserType> page = service.getWhUserTypesByPage(pageable);
		//send it to ui
		model.addAttribute("page", page);
	}

	//5. show edit
	@GetMapping("/edit")
	public String showWhUserTypeEdit(
			@RequestParam("id") Integer wid,
			Model model
			) 
	{
		String page = null;
		Optional<WhUserType> opt = service.getOneWhUserType(wid);
		if(opt.isPresent()) {
			WhUserType whUserType = opt.get();
			model.addAttribute("whUserType", whUserType);
			page = "WhUserTypeEdit";
		} else {
			page ="redirect:all";
		}

		return page;
	}

	//6. do update
	@PostMapping("/update")
	public String doWhUserTypeUpdate(
			@ModelAttribute WhUserType whUserType,
			Model model
			) 
	{
		service.updateWhUserType(whUserType);
		model.addAttribute("message", "Warehouse User '"+whUserType.getId()+"' Updated");
		getCommonSetupForDataPage(model,PageRequest.of(0, 3));

		return "WhUserTypeData";
	}


	//7. AJAX validation for email duplicate
	@GetMapping("/mail")
	public @ResponseBody String validateUserMail(
			@RequestParam String userEmail) 
	{
		String msg="";
		if(service.isWhUserTypeEmailExist(userEmail)) {
			msg =userEmail + ", already exist.";
		}
		return msg;
	}
	
	

	/***
	 * This method is used to execute either all rows
	 * or selected row into excel file.
	 * If user CLICKS on 'EXPORT ALL(EXCEL)' then no id in URL
	 * example URL looks like /excel.
	 * If user CLICK on 'EXPORT ONE' then id exist in URL
	 * example URL looks like /excel?id=101 
	 * 
	 * ID may not exist every time. It is optional.
	 * If id is not present export all.
	 * Else export one as List format, 
	 * bcoz code for View is List based.
	 */

	//8. excel export
	@GetMapping("/excel")
	public ModelAndView showExcelData(
			@RequestParam(value = "id", required = false)Integer id
			) 
	{
		ModelAndView m = new ModelAndView();
		m.setView(new WhUserTypeExcelView());
	
		//read data from DB and send to View class
		if(id==null) { //export all
			List<WhUserType> list = service.getAllWhUserTypes();
			m.addObject("list", list);
		} else { //export by id
			Optional<WhUserType> opt = service.getOneWhUserType(id);
			//Java 9
			//m.addObject("list", List.of(opt.get()));
			m.addObject("list", Arrays.asList(opt.get()));
		}

		return m;
	}

	//9. pdf export
	@GetMapping("/pdf")
	public ModelAndView exportPdfView(
			@RequestParam(value = "id", required = false)Integer id
			) 
	{
		ModelAndView m = new ModelAndView();
		m.setView(new WhUserTypePdfView());
		
		if(id==null) { //export all
			List<WhUserType> list = service.getAllWhUserTypes();
			m.addObject("list", list);
		} else { //export by id
			Optional<WhUserType> opt = service.getOneWhUserType(id);
			//Java 9
			//m.addObject("list", List.of(opt.get()));
			m.addObject("list", Arrays.asList(opt.get()));
		}
		
		return m;
	}

	//10. charts
	@GetMapping("/charts")
	public String generateCharts() {
		//fetch data from DB using service
		List<Object[]>  list = service.getUserTypeAndCount();
		String path = sc.getRealPath("/");
		
		//call util methods
		util.generatePieChart(path, list);
		util.generateBarChart(path, list);
		
		return "WhUserTypeCharts";
	}
	
	
}
