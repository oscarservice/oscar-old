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
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="security"%>

<%@page import="org.oscarehr.common.dao.SiteDao"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.oscarehr.common.model.Site"%>
<%@page import="org.oscarehr.common.model.Provider"%><html:html locale="true">
<%@ page import="org.oscarehr.common.dao.MyGroupAccessRestrictionDao" %>
<%@ page import="org.oscarehr.common.model.MyGroupAccessRestriction" %>

<%!
        boolean checkRestriction(List<MyGroupAccessRestriction> restrictions, String name) {
                for(MyGroupAccessRestriction restriction:restrictions) {
                        if(restriction.getMyGroupNo().equals(name)) return true;
                }
                return false;
        }
%>

<%@ page import="org.oscarehr.common.dao.PrivateProvidersUtil"%>
<%@page import="org.oscarehr.common.model.ProviderPreference"%>
<%@page import="org.oscarehr.web.admin.ProviderPreferencesUIBean"%>
<%@page import="org.oscarehr.util.SessionConstants"%>

<%@ page import="java.util.*, java.sql.*, oscar.*, java.net.*, oscar.oscarEncounter.pageUtil.EctSessionBean" %>
<jsp:useBean id="apptMainBean" class="oscar.AppointmentMainBean" scope="session" />
<jsp:useBean id="SxmlMisc" class="oscar.SxmlMisc" scope="session" />
<jsp:useBean id="providerBean" class="java.util.Properties" scope="session" />
<%@ include file="dbTicker.jspf" %>
<% Properties oscarVariables = OscarProperties.getInstance(); %>
<%@ include file="/common/webAppContextAndSuperMgr.jsp"%>
<jsp:useBean id="providerNameBean" class="oscar.Dict" scope="page" />
<jsp:useBean id="myGrpBean" class="java.util.Properties" scope="page" />

<%    
    String curUser_no = (String) session.getAttribute("user");
    String roleName$ = (String)session.getAttribute("userrole") + "," + (String) session.getAttribute("user");
    String[] roles = ((String)session.getAttribute("userrole")).split(",");

    boolean isSiteAccessPrivacy=false;
    boolean isTeamAccessPrivacy=false;
    boolean isTeamScheduleOnly = false;

    MyGroupAccessRestrictionDao myGroupAccessRestrictionDao = WebApplicationContextUtils.getWebApplicationContext(application).getBean(MyGroupAccessRestrictionDao.class);

    ProviderPreference providerPreference2=(ProviderPreference)session.getAttribute(SessionConstants.LOGGED_IN_PROVIDER_PREFERENCE);

    String mygroupno = providerPreference2.getMyGroupNo();
    if(mygroupno == null){
    	mygroupno = providerPreference2.getMyGroupNo();
    if(mygroupno == null)
    	mygroupno = ".default";
    }

if(session.getAttribute("user") == null)
    response.sendRedirect("../logout.jsp");
//String user_no;
//user_no = (String) session.getAttribute("user");
int  nItems=0;
String strLimit1="0";
String strLimit2="5";
if(request.getParameter("limit1")!=null) strLimit1 = request.getParameter("limit1");
if(request.getParameter("limit2")!=null) strLimit2 = request.getParameter("limit2");
String providerview = curUser_no;//request.getParameter("providerview")==null?"all":request.getParameter("providerview") ;
boolean bFirstDisp=true; //this is the first time to display the window
if (request.getParameter("bFirstDisp")!=null) bFirstDisp= (request.getParameter("bFirstDisp")).equals("true");
String ChartNo;
String demoNo = "";
String demoName = request.getParameter("name");
if ( request.getAttribute("demographic_no") != null){
    demoNo = (String) request.getAttribute("demographic_no");
    demoName = (String) request.getAttribute("demoName");
    bFirstDisp = false;
}
if(demoName == null){demoName ="";}

//Retrieve encounter id for updating encounter navbar if info this page changes anything
String parentAjaxId;
if( request.getParameter("parentAjaxId") != null )
    parentAjaxId = request.getParameter("parentAjaxId");
else
    parentAjaxId = "";
    
String updateParent;
if( request.getParameter("updateParent") != null )
    updateParent = request.getParameter("updateParent");
else
    updateParent = "true";  
        
%>
<security:oscarSec objectName="_site_access_privacy" roleName="<%=roleName$%>" rights="r" reverse="false">
	<%isSiteAccessPrivacy=true; %>
</security:oscarSec>
<security:oscarSec objectName="_team_access_privacy" roleName="<%=roleName$%>" rights="r" reverse="false">
	<%isTeamAccessPrivacy=true; %>
</security:oscarSec>
<security:oscarSec objectName="_team_schedule_only" roleName="<%=roleName$%>" rights="r" reverse="false">
	<%isTeamScheduleOnly=true; %>
</security:oscarSec>
<%

GregorianCalendar now=new GregorianCalendar();
  int curYear = now.get(Calendar.YEAR);
  int curMonth = (now.get(Calendar.MONTH)+1);
  int curDay = now.get(Calendar.DAY_OF_MONTH);   
  
  %><% //String providerview=request.getParameter("provider")==null?"":request.getParameter("provider");
   String xml_vdate=request.getParameter("xml_vdate") == null?"":request.getParameter("xml_vdate");
   String xml_appointment_date = request.getParameter("xml_appointment_date")==null?MyDateFormat.getMysqlStandardDate(curYear, curMonth, curDay):request.getParameter("xml_appointment_date");
%>
<head>
<title><bean:message key="tickler.ticklerAdd.title"/></title>
<link rel="stylesheet" href="../billing/billing.css" >

<script src="<%=request.getContextPath() %>/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery-ui-1.8.18.custom.min.js"></script>
<script src="<%=request.getContextPath()%>/js/fg.menu.js"></script>


<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cupertino/jquery-ui-1.8.18.custom.css">
<style type="text/css">
<!--
.bodytext
{
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
  font-style: bold;
  line-height: normal;
  font-weight: normal;
  font-variant: normal;
  text-transform: none;
  color: #FFFFFF;
  text-decoration: none;
}
-->
</style>
      <script language="JavaScript">
<!--
function popupPage(vheight,vwidth,varpage) { //open a new popup window
  var page = "" + varpage;
  windowprops = "height="+vheight+",width="+vwidth+",location=no,scrollbars=yes,menubars=no,toolbars=no,resizable=yes";
  var popup=window.open(page, "attachment", windowprops);
  if (popup != null) {
    if (popup.opener == null) {
      popup.opener = self; 
    }
  }
}
function selectprovider(s) {
  if(self.location.href.lastIndexOf("&providerview=") > 0 ) a = self.location.href.substring(0,self.location.href.lastIndexOf("&providerview="));
  else a = self.location.href;
	self.location.href = a + "&providerview=" +s.options[s.selectedIndex].value ;
}
function openBrWindow(theURL,winName,features) { 
  window.open(theURL,winName,features);
}
function setfocus() {
  this.focus();
  document.ADDAPPT.keyword.focus();
  document.ADDAPPT.keyword.select();
}

function validate(form){
if (validateDemoNo(form)){
form.action = "dbTicklerAdd.jsp";
form.submit();

}
else{}
}
function validateDemoNo() {
 if (document.serviceform.demographic_no.value == "") {
    alert("<bean:message key="tickler.ticklerAdd.msgInvalidDemographic"/>");
	return false;
 }
 else{  if (document.serviceform.xml_appointment_date.value == "") {
    alert("<bean:message key="tickler.ticklerAdd.msgMissingDate"/>");
	return false;
 }
 else if (document.serviceform.task_assigned_to.value.substr(0,5)=="_grp_"){
    return confirm("Please confirm ticklers for all providers in group "+document.serviceform.task_assigned_to.value.substr(6,document.serviceform.task_assigned_to.value.length-5));
 } 
 else if (document.serviceform.task_assigned_to.value=='.<bean:message key="global.default"/>'){
    return confirm("Please confirm ticklers for all providers");
 } 
<% if (org.oscarehr.common.IsPropertiesOn.isMultisitesEnable()) { %>
 else if (document.serviceform.site.value=="none"){
    alert("Must select a site");
	return false;
 } 
<% } %>
 else{
    return true;
 }
}


}
function refresh() {
  var u = self.location.href;
  if(u.lastIndexOf("view=1") > 0) {
    self.location.href = u.substring(0,u.lastIndexOf("view=1")) + "view=0" + u.substring(eval(u.lastIndexOf("view=1")+6));
  } else {
    history.go(0);
  }
}





function DateAdd(startDate, numDays, numMonths, numYears)
{
	var returnDate = new Date(startDate.getTime());
	var yearsToAdd = numYears;
	
	var month = returnDate.getMonth()	+ numMonths;
	if (month > 11)
	{
		yearsToAdd = Math.floor((month+1)/12);
		month -= 12*yearsToAdd;
		yearsToAdd += numYears;
	}
	returnDate.setMonth(month);
	returnDate.setFullYear(returnDate.getFullYear()	+ yearsToAdd);
	
	returnDate.setTime(returnDate.getTime()+60000*60*24*numDays);
	
	return returnDate;

}

function YearAdd(startDate, numYears)
{
		return DateAdd(startDate,0,0,numYears);
}

function MonthAdd(startDate, numMonths)
{
		return DateAdd(startDate,0,numMonths,0);
}

function DayAdd(startDate, numDays)
{
		return DateAdd(startDate,numDays,0,0);
}


function addMonth(no)
{       var gCurrentDate = new Date();
	var newDate = DateAdd(gCurrentDate, 0, no,0 );
var newYear = newDate.getFullYear() 
var newMonth = newDate.getMonth()+1;
var newDay = newDate.getDate();
var newD = newYear + "-" + newMonth + "-" + newDay;
	document.serviceform.xml_appointment_date.value = newD;
}









//-->
</script>


</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" rightmargin="0" topmargin="10" onLoad="setfocus()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr bgcolor="#000000"> 
    <td height="40" width="10%"> </td>
    <td width="90%" align="left"> 
      <p><font face="Verdana, Arial, Helvetica, sans-serif" color="#FFFFFF"><b><font face="Arial, Helvetica, sans-serif" size="4"><bean:message key="tickler.ticklerAdd.msgTickler"/></font></b></font> 
      </p>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0"bgcolor="#EEEEFF">
 <form name="ADDAPPT" method="post" action="../appointment/appointmentcontrol.jsp">
<tr> 
      <td width="35%"><font color="#003366"><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><b><bean:message key="tickler.ticklerAdd.formDemoName"/>: </b></font></font></td>
      <td colspan="2" width="65%">
<div align="left"><INPUT TYPE="TEXT" NAME="keyword" size="25" VALUE="<%=bFirstDisp?"":demoName.equals("")?session.getAttribute("appointmentname"):demoName%>">
   	 <input type="submit" name="Submit" value="<bean:message key="tickler.ticklerAdd.btnSearch"/>">
  </div>
</td>
    </tr>
  <INPUT TYPE="hidden" NAME="orderby" VALUE="last_name" >
				      <INPUT TYPE="hidden" NAME="search_mode" VALUE="search_name" >
				      <INPUT TYPE="hidden" NAME="originalpage" VALUE="../tickler/ticklerAdd.jsp" >
				      <INPUT TYPE="hidden" NAME="limit1" VALUE="0" >
				      <INPUT TYPE="hidden" NAME="limit2" VALUE="5" >
              <!--input type="hidden" name="displaymode" value="TicklerSearch" -->
              <INPUT TYPE="hidden" NAME="displaymode" VALUE="Search "> 

<% ChartNo = bFirstDisp?"":request.getParameter("chart_no")==null?"":request.getParameter("chart_no"); %>
   <INPUT TYPE="hidden" NAME="appointment_date" VALUE="2002-10-01" WIDTH="25" HEIGHT="20" border="0" hspace="2">
       <INPUT TYPE="hidden" NAME="status" VALUE="t"  WIDTH="25" HEIGHT="20" border="0" hspace="2">
              <INPUT TYPE="hidden" NAME="start_time" VALUE="10:45" WIDTH="25" HEIGHT="20" border="0"  onChange="checkTimeTypeIn(this)">
              <INPUT TYPE="hidden" NAME="type" VALUE="" WIDTH="25" HEIGHT="20" border="0" hspace="2">
              <INPUT TYPE="hidden" NAME="duration" VALUE="15" WIDTH="25" HEIGHT="20" border="0" hspace="2" >
              <INPUT TYPE="hidden" NAME="end_time" VALUE="10:59" WIDTH="25" HEIGHT="20" border="0" hspace="2"  onChange="checkTimeTypeIn(this)">
       

 <input type="hidden" name="demographic_no"  readonly value="" width="25" height="20" border="0" hspace="2">
         <input type="hidden" name="location"  tabindex="4" value="" width="25" height="20" border="0" hspace="2">
              <input type="hidden" name="resources"  tabindex="5" value="" width="25" height="20" border="0" hspace="2">
              <INPUT TYPE="hidden" NAME="user_id" readonly VALUE='oscardoc, doctor' WIDTH="25" HEIGHT="20" border="0" hspace="2">
     	        <INPUT TYPE="hidden" NAME="dboperation" VALUE="add_apptrecord">
              <INPUT TYPE="hidden" NAME="createdatetime" readonly VALUE="2002-10-1 17:53:50" WIDTH="25" HEIGHT="20" border="0" hspace="2">
              <INPUT TYPE="hidden" NAME="provider_no" VALUE="115">
              <INPUT TYPE="hidden" NAME="creator" VALUE="oscardoc, doctor">
              <INPUT TYPE="hidden" NAME="remarks" VALUE="">
              <input type="hidden" name="parentAjaxId" value="<%=parentAjaxId%>"/>
              <input type="hidden" name="updateParent" value="<%=updateParent%>"/> 
 </form>
</table>
<table width="100%" border="0" bgcolor="#EEEEFF">
  <form name="serviceform" method="post" >
      <input type="hidden" name="parentAjaxId" value="<%=parentAjaxId%>"/>
      <input type="hidden" name="updateParent" value="<%=updateParent%>"/>
     <tr> 
      <td width="35%"> <div align="left"><font color="#003366"><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><strong><bean:message key="tickler.ticklerAdd.formChartNo"/>:</strong> </font></font></div></td>
      <td colspan="2"> <div align="left"><INPUT TYPE="hidden" NAME="demographic_no" VALUE="<%=bFirstDisp?"":request.getParameter("demographic_no").equals("")?"":request.getParameter("demographic_no")%>"><%=ChartNo%></div></td>
    </tr>

    <tr> 
      <td><font color="#003366" size="2" face="Verdana, Arial, Helvetica, sans-serif"><strong><bean:message key="tickler.ticklerAdd.formServiceDate"/>:</strong></font></td>
      <td><input type="text" name="xml_appointment_date" value="<%=xml_appointment_date%>"> 
        <font color="#003366" size="1" face="Verdana, Arial, Helvetica, sans-serif">
        <a href="#" onClick="openBrWindow('../billing/billingCalendarPopup.jsp?type=end&amp;year=<%=curYear%>&amp;month=<%=curMonth%>','','width=300,height=300')"><bean:message key="tickler.ticklerAdd.btnCalendarLookup"/></a> &nbsp; &nbsp; 
        <a href="#" onClick="addMonth(6)"><bean:message key="tickler.ticklerAdd.btn6Month"/></a>&nbsp; &nbsp;
        <a href="#" onClick="addMonth(12)"><bean:message key="tickler.ticklerAdd.btn1Year"/></a></font> </td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td height="21" valign="top"><font color="#003366" size="2" face="Verdana, Arial, Helvetica, sans-serif"><strong><bean:message key="tickler.ticklerMain.Priority"/></strong></font></td>
      <td valign="top"> 
	<select name="priority" style="font-face:Verdana, Arial, Helvetica, sans-serif">
 	<option value="<bean:message key="tickler.ticklerMain.priority.high"/>"><bean:message key="tickler.ticklerMain.priority.high"/>
	<option value="<bean:message key="tickler.ticklerMain.priority.normal"/>" SELECTED><bean:message key="tickler.ticklerMain.priority.normal"/>
	<option value="<bean:message key="tickler.ticklerMain.priority.low"/>"><bean:message key="tickler.ticklerMain.priority.low"/>	
     	</select>
      </td>
      <td>&nbsp;</td>
    </tr>

    <tr> 
      <td height="21" valign="top"><font color="#003366" size="2" face="Verdana, Arial, Helvetica, sans-serif"><strong><bean:message key="tickler.ticklerMain.taskAssignedTo"/></strong></font></td>
      <td valign="top"> <font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#333333">

  <select id="task_assigned_to" name="task_assigned_to">
  	<option value=".<bean:message key="global.default"/>">.<bean:message key="global.default"/></option>
  </select>

<% 
//multisite starts =====================
boolean bMultisites = org.oscarehr.common.IsPropertiesOn.isMultisitesEnable();
List<String> providerNos = new ArrayList<String>();
List<String> siteGroups = new ArrayList<String>();
List<String> siteProviderNos = new ArrayList<String>();
  String selectedSiteBgColor = null;
  String selectedSite = null;
  String selectedSiteId = null;
  HashMap<String,String> siteBgColor = new HashMap<String,String>();
  HashMap<String,String> CurrentSiteMap = new HashMap<String,String>();
  List<Site> sites = new ArrayList<Site>();
  List<Site> curUserSites = new ArrayList<Site>();
  List<Provider> providers = new ArrayList<Provider>();

if(bMultisites) {

  boolean isNoGroupsForDoctors = roles[0].equalsIgnoreCase("doctor");
  selectedSite = (String)session.getAttribute("site_selected");
  String appNo = (String) session.getAttribute("cur_appointment_no");
  if (selectedSite == null && appNo != null) {
      ResultSet rs = apptMainBean.queryResults(appNo, "get_appt_location");
      if(rs.next()) selectedSite = apptMainBean.getString(rs,1);
  }

  PrivateProvidersUtil privateProvidersUtil = (PrivateProvidersUtil) WebApplicationContextUtils.getWebApplicationContext(application).getBean("privateProvidersUtil");
  //disable privacy filter in tickler provider list.
  List providersData = privateProvidersUtil.getPrivateProvidersData(
			bMultisites, false, 
			false, 
			curUser_no, selectedSite, 
			isNoGroupsForDoctors);
			
  sites = (List<Site>) providersData.get(0);
  curUserSites = (List<Site>) providersData.get(1);
  providers = (List<Provider>) providersData.get(2);
  siteGroups = (List<String>) providersData.get(3);

  for(Provider prv : providers) {
	providerNos.add(prv.getProviderNo());
  }

  SiteDao siteDao = (SiteDao)WebApplicationContextUtils.getWebApplicationContext(application).getBean("siteDao");
  sites = siteDao.getAllActiveSites();

 	if (isSiteAccessPrivacy || isTeamAccessPrivacy) {
 		//user has Access Privacy, set user provider and group list
 		curUserSites = siteDao.getActiveSitesByProviderNo(curUser_no);
 		if (selectedSite==null) {
 			siteProviderNos = siteDao.getProviderNoBySiteManagerProviderNo(curUser_no);
 			siteGroups = siteDao.getGroupBySiteManagerProviderNo(curUser_no);
 		} 
 	}
 	else {
 		//get all active site as user site list
 		curUserSites = sites;
 	}
  	if(selectedSite != null) {
			siteProviderNos = siteDao.getProviderNoBySiteLocation(selectedSite);
			siteGroups = siteDao.getGroupBySiteLocation(selectedSite); 	
			selectedSiteId = String.valueOf(siteDao.getByLocation(selectedSite).getSiteId());
	}
  
  for (Site s : curUserSites) {
		CurrentSiteMap.put(s.getName(),"Y");
  }

	//get all sites bgColors
  for (Site st : sites) {
		siteBgColor.put(st.getName(),st.getBgColor());
  }
  
  selectedSiteBgColor = (selectedSite != null ? siteBgColor.get(selectedSite) : null);

%>
<script>
function changeSite(sel) {
	sel.style.backgroundColor=sel.options[sel.selectedIndex].style.backgroundColor;
	var siteId = sel.options[sel.selectedIndex].value;
	var newGroupNo = "<%=(mygroupno == null ? ".default" : mygroupno)%>";
	refreshProviderList(siteId, newGroupNo);
}
</script>
  <space style="padding-left:30px"/>
 
      
    	<select id="site" name="site" onchange="changeSite(this)" style="background-color: <%=( selectedSite == null || siteBgColor.get(selectedSite) == null ? "#FFFFFF" : siteBgColor.get(selectedSite))%>">
    		<option value="none" style="background-color:white">---all clinic---</option>
    	<%
    	for (int i=0; i<curUserSites.size(); i++) {
    	%>
    		<option value="<%= curUserSites.get(i).getId() %>" style="background-color:<%= curUserSites.get(i).getBgColor() %>" 
    				<%=(curUserSites.get(i).getName().equals(selectedSite)) ? " selected " : "" %> >
    			<%= curUserSites.get(i).getName() %>
    		</option>
    	<% } %>
    	</select>

<%
} else {
%>
<!--
      <select name="task_assigned_to" id="task_assigned_to">           
            <%  String proFirst="";
                String proLast="";
                String proOHIP="";

                ResultSet rslocal = apptMainBean.queryResults("%", "search_provider_all");
                while(rslocal.next()){
                    proFirst = rslocal.getString("first_name");
                    proLast = rslocal.getString("last_name");
                    proOHIP = rslocal.getString("provider_no"); 

            %> 
            <option value="<%=proOHIP%>" <%=curUser_no.equals(proOHIP)?"selected":""%>><%=proLast%>, <%=proFirst%></option>
            <%
                }
            %>
      </select>    
-->
<% } %>
				<space style="padding-left:30px"/>

<!-- ----------------------------------------------------------------------------------------------- -->
  



          
           <input type="hidden" name="docType" value="<%=request.getParameter("docType")%>"/>
           <input type="hidden" name="docId" value="<%=request.getParameter("docId")%>"/>
      </td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td height="21" valign="top"><font color="#003366" size="2" face="Verdana, Arial, Helvetica, sans-serif"><strong><bean:message key="tickler.ticklerAdd.formReminder"/>:</strong></font></td>
      <td valign="top"> <textarea style="font-face:Verdana, Arial, Helvetica, sans-serif"name="textarea" cols="50" rows="10"></textarea></td>
      <td>&nbsp;</td>
    </tr>
        
     <INPUT TYPE="hidden" NAME="user_no" VALUE="<%=curUser_no%>">
    <tr> 
      <td><input type="button" name="Button" value="<bean:message key="tickler.ticklerAdd.btnCancel"/>" onClick="window.close()"></td>
      <td><input type="button" name="Button" value="<bean:message key="tickler.ticklerAdd.btnSubmit"/>" onClick="validate(this.form)"></td>
      <td></td>
	  </tr>
  </form>
</table>
<p><font face="Arial, Helvetica, sans-serif" size="2"> </font></p>
  <p>&nbsp; </p>
<%@ include file="../demographic/zfooterbackclose.jsp" %> 
<script type="text/javascript">
function refreshProviderList(siteId, groupNo) {
   	    $.ajax({
                type: "GET",
     	        url: "<%=request.getContextPath() %>/providersListAjax.do?method=getProvidersList&siteId="+siteId+"&isTeamAccessPrivacy=<%= isTeamAccessPrivacy %>&isSiteAccessPrivacy=<%= isSiteAccessPrivacy %>&isTeamScheduleOnly=<%= isTeamScheduleOnly %>&providerview=<%= providerview %>&mygroupno="+groupNo,
                dataType: "json",
     	        success: function(data) {
			var options, index, select, option;

			select = document.getElementById('task_assigned_to');
    			select.options.length = 0;

    			options = data.options;
    			var selIdx = 0;
    			for (index = 0; index < options.length; ++index) {
      			  option = options[index];
      			  select.options.add(new Option(option.text, option.value));
      			  select.options[index].setAttribute("style",option.style);
      			  if(option.selected != null) selIdx = index;
    			}
    			select.selectedIndex = selIdx;
    		},
     	        error: function(data, textStatus, jqXHR) {
			alert("<bean:message key="tickler.ticklerAdd.providerListError"/>");
     	        }
            });

}

	var groupNo = "<%=(mygroupno == null ? ".default" : mygroupno)%>";
	var siteId = "<%=selectedSiteId == null? null : selectedSiteId %>";
	refreshProviderList(siteId, groupNo);

</script>
</body>
</html:html>
