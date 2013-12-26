<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@page import="oscar.Misc"%>
<%@page import="oscar.util.UtilMisc"%>
<%@page import="java.util.Enumeration"%>
<%@page import="oscar.oscarEncounter.pageUtil.NavBarDisplayDAO"%>
<%@page	import="java.util.Arrays,java.util.Properties,java.util.List,java.util.Set,java.util.ArrayList,java.util.Enumeration,java.util.HashSet,java.util.Iterator,java.text.SimpleDateFormat,java.util.Calendar,java.util.Date,java.text.ParseException"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="org.oscarehr.common.model.UserProperty,org.oscarehr.casemgmt.model.*,org.oscarehr.casemgmt.service.* "%>
<%@page import="org.oscarehr.casemgmt.web.formbeans.*"%>
<%@page import="org.oscarehr.PMmodule.model.*"%>
<%@page import="org.oscarehr.common.model.*"%>
<%@page import="oscar.util.DateUtils"%>
<%@page import="oscar.dms.EDocUtil"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.caisi.model.Role"%>
<%@page import="org.oscarehr.casemgmt.common.Colour"%>
<%@page import="oscar.dms.EDoc"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.quatro.dao.security.*,com.quatro.model.security.Secrole"%>
<%@page import="org.oscarehr.util.EncounterUtil"%>
<%@page import="org.apache.cxf.common.i18n.UncheckedException"%>
<%@page import="org.oscarehr.casemgmt.web.NoteDisplay"%>
<%@page import="org.oscarehr.casemgmt.web.CaseManagementViewAction"%>
<%@page import="org.oscarehr.util.SpringUtils"%>
<%@page import="oscar.oscarRx.data.RxPrescriptionData"%>
<%@page import="org.oscarehr.casemgmt.dao.CaseManagementNoteLinkDAO"%>
<%@page import="org.oscarehr.common.dao.ProfessionalSpecialistDao"%>
<%@page import="oscar.OscarProperties"%>
<%@page import="org.oscarehr.util.MiscUtils"%>
<%@page import="org.oscarehr.PMmodule.model.Program"%>
<%@page import="org.oscarehr.PMmodule.dao.ProgramDao"%>
<%@page import="org.oscarehr.util.SpringUtils"%>
<%@page import="oscar.util.UtilDateUtilities"%>
<%@page import="org.oscarehr.casemgmt.web.NoteDisplayNonNote"%>
<%@page import="org.oscarehr.common.dao.EncounterTemplateDao"%>
<%@page import="org.oscarehr.casemgmt.web.CheckBoxBean"%>
<%@page import="org.oscarehr.casemgmt.web.NoteDisplay"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>Impression History</title>

 <style>
@page { size: 576px 756px; }
body {size: portrait; width: 876px;  font-family: Arial, Helvetica, sans-serif; font-size: 14px; font-weight: normal; color: #000000; background-color: #FFFFFF; }
img { border: none; }
input [type=text] { border-bottom: black 1px solid; }

.border { padding: 5px 5px 5px 5px; border: black 1px solid; }
.headerinfo { font-family: Arial, Helvetica, sans-serif; font-size: 10px; font-weight: bold; color: #333333; }
.maintitle { font-family: Arial, Helvetica, sans-serif; font-size: 16px; font-weight: bold; color: #333333; border-bottom: 1px solid #000;}
.title { font-family: Arial, Helvetica, sans-serif; font-size: 18px; font-weight: bold; color: #333333; }
.divider { border-bottom: black 1px solid; }
</style>
</head>

<body>
<table width="876" cellpadding="1" cellspacing="4" border="0">
	<tr>
		<td><b>Impression history</b></td>
		<td></td>
		<td align="right"><a href="javascript:void(0)" onclick="window.close(); return false;" style="text-decoration:none">Close</a></td>
	</tr>
	<tr>
	<table class="border" width="100%" cellpadding="0" border="0" cellspacing="0" rules="all">
	<tr bgcolor="#999999">
		<td width="14%" align="center">Date</td>
		<td width="14%" align="center">Doctor</td>
		<td width="72%" align="center">Impression Notes</td>
	</tr>
	<%
		@SuppressWarnings("unchecked")
		long savedId;
		if(session.getAttribute("savedId") != null ){
			savedId = (Long)session.getAttribute("savedId");
		}else{
			savedId = 0;
		}	
		//(Long)session.getAttribute("savedId");
		ArrayList<NoteDisplay> notesToDisplay1 = (ArrayList<NoteDisplay>)session.getAttribute("notesToDisplay1");
		//ArrayList<NoteDisplay> notesToDisplay3 = (ArrayList<NoteDisplay>)session.getAttribute("notesToDisplay3");
		ArrayList<NoteDisplay> notesToDisplay = new ArrayList<NoteDisplay>();
		for(int i=0;i<notesToDisplay1.size();i++){
			NoteDisplay note = notesToDisplay1.get(i);
			if (note.getNoteId()!=null && !"".equals(note.getNoteId()) && note.getNoteId().intValue() == savedId ){
				notesToDisplay.add(note);
				continue;
			}
			
			if (note.isCpp()||note.isEformData()||note.isEncounterForm()||note.isInvoice()){
			}else{
				notesToDisplay.add(note);
			}
		}

		String issuesToHide = OscarProperties.getInstance().getProperty("encounter.hide_notes_with_issue","");
		String[] is =issuesToHide.split(",");
		int noteSize = notesToDisplay.size();
		String dateFormat = "dd-MMM-yyyy H:mm";
		int idx = 0;
		if(noteSize > 0){
			for(idx = noteSize - 1; idx >= 0; --idx){
			
				NoteDisplay note = notesToDisplay.get(idx);
				
				boolean hideCppNotes = OscarProperties.getInstance().isPropertyActive("encounter.hide_cpp_notes");
				boolean hideDocumentNotes = OscarProperties.getInstance().isPropertyActive("encounter.hide_document_notes");
				boolean hideEformNotes = OscarProperties.getInstance().isPropertyActive("encounter.hide_eform_notes");
				boolean hideFormNotes = OscarProperties.getInstance().isPropertyActive("encounter.hide_form_notes");
				boolean hideBillingNotes = OscarProperties.getInstance().isPropertyActive("encounter.hide_billing_notes");

				String noteDisplay = "";				
				if(note.isCpp() && hideCppNotes) {
					//noteDisplay="none";
					noteDisplay="style=\"display:none\"";
				}
				if(note.isDocument() && hideDocumentNotes) {
					//noteDisplay="none";
					noteDisplay="style=\"display:none\"";
				}
				if(note.isEformData() && hideEformNotes) {
					//noteDisplay="none";
					noteDisplay="style=\"display:none\"";
				}
				if(note.isEncounterForm() && hideFormNotes) {
					//noteDisplay="none";
					noteDisplay="style=\"display:none\"";
				}
				if(note.isInvoice() && hideBillingNotes) {
					//noteDisplay="none";
					noteDisplay="style=\"display:none\"";
				}

				if(!noteDisplay.equals("none") && issuesToHide.length()>0) {
					for(String i:is) {
						if(note.containsIssue(i)) {
							//noteDisplay="none";
							noteDisplay="style=\"display:none\"";
							break;
						}
					}
				}
								
				String docname = "";
				ArrayList<String> editorNames1 = note.getEditorNames();
				Iterator<String> it1 = editorNames1.iterator();
				while (it1.hasNext())
				{
					docname = it1.next();
				}
				if(docname == ""){
				}else{
	%>
	<tr <%=noteDisplay%>>
		<td><%=DateUtils.getDate(note.getObservationDate(), dateFormat, request.getLocale())%></td>
		<td>
			<ul style="list-style: none inside none; margin: 0px;">
				<%
					ArrayList<String> editorNames = note.getEditorNames();
					Iterator<String> it = editorNames.iterator();
					int count = 0;
					int MAXLINE = 2;
					while (it.hasNext())
					{
						String providerName = it.next();

						if (count % MAXLINE == 0)
						{
							out.print("<li>" + providerName);
						}
						else
						{
							out.print(providerName + "</li>");
						}
						if (it.hasNext()) ++count;
					}
					if (count % MAXLINE == 0) out.print("</li>");
				%>
			</ul>
		</td>
		<td><%= note.getNote()%></td>
	</tr>
	<%
		}
		}
	}else{
	%>
	<tr>
		<td>empty</td>
		<td>empty</td>
		<td>empty</td>
	</tr>
	<%
		}
	%>
	<tr>
		<td colspan="3" align="center"><a href="javascript:void(0)" onclick="window.close(); return false;" style="text-decoration:none">Close</a></td>
	</tr>
	</table>
	</tr>
</table>
</body>
</html>
