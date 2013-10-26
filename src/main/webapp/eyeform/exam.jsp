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

<%@ page import="oscar.OscarProperties"%>

<%@ include file="/casemgmt/taglibs.jsp" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@page import="org.oscarehr.eyeform.model.EyeformSpecsHistory"%>
<%@page import="oscar.oscarEncounter.pageUtil.*,oscar.oscarEncounter.data.*,java.util.List,org.oscarehr.eyeform.model.EyeformSpecsHistory"%>
<%@ page import="org.oscarehr.util.*"%>
<%@ page import="org.oscarehr.eyeform.dao.*"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<link rel="stylesheet" href="<c:out value="${ctx}"/>/oscarEncounter/encounterStyles.css" type="text/css">

<%
	oscar.OscarProperties props1 = oscar.OscarProperties.getInstance();
	boolean glasses_hx = props1.getBooleanProperty("eyeform_glasses_hx_disable", "true");
	boolean vision_assessment = props1.getBooleanProperty("eyeform_vision_assessment_disable", "true");
	boolean vision_measurement = props1.getBooleanProperty("eyeform_vision_measurement_disable", "true");
	boolean refractive = props1.getBooleanProperty("eyeform_refractive_disable", "true");
	boolean iop = props1.getBooleanProperty("eyeform_iop_disable", "true");
	boolean other_exam = props1.getBooleanProperty("eyeform_other_exam_disable", "true");
	boolean duction = props1.getBooleanProperty("eyeform_duction_disable", "true");
	boolean deviation_measurement = props1.getBooleanProperty("eyeform_deviation_measurement_disable", "true");
	boolean external_orbit = props1.getBooleanProperty("eyeform_external_orbit_disable", "true");
	boolean eyelid_nld = props1.getBooleanProperty("eyeform_eyelid_nld_disable", "true");
	boolean eyelid_measurement = props1.getBooleanProperty("eyeform_eyelid_measurement_disable", "true");
	boolean posterior_segment = props1.getBooleanProperty("eyeform_posterior_segment_disable", "true");
	boolean anterior_segment = props1.getBooleanProperty("eyeform_anterior_segment_disable", "true");
	boolean eyeform_v3 = props1.getBooleanProperty("eyeform3", "true");
	String eyeform = props1.getProperty("cme_js");    
	
	
	String demo = request.getParameter("demographic_no");
	String appo = request.getParameter("appointment_no");
	String type1="distance";
    SpecsHistoryDao dao = (SpecsHistoryDao) SpringUtils.getBean("SpecsHistoryDAO");
    List<EyeformSpecsHistory> specs = dao.getRecentRecord(Integer.parseInt(demo),Integer.parseInt(appo),type1);
%>

<script>
//if have value under slidy block set the color to brown
function touchColor() {
		var divs = document.getElementsByClassName(document, "div", "slidey");
        for (var i=0; i<divs.length; i++) {
                var inputs = divs[i].getElementsByTagName("INPUT");
                var teinputs=divs[i].getElementsByTagName("TEXTAREA");
                for (var j=0; j<inputs.length; j++)
                        if (inputs[j].value.length>0 && inputs[j].className=="examfieldwhite")
                                break;
                for (var k=0; k<teinputs.length; k++)
                        if (teinputs[k].value.length>0 && teinputs[k].className=="examfieldwhite")
                                break;
                var color=(j<inputs.length || k<teinputs.length)?"brown":"black";
                divs[i].getElementsByTagName("A")[0].style.color=color;
        }
}
</script>

<script>
var effectMap = {};
jQuery("document").ready(function() {
	for(var x=21;x<34;x++) {
		var name = 's_'+x;
		var el = document.getElementById(name);
		if(el) {
			el.style.display='none';
			effectMap[name] = 'up';
		}

	}
});

function togglediv(el) {
	jQuery('#s_' + el.id.substring(el.id.indexOf('_')+1)).toggle();
}

function expanddiv(el) {
	jQuery('#s_' + el.id.substring(el.id.indexOf('_')+1)).show();
}

function collapsediv(el) {
	jQuery('#s_' + el.id.substring(el.id.indexOf('_')+1)).hide();
}

function whiteField(el){
  		el.className="examfieldwhite";
}

function setfieldvalue(name,value) {
	jQuery("input[measurement='"+name+"']").each(function() {
		jQuery(this).val(value);
		whiteField(this);
	});
}

function getfieldvalue(name) {
	var val = undefined;
	jQuery("input[measurement='"+name+"']").each(function() {
		val = jQuery(this).val();
	});
	return val;
}

function copyAR(){
	setfieldvalue("od_manifest_refraction_sph",getfieldvalue("od_ar_sph"));
	setfieldvalue("os_manifest_refraction_sph",getfieldvalue("os_ar_sph"));
	setfieldvalue("od_manifest_refraction_cyl",getfieldvalue("od_ar_cyl"));
	setfieldvalue("os_manifest_refraction_cyl",getfieldvalue("os_ar_cyl"));
	setfieldvalue("od_manifest_refraction_axis",getfieldvalue("od_ar_axis"));
	setfieldvalue("os_manifest_refraction_axis",getfieldvalue("os_ar_axis"));
}

var copy_gl_rs = "";
var copy_gl_rc = "";
var copy_gl_rx = "";
var copy_gl_ls = "";
var copy_gl_lc = "";
var copy_gl_lx = "";

function copyglasses(){
	copy_gl_rs = getfieldvalue("gl_rs");
	copy_gl_rc = getfieldvalue("gl_rc");
	copy_gl_rx = getfieldvalue("gl_rx");
	copy_gl_ls = getfieldvalue("gl_ls");
	copy_gl_lc = getfieldvalue("gl_lc");	
	copy_gl_lx = getfieldvalue("gl_lx");
}

var copy_v_rs = "";
var copy_v_rc = "";
var copy_v_rx = "";
var copy_v_ls = "";
var copy_v_lc = "";
var copy_v_lx = "";

function copyar(){
	copy_v_rs = getfieldvalue("v_rs");
	copy_v_rc = getfieldvalue("v_rc");
	copy_v_rx = getfieldvalue("v_rx");
	copy_v_ls = getfieldvalue("v_ls");
	copy_v_lc = getfieldvalue("v_lc");	
	copy_v_lx = getfieldvalue("v_lx");
}

function paste_mdist(){
	setfieldvalue("v_rds",copy_gl_rs);
	setfieldvalue("v_rdc",copy_gl_rc);
	setfieldvalue("v_rdx",copy_gl_rx);
	setfieldvalue("v_lds",copy_gl_ls);
	setfieldvalue("v_ldc",copy_gl_lc);
	setfieldvalue("v_ldx",copy_gl_lx);
}

function paste_mnear(){
	setfieldvalue("v_rns",copy_gl_rs);
	setfieldvalue("v_rnc",copy_gl_rc);
	setfieldvalue("v_rnx",copy_gl_rx);
	setfieldvalue("v_lns",copy_gl_ls);
	setfieldvalue("v_lnc",copy_gl_lc);
	setfieldvalue("v_lnx",copy_gl_lx);
}

function paste_Cyclo(){
	setfieldvalue("v_rcs",copy_gl_rs);
	setfieldvalue("v_rcc",copy_gl_rc);
	setfieldvalue("v_rcx",copy_gl_rx);
	setfieldvalue("v_lcs",copy_gl_ls);
	setfieldvalue("v_lcc",copy_gl_lc);
	setfieldvalue("v_lcx",copy_gl_lx);
}

function copySpecs(){
	jQuery.ajax({dataType: "script", url:ctx+"/eyeform/SpecsHistory.do?demographicNo="+demographicNo+"&method=copySpecs"});
}

function setAnterior(){
	setfieldvalue("od_cornea","clear");
	setfieldvalue("os_cornea","clear");
	setfieldvalue("od_conjuctiva_sclera","white");
	setfieldvalue("os_conjuctiva_sclera","white");
	setfieldvalue("od_anterior_chamber","deep and quiet");
	setfieldvalue("os_anterior_chamber","deep and quiet");
	setfieldvalue("od_angle_middle1","open");
	setfieldvalue("os_angle_middle1","open");
	setfieldvalue("od_iris","normal");
	setfieldvalue("os_iris","normal");
	setfieldvalue("od_lens","clear");
	setfieldvalue("os_lens","clear");
	setfieldvalue("od_anterior_vitreous","clear");
	setfieldvalue("os_anterior_vitreous","clear");
}
function clearAnterior(){
	setfieldvalue("od_cornea","");
	setfieldvalue("os_cornea","");
	setfieldvalue("od_conjuctiva_sclera","");
	setfieldvalue("os_conjuctiva_sclera","");
	setfieldvalue("od_anterior_chamber","");
	setfieldvalue("os_anterior_chamber","");
	setfieldvalue("od_angle_middle0","");
	setfieldvalue("od_angle_middle1","");
	setfieldvalue("od_angle_middle2","");
	setfieldvalue("od_angle_up","");
	setfieldvalue("od_angle_down","");
	setfieldvalue("os_angle_middle0","");
	setfieldvalue("os_angle_middle1","");
	setfieldvalue("os_angle_middle2","");
	setfieldvalue("os_angle_up","");
	setfieldvalue("os_angle_down","");
	setfieldvalue("od_iris","");
	setfieldvalue("os_iris","");
	setfieldvalue("od_lens","");
	setfieldvalue("os_lens","");
	setfieldvalue("od_anterior_vitreous","");
	setfieldvalue("os_anterior_vitreous","");
}

function setPosterior(){
	setfieldvalue("od_disc","normal");
	setfieldvalue("os_disc","normal");
	setfieldvalue("od_macula","normal");
	setfieldvalue("os_macula","normal");
	setfieldvalue("od_retina","normal");
	setfieldvalue("os_retina","normal");
	setfieldvalue("od_vitreous","clear");
	setfieldvalue("os_vitreous","clear");
}
function clearPosterior(){
	setfieldvalue("od_disc","");
	setfieldvalue("os_disc","");
	setfieldvalue("od_macula","");
	setfieldvalue("os_macula","");
	setfieldvalue("od_retina","");
	setfieldvalue("os_retina","");
	setfieldvalue("od_vitreous","");
	setfieldvalue("os_vitreous","");
	setfieldvalue("od_cd_ratio_horizontal","");
	setfieldvalue("os_cd_ratio_horizontal","");
}

function clearMeasurement_od(){
	setfieldvalue("v_rk1","");
	setfieldvalue("v_rk2","");
	setfieldvalue("v_rkx","");
	setfieldvalue("v_rs","");
	setfieldvalue("v_rc","");
	setfieldvalue("v_rx","");
	setfieldvalue("v_rar","");
	setfieldvalue("v_rds","");
	setfieldvalue("v_rdc","");
	setfieldvalue("v_rdx","");
	setfieldvalue("v_rdv","");
	setfieldvalue("v_rns","");
	setfieldvalue("v_rnc","");
	setfieldvalue("v_rnx","");
	setfieldvalue("v_rnv","");
	setfieldvalue("v_rcs","");
	setfieldvalue("v_rcc","");
	setfieldvalue("v_rcx","");
	setfieldvalue("v_rcv","");
}

function clearMeasurement_os(){
	setfieldvalue("v_lk1","");
	setfieldvalue("v_lk2","");
	setfieldvalue("v_lkx","");
	setfieldvalue("v_ls","");
	setfieldvalue("v_lc","");
	setfieldvalue("v_lx","");
	setfieldvalue("v_lar","");
	setfieldvalue("v_lds","");
	setfieldvalue("v_ldc","");
	setfieldvalue("v_ldx","");
	setfieldvalue("v_ldv","");
	setfieldvalue("v_lns","");
	setfieldvalue("v_lnc","");
	setfieldvalue("v_lnx","");
	setfieldvalue("v_lnv","");
	setfieldvalue("v_lcs","");
	setfieldvalue("v_lcc","");
	setfieldvalue("v_lcx","");
	setfieldvalue("v_lcv","");
}

function setExternal(){
	setfieldvalue("od_face","normal");
	setfieldvalue("os_face","normal");
	setfieldvalue("od_upper_lid","normal");
	setfieldvalue("os_upper_lid","normal");
	setfieldvalue("od_lower_lid","normal");
	setfieldvalue("os_lower_lid","normal");
	setfieldvalue("od_punctum","normal");
	setfieldvalue("os_punctum","normal");
	setfieldvalue("od_lacrimal_lake","normal");
	setfieldvalue("os_lacrimal_lake","normal");
}

function clearExternal(){
	setfieldvalue("od_face","");
	setfieldvalue("os_face","");
	setfieldvalue("od_upper_lid","");
	setfieldvalue("os_upper_lid","");
	setfieldvalue("od_lower_lid","");
	setfieldvalue("os_lower_lid","");
	setfieldvalue("od_punctum","");
	setfieldvalue("os_punctum","");
	setfieldvalue("od_lacrimal_lake","");
	setfieldvalue("os_lacrimal_lake","");
}

function setDuc_od(){
	setfieldvalue("duc_rur","normal");
	setfieldvalue("duc_rul","normal");
	setfieldvalue("duc_rr","normal");
	setfieldvalue("duc_rl","normal");
	setfieldvalue("duc_rdr","normal");
	setfieldvalue("duc_rdl","normal");
}

function clearDuc_od(){
	setfieldvalue("duc_rur","");
	setfieldvalue("duc_rul","");
	setfieldvalue("duc_rr","");
	setfieldvalue("duc_rl","");
	setfieldvalue("duc_rdr","");
	setfieldvalue("duc_rdl","");
}

function setDuc_os(){
	setfieldvalue("duc_lur","normal");
	setfieldvalue("duc_lul","normal");
	setfieldvalue("duc_lr","normal");
	setfieldvalue("duc_ll","normal");
	setfieldvalue("duc_ldr","normal");
	setfieldvalue("duc_ldl","normal");
}

function clearDuc_os(){
	setfieldvalue("duc_lur","");
	setfieldvalue("duc_lul","");
	setfieldvalue("duc_lr","");
	setfieldvalue("duc_ll","");
	setfieldvalue("duc_ldr","");
	setfieldvalue("duc_ldl","");
}

function setDuc_ou(){
	setfieldvalue("dip_ur","normal");
	setfieldvalue("dip_u","normal");
	setfieldvalue("dip_r","normal");
	setfieldvalue("dip_p","normal");
	setfieldvalue("dip_dr","normal");
	setfieldvalue("dip_d","normal");
}

function clearDuc_ou(){
	setfieldvalue("dip_ur","");
	setfieldvalue("dip_u","");
	setfieldvalue("dip_r","");
	setfieldvalue("dip_p","");
	setfieldvalue("dip_dr","");
	setfieldvalue("dip_d","");
}

function setDeviation(){
	setfieldvalue("dev_u","normal");
	setfieldvalue("dev_near","normal");
	setfieldvalue("dev_r","normal");
	setfieldvalue("dev_p","normal");
	setfieldvalue("dev_l","normal");
	setfieldvalue("dev_plus3","normal");
	setfieldvalue("dev_rt","normal");
	setfieldvalue("dev_d","normal");
	setfieldvalue("dev_lt","normal");
	setfieldvalue("dev_far","normal");
}

function clearDeviation(){
	setfieldvalue("dev_u","");
	setfieldvalue("dev_near","");
	setfieldvalue("dev_r","");
	setfieldvalue("dev_p","");
	setfieldvalue("dev_l","");
	setfieldvalue("dev_plus3","");
	setfieldvalue("dev_rt","");
	setfieldvalue("dev_d","");
	setfieldvalue("dev_lt","");
	setfieldvalue("dev_far","");
}

function setExternal_od(){
	setfieldvalue("ext_rface","normal");
	setfieldvalue("ext_rretro","normal");
	setfieldvalue("ext_rhertel","normal");
}

function clearExternal_od(){
	setfieldvalue("ext_rface","");
	setfieldvalue("ext_rretro","");
	setfieldvalue("ext_rhertel","");
}

function setExternal_os(){
	setfieldvalue("ext_lface","normal");
	setfieldvalue("ext_lretro","normal");
	setfieldvalue("ext_lhertel","normal");
}

function clearExternal_os(){
	setfieldvalue("ext_lface","");
	setfieldvalue("ext_lretro","");
	setfieldvalue("ext_lhertel","");
}

function setEyelid_od(){
	setfieldvalue("ext_rul","normal");
	setfieldvalue("ext_rll","normal");
	setfieldvalue("ext_rlake","normal");
	setfieldvalue("ext_rirrig","normal");
	setfieldvalue("ext_rpunc","normal");
	setfieldvalue("ext_rnld","normal");
	setfieldvalue("ext_rdye","normal");
}

function clearEyelid_od(){
	setfieldvalue("ext_rul","");
	setfieldvalue("ext_rll","");
	setfieldvalue("ext_rlake","");
	setfieldvalue("ext_rirrig","");
	setfieldvalue("ext_rpunc","");
	setfieldvalue("ext_rnld","");
	setfieldvalue("ext_rdye","");
}

function setEyelid_os(){
	setfieldvalue("ext_lul","normal");
	setfieldvalue("ext_lll","normal");
	setfieldvalue("ext_llake","normal");
	setfieldvalue("ext_lirrig","normal");
	setfieldvalue("ext_lpunc","normal");
	setfieldvalue("ext_lnld","normal");
	setfieldvalue("ext_ldye","normal");
}

function clearEyelid_os(){
	setfieldvalue("ext_lul","");
	setfieldvalue("ext_lll","");
	setfieldvalue("ext_llake","");
	setfieldvalue("ext_lirrig","");
	setfieldvalue("ext_lpunc","");
	setfieldvalue("ext_lnld","");
	setfieldvalue("ext_ldye","");
}

function setAnterior_od(){
	setfieldvalue("a_rk","clear");
	setfieldvalue("a_rconj","white");
	setfieldvalue("a_rac","deep and quiet");
	setfieldvalue("a_rangle","open");
	setfieldvalue("a_riris","normal");
	setfieldvalue("a_rlens","clear");
}

function clearAnterior_od(){
	setfieldvalue("a_rk","");
	setfieldvalue("a_rconj","");
	setfieldvalue("a_rac","");
	setfieldvalue("a_rangle","");
	setfieldvalue("a_riris","");
	setfieldvalue("a_rlens","");
}

function setAnterior_os(){
	setfieldvalue("a_lk","clear");
	setfieldvalue("a_lconj","white");
	setfieldvalue("a_lac","deep and quiet");
	setfieldvalue("a_langle","open");
	setfieldvalue("a_liris","normal");
	setfieldvalue("a_llens","clear");
}

function clearAnterior_os(){
	setfieldvalue("a_lk","");
	setfieldvalue("a_lconj","");
	setfieldvalue("a_lac","");
	setfieldvalue("a_langle","");
	setfieldvalue("a_liris","");
	setfieldvalue("a_llens","");
}

function setPosterior_od(){
	setfieldvalue("p_rdisc","normal");
	//setfieldvalue("p_rcd","normal");
	setfieldvalue("p_rmac","normal");
	setfieldvalue("p_rret","normal");
	setfieldvalue("p_rvit","clear");
}

function clearPosterior_od(){
	setfieldvalue("p_rdisc","");
	setfieldvalue("p_rcd","");
	setfieldvalue("p_rmac","");
	setfieldvalue("p_rret","");
	setfieldvalue("p_rvit","");
}

function setPosterior_os(){
	setfieldvalue("p_ldisc","normal");
	//setfieldvalue("p_lcd","normal");
	setfieldvalue("p_lmac","normal");
	setfieldvalue("p_lret","normal");
	setfieldvalue("p_lvit","clear");
}

function clearPosterior_os(){
	setfieldvalue("p_ldisc","");
	setfieldvalue("p_lcd","");
	setfieldvalue("p_lmac","");
	setfieldvalue("p_lret","");
	setfieldvalue("p_lvit","");
}

function expandAll() {
	jQuery(".title a").each(function() {
		if(this.id.indexOf('a_')!=-1) {
			expanddiv(this);
		}
	});
}

function collapseAll() {
	jQuery(".title a").each(function() {
		if(this.id.indexOf('a_')!=-1) {
			collapsediv(this);
		}
	});
}
var rowID=2;
function insRow()
{

if(rowID<5){
var rowNo=rowID;
var x=document.getElementById('myTable').insertRow(rowID+1);
var h1=x.insertCell(0);
var h2=x.insertCell(1);
var h3=x.insertCell(2);
var h4=x.insertCell(3);
var h5=x.insertCell(4);
var h6=x.insertCell(5);
var h7=x.insertCell(6);
var h8=x.insertCell(7);
var h9=x.insertCell(8);
var h10=x.insertCell(9);
var h11=x.insertCell(10);
var h12=x.insertCell(11);
var h13=x.insertCell(12);
var h14=x.insertCell(13);
var h15=x.insertCell(14);
var h16=x.insertCell(15);
var h17=x.insertCell(16);
var h18=x.insertCell(17);
var h19=x.insertCell(18);


h1.innerHTML="<div><select style='width:100px;' name='specs.type"+rowNo+"' id='specs.type"+rowNo+"'><option value='distance'>distance</option><option value='bifocal'>bifocal</option><option value='invisible bifocal'>invisible bifocal</option><option value='reading'>reading</option></select></div>";
h2.innerHTML="<div><input name='specs.dateStr"+rowNo+"'  type='text' tabindex='33' class='examfieldgrey' size='10'  id='gl_date"+rowNo+"' onfocus='whiteField(this);'></div>";
h3.innerHTML="<div  width='1%'><img src='"+ctx+"/images/cal.gif' id='pdate_cal"+rowNo+"'></div>";
h4.innerHTML="<div><input name='specs.odSph"+rowNo+"' id='specs.odSph"+rowNo+"' type='text' tabindex='34' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h5.innerHTML="<div><input name='specs.odCyl"+rowNo+"' id='specs.odCyl"+rowNo+"' type='text' tabindex='35' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h6.innerHTML="<div><input name='specs.odAxis"+rowNo+"' id='specs.odAxis"+rowNo+"' type='text' tabindex='36' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h7.innerHTML="<div><input name='specs.odAdd"+rowNo+"' id='specs.odAdd"+rowNo+"' type='text' tabindex='37' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h8.innerHTML="<div><input name='specs.odPrism"+rowNo+"' id='specs.odPrism"+rowNo+"' type='text' tabindex='38' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h9.innerHTML="<div  width='8%'></div>";
h10.innerHTML="<div><input name='specs.osSph"+rowNo+"' id='specs.osSph"+rowNo+"' type='text' tabindex='39' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h11.innerHTML="<div><input name='specs.osCyl"+rowNo+"' id='specs.osCyl"+rowNo+"' type='text' tabindex='40' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h12.innerHTML="<div><input name='specs.osAxis"+rowNo+"' id='specs.osAxis"+rowNo+"' type='text' tabindex='41' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h13.innerHTML="<div><input name='specs.osAdd"+rowNo+"' id='specs.osAdd"+rowNo+"' type='text' tabindex='42' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h14.innerHTML="<div><input name='specs.osPrism"+rowNo+"' id='specs.osPrism"+rowNo+"' type='text' tabindex='43' maxlength='6' class='examfieldgrey' size='6'  onfocus='whiteField(this);'></div>";
h15.innerHTML="<div><a href='javascript:void(0)' onclick='hxOpen"+rowNo+"();'><img src='../images/notes.gif' /> </a></div>";
h16.innerHTML="<div><input type='hidden' value='save' name='method'></div>";
h17.innerHTML="<div><input type='hidden' value="+<%=request.getParameter("demographic_no")%>+" name='specs.demographicNo'></div>";
h18.innerHTML="<div><input type='hidden' value="+<%=request.getParameter("appointment_no")%>+" name='specs.appointmentNo'></div>";
h19.innerHTML="<div><input type='hidden' value='' name='specs.id"+rowNo+"' id='specs.id"+rowNo+"'></div>";


Calendar.setup({ inputField : "gl_date"+rowNo, ifFormat : "%Y-%m-%d", showsTime :false, button : "pdate_cal"+rowNo, singleClick : true, step : 1 });
 
rowID=rowID+1;
}
}

function hxOpen()
{
        var id=document.getElementById("specs.id").value;
        var type=document.getElementById("type").value;  	
		var date=document.getElementById("gl_date").value;
		
		var odSph=document.getElementById("odSph").value;
		var odCyl=document.getElementById("odCyl").value;
        var odAxis=document.getElementById("odAxis").value;
		var odAdd=document.getElementById("odAdd").value;
    	var odPrism=document.getElementById("odPrism").value;
		
		var osSph=document.getElementById("osSph").value;
		var osCyl=document.getElementById("osCyl").value;
		var osAxis=document.getElementById("osAxis").value;
        var osAdd=document.getElementById("osAdd").value;
		var osPrism=document.getElementById("osPrism").value;  
		
		var appno=document.getElementById("appointment_no").value; 
		var demno=document.getElementById("demographic_no").value; 
    	
 window.open("../eyeform/glassHX.jsp?specs.id="+id+"&type="+type+"&dateStr="+date+"&odSph="+odSph+"&odCyl="+odCyl+"&odAxis="+odAxis+"&odAdd="+odAdd+"&odPrism="+odPrism+"&osSph="+osSph+"&osCyl="+osCyl+"&osAxis="+osAxis+"&osAdd="+osAdd+"&osPrism="+osPrism+"&appointment_no="+appno+"&demographic_no="+demno+"",'anwin','width=400,height=300');

}
function hxOpen2()
{
        var id=document.getElementById("specs.id2").value;
        var type=document.getElementById("specs.type2").value;  	
		var date=document.getElementById("gl_date2").value;
		
		var odSph=document.getElementById("specs.odSph2").value;
		var odCyl=document.getElementById("specs.odCyl2").value;
        var odAxis=document.getElementById("specs.odAxis2").value;
		var odAdd=document.getElementById("specs.odAdd2").value;
    	var odPrism=document.getElementById("specs.odPrism2").value;
		
		var osSph=document.getElementById("specs.osSph2").value;
		var osCyl=document.getElementById("specs.osCyl2").value;
		var osAxis=document.getElementById("specs.osAxis2").value;
        var osAdd=document.getElementById("specs.osAdd2").value;
		var osPrism=document.getElementById("specs.osPrism2").value;  
		
		var appno=document.getElementById("appointment_no").value; 
		var demno=document.getElementById("demographic_no").value; 
    	
 window.open("../eyeform/glassHX.jsp?specs.id="+id+"&type="+type+"&dateStr="+date+"&odSph="+odSph+"&odCyl="+odCyl+"&odAxis="+odAxis+"&odAdd="+odAdd+"&odPrism="+odPrism+"&osSph="+osSph+"&osCyl="+osCyl+"&osAxis="+osAxis+"&osAdd="+osAdd+"&osPrism="+osPrism+"&appointment_no="+appno+"&demographic_no="+demno+"",'anwin','width=400,height=300');

}
function hxOpen3()
{
        var id=document.getElementById("specs.id3").value;
        var type=document.getElementById("specs.type3").value;  	
		var date=document.getElementById("gl_date").value;
		
		var odSph=document.getElementById("specs.odSph3").value;
		var odCyl=document.getElementById("specs.odCyl3").value;
        var odAxis=document.getElementById("specs.odAxis3").value;
		var odAdd=document.getElementById("specs.odAdd3").value;
    	var odPrism=document.getElementById("specs.odPrism3").value;
		
		var osSph=document.getElementById("specs.osSph3").value;
		var osCyl=document.getElementById("specs.osCyl3").value;
		var osAxis=document.getElementById("specs.osAxis3").value;
        var osAdd=document.getElementById("specs.osAdd3").value;
		var osPrism=document.getElementById("specs.osPrism3").value;  
		
		var appno=document.getElementById("appointment_no").value; 
		var demno=document.getElementById("demographic_no").value; 
    	
 window.open("../eyeform/glassHX.jsp?specs.id="+id+"&type="+type+"&dateStr="+date+"&odSph="+odSph+"&odCyl="+odCyl+"&odAxis="+odAxis+"&odAdd="+odAdd+"&odPrism="+odPrism+"&osSph="+osSph+"&osCyl="+osCyl+"&osAxis="+osAxis+"&osAdd="+osAdd+"&osPrism="+osPrism+"&appointment_no="+appno+"&demographic_no="+demno+"",'anwin','width=400,height=300');

}
function hxOpen4()
{
        var id=document.getElementById("specs.id4").value;
        var type=document.getElementById("specs.type4").value;  	
		var date=document.getElementById("gl_date4").value;
		
		var odSph=document.getElementById("specs.odSph4").value;
		var odCyl=document.getElementById("specs.odCyl4").value;
        var odAxis=document.getElementById("specs.odAxis4").value;
		var odAdd=document.getElementById("specs.odAdd4").value;
    	var odPrism=document.getElementById("specs.odPrism4").value;
		
		var osSph=document.getElementById("specs.osSph4").value;
		var osCyl=document.getElementById("specs.osCyl4").value;
		var osAxis=document.getElementById("specs.osAxis4").value;
        var osAdd=document.getElementById("specs.osAdd4").value;
		var osPrism=document.getElementById("specs.osPrism4").value;  
		
		var appno=document.getElementById("appointment_no").value; 
		var demno=document.getElementById("demographic_no").value; 
    	
 window.open("../eyeform/glassHX.jsp?specs.id="+id+"&type="+type+"&dateStr="+date+"&odSph="+odSph+"&odCyl="+odCyl+"&odAxis="+odAxis+"&odAdd="+odAdd+"&odPrism="+odPrism+"&osSph="+osSph+"&osCyl="+osCyl+"&osAxis="+osAxis+"&osAdd="+osAdd+"&osPrism="+osPrism+"&appointment_no="+appno+"&demographic_no="+demno+"",'anwin','width=400,height=300');

}
</script>

<script type="text/javascript">
	Calendar.setup({ inputField : "gl_date", ifFormat : "%Y-%m-%d", showsTime :false, button : "pdate_cal", singleClick : true, step : 1 });
</script>

<style type="text/css">
.exam
{
	padding: 0px;
	border-spacing: 0px;
	margin: 0px;
	border: 0px;
}

.exam td
{
	text-align: center;
	vertical-align:middle;
	padding:1px !important;
	margin:0px;
	border: 0px;
	font-size: 9px;
}

td .label
{
	text-align: left;
}

.examfieldgrey
{
	background-color: #DDDDDD;
}

.examfieldwhite
{
	background-color: white;
}

.slidey { margin-bottom: 6px;font-size: 10pt;}
.slidey .title {
        margin-top: 1px;

        list-style-type: none;
       /* font-family: Trebuchet MS, Lucida Sans Unicode, Arial, sans-serif; */

        overflow: hidden;
        background-color: #f6f6ff;
        text-align:left;
        padding: 0px;

         /*font-size: 1.0em;*/
        font-size: 9px;
        /*font-variant:small-caps;*/

        font-weight: bold;


       font-family: Verdana,Tahoma,Arial,sans-serif;
       font-style:normal;
       text-transform:none;
       text-decoration:none;
       letter-spacing:normal;
       word-spacing:0;
       line-height:15px;
       vertical-align:baseline;




  }

.section_title {
	color:black;
}

.slidey .title2 {
        margin-top: 1px;
        font-size:10pt;
        list-style-type: none;
        font-family: Trebuchet MS, Lucida Sans Unicode, Arial, sans-serif;
        overflow: hidden;
        background-color: #ccccff;
        padding: 0px; }
/* the only noteworthy thing here is the overflow is hidden,
it's really a sleight-of-hand kind of thing, we're playing
with the height and that makes it 'slide' */
.slidey .slideblock { overflow: hidden; background-color:  #ccccff;  padding: 2px; font-size: 10pt; }

span.ge{
        margin-top: 1px;
        border-bottom: 1px solid #000;
        font-weight: bold;
        list-style-type: none;
        font-family: Trebuchet MS, Lucida Sans Unicode, Arial, sans-serif;
        font-size: 12pt;
        overflow: hidden;
        background-color: #99bbee;
        padding: 0px;
        color: black;;
        width: 300px;
}


</style>

<span style="font-size:10px">
	<a id="save_measurements" href="javascript:void(0)"  onclick="document.getElementById('hxForm').submit();">[Save Measurements]</a>
</span>
<span style="float:right;font-size:10px">
	<a href="javascript:void(0);" onclick="expandAll();">[expand all sections]</a>&nbsp;
	<a href="javascript:void(0);" onclick="collapseAll();">[collapse all sections]</a>
</span>

<table border="0" width="100%">
<%if(eyeform.equals("eyeform3")){%>
<% if (!glasses_hx) {%>
<tr>
<td>
<div class="slidey">
 <form action="<c:out value="${ctx}"/>/eyeform/SpecsHistory.do" method="post" name="specsHistoryForm" id="hxForm" target="ifFrame">
	<div class="title">
		<a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" href="javascript:void(0)" tabindex="31" id="a_21" onclick="togglediv(this);">Glasses Hx:</a>
		<span>&nbsp;&nbsp;</span>

             <a href="javascript:void(0)" tabindex="32" onclick="popupPage(500,900,'examinationhistory1','<c:out value="${ctx}"/>/eyeform/ExaminationHistory.do?demographicNo=<%=request.getParameter("demographic_no")%>&method=query&fromlist2=Glasses Rx'); return false;">[old glasses]</a>
	          <span>&nbsp;&nbsp;</span>
              <a href="javascript:void(0)" onclick="insRow()">[add]</a>
			  
			  <span>&nbsp;&nbsp;</span>
			  
			  <a href="javascript:void(0)"  onclick="document.getElementById('hxForm').submit();" style="display:none">[save]</a>
	</div>
	
	
			
	
	<div id="s_21" class="slideblock">
	
		<table class="exam" width="100%" id="myTable">
		 
		<tr>
			<td width="10%><a href="javascript:void(0)" tabindex="258" onclick="copyglasses();return false;">[copy]</a></td>
			<td width="8%"></td>
			<td width="1%"></td>
			<td colspan="5" width="36%">OD</td>
			<td width="8%"></td>
			<td colspan="5"width="36%">OS</td>
		</tr>
		<tr>
		    <td >Type</td>
			<td>Date
			
			</td>	
			
			
			<td width="8%"></td>
			<td>S</td>
			<td>C</td>
			<td>X</td>
			<td>Add</td>
			<td>prism</td>
			<td width="8%"></td>
			<td>S</td>
			<td>C</td>
			<td>X</td>
			<td>Add</td>
			<td>prism</td>
		</tr>
		<%  
                                   
        String type2 = (String) specs.get(0).getType(); 
		String date2 = specs.get(0).getDateStr();
		String odSph = specs.get(0).getOdSph();
		String odCyl = specs.get(0).getOdCyl();
		String odAxis = specs.get(0).getOdAxis();
		String odAdd = specs.get(0).getOdAdd();
		String odPrism = specs.get(0).getOdPrism();
		String osSph = specs.get(0).getOsSph();
		String osCyl = specs.get(0).getOsCyl();
		String osAxis = specs.get(0).getOsAxis();
		String osAdd = specs.get(0).getOsAdd();
		String osPrism = specs.get(0).getOsPrism();
                                %>
								
		<tr>
			  <td>
	             <select name="specs.type" style="width:100px;" id="type" value="<%=type2%>">
	   	            <option value="distance">distance</option>
	            	<option value="bifocal">bifocal</option>
	            	<option value="invisible bifocal">invisible bifocal</option>
	                <option value="reading">reading</option>
	            </select>
	         </td>
             <td><input name="specs.dateStr"  type="text" tabindex="33" class="examfieldgrey" size="10"  id="gl_date" onfocus="whiteField(this);" value="<%=date2%>"/>
			 </td>

			 <td width="8%"><img src="<%=request.getContextPath()%>/images/cal.gif" id="pdate_cal"></td>
			 <td><input name="specs.odSph" id="odSph" type="text" tabindex="34" maxlength="6" class="examfieldgrey" size="6"  onfocus="whiteField(this);" value="<%=odSph%>"/></td>
			 <td><input name="specs.odCyl" id="odCyl" type="text" tabindex="35" maxlength="6" class="examfieldgrey" size="6"  onfocus="whiteField(this);" value="<%=odCyl%>"/></td>
			 <td><input name="specs.odAxis" id="odAxis" type="text" tabindex="36" maxlength="6" class="examfieldgrey" size="6"   onfocus="whiteField(this);" value="<%=odAxis%>"/></td>
			 <td><input name="specs.odAdd" id="odAdd" type="text" tabindex="37" maxlength="6" class="examfieldgrey" size="6"  onfocus="whiteField(this);" value="<%=odAdd%>"/></td>
			 <td><input name="specs.odPrism" id="odPrism" type="text" tabindex="38" maxlength="6" class="examfieldgrey" size="6"  onfocus="whiteField(this);" value="<%=odPrism%>"/></td>
			 <td width="8%"></td>
			 <td><input name="specs.osSph" id="osSph" type="text" tabindex="39" maxlength="6" class="examfieldgrey" size="6"  onfocus="whiteField(this);" value="<%=osSph%>"/></td>
			 <td><input name="specs.osCyl" id="osCyl" type="text" tabindex="40" maxlength="6" class="examfieldgrey" size="6"  onfocus="whiteField(this);" value="<%=osCyl%>"/></td>
			 <td><input name="specs.osAxis"id="osAxis" type="text" tabindex="41" maxlength="6" class="examfieldgrey" size="6"  onfocus="whiteField(this);" value="<%=osAxis%>"/></td>
			 <td><input name="specs.osAdd" id="osAdd" type="text" tabindex="42" maxlength="6" class="examfieldgrey" size="6"   onfocus="whiteField(this);" value="<%=osAdd%>"/></td>
			 <td><input name="specs.osPrism" id="osPrism" type="text" tabindex="43" maxlength="6" class="examfieldgrey" size="6"  onfocus="whiteField(this);" value="<%=osPrism%>"/></td>
			 <td><a href="javascript:void(0)" onclick="hxOpen();"><img src="../images/notes.gif"  /> </a></td>
			 <td><input type="hidden" value="save" name="method"></td>
             <td><input type="hidden" value="<%=request.getParameter("demographic_no")%>" name="specs.demographicNo" id="demographic_no"></td>
             <td><input type="hidden" value="<%= request.getParameter("appointment_no")%>" name="specs.appointmentNo" id="appointment_no"></td>
             <td><input type="hidden" value="" name="specs.id" id="specs.id"></td>
		</tr>
		</table>
		
	</div>
	
	</form>
	<iframe style="display:none" id="ifFrame" name="ifFrame" src="about:blank">
	</iframe>
</div>
</td>
</tr>
<%}%>
<% if (!vision_assessment) {%>
<tr>
<td>
<div class="slidey">
	 <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" href="javascript:void(0)" tabindex="44" id="a_22" onclick="togglediv(this);">Vision Assessment:</a>
            <!--
            <span>&nbsp;&nbsp;</span>

            
            <a href="javascript:void(0)" tabindex="357" onclick="setPretest();return false;">[normal]</a>
            <a href="javascript:void(0)" tabindex="358" onclick="clearPretest();return false;">[clear]</a>
            -->
        </div>
        <div id="s_22" class="slideblock">
            <table class="exam" width="100%">
            	<tr>
            		<td width="8%"></td>
            		<td width="24%" colspan="3">OD</td>
					<td width="8%"></td>
            		<td width="24%" colspan="3">OS</td>
					<td width="8%"></td>
            		<td width="24%" colspan="3">OU</td>
					<td width="8%"></td>
            	</tr>
            	<tr>
            		<td></td>
            		<td>sc</td>
            		<td>cc</td>
            		<td>ph</td>
					<td></td>
            		<td>sc</td>
            		<td>Cc</td>
            		<td>ph</td>
            		<td></td>
					<td></td>
            		<td>sc</td>
            		<td>cc</td>
					<td></td>
            	</tr>
            	<tr>
            		<td class="label">Dist</td>
            		<td><input type="text" tabindex="45" maxlength="8" class="examfieldgrey" size="8" measurement="v_rdsc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="46" maxlength="8" class="examfieldgrey" size="8" measurement="v_rdcc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="47" maxlength="8" class="examfieldgrey" size="8" measurement="v_rph" onfocus="whiteField(this);"/></td>
					<td></td>
            		<td><input type="text" tabindex="48" maxlength="8" class="examfieldgrey" size="8" measurement="v_ldsc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="49" maxlength="8" class="examfieldgrey" size="8" measurement="v_ldcc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="50" maxlength="8" class="examfieldgrey" size="8" measurement="v_lph" onfocus="whiteField(this);"/></td>
					<td></td>
            		<td colspan="2">Dist <input type="text" tabindex="59" maxlength="8" class="examfieldgrey" size="8" measurement="v_dsc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="60" maxlength="8" class="examfieldgrey" size="8" measurement="v_dcc" onfocus="whiteField(this);"/></td>
					<td></td>
            	</tr>
            	<tr>
            		<td class="label">Int</td>
            		<td><input type="text" tabindex="51" maxlength="8" class="examfieldgrey" size="8" measurement="v_risc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="52" maxlength="8" class="examfieldgrey" size="8" measurement="v_ricc" onfocus="whiteField(this);"/></td>
            		<td></td>
					<td></td>
            		<td><input type="text" tabindex="53" maxlength="8" class="examfieldgrey" size="8" measurement="v_lisc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="54" maxlength="8" class="examfieldgrey" size="8" measurement="v_licc" onfocus="whiteField(this);"/></td>
            		<td></td>
					<td></td>
            		<td colspan="2">Int &nbsp&nbsp<input type="text" tabindex="61" maxlength="8" class="examfieldgrey" size="8" measurement="v_isc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="62" maxlength="8" class="examfieldgrey" size="8" measurement="v_icc" onfocus="whiteField(this);"/></td>
					<td></td>
            	</tr>
            	<tr>
            		<td class="label">Near</td>
            		<td><input type="text" tabindex="55" maxlength="8" class="examfieldgrey" size="8" measurement="v_rnsc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="56" maxlength="8" class="examfieldgrey" size="8" measurement="v_rncc" onfocus="whiteField(this);"/></td>
            		<td></td>
					<td></td>
            		<td><input type="text" tabindex="57" maxlength="8" class="examfieldgrey" size="8" measurement="v_lnsc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="58" maxlength="8" class="examfieldgrey" size="8" measurement="v_lncc" onfocus="whiteField(this);"/></td>
            		<td></td>
					<td></td>
            		<td colspan="2">Near <input type="text" tabindex="63" maxlength="8" class="examfieldgrey" size="8" measurement="v_nsc" onfocus="whiteField(this);"/></td>
            		<td><input type="text" tabindex="64" maxlength="8" class="examfieldgrey" size="8" measurement="v_ncc" onfocus="whiteField(this);"/></td>
					<td></td>
            	</tr>
            </table>
        </div>
</div>
</td>
</tr>
<%}%>
<% if (!vision_measurement) {%>
<tr>
<td>
<div class="slidey">
	<div class="title">
        <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="65" href="javascript:void(0)" id="a_23" onclick="togglediv(this);">Vision Measurement:</a>
        <span>&nbsp;&nbsp;</span>
		<!--
    	<a href="javascript:void(0)" tabindex="66" onclick="clearPretest();return false;">[PRINT Rx]</a>   
		-->
    </div>
    <div id="s_23" class="slideblock">
    	<table class="exam" width="100%">
    		<tr>
    			<td width="8%"></td>
    			<td colspan="4" width="32%">OD
    				<span>&nbsp;&nbsp;</span>
    				<a href="javascript:void(0)" tabindex="66" onclick="clearMeasurement_od();return false;">[clear]</a>
    			</td>
				<td width="6%"></td>
    			<td colspan="4" width="32%">OS
    				<span>&nbsp;&nbsp;</span>
    				<a href="javascript:void(0)" tabindex="67" onclick="clearMeasurement_os();return false;">[clear]</a>
    			</td>
				<td width="6%"></td>
    			<td colspan="2" width="16%">OU</td>
    		</tr>
    		<tr>
    			<td></td>
    			<td>K1</td>
    			<td>K2</td>
    			<td>K2 axis</td>
    			<td rowspan="2"></td>
				<td></td>
    			<td>K1</td>
    			<td>K2</td>
    			<td>K2 axis</td>
    			<td rowspan="2"></td>
				<td></td>
    			<td colspan="2"></td>
    		</tr>
    		<tr>
    			<td>K</td>
    			<td><input type="text"  tabindex="68" maxlength="8" class="examfieldgrey" size="8" measurement="v_rk1" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="69" maxlength="8" class="examfieldgrey" size="8" measurement="v_rk2" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="70" maxlength="8" class="examfieldgrey" size="8" measurement="v_rkx" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td><input type="text"  tabindex="71" maxlength="8" class="examfieldgrey" size="8" measurement="v_lk1" onfocus="whiteField(this);"/></td>				
    			<td><input type="text"  tabindex="72" maxlength="8" class="examfieldgrey" size="8" measurement="v_lk2" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="73" maxlength="8" class="examfieldgrey" size="8" measurement="v_lkx" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td>Fly</td>
    			<td><input type="text"  tabindex="74" maxlength="8" class="examfieldgrey" size="8" measurement="v_fly" onfocus="whiteField(this);"/></td>
    		</tr>
    		<tr>
    			<td></td>
    			<td>S</td>
    			<td>C</td>
    			<td>x</td>
    			<td>VA</td>
				<td></td>
    			<td>S</td>
    			<td>C</td>
    			<td>x</td>
    			<td>VA</td>
				<td></td>
    			<td>Stereo</td>
    			<td><input type="text"  tabindex="75" maxlength="8" class="examfieldgrey" size="8" measurement="v_stereo" onfocus="whiteField(this);"/></td>
    		</tr>
    		<tr>
    			<td>AR
    				<span>&nbsp;&nbsp;</span>
    				<a href="javascript:void(0)" tabindex="67" onclick="copyar();return false;">[copy]</a>
    			</td>
    			<td><input type="text"  tabindex="76" maxlength="8" class="examfieldgrey" size="8" measurement="v_rs" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="77" maxlength="8" class="examfieldgrey" size="8" measurement="v_rc" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="78" maxlength="8" class="examfieldgrey" size="8" measurement="v_rx" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="79" maxlength="8" class="examfieldgrey" size="8" measurement="v_rar" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td><input type="text"  tabindex="80" maxlength="8" class="examfieldgrey" size="8" measurement="v_ls" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="81" maxlength="8" class="examfieldgrey" size="8" measurement="v_lc" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="82" maxlength="8" class="examfieldgrey" size="8" measurement="v_lx" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="83" maxlength="8" class="examfieldgrey" size="8" measurement="v_lar" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td colspan="2"></td>
    		</tr>
    		<tr>
    			<td>M (dist)
    				<span>&nbsp;&nbsp;</span>
    				<a href="javascript:void(0)" tabindex="67" onclick="paste_mdist();return false;">[paste]</a>
    			</td>
    			<td><input type="text"  tabindex="84" maxlength="8" class="examfieldgrey" size="8" measurement="v_rds" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="85" maxlength="8" class="examfieldgrey" size="8" measurement="v_rdc" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="86" maxlength="8" class="examfieldgrey" size="8" measurement="v_rdx" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="87" maxlength="8" class="examfieldgrey" size="8" measurement="v_rdv" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td><input type="text"  tabindex="88" maxlength="8" class="examfieldgrey" size="8" measurement="v_lds" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="89" maxlength="8" class="examfieldgrey" size="8" measurement="v_ldc" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="90" maxlength="8" class="examfieldgrey" size="8" measurement="v_ldx" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="91" maxlength="8" class="examfieldgrey" size="8" measurement="v_ldv" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td>Dist</td>
    			<td><input type="text"  tabindex="92" maxlength="8" class="examfieldgrey" size="8" measurement="v_dv" onfocus="whiteField(this);"/></td>
    		</tr>
    		<tr>
    			<td>M (near)
    				<span>&nbsp;&nbsp;</span>
    				<a href="javascript:void(0)" tabindex="67" onclick="paste_mnear();return false;">[paste]</a>
    			</td>
    			<td><input type="text"  tabindex="93" maxlength="8" class="examfieldgrey" size="8" measurement="v_rns" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="94" maxlength="8" class="examfieldgrey" size="8" measurement="v_rnc" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="95" maxlength="8" class="examfieldgrey" size="8" measurement="v_rnx" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="96" maxlength="8" class="examfieldgrey" size="8" measurement="v_rnv" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td><input type="text"  tabindex="97" maxlength="8" class="examfieldgrey" size="8" measurement="v_lns" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="98" maxlength="8" class="examfieldgrey" size="8" measurement="v_lnc" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="99" maxlength="8" class="examfieldgrey" size="8" measurement="v_lnx" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="100" maxlength="8" class="examfieldgrey" size="8" measurement="v_lnv" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td>Near</td>
    			<td><input type="text"  tabindex="101" maxlength="8" class="examfieldgrey" size="8" measurement="v_nv" onfocus="whiteField(this);"/></td>
    		</tr>
    		<tr>
    			<td>Cyclo
    				<span>&nbsp;&nbsp;</span>
    				<a href="javascript:void(0)" tabindex="67" onclick="paste_Cyclo();return false;">[paste]</a>
    			</td>
    			<td><input type="text"  tabindex="102" maxlength="8" class="examfieldgrey" size="8" measurement="v_rcs" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="103" maxlength="8" class="examfieldgrey" size="8" measurement="v_rcc" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="104" maxlength="8" class="examfieldgrey" size="8" measurement="v_rcx" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="105" maxlength="8" class="examfieldgrey" size="8" measurement="v_rcv" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td><input type="text"  tabindex="106" maxlength="8" class="examfieldgrey" size="8" measurement="v_lcs" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="107" maxlength="8" class="examfieldgrey" size="8" measurement="v_lcc" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="108" maxlength="8" class="examfieldgrey" size="8" measurement="v_lcx" onfocus="whiteField(this);"/></td>
    			<td><input type="text"  tabindex="109" maxlength="8" class="examfieldgrey" size="8" measurement="v_lcv" onfocus="whiteField(this);"/></td>
				<td></td>
    			<td colspan="2"></td>
    		</tr>
    	</table>
    </div>
</div>
</td>
</tr>
<%}%>
<% if (!refractive) {%>
<tr>
<td>
<div class="slidey">
	 <div class="title">
		<a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="110" href="javascript:void(0)" id="a_24" onclick="togglediv(this);">Refractive:</a>
		
	</div>
	<div id="s_24" class="slideblock">       	
		<table class="exam" width="100%">
			<tr>
				<td width="8%"></td>
				<td width="8%">OD</td>
				<td width="8%"></td>
				<td width="8%">OS</td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
			</tr>
			<tr>
				<td class="label">Dominance</td>				
				<td><input type="text" tabindex="111" maxlength="10" size="10" measurement="ref_rdom" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td></td>
				<td><input type="text" tabindex="111" maxlength="10" size="10" measurement="ref_ldom" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="label">Pupil dim</td>
				<td><input type="text" tabindex="111" maxlength="10" size="10" measurement="ref_rpdim" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td></td>
				<td><input type="text" tabindex="112" maxlength="10" size="10" measurement="ref_lpdim" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="label">Kappa</td>
				<td><input type="text" tabindex="113" maxlength="10" size="10" measurement="ref_rkappa" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td></td>
				<td><input type="text" tabindex="114" maxlength="10" size="10" measurement="ref_lkappa" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="label">W2W</td>
				<td><input type="text" tabindex="115" maxlength="10" size="10" measurement="ref_rw2w" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td></td>
				<td><input type="text" tabindex="116" maxlength="10" size="10" measurement="ref_lw2w" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
</div>
</td>
</tr>
<%}%>
<% if (!iop) {%>
<tr>
<td>
<div class="slidey">
	<div class="title">
		<a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" href="javascript:void(0)" tabindex="117" id="a_25" onclick="togglediv(this);">IOP:</a>

	</div>
	<div id="s_25" class="slideblock">
	<table class="exam" width="100%">
		<tr>
				<td width="8%"></td>
				<td width="8%">OD</td>
				<td width="8%"></td>
				<td width="8%">OS</td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
			</tr>
			<tr>
				<td width="8%" nowrap="nowrap" class="label">NCT(<span id="nct_ts"></span>)</td>
				<td width="8%"><input type="text" tabindex="118" maxlength="10" size="10" measurement="iop_rn" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td width="8%"></td>
				<td width="8%"><input type="text" tabindex="119" maxlength="10" size="10" measurement="iop_ln" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
			</tr>
			<tr>
				<td width="8%" class="label">Applanation(<span id="applanation_ts"></span>)</td>
				<td width="8%"><input type="text" tabindex="121" maxlength="10" size="10" measurement="iop_ra" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td width="8%"></td>
				<td width="8%"><input type="text" tabindex="122" maxlength="10" size="10" measurement="iop_la" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
			</tr>
			<tr>
				<td width="8%" class="label">CCT</td>
				<td width="8%"><input type="text" tabindex="124" maxlength="10" size="10" measurement="cct_r" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td width="8%"></td>
				<td width="8%"><input type="text" tabindex="125" maxlength="10" size="10" measurement="cct_l" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
				<td width="8%"></td>
			</tr>
	</table>
	</div>
</div>
</td>
</tr>
<%}%>
<% if (!other_exam) {%>
<tr>
<td>
<div class="slidey">
	<div class="title">
		<a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="126" href="javascript:void(0)" id="a_26" onclick="togglediv(this);">Other Exam:</a>

	</div>
	<div id="s_26" class="slideblock">
	<table class="exam" width="100%">
		<tr>
			<td width="8%"></td>
			<td width="24%" colspan="3">OD</td>
			<td width="24%" colspan="3">OS</td>
			<td width="8%"></td>
			<td width="32%" colspan="4">OU</td>
			<td width="8%"></td>
		</tr>
		<tr>
			<td class="label">Colour vision</td>
			<td colspan="3"><input type="text" tabindex="127" maxlength="50" size="30" measurement="o_rcolour" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td colspan="3"><input type="text" tabindex="128" maxlength="50" size="30" measurement="o_lcolour" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td></td>
			<td colspan="4">Maddox &nbsp; &nbsp;<input type="text" tabindex="129" maxlength="50" size="26" measurement="o_mad" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td></td>
		</tr>
		<tr>
			<td class="label">Pupil</td>
			<td colspan="3"><input type="text" tabindex="130" maxlength="50" size="30" measurement="o_rpupil" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td colspan="3"><input type="text" tabindex="131" maxlength="50" size="30" measurement="o_lpupil" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td ></td>
			<td colspan="4">Bagolini &nbsp; &nbsp;<input type="text" tabindex="132" maxlength="50" size="26" measurement="o_bag" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td ></td>
		</tr>
		<tr>
			<td class="label">Amsler grid</td>
			<td colspan="3"><input type="text" tabindex="133" maxlength="50" size="30" maxlength="50" measurement="o_ramsler" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td colspan="3"><input type="text" tabindex="131" maxlength="50" size="30" measurement="o_lamsler" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td ></td>
			<td colspan="4">W4D(dist)&nbsp;<input type="text" tabindex="135" maxlength="50" size="26" maxlength="50" measurement="o_w4dd" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td ></td>
		</tr>
		<tr>
			<td class="label">PAM</td>
			<td colspan="3"><input type="text" tabindex="136" maxlength="50" size="30" measurement="o_rpam" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td colspan="3"><input type="text" tabindex="137" maxlength="50" size="30" measurement="o_lpam" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td ></td>
			<td colspan="4">W4D(near)<input type="text" tabindex="138" maxlength="50" size="26" measurement="o_w4dn" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td ></td>
		</tr>
		<tr>
			<td class="label">Confrontation</td>
			<td colspan="3"><input type="text" tabindex="139" maxlength="50" size="30" measurement="o_rconf" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td colspan="3"><input type="text" tabindex="140" maxlength="50" size="30" measurement="o_lconf" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
			<td ></td>
			<td colspan="4"></td>
			<td ></td>
		</tr>
	</table>
	</div>
</div>
</td>
</tr>
<%}%>
<% if (!duction) {%>
<tr>
<td>
	<div class="slidey">
		 <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="141" href="javascript:void(0)" id="a_27" onclick="togglediv(this);">Duction/Diplopia:</a>
			
        </div>
        <div id="s_27" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
			<td width="8%"></td>
        	<td colspan="2" width="25%">OD
        		<a href="javascript:void(0)" tabindex="142" onclick="setDuc_od();return false;">[normal]</a>
            	<a href="javascript:void(0)" tabindex="143" onclick="clearDuc_od();return false;">[clear]</a>
        	</td>
			
        	<td colspan="2" width="25%">OS
        		<a href="javascript:void(0)" tabindex="144" onclick="setDuc_os();return false;">[normal]</a>
            	<a href="javascript:void(0)" tabindex="145" onclick="clearDuc_os();return false;">[clear]</a>
        	</td>
			<td ></td>
        	<td colspan="2" width="25%">OU
        		<a href="javascript:void(0)" tabindex="146" onclick="setDuc_ou();return false;">[normal]</a>
            	<a href="javascript:void(0)" tabindex="147" onclick="clearDuc_ou();return false;">[clear]</a>
        	</td>
        	</tr>
        	<tr>
				<td></td>
        		<td><input type="text" size="14" maxlength="10" tabindex="148" measurement="duc_rur" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" size="14" maxlength="10" tabindex="149" measurement="duc_rul" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				
        		<td><input type="text" size="14" maxlength="10" tabindex="150" measurement="duc_lur" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" size="14" maxlength="10" tabindex="151" measurement="duc_lul" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
				<td></td>
        		<td><input type="text" size="14" maxlength="10" tabindex="152" measurement="dip_ur" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" size="14" maxlength="10" tabindex="153" measurement="dip_u" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>       	
        	</tr>
        	<tr>
				<td></td>
        		<td><input type="text" size="14" tabindex="154" maxlength="10" measurement="duc_rr" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" size="14" tabindex="155" maxlength="10" measurement="duc_rl" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		
        		<td><input type="text" size="14" tabindex="156" maxlength="10" measurement="duc_lr" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" size="14" tabindex="157" maxlength="10" measurement="duc_ll" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td></td>
        		<td><input type="text" size="14" tabindex="158" maxlength="10" measurement="dip_r" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" size="14" tabindex="159" maxlength="10" measurement="dip_p" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>       	
        	</tr>
        	<tr>
				<td></td>
        		<td><input type="text" size="14" tabindex="160" maxlength="10" measurement="duc_rdr" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" size="14" tabindex="161" maxlength="10" measurement="duc_rdl" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		
        		<td><input type="text" size="14" tabindex="162" maxlength="10" measurement="duc_ldr" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" size="14" tabindex="163" maxlength="10" measurement="duc_ldl" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td></td>
        		<td><input type="text" size="14" tabindex="164" maxlength="10" measurement="dip_dr" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" size="14" tabindex="165" maxlength="10" measurement="dip_d" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>       	
        	</tr>
        </table>
        </div>
	</div>
</td>
</tr>
<%}%>

<% if (!deviation_measurement) {%>
<tr>
<td>

	<div class="slidey">
		 <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="166" href="javascript:void(0)" id="a_28" onclick="togglediv(this);">Deviation Measurement:</a>
			
        </div>
        <div id="s_28" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        	<td rowspan="3" align="center">
        		<a href="javascript:void(0)" tabindex="167" onclick="setDeviation();return false;">[normal]</a>
            	<a href="javascript:void(0)" tabindex="168" onclick="clearDeviation();return false;">[clear]</a>
        	</td>
        	<td></td>
        	<td><input type="text" tabindex="169" maxlength="5" measurement="dev_u" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td></td>
        	<td colspan="2">Near &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="text" tabindex="170" maxlength="5" measurement="dev_near" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td><input type="text" tabindex="171" maxlength="5" measurement="dev_r" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="172" maxlength="5" measurement="dev_p" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="173" maxlength="5" measurement="dev_l" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td colspan="2">+3.00D &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="text" tabindex="174" maxlength="5" measurement="dev_plus3" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td><input type="text" tabindex="175" maxlength="5" measurement="dev_rt" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="176" maxlength="5" measurement="dev_d" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="177" maxlength="5" measurement="dev_lt" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td colspan="2">Far Distance <input type="text" tabindex="178" maxlength="5" measurement="dev_far" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        </table>
        </div>
    </div>

</td>
</tr>
<%}%>

<% if (!external_orbit) {%>
<tr>
<td>

	<div class="slidey">
		 <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="179" href="javascript:void(0)" id="a_29" onclick="togglediv(this);">External/Orbit:</a>
			
        </div>
        <div id="s_29" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        		<td width="8%"></td>
        		<td width="46%">OD
        			<a href="javascript:void(0)" tabindex="180" onclick="setExternal_od();return false;">[normal]</a>
            		<a href="javascript:void(0)" tabindex="181" onclick="clearExternal_od();return false;">[clear]</a>
        		</td>
        		<td width="46%">OS
        			<a href="javascript:void(0)" tabindex="182" onclick="setExternal_os();return false;">[normal]</a>
            		<a href="javascript:void(0)" tabindex="183" onclick="clearExternal_os();return false;">[clear]</a>
        		</td>
        	</tr>
        	<tr>
        		<td class="label">Face</td>
        		<td><input type="text" tabindex="184" size="50" measurement="ext_rface" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="185" size="50" measurement="ext_lface" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Retropulsion</td>
        		<td><input type="text" tabindex="186" size="50" measurement="ext_rretro" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="187" size="50" measurement="ext_lretro" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Hertel</td>
        		<td><input type="text" tabindex="188" size="50" measurement="ext_rhertel" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="189" size="50" measurement="ext_lhertel" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        </table>
        </div>
    </div>

</td>
</tr>
<%}%>

<% if (!eyelid_nld) {%>
<tr>
<td>

	<div class="slidey">
		 <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="190" href="javascript:void(0)" id="a_30" onclick="togglediv(this);">Eyelid/NLD:</a>
			
        </div>
        <div id="s_30" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        		<td width="8%"></td>
        		<td width="46%">OD
        			<a href="javascript:void(0)" tabindex="191" onclick="setEyelid_od();return false;">[normal]</a>
            		<a href="javascript:void(0)" tabindex="192" onclick="clearEyelid_od();return false;">[clear]</a>
        		</td>
        		<td width="46%">OS
        			<a href="javascript:void(0)" tabindex="193" onclick="setEyelid_os();return false;">[normal]</a>
            		<a href="javascript:void(0)" tabindex="194" onclick="clearEyelid_os();return false;">[clear]</a>
        		</td>
        	</tr>
        	<tr>
        		<td class="label">Upper lid</td>
        		<td><input type="text" tabindex="195" size="50" measurement="ext_rul" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="196" size="50" measurement="ext_lul" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Lower lid</td>
        		<td><input type="text" tabindex="197" size="50" measurement="ext_rll" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="198" size="50" measurement="ext_lll" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Lac lake</td>
        		<td><input type="text" tabindex="199" size="50" measurement="ext_rlake" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="200" size="50" measurement="ext_llake" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Lac Irrig</td>
        		<td><input type="text" tabindex="201" size="50" measurement="ext_rirrig" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="202" size="50" measurement="ext_lirrig" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Punctum</td>
        		<td><input type="text" tabindex="203" size="50" measurement="ext_rpunc" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="204" size="50" measurement="ext_lpunc" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">NLD</td>
        		<td><input type="text" tabindex="205" size="50" measurement="ext_rnld" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="206" size="50" measurement="ext_lnld" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Dye Disapp</td>
        		<td><input type="text" tabindex="207" size="50" measurement="ext_rdye" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="208" size="50" measurement="ext_ldye" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        </table>
        </div>
	</div>

</td>
</tr>
<%}%>

<% if (!eyelid_measurement) {%>
<tr>
<td>

	<div class="slidey">
		 <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="209" href="javascript:void(0)" id="a_31" onclick="togglediv(this);">Eyelid Measurement:</a>
			
        </div>
        <div id="s_31" class="slideblock">
        <table class="exam" width="100%">
     		<tr>
     			<td width="8%"></td>
     			<td colspan="2" align="center" width="46%">OD</td>
     			<td colspan="2" align="center" width="46%">OS</td>
     		</tr>
     		<tr>
     			<td></td>
     			<td>MRD</td>
     			<td>ISS</td>
     			<td>MRD</td>
     			<td>ISS</td>
     		</tr>
     		<tr>
     			<td class="label">Lid margins</td>
     			<td colspan="2"><input type="text" tabindex="210" maxlength="50" size="23" measurement="lid_rmrd" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>&nbsp&nbsp
								<input type="text" tabindex="211" maxlength="50" size="24" measurement="lid_riss" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
				</td>
     			<td colspan="2"><input type="text" tabindex="212" maxlength="50" size="23" measurement="lid_lmrd" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>&nbsp&nbsp
								<input type="text" tabindex="213" maxlength="50" size="24" measurement="lid_liss" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
				</td>
     		</tr>
     		<tr>
     			<td class="label">Levator Fn</td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="214" maxlength="50" measurement="lid_rlev" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="215" maxlength="50" measurement="lid_llev" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     		</tr>
     		<tr>
     			<td class="label">Lag</td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="216" maxlength="50" measurement="lid_rlag" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="217" maxlength="50" measurement="lid_llag" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     		</tr>
     		<tr>
     			<td class="label">Blink</td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="218" maxlength="50" measurement="lid_rblink" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="219" maxlength="50" measurement="lid_lblink" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     		</tr>
     		<tr>
     			<td class="label">CN VII</td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="220" maxlength="50" measurement="lid_rcn7" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="221" maxlength="50" measurement="lid_lcn7" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     		</tr>
     		<tr>
     			<td class="label">Bells</td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="222" maxlength="50" measurement="lid_rbell" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="223" maxlength="50" measurement="lid_lbell" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     		</tr>
     		<tr>
     			<td class="label">Schirmer</td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="224" maxlength="50" measurement="lid_rschirm" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     			<td colspan="2" align="center"><input type="text" size="50" tabindex="225" maxlength="50" measurement="lid_lschirm" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
     		</tr>		
     	</table>
     	</div>
   	</div>

</td>
</tr>
<%}%>


<% if (!anterior_segment) {%>
<tr>
<td>

	<div class="slidey">
		 <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="226" href="javascript:void(0)" id="a_32" onclick="togglediv(this);">Anterior Segment:</a>
			
        </div>
        <div id="s_32" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        		<td width="8%"></td>
        		<td width="46%">OD
        			<a href="javascript:void(0)" tabindex="227" onclick="setAnterior_od();return false;">[normal]</a>
            		<a href="javascript:void(0)" tabindex="228" onclick="clearAnterior_od();return false;">[clear]</a>
        		</td>
        		<td width="46%">OS
        			<a href="javascript:void(0)" tabindex="229" onclick="setAnterior_os();return false;">[normal]</a>
            		<a href="javascript:void(0)" tabindex="230" onclick="clearAnterior_os();return false;">[clear]</a>
        		</td>
        	</tr>
        	<tr>
        		<td class="label">Cornea</td>
        		<td><input type="text" tabindex="231" size="50" measurement="a_rk" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="232" size="50" measurement="a_lk" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Conj/Sclera</td>
        		<td><input type="text" tabindex="233" size="50" measurement="a_rconj" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="234" size="50" measurement="a_lconj" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">AC</td>
        		<td><input type="text" tabindex="235" size="50" measurement="a_rac" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="236" size="50" measurement="a_lac" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
				<td class="label">Angle</td>
				<td>
        			<table class="exam" style="width:100%">
        				<tr>
        					<td width="100%" align="center"><input type="text" size="15" tabindex="237" measurement="a_rangle_1" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        				</tr>
        				<tr>
        					<td style="text-align:center">
	        					<input size="15" type="text" tabindex="237" measurement="a_rangle_2" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
        						<input size="15" type="text" tabindex="237" measurement="a_rangle_3" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
								<input size="15" type="text" tabindex="237" measurement="a_rangle_4" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
        					</td>
        				</tr>
        				<tr>
        					<td><input type="text" size="15" tabindex="237" measurement="a_rangle_5" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        				</tr>
        			</table>
        		</td>
        		
        		<td>
        			<table class="exam" style="width:100%">
        				<tr>
        					<td width="100%" align="center"><input size="15" type="text" tabindex="237" measurement="a_langle_1" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        				</tr>

        				<tr>
        					<td style="text-align:center">
        						<input size="15" type="text" tabindex="237" measurement="a_langle_2" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
								<input size="15" type="text" tabindex="237" measurement="a_langle_3" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
								<input size="15" type="text" tabindex="237" measurement="a_langle_4" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
        					</td>
        				</tr>

        				<tr>
        					<td><input type="text" size="15" tabindex="237" measurement="a_langle_5" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        				</tr>
        			</table>
        		</td>
        	</tr>
        	<tr>
        		<td class="label">Iris</td>
        		<td><input type="text" tabindex="239" size="50" measurement="a_riris" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="240" size="50" measurement="a_liris" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Lens</td>
        		<td><input type="text" tabindex="241" size="50" measurement="a_rlens" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="242" size="50" measurement="a_llens" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        </table>
        </div>
    </div>
</td>
</tr>
<%}%>

<% if (!posterior_segment) {%>
<tr>
<td>
	<div class="slidey">
		 <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="243" href="javascript:void(0)" id="a_33" onclick="togglediv(this);">Posterior Segment:</a>
			
        </div>
        <div id="s_33" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        		<td width="8%"></td>
        		<td width="46%">OD
        			<a href="javascript:void(0)" tabindex="244" onclick="setPosterior_od();return false;">[normal]</a>
            		<a href="javascript:void(0)" tabindex="245" onclick="clearPosterior_od();return false;">[clear]</a>
        		</td>
        		<td width="46%">OS
        			<a href="javascript:void(0)" tabindex="246" onclick="setPosterior_os();return false;">[normal]</a>
            		<a href="javascript:void(0)" tabindex="247" onclick="clearPosterior_os();return false;">[clear]</a>
        		</td>
        	</tr>
        	<tr>
        		<td class="label">Disc</td>
        		<td><input type="text" tabindex="248" size="50" measurement="p_rdisc" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="249" size="50" measurement="p_ldisc" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">C/D ratio</td>
        		<td><input type="text" tabindex="250" size="50" measurement="p_rcd" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="251" size="50" measurement="p_lcd" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Macula</td>
        		<td><input type="text" tabindex="252" size="50" measurement="p_rmac" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="253" size="50" measurement="p_lmac" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Retina</td>
        		<td><input type="text" tabindex="254" size="50" measurement="p_rret" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="255" size="50" measurement="p_lret" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        		<td class="label">Vitreous</td>
        		<td><input type="text" tabindex="256" size="50" measurement="p_rvit" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        		<td><input type="text" tabindex="257" size="50" measurement="p_lvit" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        </table>
        </div>
     </div>
</td>
</tr>
<%}%>

<%}else{%>
<tr>
<td>
<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" href="javascript:void(0)" tabindex="31" id="a_21" onclick="togglediv(this);">Vision Pretest:</a>
        </div>
        <div id="s_21" class="slideblock">
            <table class="exam" width="100%">
            <tr>
            <td colspan="3">OD</td>
            <td ></td>
            <td colspan="3">OS</td>
            </tr>

			<tr>
            <td >Sph</td>
            <td >Cyl</td>
            <td >Axis</td>
            <td></td>
            <td>Sph</td>
            <td >Cyl</td>
            <td >Axis</td>
            </tr>
			<tr>
            <td><input type="text" tabindex="32"  class="examfieldgrey" size="7" measurement="od_ar_sph" onfocus="whiteField(this);"/></td>
            <td><input type="text" tabindex="33"  class="examfieldgrey" size="7" measurement="od_ar_cyl" onfocus="whiteField(this);"/></td>
            <td><input type="text" tabindex="34"  class="examfieldgrey" size="7" measurement="od_ar_axis" onfocus="whiteField(this);"/></td>


            <td >AR</td>
            <td ><input type="text" tabindex="35" class="examfieldgrey" size="7" measurement="os_ar_sph" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="36" class="examfieldgrey" size="7" measurement="os_ar_cyl" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="37" class="examfieldgrey" size="7" measurement="os_ar_axis" onfocus="whiteField(this);"/></td>

			</tr>
			<tr>
            <td >K1</td>
            <td >K2</td>
            <td >K2-Axis</td>
            <td ></td>
            <td >K1</td>
            <td >K2</td>
            <td >K2-Axis</td>
            </tr>
			<tr>
            <td ><input type="text"  tabindex="38"  class="examfieldgrey" size="7" measurement="od_k1" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="39"  class="examfieldgrey" size="7" measurement="od_k2" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="40"  class="examfieldgrey" size="7"  measurement="od_k2_axis" onfocus="whiteField(this);"/></td>


            <td >K</td>
            <td ><input type="text"  tabindex="41"  class="examfieldgrey" size="7" measurement="os_k1" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="42"  class="examfieldgrey" size="7" measurement="os_k2" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="43"  class="examfieldgrey" size="7" measurement="os_k2_axis" onfocus="whiteField(this);"/></td>

			</tr>
			<tr>
            <td >sc</td>
            <td >cc</td>
            <td >ph</td>
            <td ></td>
            <td >sc</td>
            <td >cc</td>
            <td >ph</td>
            </tr>
            <tr>
            <td ><input type="text"  tabindex="44"  class="examfieldgrey" size="7" measurement="od_sc_distance" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="45"  class="examfieldgrey" size="7" measurement="od_cc_distance" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="46"  class="examfieldgrey" size="7" measurement="od_ph_distance" onfocus="whiteField(this);"/></td>
            <td >Distance</td>
            <td ><input type="text"  tabindex="47"  class="examfieldgrey" size="7" measurement="os_sc_distance" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="48"  class="examfieldgrey" size="7" measurement="os_cc_distance" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  tabindex="49"  class="examfieldgrey" size="7" measurement="os_ph_distance" onfocus="whiteField(this);"/></td>

            </tr>

            <tr>
            <td><input type="text"  tabindex="50"  class="examfieldgrey" size="7" measurement="od_sc_near" onfocus="whiteField(this);"/></td>
            <td><input type="text"  tabindex="51"  class="examfieldgrey" size="7" measurement="od_cc_near" onfocus="whiteField(this);"/></td>
            <td></td>
           <td>Near</td>
            <td><input type="text"  tabindex="52"  class="examfieldgrey" size="7" measurement="os_sc_near" onfocus="whiteField(this);"/></td>
            <td><input type="text"  tabindex="53"  class="examfieldgrey" size="7" measurement="os_cc_near" onfocus="whiteField(this);"/></td>
            <td></td>
            </tr>




            </table>
        </div>
</div>
</td>
</tr>


<tr>
<td>
<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="60" href="javascript:void(0)" id="a_22" onclick="togglediv(this);">Vision Manifest:</a>
            <span>&nbsp;&nbsp;</span>

            <a href="javascript:void(0)" tabindex="61" onclick="copyAR();"> [copy AR] </a>
            <span>&nbsp;&nbsp;</span>
            <a href="javascript:void(0)" tabindex="62" onclick="copySpecs();"> [copy Specs] </a>

        </div>
        <div id="s_22" class="slideblock">
            <table class="exam" width="100%">
            <tr>
            <td colspan="6">OD</td>
            <td width="10%"></td>
            <td colspan="6">OS</td>
            </tr>
            <tr>
            <td width="6%">Sph</td>
            <td width="6%">Cyl</td>
            <td width="5%">Axis</td>
            <td width="6%">Add</td>
            <td width="1%"></td>
             <td width="9%">VA</td>
             <td width="9%">N</td>
            <td width="14%"></td>
            <td width="6%">Sph</td>
            <td width="6%">Cyl</td>
            <td width="5%">Axis</td>
            <td width="6%">Add</td>
            <td width="1%"></td>
            <td width="9%">VA</td>
            <td width="9%">N</td>
            </tr>
            <tr>
            <td><input type="text" size="5" tabindex="63" measurement="od_manifest_refraction_sph" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text" size="5" tabindex="64" measurement="od_manifest_refraction_cyl" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td><input type="text" size="5" tabindex="65" measurement="od_manifest_refraction_axis" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text"  size="5" tabindex="66" measurement="od_manifest_refraction_add" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ></td>
            <td><input type="text"  size="5" tabindex="67" measurement="od_manifest_distance" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td><input type="text" size="5" tabindex="68" measurement="od_manifest_near" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td >Manifest</td>
            <td><input type="text" size="5" tabindex="69" measurement="os_manifest_refraction_sph" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text" size="5" tabindex="70" measurement="os_manifest_refraction_cyl" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td><input type="text" size="5" tabindex="71" measurement="os_manifest_refraction_axis" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text" size="5" tabindex="72" measurement="os_manifest_refraction_add" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ></td>
            <td><input type="text" size="5" tabindex="73" measurement="os_manifest_distance" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text" size="5" tabindex="74" measurement="os_manifest_near" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>


            </tr>

            <tr>
            <td ><input type="text" size="5" tabindex="75" measurement="od_cycloplegic_refraction_sph" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text" size="5" tabindex="76" measurement="od_cycloplegic_refraction_cyl" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text" size="5" tabindex="77" measurement="od_cycloplegic_refraction_axis" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td><input type="text" size="5" tabindex="78" measurement="od_cycloplegic_refraction_add" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ></td>
            <td ><input type="text" size="5" tabindex="79" measurement="od_cycloplegic_distance" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td></td>
            <td >Cycloplegic</td>
            <td ><input type="text" size="5" tabindex="80" measurement="os_cycloplegic_refraction_sph" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text" size="5" tabindex="81" measurement="os_cycloplegic_refraction_cyl" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text" size="5" tabindex="82" measurement="os_cycloplegic_refraction_axis" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ><input type="text" size="5" tabindex="83" measurement="os_cycloplegic_refraction_add" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td ></td>
            <td ><input type="text" size="5" tabindex="84" measurement="os_cycloplegic_distance" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
            <td></td>
            </tr>

            </table>
        </div>
</div>
</td>
</tr>



<tr>
<td>
	<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" href="javascript:void(0)" tabindex="90" id="a_23" onclick="togglediv(this);">IOP:</a>

        </div>
        <div id="s_23" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        	<td width="17%"></td>
        	<td width="15%">OD</td>
        	<td width="1%"></td>
        	<td width="34%"></td>
        	<td width="1%"></td>
        	<td width="15%">OS</td>
        	<td width="17%"></td>
        	</tr>
        	<tr>
        	<td ></td>
        	<td ><input type="text" tabindex="91" measurement="od_iop_nct" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td ></td>
        	<td width="34%" nowrap="nowrap">NCT(<span id="nct_ts"></span>)</td>
        	<td ></td>
        	<td ><input type="text" tabindex="93" measurement="os_iop_nct" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td ></td>
        	</tr>
        	<tr>
        	<td ></td>
        	<td ><input type="text" tabindex="94" measurement="od_iop_applanation" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td ></td>
        	<td width="34%">Applanation(<span id="applanation_ts"></span>)</td>
        	<td ></td>
        	<td ><input type="text" tabindex="95" measurement="os_iop_applanation" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td></td>
        	</tr>
        	<tr>
        	<td></td>
        	<td><input type="text" tabindex="96" measurement="od_cct" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td></td>
        	<td width="34%">CCT</td>
        	<td ></td>
        	<td><input type="text" tabindex="97" measurement="os_cct" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td ></td>
        	</tr>
        </table>
        </div>
    </div>
</td>
</tr>



<tr>
<td>
	<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="100" href="javascript:void(0)" id="a_24" onclick="togglediv(this);">Special Exam:</a>

        </div>
        <div id="s_24" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        	<td width="33%">OD</td>
        	<td width="34%"></td>
        	<td width="33%">OS</td>
        	</tr>
        	<tr>
        	<td width="33%"><input type="text" tabindex="101" measurement="od_color_vision" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td width="34%">color vision</td>
        	<td width="33%"><input type="text" tabindex="102" measurement="os_color_vision" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td width="33%"><input type="text" tabindex="103" measurement="od_pupil" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td width="34%">Pupil</td>
        	<td width="33%"><input type="text" tabindex="104" measurement="os_pupil" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td width="33%"><input type="text" tabindex="105" measurement="od_amsler_grid" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td width="34%">Amsler Grid</td>
        	<td width="33%"><input type="text" tabindex="106" measurement="os_amsler_grid" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td width="33%"><input type="text" tabindex="107" measurement="od_pam" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td width="34%">PAM</td>
        	<td width="33%"><input type="text" tabindex="108" measurement="os_pam" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td width="33%">
        	<input type="text" tabindex="109" measurement="od_confrontation" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>

        	<td width="34%">Confrontation</td>
        	<td width="33%">
        	<input type="text" tabindex="110" measurement="os_confrontation" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>

        	<tr>
        </table>
        </div>
    </div>
</td>
</tr>

<tr>
<td>
	<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" href="javascript:void(0)" tabindex="120" id="a_25" onclick="togglediv(this);">
            EOM/Stereo:
            </a>

        </div>
        <div id="s_25" class="slideblock">
        <textarea measurement="EOM" tabindex="121" onchange="syncFields(this)" cols="100" rows="4" class="examfieldgrey" onfocus="whiteField(this);"></textarea>

        </div>
    </div>
</td>
</tr>



<tr>
<td>
	<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="125" href="javascript:void(0)" id="a_26" onclick="togglediv(this);">Anterior Segment:</a>
            <span>&nbsp;&nbsp;</span>

            <a href="javascript:void(0)" tabindex="126" onclick="setAnterior();return false;">[normal]</a>
            <a href="javascript:void(0)" tabindex="127" onclick="clearAnterior();return false;">[clear]</a>

        </div>
        <div id="s_26" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        	<td>OD</td>
        	<td></td>
        	<td>OS<td>
        	</tr>
        	<tr>
        	<td><input type="text" size="50" tabindex="130" measurement="od_cornea" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Cornea</td>
        	<td><input type="text" size="50" tabindex="131" measurement="os_cornea" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/><td>
        	</tr>
        	<tr>
        	<td><input type="text" size="50" tabindex="132" measurement="od_conjuctiva_sclera" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Conjuctiva/Sclera</td>
        	<td><input type="text" size="50" tabindex="133" measurement="os_conjuctiva_sclera" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/><td>
        	</tr>
        	<tr>
        	<td><input type="text" size="50"  tabindex="134" measurement="od_anterior_chamber" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Anterior Chamber</td>
        	<td><input type="text"  size="50" tabindex="135" measurement="os_anterior_chamber" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/><td>
        	</tr>
        	<!-- start of angles -->
        	<tr>
        		<td>
        			<table class="exam" style="width:100%">
        				<tr>
        					<td width="100%" align="center"><input type="text" size="12" tabindex="136" measurement="od_angle_up" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        				</tr>
        				<tr>
        					<td style="text-align:center">
	        					<input size="12" type="text" tabindex="137" measurement="od_angle_middle0" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
        						<input size="12" type="text" tabindex="138" measurement="od_angle_middle1" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
								<input size="12" type="text" tabindex="139" measurement="od_angle_middle2" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
        					</td>
        				</tr>
        				<tr>
        					<td><input type="text" size="12" tabindex="140" measurement="od_angle_down" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        				</tr>
        			</table>
        		</td>
        		<td>Angle</td>
        		<td>
        			<table class="exam" style="width:100%">
        				<tr>
        					<td width="100%" align="center"><input size="12" type="text" tabindex="141" measurement="os_angle_up" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        				</tr>

        				<tr>
        					<td style="text-align:center">
        						<input size="12" type="text" tabindex="142" measurement="os_angle_middle0" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
								<input size="12" type="text" tabindex="143" measurement="os_angle_middle1" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
								<input size="12" type="text" tabindex="144" measurement="os_angle_middle2" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/>
        					</td>
        				</tr>

        				<tr>
        					<td><input type="text" size="12" tabindex="145" measurement="os_angle_down" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        				</tr>
        			</table>
        		</td>
        	</tr>

        	<!-- end of angles -->
        	<tr>
        	<td><input type="text" size="50" tabindex="146" measurement="od_iris" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Iris</td>
        	<td><input type="text" size="50" tabindex="147" measurement="os_iris" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/><td>
        	</tr>

        	<tr>
        	<td><input type="text" size="50" tabindex="148" measurement="od_lens" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Lens</td>
        	<td><input type="text" size="50" tabindex="149" measurement="os_lens" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/><td>
        	</tr>


        </table>
        </div>
    </div>
</td>
</tr>

<tr>
<td>
	<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="160" href="javascript:void(0)" id="a_27" onclick="togglediv(this);">Posterior Segment:</a>
            <span>&nbsp;&nbsp;</span>

            <a href="javascript:void(0)" tabindex="161" onclick="setPosterior();return false;">[normal]</a>
            <a href="javascript:void(0)" tabindex="162" onclick="clearPosterior();return false;">[clear]</a>

        </div>
        <div id="s_27" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        	<td width="48%" colspan="2">OD</td>
        	<td width="4%"></td>
        	<td width="48%" colspan="2">OS</td>
        	</tr>
        	<tr>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="163" measurement="od_disc" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td width="4%">Disc</td>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="164" measurement="os_disc" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="165" measurement="od_cd_ratio_horizontal" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td width="4%">c/d ratio</td>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="166" measurement="os_cd_ratio_horizontal" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="167" measurement="od_macula" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td width="4%">Macula</td>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="168" measurement="os_macula" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="169" measurement="od_retina" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td width="4%">Retina</td>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="170" measurement="os_retina" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="171" measurement="od_vitreous" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td width="4%">Vitreous</td>
        	<td width="48%" colspan="2"><input type="text" size="50" tabindex="172" measurement="os_vitreous" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        </table>
        </div>
    </div>
</td>
</tr>

<tr>
<td>
	<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="175" href="javascript:void(0)" id="a_28" onclick="togglediv(this);">External:</a>
            <span>&nbsp;&nbsp;</span>
            <a href="javascript:void(0)" tabindex="176" onclick="setExternal();return false;">[normal]</a>
            <a href="javascript:void(0)" tabindex="177" onclick="clearExternal();return false;">[clear]</a>

        </div>
        <div id="s_28" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        	<td>OD</td>
        	<td></td>
        	<td>OS</td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="178" measurement="od_face" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Face</td>
        	<td><input type="text" tabindex="179" measurement="os_face" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="180" measurement="od_upper_lid" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Upper Lid</td>
        	<td><input type="text" tabindex="181" measurement="os_upper_lid" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="182" measurement="od_lower_lid" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>lower Lid</td>
        	<td><input type="text" tabindex="183" measurement="os_lower_lid" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="184" measurement="od_punctum" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Punctum</td>
        	<td><input type="text" tabindex="185" measurement="os_punctum" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="186" measurement="od_lacrimal_lake" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Lacrimal Lake</td>
        	<td><input type="text" tabindex="187" measurement="os_lacrimal_lake" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        </table>
        </div>
    </div>
</td>
</tr>

<tr>
<td>
	<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="190" href="javascript:void(0)" id="a_29" onclick="togglediv(this);">NLD:</a>

        </div>
        <div id="s_29" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        	<td>OD</td>
        	<td></td>
        	<td>OS</td>
        	</tr>
        	<tr>
        	<td>
        	<input type="text" tabindex="191" measurement="od_lacrimal_irrigation" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>

        	<td>Lacrimal Irrigation</td>

        	<td><input type="text" tabindex="192" measurement="os_lacrimal_irrigation"  onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>

        	</tr>
        	<tr>
        	<td><input type="text" tabindex="193" measurement="od_nld" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>NLD</td>
        	<td><input type="text" tabindex="194" measurement="os_nld" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="195" measurement="od_dye_disappearance" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Dye Disappearance</td>
        	<td><input type="text" tabindex="196" measurement="os_dye_disappearance" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	</table>
        </div>
    </div>
</td>
</tr>

<tr>
<td>
	<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="200" href="javascript:void(0)" id="a_30" onclick="togglediv(this);">Eyelid Measurements:</a>

        </div>
        <div id="s_30" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        	<td>OD</td>
        	<td></td>
        	<td>OS</td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="201" measurement="od_mrd" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>MRD</td>
        	<td><input type="text" tabindex="202" measurement="os_mrd" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="203" measurement="od_levator_function" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Levator Function</td>
        	<td><input type="text" tabindex="204" measurement="os_levator_function" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="205" measurement="od_inferior_scleral_show" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Inferior Scleral Show</td>
        	<td><input type="text" tabindex="206" measurement="os_inferior_scleral_show" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="207" measurement="od_cn_vii" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>CN VII</td>
        	<td><input type="text" tabindex="208" measurement="os_cn_vii" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="209" measurement="od_blink" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Blink</td>
        	<td><input type="text" tabindex="210" measurement="os_blink" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="211" measurement="od_bells" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Bells</td>
        	<td><input type="text" tabindex="212" measurement="os_bells" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="213" measurement="od_lagophthalmos" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Lagophthalmos</td>
        	<td><input type="text" tabindex="214" measurement="os_lagophthalmos" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        </table>
        </div>
    </div>
</td>
</tr>
<tr>
<td>
	<div class="slidey">
        <div class="title">
            <a style="font-weight: bold;color:black;text-decoration:none;font-size:12px" tabindex="220" href="javascript:void(0)" id="a_31" onclick="togglediv(this);">Orbit:</a>

        </div>
        <div id="s_31" class="slideblock">
        <table class="exam" width="100%">
        	<tr>
        	<td>OD</td>
        	<td></td>
        	<td>OS</td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="221" measurement="od_hertel" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Hertel</td>
        	<td><input type="text" tabindex="222" measurement="os_hertel" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        	<tr>
        	<td><input type="text" tabindex="223" measurement="od_retropulsion" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	<td>Retropulsion</td>
        	<td><input type="text" tabindex="224" measurement="os_retropulsion" onchange="syncFields(this)" class="examfieldgrey" onfocus="whiteField(this);"/></td>
        	</tr>
        </table>
        </div>
    </div>
</td>
</tr>
<%}%>
</table>
<span style="font-size:10px"><a id="save_measurements" href="#">[Save Measurements]</a></span>
<span style="float:right;font-size:10px">
	<a href="javascript:void(0);" onclick="expandAll();">[expand all sections]</a>&nbsp;
	<a href="javascript:void(0);" onclick="collapseAll();">[collapse all sections]</a>
</span>
