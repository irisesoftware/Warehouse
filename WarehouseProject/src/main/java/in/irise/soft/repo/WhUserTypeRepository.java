package in.irise.soft.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.irise.soft.model.WhUserType;

public interface WhUserTypeRepository 
	extends JpaRepository<WhUserType, Integer> {

	@Query("SELECT COUNT(userEmail) FROM WhUserType WHERE userEmail=:userEmail")
	public Integer getUserMailCount(String userEmail);
	
	@Query("SELECT userType, count(userType) FROM WhUserType GROUP BY userType ")
	public List<Object[]> getUserTypeAndCount();
	
	@Query("SELECT id, userCode FROM WhUserType WHERE userType=:userType")
	public List<Object[]> getUserIdAndCodeByType(String userType);
}
