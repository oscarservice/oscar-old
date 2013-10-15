package com.oscar.eformportal.ws.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oscar.OscarProperties;

public class SearchFromPortal extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IOscarService.class);
		String wsAddress = OscarProperties.getInstance().getProperty("eformPortal_ws_url");
		factory.setAddress(wsAddress+"ws/portal");
		IOscarService service = (IOscarService)factory.create();
		ConfigHttpConduit.config(service);
		String searchinfo = request.getParameter("searchinfo");
		String searchtype = request.getParameter("searchtype");
		List<EformInfo> eformInfos = new ArrayList<EformInfo>(); 
		if("ByName".equals(searchtype)){
			eformInfos = service.getEformsByName(searchinfo);
		}
		if("ByLocation".equals(searchtype)){
			eformInfos = service.getEformsByLocation(searchinfo);
		}
		if("ByCategory".equals(searchtype)){
			eformInfos = service.getEformsByCategory(searchinfo);
		}
		if("ByGroup".equals(searchtype)){
			eformInfos = service.getEformsByGroup(searchinfo);
		}
		if("ByKeyword".equals(searchtype)){
			eformInfos = service.getEformsByKeyword(searchinfo);
		}
		if("All".equals(searchtype)){
			eformInfos = service.getEforms();
		}
		request.setAttribute("eforms", eformInfos);
		ActionForward af = mapping.findForward("portal");
		return af;
	}
}
