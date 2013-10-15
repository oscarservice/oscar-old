/**
 *
 * Copyright (c) 2005-2012. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved.
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
 * This software was written for
 * Centre for Research on Inner City Health, St. Michael's Hospital,
 * Toronto, Ontario, Canada
 */


package org.oscarehr.casemgmt.service;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.oscarehr.PMmodule.dao.ProviderDao;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.common.dao.DemographicCustDao;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.dao.DemographicExtDao;
import org.oscarehr.common.model.Allergy;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.DemographicCust;
import org.oscarehr.util.SpringUtils;

import oscar.OscarProperties;
import oscar.SxmlMisc;
import oscar.oscarClinic.ClinicData;
import oscar.oscarDemographic.data.DemographicRelationship;
import oscar.oscarRx.data.RxPatientData;
import oscar.util.DateUtils;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 *
 * @author rjonasz
 */
public class CaseManagementPrintPdf {

    private HttpServletRequest request;
    private OutputStream os;

    private float upperYcoord;
    private Document document;
    private PdfContentByte cb;
    private BaseFont bf;
    private Font font;
    private boolean newPage = false;

    private SimpleDateFormat formatter;

    public final int LINESPACING = 1;
    public final float LEADING = 12;
    public final float FONTSIZE = 10;
    public final int NUMCOLS = 2;

    private Map<String, String> demoDtl;
    public CaseManagementPrintPdf(Map<String, String> demoDtl, OutputStream os) {
    	this((HttpServletRequest) null, os);
    	this.demoDtl = demoDtl;    	
    }
    
    /** Creates a new instance of CaseManagementPrintPdf */
    public CaseManagementPrintPdf(HttpServletRequest request, OutputStream os) {
        this.request = request;
        this.os = os;
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
    }

    public HttpServletRequest getRequest() {
    	return request;
    }

    public OutputStream getOutputStream() {
    	return os;
    }

    public Font getFont() {
    	return font;
    }
    public SimpleDateFormat getFormatter() {
    	return formatter;
    }

    public Document getDocument() {
    	return document;
    }

    public boolean getNewPage() {
    	return newPage;
    }
    public void setNewPage(boolean b) {
    	this.newPage = b;
    }

    public BaseFont getBaseFont() {
    	return bf;
    }

    public void printDocHeaderFooter() throws IOException, DocumentException {
        //Create the document we are going to write to
        document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,os);
        writer.setPageEvent(new EndPage());
        document.setPageSize(PageSize.LETTER);
        document.open();

        //Create the font we are going to print to
        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        font = new Font(bf, FONTSIZE, Font.NORMAL);

        String title = "", gender = "", dob = "", age = "", mrp = "";
        if(this.demoDtl!=null)
        {
            //set up document title and header
            ResourceBundle propResource = ResourceBundle.getBundle("oscarResources");
            title = propResource.getString("oscarEncounter.pdfPrint.title") + " " + (String)demoDtl.get("demoName") + "\n";
            gender = propResource.getString("oscarEncounter.pdfPrint.gender") + " " + (String)demoDtl.get("demoSex") + "\n";
            dob = propResource.getString("oscarEncounter.pdfPrint.dob") + " " + (String)demoDtl.get("demoDOB") + "\n";
            age = propResource.getString("oscarEncounter.pdfPrint.age") + " " + (String)demoDtl.get("demoAge") + "\n";
            mrp = propResource.getString("oscarEncounter.pdfPrint.mrp") + " " + (String)demoDtl.get("mrp") + "\n";        	
        }
        else
        {
            //set up document title and header
            ResourceBundle propResource = ResourceBundle.getBundle("oscarResources");
            title = propResource.getString("oscarEncounter.pdfPrint.title") + " " + (String)request.getAttribute("demoName") + "\n";
            gender = propResource.getString("oscarEncounter.pdfPrint.gender") + " " + (String)request.getAttribute("demoSex") + "\n";
            dob = propResource.getString("oscarEncounter.pdfPrint.dob") + " " + (String)request.getAttribute("demoDOB") + "\n";
            age = propResource.getString("oscarEncounter.pdfPrint.age") + " " + (String)request.getAttribute("demoAge") + "\n";
            mrp = propResource.getString("oscarEncounter.pdfPrint.mrp") + " " + (String)request.getAttribute("mrp") + "\n";        	
        }

        String[] info = new String[] { title, gender, dob, age, mrp };
        
        ClinicData clinicData = new ClinicData();
        clinicData.refreshClinicData();
        String[] clinic = new String[] {clinicData.getClinicName(), clinicData.getClinicAddress(),
        clinicData.getClinicCity() + ", " + clinicData.getClinicProvince(),
        clinicData.getClinicPostal(), clinicData.getClinicPhone(), "Fax: "+clinicData.getClinicFax()};

        //Header will be printed at top of every page beginning with p2
        Phrase headerPhrase = new Phrase(LEADING, title, font);
        HeaderFooter header = new HeaderFooter(headerPhrase,false);
        header.setAlignment(HeaderFooter.ALIGN_CENTER);
        document.setHeader(header);

        //Write title with top and bottom borders on p1
        cb = writer.getDirectContent();
        cb.setColorStroke(new Color(0,0,0));
        cb.setLineWidth(0.5f);

        cb.moveTo(document.left(), document.top());
        cb.lineTo(document.right(), document.top());
        cb.stroke();
        //cb.setFontAndSize(bf, FONTSIZE);

        upperYcoord = document.top() - (font.getCalculatedLeading(LINESPACING)*2f);

        ColumnText ct = new ColumnText(cb);
        Paragraph p = new Paragraph();
        p.setAlignment(Paragraph.ALIGN_LEFT);
        Phrase phrase = new Phrase();
        Phrase dummy = new Phrase();
        for( int idx = 0; idx < clinic.length; ++idx ) {
            phrase.add(clinic[idx] + "\n");
            dummy.add("\n");
            upperYcoord -= phrase.getLeading();
        }

        dummy.add("\n");
        ct.setSimpleColumn(document.left(), upperYcoord, document.right()/2f, document.top());
        ct.addElement(phrase);
        ct.go();

        p.add(dummy);
        document.add(p);

        //add patient info
        phrase = new Phrase();
        p = new Paragraph();
        p.setAlignment(Paragraph.ALIGN_RIGHT);
        for( int idx = 0; idx < info.length; ++idx ) {
            phrase.add(info[idx]);
        }

        ct.setSimpleColumn(document.right()/2f, upperYcoord, document.right(), document.top());
        p.add(phrase);
        ct.addElement(p);
        ct.go();

        cb.moveTo(document.left(), upperYcoord);
        cb.lineTo(document.right(), upperYcoord);
        cb.stroke();
        upperYcoord -= phrase.getLeading();
        
        if(Boolean.parseBoolean(OscarProperties.getInstance().getProperty("ICFHT_CONVERT_TO_PDF", "false")))
        {
        	printPersonalInfo();
        }
    }

    public void printPersonalInfo() throws DocumentException
    {
    	if(demoDtl!=null && demoDtl.get("DEMO_ID")!=null)
    	{
    		String demoId = demoDtl.get("DEMO_ID").toString();
    		DemographicDao demographicDao = (DemographicDao) SpringUtils.getBean("demographicDao");
    		Demographic demographic = demographicDao.getDemographic(demoId);
    		if(demographic!=null)
    		{
    			ResourceBundle propResource = ResourceBundle.getBundle("oscarResources");
    			
    			List<String> demoFields = new ArrayList<String>();
    			
    			//demographic section
    			demoFields.add("##DEMOGRAPHIC");
    			
    			String name = "Name: "+demographic.getLastName()+", "+demographic.getFirstName();
    			demoFields.add(name);
    			
    			String gender = "Gender: "+demographic.getSex();
    			demoFields.add(gender);
    			
    			String dob = propResource.getString("oscarEncounter.pdfPrint.dob")+" "+demoDtl.get("demoDOB");
    			if(dob!=null && dob.trim().length()>0)
    				demoFields.add(dob);
    			
    			String age = propResource.getString("oscarEncounter.pdfPrint.age")+" "+demographic.getAge();
    			demoFields.add(age);
    			
    			String language = propResource.getString("oscarEncounter.pdfPrint.language")+" "+demographic.getOfficialLanguage();
    			demoFields.add(language);
    			
				DemographicCustDao demographicCustDao = (DemographicCustDao)SpringUtils.getBean("demographicCustDao");
				DemographicCust demographicCust = demographicCustDao.find(Integer.parseInt(demoId));
				
    			//other contacts
    			demoFields.add("##OTHER CONTACTS");
    			
    			DemographicRelationship demoRelation = new DemographicRelationship();
                ArrayList<Hashtable<String, Object>> relList = demoRelation.getDemographicRelationshipsWithNamePhone(demographic.getDemographicNo().toString());
                
				for (int reCounter = 0; reCounter < relList.size(); reCounter++)
				{
					Hashtable<String, Object> relHash = (Hashtable<String, Object>) relList.get(reCounter);
					String sdb = relHash.get("subDecisionMaker") == null?"":((Boolean) relHash.get("subDecisionMaker")).booleanValue()?"/SDM":"";
					String ec = relHash.get("emergencyContact") == null?"":((Boolean) relHash.get("emergencyContact")).booleanValue()?"/EC":"";
					String relation = relHash.get("relation")+sdb+ec+": "+relHash.get("lastName")+", "+relHash.get("firstName")+", "+relHash.get("phone");
					demoFields.add(relation);
				}
				
				//Contact Information
				demoFields.add("##CONTACT INFORMATION");
				
				DemographicExtDao demographicExtDao = SpringUtils.getBean(DemographicExtDao.class);
				Map<String,String> demoExt = demographicExtDao.getAllValuesForDemo(demoId);
				
				String phone = "";
				if(demographic.getPhone()!=null && demographic.getPhone().trim().length()>0)
					phone = demographic.getPhone()+" "+(demoExt.get("hPhoneExt")!=null?demoExt.get("hPhoneExt"):"");					
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.phoneh")+" "+phone);
				
				String phoneW = ""; 
				if(demographic.getPhone2()!=null && demographic.getPhone2().trim().length()>0)
					phoneW = demographic.getPhone2()+" "+demoExt.get("wPhoneExt")!=null?demoExt.get("wPhoneExt"):"";
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.phonew")+" "+phoneW);
				
				String cellPhone = "";
				if(demoExt.get("demo_cell")!=null && demoExt.get("demo_cell").trim().length()>0)
					cellPhone = demoExt.get("demo_cell").trim();					
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.cellphone")+" "+cellPhone);
				
				String address = "";
				if(demographic.getAddress()!=null && demographic.getAddress().trim().length()>0)
					address = demographic.getAddress().trim();					
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.address")+" "+address);
				
				String city = "";
				if(demographic.getCity()!=null && demographic.getCity().trim().length()>0)
					city = demographic.getCity().trim();
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.city")+" "+city);
				
				String province = "";
				if(demographic.getProvince()!=null && demographic.getProvince().trim().length()>0)
					province = demographic.getProvince().trim();
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.province")+" "+province);
			
				String postal = "";
				if(demographic.getPostal()!=null && demographic.getPostal().trim().length()>0)
					postal = demographic.getPostal().trim();
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.postal")+" "+postal);
				
				String email = "";
				if(demographic.getEmail()!=null && demographic.getEmail().trim().length()>0)
					email = demographic.getEmail().trim();
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.email")+" "+email);
				
				String newsletter = "";
				if(demographic.getNewsletter()!=null && demographic.getNewsletter().trim().length()>0)
					newsletter = demographic.getNewsletter().trim();
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.newsletter")+" "+newsletter);
				
				//health insurance
				demoFields.add("##HEALTH INSURANCE");
				
				String hin = demographic.getHin()!=null?demographic.getHin():"";
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.health_ins")+" "+hin);
				
				String hcType = demographic.getHcType()!=null?demographic.getHcType():"";
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.hc_type")+" "+hcType);
				
				String EFFData = demographic.getEffDate()!=null?demographic.getEffDate().toString():"";
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.eff_date")+" "+EFFData);
				
				//clinic status
				demoFields.add("##CLINIC STATUS");
				
				String dateJoined = demographic.getDateJoined()!=null?demographic.getDateJoined().toString():"";
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.date_joined")+" "+dateJoined);
				
				//Patient Clinic Status
				demoFields.add("##PATIENT CLINIC STATUS");
				
				String doctorName = "";
				ProviderDao providerDao = SpringUtils.getBean(ProviderDao.class);
				
				if(demographic.getProviderNo()!=null && demographic.getProviderNo().trim().length()>0)
				{
					doctorName = providerDao.getProviderName(demographic.getProviderNo());
				}
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.doctor")+" "+doctorName);
				
				String nurseName = "";
				if(demographicCust.getResident()!=null && demographicCust.getResident().trim().length()>0)
				{
					nurseName = providerDao.getProviderName(demographicCust.getResident());
				}
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.nurse")+" "+nurseName);
				
				String midwifeName = "";
				if(demographicCust.getMidwife()!=null && demographicCust.getMidwife().trim().length()>0)
				{
					midwifeName = providerDao.getProviderName(demographicCust.getMidwife());
				}
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.midwife")+" "+midwifeName);
				
				String residentName = "";
				if(demographicCust.getNurse()!=null && demographicCust.getNurse().trim().length()>0)
				{
					residentName = providerDao.getProviderName(demographicCust.getNurse());
				}
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.resident")+" "+residentName);
				
				String rd = "";
                String rdohip="";
				String fd=demographic.getFamilyDoctor();
                if (fd!=null) 
                {
                	rd = SxmlMisc.getXmlContent(StringUtils.trimToEmpty(demographic.getFamilyDoctor()),"rd")    ;
                	rd = rd !=null ? rd : "" ;
                	rdohip = SxmlMisc.getXmlContent(StringUtils.trimToEmpty(demographic.getFamilyDoctor()),"rdohip");
                	rdohip = rdohip !=null ? rdohip : "" ;
                }
                
                demoFields.add(propResource.getString("oscarEncounter.pdfPrint.referral_doctor")+" "+rd);
                demoFields.add(propResource.getString("oscarEncounter.pdfPrint.referral_doctor_no")+" "+rdohip);
                
                //RX INTERACTION WARNING LEVEL
                demoFields.add("##");
                String warningLevel = demoExt.get("rxInteractionWarningLevel");
              	if(warningLevel==null) warningLevel="0";
				String warningLevelStr = "Not Specified";
				if(warningLevel.equals("1")) {warningLevelStr="Low";}
				if(warningLevel.equals("2")) {warningLevelStr="Medium";}
				if(warningLevel.equals("3")) {warningLevelStr="High";}
				if(warningLevel.equals("4")) {warningLevelStr="None";}
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.rx_int_warning_level")+" "+warningLevelStr);
                
				String alert = demographicCust.getAlert()!=null?demographicCust.getAlert():" ";
				demoFields.add(propResource.getString("oscarEncounter.pdfPrint.alert")+" "+alert);
				
                Font obsfont = new Font(bf, FONTSIZE, Font.UNDERLINE);
                Paragraph p = new Paragraph();
                p.setAlignment(Paragraph.ALIGN_CENTER);
                Phrase phrase = new Phrase(LEADING, "\n\n", font);
                p.add(phrase);
                phrase = new Phrase(LEADING, "Personal Information", obsfont);
                p.add(phrase);
                document.add(p);

                Font normal = new Font(bf, FONTSIZE, Font.NORMAL);
                Font obsfont2 = new Font(bf, 9, Font.UNDERLINE);
                
                int index = 0;
                int size = demoFields.size();
                for (String personalInfoField : demoFields)
				{
                	p = new Paragraph();
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    if(personalInfoField.startsWith("##"))
                    {
                    	phrase = new Phrase(LEADING, "", obsfont2);
                    	personalInfoField = personalInfoField.replaceFirst("##", "").trim();
                    }
                    else
                    {
                    	phrase = new Phrase(LEADING, "", normal);
                    }
                    
                    if(personalInfoField.trim().length()>0)
                    	phrase.add(personalInfoField);
                    if((index+1)<size && demoFields.get(index+1).startsWith("##"))
                    	phrase.add("\n\n");
                    
                    p.add(phrase);
                    document.add(p);
                    
                    index++;
				}
                
                newPage = true;
    		}
    	}
    }
    
    public void printAllergies(String demoNo) throws DocumentException
    {
    	List<String> allergyList = getAllergyData(demoNo);
    	
    	Font obsfont = new Font(bf, FONTSIZE, Font.UNDERLINE);
        Paragraph p = new Paragraph();
        p.setAlignment(Paragraph.ALIGN_CENTER);
        Phrase phrase = new Phrase(LEADING, "\n\n", font);
        p.add(phrase);
        phrase = new Phrase(LEADING, "Allergies", obsfont);
        p.add(phrase);
        document.add(p);

        Font normal = new Font(bf, FONTSIZE, Font.NORMAL);
        Font obsfont2 = new Font(bf, 9, Font.UNDERLINE);
        
    	if(allergyList!=null && allergyList.size()>0)
    	{
    		p = new Paragraph();
            p.setAlignment(Paragraph.ALIGN_LEFT);
            phrase = new Phrase(LEADING, "", obsfont2);
            phrase.add("DESCRIPTION - SEVERIY - REACTION - DATE\n");
            p.add(phrase);
            document.add(p);
            
    		for (String allergy : allergyList)
			{
    			p = new Paragraph();
                p.setAlignment(Paragraph.ALIGN_LEFT);
                phrase = new Phrase(LEADING, "", normal);
                phrase.add(allergy+"\n");
                p.add(phrase);
                document.add(p);
			}
    		
    		newPage = true;
    	}
    }
    
    private List<String> getAllergyData(String demoNo)
    {
    	List<String> allergies = new ArrayList<String>();
    	
    	Allergy[] allergies1 = RxPatientData.getPatient(demoNo).getActiveAllergies();

		// --- get local allergies ---
		for (int idx = 0; idx < allergies1.length; ++idx) {
			if(allergies1[idx].getArchived().equals("1")) {
				continue;
			}
			Date date = allergies1[idx].getEntryDate();
			String description = allergies1[idx].getDescription() + " - " + allergies1[idx].getSeverityOfReactionDesc() + " - "+ allergies1[idx].getReaction() + " - " + DateUtils.formatDate(date, Locale.getDefault());
			allergies.add(description);
		}
    	
    	return allergies;
    }
    
    public void printRx(String demoNo) throws DocumentException {
        printRx(demoNo,null);
    }
    public void printRx(String demoNo,List<CaseManagementNote> cpp) throws DocumentException {
        if( demoNo == null )
            return;

        if( newPage )
            document.newPage();
        else
            newPage = true;

        Paragraph p = new Paragraph();
        Font obsfont = new Font(bf, FONTSIZE, Font.UNDERLINE);
        Phrase phrase = new Phrase(LEADING, "", obsfont);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        phrase.add("Patient Rx History");
        p.add(phrase);
        document.add(p);

        Font normal = new Font(bf, FONTSIZE, Font.NORMAL);

        oscar.oscarRx.data.RxPrescriptionData prescriptData = new oscar.oscarRx.data.RxPrescriptionData();
        oscar.oscarRx.data.RxPrescriptionData.Prescription [] arr = {};
        arr = prescriptData.getUniquePrescriptionsByPatient(Integer.parseInt(demoNo));


        Font curFont;
        for(int idx = 0; idx < arr.length; ++idx ) {
            oscar.oscarRx.data.RxPrescriptionData.Prescription drug = arr[idx];
            p = new Paragraph();
            p.setAlignment(Paragraph.ALIGN_LEFT);
            if(drug.isCurrent() && !drug.isArchived() ){
                curFont = normal;
                phrase = new Phrase(LEADING, "", curFont);
                phrase.add(formatter.format(drug.getRxDate()) + " - ");
                phrase.add(drug.getFullOutLine().replaceAll(";", " "));
                p.add(phrase);
                document.add(p);
            }
        }

        if (cpp != null ){
            List<CaseManagementNote>notes = cpp;
            if (notes != null && notes.size() > 0){
                p = new Paragraph();
                p.setAlignment(Paragraph.ALIGN_LEFT);
                phrase = new Phrase(LEADING, "\nOther Meds\n", obsfont); //TODO:Needs to be i18n
                p.add(phrase);
                document.add(p);
                newPage = false;
                this.printNotes(notes);
            }

        }
    }

    public void printCPP(HashMap<String,List<CaseManagementNote> >cpp) throws DocumentException {
        if( cpp == null )
            return;

        if( newPage )
            document.newPage();
        else
            newPage = true;

        Font obsfont = new Font(bf, FONTSIZE, Font.UNDERLINE);




        Paragraph p = new Paragraph();
        p.setAlignment(Paragraph.ALIGN_CENTER);
        Phrase phrase = new Phrase(LEADING, "\n\n", font);
        p.add(phrase);
        phrase = new Phrase(LEADING, "Patient CPP", obsfont);
        p.add(phrase);
        document.add(p);
        //upperYcoord -= p.leading() * 2f;
        //lworkingYcoord = rworkingYcoord = upperYcoord;
        //ColumnText ct = new ColumnText(cb);
        String[] headings = {"Social History\n","Other Meds\n", "Medical History\n", "Ongoing Concerns\n", "Reminders\n", "Family History\n", "Risk Factors\n"};
        String[] issueCodes = {"SocHistory","OMeds","MedHistory","Concerns","Reminders","FamHistory","RiskFactors"};
        //String[] content = {cpp.getSocialHistory(), cpp.getFamilyHistory(), cpp.getMedicalHistory(), cpp.getOngoingConcerns(), cpp.getReminders()};

        //init column to left side of page
        //ct.setSimpleColumn(document.left(), document.bottomMargin()+25f, document.right()/2f, lworkingYcoord);

        //int column = 1;
        //Chunk chunk;
        //float bottom = document.bottomMargin()+25f;
        //float middle;
        //bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        //cb.beginText();
        //String headerContd;
        //while there are cpp headings to process

        for( int idx = 0; idx < headings.length; ++idx ) {
            p = new Paragraph();
            p.setAlignment(Paragraph.ALIGN_LEFT);
            phrase = new Phrase(LEADING, headings[idx], obsfont);
            p.add(phrase);
            document.add(p);
            newPage = false;
            this.printNotes(cpp.get(issueCodes[idx]));
        }
            //phrase.add(content[idx]);
            //ct.addText(phrase);

//            //do we need a page break?  check if we're within a fudge factor of the bottom
//            if( lworkingYcoord <= (bottom * 1.1) && rworkingYcoord <= (bottom*1.1) ) {
//                document.newPage();
//                rworkingYcoord = lworkingYcoord = document.top();
//            }
//
//            //Are we in right column?  if so, flip over to left column if there is room
//            if( column % 2 == 1 ) {
//                if( lworkingYcoord > bottom ) {
//                    ct.setSimpleColumn(document.left(), bottom, (document.right()/2f)-10f, lworkingYcoord);
//                    ++column;
//                }
//            }
//            //Are we in left column?  if so, flip over to right column only if text will fit
//            else {
//                ct.setSimpleColumn((document.right()/2f)+10f, bottom, document.right(), rworkingYcoord);
//
//                if( ct.go(true) == ColumnText.NO_MORE_COLUMN ) {
//                    ct.setSimpleColumn(document.left(), bottom, (document.right()/2f)-10f, lworkingYcoord);
//                }
//                else {
//                    ct.setYLine(rworkingYcoord);
//                    ++column;
//                }
//
//                //ct.go(true) consumes input so we reload
//                phrase = new Phrase(LEADING, "", font);
//                chunk = new Chunk(headings[idx], obsfont);
//                phrase.add(chunk);
//                phrase.add(content[idx]);
//                ct.setText(phrase);
//            }
//
//            //while there is text to write, fill columns/page break when page full
//            while( ct.go() == ColumnText.NO_MORE_COLUMN ) {
//                if( column % 2 == 0 ) {
//                    lworkingYcoord = bottom;
//                    middle = (document.right()/4f)*3f;
//                    headerContd = headings[idx] + " cont'd";
//                    cb.setFontAndSize(bf, FONTSIZE);
//                    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, headerContd, middle, rworkingYcoord-phrase.leading(), 0f);
//                    //cb.showTextAligned(PdfContentByte.ALIGN_CENTER, headings[idx] + " cont'd", middle, rworkingYcoord, 0f);
//                    rworkingYcoord -= phrase.leading();
//                    ct.setSimpleColumn((document.right()/2f)+10f, bottom, document.right(), rworkingYcoord);
//                }
//                else {
//                    document.newPage();
//                    rworkingYcoord = lworkingYcoord = document.top();
//                    middle = (document.right()/4f);
//                    headerContd = headings[idx] + " cont'd";
//                    cb.setFontAndSize(bf, FONTSIZE);
//                    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, headerContd, middle, lworkingYcoord-phrase.leading(), 0f);
//                    lworkingYcoord -= phrase.leading();
//                    ct.setSimpleColumn(document.left(), bottom, (document.right()/2f)-10f, lworkingYcoord);
//                }
//                ++column;
//            }
//
//            if( column % 2 == 0 )
//                lworkingYcoord -= (ct.getLinesWritten() * ct.getLeading() + (ct.getLeading() * 2f));
//            else
//                rworkingYcoord -= (ct.getLinesWritten() * ct.getLeading() + (ct.getLeading() * 2f));
//        }
//        cb.endText();
    }

    public void printNotes(List<CaseManagementNote>notes) throws DocumentException{

        CaseManagementNote note;
        Font obsfont = new Font(bf, FONTSIZE, Font.UNDERLINE);
        Paragraph p;
        Phrase phrase;
        Chunk chunk;

        if( newPage )
            document.newPage();
        else
            newPage = true;

        //Print notes
        for( int idx = 0; idx < notes.size(); ++idx ) {
            note = notes.get(idx);
            p = new Paragraph();
            //p.setSpacingBefore(font.leading(LINESPACING)*2f);
            phrase = new Phrase(LEADING, "", font);
            chunk = new Chunk("Documentation Date: " + formatter.format(note.getObservation_date()) + "\n", obsfont);
            phrase.add(chunk);
            phrase.add(note.getNote() + "\n\n");
            p.add(phrase);
            document.add(p);
        }
    }

    public void finish() {
        document.close();
    }

    /*
     *Used to print footers on each page
     */
    class EndPage extends PdfPageEventHelper {
        private Date now;
        private String promoTxt;

        public EndPage() {
            now = new Date();
            promoTxt = OscarProperties.getInstance().getProperty("FORMS_PROMOTEXT");
            if( promoTxt == null ) {
                promoTxt = "";
            }
        }

        public void onEndPage( PdfWriter writer, Document document ) {
            //Footer contains page numbers and date printed on all pages
            PdfContentByte cb = writer.getDirectContent();
            cb.saveState();

            String strFooter = promoTxt + " " + formatter.format(now);

            float textBase = document.bottom();
            cb.beginText();
            cb.setFontAndSize(font.getBaseFont(),FONTSIZE);
            Rectangle page = document.getPageSize();
            float width = page.getWidth();

            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strFooter, (width/2.0f), textBase - 20, 0);

            strFooter = "-" + writer.getPageNumber() + "-";
            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strFooter, (width/2.0f), textBase-10, 0);

            cb.endText();
            cb.restoreState();
        }
    }


}
