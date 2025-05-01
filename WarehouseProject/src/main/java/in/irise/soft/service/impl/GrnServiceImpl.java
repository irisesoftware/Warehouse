package in.irise.soft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.irise.soft.model.Grn;
import in.irise.soft.model.GrnDtl;
import in.irise.soft.repo.GrnDtlRepository;
import in.irise.soft.repo.GrnRepository;
import in.irise.soft.service.IGrnService;

@Service
public class GrnServiceImpl implements IGrnService {

	@Autowired
	private GrnRepository repo;
	@Autowired
	private GrnDtlRepository dtlRepo;
	
	public String saveGrn(Grn grn) {
		return repo.save(grn).getId();
	}

	public List<Grn> getAllGrns() {
		return repo.findAll();
	}

	public Grn getOneGrn(String id) {
		String str = String.valueOf(id);
		return repo.getbyStringId(str);
	}

	@Override
	public void saveGrnDtl(GrnDtl dtl) {
		dtlRepo.save(dtl);
	}
	@Override
	public List<GrnDtl> getAllGrnDtlByGrnId(String grnId) {
		String grnval = String.valueOf(grnId);
		return dtlRepo.getAllGrnDtlByGrnId( grnval);
	}
	@Transactional
	public void updateGrnDtlStatusById(String status, Integer dtlId) {
		dtlRepo.updateGrnDtlStatusById(status, dtlId);
	}

	
}
