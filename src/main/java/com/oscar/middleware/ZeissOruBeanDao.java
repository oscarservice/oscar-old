package com.oscar.middleware;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.common.dao.AbstractDao;
import org.springframework.stereotype.Repository;



@Repository
public class ZeissOruBeanDao extends AbstractDao<ZeissOruBean> {
	public ZeissOruBeanDao() {
		super(ZeissOruBean.class);
	}

	public List<ZeissOruBean> list(int demoId) {
		Query query = entityManager.createQuery("select zoru from ZeissOruBean zoru where zoru.pid = ?1");
		query.setParameter(1, demoId);
		@SuppressWarnings("unchecked")
		List<ZeissOruBean> types = query.getResultList();
		return types;
	}

}