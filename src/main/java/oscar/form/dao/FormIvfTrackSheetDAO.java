package oscar.form.dao;

import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.oscarehr.common.dao.AbstractDao;
import oscar.form.model.FormIvfTrackSheet;

/**
*
* @author rjonasz
*/
@Repository
public class FormIvfTrackSheetDAO extends AbstractDao<FormIvfTrackSheet>{
	
	public FormIvfTrackSheetDAO(){
		super(FormIvfTrackSheet.class);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findInfoByDemographic(int demographicNo){
		String sql = "SELECT new map(d.DemographicNo as demo_no, d.LastName as ln, d.FirstName as fn, " +
				 "d.YearOfBirth as year,d.MonthOfBirth as month,d.DateOfBirth as date, d.Hin as hin,d.Ver as ver,d.Address as address,d.City as city,d.Postal as postal,d.Phone as phone )" +
				 "FROM Demographic d WHERE d.DemographicNo =?1 ";
		 Query query = entityManager.createQuery(sql);
		 query.setParameter(1, demographicNo);
		 	 
		 List<Map<String,Object>> results=query.getResultList();
		 return (results);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProviderNameByDemographic(int demographicNo){
		String sql = "SELECT new map(p.LastName as ln,p.FirstName as fn) FROM Provider p,Demographic d WHERE p.ProviderNo = d.ProviderNo AND d.DemographicNo =?1";
		Query query = entityManager.createQuery(sql);
		query.setParameter(1, demographicNo);
				
		List<Map<String,Object>> results=query.getResultList();
		return (results);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPartnerInfoByDemographic(int demographicNo){
		String sql = "SELECT new map(d.LastName as ln,d.FirstName as fn,d.Hin as hin,d.Ver as ver,d.YearOfBirth as year,d.MonthOfBirth as month,d.DateOfBirth as date) FROM Demographic d,Relationships r WHERE r.relationDemographicNo = d.DemographicNo AND r.relation = 'Partner' AND r.demographicNo = ?1";
		Query query = entityManager.createQuery(sql);
		query.setParameter(1, demographicNo);
		query.setMaxResults(1);
		
		List<Map<String,Object>> results=query.getResultList();
		return (results);
	}
	
	public List<FormIvfTrackSheet> getAllInfoByDemographicAndId(int demographicNo,int ID){
		String sql = "SELECT f FROM FormIvfTrackSheet f WHERE f.demographicNo =?1 AND f.id =?2 ";
		Query query = entityManager.createQuery(sql);
		query.setParameter(1, demographicNo);
		query.setParameter(2, ID);
		
		@SuppressWarnings("unchecked")
		List<FormIvfTrackSheet> results=query.getResultList();
		return (results);
	}
	
}
