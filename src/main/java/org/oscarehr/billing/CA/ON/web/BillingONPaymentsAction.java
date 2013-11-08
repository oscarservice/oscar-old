/**
 *
 * Copyright (c) 2005-2012. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved.
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
 * This software was written for
 * Centre for Research on Inner City Health, St. Michael's Hospital,
 * Toronto, Ontario, Canada
 */

package org.oscarehr.billing.CA.ON.web;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.billing.CA.ON.dao.BillingClaimDAO;
import org.oscarehr.billing.CA.ON.dao.BillingONExtDao;
import org.oscarehr.billing.CA.ON.dao.BillingONPaymentDao;
import org.oscarehr.billing.CA.ON.model.BillingClaimHeader1;
import org.oscarehr.billing.CA.ON.model.BillingONPayment;
import org.oscarehr.billing.CA.dao.BillingPaymentTypeDao;
import org.oscarehr.billing.CA.model.BillingPaymentType;
import org.oscarehr.common.dao.BillingONCHeader1Dao;

import oscar.oscarBilling.ca.on.dao.BillingOnItemDao;
import oscar.oscarBilling.ca.on.model.BillingOnItem;

/**
 * 
 * @author rjonasz
 */
public class BillingONPaymentsAction extends DispatchAction {
	private static Logger logger = Logger
			.getLogger(BillingONPaymentsAction.class);

	private BillingOnItemDao billingOnItemDao;
	private BillingONPaymentDao billingONPaymentDao;
	private BillingPaymentTypeDao billingPaymentTypeDao;
	private BillingClaimDAO billingClaimDAO;
	private BillingONExtDao billingONExtDao;
	private BillingONCHeader1Dao billingONCHeader1Dao;

	// private SiteDao siteDao;
	// private ProviderDao providerDao;

	public ActionForward listPayments(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Integer billingNo = Integer.parseInt(request.getParameter("billingNo"));
		List<BillingONPayment> payments = billingONPaymentDao
				.listPaymentsByBillingNo(billingNo);
		request.setAttribute("paymentsList", payments);
		List<BillingOnItem> items = billingOnItemDao
				.getShowBillingItemByCh1Id(billingNo);
		request.setAttribute("billingOnItems", items);
		List<BillingPaymentType> paymentTypes = billingPaymentTypeDao.list();
		request.setAttribute("billingPaymentTypeList", paymentTypes);

		if (payments != null && payments.size() > 0) {
			BillingClaimHeader1 bill = payments.get(0).getBillingONCheader1();
			request.setAttribute("bill", bill);
			/*
			 * String providerNo = bill.getProvider_no(); List<Site> billSites =
			 * siteDao.getActiveSitesByProviderNo(providerNo);
			 * request.setAttribute("billSites", billSites);
			 * 
			 * List<Provider> billTeam =
			 * providerDao.getActiveTeamProviders(providerNo);
			 * request.setAttribute("billTeam", billSites);
			 */
			String total = billingONPaymentDao
					.getTotalSumByBillingNoWeb(request
							.getParameter("billingNo"));
			String refund = billingONPaymentDao
					.getPaymentsRefundByBillingNoWeb(request
							.getParameter("billingNo"));
			request.setAttribute("total", total);
			request.setAttribute("refund", refund);

		}

		return actionMapping.findForward("success");

	}

	public ActionForward savePayment(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		BigDecimal paymentValue = null;
		Date paymentDate = null;
		Integer paymentType = null;
		Integer billingNo = null;
		Date curDate = new Date();
		Vector<String> payments= new Vector<String>();
		Vector<String> discounts = new Vector<String>();
		Vector<String> refunds = new Vector<String>();
		int size = Integer.parseInt(request.getParameter("size"));
		int billNo = Integer.parseInt(request.getParameter("billingNo"));
		int paymentid = 1;
		for (int i = 0; i < size; i++) {
			String pay_ref = request.getParameter("pay_ref" + i);
			String discount = request.getParameter("discount" + i);
			String serviceCode = request.getParameter("service_code" + i);
			if(!"".equals(request.getParameter("paymentType"+i))){
			 paymentid = Integer.parseInt(request.getParameter("paymentType"+i));
			}
			if (billingOnItemDao.getBillingItemById(Integer.parseInt(serviceCode)).size() > 0) {
				BillingOnItem item = billingOnItemDao.getBillingItemById(Integer.parseInt(serviceCode)).get(0);
				if ("payment".equals(request.getParameter("sel" + i))) {
					payments.add(pay_ref);
					discounts.add(discount);
					billingOnItemDao.updateItemPayment(item, pay_ref, discount);
				} else if ("refund".equals(request.getParameter("sel" + i))) {
					discounts.add(item.getDiscount().toString());
					payments.add(item.getPaid().toString());
					refunds.add(pay_ref);
					billingOnItemDao.updateItemRefund(item, pay_ref);
				}
				
				billingOnItemDao.updatePaymentId(item,paymentid);
			}

		}	
			BigDecimal sumPaid = new BigDecimal("0.00");
			BigDecimal sumRefund = new BigDecimal("0.00");
			BigDecimal sumDiscount = new BigDecimal("0.00");
			for(int i=0;i<payments.size();i++){
				sumPaid = sumPaid.add(new BigDecimal(payments.get(i)));

			}
			for(int i=0;i<refunds.size();i++){
				sumRefund = sumRefund.add(new BigDecimal(refunds.get(i)));

			}
			for(int i=0;i<discounts.size();i++){
				sumDiscount = sumDiscount.add(new BigDecimal(discounts.get(i)));

			}
			billingONCHeader1Dao.updatePaid(billNo,sumPaid);
				
		try {
			// String paymentString = request.getParameter("payment");
			// if(paymentString.substring(0,1).equals("$")) paymentString =
			// paymentString.substring(1,paymentString.length());
//			paymentValue = BigDecimal.valueOf(Double.parseDouble(request
//					.getParameter("payment")));
			paymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(request
					.getParameter("paymentDate"));
			//paymentType = Integer.parseInt(request.getParameter("paymentType"));
			billingNo = Integer.parseInt(request.getParameter("billingNo"));
		} catch (Exception ex) {
			logger.error(
					"Wrong parameters: " + request.getParameter("payment")
							+ "," + request.getParameter("paymentDate") + ","
							+ request.getParameter("paymentType"), ex);

			return actionMapping.findForward("failure");
		}
		String paymentIdParam = request.getParameter("id");
		BillingClaimHeader1 ch1 = billingClaimDAO.find(billingNo);
	//	BillingPaymentType type = billingPaymentTypeDao.find(paymentType);
		BillingONPayment payment = null;
		if (paymentIdParam == null || paymentIdParam.equals("")) {
			// insert
			payment = new BillingONPayment();
			//payment.setTotal_payment(paymentValue);
			payment.setTotal_payment(sumPaid);
			payment.setTotal_refund(sumRefund);
			payment.setTotal_discount(sumDiscount);
			payment.setPaymentDate(paymentDate);
			payment.setBillingOnCheader1(ch1);
			// payment.setPaymentId(billingNo);
			//payment.setBillingPaymentType(type);
			// payment.setPaymentTypeId(paymentType);
			billingONPaymentDao.persist(payment);

		} else {
			// update
			Integer paymentId = Integer.parseInt(paymentIdParam);
			payment = billingONPaymentDao.find(paymentId);
			//payment.setTotal_payment(paymentValue);
			payment.setTotal_payment(sumPaid);
			payment.setTotal_refund(sumRefund);
			payment.setTotal_discount(sumDiscount);
			payment.setPaymentDate(paymentDate);
			// payment.setBillingOnCheader1(ch1);
			// payment.setPaymentId(billingNo);
			//payment.setBillingPaymentType(type);
			// payment.setPaymentTypeId(paymentType);
			billingONPaymentDao.merge(payment);
		}

		BigDecimal paid = billingONPaymentDao
				.getPaymentsSumByBillingNo(billingNo);
		BigDecimal refund = billingONPaymentDao.getPaymentsRefundByBillingNo(
				billingNo).negate();
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		//ch1.setPaid(currency.format(paid.subtract(refund)).replace("$", "")
			//	.replace(",", ""));
		//billingClaimDAO.merge(ch1);

		billingONExtDao.setExtItem(billingNo, ch1.getDemographic_no(),
				BillingONExtDao.KEY_PAYMENT,
				currency.format(paid).replace("$", "").replace(",", ""),
				curDate, '1');
		billingONExtDao.setExtItem(billingNo, ch1.getDemographic_no(),
				BillingONExtDao.KEY_REFUND,
				currency.format(refund).replace("$", "").replace(",", ""),
				curDate, '1');
		billingONExtDao.setExtItem(billingNo, ch1.getDemographic_no(),
				BillingONExtDao.KEY_PAY_DATE, new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(paymentDate), curDate,
				'1');
	//	billingONExtDao.setExtItem(billingNo, ch1.getDemographic_no(),
		//		BillingONExtDao.KEY_PAY_METHOD, payment.getBillingPaymentType()
			//			.getPaymentType(), curDate, '1');

		return listPayments(actionMapping, actionForm, request, response);

	}

	public ActionForward deletePayment(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		Date curDate = new Date();
		try {
			Integer paymentId = Integer.parseInt(request.getParameter("id"));
			BillingONPayment payment = billingONPaymentDao.find(paymentId);
			BillingClaimHeader1 ch1 = payment.getBillingONCheader1();
			Integer billingNo = payment.getBillingONCheader1().getId();

			billingONPaymentDao.remove(paymentId);

			BigDecimal paid = billingONPaymentDao
					.getPaymentsSumByBillingNo(billingNo);
			BigDecimal refund = billingONPaymentDao
					.getPaymentsRefundByBillingNo(billingNo).negate();
			NumberFormat currency = NumberFormat.getCurrencyInstance();
			ch1.setPaid(currency.format(paid.subtract(refund)).replace("$", ""));
			billingClaimDAO.merge(ch1);

			billingONExtDao.setExtItem(billingNo, ch1.getDemographic_no(),
					BillingONExtDao.KEY_PAYMENT,
					currency.format(paid).replace("$", ""), curDate, '1');
			billingONExtDao.setExtItem(billingNo, ch1.getDemographic_no(),
					BillingONExtDao.KEY_REFUND, currency.format(refund)
							.replace("$", ""), curDate, '1');

		} catch (Exception ex) {
			logger.error(
					"Failed to delete payment: " + request.getParameter("id"),
					ex);
			return actionMapping.findForward("failure");
		}

		return listPayments(actionMapping, actionForm, request, response);

	}

	public void setBillingONPaymentDao(BillingONPaymentDao paymentDao) {
		this.billingONPaymentDao = paymentDao;
	}

	public void setBillingPaymentTypeDao(BillingPaymentTypeDao paymentTypeDao) {
		this.billingPaymentTypeDao = paymentTypeDao;
	}

	public void setBillingClaimDAO(BillingClaimDAO billingDao) {
		this.billingClaimDAO = billingDao;
	}

	public void setBillingONExtDao(BillingONExtDao billingExtDao) {
		this.billingONExtDao = billingExtDao;
	}

	public void setBillingOnItemDao(BillingOnItemDao billingOnItemDao) {
		this.billingOnItemDao = billingOnItemDao;
	}

	public void setBillingONCHeader1Dao(BillingONCHeader1Dao billingONCHeader1Dao) {
		this.billingONCHeader1Dao = billingONCHeader1Dao;
	}
	
	
	/*
	 * public void setSiteDao(SiteDao siteDao) { this.siteDao = siteDao; }
	 * 
	 * public void setProviderDao(ProviderDao providerDao) { this.providerDao =
	 * providerDao; }
	 */
}
