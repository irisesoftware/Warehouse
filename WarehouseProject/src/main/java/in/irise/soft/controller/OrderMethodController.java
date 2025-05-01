package in.irise.soft.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.irise.soft.model.OrderMethod;
import in.irise.soft.service.IOrderMethodService;

@Controller
@RequestMapping("/om")
public class OrderMethodController {
	
	@Autowired
	private IOrderMethodService service;
	
	//1. show Register page
	@GetMapping("/register")
	public String showOrderMethodReg() {
		return "OrderMethodRegister";
	}
	
	//2. save Data
	@PostMapping("/save")
	public String saveOrderMethod(
			@ModelAttribute OrderMethod orderMethod,
			Model model
			) 
	{
		//call service
		Integer id = service.saveOrderMethod(orderMethod);
		model.addAttribute("message", "Order Method '"+id+"' saved");
		return "OrderMethodRegister";
	}
	
	//3. Data display
	@GetMapping("/all")
	public String showAllOrderMethods(Model model)
	{
		List<OrderMethod> list =  service.getAllOrderMethods();
		model.addAttribute("list", list);
		return "OrderMethodData";
	}
	
	//4. delete operation
	@GetMapping("/delete")
	public String doDeleteOrderMethod(
			@RequestParam("id")Integer omId,
			Model model
			) 
	{
		String message =null;
		if(service.isOrderMethodExist(omId)) {
			service.deleteOrderMethod(omId);
			message = "Order Method '"+omId+"' deleted";
		} else {
			message = "Order Method '"+omId+"' Not exist";
		}
		
		List<OrderMethod> list =  service.getAllOrderMethods();
		model.addAttribute("message", message);
		model.addAttribute("list", list);
		return "OrderMethodData";
	}
	
	//5. show edit
	@GetMapping("/edit")
	public String showOrderMethodEdit(
			@RequestParam("id")Integer oid,
			Model model
			) 
	{
		String page=null;
		Optional<OrderMethod> opt = service.getOneOrderMethod(oid);
		if(opt.isPresent()) {
			model.addAttribute("orderMethod", opt.get());
			page = "OrderMethodEdit";
		} else {
			page = "redirect:all";
		}
		return page;
	}
	
	
	//6. do update
	@PostMapping("/update")
	public String doOrderMethodUpdate(
			@ModelAttribute OrderMethod orderMethod,
			Model model
			) 
	{
		service.updateOrderMethod(orderMethod);
		
		List<OrderMethod> list =  service.getAllOrderMethods();
		model.addAttribute("message", "Order '"+orderMethod.getId()+"' Method Updated!");
		model.addAttribute("list", list);
		return "OrderMethodData";
	}
	
	//7. AJAX VALIDATION
	@GetMapping("/validateCode")
	public @ResponseBody String validateOrderCode(
			@RequestParam("code") String orderCode
			) 
	{
		String message = "";
		if(service.isOrderMethodExistByCode(orderCode)) {
			message = "Order Method '"+orderCode+"' Exist! Try different!";
		}
		
		return message;
	}
	
	
}
