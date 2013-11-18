package oscar.form;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.DigitalSignature;
import org.oscarehr.util.DigitalSignatureUtils;
import org.oscarehr.util.LoggedInInfo;
import org.oscarehr.util.SpringUtils;

import oscar.util.UtilDateUtilities;

public class FrmAudiologyReportRecord extends FrmRecord
{
	private DemographicDao demographicDao = (DemographicDao) SpringUtils.getBean("demographicDao");

	@Override
	public Properties getFormRecord(int demographicNo, int existingID) throws SQLException
	{
		Properties props = new Properties();

		if (existingID > 0)
		{
			String sql = "SELECT * FROM formAudiologyReport WHERE demographic_no = " + demographicNo + " AND ID = " + existingID;
			props = (new FrmRecordHelp()).getFormRecord(sql);
		}
		else
		{
			props.setProperty("formCreated", UtilDateUtilities.DateToString(UtilDateUtilities.Today(), "yyyy/MM/dd"));
		}

		if (props == null)
			props = new Properties();

		Demographic demographic = demographicDao.getDemographicById(demographicNo);
		if (demographic != null)
		{
			props.setProperty("demographic_no", String.valueOf(demographic.getDemographicNo()));
			props.setProperty("patientName", demographic.getLastName() + ", " + demographic.getFirstName());
			props.setProperty("healthNumber", StringUtils.trimToEmpty(demographic.getHin()));
			props.setProperty("version", StringUtils.trimToEmpty(demographic.getVer()));

			java.util.Date dob = UtilDateUtilities.calcDate(demographic.getYearOfBirth(), demographic.getMonthOfBirth(), demographic.getDateOfBirth());
			props.setProperty("birthDate", StringUtils.trimToEmpty(UtilDateUtilities.DateToString(dob, "yyyy/MM/dd")));

			props.setProperty("phoneNumber", StringUtils.trimToEmpty(demographic.getPhone()));
			props.setProperty("patientAddress", StringUtils.trimToEmpty(demographic.getAddress()));
			String sex = StringUtils.trimToEmpty(demographic.getSex());
			props.setProperty("sex", sex);
			props.setProperty("demoProvider", StringUtils.trimToEmpty(demographic.getProviderNo()));
			props.setProperty("chartNo", StringUtils.trimToEmpty(demographic.getChartNo()));
		}
		return props;
	}

	@Override
	public int saveFormRecord(Properties props) throws SQLException
	{
		handleSignatures(props);
		
		String demographic_no = props.getProperty("demographic_no");
		String sql = "SELECT * FROM formAudiologyReport WHERE demographic_no=" + demographic_no + " AND ID=0";

		return ((new FrmRecordHelp()).saveFormRecord(props, sql));
	}
	
	private void handleSignatures(Properties props)
	{
		if(props!=null)
		{
			String demographic_no = props.getProperty("demographic_no");
			
			boolean newSignature = false;
			if(props.getProperty("newSignature", "false").equalsIgnoreCase("true"))
				newSignature = true;
			
			if (newSignature) {
				//for signature of 'Otolaryngologist'
				
				//fetch signature req id
				String signatureRequestId1 = props.getProperty("otolaryngologist", "");
				
				//if value is new signature id.. means not saved in db
				if(signatureRequestId1!=null && signatureRequestId1.indexOf("_")>0)
				{
					String signatureId = saveSignatureToDB(signatureRequestId1, demographic_no);
					props.put("otolaryngologist", signatureId);
				}
				
				String signatureRequestId2 = props.getProperty("audiologist_reg", "");
				
				if(signatureRequestId2!=null && signatureRequestId2.indexOf("_")>0)
				{
					String signatureId = saveSignatureToDB(signatureRequestId2, demographic_no);
					props.put("audiologist_reg", signatureId);
				}
			}
		}
	}
	
	private String saveSignatureToDB(String signatureRequestId, String demographicNo)
	{
		String signatureId = "";
		
		LoggedInInfo loggedInInfo = LoggedInInfo.loggedInInfo.get();
		DigitalSignature signature = DigitalSignatureUtils.storeDigitalSignatureFromTempFileToDB(loggedInInfo, signatureRequestId, Integer.parseInt(demographicNo));
		if (signature != null) 
		{ 
			signatureId = "" + signature.getId();
		}		
		
		return signatureId;
	}

	@Override
	public String findActionValue(String submit) throws SQLException
	{
		return ((new FrmRecordHelp()).findActionValue(submit));
	}

	@Override
	public String createActionURL(String where, String action, String demoId, String formId) throws SQLException
	{
		return ((new FrmRecordHelp()).createActionURL(where, action, demoId, formId));
	}
}
