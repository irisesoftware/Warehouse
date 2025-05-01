package in.irise.soft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig 
	extends WebSecurityConfigurerAdapter
{
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	protected void configure(
			AuthenticationManagerBuilder auth) 
					throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}

	protected void configure(HttpSecurity http)
			throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/user/showLogin","/user/showForgotPwd","/user/updateForgotPwd").permitAll()
		.antMatchers("/user/showActivateByOtp","/user/activate").permitAll()
		.antMatchers("/user**").hasAuthority("ADMIN")
		.antMatchers("/uom**","/om**","/st**","/wh**","/part**").hasAnyAuthority("ADMIN","APPUSER")
		.antMatchers("/po**","/grn**","/so**","/ship**").hasAuthority("APPUSER")
		.anyRequest().authenticated()
		
		.and()
		.formLogin()
		.loginPage("/user/showLogin") //to show login page
		.loginProcessingUrl("/login") //to validate un,pwd
		.defaultSuccessUrl("/user/setup",true) //login success
		.failureUrl("/user/showLogin?error") //login failed
		
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //logout URL
		.logoutSuccessUrl("/user/showLogin?success") //logout success
		
		
		
		;
	}
}
