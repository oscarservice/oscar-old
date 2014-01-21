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
<%@page import="java.awt.ItemSelectable"%>
<%@page import="oscar.oscarBilling.ca.on.model.BillingOnItem"%>
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
<%@page import="org.oscarehr.billing.CA.ON.dao.BillingONExtDao" %>
<%@page import="org.oscarehr.billing.CA.ON.model.BillingONExt" %>
<%@page import="org.oscarehr.billing.CA.ON.dao.BillingOnItemPaymentDao" %>
<%@page import="org.oscarehr.billing.CA.ON.model.*" %>
<%@page import="org.oscarehr.billing.CA.ON.vo.*" %>
<%@page import="java.math.BigDecimal" %>
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
	List<BillingItemPaymentVo> items = (List<BillingItemPaymentVo>)request.getAttribute("itemPaymentList");
	List<BillingONPayment> paymentLists = (List<BillingONPayment>)request.getAttribute("paymentsList");
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
<script type="text/javascript" src="<%=request.getContextPath()%>/share/javascript/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript">

function onViewPayment(id) {
	jQuery.ajax({
		url: "<%=request.getContextPath()%>/billing/CA/ON/billingON3rdPayments.do",
		type: "GET",
		async: "false",
		timeout: 30000,
		data: {method:"viewPayment",paymentId:id},
		dataType: "json",
		success: function (data) {
			if (data == null || data.length == 2) {
				return;
			}
			var paymentDateObj = data.shift();
			var paymentTypeObj = data.shift();
			// hide save button
			jQuery("#saveBtn").css("display", "none");
			jQuery("#editBtn").css("display", "inline");
			// clear all values
			jQuery("select option[value='payment']").attr("selected", "selected");
			jQuery("tr[id^='itemPayment'] input[id^='payment'] ").val("0.00");
			jQuery("tr[id^='itemPayment'] input[id^='discount'] ").val("0.00");
			jQuery("tr[id^='itemPayment'] input[id^='discount'] ").attr("disabled", "false");
			jQuery("input[name='paymentType']").filter("input[value='" + paymentTypeObj.paymentType + "']")[0].checked=true;
			jQuery("#paymentDate").val(paymentDateObj.paymentDate);
			
			jQuery(data).each(function () {
				var elem = jQuery("#itemPayment" + this.id);
				if (elem == null) {
					return;
				}
				if (this.type == "payment") {
					elem.find("select")[0].selectedIndex=0;
					elem.find("input[id^='payment']")[0].value=this.payment;
					elem.find("input[id^='discount']")[0].value=this.discount;
				} else if (this.type == "refund") {
					elem.find("select")[0].selectedIndex=1;
					elem.find("input[id^='payment']")[0].value=this.refund;
					elem.find("input[id^='discount']")[0].disabled=true;
				}
			});
		},
		error: function() {
			alert("Error happeded!");
		}
	});
}

function clickEditBtn() {
	jQuery("#editBtn").css("display", "none");
	jQuery("select option[value='payment']").attr("selected", "selected");
	jQuery("tr[id^='itemPayment'] input[id^='payment'] ").val("0.00");
	jQuery("tr[id^='itemPayment'] input[id^='discount'] ").val("0.00");
	jQuery("input[name='paymentType']")[0].checked=true;
	jQuery("#paymentDate").val("");
	jQuery("#saveBtn").css("display", "inline");
}

function checkInput() {
	var validInput = true;
	
	elem = document.getElementById('paymentDate');
	if(elem.value == null || elem.value == '') {
	    alert('Payment Date is required');
	    validInput = false;
	}
	if(validInput) document.forms['editPayment'].submit();
	return false;
}

function setStatus(selIndex, idx){
	switch (selIndex) {
	case 0:
		document.getElementById("discount" + idx).disabled=false;
		break;
	case 1:
		document.getElementById("discount" + idx).disabled=true;
		break;
	}
}

</script>
<title><bean:message key="admin.admin.editBillPaymentList"/></title>
</head>

<body bgcolor="ivory" text="#000000" topmargin="0" leftmargin="0"
	rightmargin="0" onload="refreshParent()">


<logic:present name="paymentTypeList" scope="request">
    <form name="editPayment" id="editPayment" method="GET" action="<%=request.getContextPath() %>/billing/CA/ON/billingON3rdPayments.do">
	  	<input type="hidden" name="method" value="savePayment" />
	  	<input type="hidden" name="billingNo" value="<%= billingNo %>" />
	  	<input type="hidden" name="id" id="paymentId" value="" />
	  	<table border=0 cellspacing=0 cellpadding=0 width="100%" >
	    		<tr  bgcolor="#CCCCFF">
	      			<th><font face="Helvetica">ADD / EDIT PAYMENT</font></th>
	    		</tr>
	  	</table>
		
		<table BORDER="3" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" BGCOLOR="#C0C0C0">
		<%
		for(int i=0;i<items.size();i++){ 
			BillingItemPaymentVo vo = items.get(i);
			BigDecimal itemBalance = vo.getTotal().subtract(vo.getPaid()).subtract(vo.getDiscount()).subtract(vo.getRefund());
			String sign = "";
			if (itemBalance.compareTo(BigDecimal.ZERO) == -1) {
				sign = "-";
			}
		%>
			<tr id="itemPayment<%=vo.getItemId() %>" BGCOLOR="#EEEEFF">
				<td width="30%">
					<div align="right">
						<select id="sel<%=i%>" name="sel<%=i%>" onchange="setStatus(this.selectedIndex,<%=i%>);">
							<option value="payment">Payment</option>
	       		 			<option value="refund">Refund</option>
						</select>
	        		</div>
	      		</td>
				<td width="70%" align="left">
					<input type="text" name="payment<%=i %>" id="payment<%=i %>" value="0.00" WIDTH="8" HEIGHT="20" border="0" hspace="2" maxlength="50" />
					Discount     <input type="text" id="discount<%=i%>"name="discount<%=i %>" value="0.00">
				</td>
    		</tr>
			<tr BGCOLOR="#EEEEFF">
				<td>
					<div></div>
				</td>
				<td align="left">
					Service Code:&nbsp;<b><%=vo.getServiceCode()%>&nbsp;$<%=vo.getTotal() %>&nbsp;Balance:&nbsp;<%=sign %><%=currency.format(itemBalance) %></b>
					<input type="hidden" name="itemId<%=i %>" value="<%=vo.getItemId()%>"/>
				</td>
			</tr>
			<%if (i == (items.size() - 1)) {%>
			<tr BGCOLOR="#EEEEFF">
				<td>
					<div align="right"><font face="arial">Payment Type:</font></div>
				</td>
				<td align="left">
					<table width="100%">
						<logic:iterate id="billingPaymentType" name="paymentTypeList" indexId="ttr">
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
			<%} %>
		<%} %>
		</table>
		
		<table border="0" cellpadding="0" cellspacing="0" width="100%"> 
	  		<tr bgcolor="#CCCCFF"> 
      	    	<td nowrap align="center"> 
      	      		 <input type="text" name="paymentDate" id="paymentDate" onDblClick="calToday(this)" size="10" value="">
					<a id="btn_date"><img title="Calendar" src="../../../images/cal.gif" alt="Calendar" border="0" /></a>
      	      		<input type="submit" id="saveBtn" name="submitBtn" value="    Save  " onClick="checkInput(); return false;" />
      	      		<input type="button" id="editBtn" style="display:none" value="    Edit  " onClick="clickEditBtn(); return true;" />
      	    	</td> 
    	  	</tr>
    	</table>
    	<input type="hidden" name="size" value="<%=items.size() %>">
    </form>
</logic:present>

<%
	BigDecimal sum = BigDecimal.valueOf(0);
	BigDecimal balance = BigDecimal.valueOf(0);
	int index = 0;
	List balances = new ArrayList();
	if(paymentLists != null && paymentLists.size()>0) {
		BigDecimal total = new BigDecimal(paymentLists.get(0).getBillingONCheader1().getTotal());
		for(int i=0;i<paymentLists.size();i++){
			balance = BigDecimal.ZERO;
			BigDecimal payment = paymentLists.get(i).getTotal_payment();
			BigDecimal discount = paymentLists.get(i).getTotal_discount();
			BigDecimal refund = paymentLists.get(i).getTotal_refund();
		    balance = total.subtract(payment).subtract(discount).subtract(refund);
		    balances.add(balance);
		}
	}
	sum = (BigDecimal)request.getAttribute("totalInvoiced");
	balance = (BigDecimal)request.getAttribute("balance");
%>

	<table border=0 cellspacing=0 cellpadding=0 width="100%" >
    	<tr  bgcolor="#CCCCFF">
    		<th><font face="Helvetica">PAYMENTS LIST</font></th>
    	</tr>
		<br/>
		<table width="100%" border="0">
			<thead>
				<tr>
					<th align="left">#</th>
					<th align="left">Payment</th>
					<th align="left">Date</th>
					<th align="left">Discount</th>
					<th align="left">Refund</th>
					<th align="left">Balance</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			<logic:present name="paymentsList" scope="request">
				<logic:iterate id="displayPayment" name="paymentsList" indexId="ctr">
				<tr>			    
				    <td><%= ctr+1 %></td>
				    <td><bean:write name="displayPayment" property="total_payment" /> </td>
				    <td><bean:write name="displayPayment" property="paymentDateFormatted" /> </td>
				    <td><bean:write name="displayPayment" property="total_discount" /> </td>
				    <td><bean:write name="displayPayment" property="total_refund" /> </td>
					<%if(((BigDecimal)balances.get(index)).compareTo(BigDecimal.ZERO) == -1){%>
				    <td><%= "-" + currency.format(balances.get(index++)) %> </td>
				    <%}else{ %>
				    <td><%= currency.format(balances.get(index++)) %> </td>
				    <%} %>
				    <td><a href="#" onClick="onViewPayment('<bean:write name="displayPayment" property="id" />');return false;">view</a>
				    </td>	
				</tr>    
				</logic:iterate>
			</logic:present>
			<tr><td/><td/><td><b>Total:</b></td><td><b><%= currency.format(sum) %></b>
			<%if (balance.compareTo(BigDecimal.ZERO) == -1) { %>
			<tr><td/><td/><td><b>Balance:</b></td><td><b><%= "-" + currency.format(balance) %></b>
			<%} else { %>
			<tr><td/><td/><td><b>Balance:</b></td><td><b><%= currency.format(balance) %></b>
			<%} %>
			</tbody>
			</table>
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
