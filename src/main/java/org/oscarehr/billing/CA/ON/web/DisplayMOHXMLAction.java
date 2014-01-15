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
package org.oscarehr.billing.CA.ON.web;

import java.io.File;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.oscarehr.util.MiscUtils;

public class DisplayMOHXMLAction extends Action {
	private static Logger log = MiscUtils.getLogger();

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse servletResponse) {	
		StringWriter writer = new StringWriter();
		
		if(StringUtils.isBlank(request.getParameter("filename"))
				|| StringUtils.isBlank((String)request.getSession().getAttribute("backupfilepath"))) {
			log.error("Wrong parameters: filename="+request.getParameter("filename")+", session.backupfilepath="+(String)request.getSession().getAttribute("backupfilepath"));
			request.setAttribute("xsltError", "Action failed: wrong parameters");
			return actionMapping.findForward("failure");
		}
		String fname = request.getParameter("filename");   
		String backuppath = (String) request.getSession().getAttribute("backupfilepath");
		String fullpath = backuppath+fname;
		
		String sname="ES.xsl";
		if (fname.substring(2,4).equals("OU")) {
		   sname="OU.xsl";
		}

		try {
			String xslPath = request.getSession().getServletContext().getRealPath(request.getServletPath());	

			xslPath = xslPath.replaceAll("\\\\", "/");
			int idx = xslPath.lastIndexOf("/");			
			xslPath = xslPath.substring(0, idx);
			
			transformToString(fullpath, xslPath +"/"+sname, writer);
			
			String content = writer.toString();
			if(content == null || content.equals("")) {
				log.error("XSLT transformation failed: returned nothing");
				request.setAttribute("xsltError", "Action failed: XSLT transformation failed.");
				return actionMapping.findForward("failure");							
			}
			content = content.replaceAll("\r", "").replaceAll("\n", "");
			
			request.setAttribute("xsltResult", content);
			return actionMapping.findForward("success");
		} catch(Exception ex) {
			log.error("XSLT transformation failed", ex);
			request.setAttribute("xsltError", "Action failed: XSLT transformation failed.");
			return actionMapping.findForward("failure");			
		}
	}
	
	public static void transformToString(String sourcePath, String xsltPath,  
            StringWriter writer) throws Exception {  
		TransformerFactory tFactory = TransformerFactory.newInstance();  
		Transformer transformer =  
			tFactory.newTransformer(new StreamSource(new File(xsltPath)));  

		transformer.transform(new StreamSource(new File(sourcePath)),  
			new StreamResult(writer));  
	}
	
}
