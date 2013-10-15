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

<%@page import="java.util.*" %>
<%@page import="org.oscarehr.common.dao.ConsultationRequestDao" %>
<%@page import="org.oscarehr.common.model.ConsultationRequest" %>
<%@page import="org.oscarehr.util.SpringUtils" %>
<%@page import="org.oscarehr.util.WKHtmlToPdfUtils" %>
<%@page import="oscar.oscarDemographic.pageUtil.Util" %>
<%@page import="oscar.oscarReport.data.DemographicSets" %>
<%@page import="java.io.InputStream, java.io.FileOutputStream, java.io.File" %>
<%@page import="java.net.HttpURLConnection, java.net.URL" %>


<%
if(session.getAttribute("user") == null) response.sendRedirect("../logout.jsp");

String tmp_dir = oscar.OscarProperties.getInstance().getProperty("TMP_DIR");
boolean tmp_dir_ready = Util.checkDir(tmp_dir);


//Actual exporting <<--
if ("true".equals(request.getParameter("sendExport"))) {
	String patientSet = request.getParameter("patientSet");
    if (patientSet != null) {
    	List<String> demoNoList = new DemographicSets().getDemographicSet(patientSet);
    	
    	String requestURL = request.getRequestURL().toString();
    	requestURL = requestURL.substring(0, requestURL.indexOf(request.getRequestURI()));
    	requestURL += request.getContextPath() + "/oscarEncounter/oscarConsultationRequest/ConsultationFormPrint.jsp?reqId=";
    	
    	ConsultationRequestDao consultationRequestDao = (ConsultationRequestDao) SpringUtils.getBean(ConsultationRequestDao.class);
        
    	ArrayList<File> pdfFiles = new ArrayList<File>();
        File tmpDir = new File(tmp_dir);
        File tmpHtml = new File(tmpDir, "tmp.html");
    	
    	for (String demoNo : demoNoList) {
    	    for (ConsultationRequest consultReq : consultationRequestDao.getConsults(demoNo)) {
    	    	Integer reqId = consultReq.getId();

                URL urlConsultReq = new URL(requestURL+reqId);
                HttpURLConnection urlConnection = (HttpURLConnection) urlConsultReq.openConnection();
                urlConnection.setRequestProperty("Cookie", "JSESSIONID="+request.getSession().getId());
                InputStream is = urlConnection.getInputStream();
                FileOutputStream fos = new FileOutputStream(tmpHtml);

                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, read);
                }
                
                File outputPdf = new File(tmpDir, "consultReq_d"+demoNo+"_c"+reqId+".pdf");
                WKHtmlToPdfUtils.convertToPdf(tmp_dir+"/tmp.html", outputPdf);
                pdfFiles.add(outputPdf);
    	    }
    	}
    	
    	Util.zipFiles(pdfFiles, "consultReqs.zip", tmp_dir);
    	Util.downloadFile("consultReqs.zip", tmp_dir, response);
        tmpHtml.delete();
        Util.cleanFile("consultReqs.zip", tmp_dir);
        Util.cleanFiles(pdfFiles);
    }
}
//-->> Actual exporting


DemographicSets ds = new DemographicSets();
List<String> sets = ds.getDemographicSets();

String userRole = (String)session.getAttribute("userrole");
%>

<html locale="true">

<head>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/global.js"></script>
<title>Demographic Export</title>
<script src="../share/javascript/Oscar.js"></script>
<link rel="stylesheet" type="text/css" href="../share/css/OscarStandardLayout.css">
<link rel="stylesheet" type="text/css" media="all" href="../share/css/extractedFromPages.css"  />

<SCRIPT LANGUAGE="JavaScript">
function checkSelect(slct) {
    if (slct==-1) {
        alert("Please select a Patient Set");
        return false;
    }
    else return true;
}
</SCRIPT>
</head>

<body class="BodyStyle" vlink="#0000FF">
<%
if (!userRole.toLowerCase().contains("admin")) { %>
<p>
<h2>Sorry! Only administrators can export consultations.</h2>

<%
} else if (!tmp_dir_ready) { %>
<p>
<h2>Error! Cannot write to tmp directory. Please contact support.</h2>

<%
} else {
%>

<table class="MainTable" id="scrollNumber1" name="encounterTable">
<tr class="MainTableTopRow">
    <td style="max-width:200px;" class="MainTableTopRowLeftColumn">Consultation Export</td>
    <td class="MainTableTopRowRightColumn"></td>
</tr>
<tr>
    <td class="MainTableLeftColumn" valign="top" nowrap="nowrap"></td>
	<td valign="top" class="MainTableRightColumn">
	    <form action="consultationExport.jsp" method="get" onsubmit="return checkSelect(patientSet.value);">
			Patient Set:
			<select name="patientSet">demographics
                <option value="-1">--Select Set--</option>
	<%
	for (int i=0; i<sets.size(); i++) {
	    String setName = sets.get(i);
	%>
                <option value="<%=setName%>"><%=setName%></option>
    <%}%>
            </select>
            <input type="hidden" name="sendExport" value="true"/>
            <input type="submit" value="Export Consultations" />
        </form>
    </td>
</tr>
<tr>
	<td class="MainTableBottomRowLeftColumn">&nbsp;</td>
	<td class="MainTableBottomRowRightColumn" valign="top">&nbsp;</td>
</tr>
</table>
<%}%>
</body>
</html>
