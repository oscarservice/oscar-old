<!--
 *
 * Copyright (c) 2006-. OSCARservice, OpenSoft System. All Rights Reserved. *
 * This software is published under the GPL GNU General Public License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details. * * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. *
 *
 * Yi Li
 */
-->
<%@ taglib uri="/WEB-INF/security.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="java.math.*,java.util.*,java.sql.*,oscar.*,java.net.*,java.text.*"
	errorPage="errorpage.jsp"%>
<%@page import="org.oscarehr.common.model.Site,org.oscarehr.common.dao.SiteDao" %>
<%@page import="org.oscarehr.common.model.Provider,org.oscarehr.PMmodule.dao.ProviderDao" %>
<%@page import="org.oscarehr.billing.CA.ON.model.BillingONPayment,org.oscarehr.billing.CA.ON.dao.BillingONPaymentDao" %>
<%@page import="org.oscarehr.billing.CA.ON.model.BillingClaimHeader1" %>
<%@page import="org.oscarehr.billing.CA.model.BillingPaymentType" %>
<%@page import="java.text.SimpleDateFormat,java.text.NumberFormat" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>

<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.oscarehr.util.SpringUtils"%>

<% 
List<String> errors = new ArrayList<String>();

	boolean bMultisites = org.oscarehr.common.IsPropertiesOn.isMultisitesEnable();
	if(session.getAttribute("user") == null ) response.sendRedirect("../logout.jsp");
	String providerNo = (String) session.getAttribute("user");
	
	if(session.getAttribute("userrole") == null )  response.sendRedirect("../logout.jsp");
	String roleName$ = (String)session.getAttribute("userrole") + "," + (String) session.getAttribute("user");

	boolean isTeamBillingOnly=false;
	boolean isSiteAccessPrivacy=false;
	boolean isTeamAccessPrivacy=false;

	boolean isMultiSiteProvider = true;
	List<String> mgrSites = new ArrayList<String>();
%>
<security:oscarSec objectName="_team_billing_only" roleName="<%= roleName$ %>" rights="r" reverse="false">
	<% isTeamBillingOnly=true; %>
</security:oscarSec>
<security:oscarSec objectName="_site_access_privacy" roleName="<%=roleName$%>" rights="r" reverse="false">
	<%isSiteAccessPrivacy=true; %>
</security:oscarSec>
<security:oscarSec objectName="_team_access_privacy" roleName="<%=roleName$%>" rights="r" reverse="false">
	<%isTeamAccessPrivacy=true; %>
</security:oscarSec>
<%
/* suppose that access is controlled by containing billingONCorrection.jsp page
List<Site> providerSites = null;
if(isSiteAccessPrivacy) {
	SiteDao siteDao = (SiteDao)WebApplicationContextUtils.getWebApplicationContext(application).getBean("siteDao");
	providerSites = siteDao.getActiveSitesByProviderNo(providerNo);
}
List<Provider> teamProviders = null;
if(isTeamBillingOnly || isTeamAccessPrivacy) {
	ProviderDao providerDao = (ProviderDao)WebApplicationContextUtils.getWebApplicationContext(application).getBean("providerDao");
 	teamProviders = providerDao.getActiveTeamProviders(providerNo);
}
*/
NumberFormat currency = NumberFormat.getCurrencyInstance();
BigDecimal paymentParam = BigDecimal.valueOf(0);

String billingNo = request.getParameter("billingNo");
if(billingNo == null) errors.add("Wrong parameters");

%>

<html>
<head>
<link rel="stylesheet" type="text/css" media="all"
	href="../../../share/calendar/calendar.css" title="win2k-cold-1" />

<script type="text/javascript" src="../../../share/calendar/calendar.js"></script>
<script type="text/javascript"
	src="../../../share/calendar/lang/<bean:message key="global.javascript.calendar"/>"></script>
<script type="text/javascript" src="../../../share/calendar/calendar-setup.js"></script>
<script type="text/javascript">
function onEditPayment(id, sum, date, type) {
	document.getElementById('paymentId').value = id;
	document.getElementById('payment').value = sum;
	document.getElementById('paymentDate').value = date;
	
<logic:present name="billingPaymentTypeList" scope="request">
    <logic:iterate id="billingPaymentType" name="billingPaymentTypeList">
	elem = document.getElementById('paymentType<bean:write name='billingPaymentType' property='id'/>');
	if(type == '<bean:write name='billingPaymentType' property='id'/>') elem.checked=true;
	else elem.checked=false;
    </logic:iterate>
</logic:present>	
	
}
function onDeletePayment(id) {
	window.location.href='billingON3rdPayments.do?method=deletePayment&id='+id+'&billingNo='+<%= billingNo %>;
}
function checkInput() {
	var validInput = true;
	elem = document.getElementById('payment');
	if(elem.value == '0.00') {
	    alert('Non-zero payment value is required');
	    validInput = false;
	}    
	elem = document.getElementById('paymentDate');
	if(elem.value == null || elem.value == '') {
	    alert('Payment Date is required');
	    validInput = false;
	}
	if(validInput) document.forms['editPayment'].submit();
	return false;
}

</script>
</head>

<body bgcolor="ivory" text="#000000" topmargin="0" leftmargin="0"
	rightmargin="0" onload="refreshParent()">


<logic:present name="billingPaymentTypeList" scope="request">
    <form name="editPayment" id="editPayment" method="get" action="<%=request.getContextPath() %>/billing/CA/ON/billingON3rdPayments.do">
  	<input type="hidden" name="method" value="savePayment" />
  	<input type="hidden" name="billingNo" value="<%= billingNo %>" />
  	<input type="hidden" name="id" id="paymentId" value="" />
  	<table border=0 cellspacing=0 cellpadding=0 width="100%" >
    		<tr  bgcolor="#CCCCFF">
      			<th><font face="Helvetica">ADD / EDIT PAYMENT</font></th>
    		</tr>
  	</table>
  	<table border="0" cellpadding="0" cellspacing="0" width="100%">
  	  <tr>
  	    <td width="100%">
      	  <table BORDER="3" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" height="100%" BGCOLOR="#C0C0C0">
      	    <tr BGCOLOR="#EEEEFF">
      	      <td width="30%">
      	        <div align="right"><font face="arial">Payment:</font></div>
      	      <td>
      	      <td width="70%" align="left">
      	        <input type="text" name="payment" id="payment" value="0.00" WIDTH="8" HEIGHT="20" border="0" hspace="2" maxlength="50">
      	      </td>
      	    </tr>
      	    <tr BGCOLOR="#EEEEFF">
      	      <td>
      	        <div align="right"><font face="arial">Date:</font></div>
      	      <td>
      	      <td align="left">
      	        <input type="text" name="paymentDate" id="paymentDate" onDblClick="calToday(this)" size="10" value="">
				<a id="btn_date"><img title="Calendar" src="../../../images/cal.gif" alt="Calendar" border="0" /></a>
      	      </td>
      	    </tr>
      	    <tr BGCOLOR="#EEEEFF">
      	      <td>
      	        <div align="right"><font face="arial">Payment Type:</font></div>
      	      <td>
      	      <td align="left">
		<table width="100%">
		<logic:iterate id="billingPaymentType" name="billingPaymentTypeList" indexId="ttr">
		    <%= ttr.intValue()%2 == 0 ? "<tr>" : "" %>
		      <td width="50%">
			<input type="radio" name="paymentType" id="paymentType<bean:write name='billingPaymentType' property='id'/>" value="<bean:write name='billingPaymentType' property='id'/>" <%=(ttr==0 ? "checked=true" : "")%> />
		        <bean:write name="billingPaymentType" property="paymentType"/>		    	
		      </td>  
		    <%= ttr.intValue()%2 == 0 ? "" : "</tr>" %>
		</logic:iterate>
		</table>
  	    </td>
  	  </tr>

	</table>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"> 
	  <tr bgcolor="#CCCCFF"> 
      	    <TD nowrap align="center"> 
      	      <input type="submit" name="submitBtn" value="    Save  " onClick="checkInput(); return false;" /> 
      	    </TD> 
    	  </tr>
    	</table>
    </form>
</logic:present>

<%
List<BillingONPayment> payments = (List<BillingONPayment>)request.getAttribute("paymentsList");

String billClinic = null;
int count = 0;
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:MM");
BigDecimal sum = BigDecimal.valueOf(0);
BigDecimal balance = BigDecimal.valueOf(0);

org.oscarehr.billing.CA.ON.model.BillingClaimHeader1 ch1 = null;
if(payments != null && payments.size()>0) {
    ch1 = payments.get(0).getBillingONCheader1();
    for(BillingONPayment payment : payments) {
	sum = sum.add(payment.getPayment());
    }
    balance = new BigDecimal(ch1.getTotal().replace("$","").replace(",","").replace(" ",""));
    balance= balance.subtract(sum);
    request.setAttribute("balance", currency.format(balance));	
}    
%>
<table border=0 cellspacing=0 cellpadding=0 width="100%" >
    	<tr  bgcolor="#CCCCFF">
    		<th><font face="Helvetica">PAYMENTS LIST</font></th>
    	</tr>
	<br/>
	<table width="100%" border="0">
		<tr><th align="left">#</th><th align="left">Payment</th><th align="left">Date</th><th align="left">Type</th><th></th>
<logic:present name="paymentsList" scope="request">
		<logic:iterate id="displayPayment" name="paymentsList" indexId="ctr">
			<tr>			    
			    <td><%= ctr+1 %></td>
			    <td><bean:write name="displayPayment" property="payment" /> </td>
			    <td><bean:write name="displayPayment" property="paymentDateFormatted" /> </td>
			    <td><bean:write name="displayPayment" property="billingPaymentType.paymentType" /> </td>
			    <td><a href="#" onClick="onEditPayment('<bean:write name="displayPayment" property="id" />',
			    	'<bean:write name="displayPayment" property="payment" />',
			    	'<bean:write name="displayPayment" property="paymentDateFormatted" />',
			    	'<bean:write name="displayPayment" property="billingPaymentType.id" />')">edit</a>
			    	&nbsp;&nbsp;&nbsp; <a href="#" onClick="onDeletePayment(<bean:write name="displayPayment" property="id" />)">delete</a>
			    </td>	
			</tr>    
		</logic:iterate>
</logic:present>
		<tr><td/><td/><td><b>Total:</b></td><td><b><%= currency.format(sum) %></b>
		<tr><td/><td/><td><b>Balance:</b></td><td><b><%= currency.format(balance) %></b>

	</table>

<%      for(String error : errors) {   %>
	Error: <%= error %><br>
<%	}      %>

</body>
</html>
<script type="text/javascript">
    Calendar.setup( { inputField : "paymentDate", ifFormat : "%Y-%m-%d", showsTime :false, button : "btn_date", singleClick : true, step : 1 } );
    function refreshParent() {
    	window.opener.document.getElementById('payment').innerHTML =  '<%= request.getAttribute("total")==null ? "$0.00" : request.getAttribute("total") %>';
    //	window.opener.document.getElementById('refund').innerHTML =  '<%= request.getAttribute("refund")==null ? "$0.00" : request.getAttribute("refund") %>';
    	window.opener.document.getElementById('balance').innerHTML =  '<%= request.getAttribute("balance")==null ? "$0.00" : request.getAttribute("balance") %>';
    	
    }
</script>
