package in.irise.soft.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.irise.soft.model.Shipping;
import in.irise.soft.model.ShippingDtl;
import in.irise.soft.repo.ShippingDtlRepo;
import in.irise.soft.repo.ShippingRepository;
import in.irise.soft.service.IShippingService;

@Service
public class ShippingServiceImpl implements IShippingService {

	@Autowired
	private ShippingRepository repo;
	@Autowired
	private ShippingDtlRepo dtlrepo;

	@Override
	@Transactional
	public Integer saveShipping(Shipping s) {
		return repo.save(s).getId();
	}

	@Override
	@Transactional
	public void updateShipping(Shipping s) {
		repo.save(s);
	}

	@Override
	@Transactional
	public void deleteShipping(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Shipping> getOneShipping(Integer id) {
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Shipping> getAllShippings() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isShippingExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	@Transactional
	public Integer saveShippingDtl(ShippingDtl dtl) {
		return dtlrepo.save(dtl).getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShippingDtl> getAllDtlsByShippingId(Integer shippingId) {
		return dtlrepo.getAllDtlsByShippingId(shippingId);
	}

	@Override
	@Transactional
	public void updateStatusByShippinDtlId(String status, Integer id) {
		dtlrepo.updateStatusByShippinDtlId(status, id);
	}
}
