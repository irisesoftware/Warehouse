package in.irise.soft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="grn_tab")
public class Grn {
	@Id
	@GeneratedValue(generator = "grn_cust_gen")
	@GenericGenerator(name = "grn_cust_gen",
	strategy = "in.irise.soft.generator.MyGrnIdGen")
	@Column(name="grn_id_col")
	private String id;

	@Column(name="grn_code_col")
	private String grnCode;
	@Column(name="grn_type_col")
	private String grnType;
	@Column(name="grn_desc_col")
	private String grnDescription;

	@ManyToOne
	@JoinColumn(name="po_id_fk_col",unique = true)
	private PurchaseOrder po;

	public Grn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Grn(String id, String grnCode, String grnType, String grnDescription, PurchaseOrder po) {
		super();
		this.id = id;
		this.grnCode = grnCode;
		this.grnType = grnType;
		this.grnDescription = grnDescription;
		this.po = po;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGrnCode() {
		return grnCode;
	}

	public void setGrnCode(String grnCode) {
		this.grnCode = grnCode;
	}

	public String getGrnType() {
		return grnType;
	}

	public void setGrnType(String grnType) {
		this.grnType = grnType;
	}

	public String getGrnDescription() {
		return grnDescription;
	}

	public void setGrnDescription(String grnDescription) {
		this.grnDescription = grnDescription;
	}

	public PurchaseOrder getPo() {
		return po;
	}

	public void setPo(PurchaseOrder po) {
		this.po = po;
	}
	
	

}
