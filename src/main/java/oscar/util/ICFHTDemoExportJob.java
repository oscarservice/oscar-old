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

/**
 * @author Rohit Prajapati (rohitprajapati54@gmail.com)
 */
package oscar.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.oscarehr.common.dao.DemographicSiteDao;
import org.oscarehr.common.model.DemographicSite;
import org.oscarehr.util.SpringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import oscar.OscarProperties;

public class ICFHTDemoExportJob implements Job
{
	private static Logger logger = Logger.getLogger(ICFHTDemoExportJob.class);
	
	private static OscarProperties oscarProperties = OscarProperties.getInstance();	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		logger.info("in ICFHTDemoExportJob : execute");
		
		try
		{
			List<String> demographicIdList = getDemographicIdsToBeExported();
			logger.info("demographicIdList = "+demographicIdList);
			
			final ICFHTDemoExportUtil demoExportUtil = new ICFHTDemoExportUtil();
			
			for (final String demographicId : demographicIdList)
			{
				Thread t1 = new Thread(){
					public void run(){
						try
						{
							demoExportUtil.exportDemographic(demographicId);
						}
						catch (Exception e)
						{
							logger.error("Error while exporting demographic no. - "+demographicId);
						}
					}
				};
				t1.start();
				t1.join();
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		
		logger.info("out ICFHTDemoExportJob : execute");
	}
	
	private List<String> getDemographicIdsToBeExported()
	{
		List<String> demographicIdList = null;
		
		String siteIds = oscarProperties.getProperty("ICFHT_DEMO_EXPORT_SITE_IDS", "");
		
		List<DemographicSite> demographicSiteList = null; 
		if(siteIds!=null && siteIds.length()>0)
		{
			demographicIdList = new ArrayList<String>();
			String[] siteIdsArr = siteIds.split(",");
			
			DemographicSiteDao demographicSiteDao = (DemographicSiteDao)SpringUtils.getBean("demographicSiteDao");
			
			for (String siteId : siteIdsArr)
			{
				siteId = siteId.trim();
				demographicSiteList = demographicSiteDao.findDemographicBySiteId(Integer.parseInt(siteId));
				
				if(demographicSiteList!=null && demographicSiteList.size()>0)
				{
					for (DemographicSite demographicSite : demographicSiteList)
					{
						if(demographicSite!=null)
							demographicIdList.add(demographicSite.getDemographicId()+"");
					}					
				}
			}
		}
		
		return demographicIdList;
	}
}
