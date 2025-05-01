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
@Table(name = "sale_order_tab")
public class SaleOrder {
	@Id
	@GeneratedValue
	@Column(name = "sal_id_col")
	private Integer id;
	
	@Column(name = "sal_code_col")
	private String orderCode;

	@Column(name = "sal_refNum_col")
	private String referenceNumber;

	@Column(name = "sal_mode_col")
	private String stockMode;

	@Column(name = "sal_source_col")
	private String stockSource;

	@Column(name = "sal_status_col")
	private String status;

	@Column(name = "sal_desc_col")
	private String description;

	@ManyToOne
	@JoinColumn(name = "porder_shipCode_fk")
	private ShipmentType shipmentCode;

	@ManyToOne
	@JoinColumn(name = "porder_wu_vendor_fk")
	private WhUserType customer;
	
	@OneToMany(mappedBy = "so")
	private List<SaleDtl>Dtls;

	public SaleOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SaleOrder(Integer id, String orderCode, String referenceNumber, String stockMode, String stockSource,
			String status, String description, ShipmentType shipmentCode, WhUserType customer, List<SaleDtl> dtls) {
		super();
		this.id = id;
		this.orderCode = orderCode;
		this.referenceNumber = referenceNumber;
		this.stockMode = stockMode;
		this.stockSource = stockSource;
		this.status = status;
		this.description = description;
		this.shipmentCode = shipmentCode;
		this.customer = customer;
		Dtls = dtls;
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

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getStockMode() {
		return stockMode;
	}

	public void setStockMode(String stockMode) {
		this.stockMode = stockMode;
	}

	public String getStockSource() {
		return stockSource;
	}

	public void setStockSource(String stockSource) {
		this.stockSource = stockSource;
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

	public ShipmentType getShipmentCode() {
		return shipmentCode;
	}

	public void setShipmentCode(ShipmentType shipmentCode) {
		this.shipmentCode = shipmentCode;
	}

	public WhUserType getCustomer() {
		return customer;
	}

	public void setCustomer(WhUserType customer) {
		this.customer = customer;
	}

	public List<SaleDtl> getDtls() {
		return Dtls;
	}

	public void setDtls(List<SaleDtl> dtls) {
		Dtls = dtls;
	}
	
	
	
}
