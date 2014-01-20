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
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
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
import org.oscarehr.billing.CA.ON.dao.BillingOnItemPaymentDao;
import org.oscarehr.billing.CA.ON.dao.BillingOnTransactionDao;
import org.oscarehr.billing.CA.ON.model.BillingClaimHeader1;
import org.oscarehr.billing.CA.ON.model.BillingONExt;
import org.oscarehr.billing.CA.ON.model.BillingONPayment;
import org.oscarehr.billing.CA.ON.model.BillingOnItemPayment;
import org.oscarehr.billing.CA.ON.model.BillingOnTransaction;
import org.oscarehr.billing.CA.ON.vo.BillingItemPaymentVo;
import org.oscarehr.billing.CA.dao.BillingPaymentTypeDao;
import org.oscarehr.billing.CA.model.BillingPaymentType;
import org.oscarehr.common.dao.BillingONCHeader1Dao;
import org.oscarehr.common.model.BillingONCHeader1;
import org.oscarehr.util.MiscUtils;
import org.oscarehr.util.SpringUtils;

import oscar.oscarBilling.ca.on.dao.BillingOnItemDao;
import oscar.oscarBilling.ca.on.data.BillingClaimHeader1Data;
import oscar.oscarBilling.ca.on.data.BillingDataHlp;
import oscar.oscarBilling.ca.on.data.JdbcBilling3rdPartImpl;
import oscar.oscarBilling.ca.on.data.JdbcBillingCorrection;
import oscar.oscarBilling.ca.on.model.BillingOnCHeader1;
import oscar.oscarBilling.ca.on.model.BillingOnItem;
import oscar.oscarBilling.ca.on.model.BillingOnPaymentItem;
import oscar.oscarBilling.ca.on.pageUtil.BillingCorrectionPrep;

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
	private BillingOnItemPaymentDao billingOnItemPaymentDao;
	private BillingOnTransactionDao billingOnTransactionDao;

	public ActionForward listPayments(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Integer billingNo = Integer.parseInt(request.getParameter("billingNo"));
		List<BillingOnItem> items = billingOnItemDao.getShowBillingItemByCh1Id(billingNo);
		List<BillingItemPaymentVo> itemPaymentList = new ArrayList<BillingItemPaymentVo>();
		for (BillingOnItem item : items) {
			List<BillingOnItemPayment> paymentList = billingOnItemPaymentDao.getAllByItemId(item.getId());
			BigDecimal payment = BigDecimal.ZERO;
			BigDecimal discount = BigDecimal.ZERO;
			BigDecimal refund = BigDecimal.ZERO;
			for (BillingOnItemPayment payIter : paymentList) {
				payment = payment.add(payIter.getPaid());
				discount = discount.add(payIter.getDiscount());
				refund = refund.add(payIter.getRefund());
			}
			
			BillingItemPaymentVo itemPayment = new BillingItemPaymentVo();
			itemPayment.setItemId(item.getId());
			itemPayment.setServiceCode(item.getService_code());
			itemPayment.setPaid(payment);
			itemPayment.setRefund(refund);
			itemPayment.setTotal(new BigDecimal(item.getFee()));
			itemPayment.setDiscount(discount);
			itemPaymentList.add(itemPayment);
		}
		
		request.setAttribute("itemPaymentList", itemPaymentList);
		List<BillingPaymentType> paymentTypes = billingPaymentTypeDao.list();
		request.setAttribute("paymentTypeList", paymentTypes);
		
		BillingClaimHeader1 cheader1 = billingClaimDAO.find(billingNo);
		Integer demographicNo = cheader1.getDemographic_no();
		BigDecimal payment = BigDecimal.ZERO;
		BigDecimal balance = BigDecimal.ZERO;
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal refund = BigDecimal.ZERO;
		BigDecimal discount = BigDecimal.ZERO;
		
		BillingONExt paymentItem = billingONExtDao.getClaimExtItem(billingNo, demographicNo, BillingONExtDao.KEY_PAYMENT);
		if (paymentItem != null) {
			payment = new BigDecimal(paymentItem.getValue());
		}
		BillingONExt discountItem = billingONExtDao.getClaimExtItem(billingNo, demographicNo, BillingONExtDao.KEY_DISCOUNT);
		if (discountItem != null) {
			discount = new BigDecimal(discountItem.getValue());
		}
		BillingONExt refundItem = billingONExtDao.getClaimExtItem(billingNo, demographicNo, BillingONExtDao.KEY_REFUND);
		if (refundItem != null) {
			refund = new BigDecimal(refundItem.getValue());
		}
		BillingONExt totalItem = billingONExtDao.getClaimExtItem(billingNo, demographicNo, BillingONExtDao.KEY_TOTAL);
		if (totalItem != null) {
			total = new BigDecimal(totalItem.getValue());
		}
		balance = total.subtract(payment).subtract(discount).subtract(refund);
		
		request.setAttribute("totalInvoiced", total);
		request.setAttribute("balance", balance);
	
		return actionMapping.findForward("success");
	}
	
	public ActionForward savePayment(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		Date curDate = new Date();
		Vector<String> payments = new Vector<String>();
		Vector<String> discounts = new Vector<String>();
		Vector<String> refunds = new Vector<String>();
		int itemSize = Integer.parseInt(request.getParameter("size"));
		int billNo = Integer.parseInt(request.getParameter("billingNo"));
		String curProviderNo = (String) request.getSession().getAttribute("user");
		String paymentTypeId = request.getParameter("paymentType");
		if (paymentTypeId == null || !paymentTypeId.isEmpty()) {
			paymentTypeId = "0";
		}

		// get all paid, discount and refund list
		for (int i = 0; i < itemSize; i++) {
			String payment = request.getParameter("payment" + i);
			String discount = request.getParameter("discount" + i);
			String itemId = request.getParameter("itemId" + i);
			if (billingOnItemDao.getBillingItemById(Integer.parseInt(itemId)).size() > 0) {
				if ("payment".equals(request.getParameter("sel" + i))) {
					payments.add(payment);
					discounts.add(discount);
				} else if ("refund".equals(request.getParameter("sel" + i))) {
					refunds.add(payment);
				}
			}
		}

		// count sum of paid,refund,discount
		BillingClaimHeader1 cheader1 = billingClaimDAO.find(billNo);
		if (cheader1 == null) {
			return actionMapping.findForward("failure");
		}
		String demographicNo = cheader1.getDemographic_no().toString();
		BigDecimal sumPaid = new BigDecimal(cheader1.getPaid());
		BigDecimal sumRefund = BigDecimal.ZERO;
		BigDecimal sumDiscount = BigDecimal.ZERO;
		for (int i = 0; i < payments.size(); i++) {
			sumPaid = sumPaid.add(new BigDecimal(payments.get(i)));
		}
		for (int i = 0; i < refunds.size(); i++) {
			sumRefund = sumRefund.add(new BigDecimal(refunds.get(i)));
		}
		for (int i = 0; i < discounts.size(); i++) {
			sumDiscount = sumDiscount.add(new BigDecimal(discounts.get(i)));
		}

		// 1.update billing_on_cheader1 and billing_on_ext table: payment
		JdbcBilling3rdPartImpl tExtObj = new JdbcBilling3rdPartImpl();
		if (sumPaid.compareTo(BigDecimal.ZERO) == 1) {
			cheader1.setPaid(sumPaid.toString());
			billingClaimDAO.merge(cheader1);
			if (tExtObj.keyExists(Integer.toString(billNo), BillingONExtDao.KEY_PAYMENT)) {
				tExtObj.updateKeyValue(Integer.toString(billNo), BillingONExtDao.KEY_PAYMENT, sumPaid.toString());
			} else {
				tExtObj.add3rdBillExt(Integer.toString(billNo), demographicNo, BillingONExtDao.KEY_PAYMENT, sumPaid.toString());
			}
		}
		
		// 2.update billing_on_ext table: discount
		if (sumDiscount.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal extDiscount = BigDecimal.ZERO;
			try {
				extDiscount = new BigDecimal(billingONExtDao.getClaimExtDiscount(billNo));
			} catch (Exception e) {
				MiscUtils.getLogger().info(e.toString());
			}
			sumDiscount = sumDiscount.add(extDiscount);
			if (tExtObj.keyExists(Integer.toString(billNo), BillingONExtDao.KEY_DISCOUNT)) {
				tExtObj.updateKeyValue(Integer.toString(billNo), BillingONExtDao.KEY_DISCOUNT, sumDiscount.toString());
			} else {
				tExtObj.add3rdBillExt(Integer.toString(billNo), demographicNo, BillingONExtDao.KEY_DISCOUNT, sumDiscount.toString());
			}
		}
		
		// 3.update billing_on_ext table: refund
		if (sumRefund.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal extRefund = BigDecimal.ZERO;
			try {
				extRefund = new BigDecimal(billingONExtDao.getClaimExtRefund(billNo));
			} catch (Exception e) {
				MiscUtils.getLogger().info(e.toString());
			}
			sumRefund = sumRefund.add(extRefund);
			if (tExtObj.keyExists(Integer.toString(billNo), BillingONExtDao.KEY_REFUND)) {
				tExtObj.updateKeyValue(Integer.toString(billNo), BillingONExtDao.KEY_REFUND, sumRefund.toString());
			} else {
				tExtObj.add3rdBillExt(Integer.toString(billNo), demographicNo, BillingONExtDao.KEY_REFUND, sumRefund.toString());
			}
		}

		// 4.update billing_on_payment
		BillingONPayment billPayment = new BillingONPayment();
		billPayment.setBillingOnCheader1(cheader1);
		billPayment.setCreator(curProviderNo);
		billPayment.setPaymentDate(curDate);
		billPayment.setPaymentTypeId(Integer.parseInt(paymentTypeId));
		billPayment.setTotal_payment(sumPaid);
		billPayment.setTotal_discount(sumDiscount);
		billPayment.setTotal_refund(sumRefund);
		billingONPaymentDao.persist(billPayment);
		
		// 5.update biling_on_item_payment
		for (int i = 0; i < itemSize; i++) {
			String payment = request.getParameter("payment" + i);
			String discount = request.getParameter("discount" + i);
			String itemId = request.getParameter("itemId" + i);
			BillingOnItem billItem = null;
			try {
				List<BillingOnItem> itemList = billingOnItemDao.getBillingItemById(Integer.parseInt(itemId));
				if (itemList == null || itemList.size() == 0) {
					continue;
				}
				billItem = itemList.get(0);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			BillingOnItemPayment billItemPayment = new BillingOnItemPayment();
			billItemPayment.setBillingOnItemId(Integer.parseInt(itemId));
			billItemPayment.setBillingOnPaymentId(billPayment.getId());
			billItemPayment.setCh1Id(billNo);
			billItemPayment.setPaymentTimestamp(new Timestamp(curDate.getTime()));
			
			if ("payment".equals(request.getParameter("sel" + i))) {
				BigDecimal itemPayment = BigDecimal.ZERO;
				BigDecimal itemDiscnt = BigDecimal.ZERO;
				try {
					itemPayment = new BigDecimal(payment);
				} catch (Exception e) {}
				try {
					itemDiscnt = new BigDecimal(discount);
				} catch (Exception e) {}
				
				if (itemPayment.compareTo(BigDecimal.ZERO) == 0 || itemDiscnt.compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				billItemPayment.setPaid(itemPayment);
				billItemPayment.setDiscount(itemDiscnt);
				BillingOnTransaction billTrans = billingOnTransactionDao.getTransTemplate(cheader1, billItem, billPayment, curProviderNo);
				billTrans.setServiceCodePaid(itemPayment);
				billTrans.setServiceCodeDiscount(itemDiscnt);
				billingOnTransactionDao.persist(billTrans);
			} else if ("refund".equals(request.getParameter("sel" + i))) {
				BigDecimal itemRefund = BigDecimal.ZERO;
				try {
					itemRefund = new BigDecimal(payment);
				} catch (Exception e) {}
				if (itemRefund.compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				billItemPayment.setRefund(itemRefund);
				BillingOnTransaction billTrans = billingOnTransactionDao.getTransTemplate(cheader1, billItem, billPayment, curProviderNo);
				billTrans.setServiceCodeRefund(itemRefund);
				billingOnTransactionDao.persist(billTrans);
			}
			billingOnItemPaymentDao.persist(billItemPayment);
		}

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
	
	public void setBillingOnItemPaymentDao(BillingOnItemPaymentDao billingOnItemPaymentDao) {
		this.billingOnItemPaymentDao = billingOnItemPaymentDao;
	}
}
