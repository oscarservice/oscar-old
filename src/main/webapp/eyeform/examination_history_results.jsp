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

<%@ include file="/taglibs.jsp"%>
<%@page import="org.oscarehr.eyeform.web.EyeformAction"%>
<%@page import="org.oscarehr.common.model.Appointment"%>
<%@page import="oscar.oscarEncounter.oscarMeasurements.model.Measurements"%>
<%@page import="java.util.List"%>
<%@page import="oscar.util.StringUtils" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@ page import="oscar.dms.*,java.util.*" %>
<%@ page import="oscar.OscarProperties"%>
<%
	String sdate = StringUtils.transformNullInEmptyString((String)request.getAttribute("sdate"));
	String edate = StringUtils.transformNullInEmptyString((String)request.getAttribute("edate"));
	oscar.OscarProperties props1 = oscar.OscarProperties.getInstance();
	String eyeform = props1.getProperty("cme_js");
	String[] fields = request.getParameterValues("fromlist2");
	List<String> fieldList = new ArrayList<String>();
	if(fields != null) {
		for(String field:fields) {
			fieldList.add(field);
		}
	}
%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />

<html>
	<head>
    	<title>Examination History Results</title>
    	<link rel="stylesheet" type="text/css" href='<html:rewrite page="/jsCalendar/skins/aqua/theme.css" />' />
		<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/share/calendar/calendar.css" title="win2k-cold-1" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/share/calendar/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/share/calendar/lang/<bean:message key="global.javascript.calendar"/>"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/share/calendar/calendar-setup.js"></script>
    	<link rel="stylesheet" href="<%=request.getContextPath()%>/eyeform/display2.css" type="text/css">
    	<style type="text/css">
			* { font-family: Trebuchet MS, Lucida Sans Unicode, Arial, Helvetica, sans-serif; color: #000; margin: 0px; padding: 0px; }
			body { padding: 10px; }

			td.inner{
			border:1px solid #666;
			}

			table.common{
			border:0;
			font-size: 10pt;
			}
			h5{
				margin-top: 1px;
				border-bottom: 1px solid #000;
				font-weight: bold;
				list-style-type: none;
				font-family: Trebuchet MS, Lucida Sans Unicode, Arial, sans-serif;
				font-size: 10pt;
				overflow: hidden;
				background-color: #ccccff;
				padding: 0px;
				color: black;
				width: 300px;
			}
			th {white-space:nowrap}

			.centered {text-align:center}
			
			.title { font-family: Arial, Helvetica, sans-serif; font-size: 16px; font-weight: bold; color: #333333; }
			.title1 { font-family: Arial, Helvetica, sans-serif; font-size: 12px; font-weight: bold; color: #333333; }
		</style>
		<script>
			function allsubmit(){
				document.getElementById("sdate").value = "";
				document.getElementById("edate").value = "";
				inputForm.submit();
			}
		</script>
	</head>

	<body>
		<form action="<%=request.getContextPath()%>/eyeform/ExaminationHistory.do" method="POST" id="inputForm" name="inputForm">
		<input type="hidden" name="method" value="query"/>
		<input type="hidden" name="demographicNo" value="<c:out value="${demographic.demographicNo}"/>"/>
		<input type="hidden" name="refPage" value="<c:out value="${refPage}"/>"/>
		<c:forEach var="field" items="${fields}">
			<input type="hidden" name="fromlist2" value="<c:out value="${field}"/>"/>
		</c:forEach>
		<table class="common">
	  		<tr>
	  			<td>
	  				<h4 style="background-color: #69c">Demographic name:<c:out value="${demographic.formattedName}"/></h4>
	  			</td>
	  		</tr>
			<tr>
				<table style="background-color: #efefff">
					<tr>
						<td>Start Date:</td>
						<td>
							<%if(eyeform.equals("eyeform3")){%>
							<input type="text" class="plain" name="sdate" id="sdate" size="12" onfocus="this.blur()" readonly="readonly" value=""/>
							<%}else{%>
			 				<input type="text" class="plain" name="sdate" id="sdate" size="12" onfocus="this.blur()" readonly="readonly" value="<%=sdate%>"/>
							<%}%>
			 				<img src="<%=request.getContextPath()%>/images/cal.gif" id="sdate_cal">
			 				<script type="text/javascript">
								Calendar.setup({ inputField : "sdate", ifFormat : "%Y-%m-%d", showsTime :false, button : "sdate_cal", singleClick : true, step : 1 });
							</script>
		    			</td>
						<td>End Date:</td>
						<td>
							<input type="text" class="plain" name="edate" id="edate" size="12" onfocus="this.blur()" readonly="readonly" value="<%=edate%>"/>
							<img src="<%=request.getContextPath()%>/images/cal.gif" id="edate_cal">
			 				<script type="text/javascript">
								Calendar.setup({ inputField : "edate", ifFormat : "%Y-%m-%d", showsTime :false, button : "edate_cal", singleClick : true, step : 1 });
							</script>
						</td>
						<td></td>
						<td>
							<input type="submit" onclick="this.form.refPage.value=null" value="Search"/>
							<%if(eyeform.equals("eyeform3")){%>
							<!--<input type="button" onclick="allsubmit();" value="Alls" />-->
							<%}%>
						</td>
					</tr>
				</table>
			</tr>
	 	</table>
<%if(!eyeform.equals("eyeform3")){%>
		<h5>Simple field history:</h5>
		<table class="display" style="width:20%">
		<tr style="background-color: rgb(204, 204, 255);">
	    <td>Total <c:out value="${numPages}"/> pages.</td>
		<td align="right">
		<%
			int numPages = (Integer)request.getAttribute("numPages");
			int pageNumber = (Integer)request.getAttribute("refPage");
			if(pageNumber>1) {
				%><a href="#" onclick="document.inputForm.refPage.value=<%=(pageNumber-1)%>;return document.inputForm.submit();">prev</a><%
			} else {
				%>prev<%
			}
		%>
		&nbsp;
		<%
			if(numPages > 1 && pageNumber<numPages) {
				%><a href="#" onclick="document.inputForm.refPage.value=<%=(pageNumber+1)%>;return document.inputForm.submit();">next</a><%
			} else {
				%>next<%
			}
		%>
		</td>
	  </tr>
		<table class="display" style="width:20%">
			<tr style="background-color: rgb(204, 204, 255);">
		  		<td nowrap="nowrap"></td>
		  		<%
		  			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		  			for(Appointment appointment:(List<Appointment>)request.getAttribute("appointments")) {
		  				out.println("<td nowrap=\"nowrap\">"+formatter.format(appointment.getAppointmentDate())+"</td>");
		  			}
		  		%>
			</tr>
			<%
				@SuppressWarnings("unchecked")
				List<String> simpleFieldNames = (List<String>)request.getAttribute("simpleFieldNames");
				Measurements simpleFields[][] = (Measurements[][])request.getAttribute("simpleFields");
				for(int x=0;x<simpleFields.length;x++) {
					out.println("<tr class=\""+(((x%2)==0)?"even":"odd")+"\">");
					out.println("<td nowrap=\"nowrap\">"+simpleFieldNames.get(x)+"</td>");
					for(int y=0;y<simpleFields[x].length;y++) {
						out.println("<td nowrap=\"nowrap\">"+((simpleFields[x][y]!=null)?simpleFields[x][y].getDataField():"")+"</td>");
					}
					out.println("</tr>");
				}
			%>
</table>
<%}%>
<%if(eyeform.equals("eyeform3")){%>
<table width="100%" cellpadding="1" cellspacing="4" border="0">
<%if(fieldList.contains("Glasses Rx")){%>
<tr>
	<td class="title">GLASSES HISTORY :</td>
</tr>

<%
	ArrayList<Map<String,String>>  glasses_str = new ArrayList<Map<String,String>>();
	glasses_str = (ArrayList<Map<String,String>>)request.getAttribute("glasses");
	if(glasses_str != null){
	for(int i=0;i < glasses_str.size(); i ++ ){
%>
<tr>
<%
		Map<String,String> glasses = glasses_str.get(i);
		String date_str = glasses.get("date");
		String gl_rs = glasses.get("gl_rs");
		String gl_rc = glasses.get("gl_rc");
		String gl_rx = glasses.get("gl_rx");
		String gl_ra = glasses.get("gl_ra");
		String gl_rp = glasses.get("gl_rp");
		String gl_ls = glasses.get("gl_ls");
		String gl_lc = glasses.get("gl_lc");
		String gl_lx = glasses.get("gl_lx");
		String gl_la = glasses.get("gl_la");
		String gl_lp = glasses.get("gl_lp");
		String gl_date = glasses.get("gl_date");
		String gl_note = glasses.get("gl_note");
%>
		<td style="word-break:break-all"><span class="title1">(<%=date_str%>)&nbsp;  &nbsp;Glasses Rx </span>
		OD[<%=gl_rs%>][<%=gl_rc%>]X [<%=gl_rx%>]add [<%=gl_ra%>]prism [<%=gl_rp%>];OS[<%=gl_ls%>][<%=gl_lc%>]X [<%=gl_lx%>]add [<%=gl_la%>]prism [<%=gl_lp%>];date: [<%=gl_date%>]note:[<%=gl_note%>].
		</td>
</tr>	
<%		
		}
	}
%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Distance vision (sc)")){
%>
<tr>
	<td class="title">VISION ASSESSMENT :</td>
</tr>
<%
	ArrayList<Map<String,String>>  distance_vision = new ArrayList<Map<String,String>>(); 
	distance_vision = (ArrayList<Map<String,String>>)request.getAttribute("distance_vision");
	if(distance_vision != null){
	for(int i=0;i < distance_vision.size(); i ++ ){
		Map<String,String> distance = distance_vision.get(i);
		String date_str = distance.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%	
	if(fieldList.contains("Distance vision (sc)")){		
		String v_rdsc = distance.get("v_rdsc");
		String v_ldsc = distance.get("v_ldsc");
		String v_dsc = distance.get("v_dsc");
%>
		<span class="title1">Distance vision (sc)</span>
			OD [<%=v_rdsc%>];OS [<%=v_ldsc%>];OU [<%=v_dsc%>].
<%
	}
%>
<%	
	if(fieldList.contains("Distance vision (cc)")){		
		String v_rdcc = distance.get("v_rdcc");
		String v_ldcc = distance.get("v_ldcc");
		String v_dcc = distance.get("v_dcc");
%>
		<span class="title1">Distance vision (cc) </span>
		OD [<%=v_rdcc%>];OS [<%=v_ldcc%>];OU [<%=v_dcc%>].
<%
	}
%>
<%	
	if(fieldList.contains("Distance vision (ph)")){	
		String v_rph = distance.get("v_rph");
		String v_lph = distance.get("v_lph");
%>
		<span class="title1">Distance vision (ph) </span>
		OD [<%=v_rph%>];OS [<%=v_lph%>];
<%
	}
%>
<%	
	if(fieldList.contains("Intermediate vision (sc)")){	
		String v_risc = distance.get("v_risc");
		String v_lisc = distance.get("v_lisc");
		String v_isc = distance.get("v_isc");
%>
		<span class="title1">Intermediate vision (sc) </span>
		OD [<%=v_risc%>];OS [<%=v_lisc%>];OU [<%=v_isc%>].
<%
	}
%>
<%	
	if(fieldList.contains("Intermediate vision (cc)")){		
		String v_ricc = distance.get("v_ricc");
		String v_licc = distance.get("v_licc");
		String v_icc = distance.get("v_icc");
%>
		<span class="title1">Intermediate vision (sc) </span>
		OD [<%=v_ricc%>];OS [<%=v_licc%>];OU [<%=v_icc%>].
<%
	}
%>
<%	
	 if(fieldList.contains("Near vision (sc)")){	
		String v_rnsc = distance.get("v_rnsc");
		String v_lnsc = distance.get("v_lnsc");
		String v_nsc = distance.get("v_nsc");
%>
		<span class="title1">Near vision (sc) </span>
		OD [<%=v_rnsc%>];OS [<%=v_lnsc%>];OU [<%=v_nsc%>].
<%
	}
%>
<%	
	if(fieldList.contains("Near vision (cc)")){	
		String v_rncc = distance.get("v_rncc");
		String v_lncc = distance.get("v_lncc");
		String v_ncc = distance.get("v_ncc");
%>
		<span class="title1">Near vision (cc) </span>
		OD [<%=v_rncc%>];OS [<%=v_lncc%>];OU [<%=v_ncc%>].
<%
	}
%>
</td>
</tr>
<%		
	}}
%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Fly test")){
%>
<tr>
	<td class="title">STEREO VISION :</td>
</tr>
<%
	ArrayList<Map<String,String>>  fly_test = new ArrayList<Map<String,String>>(); 
	fly_test = (ArrayList<Map<String,String>>)request.getAttribute("fly_test");
	if(fly_test != null){
	for(int i=0;i < fly_test.size(); i ++ ){
		Map<String,String> fly = fly_test.get(i);
		String date_str = fly.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%	
		if(fieldList.contains("Fly test")){		
		String v_fly = fly.get("v_fly");
%>
		<span class="title1">Fly test </span>
		[<%=v_fly%>].
<%
	}
%>
<%	
		if(fieldList.contains("Stereo-acuity")){	
		String v_stereo = fly.get("v_stereo");
%>
		<span class="title1">Stereo-acuity </span>
		[<%=v_stereo%>].
</td>
<%
	}
%>
</tr>
<%		
	}}
%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Keratometry")){
%>
<tr>
	<td class="title">VISION MEASUREMENT :</td>
</tr>
<%
	ArrayList<Map<String,String>>  keratometry = new ArrayList<Map<String,String>>(); 
	keratometry = (ArrayList<Map<String,String>>)request.getAttribute("keratometry");
	if(keratometry != null){
	for(int i=0;i < keratometry.size(); i ++ ){
		Map<String,String> keratometry_str = keratometry.get(i);
		String date_str = keratometry_str.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%	
	if(fieldList.contains("Keratometry")){		
		String v_rk1 = keratometry_str.get("v_rk1");
		String v_rk2 = keratometry_str.get("v_rk2");
		String v_rkx = keratometry_str.get("v_rkx");
		String v_lk1 = keratometry_str.get("v_lk1");
		String v_lk2 = keratometry_str.get("v_lk2");
		String v_lkx = keratometry_str.get("v_lkx");
%>
		<span class="title1">Keratometry </span>
		OD [<%=v_rk1%>]x [<%=v_rk2%>]@ [<%=v_rkx%>];OS [<%=v_lk1%>]x [<%=v_lk2%>]@ [<%=v_lkx%>].
<%
	}
%>	
<%	
	if(fieldList.contains("Auto-refraction")){	
		String v_rs = keratometry_str.get("v_rs");
		String v_rc = keratometry_str.get("v_rc");
		String v_rx = keratometry_str.get("v_rx");
		String v_rar = keratometry_str.get("v_rar");
		String v_ls = keratometry_str.get("v_ls");
		String v_lc = keratometry_str.get("v_lc");
		String v_lx = keratometry_str.get("v_lx");
		String v_lar = keratometry_str.get("v_lar");
%>
		<span class="title1">Auto-refraction </span>
		OD [<%=v_rs%>][<%=v_rc%>]x [<%=v_rx%>]([<%=v_rar%>]);OS [<%=v_ls%>][<%=v_lc%>]x [<%=v_lx%>]([<%=v_lar%>]).
<%
	}
%>	
<%	
		if(fieldList.contains("Manifest distance")){	
		String v_rds = keratometry_str.get("v_rds");
		String v_rdc = keratometry_str.get("v_rdc");
		String v_rdx = keratometry_str.get("v_rdx");
		String v_rdv = keratometry_str.get("v_rdv");
		String v_lds = keratometry_str.get("v_lds");
		String v_ldc = keratometry_str.get("v_ldc");
		String v_ldx = keratometry_str.get("v_ldx");
		String v_ldv = keratometry_str.get("v_ldv");
		String v_dist = keratometry_str.get("v_dist");
%>
		<span class="title1">Manifest distance </span>
		OD [<%=v_rds%>][<%=v_rdc%>]x [<%=v_rdx%>]([<%=v_rdv%>]);OS [<%=v_lds%>][<%=v_ldc%>]x [<%=v_ldx%>]([<%=v_ldv%>]);OU [<%=v_dist%>].
<%
	}
%>	
<%	
	if(fieldList.contains("Manifest near")){		
		String v_rns = keratometry_str.get("v_rns");
		String v_rnc = keratometry_str.get("v_rnc");
		String v_rnx = keratometry_str.get("v_rnx");
		String v_rnv = keratometry_str.get("v_rnv");
		String v_lns = keratometry_str.get("v_lns");
		String v_lnc = keratometry_str.get("v_lnc");
		String v_lnx = keratometry_str.get("v_lnx");
		String v_lnv = keratometry_str.get("v_lnv");
		String v_near = keratometry_str.get("v_near");
%>
		<span class="title1">Manifest near </span>
		OD [<%=v_rns%>][<%=v_rnc%>]x [<%=v_rnx%>]([<%=v_rnv%>]);OS [<%=v_lns%>][<%=v_lnc%>]x [<%=v_lnx%>]([<%=v_lnv%>]);OU [<%=v_near%>].
<%
	}
%>
<%	
	if(fieldList.contains("Cycloplegic refraction")){		
		String v_rcs = keratometry_str.get("v_rcs");
		String v_rcc = keratometry_str.get("v_rcc");
		String v_rcx = keratometry_str.get("v_rcx");
		String v_rcv = keratometry_str.get("v_rcv");
		String v_lcs = keratometry_str.get("v_lcs");
		String v_lcc = keratometry_str.get("v_lcc");
		String v_lcx = keratometry_str.get("v_lcx");
		String v_lcv = keratometry_str.get("v_lcv");
%>
		<span class="title1">Cycloplegic refraction </span>
		OD [<%=v_rcs%>][<%=v_rcc%>]x [<%=v_rcx%>]([<%=v_rcv%>]);OS [<%=v_lcs%>][<%=v_lcc%>]x [<%=v_lcx%>]([<%=v_lcv%>]).
<%
	}
%>
</td>	
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("NCT")){
%>
<tr>
	<td class="title">INTRAOCULAR PRESSURE :</td>
</tr>
<%
	ArrayList<Map<String,String>>  nct = new ArrayList<Map<String,String>>(); 
	nct = (ArrayList<Map<String,String>>)request.getAttribute("nct");
	if(nct != null){
	for(int i=0;i < nct.size(); i ++ ){
		Map<String,String> nct_str = nct.get(i);
		String date_str = nct_str.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%	
	if(fieldList.contains("NCT")){		
		String iop_rn = nct_str.get("iop_rn");
		String iop_ln = nct_str.get("iop_ln");
		String iop_ntime = nct_str.get("iop_ntime");
%>
		<span class="title1">NCT </span>
		OD [<%=iop_rn%>];OS [<%=iop_ln%>];([<%=iop_ntime%>]).
<%
	}
%>	
<%	
	if(fieldList.contains("Applanation")){		
		String iop_ra = nct_str.get("iop_ra");
		String iop_la = nct_str.get("iop_la");
		String iop_atime = nct_str.get("iop_atime");
		if(iop_atime == null){
			iop_atime = "";
		}
%>
		<span class="title1">Applanation  </span>
		OD [<%=iop_ra%>];OS [<%=iop_la%>];([<%=iop_atime%>]).	
<%
	}
%>
<%	
	if(fieldList.contains("Central corneal thickness")){		
		String cct_r = nct_str.get("cct_r");
		String cct_l = nct_str.get("cct_l");
%>
		<span class="title1">Central corneal thickness </span>
		OD [<%=cct_r%>]microns;OS [<%=cct_l%>]microns.
<%
	}
%>
</td>		
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Dominance")){
%>
<tr>
	<td class="title">REFRACTIVE :</td>
</tr>
<%
	ArrayList<Map<String,String>>  dominance = new ArrayList<Map<String,String>>(); 
	dominance = (ArrayList<Map<String,String>>)request.getAttribute("dominance");
	if(dominance != null){
	for(int i=0;i < dominance.size(); i ++ ){
		Map<String,String> dominance_str = dominance.get(i);
		String date_str = dominance_str.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%	
	if(fieldList.contains("Dominance")){
		
		String ref_rdom = dominance_str.get("ref_rdom");
		String ref_ldom = dominance_str.get("ref_ldom");
%>
		<span class="title1">Dominance </span>OD [<%=ref_rdom%>];OS [<%=ref_ldom%>].
<%
	}
%>	
<%	
	if(fieldList.contains("Mesopic pupil size")){		
		String ref_rpdim = dominance_str.get("ref_rpdim");
		String ref_lpdim = dominance_str.get("ref_lpdim");
%>
		<span class="title1">Mesopic pupil size </span>OD [<%=ref_rpdim%>];OS [<%=ref_lpdim%>].
<%
	}
%>
<%	
		if(fieldList.contains("Angle Kappa")){		
		String ref_rkappa = dominance_str.get("ref_rkappa");
		String ref_lkappa = dominance_str.get("ref_lkappa");
%>
		<span class="title1">Angle Kappa </span>
		OD [<%=ref_rkappa%>];OS [<%=ref_lkappa%>].	
<%
	}
%>
</td>
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Colour vision")){
%>
<tr>
	<td class="title">OTHER EXAM :</td>
</tr>
<%
	ArrayList<Map<String,String>>  colour_vision = new ArrayList<Map<String,String>>(); 
	colour_vision = (ArrayList<Map<String,String>>)request.getAttribute("colour_vision");
	if(colour_vision != null){
	for(int i=0;i < colour_vision.size(); i ++ ){
		Map<String,String> colour = colour_vision.get(i);
		String date_str = colour.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%	
		if(fieldList.contains("Colour vision")){		
		String o_rcolour = colour.get("o_rcolour");
		String o_lcolour = colour.get("o_lcolour");
%>
		<span class="title1">Colour vision </span>
		OD [<%=o_rcolour%>];OS [<%=o_lcolour%>].	
<%
	}
%>
<%	
	if(fieldList.contains("Pupil")){		
		String o_rpupil = colour.get("o_rpupil");
		String o_lpupil = colour.get("o_lpupil");
%>
		<span class="title1">Pupil </span>
		OD [<%=o_rpupil%>];OS [<%=o_lpupil%>].
<%
	}
%>
<%	
	if(fieldList.contains("Amsler grid")){		
		String o_ramsler = colour.get("o_ramsler");
		String o_lamsler = colour.get("o_lamsler");
%>
		<span class="title1">Amsler grid </span>
		OD [<%=o_ramsler%>];OS [<%=o_lamsler%>].
<%
	}
%>
<%	
		if(fieldList.contains("Potential acuity meter")){		
		String o_rpam = colour.get("o_rpam");
		String o_lpam = colour.get("o_lpam");
%>
		<span class="title1">Potential acuity meter </span>
		OD [<%=o_rpam%>];OS [<%=o_lpam%>].
<%
	}
%>
<%	
	if(fieldList.contains("Confrontation fields")){		
		String o_rconf = colour.get("o_rconf");
		String o_lconf = colour.get("o_lconf");
%>
		<span class="title1">Confrontation fields </span>
		OD [<%=o_rconf%>];OS [<%=o_lconf%>].
<%
	}
%>
<%	
	if(fieldList.contains("Maddox rod")){		
		String o_mad = colour.get("o_mad");
%>
		<span class="title1">Maddox rod </span>
		[<%=o_mad%>].
<%
	}
%>	
<%	
	if(fieldList.contains("Bagolini test")){		
		String o_bag = colour.get("o_bag");
%>
		<span class="title1">Bagolini test </span>
		[<%=o_bag%>].
<%
	}
%>
<%	
	if(fieldList.contains("Worth 4 Dot (distance)")){		
		String o_w4dd = colour.get("o_w4dd");
%>
		<span class="title1">Worth 4 Dot (distance) </span>
		[<%=o_w4dd%>].
<%
	}
%>
<%	
	if(fieldList.contains("Worth 4 Dot (near)")){		
		String o_w4dn = colour.get("o_w4dn");
%>
		<span class="title1">Worth 4 Dot (near) </span>
		[<%=o_w4dn%>].
<%
	}
%>
</td>
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("DUCTION/DIPLOPIA TESTING")){
%>
<tr>
	<td class="title">DUCTION/DIPLOPIA TESTING :</td>
</tr>
<%
	ArrayList<Map<String,String>>  ductlion = new ArrayList<Map<String,String>>(); 
	ductlion = (ArrayList<Map<String,String>>)request.getAttribute("ductlion");
	if(ductlion != null){
	for(int i=0;i < ductlion.size(); i ++ ){
		Map<String,String> ductlion_str = ductlion.get(i);
		String duc_rur = ductlion_str.get("duc_rur");
		String duc_rul = ductlion_str.get("duc_rul");
		String duc_lur = ductlion_str.get("duc_lur");
		String duc_lul = ductlion_str.get("duc_lul");
		String dip_ur = ductlion_str.get("dip_ur");
		String dip_u = ductlion_str.get("dip_u");
		String dip_ul = ductlion_str.get("dip_ul");
		String duc_rr = ductlion_str.get("duc_rr");
		String duc_rl = ductlion_str.get("duc_rl");
		String duc_lr = ductlion_str.get("duc_lr");
		String duc_ll = ductlion_str.get("duc_ll");
		String dip_r = ductlion_str.get("dip_r");
		String dip_p = ductlion_str.get("dip_p");
		String dip_l = ductlion_str.get("dip_l");
		String duc_rdr = ductlion_str.get("duc_rdr");
		String duc_rdl = ductlion_str.get("duc_rdl");
		String duc_ldr = ductlion_str.get("duc_ldr");
		String duc_ldl = ductlion_str.get("duc_ldl");
		String dip_dr = ductlion_str.get("dip_dr");
		String dip_d = ductlion_str.get("dip_d");
		String dip_dl = ductlion_str.get("dip_dl");
		String date_str = ductlion_str.get("date");
%>
<tr>
			<td style="white-space:nowrap">
			<table cellpadding="0" cellspacing="0" border="0">
			<tr>
			<td>
			OD<span class="title1">(<%= date_str%>)</span>		
			
			<table>
			<tr>
				<td>
					[<%=duc_rur%>]
				</td>
				<td>
					[<%=duc_rul%>]
				</td>
			</tr>
			<tr>
				<td>
					[<%=duc_rr%>]
				</td>
				<td>
					[<%=duc_rl%>]
				</td>
			</tr>
			<tr>
				<td>
					[<%=duc_rdr%>]
				</td>
				<td>
					[<%=duc_rdl%>]
				</td>
			</tr>
			</table>
			</td>
			<td>
			OS<span class="title1">(<%= date_str%>)</span>
			<table>
			<tr>
				<td>
					[<%=duc_lur%>]
				</td>
				<td>
					[<%=duc_lul%>]
				</td>
			</tr>
			<tr>
				<td>
					[<%=duc_lr%>]
				</td>
				<td>
					[<%=duc_ll%>]
				</td>
			</tr>
			<tr>
				<td>
					[<%=duc_ll%>]
				</td>
				<td>
					[<%=duc_ldl%>]
				</td>
			</tr>
			</table>
			</td>
			<td>
			OU<span class="title1">(<%= date_str%>)</span>
			<table>
			<tr>
				<td>
					[<%=dip_ur%>]
				</td>
				<td>
					[<%=dip_u%>]
				</td>
			</tr>
			<tr>
				<td>
					[<%=dip_r%>]
				</td>
				<td>
					[<%=dip_p%>]
				</td>
			</tr>
			<tr>
				<td>
					[<%=dip_dr%>]
				</td>
				<td>
					[<%=dip_d%>]
				</td>
			</tr>
			</table>
			</td>
			</tr>
			</table>
			</td>
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Primary gaze")){
%>
<tr>
	<td class="title">DEVIATION MEASUREMENT :</td>
</tr>
<%
	ArrayList<Map<String,String>>  primary = new ArrayList<Map<String,String>>(); 
	primary = (ArrayList<Map<String,String>>)request.getAttribute("primary");
	if(primary != null){
	for(int i=0;i < primary.size(); i ++ ){
		Map<String,String> primary_str = primary.get(i);
		String date_str = primary_str.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%
	if(fieldList.contains("Primary gaze")){
		String dev_p = primary_str.get("dev_p");
%>
		<span class="title1">Primary gaze </span>
			[<%=dev_p%>].
<% 
	}
%>
<%
	if(fieldList.contains("Up gaze")){
		String dev_u = primary_str.get("dev_u");
%>
		<span class="title1">Up gaze </span>
			[<%=dev_u%>].
<%
	}
%>
<%
	if(fieldList.contains("Down gaze")){
		String dev_d = primary_str.get("dev_d");
%>
		<span class="title1">Down gaze </span>
			[<%=dev_d%>].
<%
	}
%>
<%
	if(fieldList.contains("Right gaze")){
		String dev_r = primary_str.get("dev_r");
%>
		<span class="title1">Right gaze </span>
			[<%=dev_r%>].
<%
	}
%>
<%
	if(fieldList.contains("Left gaze")){
		String dev_l = primary_str.get("dev_l");
%>
		<span class="title1">Left  gaze </span>
			[<%=dev_l%>].
<%
	}
%>
<%
	if(fieldList.contains("Right head tilt")){
		String dev_rt = primary_str.get("dev_rt");
%>
		<span class="title1">Right head tilt </span>
			[<%=dev_rt%>].
<%
	}
%>
<%
	if(fieldList.contains("Left head tilt")){
		String dev_lt = primary_str.get("dev_lt");
%>
		<span class="title1">Left head tilt </span>
			[<%=dev_lt%>].
<%
	}
%>
<%
	if(fieldList.contains("Near")){
		String dev_near = primary_str.get("dev_near");
%>
		<span class="title1">Near </span>
			[<%=dev_near%>].
<%
	}
%>
<%
	if(fieldList.contains("Near with +3D add")){
		String dev_plus3 = primary_str.get("dev_plus3");
%>
		<span class="title1">Near with +3D add </span>
			[<%=dev_plus3%>].
<%
	}
%>
<%
	if(fieldList.contains("Far distance")){
		String dev_far = primary_str.get("dev_far");
%>
		<span class="title1">Far distance </span>
			[<%=dev_far%>].
<%
	}
%>
</td>
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Retropulsion")){
%>
<tr>
	<td class="title">EXTERNAL/ORBIT :</td>
</tr>
<%
	ArrayList<Map<String,String>>  retropulsion = new ArrayList<Map<String,String>>(); 
	retropulsion = (ArrayList<Map<String,String>>)request.getAttribute("retropulsion");
	if(retropulsion != null){
	for(int i=0;i < retropulsion.size(); i ++ ){
		Map<String,String> retropulsion_str = retropulsion.get(i);
		String date_str = retropulsion_str.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%
	if(fieldList.contains("Face")){
		String ext_rface = retropulsion_str.get("ext_rface");
		String ext_lface = retropulsion_str.get("ext_lface");	
%>
		<span class="title1">Face </span>
			Right side[<%=ext_rface%>];Left  side[<%=ext_lface%>].
<%
	}
%>
<%
	if(fieldList.contains("Retropulsion")){
		String ext_rretro = retropulsion_str.get("ext_rretro");
		String ext_lretro = retropulsion_str.get("ext_lretro");	
%>
		<span class="title1">Retropulsion </span>
			OD [<%=ext_rretro%>];OS [<%=ext_lretro%>].
<%
	}
%>
<%
	if(fieldList.contains("Hertel")){
		String ext_rhertel = retropulsion_str.get("ext_rhertel");
		String ext_lhertel = retropulsion_str.get("ext_lhertel");	
%>
		<span class="title1">Hertel </span>
			OD [<%=ext_rhertel%>];OS [<%=ext_lhertel%>].
<%
	}
%>
</td>
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Upper lid")){
%>
<tr>
	<td class="title">EYELID/NASOLACRIMAL DUCT :</td>
</tr>
<%
	ArrayList<Map<String,String>>  upper = new ArrayList<Map<String,String>>(); 
	upper = (ArrayList<Map<String,String>>)request.getAttribute("upper");
	if(upper != null){
	for(int i=0;i < upper.size(); i ++ ){
		Map<String,String> upper_str = upper.get(i);
		String date_str = upper_str.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%
	if(fieldList.contains("Upper lid")){
		String ext_rul = upper_str.get("ext_rul");
		String ext_lul = upper_str.get("ext_lul");	
%>
		<span class="title1">Upper lid </span>
		OD [<%=ext_rul%>];OS [<%=ext_lul%>].
<%
	}
%>
<%
	if(fieldList.contains("Lower lid")){
		String ext_rll = upper_str.get("ext_rll");
		String ext_lll = upper_str.get("ext_lll");	
%>
		<span class="title1">Lower lid </span>
			OD [<%=ext_rll%>];OS [<%=ext_lll%>].
<%
	}
%>
<%
	if(fieldList.contains("Lacrimal lake")){
		String ext_rlake = upper_str.get("ext_rlake");
		String ext_llake = upper_str.get("ext_llake");	
%>
		<span class="title1">Lacrimal lake </span>
			OD [<%=ext_rlake%>];OS [<%=ext_llake%>].
<%
	}
%>
<%
	if(fieldList.contains("Lacrimal irrigation")){
		String ext_rirrig = upper_str.get("ext_rirrig");
		String ext_lirrig = upper_str.get("ext_lirrig");	
%>
		<span class="title1">Lacrimal irrigation </span>
			OD [<%=ext_rirrig%>];OS [<%=ext_lirrig%>].
<%
	}
%>
<%
	if(fieldList.contains("Punctum")){
		String ext_rpunc = upper_str.get("ext_rpunc");
		String ext_lpunc = upper_str.get("ext_lpunc");	
%>
		<span class="title1">Punctum  </span>
			OD [<%=ext_rpunc%>];OS [<%=ext_lpunc%>].
<%
	}
%>
<%
	if(fieldList.contains("Nasolacrimal duct")){
		String ext_rnld = upper_str.get("ext_rnld");
		String ext_lnld = upper_str.get("ext_lnld");	
%>
		<span class="title1">Nasolacrimal duct  </span>
			OD [<%=ext_rnld%>];OS [<%=ext_lnld%>].
<%
	}
%>
<%
	if(fieldList.contains("Dye disappearance")){
		String ext_rdye = upper_str.get("ext_rdye");
		String ext_ldye = upper_str.get("ext_ldye");	
%>
		<span class="title1">Dye disappearance  </span>
			OD [<%=ext_rdye%>];OS [<%=ext_ldye%>].
<%
	}
%>
</td>
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Margin reflex distance")){
%>
<tr>
	<td class="title">EYELID MEASUREMENT :</td>
</tr>
<%
	ArrayList<Map<String,String>>  margin = new ArrayList<Map<String,String>>(); 
	margin = (ArrayList<Map<String,String>>)request.getAttribute("margin");
	if(margin != null){
	for(int i=0;i < margin.size(); i ++ ){
		Map<String,String> margin_str = margin.get(i);
		String date_str = margin_str.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%
	if(fieldList.contains("Margin reflex distance")){
		String lid_rmrd = margin_str.get("lid_rmrd");
		String lid_lmrd = margin_str.get("lid_lmrd");	
%>
		<span class="title1">Margin reflex distance  </span>
			OD [<%=lid_rmrd%>];OS [<%=lid_lmrd%>].
<%
	}
%>
<%
	if(fieldList.contains("Inferior scleral show")){
		String lid_riss = margin_str.get("lid_riss");
		String lid_liss = margin_str.get("lid_liss");	
%>
		<span class="title1">Inferior scleral show </span> 
			OD [<%=lid_riss%>];OS [<%=lid_liss%>].
<%
	}
%>
<%
	if(fieldList.contains("Levator function")){
		String lid_rlev = margin_str.get("lid_rlev");
		String lid_llev = margin_str.get("lid_llev");	
%>
		<span class="title1">Levator function  </span>
		OD [<%=lid_rlev%>];OS [<%=lid_llev%>].
<%
	}
%>
<%
	if(fieldList.contains("Lagophthalmos")){
		String lid_rlag = margin_str.get("lid_rlag");
		String lid_llag = margin_str.get("lid_llag");	
%>
		<span class="title1">Lagophthalmos  </span>
			OD [<%=lid_rlag%>];OS [<%=lid_llag%>].
<%
	}
%>
<%
	if(fieldList.contains("Blink reflex")){
		String lid_rblink = margin_str.get("lid_rblink");
		String lid_lblink = margin_str.get("lid_lblink");	
%>
		<span class="title1">Blink reflex  </span>
			OD [<%=lid_rblink%>];OS [<%=lid_lblink%>].
<%
	}
%>
<%
	if(fieldList.contains("Cranial Nerve VII function")){
		String lid_rcn7 = margin_str.get("lid_rcn7");
		String lid_lcn7 = margin_str.get("lid_lcn7");	
%>
		<span class="title1">Cranial Nerve VII function  </span>
			OD [<%=lid_rcn7%>];OS [<%=lid_lcn7%>].
<%
	}
%>
<%
	if(fieldList.contains("Bells phenomenon")){
		String lid_rbell = margin_str.get("lid_rbell");
		String lid_lbell = margin_str.get("lid_lbell");	
%>
		<span class="title1">Bell's phenomenon  </span>
			OD [<%=lid_rbell%>];OS [<%=lid_lbell%>].
<%
	}
%>
<%
	if(fieldList.contains("Schirmer test")){
		String lid_rschirm = margin_str.get("lid_rschirm");
		String lid_lschirm = margin_str.get("lid_lschirm");	
%>
		<span class="title1">Schirmer test  </span>
			OD [<%=lid_rschirm%>];OS [<%=lid_lschirm%>].
<%
	}
%>
</td>
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Cornea")){
%>
<tr>
	<td class="title">ANTERIOR SEGMENT :</td>
</tr>
<%
	ArrayList<Map<String,String>>  cornea = new ArrayList<Map<String,String>>(); 
	cornea = (ArrayList<Map<String,String>>)request.getAttribute("cornea");
	if(cornea != null){
	for(int i=0;i < cornea.size(); i ++ ){
		Map<String,String> cornea_str = cornea.get(i);
		String date_str = cornea_str.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%
	if(fieldList.contains("Cornea")){
		String a_rk = cornea_str.get("a_rk");
		String a_lk = cornea_str.get("a_lk");	
%>
		<span class="title1">Cornea  </span>
			OD [<%=a_rk%>];OS [<%=a_lk%>].
<%
	}
%>
<%
	if(fieldList.contains("Conjunctiva/Sclera")){
		String a_rconj = cornea_str.get("a_rconj");
		String a_lconj = cornea_str.get("a_lconj");	
%>
		<span class="title1">Conjunctiva/Sclera  </span>
			OD [<%=a_rconj%>];OS [<%=a_lconj%>].
<%
	}
%>
<%
	if(fieldList.contains("Anterior chamber")){
		String a_rac = cornea_str.get("a_rac");
		String a_lac = cornea_str.get("a_lac");	
%>
		<span class="title1">Anterior chamber  </span>
			OD [<%=a_rac%>];OS [<%=a_lac%>].
<%
	}
%>
<%
	if(fieldList.contains("Angle")){
		String a_rangle_1 = cornea_str.get("a_rangle_1");
		String a_rangle_2 = cornea_str.get("a_rangle_2");
		String a_rangle_3 = cornea_str.get("a_rangle_3");
		String a_rangle_4 = cornea_str.get("a_rangle_4");
		String a_rangle_5 = cornea_str.get("a_rangle_5");
		String a_langle_1 = cornea_str.get("a_langle_1");	
		String a_langle_2 = cornea_str.get("a_langle_2");	
		String a_langle_3 = cornea_str.get("a_langle_3");	
		String a_langle_4 = cornea_str.get("a_langle_4");	
		String a_langle_5 = cornea_str.get("a_langle_5");	
%>
		<span class="title1">Angle </span>
			OD [<%=a_rangle_3%>] (superior[<%=a_rangle_1%>] nasal[<%=a_rangle_4%>] inferior[<%=a_rangle_5%>] temporal[<%=a_rangle_2%>]);OS [<%=a_langle_3%>] (superior[<%=a_langle_1%>] nasal[<%=a_langle_2%>] inferior[<%=a_langle_5%>] temporal[<%=a_langle_4%>]).
<%
	}
%>
<%
	if(fieldList.contains("Iris")){
		String a_riris = cornea_str.get("a_riris");
		String a_liris = cornea_str.get("a_liris");	
%>
		<span class="title1">Iris </span>
			OD [<%=a_riris%>];OS [<%=a_liris%>].
<%
	}
%>
<%
	if(fieldList.contains("Lens")){
		String a_rlens = cornea_str.get("a_rlens");
		String a_llens = cornea_str.get("a_llens");	
%>
		<span class="title1">Lens </span>
			OD [<%=a_rlens%>];OS [<%=a_llens%>].
<%
	}
%>
</td>
</tr>
<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
if(fieldList.contains("Optic disc")){
%>
<tr>
	<td class="title">POSTERIOR SEGMENT :</td>
</tr>
<%
	ArrayList<Map<String,String>>  optic = new ArrayList<Map<String,String>>(); 
	optic = (ArrayList<Map<String,String>>)request.getAttribute("optic");
	if(optic != null){
	for(int i=0;i < optic.size(); i ++ ){
		Map<String,String> optic_str = optic.get(i);
		String date_str = optic_str.get("date");
%>
<tr><td style="word-break:break-all"><span class="title1">(<%= date_str%>)&nbsp; &nbsp;</span>
<%
	if(fieldList.contains("Optic disc")){
		String p_rdisc = optic_str.get("p_rdisc");
		String p_ldisc = optic_str.get("p_ldisc");	
%>
		<span class="title1">Optic disc </span>
			OD [<%=p_rdisc%>];OS [<%=p_ldisc%>].
<%
	}
%>
<%
	if(fieldList.contains("C/D ratio")){
		String p_rcd = optic_str.get("p_rcd");
		String p_lcd = optic_str.get("p_lcd");	
%>
		<span class="title1">C/D ratio </span>
			OD [<%=p_rcd%>];OS [<%=p_lcd%>].
<%
	}
%>
<%
	if(fieldList.contains("Macula")){
		String p_rmac = optic_str.get("p_rmac");
		String p_lmac = optic_str.get("p_lmac");	
%>
		<span class="title1">Macula </span>
			OD [<%=p_rmac%>];OS [<%=p_lmac%>].
<%
	}
%>
<%
	if(fieldList.contains("Retina")){
		String p_rret = optic_str.get("p_rret");
		String p_lret = optic_str.get("p_lret");	
%>
		<span class="title1">Retina </span>
			OD [<%=p_rret%>];OS [<%=p_lret%>].
<%
	}
%>
<%
	if(fieldList.contains("Vitreous")){
		String p_rvit = optic_str.get("p_rvit");
		String p_lvit = optic_str.get("p_lvit");	
%>
		<span class="title1">Vitreous </span>
			OD [<%=p_rvit%>];OS [<%=p_lvit%>].
<%
	}
%>
</td>
</tr>

<%}}%>
<tr><td>&nbsp;</td></tr>
<%
}
%>
</table>

<%}else{%>
<table class="display">
  <tr>
  <td><h5>AR history</h5>
  <display:table name="ars" requestURI="/eyeform/ExaminationHistory.do" class="display" style="width:100%" id="map" pagesize="5">

			<display:column title="OD Sph" style="width:30px;white-space: nowrap;">
			<c:out value="${map.od_ar_sph}"/>
			</display:column>

			<display:column title="OD Cyl" style="width:30px;white-space: nowrap;">
			<c:out value="${map.od_ar_cyl}"/>

			</display:column>
			 <display:column title="OD Axis" style="width:30px;white-space: nowrap;">
		    <c:out value="${map.od_ar_axis}"/>
			</display:column>

			<display:column title="Date" style="width:60px;white-space: nowrap;text-align:center" headerClass="centered">
			<c:out value="${map.date}"/>
			</display:column>

			<display:column title="OS Sph" style="width:30px;white-space: nowrap;">
			<c:out value="${map.os_ar_sph}"/>
			</display:column>

			<display:column title="OS Cyl" style="width:30px;white-space: nowrap;">
			<c:out value="${map.os_ar_cyl}"/>
			</display:column>

			 <display:column title="OS Axis" style="width:30px;">
		    <c:out value="${map.os_ar_axis}"/>
			</display:column>

	</display:table>
   </td>
   </tr>

  <tr>
  <td><h5>K history</h5>
  <display:table name="ks" requestURI="/ExaminationHistory.do" class="display" style="width:100%" id="map" pagesize="5">

			<display:column title="OD K1" style="width:30px;">
			<c:out value="${map.od_k1}"/>
			</display:column>

			<display:column title="OD K2" style="width:30px;">
			<c:out value="${map.od_k2}"/>

			</display:column>
			 <display:column title="OD K2-Axis" style="width:30px;">
		    <c:out value="${map.od_k2_axis}"/>
			</display:column>

			<display:column title="Date" style="text-align:center" headerClass="centered">
			<c:out value="${map.date}"/>
			</display:column>

			<display:column title="OS K1" style="width:30px;">
			<c:out value="${map.os_k1}"/>
			</display:column>

			<display:column title="OS K2" style="width:30px;">
			<c:out value="${map.os_k2}"/>
			</display:column>

			 <display:column title="OS K2-Axis" style="width:30px;">
		    <c:out value="${map.os_k2_axis}"/>
			</display:column>

	</display:table>
   </td>
   </tr>


  <tr>
  <td><h5>Manifest Refraction history</h5>
  <display:table name="manifestRefraction" requestURI="/eyeform/ExaminationHistory.do" class="display" style="width:100%" id="map" pagesize="5">

			<display:column title="OD Sph" style="width:30px;">
			<c:out value="${map.od_manifest_refraction_sph}"/>
			</display:column>

			<display:column title="OD Cyl" style="width:30px;">
			<c:out value="${map.od_manifest_refraction_cyl}"/>

			</display:column>

			 <display:column title="OD Axis" style="width:30px;">
		    <c:out value="${map.od_manifest_refraction_axis}"/>
			</display:column>


			 <display:column title="OD Add" style="width:30px;">
		    <c:out value="${map.od_manifest_refraction_add}"/>
			</display:column>

		   <display:column title="Date" style="text-align:center" headerClass="centered">
			<c:out value="${map.date}"/>
			</display:column>

			<display:column title="OS Sph" style="width:30px;">
			<c:out value="${map.os_manifest_refraction_sph}"/>
			</display:column>

			<display:column title="OS Cyl" style="width:30px;">
			<c:out value="${map.os_manifest_refraction_cyl}"/>
			</display:column>

			 <display:column title="OS Axis" style="width:30px;">
		    <c:out value="${map.os_manifest_refraction_axis}"/>
			</display:column>

		 <display:column title="OS Add" style="width:30px;">
		    <c:out value="${map.os_manifest_refraction_add}"/>
			</display:column>

  </display:table>
   </td>
   </tr>


  <tr>
  <td><h5>Cycloplegic refraction history</h5>
  <display:table name="cycloplegicRefraction" requestURI="/eyeform/ExaminationHistory.do" class="display" style="width:100%" id="map" pagesize="5">

			<display:column title="OD Sph" style="width:30px;">
			<c:out value="${map.od_cycloplegic_refraction_sph}"/>
			</display:column>

			<display:column title="OD Cyl" style="width:30px;">
			<c:out value="${map.od_cycloplegic_refraction_cyl}"/>

			</display:column>

			 <display:column title="OD Axis" style="width:30px;">
		    <c:out value="${map.od_cycloplegic_refraction_axis}"/>
			</display:column>


			 <display:column title="OD Add" style="width:30px;">
		    <c:out value="${map.od_cycloplegic_refraction_add}"/>
			</display:column>

		   <display:column title="Date" style="text-align:center" headerClass="centered">
			<c:out value="${map.date}"/>
			</display:column>

			<display:column title="OS Sph" style="width:30px;">
			<c:out value="${map.os_cycloplegic_refraction_sph}"/>
			</display:column>

			<display:column title="OS Cyl" style="width:30px;">
			<c:out value="${map.os_cycloplegic_refraction_cyl}"/>
			</display:column>

			 <display:column title="OS Axis" style="width:30px;">
		    <c:out value="${map.os_cycloplegic_refraction_axis}"/>
			</display:column>

		 <display:column title="OS Add" style="width:30px;">
		    <c:out value="${map.os_cycloplegic_refraction_add}"/>
			</display:column>

  </display:table>
   </td>
   </tr>

  <tr>
  <td><h5>Angle history</h5>
  <display:table name="angle" requestURI="/eyeform/ExaminationHistory.do" class="display" style="width:100%" id="map" pagesize="5">

			<display:column title="OD" >
			<table style="border:0px">
			<tr>
			<td width="33%"></td>
			<td class="inner" width="34%"><c:out value="${map.od_angle_up}"/></td>
			<td width="33%"></td>
			</tr>
			<tr>
			<td class="inner"><c:out value="${map.od_angle_middle0}"/></td>
			<td class="inner"><c:out value="${map.od_angle_middle1}"/></td>
			<td class="inner"><c:out value="${map.od_angle_middle2}"/></td>
			</tr>
			<tr>
			<td></td>
			<td class="inner"><c:out value="${map.od_angle_down}"/></td>
			<td></td>
			</tr>
			</table>
			</display:column>
			<display:column title="Date" style="text-align:center" headerClass="centered">
			<c:out value="${map.date}"/>
			</display:column>

			<display:column title="OS" >
			<table border="1">
			<tr>
			<td width="33%"></td>
			<td class="inner" width="34%"><c:out value="${map.os_angle_up}"/></td>
			<td width="33%"></td>
			</tr>
			<tr>
			<td class="inner"><c:out value="${map.os_angle_middle0}"/></td>
			<td class="inner"><c:out value="${map.os_angle_middle1}"/></td>
			<td class="inner"><c:out value="${map.os_angle_middle2}"/></td>
			</tr>
			<tr>
			<td width="33%"></td>
			<td class="inner" width="34%"><c:out value="${map.os_angle_down}"/></td>
			<td width="33%"></td>
			</tr>
			</table>
			</display:column>

  </display:table>
   </td>
   </tr>

		</table>
<%}%>
	</body>
</html>
