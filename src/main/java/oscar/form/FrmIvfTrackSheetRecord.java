
package oscar.form;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.oscarehr.util.SpringUtils;
import oscar.form.dao.FormIvfTrackSheetDAO;
import oscar.util.UtilDateUtilities;

public class FrmIvfTrackSheetRecord  extends FrmRecord {
    public Properties getFormRecord(int demographicNo, int existingID)
            throws SQLException {
        Properties props = new Properties();

        FormIvfTrackSheetDAO dao = (FormIvfTrackSheetDAO)SpringUtils.getBean("formIvfTrackSheetDAO");
        if(existingID <= 0) {
			
        	
			List<Map<String,Object>> providerName = null;
			List<Map<String,Object>> patientInfo = null;
			List<Map<String,Object>> partnerInfo = null;
			providerName = dao.getProviderNameByDemographic(demographicNo);
			patientInfo = dao.findInfoByDemographic(demographicNo);
			partnerInfo = dao.getPartnerInfoByDemographic(demographicNo);
			
        	


            if(patientInfo.size() > 0){
            	java.util.Date dob = UtilDateUtilities.calcDate((String)patientInfo.get(0).get("year"),(String)patientInfo.get(0).get("month"),(String)patientInfo.get(0).get("date"));
            	props.setProperty("demographic_no", patientInfo.get(0).get("demo_no").toString());
                props.setProperty("patientFirstName", patientInfo.get(0).get("fn").toString());
                props.setProperty("patientSurname", patientInfo.get(0).get("ln").toString());
                props.setProperty("formCreated", UtilDateUtilities.DateToString(UtilDateUtilities.Today(), "yyyy/MM/dd"));
                props.setProperty("age", String.valueOf(UtilDateUtilities.calcAge(dob)));
                props.setProperty("dob", (String)patientInfo.get(0).get("year") + "/" + (String)patientInfo.get(0).get("month") + "/" + (String)patientInfo.get(0).get("date") );
                props.setProperty("healthNum", patientInfo.get(0).get("hin").toString() + patientInfo.get(0).get("ver").toString());
				props.setProperty("address", patientInfo.get(0).get("address").toString());
				props.setProperty("city", patientInfo.get(0).get("city").toString());
				props.setProperty("postalCode", patientInfo.get(0).get("postal").toString());
				props.setProperty("phone", patientInfo.get(0).get("phone").toString());
            }

            if(providerName.size() > 0){
            	props.setProperty("physician", providerName.get(0).get("ln") +","+ providerName.get(0).get("fn"));
            }

            if(partnerInfo.size() > 0){
            	props.setProperty("partnerFirstName", (String)partnerInfo.get(0).get("fn").toString());
				props.setProperty("partnerSurname", (String)partnerInfo.get(0).get("ln").toString());
				props.setProperty("partnerdob", (String)partnerInfo.get(0).get("year") + "/" + (String)partnerInfo.get(0).get("month") + "/" + (String)partnerInfo.get(0).get("date"));
				props.setProperty("partnerHealthNum", (String)partnerInfo.get(0).get("hin").toString() + (String)partnerInfo.get(0).get("ver").toString());
            }
        } else {
            String sql = "SELECT * FROM formivftracksheet WHERE demographic_no = " +demographicNo +" AND ID = " +existingID;
			props = (new FrmRecordHelp()).getFormRecord(sql);
        }

        return props;
    }
    
    public int saveFormRecord(Properties props) throws SQLException {
        String demographic_no = props.getProperty("demographic_no");
        String sql = "SELECT * FROM formivftracksheet WHERE demographic_no=" +demographic_no +" AND ID=0";

		return ((new FrmRecordHelp()).saveFormRecord(props, sql));
	}

    public Properties getPrintRecord(int demographicNo, int existingID) throws SQLException  {
        String sql = "SELECT * FROM formivftracksheet WHERE demographic_no = " +demographicNo +" AND ID = " +existingID ;
		return ((new FrmRecordHelp()).getPrintRecord(sql));
    }


    public String findActionValue(String submit) throws SQLException {
 		return ((new FrmRecordHelp()).findActionValue(submit));
    }

    public String createActionURL(String where, String action, String demoId, String formId) throws SQLException {
 		return ((new FrmRecordHelp()).createActionURL(where, action, demoId, formId));
    }

}
