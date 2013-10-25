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

<%
	Boolean isModify = false;
	String id = request.getParameter("id");
	String type = request.getParameter("type");
	String titleStr = "Create Billing Payment Type";
	String method = "createType";
	if (id != null && type != null) {
		isModify = true;
		titleStr = "Modify Billing Payment Type";
		method = "editType";
	} else {
		type = "";
	}
%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=titleStr%></title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript">

function check() {
	if (document.getElementById("paymentType").value.length < 1) {
		alert("Payment type can not be empty!");
		return false;
	}
	return true;
}

function createType() {
	if (!check()) {
		return;
	}
	$.ajax({
		type:"GET",
		async: true,
		data: {paymentType:document.getElementById("paymentType").value},
		url:"<%=request.getContextPath()%>/billing/CA/ON/managePaymentType.do?method=<%=method%>",
		dataType: "json",
		success: function(ret){
			if (!ret) {
				alert("Failed to create new payment type!");
			} else if (ret.ret=="1") {
				alert(ret.reason);
			} else {
				alert("Success");
				history.back();
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			if (textStatus) {
				alert(JSON.toString(textStatus));
			} else if (errorThrown) {
				alert(JSON.toString(errorThrown));
			} else {
				alert("Unknown error happened!");
			}
		}
	});
}

function saveType() {
	if (!check()) {
                return;
        }
	$.ajax({
		type:"GET",
		async: true,
		data: {id:"<%=id%>",oldPaymentType:"<%=type%>", paymentType:document.getElementById("paymentType").value},
		url:"<%=request.getContextPath()%>/billing/CA/ON/managePaymentType.do?method=<%=method%>",
		dataType: "json",
		success : function(ret) {
			if (!ret) {
				alert("Failed to create new payment type!");
			} else if (ret.ret == "1") {
				alert(ret.reason);
			} else {
				alert("Success");
				history.back();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (textStatus) {
				alert(JSON.toString(textStatus));
			} else if (errorThrown) {
				alert(JSON.toString(errorThrown));
			} else {
				alert("Unknown error happened!");
			}
		}
	});
}
</script>

</head>

<body>
	<table width="100%">
		<tbody>
			<tr bgcolor="#CCCCFF">
				<th><%=titleStr%></th>
			</tr>
		</tbody>
	</table>
	<p />
	<p />

	<center>
		<input id="paymentType" name="paymentType" type="text"
			value="<%=type%>" placeholder="Please input a new payment type"
			size="38" />
		<%
			if (isModify) {
		%>
		<input name="save" type="button" onclick="saveType()" value="save" />
		<%
			} else {
		%>
		<input name="create" type="button" onclick="createType()"
			value="create" />
		<%}%>
		<input name="back" type="button" onclick="history.back();" value="back" />
	</center>
</body>
</html>
