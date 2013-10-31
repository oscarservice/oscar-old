package oscar.oscarLab.ca.all.parsers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.oscarehr.util.MiscUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import oscar.OscarProperties;
import oscar.oscarLab.ca.all.util.Utilities;
import oscar.util.UtilDateUtilities;

public class GAMXRAYHandler
  implements MessageHandler
{
  Logger logger = Logger.getLogger(GAMXRAYHandler.class);
  private ArrayList<String> headers = new ArrayList<String>();
  private Document doc = null;
  private NodeList nodelist;
  private HashMap<String, String> docMap = new HashMap<String, String>();
  private String reportBody = null;
  private String pdfName = null;
  private int pdfPageCount = 0;
  private String fileName = null;
  private String reportID = null;
  private String accessionNumber = null;
  private String bagNumber = null;
  private String serviceDate = null;
  private String transcriptionDate = null;
  private String ApprovedDate = null;
  private String patient_fName = null;
  private String patient_lName = null;
  private String patient_Phone = null;
  private String sex = null;
  private String hin = null;
  private String dob = null;
  private String repDoc_fName = null;
  private String refDoc_fName = null;
  private String repDoc_lName = null;
  private String refDoc_lName = null;
  private String repDoc_provid = null;
  private String refDoc_provid = null;

  public void init(String hl7Body)
  {
    try
    {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler()
      {
        boolean ServiceDate = false;
        boolean AccessionNumber = false;
        boolean TranscriptionDate = false;
        boolean patient_fn = false;
        boolean patient_ln = false;
        boolean patient_sex = false;
        boolean patient_hin = false;
        boolean patient_dob = false;
        boolean patient = false;
        boolean repDoc = false;
        boolean refDoc = false;
        boolean repDoc_fn = false;
        boolean refDoc_fn = false;
        boolean repDoc_ln = false;
        boolean refDoc_ln = false;
        boolean repDoc_id = false;
        boolean refDoc_id = false;
        boolean patient_phone = false;
        boolean visit_reason = false;

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
          if (qName.equalsIgnoreCase("VisitReason")) {
            this.visit_reason = true;
          }
          if (qName.equalsIgnoreCase("ServiceDate")) {
            this.ServiceDate = true;
          }

          if (qName.equalsIgnoreCase("AccessionNumber")) {
            this.AccessionNumber = true;
          }

          if (qName.equalsIgnoreCase("TranscriptionDate")) {
            this.TranscriptionDate = true;
          }

          if (qName.equalsIgnoreCase("PATIENT")) {
            this.patient = true;
          }

          if (qName.equalsIgnoreCase("ReportingDoctor")) {
            this.repDoc = true;
          }

          if (qName.equalsIgnoreCase("ReferralDoctor")) {
            this.refDoc = true;
          }

          if (qName.equalsIgnoreCase("FirstName")) {
            if (this.patient) this.patient_fn = true;
            else if (this.repDoc) this.repDoc_fn = true;
            else if (this.refDoc) this.refDoc_fn = true;
          }

          if (qName.equalsIgnoreCase("LastName")) {
            if (this.patient) this.patient_ln = true;
            else if (this.repDoc) this.repDoc_ln = true;
            else if (this.refDoc) this.refDoc_ln = true;
          }

          if (qName.equalsIgnoreCase("ProviderID")) {
            if (this.repDoc) this.repDoc_id = true;
            else if (this.refDoc) this.refDoc_id = true;
          }

          if (qName.equalsIgnoreCase("GENDER")) {
            this.patient_sex = true;
          }
          if (qName.equalsIgnoreCase("HealthCardNumber")) {
            this.patient_hin = true;
          }
          if (qName.equalsIgnoreCase("BirthDay")) {
            this.patient_dob = true;
          }
          if ((qName.equalsIgnoreCase("Phone")) && 
            (this.patient)) this.patient_phone = true;
        }

        public void endElement(String uri, String localName, String qName)
          throws SAXException
        {
        }

        public void characters(char[] ch, int start, int length)
          throws SAXException
        {
          if (this.ServiceDate)
          {
            GAMXRAYHandler.this.serviceDate = new String(ch, start, length);
            this.ServiceDate = false;
          }

          if (this.AccessionNumber)
          {
            GAMXRAYHandler.this.accessionNumber = new String(ch, start, length);
            this.AccessionNumber = false;
          }

          if (this.TranscriptionDate) {
            GAMXRAYHandler.this.transcriptionDate = new String(ch, start, length);
            this.TranscriptionDate = false;
          }

          if (this.patient) {
            if (this.patient_fn) {
              GAMXRAYHandler.this.patient_fName = new String(ch, start, length); this.patient_fn = false; this.patient = false;
            } else {
              this.patient = true;
            }if (this.patient_ln) { GAMXRAYHandler.this.patient_lName = new String(ch, start, length); this.patient_ln = false; this.patient = false;
            } else {
              this.patient = true;
            }if (this.patient_sex) { GAMXRAYHandler.this.sex = new String(ch, start, length); this.patient_sex = false; this.patient = false;
            } else {
              this.patient = true;
            }if (this.patient_hin) { GAMXRAYHandler.this.hin = new String(ch, start, length); this.patient_hin = false; this.patient = false;
            } else {
              this.patient = true;
            }if (this.patient_dob) { GAMXRAYHandler.this.dob = new String(ch, start, length); this.patient_dob = false; this.patient = false;
            } else {
              this.patient = true;
            }if (this.patient_phone) { GAMXRAYHandler.this.patient_Phone = new String(ch, start, length); this.patient_phone = false; this.patient = false;
            } else {
              this.patient = true;
            }
          }
          if (this.repDoc) {
            if (this.repDoc_fn) { GAMXRAYHandler.this.repDoc_fName = new String(ch, start, length); this.repDoc_fn = false; this.repDoc = false;
            } else {
              this.repDoc = true;
            }if (this.repDoc_ln) { GAMXRAYHandler.this.repDoc_lName = new String(ch, start, length); this.repDoc_ln = false; this.repDoc = false;
            } else {
              this.repDoc = true;
            }if (this.repDoc_id) { GAMXRAYHandler.this.repDoc_provid = new String(ch, start, length); this.repDoc_id = false; this.repDoc = false;
            } else {
              this.repDoc = true;
            }
          }

          if (this.refDoc) {
            if (this.refDoc_fn) { GAMXRAYHandler.this.refDoc_fName = new String(ch, start, length); this.refDoc_fn = false; this.refDoc = false;
            } else {
              this.refDoc = true;
            }if (this.refDoc_ln) { GAMXRAYHandler.this.refDoc_lName = new String(ch, start, length); this.refDoc_ln = false; this.refDoc = false;
            } else {
              this.refDoc = true;
            }if (this.refDoc_id) { GAMXRAYHandler.this.refDoc_provid = new String(ch, start, length); this.refDoc_id = false; this.refDoc = false;
            } else {
              this.refDoc = true;
            }
          }

          if (this.visit_reason) {
            GAMXRAYHandler.this.headers.add(new String(ch, start, length));
            this.visit_reason = false;
          }
        }
      };
      this.reportBody = hl7Body.split("<ReportBody>")[1].split("</ReportBody>")[0];
      String hl7Body_modified = new StringBuilder().append(hl7Body.split("<ReportBody>")[0]).append("</ReportInfo>").toString();
      InputStream inputStream = new ByteArrayInputStream(hl7Body_modified.getBytes("utf-8"));
      Reader reader = new InputStreamReader(inputStream, "UTF-8");

      InputSource is = new InputSource(reader);
      is.setEncoding("UTF-8");

      saxParser.parse(is, handler);
      if ((!this.reportBody.startsWith("<!DOCTYPE html")) && (!this.reportBody.startsWith("<html")))
      {
        String docDir = OscarProperties.getInstance().getProperty("DOCUMENT_DIR");
        Base64 base64 = new Base64();
        byte[] decodedReportBody = MiscUtils.decodeBase64(this.reportBody);
        this.fileName = new StringBuilder().append(this.patient_fName).append("_").append(this.patient_lName).append("_").append(this.serviceDate.length() > 10 ? this.serviceDate.substring(0, 10) : "").append("_").append(getAccessionNum()).toString();
        File file = new File(new StringBuilder().append(docDir).append("/").append(this.fileName).append(".pdf").toString());
        FileOutputStream fop = new FileOutputStream(file);

        fop.write(decodedReportBody);
        fop.flush();
        fop.close();
        this.pdfPageCount = Utilities.createImagesForPdf(docDir, this.fileName);
        this.pdfName = file.getName();
      }
    }
    catch (Exception e) {
      this.logger.error("Error initializing GAMXRAYHandler", e);
    }
  }

  public String getMsgType()
  {
    return "GAMXRAY";
  }

  public String getMsgDate()
  {
    if (this.dob.indexOf("T") != -1)
      return getString(this.serviceDate).replace("T", " ");
    return "";
  }

  public String getMsgPriority()
  {
    return "";
  }

  public int getOBRCount()
  {
    return 0;
  }

  public int getOBXCount(int i)
  {
    return this.pdfPageCount;
  }

  public String getOBRName(int i)
  {
    return "";
  }

  public String getTimeStamp(int i, int j)
  {
    return "";
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
    return "GAM XRAY";
  }

  public String getOBXIdentifier(int i, int j)
  {
    return "";
  }

  public String getOBXValueType(int i, int j)
  {
    return "";
  }

  public String getOBXName(int i, int j)
  {
    return this.fileName;
  }

  public String getOBXResult(int i, int j)
  {
    if (this.pdfName != null) return this.pdfName;

    return this.reportBody;
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
    return "";
  }

  public ArrayList<String> getHeaders()
  {
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
    return "";
  }

  public String getPatientName()
  {
    return getString(new StringBuilder().append(getFirstName()).append(" ").append(getLastName()).toString());
  }

  public String getFirstName()
  {
    return getString(this.patient_fName);
  }

  public String getLastName()
  {
    return getString(this.patient_lName);
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

  public String getDOB()
  {
    if (this.dob.indexOf("T") != -1)
      return getString(this.dob).split("T")[0];
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
    return getString(this.sex);
  }

  public String getHealthNum()
  {
    return getString(this.hin);
  }

  public String getHomePhone()
  {
    return getString(this.patient_Phone);
  }

  public String getWorkPhone()
  {
    return "";
  }

  public String getPatientLocation()
  {
    return "";
  }

  public String getServiceDate()
  {
    return getMsgDate();
  }

  public String getRequestDate(int i)
  {
    if (this.transcriptionDate.indexOf("T") != -1)
      return getString(this.transcriptionDate).replace("T", " ");
    return "";
  }

  public String getOrderStatus()
  {
    return "";
  }

  public int getOBXFinalResultCount()
  {
    return 0;
  }

  public String getClientRef()
  {
    return "";
  }

  public String getAccessionNum()
  {
    return getString(this.accessionNumber);
  }

  public String getDocName()
  {
    return getString(new StringBuilder().append(this.repDoc_fName).append(" ").append(this.repDoc_lName).toString());
  }

  public String getCCDocs()
  {
    return getString(new StringBuilder().append(this.refDoc_fName).append(" ").append(this.refDoc_lName).toString());
  }

  public ArrayList<String> getDocNums()
  {
    ArrayList nums = new ArrayList<String>();
    nums.add(getString(this.repDoc_provid));
    nums.add(getString(this.refDoc_provid));
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
    return "";
  }

  public String getNteForOBX(int i, int j)
  {
    return "";
  }
}
