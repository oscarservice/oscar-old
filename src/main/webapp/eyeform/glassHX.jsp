<%--

    Copyright (c) 2001-2002. Department of Family Medicine, McMaster University. All Rights Reserved.
    This software is published under the GPL GNU General Public License.
    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.

    This software was written for the
    Department of Family Medicine
    McMaster University
    Hamilton
    Ontario, Canada

--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page	import="org.springframework.web.context.WebApplicationContext,
		org.springframework.web.context.support.WebApplicationContextUtils,
		org.oscarehr.casemgmt.model.CaseManagementNote,
		org.oscarehr.casemgmt.model.CaseManagementNoteLink,
		org.oscarehr.casemgmt.service.CaseManagementManager,
		org.oscarehr.common.dao.SecRoleDao,
		org.oscarehr.common.model.SecRole,
		org.oscarehr.util.SpringUtils,
		oscar.oscarEncounter.data.EctProgram,
		java.util.Date, java.util.List"%>
<%@page import="oscar.log.LogAction, oscar.log.LogConst"%>
<%@page import="oscar.dms.EDocUtil"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/oscar-tag.tld" prefix="oscar"%>
<%@ taglib uri="/WEB-INF/rewrite-tag.tld" prefix="rewrite"%>

<%
        
    String attrib_name = request.getParameter("atbname");
    String special=request.getParameter("drugSpecial");
    if (attrib_name==null) attrib_name="";

    String demo = request.getParameter("demo");
    String display = request.getParameter("display");
    boolean saved = Boolean.valueOf(request.getParameter("saved"));

    String tid = request.getParameter("table_id");
    Long tableId = 0L;
    if (filled(tid)) tableId = Long.valueOf(tid);
    else tid = "";
    
    String oid = request.getParameter("other_id");
    if(oid==null) {oid="";}

    HttpSession se = request.getSession();
    String user_no = (String) se.getAttribute("user");

    WebApplicationContext  ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(se.getServletContext());
    CaseManagementManager cmm = (CaseManagementManager) ctx.getBean("caseManagementManager");

    Integer tableName = cmm.getTableNameByDisplay(display);
    String note = "";
    String dump = "";
    List<CaseManagementNoteLink> cmll = null;
    CaseManagementNoteLink cml = null;
    if(oid!=null && oid.length()>0) {
    	cmll = cmm.getLinkByTableIdDesc(tableName, tableId, oid);
    } else {
    	cmll = cmm.getLinkByTableIdDesc(tableName, tableId);
    }
    CaseManagementNote p_cmn = null;
    CaseManagementNote p_cmn_dump = null;
    
    for (CaseManagementNoteLink link : cmll) {

        CaseManagementNote cmnote = cmm.getNote(link.getNoteId().toString());
        if (cmnote.getNote().startsWith("imported.cms4.2011.06")) {
            if (p_cmn_dump==null) p_cmn_dump = cmm.getNote(link.getNoteId().toString());
        } else {
            if (p_cmn==null) {
                p_cmn = cmm.getNote(link.getNoteId().toString());
                cml = link;
            }
        }
        if (p_cmn_dump!=null && p_cmn!=null) break;
    }

    //get the most recent previous note from uuid.
    if (p_cmn!=null) p_cmn=cmm.getMostRecentNote(p_cmn.getUuid());

    String uuid = "";
    //if get provider no is -1 , it's a document note.
    if (p_cmn!=null && !p_cmn.getProviderNo().equals("-1") ) uuid = p_cmn.getUuid();
    else p_cmn=null;//don't use document note as annotation.
    //get note from attribute
    CaseManagementNote a_cmn = (CaseManagementNote)se.getAttribute(attrib_name);
    CaseManagementNote historyNote=new CaseManagementNote();
    if (a_cmn!=null) {
        historyNote=a_cmn;
	note = a_cmn.getNote();
    } else if (p_cmn!=null){
	//get note from database
	 //previous annotation exists
            historyNote=p_cmn;
            note = p_cmn.getNote();
    }
    if (p_cmn_dump!=null) {
        dump = p_cmn_dump.getNote().substring("imported.cms4.2011.06".length());
    }
    if (saved) {
	String prog_no = new EctProgram(se).getProgram(user_no);
        //create a note with demo, user_no, prog_no
	CaseManagementNote cmn = createCMNote(historyNote,request.getParameter("note"), demo, user_no, prog_no);
	if (cmn!=null) {
	    if (p_cmn!=null) {  //previous annotation exists
//            if (p_cmn!=null && note !="") { //previous annotation exists
		cmn.setUuid(uuid); //assign same UUID to new annotation
	    }
	    if (tableName.equals(cml.CASEMGMTNOTE) || tableId.equals(0L)) {
                //new casemgmt_note may be saved AFTER annotation
		if (!attrib_name.equals("")) se.setAttribute(attrib_name, cmn);
            }
	    if (!tableId.equals(0L)) {
                    cmm.saveNoteSimple(cmn);
                    cml = new CaseManagementNoteLink();
                    cml.setTableName(tableName);
                    cml.setTableId(tableId);
                    cml.setNoteId(cmn.getId());
                    cml.setOtherId(oid);                    
                    cmm.saveNoteLink(cml);
                    LogAction.addLog(user_no,LogConst.ANNOTATE, display, String.valueOf(tableId), request.getRemoteAddr(), demo, cmn.getNote());                
	    }
	}
	response.sendRedirect("../close.html");
    }

    //Display revision history
    Integer rev = 0;
    if (p_cmn!=null) {
	List l = cmm.getNotesByUUID(uuid);
	rev = l.size();
    }

    
    CaseManagementNote lastCmn=null;
    if(uuid.length()>0){
        lastCmn=cmm.getMostRecentNote(uuid);
    }
    Boolean isMobileOptimized = session.getAttribute("mobileOptimized") != null;
%>


<html:html locale="true">
<head>
    <title>Annotation</title>
    <% if (isMobileOptimized) { %>
        <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width" />
        <link rel="stylesheet" type="text/css" href="../share/css/OscarStandardMobileLayout.css" />
    <% } else { %>
    <link rel="stylesheet" type="text/css" href="../share/css/OscarStandardLayout.css" />
        <style type="text/css">
            body { font-size: x-small; }
            textarea { width: 100%; margin: 5px 0; }
            div.label { float: left; }
        </style>
    <% } %>
   
</head>

<body bgcolor="#EEEEFF" onload="document.forms[0].note.focus();">
   <form action="/oscar-SNAPSHOT/eyeform/hxHistory.do" method="post" name="specsHistoryForm">
       <div class="panel">
            Notes
            <textarea name="specs.note" rows="10"><%=note%></textarea>
            <input type="submit" value="Save" onclick="this.form.method.value='save'; return validate(this);"/> &nbsp;
            <input type="button" class="leftButton top" value="Cancel" onclick="window.close();"/>          
        </div>
		<input type="hidden" value="save" name="method">
       <input type="hidden" value="<%=request.getParameter("demographic_no")%>" name="specs.demographicNo">
       <input type="hidden" value="<%=request.getParameter("appointment_no")%>" name="specs.appointmentNo">
       <input type="hidden" value="<%=request.getParameter("specs.id")%>" name="specs.id">
	   
	   <input type="hidden" value="<%=request.getParameter("type")%>" name="type">
	   <input type="hidden" value="<%=request.getParameter("dateStr")%>" name="dateStr">
	   
	   <input type="hidden" value="<%=request.getParameter("odSph")%>" name="odSph">
	   <input type="hidden" value="<%=request.getParameter("odCyl")%>" name="odCyl">
	   <input type="hidden" value="<%=request.getParameter("odAxis")%>" name="odAxis">
	   <input type="hidden" value="<%=request.getParameter("odAdd")%>" name="odAdd">
	   <input type="hidden" value="<%=request.getParameter("odPrism")%>" name="odPrism">
	   
	   <input type="hidden" value="<%=request.getParameter("osSph")%>" name="osSph">
	   <input type="hidden" value="<%=request.getParameter("osCyl")%>" name="osCyl">
	   <input type="hidden" value="<%=request.getParameter("osAxis")%>" name="osAxis">
	   <input type="hidden" value="<%=request.getParameter("osAdd")%>" name="osAdd">
	   <input type="hidden" value="<%=request.getParameter("osPrism")%>" name="osPrism">
    </form>
</body>
 
</html:html>


<%!
    CaseManagementNote createCMNote(CaseManagementNote historyNote,String note, String demo_no, String provider, String program_no) {
	if (!filled(note)) return null;

	CaseManagementNote cmNote = new CaseManagementNote();
	    cmNote.setUpdate_date(new Date());
	    cmNote.setObservation_date(new Date());
	    cmNote.setDemographic_no(demo_no);
	    cmNote.setProviderNo(provider);
	    cmNote.setSigning_provider_no(provider);
	    cmNote.setSigned(true);
	    cmNote.setProgram_no(program_no);
	    
	    SecRoleDao secRoleDao = (SecRoleDao) SpringUtils.getBean("secRoleDao");
		SecRole doctorRole = secRoleDao.findByName("doctor");		
		cmNote.setReporter_caisi_role(doctorRole.getId().toString());
	    	    
	    cmNote.setReporter_program_team("0");
	    cmNote.setNote(note);
            String historyStr;

            if(historyNote==null || historyNote.getHistory()==null || historyNote.getHistory().trim().length()==0 ){
                historyStr=note;
            }
            else{
                historyStr=note + "\n" + "   ----------------History Record----------------   \n" + historyNote.getHistory().trim() + "\n";
            }
            cmNote.setHistory(historyStr);
	return cmNote;
    }

    boolean filled(String s) {
	return (s!=null && s.trim().length()>0);
    }
%>
