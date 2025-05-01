package in.irise.soft.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.irise.soft.model.User;
import in.irise.soft.service.IUserService;
import in.irise.soft.util.EmailUtil;
import in.irise.soft.util.UserUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService service;

	@Autowired
	private UserUtil util;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private BCryptPasswordEncoder encoder;


	//1. Show User Register Page
	@GetMapping("/register")
	public String showUserReg() {
		return "UserRegister";
	}

	//2. Add User to DB
	@PostMapping("/save")
	public String saveUser(
			@ModelAttribute User user,
			Model model
			) 
	{
		// gen pwd
		String pwd = util.genPwd();
		//gen otp
		String otp = util.getOtp();

		//set data to Model Attribute
		user.setPwd(pwd);
		user.setOtp(otp);

		//save user to DB
		Integer id = service.saveUser(user);
		//send msg to UI
		model.addAttribute("message", "User '"+user.getUname()+"' Created!");

		//send email
		if(id!=null) {//on user add success
			String text=
					new StringBuffer()
					.append("Hello User, ")
					.append(user.getUname())
					.append(", Your OTP:")
					.append(otp)
					.append(", Your Password:")
					.append(pwd)
					.append(", Roles: ")
					.append(user.getRoles())
					.append(", Status: ")
					.append(user.isActive()?"ACTIVE":"IN-ACTIVE")
					.toString();
			emailUtil.send(user.getEmail(),"Welcome to User!",text);
		}


		return "UserRegister";
	}

	//3. fetch all users
	@GetMapping("/all")
	public String showUsers(Model model) 
	{
		model.addAttribute("list", service.getAllUsers());
		return "UserData";
	}

	//4. activate user
	@GetMapping("/activate")
	public String activateUser(
			@RequestParam Integer id
			)
	{
		service.modifyStatus(id, true);
		return "redirect:all";
	}


	//5. inactivate users
	@GetMapping("/inactivate")
	public String inActivateUser(
			@RequestParam Integer id
			)
	{
		service.modifyStatus(id, false);
		return "redirect:all";
	}

	//6. setup http session
	@GetMapping("/setup")
	public String loginSetup(
			Principal p, //current username
			HttpSession ses //current session
			) 
	{
		//load user object from db to store inside session
		User user = service.findByEmail(p.getName()).get();

		//store data inside http session
		ses.setAttribute("userOb", user);

		return "redirect:../uom/all";
	}

	@GetMapping("/profile")
	public String showProfile(
			HttpSession ses, Model model
			) 
	{
		User user = (User)ses.getAttribute("userOb");
		model.addAttribute("usr", user);
		return "UserProfile";
	}


	@GetMapping("/showLogin")
	public String showLoginPage() {
		return "LoginPage";
	}

	//*************Update Pwd after login**********************//
	@GetMapping("/showPwdUpdate")
	public String showPwdUpdatePage() {
		return "PwdUpdatePage";
	}

	/***
	 * Read password as Param,
	 * read current user id, email from session
	 * update pwd after encode with session#user.id
	 * send email to user using  Session#user.email
	 */
	@PostMapping("/newPwdUpdate")
	public String userNewPwdUpdate(
			@RequestParam String password,
			HttpSession ses,
			Model model
			) 
	{
		//read current logged-in user
		User user = (User)ses.getAttribute("userOb");
		String encPwd = encoder.encode(password);

		//update same pwd in db
		service.updatePwd(user.getId(), encPwd);

		//send email
		String text =" Hello, "+user.getUname()+", Your Password is updated => " + password;
		emailUtil.send(user.getEmail(), "Password Updated!", text);

		//msg at UI
		model.addAttribute("message", "Password Updated");

		return "PwdUpdatePage";
	}

	//*************Forgot Pwd concept**********************//
	@GetMapping("/showForgotPwd")
	public String showForGotPwdPage() {

		return "ShowForgotPwdPage";
	}

	@PostMapping("/updateForgotPwd")
	public String updatePwdByEmail(
			@RequestParam String email,
			Model model
			) 
	{
		String msg = null;
		//check email exist or not
		Optional<User> opt = service.findByEmail(email);
		if(opt.isEmpty()) { //user not exist
			msg = email +" is invalid Email id";
		} else { // update new pass
			User user = opt.get();
			//gen new pass
			String pwd = util.genPwd();
			//encode pass
			String encPwd = encoder.encode(pwd);

			//update to db
			service.updatePwd(user.getId(), encPwd);

			//send email
			String text =" Hello, "+user.getUname()+", Your new Password is generated => " + pwd;
			emailUtil.send(user.getEmail(), "New Password Generated!", text);

			msg = " New Password sent to email ";
		}

		model.addAttribute("message", msg);
		return "ShowForgotPwdPage";
	}

	//**************USER ACTIVATION BY OTP PROCESS***********
	@GetMapping("/showActivateByOtp")
	public String showActivateByOtpPage() {

		return "UserActivatePage";
	}
	@PostMapping("/activate")
	public String activateUser(
			@RequestParam("opr")String option,
			@RequestParam("emailId")String emailId,
			@RequestParam("otp")String otp,
			Model model
			) 
	{
		//1. Read user by email
		Optional<User>  optional = service.findByEmail(emailId);
		//2. verify user
		if(optional.isEmpty()) {
			model.addAttribute("message", "Invalid EmailId Entered");
		} else {
			//3. read user object
			User user = optional.get();

			if(option.equals("Activate")) {
				//4. Read DB OTP and compare with Form OTP
				if(user.getOtp().equals(otp)) {
					//5. activate user
					service.modifyStatus(user.getId(), true);
					model.addAttribute("message", "User Activated !!");
				} else {
					model.addAttribute("message", "Invalid OTP Entered");
				}

			} else if(option.equals("Resend")) {
				//1. generate new OTP
				String otpNew = util.getOtp();
				//2. update to DB
				service.updateNewOtpById(user.getId(), otpNew);
				//3. send email
				String text="Hello :"+user.getUname()
				+", Your new OTP "
				+otpNew
				+", For Account activation";
				emailUtil.send(user.getEmail(), "New OTP For Activation", text);
				//4. send msg to ui
				model.addAttribute("message", "New OTP is sent to your email");
			}
		}

		return "UserActivatePage";
	}

}
