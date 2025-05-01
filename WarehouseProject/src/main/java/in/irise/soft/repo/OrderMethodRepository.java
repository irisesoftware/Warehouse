package in.irise.soft.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.irise.soft.model.OrderMethod;

public interface OrderMethodRepository 
	extends JpaRepository<OrderMethod, Integer>
{

	@Query("SELECT COUNT(om.orderCode) FROM OrderMethod om WHERE om.orderCode=:orderCode")
	public Integer getOrderMethodCountByCode(String orderCode);
	
	@Query("SELECT id,orderCode FROM OrderMethod WHERE orderMode=:mode")
	List<Object[]> getOrderMethodIdAndCode(String mode);
}
