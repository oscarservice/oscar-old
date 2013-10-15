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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.oscarehr.common.model.ProviderData;
import org.springframework.stereotype.Repository;

@Repository
public class ProviderDataDao extends AbstractDao<ProviderData> {

	public ProviderDataDao() {
		super(ProviderData.class);
	}
	
	

	@SuppressWarnings("unchecked")
	public ProviderData findByOhipNumber(String ohipNumber) {
		Query query;
		List<ProviderData> results;
		String sqlCommand = "SELECT x FROM ProviderData x WHERE x.ohipNo=?";
		
		query = this.entityManager.createQuery(sqlCommand);
		query.setParameter(1, ohipNumber);
		
		results = query.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		}
		// If we get here, there were no results
		return null;
	}
	
    public ProviderData findByProviderNo(String providerNo) {

    	String sqlCommand = "select x from ProviderData x where x.id=?1";

        Query query = entityManager.createQuery(sqlCommand);
        query.setParameter(1, providerNo);

        @SuppressWarnings("unchecked")
        List<ProviderData> results = query.getResultList();

        if (results.size()>0) return results.get(0);
        return null;
    }

    public List<ProviderData> findAllOrderByLastName() {
    	String sqlCommand = "select x from ProviderData x order by x.lastName";
        Query query = entityManager.createQuery(sqlCommand);

        @SuppressWarnings("unchecked")
        List<ProviderData> results = query.getResultList();
        return results;
    }
    
//replacement for "searchloginteam" in ProviderDao JDBC    
    public List<ProviderData> searchLoginTeam(String providerNo) {    	
    	List<ProviderData> results = new ArrayList<ProviderData>();
    	String sqlCommand="select p from ProviderData p where p.id=?1 and p.status='1'";
        Query query = entityManager.createQuery(sqlCommand);
        query.setParameter(1, providerNo);

    	String teamNo = null;
    	try {
    		ProviderData result = (ProviderData)query.getSingleResult();
    		results.add(result);
    		teamNo = result.getTeam();
    		
    		sqlCommand="select p from ProviderData p where p.team=?1 and p.status='1' order by p.lastName";
            query = entityManager.createQuery(sqlCommand);
            query.setParameter(1, teamNo);
    		   		
            results.addAll(query.getResultList());
    		
    	} catch(NoResultException nex) { return results; }	

        return results;
    }
    
//replacemanent for "searchprovider"  in ProviderDao JDBC
    public List<ProviderData> searchDoctors() {
    	String sqlCommand = "select p from ProviderData p where p.providerType='doctor' and p.status='1' order by lastName";
        Query query = entityManager.createQuery(sqlCommand);

        @SuppressWarnings("unchecked")
        List<ProviderData> results = query.getResultList();
        return results;
    }
    
  //replacemanent for "search_provider_all"  in tickler.jspf JDBC
    public List<ProviderData> findAllActiveOrderByLastName(String providerNo) {
    	String sqlCommand = "select x from ProviderData x where provider_no like ?1 and x.status='1' order by x.lastName";
        Query query = entityManager.createQuery(sqlCommand);
        query.setParameter(1, providerNo);

        @SuppressWarnings("unchecked")
        List<ProviderData> results = query.getResultList();
        return results;
    }

}
