package in.irise.soft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

//ctrl+shift+O
@Data
@Entity
@Table(name="shipment_type_tab")
public class ShipmentType {
	@Id
	@Column(name="ship_id_col")
	@GeneratedValue(generator = "ship_type_seq")
	@SequenceGenerator(name = "ship_type_seq",sequenceName = "ship_type_seq_test")
	private Integer id;
	
	@Column(name="ship_mode_col")
	private String shipmentMode;
	
	@Column(name="ship_code_col")
	private String shipmentCode;
	
	@Column(name="ship_enbl_col")
	private String enableShipment;
	
	@Column(name="ship_grde_col")
	private String shipmentGrade;
	
	@Column(name="ship_desc_col")
	private String shipmentDescription;

	public ShipmentType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShipmentType(Integer id, String shipmentMode, String shipmentCode, String enableShipment,
			String shipmentGrade, String shipmentDescription) {
		super();
		this.id = id;
		this.shipmentMode = shipmentMode;
		this.shipmentCode = shipmentCode;
		this.enableShipment = enableShipment;
		this.shipmentGrade = shipmentGrade;
		this.shipmentDescription = shipmentDescription;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShipmentMode() {
		return shipmentMode;
	}

	public void setShipmentMode(String shipmentMode) {
		this.shipmentMode = shipmentMode;
	}

	public String getShipmentCode() {
		return shipmentCode;
	}

	public void setShipmentCode(String shipmentCode) {
		this.shipmentCode = shipmentCode;
	}

	public String getEnableShipment() {
		return enableShipment;
	}

	public void setEnableShipment(String enableShipment) {
		this.enableShipment = enableShipment;
	}

	public String getShipmentGrade() {
		return shipmentGrade;
	}

	public void setShipmentGrade(String shipmentGrade) {
		this.shipmentGrade = shipmentGrade;
	}

	public String getShipmentDescription() {
		return shipmentDescription;
	}

	public void setShipmentDescription(String shipmentDescription) {
		this.shipmentDescription = shipmentDescription;
	}
	
	
	
	
	
}
