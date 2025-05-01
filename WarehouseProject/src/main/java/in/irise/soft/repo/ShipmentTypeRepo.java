package in.irise.soft.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.irise.soft.model.ShipmentType;

public interface ShipmentTypeRepo 
	extends JpaRepository<ShipmentType, Integer>
{

	@Query("SELECT ST.shipmentMode, count(ST.shipmentMode) FROM ShipmentType ST GROUP BY ST.shipmentMode")
	List<Object[]> getShipmentTypeModeCount();
	
	@Query("SELECT id, shipmentCode from ShipmentType WHERE enableShipment='Yes'")
	List<Object[]> getEnabledShipments();
}
