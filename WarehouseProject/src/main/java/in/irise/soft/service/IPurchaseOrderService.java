package in.irise.soft.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.irise.soft.model.PurchaseDtl;
import in.irise.soft.model.PurchaseOrder;

public interface IPurchaseOrderService {

	public Integer savePurchaseOrder(PurchaseOrder po);
	public PurchaseOrder getOnePurchaseOrder(Integer id);
	public List<PurchaseOrder> getAllPurchaseOrders();
	
	//screen#2 methods
	public void addPoPart(PurchaseDtl purchaseDtl);
	public List<PurchaseDtl> getPurchaseDtlByOrderId(Integer orderId);
	public void deletePurchaseDtl(Integer dtlId);
	
	public void updatePoStatusByOrderId(String orderStatus, Integer orderId);
	public String getStatusByOrderId(Integer orderId);
	public Integer getCountOfItemsByOrderId(Integer orderId);
	
	//qty update for duplicate item add
	public Optional<Integer> getPurchaseDtlByPartIdAndPoId(Integer partId,Integer orderId);
	public void updatePurchaseDtlQtyByDtlId(Integer dtlId,Integer newQty);
	
	//#grn integration
	public Map<Integer,String> getPoOrderIdAndCode(String status);
}
