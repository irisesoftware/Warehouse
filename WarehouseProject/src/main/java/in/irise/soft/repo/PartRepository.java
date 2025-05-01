package in.irise.soft.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.irise.soft.model.Part;

public interface PartRepository 
	extends JpaRepository<Part, Integer> 
{

	@Query("SELECT id, partCode  FROM Part")
	List<Object[]> getPartIdAndCode();
}
