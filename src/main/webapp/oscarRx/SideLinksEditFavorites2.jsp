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
<!-- tile of SearchDrug3.jsp -->
<%@page import="oscar.oscarRx.data.RxPatientData"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
        oscar.oscarRx.pageUtil.RxSessionBean bean2 = (oscar.oscarRx.pageUtil.RxSessionBean)request.getSession().getAttribute("RxSessionBean");

        org.oscarehr.common.model.Allergy[] allergies = RxPatientData.getPatient(bean2.getDemographicNo()).getActiveAllergies();
        String alle = "";
        if (allergies.length > 0 ){ alle = "Red"; }
        %>
<td width="22%" height="100%" valign="top">
<div class="PropSheetMenu">
<p class="PropSheetLevel1CurrentItem<%=alle%>">
    <bean:message key="oscarRx.sideLinks.msgAllergies"/>
    <a href="javascript:void(0);" name="cmdAllergies"   onclick="javascript:window.location.href='ShowAllergies2.jsp?demographicNo=<%=request.getParameter("demographicNo")%>';" style="width: 200px" >+</a>
</p>
<p class="PropSheetMenuItemLevel1">
<%

        for (int j=0; j<allergies.length; j++){%>

<p class="PropSheetMenuItemLevel1"><a
	title="<%= allergies[j].getDescription() %> - <%= allergies[j].getReaction() %>">
<%=allergies[j].getShortDesc(13,8,"...")%> </a></p>
<%}%>
</p>
<p class="PropSheetLevel1CurrentItem"><bean:message key="oscarRx.sideLinks.msgFavorites"/>
<a href="EditFavorites2.jsp">edit</a>
<a href="CopyFavorites2.jsp">copy</a>  <%-- <bean:message key="oscarRx.sideLinks.msgCopyFavorites"/> --%>
</p>
<div id="favoritesContainer" style="width:100%">
<%
        oscar.oscarRx.data.RxPrescriptionData.Favorite[] favorites
            = new oscar.oscarRx.data.RxPrescriptionData().getFavorites(bean2.getProviderNo());

        for (int j=0; j<favorites.length; j++){%>

<p class="PropSheetMenuItemLevel1">
  <input type="checkbox" id="checkfav_<%= favorites[j].getFavoriteId() %>" >
  <a href="javascript:void(0);" onclick="useFav2('<%= favorites[j].getFavoriteId() %>');"
	title="<%= favorites[j].getFavoriteName() %>"> <%if(favorites[j].getFavoriteName().length()>28){%>
<%= favorites[j].getFavoriteName().substring(0, 25) + "..." %> <%}else{%>
<%= favorites[j].getFavoriteName() %> <%}%> </a>
</p>
<%}%>
</div>
</div>
<br/>
<span style="padding-left:15px"><input type="button" name="prescribeFavsBtn" id="prescribeFavsBtn" value="Prescribe" class="ControlPushButton" style="width:100px" onClick="prescribeFavorites()"/> </span>
</td>
