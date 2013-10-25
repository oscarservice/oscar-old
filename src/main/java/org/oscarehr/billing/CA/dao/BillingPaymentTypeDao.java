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


package org.oscarehr.billing.CA.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.billing.CA.model.BillingPaymentType;
import org.oscarehr.common.dao.AbstractDao;
import org.springframework.stereotype.Repository;

@Repository
public class BillingPaymentTypeDao extends AbstractDao<BillingPaymentType>{

	public BillingPaymentTypeDao() {
		super(BillingPaymentType.class);
	}
	
    @SuppressWarnings("unchecked")
	public List<BillingPaymentType> list(){
        Query query = entityManager.createQuery("select bpt from BillingPaymentType bpt");
        List<BillingPaymentType> types = query.getResultList();
        return types;
    }
    
    public BillingPaymentType getPaymentTypeByName(String typeName) {
    	Query query = entityManager.createQuery("select bpt from BillingPaymentType bpt where bpt.paymentType = ?1");
    	query.setParameter(1, typeName);
    	BillingPaymentType payType = null;
    	try {
    		payType = (BillingPaymentType)query.getSingleResult();
    	} catch (Exception e) {
    		return null;
    	}
    	return payType;
    }
	
}
