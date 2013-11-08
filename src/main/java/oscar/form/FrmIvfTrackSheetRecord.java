
package oscar.form;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import oscar.oscarDB.DBHandler;
import oscar.util.UtilDateUtilities;

public class FrmIvfTrackSheetRecord  extends FrmRecord {
    public Properties getFormRecord(int demographicNo, int existingID)
            throws SQLException {
        Properties props = new Properties();

        if(existingID <= 0) {
			
            String sql = "SELECT demographic_no, last_name, first_name, " +
						 "year_of_birth, month_of_birth, date_of_birth, hin, ver, address, city, postal, phone " +
						 "FROM demographic WHERE demographic_no = " + demographicNo;
            ResultSet rs = DBHandler.GetSQL(sql);

            if(rs.next()) {
                    java.util.Date dob = UtilDateUtilities.calcDate(oscar.Misc.getString(rs, "year_of_birth"), oscar.Misc.getString(rs, "month_of_birth"), oscar.Misc.getString(rs, "date_of_birth"));

                    props.setProperty("demographic_no", oscar.Misc.getString(rs, "demographic_no"));
                    props.setProperty("patientFirstName", oscar.Misc.getString(rs, "first_name"));
                    props.setProperty("patientSurname", oscar.Misc.getString(rs, "last_name"));
                    props.setProperty("formCreated", UtilDateUtilities.DateToString(UtilDateUtilities.Today(), "yyyy/MM/dd"));
                    //props.setProperty("formEdited", UtilDateUtilities.DateToString(UtilDateUtilities.Today(), "yyyy/MM/dd"));
                    props.setProperty("age", String.valueOf(UtilDateUtilities.calcAge(dob)));
                    props.setProperty("dob", oscar.Misc.getString(rs, "year_of_birth") + "/" + oscar.Misc.getString(rs, "month_of_birth") + "/" + oscar.Misc.getString(rs, "date_of_birth") );
                    props.setProperty("healthNum", oscar.Misc.getString(rs, "hin") + oscar.Misc.getString(rs, "ver"));
					props.setProperty("address", oscar.Misc.getString(rs, "address"));
					props.setProperty("city", oscar.Misc.getString(rs, "city"));
					props.setProperty("postalCode", oscar.Misc.getString(rs, "postal"));
					props.setProperty("phone", oscar.Misc.getString(rs, "phone"));
            }
            rs.close();

            String sql1 = "SELECT p.last_name,p.first_name FROM provider p,demographic d WHERE p.provider_no = d.provider_no AND d.demographic_no =" + demographicNo;
			ResultSet rs1 = DBHandler.GetSQL(sql1);
			if(rs1.next()){
				props.setProperty("physician", oscar.Misc.getString(rs1, "last_name") +","+ oscar.Misc.getString(rs1, "first_name"));
			}
			rs1.close();

			String sql2 = "SELECT d.last_name,d.first_name,d.hin,d.ver,d.year_of_birth,d.month_of_birth,d.date_of_birth FROM demographic d,relationships r WHERE r.relation_demographic_no = d.demographic_no AND r.relation = 'Partner' AND r.demographic_no = " +demographicNo + " LIMIT 1";
			ResultSet rs2 = DBHandler.GetSQL(sql2);
			if(rs2.next()){
				props.setProperty("partnerFirstName", oscar.Misc.getString(rs2, "first_name"));
				props.setProperty("partnerSurname", oscar.Misc.getString(rs2, "last_name"));
				props.setProperty("partnerdob", oscar.Misc.getString(rs2, "year_of_birth") + "/" + oscar.Misc.getString(rs2, "month_of_birth") + "/" + oscar.Misc.getString(rs2, "date_of_birth"));
				props.setProperty("partnerHealthNum", oscar.Misc.getString(rs2, "hin") + oscar.Misc.getString(rs2, "ver"));
			}
			rs2.close();
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
