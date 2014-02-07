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

import org.oscarehr.billing.CA.ON.dao.BillingOnTransactionDao;
import org.oscarehr.billing.CA.ON.model.BillingItem;
import org.oscarehr.billing.CA.ON.model.BillingOnTransaction;
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
	
	private BillingOnTransactionDao billingOnTransactionDao;

	public void setBillingOnTransactionDao(
			BillingOnTransactionDao billingOnTransactionDao) {
		this.billingOnTransactionDao = billingOnTransactionDao;
	}

	public BillingOnItem addBillingOnItem(BillingOnItem billingItem) {
		return (BillingOnItem)getHibernateTemplate().merge(billingItem);
	}

	public List<BillingOnItem> getBillingItemById(Integer id) {
		String queryStr = "FROM BillingOnItem b WHERE b.id = " + id;

		@SuppressWarnings("unchecked")
		List<BillingOnItem> rs = getHibernateTemplate().find(queryStr);

		return rs;
	}

	public List<BillingOnItem> getBillingItemByCh1Id(Integer ch1_id) {
		String queryStr = "FROM BillingOnItem b WHERE b.ch1_id = " + ch1_id
				+ " ORDER BY b.id";

		@SuppressWarnings("unchecked")
		List<BillingOnItem> rs = getHibernateTemplate().find(queryStr);

		return rs;
	}

	public List<BillingOnItem> getShowBillingItemByCh1Id(Integer ch1_id) {
		String queryStr = "FROM BillingOnItem b where b.ch1_id = " + ch1_id
				+ "AND b.status!='D' ORDER BY b.id";
		@SuppressWarnings("unchecked")
		List<BillingOnItem> rs = getHibernateTemplate().find(queryStr);

		return rs;
	}

	public List<BillingOnItem> getBillingItemByCh1IdDesc(Integer ch1_id) {
		String queryStr = "FROM BillingOnItem b where b.ch1_id = " + ch1_id
				+ " AND b.status!='D' ORDER BY b.id desc";
		@SuppressWarnings("unchecked")
		List<BillingOnItem> rs = getHibernateTemplate().find(queryStr);

		// String a=rs.get(0).getId().toString();

		return rs;
	}

	@SuppressWarnings("unchecked")
	public List<BillingItem> getBillingItemByIdDesc(Integer ch1_id) {
		// log.debug("WHAT IS NULL ? "+billingmasterNo+"  status "+status+"   "+entityManager);
		Query query = entityManager
				.createQuery("select b from BillingItem b where b.ch1_id = ? AND b.status!='D' ORDER BY b.id desc");
		query.setParameter(1, ch1_id);

		List<BillingItem> list = query.getResultList();
		return list;
	}

	public List<BillingOnCHeader1> getCh1ByDemographicNo(Integer demographic_no) {
		String queryStr = "FROM BillingOnCHeader1 b WHERE b.demographic_no = "
				+ demographic_no + " ORDER BY b.id";

		@SuppressWarnings("unchecked")
		List<BillingOnCHeader1> rs = getHibernateTemplate().find(queryStr);

		return rs;
	}
}
