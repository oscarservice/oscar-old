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