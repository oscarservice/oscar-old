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

package org.oscarehr.billing.CA.ON.dao;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.oscarehr.billing.CA.ON.model.BillingONPayment;
import org.oscarehr.common.dao.AbstractDao;
import org.springframework.stereotype.Repository;

/**
*
* @author Eugene Katyukhin
*/

@Repository
public class BillingONPaymentDao extends AbstractDao<BillingONPayment>{

    public BillingONPaymentDao() {
        super(BillingONPayment.class);
    }

    public List<BillingONPayment> listPaymentsByBillingNo(Integer billingNo){
        Query query = entityManager.createQuery("select bp from BillingONPayment bp where bp.billingONCheader1.id = :billingNo");
        query.setParameter("billingNo", billingNo);
        List<BillingONPayment> payments = query.getResultList();
        return payments;
    }

    public BigDecimal getPaymentsSumByBillingNo(Integer billingNo){
        Query query = entityManager.createQuery("select sum(bp.total_payment) from BillingONPayment bp where bp.billingONCheader1.id = :billingNo and total_payment>0 group by bp.billingONCheader1");
        query.setParameter("billingNo", billingNo);
        BigDecimal paymentsSum = null;
        try {
        	paymentsSum = (BigDecimal) query.getSingleResult();
        } catch(NoResultException ex) {
        	paymentsSum = new BigDecimal(0);
        }
        return paymentsSum;
    }
    
    public BigDecimal getPaymentsRefundByBillingNo(Integer billingNo){
        Query query = entityManager.createQuery("select sum(bp.total_payment) from BillingONPayment bp where bp.billingONCheader1.id = :billingNo and total_payment<0 group by bp.billingONCheader1");
        query.setParameter("billingNo", billingNo);
        BigDecimal paymentsSum = null;
        try {
        	paymentsSum = (BigDecimal) query.getSingleResult();
        } catch(NoResultException ex) {
        	paymentsSum = new BigDecimal(0);
        }
        return paymentsSum;
    }
    
    public String getTotalSumByBillingNoWeb(String billingNo){
        Query query = entityManager.createQuery("select sum(bp.total_payment) from BillingONPayment bp where bp.billingONCheader1.id = :billingNo group by bp.billingONCheader1");
        query.setParameter("billingNo", Integer.parseInt(billingNo));
        BigDecimal paymentsSum = null;
        try {
        	paymentsSum = (BigDecimal) query.getSingleResult();
        } catch(NoResultException ex) {
        	paymentsSum = new BigDecimal(0);
        }
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        return currency.format(paymentsSum);
    }
    
    public String getPaymentsRefundByBillingNoWeb(String billingNo){
        Query query = entityManager.createQuery("select -sum(bp.total_payment) from BillingONPayment bp where bp.billingONCheader1.id = :billingNo and total_payment<0 group by bp.billingONCheader1");
        query.setParameter("billingNo", Integer.parseInt(billingNo));
        BigDecimal paymentsSum = null;
        try {
        	paymentsSum = (BigDecimal) query.getSingleResult();
        } catch(NoResultException ex) {
        	paymentsSum = new BigDecimal(0);
        }
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(paymentsSum);
    }
    
    public int getPaymentIdByBillingNo(int billingNo){
    	Query query = entityManager.createQuery("select bp.id from BillingONPayment bp where bp.billingONCheader1.id = :billingNo");
    	query.setParameter("billingNo", billingNo);
    	return (Integer) query.getSingleResult();
    }
/*
    private Long convertToLong(BigDecimal param) {
    	BigDecimal res = param.multiply(BigDecimal.valueOf(100));
    	return res.longValue();
    }
*/
}
