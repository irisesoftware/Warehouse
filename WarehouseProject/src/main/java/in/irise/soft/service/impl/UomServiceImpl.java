package in.irise.soft.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.irise.soft.exception.UomNotFoundException;
import in.irise.soft.model.Uom;
import in.irise.soft.repo.UomRepository;
import in.irise.soft.service.IUomService;
import in.irise.soft.util.AppUtil;

@Service
public class UomServiceImpl implements IUomService {

	@Autowired
	private UomRepository repo;

	@Override
	public Integer saveUom(Uom uom) {
		return repo.save(uom).getId();
	}

	@Override
	public List<Uom> getAllUoms() {
		return repo.findAll();
	}

	@Override
	public void deleteUom(Integer id) {
		repo.delete(getOneUom(id));
	}

	@Override
	public Uom getOneUom(Integer id) {
		/*Optional<Uom> opt = repo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		} else {
			throw new UomNotFoundException("Uom '"+id+"' Not Exist");
		}*/
		return repo.findById(id)
				.orElseThrow(
						()-> new UomNotFoundException("Uom '"+id+"' Not Exist")
						);
	}

	@Override
	public void updateUom(Uom uom) {
		if(uom.getId()!=null && isUomExistById(uom.getId()))
			repo.save(uom);
		else
			throw new UomNotFoundException("Uom '"+uom.getId()+"' Not Exist");
	}


	@Override
	public boolean isUomModelExist(String uomModel) {
		return repo.getUomModelCount(uomModel) > 0 ? true : false;
	}

	@Override
	public List<Object[]> getUomTypeAndCount() {
		return repo.getUomTypeAndCount();
	}


	@Override
	public boolean isUomModelExistForEdit(String uomModel, Integer id) {
		//return repo.getUomModelCountForNotId(uomModel, id) > 0 ? true : false;
		return repo.getUomModelCountForNotId(uomModel, id) > 0;
	}

	@Override
	public Page<Uom> getAllUoms(Pageable p) {
		return repo.findAll(p);
	}

	@Override
	public Page<Uom> findByUomModelContaining(String uomModel, Pageable pageable) {
		return repo.findByUomModelContaining(uomModel, pageable);
	}

	@Override
	public Map<Integer, String> getUomIdAndModel() {
		List<Object[]> list = repo.getUomIdAndModel();
		Map<Integer,String> map = AppUtil.convertToMap(list);
		return map;
	}

	@Override
	public boolean isUomExistById(Integer id) {
		return repo.existsById(id);
	}

}