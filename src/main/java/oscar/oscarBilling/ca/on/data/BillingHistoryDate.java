package oscar.oscarBilling.ca.on.data;

public class BillingHistoryDate {
	
	String id;
	String timestamp1;
	String total_payment;
	String total_discount;
	String total_refund;
	String total;
	String pay_program;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTimestamp1() {
		return timestamp1;
	}
	public void setTimestamp1(String timestamp1) {
		this.timestamp1 = timestamp1;
	}
	public String getTotal_payment() {
		return total_payment;
	}
	public void setTotal_payment(String total_payment) {
		this.total_payment = total_payment;
	}
	public String getTotal_refund() {
		return total_refund;
	}
	public void setTotal_refund(String total_refund) {
		this.total_refund = total_refund;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTotal_discount() {
		return total_discount;
	}
	public void setTotal_discount(String total_discount) {
		this.total_discount = total_discount;
	}
	public String getPay_program() {
		return pay_program;
	}
	public void setPay_program(String pay_program) {
		this.pay_program = pay_program;
	}
	
	
	
	
	

}
