package in.irise.soft.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="order_method_tab")
public class OrderMethod {
	@Id
	@GeneratedValue(generator = "om_seq")
	@SequenceGenerator(name = "om_seq",sequenceName = "om_seq_test")
	@Column(name="ord_id_col")
	private Integer id;
	@Column(name="ord_mode_col")
	private String orderMode;
	@Column(name="ord_code_col")
	private String orderCode;
	@Column(name="ord_type_col")
	private String orderType;
	
	@ElementCollection
	@CollectionTable(
			name="order_acpt_tab",
			joinColumns = @JoinColumn(name="ord_id_col")
			)
	@Column(name="ord_acpt_col")
	private Set<String> orderAcpt;
	
	
	@Column(name="ord_desc_col")
	private String orderDesc;


	public OrderMethod() {
		super();
		// TODO Auto-generated constructor stub
	}


	public OrderMethod(Integer id, String orderMode, String orderCode, String orderType, Set<String> orderAcpt,
			String orderDesc) {
		super();
		this.id = id;
		this.orderMode = orderMode;
		this.orderCode = orderCode;
		this.orderType = orderType;
		this.orderAcpt = orderAcpt;
		this.orderDesc = orderDesc;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getOrderMode() {
		return orderMode;
	}


	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}


	public String getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public Set<String> getOrderAcpt() {
		return orderAcpt;
	}


	public void setOrderAcpt(Set<String> orderAcpt) {
		this.orderAcpt = orderAcpt;
	}


	public String getOrderDesc() {
		return orderDesc;
	}


	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	
	
}
