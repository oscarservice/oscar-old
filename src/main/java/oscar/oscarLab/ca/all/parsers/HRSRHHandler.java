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
import org.oscarehr.util.SpringUtils;
import oscar.util.UtilDateUtilities;

public class HRSRHHandler
  implements MessageHandler
{
  Logger logger = Logger.getLogger(HRSRHHandler.class);
  ORU_R01 msg = null;
  ArrayList<String> headers = null;
  HashMap<OBR, ArrayList<OBX>> obrSegMap = null;
  ArrayList<OBR> obrSegKeySet = null;

  public HRSRHHandler() {
    this.logger.info("NEW HRSRHHandler UPLOAD HANDLER instance just instantiated. ");
  }

  public void init(String hl7Body) throws HL7Exception
  {
    Parser p = new PipeParser();
    p.setValidationContext(new NoValidation());
    this.msg = ((ORU_R01)p.parse(hl7Body.replaceAll("\n", "\r\n")));

    ArrayList labs = getMatchingHRSRHLabs(hl7Body);
    this.headers = new ArrayList<String>();
    this.obrSegMap = new LinkedHashMap<OBR, ArrayList<OBX>>();
    this.obrSegKeySet = new ArrayList<OBR>();

    for (int i = 0; i < labs.size(); i++) {
      this.msg = ((ORU_R01)p.parse(((String)labs.get(i)).replaceAll("\n", "\r\n")));
      int obrCount = this.msg.getRESPONSE().getORDER_OBSERVATIONReps();

      for (int j = 0; j < obrCount; j++)
      {
        OBR obrSeg = this.msg.getRESPONSE().getORDER_OBSERVATION(j).getOBR();
        ArrayList obxSegs = (ArrayList)this.obrSegMap.get(obrSeg);

        if (obxSegs == null) {
          obxSegs = new ArrayList<OBR>();
        }
        int obxCount = this.msg.getRESPONSE().getORDER_OBSERVATION(j).getOBSERVATIONReps();
        for (int k = 0; k < obxCount; k++) {
          obxSegs.add(this.msg.getRESPONSE().getORDER_OBSERVATION(j).getOBSERVATION(k).getOBX());
        }

        this.obrSegMap.put(obrSeg, obxSegs);
        this.obrSegKeySet.add(obrSeg);

        String header = "";

        if ((this.msg.getMSH().getSendingApplication().getNamespaceID().getValue().equals("ITS")) && (getString(((OBR)this.obrSegKeySet.get(i)).getFillerField1().getValue()).equalsIgnoreCase("HER"))) {
          header = getString(Terser.get((Segment)this.obrSegKeySet.get(i), 21, 0, 2, 1)).equals("") ? "Health Records" : getString(Terser.get((Segment)this.obrSegKeySet.get(i), 21, 0, 2, 1));
        }
        else
          header = getString(obrSeg.getUniversalServiceIdentifier().getText().getValue());
        if (!this.headers.contains(header))
          this.headers.add(header);
      }
    }
  }

  private ArrayList<String> getMatchingHRSRHLabs(String hl7Body)
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
    return "HRSRH";
  }

  public String getMsgDate() {
    return formatDateTime(this.msg.getMSH().getDateTimeOfMessage().getTimeOfAnEvent().getValue());
  }

  public String getMsgPriority()
  {
    return "";
  }

  public int getOBRCount()
  {
    return this.obrSegMap.size();
  }

  public int getOBXCount(int i) {
    return ((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).size();
  }

  public String getOBRName(int i) {
    String obrname = "";
    try {
      obrname = getString(((OBR)this.obrSegKeySet.get(i)).getUniversalServiceIdentifier().getText().getValue());

      if ((this.msg.getMSH().getSendingApplication().getNamespaceID().getValue().equals("ITS")) && (obrname.equals(""))) {
        obrname = getString(Terser.get((Segment)this.obrSegKeySet.get(i), 21, 0, 2, 1)).equals("") ? "Health Records" : getString(Terser.get((Segment)this.obrSegKeySet.get(i), 21, 0, 2, 1));
      }
    }
    catch (Exception e)
    {
      this.logger.error("Error getting OBR name: ", e);
    }
    return obrname;
  }

  public String getTimeStamp(int i, int j) {
    try {
      String ret = ((OBR)this.obrSegKeySet.get(i)).getResultsRptStatusChngDateTime().getTimeOfAnEvent().getValue();
      if (ret == null)
        ret = ((OBR)this.obrSegKeySet.get(i)).getObservationDateTime().getTimeOfAnEvent().getValue();
      return formatDateTime(getString(ret));
    } catch (Exception e) {
      this.logger.error("Exception retrieving timestamp", e);
    }return "";
  }

  public boolean isOBXAbnormal(int i, int j)
  {
    String abnormalFlag = getOBXAbnormalFlag(i, j);
    if ((abnormalFlag.equals("")) || (abnormalFlag.equals("N"))) {
      return false;
    }
    return true;
  }

  public String getOBXAbnormalFlag(int i, int j)
  {
    try
    {
      return getString(((OBX)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j)).getAbnormalFlags(0).getValue());
    } catch (Exception e) {
      this.logger.error("Exception retrieving abnormal flag", e);
    }return "";
  }

  public String getObservationHeader(int i, int j)
  {
    String header = "";
    try {
      header = getString(((OBR)this.obrSegKeySet.get(i)).getUniversalServiceIdentifier().getText().getValue());

      if ((this.msg.getMSH().getSendingApplication().getNamespaceID().getValue().equals("ITS")) && (header.equals(""))) {
        header = getString(Terser.get((Segment)this.obrSegKeySet.get(i), 21, 0, 2, 1)).equals("") ? "Health Records" : getString(Terser.get((Segment)this.obrSegKeySet.get(i), 21, 0, 2, 1));
      }
    }
    catch (Exception e)
    {
      this.logger.error("Exception getting header", e);
    }

    return header;
  }

  public String getOBXIdentifier(int i, int j)
  {
    try
    {
      Segment obxSeg = (Segment)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j);
      String ident = getString(Terser.get(obxSeg, 3, 0, 1, 1));
      String subIdent = Terser.get(obxSeg, 3, 0, 1, 2);

      if (subIdent != null) {
        ident = new StringBuilder().append(ident).append("&").append(subIdent).toString();
      }
      this.logger.info(new StringBuilder().append("returning obx identifier: ").append(ident).toString());
      return ident;
    } catch (Exception e) {
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
    try
    {
      OBX obxSeg = (OBX)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j);
      String sendingApp = this.msg.getMSH().getSendingApplication().toString();
      if ((!sendingApp.equals("ITS")) && (!obxSeg.getValueType().getValue().equals("TX")))
        ret = getString(obxSeg.getObservationIdentifier().getText().getValue());
    }
    catch (Exception e) {
      this.logger.error("Error returning OBX name", e);
    }

    return ret;
  }

  public String getOBXResult(int i, int j)
  {
    String result = ""; String newComment = "";
    int len = 80;
    try
    {
      result = getString(Terser.get((Segment)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j), 5, 0, 1, 1));

      if (result.endsWith(".")) {
        result = result.substring(0, result.length() - 1);
      }
      if (result.length() > len) {
        StringBuilder sb = new StringBuilder(result);

        int i1 = 0;
        while ((i1 = sb.indexOf(" ", i1 + len)) != -1) {
          sb.replace(i1, i1 + 1, "<br/>");
        }
        newComment = sb.toString();
        this.logger.info(new StringBuilder().append("Modified comment =").append(newComment).toString());
      } else {
        newComment = result;
        this.logger.info(new StringBuilder().append("Original comment =").append(newComment).toString());
      }
    }
    catch (Exception e) {
      this.logger.error("Exception returning result", e);
    }
    return newComment;
  }

  public String getOBXReferenceRange(int i, int j) {
    String ret = "";
    try
    {
      OBX obxSeg = (OBX)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j);

      if (getOBXUnits(i, j).equals("")) {
        ret = getString(Terser.get(obxSeg, 7, 0, 2, 1));
      }

      if (ret.equals("")) {
        ret = getString(obxSeg.getReferencesRange().getValue());
        if (!ret.equals(""))
        {
          String[] ranges = ret.split("-");
          for (int k = 0; k < ranges.length; k++) {
            if (ranges[k].endsWith(".")) {
              ranges[k] = ranges[k].substring(0, ranges[k].length() - 1);
            }
          }
          if (ranges.length > 1) {
            if ((ranges[0].contains(">")) || (ranges[0].contains("<")))
              ret = new StringBuilder().append(ranges[0]).append("= ").append(ranges[1]).toString();
            else
              ret = new StringBuilder().append(ranges[0]).append(" - ").append(ranges[1]).toString();
          } else if (ranges.length == 1)
            ret = new StringBuilder().append(ranges[0]).append(" -").toString();
        }
      }
    }
    catch (Exception e) {
      this.logger.error("Exception retrieving reception range", e);
    }
    return ret.replaceAll("\\\\\\.br\\\\", "");
  }

  public String getOBXUnits(int i, int j) {
    String ret = "";
    try {
      OBX obxSeg = (OBX)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j);
      ret = getString(obxSeg.getUnits().getIdentifier().getValue());

      if (ret.equals("")) {
        ret = getString(Terser.get(obxSeg, 7, 0, 2, 1));

        if ((ret.contains("-")) || (ret.contains("<")) || (ret.contains(">")) || (ret.contains("NEGATIVE")))
          ret = "";
      }
    } catch (Exception e) {
      this.logger.error("Exception retrieving units", e);
    }
    return ret.replaceAll("\\\\\\.br\\\\", "");
  }

  public String getOBXResultStatus(int i, int j)
  {
    try {
      String ret = getString(((OBX)((ArrayList)this.obrSegMap.get(this.obrSegKeySet.get(i))).get(j)).getObservResultStatus().getValue());
      if (ret != null) return ret; return "";
    } catch (Exception e) {
      this.logger.error("Exception retrieving results status", e);
    }return "";
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
    try
    {
      return getString(this.msg.getMSH().getSendingFacility().getNamespaceID().getValue());
    } catch (Exception e) {
      this.logger.error(new StringBuilder().append("Error getting patient location: ").append(e).toString());
    }return "";
  }

  public String getServiceDate()
  {
    String date = "";
    try {
      date = getString(this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getObservationDateTime().getTimeOfAnEvent().getValue());
      return formatDateTime(date);
    } catch (Exception e) {
      this.logger.error("Exception retrieving service date", e);
    }return "";
  }

  public String getOrderStatus()
  {
    String status = "";
    try {
      status = this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getResultStatus().getValue();
    } catch (Exception e) {
      this.logger.error(new StringBuilder().append("Error getting result status: ").append(e).toString());
    }

    return status;
  }

  public String getClientRef()
  {
    try
    {
      return getString(this.msg.getRESPONSE().getPATIENT().getPID().getPatientIDInternalID(0).getAssigningAuthority().getNamespaceID().getValue());
    } catch (Exception e) {
      this.logger.error("Could not return accession num: ", e);
    }return "";
  }

  public String getAccessionNum()
  {
    return "";
  }

  public String getDocName() {
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

      String givenName = terser.get("/.OBR-28(0)-3");
      String familyName = terser.get("/.OBR-28(0)-2");

      int i = 1;
      while (givenName != null)
      {
        if (i == 1)
          docNames = givenName;
        else {
          docNames = new StringBuilder().append(docNames).append(", ").append(givenName).toString();
        }

        if (familyName != null) {
          docNames = new StringBuilder().append(docNames).append(" ").append(familyName).toString();
        }
        givenName = terser.get(new StringBuilder().append("/.OBR-28(").append(i).append(")-3").toString());
        familyName = terser.get(new StringBuilder().append("/.OBR-28(").append(i).append(")-2").toString());

        i++;
      }

      return docNames;
    }
    catch (Exception e) {
      this.logger.error("Error retrieving cc'd docs from OBR-28: ", e);
    }return "";
  }

  public ArrayList<String> getDocNums()
  {
    String docNum = "";
    ArrayList nums = new ArrayList<String>();
    int i = 0;
    try
    {
      docNum = this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getOrderingProvider(i).getIDNumber().getValue();
      nums.add(docNum);

      Terser terser = new Terser(this.msg);
      String num = terser.get("/.OBR-28(0)-1");
      i = 1;
      while (num != null) {
        if (!num.equals(docNum))
          nums.add(num);
        num = terser.get(new StringBuilder().append("/.OBR-28(").append(i).append(")-1").toString());
        i++;
      }

    }
    catch (Exception e)
    {
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
    String fon = "";
    try {
      fon = this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getFillerOrderNumber().getEntityIdentifier().getValue();
    } catch (Exception e) {
      this.logger.error("Error getting Filler Order Number from: ", e);
    }
    return fon;
  }
  public String getEncounterId() {
    return "";
  }
  public String getRadiologistInfo() {
    return "";
  }
  public String getNteForOBX(int i, int j) {
    String ret = "";
    try
    {
      ret = this.msg.getRESPONSE().getORDER_OBSERVATION(i).getOBSERVATION(j).getNTE().getComment(0).toString();
      if (ret == null) ret = "";
      this.logger.info(new StringBuilder().append("NTE for OBR=").append(i).append(" ,OBX=").append(j).append(" is ").append(ret).toString());
    }
    catch (Exception e) {
      this.logger.error(new StringBuilder().append("Error accessing the NTE segment for OBX").append(j).append(" ").append(e).toString());
    }

    return ret.replaceAll("\\\\\\.br\\\\", "<br />");
  }

  public String getRequestDate(int i)
  {
    String datetime = "";
    try {
      datetime = formatDateTime(getString(this.msg.getRESPONSE().getORDER_OBSERVATION(0).getOBR().getRequestedDateTime().getTimeOfAnEvent().getValue()));
    } catch (Exception e) {
      this.logger.error("Error getting request date: ", e);
    }
    return datetime;
  }
}
