package org.oscarehr.billing.CA.ON.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.oscarehr.billing.CA.ON.model.BillingOnItemPayment;
import org.oscarehr.common.dao.AbstractDao;
import org.springframework.stereotype.Repository;

@Repository
public class BillingOnItemPaymentDao extends AbstractDao<BillingOnItemPayment>{
	public BillingOnItemPaymentDao() {
		super(BillingOnItemPayment.class);
	}
	
	public BillingOnItemPayment findByPaymentIdAndItemId(int paymentId, int itemId) {
		Query query = entityManager.createQuery("select boip from BillingOnItemPayment boip where boip.billingOnPaymentId = ?1 amd boip.billingOnItemId = ?2");
		query.setParameter(1, paymentId);
		query.setParameter(2, itemId);
		return getSingleResultOrNull(query);
	}
	
	@SuppressWarnings("unchecked")
	public List<BillingOnItemPayment> getAllByItemId(int itemId) {
		Query query = entityManager.createQuery("select boip from BillingOnItemPayment boip where boip.billingOnItemId =?1");
		query.setParameter(1, itemId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<BillingOnItemPayment> getItemsByPaymentId(int paymentId) {
		Query query = entityManager.createQuery("select boip from BillingOnItemPayment boip where boip.billingOnPaymentId = ?1");
		query.setParameter(1, paymentId);
		return query.getResultList();
	}
	
	public BigDecimal getAmountPaidByItemId(int itemId) {
		Query query = entityManager.createQuery("select sum(boip.paid) from BillingOnItemPayment boip where boip.billingOnItemId = ?1");
		query.setParameter(1, itemId);
		BigDecimal paid = null;
		try {
			paid = (BigDecimal) query.getSingleResult();
		} catch (Exception e) {}
		
		if (paid == null) {
			paid = new BigDecimal("0.00");
		}
		
		return paid;
	}
}
