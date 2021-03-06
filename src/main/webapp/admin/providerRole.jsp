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
<%-- Updated by Eugene Petruhin on 09 dec 2008 while fixing #2392669 --%>

<%@ taglib uri="/WEB-INF/security.tld" prefix="security" %>
<%@ page errorPage="../errorpage.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="oscar.util.*,oscar.*" %>
<%@ page import="oscar.login.*" %>
<%@ page import="oscar.log.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="org.springframework.util.StringUtils" %>
<%@ page import="org.oscarehr.util.SpringUtils" %>
<%@ page import="org.oscarehr.PMmodule.model.Program" %>
<%@ page import="org.oscarehr.PMmodule.dao.ProgramDao" %>
<%@ page import="org.oscarehr.PMmodule.model.ProgramProvider" %>
<%@ page import="org.oscarehr.PMmodule.dao.ProgramProviderDAO" %>
<%@ page import="org.oscarehr.common.model.SecRole" %>
<%@ page import="org.oscarehr.common.dao.SecRoleDao" %>
<%@ page import="com.quatro.model.security.Secuserrole" %>
<%@ page import="com.quatro.dao.security.SecuserroleDao" %>
<%@ page import="org.oscarehr.common.model.RecycleBin" %>
<%@ page import="org.oscarehr.common.dao.RecycleBinDao" %>
<%
	ProgramDao programDao = SpringUtils.getBean(ProgramDao.class);
	SecRoleDao secRoleDao = SpringUtils.getBean(SecRoleDao.class);
	SecuserroleDao secUserRoleDao = (SecuserroleDao)SpringUtils.getBean("secuserroleDao");
	RecycleBinDao recycleBinDao = SpringUtils.getBean(RecycleBinDao.class);
	ProgramProviderDAO programProviderDao = (ProgramProviderDAO) SpringUtils.getBean("programProviderDAO");
%>
<%
if(session.getAttribute("user") == null )
	response.sendRedirect("../logout.jsp");
String roleName$ = (String)session.getAttribute("userrole") + "," + (String) session.getAttribute("user");
String curUser_no = (String)session.getAttribute("user");
%>
<security:oscarSec roleName="<%=roleName$%>" objectName="_admin,_admin.userAdmin,_admin.torontoRfq" rights="r" reverse="<%=true%>" >
<%response.sendRedirect("../logout.jsp");%>
</security:oscarSec>

<%
    boolean isSiteAccessPrivacy=false;
%>

<security:oscarSec objectName="_site_access_privacy" roleName="<%=roleName$%>" rights="r" reverse="false">
	<%isSiteAccessPrivacy=true; %>
</security:oscarSec>

<%
//check to see if new case management is request
ArrayList<String> users = (ArrayList<String>)session.getServletContext().getAttribute("CaseMgmtUsers");
boolean newCaseManagement = false;

if(!org.oscarehr.common.IsPropertiesOn.isCaisiEnable()) {
	//This should only temporarily apply to oscar, not caisi.
	//You cannot assign provider to one program "OSCAR" here if you have caisi enabled.
	//If there is no program called "OSCAR", it will only assign empty program to the provider which is not acceptable.
	if(( users != null && users.size() > 0 ) || OscarProperties.getInstance().getProperty("CASEMANAGEMENT", "").equalsIgnoreCase("all"))
    	newCaseManagement = true;
}

String ip = request.getRemoteAddr();

String msg = "";
DBHelp dbObj = new DBHelp();

String caisiProgram = null;

//get caisi programid for oscar
if( newCaseManagement ) {
	Program p = programDao.getProgramByName("OSCAR");
	if(p != null) {
		caisiProgram = String.valueOf(p.getId());
	}
}

// get role from database
Vector vecRoleName = new Vector();
String	sql;
String adminRoleName = "";


String omit="";
if (isSiteAccessPrivacy) {
	omit = OscarProperties.getInstance().getProperty("multioffice.admin.role.name", "");
}
List<SecRole> secRoles = secRoleDao.findAllOrderByRole();
for(SecRole secRole:secRoles) {
	if(!secRole.getName().equals(omit)) {
		vecRoleName.add(secRole.getName());
	}

}


// update the role
if (request.getParameter("buttonUpdate") != null && request.getParameter("buttonUpdate").length() > 0) {
    String number = request.getParameter("providerId");
    String roleId = request.getParameter("roleId");
    String roleOld = request.getParameter("roleOld");
    String roleNew = request.getParameter("roleNew");

    if(!"-".equals(roleNew)) {
		Secuserrole secUserRole = secUserRoleDao.findById(Integer.parseInt(roleId));
		if(secUserRole != null) {
			secUserRole.setRoleName(roleNew);
			secUserRoleDao.updateRoleName(Integer.parseInt(roleId),roleNew);
			msg = "Role " + roleNew + " is updated. (" + number + ")";

			RecycleBin recycleBin = new RecycleBin();
			recycleBin.setProviderNo(curUser_no);
			recycleBin.setUpdateDateTime(new java.util.Date());
			recycleBin.setTableName("secUserRole");
			recycleBin.setKeyword(number +"|"+ roleOld);
			recycleBin.setTableContent("<provider_no>" + number + "</provider_no>" + "<role_name>" + roleOld + "</role_name>"  + "<role_id>" + roleId + "</role_id>");
			recycleBinDao.persist(recycleBin);

			LogAction.addLog(curUser_no, LogConst.UPDATE, LogConst.CON_ROLE, number +"|"+ roleOld +">"+ roleNew, ip);

			if( newCaseManagement ) {
                ProgramProvider programProvider = programProviderDao.getProgramProvider(number, Long.valueOf(caisiProgram));
                if(programProvider == null) {
                	programProvider = new ProgramProvider();
                }
                programProvider.setProgramId( Long.valueOf(caisiProgram));
                programProvider.setProviderNo(number);
                programProvider.setRoleId(Long.valueOf(secRoleDao.findByName(roleNew).getId()));
                programProviderDao.saveProgramProvider(programProvider);
			}

		} else {
			msg = "Role " + roleNew + " is <font color='red'>NOT</font> updated!!! (" + number + ")";
		}
    }

}

// add the role
if (request.getParameter("submit") != null && request.getParameter("submit").equals("Add")) {
    String number = request.getParameter("providerId");
    String roleNew = request.getParameter("roleNew");

    if(!"-".equals(roleNew)) {
	    Secuserrole secUserRole = new Secuserrole();
	    secUserRole.setProviderNo(number);
	    secUserRole.setRoleName(roleNew);
	    secUserRole.setActiveyn(1);
	    secUserRoleDao.save(secUserRole);
	    msg = "Role " + roleNew + " is added. (" + number + ")";
	    LogAction.addLog(curUser_no, LogConst.ADD, LogConst.CON_ROLE, number +"|"+ roleNew, ip);
	    if( newCaseManagement ) {
            ProgramProvider programProvider = programProviderDao.getProgramProvider(number, Long.valueOf(caisiProgram));
            if(programProvider == null) {
            	programProvider = new ProgramProvider();
            }
            programProvider.setProgramId( Long.valueOf(caisiProgram));
            programProvider.setProviderNo(number);
            programProvider.setRoleId(Long.valueOf(secRoleDao.findByName(roleNew).getId()));
            programProviderDao.saveProgramProvider(programProvider);
	    }
    } else {
    	msg = "Role " + roleNew + " is <font color='red'>NOT</font> added!!! (" + number + ")";
    }

}

// delete the role
if (request.getParameter("submit") != null && request.getParameter("submit").equals("Delete")) {
    String number = request.getParameter("providerId");
    String roleId = request.getParameter("roleId");
    String roleOld = request.getParameter("roleOld");
    String roleNew = request.getParameter("roleNew");

    Secuserrole secUserRole = secUserRoleDao.findById(Integer.parseInt(roleId));
    if(secUserRole != null) {
    	secUserRoleDao.deleteById(secUserRole.getId());
    	msg = "Role " + roleOld + " is deleted. (" + number + ")";

    	RecycleBin recycleBin = new RecycleBin();
		recycleBin.setProviderNo(curUser_no);
		recycleBin.setUpdateDateTime(new java.util.Date());
		recycleBin.setTableName("secUserRole");
		recycleBin.setKeyword(number +"|"+ roleOld);
		recycleBin.setTableContent("<provider_no>" + number + "</provider_no>" + "<role_name>" + roleOld + "</role_name>");
		recycleBinDao.persist(recycleBin);

		LogAction.addLog(curUser_no, LogConst.DELETE, LogConst.CON_ROLE, number +"|"+ roleOld, ip);

        if( newCaseManagement ) {
            ProgramProvider programProvider = programProviderDao.getProgramProvider(number, Long.valueOf(caisiProgram));
            if(programProvider != null) {
            	programProviderDao.deleteProgramProvider(programProvider.getId());
            }
        }
    } else {
    	msg = "Role " + roleOld + " is <font color='red'>NOT</font> deleted!!! (" + number + ")";
    }

}

String keyword = request.getParameter("keyword")!=null?request.getParameter("keyword"):"";
%>

  <html>
    <head>
      <script type="text/javascript" src="<%= request.getContextPath() %>/js/global.js"></script>
      <title>
        PROVIDER
      </title>
      <link rel="stylesheet" href="../receptionist/receptionistapptstyle.css">
      <script language="JavaScript">
<!--
function setfocus() {
	this.focus();
	document.forms[0].keyword.select();
}
function submit(form) {
	form.submit();
}
//-->
      </script>
    </head>
    <body bgproperties="fixed" bgcolor="ivory" onLoad="setfocus()" topmargin="0" leftmargin="0" rightmargin="0">
      <form name="myform" action="providerRole.jsp" method="POST">
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr bgcolor="#486ebd">
          <th align="CENTER" width="90%">
            <font face="Helvetica" color="#FFFFFF">
            <% if(msg.length()>1) {%>
			<%=msg%>
			<% } %>
            </font>
          </th>
          <td nowrap>
            <font size="-1" color="#FFFFFF">
              Name:
              <input type="text" name="keyword" size="15" value="<%=keyword%>" />
              <input type="submit" name="search" value="Search">
            </font>
          </td>
        </tr>
      </table>
      </form>
<%
String lastName = "";
String firstName = "";
String[] temp = keyword.split("\\,");
if(temp.length>1) {
	lastName = temp[0] + "%";
	firstName = temp[1] + "%";
} else {
	lastName = keyword + "%";
	firstName = "%";
}


String query;

if (isSiteAccessPrivacy){
	//multisites: only select providers have same site with current user
	query = "select u.id, u.role_name, p.provider_no, p.first_name, p.last_name from provider p LEFT JOIN secUserRole u ON ";
	query += " p.provider_no=u.provider_no LEFT JOIN providersite ps ON p.provider_no = ps.provider_no ";
	query += " where p.last_name like '" + lastName + "' and p.first_name like '" + firstName + "' and p.status='1' ";
	query += " and not exists(select * from secUserRole scr where scr.provider_no =  p.provider_no and scr.role_name = '" + adminRoleName + "') " ;
	query += " and ps.site_id in (select site_id from providersite where provider_no = " + curUser_no + ")  order by p.first_name, p.last_name, u.role_name";
}
else {
	query = "select u.id, u.role_name, p.provider_no, p.first_name, p.last_name from provider p LEFT JOIN secUserRole u ON ";
	query += " p.provider_no=u.provider_no where p.last_name like '" + lastName + "' and p.first_name like '" + firstName + "' and p.status='1' order by p.first_name, p.last_name, u.role_name";
}

ResultSet rs = DBHelp.searchDBRecord(query);
Vector<Properties> vec = new Vector<Properties>();
while (rs.next()) {
	Properties prop = new Properties();
	prop.setProperty("provider_no", DBHelp.getString(rs,"provider_no")==null?"":DBHelp.getString(rs,"provider_no"));
	prop.setProperty("first_name", DBHelp.getString(rs,"first_name"));
	prop.setProperty("last_name", DBHelp.getString(rs,"last_name"));
	prop.setProperty("role_id", DBHelp.getString(rs,"id")!=null?DBHelp.getString(rs,"id"):"");
	prop.setProperty("role_name", DBHelp.getString(rs,"role_name")!=null?DBHelp.getString(rs,"role_name"):"");
	vec.add(prop);
}
%>
        <table width="100%" border="0" bgcolor="ivory" cellspacing="1" cellpadding="1">
          <tr bgcolor="mediumaquamarine">
            <th colspan="5" align="left">
              Provider-Role List
            </th>
          </tr>
          <tr bgcolor="silver">
            <th width="10%" nowrap>
              ID
            </th>
            <th width="20%" nowrap>
              <b>First Name</b>
            </th>
            <th width="20%" nowrap>
              <b>Last Name</b>
            </th>
            <th width="20%" nowrap>
              Role
            </th>
            <th nowrap>
              Action
            </th>
          </tr>
<%
        String[] colors = { "#ccCCFF", "#EEEEFF" };
        for (int i = 0; i < vec.size(); i++) {
          	Properties item = vec.get(i);
          	String providerNo = item.getProperty("provider_no", "");
%>
      <form name="myform<%= providerNo %>" action="providerRole.jsp" method="POST">
            <tr bgcolor="<%=colors[i%2]%>">
              <td>
                <%= providerNo %>
              </td>
              <td>
                <%= item.getProperty("first_name", "") %>
              </td>
              <td>
                <%= item.getProperty("last_name", "") %>
              </td>
              <td align="center">
                  <select name="roleNew">
                      <option value="-" >-</option>
<%
                    for (int j = 0; j < vecRoleName.size(); j++) {
%>
                      <option value="<%=vecRoleName.get(j)%>" <%= vecRoleName.get(j).equals(item.getProperty("role_name", ""))?"selected":"" %>>
                      <%= vecRoleName.get(j) %>
                      </option>
<%
                    }
%>
                  </select>
            </td>
            <td align="center">
              <input type="hidden" name="keyword" value="<%=keyword%>" />
              <input type="hidden" name="providerId" value="<%=providerNo%>">
              <input type="hidden" name="roleId" value="<%= item.getProperty("role_id", "")%>">
              <input type="hidden" name="roleOld" value="<%= item.getProperty("role_name", "")%>">
              <input type="submit" name="submit" value="Add">
              -
              <input type="submit" name="buttonUpdate" value="Update" <%= StringUtils.hasText(item.getProperty("role_id"))?"":"disabled"%>>
              -
              <input type="submit" name="submit" value="Delete" <%= StringUtils.hasText(item.getProperty("role_id"))?"":"disabled"%>>
            </td>
            </tr>
      </form>
<%
          }
%>
        </table>
      <hr>
      </body>
    </html>
