package in.irise.soft.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="shippingipping_tab")
public class Shipping {

	@Id
	@GeneratedValue
	@Column(name="shipping_id_col")
	private Integer id;
	
	@Column(name="shipping_code_col")
	private String shippingCode;

	@Column(name="shipping_refNum_col")
	private String shippingRefNum;

	@Column(name="shipping_courier_col")
	private String courierRefNum;

	@Column(name="shipping_contact_col")
	private String contact;

	@Column(name="shipping_so_code_col")
	private String soCode;

	@Column(name="shipping_desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "so_id_fk", unique = true)
	private SaleOrder so;
	
	@OneToMany(mappedBy = "shipping")
	private List<ShippingDtl> dtls;

	public Shipping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Shipping(Integer id, String shippingCode, String shippingRefNum, String courierRefNum, String contact,
			String soCode, String description, SaleOrder so, List<ShippingDtl> dtls) {
		super();
		this.id = id;
		this.shippingCode = shippingCode;
		this.shippingRefNum = shippingRefNum;
		this.courierRefNum = courierRefNum;
		this.contact = contact;
		this.soCode = soCode;
		this.description = description;
		this.so = so;
		this.dtls = dtls;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public String getShippingRefNum() {
		return shippingRefNum;
	}

	public void setShippingRefNum(String shippingRefNum) {
		this.shippingRefNum = shippingRefNum;
	}

	public String getCourierRefNum() {
		return courierRefNum;
	}

	public void setCourierRefNum(String courierRefNum) {
		this.courierRefNum = courierRefNum;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSoCode() {
		return soCode;
	}

	public void setSoCode(String soCode) {
		this.soCode = soCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SaleOrder getSo() {
		return so;
	}

	public void setSo(SaleOrder so) {
		this.so = so;
	}

	public List<ShippingDtl> getDtls() {
		return dtls;
	}

	public void setDtls(List<ShippingDtl> dtls) {
		this.dtls = dtls;
	}
	
	
	
}
