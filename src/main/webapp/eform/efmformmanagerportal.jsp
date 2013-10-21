<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.oscar.eformportal.ws.service.EformInfo"%>
<%@page import="org.apache.cxf.jaxws.JaxWsProxyFactoryBean"%>
<%@page import="com.oscar.eformportal.ws.service.MyFile"%>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.oscar.eformportal.ws.service.IOscarService"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%	
	List<EformInfo> infos = (List)request.getAttribute("eforms");
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>eFormPortal
<link rel="stylesheet" href="<%=request.getContextPath()%>/share/css/OscarStandardLayout.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/share/css/eformStyle.css">
</title>

  <style type="text/css">
	  tr.odd
        {
            background-color: #F2F2F2;
        }
        tr.even
        {
            background-color: white;
        }
  </style>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
function newWindow(url, id) {
	Popup = window.open(url,id,'toolbar=no,location=no,status=yes,menubar=no, scrollbars=yes,resizable=yes,width=700,height=600,left=200,top=0');
} 

function setInfo(){
	var select1 = document.getElementById("select1");
	document.getElementById("hid1").value=select1.options[select1.selectedIndex].value;
	document.getElementById("form1").submit();
}
function _key(){
	 if(event.keyCode==13) {
		setInfo(); 
	}
}
</script>
<script type="text/javascript" language="javascript">
$(function() {
          
	
            jQuery.fn.alternateRowColors = function() {                     
                $('tbody tr:odd', this).removeClass('even').addClass('odd'); 
                $('tbody tr:even', this).removeClass('odd').addClass('even');
                return this;
            };
            $('#myTable').each(function() {
                var $table = $(this);                       
                      $table.alternateRowColors($table);     
                $('th', $table).each(function(column) {
                    var findSortKey;
                    if ($(this).is('.fname')) {       
                        findSortKey = function($cell) {
                            return $cell.find('sort-key').text().toUpperCase() + '' + $cell.text().toUpperCase();
                        };
                    } else if ($(this).is('.datetime')) {          
                        findSortKey = function($cell) {

                            return Date.parse($cell.text().replace(/-/g,'/'));
                        };
                    }

                    if (findSortKey) {
                        $(this).addClass('clickable').hover(function() { $(this).addClass('hover'); }, function() { $(this).removeClass('hover'); }).click(function() {
                     
                            var newDirection = 1;
                            if ($(this).is('.sorted-asc')) {
                                newDirection = -1;
                            }
                            var rows = $table.find('tbody>tr').get(); 
                            $.each(rows, function(index, row) {
                                row.sortKey = findSortKey($(row).children('td').eq(column));
                            });
                            rows.sort(function(a, b) {
                                if (a.sortKey < b.sortKey) return -newDirection;
                                if (a.sortKey > b.sortKey) return newDirection;
                                return 0;
                            });
                            $.each(rows, function(index, row) {
                                $table.children('tbody').append(row);
                                row.sortKey = null;
                            });

                            $table.find('th').removeClass('sorted-asc').removeClass('sorted-desc');
                            var $sortHead = $table.find('th').filter(':nth-child(' + (column + 1) + ')');
                      
                            if (newDirection == 1) {
                                $sortHead.addClass('sorted-asc');
                            } else {
                                $sortHead.addClass('sorted-desc');
                            }
							$table.alternateRowColors($table);
							 $table.find('td').removeClass('sorted').filter(':nth-child(' + (column + 1) + ')').addClass('sorted');
                            $table.trigger('repaginate');
                        
                        });
                    }
                });
            });
    
		});
</script>
</head>
<body onkeydown="_key()">
<table border="0" cellspacing="0" cellpadding="0" width="98%">
    <tr bgcolor="#CCCCFF">
        <th><font face="Helvetica">Eform Portal</font></th>
    </tr>
</table>
	<div  style="width:100%;height:100px;text-align:center;padding-top:20px;align=center;">
	<table class="eformInputHeadingTable" border="0" cellpadding="0" cellspacing="5" width="98%">
		<tr>
			<td>
				<form action="../eform/searchfromportal.do" method="get" id="form1">
				<div style="display:inline-block">
					<img src="<%=request.getContextPath()%>/oscarEncounter/graphics/edit-find.png" alt="find">
					<select style="width:140px" id="select1">
						<option value="All">All</option>
						<option value="ByName">By Name</option>
						<option value="ByLocation">By Location</option>
						<option value="ByCategory">By Category</option>
						<option value="ByGroup">By Group</option>
						<option value="ByKeyword">By Keyword</option>
					</select>
					<input type="hidden" name="searchtype" id="hid1">
					<input size="40%" type="text" name="searchinfo" />
					<input type="button" name="search" value="Search" onclick="setInfo();">
				</div>
				</form>
			</td>
		</tr>
		
	</table>
	</div>
	<%if(infos!=null){ %>
	<table width="98%" class="elements" id="myTable">
	<thead>
    <tr bgcolor="#CCCCFF">
        <th class="fname">eForm Name</th>
        <th>Version</th>
        <th>Creator</th>
        <th>Location</th>
        <th class="datetime">PublishTime</th>
        <th>Opts</th>
    </tr>
	</thead>
	<tbody>
    <%
		for(int i=0;i<infos.size();i++){
	%>

    <tr>
        <td width="25%" style="padding-left: 4px;"><a href="#" onclick="newWindow('efmformportalpreview.jsp?fid=<%=infos.get(i).getFid()%>', '<%="Form"+i%>'); return false;"><%=infos.get(i).getName()%></a></td>
        <td width="15%" style="padding-left: 4px"><%=infos.get(i).getVersion()%> </td>
        <td width="15%" style="padding-left: 4px"><%=infos.get(i).getCreator()%></td>
        <td nowrap align='center' width="10%"><%=infos.get(i).getLocation()%></td>
        <td align="center" width="30%" style="padding-left: 4px"><%=df.format(infos.get(i).getApprovetime()) %></td>
        <td nowrap align='center' width="10%"><a href="../eform/importPortal.do?fid=<%=infos.get(i).getFid() %>&name=<%=infos.get(i).getName() %>" >Import</a></td>
    </tr>
	
    <% } %>
	</tbody>
</table>
<%}else{ %>
	<table class="elements" width="98%">
		<tr>
			<td>
				No Result
			</td>
		</tr>
	</table>
<%} %>
</body>
</html>