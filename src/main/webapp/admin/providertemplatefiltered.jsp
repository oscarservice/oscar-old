<%@ taglib uri="/WEB-INF/security.tld" prefix="security"%>
<%
    if(session.getAttribute("userrole") == null )  response.sendRedirect("../logout.jsp");
    String roleName$ = (String)session.getAttribute("userrole") + "," + (String) session.getAttribute("user");
%>
<security:oscarSec roleName="<%=roleName$%>"
        objectName="_admin,_admin.schedule" rights="r" reverse="<%=true%>">
        <%response.sendRedirect("../logout.jsp");%>
</security:oscarSec>
<%@ page import="java.util.*, java.sql.*, oscar.*,oscar.util.*, net.sf.json.*"
        errorPage="errorpage.jsp"%>
<jsp:useBean id="apptMainBean" class="oscar.AppointmentMainBean"
        scope="page" />
<%
  String param = request.getParameter("name") == null ? "%" : request.getParameter("name");
  String [][] dbQueries=new String[][] {
{"search_templatename", "select encountertemplate_name, encountertemplate_value from encountertemplate where encountertemplate_name like ? order by encountertemplate_name"}};
  apptMainBean.doConfigure(dbQueries);

 JSONObject aJS = new JSONObject();
 JSONArray arJS = new JSONArray();

  ResultSet rsdemo = null;
  rsdemo = apptMainBean.queryResults(param, "search_templatename");

  %>
{"result":
<%
  while (rsdemo.next()) {
    aJS.put("name", rsdemo.getString("encountertemplate_name"));
    aJS.put("value", rsdemo.getString("encountertemplate_value"));
    arJS.add(aJS);

  }
  out.println(arJS);
%>}
