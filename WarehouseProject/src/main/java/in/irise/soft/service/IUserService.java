package in.irise.soft.service;

import java.util.List;
import java.util.Optional;

import in.irise.soft.model.User;

public interface IUserService {

	Integer saveUser(User user);
	User getOneUser(Integer id);
	List<User> getAllUsers();
	
	Optional<User> findByEmail(String email);
	void modifyStatus(Integer id, boolean active);
	void updatePwd(Integer id,String newPwd);
	void updateNewOtpById(Integer id,String otpNew);
}
