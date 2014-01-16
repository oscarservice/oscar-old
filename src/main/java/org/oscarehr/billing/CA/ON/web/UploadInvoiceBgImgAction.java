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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.oscarehr.billing.CA.ON.dao.BillingOnPrintDao;
import org.oscarehr.billing.CA.ON.model.BillingOnPrint;
import org.oscarehr.util.MiscUtils;
import org.oscarehr.util.SpringUtils;

import oscar.OscarProperties;

public class UploadInvoiceBgImgAction extends Action {
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) {
		 
		 InvoiceBgImgUploadForm imgForm = (InvoiceBgImgUploadForm)form;
		 FormFile imgFile = imgForm.getBgImage();
		 try {
			 InputStream in = imgFile.getInputStream();
			 String docFolder = OscarProperties.getInstance().getProperty("DOCUMENT_DIR");
			 String bgImgName = docFolder + imgFile.getFileName();
			 OutputStream fileOut = new FileOutputStream(new File(bgImgName));
			 byte[] byteBuf = new byte[1024];
			 while (in.read(byteBuf) != -1) {
				 fileOut.write(byteBuf);
			 }
			 
			 BillingOnPrintDao billingOnPrintDao = (BillingOnPrintDao)SpringUtils.getBean("billingOnPrintDao");
			 if (billingOnPrintDao != null) {
				 BillingOnPrint bop = billingOnPrintDao.getOneItem();
				 if (bop == null) {
					 bop = new BillingOnPrint();
				 }
				 bop.setDocName(bgImgName);
				 billingOnPrintDao.save(bop);
			 }
			 request.setAttribute("result", "success!");
		 } catch (FileNotFoundException e) {
			 MiscUtils.getLogger().info(e.toString());
			 request.setAttribute("result", e.toString());
		 } catch (IOException e) {
			 MiscUtils.getLogger().info(e.toString());
			 request.setAttribute("result", e.toString());
		 }
		 
		 return mapping.findForward("success");
	 }
}
