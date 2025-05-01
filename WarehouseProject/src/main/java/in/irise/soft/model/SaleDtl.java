package in.irise.soft.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name="sales_dtl_tab")
public class SaleDtl {

	@Id
	@GeneratedValue
	@Column(name="sal_dtl_id_col")
	private Integer id;
	
	@Transient
	private Integer slno;
	
	@Column(name="sal_dtl_qty_col")
	private Integer qty;
	
	@ManyToOne
	@JoinColumn(name="part_id_fk")
	private Part part; //HAS-A
	
	@ManyToOne
	@JoinColumn(name="so_id_fk")
	private SaleOrder  so; //HAS-A

	public SaleDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SaleDtl(Integer id, Integer slno, Integer qty, Part part, SaleOrder so) {
		super();
		this.id = id;
		this.slno = slno;
		this.qty = qty;
		this.part = part;
		this.so = so;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSlno() {
		return slno;
	}

	public void setSlno(Integer slno) {
		this.slno = slno;
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

	public SaleOrder getSo() {
		return so;
	}

	public void setSo(SaleOrder so) {
		this.so = so;
	}
	
	
}
