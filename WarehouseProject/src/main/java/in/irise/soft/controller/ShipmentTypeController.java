package in.irise.soft.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import in.irise.soft.model.ShipmentType;
import in.irise.soft.service.IShipmentTypeService;
import in.irise.soft.util.ShipmentTypeUtil;
import in.irise.soft.view.ShipmentTypeExcelOneView;
import in.irise.soft.view.ShipmentTypeExcelView;
import in.irise.soft.view.ShipmentTypePdfView;

@Controller
@RequestMapping("/st")
public class ShipmentTypeController {

	@Autowired
	private IShipmentTypeService service;
	
	@Autowired
	private ShipmentTypeUtil util;
	
	@Autowired
	private ServletContext sc;

	//1. Show Register page
	@GetMapping("/register")
	public String showReg() {
		return "ShipmentTypeRegister";
	}

	//2. On click submit button read form -> save data
	@PostMapping("/save")
	public String saveShipmentType(
			//Reading Form Data
			@ModelAttribute ShipmentType shipmentType,
			Model model //send to UI
			) 
	{

		//call service
		Integer id = service.saveShipmentType(shipmentType);

		//create message
		String message = "Shipment Type "+id+" saved";

		//send message to UI
		model.addAttribute("message", message);

		//Go back to same page
		return "ShipmentTypeRegister";
	}


	//3. Display all rows
	@GetMapping("/all")
	public String showAllShipmentTypes(
			Model model	) 
	{
		// call service layer
		List<ShipmentType> list = service.getAllShipmentTypes();

		//send data to UI
		model.addAttribute("list", list);

		//Go back to UI page
		return "ShipmentTypeData";
	}

	//4. delete by id : /st/delete?id=<val>
	@GetMapping("/delete")
	public String deleteShipmentType( 
			@RequestParam("id")Integer sid, //read param
			Model model
			) 
	{
		if(service.isShipmentTypeExist(sid)) {
			//call service
			service.deleteShipmentType(sid);

			//create message
			String message = new StringBuffer()
					.append("Shipment Type '")
					.append(sid)
					.append("' Deleted!")
					.toString();

			//send to UI
			model.addAttribute("message", message);
		} else {
			model.addAttribute("message", sid+ " not found!!");
		}
		//latest data
		model.addAttribute("list", service.getAllShipmentTypes());

		return "ShipmentTypeData";
	}

	//5. show edit page
	@GetMapping("/edit")
	public String showShipmentTypeEdit(
			@RequestParam("id")Integer sid,
			Model model
			) 
	{
		String page = null;
		Optional<ShipmentType> opt = service.getOneShipmentType(sid);
		if(opt.isPresent()) { //if data is present (not null)
			//object --> Fill in Form
			model.addAttribute("shipmentType", opt.get());
			page = "ShipmentTypeEdit"; 
		} else {
			// response.sendRedirect("/all");
			page = "redirect:all";
		}
		return page;
	}

	//6. Update ShipmentType
	@PostMapping("/update")
	public String doUpdateShipmentType(
			@ModelAttribute ShipmentType shipmentType,
			Model model) 
	{
		//call service to update
		service.updateShipmentType(shipmentType);

		//return "redirect:all";
		
		//send message to UI
		model.addAttribute("message", "Shipment Type '"+shipmentType.getId()+"' Updated!!");

		// call service layer for latest data
		List<ShipmentType> list = service.getAllShipmentTypes();

		//send data to UI for HTML table
		model.addAttribute("list", list);

		//Go back to UI page
		return "ShipmentTypeData";
		
	}

	//7. AJAX VALIDATION
	
	
	//8. EXCEL EXPORT
	@GetMapping("/excel")
	public ModelAndView showExcelExport() {
		//fetch all rows from DB
		List<ShipmentType> list = service.getAllShipmentTypes();
		
		//create ModelAndView
		ModelAndView  m = new ModelAndView();
		m.addObject("list", list);
		m.setView(new ShipmentTypeExcelView());
		
		return m;
	}
	
	@GetMapping("/excelone")
	public ModelAndView showExcelOneExport(
			@RequestParam("id")Integer sid) 
	{
		//fetch all rows from DB
		Optional<ShipmentType> opt = service.getOneShipmentType(sid);
		
		//create ModelAndView
		ModelAndView  m = new ModelAndView();
		m.addObject("st", opt.get());
		
		m.setView(new ShipmentTypeExcelOneView());
		
		return m;
	}

	
	//9. PDF Export
	@GetMapping("/pdf")
	public ModelAndView exportToPdf() 
	{
		//fetch all rows from DB
		List<ShipmentType> list = service.getAllShipmentTypes();
		
		//create ModelAndView
		ModelAndView  m = new ModelAndView();
		m.addObject("list", list);
		m.setView(new ShipmentTypePdfView());
		return m;
	}
	
	//10. Charts
	@GetMapping("/charts")
	public String showCharts() {
		// call service for data
		List<Object[]> list = service.getShipmentTypeModeCount();
		// dynamic path inside server(runtime location)
		String path = sc.getRealPath("/"); //root location
		System.out.println("Runtime location=>" + path);
		
		// call util method for generation
		util.generatePieChart(path, list);
		util.generateBarChart(path, list);
		return "ShipmentTypeCharts.html";
	}
	
	
}
