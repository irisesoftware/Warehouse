package in.irise.soft.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.irise.soft.model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

	@Modifying
	@Query("UPDATE PurchaseOrder SET status=:orderStatus WHERE id=:orderId")
	void updatePoStatusByOrderId(String orderStatus, Integer orderId);
	
	@Query("SELECT status FROM PurchaseOrder WHERE id=:orderId")
	String getStatusByOrderId(Integer orderId);
	
	@Query("SELECT id, orderCode FROM PurchaseOrder WHERE status=:status")
	List<Object[]> getPoOrderIdAndCode(String status);
}
