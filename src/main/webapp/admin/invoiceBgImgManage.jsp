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
<% 
if(session.getValue("user") == null) response.sendRedirect("../../logout.jsp");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String result = (String)request.getAttribute("result");
if (result == null) {
	result = "";
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/share/css/OscarStandardLayout.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/Oscar.js"></script>
<title>Set invoice letter head</title>
</head>

<script type="text/javascript">

function isPictureSuffix(suffix) {
	if (suffix.length == 0) {
		return false;
	}
	suffix = suffix.toLowerCase();
	if (suffix != "jpg" && suffix != "png" && suffix != "jpeg" && suffix != "bmp" && suffix != "gif") {
		return false;
	}
	return true;
}

function checkFormAndDisable(){
	var fileName = document.forms[0].bgImage.value;
	if (fileName.length == 0) {
		alert("<bean:message key="eform.uploadhtml.msgFileMissing"/>");
		return false;
	} else {
		var index = fileName.lastIndexOf(".");
		var suffix = "";
		if (index != -1) {
			suffix = fileName.substr(index + 1);
			suffix = suffix.trim();
		} 
		if (!isPictureSuffix(suffix)) {
			alert("The format isn't supported, please upload 'JPG,JPEG,PNG,BMP,GIF' format image!");
			return false;
		}
		document.forms[0].subm.value = "<bean:message key="eform.uploadimages.processing"/>";
		document.forms[0].subm.disabled = true;
		document.forms[0].submit();
	}
}

</script>
<body>
	<form method="POST" onsubmit="return checkFormAndDisable()" enctype="multipart/form-data" action="<%=request.getContextPath() %>/billing/CA/ON/uploadInvoiceBgImg.do">
		<table align="center" class="MainTable">
			<tr class="MainTableTopRow">
				<td class="MainTableTopRowLeftColumn" width="175">
					<bean:message key="admin.admin.uploadInvoiceBgImage" />
				</td>
				<td class="MainTableTopRowRightColumn">
					<table class="TopStatusBar">
						<tr>
							<td>Upload <!--i18n--></td>
							<td>&nbsp;</td>
							<td style="text-align: right">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<table>
						<tr>
							<td>Please select the image file:</td>
							<td><input type="file" name="bgImage"></td>
							<td><input type="submit" name="subm" value="Upload the image"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<%if (!result.isEmpty()) {%>
					<b>Upload result: </b><span style="color:red"><%=result %></span>
					<%} %>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>