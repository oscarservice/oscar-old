<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.oscarehr.billing.CA.model.BillingPaymentType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manage Billing Payment Type</title>

<script type="text/javascript">

function editType(typeId) {
	// 1.show a input field
	// 2.show a save button
}

function saveType(typeName) {
	
} 

</script>

</head>

<%
	List<BillingPaymentType> paymentTypeList = (List<BillingPaymentType>)request.getAttribute("paymentTypeList");
%>

<body>
<div>
	<table>
		<thead>
			<tr>
				<td>Id</td>
				<td>Type</td>
				<td></td>
			</tr>
		</thead>
		<tbody>
			<%for (BillingPaymentType paymentType : paymentTypeList) {%>
				<tr>
					<form name="typeForm_<%=paymentType.getId()%>" onsubmit="return false;" action="<%=request.getContextPath()%>/billing/CA/ON/managePaymentType.do">
						<td><%=paymentType.getId() %></td>
						<td id="typeName_<%=paymentType.getId()%>"><%=paymentType.getPaymentType() %></td>
						<td id="typeOperate_<%=paymentType.getId()%>"><a href="#" onclick="editType(<%=paymentType.getId()%>)">Edit</a></td>
					</form>
				</tr>
			<%} %>
		</tbody>
	</table>
</div>


</body>
</html>