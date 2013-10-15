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
import java.io.FileWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.oscarehr.common.dao.EFormDataDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.EFormData;
import org.oscarehr.util.SpringUtils;
import org.oscarehr.util.WKHtmlToPdfUtils;

import oscar.OscarProperties;

public class EFormsExportUtil
{
	private Logger logger = Logger.getLogger(EFormsExportUtil.class);
	private OscarProperties oscarProperties = OscarProperties.getInstance();

	private EFormDataDao eFormDataDao = (EFormDataDao) SpringUtils.getBean("EFormDataDao");
	private String oscarImagePath = oscarProperties.getProperty("oscar_image_path", "");

	public EFormsExportUtil()
	{
		if (!oscarImagePath.isEmpty() && oscarImagePath.charAt(oscarImagePath.length() - 1) != '/')
		{
			oscarImagePath += "/";
		}
	}

	public void exportEForms(Demographic demographic, String outputDirPath) throws Exception
	{
		logger.info("in EFormsExportUtil : exportEForms");
		logger.info("demographic = "+demographic);
		logger.info("outputDirPath = "+outputDirPath);
		
		if (demographic != null)
		{
			Integer demographicId = demographic.getDemographicNo();
			String demoLastName = demographic.getLastName();
			String demoFirstName = demographic.getFirstName();

			String eFormName = "";
			String eFormId = "";
			
			List<EFormData> eFormDataList = eFormDataDao.findByDemographicIdCurrentPatientIndependent(demographicId, true, null);
			logger.info("found <"+(eFormDataList!=null?eFormDataList.size():"0")+"> eforms to be exported for demographic no. - "+demographicId);
			
			for (EFormData eFormData : eFormDataList)
			{
				eFormName = eFormData.getFormName();
				if(eFormName!=null)
					eFormName = eFormName.replaceAll("[\t ]+", "_");
				eFormId = eFormData.getId()+"";
				
				String outputFileName = demographicId + "_" + demoFirstName + "_" + demoLastName + "_eformExport_" + eFormName+ "_"+eFormId+ ".pdf";
				File outputPDFFile = new File(outputDirPath, outputFileName);

				try
				{
					logger.info("exporting eform - "+eFormName);
					convertHTMLToPDF(eFormData.getFormData(), outputPDFFile, outputDirPath);
					logger.info("eform exported to file - "+outputPDFFile.getAbsolutePath());
				}
				catch (Exception e)
				{
					logger.error("error while exporting eform - "+eFormName+" , for demographic no. - "+demographicId, e);
				}
			}
		}
		
		logger.info("out EFormsExportUtil : exportEForms");
	}

	private void convertHTMLToPDF(String formData, File outputPdf, String outputDirPath) throws Exception
	{
		formData = formData.replace("../eform/displayImage.do?imagefile=", oscarImagePath);

		File file = new File(outputDirPath, "tmp_eform.html");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(formData);
		fileWriter.close();
		file.deleteOnExit();

		WKHtmlToPdfUtils.convertToPdf(file.getAbsolutePath(), outputPdf);
		
		try
		{
			if(file!=null && file.exists())
				file.delete();
		}
		catch (Exception e)
		{
		}
	}

}
