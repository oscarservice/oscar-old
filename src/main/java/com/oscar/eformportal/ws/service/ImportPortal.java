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
