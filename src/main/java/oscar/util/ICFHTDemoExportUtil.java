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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.util.SpringUtils;

import oscar.OscarProperties;

public class ICFHTDemoExportUtil
{
	private static Logger logger = Logger.getLogger(EFormsExportUtil.class);

	private static OscarProperties oscarProperties = OscarProperties.getInstance();
	public static final String EXPORTED_PDF_DEST_DIR = oscarProperties.getProperty("FOLDER_STORE_PDF_FILES", "");;

	private DemographicDao demographicDao = (DemographicDao) SpringUtils.getBean("demographicDao");

	private ConsultationsExportUtil consultationsExportUtil = new ConsultationsExportUtil();
	private EFormsExportUtil eFormsExportUtil = new EFormsExportUtil();
	private EChartExportUtil eChartExportUtil = new EChartExportUtil();
	private EncounterFormPDFExportUtil encounterFormPDFExportUtil = new EncounterFormPDFExportUtil();

	public void exportDemographic(String demographicNo)
	{
		Demographic demographic = getDemographic(demographicNo);
		if (demographic != null)
		{
			try
			{
				logger.info("############## exporting demographic - " + demographicNo + " : start ##############");
				logger.info("===================== exporting eforms : start =====================");
				exportEForms(demographic);
				logger.info("===================== exporting eforms : end =====================");
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}

			try
			{
				logger.info("===================== exporting consultations : start =====================");
				exportConsultations(demographic);
				logger.info("===================== exporting consultations : end =====================");
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}

			try
			{
				logger.info("===================== exporting echart : start =====================");
				exportEChart(demographic);
				logger.info("===================== exporting echart : end =====================");
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}
			
			try
			{
				logger.info("===================== exporting encounter forms : start =====================");
				exportEncounterForms(demographicNo);
				logger.info("===================== exporting encounter forms : end =====================");
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}
			logger.info("############## exporting demographic - " + demographicNo + " : end ##############");
						
		}
	}

	private Demographic getDemographic(String demographicNo)
	{
		Demographic demographic = demographicDao.getDemographic(demographicNo);
		return demographic;
	}

	private void exportEChart(Demographic demographic) throws Exception
	{
		String demoName = demographic.getFirstName() + "_" + demographic.getLastName();
		String outputFileName = demographic.getDemographicNo() + "_" + demoName + "_echart.pdf";

		File file = new File(EXPORTED_PDF_DEST_DIR, outputFileName);
		FileOutputStream fout = new FileOutputStream(file);

		eChartExportUtil.exportEChart(demographic, fout);
	}

	public void exportEChart(String demographicNo) throws Exception
	{
		Demographic demographic = getDemographic(demographicNo);
		exportEChart(demographic);
	}

	public void exportEForms(String demographicNo) throws Exception
	{
		Demographic demographic = getDemographic(demographicNo);
		exportEForms(demographic);
	}

	public void exportEForms(Demographic demographic) throws Exception
	{
		eFormsExportUtil.exportEForms(demographic, EXPORTED_PDF_DEST_DIR);
	}

	public void exportConsultations(String demographicNo) throws Exception
	{
		Demographic demographic = getDemographic(demographicNo);
		exportConsultations(demographic);
	}

	private void exportConsultations(Demographic demographic) throws Exception
	{
		try
		{
			if (demographic != null)
			{
				String oscarUrl = oscarProperties.getProperty("OSCAR_URL", "");

				List<File> pdfFiles = consultationsExportUtil.exportConsultations(demographic, oscarUrl, EXPORTED_PDF_DEST_DIR);
				logger.info("<" + (pdfFiles != null ? pdfFiles.size() : "0") + "> consultations exported for demographic no. - " + demographic.getDemographicNo());

				if (pdfFiles != null && pdfFiles.size() > 0)
				{
					List<String> fileNamesList = new ArrayList<String>();
					for (File file : pdfFiles)
					{
						if (file != null)
							fileNamesList.add(file.getAbsolutePath());
					}
					
					String finalPDFFilePath = demographic.getDemographicNo() + "_" + demographic.getFirstName() + "_" + demographic.getLastName() + "_consultation.pdf";
						
					FileOutputStream fout = new FileOutputStream(new File(EXPORTED_PDF_DEST_DIR, finalPDFFilePath));
					
					ConcatPDF.concat((ArrayList) fileNamesList, fout);
					
					
					for (File file : pdfFiles)
					{
						try
						{
							file.delete();
						}
						catch (Exception e)
						{
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Error while exporting consulations for demographic no. - " + demographic.getDemographicNo(), e);
			throw e;
		}
	}

	public void exportEncounterForms(String demographicNo) throws Exception
	{
		String oscarUrl = oscarProperties.getProperty("OSCAR_URL", "");
		encounterFormPDFExportUtil.exportEncounterFormsPDF(demographicNo, oscarUrl, EXPORTED_PDF_DEST_DIR);
	}
}
