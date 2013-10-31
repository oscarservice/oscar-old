package oscar.oscarLab.ca.all.parsers;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v23.datatype.CX;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_RESPONSE;
import ca.uhn.hl7v2.model.v23.message.ORU_R01;
import ca.uhn.hl7v2.model.v23.segment.OBR;
import ca.uhn.hl7v2.model.v23.segment.OBX;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;
import ca.uhn.hl7v2.validation.impl.NoValidation;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.oscarehr.common.dao.Hl7TextInfoDao;
import org.oscarehr.common.model.Hl7TextMessageInfo;
import org.oscarehr.util.SpringUtils;
import oscar.util.UtilDateUtilities;

public class TRUENORTHHandler
  implements MessageHandler
{
  Logger logger = Logger.getLogger(TRUENORTHHandler.class);

  private ORU_R01 msg = null;
  private ArrayList<String> headers = null;
  private OBR obrseg = null;
  private OBX obxseg = null;
  private ORU_R01_PATIENT pat_23;

  public void init(String hl7Body)
    throws HL7Exception
  {
    Parser p = new PipeParser();

    this.msg = ((ORU_R01)p.parse(hl7Body.replaceAll("\n", "\r\n")));

    p.setValidationContext(new NoValidation());
    ArrayList labs = getMatchingHL7Labs(hl7Body);

    this.headers = new ArrayList<String>();

    for (int i = 0; i < labs.size(); i++)
    {
      this.msg = ((ORU_R01)p.parse(((String)labs.get(i)).replaceAll("\n", "\r\n")));

      ORU_R01_RESPONSE pat_res = this.msg.getRESPONSE();

      ORU_R01_ORDER_OBSERVATION obsr = pat_res.getORDER_OBSERVATION();

      this.pat_23 = pat_res.getPATIENT();
      this.obrseg = obsr.getOBR();
      this.obxseg = obsr.getOBSERVATION().getOBX();
    }
  }

  private ArrayList<String> getMatchingHL7Labs(String hl7Body)
  {
    Base64 base64 = new Base64();
    ArrayList ret = new ArrayList<String>();
    int monthsBetween = 0;
    Hl7TextInfoDao hl7TextInfoDao = (Hl7TextInfoDao)SpringUtils.getBean("hl7TextInfoDao");
    try
    {
      List<Hl7TextMessageInfo> matchingLabs = hl7TextInfoDao.getMatchingLabs(hl7Body);
      for (Hl7TextMessageInfo l : matchingLabs) {
        Date dateA = UtilDateUtilities.StringToDate(l.labDate_A, "yyyy-MM-dd hh:mm:ss");
        Date dateB = UtilDateUtilities.StringToDate(l.labDate_B, "yyyy-MM-dd hh:mm:ss");
        if (dateA.before(dateB))
          monthsBetween = UtilDateUtilities.getNumMonths(dateA, dateB);
        else {
          monthsBetween = UtilDateUtilities.getNumMonths(dateB, dateA);
        }
        if (monthsBetween < 4) {
          ret.add(new String(base64.decode(l.message.getBytes("UTF-8")), "UTF-8"));
        }
        if (l.lab_no_A == l.lab_no_B)
          break;
      }
    }
    catch (Exception e)
    {
      this.logger.error("Exception in HL7 getMatchingLabs: ", e);
    }

    if (ret.size() == 0)
      ret.add(hl7Body);
    return ret;
  }

  public String getMsgType() {
    return "TRUENORTH";
  }

  public String getMsgDate()
  {
    return formatDateTime(this.msg.getMSH().getDateTimeOfMessage().getTimeOfAnEvent().getValue());
  }

  public String getMsgPriority()
  {
    return "";
  }

  public int getOBRCount()
  {
    return 1;
  }

  public int getOBXCount(int i)
  {
    return 1;
  }

  public String getOBRName(int i)
  {
    String val = "";
    return (val = this.obrseg.getUniversalServiceIdentifier().getText().getValue()) == null ? " " : val;
  }

  public String getTimeStamp(int i, int j)
  {
    try
    {
      String ret = this.obrseg.getObservationDateTime().getTimeOfAnEvent().toString();
      return formatDateTime(getString(ret));
    } catch (Exception e) {
      this.logger.error("Exception retrieving timestamp", e);
    }return "";
  }

  public boolean isOBXAbnormal(int i, int j)
  {
    return false;
  }

  public String getOBXAbnormalFlag(int i, int j)
  {
    return "";
  }

  public String getObservationHeader(int i, int j)
  {
    try
    {
      return getString(this.obrseg.getUniversalServiceIdentifier().getText().getValue());
    }
    catch (Exception e)
    {
      this.logger.error("Exception gettin header", e);
    }

    return "";
  }

  public String getOBXIdentifier(int i, int j)
  {
    return "";
  }

  public String getOBXValueType(int i, int j)
  {
    return "FT";
  }

  public String getOBXName(int i, int j)
  {
    return getString(this.obxseg.getObservationIdentifier().getText().getValue());
  }

  public String getOBXResult(int i, int j)
  {
    String result = "";
    try {
      Terser terser = new Terser(this.msg);
      result = getString(Terser.get(this.obxseg, 5, 0, 1, 1));
    } catch (Exception e) {
      this.logger.error("Exception returning result", e);
    }

    return result;
  }

  public String getOBXReferenceRange(int i, int j)
  {
    return "";
  }

  public String getOBXUnits(int i, int j)
  {
    return "";
  }

  public String getOBXResultStatus(int i, int j)
  {
    return "F";
  }

  public ArrayList<String> getHeaders()
  {
    String header = getString(this.obrseg.getUniversalServiceIdentifier().getText().getValue());
    this.headers.add(header);
    return this.headers;
  }

  public int getOBRCommentCount(int i)
  {
    return 0;
  }

  public String getOBRComment(int i, int j)
  {
    return "";
  }

  public int getOBXCommentCount(int i, int j)
  {
    return 0;
  }

  public String getOBXComment(int i, int j, int k)
  {
    String comment = ""; String newComment = "";
    int len = 80; String concatComment = "";
    try
    {
      Terser terser = new Terser(this.msg);
      comment = Terser.get(this.obxseg, 5, 0, 1, 1);

      this.logger.info("Modified comment =" + newComment);
      return comment;
    }
    catch (Exception e) {
      this.logger.error("getOBRComment error", e);
    }return "";
  }

  public String getPatientName()
  {
    return getFirstName() + " " + getLastName();
  }

  public String getFirstName()
  {
    try {
      return getString(this.pat_23.getPID().getPatientName().getGivenName().getValue());
    } catch (Exception e) {
      this.logger.error("Exception getting firstName of the patient", e);
    }

    return "";
  }

  public String getLastName()
  {
    try {
      return getString(this.pat_23.getPID().getPatientName().getFamilyName().getValue());
    } catch (Exception e) {
      this.logger.error("Exception getting lastName of the patient", e);
    }

    return "";
  }

  public String getDOB()
  {
    try {
      return formatDateTime(getString(this.pat_23.getPID().getDateOfBirth().getTimeOfAnEvent().getValue())).substring(0, 10);
    }
    catch (Exception e) {
      this.logger.error("Exception retrieving DOB", e);
    }
    return "";
  }

  public String getAge()
  {
    String age = "N/A";
    String dob = getDOB();
    try
    {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date date = formatter.parse(dob);
      age = UtilDateUtilities.calcAge(date);
    } catch (ParseException e) {
      this.logger.error("Could not get age", e);
    }

    return age;
  }

  public String getSex()
  {
    try {
      return getString(this.pat_23.getPID().getSex().getValue());
    } catch (Exception e) {
      this.logger.error("Exception getting the sex of the patient", e);
    }

    return "";
  }

  public String getHealthNum()
  {
    try {
      CX patIdList = this.pat_23.getPID().getPatientIDInternalID(0);
      return patIdList.getID().getValue();
    }
    catch (Exception e)
    {
      this.logger.error("ERROR getting the health number for HL7 lab report patient: ", e);
    }

    return "";
  }

  public String getHomePhone()
  {
    return "";
  }

  public String getWorkPhone()
  {
    return "";
  }

  public String getPatientLocation()
  {
    return "True North Imaging";
  }

  public String getServiceDate()
  {
    try {
      return formatDateTime(getString(this.obrseg.getRequestedDateTime().getTimeOfAnEvent().getValue()));
    } catch (Exception e) {
      this.logger.error("Exception retrieving service date", e);
    }
    return "";
  }

  public String getRequestDate(int i)
  {
    return "";
  }

  public String getOrderStatus()
  {
    return "F";
  }

  public int getOBXFinalResultCount()
  {
    return 0;
  }

  public String getClientRef()
  {
    String clientref = "";
    try {
      return this.pat_23.getVISIT().getPV1().getReferringDoctor(0).getIDNumber().getValue();
    }
    catch (Exception e) {
      this.logger.error("Error returning clieng reference number + ", e);
    }

    return "00000000";
  }

  public String getAccessionNum()
  {
    try {
      Terser terser = new Terser(this.msg);
      return terser.get("/.OBR-2(0)-1");
    } catch (Exception e) {
      this.logger.error("Could not return accession num: ", e);
    }
    return "";
  }

  public String getDocName()
  {
    String docFirstName = "";
    try {
      String docLastName = this.pat_23.getVISIT().getPV1().getReferringDoctor(0).getFamilyName().getValue();
      docFirstName = this.pat_23.getVISIT().getPV1().getReferringDoctor(0).getGivenName().getValue();
      return docLastName + "," + docFirstName;
    }
    catch (Exception e) {
      this.logger.error("Could not return doctor names", e);
    }

    return "";
  }

  public String getCCDocs()
  {
    String docNames = "";

    return docNames;
  }

  public ArrayList getDocNums()
  {
    ArrayList nums = new ArrayList<String>();

    return nums;
  }

  public String audit()
  {
    return "";
  }

  public String getFillerOrderNumber()
  {
    return "";
  }

  public String getEncounterId()
  {
    return "";
  }

  public String getRadiologistInfo()
  {
    String docName = "";
    try {
      Terser terser = new Terser(this.msg);
      String givenName = terser.get("/.OBR-32(0)-1-3");
      String familyName = terser.get("/.OBR-32(0)-1-2");
      String num = terser.get("/.OBR-32(0)-1-1");
      if ((givenName != null) && (familyName != null) && (num != null))
        docName = givenName + " " + familyName + " - " + num;
    } catch (Exception e) {
      this.logger.error("Error getting reporting doctor from OBR-32", e);
    }
    return docName;
  }

  public String getNteForOBX(int i, int j)
  {
    return "";
  }

  private String formatDateTime(String plain) {
    String dateFormat = "yyyyMMddHHmmss";
    dateFormat = dateFormat.substring(0, plain.length());
    String stringFormat = "yyyy-MM-dd HH:mm:ss";
    stringFormat = stringFormat.substring(0, stringFormat.lastIndexOf(dateFormat.charAt(dateFormat.length() - 1)) + 1);

    Date date = UtilDateUtilities.StringToDate(plain, dateFormat);
    return UtilDateUtilities.DateToString(date, stringFormat);
  }

  private String getString(String retrieve) {
    if (retrieve != null) {
      return retrieve.trim();
    }
    return "";
  }
}
