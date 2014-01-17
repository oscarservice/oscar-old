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
import javax.persistence.*;

import org.oscarehr.common.model.AbstractModel;

import oscar.util.UtilDateUtilities;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the billing_on_transaction database table.
 * 
 */
@Entity
@Table(name="billing_on_transaction")
public class BillingOnTransaction extends AbstractModel<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="action_type", length=1)
	private String actionType;

	@Temporal(TemporalType.DATE)
	@Column(name="admission_date")
	private Date admissionDate;

	@Temporal(TemporalType.DATE)
	@Column(name="billing_date")
	private Date billingDate;

	@Column(name="billing_notes", length=255)
	private String billingNotes;

	@Column(name="billing_on_item_id", nullable=false)
	private int billingOnItemId;

	@Column(name="ch1_id", nullable=false)
	private int ch1Id;

	@Column(length=30)
	private String clinic;

	@Column(length=30)
	private String creator;

	@Column(name="demographic_no", nullable=false)
	private int demographicNo = 0;

	@Column(name="dx_code", length=3)
	private String dxCode;

	@Column(name="facility_num", length=4)
	private String facilityNum;

	@Column(name="man_review", length=1)
	private String manReview;

	@Column(name="pay_program", length=3)
	private String payProgram = "HCP";

	@Temporal(TemporalType.DATE)
	@Column(name="payment_date")
	private Date paymentDate;

	@Column(name="payment_id", nullable=false)
	private int paymentId;

	@Column(length=1)
	private int paymentType = 0;

	@Column(name="provider_no", length=6)
	private String providerNo;

	@Column(length=2)
	private String province = "ON";

	@Column(name="ref_num", length=6)
	private String refNum;

	@Column(name="service_code", length=10)
	private String serviceCode;

	@Column(name="service_code_discount", precision=10, scale=2)
	private BigDecimal serviceCodeDiscount;

	@Column(name="service_code_invoiced", length=64)
	private String serviceCodeInvoiced;

	@Column(name="service_code_num", length=2)
	private String serviceCodeNum = "01";

	@Column(name="service_code_paid", precision=10, scale=2)
	private BigDecimal serviceCodePaid;

	@Column(name="service_code_refund", precision=10, scale=2)
	private BigDecimal serviceCodeRefund;

	@Column(name="sli_code", length=10)
	private String sliCode;

	@Column(length=1)
	private String status;

	@Column(name="update_datetime", nullable=false)
	private Timestamp updateDatetime = new Timestamp(UtilDateUtilities.now().getTime());

	@Column(name="update_provider_no", nullable=false, length=6)
	private String updateProviderNo;

	@Column(length=2)
	private String visittype;

	public BillingOnTransaction() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActionType() {
		return this.actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Date getAdmissionDate() {
		return this.admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	public Date getBillingDate() {
		return this.billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public String getBillingNotes() {
		return this.billingNotes;
	}

	public void setBillingNotes(String billingNotes) {
		this.billingNotes = billingNotes;
	}

	public int getBillingOnItemId() {
		return this.billingOnItemId;
	}

	public void setBillingOnItemId(int billingOnItemId) {
		this.billingOnItemId = billingOnItemId;
	}

	public int getCh1Id() {
		return this.ch1Id;
	}

	public void setCh1Id(int ch1Id) {
		this.ch1Id = ch1Id;
	}

	public String getClinic() {
		return this.clinic;
	}

	public void setClinic(String clinic) {
		this.clinic = clinic;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getDemographicNo() {
		return this.demographicNo;
	}

	public void setDemographicNo(int demographicNo) {
		this.demographicNo = demographicNo;
	}

	public String getDxCode() {
		return this.dxCode;
	}

	public void setDxCode(String dxCode) {
		this.dxCode = dxCode;
	}

	public String getFacilityNum() {
		return this.facilityNum;
	}

	public void setFacilityNum(String facilityNum) {
		this.facilityNum = facilityNum;
	}

	public String getManReview() {
		return this.manReview;
	}

	public void setManReview(String manReview) {
		this.manReview = manReview;
	}

	public String getPayProgram() {
		return this.payProgram;
	}

	public void setPayProgram(String payProgram) {
		this.payProgram = payProgram;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public String getProviderNo() {
		return this.providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRefNum() {
		return this.refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getServiceCode() {
		return this.serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public BigDecimal getServiceCodeDiscount() {
		return this.serviceCodeDiscount;
	}

	public void setServiceCodeDiscount(BigDecimal serviceCodeDiscount) {
		this.serviceCodeDiscount = serviceCodeDiscount;
	}

	public String getServiceCodeInvoiced() {
		return this.serviceCodeInvoiced;
	}

	public void setServiceCodeInvoiced(String serviceCodeInvoiced) {
		this.serviceCodeInvoiced = serviceCodeInvoiced;
	}

	public String getServiceCodeNum() {
		return this.serviceCodeNum;
	}

	public void setServiceCodeNum(String serviceCodeNum) {
		this.serviceCodeNum = serviceCodeNum;
	}

	public BigDecimal getServiceCodePaid() {
		return this.serviceCodePaid;
	}

	public void setServiceCodePaid(BigDecimal serviceCodePaid) {
		this.serviceCodePaid = serviceCodePaid;
	}

	public BigDecimal getServiceCodeRefund() {
		return this.serviceCodeRefund;
	}

	public void setServiceCodeRefund(BigDecimal serviceCodeRefund) {
		this.serviceCodeRefund = serviceCodeRefund;
	}

	public String getSliCode() {
		return this.sliCode;
	}

	public void setSliCode(String sliCode) {
		this.sliCode = sliCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateProviderNo() {
		return this.updateProviderNo;
	}

	public void setUpdateProviderNo(String updateProviderNo) {
		this.updateProviderNo = updateProviderNo;
	}

	public String getVisittype() {
		return this.visittype;
	}

	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}

}