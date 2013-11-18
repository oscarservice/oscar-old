<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.oscarehr.ui.servlet.ImageRenderingServlet"%>
<%@page import="org.oscarehr.util.LoggedInInfo"%>
<%@page import="org.oscarehr.util.DigitalSignatureUtils"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="oscar.form.FrmRecordHelp"%>
<%@page import="oscar.form.FrmRecordFactory"%>
<%@page import="oscar.form.FrmRecord"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html>
<head>

<%!
int cell_width = 32;
int cell_height = 18;

String cell_width_str = cell_width+"px";
String cell_height_str = cell_height+"px";
%>

<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.7.3.custom.min.js"></script>
<script type="text/javascript" src="../js/html2canvas.js"></script>
<script type="text/javascript" src="../js/jquery.plugin.html2canvas.js"></script>

<link rel="stylesheet" type="text/css" href="../js/jquery_css/smoothness/jquery-ui-1.7.3.custom.css">

<script>
jQuery.noConflict();

jQuery(document).ready(function(){
	bind_event_("table_right_ear");
	bind_event_("table_left_ear");
});

function bind_event_(clsname)
{
	/* jQuery("."+clsname).find("tr").mouseover(function(){
		jQuery(this).addClass("tr_mouse_over");
	});
	jQuery("."+clsname).find("tr").mouseout(function(){
		jQuery(this).removeClass("tr_mouse_over");
	}); */
	
	jQuery("."+clsname).find("[type=text]").focus(function(){
		jQuery("."+clsname).find("tr").removeClass("tr_focus_");
		jQuery(this).parent().parent().addClass("tr_focus_");
	});
}

jQuery(document).ready(function(){
	
	jQuery( "#dialog_cell_value" ).dialog({
		 autoOpen: false,
		 modal: true,
		 title: "Set Value"
	});
	
	jQuery( "#dialog_printing_wait" ).dialog({
		 autoOpen: false,
		 modal: true,
		 title: "Please Wait"
	});
	
	jQuery("#select_cell_value").change(function(){
		var selectedOption = jQuery("option:selected", this);
		jQuery(this).css("background-image", jQuery(selectedOption).css("background-image"));
	});
	
	//workaround for IE (not passing hidden values)
	//jQuery("#r500v-10").val();
});

function dialog_init()
{
	alert("in dialog_init");
	jQuery( "#dialog_cell_value" ).dialog({
		 autoOpen: false,
		 modal: true,
		 title: "Set Value"
	});
	alert("after dialog_init");
}

var selected_x = -1;
var selected_y = -1;
var selected_txt_id = "";
var selected_cell_obj = null;

//open dialog
function onClickCell(x, y, txtId, cell_obj)
{
	//show/hide symbols according to the left/right table
	var startIndex = 0;
	var endIndex = 9;
	var selectedOptionIndex = 9;
	if(txtId.indexOf("r")==0)
	{
		startIndex = 9;
		endIndex = 18;
		selectedOptionIndex = 0;
	}
	jQuery("#select_cell_value").find("option").show();
	jQuery("option:selected", jQuery("#select_cell_value")).removeAttr("selected");
	
	var options_arr = jQuery("#select_cell_value").find("option");
	var i = -1;
	for(i=startIndex;i<endIndex;i++)
	{
	   jQuery(jQuery(options_arr[i]).hide());
	}
	//set 1st option's background image as a background image for the combo box
	jQuery(options_arr[selectedOptionIndex]).attr("selected", "selected");
	//jQuery("#select_cell_value").css("background-image", jQuery(options_arr[selectedOptionIndex]).css("background-image"));
	jQuery("#select_cell_value").change();
	
	jQuery( "#span_cell_id" ).html(x+"/"+y);
	jQuery( "#dialog_cell_value" ).dialog( "open" );
	selected_x = x;
	selected_y = y;
	selected_txt_id = txtId;
	selected_cell_obj = cell_obj;
	
	//var current_val = jQuery("#"+txtId).val();
	//jQuery("#span_current_cell_val").html(current_val);
	jQuery("#span_current_cell_val").html("");
	jQuery(selected_cell_obj).find("img").each(function(){
		var img_ele_clone = jQuery(this).clone();
		
		var tooltip_ = jQuery(img_ele_clone).attr("title")+" (click symbol to remove)";
		jQuery(img_ele_clone).attr("title", tooltip_);
		//add click handler to remove image
		jQuery(img_ele_clone).click(function(){
			jQuery(this).remove();
			recalc_td_content();
			recalc_textval();
		});
		
		jQuery("#span_current_cell_val").append(img_ele_clone);
	});	
}

//close dialog
function onClickCellValueDlgCancel()
{
	jQuery( "#dialog_cell_value" ).dialog( "close" );
}

function removeImgEle(imgObj)
{	
	var parentObj = jQuery(imgObj).parents("span#span_current_cell_val");
	if(parentObj!=null && parentObj.length==1)
	{
		jQuery(imgObj).remove();
		recalc_td_content();
		recalc_textval();	
	}	
}

function getImgElement(val)
{
	var tooltip = "";
	if(val=="r1")
		tooltip = "Rt - air unmasked";
	else if(val=="r2")
		tooltip = "Rt - air masked";
	else if(val=="r3")
		tooltip = "Rt - bone unmasked";
	else if(val=="r4")
		tooltip = "Rt - bone masked";
	else if(val=="r5")
		tooltip = "Rt - no response";
	else if(val=="r6")
		tooltip = "Rt - UCL";
	else if(val=="r7")
		tooltip = "Rt - No Rresponse";
	else if(val=="r8")
		tooltip = "Rt - Free Field";
	else if(val=="r9")
		tooltip = "Rt - Most Comfortable Listening";
	
	else if(val=="l1")
		tooltip = "Lt - air unmasked";
	else if(val=="l2")
		tooltip = "Lt - air masked";
	else if(val=="l3")
		tooltip = "Lt - bone unmasked";
	else if(val=="l4")
		tooltip = "Lt - bone masked";
	else if(val=="l5")
		tooltip = "Lt - no response";
	else if(val=="l6")
		tooltip = "Lt - UCL";
	else if(val=="l7")
		tooltip = "Lt - No Rresponse";
	else if(val=="l8")
		tooltip = "Lt - Free Field";
	else if(val=="l9")
		tooltip = "Lt - Most Comfortable Listening";
	
	tooltip = tooltip+" (click to remove)";
	
	var img_ele = "<img class='connecting_ele' onclick='removeImgEle(this);' title='"+tooltip+"' image_val='"+val+"' src='../images/audiologyform/"+val+".png'/>";
	return img_ele;
}

//add value to cell
function addCellValue(selected_symbol)
{
	var spanObj = jQuery("#span_current_cell_val");
	//var selectObj = jQuery("#select_cell_value");
	
	var current_span_val = jQuery(spanObj).html();
	//var selected_val = jQuery(selectObj).val();
	//var selected_val = getImgElement(jQuery(selectObj).val());
	selected_val = getImgElement(selected_symbol);
	
	if(current_span_val=="")
		current_span_val = selected_val;
	else
		current_span_val = current_span_val+""+selected_val;
	
	//set span content in dialog
	jQuery(spanObj).html(current_span_val);
	
	//set cell content in table
	recalc_td_content();
	
	//set text field value
	recalc_textval();
}

function recalc_td_content()
{
	var current_span_val = jQuery("#span_current_cell_val").html();
	
	//var first_txt_obj = jQuery(selected_cell_obj).children()[0];
	var first_txt_obj = jQuery(selected_cell_obj).parents("td:first").find("[type=text]");
	jQuery(selected_cell_obj).html("");
	jQuery(selected_cell_obj).append(first_txt_obj);
	//jQuery(selected_cell_obj).html(jQuery(selected_cell_obj).html()+current_span_val);
	jQuery("#span_current_cell_val").find("img").each(function(){
		var img_clone = jQuery(this).clone();
		
		//remove 'click to remove' as not removing symbol from td directly
		var tooltip_ = jQuery(img_clone).attr("title");
		tooltip_ = tooltip_.substring(tooltip_, tooltip_.indexOf("("));
		jQuery(img_clone).attr("title", tooltip_);
		
		//remove click handler to remove image .. from td only
		jQuery(img_clone).unbind("click");
		
		jQuery(selected_cell_obj).append(img_clone);
	});
}

function recalc_textval()
{
	//alert("in recalc_textval");
	var text_val = "";
	var image_val = "";
	jQuery(selected_cell_obj).find("img").each(function(){
		image_val = jQuery(this).attr("image_val");
		//alert("image_val = "+image_val);
		
		if(text_val=="")
			text_val = image_val;
		else
			text_val = text_val+","+image_val;
		//alert("text_val = "+text_val);
		
	});
	jQuery("#"+selected_txt_id).val(text_val);
	//alert("text val = "+jQuery("#"+selected_txt_id).val());
}

</script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/global.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="../share/calendar/calendar.css" title="win2k-cold-1">
<script type="text/javascript" src="../share/calendar/calendar.js"></script>
<script type="text/javascript" src="../share/calendar/lang/calendar-en.js"></script>
<script type="text/javascript" src="../share/calendar/calendar-setup.js"></script>

<script>
function imposeMaxLength(Event, Object, MaxLen)
{
    return (Object.value.length <= MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
}
function setupCalendar(id)
{
	Calendar.setup({
		inputField : id,
		ifFormat : "%Y/%m/%d",
		showsTime : false,
		button : id+"_call",
		singleClick : true,
		step : 1
	});
}
jQuery(document).ready(function(){
	setupCalendar("date1");
});
</script>

<title>Audiology Report</title>

<style type="text/css">
body {
	font-family: tahoma;
	font-size: 11px;
}

h3 {
	margin-top: 5px !important;
	margin-bottom: 5px !important;
	background-color: #D4D0C8;
}

ul {
	margin-top: 5px !important;
	margin-bottom: 5px !important;
}

.outerdiv { /*border: 1px solid black;*/
	width: 100%;
	height: 100%;
}

.mytable {
	width: 100%;
}

.label {
	font-weight: bold;
}

.ul_patient_history li span {
	margin-right: 10px;
}
.ul_patient_history li
{
	height: 23px !important;
}
.ul_patient_history li:hover
{
	background-color: #D2DADB;
}

.table_legend td {
	padding-right: 5px;
}
.table_legend tr
{
	background-color: #D2DADB;
}
.tr_mouse_over
{
	background-color: #BCCBD1 !important;
}

.tr_focus_
{
	background-color: #8B9EA5 !important;
}

.table_right_ear input,.table_left_ear input {
	width: 20px;
	border: none;
	font-weight: bold;
	font-size: 10px !important;
}

.table_speech_rec_1 input {
	width: 30px;
	border: none;
	font-weight: bold;
}

.table_speech_rec_1 {
	border: 1px solid black;
	border-collapse: collapse;
}

.table_impendance_result {
	border: 1px solid black;
}

.table_stapedial_acoustic_reflexes .border1 {
	border: 1px solid black;
}

.table_stapedial_acoustic_reflexes input {
	border: none;
	font-weight: bold;
}

.table_hearing_aid td {
	padding-right: 10px;
}

.table_hearing_aid span {
	margin-right: 10px;
}

.input1 {
	border: 1px solid black;
}

.td_level
{
	border-top: 1px dotted black;
	border-bottom: 1px dotted black;
	font-size: 10px;
}
.axis_label
{
	font-size: 12px;
	background-color: #D2DADB;
}
.x_axis_label
{	
	background-image: url(../images/audiologyform/other/x_label.png);
	background-repeat: no-repeat;
}
.y_axis_label
{
	width: 13px;
	background-image: url(../images/audiologyform/other/y_label.png);
	background-repeat: no-repeat;
}
/*#select_cell_value , #select_cell_value option
{
	background-repeat: no-repeat;
	background-position: left center;
	background-position: 5px 3px;
}
#select_cell_value option
{
	height: 15px;
}*/

.range_label
{
	font-size: 11px !important;
}
.table_right_ear , .table_left_ear , .middle_table
{
	/*border: 1px solid #D5E4E8;*/
	border-collapse: collapse;
	font-size: 10px !important;
	position: relative;
	z-index: 1;
}

.table_left_ear>tbody>tr>td , .table_right_ear>tbody>tr>td , .main_table>tbody>tr>td , .middle_table>tbody>tr>td
{
	padding: 0px !important;
}

.middle_table .div_line
{
	width: <%=(cell_width/2)-5%>px; 
	height: <%=cell_height_str%>;
	float: left;
	vertical-align: middle;
	background-image: url(../images/audiologyform/other/line_<%=cell_width%>.png); 
	background-repeat:repeat;
}

/* ---------------- css for 1st row : start ------------------ */
.div_cross_1st_row
{
	width: <%=(cell_width)%>px; 
	height: <%=cell_height_str%>;
	float: left;
	background-image: url(../images/audiologyform/other/line_cross_1st_row.png);
	background-position: center;  
	background-repeat:repeat;
	white-space: nowrap;
	overflow: hidden;	
}
.div_cross_1st_row>img
{
	/*padding-top: 10px;*/
	margin-top: 4px;
	padding-right: 1px;
}
.div_cross_1st_row:HOVER
{
	background-image: url(../images/audiologyform/other/line_cross_1st_row_hover.png);
	background-position: center; 
	background-repeat:repeat;
	cursor: pointer;
}
/* ---------------- css for 1st row : end  ------------------ */

/* ---------------- css for 1st col : start  ------------------ */
.div_cross_1st_col>img
{
	/*padding-top: 10px;*/
	margin-top: 4px;
	padding-right: 1px;
	overflow: hidden;
}
.div_cross_1st_col
{
	width: <%=cell_width%>px; 
	height: <%=cell_height_str%>;
	float: left;
	/*background-image: url(../images/audiologyform/other/line_<%=cell_width%>.png);*/
	/*background-image: url(../images/audiologyform/other/line_cross_1st_col.png);  */
	background-image: url(../images/audiologyform/other/line_cross.png);
	background-repeat:repeat;
	background-position: center;
	white-space: nowrap;
	overflow: hidden;
}
.div_cross_1st_col:HOVER
{
	/*background-image: url(../images/audiologyform/other/line_cross_1st_col_hover.png); */
	background-image: url(../images/audiologyform/other/line_cross_hover.png);
	background-repeat:repeat;
	background-position: center;
	cursor: pointer;
}
.table_right_ear div.div_cross_1st_col
{
	width: <%=cell_width%>px; 
	height: <%=cell_height_str%>;
	float: left;
	background-image: url(../images/audiologyform/other/line_cross.png);  
	background-repeat:repeat;
	background-position: center;
}
.table_right_ear div.div_cross_1st_col:HOVER
{
	width: <%=cell_width%>px; 
	height: <%=cell_height_str%>;
	float: left;
	background-image: url(../images/audiologyform/other/line_cross_hover.png);  
	background-repeat:repeat;
	background-position: center;
}
/* ---------------- css for 1st col : end  ------------------ */

/* ---------------- css for middle cols (other than first/last) : start  ------------------ */
.div_cross_middle
{
	width: <%=(cell_width)%>px; 
	height: <%=cell_height_str%>;
	float: left;
	/*background-image: url(../images/audiologyform/other/line_<%=cell_width%>.png);*/
	background-image: url(../images/audiologyform/other/line_cross.png); 
	background-repeat:repeat;
	background-position: center;
	white-space: nowrap;
	overflow: hidden;	
}
.div_cross_middle:HOVER
{
	background-image: url(../images/audiologyform/other/line_cross_hover.png); 
	background-repeat:repeat;
	background-position: center;
	cursor: pointer;
}
.div_cross_middle>img
{
	/*padding-top: 10px;*/
	margin-top: 4px;
	padding-right: 1px;
}

/* ---------------- css for middle cols (other than first/last) : end  ------------------ */
#span_current_cell_val>img
{
	 margin-left: 5px;
}
#span_current_cell_val>img:HOVER
{
	 cursor: pointer;
}
.div2_1st_row_middle_table
{
	width: <%=(cell_width)%>px; 
	height: <%=cell_height_str%>;
	float: left;
}

.first_cell , .last_cell
{
	padding-top: 6px !important;
}

div.vertical-line{
  width: 1px;
  background-color: black;
  height: <%=(cell_height/3)+1%>px;
}
/*div.vertical-line:HOVER{
	width: 2px;
}*/
div.vertical-line_1{
	height: 15px;
	vertical-align: top;
}
div.vertical-line_3{
	height: 15px;
	vertical-align: top;
}
div.vertical-line_2{
  width: 1px;
  background-color: black;
  height: 12px;
}

/*#select_cell_value>option
{
	background-repeat: no-repeat;
	background-position: center;
}
#select_cell_value>option:HOVER {
	background-color: red;
}*/

.table_terms
{
	margin-top: 50px;
	font-size: 12px !important;
	border: 1px solid black;
	border-collapse: collapse;
}


.background {
	left: 0;
	top: 0;
	position:absolute;
	z-index:0;
}

.img_highlight
{
	background-color: #FFFF00;
    border: 1px solid #999897;
}

/*.tr_highlight
{
	background-color: #CCCCCC;
}*/

.td_border_left
{
	border-left: 1px solid #858687 !important;
}
.td_border_right
{
	border-right: 1px solid #858687 !important;
}
.td_border_bottom
{
	border-bottom: 1px solid #858687 !important;
}


/*#select_cell_value {
	padding: 0 !important;
}*/
.right_ear_symbols li , .left_ear_symbols li{
    background-repeat: no-repeat;
    background-position: center;
    float: left;
    list-style: none outside none;
    padding: 7px;
    margin: 2px;
    border: 1px solid #DBD9D9;
    width: 7px;
}
.right_ear_symbols li:HOVER , .left_ear_symbols li:HOVER{
	background-position: 3px 3px;
	cursor: pointer;
}

ul.right_ear_symbols , ul.left_ear_symbols {
	padding: 0 !important;
}

/*.right_ear_symbols li, .left_ear_symbols li
{
	background-repeat: no-repeat;
	background-position: left;	
}*/

</style>

</head>

<%
	String formClass = "AudiologyReport";
	String formLink = "formAudiologyReport.jsp";

	boolean readOnly = false;
	int demoNo = Integer.parseInt(request
			.getParameter("demographic_no"));
	int formId = Integer.parseInt(request.getParameter("formId"));
	String provNo = (String) session.getAttribute("user");
	String remoteFacilityIdString = request
			.getParameter("remoteFacilityId");
	String fromSession = request.getParameter("fromSession");
	java.util.Properties props = null;

	FrmRecord rec = (new FrmRecordFactory()).factory(formClass);
	props = rec.getFormRecord(demoNo, formId);
	FrmRecordHelp.convertBooleanToChecked(props);

	String patientName = props.getProperty("patientName", " , ");

	String[] patientDOB = props.getProperty("birthDate", " / / ")
			.split("/");
	request.removeAttribute("submit");
%>

<script>
	function onSave() {
		document.forms[0].target = "_self";
		document.forms[0].submit.value = "save";
		return true;
	}

	function onSaveExit() {
		document.forms[0].target = "_self";
		document.forms[0].submit.value = "exit";
		var ret = checkAllDates();
		if (ret == true) {
			ret = confirm("Are you sure you wish to save and close this window?");
		}
		return ret;
	}
	
	function onClickPrint()
	{
		//convert left/right ear tables to canvas
		
		jQuery( "#dialog_printing_wait" ).dialog( "open" );
		
		setTimeout(convertHtmlToCanvas, 300);
		//convertHtmlToCanvas();
		
		//revert canvas change
		//removeCanvasAfterPrint();
	}
	
	function convertHtmlToCanvas()
	{
		html2canvas(jQuery(".td_table_left_ear")[0], 
		{	
			onrendered: function(canvas) 
			{
				jQuery(".td_table_left_ear>div").hide();
                jQuery(".td_table_left_ear").append(canvas);
				//jQuery(".td_table_left_ear").append("<img src='"+canvas.toDataURL("image/png")+"'></img>");
				
                html2canvas(jQuery(".td_middle_table")[0], 
           		{	
           			onrendered: function(canvas) 
           			{
           				jQuery(".td_middle_table>table").hide();
                        jQuery(".td_middle_table").append(canvas);
                        
                        html2canvas(jQuery(".td_table_right_ear")[0], 
                   		{	
                   			onrendered: function(canvas) 
                   			{
                   				jQuery(".td_table_right_ear>div").hide();
                                jQuery(".td_table_right_ear").append(canvas);
                                
                                jQuery(".no_print").hide();
                                jQuery( "#dialog_printing_wait" ).dialog( "close" );
                                
                        		window.print();
                        		jQuery(".no_print").show();
                        		
                        		removeCanvasAfterPrint();
                   			}
                   		});
           			}
           		});
			}
		});
	}
	
	function removeCanvasAfterPrint()
	{
		jQuery(".td_table_left_ear>canvas").remove();
		jQuery(".td_table_left_ear>div").show();

		jQuery(".td_middle_table>canvas").remove();
		jQuery(".td_middle_table>table").show();

		jQuery(".td_table_right_ear>canvas").remove();
		jQuery(".td_table_right_ear>div").show();
	}
</script>

<script>
var connect_symbols = false;

function onclick_div(x, y, divObj)
{
	if(connect_symbols)
		return;
	
	//show/hide symbols according to the left/right table
	var startIndex = 0;
	var endIndex = 9;
	var selectedOptionIndex = 9;
	var divId = jQuery(divObj).attr("id");	
	
	var txtId = divId.replace("div_", "");
	//alert("txtId = "+txtId);
	
	if(txtId.indexOf("r")==0)
	{
		/*startIndex = 9;
		endIndex = 18;
		selectedOptionIndex = 0;*/
		jQuery(".right_ear_symbols").show();
		jQuery(".left_ear_symbols").hide();
	}
	else
	{
		jQuery(".right_ear_symbols").hide();
		jQuery(".left_ear_symbols").show();
	}
	
	/*jQuery("#select_cell_value").find("option").show();
	jQuery("option:selected", jQuery("#select_cell_value")).removeAttr("selected");
	
	var options_arr = jQuery("#select_cell_value").find("option");
	var i = -1;
	for(i=startIndex;i<endIndex;i++)
	{
	   jQuery(jQuery(options_arr[i]).hide());
	}
	//set 1st option's background image as a background image for the combo box
	jQuery(options_arr[selectedOptionIndex]).attr("selected", "selected");
	//jQuery("#select_cell_value").css("background-image", jQuery(options_arr[selectedOptionIndex]).css("background-image"));
	jQuery("#select_cell_value").change();*/
	
	jQuery( "#span_cell_id" ).html(x+"/"+y);
	jQuery( "#dialog_cell_value" ).dialog( "open" );
	selected_x = x;
	selected_y = y;
	selected_txt_id = txtId;
	selected_cell_obj = divObj;
	
	//set selected images in dialog box
	jQuery("#span_current_cell_val").html("");
	jQuery(selected_cell_obj).find("img").each(function(){
		var img_ele_clone = jQuery(this).clone();
		
		var tooltip_ = jQuery(img_ele_clone).attr("title")+" (click symbol to remove)";
		jQuery(img_ele_clone).attr("title", tooltip_);
		//add click handler to remove image
		jQuery(img_ele_clone).click(function(){
			jQuery(this).remove();
			recalc_td_content();
			recalc_textval();
		});
		
		jQuery("#span_current_cell_val").append(img_ele_clone);
	});	
}


// for connecting lines
jQuery(document).ready(function(){
	jQuery("#connect_line_switch").click(function(){
		if(jQuery("#connect_line_switch").attr("checked")=='checked')
		{
			connect_symbols = true;
			jQuery(".div_add_symbol").mouseover(function(){
				var bg_image = jQuery(this).css("background-image");
				jQuery(this).css("background-image", bg_image.replace("_hover", ""));
			});
			
			jQuery(".connecting_ele").mouseover(function(){
				jQuery(this).addClass("img_highlight");
			});
			jQuery(".connecting_ele").mouseout(function(){
				jQuery(this).removeClass("img_highlight");
			});
		}
		else
		{
			jQuery(".div_add_symbol").mouseover(function(){
				var bg_image = jQuery(this).css("background-image");
				jQuery(this).css("background-image", bg_image.replace(".png", "_hover.png"));
			});
			
			jQuery(".connecting_ele").unbind("mouseover");
			jQuery(".connecting_ele").unbind("mouseout");
			
			connect_symbols = false;
		}
		jQuery(".div_add_symbol").mouseout(function(){
			var bg_image = jQuery(this).css("background-image");
			jQuery(this).css("background-image", bg_image.replace("_hover", ""));
		});
	});
	/*jQuery("#table_left_ear_canvas").width(jQuery(".table_left_ear").width());
	jQuery("#table_left_ear_canvas").height(jQuery(".table_left_ear").height());
	jQuery("#table_left_ear_canvas").offset(jQuery(".table_left_ear").offset());
	
	jQuery("#table_right_ear_canvas").width(jQuery(".table_right_ear").width());
	jQuery("#table_right_ear_canvas").height(jQuery(".table_right_ear").height());
	jQuery("#table_right_ear_canvas").offset(jQuery(".table_right_ear").offset());*/
});

var last_clicked = null;
jQuery(function(){
    jQuery('body').on('click', '.connecting_ele', function(e){
   if(connect_symbols && !jQuery(this).is('.clicked'))
        {
            jQuery(this).addClass('clicked');
            jQuery(this).addClass('last_clicked');
                        
            //var last_clicked = jQuery(jQuery('.connecting_ele.last_clicked').get(0));
            if(jQuery('.clicked').length > 1){
                var ctx_id_val = jQuery(this).parents("table:first").attr("class");
                ctx_id_val = ctx_id_val+"_canvas";
                
                var connecting_ele_1_id = jQuery(this).parents("div:first").attr("id");
                connecting_ele_1_id = connecting_ele_1_id.replace("div_", "");
                
                var connecting_ele_2_id = jQuery(last_clicked).parents("div:first").attr("id");
                connecting_ele_2_id = connecting_ele_2_id.replace("div_", "");
                
                add_line(
                        jQuery(this).position().left + (jQuery(this).outerWidth(true)),
                        jQuery(this).position().top + (jQuery(this).outerHeight(true)),
                        last_clicked.position().left + (last_clicked.outerWidth(true)), 
                        last_clicked.position().top + (last_clicked.outerHeight(true)),
                        ctx_id_val, connecting_ele_1_id, connecting_ele_2_id
                        );
                jQuery('.connecting_ele').removeClass('last_clicked');
                jQuery('.connecting_ele').removeClass('clicked');
            }
            else
            	{
            		last_clicked = jQuery(this);
            	}
        }                
    });
    
    //var ctx = document.getElementById("background").getContext("2d");
    var ctx = null;
    var lines = [];
    
    function draw_loop()
    {
        //ctx.fillStyle="#fff";
        //Clear the background
        //ctx.fillRect(0,0,7000,7000);
        
        var connecting_lines = "";
        for(var i in lines)
        {
        	ctx = document.getElementById(lines[i].ctx_id).getContext("2d");
//Draw each line in the draw buffer            
            ctx.beginPath();
            ctx.lineWidth = 2;
            ctx.strokeStyle = lines[i].color;
            ctx.moveTo(lines[i].start.x, lines[i].start.y);
            ctx.lineTo(lines[i].end.x, lines[i].end.y);
            ctx.stroke();
            
            //string to be persisted in db
            connecting_lines = connecting_lines+lines[i].connecting_ele_1_id+"#"+lines[i].connecting_ele_2_id+",";
        }
        jQuery("[name=connecting_lines]").val(connecting_lines);
        
        //setTimeout(draw_loop, 25/1000);
         //console.log(lines);
    }
    //Run the draw_loop for the first time. It will call itself 25 times per second after that    
    draw_loop();
    
    //Adds a line in the drawing loop of the background canvas
    function add_line(start_x, start_y, end_x, end_y, ctx_id_val, connecting_ele_1_id, connecting_ele_2_id)
    {
    	end_x = end_x+1;
    	start_x = start_x+1;
    	
    	if(end_x>start_x)
    	{
    		//start_x = start_x + 10;
    		end_x = end_x - 12;
    	}
    	else
    	{
    		//end_x = end_x + 10;
    		start_x = start_x - 12;
    	}
    	if(end_y>start_y)
    	{
    		//start_y = start_y + 10; //incremented with considering padding top 10px
    		//start_x = start_x - 2;
    		end_y = end_y - 12;
    	}
    	else
    	{
    		//end_y = end_y + 10;
    		//end_x = end_x - 2;
    		start_y = start_y - 12;
    	}
    	
        lines.push({
            start:{
                x: start_x,
                y: start_y
            },
            end:{
                x: end_x,
                y: end_y
            },
            //'color': color?color:"#"+("000"+(Math.random()*(1<<24)|0).toString(16)).substr(-6)
            'color': 'black',
            ctx_id : ctx_id_val,
            connecting_ele_1_id : connecting_ele_1_id,
            connecting_ele_2_id : connecting_ele_2_id
        });
        draw_loop();
    }
    
    jQuery("#btn_clear_all_lines").click(function(){
    	clearCanvas("table_left_ear_canvas");
    	clearCanvas("table_right_ear_canvas");
    	
    	lines = [];
    	draw_loop();
    });
    
    function clearCanvas(canvasId)
    {
    	var canvas_obj = document.getElementById(canvasId);
    	var ctx_temp = canvas_obj.getContext("2d");
    	ctx_temp.clearRect(0, 0, canvas_obj.width, canvas_obj.height);
    }
});

<%
//String connect_lines_str = "r750v0#r2000v25,r2000v25#r8000v-10,l125v-5#l1000v25";
String connect_lines_str = props.getProperty("connecting_lines", "");
String[] arr_temp = connect_lines_str.split(",");
%>
jQuery(document).ready(function(){
<%
if(arr_temp!=null && arr_temp.length>0)
{
	%>
	connect_symbols = true;
	<%
	String[] arr_temp2 = null;
	for(int i=0;i<arr_temp.length;i++)
	{
		arr_temp2 = arr_temp[i].split("#");
		for(int j=0;j<arr_temp2.length;j++){
		%>
			jQuery("#div_<%=arr_temp2[j]%>").find("img:first").click();
		<%
		}
	}
}
%>
connect_symbols = false;
});

</script>

<%

String signatureRequestId1 = "", signatureRequestId2 = "";
String existingSignatureImageRenderingUrl1 = "", existingSignatureImageRenderingUrl2 = "";

//base url for new signature images
String baseNewSignatureImageRenderingUrl = request.getContextPath()+"/imageRenderingServlet?source="+ImageRenderingServlet.Source.signature_preview.name()+"&"+DigitalSignatureUtils.SIGNATURE_REQUEST_ID_KEY+"=";

//base url for signature images already stored/saved
String baseStoredSignatureImageRenderingUrl = request.getContextPath()+"/imageRenderingServlet?source="+ImageRenderingServlet.Source.signature_stored.name()+"&digitalSignatureId=";

//for signature of 'Otolaryngologist'
if(props.getProperty("otolaryngologist", "").trim().length()>0)
{
	signatureRequestId1 = props.getProperty("otolaryngologist", "");
	existingSignatureImageRenderingUrl1 = baseStoredSignatureImageRenderingUrl+signatureRequestId1;
}

//for signature of 'OAudiologist Reg. CASLPO'
if(props.getProperty("audiologist_reg", "").trim().length()>0)
{
	signatureRequestId2 = props.getProperty("audiologist_reg", "");
	existingSignatureImageRenderingUrl2 = baseStoredSignatureImageRenderingUrl+signatureRequestId2;
}

String newSignatureRequestId1=DigitalSignatureUtils.generateSignatureRequestId(LoggedInInfo.loggedInInfo.get().loggedInProvider.getProviderNo());

String newSignatureRequestId2=DigitalSignatureUtils.generateSignatureRequestId(LoggedInInfo.loggedInInfo.get().loggedInProvider.getProviderNo());
newSignatureRequestId2 = newSignatureRequestId2+"0";

String newSignatureImageRenderingUrl1 = baseNewSignatureImageRenderingUrl+newSignatureRequestId1;
String newSignatureImageRenderingUrl2 = baseNewSignatureImageRenderingUrl+newSignatureRequestId2;

%>

<script>
jQuery(document).ready(function(){
	
	//for Otolaryngologist signature
	if('<%=signatureRequestId1%>'!='')
	{
		//if signature is already saved.. then show the signature.. and hide the new signature fields
		jQuery("#signatureShow1").show();
		jQuery("#signatureFrame1").hide();
	}
	else
	{
		//if signature is not saved already.. then show signature creation fields.. and hide existing signature display fields
		jQuery("#signatureShow1").hide();
		jQuery("#signatureFrame1").show();
	}
	
	//for OAudiologist Reg. CASLPO signature
	if('<%=signatureRequestId2%>'!='')
	{
		//if signature is already saved.. then show the signature.. and hide the new signature fields
		jQuery("#signatureShow2").show();
		jQuery("#signatureFrame2").hide();
	}
	else
	{
		//if signature is not saved already.. then show signature creation fields.. and hide existing signature display fields
		jQuery("#signatureShow2").hide();
		jQuery("#signatureFrame2").show();
	}
});

var counter=0;
function afterSaveSignature(requestIdKey)
{
	//set hidden value as a signature id to be stored in db
	var existingVal = document.getElementById('otolaryngologist').value;
	if((!existingVal || existingVal==null || existingVal=='') && ('<%=newSignatureRequestId1%>'==requestIdKey))
	{
		document.getElementById('otolaryngologist').value='<%=newSignatureRequestId1%>';
		
		//refresh image after save
		refreshSignatureImage1();
	}
	
	existingVal = document.getElementById('audiologist_reg').value;
	if((!existingVal || existingVal==null || existingVal=='') && ('<%=newSignatureRequestId2%>'==requestIdKey))
	{
		document.getElementById('audiologist_reg').value='<%=newSignatureRequestId2%>';
		
		//refresh image after save
		refreshSignatureImage2();
	}
}

function refreshSignatureImage1()
{
	counter=counter+1;
	document.getElementById('signatureImgTag1').src='<%=newSignatureImageRenderingUrl1%>&rand='+counter;
}
function refreshSignatureImage2()
{
	counter=counter+1;
	document.getElementById('signatureImgTag2').src='<%=newSignatureImageRenderingUrl2%>&rand='+counter;
}

//handler will be invoked on save of signature.. 
//common handler for both signature
function signatureHandler(e) {
	isSignatureDirty = e.isDirty;
	isSignatureSaved = e.isSave;
	
	var requestIdKey = e.requestIdKey;
	
	if (e.isSave) {
		//alert("in signatureHandler save");
		
		afterSaveSignature(requestIdKey);
		document.getElementById('newSignature').value = "true";
	}
	else {
		document.getElementById('newSignature').value = "false";
	}
}
</script>

<%!
String getImageEle(String val)
{
	String tooltip = "";
	if(val.equalsIgnoreCase("r1"))
		tooltip = "Rt - air unmasked";
	else if(val.equalsIgnoreCase("r2"))
		tooltip = "Rt - air masked";
	else if(val.equalsIgnoreCase("r3"))
		tooltip = "Rt - bone unmasked";
	else if(val.equalsIgnoreCase("r4"))
		tooltip = "Rt - bone masked";
	else if(val.equalsIgnoreCase("r5"))
		tooltip = "Rt - no response";
	else if(val.equalsIgnoreCase("r6"))
		tooltip = "Rt - UCL";
	else if(val.equalsIgnoreCase("r7"))
		tooltip = "Rt - No Rresponse";
	else if(val.equalsIgnoreCase("r8"))
		tooltip = "Rt - Free Field";
	else if(val.equalsIgnoreCase("r9"))
		tooltip = "Rt - Most Comfortable Listening";
	
	else if(val.equalsIgnoreCase("l1"))
		tooltip = "Lt - air unmasked";
	else if(val.equalsIgnoreCase("l2"))
		tooltip = "Lt - air masked";
	else if(val.equalsIgnoreCase("l3"))
		tooltip = "Lt - bone unmasked";
	else if(val.equalsIgnoreCase("l4"))
		tooltip = "Lt - bone masked";
	else if(val.equalsIgnoreCase("l5"))
		tooltip = "Lt - no response";
	else if(val.equalsIgnoreCase("l6"))
		tooltip = "Lt - UCL";
	else if(val.equalsIgnoreCase("l7"))
		tooltip = "Lt - No Rresponse";
	else if(val.equalsIgnoreCase("l8"))
		tooltip = "Lt - Free Field";
	else if(val.equalsIgnoreCase("l9"))
		tooltip = "Lt - Most Comfortable Listening";
	
	//tooltip = tooltip+" (click to remove)";
	
	String img_ele = "<img title='"+tooltip+"' onclick='removeImgEle(this);' class='connecting_ele' image_val='"+val+"' src='../images/audiologyform/"+val+".png'/>";
	return img_ele;
}
%>

<body>
<div id="dialog_printing_wait" align="center">
	<span>Printing form... Please Wait</span>
</div>

<div id="dialog_cell_value" title="X= , Y=">
	<div>Current Value (<span id="span_cell_id"></span>) : <span id="span_current_cell_val" style="font-weight: bold;"></span>
	</div>
	<div style="margin-top: 10px;">
		<!-- <select id="select_cell_value" style="width: 45px; background-image: url('../images/audiologyform/r1.png'); ">
			<option value="r1" style="background-image: url('../images/audiologyform/r1.png');"></option>
			<option value="r1" style="background: url('../images/audiologyform/r1.png') no-repeat;"></option>
			<option value="r2" style="background-image: url('../images/audiologyform/r2.png');"></option>
			<option value="r3" style="background-image: url('../images/audiologyform/r3.png');"></option>
			<option value="r4" style="background-image: url('../images/audiologyform/r4.png');"></option>
			<option value="r5" style="background-image: url('../images/audiologyform/r5.png');"></option>
			<option value="r6" style="background-image: url('../images/audiologyform/r6.png');"></option>
			<option value="r7" style="background-image: url('../images/audiologyform/r7.png');"></option>
			<option value="r8" style="background-image: url('../images/audiologyform/r8.png');"></option>
			<option value="r9" style="background-image: url('../images/audiologyform/r9.png');"></option>
			
			<option value="l1" style="background-image: url('../images/audiologyform/l1.png');"></option>
			<option value="l2" style="background-image: url('../images/audiologyform/l2.png');"></option>
			<option value="l3" style="background-image: url('../images/audiologyform/l3.png');"></option>
			<option value="l4" style="background-image: url('../images/audiologyform/l4.png');"></option>
			<option value="l5" style="background-image: url('../images/audiologyform/l5.png');"></option>
			<option value="l6" style="background-image: url('../images/audiologyform/l6.png');"></option>
			<option value="l7" style="background-image: url('../images/audiologyform/l7.png');"></option>
			<option value="l8" style="background-image: url('../images/audiologyform/l8.png');"></option>
			<option value="l9" style="background-image: url('../images/audiologyform/l9.png');"></option>
		</select> -->
		<ul class="right_ear_symbols">
			<li value="r1" style="background-image: url('../images/audiologyform/r1.png');" onclick="addCellValue('r1');"></li>
			<li value="r2" style="background-image: url('../images/audiologyform/r2.png');" onclick="addCellValue('r2');"></li>
			<li value="r3" style="background-image: url('../images/audiologyform/r3.png');" onclick="addCellValue('r3');"></li>
			<li value="r4" style="background-image: url('../images/audiologyform/r4.png');" onclick="addCellValue('r4');"></li>
			<li value="r5" style="background-image: url('../images/audiologyform/r5.png');" onclick="addCellValue('r5');"></li>
			<li value="r6" style="background-image: url('../images/audiologyform/r6.png');" onclick="addCellValue('r6');"></li>
			<li value="r7" style="background-image: url('../images/audiologyform/r7.png');" onclick="addCellValue('r7');"></li>
			<li value="r8" style="background-image: url('../images/audiologyform/r8.png');" onclick="addCellValue('r8');"></li>
			<li value="r9" style="background-image: url('../images/audiologyform/r9.png');" onclick="addCellValue('r9');"></li>
		</ul>
		<ul class="left_ear_symbols">
			<li value="l1" style="background-image: url('../images/audiologyform/l1.png');" onclick="addCellValue('l1');"></li>
			<li value="l2" style="background-image: url('../images/audiologyform/l2.png');" onclick="addCellValue('l2');"></li>
			<li value="l3" style="background-image: url('../images/audiologyform/l3.png');" onclick="addCellValue('l3');"></li>
			<li value="l4" style="background-image: url('../images/audiologyform/l4.png');" onclick="addCellValue('l4');"></li>
			<li value="l5" style="background-image: url('../images/audiologyform/l5.png');" onclick="addCellValue('l5');"></li>
			<li value="l6" style="background-image: url('../images/audiologyform/l6.png');" onclick="addCellValue('l6');"></li>
			<li value="l7" style="background-image: url('../images/audiologyform/l7.png');" onclick="addCellValue('l7');"></li>
			<li value="l8" style="background-image: url('../images/audiologyform/l8.png');" onclick="addCellValue('l8');"></li>
			<li value="l9" style="background-image: url('../images/audiologyform/l9.png');" onclick="addCellValue('l9');"></li>
		</ul>
		<br><br>
		<!-- <input type="button" value="Add Cell Value" onclick="addCellValue();"> -->
		<input type="button" value="Close" onclick="onClickCellValueDlgCancel();">		
	</div>
	
	<div style="margin-top: 15px; font-style: italic; color: red;">
	NOTE: Click on the symbol to remove it.
	</div>
</div>
	<html:form action="/form/formname">

		<input type="hidden" name="connecting_lines" value=""/> 
		<input type="hidden" name="demographic_no" value="<%=props.getProperty("demographic_no", "0")%>" />
		<input type="hidden" name="ID" value="<%=props.getProperty("ID", "0")%>" />
		<input type="hidden" name="provider_no" value=<%=request.getParameter("provNo")%> />
		<input type="hidden" name="formCreated" value="<%=props.getProperty("formCreated", "")%>" />
		<input type="hidden" name="form_class" value="<%=formClass%>" />
		<input type="hidden" name="form_link" value="<%=formLink%>" />
		<input type="hidden" name="provNo" value="<%=request.getParameter("provNo")%>" />
		<input type="hidden" name="submit" value="exit" />
		<input type="hidden" name="formId" value="<%=formId%>" />

		<input type="hidden" id="otolaryngologist" name="otolaryngologist" value="<%=signatureRequestId1%>" />
		<input type="hidden" id="audiologist_reg" name="audiologist_reg" value="<%=signatureRequestId2%>" />
		
		<input type="hidden" id="newSignature" name="newSignature" value="false" />

		<table class="Head" class="hidePrint">

			<table align="center" style="border: 1px solid #B7B4AE;">
				<tr>
					<td align="center" style="padding-bottom: 10px;">


						<table class="outertable mytable" border="0" align="center">
							<tr class="no_print">
								<td align="left"><input type="submit" value="Save" onclick="javascript:return onSave();" /> <input type="submit" value="Save and Exit"
									onclick="javascript:return onSaveExit();" /> <input type="submit" value="Exit" onclick="javascript:return onExit();" /> <input
									type="button" value="Print" onclick="return onClickPrint();" />
								</td>
							</tr>
							<tr>
								<td align="center">
									<table class="mytable" align="center">
										<tr>
											<td>
												<table class="mytable">
													<tr>
														<td height="100px" align="center"><img alt="logo" src="../images/sunny_brook_logo.png">
														</td>
													</tr>
													<tr>
														<td align="left">
															<h1>AUDIOLOGY REPORT</h1></td>
													</tr>
													<tr>
														<td style="font-size: 12px !important;font-weight: bold;">Informed consent obtained 
														<input type="checkbox" id="info_consert_obtained" name="info_consert_obtained" <%=props.getProperty("info_consert_obtained", "")%>>
														</td>
													</tr>
													<tr>
														<td>
															<table class="mytable">
																<tr>
																	<td class="label" align="left" width="170px" va>Date: (YYYY/MM/DD)</td>
																	<td align="left"><input type="text" value="<%=props.getProperty("date1", "")%>" id="date1" name="date1" class="input1">
																	&nbsp;<img src="../images/calendar.gif" id="date1_call" class="NonPrintable">  
																	</td>
																</tr>
																<tr>
																	<td class="label" align="left">Referring Physician:</td>
																	<td align="left"><input type="text" value="<%=props.getProperty("refer_phy", "")%>" id="refer_phy" name="refer_phy"
																		maxlength="100" style="width: 250px" class="input1"></td>
																</tr>
																<tr>
																	<td class="label" align="left">Reason for Referral:</td>
																	<td align="left"><input type="text" value="<%=props.getProperty("reason_for_ref", "")%>" id="reason_for_ref"
																		name="reason_for_ref" maxlength="200" style="width: 250px" class="input1">
																	</td>
																</tr>
															</table></td>
													</tr>
												</table></td>
											<td id="td_patient_identification">
												<table class="table_patient_identification">
													<tr>
														<td align="left" colspan="5"><h3>PATIENT INDENTIFICATION</h3>
														</td>
													</tr>
													<tr>
														<td class="label" align="left" style="padding-right: 7px;">Patient Name:</td>
														<td align="left"><%=props.getProperty("patientName", "")%> </td>
														<td width="25px">&nbsp;</td>
														<td class="label" align="left" style="padding-right: 7px;">Sex:</td>
														<td align="left"><%=props.getProperty("sex", "")%> </td>
													</tr>
													<tr>
														<td class="label" align="left">DOB:</td>
														<td align="left"><%=props.getProperty("birthDate", "")%> </td>
														<td>&nbsp;</td>
														<td class="label" align="left" style="padding-right: 7px;">HIN:</td>
														<td align="left"><%=props.getProperty("healthNumber", "")%> </td>
													</tr>
													<tr>
														<td class="label" align="left">Phone:</td>
														<td align="left"><%=props.getProperty("phoneNumber", "")%> </td>
														<td colspan="3">&nbsp;</td>
													</tr>
													<tr>
														<td class="label" align="left">Address:</td>
														<td colspan="4" align="left"><%=props.getProperty("patientAddress", "")%> </td>
													</tr>
													<tr>
														<td class="label" align="left">HFN:</td>
														<td colspan="4" align="left"><%=props.getProperty("chartNo", "")%> </td>
													</tr>
												</table></td>
										</tr>
									</table></td>
							</tr>

							<tr>
								<td align="center" width="100%">
									<table width="100%">
										<tr>


											<td width="60%" align="left">
												<table width="100%">
													<tr>
														<td align="left">
															<h3>Patient History</h3></td>
													</tr>
													<tr>
														<td align="left">
															<ul style="padding-left: 20px !important;" class="ul_patient_history">
																<li><span class="label">Hearing Loss :</span> <span><input type="checkbox" <%=props.getProperty("ph_hl_rt", "")%> id="ph_hl_rt" name="ph_hl_rt">Rt</span> <span><input
																		type="checkbox" <%=props.getProperty("ph_hl_lt", "")%> id="ph_hl_lt" name="ph_hl_lt">Lt</span> <span><input type="checkbox" <%=props.getProperty("ph_hl_sudden", "")%> id="ph_hl_sudden" name="ph_hl_sudden">sudden</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_hl_gradual", "")%> id="ph_hl_gradual" name="ph_hl_gradual">gradual</span> <span><input type="checkbox"
																		<%=props.getProperty("ph_hl_longstanding", "")%> id="ph_hl_longstanding" name="ph_hl_longstanding">longstanding</span> <span><input type="checkbox" <%=props.getProperty("ph_hl_no", "")%> id="ph_hl_no"
																		name="ph_hl_no">no</span></li>
																<li><span class="label">Tinnitus :</span> <span><input type="checkbox" <%=props.getProperty("ph_tinni_rt", "")%> id="ph_tinni_rt" name="ph_tinni_rt">Rt</span> <span><input
																		type="checkbox" <%=props.getProperty("ph_tinni_lt", "")%> id="ph_tinni_lt" name="ph_tinni_lt">Lt</span> <span><input type="checkbox" <%=props.getProperty("ph_tinni_occasional", "")%> id="ph_tinni_occasional"
																		name="ph_tinni_occasional">occasional</span>
																</li>
																<li><span class="label">Dizziness :</span> <span><input type="checkbox" <%=props.getProperty("ph_dizzi_yes", "")%> id="ph_dizzi_yes" name="ph_dizzi_yes">yes</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_dizzi_no", "")%> id="ph_dizzi_no" name="ph_dizzi_no">no</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_dizzi_occas_inter", "")%> id="ph_dizzi_occas_inter" name="ph_dizzi_occas_inter">Occasional/Intermittent</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_dizzi_prev_resolv", "")%> id="ph_dizzi_prev_resolv" name="ph_dizzi_prev_resolv">Previously/Resolved</span>
																</li>
																<li><span class="label">Pain :</span> <span><input type="checkbox" <%=props.getProperty("ph_pain_rt", "")%> id="ph_pain_rt" name="ph_pain_rt">Rt</span> <span><input
																		type="checkbox" <%=props.getProperty("ph_pain_lt", "")%> id="ph_pain_lt" name="ph_pain_lt">Lt</span> <span><input type="checkbox" <%=props.getProperty("ph_pain_no", "")%> id="ph_pain_no" name="ph_pain_no">no</span>
																		<span><input type="checkbox" <%=props.getProperty("ph_pain_occas_inter", "")%> id="ph_pain_occas_inter" name="ph_pain_occas_inter">Occasional/Intermittent</span>
																		<span><input type="checkbox" <%=props.getProperty("ph_pain_prev_resolv", "")%> id="ph_pain_prev_resolv" name="ph_pain_prev_resolv">Previously/Resolved</span>
																</li>
																<li><span class="label">Discharge :</span> <span><input type="checkbox" <%=props.getProperty("ph_discharge_rt", "")%> id="ph_discharge_rt" name="ph_discharge_rt">Rt</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_discharge_lt", "")%> id="ph_discharge_lt" name="ph_discharge_lt">Lt</span> <span><input type="checkbox"
																		<%=props.getProperty("ph_discharge_no", "")%> id="ph_discharge_no" name="ph_discharge_no">no</span>
																		<span><input type="checkbox" <%=props.getProperty("ph_discharge_occas_inter", "")%> id="ph_discharge_occas_inter" name="ph_discharge_occas_inter">Occasional/Intermittent</span>
																		<span><input type="checkbox" <%=props.getProperty("ph_discharge_prev_resolv", "")%> id="ph_discharge_prev_resolv" name="ph_discharge_prev_resolv">Previously/Resolved</span>
																</li>
																<li><span class="label">Fullness :</span> <span><input type="checkbox" <%=props.getProperty("ph_fullness_rt", "")%> id="ph_fullness_rt" name="ph_fullness_rt">Rt</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_fullness_lt", "")%> id="ph_fullness_lt" name="ph_fullness_lt">Lt</span> <span><input type="checkbox"
																		<%=props.getProperty("ph_fullness_no", "")%> id="ph_fullness_no" name="ph_fullness_no">no</span>
																		<span><input type="checkbox" <%=props.getProperty("ph_fullness_occas_inter", "")%> id="ph_fullness_occas_inter" name="ph_fullness_occas_inter">Occasional/Intermittent</span>
																		<span><input type="checkbox" <%=props.getProperty("ph_fullness_prev_resolv", "")%> id="ph_fullness_prev_resolv" name="ph_fullness_prev_resolv">Previously/Resolved</span>
																		</li>
																<li><span class="label">History of Noise Exposure :</span><span><input type="checkbox"
																		<%=props.getProperty("ph_hne_yes", "")%> id="ph_hne_yes" name="ph_hne_yes">yes</span>
																		<span><input type="checkbox"
																		<%=props.getProperty("ph_hne_no", "")%> id="ph_hne_no" name="ph_hne_no">no</span>
																		</li>
																<li><span class="label">ESL :</span><span><input type="checkbox"
																		<%=props.getProperty("ph_esl_yes", "")%> id="ph_esl_yes" name="ph_esl_yes">yes</span>
																		<span><input type="checkbox"
																		<%=props.getProperty("ph_esl_no", "")%> id="ph_esl_no" name="ph_esl_no">no</span>
																		</li>
															</ul></td>
													</tr>
												</table></td>
											<td width="40%">
												<table>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td>
															<ul style="padding-left: 20px !important;" class="ul_patient_history">
																<li><span class="label">Otoscopy :</span> <span>Rt</span><span><input type="checkbox" <%=props.getProperty("ph_otosc_rt_clear", "")%> id="ph_otosc_rt_clear" name="ph_otosc_rt_clear">clear</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_otosc_rt_non_occ_wax", "")%> id="ph_otosc_rt_non_occ_wax" name="ph_otosc_rt_non_occ_wax">non occluding wax</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_otosc_rt_occ_wax", "")%> id="ph_otosc_rt_occ_wax" name="ph_otosc_rt_occ_wax">occluding wax</span>
																</li>
																<li style="list-style: none !important;"><span class="label" style="visibility: hidden;">Otoscopy :</span> <span>Lt</span><span><input type="checkbox" <%=props.getProperty("ph_otosc_lt_clear", "")%> id="ph_otosc_lt_clear" name="ph_otosc_lt_clear">clear</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_otosc_lt_non_occ_wax", "")%> id="ph_otosc_lt_non_occ_wax" name="ph_otosc_lt_non_occ_wax">non occluding wax</span>
																	<span><input type="checkbox" <%=props.getProperty("ph_otosc_lt_occ_wax", "")%> id="ph_otosc_lt_occ_wax" name="ph_otosc_lt_occ_wax">occluding wax</span>
																</li>
															</ul>
														</td>
													</tr>
													<tr>
														<td align="left"><h3>Problems/needs/concerns/important to patient and family:</h3>
														</td>
													</tr>
													<tr>
														<td align="left"><textarea rows="7" cols="50" id="concerns" name="concerns" class="input1" onkeypress="return imposeMaxLength(event, this, 499);"><%=props.getProperty("concerns", "")%> </textarea>
														</td>
													</tr>
												</table>
										</tr>
									</table></td>
							</tr>

							<tr>
								<td align="left">
									<div class="label">
										Room # <input type="text" id="room_no" value="<%=props.getProperty("room_no", "")%>" name="room_no" maxlength="4" class="input1">
									</div></td>
							</tr>

							<tr>
								<td align="center" style="position: relative;">
									<div style="position: relative;">
									<table cellpadding="0" cellspacing="0" style="position: relative; z-index: 1;">
										<tr>
											<td>
												<input type="checkbox" id="connect_line_switch" />Connect symbols<br>
												<input type="button" id="btn_clear_all_lines" value="Clear All Lines">
												<br><br>
												
												<table class="table_legend">
													<tr>
														<td class="label" colspan="3">LEGEND:</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
														<td>Rt</td>
														<td>Lt</td>
													</tr>
													<tr>
														<td>air unmasked</td>
														<td><img alt="r1" src="../images/audiologyform/r1.png"></td>
														<td><img alt="l1" src="../images/audiologyform/l1.png"></td>
													</tr>
													<tr>
														<td>air masked</td>
														<td><img alt="r2" src="../images/audiologyform/r2.png"></td>
														<td><img alt="l2" src="../images/audiologyform/l2.png"></td>
													</tr>
													<tr>
														<td>bone unmasked</td>
														<td><img alt="r3" src="../images/audiologyform/r3.png"></td>
														<td><img alt="l3" src="../images/audiologyform/l3.png"></td>
													</tr>
													<tr>
														<td>bone masked</td>
														<td><img alt="r4" src="../images/audiologyform/r4.png"></td>
														<td><img alt="l4" src="../images/audiologyform/l4.png"></td>
													</tr>
													<tr>
														<td>no response</td>
														<td><img alt="r5" src="../images/audiologyform/r5.png"></td>
														<td><img alt="l5" src="../images/audiologyform/l5.png"></td>
													</tr>
													<tr>
														<td>UCL</td>
														<td><img alt="r6" src="../images/audiologyform/r6.png"></td>
														<td><img alt="l6" src="../images/audiologyform/l6.png"></td>
													</tr>
													<tr>
														<td>No Rresponse</td>
														<td><img alt="r7" src="../images/audiologyform/r7.png"></td>
														<td><img alt="l7" src="../images/audiologyform/l7.png"></td>
													</tr>
													<tr>
														<td>Free Field</td>
														<td><img alt="r8" src="../images/audiologyform/r8.png"></td>
														<td><img alt="l8" src="../images/audiologyform/l8.png"></td>
													</tr>
													<tr>
														<td>Most Comfortable Listening</td>
														<td><img alt="r9" src="../images/audiologyform/r9.png"></td>
														<td><img alt="l9" src="../images/audiologyform/l9.png"></td>
													</tr>
												</table></td>

											<td width="20px;">&nbsp;</td>

											<!-- right ear -->
											<td class="td_table_left_ear">
												<div  style="position: relative;">																						
												<table class="table_left_ear" border="0" bordercolor="#D5E4E8">
													<%
													List<String> yValues = new ArrayList<String>();
													//yValues.add("0");
													yValues.add("125");yValues.add("250");yValues.add("500");yValues.add("750");yValues.add("1000");
													yValues.add("1500");yValues.add("2000");yValues.add("3000");
													yValues.add("4000");yValues.add("6000");yValues.add("8000");	
												
													List<String> xValues = new ArrayList<String>();
													for(int y=-10;y<=120;y+=5)
													{
														xValues.add(y+"");
													}
													
													int x_count = xValues.size();
													int y_count = yValues.size();
													
													String x_str = "", y_str = "";
													
													String txt_id = "";
													%>
													<tr style="height: 20px; font-size: 12px; font-weight: bold !important;"><td colspan="<%=y_count+2%>" align="center">Right Ear</td></tr>
													<%
													String tr_cls = "tr_highlight";
													for(int i=0;i<x_count;i++)
													{
														x_str = xValues.get(i);
														if(i>=2 && i<=7)
															tr_cls = "tr_highlight";
														else
															tr_cls = "";
															
													%>
														<tr class="<%=tr_cls%>">
															<%for(int j=0;j<y_count;j++)
															{																
																y_str = yValues.get(j);
																txt_id = "r"+y_str+"v"+x_str;
																
																String style_ = "";
																if(j==0)
																{
																	%>
																	<%if(i==0){ %>
																		<td valign="middle" align="center" style="font-size: 10px !important;" class="label y_axis_label" rowspan="<%=x_count%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
																	<%
																	style_ = "padding-top: 10px !important;";
																	}%>
																	<td align="center" style="<%=style_%>font-weight: bold !important; padding-right: 2px !important;"><%=x_str %></td>
																	<%
																}
																String class_ = "";
																if(j==0)
																{
																	class_ = class_+" td_border_left";
																}
																if(i==(x_count-1))
																{
																	class_ = class_+" td_border_bottom";
																}
																if(j==(y_count-1))
																{
																	class_ = class_+" td_border_right";
																}
															%>				
																<td width="<%=cell_width_str %>" height="<%=cell_height_str%>" class="<%=class_%>">					
																	<div>
																		<%if(i==0){ %>
																			<div align="center" class="vertical-line_1" style="font-weight: bold !important;"><%=y_str %></div>
																			<div title="<%=x_str %>/<%=y_str %>" class="div_cross_1st_row div_add_symbol" id="div_<%=txt_id %>" align="center" onclick="onclick_div('<%=x_str%>', '<%=y_str%>', this)">
																		<%}else if(j==0){ %>							
																			<div title="<%=x_str %>/<%=y_str %>" class="div_cross_1st_col div_add_symbol" id="div_<%=txt_id %>" align="center" onclick="onclick_div('<%=x_str%>', '<%=y_str%>', this)">
																		<%}else{ %>
																			<div title="<%=x_str %>/<%=y_str %>" class="div_cross_middle div_add_symbol" id="div_<%=txt_id %>" align="center" onclick="onclick_div('<%=x_str%>', '<%=y_str%>', this)">
																		<%} %>
																		<input type="text" style="visibility: hidden; width: 1px; float: left;" id="<%=txt_id %>" value="<%=props.getProperty(txt_id, "")%>" name="<%=txt_id %>">
																		<%
																		//generate image elements
																		if(props.getProperty(txt_id, "").length()>0)
																		{
																			String[] arr = props.getProperty(txt_id, "").split(",");
																			for(int x=0;x<arr.length;x++)
																			{
																				out.println(getImageEle(arr[x]));
																			}				
																		}
																		%>						
																		</div>					
																	</div>
																</td>
															<%} %>
														</tr>
													<%} %>
													<tr style="height: 17px;"><td align="center" class="label x_axis_label" colspan="<%=y_count+2%>"></td></tr>
												</table>
												<canvas width="390px" height="539px" id="table_left_ear_canvas" class="background"></canvas>												
												</div>												
											</td>
											
											<td width="60px" class="td_middle_table">
						<table class="middle_table" border="0" bordercolor="#D5E4E8">		
						<%
						List<String> linesTobeDrawnList = new ArrayList<String>();
						linesTobeDrawnList.add("0");linesTobeDrawnList.add("25");
						linesTobeDrawnList.add("40");linesTobeDrawnList.add("55");
						linesTobeDrawnList.add("70");linesTobeDrawnList.add("90");linesTobeDrawnList.add("120");
						
						Map<String, String> rangeLabelMap = new HashMap<String, String>();
						rangeLabelMap.put("10", "Normal");
						rangeLabelMap.put("30", "Mild");
						rangeLabelMap.put("45", "Moderate");
						rangeLabelMap.put("60", "Moderately");
						rangeLabelMap.put("65", "Severe");
						rangeLabelMap.put("80", "Severe");
						rangeLabelMap.put("105", "Profound");
						%>
						
						<tr style="height: 20px; font-size: 12px;"><td align="center">&nbsp;</td></tr>
						
						<%for(int i=0;i<x_count;i++)
						{
							x_str = xValues.get(i);
							boolean drawLine = false;
							if(linesTobeDrawnList.contains(x_str))
							{
								drawLine = true;
							}
						%>
							<tr>
								<td height="<%=cell_height_str%>" align="center">
									<%if(i==0){ %>
										<div class="vertical-line_1">&nbsp;</div>
										<div class="div2_1st_row_middle_table">&nbsp;</div>
									<%} %>
									<%if(drawLine){ %>
										<div class="div_line" style="width: 100% !important;"></div>
									<%}else{ %>
										<%if(rangeLabelMap.get(x_str)!=null){ %>
											<span class="range_label"><%= rangeLabelMap.get(x_str)%></span>
										<%} %>
									<%} %>
								</td>				
							</tr>
						<%} %>
						<tr style="height: 17px;"><td>&nbsp;</td></tr>
						</table>
											</td>

											<!-- left ear -->
											<td class="td_table_right_ear">
												<div  style="position: relative;">
												<table class="table_right_ear" border="0" bordercolor="#D5E4E8">
													<%
													yValues = new ArrayList<String>();
													//yValues.add("0");
													yValues.add("125");yValues.add("250");yValues.add("500");yValues.add("750");yValues.add("1000");
													yValues.add("1500");yValues.add("2000");yValues.add("3000");
													yValues.add("4000");yValues.add("6000");yValues.add("8000");	
												
													xValues = new ArrayList<String>();
													for(int y=-10;y<=120;y+=5)
													{
														xValues.add(y+"");
													}
													
													x_count = xValues.size();
													y_count = yValues.size();
													
													x_str = "";
													y_str = "";
													txt_id = "";
													
													%>
													<tr style="height: 20px; font-size: 12px;"><td colspan="<%=y_count+1%>" align="center" style="font-weight: bold;">Left Ear</td></tr>
													<%
													
													for(int i=0;i<x_count;i++)
													{
														x_str = xValues.get(i);		
													%>
														<tr>
															<%for(int j=0;j<y_count+1;j++)
															{
																String style_ = "";
																if(j==y_count)
																{
																	if(i==0)
																		style_ = "padding-top: 10px !important;";																		
																	%>
																	<td align="center" style="font-weight: bold !important; padding-left: 2px !important;<%=style_%>"><!-- <div class="last_cell" > --><%=x_str %><!-- </div> --></td>
																	<%if(i==0){ %>
																		<td valign="middle" align="center" style="font-size: 10px !important;" width="13px" class="label y_axis_label" rowspan="<%=x_count%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
																	<%} %>							
																	<%
																	continue;
																}
																
																String class_ = "";
																if(j==0)
																{
																	class_ = class_+" td_border_left";
																}
																if(i==(x_count-1))
																{
																	class_ = class_+" td_border_bottom";
																}
																if(j==(y_count-1))
																{
																	class_ = class_+" td_border_right";
																}
																
																y_str = yValues.get(j);
																txt_id = "l"+y_str+"v"+x_str;
															%>
																<td width="<%=cell_width_str %>" height="<%=cell_height_str%>" class="<%=class_%>">
																	<div>
																		<%if(i==0){ %>
																			<div align="center" class="vertical-line_1" style="font-weight: bold !important;"><%=y_str %></div>
																			<div title="<%=x_str %>/<%=y_str %>" class="div_cross_1st_row div_add_symbol" id="div_<%=txt_id %>" align="center" onclick="onclick_div('<%=x_str%>', '<%=y_str%>', this)">
																		<%}else if(j==(y_count-1)){%>
																			<div title="<%=x_str %>/<%=y_str %>" class="div_cross_1st_col div_add_symbol" id="div_<%=txt_id %>" align="center" onclick="onclick_div('<%=x_str%>', '<%=y_str%>', this)">
																		<%}else{ %>
																			<div title="<%=x_str %>/<%=y_str %>" class="div_cross_middle div_add_symbol" id="div_<%=txt_id %>" align="center" onclick="onclick_div('<%=x_str%>', '<%=y_str%>', this)">
																		<%} %>
																		<input type="text" style="visibility: hidden; width: 1px; float: left;" id="<%=txt_id %>" value="<%=props.getProperty(txt_id, "")%>" name="<%=txt_id %>">
																		<%
																		//generate image elements
																		if(props.getProperty(txt_id, "").length()>0)
																		{
																			String[] arr = props.getProperty(txt_id, "").split(",");
																			for(int x=0;x<arr.length;x++)
																			{
																				out.println(getImageEle(arr[x]));
																			}				
																		}
																		%>
																		</div>
																	</div>
																</td>
															<%} %>
														</tr>
													<%} %>
													<tr style="height: 17px;"><td align="center" class="label x_axis_label" colspan="<%=y_count+2%>"></td></tr>
												</table>
												<canvas id="table_right_ear_canvas" width="389px" height="539px" class="background"></canvas>
												</div>
											</td>

											<td width="20px;">&nbsp;</td>

											<td>
												<table>
													<tr>
														<td class="label" align="left">Test Done Using:</td>
													</tr>
													<tr>
														<td align="left"><input type="checkbox" <%=props.getProperty("test_done_insert_phones", "")%> id="test_done_insert_phones" name="test_done_insert_phones">Insert Phones</td>
													</tr>
													<tr>
														<td align="left"><input type="checkbox" <%=props.getProperty("test_done_head_phones", "")%> id="test_done_head_phones" name="test_done_head_phones">Headphones</td>
													</tr>
													<tr>
														<td class="label" align="left">
															<input type="text" id="test_done_text" name="test_done_text" maxlength="49" value="<%=props.getProperty("test_done_text", "")%>">
														</td>
													</tr>
												</table> <br>
												<table>
													<tr>
														<td class="label" align="left">Reliability:</td>
													</tr>
													<tr>
														<td align="left"><input type="checkbox" <%=props.getProperty("reliability_good", "")%> id="reliability_good" name="reliability_good">Good</td>
													</tr>
													<tr>
														<td align="left"><input type="checkbox" <%=props.getProperty("reliability_fair", "")%> id="reliability_fair" name="reliability_fair">Fair</td>
													</tr>
													<tr>
														<td align="left"><input type="checkbox" <%=props.getProperty("reliability_poor", "")%> id="reliability_poor" name="reliability_poor">Poor</td>
													</tr>
												</table></td>
										</tr>
									</table>
																											
									</div>									
									</td>
							</tr>
							
							<!-- speech recognition -->
							<tr>
								<td align="center">
									<table>
										<tr>
											<td>
												<table border="1" class="table_speech_rec_1">
													<tr>
														<td class="label" align="center">SRT</td>
														<td colspan="5" class="label" align="center">SPEECH RECOGNITION</td>
													</tr>
													<tr>
														<td class="label">&nbsp;</td>
														<td class="label" width="65px" align="center">Condition</td>
														<td class="label" width="50px" align="center">Score</td>
														<td class="label" width="52px" align="center">Level</td>
														<td class="label" width="60px" align="center">Masking</td>
														<td class="label" width="65px" align="center">List</td>
													</tr>
													<tr>
														<td class="label" style="border: none !important;" align="right"><input type="text" id="speech_rec_1_srt1" value="<%=props.getProperty("speech_rec_1_srt1", "")%>" name="speech_rec_1_srt1" maxlength="3" style="width: 32px !important;">dB</td>
														<td><input type="text" id="speech_rec_1_cond1" value="<%=props.getProperty("speech_rec_1_cond1", "")%>" name="speech_rec_1_cond1" maxlength="4" style="width: 65px !important;">
														</td>
														<td align="left"><input type="text" id="speech_rec_1_score1" value="<%=props.getProperty("speech_rec_1_score1", "")%>" value="<%=props.getProperty("speech_rec_1_score1", "")%>" name="speech_rec_1_score1" maxlength="3">%</td>
														<td><input type="text" id="speech_rec_1_level1" value="<%=props.getProperty("speech_rec_1_level1", "")%>" name="speech_rec_1_level1" maxlength="3">dB</td>
														<td><input type="text" id="speech_rec_1_mask1" value="<%=props.getProperty("speech_rec_1_mask1", "")%>" name="speech_rec_1_mask1" maxlength="3" style="width: 42px !important;">dB</td>
														<td><input type="text" id="speech_rec_1_list1" value="<%=props.getProperty("speech_rec_1_list1", "")%>" name="speech_rec_1_list1" maxlength="10" style="width: 65px !important;">
														</td>
													</tr>
													<tr>
														<td class="label" style="border: none !important;" align="right">MASKING</td>
														<td><input type="text" id="speech_rec_1_cond2" value="<%=props.getProperty("speech_rec_1_cond2", "")%>" name="speech_rec_1_cond2" maxlength="4" style="width: 65px !important;">
														</td>
														<td><input type="text" id="speech_rec_1_score2" value="<%=props.getProperty("speech_rec_1_score2", "")%>" name="speech_rec_1_score2" maxlength="3">%</td>
														<td><input type="text" id="speech_rec_1_level2" value="<%=props.getProperty("speech_rec_1_level2", "")%>" name="speech_rec_1_level2" maxlength="3">dB</td>
														<td><input type="text" id="speech_rec_1_mask2" value="<%=props.getProperty("speech_rec_1_mask2", "")%>" name="speech_rec_1_mask2" maxlength="3" style="width: 42px !important;">dB</td>
														<td><input type="text" id="speech_rec_1_list2" value="<%=props.getProperty("speech_rec_1_list2", "")%>" name="speech_rec_1_list2" maxlength="10" style="width: 65px !important;">
														</td>
													</tr>
													<tr>
														<td class="label" style="border: none !important;" align="right"><input type="text" id="speech_rec_1_srt2" value="<%=props.getProperty("speech_rec_1_srt2", "")%>" name="speech_rec_1_srt2" maxlength="3" style="width: 32px !important;">dB</td>
														<td colspan="3">MCL&nbsp;&nbsp;&nbsp;<input type="text" id="speech_rec_1_mcl" value="<%=props.getProperty("speech_rec_1_mcl", "")%>" name="speech_rec_1_mcl" maxlength="4"
															style="width: 100px !important;">dB</td>
														<td colspan="2">UCL&nbsp;&nbsp;&nbsp;<input type="text" id="speech_rec_1_ucl" value="<%=props.getProperty("speech_rec_1_ucl", "")%>" name="speech_rec_1_ucl" maxlength="4"
															style="width: 60px !important;">dB</td>
													</tr>
												</table></td>
											<td width="25px">&nbsp;</td>
											<td>
												<table border="1" class="table_speech_rec_1">
													<tr>
														<td class="label" align="center">SRT</td>
														<td colspan="5" class="label" align="center">SPEECH RECOGNITION</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
														<td class="label" width="65px" align="center">Condition</td>
														<td class="label" width="50px" align="center">Score</td>
														<td class="label" width="52px" align="center">Level</td>
														<td class="label" width="60px" align="center">Masking</td>
														<td class="label" width="65px" align="center">List</td>
													</tr>
													<tr>
														<td class="label" style="border: none !important;" align="right"><input type="text" id="speech_rec_2_srt1" value="<%=props.getProperty("speech_rec_2_srt1", "")%>" name="speech_rec_2_srt1" maxlength="3" style="width: 32px !important;">dB</td>
														<td><input type="text" id="speech_rec_2_cond1" value="<%=props.getProperty("speech_rec_2_cond1", "")%>" name="speech_rec_2_cond1" maxlength="4" style="width: 65px !important;">
														</td>
														<td><input type="text" id="speech_rec_2_score1" value="<%=props.getProperty("speech_rec_2_score1", "")%>" name="speech_rec_2_score1" maxlength="3">%</td>
														<td><input type="text" id="speech_rec_2_level1" value="<%=props.getProperty("speech_rec_2_level1", "")%>" name="speech_rec_2_level1" maxlength="3">dB</td>
														<td><input type="text" id="speech_rec_2_mask1" value="<%=props.getProperty("speech_rec_2_mask1", "")%>" name="speech_rec_2_mask1" maxlength="3" style="width: 42px !important;">dB</td>
														<td><input type="text" id="speech_rec_2_list1" value="<%=props.getProperty("speech_rec_2_list1", "")%>" name="speech_rec_2_list1" maxlength="10" style="width: 65px !important;">
														</td>
													</tr>
													<tr>
														<td class="label" style="border: none !important;" align="right">MASKING</td>
														<td><input type="text" id="speech_rec_2_cond2" value="<%=props.getProperty("speech_rec_2_cond2", "")%>" name="speech_rec_2_cond2" maxlength="4" style="width: 65px !important;">
														</td>
														<td><input type="text" id="speech_rec_2_score2" value="<%=props.getProperty("speech_rec_2_score2", "")%>" name="speech_rec_2_score2" maxlength="3">%</td>
														<td><input type="text" id="speech_rec_2_level2" value="<%=props.getProperty("speech_rec_2_level2", "")%>" name="speech_rec_2_level2" maxlength="3">dB</td>
														<td><input type="text" id="speech_rec_2_mask2" value="<%=props.getProperty("speech_rec_2_mask2", "")%>" name="speech_rec_2_mask2" maxlength="3" style="width: 42px !important;">dB</td>
														<td><input type="text" id="speech_rec_2_list2" value="<%=props.getProperty("speech_rec_2_list2", "")%>" name="speech_rec_2_list2" maxlength="10" style="width: 65px !important;">
														</td>
													</tr>
													<tr>
														<td class="label" style="border: none !important;" align="right"><input type="text" id="speech_rec_2_srt2" value="<%=props.getProperty("speech_rec_2_srt2", "")%>" name="speech_rec_2_srt2" maxlength="3" style="width: 32px !important;">dB</td>
														<td colspan="3">MCL&nbsp;&nbsp;&nbsp;<input type="text" id="speech_rec_2_mcl" value="<%=props.getProperty("speech_rec_2_mcl", "")%>" name="speech_rec_2_mcl" maxlength="4"
															style="width: 100px !important;">dB</td>
														<td colspan="2">UCL&nbsp;&nbsp;&nbsp;<input type="text" id="speech_rec_2_ucl" value="<%=props.getProperty("speech_rec_2_ucl", "")%>" name="speech_rec_2_ucl" maxlength="4"
															style="width: 60px !important;">dB</td>
													</tr>
												</table></td>
										</tr>
									</table></td>
							</tr>

							<!-- impendance result -->
							<tr>
								<td align="center" style="padding-top: 7px;">
									<table class="table_impendance_result">
										<tr>
											<td align="center">
												<h3>Impendace Result</h3></td>
										</tr>
										<tr>
											<td>
												<table>
													<tr>
														<!-- first table : start : could not seal -->
														<td>
															<table style="border-collapse: collapse;">
																<tr>
																	<td>Rt:&nbsp;<input type="checkbox" <%=props.getProperty("ir_rt_could_not_seal", "")%> id="ir_rt_could_not_seal" name="ir_rt_could_not_seal">&nbsp;could not seal</td>
																	<td>Lt:&nbsp;<input type="checkbox" <%=props.getProperty("ir_lt_could_not_seal", "")%> id="ir_lt_could_not_seal" name="ir_lt_could_not_seal">&nbsp;could not seal</td>
																</tr>
																<tr>
																	<td style="border: 1px solid black;">
																		<table>
																			<tr>
																				<td colspan="2" class="label">Middle Ear Pressure:</td>
																			</tr>
																			<tr>
																				<td class="label" style="padding-right: 15px;">Normal</td>
																				<td class="label">Abnormal</td>
																			</tr>
																			<tr>
																				<td>Rt:&nbsp;<input type="checkbox" <%=props.getProperty("mep_rt", "")%> id="mep_rt" name="mep_rt">
																				</td>
																				<td><input type="text" id="mep_rt_txt" value="<%=props.getProperty("mep_rt_txt", "")%>" value="<%=props.getProperty("mep_rt_txt", "")%>" name="mep_rt_txt" style="width: 45px" maxlength="4">&nbsp;mmH<sub>2</sub>O</td>
																			</tr>
																			<tr>
																				<td>Lt:&nbsp;<input type="checkbox" <%=props.getProperty("mep_lt", "")%> id="mep_lt" name="mep_lt">
																				</td>
																				<td><input type="text" id="mep_lt_txt" value="<%=props.getProperty("mep_lt_txt", "")%>" value="<%=props.getProperty("mep_lt_txt", "")%>" name="mep_lt_txt" style="width: 45px" maxlength="4">&nbsp;mmH<sub>2</sub>O</td>
																			</tr>
																		</table></td>
																	<td style="border: 1px solid black;">
																		<table>
																			<tr>
																				<td colspan="3" class="label">Static Compliance:</td>
																			</tr>
																			<tr>
																				<td class="label" style="padding-right: 15px;">Normal</td>
																				<td class="label" style="padding-right: 15px;">Low</td>
																				<td class="label">High</td>
																				<td class="label">Volume</td>
																			</tr>
																			<tr>
																				<td>Rt:&nbsp;<input type="checkbox" <%=props.getProperty("static_comp_rt", "")%> id="static_comp_rt" name="static_comp_rt">
																				</td>
																				<td><input type="text" id="static_comp_rt_txt1" value="<%=props.getProperty("static_comp_rt_txt1", "")%>" value="<%=props.getProperty("static_comp_rt_txt1", "")%>" name="static_comp_rt_txt1" style="width: 50px" maxlength="4">&nbsp;ml</td>
																				<td><input type="text" id="static_comp_rt_txt2" value="<%=props.getProperty("static_comp_rt_txt2", "")%>" name="static_comp_rt_txt2" style="width: 50px" maxlength="4">&nbsp;ml</td>
																				<td><input type="text" id="static_comp_rt_vol" value="<%=props.getProperty("static_comp_rt_vol", "")%>" name="static_comp_rt_vol" style="width: 50px" maxlength="4">&nbsp;ml</td>
																			</tr>
																			<tr>
																				<td>Lt:&nbsp;<input type="checkbox" <%=props.getProperty("static_comp_lt", "")%> id="static_comp_lt" name="static_comp_lt">
																				</td>
																				<td><input type="text" id="static_comp_lt_txt1" value="<%=props.getProperty("static_comp_lt_txt1", "")%>" name="static_comp_lt_txt1" style="width: 50px" maxlength="4">&nbsp;ml</td>
																				<td><input type="text" id="static_comp_lt_txt2" value="<%=props.getProperty("static_comp_lt_txt2", "")%>" name="static_comp_lt_txt2" style="width: 50px" maxlength="4">&nbsp;ml</td>
																				<td><input type="text" id="static_comp_lt_vol" value="<%=props.getProperty("static_comp_lt_vol", "")%>" name="static_comp_lt_vol" style="width: 50px" maxlength="4">&nbsp;ml</td>
																			</tr>
																		</table></td>
																</tr>
															</table></td>
														<!-- first table : end : could not seal -->

														<!-- second table : start : Reflect Decay Test Results-->
														<td style="padding-left: 10px; padding-right: 10px;">
															<table>
																<tr>
																	<td class="label" align="center">Reflex Decay Test Results</td>
																</tr>
																<tr>
																	<td align="left"><input type="checkbox" <%=props.getProperty("reflect_decay_dnt", "")%> id="reflect_decay_dnt" name="reflect_decay_dnt">&nbsp;DNT</td>
																</tr>
																<tr>
																	<td align="left"><input type="checkbox" <%=props.getProperty("reflect_decay_normal", "")%> id="reflect_decay_normal" name="reflect_decay_normal">&nbsp;Normal at 500 & 1000
																		Hz bilaterally</td>
																</tr>
																<tr>
																	<td align="left"><input type="checkbox" <%=props.getProperty("reflect_decay_abnormal", "")%> id="reflect_decay_abnormal" name="reflect_decay_abnormal">&nbsp;Abnormal
																		at&nbsp; <input type="text" id="reflect_decay_abnormal_txt" value="<%=props.getProperty("reflect_decay_abnormal_txt", "")%>" name="reflect_decay_abnormal_txt" style="width: 115px" maxlength="50"
																		class="input1">
																	</td>
																</tr>
															</table></td>
														<!-- second table : end : Reflect Decay Test Results-->

														<!-- third table : start : STAPEDIAL ACOUSTIC REFLEXES -->
														<td>
															<table style="border-collapse: collapse;" class="table_stapedial_acoustic_reflexes">
																<tr>
																	<td class="label" colspan="5" align="center">STAPEDIAL ACOUSTIC REFLEXES</td>
																</tr>
																<tr>
																	<td>&nbsp;</td>
																	<td class="label" colspan="2" align="center">RIGHT</td>
																	<td class="label" colspan="2" align="center">LEFT</td>
																</tr>
																<tr>
																	<td class="border1">&nbsp;</td>
																	<td class="border1" align="center"><span class="label">CONTRA</span><br>(probe left)</td>
																	<td class="border1" align="center"><span class="label">IPSI</span><br>(probe right)</td>
																	<td class="border1" align="center"><span class="label">CONTRA</span><br>(probe right)</td>
																	<td class="border1" align="center"><span class="label">IPSI</span><br>(probe left)</td>
																</tr>
																<tr>
																	<td class="border1" align="center">500 Hz</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_500_contra_left" value="<%=props.getProperty("stape_acou_500_contra_left", "")%>" name="stape_acou_500_contra_left"
																		style="width: 68px;" maxlength="7">
																	</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_500_ipsi_right" value="<%=props.getProperty("stape_acou_500_ipsi_right", "")%>" name="stape_acou_500_ipsi_right"
																		style="width: 68px;" maxlength="7">
																	</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_500_contra_right" value="<%=props.getProperty("stape_acou_500_contra_right", "")%>" name="stape_acou_500_contra_right"
																		style="width: 68px;" maxlength="7">
																	</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_500_ipsi_left" value="<%=props.getProperty("stape_acou_500_ipsi_left", "")%>" name="stape_acou_500_ipsi_left"
																		style="width: 68px;" maxlength="7">
																	</td>
																</tr>
																<tr>
																	<td class="border1" align="center">1000 Hz</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_1000_contra_left" value="<%=props.getProperty("stape_acou_1000_contra_left", "")%>" name="stape_acou_1000_contra_left"
																		style="width: 68px;" maxlength="7">
																	</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_1000_ipsi_right" value="<%=props.getProperty("stape_acou_1000_ipsi_right", "")%>" name="stape_acou_1000_ipsi_right"
																		style="width: 68px;" maxlength="7">
																	</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_1000_contra_right" value="<%=props.getProperty("stape_acou_1000_contra_right", "")%>" name="stape_acou_1000_contra_right"
																		style="width: 68px;" maxlength="7">
																	</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_1000_ipsi_left" value="<%=props.getProperty("stape_acou_1000_ipsi_left", "")%>" name="stape_acou_1000_ipsi_left"
																		style="width: 68px;" maxlength="7">
																	</td>
																</tr>
																<tr>
																	<td class="border1" align="center">2000 Hz</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_2000_contra_left" value="<%=props.getProperty("stape_acou_2000_contra_left", "")%>" name="stape_acou_2000_contra_left"
																		style="width: 68px;" maxlength="7">
																	</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_2000_ipsi_right" value="<%=props.getProperty("stape_acou_2000_ipsi_right", "")%>" name="stape_acou_2000_ipsi_right"
																		style="width: 68px;" maxlength="7">
																	</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_2000_contra_right" value="<%=props.getProperty("stape_acou_2000_contra_right", "")%>" name="stape_acou_2000_contra_right"
																		style="width: 68px;" maxlength="7">
																	</td>
																	<td class="border1" align="center"><input type="text" id="stape_acou_2000_ipsi_left" value="<%=props.getProperty("stape_acou_2000_ipsi_left", "")%>" name="stape_acou_2000_ipsi_left"
																		style="width: 68px;" maxlength="7">
																	</td>
																</tr>
															</table></td>
														<!-- third table : end : STAPEDIAL ACOUSTIC REFLEXES -->

													</tr>
												</table></td>
										</tr>
									</table></td>
							</tr>

							<tr>
								<td style="padding-top: 10px;" align="center">
									<table class="table_hearing_aid">
										<tr>
											<td class="label" width="100px" rowspan="2">If patient owns a hearing aid:</td>
											<td>Rt:&nbsp;<input type="checkbox" <%=props.getProperty("owns_hr_aid_rt", "")%> id="owns_hr_aid_rt" name="owns_hr_aid_rt">
											</td>
											<td>Model:&nbsp;<input type="text" id="owns_hr_aid_rt_model" value="<%=props.getProperty("owns_hr_aid_rt_model", "")%>" name="owns_hr_aid_rt_model" style="width: 200px" maxlength="50"
												class="input1">
											</td>
											<td>Serial Number:&nbsp;<input type="text" id="owns_hr_aid_rt_sr_no" value="<%=props.getProperty("owns_hr_aid_rt_sr_no", "")%>" name="owns_hr_aid_rt_sr_no" style="width: 125px" maxlength="50"
												class="input1">
											</td>
											<td><span class="label">Condition:</span> <span><input type="checkbox" <%=props.getProperty("owns_hr_aid_rt_cond_good", "")%> id="owns_hr_aid_rt_cond_good"
													name="owns_hr_aid_rt_cond_good">&nbsp;Good</span> <span><input type="checkbox" <%=props.getProperty("owns_hr_aid_rt_cond_repair", "")%> id="owns_hr_aid_rt_cond_repair"
													name="owns_hr_aid_rt_cond_repair">&nbsp;Repair</span> <span><input type="checkbox" <%=props.getProperty("owns_hr_aid_rt_cond_replace", "")%> id="owns_hr_aid_rt_cond_replace"
													name="owns_hr_aid_rt_cond_replace">&nbsp;Replace</span> <span><input type="checkbox" <%=props.getProperty("owns_hr_aid_rt_cond_dnt", "")%> id="owns_hr_aid_rt_cond_dnt"
													name="owns_hr_aid_rt_cond_dnt">&nbsp;DNT</span></td>
										</tr>
										<tr>
											<td>Lt:&nbsp;<input type="checkbox" <%=props.getProperty("owns_hr_aid_lt", "")%> id="owns_hr_aid_lt" name="owns_hr_aid_lt">
											</td>
											<td>Model:&nbsp;<input type="text" id="owns_hr_aid_lt_model" value="<%=props.getProperty("owns_hr_aid_lt_model", "")%>" name="owns_hr_aid_lt_model" style="width: 200px" maxlength="50"
												class="input1">
											</td>
											<td>Serial Number:&nbsp;<input type="text" id="owns_hr_aid_lt_sr_no" value="<%=props.getProperty("owns_hr_aid_lt_sr_no", "")%>" name="owns_hr_aid_lt_sr_no" style="width: 125px" maxlength="50"
												class="input1">
											</td>
											<td><span class="label">Condition:</span> <span><input type="checkbox" <%=props.getProperty("owns_hr_aid_lt_cond_good", "")%> id="owns_hr_aid_lt_cond_good"
													name="owns_hr_aid_lt_cond_good">&nbsp;Good</span> <span><input type="checkbox" <%=props.getProperty("owns_hr_aid_lt_cond_repair", "")%> id="owns_hr_aid_lt_cond_repair"
													name="owns_hr_aid_lt_cond_repair">&nbsp;Repair</span> <span><input type="checkbox" <%=props.getProperty("owns_hr_aid_lt_cond_replace", "")%> id="owns_hr_aid_lt_cond_replace"
													name="owns_hr_aid_lt_cond_replace">&nbsp;Replace</span> <span><input type="checkbox" <%=props.getProperty("owns_hr_aid_lt_cond_dnt", "")%> id="owns_hr_aid_lt_cond_dnt"
													name="owns_hr_aid_lt_cond_dnt">&nbsp;DNT</span></td>
										</tr>
									</table></td>
							</tr>

							<!-- Recommendation / Rehabilitation Plan : start -->
							<tr>
								<td style="padding-top: 10px;" align="left">
									<table width="100%;">
										<tr>
											<td colspan="3" align="left"><h3>Recommendation / Rehabilitation Plan</h3>
											</td>
										</tr>
										<tr>
											<td style="padding-right: 15px;" align="left">
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_periodic_reeval", "")%> id="rr_plan_periodic_reeval" name="rr_plan_periodic_reeval">&nbsp;Periodic re-evaluation should concerns
													arise
												</div>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_med_mngmnt", "")%> id="rr_plan_med_mngmnt" name="rr_plan_med_mngmnt">&nbsp;Medical management at physician's discretion
												</div>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_ref_ent", "")%> id="rr_plan_ref_ent" name="rr_plan_ref_ent">&nbsp;Referral to ENT at physician's discretion
												</div>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_abr", "")%> id="rr_plan_abr" name="rr_plan_abr">&nbsp;ABR if clinically indicated
												</div>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_microdebridement", "")%> name="rr_plan_microdebridement" id="rr_plan_microdebridement">&nbsp;Return to physician for microdebridement
												</div></td>
											<td style="padding-right: 15px;" align="left">
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_amplification_disc", "")%> id="rr_plan_amplification_disc" name="rr_plan_amplification_disc">&nbsp;Amplification discussed
												</div>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_not_interest_aid", "")%> id="rr_plan_not_interest_aid" name="rr_plan_not_interest_aid">&nbsp;Patient not interested in hearing aid
													now
												</div>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_hear_aid_pack_given", "")%> id="rr_plan_hear_aid_pack_given" name="rr_plan_hear_aid_pack_given">&nbsp;Hearing aid information package
													given
												</div>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_ret_hear_eval", "")%> id="rr_plan_ret_hear_eval" name="rr_plan_ret_hear_eval">&nbsp;Returning for hearing aid evaluation
												</div>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_ret_check_aid", "")%> id="rr_plan_ret_check_aid" name="rr_plan_ret_check_aid">&nbsp;Returning for check aid
												</div></td>
											<td style="padding-right: 15px;" align="left">
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_comm_strat_disc", "")%> id="rr_plan_comm_strat_disc" name="rr_plan_comm_strat_disc">&nbsp;Communication strategies provided and
													discussed
												</div>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_counselled_tinnit", "")%> id="rr_plan_counselled_tinnit" name="rr_plan_counselled_tinnit">&nbsp;Counselled on tinnitus managements
												</div>
												<%-- <div>
													<input type="checkbox" <%=props.getProperty("rr_plan_brochure_workshop", "")%> id="rr_plan_brochure_workshop" name="rr_plan_brochure_workshop">&nbsp;Given brochure for ways to Better
													Hearing workshop
												</div> --%>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_brochure_tinnitus", "")%> id="rr_plan_brochure_tinnitus" name="rr_plan_brochure_tinnitus">&nbsp;Given brochure for Tinnitus workshop
												</div>
												<%-- <div>
													<input type="checkbox" <%=props.getProperty("rr_plan_no_rec", "")%> id="rr_plan_no_rec" name="rr_plan_no_rec">&nbsp;No further recommendations
												</div> --%>
												<div>
													<input type="checkbox" <%=props.getProperty("rr_plan_disc_prot", "")%> id="rr_plan_disc_prot" name="rr_plan_disc_prot">&nbsp;Discussed hearing conservation/ear protection
												</div>
												</td>
										</tr>
									</table></td>
							</tr>
							<!-- Recommendation / Rehabilitation Plan : end -->

							<tr>
								<td style="padding-top: 10px;" width="100%">
									<table width="100%">
										<tr>
											<td class="label" align="left">Comments:</td>
										</tr>
										<tr>
											<td align="left"><textarea rows="5" cols="" id="comments" name="comments" style="width: 100%;" onkeypress="return imposeMaxLength(event, this, 255);"class="input1"><%=props.getProperty("comments", "")%> </textarea></td>
										</tr>
									</table></td>
							</tr>

<tr>
	<td style="padding-top: 20px;">
		<div style="text-align: left;" class="label">
			<span style="float: left">
				<%-- <input style="border: 1px solid black;" type="text" id="otolaryngologist" name="otolaryngologist" maxlength="49" value="<%=props.getProperty("otolaryngologist", "")%>"> --%> 
				<div id="signatureShow1" style="display: none;">
					<img id="signatureImgTag1" src="<%=existingSignatureImageRenderingUrl1 %>" />
				</div>
				<iframe style="width:400px; height:132px;"id="signatureFrame1" src="<%= request.getContextPath() %>/signature_pad/tabletSignature.jsp?inWindow=true&<%=DigitalSignatureUtils.SIGNATURE_REQUEST_ID_KEY%>=<%=newSignatureRequestId1%>" ></iframe>
				<br>Otolaryngologist
			</span> 
				
			<span style="padding-right: 10px; padding-left: 10px; float: left;">
				<div id="signatureShow2" style="display: none;">
					<img id="signatureImgTag2" src="<%=existingSignatureImageRenderingUrl2 %>" />
				</div>
				<iframe style="width:400px; height:132px;"id="signatureFrame2" src="<%= request.getContextPath() %>/signature_pad/tabletSignature.jsp?inWindow=true&<%=DigitalSignatureUtils.SIGNATURE_REQUEST_ID_KEY%>=<%=newSignatureRequestId2%>" ></iframe>
				<%-- <input style="border: 1px solid black;" type="text" id="audiologist_reg" name="audiologist_reg" maxlength="49" value="<%=props.getProperty("audiologist_reg", "")%>"> --%> 
				<br>Audiologist Reg. CASLPO
			</span>
		</div></td>
</tr>

						</table></td>
				</tr>
			</table>
			
			<div style="width: 100%" align="center">
			<table class="table_terms" border="1">
				<tr>
					<td colspan="2" class="label" style="font-size: 15px;">Terms</td>
				</tr>
				<tr>
					<td class="label">ABR</td>
					<td>auditory brainstem response</td>
				</tr>
				<tr>
					<td class="label">C</td>
					<td>cortical threshold</td>
				</tr>
				<tr>
					<td class="label">CA</td>
					<td>check aid</td>
				</tr>
				<tr>
					<td class="label">CNM</td>
					<td>could not mask</td>
				</tr>
				<tr>
					<td class="label">CNT</td>
					<td>could not test</td>
				</tr>
				<tr>
					<td class="label">DNT</td>
					<td>did not test</td>
				</tr>
				<tr>
					<td class="label">DPOAE</td>
					<td>distortion product otoacoustic emissions</td>
				</tr>
				<tr>
					<td class="label">ECoG</td>
					<td>electrocochleography</td>
				</tr>
				<tr>
					<td class="label">ESL</td>
					<td>Engish second language</td>
				</tr>
				<tr>
					<td class="label">Lt</td>
					<td>left</td>
				</tr>
				<tr>
					<td class="label">MCL</td>
					<td>most confortable level</td>
				</tr>
				<tr>
					<td class="label">MLV</td>
					<td>monitored live voice</td>
				</tr>
				<tr>
					<td class="label">NR</td>
					<td>no response</td>
				</tr>
				<tr>
					<td class="label">Rt</td>
					<td>right</td>
				</tr>
				<tr>
					<td class="label">SAT</td>
					<td>speech awareness threshold</td>
				</tr>
				<tr>
					<td class="label">SRT</td>
					<td>speech reception threshold</td>
				</tr>
				<tr>
					<td class="label">TOAE</td>
					<td>transient otoacoustic emissions</td>
				</tr>
				<tr>
					<td class="label">TP</td>
					<td>trial period</td>
				</tr>
				<tr>
					<td class="label">UCL</td>
					<td>uncomfortable listening level</td>
				</tr>
				<tr>
					<td class="label">UM</td>
					<td>unmasked</td>
				</tr>
				<tr>
					<td class="label">VT</td>
					<td>vibrotactile</td>
				</tr>
			</table>
			</div>
	</html:form>
</body>
</html>
