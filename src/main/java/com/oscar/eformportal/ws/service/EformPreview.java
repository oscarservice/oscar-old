package com.oscar.eformportal.ws.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oscar.OscarProperties;

public class EformPreview extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int fid = Integer.parseInt(request.getParameter("fid"));
		String imageName = request.getParameter("imageFile");
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IOscarService.class);
		String wsAddress = OscarProperties.getInstance().getProperty("eformPortal_ws_url");
		factory.setAddress(wsAddress+"ws/portal");
		IOscarService service = (IOscarService) factory.create();
		ConfigHttpConduit.config(service);
		Image image = service.showImage(fid, imageName);
		String fileName = imageName.substring(imageName.indexOf("."), imageName.length());
		if("png".equalsIgnoreCase(fileName)){
			response.setContentType("image/png");
		}
		if("jpg".equalsIgnoreCase(fileName)){
			response.setContentType("image/jpeg");
		}
		response.setContentType("text/html; charset=utf-8");
		OutputStream os = response.getOutputStream();
		os.write(image.getBytes());
		return null;
	}
}
