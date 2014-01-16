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
<%@page import="com.oscar.eformportal.ws.service.EformInfo"%>
<%@page import="com.oscar.eformportal.ws.service.IOscarService"%>
<%@page import="org.apache.cxf.jaxws.JaxWsProxyFactoryBean"%>
<%@page import="java.util.*" %>
<%@page import="oscar.OscarProperties"%>
<%@page import="com.oscar.eformportal.ws.service.ConfigHttpConduit" %>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%
JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
factory.setServiceClass(IOscarService.class);
String wsAddress = OscarProperties.getInstance().getProperty("eformPortal_ws_url");
factory.setAddress(wsAddress + "ws/portal");
IOscarService service = (IOscarService) factory.create();
ConfigHttpConduit.config(service);
List<EformInfo> infos = service.getEforms();
int fid = Integer.parseInt(request.getParameter("fid"));
String html = "";
for(int i=0;i<infos.size();i++){
	if(infos.get(i).getFid()==fid){
	html = infos.get(i).getContent().replaceAll("\\$\\{oscar_image_path\\}","../eform/showImage.do?fid="+infos.get(i).getFid()+"&imageFile=");
	}
}
out.print(html);
%>