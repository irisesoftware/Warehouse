package in.irise.soft.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.irise.soft.model.User;
import in.irise.soft.repo.UserRepository;
import in.irise.soft.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public Integer saveUser(User user) {
		//password encode 
		String encPwd = encoder.encode(user.getPwd());
		user.setPwd(encPwd);
		user = repo.save(user);
		return user.getId();
	}

	public User getOneUser(Integer id) {
		Optional<User> opt = repo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	public List<User> getAllUsers() {
		return repo.findAll();
	}

	public Optional<User> findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Transactional
	public void modifyStatus(Integer id, boolean active) {
		repo.updateStatus(id, active);
	}

	@Transactional
	public void updatePwd(Integer id, String newPwd) {
		repo.updatePwd(id, newPwd);
	}

	@Transactional
	public void updateNewOtpById(Integer id, String otpNew) {
		repo.updateNewOtpById(id, otpNew);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//using username(email) fetch model class object
		Optional<User> opt = repo.findByEmail(username);

		//if not exist/inactive throw exception
		if(opt.isEmpty() || !opt.get().isActive()) {
			throw new UsernameNotFoundException("User not exist");
		}else {
			//else convert model class object to spring security object
			//read model clas  user
			User user = opt.get();
			
			//convert our String roles to GrantedAuthority
			Set<GrantedAuthority> auths = new HashSet<>();
			for(String role:user.getRoles()) {
				auths.add(new SimpleGrantedAuthority(role));
			}
			//return Spring Security user object
			return new org.springframework.security.core.userdetails.
					User(username, user.getPwd(), auths);
		}
	}

}
