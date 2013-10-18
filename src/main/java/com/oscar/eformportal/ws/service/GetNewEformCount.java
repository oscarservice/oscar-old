package com.oscar.eformportal.ws.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.oscarehr.util.DbConnectionFilter;
import org.oscarehr.util.MiscUtils;

import oscar.OscarProperties;

public class GetNewEformCount {
	public static int getCount(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IOscarService.class);
		String wsAddress = OscarProperties.getInstance().getProperty("eformPortal_ws_url");
		factory.setAddress(wsAddress+"ws/portal");
		IOscarService service = (IOscarService)factory.create();
		ConfigHttpConduit.config(service);
		String sql = "select * from eform_inquiry";
		int count = 0 ;
		ResultSet rs;
		try {
			rs = DbConnectionFilter.getThreadLocalDbConnection().prepareStatement(sql).executeQuery();
			if(rs.next()){
				 count = service.getNewEform(rs.getTimestamp("inquirytime"));	
			}
		} catch (SQLException e) {
			MiscUtils.getLogger().debug(e.toString());
		}
		return count;
	}
	
	public static int getFormsNum(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IOscarService.class);
		String wsAddress = OscarProperties.getInstance().getProperty("eformPortal_ws_url");
		factory.setAddress(wsAddress+"ws/portal");
		IOscarService service = (IOscarService)factory.create();
		ConfigHttpConduit.config(service);
		int formsNum =0 ;
		formsNum=service.getEformsNum();
		return formsNum;
	}
}
