package com.oscar.eformportal.ws.service;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oscar.OscarProperties;

public class ImportPortal extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String name = request.getParameter("name");
		int fid = Integer.parseInt(request.getParameter("fid"));
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IOscarService.class);
		String wsAddress = OscarProperties.getInstance().getProperty("eformPortal_ws_url");
		factory.setAddress(wsAddress+"ws/portal");
		IOscarService service = (IOscarService) factory.create();
		ConfigHttpConduit.config(service);
		List<EformInfo> infos = service.getEforms();
		EformInfo info = new EformInfo();
		for(EformInfo eformInfo : infos){
			if(eformInfo.getFid()==fid){
				info = eformInfo;
			}
		}
		MyFile myFile = new MyFile();
		myFile = service.uploadFile(fid);
		OutputStream os = null;
		String tmpDir = OscarProperties.getInstance().getProperty("eformPortal_tmp_dir");
		File dirFile = new File(tmpDir);;
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		os = org.apache.commons.io.FileUtils.openOutputStream(new File(tmpDir + File.separator + name + ".zip"));
		os.write(myFile.getBytes());
		UnZip.decompression(tmpDir + File.separator + name + ".zip",info);
		ActionForward af = null;
		af = mapping.findForward("import");
		return af;
	} 
}
