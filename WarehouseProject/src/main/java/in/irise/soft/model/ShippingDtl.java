package in.irise.soft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="shipping_dtl_tab")
public class ShippingDtl {

	@Id
	@GeneratedValue
	@Column(name="shipping_dtl_id")
	private Integer id;

	@Column(name="shipping_dtl_code")
	private String itemCode;

	@Column(name="shipping_dtl_cost")
	private Double baseCost;

	@Column(name="shipping_dtl_qty")
	private Integer qty;

	@Column(name="shipping_dtl_value")
	private Double itemValue;
	
	@Column(name="shipping_dtl_status")
	private String status;

	@ManyToOne
	@JoinColumn(name="shipping_id_fk")
	private Shipping shipping;

	public ShippingDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShippingDtl(Integer id, String itemCode, Double baseCost, Integer qty, Double itemValue, String status,
			Shipping shipping) {
		super();
		this.id = id;
		this.itemCode = itemCode;
		this.baseCost = baseCost;
		this.qty = qty;
		this.itemValue = itemValue;
		this.status = status;
		this.shipping = shipping;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Double getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Double getItemValue() {
		return itemValue;
	}

	public void setItemValue(Double itemValue) {
		this.itemValue = itemValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	
	
	
}
