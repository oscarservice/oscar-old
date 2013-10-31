package oscar.oscarLab.ca.all.parsers;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.v23.datatype.XCN;
import ca.uhn.hl7v2.model.v23.message.ORU_R01;
import ca.uhn.hl7v2.model.v23.segment.OBR;
import ca.uhn.hl7v2.model.v23.segment.OBX;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;
import ca.uhn.hl7v2.validation.impl.NoValidation;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.oscarehr.common.dao.Hl7TextInfoDao;
import org.oscarehr.common.model.Hl7TextMessageInfo;
import org.oscarehr.util.MiscUtils;
import org.oscarehr.util.SpringUtils;
import oscar.OscarProperties;
import oscar.oscarLab.ca.all.util.Utilities;
import oscar.util.UtilDateUtilities;

public class KMHHandler
  implements MessageHandler
{
  Logger logger = Logger.getLogger(KMHHandler.class);

  private ORU_R01 msg = null;

  private ArrayList<String> headers = null;
  private HashMap<OBR, ArrayList<OBX>> obrSegMap = null;
  private HashMap<String, HashMap<String, String>> pdfMap = null;
  private ArrayList<OBR> obrSegKeySet = null;
  private HashMap<String, String> pdfInfo = null;
  private final String docDir = OscarProperties.getInstance().getProperty("DOCUMENT_DIR");

  public KMHHandler()
  {
    this.logger.info("NEW KMHHandler UPLOAD HANDLER instance just instantiated. ");
  }

  public void init(String hl7Body) throws HL7Exception
  {
    Parser p = new PipeParser();
    p.setValidationContext(new NoValidation());
    this.msg = ((ORU_R01)p.parse(hl7Body.replaceAll("\n", "\r\n")));

    this.msg = ((ORU_R01)p.parse(hl7Body));

    ArrayList labs = getMatchingKMHlabs(hl7Body);
    this.headers = new ArrayList<String>();
    this.obrSegMap = new LinkedHashMap<OBR, ArrayList<OBX> >();
    this.obrSegKeySet = new ArrayList<OBR>();
    this.pdfMap = new LinkedHashMap<String, HashMap<String,String> >();

    for (int i = 0; i < labs.size(); i++) {
      this.msg = ((ORU_R01)p.parse(((String)labs.get(i)).replaceAll("\n", "\r\n")));
      int obrCount = this.msg.getRESPONSE().getORDER_OBSERVATIONReps();

      for (int j = 0; j < obrCount; j++)
      {
        OBR obrSeg = this.msg.getRESPONSE().getORDER_OBSERVATION(j).getOBR();
        ArrayList obxSegs = (ArrayList)this.obrSegMap.get(obrSeg);

        if (obxSegs == null) {
          obxSegs = new ArrayList<OBX>();
        }
        int obxCount = this.msg.getRESPONSE().getORDER_OBSERVATION(j).getOBSERVATIONReps();
        int pdfPageCount = 0;
        this.pdfInfo = new LinkedHashMap<String,String>();
        this.logger.info(new StringBuilder().append("obxCount = ").append(obxCount).toString());
        for (int k = 0; k < obxCount; k++)
        {
          OBX curObxSeg = this.msg.getRESPONSE().getORDER_OBSERVATION(j).getOBSERVATION(k).getOBX();
          String obxValueType = curObxSeg.getValueType().getValue();
          String result = "";
          try {
            if (obxValueType.equals("ED")) {
              result = getString(Terser.get(curObxSeg, 5, 0, 5, 1));
              byte[] decodedReportBody = MiscUtils.decodeBase64(result);
              String fileName = new StringBuilder().append(getFirstName()).append("_").append(getLastName()).append("_").append(getString(this.msg.getMSH().getDateTimeOfMessage().getTimeOfAnEvent().getValue())).append("_").append(getAccessionNum()).toString();
              File file = new File(new StringBuilder().append(this.docDir).append("/").append(fileName).append(".pdf").toString());
              if (!file.exists()) {
                FileOutputStream fop = new FileOutputStream(file);

                fop.write(decodedReportBody);
                fop.flush();
                fop.close();
              }
              pdfPageCount = Utilities.createImagesForPdf(this.docDir, fileName);
              result = file.getName();
            } else if (obxValueType.equals("RP")) {
              result = getString(Terser.get(curObxSeg, 5, 0, 1, 1));
              pdfPageCount = Utilities.createImagesForPdf(this.docDir, result);
            }
            this.pdfInfo.put("num_pages", new Integer(pdfPageCount).toString());
            this.pdfInfo.put("file", result);

            this.pdfMap.put(getString(this.msg.getMSH().getDateTimeOfMessage().getTimeOfAnEvent().getValue()), this.pdfInfo);
          } catch (Exception e) {
            this.logger.error("Error getting pdf info from OBX-5", e);
          }
          obxSegs.add(curObxSeg);
        }

        this.obrSegMap.put(obrSeg, obxSegs);
        this.obrSegKeySet.add(obrSeg);

        String header = getString(obrSeg.getUniversalServiceIdentifier().getText().getValue());
        this.logger.info(new StringBuilder().append("KMH header =").append(header).toString());
        if (!this.headers.contains(header))
          this.headers.add(header);
      }
    }
  }

  private ArrayList<String> getMatchingKMHlabs(String hl7Body)
  {
    Base64 base64 = new Base64();
    ArrayList ret = new ArrayList<String>();
    int monthsBetween = 0;
    Hl7TextInfoDao hl7TextInfoDao = (Hl7TextInfoDao)SpringUtils.getBean("hl7TextInfoDao");
    try {
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
    return "KMH";
  }

  public String getMsgDate() {
    return formatDateTime(this.msg.getMSH().getDateTimeOfMessage().getTimeOfAnEvent().getValue());
  }

  public String getMsgPriority() {
    String priority = "R";
    for (int i = 0; i < getOBRCount(); i++) {
      try {
        priority = getString(this.msg.getRESPONSE().getORDER_OBSERVATION(i).getOBR().getQuantityTiming().getPriority().getValue());
      } catch (Exception e) {
        this.logger.error("Error finding priority", e);
      }
    }

    return priority;
  }

  public int getOBRCount()
  {
    return this.obrSegMap.size();
  }

  public int getOBXCount(int i) {
    return ((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).size();
  }

  public String getOBRName(int i) {
    return ((OBR)this.obrSegKeySet.get(i)).getUniversalServiceIdentifier().getText().getValue();
  }

  public String getTimeStamp(int i, int j)
  {
    try
    {
      String ret = ((OBR)this.obrSegKeySet.get(i)).getObservationDateTime().getTimeOfAnEvent().getValue();
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
    try {
      return getString(((OBR)this.obrSegKeySet.get(i)).getUniversalServiceIdentifier().getText().getValue());
    } catch (Exception e) {
      this.logger.error("Exception getting header", e);
    }return "";
  }

  public String getOBXIdentifier(int i, int j)
  {
    try
    {
      Segment obxSeg = (Segment)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j);
      return getString(Terser.get(obxSeg, 3, 0, 1, 1));
    }
    catch (Exception e) {
      this.logger.error("error returning obx identifier", e);
    }return "";
  }

  public String getOBXValueType(int i, int j)
  {
    String ret = "";
    try {
      OBX obxSeg = (OBX)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j);
      ret = obxSeg.getValueType().getValue();
    } catch (Exception e) {
      this.logger.error("Error returning OBX name", e);
    }

    return ret;
  }

  public String getOBXName(int i, int j) {
    String ret = "";
    if (this.pdfMap.containsKey(getString(this.msg.getMSH().getDateTimeOfMessage().getTimeOfAnEvent().getValue()))) {
      ret = (String)((HashMap)this.pdfMap.get(getString(this.msg.getMSH().getDateTimeOfMessage().getTimeOfAnEvent().getValue()))).get("file");
    }
    return ret;
  }

  public String getOBXResult(int i, int j)
  {
    String result = ""; String newComment = "";

    int len = 80;
    try
    {
      String obxValueType = getOBXValueType(i, j);
      result = getString(Terser.get((Segment)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j), 5, 0, 1, 1));

      if (obxValueType.equals("TX"))
      {
        if (result.endsWith(".")) {
          result = result.substring(0, result.length() - 1);
        }
        result = result.replaceAll("\\\\\\.br\\\\", "<br />");

        String[] resultarray = result.split("<br />");

        for (int ind = 0; ind < resultarray.length; ind++) {
          if (resultarray[ind].length() > len) {
            StringBuilder sb = new StringBuilder(resultarray[ind]);

            int i1 = 0;
            while ((i1 = sb.indexOf(" ", i1 + len)) != -1) {
              sb.replace(i1, i1 + 1, "<br/>");
            }
            newComment = new StringBuilder().append(newComment).append(sb.toString()).append("<br/>").toString();
          } else {
            newComment = new StringBuilder().append(newComment).append(resultarray[ind]).append("<br/>").toString();
          }
        }
        return newComment;
      }
    }
    catch (Exception e) {
      this.logger.error("Exception returning result", e);
    }
    return "";
  }

  public String getOBXReferenceRange(int i, int j)
  {
    String ret = "";
    return ret;
  }

  public String getOBXUnits(int i, int j)
  {
    String ret = "";
    return ret;
  }

  public String getOBXResultStatus(int i, int j)
  {
    return "F";
  }

  public int getOBXFinalResultCount()
  {
    return 0;
  }

  public ArrayList<String> getHeaders()
  {
    return this.headers;
  }

  public int getOBRCommentCount(int i)
  {
    int count = 0;
    return count;
  }

  public String getOBRComment(int i, int j)
  {
    String comment = "";
    return comment;
  }

  public String getNteForOBX(int i, int j)
  {
    String ret = "";
    return ret;
  }

  public int getOBXCommentCount(int i, int j)
  {
    int count = 0;
    return count;
  }

  public String getOBXComment(int i, int j, int k) {
    String comment = "";
    return comment;
  }

  public String getPatientName()
  {
    return new StringBuilder().append(getFirstName()).append(" ").append(getLastName()).toString();
  }

  public String getFirstName() {
    return getString(this.msg.getRESPONSE().getPATIENT().getPID().getPatientName().getGivenName().getValue());
  }

  public String getLastName() {
    return getString(this.msg.getRESPONSE().getPATIENT().getPID().getPatientName().getFamilyName().getValue());
  }

  public String getDOB() {
    try {
      return formatDateTime(getString(this.msg.getRESPONSE().getPATIENT().getPID().getDateOfBirth().getTimeOfAnEvent().getValue())).substring(0, 10);
    } catch (Exception e) {
      this.logger.error("Exception retrieving DOB", e);
    }return "";
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

  public String getSex() {
    return getString(this.msg.getRESPONSE().getPATIENT().getPID().getSex().getValue());
  }

  public String getHealthNum() {
    return getString(this.msg.getRESPONSE().getPATIENT().getPID().getPatientIDExternalID().getID().getValue());
  }

  public String getHomePhone() {
    String phone = "";
    int i = 0;
    try {
      while (!getString(this.msg.getRESPONSE().getPATIENT().getPID().getPhoneNumberHome(i).get9999999X99999CAnyText().getValue()).equals("")) {
        if (i == 0)
          phone = getString(this.msg.getRESPONSE().getPATIENT().getPID().getPhoneNumberHome(i).get9999999X99999CAnyText().getValue());
        else {
          phone = new StringBuilder().append(phone).append(", ").append(getString(this.msg.getRESPONSE().getPATIENT().getPID().getPhoneNumberHome(i).get9999999X99999CAnyText().getValue())).toString();
        }
        i++;
      }
      return phone;
    } catch (Exception e) {
      this.logger.error("Could not return phone number", e);
    }
    return "";
  }

  public String getWorkPhone()
  {
    String phone = "";
    int i = 0;
    try {
      while (!getString(this.msg.getRESPONSE().getPATIENT().getPID().getPhoneNumberBusiness(i).get9999999X99999CAnyText().getValue()).equals("")) {
        if (i == 0)
          phone = getString(this.msg.getRESPONSE().getPATIENT().getPID().getPhoneNumberBusiness(i).get9999999X99999CAnyText().getValue());
        else {
          phone = new StringBuilder().append(phone).append(", ").append(getString(this.msg.getRESPONSE().getPATIENT().getPID().getPhoneNumberBusiness(i).get9999999X99999CAnyText().getValue())).toString();
        }
        i++;
      }
      return phone;
    } catch (Exception e) {
      this.logger.error("Could not return phone number", e);
    }
    return "";
  }

  public String getPatientLocation()
  {
    return getString(this.msg.getMSH().getSendingFacility().getNamespaceID().getValue());
  }

  public String getServiceDate() {
    try {
      return formatDateTime(getString(this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getObservationDateTime().getTimeOfAnEvent().getValue()));
    } catch (Exception e) {
      this.logger.error("Exception retrieving service date", e);
    }return "";
  }

  public String getOrderStatus()
  {
    return "F";
  }

  public String getClientRef()
  {
    try {
      return getString(this.msg.getRESPONSE().getPATIENT().getPID().getPatientIDInternalID(0).getAssigningAuthority().getNamespaceID().getValue());
    } catch (Exception e) {
      this.logger.error("Could not return accession num: ", e);
    }return "";
  }

  public String getAccessionNum()
  {
    try {
      return getString(this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getPlacerOrderNumber(0).getEntityIdentifier().getValue());
    }
    catch (Exception e) {
      this.logger.error("Could not return accession num: ", e);
    }return "";
  }

  public String getDocName()
  {
    String docName = "";
    int i = 0;
    try {
      while (!getFullDocName(this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getOrderingProvider(i)).equals("")) {
        if (i == 0)
          docName = getFullDocName(this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getOrderingProvider(i));
        else {
          docName = new StringBuilder().append(docName).append(", ").append(getFullDocName(this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getOrderingProvider(i))).toString();
        }
        i++;
      }
      return docName;
    } catch (Exception e) {
      this.logger.error("Could not return doctor names", e);
    }
    return "";
  }

  public String getCCDocs()
  {
    String docNames = "";
    try
    {
      Terser terser = new Terser(this.msg);

      String givenName = terser.get("/.PV1(0)-7-3");
      String familyName = terser.get("/.PV1(0)-7-2");

      if (givenName != null) {
        docNames = givenName;

        if (familyName != null)
          docNames = new StringBuilder().append(docNames).append(" ").append(familyName).toString();
      }
      givenName = terser.get("/.PV1(0)-8-3");
      familyName = terser.get("/.PV1(0)-8-2");

      if (givenName != null) { docNames = new StringBuilder().append(docNames).append(", ").append(givenName).toString();

        if (familyName == null); }
      return new StringBuilder().append(docNames).append(" ").append(familyName).toString();
    }
    catch (Exception e)
    {
      this.logger.error("Error retrieving Admitting doctor from PV1-7 and Referring doctor from PV1-8");
    }return "";
  }

  public ArrayList<String> getDocNums()
  {
    String docNum = "";
    ArrayList nums = new ArrayList<String>();
    try
    {
      docNum = this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getOrderingProvider(0).getIDNumber().getValue();
      nums.add(docNum);

      Terser terser = new Terser(this.msg);

      nums.add(terser.get("/.PV1(0)-7-1"));
      nums.add(terser.get("/.PV1(0)-8-1"));
    }
    catch (Exception e)
    {
      this.logger.error("Error retrieving Admitting doctor from PV1-7 and Referring doctor from PV1-8");
    }

    return nums;
  }

  public String audit() {
    return "";
  }

  private String getFullDocName(XCN docSeg) {
    String docName = "";

    if (docSeg.getPrefixEgDR().getValue() != null) {
      docName = docSeg.getPrefixEgDR().getValue();
    }
    if (docSeg.getGivenName().getValue() != null) {
      if (docName.equals(""))
        docName = docSeg.getGivenName().getValue();
      else {
        docName = new StringBuilder().append(docName).append(" ").append(docSeg.getGivenName().getValue()).toString();
      }
    }
    if (docSeg.getMiddleInitialOrName().getValue() != null) {
      if (docName.equals(""))
        docName = docSeg.getMiddleInitialOrName().getValue();
      else {
        docName = new StringBuilder().append(docName).append(" ").append(docSeg.getMiddleInitialOrName().getValue()).toString();
      }
    }
    if (docSeg.getFamilyName().getValue() != null) {
      if (docName.equals(""))
        docName = docSeg.getFamilyName().getValue();
      else {
        docName = new StringBuilder().append(docName).append(" ").append(docSeg.getFamilyName().getValue()).toString();
      }
    }
    if (docSeg.getSuffixEgJRorIII().getValue() != null) {
      if (docName.equals(""))
        docName = docSeg.getSuffixEgJRorIII().getValue();
      else
        docName = new StringBuilder().append(docName).append(" ").append(docSeg.getSuffixEgJRorIII().getValue()).toString();
    }
    if (docSeg.getDegreeEgMD().getValue() != null) {
      if (docName.equals(""))
        docName = docSeg.getDegreeEgMD().getValue();
      else {
        docName = new StringBuilder().append(docName).append(" ").append(docSeg.getDegreeEgMD().getValue()).toString();
      }
    }
    return docName;
  }

  private String formatDateTime(String plain)
  {
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

  public String getFillerOrderNumber()
  {
    String ret = "";
    if (this.pdfMap.containsKey(getString(this.msg.getMSH().getDateTimeOfMessage().getTimeOfAnEvent().getValue()))) {
      ret = (String)((HashMap)this.pdfMap.get(getString(this.msg.getMSH().getDateTimeOfMessage().getTimeOfAnEvent().getValue()))).get("num_pages");
    }
    return ret;
  }

  public String getEncounterId() {
    return "";
  }

  public String getRadiologistInfo() {
    return "";
  }
  public String getObservationName(int i, int j) {
    return "";
  }

  public String getRequestDate(int i)
  {
    return getMsgDate();
  }
}
