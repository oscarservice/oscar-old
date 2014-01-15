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
<html>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

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

<script src="../js/jquery.js"></script>
<script type="text/javascript">
function onClickUpdate()
{
	jQuery("[name=importDemoForm]").submit();
}

function onClickCancel()
{
	jQuery("[name=actionVal]").val("cancel_update");
	jQuery("[name=importDemoForm]").submit();
}
</script>

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
	<html:form action="/demographic/importNewDemoAction.do" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="actionVal" value="update">
		<div class="<%=cls%>" align="center"><span class="status_msg"><%=status_msg %></span>
			<%if(status!=null && status.equalsIgnoreCase("DEMO_EXISTS")){ %>		
				 <input type="hidden" name="DEMO_UPDATE_KEY" value="<%=request.getAttribute("DEMO_UPDATE_KEY")%>">
				 <input type="button" name="btn_import" value="Update Demographic" onclick="onClickUpdate();">
				 <input type="button" name="btn_cancel" value="Cancel" onclick="onClickCancel();">
			<%} %>
		</div>
	</html:form>	
</body>
</html>
