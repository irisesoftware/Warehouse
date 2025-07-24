package in.irise.soft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.irise.soft.model.Grn;

public interface GrnRepository 
	extends JpaRepository<Grn, String>
{
	
	@Query(value = "select * from grn_tab where grn_id_col=?", nativeQuery = true )
	public Grn getbyStringId(String id);

	

}
