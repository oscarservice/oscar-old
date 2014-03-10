/**
 * Copyright (c) 2001-2002. Department of Family Medicine, McMaster University. All Rights Reserved.
 * This software is published under the GPL GNU General Public License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * This software was written for the
 * Department of Family Medicine
 * McMaster University
 * Hamilton
 * Ontario, Canada
 */
package org.oscarehr.billing.CA.ON.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
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

	private BigDecimal total_payment = new BigDecimal("0.00");
	@Column(nullable=false)
	private Date paymentdate;
	
	private BigDecimal total_refund = new BigDecimal("0.00");
	
	private BigDecimal total_credit = new BigDecimal("0.00");
	
	private BigDecimal total_discount = new BigDecimal("0.00");
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@JoinColumn(name="ch1_id")
	private BillingClaimHeader1 billingONCheader1;
	
	private String creator;
	private int paymentTypeId; 

	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

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

	public BigDecimal getTotal_credit() {
		return total_credit;
	}

	public void setTotal_credit(BigDecimal total_credit) {
		this.total_credit = total_credit;
	}
}
