package in.irise.soft.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.irise.soft.model.Part;
import in.irise.soft.service.IOrderMethodService;
import in.irise.soft.service.IPartService;
import in.irise.soft.service.IUomService;

@Controller
@RequestMapping("/part")
public class PartController {

	@Autowired
	private IPartService service;
	
	@Autowired
	private IUomService uomService;
	
	@Autowired
	private IOrderMethodService omService;
	
	private void commonChildUi(Model model) {
		Map<Integer,String> uomMap = uomService.getUomIdAndModel();
		model.addAttribute("uoms", uomMap);
		
		Map<Integer,String> omSaleMap = omService.getOrderMethodIdAndCode("Sale");
		model.addAttribute("omSales", omSaleMap);
	}
	
	
	//1. Show Register page
	@GetMapping("/register")
	public String showReg(Model model) {
		commonChildUi(model);
		return "PartRegister";
	}

	//2. On click submit button read form -> save data
	@PostMapping("/save")
	public String savePart(
			//Reading Form Data
			@ModelAttribute Part part,
			Model model //send to UI
			) 
	{

		//call service
		Integer id = service.savePart(part);

		//create message
		String message = "Part "+id+" saved";

		//send message to UI
		model.addAttribute("message", message);
		//show child dropdown
		commonChildUi(model);
		//Go back to same page
		return "PartRegister";
	}


	//3. Display all rows
	@GetMapping("/all")
	public String showAllParts(
			Model model	) 
	{
		// call service layer
		List<Part> list = service.getAllParts();

		//send data to UI
		model.addAttribute("list", list);

		//Go back to UI page
		return "PartData";
	}

	//4. delete by id : /st/delete?id=<val>
	@GetMapping("/delete")
	public String deletePart( 
			@RequestParam("id")Integer pid, //read param
			Model model
			) 
	{
		if(service.isPartExist(pid)) {
			//call service
			service.deletePart(pid);

			//create message
			String message = new StringBuffer()
					.append("Part '")
					.append(pid)
					.append("' Deleted!")
					.toString();

			//send to UI
			model.addAttribute("message", message);
		} else {
			model.addAttribute("message", pid+ " not found!!");
		}
		//latest data
		model.addAttribute("list", service.getAllParts());

		return "PartData";
	}

	//5. show edit page
	@GetMapping("/edit")
	public String showPartEdit(
			@RequestParam("id")Integer pid,
			Model model
			) 
	{
		String page = null;
		Optional<Part> opt = service.getOnePart(pid);
		if(opt.isPresent()) { //if data is present (not null)
			//object --> Fill in Form
			model.addAttribute("part", opt.get());
			commonChildUi(model);
			page = "PartEdit"; 
		} else {
			// response.sendRedirect("/all");
			page = "redirect:all";
		}
		return page;
	}

	//6. Update Part
	@PostMapping("/update")
	public String doUpdatePart(
			@ModelAttribute Part part,
			Model model) 
	{
		//call service to update
		service.updatePart(part);

		//return "redirect:all";
		
		//send message to UI
		model.addAttribute("message", "Part '"+part.getId()+"' Updated!!");

		// call service layer for latest data
		List<Part> list = service.getAllParts();

		//send data to UI for HTML table
		model.addAttribute("list", list);

		//Go back to UI page
		return "PartData";
		
	}

	//7. AJAX VALIDATION
	//8. EXCEL EXPORT
	//9. PDF Export
	//10. Charts
		
}
