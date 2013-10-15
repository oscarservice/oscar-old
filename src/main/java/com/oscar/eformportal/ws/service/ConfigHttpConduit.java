package com.oscar.eformportal.ws.service;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.oscarehr.util.CxfClientUtils;

public class ConfigHttpConduit {
	public static void config(Object service) {
		Client cxfClient = ClientProxy.getClient(service);
		HTTPConduit conduit = (HTTPConduit)cxfClient.getConduit();
		String targetAddr = conduit.getTarget().getAddress().getValue();
		if (targetAddr.toLowerCase().startsWith("https:")) {
			CxfClientUtils.configureClientConnection(service);
		}
	}
}
