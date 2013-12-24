package org.oscarehr.billing.CA.ON.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.billing.CA.ON.model.BillingItem;
import org.oscarehr.common.dao.AbstractDao;
import org.springframework.stereotype.Repository;
@Repository
public class BillingItemDao extends AbstractDao<BillingItem>{

	public BillingItemDao() {
        super(BillingItem.class);
    }
	
	public List<BillingItem> getBillingItemByIdDesc(Integer ch1_id){
        
        Query query = entityManager.createQuery("select b from BillingItem b where b.ch1_id = :ch1_id AND b.status!='D' ORDER BY b.id desc");
        query.setParameter("ch1_id",ch1_id);
        
        List<BillingItem> list= query.getResultList();
        return list;
    }

}
