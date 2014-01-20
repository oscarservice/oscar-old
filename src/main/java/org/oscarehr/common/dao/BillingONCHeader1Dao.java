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

package org.oscarehr.common.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.oscarehr.common.model.BillingONCHeader1;
import org.springframework.stereotype.Repository;

import oscar.oscarBilling.ca.on.data.BillingHistoryDate;
import oscar.oscarBilling.ca.on.data.BillingONDataHelp;
import oscar.oscarBilling.ca.on.data.JdbcBillingCorrection;

/**
*
* @author Eugene Katyukhin
*/

@Repository
public class BillingONCHeader1Dao extends AbstractDao<BillingONCHeader1>{
	private static final Logger _logger = Logger.getLogger(JdbcBillingCorrection.class);

	
	BillingONDataHelp dbObj = new BillingONDataHelp();

    public BillingONCHeader1Dao() {
        super(BillingONCHeader1.class);
    }

    public int getNumberOfDemographicsWithInvoicesForProvider(String providerNo,Date startDate,Date endDate,boolean distinct ){
        String distinctStr = "distinct";
        if (distinct == false){
                distinctStr = StringUtils.EMPTY;
        }

        Query query = entityManager.createNativeQuery("select count("+distinctStr+" demographic_no) from billing_on_cheader1 ch where ch.provider_no = ? and billing_date >= ? and billing_date <= ?");
        query.setParameter(1, providerNo);
                query.setParameter(2,startDate);
                query.setParameter(3,endDate);
                BigInteger bint =  (BigInteger) query.getSingleResult();
                return bint.intValue();
    }
    
    public List<BillingONCHeader1> getBillCheader1ByDemographicNo(int demographic_no){
    	Query query = entityManager.createQuery("select ch from BillingONCHeader1 ch where ch.demographicNo=?");
    	query.setParameter(1, demographic_no);
    	return query.getResultList();
    }
    
    public List<BillingONCHeader1> getBillCheader1ByDemographicNoNew(int demographic_no){
    	Query query = entityManager.createQuery("select ch from BillingONCHeader1 ch where ch.demographicNo=? AND ch.status!='D'");
    	query.setParameter(1, demographic_no);
    	return query.getResultList();
    }
    
    @SuppressWarnings("rawtypes")
	public List<BillingHistoryDate> getHistoryByDemographicNo(int demographic_no){
    	List obj = new Vector();
    	String sql ="SELECT * FROM billing_on_cheader1 ch LEFT JOIN billing_on_payment bp ON ch.id=bp.ch1_id  where ch.demographicNo=" + demographic_no + " AND ch.status!='D'";
    	//query.setParameter(1, demographic_no);
    	BillingHistoryDate historyObj = null;
		ResultSet rs = dbObj.searchDBRecord(sql);
		try {
			while (rs.next()) {
				historyObj = new BillingHistoryDate();
				
				historyObj.setId("" + rs.getInt("id"));
				historyObj.setPay_program(rs.getString("pay_program"));
				historyObj.setTotal_discount(rs.getString("total_discount"));
				historyObj.setTotal_payment(rs.getString("total_payment"));
				historyObj.setTotal_refund(rs.getString("total_refund"));		
				historyObj.setTotal(rs.getString("total"));				
				historyObj.setTimestamp1(rs.getString("timestamp1"));		
								
				obj.add(historyObj);
			}
		} catch (SQLException e) {
			_logger.error("getBillingRecordObj(sql = " + sql + ")");
			obj = null;
		}
    	return obj;
    }

	public void updatePaid(int billNo, BigDecimal sumPaid) {
		Query query = entityManager.createQuery("update BillingONCHeader1 ch set ch.paid=? where ch.id=?");
		query.setParameter(1, sumPaid);
		query.setParameter(2, billNo);
		query.executeUpdate();
	}

}
