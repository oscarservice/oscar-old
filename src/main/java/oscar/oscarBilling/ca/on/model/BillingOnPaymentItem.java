package oscar.oscarBilling.ca.on.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BillingOnPaymentItem {
	
	private static final long serialVersionUID = 5136238841649376528L;
	
	private Integer id;
    private Integer ch1_id;
    private Integer  billing_on_payment_id;
    private Integer  billing_on_item_id;
    private Timestamp  payment_timestamp;
   
    private BigDecimal paid;
    private BigDecimal refund;
    private BigDecimal discount;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCh1_id() {
		return ch1_id;
	}
	public void setCh1_id(Integer ch1_id) {
		this.ch1_id = ch1_id;
	}
	public Integer getBilling_on_payment_id() {
		return billing_on_payment_id;
	}
	public void setBilling_on_payment_id(Integer billing_on_payment_id) {
		this.billing_on_payment_id = billing_on_payment_id;
	}
	public Integer getBilling_on_item_id() {
		return billing_on_item_id;
	}
	public void setBilling_on_item_id(Integer billing_on_item_id) {
		this.billing_on_item_id = billing_on_item_id;
	}
	public Timestamp getPayment_timestamp() {
		return payment_timestamp;
	}
	public void setPayment_timestamp(Timestamp payment_timestamp) {
		this.payment_timestamp = payment_timestamp;
	}
	public BigDecimal getPaid() {
		return paid;
	}
	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}
	public BigDecimal getRefund() {
		return refund;
	}
	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
   

}
