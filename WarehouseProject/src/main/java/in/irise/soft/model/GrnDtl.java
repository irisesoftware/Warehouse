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
@Table(name="grn_dtl_tab")
public class GrnDtl {
	@Id
	@GeneratedValue
	@Column(name="grn_dtl_id_col")
	private Integer id;
	
	@Column(name="grn_dtl_code_col")
	private String itemCode;
	
	@Column(name="grn_dtl_cost_col")
	private Double baseCost;
	
	@Column(name="grn_dtl_qty_col")
	private Integer qty;
	
	@Column(name="grn_dtl_val_col")
	private Double itemValue;
	
	@Column(name="grn_dtl_status_col")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="grn_id_fk_col")
	private Grn grn;//HAS-A

	public GrnDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GrnDtl(Integer id, String itemCode, Double baseCost, Integer qty, Double itemValue, String status, Grn grn) {
		super();
		this.id = id;
		this.itemCode = itemCode;
		this.baseCost = baseCost;
		this.qty = qty;
		this.itemValue = itemValue;
		this.status = status;
		this.grn = grn;
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

	public Grn getGrn() {
		return grn;
	}

	public void setGrn(Grn grn) {
		this.grn = grn;
	}
	
	
}
