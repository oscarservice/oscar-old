package oscar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.oscarehr.PMmodule.dao.AdmissionDao;
import org.oscarehr.PMmodule.dao.ProgramDao;
import org.oscarehr.PMmodule.dao.ProviderDao;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.dao.DemographicExtDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.Provider;
import org.oscarehr.util.SpringUtils;

import oscar.MyDateFormat;
import oscar.oscarProvider.data.ProviderData;

/**
 * 
 * @author Rohit Prajapati (rohitprajapati54@gmail.com)
 * 
 *         utility to parse sunnby brook hospital's exported demographic txt
 *         file and save demographic and provider dtl into db -- also insert
 *         into admission table
 */
public class SunnyBrookImportDemoUtil
{
	private Logger logger = Logger.getLogger(SunnyBrookImportDemoUtil.class);

	public static final String KEY_PATIENT_CURRENT_HFN = "Current HFN:";
	public static final String KEY_PATIENT_NAME = "Name:";
	public static final String KEY_PATIENT_ADDRESS = "Address:";
	public static final String KEY_PATIENT_APT = "Apt:";
	public static final String KEY_PATIENT_CITY = "City:";
	public static final String KEY_PATIENT_PROV = "Prov:";
	public static final String KEY_PATIENT_POSTAL_CODE = "Postal Code:";
	public static final String KEY_PATIENT_HOME_PHONE = "Home Phon";
	public static final String KEY_PATIENT_SEX = "Sex:";
	public static final String KEY_PATIENT_BIRTH_DATE = "Birthdate:";
	public static final String KEY_PATIENT_BUS_PHONE = "Bus Phone";
	public static final String KEY_PATIENT_EXT = "Ext:";
	public static final String KEY_PATIENT_OHIN_NO = "OHIN No:";
	public static final String KEY_PATIENT_VERS = "Vers:";
	public static final String KEY_PATIENT_CELL_PHONE = "Cell Phone";
	public static final String KEY_PATIENT_SB_MYCHART_EMAIL_ADDR = "SB MyChart Email Addr:";

	public static final String KEY_DOCTOR_NAME = "Sunnybrook Doctor:";
	public static final String KEY_DOCTOR_CLINIC = "Clinic:";
	public static final String KEY_DOCTOR_SERVICE = "Service:";

	private static final List<String> LIST_PATIENT_KEY = new ArrayList<String>();
	private static final List<String> LIST_DOCTOR_KEY = new ArrayList<String>();

	public static final String PROVIDER_TYPE = "doctor";
	public static final String PATIENT_STATUS = "AC";

	static
	{
		init();
	}

	private static void init()
	{		
		LIST_PATIENT_KEY.add(KEY_PATIENT_CURRENT_HFN);
		LIST_PATIENT_KEY.add(KEY_PATIENT_NAME);
		LIST_PATIENT_KEY.add(KEY_PATIENT_ADDRESS);
		LIST_PATIENT_KEY.add(KEY_PATIENT_APT);
		LIST_PATIENT_KEY.add(KEY_PATIENT_CITY);
		LIST_PATIENT_KEY.add(KEY_PATIENT_PROV);
		LIST_PATIENT_KEY.add(KEY_PATIENT_POSTAL_CODE);
		LIST_PATIENT_KEY.add(KEY_PATIENT_HOME_PHONE);
		LIST_PATIENT_KEY.add(KEY_PATIENT_SEX);
		LIST_PATIENT_KEY.add(KEY_PATIENT_BIRTH_DATE);
		LIST_PATIENT_KEY.add(KEY_PATIENT_BUS_PHONE);
		LIST_PATIENT_KEY.add(KEY_PATIENT_EXT);
		LIST_PATIENT_KEY.add(KEY_PATIENT_OHIN_NO);
		LIST_PATIENT_KEY.add(KEY_PATIENT_VERS);
		LIST_PATIENT_KEY.add(KEY_PATIENT_CELL_PHONE);
		LIST_PATIENT_KEY.add(KEY_PATIENT_SB_MYCHART_EMAIL_ADDR);

		LIST_DOCTOR_KEY.add(KEY_DOCTOR_NAME);
		LIST_DOCTOR_KEY.add(KEY_DOCTOR_CLINIC);
		LIST_DOCTOR_KEY.add(KEY_DOCTOR_SERVICE);
	}

	public static void main(String[] args)
	{
		try
		{
			SunnyBrookImportDemoUtil util = new SunnyBrookImportDemoUtil();

			// InputStream in = util.getFileInputStream();
			/*
			 * String str = util.getLines(in, 24, 25);
			 * logger.info("str = "+str);
			 */

			File file = util.getFile();
			util.parseFile(file);

		}
		catch (Exception e)
		{
		}
	}

	private File getFile()
	{
		File file = new File(getFilePath());
		return file;
	}

	private String getFilePath()
	{
		String filePath = "E:/oscarservice/other_files/sunny brook/requirement/exportDemographicsFromSunnyBrook.txt";
		return filePath;
	}

	private InputStream getFileInputStream() throws FileNotFoundException
	{
		InputStream in = null;

		String path = getFilePath();
		in = new FileInputStream(path);

		return in;
	}

	public void updateDemo(Demographic demoFromFile, Demographic demoFromDB) throws Exception
	{
		class NullAwareBeanUtilsBean extends BeanUtilsBean
		{

			@Override
			public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException
			{
				if(value==null || value.toString().trim().length()==0)
					return;
				super.copyProperty(bean, name, value);
			}
			
		}
		new NullAwareBeanUtilsBean().copyProperties(demoFromDB, demoFromFile);
		
		DemographicDao demographicDao = (DemographicDao) SpringUtils.getBean("demographicDao");
		demographicDao.save(demoFromDB);
	}
	
	public Map<String, Object> importFile(File file) throws Exception
	{
		Map<String, Map<String, String>> dtlMap = parseFile(file);
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Provider provider = null;
		Demographic demographic = null;

		if (dtlMap != null)
		{
			DemographicUtil demographicUtil = new DemographicUtil();
			ProviderUtil providerUtil = new ProviderUtil();

			if (dtlMap.get("patientDtlMap") != null)
			{
				Map<String, String> patientDtlMap = dtlMap.get("patientDtlMap");

				// get Demographic VO from map
				demographic = demographicUtil.prepareDemographicVO(patientDtlMap, null);
				Demographic existingDemographic = demographicUtil.getDemographicFromDB(demographic.getHin());
				if (existingDemographic != null)
				{
					resultMap.put("DEMO_EXISTS", "true");
					resultMap.put("DEMO", existingDemographic);
					resultMap.put("DEMO_TO_BE_IMPORTED", demographic);

					return resultMap;
				}
				else
				{
					// demographic doesnt exists.. save to db

					// get provider no
					if (dtlMap.get("doctorDtlMap") != null)
					{
						Map<String, String> doctorDtlMap = dtlMap.get("doctorDtlMap");

						// get Provider VO from map
						provider = providerUtil.prepareProviderVO(doctorDtlMap);
						Provider existingProvider = providerUtil.getProviderFromDB(provider.getLastName(), provider.getFirstName());
						if (existingProvider != null)
						{
							provider = existingProvider;
						}
						else
						{
							provider = providerUtil.saveProvider(provider);
						}
					}

					if (provider != null)
						demographic.setProviderNo(provider.getProviderNo());

					demographic = demographicUtil.saveDemographic(demographic);
					
					//once generated demographic no.. save extras
					demographicUtil.saveDemographicExt(demographic, patientDtlMap);
					
					resultMap.put("DEMO", demographic);
				}
			}
		}
		return resultMap;
	}

	// considering full name as (lastname, firstname)
	// pass 0 for lastname, 1 for firstname
	public static String getPartName(String fullName, int index)
	{
		String partName = "";

		if (fullName != null)
		{
			String[] arr = fullName.split(",");
			if (arr.length > index)
				partName = arr[index].trim();
		}

		return partName;
	}

	public Map<String, Map<String, String>> parseFile(File file) throws Exception
	{
		logger.info("in SunnyBrookImportDemoUtil : parseFile");
		logger.info("file = " + file);

		Map<String, Map<String, String>> resultMap = null;

		if (file != null)
		{
			resultMap = new HashMap<String, Map<String, String>>();

			// get patient details
			InputStream in = new FileInputStream(file);
			String patientDtlStr = getLines(in, 3, 11);
			Map<String, String> patientDtlMap = getPatientDtl(patientDtlStr);

			logger.info("patientDtlMap = " + patientDtlMap);
			resultMap.put("patientDtlMap", patientDtlMap);

			// get doctor details
			in = new FileInputStream(file);
			String doctorDtlStr = getLines(in, 21, 22);
			Map<String, String> doctorDtlMap = getDoctorDtl(doctorDtlStr);

			logger.info("doctorDtlMap = " + doctorDtlMap);
			resultMap.put("doctorDtlMap", doctorDtlMap);
		}

		return resultMap;
	}

	private Map<String, String> getDoctorDtl(String doctorDtlStr)
	{
		Map<String, String> doctorDtlMap = getDtlMap(doctorDtlStr, LIST_DOCTOR_KEY);
		return doctorDtlMap;
	}

	private Map<String, String> getPatientDtl(String patientDtlStr)
	{
		Map<String, String> patientDtlMap = getDtlMap(patientDtlStr, LIST_PATIENT_KEY);
		return patientDtlMap;
	}

	private Map<String, String> getDtlMap(String dtlStr, List<String> keyList)
	{
		Map<String, String> dtlMap = new HashMap<String, String>();

		if (dtlStr != null)
		{
			dtlStr = dtlStr + "#";

			// replacing each key with #KEY .. means prepending each key with #
			// so that we can identify the value of the previous key
			// value of the previous key will be the chars after that key until
			// # of next key or until end of line
			String key = "";
			for (int i = 0; i < keyList.size(); i++)
			{
				key = keyList.get(i);
				if (dtlStr.indexOf(key) >= 0)
				{
					dtlStr = dtlStr.replace(key, "#" + key);
				}
			}
			// logger.info("modified dtlStr = "+dtlStr);

			// extract values
			// logger.info("------------- key - val ---------------------");
			for (int i = 0; i < keyList.size(); i++)
			{
				key = keyList.get(i);
				String val = getVal(key, dtlStr);

				dtlMap.put(key, val);

				// logger.info(key+" - "+val);
				// logger.info("-----------------------------------------------");
			}
		}

		return dtlMap;
	}

	private String getVal(String key, String sourceStr)
	{
		String val = "";

		int startIndex = 0;
		int endIndex1 = 0;
		int endIndex2 = 0;
		int endIndex = 0;

		if (sourceStr.indexOf(key) >= 0)
		{
			startIndex = sourceStr.indexOf(key) + key.length();
			endIndex1 = sourceStr.indexOf("#", startIndex);
			endIndex2 = sourceStr.indexOf("\n", startIndex);

			endIndex = endIndex1 < endIndex2 ? endIndex1 : endIndex2;

			// logger.info("key = "+key);
			// logger.info("startIndex = "+startIndex);
			// logger.info("endIndex = "+endIndex);

			if (startIndex < 0 || endIndex < 0 || startIndex >= endIndex)
			{
				val = "";
			}
			else
			{
				val = sourceStr.substring(startIndex, endIndex);
				val = val.trim();
			}
		}

		return val;
	}

	/**
	 * read lines from inputstream.. inclusive startLineNo and endLineNo
	 * 
	 * @param in
	 *            - inputstream to read from
	 * @param startLineNo
	 *            - start line no (inclusive)
	 * @param endLineNo
	 *            - end line no (inclusive)
	 * @return
	 * @throws IOException
	 */
	private String getLines(InputStream in, int startLineNo, int endLineNo) throws IOException
	{
		String strRet = "";

		if (in != null && startLineNo > 0 && endLineNo > 0 && endLineNo > startLineNo)
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String str = null;
			int lineCount = 1;
			while ((str = reader.readLine()) != null)
			{
				if (lineCount >= startLineNo && lineCount <= endLineNo)
				{
					strRet = strRet + str + "\n";
				}
				lineCount++;
			}
			reader.close();
		}

		return strRet;
	}
}

class ProviderUtil
{
	public ProviderUtil()
	{
	}

	// save provider to db.
	// this will first check for existing provider too. if it exists this will
	// return that one
	public Provider saveProvider(Provider provider)
	{
		if (provider != null)
		{
			ProviderDao providerDao = (ProviderDao) SpringUtils.getBean("providerDao");
			providerDao.saveProvider(provider);
		}
		return provider;
	}

	public Provider getProviderFromDB(String lastName, String firstName)
	{
		Provider provider = null;

		ProviderDao providerDao = (ProviderDao) SpringUtils.getBean("providerDao");
		List<Provider> existingProvList = providerDao.getProviderFromFirstLastName(firstName, lastName);
		if (existingProvList != null && existingProvList.size() > 0)
		{
			provider = existingProvList.get(0);
		}

		return provider;
	}

	public Provider prepareProviderVO(Map<String, String> doctorDtlMap)
	{
		Provider provider = null;

		if (doctorDtlMap != null)
		{
			provider = new Provider();

			if (doctorDtlMap.get(SunnyBrookImportDemoUtil.KEY_DOCTOR_NAME) != null)
			{
				String lastName = "", firstName = "";

				String fullName = doctorDtlMap.get(SunnyBrookImportDemoUtil.KEY_DOCTOR_NAME);
				lastName = SunnyBrookImportDemoUtil.getPartName(fullName, 0);
				firstName = SunnyBrookImportDemoUtil.getPartName(fullName, 1);

				provider.setProviderNo(getNextProviderNo());
				provider.setLastName(lastName);
				provider.setFirstName(firstName);

				provider.setProviderType(SunnyBrookImportDemoUtil.PROVIDER_TYPE);
				provider.setSex("");
				provider.setSpecialty("");
			}
		}

		return provider;
	}

	private String getNextProviderNo()
	{
		String providerNo = "";

		ArrayList<Hashtable<String, String>> list = ProviderData.getProviderListOfAllTypes(true);
		ArrayList<Integer> providerList = new ArrayList<Integer>();
		for (Hashtable<String, String> h : list)
		{
			try
			{
				String pn = h.get("providerNo");
				providerList.add(Integer.valueOf(pn));
			}
			catch (Exception alphaProviderNumber)
			{
			}
		}

		for (Integer i = 1; i < 1000000; i++)
		{
			if (!providerList.contains(i))
			{
				providerNo = i.toString();
				break;
			}
		}

		return providerNo;
	}
}

class DemographicUtil
{
	private static String OSCAR_PROG_ID = null;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public DemographicUtil()
	{
	}

	public Demographic saveDemographic(Demographic demographic) throws Exception
	{
		if (demographic != null)
		{
			DemographicDao demographicDao = (DemographicDao) SpringUtils.getBean("demographicDao");
			demographicDao.save(demographic);

			Admission admission = getAdmissionVO(demographic);
			insertIntoAdmission(admission);
		}

		return demographic;
	}

	public void saveDemographicExt(Demographic demographic, Map<String, String> demoDtlMap)
	{
		if(demographic!=null && demoDtlMap!=null)
		{
			String ext = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_EXT);
			if (!isEmpty(ext))
			{
				DemographicExtDao demographicExtDao = SpringUtils.getBean(DemographicExtDao.class);
				demographicExtDao.saveDemographicExt(demographic.getDemographicNo(), "wPhoneExt", ext);
			}
		}
	}
	
	public Demographic getDemographicFromDB(String hin)
	{
		Demographic demographic = null;
		DemographicDao demographicDao = (DemographicDao) SpringUtils.getBean("demographicDao");

		List<Demographic> existingDemoList = demographicDao.getDemographicsByHealthNum(hin);
		if (existingDemoList != null && existingDemoList.size() > 0)
		{
			demographic = existingDemoList.get(0);
		}

		return demographic;
	}

	public Demographic prepareDemographicVO(Map<String, String> demoDtlMap, String providerNo)
	{
		Demographic demographic = null;

		if (demoDtlMap != null)
		{
			demographic = new Demographic();

			String chartNo = "";
			String lastName = "", firstName = "";
			String address = "", province = "", postal = "", city = "";
			String phone = "", phone2 = "";
			
			String sex = "", dob = "";
			String hin = "", ver = "";

			String fullName = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_NAME);
			lastName = SunnyBrookImportDemoUtil.getPartName(fullName, 0);
			firstName = SunnyBrookImportDemoUtil.getPartName(fullName, 1);

			demographic.setLastName(lastName);
			demographic.setFirstName(firstName);

			if (!isEmpty(providerNo))
				demographic.setProviderNo(providerNo);

			chartNo = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_CURRENT_HFN);
			if(chartNo!=null)
				chartNo = chartNo.replaceAll("[\t ]+", "");
			else
				chartNo = "";
			demographic.setChartNo(chartNo);
			
			address = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_ADDRESS);
			if (!isEmpty(address))
			{
				String apt = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_APT);
				if (!isEmpty(apt))
					address = "Apt: "+apt+", "+address;
				demographic.setAddress(address);
			}
			else
				demographic.setAddress("");

			province = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_PROV);
			if (!isEmpty(province))
			{
				if(province.equalsIgnoreCase("ONT"))
					province = "ON";
				demographic.setProvince(province);
			}
			else
				demographic.setProvince("");

			postal = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_POSTAL_CODE);
			postal = trim(postal);
			demographic.setPostal(postal);

			city = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_CITY);
			city = trim(city);
			demographic.setCity(city);

			phone = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_HOME_PHONE);
			String tempPhone = phone.replaceAll("[^\\d]", "");
			if (!isEmpty(tempPhone))
				demographic.setPhone(phone);
			else
				demographic.setPhone("");

			sex = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_SEX);
			sex = trim(sex);
			demographic.setSex(sex);

			// pending
			dob = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_BIRTH_DATE);
			if (!isEmpty(dob))
			{
				try
				{
					dob = "1"+dob;
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateFormat.parse(dob));
					demographic.setBirthDay(cal);
				}
				catch (ParseException e)
				{
				}
			}

			hin = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_OHIN_NO);
			hin = trim(hin);
			demographic.setHin(hin);

			ver = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_VERS);
			ver = trim(ver);
			demographic.setVer(ver);

			phone2 = demoDtlMap.get(SunnyBrookImportDemoUtil.KEY_PATIENT_BUS_PHONE);
			String tempPhone2 = phone2.replaceAll("[^\\d]", "");
			if (!isEmpty(tempPhone2))
				demographic.setPhone2(phone2);
			else
				demographic.setPhone2("");

			demographic.setPatientStatus(SunnyBrookImportDemoUtil.PATIENT_STATUS);
			
			demographic.setTitle("");
			demographic.setOfficialLanguage("");
			demographic.setSpokenLanguage("");
			demographic.setRosterStatus("");
			demographic.setHcType("ON");
			demographic.setSin("");
		}

		return demographic;
	}

	private boolean isEmpty(String str)
	{
		return (str == null || str.trim().length() == 0);
	}
	
	private String trim(String str)
	{
		if(str==null)
			str = "";
		else
			str = str.trim();
		
		return str;
	}

	private Admission getAdmissionVO(Demographic demographic) throws Exception
	{
		Admission admission = null;

		if (demographic != null)
		{
			admission = new Admission();

			String progId = getOscarProgramId();
			String admissionDate = "";

			GregorianCalendar cal = new GregorianCalendar();
			admissionDate = "" + cal.get(GregorianCalendar.YEAR) + '-' + (cal.get(GregorianCalendar.MONTH) + 1) + '-' + cal.get(GregorianCalendar.DAY_OF_MONTH);

			admission.setClientId(demographic.getDemographicNo());
			admission.setProgramId(Integer.parseInt(progId));
			admission.setProviderNo(demographic.getProviderNo());
			admission.setAdmissionDate(MyDateFormat.getSysDate(admissionDate));
			admission.setAdmissionStatus("current");
			admission.setTeamId(0);
			admission.setTemporaryAdmission(false);
			admission.setAdmissionFromTransfer(false);
			admission.setDischargeFromTransfer(false);
			admission.setRadioDischargeReason("0");
			admission.setClientStatusId(0);
		}

		return admission;
	}

	private static String getOscarProgramId() throws Exception
	{
		if (OSCAR_PROG_ID == null || OSCAR_PROG_ID.trim().length() == 0)
		{
			String oscarProgName = "OSCAR";

			ProgramDao programDao = (ProgramDao) SpringUtils.getBean("programDao");
			OSCAR_PROG_ID = programDao.getProgramIdByProgramName(oscarProgName) + "";
		}

		return OSCAR_PROG_ID;
	}

	private void insertIntoAdmission(Admission admission)
	{
		if (admission != null)
		{
			AdmissionDao admissionDao = (AdmissionDao) SpringUtils.getBean("admissionDao");
			admissionDao.saveAdmission(admission);
		}
	}
}
