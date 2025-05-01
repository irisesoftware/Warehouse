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

import in.irise.soft.consts.PurchaseOrderStatus;
import in.irise.soft.model.Grn;
import in.irise.soft.model.GrnDtl;
import in.irise.soft.model.PurchaseDtl;
import in.irise.soft.service.IGrnService;
import in.irise.soft.service.IPurchaseOrderService;

@Controller
@RequestMapping("/grn")
public class GrnController {
	
	@Autowired
	private IGrnService service;
	
	@Autowired
	private IPurchaseOrderService orderService;

	private void addChildUI(Model model) {
		model.addAttribute("pos", orderService.getPoOrderIdAndCode("INVOICED"));
	}
	
	
	//1. show Grn register screen
	@GetMapping("/register")
	public String showReg(Model model) {
		addChildUI(model);
		return "GrnRegister";
	}
	
	//2. on click create button
	@PostMapping("/save")
	public String saveGrn(
			@ModelAttribute Grn grn,
			Model model)
	{
		String id = service.saveGrn(grn);
		if(id!=null) {
			orderService.updatePoStatusByOrderId(
					PurchaseOrderStatus.RECEIVED.getValue(),
					grn.getPo().getId()
					);
		}
		model.addAttribute("message", "Grn saved with id "+id);
		createGrnDtlsUsingPoDtls(grn.getPo().getId(),grn);
		addChildUI(model);
		return "GrnRegister";
	}
	
	private void createGrnDtlsUsingPoDtls(Integer orderId,Grn grn) {
		//1. Load List<PurchaseDtl> using orderId
		List<PurchaseDtl>  poDtls = orderService.getPurchaseDtlByOrderId(orderId);
		
		for(PurchaseDtl poDtl: poDtls) {
			GrnDtl grnDtl = new GrnDtl();
			grnDtl.setItemCode(poDtl.getPart().getPartCode());
			grnDtl.setBaseCost(poDtl.getPart().getPartBaseCost());
			grnDtl.setQty(poDtl.getQty());
			grnDtl.setItemValue(
					poDtl.getPart().getPartBaseCost() * poDtl.getQty()
					);
			//link with grn
			grnDtl.setGrn(grn); //it will take internally only id
			//save grnDtl
			service.saveGrnDtl(grnDtl);
		}
	}


	@GetMapping("/all")
	public String showAllGrns(Model model) {
		List<Grn> list = service.getAllGrns();
		model.addAttribute("list", list);
		return "GrnData";
	}
	
	@GetMapping("/showDtls")
	public String showGrnDtls(
			@RequestParam String grnId,
			Model model) 
	{
		List<GrnDtl> list = service.getAllGrnDtlByGrnId(grnId);
		
		Grn opt = service.getOneGrn(grnId);
		model.addAttribute("list", list);
		model.addAttribute("grnCode", opt.getGrnCode());
		model.addAttribute("orderCode", opt.getPo().getOrderCode());
		
		return "GrnDtlParts";
	}
	//status update for GrnDtl
	@GetMapping("/statusUpdate")
	public String updateGrnDtlStatus(
			@RequestParam String grnId,
			@RequestParam Integer dtlId,
			@RequestParam String status
			) 
	{
		
		service.updateGrnDtlStatusById(status, dtlId);
		return "redirect:showDtls?grnId="+grnId;
	}
	
	
	
	
	
}
