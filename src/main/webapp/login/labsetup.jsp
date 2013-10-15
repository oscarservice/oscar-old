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


<%@page import="java.util.List, java.util.Map, java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.io.*" %>
<%@page import="org.oscarehr.common.dao.UserPropertyDAO, oscar.OscarProperties" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

   
        <% if(OscarProperties.getInstance().getBooleanProperty("LAB_SETUP_ENABLE","true")){ %>
	          <% response.sendRedirect(OscarProperties.getInstance().getLabportalURL());%>
	      <% } else { %>
	      	<jsp:forward page="Connectfailed.jsp"></jsp:forward>;
	      <%}%>
`


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
            <title>lab setup</title>
    </head>
    <body>
        
    </body>
</html>
