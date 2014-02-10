/**
 * Copyright (c) 2006-. OSCARservice, OpenSoft System. All Rights Reserved.
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
 */
package oscar.oscarBilling.ca.on.pageUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.oscarehr.billing.CA.ON.dao.BillingClaimDAO;
import org.oscarehr.billing.CA.ON.dao.BillingItemDao;
import org.oscarehr.billing.CA.ON.dao.BillingONExtDao;
import org.oscarehr.billing.CA.ON.dao.BillingONPaymentDao;
import org.oscarehr.billing.CA.ON.dao.BillingOnTransactionDao;
import org.oscarehr.billing.CA.ON.model.BillingClaimHeader1;
import org.oscarehr.billing.CA.ON.model.BillingItem;
import org.oscarehr.billing.CA.ON.model.BillingONExt;
import org.oscarehr.billing.CA.ON.model.BillingONPayment;
import org.oscarehr.billing.CA.ON.model.BillingOnItemPayment;
import org.oscarehr.billing.CA.ON.model.BillingOnTransaction;
import org.oscarehr.billing.CA.dao.BillingPaymentTypeDao;
import org.oscarehr.util.MiscUtils;
import org.oscarehr.util.SpringUtils;
import org.oscarehr.billing.CA.ON.dao.BillingOnItemPaymentDao;


import oscar.oscarBilling.ca.on.dao.BillingOnItemDao;
import oscar.oscarBilling.ca.on.data.BillingClaimHeader1Data;
import oscar.oscarBilling.ca.on.data.BillingDataHlp;
import oscar.oscarBilling.ca.on.data.BillingItemData;
import oscar.oscarBilling.ca.on.data.BillingProviderData;
import oscar.oscarBilling.ca.on.data.JdbcBilling3rdPartImpl;
import oscar.oscarBilling.ca.on.data.JdbcBillingClaimImpl;
import oscar.oscarBilling.ca.on.data.JdbcBillingCodeImpl;
import oscar.oscarBilling.ca.on.data.JdbcBillingCorrection;
import oscar.oscarBilling.ca.on.data.JdbcBillingPageUtil;
import oscar.oscarBilling.ca.on.data.JdbcBillingReviewImpl;
import oscar.oscarBilling.ca.on.model.BillingOnItem;

import oscar.util.StringUtils;

public class BillingCorrectionPrep {
	private static final Logger _logger = Logger
			.getLogger(BillingCorrectionPrep.class);

	JdbcBillingCorrection dbObj = new JdbcBillingCorrection();
	BillingItemDao item = new BillingItemDao();

	//
	public List getBillingRecordObj(String id) {
		List ret = dbObj.getBillingRecordObj(id);
		return ret;
	}

	// get error code
	public List getBillingExplanatoryList(String id) {
		List ret = dbObj.getBillingExplanatoryList(id);
		return ret;
	}

	// get rejected code
	public List getBillingRejectList(String id) {
		List ret = dbObj.getBillingRejectList(id);
		return ret;
	}

	// compare the obj first, push old record to repo if needed.
	public boolean updateBillingClaimHeader(BillingClaimHeader1Data ch1Obj,
			HttpServletRequest requestData) {
		boolean ret = true;
		String status;
		if (isChangedBillingClaimHeader(ch1Obj, requestData)) {
			int i = dbObj.addRepoClaimHeader(ch1Obj);
			_logger.info("updateBillingClaimHeader(old value = " + i
					+ ch1Obj.getStatus() + "|" + ch1Obj.getRef_num() + "|"
					+ ch1Obj.getAdmission_date() + "|" 
					+ ch1Obj.getFacilty_num() + "|" + ch1Obj.getMan_review()
					+ "|" + ch1Obj.getBilling_date() + "|"
					+ ch1Obj.getProviderNo() + "|" + ch1Obj.getCreator());

			status = requestData.getParameter("status").substring(0, 1);
			if (status.equals("S") && !ch1Obj.getStatus().equals(status)) {
				this.updateExt("payDate", requestData);
			}
			ch1Obj.setStatus(status);
			
			if(requestData.getParameter("status").substring(0,1).equals("N")){
				ch1Obj.setPay_program("NOT");				
			}

			ch1Obj.setRef_num(requestData.getParameter("rdohip"));
			ch1Obj.setVisittype(requestData.getParameter("visittype"));

			ch1Obj.setAdmission_date(requestData.getParameter("xml_vdate"));
			ch1Obj.setFacilty_num(requestData.getParameter("clinic_ref_code"));
			ch1Obj.setMan_review(requestData.getParameter("m_review") == null ? ""
					: "Y");
			ch1Obj.setBilling_date(requestData
					.getParameter("xml_appointment_date"));
			ch1Obj.setProviderNo(requestData.getParameter("provider_no"));
			ch1Obj.setComment(requestData.getParameter("comment"));

			// provider_ohip_no update as well
			BillingProviderData otemp = (new JdbcBillingPageUtil())
					.getProviderObj(requestData.getParameter("provider_no"));
			ch1Obj.setProvider_ohip_no(otemp.getOhipNo());
			ch1Obj.setProvider_rma_no(otemp.getRmaNo());
			ch1Obj.setCreator((String) requestData.getSession().getAttribute(
					"user"));

			ch1Obj.setClinic(requestData.getParameter("site"));

			ch1Obj.setProvince(requestData.getParameter("hc_type"));

			ch1Obj.setLocation(requestData.getParameter("xml_slicode"));

			ch1Obj.setPay_program(requestData.getParameter("payProgram"));
			ret = dbObj.updateBillingClaimHeader(ch1Obj);
			
			if ("D".equals(ch1Obj.getStatus())) {
				// change status in billing_on_item table
				BillingOnItemDao billOnItemDao = (BillingOnItemDao)SpringUtils.getBean(BillingOnItemDao.class);
				List<BillingOnItem> billOnItems = billOnItemDao.getBillingItemByCh1Id(Integer.parseInt(ch1Obj.getId()));
				for (BillingOnItem billOnItem : billOnItems) {
					billOnItem.setStatus("D");
					billOnItemDao.addBillingOnItem(billOnItem); // this statement can update billing_on_item table 
				}
			}
		}

		// set inactive 3rd party payment record if user switched from 3rd party
		// to some other pay program
		String payProgram = requestData.getParameter("payProgram");
		if (requestData.getParameter("oldStatus").equals("thirdParty")
				&& ("HCP".equals(payProgram) || "RMB".equals(payProgram) || "WCB"
						.equals(payProgram))) {
			setInactive(BillingONExtDao.KEY_PAYMENT, requestData);
			setInactive(BillingONExtDao.KEY_TOTAL, requestData);
			setInactive(BillingONExtDao.KEY_DISCOUNT, requestData);
			setInactive(BillingONExtDao.KEY_REFUND, requestData);
			setInactive(BillingONExtDao.KEY_PAY_DATE, requestData);
			setInactive("billTo", requestData);
		}

		// 3rd party elements
		if (payProgram.matches(BillingDataHlp.BILLINGMATCHSTRING_3RDPARTY)) {
			if (requestData.getParameter("payment") != null) {
				ret = update3rdPartyItem(BillingONExtDao.KEY_DISCOUNT, requestData);
				ret = update3rdPartyItem(BillingONExtDao.KEY_TOTAL, requestData);
				ret = update3rdPartyItem(BillingONExtDao.KEY_PAYMENT, requestData);
				ret = update3rdPartyItem(BillingONExtDao.KEY_REFUND, requestData);
				ret = update3rdPartyItem(BillingONExtDao.KEY_PAY_DATE, requestData);
				ch1Obj.setPaid(requestData.getParameter("payment"));
				ret = dbObj.updateBillingClaimHeader(ch1Obj);
			}

			if (requestData.getParameter("billTo") != null) {
				ret = update3rdPartyItem("billTo", requestData);
			}
		}
		return ret;
	}

	public void setInactive(String key, HttpServletRequest request) {
		JdbcBilling3rdPartImpl tobj = new JdbcBilling3rdPartImpl();
		String billingNo = request.getParameter("xml_billing_no");
		tobj.updateKeyStatus(billingNo, key, JdbcBilling3rdPartImpl.INACTIVE);
	}

	/*
	 * Need to use billing extension table to capture data for invoices in
	 * addition to 3rd party bills
	 */
	public void updateExt(String key, HttpServletRequest request) {
		update3rdPartyItem(key, request);
	}

	public boolean update3rdPartyItem(String key, HttpServletRequest request) {
		boolean ret = true;
		JdbcBilling3rdPartImpl tobj = new JdbcBilling3rdPartImpl();
		String billingNo = request.getParameter("xml_billing_no");
		if (tobj.keyExists(billingNo, key)) {
			String val = request.getParameter(key);
			if (val == null && BillingONExtDao.isNumberKey(key)) {
				val = "0.00";
			}
			ret = tobj.updateKeyValue(billingNo, key, val);
		} else {
			ret = tobj.add3rdBillExt(billingNo, request.getParameter("demoNo"),
					key, request.getParameter(key));
		}
		return ret;
	}

	public boolean update3rdPartyItem(HttpServletRequest request) {
		boolean ret = true;
		JdbcBilling3rdPartImpl tobj = new JdbcBilling3rdPartImpl();
		String billingNo = request.getParameter("xml_billing_no");
		tobj.updateKeyValue(billingNo, "payment",
				request.getParameter("payment"));
		tobj.updateKeyValue(billingNo, "refund", request.getParameter("refund"));
		return ret;
	}

	public boolean updateBillingItem(List lItemObj, HttpServletRequest request) {
		boolean ret = true; // dbObj.updateBillingClaimHeader(ch1Obj);
		// _logger.info("updateBillingItem(old value = ");

		BillingClaimHeader1Data ch1Obj = (BillingClaimHeader1Data) lItemObj.get(0);
		String updateProviderNo = (String) request.getSession().getAttribute("user");
		lItemObj.remove(0);

		Vector vecName = new Vector();
		Vector vecUnit = new Vector();
		Vector vecFee = new Vector();
		Vector vecStatus = new Vector();
		String dx = request.getParameter("xml_diagnostic_detail");
		dx = dx.length() > 2 ? dx.substring(0, 3) : dx;
		String serviceDate = request.getParameter("xml_appointment_date");

		for (int i = 0; i < BillingDataHlp.FIELD_MAX_SERVICE_NUM; i++) {
			vecName.add(request.getParameter("servicecode" + i));
			vecUnit.add(request.getParameter("billingunit" + i));
			vecFee.add(request.getParameter("billingamount" + i));
			String billStatus = request.getParameter("itemStatus" + i);
			if (billStatus != null) {
				vecStatus.add("S");
			} else {
				vecStatus.add(ch1Obj.getStatus());
			}
			
		}

		// update item first
		String claimId = "0";
		for (int i = 0; i < lItemObj.size(); i++) {
			BillingItemData iObj = (BillingItemData) lItemObj.get(i);
			BillingOnItem billOnItem = changeItem(ch1Obj, iObj, updateProviderNo, dx, serviceDate,
					(String)vecName.get(i), (String)vecUnit.get(i), (String)vecFee.get(i), (String)vecStatus.get(i));
			//claimId = iObj.getCh1_id();
			if (billOnItem != null) {
				// this condition indicates one service code item was changed
				iObj.setService_code(billOnItem.getService_code());
				iObj.setFee(billOnItem.getFee());
				iObj.setSer_num(billOnItem.getSer_num());
			}
			_logger.info(iObj.getService_code());
		}

		// add item if possible
		Vector<Object> elemToDel = new Vector<Object>();
		elemToDel.add(null);
		elemToDel.add("");
		vecName.removeAll(elemToDel);
		for (int i = 0; i < vecName.size(); i++) {
			if (vecName.get(i) == null || ((String)vecName.get(i)).isEmpty()) {
				continue;
			}
			String sName = (String) vecName.get(i);
			String sUnit = (String) vecUnit.get(i);
			String sFee = (String) vecFee.get(i);
			String sStatus = (String) vecStatus.get(i);
			ret = addItem(ch1Obj, lItemObj, updateProviderNo, dx, serviceDate,
					sName, sUnit, sFee, sStatus);
			_logger.info(sName + " lItemObj(value = " + ret);
		}

		// recalculate amount
		String newAmount = sumFee(vecFee);
		_logger.info(" lItemObj(newAmount = " + newAmount);
		updateAmount(newAmount, ch1Obj.getId(), updateProviderNo, dx);

		// update total field in billing_on_ext if pay_program is 3rd party
		if (ch1Obj.getPay_program().matches(
				BillingDataHlp.BILLINGMATCHSTRING_3RDPARTY)) {
			BillingONExtDao billOnExtDao = SpringUtils
					.getBean(BillingONExtDao.class);
			if (null != billOnExtDao) {
				int billingNo = Integer.parseInt(ch1Obj.getId());
				int demographicNo = Integer
						.parseInt(ch1Obj.getDemographic_no());
				BillingONExt billOnExt = billOnExtDao.getClaimExtItem(
						billingNo, demographicNo, "payDate");
				if (billOnExt != null) {
					// update total,provider_no payDate field has already been
					// updated
					billOnExtDao.setExtItem(billingNo, demographicNo, "total",
							newAmount, billOnExt.getDateTime(), '1');
				}
			}
		}

		return ret;
	}

	// billing correction
	public String getBillingCodeDesc(String codeName) {
		String ret = null;
		JdbcBillingCodeImpl dbCodeObj = new JdbcBillingCodeImpl();
		List descL = dbCodeObj.getBillingCodeAttr(codeName);
		ret = descL.size() > 1 ? (String) descL.get(1) : "Unknown";
		return ret;
	}

	// billing correction
	public Properties getBillingCodeDesc(List codeName) {
		JdbcBillingCodeImpl dbCodeObj = new JdbcBillingCodeImpl();
		Properties ret = new Properties();
		for (int i = 0; i < codeName.size(); i++) {
			List descL = dbCodeObj.getBillingCodeAttr((String) codeName.get(i));
			String desc = descL.size() > 1 ? (String) descL.get(1) : "Unknown";
			ret.setProperty((String) codeName.get(i), desc);
		}
		return ret;
	}

	// from billing correction
	public List getBillingClaimHeaderObj(String ch1Id) {
		List recordObj = null;
		recordObj = getBillingRecordObj(ch1Id);
		return recordObj;
	}

	// sql - set key=value, key1=value1, ...
	private boolean isChangedBillingClaimHeader(
			BillingClaimHeader1Data existObj, HttpServletRequest request) {
		boolean ret = false;
		if (existObj == null)
			return ret;
		// _logger.info("isChangedBillingClaimHeader(old value = " +
		// existObj.getStatus() + "|" + existObj.getRef_num()
		// + "|" + existObj.getAdmission_date() + "|" +
		// existObj.getFacilty_num() + "|" + existObj.getMan_review()
		// + "|" + existObj.getBilling_date() + "|" + existObj.getProviderNo()
		// + "|" + existObj.getPay_program());
		String temp = request.getParameter("m_review") == null ? "" : "Y";
		if (existObj.getStatus() != null
				&& request.getParameter("status") != null
				&& !existObj.getStatus().equals(
						request.getParameter("status").substring(0, 1)))
			MiscUtils.getLogger().debug("status");
		if (existObj.getPay_program() != null
				&& !existObj.getPay_program().equals(
						request.getParameter("payProgram")))
			MiscUtils.getLogger().debug("payProgram");
		if (existObj.getRef_num() != null
				&& !existObj.getRef_num()
						.equals(request.getParameter("rdohip")))
			MiscUtils.getLogger().debug("rdohip");
		if (existObj.getVisittype() != null
				&& !existObj.getVisittype().equals(
						request.getParameter("visittype")))
			MiscUtils.getLogger().debug("visittype");
		if (existObj.getAdmission_date() != null
				&& !existObj.getAdmission_date().equals(
						request.getParameter("xml_vdate")))
			MiscUtils.getLogger().debug("xml_vdate");

		if (existObj.getFacilty_num() != null
				&& !existObj.getFacilty_num().equals(
						request.getParameter("clinic_ref_code")))
			MiscUtils.getLogger().debug("facNum:" + existObj.getFacilty_num());
		if (existObj.getMan_review() != null
				&& !existObj.getMan_review().equals(temp))
			MiscUtils.getLogger().debug(
					"|" + existObj.getMan_review() + ":temp:" + temp + "|");
		if (existObj.getBilling_date() != null
				&& !existObj.getBilling_date().equals(
						request.getParameter("xml_appointment_date")))
			MiscUtils.getLogger().debug("Billing_date");
		if (existObj.getProviderNo() != null
				&& !existObj.getProviderNo().equals(
						request.getParameter("provider_no")))
			MiscUtils.getLogger().debug("getProvider_no");

		if ((existObj.getStatus() != null
				&& request.getParameter("status") != null && !existObj
				.getStatus().equals(
						request.getParameter("status").substring(0, 1)))
				|| (existObj.getPay_program() != null && !existObj
						.getPay_program().equals(
								request.getParameter("payProgram")))
				|| (existObj.getRef_num() != null && !existObj.getRef_num()
						.equals(request.getParameter("rdohip")))
				|| (existObj.getVisittype() != null && !existObj.getVisittype()
						.equals(request.getParameter("visittype")))
				|| (existObj.getAdmission_date() != null && !existObj
						.getAdmission_date().equals(
								request.getParameter("xml_vdate")))
				|| (existObj.getFacilty_num() != null && !existObj
						.getFacilty_num().equals(
								request.getParameter("clinic_ref_code")))
				|| (existObj.getMan_review() != null && !existObj
						.getMan_review().equals(temp))
				|| (existObj.getBilling_date() != null && !existObj
						.getBilling_date().equals(
								request.getParameter("xml_appointment_date")))
				|| (existObj.getComment() != null && !existObj.getComment()
						.equals(request.getParameter("comment")))
				|| (existObj.getProviderNo() != null && !existObj
						.getProviderNo().equals(
								request.getParameter("provider_no")))
				|| (existObj.getLocation() != null && !existObj.getLocation()
						.equals(request.getParameter("xml_slicode")))
				|| !StringUtils.nullSafeEquals(existObj.getClinic(),
						request.getParameter("site"))
				|| (existObj.getProvince() != null && !existObj.getProvince()
						.equals(request.getParameter("hc_type")))) {
			ret = true;
		}
		return ret;
	}
	
	private BillingOnItem changeItem(BillingClaimHeader1Data ch1Obj,
			BillingItemData oldObj, String updateProviderNo, String sDx,
			String serviceDate, String serviceCode, String unit, String fee,
			String status) {
		boolean ret = true;
		if (oldObj.getService_code().equals(serviceCode)) {
			// change to settle or not
			boolean bStatusChange = false;
			String cStatus = (String) oldObj.getStatus();
			if ((!oldObj.getStatus().equals("S") && cStatus.equals("S"))
					|| (oldObj.getStatus().equals("S") && !cStatus.equals("S"))) {
				bStatusChange = true;
			}

			if (!oldObj.getSer_num().equals(unit)
					|| !oldObj.getFee().equals(fee)
					|| !oldObj.getDx().equals(sDx)
					|| !oldObj.getService_date().equals(serviceDate)
					|| bStatusChange) {
				int j = dbObj.addRepoOneItem(oldObj);
				if (j == 0) {
					return null;
				}
				oldObj.setSer_num(getUnit(unit));
				oldObj.setFee(getFee(fee, getUnit(unit), serviceCode, serviceDate));
				oldObj.setService_date(serviceDate);
				oldObj.setDx(sDx);
				oldObj.setStatus(cStatus);
				ret = dbObj.updateBillingOneItem(oldObj);
				if (ret) {
					dbObj.addUpdateOneBillItemTrans(ch1Obj, oldObj, updateProviderNo);
				}
			}
		} else {
			oldObj.setStatus("D");
			ret = dbObj.updateBillingOneItem(oldObj);
			// add one transaction: delete a service code
			BillingClaimDAO billClaimDao = (BillingClaimDAO)SpringUtils.getBean(BillingClaimDAO.class);
			BillingClaimHeader1 billCheader1 = billClaimDao.find(Integer.parseInt(oldObj.getCh1_id()));
			
			BillingOnTransactionDao billOnTransDao = (BillingOnTransactionDao)SpringUtils.getBean(BillingOnTransactionDao.class);
			BillingOnTransaction billTrans = new BillingOnTransaction();
			billTrans.setActionType(BillingDataHlp.ACTION_TYPE.D.name());
			try {
				billTrans.setAdmissionDate(new SimpleDateFormat("yyyy-MM-dd").parse(billCheader1.getAdmission_date()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				billTrans.setAdmissionDate(null);
			}
			billTrans.setBillingDate(billCheader1.getBilling_date());
			billTrans.setBillingNotes(billCheader1.getComment1());
			billTrans.setCh1Id(billCheader1.getId());
			billTrans.setClinic(billCheader1.getClinic());
			billTrans.setCreator(updateProviderNo);
			billTrans.setDemographicNo(billCheader1.getDemographic_no());
			billTrans.setDxCode(sDx);
			billTrans.setFacilityNum(billCheader1.getFacilty_num());
			billTrans.setManReview(billCheader1.getMan_review());
			billTrans.setProviderNo(billCheader1.getProvider_no());
			billTrans.setProvince(billCheader1.getProvince());
			billTrans.setPayProgram(billCheader1.getPay_program());
			billTrans.setRefNum(billCheader1.getRef_num());
			billTrans.setServiceCode(oldObj.getService_code());
			billTrans.setServiceCodeNum(oldObj.getSer_num());
			billTrans.setServiceCodeInvoiced(oldObj.getFee());
			billTrans.setSliCode(billCheader1.getLocation());
			billTrans.setUpdateProviderNo(updateProviderNo);
			billTrans.setVisittype(billCheader1.getVisittype());
			billTrans.setUpdateDatetime(new Timestamp(new Date().getTime()));
			billTrans.setStatus("D");
			billOnTransDao.persist(billTrans);
			
			if (serviceCode != null && !serviceCode.isEmpty()) {
				// this condition indicates modification
				// persist a new service code item
				BillingOnItem newBillItem = addItem(oldObj, updateProviderNo, sDx, serviceDate, serviceCode, unit, fee, status);
				// add one transaction: add a service code
				billTrans = new BillingOnTransaction();
				billTrans.setActionType(BillingDataHlp.ACTION_TYPE.C.name());
				try {
					billTrans.setAdmissionDate(new SimpleDateFormat("yyyy-MM-dd").parse(billCheader1.getAdmission_date()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					billTrans.setAdmissionDate(null);
				}
				billTrans.setBillingDate(billCheader1.getBilling_date());
				billTrans.setBillingNotes(billCheader1.getComment1());
				billTrans.setCh1Id(billCheader1.getId());
				billTrans.setClinic(billCheader1.getClinic());
				billTrans.setCreator(updateProviderNo);
				billTrans.setDemographicNo(billCheader1.getDemographic_no());
				billTrans.setDxCode(sDx);
				billTrans.setFacilityNum(billCheader1.getFacilty_num());
				billTrans.setManReview(billCheader1.getMan_review());
				billTrans.setPayProgram(billCheader1.getPay_program());
				billTrans.setProviderNo(billCheader1.getProvider_no());
				billTrans.setProvince(billCheader1.getProvince());
				billTrans.setRefNum(billCheader1.getRef_num());
				billTrans.setServiceCode(serviceCode);
				billTrans.setServiceCodeInvoiced(fee);
				billTrans.setServiceCodeNum(unit);
				billTrans.setSliCode(billCheader1.getLocation());
				billTrans.setUpdateProviderNo(updateProviderNo);
				billTrans.setVisittype(billCheader1.getVisittype());
				billTrans.setUpdateDatetime(new Timestamp(new Date().getTime()));
				billTrans.setStatus(status);
				billOnTransDao.persist(billTrans);

				if (ch1Obj.getPay_program().matches(BillingDataHlp.BILLINGMATCHSTRING_3RDPARTY)) {
					// get which item_payments are associated to this service code
					BillingOnItemPaymentDao billOnItemPaymentDao = (BillingOnItemPaymentDao)SpringUtils.getBean(BillingOnItemPaymentDao.class);
					List<BillingOnItemPayment> billOnItemPaymentList = billOnItemPaymentDao.getAllByItemId(Integer.parseInt(oldObj.getId()));
					
					// update item_payments
					if (billOnItemPaymentList != null && billOnItemPaymentList.size() > 0) {
						for (BillingOnItemPayment billOnItemPayment : billOnItemPaymentList) {
							billOnItemPayment.setBillingOnItemId(newBillItem.getId());
							billOnItemPaymentDao.merge(billOnItemPayment);							
						}
					}
				}
				
				return newBillItem;
			}
		}
		
		return null;
	}

	private BillingOnItem addItem(BillingItemData oldObj, String updateProviderNo, String sDx,
			String serviceDate, String serviceCode, String unit, String fee,
			String status) {
		BillingOnItem billOnItem = new BillingOnItem();
		billOnItem.setCh1_id(Integer.parseInt(oldObj.getCh1_id()));
		billOnItem.setDx(sDx);
		billOnItem.setService_code(serviceCode);
		billOnItem.setSer_num(unit);
		billOnItem.setFee(fee);
		billOnItem.setStatus(status);
		try {
			billOnItem.setService_date(new SimpleDateFormat("yyyy-MM-dd").parse(serviceDate));
		} catch (Exception e) {
			billOnItem.setService_date(new Date());
		}
		billOnItem.setRec_id(oldObj.getRec_id());
		billOnItem.setTransc_id(oldObj.getTransc_id());
		billOnItem.setDx1(oldObj.getDx1());
		billOnItem.setDx2(oldObj.getDx2());
		billOnItem.setTimestamp(new Timestamp(new Date().getTime()));
		BillingOnItemDao billOnItemDao = (BillingOnItemDao)SpringUtils.getBean(BillingOnItemDao.class);
		
		return billOnItemDao.addBillingOnItem(billOnItem);
	}
	
	private boolean addItem(BillingClaimHeader1Data ch1Obj, List lItemObj,
			String updateProviderNo, String sDx, String serviceDate,
			String sName, String sUnit, String sFee, String sStatus) {
		boolean ret = true;
		BillingItemData oldObj = null;
		BillingItemData newObj = null;
		for (int i = 0; i < lItemObj.size(); i++) {
			oldObj = (BillingItemData) lItemObj.get(i);
			if (sName.equals(oldObj.getService_code())) {
				ret = false;
				break;
			}
		}
		if (ret) {
			newObj = new BillingItemData(oldObj);
			newObj.setService_code(sName);
			newObj.setSer_num(getUnit(sUnit));
			newObj.setFee(getFee(sFee, getUnit(sUnit), sName, serviceDate));
			newObj.setService_date(serviceDate);
			newObj.setDx(sDx);
			newObj.setStatus(sStatus);
			JdbcBillingClaimImpl myObj = new JdbcBillingClaimImpl();
			int i = myObj.addOneItemRecord(newObj);
			if (0 == i) {
				return false;
			}
			dbObj.addInsertOneBillItemTrans(ch1Obj, newObj, updateProviderNo);
			lItemObj.add(newObj);
		}

		ret = true;
		return ret;
	}

	// for appt unbill; 0 - id, 1 - status
	public List getBillingNoStatusByAppt(String apptNo) {
		List ret = dbObj.getBillingCH1NoStatusByAppt(apptNo);
		return ret;
	}

	public List getBillingNoStatusByBillNo(String billNo) {
		List ret = dbObj.getBillingCH1NoStatusByBillNo(billNo);
		return ret;
	}

	// for appt unbill;
	public boolean deleteBilling(String id, String status, String providerNo) {
		boolean ret = dbObj.updateBillingStatus(id, status, providerNo);
		if ("D".equals(status)) {
			// change status in billing_on_item table
			BillingOnItemDao billOnItemDao = (BillingOnItemDao)SpringUtils.getBean(BillingOnItemDao.class);
			List<BillingOnItem> billOnItems = billOnItemDao.getBillingItemByCh1Id(Integer.parseInt(id));
			for (BillingOnItem billOnItem : billOnItems) {
				billOnItem.setStatus("D");
				billOnItemDao.addBillingOnItem(billOnItem); // this statement can update billing_on_item table 
			}
		}
		return ret;
	}

	public List getFacilty_num() {
		JdbcBillingPageUtil dbPageObj = new JdbcBillingPageUtil();
		List ret = dbPageObj.getFacilty_num();
		return ret;
	}

	private String sumFee(Vector vecFee) {
		String ret = "";
		BigDecimal fee = new BigDecimal(Double.parseDouble("0.00")).setScale(4,
				BigDecimal.ROUND_HALF_UP);
		for (int i = 0; i < vecFee.size(); i++) {
			String temp = (String) vecFee.get(i);
			if (temp == null || temp.isEmpty()) {
				continue;
			}
			if (temp.indexOf(".") < 0) {
				temp = temp + ".00";
			}
			BigDecimal tFee = new BigDecimal(Double.parseDouble(temp))
					.setScale(4, BigDecimal.ROUND_HALF_UP);
			fee = fee.add(tFee);
		}
		ret = fee.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		return ret;
	}

	private void updateAmount(String newAmount, String claimId, String updateProviderNo, String sDx) {
		String oldTotal = dbObj.getBillingTotal(claimId);
		if (!newAmount.equals(oldTotal)) {
			dbObj.updateBillingTotal(newAmount, claimId);
		}
	}

	private void updatePaid(String newAmount, String claimId) {
		String oldTotal = dbObj.getBillingPaid(claimId);
		if (!newAmount.equals(oldTotal)) {
			dbObj.updateBillingPaid(newAmount, claimId);
		}
	}

	private String getUnit(String unit) {
		String ret = unit;
		if (!ret.matches("\\d+")) {
			ret = "1";
		}
		return ret;
	}

	private String getFee(String fee, String unit, String codeName,
			String billReferenceDate) {
		String ret = fee;
		if (fee.length() == 0 || fee.equals(" ")) {
			JdbcBillingReviewImpl dbObj = new JdbcBillingReviewImpl();
			fee = dbObj.getCodeFee(codeName, billReferenceDate);
			// calculate fee
			BigDecimal bigCodeFee = new BigDecimal(fee);
			BigDecimal bigCodeUnit = new BigDecimal(unit);
			BigDecimal bigFee = bigCodeFee.multiply(bigCodeUnit);
			bigFee = bigFee.setScale(2, BigDecimal.ROUND_HALF_UP);
			// bigFee = bigFee.round(new MathContext(2));
			ret = bigFee.toString();
		}
		return ret;
	}

}
