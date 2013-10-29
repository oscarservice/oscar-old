package org.oscarehr.billing.CA.ON.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.oscarehr.billing.CA.model.BillingPaymentType;
import org.oscarehr.common.model.AbstractModel;

@Entity
@Table(name="billing_on_payment")
public class BillingONPayment extends AbstractModel<Integer> implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private BigDecimal total_payment;

	private Date paymentdate;
	
	private BigDecimal total_refund;
	
	private BigDecimal total_discount;
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@JoinColumn(name="ch1_id")
	private BillingClaimHeader1 billingONCheader1;

	@ManyToOne(optional=false)
	@JoinColumn(name="paymentTypeId")
	private BillingPaymentType billingPaymentType;

	public BillingONPayment() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getPaymentDate() {
		return this.paymentdate;
	}

	public void setPaymentDate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}

	public String getPaymentDateFormatted() {
		return this.paymentdate != null ? new SimpleDateFormat("yyyy-MM-dd").format(this.paymentdate) : "---";
	}

	public BillingClaimHeader1 getBillingONCheader1() {
		return this.billingONCheader1;
	}

	public void setBillingOnCheader1(BillingClaimHeader1 billingOnCheader1) {
		this.billingONCheader1 = billingOnCheader1;
	}

	public BillingPaymentType getBillingPaymentType() {
		return this.billingPaymentType;
	}

	public void setBillingPaymentType(BillingPaymentType billingPaymentType) {
		this.billingPaymentType = billingPaymentType;
	}

	public BigDecimal getTotal_payment() {
		return total_payment;
	}

	public void setTotal_payment(BigDecimal total_payment) {
		this.total_payment = total_payment;
	}

	public BigDecimal getTotal_refund() {
		return total_refund;
	}

	public void setTotal_refund(BigDecimal total_refund) {
		this.total_refund = total_refund;
	}

	public BigDecimal getTotal_discount() {
		return total_discount;
	}

	public void setTotal_discount(BigDecimal total_discount) {
		this.total_discount = total_discount;
	}
	
	

}
