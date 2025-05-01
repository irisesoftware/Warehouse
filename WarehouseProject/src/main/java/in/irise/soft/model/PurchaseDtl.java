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
@Table(name="po_dtl_tab")
public class PurchaseDtl {
	@Id
	@GeneratedValue
	@Column(name="po_dtl_id_col")
	private Integer id;
	
	@Column(name="po_dtl_qty_col")
	private Integer qty;
	
	@ManyToOne
	@JoinColumn(name="part_id_fk_col")
	private Part part;
	
	@ManyToOne
	@JoinColumn(name="po_order_id_fk_col")
	private PurchaseOrder po;

	public PurchaseDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PurchaseDtl(Integer id, Integer qty, Part part, PurchaseOrder po) {
		super();
		this.id = id;
		this.qty = qty;
		this.part = part;
		this.po = po;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public PurchaseOrder getPo() {
		return po;
	}

	public void setPo(PurchaseOrder po) {
		this.po = po;
	}
	
	
}
