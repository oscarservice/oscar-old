<html>
<head>
<style>
.success
{
	color: green;
}
.error
{
	color: red;
}
.div_importing
{
	color: black;
}
.status_msg
{
	background-color: #E2DA7F;
	border: 1px solid #BFB75D;
	/*background-color: yellow;*/
	font-weight: bold;
	font-size: 14px;
	width: auto;
}
</style>	
</head>

<%
String actionVal = request.getParameter("actionVal");
if(actionVal!=null && actionVal.equalsIgnoreCase("show_importing"))
{
	%>
	</head>
	<body style="margin: 0 !important; margin-top: 2px !important;"><div align="center" class="status_msg div_importing">Importing Patient.. Please Wait..</div></body></html>
	<%
	return;
}

String status = "", status_msg = "";
if(request.getAttribute("status")!=null)
	status = request.getAttribute("status").toString();
if(request.getAttribute("status_msg")!=null)
	status_msg = request.getAttribute("status_msg").toString();

String cls = "success";
if(status.equalsIgnoreCase("error"))
	cls = "error";
%>

<body style="margin: 0 !important; margin-top: 2px !important;">
	<div class="status_msg <%=cls%>" align="center"><%=status_msg %></div>
</body>
</html>
