<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@ page language="java"%>
<%@ page import="oscar.form.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
	String formClass = "IvfTrackSheet";
	String formLink = "formivftracksheet.jsp";

    int demoNo = Integer.parseInt(request.getParameter("demographic_no"));
    int formId = Integer.parseInt(request.getParameter("formId"));
	int provNo = Integer.parseInt((String) session.getAttribute("user"));
	FrmRecord rec = (new FrmRecordFactory()).factory(formClass);
    java.util.Properties props = rec.getFormRecord(demoNo, formId);
	
%>

<html:html locale="true">
<% response.setHeader("Cache-Control","no-cache");%>
<head>
<title>IVF Track Sheet</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="formovulation.css" />
<link rel="stylesheet" type="text/css" media="print" href="print.css">
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/share/calendar/calendar.css" title="win2k-cold-1" />
<script type="text/javascript" src="<%=request.getContextPath()%>/share/calendar/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/share/calendar/lang/<bean:message key="global.javascript.calendar"/>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/share/calendar/calendar-setup.js"></script>


<style type="text/css" media="print">
    BODY
    {
        font-size:100%;
    }
    TABLE
    {
        font-size:100%;
    }
</style>

<style type="text/css">
<!--
.demo  {color:#000033; background-color:#cccccc; layer-background-color:#cccccc;
        position:absolute; top:150px; left:270px; width:80px; height:120px;
        z-index:99;  visibility:hidden;}
.demo1  {color:#000033; background-color:silver; layer-background-color:#cccccc;
        position:absolute; top:40px; left:370px; width:190px; height:80px;
        z-index:99;  visibility:hidden;}
.demo2  {color:#000033; background-color:silver; layer-background-color:#cccccc;
        position:absolute; top:40px; left:370px; width:190px; height:80px;
        z-index:99;  visibility:hidden;}
.demo3  {color:#000033; background-color:silver; layer-background-color:#cccccc;
        position:absolute; top:220px; left:300px; width:80px; height:30px;
        z-index:99;  visibility:hidden;}
.demo4  {color:#000033; background-color:silver; layer-background-color:#cccccc;
        position:absolute; top:50px; left:280px; width:80px; height:30px;
        z-index:99;  visibility:hidden;}
-->
</style>
<html:base/>

<style type="text/css">
<!--
body {
	background-color: #FFCCCC;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.style2 {color: #FFFFFF}
.style3 {
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.style4 {	font-family: Arial, Helvetica, sans-serif;
	font-size: 18px;
	color: #677677;
	font-weight: bold;
}


.style6 {font-size: 12}
.style7 {font-family: Arial, Helvetica, sans-serif;
	font-size: 12;
	color: #677677;
}

.style12 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 18px;
	font-weight: bold;
	color: #FFFFFF;
}
.style13 {color: #999999}
.style14 {color: #003399}
.style17 {	color: #999999;
	font-family: Arial, Helvetica, sans-serif;
}


.style28 {color: #0000FF}
.style29 {color: red; font-family: Arial, Helvetica, sans-serif; font-weight: bold;  font-size: 16px; }
.style36 {font-family: Arial, Helvetica, sans-serif; color: #FFFFFF; }

.style37 {color: #CCCCCC}
.style38 {font-family: Arial, Helvetica, sans-serif; color: #CCCCCC; }
.style39 {font-family: Arial, Helvetica, sans-serif; color: #CCCCCC; font-weight: bold; }

.style42 {color: #FFFFFF; font-size: 36px; }


.style44 {font-weight: bold; font-size: 12px; font-family: Arial, Helvetica, sans-serif;}
.style46 {color: #FFFFFF; font-size: 18px; }

.style48 {color: #FFFFFF; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 12px; }
.style49 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #003399; font-weight: bold; }

.style51 {	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	color: #003399;
	font-weight: bold;
}
.style52 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; }

.style53 {color: #FFFFFF; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: x-small; }
.style55 {font-weight: bold; font-size: x-small; font-family: Arial, Helvetica, sans-serif; }


.style61 {
	font-size: 14px;
	color: #E91B25;
	font-weight: bold;
}
.style63 {font-size: 18px; color: #003399; }
.style64 {font-size: 12px}
.style68 {font-family: Arial, Helvetica, sans-serif; font-size: 12px; }
.style69 {font-family: Arial, Helvetica, sans-serif; color: #FFFFFF; font-size: 12px; }


.style70 {	
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: blue;
}

.style71 {color: #677677; font-family: Arial, Helvetica, sans-serif; font-size: 11px; }

.style72 {color: #677677; font-family: Arial, Helvetica, sans-serif; font-size: 14px; }

.style73 {color: #677677; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 16px; }

.style74 {	
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	color: blue;
}

.style75 {color: #677677; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 16px; }

.style76 {color: #677677; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 12px; }

.style77 {	font-weight: bold;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #CCCCCC;
}

.style78 {font-size: 14px}
.style79 {color: #999999}


.style80 {
	color: #FFFFFF;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-weight: bold;
}
.style81 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-weight: bold;
}
.style82 {font-size: xx-small}

.style83 {color: #999999; font-size: 14px; font-family: Arial, Helvetica, sans-serif; }

.style84 {color: #677677; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 18px; }

.style85 {font-size: 16px; color: #0066FF; font-weight: bold; }

.style86 {
	color: #0080C0;
	font-weight: bold;
	font-family: Arial, Helvetica, sans-serif;
}

.style87 {font-family: Arial, Helvetica, sans-serif}

.style88 {font-size: 14px}
.style90 {font-size: 12px; color: #CCCCCC; font-family: Arial, Helvetica, sans-serif;}
.style91 {font-size: 10px}
.style92 {font-weight: bold; font-size: 12px; font-family: Arial, Helvetica, sans-serif; color: #677677; }
.style93 {color: #003399; font-weight: bold; font-family: Arial, Helvetica, sans-serif;}

.style94 {color: #CCCCCC}
.style96 {font-size: 12px}
.style97 {font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #003399; font-weight: bold; }
.style98 {color: #4D93FB; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 12px; }

.style99 {font-size: 12px}

.style101 {color: #677677}
.style102 {
	font-size: 12px;
	color: #677677;
	font-family: Arial, Helvetica, sans-serif;
}

.style103 {font-family: Arial, Helvetica, sans-serif}
.style105 {font-weight: bold; font-size: 12px; }
.style106 {color: #003399}
.style107 {font-weight: bold; font-size: 12px; font-family: Arial, Helvetica, sans-serif; color: #677677; }

.style113 {color: #FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 9px; font-weight: bold; }
.style116 {color: #91C0D0}
.style120 {color: #FF0000}

.style121 {font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 16px; }
.style122 {font-size: 16}
.style124 {font-size: 16px}



.style127 {color: #0080C0; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 18px; }
.style138 {color: #33BDFF}
.style139 {
	color: #91C0D0;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
}


.style140 {
	color: #0080C0;
	font-family: Arial, Helvetica, sans-serif;
}

.style141 {color: #999999; font-size: 10px; }

.style142 {color: #003399}
.style143 {
color: #0080C0; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 16px;
}
.style144 {font-family: Arial, Helvetica, sans-serif; font-size: 12px; color: #677677;}

.style145 {
	font-size: 14px;
	color: #677677;
}
.style147 {color: #006699}
.style148 {color: #677677}


.style151 {color: #677677; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 14px; }

.buttons {
color: #0080C0; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 12px;
}
.buttons1 {color: #0080C0; font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 12px;
}
a:active {
	text-decoration: none;
}


a:link {
	color: #FFFFFF;
	text-decoration: none;
}
a:visited {
	color: #FFFFFF;
	text-decoration: none;
}
a:hover {
	color: #91C0D0;
	text-decoration: none;
}

-->
</style>


<script language="Javascript"><!--

var fieldObj;
function assignBackgroundColor(obj)
{
	var flagObj = null;
	var flagObjName = "flag_" + obj.name;
	flagObj = document.getElementsByName(flagObjName);

	if(flagObj[0].value == 'red')
	{
		obj.style.backgroundColor = 'red'; 
		obj.style.color = 'white';
	}
	else
	{
		obj.style.backgroundColor = 'white'; 
		obj.style.color = '#677677';
	}
}
function changeColor(obj)
{
	var flagObjName = "";
	var flagObj = null;
	if(obj.style.backgroundColor == 'white')
	{
		obj.style.backgroundColor = 'red';	
		obj.style.color = 'white';
		flagObjName = "flag_" + obj.name;
		flagObj = document.getElementsByName(flagObjName);
		flagObj[0].value = "red";
	}
	else
	{
		obj.style.backgroundColor = 'white';	
		obj.style.color = '#677677';
		flagObjName = "flag_" + obj.name;
		flagObj = document.getElementsByName(flagObjName);
		flagObj[0].value = "";
	}
}

function showHideBox(layerName, iState) 
{ // 1 visible, 0 hidden
    if(document.layers)	   //NN4+
    {
       document.layers[layerName].visibility = iState ? "show" : "hide";
    } else if(document.getElementById)	  //gecko(NN6) + IE 5+
    {
        var obj = document.getElementById(layerName);
        obj.style.visibility = iState ? "visible" : "hidden";
    } else if(document.all)	// IE 4
    {
        document.all[layerName].style.visibility = iState ? "visible" : "hidden";
    }
}
function showBox(layerName, iState, field, e) { // 1 visible, 0 hidden
    fieldObj = field;
    //get the number of the field
    fieldName = fieldObj.name;
    fieldName = fieldName.substring("pg2_pos".length);

    if(document.layers)	{   //NN4+
       document.layers[layerName].visibility = iState ? "show" : "hide";
    } else if(document.getElementById) {	  //gecko(NN6) + IE 5+
        var obj = document.getElementById(layerName);
        obj.style.top = e.screenY + (481-e.screenY + 26*fieldName);
        obj.style.left = "390px";
        obj.style.visibility = iState ? "visible" : "hidden";
    } else if(document.all)	// IE 4
    {
        document.all[layerName].style.visibility = iState ? "visible" : "hidden";
    }
    fieldObj = field;
}
function showBMIBox(layerName, iState, field, e) { // 1 visible, 0 hidden

    fieldObj = field;
    //get the number of the field
    fieldName = fieldObj.name;
    //fieldName = fieldName.substring("pg2_pos".length);

    if(document.layers)	{   //NN4+
       document.layers[layerName].visibility = iState ? "show" : "hide";
    } else if(document.getElementById) {	  //gecko(NN6) + IE 5+
        var obj = document.getElementById(layerName);
        obj.style.top = e.screenY + (401-e.screenY + 26*fieldName);
        obj.style.left = "30px";
        obj.style.visibility = iState ? "visible" : "hidden";
    } else if(document.all)	// IE 4
    {
        document.all[layerName].style.visibility = iState ? "visible" : "hidden";
    }
    fieldObj = field;
}
function showPGBox(layerName, iState, field, e, prefix, origX, origY, deltaY) { // 1 visible, 0 hidden
    fieldObj = field;
    //get the number of the field
    fieldName = fieldObj.name;
    fieldName = fieldName.substring(prefix.length);
    if (fieldName=="")
	{
    	fieldName=0;  
	}
//alert("Ovulation/showPGBox(): fieldName = " + fieldName);

    if(document.layers)	
	{   //NN4+
//alert("Ovulation/showPGBox(): document.layers = " + document.layers);
       document.layers[layerName].visibility = iState ? "show" : "hide";
    } 
	else if(document.getElementById) 
	{	  //gecko(NN6) + IE 5+
	
//alert("Ovulation/showPGBox(): document.getElementById = " + document.getElementById);
//alert("Ovulation/showPGBox(): layerName = " + layerName);

        var obj = document.getElementById(layerName);
        obj.style.top = e.screenY + (origY-e.screenY + deltaY*fieldName);
        obj.style.left = origX;
        obj.style.visibility = iState ? "visible" : "hidden";
		
		obj.style.visibility = "visible";
		
//alert("Ovulation/showPGBox(): obj.style.visibility = " + obj.style.visibility);
		
    } 
	else if(document.all)
	{// IE 4
        document.all[layerName].style.visibility = iState ? "visible" : "hidden";
    }
    fieldObj = field;
}
function insertBox(str, layerName) { // 1 visible, 0 hidden
    if(document.getElementById)	{
        //var obj = document.getElementById(field);
        fieldObj.value = str;
    }
    showHideBox(layerName, 0);
}
function showDef(str, field) { 
    if(document.getElementById)	{
        field.value = str;
    }
}
function syncDemo() { 
    document.forms[0].c_surname.value = "BRO'WN";
    document.forms[0].c_givenName.value = "PREGNANT";
    document.forms[0].c_address.value = "12 Mockingbird lane";
    document.forms[0].c_city.value = "Pemberton";
    document.forms[0].c_province.value = "BC";
    document.forms[0].c_postal.value = "V2S 1V9";
    document.forms[0].c_phn.value = "9069158251";
    document.forms[0].c_phone.value = "604-778-4593  ";
}


function wtEnglish2Metric(obj) {
	//if(isNumber(document.forms[0].c_ppWt) ) {
	//	weight = document.forms[0].c_ppWt.value;

	if(isNumber(obj) ) {
		weight = obj.value;
		weightM = Math.round(weight * 10 * 0.4536) / 10 ;
		if(confirm("Are you sure you want to change " + weight + " pounds to " + weightM +"kg?") ) {
			//document.forms[0].c_ppWt.value = weightM;
			obj.value = weightM;
		}

	}
}
function htEnglish2Metric(obj) {

	height = obj.value;
	if(height.length > 1 && height.indexOf("'") > 0 ) {
		feet = height.substring(0, height.indexOf("'"));
		inch = height.substring(height.indexOf("'"));
		if(inch.length == 1) {
			inch = 0;
		} else {
			inch = inch.charAt(inch.length-1)=='"' ? inch.substring(0, inch.length-1) : inch;
			inch = inch.substring(1);
		}
		
		//if(isNumber(feet) && isNumber(inch) )
			height = Math.round((feet * 30.48 + inch * 2.54) * 10) / 10 ;
			if(confirm("Are you sure you want to change " + feet + " feet " + inch + " inch(es) to " + height +"cm?") ) {
				obj.value = height;
			}
		//}
	}

}
function calcBMIMetric(wt, ht, obj) {

	if(isNumber(wt) && isNumber(ht)) 
	{
		var weight = parseFloat(wt.value);
		var height = parseFloat(ht.value);

		height = height / 100;

		if(weight > 0  &&  height > 0) 
		{
			obj.value =  "" + Math.round(weight * 10 / height / height) / 10;
		}
	}
}

function calcTMC(volumeObj, densityObj, motilityObj, obj) {

	if(isNumber(volumeObj) && isNumber(densityObj)  &&  isNumber(motilityObj)) 
	{
		var volume = parseFloat(volumeObj.value);
		var density = parseFloat(densityObj.value);
		var motility = parseFloat(motilityObj.value);

		motility = motility / 100;

		if(volume > 0  &&  density > 0  &&  motility > 0) 
		{
//			obj.value =  "" +  Math.round(volume * density * motility);
			obj.value =  "" +  to2DecimalDigits(volume * density * motility);

		}
	}
}

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

function  to2DecimalDigits(decimal)
{
// 649; 649.; 649.0; 649.23; 649.9000000000001
    var decimalDouble = 0.00;
	decimalDouble = decimal;
    var rtnStr = "";

	try
    {
        decimalDouble = (Math.round(decimalDouble * 1000)) / 1000.00;
        rtnStr = "" + decimalDouble;
    }
    catch(ex)
    {
        rtnStr = decimal;
    }


    if(decimal == null)
    {
        return "0.00"; 
    }

    var index = 0;
    
    index = rtnStr.indexOf("."); 

	var pos = rtnStr.length - index;

	if(pos == 3)
			; // in  xxx.xx format already
	else if(pos == 2)
			rtnStr += "0";
	else if(pos == 1)
			rtnStr += "00";
	else if(pos <= 0)
	{
		rtnStr += ".00";
	}
	else if(pos > 4)
	{
		rtnStr = rtnStr.substring(0,index+3);
	}

    return rtnStr;

}

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    function onPrint_old() {
        window.print();
    }

    function onPrint() {
        document.forms[0].submit.value="print"; 			
		
        var ret = checkAllDates();
        if(ret==true)
        {
			document.forms[0].action = "../form/createpdf?__title=IvfTrackSheet+Form&__cfgfile=ivfTrackSheetPrintCfgPg1&__cfgfile=ivfTrackSheetPrintCfgPg2&__template=IvfTrackSheetForm_95";
            document.forms[0].target="_blank";            
        }
        return ret;
    }

    function onSave(urlPath) {
        document.forms[0].submit.value="save";
        var ret = checkAllDates();
//        ret = checkAllNumber();
        if(ret==true) {
            ret = confirm("Are you sure you want to save this form?");
			reset(urlPath);
        }
        return ret;
    }
    function onExit() {
        if(confirm("Are you sure you wish to exit without saving your changes?")==true) {
            window.close();
        }
        return(false);
    }
    function onSaveExit(urlPath) {
        document.forms[0].submit.value="exit";
        var ret = true; //checkAllDates();
//        ret = checkAllNumber();
        if(ret == true) {
            ret = confirm("Are you sure you wish to save and close this window?");
            reset(urlPath);
        }
		
        return ret;
    }

    function reset(urlPath) {
        document.forms[0].target = "";
        document.forms[0].action = urlPath;
	}

	function isNumber(ss){
		var s = ss.value;
        var i;
        for (i = 0; i < s.length; i++){
            // Check that current character is number.
            var c = s.charAt(i);
			if (c == '.') {
				continue;
			} else if (((c < "0") || (c > "9"))) {
                alert('Invalid '+s+' in field ' + ss.name);
                ss.focus();
                return false;
			}
        }
        // All characters are numbers.
        return true;
    }
    function checkAllNumber() {
        var b = true;
        if(isNumber(document.forms[0].pg2_ht1)==false){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht2) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht3) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht4) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht5) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht6) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht7) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht8) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht9) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht10) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht11) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht12) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht13) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht14) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht15) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht16) ){
            b = false;
		} else if(!isNumber(document.forms[0].pg2_ht17) ){
            b = false;
		}
		return b;
	}

/**
 * DHTML date validation script. Courtesy of SmartWebby.com (http://www.smartwebby.com/dhtml/)
 */
// Declaring valid date character, minimum year and maximum year
var dtCh= "/";
var minYear=1900;
var maxYear=9900;

    function isInteger(s){
        var i;
        for (i = 0; i < s.length; i++){
            // Check that current character is number.
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) return false;
        }
        // All characters are numbers.
        return true;
    }

    function stripCharsInBag(s, bag){
        var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.
        for (i = 0; i < s.length; i++){
            var c = s.charAt(i);
            if (bag.indexOf(c) == -1) returnString += c;
        }
        return returnString;
    }

    function daysInFebruary (year){
        // February has 29 days in any year evenly divisible by four,
        // EXCEPT for centurial years which are not also divisible by 400.
        return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
    }
    function DaysArray(n) {
        for (var i = 1; i <= n; i++) {
            this[i] = 31
            if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
            if (i==2) {this[i] = 29}
       }
       return this
    }

    function isDate(dtStr){
        var daysInMonth = DaysArray(12)
        var pos1=dtStr.indexOf(dtCh)
        var pos2=dtStr.indexOf(dtCh,pos1+1)
        var strMonth=dtStr.substring(0,pos1)
        var strDay=dtStr.substring(pos1+1,pos2)
        var strYear=dtStr.substring(pos2+1)
        strYr=strYear
        if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
        if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
        for (var i = 1; i <= 3; i++) {
            if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
        }
        month=parseInt(strMonth)
        day=parseInt(strDay)
        year=parseInt(strYr)
        if (pos1==-1 || pos2==-1){
            return "format"
        }
        if (month<1 || month>12){
            return "month"
        }
        if (day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
            return "day"
        }
        if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
            return "year"
        }
        if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
            return "date"
        }
    return true
    }


    function checkTypeIn(obj) {
      if(!checkTypeNum(obj.value) ) {
          alert ("You must type in a number in the field.");
        }
    }

    function valDate(dateBox)
    {
        try
        {
            var dateString = dateBox.value;
            if(dateString == "")
            {
                return true;
            }
            var dt = dateString.split('/');
            var y = dt[2];  var m = dt[1];  var d = dt[0];
            //var y = dt[0];  var m = dt[1];  var d = dt[2];
            var orderString = m + '/' + d + '/' + y;
            var pass = isDate(orderString);

            if(pass!=true)
            {
                alert('Invalid '+pass+' in field ' + dateBox.name);
                dateBox.focus();
                return false;
            }
        }
        catch (ex)
        {
            alert('Catch Invalid Date in field ' + dateBox.name);
            dateBox.focus();
            return false;
        }
        return true;
    }

    function checkAllDates()
    {
        var b = true;
		
        //if(valDate(document.forms[0].lmp)==false){
        //    b = false;
        //}else
        if(valDate(document.forms[0].date1)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date2)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date3)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date4)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date5)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date6)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date7)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date8)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date9)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date10)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date11)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date12)==false){
            b = false;
        }else
        if(valDate(document.forms[0].date13)==false){
            b = false;
		}else
        if(valDate(document.forms[0].date14)==false){
            b = false;
		}else
        if(valDate(document.forms[0].date15)==false){
            b = false;
		}else
        if(valDate(document.forms[0].date16)==false){
            b = false;
		}else
        if(valDate(document.forms[0].date17)==false){
            b = false;
		}else
        if(valDate(document.forms[0].date18)==false){
            b = false;
		}
        return b;
    }

	function calcWeek(source) {

	    var delta = 0;
        var str_date = getDateField(source.name);
        if (str_date.length < 10) return;
        //var yyyy = str_date.substring(0, str_date.indexOf("/"));
        //var mm = eval(str_date.substring(eval(str_date.indexOf("/")+1), str_date.lastIndexOf("/")) - 1);
        //var dd = str_date.substring(eval(str_date.lastIndexOf("/")+1));
        var dd = str_date.substring(0, str_date.indexOf("/"));
        var mm = eval(str_date.substring(eval(str_date.indexOf("/")+1), str_date.lastIndexOf("/")) - 1);
        var yyyy  = str_date.substring(eval(str_date.lastIndexOf("/")+1));
        var check_date=new Date(yyyy,mm,dd);
        var start=new Date("December 25, 2003");

		if (check_date.getUTCHours() != start.getUTCHours()) {
			if (check_date.getUTCHours() > start.getUTCHours()) {
			    delta = -1 * 60 * 60 * 1000;
			} else {
			    delta = 1 * 60 * 60 * 1000;
			}
		} 

		var day = eval((check_date.getTime() - start.getTime() + delta) / (24*60*60*1000));
        var week = Math.floor(day/7);
		var weekday = day%7;
        source.value = week + "w+" + weekday;

}

	function getDateField(name) {
		var temp = ""; //pg2_gest1 - pg2_date1
		var n1 = name.substring(eval(name.indexOf("t")+1));

		if (n1>17) {
			name = "pg3_date" + n1;
		} else {
			name = "pg2_date" + n1;
		}
        
        for (var i =0; i <document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == name) {
               return document.forms[0].elements[i].value;
    	    }
	    }
        return temp;
    }
function calToday(field) {
	var calDate=new Date();
	field.value = calDate.getDate() + '/' + (calDate.getMonth()+1) + '/' + calDate.getFullYear();
}

function sortedTextValue(num){
	var count;
	var count1;
	var value=[];
	var name;
	var change;
	var num1 = 0;
	
	for(count = 0;count < 15;count ++){
		name = "r_l" + num;
		count = count + 1; 
		name = name + count.toString();
		count = count - 1;
		if(document.getElementById(name).value > 0){
			value[num1] = document.getElementById(name).value;
			num1 ++;
		}
	}
	for(count = 0;count < value.length;count ++){
		for(count1 = 0; count1 < value.length - count;count1 ++){
			if(Number(value[count1]) > Number(value[count1 + 1])){
				change = value[count1];
				value[count1] = value[count1 + 1];
				value[count1 + 1] = change;
			}
		}
	}
	for(count = 0;count < value.length;count ++){
		name = "r_l" + num;
		count = count + 1;
		name = name + count.toString();
		count = count - 1;
		document.getElementById(name).value = value[count];
	}
	if(value.length < 15){
		for(count = value.length + 1; count <= 15;count ++){
			name = "r_l" + num;
			name = name + count.toString();
			document.getElementById(name).value = "";
		}
	}
}

//-->
</script>
</head>

<body>
<html:form action="/form/formname">

<div>
<input type="hidden" name="demographic_no" value="<%= props.getProperty("demographic_no", "0") %>" />
<input type="hidden" name="ID" value="<%= props.getProperty("ID", "0") %>" />
<input type="hidden" name="provider_no" value=<%=request.getParameter("provNo")%> />
<input type="hidden" name="formCreated" value="<%= props.getProperty("formCreated", "") %>" />
<input type="hidden" name="form_class" value="<%=formClass%>" />
<input type="hidden" name="form_link" value="<%=formLink%>" />
<input type="hidden" name="provNo" value="<%= request.getParameter("provNo") %>" />
<input type="hidden" name="submit" value="exit"/>
</div>

<table align="center" width="100%" border="1" cellspacing="0" cellpadding="0">
<tbody>
<tr>
<td>
	<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tbody>
	<tr>
	<td width="66%">
	<table   border="0" cellspacing="0" cellpadding="0">
	<tbody>
	
	<tr>
	<td width="50%">
		<table align="center" border="0" cellspacing="1" cellpadding="1">
		<tbody>
		<tr>
		<td><img src="../form/formivftracksheet.png" style="width:75px;height:100px"/></td>
		<td>
		Ontario Network of Experts in Fertility<br />
		3210 Harvester Road<br />
		Burlington, ON 	L7N 3T1<br />
		Phone: 1-877-663-0223    Fax: (905) 639-3810
		</td>
		</tr>
		</tbody>
		</table>
	</td>
	<td align="center" width="50%">
		<b>IVF Track Sheet </b>
	</td>
	</tr>
	<tr>
		<td>
			<table align="center" border="0" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<td>
					<table align="center" width="100%" border="0" cellspacing="1" cellpadding="1">
					<tbody>
						<tr>
						<td>
						IVF Cycle Number:<input type="text" id="ivfCycleNumber" name="ivfCycleNumber" maxlength="6" value="<%=props.getProperty("ivfCycleNumber", "")%>"/>
						</td> 
						</tr>
						<tr>
						<td>
						LMP:<input type="text" id="lmp" name="lmp" value=""/><img src="<%=request.getContextPath()%>/images/cal.gif" id="pdate_cal">
						<script type="text/javascript">
							Calendar.setup({ inputField : "lmp", ifFormat : "%Y-%m-%d", showsTime :false, button : "pdate_cal", singleClick : true, step : 1 });
						</script>
						</td> 
						</tr>
						<tr>
						<td>
						Funded : &nbsp; Y&nbsp;<input type="checkbox" id="funded_y" name="funded_y" <%=props.getProperty("funded_y", "")%> /> &nbsp;	N&nbsp;<input type="checkbox" id="funded_n" name="funded_n" />
						</td>
						</tr>
						<tr>
						<td>
						G <input type="text" id="g_txt" size="3" name="g_txt" maxlength="2" value="<%=props.getProperty("g_txt", "")%>"/>      P  <input type="text" id="p_txt" size="3" maxlength="2" name="p_txt" value="<%=props.getProperty("p_txt", "")%>"/>     A  	<input type="text" id="a_txt" size="3" maxlength="2" name="a_txt" value="<%=props.getProperty("a_txt", "")%>"/>
						</td> 
						</tr>
						<tr>
						<td>
						Consents:<input type="checkbox" id="consents" name="consents"<%=props.getProperty("consents", "")%> />
						</td> 
						</tr>
						<tr>
						<td>
						BORN Consent Signed :<input type="checkbox" id="bcs" name="bcs"<%=props.getProperty("bcs", "")%> />
						</td> 
						</tr>
						<tr>
						<td>
							<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td colspan="2">
										<u>Sperm </u>
									</td>
									<td colspan="2">
										<u>Treatment Plan  </u>
									</td>
								</tr>
								<tr>
									<td>
										Fresh 
									</td>
									<td>
										<input type="checkbox" id="fresh" name="fresh" <%=props.getProperty("fresh", "")%> />
									</td>
									<td>
										IVF 
									</td>
									<td>
										<input type="checkbox" id="ivf" name="ivf" <%=props.getProperty("ivf", "")%> />
									</td>
								</tr>
								<tr>
									<td>
										Frozen 
									</td>
									<td>
										<input type="checkbox" id="frozen" name="frozen" <%=props.getProperty("frozen", "")%> />
									</td>
									<td>
										ICSI 
									</td>
									<td>
										<input type="checkbox" id="icsi" name="icsi"<%=props.getProperty("icsi", "")%> />
									</td>
								</tr>
								<tr>
									<td>
										Donor 
									</td>
									<td>
										<input type="checkbox" id="donor" name="donor" <%=props.getProperty("donor", "")%> />
									</td>
									<td>
										Blastocyst 
									</td>
									<td>
										<input type="checkbox" id="blastocyst" name="blastocyst" <%=props.getProperty("blastocyst", "")%> />
									</td>
								</tr>
								<tr>
									<td>
										PESA/TESA 
									</td>
									<td>
										<input type="checkbox" id="pesa" name="pesa" <%=props.getProperty("pesa", "")%> />
									</td>
									<td>
										Assisted Hatching 
									</td>
									<td>
										<input type="checkbox" id="assisted" name="assisted" <%=props.getProperty("assisted", "")%> />
									</td>
								</tr>
								<tr>
									<td>
									</td>
									<td>
									</td>
									<td>
										# for ET 
									</td>
									<td>
										<input type="text" id="for_et" size="10" name="for_et" value="<%=props.getProperty("for_et", "")%>"/>
									</td>
								</tr>
							</tbody>
							</table>
						</td>
						</tr>
					</tbody>
					</table>
					</td>
				</tr>
			</tbody>
			</table>
		</td>
		<td width="50%">
		<table align="center"  border="0" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
			<td>
			Primary Nurse:<input type="text" id="primary_nurse" name="primary_nurse" maxlength="20" value="<%=props.getProperty("primary_nurse", "")%>"/>
			</td> 
			</tr>
			<tr>
			<td>
			Medication Protocol:<input type="text" id="medication" name="medication" maxlength="20" value="<%=props.getProperty("medication", "")%>"/>
			</td> 
			</tr>
			<tr>
			<td>
			Diagnosis:<input type="text" id="diagnosis" name="diagnosis" maxlength="20" value="<%=props.getProperty("diagnosis", "")%>"/>
			</td> 
			</tr>
			<tr>
			<td>
			Previous Cycles:# fresh:<input type="text" id="previousfresh" name="previousfresh" size="10" maxlength="2" value="<%=props.getProperty("previousfresh", "")%>"/># frozen:<input type="text" id="previousfrozen" name="previousfrozen" size="10" maxlength="2" value="<%=props.getProperty("previousfrozen", "")%>"/>
			</td> 
			</tr>
			<tr rowspan="2">
			<td>
			Physician:<input type="text" id="physician" name="physician" value="<%=props.getProperty("physician", "")%>"/>
			</td> 
			</tr>
			<tr>
			<td height="15">
			&nbsp;
			</td> 
			</tr>
			
			<tr>
			<td>
				<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td colspan="2">
							<u>Lab Results</u>
						</td>
						<td colspan="2">
							<u>Booking Information  </u>
						</td>
					</tr>
					<tr>
						<td>
							FSH 
						</td>
						<td>
							<input type="text" id="fsh" size="10" name="fsh" maxlength="6" value="<%=props.getProperty("fsh", "")%>"/>
						</td>
						<td>
							Hcg/Ovidrel 
						</td>
						<td>
							<input type="checkbox" id="hcg" name="hcg" <%=props.getProperty("hcg", "")%> />
						</td>
					</tr>
					<tr>
						<td>
							TSH 
						</td>
						<td>
							<input type="text" id="tsh" size="10" name="tsh" maxlength="6" value="<%=props.getProperty("tsh", "")%>"/>
						</td>
						<td>
							P4 supps 
						</td>
						<td>
							<input type="checkbox" id="p4supps" name="p4supps" <%=props.getProperty("p4supps", "")%>/>
						</td>					
					</tr>
					<tr>
						<td>AMH</td>
						<td>
							<input type="text" id="amh" size="10" name="amh" maxlength="6" value="<%=props.getProperty("amh", "")%>"/>
						</td>
						<td>
							Crinone
						</td>
						<td>
							<input type="checkbox" id="crinone" name="crinone" <%=props.getProperty("crinone", "")%> />
						</td>					
					</tr>
					<tr>
						<td colspan="2" rowspan="2">
							<textarea name="txt1" id="txt1"><%=props.getProperty("txt1", "")%></textarea>
						</td>
						<td>
							Endometrin
						</td>
						<td>
							<input type="checkbox" id="endometrin" name="endometrin" <%=props.getProperty("endometrin", "")%>/>
						</td>					
					</tr>
					<tr>
						<td>
							Antibiotics
						</td>
						<td>
							<input type="checkbox" id="antibiotics" name="antibiotics" <%=props.getProperty("antibiotics", "")%>/>
						</td>					
					</tr>
				</tbody>
				</table>
			</td> 
			</tr>
		</tbody>
		</table>
		</td>
	</tr>
	</tbody>
	</table>
	</td>
	<td width="33%">
		<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
			<td>
				Patient Surname:<input type="text" id="patientSurname" name="patientSurname" value="<%=props.getProperty("patientSurname", "")%>"/>
			</td>
			</tr>
			<tr>
			<td>
				Patient First Name:<input type="text" id="patientFirstName" name="patientFirstName" value="<%=props.getProperty("patientFirstName", "")%>"/>
			</td>
			</tr>
			</tr>
			<tr>
			<td>
				Patient HC#:<input type="text" id="healthNum" name="healthNum" value="<%=props.getProperty("healthNum", "")%>"/>
			</td>
			</tr>
			</tr>
			<tr>
			<td>
				Patient DOB:<input type="text" id="dob" name="dob" value="<%=props.getProperty("dob", "")%>"/>&nbsp;&nbsp;&nbsp;Age:<input type="text" id="age" name="age" value="<%=props.getProperty("age", "")%>"/>
			</td>
			</tr>
			<tr>
			<td height="15">&nbsp;

			</td>
			</tr>
			<tr>
			<td>
				Partner Surname:<input type="text" id="partnerSurname" name="partnerSurname" value="<%=props.getProperty("partnerSurname", "")%>"/>
			</td>
			</tr>
			<tr>
			<td>
				Partner First Name:<input type="text" id="partnerFirstName" name="partnerFirstName" value="<%=props.getProperty("partnerFirstName", "")%>"/>
			</td>
			</tr>
			<tr>
			<td>
				Partner HC#:<input type="text" id="partnerHealthNum" name="partnerHealthNum" value="<%=props.getProperty("partnerHealthNum", "")%>"/>
			</td>
			</tr>
			<tr>
			<td>
				Partner DOB:<input type="text" id="partnerdob" name="partnerdob" value="<%=props.getProperty("partnerdob", "")%>"/>
			</td>
			</tr>
			<tr>
			<td height="15">&nbsp;

			</td>
			</tr>
			<tr>
			<td>
				Address:<input type="text" id="address" name="address" value="<%=props.getProperty("address", "")%>"/>
			</td>
			</tr>
			<tr>
			<td>
				City:<input type="text" id="city" name="city" value="<%=props.getProperty("city", "")%>"/>
			</td>
			</tr>
			<tr>
			<td>
				Postal Code:<input type="text" id="postalCode" name="postalCode" value="<%=props.getProperty("postalCode", "")%>"/>
			</td>
			</tr>
			<tr>
			<td>
				Phone #:<input type="text" id="phone" name="phone" value="<%=props.getProperty("phone", "")%>"/>
			</td>
			</tr>
			<tr>
			<td>
				<u>Comments:</u>
			</td>
			</tr>
			<tr>
			<td>
				<textarea id="comment1" name="comment1" style="width: 500px; height: 120px;"><%=props.getProperty("comment1", "")%></textarea>
			</td>
			</tr>
		</tbody>
		</table>
	</td>
	</tr>
	</tbody>
	</table>
</td>
</tr>
<tr>
<td>
<table align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
<tbody>
	<tr>
		<td width="10%">Date(dd/mm/yyyy)</td>
		<td width="5%" colspan="2"><input type="text" id="date1" name="date1" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date1", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date2" name="date2" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date2", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date3" name="date3" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date3", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date4" name="date4" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date4", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date5" name="date5" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date5", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date6" name="date6" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date6", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date7" name="date7" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date7", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date8" name="date8" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date8", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date9" name="date9" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date9", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date10" name="date10" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date10", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date11" name="date11" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date11", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date12" name="date12" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date12", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date13" name="date13" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date13", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date14" name="date14" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date14", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date15" name="date15" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date15", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date16" name="date16" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date16", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date17" name="date17" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date17", "")%>"/></td>
		<td width="5%" colspan="2"><input type="text" id="date18" name="date18" size="8" onDblClick="calToday(this)" value="<%=props.getProperty("date18", "")%>"/></td>
	</tr>
	<tr>
		<td>Cycle Day</td>
		<td colspan="2">AFC</td>
		<td colspan="2"><input type="text" style="width:2px;visibility:hidden;"/></td>
		<td colspan="2">3</td>
		<td colspan="2">4</td>
		<td colspan="2">5</td>
		<td colspan="2">6</td>
		<td colspan="2">7</td>
		<td colspan="2">8</td>
		<td colspan="2">9</td>
		<td colspan="2">10</td>
		<td colspan="2">11</td>
		<td colspan="2">12</td>
		<td colspan="2">13</td>
		<td colspan="2">14</td>
		<td colspan="2">15</td>
		<td colspan="2">16</td>
		<td colspan="2">17</td>
		<td colspan="2">18</td>
	</tr>
	<tr>
	<!--	<td>Puregon<input type="checkbox" id="puregon" name="puregon" <%=props.getProperty("puregon", "")%> />Gonal F<input type="checkbox" id="gonal" name="gonal" <%=props.getProperty("gonal", "")%> /></td>	-->
		<td>
		<select id="pgb" name="pgb">
			<option value="puregon" <%=props.getProperty("pgb", "").equals("puregon")? "selected" : ""%>>Puregon</option>
			<option value="gonal" <%=props.getProperty("pgb", "").equals("gonal")? "selected" : ""%>>Gonal F</option>
			<option value="brazelle" <%=props.getProperty("pgb", "").equals("brazelle")? "selected" : ""%>>Brazelle</option>
		</select>
		</td>
		<td colspan="2"><input type="text" id="puregon1" name="puregon1" maxlength="7" size="8" value="<%=props.getProperty("puregon1", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon2" name="puregon2" maxlength="7" size="8" value="<%=props.getProperty("puregon2", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon3" name="puregon3" maxlength="7" size="8" value="<%=props.getProperty("puregon3", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon4" name="puregon4" maxlength="7" size="8" value="<%=props.getProperty("puregon4", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon5" name="puregon5" maxlength="7" size="8" value="<%=props.getProperty("puregon5", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon6" name="puregon6" maxlength="7" size="8" value="<%=props.getProperty("puregon6", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon7" name="puregon7" maxlength="7" size="8" value="<%=props.getProperty("puregon7", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon8" name="puregon8" maxlength="7" size="8" value="<%=props.getProperty("puregon8", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon9" name="puregon9" maxlength="7" size="8" value="<%=props.getProperty("puregon9", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon10" name="puregon10" maxlength="7" size="8" value="<%=props.getProperty("puregon10", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon11" name="puregon11" maxlength="7" size="8" value="<%=props.getProperty("puregon11", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon12" name="puregon12" maxlength="7" size="8" value="<%=props.getProperty("puregon12", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon13" name="puregon13" maxlength="7" size="8" value="<%=props.getProperty("puregon13", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon14" name="puregon14" maxlength="7" size="8" value="<%=props.getProperty("puregon14", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon15" name="puregon15" maxlength="7" size="8" value="<%=props.getProperty("puregon15", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon16" name="puregon16" maxlength="7" size="8" value="<%=props.getProperty("puregon16", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon17" name="puregon17" maxlength="7" size="8" value="<%=props.getProperty("puregon17", "")%>"/></td>
		<td colspan="2"><input type="text" id="puregon18" name="puregon18" maxlength="7" size="8" value="<%=props.getProperty("puregon18", "")%>"/></td>
	</tr>
	<tr>
	<!--	<td>Menopur<input type="checkbox" id="menopur" name="menopur" <%=props.getProperty("menopur", "")%>/>mhCG<input type="checkbox" id="mhcg" name="mhcg" <%=props.getProperty("mhcg", "")%>/>Luveris<input type="checkbox" id="luver" name="luver" <%=props.getProperty("luver", "")%>/></td>	-->
		<td>
		<select id="mml" name="mml">
			<option value="menopur" <%=props.getProperty("mml", "").equals("menopur")? "selected" : ""%>>Menopur</option>
			<option value="mhcg" <%=props.getProperty("mml", "").equals("mhcg")? "selected" : ""%>>mhCG</option>
			<option value="luveris" <%=props.getProperty("mml", "").equals("luveris")? "selected" : ""%>>Luveris</option>
		</select>
		</td>
		<td colspan="2"><input type="text" id="menopur1" name="menopur1" maxlength="3" size="8" value="<%=props.getProperty("menopur1", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur2" name="menopur2" maxlength="3" size="8" value="<%=props.getProperty("menopur2", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur3" name="menopur3" maxlength="3" size="8" value="<%=props.getProperty("menopur3", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur4" name="menopur4" maxlength="3" size="8" value="<%=props.getProperty("menopur4", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur5" name="menopur5" maxlength="3" size="8" value="<%=props.getProperty("menopur5", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur6" name="menopur6" maxlength="3" size="8" value="<%=props.getProperty("menopur6", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur7" name="menopur7" maxlength="3" size="8" value="<%=props.getProperty("menopur7", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur8" name="menopur8" maxlength="3" size="8" value="<%=props.getProperty("menopur8", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur9" name="menopur9" maxlength="3" size="8" value="<%=props.getProperty("menopur9", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur10" name="menopur10" maxlength="3" size="8" value="<%=props.getProperty("menopur10", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur11" name="menopur11" maxlength="3" size="8" value="<%=props.getProperty("menopur11", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur12" name="menopur12" maxlength="3" size="8" value="<%=props.getProperty("menopur12", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur13" name="menopur13" maxlength="3" size="8" value="<%=props.getProperty("menopur13", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur14" name="menopur14" maxlength="3" size="8" value="<%=props.getProperty("menopur14", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur15" name="menopur15" maxlength="3" size="8" value="<%=props.getProperty("menopur15", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur16" name="menopur16" maxlength="3" size="8" value="<%=props.getProperty("menopur16", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur17" name="menopur17" maxlength="3" size="8" value="<%=props.getProperty("menopur17", "")%>"/></td>
		<td colspan="2"><input type="text" id="menopur18" name="menopur18" maxlength="3" size="8" value="<%=props.getProperty("menopur18", "")%>"/></td>
	</tr>
	<tr>
	<!--	<td>Orgalutran<input type="checkbox" id="orgalutran" name="orgalutran" <%=props.getProperty("orgalutran", "")%>>Suprefact<input type="checkbox" id="suprefact" name="suprefact" <%=props.getProperty("suprefact", "")%>></td>	-->
		<td>
		<select id="osc" name="osc">
			<option value="orgalutran" <%=props.getProperty("osc", "").equals("orgalutran")? "selected" : ""%>>Orgalutran</option>
			<option value="suprefact" <%=props.getProperty("osc", "").equals("suprefact")? "selected" : ""%>>Suprefact</option>
			<option value="cetrotide" <%=props.getProperty("osc", "").equals("cetrotide")? "selected" : ""%>>Cetrotide</option>
		</select>
		</td>
		<td colspan="2"><input type="text" id="orgalutran1" name="orgalutran1" maxlength="5" size="8" value="<%=props.getProperty("orgalutran1", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran2" name="orgalutran2" maxlength="5" size="8" value="<%=props.getProperty("orgalutran2", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran3" name="orgalutran3" maxlength="5" size="8" value="<%=props.getProperty("orgalutran3", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran4" name="orgalutran4" maxlength="5" size="8" value="<%=props.getProperty("orgalutran4", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran5" name="orgalutran5" maxlength="5" size="8" value="<%=props.getProperty("orgalutran5", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran6" name="orgalutran6" maxlength="5" size="8" value="<%=props.getProperty("orgalutran6", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran7" name="orgalutran7" maxlength="5" size="8" value="<%=props.getProperty("orgalutran7", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran8" name="orgalutran8" maxlength="5" size="8" value="<%=props.getProperty("orgalutran8", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran9" name="orgalutran9" maxlength="5" size="8" value="<%=props.getProperty("orgalutran9", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran10" name="orgalutran10" maxlength="5" size="8" value="<%=props.getProperty("orgalutran10", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran11" name="orgalutran11" maxlength="5" size="8" value="<%=props.getProperty("orgalutran11", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran12" name="orgalutran12" maxlength="5" size="8" value="<%=props.getProperty("orgalutran12", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran13" name="orgalutran13" maxlength="5" size="8" value="<%=props.getProperty("orgalutran13", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran14" name="orgalutran14" maxlength="5" size="8" value="<%=props.getProperty("orgalutran14", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran15" name="orgalutran15" maxlength="5" size="8" value="<%=props.getProperty("orgalutran15", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran16" name="orgalutran16" maxlength="5" size="8" value="<%=props.getProperty("orgalutran16", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran17" name="orgalutran17" maxlength="5" size="8" value="<%=props.getProperty("orgalutran17", "")%>"/></td>
		<td colspan="2"><input type="text" id="orgalutran18" name="orgalutran18" maxlength="5" size="8" value="<%=props.getProperty("orgalutran18", "")%>"/></td>
	</tr>
	<tr>
		<td>Progesterone</td>
		<td colspan="2"><input type="text" id="proges1" name="proges1" maxlength="5" size="8" value="<%=props.getProperty("proges1", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges2" name="proges2" maxlength="5" size="8" value="<%=props.getProperty("proges2", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges3" name="proges3" maxlength="5" size="8" value="<%=props.getProperty("proges3", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges4" name="proges4" maxlength="5" size="8" value="<%=props.getProperty("proges4", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges5" name="proges5" maxlength="5" size="8" value="<%=props.getProperty("proges5", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges6" name="proges6" maxlength="5" size="8" value="<%=props.getProperty("proges6", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges7" name="proges7" maxlength="5" size="8" value="<%=props.getProperty("proges7", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges8" name="proges8" maxlength="5" size="8" value="<%=props.getProperty("proges8", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges9" name="proges9" maxlength="5" size="8" value="<%=props.getProperty("proges9", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges10" name="proges10" maxlength="5" size="8" value="<%=props.getProperty("proges10", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges11" name="proges11" maxlength="5" size="8" value="<%=props.getProperty("proges11", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges12" name="proges12" maxlength="5" size="8" value="<%=props.getProperty("proges12", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges13" name="proges13" maxlength="5" size="8" value="<%=props.getProperty("proges13", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges14" name="proges14" maxlength="5" size="8" value="<%=props.getProperty("proges14", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges15" name="proges15" maxlength="5" size="8" value="<%=props.getProperty("proges15", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges16" name="proges16" maxlength="5" size="8" value="<%=props.getProperty("proges16", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges17" name="proges17" maxlength="5" size="8" value="<%=props.getProperty("proges17", "")%>"/></td>
		<td colspan="2"><input type="text" id="proges18" name="proges18" maxlength="5" size="8" value="<%=props.getProperty("proges18", "")%>"/></td>
	</tr>
	<tr>
		<td>Estradiol</td>
		<td colspan="2"><input type="text" id="estradiol1" name="estradiol1" maxlength="5" size="8" value="<%=props.getProperty("estradiol1", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol2" name="estradiol2" maxlength="5" size="8" value="<%=props.getProperty("estradiol2", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol3" name="estradiol3" maxlength="5" size="8" value="<%=props.getProperty("estradiol3", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol4" name="estradiol4" maxlength="5" size="8" value="<%=props.getProperty("estradiol4", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol5" name="estradiol5" maxlength="5" size="8" value="<%=props.getProperty("estradiol5", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol6" name="estradiol6" maxlength="5" size="8" value="<%=props.getProperty("estradiol6", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol7" name="estradiol7" maxlength="5" size="8" value="<%=props.getProperty("estradiol7", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol8" name="estradiol8" maxlength="5" size="8" value="<%=props.getProperty("estradiol8", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol9" name="estradiol9" maxlength="5" size="8" value="<%=props.getProperty("estradiol9", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol10" name="estradiol10" maxlength="5" size="8" value="<%=props.getProperty("estradiol10", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol11" name="estradiol11" maxlength="5" size="8" value="<%=props.getProperty("estradiol11", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol12" name="estradiol12" maxlength="5" size="8" value="<%=props.getProperty("estradiol12", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol13" name="estradiol13" maxlength="5" size="8" value="<%=props.getProperty("estradiol13", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol14" name="estradiol14" maxlength="5" size="8" value="<%=props.getProperty("estradiol14", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol15" name="estradiol15" maxlength="5" size="8" value="<%=props.getProperty("estradiol15", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol16" name="estradiol16" maxlength="5" size="8" value="<%=props.getProperty("estradiol16", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol17" name="estradiol17" maxlength="5" size="8" value="<%=props.getProperty("estradiol17", "")%>"/></td>
		<td colspan="2"><input type="text" id="estradiol18" name="estradiol18" maxlength="5" size="8" value="<%=props.getProperty("estradiol18", "")%>"/></td>
	</tr>
	<tr>
		<td>LH</td>
		<td colspan="2"><input type="text" id="lh1" name="lh1" maxlength="5" size="8" value="<%=props.getProperty("lh1", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh2" name="lh2" maxlength="5" size="8" value="<%=props.getProperty("lh2", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh3" name="lh3" maxlength="5" size="8" value="<%=props.getProperty("lh3", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh4" name="lh4" maxlength="5" size="8" value="<%=props.getProperty("lh4", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh5" name="lh5" maxlength="5" size="8" value="<%=props.getProperty("lh5", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh6" name="lh6" maxlength="5" size="8" value="<%=props.getProperty("lh6", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh7" name="lh7" maxlength="5" size="8" value="<%=props.getProperty("lh7", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh8" name="lh8" maxlength="5" size="8" value="<%=props.getProperty("lh8", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh9" name="lh9" maxlength="5" size="8" value="<%=props.getProperty("lh9", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh10" name="lh10" maxlength="5" size="8" value="<%=props.getProperty("lh10", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh11" name="lh11" maxlength="5" size="8" value="<%=props.getProperty("lh11", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh12" name="lh12" maxlength="5" size="8" value="<%=props.getProperty("lh12", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh13" name="lh13" maxlength="5" size="8" value="<%=props.getProperty("lh13", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh14" name="lh14" maxlength="5" size="8" value="<%=props.getProperty("lh14", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh15" name="lh15" maxlength="5" size="8" value="<%=props.getProperty("lh15", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh16" name="lh16" maxlength="5" size="8" value="<%=props.getProperty("lh16", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh17" name="lh17" maxlength="5" size="8" value="<%=props.getProperty("lh17", "")%>"/></td>
		<td colspan="2"><input type="text" id="lh18" name="lh18" maxlength="5" size="8" value="<%=props.getProperty("lh18", "")%>"/></td>
	</tr>
	<tr>
		<td>Ultrasound</td>
		<td align="center" onclick="sortedTextValue(0);">R</td>
		<td align="center" onclick="sortedTextValue(1);">L</td>
		<td align="center" onclick="sortedTextValue(2);">R</td>
		<td align="center" onclick="sortedTextValue(3);">L</td>
		<td align="center" onclick="sortedTextValue(4);">R</td>
		<td align="center" onclick="sortedTextValue(5);">L</td>
		<td align="center" onclick="sortedTextValue(6);">R</td>
		<td align="center" onclick="sortedTextValue(7);">L</td>
		<td align="center" onclick="sortedTextValue(8);">R</td>
		<td align="center" onclick="sortedTextValue(9);">L</td>
		<td align="center" onclick="sortedTextValue(10);">R</td>
		<td align="center" onclick="sortedTextValue('11_');">L</td>
		<td align="center" onclick="sortedTextValue(12);">R</td>
		<td align="center" onclick="sortedTextValue(13);">L</td>
		<td align="center" onclick="sortedTextValue(14);">R</td>
		<td align="center" onclick="sortedTextValue(15);">L</td>
		<td align="center" onclick="sortedTextValue(16);">R</td>
		<td align="center" onclick="sortedTextValue(17);">L</td>
		<td align="center" onclick="sortedTextValue(18);">R</td>
		<td align="center" onclick="sortedTextValue(19);">L</td>
		<td align="center" onclick="sortedTextValue(20);">R</td>
		<td align="center" onclick="sortedTextValue('21_');">L</td>
		<td align="center" onclick="sortedTextValue(22);">R</td>
		<td align="center" onclick="sortedTextValue(23);">L</td>
		<td align="center" onclick="sortedTextValue(24);">R</td>
		<td align="center" onclick="sortedTextValue(25);">L</td>
		<td align="center" onclick="sortedTextValue(26);">R</td>
		<td align="center" onclick="sortedTextValue(27);">L</td>
		<td align="center" onclick="sortedTextValue(28);">R</td>
		<td align="center" onclick="sortedTextValue(29);">L</td>
		<td align="center" onclick="sortedTextValue(30);">R</td>
		<td align="center" onclick="sortedTextValue('31_');">L</td>
		<td align="center" onclick="sortedTextValue(32);">R</td>
		<td align="center" onclick="sortedTextValue(33);">L</td>
		<td align="center" onclick="sortedTextValue(34);">R</td>
		<td align="center" onclick="sortedTextValue(35);">L</td>
	</tr>
	<tr>
		<td><textarea id="txt2" name="txt2" style="width: 139px; height: 374px;"><%=props.getProperty("txt2", "")%></textarea></td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l01" name="r_l01" size="2" maxlength="2" value="<%=props.getProperty("r_l01", "")%>" /></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l02" name="r_l02" size="2" maxlength="2" value="<%=props.getProperty("r_l02", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l03" name="r_l03" size="2" maxlength="2" value="<%=props.getProperty("r_l03", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l04" name="r_l04" size="2" maxlength="2" value="<%=props.getProperty("r_l04", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l05" name="r_l05" size="2" maxlength="2" value="<%=props.getProperty("r_l05", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l06" name="r_l06" size="2" maxlength="2" value="<%=props.getProperty("r_l06", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l07" name="r_l07" size="2" maxlength="2" value="<%=props.getProperty("r_l07", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l08" name="r_l08" size="2" maxlength="2" value="<%=props.getProperty("r_l08", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l09" name="r_l09" size="2" maxlength="2" value="<%=props.getProperty("r_l09", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l010" name="r_l010" size="2" maxlength="2" value="<%=props.getProperty("r_l010", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l011" name="r_l011" size="2" maxlength="2" value="<%=props.getProperty("r_l011", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l012" name="r_l012" size="2" maxlength="2" value="<%=props.getProperty("r_l012", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l013" name="r_l013" size="2" maxlength="2" value="<%=props.getProperty("r_l013", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l014" name="r_l014" size="2" maxlength="2" value="<%=props.getProperty("r_l014", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l015" name="r_l015" size="2" maxlength="2" value="<%=props.getProperty("r_l015", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l11" name="r_l11" size="2" maxlength="2" value="<%=props.getProperty("r_l11", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l12" name="r_l12" size="2" maxlength="2" value="<%=props.getProperty("r_l12", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l13" name="r_l13" size="2" maxlength="2" value="<%=props.getProperty("r_l13", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l14" name="r_l14" size="2" maxlength="2" value="<%=props.getProperty("r_l14", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l15" name="r_l15" size="2" maxlength="2" value="<%=props.getProperty("r_l15", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l16" name="r_l16" size="2" maxlength="2" value="<%=props.getProperty("r_l16", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l17" name="r_l17" size="2" maxlength="2" value="<%=props.getProperty("r_l17", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l18" name="r_l18" size="2" maxlength="2" value="<%=props.getProperty("r_l18", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l19" name="r_l19" size="2" maxlength="2" value="<%=props.getProperty("r_l19", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l110" name="r_l110" size="2" maxlength="2" value="<%=props.getProperty("r_l110", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l111" name="r_l111" size="2" maxlength="2" value="<%=props.getProperty("r_l111", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l112" name="r_l112" size="2" maxlength="2" value="<%=props.getProperty("r_l112", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l113" name="r_l113" size="2" maxlength="2" value="<%=props.getProperty("r_l113", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l114" name="r_l114" size="2" maxlength="2" value="<%=props.getProperty("r_l114", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l115" name="r_l115" size="2" maxlength="2" value="<%=props.getProperty("r_l115", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l21" name="r_l21" size="2" maxlength="2" value="<%=props.getProperty("r_l21", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l22" name="r_l22" size="2" maxlength="2" value="<%=props.getProperty("r_l22", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l23" name="r_l23" size="2" maxlength="2" value="<%=props.getProperty("r_l23", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l24" name="r_l24" size="2" maxlength="2" value="<%=props.getProperty("r_l24", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l25" name="r_l25" size="2" maxlength="2" value="<%=props.getProperty("r_l25", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l26" name="r_l26" size="2" maxlength="2" value="<%=props.getProperty("r_l26", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l27" name="r_l27" size="2" maxlength="2" value="<%=props.getProperty("r_l27", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l28" name="r_l28" size="2" maxlength="2" value="<%=props.getProperty("r_l28", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l29" name="r_l29" size="2" maxlength="2" value="<%=props.getProperty("r_l29", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l210" name="r_l210" size="2" maxlength="2" value="<%=props.getProperty("r_l210", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l211" name="r_l211" size="2" maxlength="2" value="<%=props.getProperty("r_l211", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l212" name="r_l212" size="2" maxlength="2" value="<%=props.getProperty("r_l212", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l213" name="r_l213" size="2" maxlength="2" value="<%=props.getProperty("r_l213", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l214" name="r_l214" size="2" maxlength="2" value="<%=props.getProperty("r_l214", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l215" name="r_l215" size="2" maxlength="2" value="<%=props.getProperty("r_l215", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l31" name="r_l31" size="2" maxlength="2" value="<%=props.getProperty("r_l31", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l32" name="r_l32" size="2" maxlength="2" value="<%=props.getProperty("r_l32", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l33" name="r_l33" size="2" maxlength="2" value="<%=props.getProperty("r_l33", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l34" name="r_l34" size="2" maxlength="2" value="<%=props.getProperty("r_l34", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l35" name="r_l35" size="2" maxlength="2" value="<%=props.getProperty("r_l35", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l36" name="r_l36" size="2" maxlength="2" value="<%=props.getProperty("r_l36", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l37" name="r_l37" size="2" maxlength="2" value="<%=props.getProperty("r_l37", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l38" name="r_l38" size="2" maxlength="2" value="<%=props.getProperty("r_l38", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l39" name="r_l39" size="2" maxlength="2" value="<%=props.getProperty("r_l39", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l310" name="r_l310" size="2" maxlength="2" value="<%=props.getProperty("r_l310", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l311" name="r_l311" size="2" maxlength="2" value="<%=props.getProperty("r_l311", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l312" name="r_l312" size="2" maxlength="2" value="<%=props.getProperty("r_l312", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l313" name="r_l313" size="2" maxlength="2" value="<%=props.getProperty("r_l313", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l314" name="r_l314" size="2" maxlength="2" value="<%=props.getProperty("r_l314", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l315" name="r_l315" size="2" maxlength="2" value="<%=props.getProperty("r_l315", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l41" name="r_l41" size="2" maxlength="2" value="<%=props.getProperty("r_l41", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l42" name="r_l42" size="2" maxlength="2" value="<%=props.getProperty("r_l42", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l43" name="r_l43" size="2" maxlength="2" value="<%=props.getProperty("r_l43", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l44" name="r_l44" size="2" maxlength="2" value="<%=props.getProperty("r_l44", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l45" name="r_l45" size="2" maxlength="2" value="<%=props.getProperty("r_l45", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l46" name="r_l46" size="2" maxlength="2" value="<%=props.getProperty("r_l46", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l47" name="r_l47" size="2" maxlength="2" value="<%=props.getProperty("r_l47", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l48" name="r_l48" size="2" maxlength="2" value="<%=props.getProperty("r_l48", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l49" name="r_l49" size="2" maxlength="2" value="<%=props.getProperty("r_l49", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l410" name="r_l410" size="2" maxlength="2" value="<%=props.getProperty("r_l410", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l411" name="r_l411" size="2" maxlength="2" value="<%=props.getProperty("r_l411", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l412" name="r_l412" size="2" maxlength="2" value="<%=props.getProperty("r_l412", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l413" name="r_l413" size="2" maxlength="2" value="<%=props.getProperty("r_l413", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l414" name="r_l414" size="2" maxlength="2" value="<%=props.getProperty("r_l414", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l415" name="r_l415" size="2" maxlength="2" value="<%=props.getProperty("r_l415", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l51" name="r_l51" size="2" maxlength="2" value="<%=props.getProperty("r_l51", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l52" name="r_l52" size="2" maxlength="2" value="<%=props.getProperty("r_l52", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l53" name="r_l53" size="2" maxlength="2" value="<%=props.getProperty("r_l53", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l54" name="r_l54" size="2" maxlength="2" value="<%=props.getProperty("r_l54", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l55" name="r_l55" size="2" maxlength="2" value="<%=props.getProperty("r_l55", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l56" name="r_l56" size="2" maxlength="2" value="<%=props.getProperty("r_l56", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l57" name="r_l57" size="2" maxlength="2" value="<%=props.getProperty("r_l57", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l58" name="r_l58" size="2" maxlength="2" value="<%=props.getProperty("r_l58", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l59" name="r_l59" size="2" maxlength="2" value="<%=props.getProperty("r_l59", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l510" name="r_l510" size="2" maxlength="2" value="<%=props.getProperty("r_l510", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l511" name="r_l511" size="2" maxlength="2" value="<%=props.getProperty("r_l511", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l512" name="r_l512" size="2" maxlength="2" value="<%=props.getProperty("r_l512", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l513" name="r_l513" size="2" maxlength="2" value="<%=props.getProperty("r_l513", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l514" name="r_l514" size="2" maxlength="2" value="<%=props.getProperty("r_l514", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l515" name="r_l515" size="2" maxlength="2" value="<%=props.getProperty("r_l515", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l61" name="r_l61" size="2" maxlength="2" value="<%=props.getProperty("r_l61", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l62" name="r_l62" size="2" maxlength="2" value="<%=props.getProperty("r_l62", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l63" name="r_l63" size="2" maxlength="2" value="<%=props.getProperty("r_l63", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l64" name="r_l64" size="2" maxlength="2" value="<%=props.getProperty("r_l64", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l65" name="r_l65" size="2" maxlength="2" value="<%=props.getProperty("r_l65", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l66" name="r_l66" size="2" maxlength="2" value="<%=props.getProperty("r_l66", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l67" name="r_l67" size="2" maxlength="2" value="<%=props.getProperty("r_l67", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l68" name="r_l68" size="2" maxlength="2" value="<%=props.getProperty("r_l68", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l69" name="r_l69" size="2" maxlength="2" value="<%=props.getProperty("r_l69", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l610" name="r_l610" size="2" maxlength="2" value="<%=props.getProperty("r_l610", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l611" name="r_l611" size="2" maxlength="2" value="<%=props.getProperty("r_l611", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l612" name="r_l612" size="2" maxlength="2" value="<%=props.getProperty("r_l612", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l613" name="r_l613" size="2" maxlength="2" value="<%=props.getProperty("r_l613", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l614" name="r_l614" size="2" maxlength="2" value="<%=props.getProperty("r_l614", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l615" name="r_l615" size="2" maxlength="2" value="<%=props.getProperty("r_l615", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l71" name="r_l71" size="2" maxlength="2" value="<%=props.getProperty("r_l71", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l72" name="r_l72" size="2" maxlength="2" value="<%=props.getProperty("r_l72", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l73" name="r_l73" size="2" maxlength="2" value="<%=props.getProperty("r_l73", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l74" name="r_l74" size="2" maxlength="2" value="<%=props.getProperty("r_l74", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l75" name="r_l75" size="2" maxlength="2" value="<%=props.getProperty("r_l75", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l76" name="r_l76" size="2" maxlength="2" value="<%=props.getProperty("r_l76", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l77" name="r_l77" size="2" maxlength="2" value="<%=props.getProperty("r_l77", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l78" name="r_l78" size="2" maxlength="2" value="<%=props.getProperty("r_l78", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l79" name="r_l79" size="2" maxlength="2" value="<%=props.getProperty("r_l79", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l710" name="r_l710" size="2" maxlength="2" value="<%=props.getProperty("r_l710", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l711" name="r_l711" size="2" maxlength="2" value="<%=props.getProperty("r_l711", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l712" name="r_l712" size="2" maxlength="2" value="<%=props.getProperty("r_l712", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l713" name="r_l713" size="2" maxlength="2" value="<%=props.getProperty("r_l713", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l714" name="r_l714" size="2" maxlength="2" value="<%=props.getProperty("r_l714", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l715" name="r_l715" size="2" maxlength="2" value="<%=props.getProperty("r_l715", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l81" name="r_l81" size="2" maxlength="2" value="<%=props.getProperty("r_l81", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l82" name="r_l82" size="2" maxlength="2" value="<%=props.getProperty("r_l82", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l83" name="r_l83" size="2" maxlength="2" value="<%=props.getProperty("r_l83", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l84" name="r_l84" size="2" maxlength="2" value="<%=props.getProperty("r_l84", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l85" name="r_l85" size="2" maxlength="2" value="<%=props.getProperty("r_l85", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l86" name="r_l86" size="2" maxlength="2" value="<%=props.getProperty("r_l86", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l87" name="r_l87" size="2" maxlength="2" value="<%=props.getProperty("r_l87", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l88" name="r_l88" size="2" maxlength="2" value="<%=props.getProperty("r_l88", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l89" name="r_l89" size="2" maxlength="2" value="<%=props.getProperty("r_l89", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l810" name="r_l810" size="2" maxlength="2" value="<%=props.getProperty("r_l810", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l811" name="r_l811" size="2" maxlength="2" value="<%=props.getProperty("r_l811", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l812" name="r_l812" size="2" maxlength="2" value="<%=props.getProperty("r_l812", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l813" name="r_l813" size="2" maxlength="2" value="<%=props.getProperty("r_l813", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l814" name="r_l814" size="2" maxlength="2" value="<%=props.getProperty("r_l814", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l815" name="r_l815" size="2" maxlength="2" value="<%=props.getProperty("r_l815", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l91" name="r_l91" size="2" maxlength="2" value="<%=props.getProperty("r_l91", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l92" name="r_l92" size="2" maxlength="2" value="<%=props.getProperty("r_l92", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l93" name="r_l93" size="2" maxlength="2" value="<%=props.getProperty("r_l93", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l94" name="r_l94" size="2" maxlength="2" value="<%=props.getProperty("r_l94", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l95" name="r_l95" size="2" maxlength="2" value="<%=props.getProperty("r_l95", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l96" name="r_l96" size="2" maxlength="2" value="<%=props.getProperty("r_l96", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l97" name="r_l97" size="2" maxlength="2" value="<%=props.getProperty("r_l97", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l98" name="r_l98" size="2" maxlength="2" value="<%=props.getProperty("r_l98", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l99" name="r_l99" size="2" maxlength="2" value="<%=props.getProperty("r_l99", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l910" name="r_l910" size="2" maxlength="2" value="<%=props.getProperty("r_l910", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l911" name="r_l911" size="2" maxlength="2" value="<%=props.getProperty("r_l911", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l912" name="r_l912" size="2" maxlength="2" value="<%=props.getProperty("r_l912", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l913" name="r_l913" size="2" maxlength="2" value="<%=props.getProperty("r_l913", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l914" name="r_l914" size="2" maxlength="2" value="<%=props.getProperty("r_l914", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l915" name="r_l915" size="2" maxlength="2" value="<%=props.getProperty("r_l915", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l101" name="r_l101" size="2" maxlength="2" value="<%=props.getProperty("r_l101", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l102" name="r_l102" size="2" maxlength="2" value="<%=props.getProperty("r_l102", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l103" name="r_l103" size="2" maxlength="2" value="<%=props.getProperty("r_l103", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l104" name="r_l104" size="2" maxlength="2" value="<%=props.getProperty("r_l104", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l105" name="r_l105" size="2" maxlength="2" value="<%=props.getProperty("r_l105", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l106" name="r_l106" size="2" maxlength="2" value="<%=props.getProperty("r_l106", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l107" name="r_l107" size="2" maxlength="2" value="<%=props.getProperty("r_l107", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l108" name="r_l108" size="2" maxlength="2" value="<%=props.getProperty("r_l108", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l109" name="r_l109" size="2" maxlength="2" value="<%=props.getProperty("r_l109", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1010" name="r_l1010" size="2" maxlength="2" value="<%=props.getProperty("r_l1010", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1011" name="r_l1011" size="2" maxlength="2" value="<%=props.getProperty("r_l1011", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1012" name="r_l1012" size="2" maxlength="2" value="<%=props.getProperty("r_l1012", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1013" name="r_l1013" size="2" maxlength="2" value="<%=props.getProperty("r_l1013", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1014" name="r_l1014" size="2" maxlength="2" value="<%=props.getProperty("r_l1014", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1015" name="r_l1015" size="2" maxlength="2" value="<%=props.getProperty("r_l1015", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l11_1" name="r_l11_1" size="2" maxlength="2" value="<%=props.getProperty("r_l11_1", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_2" name="r_l11_2" size="2" maxlength="2" value="<%=props.getProperty("r_l11_2", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_3" name="r_l11_3" size="2" maxlength="2" value="<%=props.getProperty("r_l11_3", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_4" name="r_l11_4" size="2" maxlength="2" value="<%=props.getProperty("r_l11_4", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_5" name="r_l11_5" size="2" maxlength="2" value="<%=props.getProperty("r_l11_5", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_6" name="r_l11_6" size="2" maxlength="2" value="<%=props.getProperty("r_l11_6", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_7" name="r_l11_7" size="2" maxlength="2" value="<%=props.getProperty("r_l11_7", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_8" name="r_l11_8" size="2" maxlength="2" value="<%=props.getProperty("r_l11_8", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_9" name="r_l11_9" size="2" maxlength="2" value="<%=props.getProperty("r_l11_9", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_10" name="r_l11_10" size="2" maxlength="2" value="<%=props.getProperty("r_l11_10", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_11" name="r_l11_11" size="2" maxlength="2" value="<%=props.getProperty("r_l11_11", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_12" name="r_l11_12" size="2" maxlength="2" value="<%=props.getProperty("r_l11_12", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_13" name="r_l11_13" size="2" maxlength="2" value="<%=props.getProperty("r_l11_13", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_14" name="r_l11_14" size="2" maxlength="2" value="<%=props.getProperty("r_l11_14", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l11_15" name="r_l11_15" size="2" maxlength="2" value="<%=props.getProperty("r_l11_15", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l121" name="r_l121" size="2" maxlength="2" value="<%=props.getProperty("r_l121", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l122" name="r_l122" size="2" maxlength="2" value="<%=props.getProperty("r_l122", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l123" name="r_l123" size="2" maxlength="2" value="<%=props.getProperty("r_l123", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l124" name="r_l124" size="2" maxlength="2" value="<%=props.getProperty("r_l124", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l125" name="r_l125" size="2" maxlength="2" value="<%=props.getProperty("r_l125", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l126" name="r_l126" size="2" maxlength="2" value="<%=props.getProperty("r_l126", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l127" name="r_l127" size="2" maxlength="2" value="<%=props.getProperty("r_l127", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l128" name="r_l128" size="2" maxlength="2" value="<%=props.getProperty("r_l128", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l129" name="r_l129" size="2" maxlength="2" value="<%=props.getProperty("r_l129", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1210" name="r_l1210" size="2" maxlength="2" value="<%=props.getProperty("r_l1210", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1211" name="r_l1211" size="2" maxlength="2" value="<%=props.getProperty("r_l1211", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1212" name="r_l1212" size="2" maxlength="2" value="<%=props.getProperty("r_l1212", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1213" name="r_l1213" size="2" maxlength="2" value="<%=props.getProperty("r_l1213", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1214" name="r_l1214" size="2" maxlength="2" value="<%=props.getProperty("r_l1214", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1215" name="r_l1215" size="2" maxlength="2" value="<%=props.getProperty("r_l1215", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l131" name="r_l131" size="2" maxlength="2" value="<%=props.getProperty("r_l131", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l132" name="r_l132" size="2" maxlength="2" value="<%=props.getProperty("r_l132", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l133" name="r_l133" size="2" maxlength="2" value="<%=props.getProperty("r_l133", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l134" name="r_l134" size="2" maxlength="2" value="<%=props.getProperty("r_l134", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l135" name="r_l135" size="2" maxlength="2" value="<%=props.getProperty("r_l135", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l136" name="r_l136" size="2" maxlength="2" value="<%=props.getProperty("r_l136", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l137" name="r_l137" size="2" maxlength="2" value="<%=props.getProperty("r_l137", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l138" name="r_l138" size="2" maxlength="2" value="<%=props.getProperty("r_l138", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l139" name="r_l139" size="2" maxlength="2" value="<%=props.getProperty("r_l139", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1310" name="r_l1310" size="2" maxlength="2" value="<%=props.getProperty("r_l1310", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1311" name="r_l1311" size="2" maxlength="2" value="<%=props.getProperty("r_l1311", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1312" name="r_l1312" size="2" maxlength="2" value="<%=props.getProperty("r_l1312", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1313" name="r_l1313" size="2" maxlength="2" value="<%=props.getProperty("r_l1313", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1314" name="r_l1314" size="2" maxlength="2" value="<%=props.getProperty("r_l1314", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1315" name="r_l1315" size="2" maxlength="2" value="<%=props.getProperty("r_l1315", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l141" name="r_l141" size="2" maxlength="2" value="<%=props.getProperty("r_l141", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l142" name="r_l142" size="2" maxlength="2" value="<%=props.getProperty("r_l142", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l143" name="r_l143" size="2" maxlength="2" value="<%=props.getProperty("r_l143", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l144" name="r_l144" size="2" maxlength="2" value="<%=props.getProperty("r_l145", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l145" name="r_l145" size="2" maxlength="2" value="<%=props.getProperty("r_l145", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l146" name="r_l146" size="2" maxlength="2" value="<%=props.getProperty("r_l146", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l147" name="r_l147" size="2" maxlength="2" value="<%=props.getProperty("r_l147", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l148" name="r_l148" size="2" maxlength="2" value="<%=props.getProperty("r_l148", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l149" name="r_l149" size="2" maxlength="2" value="<%=props.getProperty("r_l149", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1410" name="r_l1410" size="2" maxlength="2" value="<%=props.getProperty("r_l1410", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1411" name="r_l1411" size="2" maxlength="2" value="<%=props.getProperty("r_l1411", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1412" name="r_l1412" size="2" maxlength="2" value="<%=props.getProperty("r_l1412", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1413" name="r_l1413" size="2" maxlength="2" value="<%=props.getProperty("r_l1413", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1414" name="r_l1414" size="2" maxlength="2" value="<%=props.getProperty("r_l1414", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1415" name="r_l1415" size="2" maxlength="2" value="<%=props.getProperty("r_l1415", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l151" name="r_l151" size="2" maxlength="2" value="<%=props.getProperty("r_l151", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l152" name="r_l152" size="2" maxlength="2" value="<%=props.getProperty("r_l152", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l153" name="r_l153" size="2" maxlength="2" value="<%=props.getProperty("r_l153", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l154" name="r_l154" size="2" maxlength="2" value="<%=props.getProperty("r_l154", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l155" name="r_l155" size="2" maxlength="2" value="<%=props.getProperty("r_l155", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l156" name="r_l156" size="2" maxlength="2" value="<%=props.getProperty("r_l156", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l157" name="r_l157" size="2" maxlength="2" value="<%=props.getProperty("r_l157", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l158" name="r_l158" size="2" maxlength="2" value="<%=props.getProperty("r_l158", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l159" name="r_l159" size="2" maxlength="2" value="<%=props.getProperty("r_l159", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1510" name="r_l1510" size="2" maxlength="2" value="<%=props.getProperty("r_l1510", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1511" name="r_l1511" size="2" maxlength="2" value="<%=props.getProperty("r_l1511", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1512" name="r_l1512" size="2" maxlength="2" value="<%=props.getProperty("r_l1512", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1513" name="r_l1513" size="2" maxlength="2" value="<%=props.getProperty("r_l1513", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1514" name="r_l1514" size="2" maxlength="2" value="<%=props.getProperty("r_l1514", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1515" name="r_l1515" size="2" maxlength="2" value="<%=props.getProperty("r_l1515", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l161" name="r_l161" size="2" maxlength="2" value="<%=props.getProperty("r_l161", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l162" name="r_l162" size="2" maxlength="2" value="<%=props.getProperty("r_l162", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l163" name="r_l163" size="2" maxlength="2" value="<%=props.getProperty("r_l163", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l164" name="r_l164" size="2" maxlength="2" value="<%=props.getProperty("r_l164", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l165" name="r_l165" size="2" maxlength="2" value="<%=props.getProperty("r_l165", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l166" name="r_l166" size="2" maxlength="2" value="<%=props.getProperty("r_l166", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l167" name="r_l167" size="2" maxlength="2" value="<%=props.getProperty("r_l167", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l168" name="r_l168" size="2" maxlength="2" value="<%=props.getProperty("r_l168", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l169" name="r_l169" size="2" maxlength="2" value="<%=props.getProperty("r_l169", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1610" name="r_l1610" size="2" maxlength="2" value="<%=props.getProperty("r_l1610", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1611" name="r_l1611" size="2" maxlength="2" value="<%=props.getProperty("r_l1611", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1612" name="r_l1612" size="2" maxlength="2" value="<%=props.getProperty("r_l1612", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1613" name="r_l1613" size="2" maxlength="2" value="<%=props.getProperty("r_l1613", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1614" name="r_l1614" size="2" maxlength="2" value="<%=props.getProperty("r_l1614", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1615" name="r_l1615" size="2" maxlength="2" value="<%=props.getProperty("r_l1615", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l171" name="r_l171" size="2" maxlength="2" value="<%=props.getProperty("r_l171", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l172" name="r_l172" size="2" maxlength="2" value="<%=props.getProperty("r_l172", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l173" name="r_l173" size="2" maxlength="2" value="<%=props.getProperty("r_l173", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l174" name="r_l174" size="2" maxlength="2" value="<%=props.getProperty("r_l174", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l175" name="r_l175" size="2" maxlength="2" value="<%=props.getProperty("r_l175", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l176" name="r_l176" size="2" maxlength="2" value="<%=props.getProperty("r_l176", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l177" name="r_l177" size="2" maxlength="2" value="<%=props.getProperty("r_l177", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l178" name="r_l178" size="2" maxlength="2" value="<%=props.getProperty("r_l178", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l179" name="r_l179" size="2" maxlength="2" value="<%=props.getProperty("r_l179", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1710" name="r_l1710" size="2" maxlength="2" value="<%=props.getProperty("r_l1710", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1711" name="r_l1711" size="2" maxlength="2" value="<%=props.getProperty("r_l1711", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1712" name="r_l1712" size="2" maxlength="2" value="<%=props.getProperty("r_l1712", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1713" name="r_l1713" size="2" maxlength="2" value="<%=props.getProperty("r_l1713", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1714" name="r_l1714" size="2" maxlength="2" value="<%=props.getProperty("r_l1714", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1715" name="r_l1715" size="2" maxlength="2" value="<%=props.getProperty("r_l1715", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l181" name="r_l181" size="2" maxlength="2" value="<%=props.getProperty("r_l181", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l182" name="r_l182" size="2" maxlength="2" value="<%=props.getProperty("r_l182", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l183" name="r_l183" size="2" maxlength="2" value="<%=props.getProperty("r_l183", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l184" name="r_l184" size="2" maxlength="2" value="<%=props.getProperty("r_l184", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l185" name="r_l185" size="2" maxlength="2" value="<%=props.getProperty("r_l185", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l186" name="r_l186" size="2" maxlength="2" value="<%=props.getProperty("r_l186", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l187" name="r_l187" size="2" maxlength="2" value="<%=props.getProperty("r_l187", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l188" name="r_l188" size="2" maxlength="2" value="<%=props.getProperty("r_l188", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l189" name="r_l189" size="2" maxlength="2" value="<%=props.getProperty("r_l189", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1810" name="r_l1810" size="2" maxlength="2" value="<%=props.getProperty("r_l1810", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1811" name="r_l1811" size="2" maxlength="2" value="<%=props.getProperty("r_l1811", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1812" name="r_l1812" size="2" maxlength="2" value="<%=props.getProperty("r_l1812", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1813" name="r_l1813" size="2" maxlength="2" value="<%=props.getProperty("r_l1813", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1814" name="r_l1814" size="2" maxlength="2" value="<%=props.getProperty("r_l1814", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1815" name="r_l1815" size="2" maxlength="2" value="<%=props.getProperty("r_l1815", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l191" name="r_l191" size="2" maxlength="2" value="<%=props.getProperty("r_l191", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l192" name="r_l192" size="2" maxlength="2" value="<%=props.getProperty("r_l192", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l193" name="r_l193" size="2" maxlength="2" value="<%=props.getProperty("r_l193", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l194" name="r_l194" size="2" maxlength="2" value="<%=props.getProperty("r_l194", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l195" name="r_l195" size="2" maxlength="2" value="<%=props.getProperty("r_l195", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l196" name="r_l196" size="2" maxlength="2" value="<%=props.getProperty("r_l196", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l197" name="r_l197" size="2" maxlength="2" value="<%=props.getProperty("r_l197", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l198" name="r_l198" size="2" maxlength="2" value="<%=props.getProperty("r_l198", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l199" name="r_l199" size="2" maxlength="2" value="<%=props.getProperty("r_l199", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1910" name="r_l1910" size="2" maxlength="2" value="<%=props.getProperty("r_l1910", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1911" name="r_l1911" size="2" maxlength="2" value="<%=props.getProperty("r_l1911", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1912" name="r_l1912" size="2" maxlength="2" value="<%=props.getProperty("r_l1912", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1913" name="r_l1913" size="2" maxlength="2" value="<%=props.getProperty("r_l1913", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1914" name="r_l1914" size="2" maxlength="2" value="<%=props.getProperty("r_l1914", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l1915" name="r_l1915" size="2" maxlength="2" value="<%=props.getProperty("r_l1915", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l201" name="r_l201" size="2" maxlength="2" value="<%=props.getProperty("r_l201", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l202" name="r_l202" size="2" maxlength="2" value="<%=props.getProperty("r_l202", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l203" name="r_l203" size="2" maxlength="2" value="<%=props.getProperty("r_l203", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l204" name="r_l204" size="2" maxlength="2" value="<%=props.getProperty("r_l204", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l205" name="r_l205" size="2" maxlength="2" value="<%=props.getProperty("r_l205", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l206" name="r_l206" size="2" maxlength="2" value="<%=props.getProperty("r_l206", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l207" name="r_l207" size="2" maxlength="2" value="<%=props.getProperty("r_l207", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l208" name="r_l208" size="2" maxlength="2" value="<%=props.getProperty("r_l208", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l209" name="r_l209" size="2" maxlength="2" value="<%=props.getProperty("r_l209", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2010" name="r_l2010" size="2" maxlength="2" value="<%=props.getProperty("r_l2010", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2011" name="r_l2011" size="2" maxlength="2" value="<%=props.getProperty("r_l2011", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2012" name="r_l2012" size="2" maxlength="2" value="<%=props.getProperty("r_l2012", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2013" name="r_l2013" size="2" maxlength="2" value="<%=props.getProperty("r_l2013", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2014" name="r_l2014" size="2" maxlength="2" value="<%=props.getProperty("r_l2014", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2015" name="r_l2015" size="2" maxlength="2" value="<%=props.getProperty("r_l2015", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l21_1" name="r_l21_1" size="2" maxlength="2" value="<%=props.getProperty("r_l21_1", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_2" name="r_l21_2" size="2" maxlength="2" value="<%=props.getProperty("r_l21_2", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_3" name="r_l21_3" size="2" maxlength="2" value="<%=props.getProperty("r_l21_3", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_4" name="r_l21_4" size="2" maxlength="2" value="<%=props.getProperty("r_l21_4", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_5" name="r_l21_5" size="2" maxlength="2" value="<%=props.getProperty("r_l21_5", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_6" name="r_l21_6" size="2" maxlength="2" value="<%=props.getProperty("r_l21_6", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_7" name="r_l21_7" size="2" maxlength="2" value="<%=props.getProperty("r_l21_7", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_8" name="r_l21_8" size="2" maxlength="2" value="<%=props.getProperty("r_l21_8", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_9" name="r_l21_9" size="2" maxlength="2" value="<%=props.getProperty("r_l21_9", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_10" name="r_l21_10" size="2" maxlength="2" value="<%=props.getProperty("r_l21_10", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_11" name="r_l21_11" size="2" maxlength="2" value="<%=props.getProperty("r_l21_11", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_12" name="r_l21_12" size="2" maxlength="2" value="<%=props.getProperty("r_l21_12", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_13" name="r_l21_13" size="2" maxlength="2" value="<%=props.getProperty("r_l21_13", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_14" name="r_l21_14" size="2" maxlength="2" value="<%=props.getProperty("r_l21_14", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l21_15" name="r_l21_15" size="2" maxlength="2" value="<%=props.getProperty("r_l21_15", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l221" name="r_l221" size="2" maxlength="2" value="<%=props.getProperty("r_l221", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l222" name="r_l222" size="2" maxlength="2" value="<%=props.getProperty("r_l222", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l223" name="r_l223" size="2" maxlength="2" value="<%=props.getProperty("r_l223", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l224" name="r_l224" size="2" maxlength="2" value="<%=props.getProperty("r_l224", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l225" name="r_l225" size="2" maxlength="2" value="<%=props.getProperty("r_l225", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l226" name="r_l226" size="2" maxlength="2" value="<%=props.getProperty("r_l226", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l227" name="r_l227" size="2" maxlength="2" value="<%=props.getProperty("r_l227", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l228" name="r_l228" size="2" maxlength="2" value="<%=props.getProperty("r_l228", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l229" name="r_l229" size="2" maxlength="2" value="<%=props.getProperty("r_l229", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2210" name="r_l2210" size="2" maxlength="2" value="<%=props.getProperty("r_l2210", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2211" name="r_l2211" size="2" maxlength="2" value="<%=props.getProperty("r_l2211", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2212" name="r_l2212" size="2" maxlength="2" value="<%=props.getProperty("r_l2212", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2213" name="r_l2213" size="2" maxlength="2" value="<%=props.getProperty("r_l2213", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2214" name="r_l2214" size="2" maxlength="2" value="<%=props.getProperty("r_l2214", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2215" name="r_l2215" size="2" maxlength="2" value="<%=props.getProperty("r_l2215", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l231" name="r_l231" size="2" maxlength="2" value="<%=props.getProperty("r_l231", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l232" name="r_l232" size="2" maxlength="2" value="<%=props.getProperty("r_l232", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l233" name="r_l233" size="2" maxlength="2" value="<%=props.getProperty("r_l233", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l234" name="r_l234" size="2" maxlength="2" value="<%=props.getProperty("r_l234", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l235" name="r_l235" size="2" maxlength="2" value="<%=props.getProperty("r_l235", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l236" name="r_l236" size="2" maxlength="2" value="<%=props.getProperty("r_l236", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l237" name="r_l237" size="2" maxlength="2" value="<%=props.getProperty("r_l237", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l238" name="r_l238" size="2" maxlength="2" value="<%=props.getProperty("r_l238", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l239" name="r_l239" size="2" maxlength="2" value="<%=props.getProperty("r_l239", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2310" name="r_l2310" size="2" maxlength="2" value="<%=props.getProperty("r_l2310", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2311" name="r_l2311" size="2" maxlength="2" value="<%=props.getProperty("r_l2311", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2312" name="r_l2312" size="2" maxlength="2" value="<%=props.getProperty("r_l2312", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2313" name="r_l2313" size="2" maxlength="2" value="<%=props.getProperty("r_l2313", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2314" name="r_l2314" size="2" maxlength="2" value="<%=props.getProperty("r_l2314", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2315" name="r_l2315" size="2" maxlength="2" value="<%=props.getProperty("r_l2315", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l241" name="r_l241" size="2" maxlength="2" value="<%=props.getProperty("r_l241", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l242" name="r_l242" size="2" maxlength="2" value="<%=props.getProperty("r_l242", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l243" name="r_l243" size="2" maxlength="2" value="<%=props.getProperty("r_l243", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l244" name="r_l244" size="2" maxlength="2" value="<%=props.getProperty("r_l244", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l245" name="r_l245" size="2" maxlength="2" value="<%=props.getProperty("r_l245", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l246" name="r_l246" size="2" maxlength="2" value="<%=props.getProperty("r_l246", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l247" name="r_l247" size="2" maxlength="2" value="<%=props.getProperty("r_l247", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l248" name="r_l248" size="2" maxlength="2" value="<%=props.getProperty("r_l248", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l249" name="r_l249" size="2" maxlength="2" value="<%=props.getProperty("r_l249", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2410" name="r_l2410" size="2" maxlength="2" value="<%=props.getProperty("r_l2410", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2411" name="r_l2411" size="2" maxlength="2" value="<%=props.getProperty("r_l2411", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2412" name="r_l2412" size="2" maxlength="2" value="<%=props.getProperty("r_l2412", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2413" name="r_l2413" size="2" maxlength="2" value="<%=props.getProperty("r_l2413", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2414" name="r_l2414" size="2" maxlength="2" value="<%=props.getProperty("r_l2414", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2415" name="r_l2415" size="2" maxlength="2" value="<%=props.getProperty("r_l2415", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l251" name="r_l251" size="2" maxlength="2" value="<%=props.getProperty("r_l251", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l252" name="r_l252" size="2" maxlength="2" value="<%=props.getProperty("r_l252", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l253" name="r_l253" size="2" maxlength="2" value="<%=props.getProperty("r_l253", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l254" name="r_l254" size="2" maxlength="2" value="<%=props.getProperty("r_l254", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l255" name="r_l255" size="2" maxlength="2" value="<%=props.getProperty("r_l255", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l256" name="r_l256" size="2" maxlength="2" value="<%=props.getProperty("r_l256", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l257" name="r_l257" size="2" maxlength="2" value="<%=props.getProperty("r_l257", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l258" name="r_l258" size="2" maxlength="2" value="<%=props.getProperty("r_l258", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l259" name="r_l259" size="2" maxlength="2" value="<%=props.getProperty("r_l259", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2510" name="r_l2510" size="2" maxlength="2" value="<%=props.getProperty("r_l2510", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2511" name="r_l2511" size="2" maxlength="2" value="<%=props.getProperty("r_l2511", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2512" name="r_l2512" size="2" maxlength="2" value="<%=props.getProperty("r_l2512", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2513" name="r_l2513" size="2" maxlength="2" value="<%=props.getProperty("r_l2513", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2514" name="r_l2514" size="2" maxlength="2" value="<%=props.getProperty("r_l2514", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2515" name="r_l2515" size="2" maxlength="2" value="<%=props.getProperty("r_l2515", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l261" name="r_l261" size="2" maxlength="2" value="<%=props.getProperty("r_l261", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l262" name="r_l262" size="2" maxlength="2" value="<%=props.getProperty("r_l262", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l263" name="r_l263" size="2" maxlength="2" value="<%=props.getProperty("r_l263", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l264" name="r_l264" size="2" maxlength="2" value="<%=props.getProperty("r_l264", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l265" name="r_l265" size="2" maxlength="2" value="<%=props.getProperty("r_l265", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l266" name="r_l266" size="2" maxlength="2" value="<%=props.getProperty("r_l266", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l267" name="r_l267" size="2" maxlength="2" value="<%=props.getProperty("r_l267", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l268" name="r_l268" size="2" maxlength="2" value="<%=props.getProperty("r_l268", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l269" name="r_l269" size="2" maxlength="2" value="<%=props.getProperty("r_l269", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2610" name="r_l2610" size="2" maxlength="2" value="<%=props.getProperty("r_l2610", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2611" name="r_l2611" size="2" maxlength="2" value="<%=props.getProperty("r_l2611", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2612" name="r_l2612" size="2" maxlength="2" value="<%=props.getProperty("r_l2612", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2613" name="r_l2613" size="2" maxlength="2" value="<%=props.getProperty("r_l2613", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2614" name="r_l2614" size="2" maxlength="2" value="<%=props.getProperty("r_l2614", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2615" name="r_l2615" size="2" maxlength="2" value="<%=props.getProperty("r_l2615", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l271" name="r_l271" size="2" maxlength="2" value="<%=props.getProperty("r_l271", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l272" name="r_l272" size="2" maxlength="2" value="<%=props.getProperty("r_l272", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l273" name="r_l273" size="2" maxlength="2" value="<%=props.getProperty("r_l273", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l274" name="r_l274" size="2" maxlength="2" value="<%=props.getProperty("r_l274", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l275" name="r_l275" size="2" maxlength="2" value="<%=props.getProperty("r_l275", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l276" name="r_l276" size="2" maxlength="2" value="<%=props.getProperty("r_l276", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l277" name="r_l277" size="2" maxlength="2" value="<%=props.getProperty("r_l277", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l278" name="r_l278" size="2" maxlength="2" value="<%=props.getProperty("r_l278", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l279" name="r_l279" size="2" maxlength="2" value="<%=props.getProperty("r_l279", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2710" name="r_l2710" size="2" maxlength="2" value="<%=props.getProperty("r_l2710", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2711" name="r_l2711" size="2" maxlength="2" value="<%=props.getProperty("r_l2711", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2712" name="r_l2712" size="2" maxlength="2" value="<%=props.getProperty("r_l2712", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2713" name="r_l2713" size="2" maxlength="2" value="<%=props.getProperty("r_l2713", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2714" name="r_l2714" size="2" maxlength="2" value="<%=props.getProperty("r_l2714", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2715" name="r_l2715" size="2" maxlength="2" value="<%=props.getProperty("r_l2715", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l281" name="r_l281" size="2" maxlength="2" value="<%=props.getProperty("r_l281", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l282" name="r_l282" size="2" maxlength="2" value="<%=props.getProperty("r_l282", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l283" name="r_l283" size="2" maxlength="2" value="<%=props.getProperty("r_l283", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l284" name="r_l284" size="2" maxlength="2" value="<%=props.getProperty("r_l284", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l285" name="r_l285" size="2" maxlength="2" value="<%=props.getProperty("r_l285", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l286" name="r_l286" size="2" maxlength="2" value="<%=props.getProperty("r_l286", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l287" name="r_l287" size="2" maxlength="2" value="<%=props.getProperty("r_l287", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l288" name="r_l288" size="2" maxlength="2" value="<%=props.getProperty("r_l288", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l289" name="r_l289" size="2" maxlength="2" value="<%=props.getProperty("r_l289", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2810" name="r_l2810" size="2" maxlength="2" value="<%=props.getProperty("r_l2810", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2811" name="r_l2811" size="2" maxlength="2" value="<%=props.getProperty("r_l2811", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2812" name="r_l2812" size="2" maxlength="2" value="<%=props.getProperty("r_l2812", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2813" name="r_l2813" size="2" maxlength="2" value="<%=props.getProperty("r_l2813", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2814" name="r_l2814" size="2" maxlength="2" value="<%=props.getProperty("r_l2814", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2815" name="r_l2815" size="2" maxlength="2" value="<%=props.getProperty("r_l2815", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l291" name="r_l291" size="2" maxlength="2" value="<%=props.getProperty("r_l291", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l292" name="r_l292" size="2" maxlength="2" value="<%=props.getProperty("r_l292", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l293" name="r_l293" size="2" maxlength="2" value="<%=props.getProperty("r_l293", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l294" name="r_l294" size="2" maxlength="2" value="<%=props.getProperty("r_l294", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l295" name="r_l295" size="2" maxlength="2" value="<%=props.getProperty("r_l295", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l296" name="r_l296" size="2" maxlength="2" value="<%=props.getProperty("r_l296", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l297" name="r_l297" size="2" maxlength="2" value="<%=props.getProperty("r_l297", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l298" name="r_l298" size="2" maxlength="2" value="<%=props.getProperty("r_l298", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l299" name="r_l299" size="2" maxlength="2" value="<%=props.getProperty("r_l299", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2910" name="r_l2910" size="2" maxlength="2" value="<%=props.getProperty("r_l2910", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2911" name="r_l2911" size="2" maxlength="2" value="<%=props.getProperty("r_l2911", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2912" name="r_l2912" size="2" maxlength="2" value="<%=props.getProperty("r_l2912", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2913" name="r_l2913" size="2" maxlength="2" value="<%=props.getProperty("r_l2913", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2914" name="r_l2914" size="2" maxlength="2" value="<%=props.getProperty("r_l2914", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l2915" name="r_l2915" size="2" maxlength="2" value="<%=props.getProperty("r_l2915", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l301" name="r_l301" size="2" maxlength="2" value="<%=props.getProperty("r_l301", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l302" name="r_l302" size="2" maxlength="2" value="<%=props.getProperty("r_l302", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l303" name="r_l303" size="2" maxlength="2" value="<%=props.getProperty("r_l303", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l304" name="r_l304" size="2" maxlength="2" value="<%=props.getProperty("r_l304", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l305" name="r_l305" size="2" maxlength="2" value="<%=props.getProperty("r_l305", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l306" name="r_l306" size="2" maxlength="2" value="<%=props.getProperty("r_l306", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l307" name="r_l307" size="2" maxlength="2" value="<%=props.getProperty("r_l307", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l308" name="r_l308" size="2" maxlength="2" value="<%=props.getProperty("r_l308", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l309" name="r_l309" size="2" maxlength="2" value="<%=props.getProperty("r_l309", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3010" name="r_l3010" size="2" maxlength="2" value="<%=props.getProperty("r_l3010", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3011" name="r_l3011" size="2" maxlength="2" value="<%=props.getProperty("r_l3011", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3012" name="r_l3012" size="2" maxlength="2" value="<%=props.getProperty("r_l3012", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3013" name="r_l3013" size="2" maxlength="2" value="<%=props.getProperty("r_l3013", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3014" name="r_l3014" size="2" maxlength="2" value="<%=props.getProperty("r_l3014", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3015" name="r_l3015" size="2" maxlength="2" value="<%=props.getProperty("r_l3015", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l31_1" name="r_l31_1" size="2" maxlength="2" value="<%=props.getProperty("r_l31_1", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_2" name="r_l31_2" size="2" maxlength="2" value="<%=props.getProperty("r_l31_2", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_3" name="r_l31_3" size="2" maxlength="2" value="<%=props.getProperty("r_l31_3", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_4" name="r_l31_4" size="2" maxlength="2" value="<%=props.getProperty("r_l31_4", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_5" name="r_l31_5" size="2" maxlength="2" value="<%=props.getProperty("r_l31_5", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_6" name="r_l31_6" size="2" maxlength="2" value="<%=props.getProperty("r_l31_6", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_7" name="r_l31_7" size="2" maxlength="2" value="<%=props.getProperty("r_l31_7", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_8" name="r_l31_8" size="2" maxlength="2" value="<%=props.getProperty("r_l31_8", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_9" name="r_l31_9" size="2" maxlength="2" value="<%=props.getProperty("r_l31_9", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_10" name="r_l31_10" size="2" maxlength="2" value="<%=props.getProperty("r_l31_10", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_11" name="r_l31_11" size="2" maxlength="2" value="<%=props.getProperty("r_l31_11", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_12" name="r_l31_12" size="2" maxlength="2" value="<%=props.getProperty("r_l31_12", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_13" name="r_l31_13" size="2" maxlength="2" value="<%=props.getProperty("r_l31_13", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_14" name="r_l31_14" size="2" maxlength="2" value="<%=props.getProperty("r_l31_14", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l31_15" name="r_l31_15" size="2" maxlength="2" value="<%=props.getProperty("r_l31_15", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l321" name="r_l321" size="2" maxlength="2" value="<%=props.getProperty("r_l321", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l322" name="r_l322" size="2" maxlength="2" value="<%=props.getProperty("r_l322", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l323" name="r_l323" size="2" maxlength="2" value="<%=props.getProperty("r_l323", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l324" name="r_l324" size="2" maxlength="2" value="<%=props.getProperty("r_l324", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l325" name="r_l325" size="2" maxlength="2" value="<%=props.getProperty("r_l325", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l326" name="r_l326" size="2" maxlength="2" value="<%=props.getProperty("r_l326", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l327" name="r_l327" size="2" maxlength="2" value="<%=props.getProperty("r_l327", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l328" name="r_l328" size="2" maxlength="2" value="<%=props.getProperty("r_l328", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l329" name="r_l329" size="2" maxlength="2" value="<%=props.getProperty("r_l329", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3210" name="r_l3210" size="2" maxlength="2" value="<%=props.getProperty("r_l3210", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3211" name="r_l3211" size="2" maxlength="2" value="<%=props.getProperty("r_l3211", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3212" name="r_l3212" size="2" maxlength="2" value="<%=props.getProperty("r_l3212", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3213" name="r_l3213" size="2" maxlength="2" value="<%=props.getProperty("r_l3213", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3214" name="r_l3214" size="2" maxlength="2" value="<%=props.getProperty("r_l3214", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3215" name="r_l3215" size="2" maxlength="2" value="<%=props.getProperty("r_l3215", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l331" name="r_l331" size="2" maxlength="2" value="<%=props.getProperty("r_l331", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l332" name="r_l332" size="2" maxlength="2" value="<%=props.getProperty("r_l332", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l333" name="r_l333" size="2" maxlength="2" value="<%=props.getProperty("r_l333", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l334" name="r_l334" size="2" maxlength="2" value="<%=props.getProperty("r_l334", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l335" name="r_l335" size="2" maxlength="2" value="<%=props.getProperty("r_l335", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l336" name="r_l336" size="2" maxlength="2" value="<%=props.getProperty("r_l336", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l337" name="r_l337" size="2" maxlength="2" value="<%=props.getProperty("r_l337", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l338" name="r_l338" size="2" maxlength="2" value="<%=props.getProperty("r_l338", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l339" name="r_l339" size="2" maxlength="2" value="<%=props.getProperty("r_l339", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3310" name="r_l3310" size="2" maxlength="2" value="<%=props.getProperty("r_l3310", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3311" name="r_l3311" size="2" maxlength="2" value="<%=props.getProperty("r_l3311", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3312" name="r_l3312" size="2" maxlength="2" value="<%=props.getProperty("r_l3312", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3313" name="r_l3313" size="2" maxlength="2" value="<%=props.getProperty("r_l3313", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3314" name="r_l3314" size="2" maxlength="2" value="<%=props.getProperty("r_l3314", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3315" name="r_l3315" size="2" maxlength="2" value="<%=props.getProperty("r_l3315", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l341" name="r_l341" size="2" maxlength="2" value="<%=props.getProperty("r_l341", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l342" name="r_l342" size="2" maxlength="2" value="<%=props.getProperty("r_l342", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l343" name="r_l343" size="2" maxlength="2" value="<%=props.getProperty("r_l343", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l344" name="r_l344" size="2" maxlength="2" value="<%=props.getProperty("r_l344", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l345" name="r_l345" size="2" maxlength="2" value="<%=props.getProperty("r_l345", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l346" name="r_l346" size="2" maxlength="2" value="<%=props.getProperty("r_l346", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l347" name="r_l347" size="2" maxlength="2" value="<%=props.getProperty("r_l347", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l348" name="r_l348" size="2" maxlength="2" value="<%=props.getProperty("r_l348", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l349" name="r_l349" size="2" maxlength="2" value="<%=props.getProperty("r_l349", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3410" name="r_l3410" size="2" maxlength="2" value="<%=props.getProperty("r_l3410", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3411" name="r_l3411" size="2" maxlength="2" value="<%=props.getProperty("r_l3411", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3412" name="r_l3412" size="2" maxlength="2" value="<%=props.getProperty("r_l3412", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3413" name="r_l3413" size="2" maxlength="2" value="<%=props.getProperty("r_l3413", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3414" name="r_l3414" size="2" maxlength="2" value="<%=props.getProperty("r_l3414", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3415" name="r_l3415" size="2" maxlength="2" value="<%=props.getProperty("r_l3415", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><input type="text" id="r_l351" name="r_l351" size="2" maxlength="2" value="<%=props.getProperty("r_l351", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l352" name="r_l352" size="2" maxlength="2" value="<%=props.getProperty("r_l352", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l353" name="r_l353" size="2" maxlength="2" value="<%=props.getProperty("r_l353", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l354" name="r_l354" size="2" maxlength="2" value="<%=props.getProperty("r_l354", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l355" name="r_l355" size="2" maxlength="2" value="<%=props.getProperty("r_l355", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l356" name="r_l356" size="2" maxlength="2" value="<%=props.getProperty("r_l356", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l357" name="r_l357" size="2" maxlength="2" value="<%=props.getProperty("r_l357", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l358" name="r_l358" size="2" maxlength="2" value="<%=props.getProperty("r_l358", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l359" name="r_l359" size="2" maxlength="2" value="<%=props.getProperty("r_l359", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3510" name="r_l3510" size="2" maxlength="2" value="<%=props.getProperty("r_l3510", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3511" name="r_l3511" size="2" maxlength="2" value="<%=props.getProperty("r_l3511", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3512" name="r_l3512" size="2" maxlength="2" value="<%=props.getProperty("r_l3512", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3513" name="r_l3513" size="2" maxlength="2" value="<%=props.getProperty("r_l3513", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3514" name="r_l3514" size="2" maxlength="2" value="<%=props.getProperty("r_l3514", "")%>"/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3515" name="r_l3515" size="2" maxlength="2" value="<%=props.getProperty("r_l3515", "")%>"/></td>
				</tr>
			</table>
		</td>
		<td style="display:none">
			<table>
				<tr>
					<td><input type="text" id="r_l351" name="r_l351" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l352" name="r_l352" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l353" name="r_l353" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l354" name="r_l354" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l355" name="r_l355" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l356" name="r_l356" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l357" name="r_l357" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l358" name="r_l358" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l359" name="r_l359" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3510" name="r_l3510" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3511" name="r_l3511" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3512" name="r_l3512" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3513" name="r_l3513" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3514" name="r_l3514" size="2" maxlength="2" value=""/></td>
				</tr>
				<tr>
					<td><input type="text" id="r_l3515" name="r_l3515" size="2" maxlength="2" value=""/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>Endometrial thickness</td>
		<td colspan="2"><input type="text" id="endometrial1" name="endometrial1" maxlength="7" size="8" value="<%=props.getProperty("endometrial1", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial2" name="endometrial2" maxlength="7" size="8" value="<%=props.getProperty("endometrial2", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial3" name="endometrial3" maxlength="7" size="8" value="<%=props.getProperty("endometrial3", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial4" name="endometrial4" maxlength="7" size="8" value="<%=props.getProperty("endometrial4", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial5" name="endometrial5" maxlength="7" size="8" value="<%=props.getProperty("endometrial5", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial6" name="endometrial6" maxlength="7" size="8" value="<%=props.getProperty("endometrial6", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial7" name="endometrial7" maxlength="7" size="8" value="<%=props.getProperty("endometrial7", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial8" name="endometrial8" maxlength="7" size="8" value="<%=props.getProperty("endometrial8", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial9" name="endometrial9" maxlength="7" size="8" value="<%=props.getProperty("endometrial9", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial10" name="endometrial10" maxlength="7" size="8" value="<%=props.getProperty("endometrial10", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial11" name="endometrial11" maxlength="7" size="8" value="<%=props.getProperty("endometrial11", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial12" name="endometrial12" maxlength="7" size="8" value="<%=props.getProperty("endometrial12", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial13" name="endometrial13" maxlength="7" size="8" value="<%=props.getProperty("endometrial13", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial14" name="endometrial14" maxlength="7" size="8" value="<%=props.getProperty("endometrial14", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial15" name="endometrial15" maxlength="7" size="8" value="<%=props.getProperty("endometrial15", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial16" name="endometrial16" maxlength="7" size="8" value="<%=props.getProperty("endometrial16", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial17" name="endometrial17" maxlength="7" size="8" value="<%=props.getProperty("endometrial17", "")%>"/></td>
		<td colspan="2"><input type="text" id="endometrial18" name="endometrial18" maxlength="7" size="8" value="<%=props.getProperty("endometrial18", "")%>"/></td>
	</tr>
	<tr>
		<td>Sonographer/Room/Probe</td>
		<td colspan="2"><input type="text" id="sonographer1" name="sonographer1" maxlength="7" size="8" value="<%=props.getProperty("sonographer1", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer2" name="sonographer2" maxlength="7" size="8" value="<%=props.getProperty("sonographer2", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer3" name="sonographer3" maxlength="7" size="8" value="<%=props.getProperty("sonographer3", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer4" name="sonographer4" maxlength="7" size="8" value="<%=props.getProperty("sonographer4", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer5" name="sonographer5" maxlength="7" size="8" value="<%=props.getProperty("sonographer5", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer6" name="sonographer6" maxlength="7" size="8" value="<%=props.getProperty("sonographer6", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer7" name="sonographer7" maxlength="7" size="8" value="<%=props.getProperty("sonographer7", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer8" name="sonographer8" maxlength="7" size="8" value="<%=props.getProperty("sonographer8", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer9" name="sonographer9" maxlength="7" size="8" value="<%=props.getProperty("sonographer9", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer10" name="sonographer10" maxlength="7" size="8" value="<%=props.getProperty("sonographer10", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer11" name="sonographer11" maxlength="7" size="8" value="<%=props.getProperty("sonographer11", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer12" name="sonographer12" maxlength="7" size="8" value="<%=props.getProperty("sonographer12", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer13" name="sonographer13" maxlength="7" size="8" value="<%=props.getProperty("sonographer13", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer14" name="sonographer14" maxlength="7" size="8" value="<%=props.getProperty("sonographer14", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer15" name="sonographer15" maxlength="7" size="8" value="<%=props.getProperty("sonographer15", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer16" name="sonographer16" maxlength="7" size="8" value="<%=props.getProperty("sonographer16", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer17" name="sonographer17" maxlength="7" size="8" value="<%=props.getProperty("sonographer17", "")%>"/></td>
		<td colspan="2"><input type="text" id="sonographer18" name="sonographer18" maxlength="7" size="8" value="<%=props.getProperty("sonographer18", "")%>"/></td>
	</tr>
</tbody>
</table>
</td>
</tr>

</tbody>
</table>


<!-- ################################################################################################################# -->

<table>
<tr>
<td height="15">&nbsp;

</td>
</tr>
</table>

<!-- ################################################################################################################### -->

<!-- ################################################################################################################### -->
<table width="95%" border="0">
<tr>
<td align="center" class="style76">
            <input type="submit" value="Save" onclick="javascript:return onSave('<html:rewrite page="/form/formname.do"/>');" />
		    <input type="submit" value="Save and Exit" onclick="javascript:return onSaveExit('<html:rewrite page="/form/formname.do"/>');"/>
            <input type="submit" value="Exit" onclick="javascript:return onExit();"/>
         <!--   <input type="submit"  value="Print" onClick="javascript:return onPrint();"/> 	-->
			<input type="submit"  value="Print" onClick="javascript:window.print();"/>	
          </td>
</tr>
</table>

</html:form>
</body>
</html:html>