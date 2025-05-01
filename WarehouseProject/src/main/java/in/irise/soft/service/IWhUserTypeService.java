package in.irise.soft.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.irise.soft.model.WhUserType;

public interface IWhUserTypeService {

	Integer saveWhUserType(WhUserType whUserType);
	void updateWhUserType(WhUserType whUserType);
	void deleteWhUserType(Integer id);
	Optional<WhUserType> getOneWhUserType(Integer id);
	List<WhUserType> getAllWhUserTypes();
	boolean isWhUserTypeExist(Integer id);
	boolean isWhUserTypeEmailExist(String userEmail);
	List<Object[]> getUserTypeAndCount();
	
	Page<WhUserType> getWhUserTypesByPage(Pageable pageable);
	
	Map<Integer,String> getUserIdAndCodeByType(String userType);
	
}
