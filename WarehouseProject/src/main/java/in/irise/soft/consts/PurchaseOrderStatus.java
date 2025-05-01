package in.irise.soft.consts;

public enum PurchaseOrderStatus {

	OPEN("OPEN"), 
	PICKING("PICKING"), 
	ORDERED("ORDERED"),
	INVOICED("INVOICED"), 
	RECEIVED("RECEIVED")
	;
	
	private String value;
	
	private PurchaseOrderStatus(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
