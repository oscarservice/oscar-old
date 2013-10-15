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


package org.oscarehr.provider.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.common.dao.MyGroupDao;
import org.oscarehr.common.dao.PrivateProvidersUtil;
import org.oscarehr.common.dao.ProviderDataDao;
import org.oscarehr.common.dao.SiteDao;
import org.oscarehr.common.model.ProviderData;
import org.oscarehr.common.model.Site;
import org.oscarehr.util.MiscUtils;
import org.oscarehr.util.SpringUtils;

import oscar.Dict;

/**
 *
 * @author ekatyukhin
 */
public class ProviderListAjaxAction extends DispatchAction {
	private ProviderDataDao providerDataDao;
	private MyGroupDao myGroupDao;
	private SiteDao siteDao;

	public ProviderListAjaxAction() {
	}

	public void setProviderDataDao(ProviderDataDao dao) {
		this.providerDataDao = dao;
	}
	public void setMyGroupDao(MyGroupDao dao) {
		this.myGroupDao = dao;
	}
	public void setSiteDao(SiteDao dao) {
		this.siteDao = dao;
	}

	public ActionForward unspecified(ActionMapping actionmapping,
			ActionForm actionform,
			HttpServletRequest request,
			HttpServletResponse response) {

		return null;
	}

	public ActionForward getProvidersAndGroupsList(ActionMapping actionmapping,
			ActionForm actionform,
			HttpServletRequest request,
			HttpServletResponse response) {

		ResourceBundle oscarResource = ResourceBundle.getBundle("oscarResources", request.getLocale());    	

		String resHtml = "{\"options\": [";
		resHtml += "{\"value\": \""+"."+oscarResource.getString("global.default")+"\", \"text\": \""+"."+oscarResource.getString("global.default")+"\"}";
		boolean isMultisites = org.oscarehr.common.IsPropertiesOn.isMultisitesEnable();
		String curUser_no = (String) request.getSession().getAttribute("user");
		Boolean isTeamScheduleOnly = (request.getParameter("isTeamScheduleOnly") != null ? Boolean.valueOf(request.getParameter("isTeamScheduleOnly")) : false);
		Boolean isSiteAccessPrivacy = (request.getParameter("isSiteAccessPrivacy") != null ? Boolean.valueOf(request.getParameter("isSiteAccessPrivacy")) : false);
		Boolean isTeamAccessPrivacy = (request.getParameter("isTeamAccessPrivacy") != null ? Boolean.valueOf(request.getParameter("isTeamAccessPrivacy")) : false);
//		String role = (String)request.getSession().getAttribute("userrole");
		String providerview = request.getParameter("providerview")==null?"all":request.getParameter("providerview");
		String mygroupno = request.getParameter("mygroupno");
		int NameMaxLen = 15;
		Dict formatter = new Dict();
		List<ProviderData> resultList1 = null;

		if(!isMultisites) {
			List<String> resultList = new ArrayList<String>();			
			resultList = myGroupDao.getGroupNosOrderByGroupNo();
			for (String group : resultList) {
				resHtml += ",{\"value\": \""+"_grp_"+group+"\""+
					", \"text\": \""+oscarResource.getString("provider.appointmentprovideradminmonth.formGRP")+" : "+group+"\"" +
//							", \"style\": \""+(selectedSiteBgColor != null ? "color:"+selectedSiteBgColor : "")+"\"" +
					(providerview.indexOf("_grp_") != -1 && mygroupno.equals(group) ? ", \"selected\": \"selected\"":"")+"}";
			}
		
			String providerNo = "%";
			List<ProviderData> providers = providerDataDao.findAllActiveOrderByLastName(providerNo);  //get all providers
			if(providers != null) {
				for(ProviderData provider : providers) {
					formatter.setDef(String.valueOf(provider.getId()), provider.getLastName()+","+provider.getFirstName());
					resHtml += ",{\"value\": \""+provider.getId()+"\""+
							", \"text\": \""+formatter.getShortDef(provider.getId(), "", NameMaxLen)+"\"" +
							(providerview != null && providerview.equals(provider.getId()) ? ", \"selected\": \"selected\"":"")+"}";

				}
			}	 
		} else {
			List<String> siteGroups = new ArrayList<String>();
			List<String> siteProviderNos = new ArrayList<String>();

			String selectedSiteBgColor = "#000000"; //default
			String selectedSite = "%";
			if(request.getParameter("siteId") != null 
					&& !request.getParameter("siteId").equals("null") 
					&& !request.getParameter("siteId").equals("."+oscarResource.getString("global.default"))) {
				Integer siteId = Integer.parseInt(request.getParameter("siteId"));
				Site selSite = siteDao.find(siteId);
				selectedSiteBgColor = selSite.getBgColor();
				selectedSite = selSite.getName();				
			}

			String[] roles = ((String)request.getSession().getAttribute("userrole")).split(",");
			boolean isNoGroupsForDoctors = roles[0].equalsIgnoreCase("doctor");

			PrivateProvidersUtil privateProvidersUtil = (PrivateProvidersUtil) SpringUtils.getBean("privateProvidersUtil");
			List providersData = privateProvidersUtil.getPrivateProvidersData(
					isMultisites, isTeamAccessPrivacy, 
					isSiteAccessPrivacy, 
					curUser_no, selectedSite, 
					isNoGroupsForDoctors);

			siteGroups = (List<String>) providersData.get(3);
	
			if (isSiteAccessPrivacy || isTeamAccessPrivacy) {
				//user has Access Privacy, set user provider and group list
				if (selectedSite==null) {
					siteProviderNos = siteDao.getProviderNoBySiteManagerProviderNo(curUser_no);
					siteGroups = siteDao.getGroupBySiteManagerProviderNo(curUser_no);
					
				} 
			}
	
			if(selectedSite != null) {
				siteProviderNos = siteDao.getProviderNoBySiteLocation(selectedSite);
				siteGroups = siteDao.getGroupBySiteLocation(selectedSite); 		
				
			}

			if(isTeamScheduleOnly) {
				List<ProviderData> resultList = new ArrayList<ProviderData>();
				String provider_no = curUser_no;
				resultList = providerDataDao.searchLoginTeam(provider_no);
				for (ProviderData provider : resultList) {
					formatter.setDef(String.valueOf(provider.getId()), provider.getLastName()+","+provider.getFirstName());	
					resHtml += ",{\"value\": \""+provider.getId()+"\""+
							", \"text\": \""+formatter.getShortDef(provider.getId(), "", NameMaxLen)+"\"" +
//							", \"style\": \""+(selectedSiteBgColor != null ? "color:"+selectedSiteBgColor : "")+"\"" +
							(providerview.equals(provider.getId()) ? ", \"selected\": \"selected\"":"")+"}";
				}

			} else {
				List<String> resultList = new ArrayList<String>();
				resultList = myGroupDao.getGroupNosOrderByGroupNo();
				for (String group : resultList) {
					if (!isMultisites || siteGroups == null || siteGroups.size() == 0 || siteGroups.contains(group)) {  		
						resHtml += ",{\"value\": \""+"_grp_"+group+"\""+
								", \"text\": \""+oscarResource.getString("provider.appointmentprovideradminmonth.formGRP")+" : "+group+"\"" +
//								", \"style\": \""+(selectedSiteBgColor != null ? "color:"+selectedSiteBgColor : "")+"\"" +
								(providerview.indexOf("_grp_") != -1 && mygroupno.equals(group) ? ", \"selected\": \"selected\"":"")+"}";
					}
				}
			

				resultList1 = providerDataDao.searchDoctors();  //should list all staff not just only doctors.				
				for (ProviderData provider : resultList1) {
					if (!isMultisites || siteProviderNos  == null || siteProviderNos.size() == 0 || siteProviderNos.contains(provider.getId())) { 
						formatter.setDef(String.valueOf(provider.getId()), provider.getLastName()+","+provider.getFirstName());
						resHtml += ",{\"value\": \""+provider.getId()+"\""+
								", \"text\": \""+formatter.getShortDef(provider.getId(), "", NameMaxLen)+"\"" +
//								", \"style\": \""+(selectedSiteBgColor != null ? "color:"+selectedSiteBgColor : "")+"\"" +
								(providerview.equals(provider.getId()) ? ", \"selected\": \"selected\"":"")+"}";
					}
				}
			}
		}

		resHtml += "]}";
		try {
			response.getWriter().print(resHtml);

		} catch (IOException e) {
			MiscUtils.getLogger().error("Couldn't get data for provider list", e);
		}

		return null;
	}

	public ActionForward getProvidersList(ActionMapping actionmapping,
			ActionForm actionform,
			HttpServletRequest request,
			HttpServletResponse response) {

		ResourceBundle oscarResource = ResourceBundle.getBundle("oscarResources", request.getLocale());    	

		String resHtml = "{\"options\": [";
		resHtml += "{\"value\": \""+"."+oscarResource.getString("global.default")+"\", \"text\": \""+"."+oscarResource.getString("global.default")+"\"}";
		boolean isMultisites = org.oscarehr.common.IsPropertiesOn.isMultisitesEnable();
		String curUser_no = (String) request.getSession().getAttribute("user");
		Boolean isTeamScheduleOnly = (request.getParameter("isTeamScheduleOnly") != null ? Boolean.valueOf(request.getParameter("isTeamScheduleOnly")) : false);
		Boolean isSiteAccessPrivacy = (request.getParameter("isSiteAccessPrivacy") != null ? Boolean.valueOf(request.getParameter("isSiteAccessPrivacy")) : false);
		Boolean isTeamAccessPrivacy = (request.getParameter("isTeamAccessPrivacy") != null ? Boolean.valueOf(request.getParameter("isTeamAccessPrivacy")) : false);

		String providerview = request.getParameter("providerview")==null?"all":request.getParameter("providerview");
		
		int NameMaxLen = 15;
		Dict formatter = new Dict();
		List<ProviderData> resultList1 = null;

		if(!isMultisites) {
			List<String> resultList = new ArrayList<String>();				
		
			String providerNo = "%";
			List<ProviderData> providers = providerDataDao.findAllActiveOrderByLastName(providerNo);  //get all providers
			if(providers != null) {
				for(ProviderData provider : providers) {
					formatter.setDef(String.valueOf(provider.getId()), provider.getLastName()+","+provider.getFirstName());
					resHtml += ",{\"value\": \""+provider.getId()+"\""+
							", \"text\": \""+formatter.getShortDef(provider.getId(), "", NameMaxLen)+"\"" +
							(providerview != null && providerview.equals(provider.getId()) ? ", \"selected\": \"selected\"":"")+"}";

				}
			}	 
		} else {			
			List<String> siteProviderNos = new ArrayList<String>();

			String selectedSiteBgColor = "#000000"; //default
			String selectedSite = "%";
			if(request.getParameter("siteId") != null 
					&& !request.getParameter("siteId").equals("null") 
					&& !request.getParameter("siteId").equals("."+oscarResource.getString("global.default"))) {
				Integer siteId = Integer.parseInt(request.getParameter("siteId"));
				Site selSite = siteDao.find(siteId);
				selectedSiteBgColor = selSite.getBgColor();
				selectedSite = selSite.getName();				
			}

			
			if (selectedSite==null) {
					siteProviderNos = siteDao.getProviderNoBySiteManagerProviderNo(curUser_no);					
							
			}
	
			if(selectedSite != null) {
				siteProviderNos = siteDao.getProviderNoBySiteLocation(selectedSite);						
			
			}
			
			List<ProviderData> providers = new ArrayList<ProviderData> ();
			for(String providerNo : siteProviderNos) {
				ProviderData provider = providerDataDao.findByProviderNo(providerNo);
				providers.add(provider);
			}
			Collections.sort(providers, ProviderData.providerLastNameCompare);
			
			for(ProviderData provider : providers) {
				//ProviderData provider = providerDataDao.findByProviderNo(providerNo);
				formatter.setDef(String.valueOf(provider.getId()), provider.getLastName()+","+provider.getFirstName());
				resHtml += ",{\"value\": \""+provider.getId()+"\""+
						", \"text\": \""+formatter.getShortDef(provider.getId(), "", NameMaxLen)+"\"" +
						(providerview.equals(provider.getId()) ? ", \"selected\": \"selected\"":"")+"}";		
			}
			
		}

		resHtml += "]}";
		try {
			response.getWriter().print(resHtml);

		} catch (IOException e) {
			MiscUtils.getLogger().error("Couldn't get data for provider list", e);
		}

		return null;
	}
}
