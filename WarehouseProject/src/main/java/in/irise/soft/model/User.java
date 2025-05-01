package in.irise.soft.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="users_tab")
public class User {
	@Id
	@GeneratedValue
	@Column(name="usr_id_col")
	private Integer id;
	
	@Column(name="usr_display_name_col")
	private String uname;
	@Column(name="usr_email_col")
	private String email;
	@Column(name="usr_pwd_col")
	private String pwd;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="usr_roles_tab",
			joinColumns = @JoinColumn(name="usr_id_col")
			)
	@Column(name="usr_roles_col")
	private Set<String> roles;
	
	@Column(name="usr_active_col")
	private boolean active;
	
	@Column(name="usr_otp_col")
	private String otp;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Integer id, String uname, String email, String pwd, Set<String> roles, boolean active, String otp) {
		super();
		this.id = id;
		this.uname = uname;
		this.email = email;
		this.pwd = pwd;
		this.roles = roles;
		this.active = active;
		this.otp = otp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
}
