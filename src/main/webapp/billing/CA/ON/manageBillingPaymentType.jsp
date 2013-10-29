<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.oscarehr.billing.CA.model.BillingPaymentType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<style type="text/css">
body {
	font-size: 18px;
	font-family: Verdana;
}
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manage Billing Payment Type</title>

</head>

<%
	List<BillingPaymentType> paymentTypeList = (List<BillingPaymentType>) request
			.getAttribute("paymentTypeList");
%>

<body>
	<table width="100%">
		<tbody>
			<tr bgcolor="#CCCCFF">
				<th>Manage Billing Payment Type</th>
			</tr>
		</tbody>
	</table>
	<p />
	<p />

	<table width="100%">
		<tbody>
			<tr bgcolor="#CCCCFF">
				<th>Id</th>
				<th>Type</th>
				<th></th>
			</tr>
			<%
				int count = 0;
				String bgColor = "white";
				for (BillingPaymentType paymentType : paymentTypeList) {
					count++;
					if (count % 2 == 0) {
			%>
			<tr bgcolor="white">
				<%
					} else {
				%>
			
			<tr bgcolor="#EEEEFF">
				<%
					}
				%>
				<td><%=paymentType.getId()%></td>
				<td><%=paymentType.getPaymentType()%></td>
				<td><a
					href="<%=request.getContextPath()%>/billing/CA/ON/editBillingPaymentType.jsp?id=<%=paymentType.getId()%>&type=<%=paymentType.getPaymentType()%>">Edit</a></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<p />
	<hr />
	<center style="font-family: Verdana">
		<a
			href="<%=request.getContextPath()%>/billing/CA/ON/editBillingPaymentType.jsp">Create
			a new payment type</a>
	</center>
</body>
</html>
