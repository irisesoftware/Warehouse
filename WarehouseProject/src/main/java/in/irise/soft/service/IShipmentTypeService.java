package in.irise.soft.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.irise.soft.model.ShipmentType;

public interface IShipmentTypeService {

	Integer saveShipmentType(ShipmentType st);
	List<ShipmentType> getAllShipmentTypes();
	void deleteShipmentType(Integer id);
	boolean isShipmentTypeExist(Integer id);
	Optional<ShipmentType> getOneShipmentType(Integer id);
	void updateShipmentType(ShipmentType st);
	List<Object[]> getShipmentTypeModeCount();
	
	Map<Integer,String> getEnabledShipments();
}
