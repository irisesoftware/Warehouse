package in.irise.soft.service.impl;

import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.irise.soft.model.Part;
import in.irise.soft.repo.PartRepository;
import in.irise.soft.service.IPartService;
import in.irise.soft.util.AppUtil;

@Service
public class PartServiceImpl 
	implements IPartService
{

	@Autowired
	private PartRepository repo; //HAS-A
	
	@Override
	public Integer savePart(Part part) {
		part = repo.save(part);
		Integer id = part.getId();
		return id;
	}
	
	@Override
	public List<Part> getAllParts() {
		List<Part> list = repo.findAll();
		return list;
	}
	
	@Override
	public void deletePart(Integer id) {
		repo.deleteById(id);
	}
	
	@Override
	public boolean isPartExist(Integer id) {
		return repo.existsById(id);
	}
	
	@Override
	public Optional<Part> getOnePart(Integer id) {
		Optional<Part> opt = repo.findById(id);
		return opt;
	}
	
	@Override
	public void updatePart(Part part) {
		repo.save(part); //UPDATE SQL..
	}

	@Override
	public Map<Integer, String> getPartIdAndCode() {
		List<Object[]> list = repo.getPartIdAndCode();
		return AppUtil.convertToMap(list);
	}
	
}
