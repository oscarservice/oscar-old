package org.oscarehr.common.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="demographicSite")
public class DemographicSite extends AbstractModel<Integer> implements Serializable {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer demographicId;
	private Integer siteId;
	
	public Integer getId() {
		return null;
	}
	public Integer getDemographicId() {
		return demographicId;
	}
	public void setDemographicId(Integer demographicId) {
		this.demographicId = demographicId;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
		

}
