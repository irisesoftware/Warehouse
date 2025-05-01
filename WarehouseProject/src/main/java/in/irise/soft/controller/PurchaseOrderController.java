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
import org.springframework.web.servlet.ModelAndView;

import in.irise.soft.consts.PurchaseOrderStatus;
import in.irise.soft.model.PurchaseDtl;
import in.irise.soft.model.PurchaseOrder;
import in.irise.soft.service.IPartService;
import in.irise.soft.service.IPurchaseOrderService;
import in.irise.soft.service.IShipmentTypeService;
import in.irise.soft.service.IWhUserTypeService;
import in.irise.soft.view.VendorInvoicePdfView;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

	@Autowired
	private IPurchaseOrderService service;

	@Autowired
	private IShipmentTypeService shipmentTypeService;
	@Autowired
	private IWhUserTypeService whUserTypeService;

	@Autowired
	private IPartService partService;

	private void createChildUi(Model model) {
		Map<Integer,String> mapShipments = shipmentTypeService.getEnabledShipments();
		model.addAttribute("shipments", mapShipments);

		Map<Integer,String> mapVendors =  whUserTypeService.getUserIdAndCodeByType("Vendor");
		model.addAttribute("vendors", mapVendors);
	}


	//1. show register page
	@GetMapping("/register")
	public String showReg(Model model) {
		PurchaseOrder po = new PurchaseOrder();
		po.setStatus("OPEN");
		model.addAttribute("purchaseOrder", po);
		createChildUi(model);
		return "PurchaseOrderRegister";
	}

	//2. save data
	@PostMapping("/save")
	public String save(
			@ModelAttribute PurchaseOrder purchaseOrder,
			Model model) 
	{
		Integer id = service.savePurchaseOrder(purchaseOrder);
		model.addAttribute("message", "Purchase Order '"+id+"' saved");

		PurchaseOrder po = new PurchaseOrder();
		po.setStatus("OPEN");
		model.addAttribute("purchaseOrder", po);
		createChildUi(model);
		return "PurchaseOrderRegister";
	}

	//3. display POs
	@GetMapping("/all")
	public String showAll(Model model) {
		List<PurchaseOrder> list = service.getAllPurchaseOrders();
		model.addAttribute("list", list);
		return "PurchaseOrderData";
	}

	//-----------------SCREEN#2 method here------------------
	private void createChildUiForParts(Model model) {
		Map<Integer,String> partsMap = partService.getPartIdAndCode();
		model.addAttribute("parts", partsMap);
	}

	//.../parts?poId=___
	@GetMapping("/parts")
	public String showPurchasePartsPage(
			@RequestParam("poId")Integer orderId, //PO-ID
			Model model
			) 
	{
		//load PO object by id
		PurchaseOrder po = service.getOnePurchaseOrder(orderId);
		model.addAttribute("po", po);

		//create DropDown for Parts
		createChildUiForParts(model);

		//form backing object
		PurchaseDtl purchaseDtl = new PurchaseDtl();
		purchaseDtl.setPo(po);
		model.addAttribute("purchaseDtl", purchaseDtl);

		//Display added Dtls as HTML TABLE
		List<PurchaseDtl> poDtls = service.getPurchaseDtlByOrderId(po.getId());
		model.addAttribute("poDtls", poDtls);

		return "PurchaseParts";
	}

	@PostMapping("/addPart")
	public String addPoPart(
			@ModelAttribute PurchaseDtl purchaseDtl
			) 
	{
		//System.out.println(purchaseDtl);
		Integer orderId = purchaseDtl.getPo().getId();
		Integer partId = purchaseDtl.getPart().getId();
		Optional<Integer> opt = service.getPurchaseDtlByPartIdAndPoId(partId, orderId);
		if(opt.isPresent()) {
			service.updatePurchaseDtlQtyByDtlId(opt.get(), purchaseDtl.getQty());
		} else {
			service.addPoPart(purchaseDtl);
		}
		//if(count>0 and current status is not picking ) then update status=picking
		if(service.getCountOfItemsByOrderId(orderId)>0 &&
				!PurchaseOrderStatus.PICKING.getValue().equals(
						service.getStatusByOrderId(orderId))
				)
		{
			service.updatePoStatusByOrderId(
					PurchaseOrderStatus.PICKING.getValue(), 
					orderId);
		}
		return "redirect:parts?poId="+ orderId;
	}

	/**
	 * To call this method two inputs required
	 * one is orderId(poId) and another one dtlId
	 * Here dtlId is used to delete row from PurchaseDtl.
	 * PoId is used to redirect to same page  (/parts?poId=___)
	 * 
	 * @param orderId
	 * @param dtlId
	 * @return
	 */
	@GetMapping("/removeDtl")
	public String removePoPart(
			@RequestParam("poId")Integer orderId,
			@RequestParam("dtlId")Integer dtlId
			) 
	{
		service.deletePurchaseDtl(dtlId);
		//if(count==0 and current status is not open ) then update status=open
		if(service.getCountOfItemsByOrderId(orderId)==0 && 
				!PurchaseOrderStatus.OPEN.getValue().equals(
						service.getStatusByOrderId(orderId))
				)
		{
			service.updatePoStatusByOrderId(
					PurchaseOrderStatus.OPEN.getValue(), orderId);
		}
		return "redirect:parts?poId="+orderId;
	}
	/***
	 * Place orderbased on orderId
	 */
	@GetMapping("/placeOrder")
	public String placeOrderById(
			@RequestParam("poId")Integer orderId
			) 
	{

		service.updatePoStatusByOrderId(
				PurchaseOrderStatus.ORDERED.getValue(), 
				orderId);
		return "redirect:parts?poId="+orderId;
	}

	//adjuest qty methods
	@GetMapping("/increaseByOne")
	public String increaseByOne(
			@RequestParam("poId")Integer orderId,
			@RequestParam("dtlId")Integer dtlId
			) 
	{
		service.updatePurchaseDtlQtyByDtlId(dtlId, 1);
		return "redirect:parts?poId="+orderId;
	}
	@GetMapping("/reduceByOne")
	public String reduceByOne(
			@RequestParam("poId")Integer orderId,
			@RequestParam("dtlId")Integer dtlId
			) 
	{
		service.updatePurchaseDtlQtyByDtlId(dtlId, -1);
		return "redirect:parts?poId="+orderId;
	}

	//Invoice Status update
	@GetMapping("/genInv")
	public String generateInvoice(
			@RequestParam("poId")Integer orderId
			) 
	{

		if(PurchaseOrderStatus.ORDERED.getValue().equals(
				service.getStatusByOrderId(orderId))){

			service.updatePoStatusByOrderId(
					PurchaseOrderStatus.INVOICED.getValue(), 
					orderId);
		}
		return "redirect:all";
	}
	
	//print Invoice (PDF)
	@GetMapping("/printInv")
	public ModelAndView printInvoice(
			@RequestParam("poId")Integer orderId
			) 
	{
		ModelAndView m = new ModelAndView();
		
		m.setView(new VendorInvoicePdfView());
		m.addObject("po", service.getOnePurchaseOrder(orderId));
		m.addObject("poDtls", service.getPurchaseDtlByOrderId(orderId));
		
		return m;
	}

}
