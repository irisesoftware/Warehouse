package in.irise.soft.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.irise.soft.model.ShipmentType;
import in.irise.soft.repo.ShipmentTypeRepo;
import in.irise.soft.service.IShipmentTypeService;
import in.irise.soft.util.AppUtil;

@Service
public class ShipmentTypeServiceImpl 
	implements IShipmentTypeService
{

	@Autowired
	private ShipmentTypeRepo repo; //HAS-A
	
	@Override
	public Integer saveShipmentType(ShipmentType st) {
		st = repo.save(st);
		Integer id = st.getId();
		return id;
	}
	
	@Override
	public List<ShipmentType> getAllShipmentTypes() {
		List<ShipmentType> list = repo.findAll();
		return list;
	}
	
	@Override
	public void deleteShipmentType(Integer id) {
		repo.deleteById(id);
	}
	
	@Override
	public boolean isShipmentTypeExist(Integer id) {
		return repo.existsById(id);
	}
	
	@Override
	public Optional<ShipmentType> getOneShipmentType(Integer id) {
		Optional<ShipmentType> opt = repo.findById(id);
		return opt;
	}
	
	@Override
	public void updateShipmentType(ShipmentType st) {
		repo.save(st); //UPDATE SQL..
	}
	
	@Override
	public List<Object[]> getShipmentTypeModeCount() {
		List<Object[]>  list = repo.getShipmentTypeModeCount();
		return list;
	}
	
	@Override
	public Map<Integer, String> getEnabledShipments() {
		List<Object[]> list = repo.getEnabledShipments();
		return AppUtil.convertToMap(list);
	}
}
