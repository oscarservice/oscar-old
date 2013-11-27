<%--

    Copyright (c) 2008-2012 Indivica Inc.

    This software is made available under the terms of the
    GNU General Public License, Version 2, 1991 (GPLv2).
    License details are available via "indivica.ca/gplv2"
    and "gnu.org/licenses/gpl-2.0.html".
 
--%>
<%--
When including the "inWindow" parameter as "true" it is assumed that tabletSignature.jsp 
is hosted in an IFrame and that the IFrame's parent window implements signatureHandler(e)
--%>
<%@ page import="org.oscarehr.util.DigitalSignatureUtils" %>
<%@ page import="org.oscarehr.util.MiscUtils" %>
<%@ page import="org.oscarehr.util.LoggedInInfo" %>
<%@ page import="org.oscarehr.ui.servlet.ImageRenderingServlet"%>
 
<!DOCTYPE html> 
<html lang="en" manifest="cache.manifest"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Signature Pad</title>
<style>


    .signature {
      width: 500px;
      height: 100px;
      background: white;
      border: 1px solid #CCC;
      cursor: crosshair;
      display: block;
    }
    
  </style>
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/share/css/TabletSignature.css" media="screen"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/share/javascript/TabletSignature.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/share/javascript/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/share/javascript/jquery/jquery.form.js"></script>

</head>
<%
String requestIdKey = request.getParameter(DigitalSignatureUtils.SIGNATURE_REQUEST_ID_KEY);
if (requestIdKey == null) {
	requestIdKey = DigitalSignatureUtils.generateSignatureRequestId(LoggedInInfo.loggedInInfo.get().loggedInProvider.getProviderNo());
}
String imageUrl=request.getContextPath()+"/imageRenderingServlet?source="+ImageRenderingServlet.Source.signature_preview.name()+"&"+DigitalSignatureUtils.SIGNATURE_REQUEST_ID_KEY+"="+requestIdKey;
String storedImageUrl=request.getContextPath()+"/imageRenderingServlet?source="+ImageRenderingServlet.Source.signature_stored.name()+"&digitalSignatureId=";
boolean saveToDB = "true".equals(request.getParameter("saveToDB"));
%>
<script type="text/javascript">
var _in_window = <%= "true".equals(request.getParameter("inWindow"))%>;

var requestIdKey = "<%= requestIdKey %>";

var previewImageUrl = "<%= imageUrl %>";

var storedImageUrl = "<%= storedImageUrl %>";

var contextPath = "<%=request.getContextPath() %>";

</script>
<script type="text/javascript" src="<%= request.getContextPath() %>/share/javascript/signature.js"></script>
  
<script type="text/javascript">
    $(function() { 
      $("#sig-canvas").scriptelSigCap();
      $("#draw-sig").click(function() {
        $("#sig-canvas").scriptelSigCap("showScriptel", $("#signature").val()); 
        return false;
      });
      $("#clear").click(function() {
        $("#sig-canvas").scriptelSigCap("clear");
        $("#signature").val("");

        return false;
      });
    });

    function showSignature(){
    	$("#sig-canvas").scriptelSigCap("showScriptel", $("#signature").val());
    	return false;
    	}
    var timeroutHandle = null;
    //var timerIntervalHandle = null;
    function clearRefreshInterval() {
    	/*if (timerIntervalHandle != null) {
    	  clearInterval(timerIntervalHandle);
    	}*/
    	if (timeroutHandle != null) {
    		clearTimeout(timeroutHandle);
    	}
    	showSignature();
    }

    function onkeyuphdlr(){
    	if (timeroutHandle != null) {
    		clearTimeout(timeroutHandle);
    	}/* else {
    		if (null == timerIntervalHandle) {
    		  timerIntervalHandle = setInterval(showSignature, 50);
    		}
    	}*/
    	timeroutHandle = setTimeout(clearRefreshInterval, 10);
    }

    function myreflush(){
    	window.location.reload();
   	}

 </script>

<body style="background-color: #555">

<div class="verticalCenterDiv">
	<div class="centerDiv">
   <div id="sig-canvas" class="signature shadow-inset"  style="display: block;position: relative;"></div>
		<div><span id="signMessage" style="color:#FFFFFF;"></span><button id="clear" style="display:inline" onclick="myreflush()">Clear</button><button id="save" style="display:inline;">Save</button></div>
	</div>
</div>

<form onsubmit="return submitSignature();" action="<%=request.getContextPath() %>/signature_pad/uploadSignature.jsp" id="signatureForm" method="POST">
	<input type="text" name="signature" id="signature"  onkeyup="onkeyuphdlr();" style="position: absolute; top: 104px;left: 200px;"/>
  <input id="draw-sig" type="submit" value="import" style="position: absolute; top: 103px;left: 360px; display:none;"/>
  <input id="clear-sig" type="submit" value="Clear"style="position: absolute; top: 103px;left: 428px;display:none;"/><br />
	<input type="hidden" id="signatureImage" name="signatureImage" value="" />
	<input type="hidden" name="source" value="IPAD" />
	<input type="hidden" name="<%=DigitalSignatureUtils.SIGNATURE_REQUEST_ID_KEY %>" value="<%= requestIdKey %>" />
	<input type="hidden" name="demographicNo" value="<%= request.getParameter("demographicNo") %>" />
	<input type="hidden" name="saveToDB" value="<%=saveToDB%>" />
</form>

</body>
</html>
