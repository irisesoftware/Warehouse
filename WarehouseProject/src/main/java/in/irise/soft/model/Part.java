package in.irise.soft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="part_tab")
public class Part {
	@Id
	@GeneratedValue(generator = "part_gen")
	@SequenceGenerator(name = "part_gen",sequenceName = "part_seq_gen")
	@Column(name="part_id_col")
	private Integer id;

	
	@Column(name="part_code_col")
	private String partCode;

	@Column(name="part_wid_col")
	private Double partWid;
	@Column(name="part_len_col")
	private Double partLen;
	@Column(name="part_ht_col")
	private Double partHt;
	
	@Column(name="part_cost_col")
	private Double partBaseCost;
	
	@Column(name="part_curr_col")
	private String partBaseCurrency;
	
	@Column(name="part_desc_col")
	private String partDesc;

	//---Integrations---------------
	@ManyToOne
	@JoinColumn(name="uom_id_fk_col")
	private Uom uom;//HAS-A
	
	@ManyToOne
	@JoinColumn(name="order_method_sale_fk_col")
	private OrderMethod omSale; //HAS-A

	public Part() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Part(Integer id, String partCode, Double partWid, Double partLen, Double partHt, Double partBaseCost,
			String partBaseCurrency, String partDesc, Uom uom, OrderMethod omSale) {
		super();
		this.id = id;
		this.partCode = partCode;
		this.partWid = partWid;
		this.partLen = partLen;
		this.partHt = partHt;
		this.partBaseCost = partBaseCost;
		this.partBaseCurrency = partBaseCurrency;
		this.partDesc = partDesc;
		this.uom = uom;
		this.omSale = omSale;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public Double getPartWid() {
		return partWid;
	}

	public void setPartWid(Double partWid) {
		this.partWid = partWid;
	}

	public Double getPartLen() {
		return partLen;
	}

	public void setPartLen(Double partLen) {
		this.partLen = partLen;
	}

	public Double getPartHt() {
		return partHt;
	}

	public void setPartHt(Double partHt) {
		this.partHt = partHt;
	}

	public Double getPartBaseCost() {
		return partBaseCost;
	}

	public void setPartBaseCost(Double partBaseCost) {
		this.partBaseCost = partBaseCost;
	}

	public String getPartBaseCurrency() {
		return partBaseCurrency;
	}

	public void setPartBaseCurrency(String partBaseCurrency) {
		this.partBaseCurrency = partBaseCurrency;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public Uom getUom() {
		return uom;
	}

	public void setUom(Uom uom) {
		this.uom = uom;
	}

	public OrderMethod getOmSale() {
		return omSale;
	}

	public void setOmSale(OrderMethod omSale) {
		this.omSale = omSale;
	}
	
	
}
