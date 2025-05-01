package in.irise.soft.runner;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.irise.soft.model.User;
import in.irise.soft.service.IUserService;

@Component
public class SetupRunner 
	implements CommandLineRunner
{
	@Autowired
	private IUserService service;
	
	public void run(String... args) throws Exception {
		if(service.findByEmail("irise.com").isEmpty()) {
			User user = new User();
			user.setUname("IriseIT Admin");
			user.setEmail("irise.com");
			user.setPwd("1234");
			//user.setOtp("1234");
			user.setActive(true);
			Set<String> roles = new HashSet<>();
			roles.add("ADMIN");
			roles.add("APPUSER");
			user.setRoles(roles);
			service.saveUser(user);
		}
		
	}

}
