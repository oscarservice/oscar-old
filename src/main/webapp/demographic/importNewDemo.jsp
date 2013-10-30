<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>

<script src="../js/jquery.js"></script>
<script>
	jQuery.noConflict();
</script>
<script>
function onClickImport() 
{
	var file1 = jQuery("[name=file1]").val();
	if(file1==null || file1=="")
	{
		alert("Please select the file to import");
		return;
	}
	jQuery("[name=importDemoForm]").submit();
	jQuery("#iframe_import_demo").show();
}
 
</script>

<style>
.div_import_new_demo
{
	/*margin-top: 10px; 
	margin-bottom: 10px;*/
}

#iframe_import_demo
{
	display: none;
}
#div_importing
{
	background-color: #E2DA7F;
	border: 1px solid #BFB75D;
	/*background-color: yellow;*/
	font-weight: bold;
	font-size: 14px;	
}
</style>

</head>

<body style="margin: 0 !important; ">

<div class="div_import_new_demo">
	<html:form action="/demographic/importNewDemoAction.do" method="POST" enctype="multipart/form-data" >
		<div>
			<bean:message key="demographic.importNew" /> : <input type="file" name="file1" value="">&nbsp;<input
				type="button" name="btn_import" value="Import" onclick="onClickImport();">
		</div>
	</html:form> 
	<!-- <iframe id="iframe_import_demo" width="550px" height="23px" src="../demographic/importDemoResult.jsp?actionVal=show_importing" frameborder="0" name="iframe_import_demo" scrolling="no">
		<div id="div_importing">Importing Patient.. Please Wait..</div>
	</iframe> -->
</div>

</body>
</html>
