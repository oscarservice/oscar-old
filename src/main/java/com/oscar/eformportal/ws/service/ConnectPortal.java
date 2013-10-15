package com.oscar.eformportal.ws.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oscar.OscarProperties;

public class ConnectPortal extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IOscarService.class);
		String wsAddress = OscarProperties.getInstance().getProperty("eformPortal_ws_url");
		factory.setAddress(wsAddress+"ws/portal");
		IOscarService service = (IOscarService) factory.create();
		ConfigHttpConduit.config(service);
		List <EformInfo> infos =  service.getEforms();
		request.setAttribute("eforms", infos);
		ActionForward af = mapping.findForward("portal1");
		return af;
	}
}
