package in.irise.soft.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.irise.soft.model.PurchaseDtl;

public interface PurchaseDtlRepository 
	extends JpaRepository<PurchaseDtl, Integer> 
{

	@Query("SELECT pdtl FROM PurchaseDtl pdtl INNER JOIN pdtl.po as po WHERE po.id=:orderId")
	List<PurchaseDtl> getPurchaseDtlByOrderId(Integer orderId);

	@Query("SELECT count(dtl.id) FROM  PurchaseDtl dtl  INNER JOIN dtl.po as po WHERE po.id=:orderId")
	Integer getCountOfItemsByOrderId(Integer orderId);

	@Query("SELECT dtl.id  FROM PurchaseDtl dtl JOIN dtl.part as prt JOIN dtl.po as po WHERE prt.id=:partId  and po.id=:orderId")
	Optional<Integer> getPurchaseDtlByPartIdAndPoId(Integer partId,Integer orderId);

	@Modifying
	@Query("UPDATE PurchaseDtl SET qty=qty+:newQty WHERE id=:dtlId")
	void updatePurchaseDtlQtyByDtlId(Integer dtlId,Integer newQty);
}
