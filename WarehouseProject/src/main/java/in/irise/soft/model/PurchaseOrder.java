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
@Table(name="purchase_order_tab")
public class PurchaseOrder {
	@Id
	@Column(name="po_id_col")
	@GeneratedValue
	private Integer id;
	
	@Column(name="po_code_col")
	private String orderCode;
	
	@Column(name="po_ref_num_col")
	private String refNumber;
	
	@Column(name="po_qty_chk_col")
	private String qltyCheck;
	
	@Column(name="po_status_col")
	private String status;
	
	@Column(name="po_desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="ship_type_id_fk_col")
	private ShipmentType shipment; //HAS-A
	
	@ManyToOne
	@JoinColumn(name="wh_user_id_fk_col")
	private WhUserType vendor; //HAS-A

	public PurchaseOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PurchaseOrder(Integer id, String orderCode, String refNumber, String qltyCheck, String status,
			String description, ShipmentType shipment, WhUserType vendor) {
		super();
		this.id = id;
		this.orderCode = orderCode;
		this.refNumber = refNumber;
		this.qltyCheck = qltyCheck;
		this.status = status;
		this.description = description;
		this.shipment = shipment;
		this.vendor = vendor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public String getQltyCheck() {
		return qltyCheck;
	}

	public void setQltyCheck(String qltyCheck) {
		this.qltyCheck = qltyCheck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ShipmentType getShipment() {
		return shipment;
	}

	public void setShipment(ShipmentType shipment) {
		this.shipment = shipment;
	}

	public WhUserType getVendor() {
		return vendor;
	}

	public void setVendor(WhUserType vendor) {
		this.vendor = vendor;
	}
	
	
}
