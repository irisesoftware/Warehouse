package in.irise.soft.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.irise.soft.model.User;

public interface UserRepository 
	extends JpaRepository<User, Integer>
{

	// fetch user by email
	Optional<User> findByEmail(String email);
	
	// modify status
	@Modifying
	@Query("UPDATE in.irise.soft.model.User SET active=:active WHERE id=:id")
	void updateStatus(Integer id, boolean active);
	
	// update new pwd
	@Modifying
	@Query("UPDATE in.irise.soft.model.User SET pwd=:newPwd WHERE id=:id")
	void updatePwd(Integer id,String newPwd);
	
	// updateNew OTP
	@Modifying
	@Query("UPDATE in.irise.soft.model.User SET otp=:otpNew WHERE id=:id")
	void updateNewOtpById(Integer id,String otpNew);
}
