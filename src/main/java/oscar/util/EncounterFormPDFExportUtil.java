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
package oscar.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.dao.EncounterFormDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.EncounterForm;
import org.oscarehr.util.SpringUtils;

import oscar.form.FrmRecord;
import oscar.form.FrmRecordFactory;
import oscar.oscarEncounter.data.EctFormData;

public class EncounterFormPDFExportUtil
{
	private Logger logger = Logger.getLogger(EncounterFormPDFExportUtil.class);
	private DemographicDao demographicDao = (DemographicDao) SpringUtils.getBean("demographicDao");

	private static Map<String, Object> formPrintConfigDtlMap = new HashMap<String, Object>();

	static
	{
		formPrintConfigDtlMap.put("Antenatal Lab Req", "__title=Lab+Request&__cfgfile=labReqPrint07&__template=labReqForm07");

		List<String> reqParamList = new ArrayList<String>();
		reqParamList.add("__title=Antenatal+Record+Part+1&__cfgfile=onar1PrintCfgPg1&__template=onar1");
		reqParamList.add("__title=Antenatal+Record+Part+2&__cfgfile=onar2PrintCfgPg1&__cfgGraphicFile=onar2PrintGraphCfgPg1&__template=onar2");
		reqParamList.add("__title=Antenatal+Record+Part+2&__cfgfile=onar2PrintCfgPg2&__cfgGraphicFile=onar2PrintGraphCfgPg2&__template=onar2");
		reqParamList.add("__title=Antenatal+Record+Part+2&__cfgfile=onar2PrintCfgPg3&__cfgGraphicFile=onar2PrintGraphCfgPg3&__template=onar2");
		formPrintConfigDtlMap.put("AR2005", reqParamList);

		/*reqParamList = new ArrayList<String>();
		reqParamList.add("__cfgGraphicFile=growthBoyLength0_36Graphic&__cfgGraphicFile=growthBoyLength0_36Graphic2");
		reqParamList.add("__cfgGraphicFile=growthBoyHead0_36Graphic&__cfgGraphicFile=growthBoyHead0_36Graphic2");
		reqParamList.add("__cfgGraphicFile=growthBoyHead0_36Graphic&__cfgGraphicFile=growthBoyHead0_36Graphic2");

		reqParamList.add("__cfgGraphicFile=growthBoyLength0_36Graphic&__cfgGraphicFile=growthBoyLength0_36Graphic2");
		reqParamList.add("__cfgGraphicFile=growthBoyHead0_36Graphic&__cfgGraphicFile=growthBoyHead0_36Graphic2");
		reqParamList.add("__cfgGraphicFile=growthBoyHead0_36Graphic&__cfgGraphicFile=growthBoyHead0_36Graphic2");
		formPrintConfigDtlMap.put("Growth 0-36m", reqParamList);*/

		/*reqParamList = new ArrayList<String>();
		reqParamList.add("__cfgGraphicFile=growthChartBoyGraphic&__cfgGraphicFile=growthChartBoyGraphic2");
		reqParamList.add("__cfgGraphicFile=growthChartBoyGraphicBMI");
		formPrintConfigDtlMap.put("Growth Charts", reqParamList);*/

		formPrintConfigDtlMap.put("Lab Req 2007", "__title=Lab+Request&__cfgfile=labReqPrint07&__template=labReqForm07");
		formPrintConfigDtlMap.put("Lab Req 2010", "__title=Lab+Request&__cfgfile=labReqPrintEncounterForm2010&__template=labReqForm2010");
		formPrintConfigDtlMap.put("Mental Health Form1",
				"__title=MentalHealthForm1&__cfgfile=mentalHealthForm1Print&__cfgfile=mentalHealthForm1Print_2&__cfgfile=mentalHealthForm1Print_3&__template=mentalHealthForm1");
		formPrintConfigDtlMap.put("Mental Health Form14", "__title=MentalHealthForm14%20&__cfgfile=mentalHealthForm14Print&__template=mentalHealthForm14");
		formPrintConfigDtlMap.put("Mental Health Form42", "__title=MentalHealthForm42&__cfgfile=mentalHealthForm42Print&__cfgfile=mentalHealthForm42Print_2&__template=mentalHealthForm42");
		formPrintConfigDtlMap.put("Rourke2006",
				"__title=Rourke+Baby+Report&__cfgfile=rourke2006printCfgPg1&__cfgfile=rourke2006printCfgPg2&__cfgfile=rourke2006printCfgPg3&__cfgfile=rourke2006printCfgPg4&__template=rourke2006");
	}

	public List<File> exportEncounterFormsPDF(String demographicNo, String oscarUrl, String destDirPath) throws Exception
	{
		logger.info("exporting demographic encounter forms");
		logger.info("demographicNo = " + demographicNo);
		logger.info("oscarUrl = " + oscarUrl);
		logger.info("destDirPath = " + destDirPath);

		List<File> exportedPDFFileList = null;

		if (demographicNo != null && demographicNo.trim().length() > 0)
		{
			exportedPDFFileList = new ArrayList<File>();

			Demographic demographic = demographicDao.getDemographic(demographicNo);
			ArrayList<HashMap<String, Object>> encounterFormList = getDemographicEncounterForms(demographicNo);

			logger.info("found <" + (encounterFormList != null ? encounterFormList.size() : "0") + "> encounter forms to be exported");

			if (encounterFormList != null && encounterFormList.size() > 0)
			{
				for (HashMap<String, Object> formDataMap : encounterFormList)
				{
					String pdfFileName = getPDFFileName((EctFormData.PatientForm) formDataMap.get("PATIENT_FORM_OBJ"), demographic);

					File pdfFile = null;
					if (destDirPath != null && destDirPath.trim().length() > 0)
						pdfFile = new File(destDirPath, pdfFileName);
					else
						pdfFile = new File(pdfFileName);

					try
					{
						exportEncounterFormPDF(formDataMap, pdfFile, oscarUrl);
						logger.info("exported pdf file - " + (pdfFile.getPath()));

						exportedPDFFileList.add(pdfFile);
					}
					catch (Exception e)
					{
						logger.error("Error while exporting encounter form pdf file - " + pdfFile.getPath(), e);
					}
				}
			}
		}

		logger.info("<" + (exportedPDFFileList != null ? exportedPDFFileList.size() : "0") + "> encounter forms exported for demographic no : <" + demographicNo + ">");
		return exportedPDFFileList;
	}

	public List<File> exportEncounterFormsPDF(String demographicNo, String oscarUrl) throws Exception
	{
		return exportEncounterFormsPDF(demographicNo, oscarUrl, "");
	}

	// exports encounter form pdf file into outputstream
	// oscarUrl : http(s)://hostname:port/oscar_context_root
	private void exportEncounterFormPDF(Map<String, Object> formDataMap, File pdfFile, String oscarUrl) throws Exception
	{
		if (formDataMap != null && formDataMap.get("PATIENT_FORM_OBJ") != null && formDataMap.get("FORM_TABLE") != null)
		{
			EctFormData.PatientForm patientForm = (EctFormData.PatientForm) formDataMap.get("PATIENT_FORM_OBJ");

			String demographicNo = patientForm.getDemoNo();
			String formName = patientForm.getFormName();

			String formClass = formDataMap.get("FORM_TABLE").toString();
			formClass = formClass.replaceFirst("form", "");

			String formId = patientForm.getFormId();
			String submit = "printAll";

			Map<String, String> requestParamMap = new HashMap<String, String>();
			requestParamMap.put("demographic_no", demographicNo);
			requestParamMap.put("form_class", formClass);
			requestParamMap.put("formId", formId);
			requestParamMap.put("submit", submit);
			requestParamMap.put("fromScheduler", "true");

			/*
			 * requestParamMap.put("__title", "Lab Request");
			 * requestParamMap.put("__cfgfile", "labReqPrintEncounterForm2010");
			 * requestParamMap.put("__template", "labReqForm2010");
			 */
			ArrayList<HashMap<String, String>> configList = getConfigParams(formName);
			if (configList==null || configList.size()==0)
			{
				logger.info("config parameters not found for print pdf for the form - " + formName + ", not generating pdf");
				return;
			}

			Properties props = getFormData(Integer.parseInt(demographicNo), Integer.parseInt(formId), formClass);
			if (props != null && props.get("provider_no") != null)
				requestParamMap.put("provider_no", props.get("provider_no").toString());

			String requestUrl = oscarUrl + "/form/formname.do";

			int configListSize = configList.size();
			int index = 0;
			List<File> tempFileList = new ArrayList<File>();
			for (HashMap<String, String> configMap : configList)
			{
				Map<String, String> requestParamMapFinal = copyMap(requestParamMap);
				requestParamMapFinal.putAll(configMap);
				
				byte[] pdfData = sendPostRequest(requestUrl, requestParamMapFinal);
				if (pdfData != null)
				{
					File outFile = pdfFile;
					if(configListSize>1)
					{
						File tmpFile = File.createTempFile(formName+"_"+index, ".pdf", pdfFile.getParentFile());
						outFile = tmpFile;
					}
					OutputStream outputStream = new FileOutputStream(outFile);
					outputStream.write(pdfData);
					outputStream.close();
					
					tempFileList.add(outFile);
					
					index++;
				}
			}
			
			if(tempFileList.size()>1)
			{
				//merge files
				List<String> fileNamesList = new ArrayList<String>();
				for (File file : tempFileList)
				{
					if (file != null)
						fileNamesList.add(file.getAbsolutePath());
				}
				OutputStream fout = new FileOutputStream(pdfFile);
				ConcatPDF.concat((List) fileNamesList, fout);

				for (File file : tempFileList)
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

	private Map<String, String> copyMap(Map<String, String> sourceMap)
	{
		Map<String, String> resultMap = null;
		
		if(sourceMap!=null)
		{
			resultMap = new LinkedHashMap<String, String>();
			
			Set<Entry<String, String>> entrySet = sourceMap.entrySet();
			for (Entry<String, String> entry : entrySet)
			{
				resultMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		return resultMap;
	}
	
	private ArrayList<HashMap<String, String>> getConfigParams(String formName)
	{
		ArrayList<HashMap<String, String>> configList = null;

		if (formPrintConfigDtlMap.get(formName) != null)
		{
			configList = new ArrayList<HashMap<String, String>>();

			Object val = formPrintConfigDtlMap.get(formName);
			if (val instanceof String)
			{
				HashMap<String, String> paramMap = getRequestParamMapFromQueryString(val.toString());
				configList.add(paramMap);
			}
			else if (val instanceof List<?>)
			{
				@SuppressWarnings("unchecked")
				List<String> queryStringList = (List<String>) val;
				if (queryStringList != null)
				{
					for (String queryString : queryStringList)
					{
						HashMap<String, String> paramMap = getRequestParamMapFromQueryString(queryString);
						configList.add(paramMap);
					}
				}
			}
		}
		return configList;
	}

	private HashMap<String, String> getRequestParamMapFromQueryString(String queryString)
	{
		HashMap<String, String> paramsMap = null;

		if (queryString != null && queryString.trim().length() > 0)
		{
			paramsMap = new LinkedHashMap<String, String>();
			String[] paramsArr = queryString.split("&");
			if (paramsArr != null)
			{
				String[] paramNameValArr = null;
				int index = 0;
				for (String param : paramsArr)
				{
					paramNameValArr = param.split("=");
					String paramName = "", paramVal = "";
					if (paramNameValArr != null)
					{
						paramName = paramNameValArr[0];
						paramVal = paramNameValArr[1];

						if(paramsMap.get(paramName)!=null)
						{
							paramName = paramName+"#"+index;
						}
						paramsMap.put(paramName.trim(), paramVal.trim());
					}
					index++;
				}
			}
		}

		return paramsMap;
	}

	private Properties getFormData(int demographicNo, int formId, String formClass) throws Exception
	{
		FrmRecordFactory recorder = new FrmRecordFactory();
		FrmRecord rec = recorder.factory(formClass);
		Properties props = rec.getFormRecord(demographicNo, formId);
		return props;
	}

	private String getPDFFileName(EctFormData.PatientForm patientForm, Demographic demographic)
	{
		String fileName = "";

		if (patientForm != null && demographic != null)
		{
			String demographicNo = patientForm.getDemoNo();
			String demoFirstName = demographic.getFirstName();
			String demoLastName = demographic.getLastName();
			String formName = patientForm.getFormName();
			//String formId = patientForm.getFormId();

			if (formName != null)
				formName = formName.replaceAll("[\t ]+", "_");

			fileName = demographicNo + "_" + demoFirstName + "_" + demoLastName + "_formExport_" + formName + ".pdf";
		}

		return fileName;
	}

	private byte[] sendPostRequest(String requestUrl, Map<String, String> postRequestParams) throws IOException
	{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(requestUrl);
		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(3);

		if (postRequestParams != null && postRequestParams.size() > 0)
		{
			Set<String> keySet = postRequestParams.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext())
			{
				String key = iterator.next();
				String val = postRequestParams.get(key);
				
				if(key.indexOf("#")>0)
					key = key.substring(0, key.indexOf("#"));
				nameValuePairs.add(new BasicNameValuePair(key, val));
			}
		}
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpClient.execute(httppost);

		byte[] pdfData = null;
		if (response != null)
		{
			pdfData = EntityUtils.toByteArray(response.getEntity());
		}
		return pdfData;
	}

	private ArrayList<HashMap<String, Object>> getDemographicEncounterForms(String demographicNo)
	{
		ArrayList<HashMap<String, Object>> encounterFormDtlList = null;

		EncounterFormDao encounterFormDao = (EncounterFormDao) SpringUtils.getBean("encounterFormDao");
		List<EncounterForm> encounterForms = encounterFormDao.findAll();

		if (encounterForms != null)
		{
			encounterFormDtlList = new ArrayList<HashMap<String, Object>>();

			for (EncounterForm encounterForm : encounterForms)
			{
				String table = encounterForm.getFormTable();
				String formName = encounterForm.getFormName();
				if (table != null && table.trim().length() > 0)
				{
					new EctFormData();
					EctFormData.PatientForm[] pforms = EctFormData.getPatientFormsFromLocalAndRemote(demographicNo, table);
					if (pforms.length > 0)
					{
						HashMap<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("FORM_TABLE", table);

						pforms[0].formName = formName;
						dataMap.put("PATIENT_FORM_OBJ", pforms[0]);

						encounterFormDtlList.add(dataMap);
					}
				}
			}
		}

		return encounterFormDtlList;
	}

}
