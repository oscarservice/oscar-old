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

/**
 * @author Rohit Prajapati (rohitprajapati54@gmail.com)
 */
package oscar.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.oscarehr.common.dao.ConsultationRequestDao;
import org.oscarehr.common.model.ConsultationRequest;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.util.SpringUtils;
import org.oscarehr.util.WKHtmlToPdfUtils;

import oscar.OscarProperties;

public class ConsultationsExportUtil
{
	private Logger logger = Logger.getLogger(ConsultationsExportUtil.class);
	private ConsultationRequestDao consultationRequestDao = (ConsultationRequestDao) SpringUtils.getBean(ConsultationRequestDao.class);
	private static OscarProperties oscarProperties = OscarProperties.getInstance();

	/**
	 * export consultations for given demographic into given directory.
	 * one pdf file will be generated for each consultations.
	 * generated pdf file name will be : <demographicNo>_<demoFirstName>_<demoLastName>_consulation_<requestId>.pdf
	 * 
	 * @param demographicNo
	 *            - Demographic No.
	 * @param oscarUrl
	 *            - oscar url (http://server:port/context_root)
	 * @param outputPDFFile
	 *            - output pdf file object
	 * @throws Exception
	 */
	public List<File> exportConsultations(Demographic demographic, String oscarUrl, String dirPath) throws Exception
	{
		logger.info("in ConsultationsExportUtil : exportConsultations");
		logger.info("demographic = "+demographic);
		logger.info("oscarUrl = "+oscarUrl);
		logger.info("dirPath = "+dirPath);
		
		List<File> pdfFiles = null;
		
		if (demographic!=null)
		{
			String demographicNo = demographic.getDemographicNo()+"";
			pdfFiles = new ArrayList<File>();
			
			File tmpHtml = new File(dirPath, "tmp_cons.html");
			String requestURL = oscarUrl + "/oscarEncounter/oscarConsultationRequest/ConsultationFormPrint.jsp?fromScheduler=true&reqId=";
			
			List<ConsultationRequest> consultationReqList = consultationRequestDao.getConsults(demographicNo);
			logger.info("found <"+(consultationReqList!=null?consultationReqList.size():"0")+"> to be exported.");
			
			for (ConsultationRequest consultReq : consultationReqList)
			{
				Integer reqId = -1;
				try
				{
					reqId = consultReq.getId();
					logger.info("exorting consultation request id : "+reqId);

					URL urlConsultReq = new URL(requestURL + reqId);
					HttpURLConnection urlConnection = (HttpURLConnection) urlConsultReq.openConnection();
					// urlConnection.setRequestProperty("Cookie",
					// "JSESSIONID="+request.getSession().getId());
					InputStream is = urlConnection.getInputStream();
					FileOutputStream fos = new FileOutputStream(tmpHtml);

					int read = 0;
					byte[] bytes = new byte[1024];
					while ((read = is.read(bytes)) != -1)
					{
						fos.write(bytes, 0, read);
					}

					String outputPDFFileName = demographicNo + "_" + demographic.getFirstName() + "_" + demographic.getLastName() + "_consultationExport_" + reqId;
					File outputPdf = new File(dirPath, outputPDFFileName + ".pdf");	
				
					// generate pdf for each consultation
					WKHtmlToPdfUtils.convertToPdf(tmpHtml.getAbsolutePath(), outputPdf);

					pdfFiles.add(outputPdf);
					logger.info("consultation exported for request id : "+reqId+" , pdf generated - "+outputPdf.getAbsolutePath());
				}
				catch (Exception e)
				{
					logger.error("error while exporting consultation for req id - "+reqId, e);
				}
			}
			
			try
			{
				if(tmpHtml!=null && tmpHtml.exists())
					tmpHtml.delete();				
			}
			catch (Exception e)
			{
			}
		}

		logger.info("out ConsultationsExportUtil : exportConsultations");
		return pdfFiles;
		/*
		 * Util.zipFiles(pdfFiles, "consultReqs.zip", tmp_dir);
		 * Util.downloadFile("consultReqs.zip", tmp_dir, response);
		 * tmpHtml.delete(); Util.cleanFile("consultReqs.zip", tmp_dir);
		 * Util.cleanFiles(pdfFiles);
		 */
	}
}
