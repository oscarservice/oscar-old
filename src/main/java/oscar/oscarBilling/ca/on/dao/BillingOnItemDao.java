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


package oscar.oscarBilling.ca.on.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.oscarehr.billing.CA.ON.model.BillingItem;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import oscar.oscarBilling.ca.on.data.BillingClaimHeader1Data;
import oscar.oscarBilling.ca.on.data.BillingDataHlp;
import oscar.oscarBilling.ca.on.data.BillingONDataHelp;
import oscar.oscarBilling.ca.on.model.BillingOnCHeader1;
import oscar.oscarBilling.ca.on.model.BillingOnItem;
import oscar.oscarBilling.ca.on.model.BillingOnPaymentItem;
@Repository
public class BillingOnItemDao extends HibernateDaoSupport {
	
	BillingONDataHelp dbObj = new BillingONDataHelp();
	@PersistenceContext
	protected EntityManager entityManager = null;

	public void addBillingOnItem(BillingOnItem billingItem) {
		getHibernateTemplate().merge(billingItem);
	}

        public List<BillingOnItem> getBillingItemById(Integer id) {
            String queryStr = "FROM BillingOnItem b WHERE b.id = "+id;

            @SuppressWarnings("unchecked")
            List<BillingOnItem> rs = getHibernateTemplate().find(queryStr);

            return rs;
        }
        
        public List<BillingOnPaymentItem> getBillingItemByIdNew(Integer id) {
            String queryStr = "FROM BillingOnPaymentItem b WHERE b.billing_on_item_id = "+id;

            @SuppressWarnings("unchecked")
            List<BillingOnPaymentItem> rs = getHibernateTemplate().find(queryStr);

            return rs;
        }

        public List<BillingOnItem> getBillingItemByCh1Id(Integer ch1_id) {
            String queryStr = "FROM BillingOnItem b WHERE b.ch1_id = "+ch1_id+" ORDER BY b.id";

            @SuppressWarnings("unchecked")
            List<BillingOnItem> rs = getHibernateTemplate().find(queryStr);

            return rs;
        }
        
        public List<BillingOnItem> getShowBillingItemByCh1Id(Integer ch1_id){
        	String queryStr = "FROM BillingOnItem b where b.ch1_id = "+ch1_id+"AND b.status!='D' ORDER BY b.id";
        	@SuppressWarnings("unchecked")
			List<BillingOnItem> rs = getHibernateTemplate().find(queryStr);
        	
        	return rs;
        }
        
        public List<BillingOnItem> getBillingItemByCh1IdDesc(Integer ch1_id){
        	String queryStr = "FROM BillingOnItem b where b.ch1_id = "+ch1_id+" AND b.status!='D' ORDER BY b.id desc";
        	@SuppressWarnings("unchecked")
			List<BillingOnItem> rs = getHibernateTemplate().find(queryStr);
        	
        	// String a=rs.get(0).getId().toString();
        	
        	return rs;
        }
        
        @SuppressWarnings("unchecked")
		public List<BillingItem> getBillingItemByIdDesc(Integer ch1_id){
            //log.debug("WHAT IS NULL ? "+billingmasterNo+"  status "+status+"   "+entityManager);
            Query query = entityManager.createQuery("select b from BillingItem b where b.ch1_id = ? AND b.status!='D' ORDER BY b.id desc");
            query.setParameter(1,ch1_id);
            
            List<BillingItem> list= query.getResultList();
            return list;
        }
        
        public List<BillingOnCHeader1> getCh1ByDemographicNo(Integer demographic_no) {
            String queryStr = "FROM BillingOnCHeader1 b WHERE b.demographic_no = "+demographic_no+" ORDER BY b.id";

            @SuppressWarnings("unchecked")
            List<BillingOnCHeader1> rs = getHibernateTemplate().find(queryStr);

            return rs;
        }
        
//        public List<BillingONPayment> getIdByBillingNo(Integer BillingNo) {
//            String queryStr = "FROM BillingONPayment b WHERE b.ch1_id = "+BillingNo+" ORDER BY b.id desc";
//
//            @SuppressWarnings("unchecked")
//            List<BillingONPayment> rs = getHibernateTemplate().find(queryStr);
//
//            return rs;
//        }
        
        public void updateItemRefund(BillingOnItem item,String refund){
        	//item.setRefund(new BigDecimal(refund));
        	getHibernateTemplate().update(item);
        }
        
        public void updateItemRefundNew(BillingOnPaymentItem item,String refund){
        	item.setRefund(new BigDecimal(refund));
        	//item.setBilling_on_payment_id(paymentId);
        	getHibernateTemplate().update(item);
        }
        
        public void updateItemPayment(BillingOnItem item,String paid,String discount){
//        	item.setPaid(new BigDecimal(paid));
//        	item.setDiscount(new BigDecimal(discount));
        	getHibernateTemplate().update(item);
        }
        
        public void updateItemPaymentNew(BillingOnPaymentItem item,String paid,String discount){
        	item.setPaid(new BigDecimal(paid));
        	item.setDiscount(new BigDecimal(discount));
        	//item.setBilling_on_payment_id(paymentId);
        	getHibernateTemplate().update(item);
        }

		public void updatePaymentId(BillingOnItem item, int paymentid) {
			//item.setPayment_typeID(paymentid);
			getHibernateTemplate().update(item);
		}
		
		public void addUpdateOneBillItemTrans(BillingClaimHeader1Data billHeader, BillingOnItem item, String updateProviderNo,String pay_ref, String discount,BillingOnPaymentItem item1) {
			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append("insert into billing_on_transaction values");
			sqlBuf.append("(\\N,");
			sqlBuf.append(item.getCh1_id() + ","); // cheader1_id
			sqlBuf.append("'"+0+"',"); // paymentId
			sqlBuf.append(item1.getId() + ","); // billing_on_item_id
			sqlBuf.append(billHeader.getDemographic_no() + ","); // demographic_no
			sqlBuf.append("'" + updateProviderNo + "',"); // update_provider_no
			sqlBuf.append("CURRENT_TIMESTAMP,"); // update_datetime
			sqlBuf.append("null,"); // payment_date
			sqlBuf.append("'" + billHeader.getRef_num() + "',"); // ref_num
			sqlBuf.append("'" + billHeader.getProvince() + "',"); // province
			sqlBuf.append("'" + billHeader.getMan_review() + "',"); // man_review
			sqlBuf.append("'" + item.getService_date() + "',"); // billing_date
			sqlBuf.append("'" + item.getStatus() + "',"); // status
			sqlBuf.append("'" + billHeader.getPay_program() + "',"); // pay_program
			//sqlBuf.append("'" + billHeader.getPayee() + "',"); // paymentType
			sqlBuf.append("'" + billHeader.getFacilty_num() + "',"); // facility_num
			sqlBuf.append("'" + billHeader.getClinic() + "',"); // clinic
			sqlBuf.append("'" + billHeader.getProviderNo() + "',"); // provider_no
			sqlBuf.append("'" + billHeader.getCreator() + "',"); // creator
			sqlBuf.append("'" + billHeader.getVisittype() + "',"); // visittype
			sqlBuf.append("'" + billHeader.getAdmission_date() + "',"); // admission_date
			sqlBuf.append("'" + billHeader.getLocation() + "',"); // sli_code
			sqlBuf.append("'" + item.getService_code() + "',"); // service_code
			sqlBuf.append("'" + item.getSer_num() + "',"); // service_code_num
			sqlBuf.append("'" + item.getFee() + "',"); // service_code_invoiced
			sqlBuf.append("'" + pay_ref + "',"); // service_code_paid
			sqlBuf.append("'',"); // service_code_refund
			sqlBuf.append("'" + discount + "',"); // service_code_discount
			sqlBuf.append("'" + item.getDx() + "',"); // dx_code
			sqlBuf.append("'" + billHeader.getComment() + "',"); // billing_notes
			sqlBuf.append("'" + BillingDataHlp.ACTION_TYPE.U.name() + "',"); // action_type
			sqlBuf.append("'" + 1 + "'");//paymenttypeId
			sqlBuf.append(")");
			dbObj.saveBillingRecord(sqlBuf.toString());
		}
		
		public void addUpdateOneBillItemTransForRefund(BillingClaimHeader1Data billHeader, BillingOnItem item, String updateProviderNo,String pay_ref, String discount,String refund,BillingOnPaymentItem item1) {
			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append("insert into billing_on_transaction values");
			sqlBuf.append("(\\N,");
			sqlBuf.append(item.getCh1_id() + ","); // cheader1_id
			sqlBuf.append("'"+0+"',"); // paymentId
			sqlBuf.append(item1.getId() + ","); // billing_on_item_id
			sqlBuf.append(billHeader.getDemographic_no() + ","); // demographic_no
			sqlBuf.append("'" + updateProviderNo + "',"); // update_provider_no
			sqlBuf.append("CURRENT_TIMESTAMP,"); // update_datetime
			sqlBuf.append("null,"); // payment_date
			sqlBuf.append("'" + billHeader.getRef_num() + "',"); // ref_num
			sqlBuf.append("'" + billHeader.getProvince() + "',"); // province
			sqlBuf.append("'" + billHeader.getMan_review() + "',"); // man_review
			sqlBuf.append("'" + item.getService_date() + "',"); // billing_date
			sqlBuf.append("'" + item.getStatus() + "',"); // status
			sqlBuf.append("'" + billHeader.getPay_program() + "',"); // pay_program
			//sqlBuf.append("'" + billHeader.getPayee() + "',"); // paymentType
			sqlBuf.append("'" + billHeader.getFacilty_num() + "',"); // facility_num
			sqlBuf.append("'" + billHeader.getClinic() + "',"); // clinic
			sqlBuf.append("'" + billHeader.getProviderNo() + "',"); // provider_no
			sqlBuf.append("'" + billHeader.getCreator() + "',"); // creator
			sqlBuf.append("'" + billHeader.getVisittype() + "',"); // visittype
			sqlBuf.append("'" + billHeader.getAdmission_date() + "',"); // admission_date
			sqlBuf.append("'" + billHeader.getLocation() + "',"); // sli_code
			sqlBuf.append("'" + item.getService_code() + "',"); // service_code
			sqlBuf.append("'" + item.getSer_num() + "',"); // service_code_num
			sqlBuf.append("'" + item.getFee() + "',"); // service_code_invoiced
			sqlBuf.append("'" + pay_ref + "',"); // service_code_paid
			sqlBuf.append("'" + refund + "',"); // service_code_refund
			sqlBuf.append("'" + discount + "',"); // service_code_discount
			sqlBuf.append("'" + item.getDx() + "',"); // dx_code
			sqlBuf.append("'" + billHeader.getComment() + "',"); // billing_notes
			sqlBuf.append("'" + BillingDataHlp.ACTION_TYPE.U.name() + "',"); // action_type
			sqlBuf.append("'" + 1 + "'");//paymenttypeId
			sqlBuf.append(")");
			dbObj.saveBillingRecord(sqlBuf.toString());
		}
}
