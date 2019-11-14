

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ 
page import="java.util.List,org.model.Employe, org.model.Demand,java.util.ArrayList"
 %>
<%
ArrayList<Demand> demandsEmployeList = (ArrayList<Demand>)request.getAttribute("demandsList");

for(Demand d: demandsEmployeList) {
	%>
	<p><%=d.getMotif()%></p>
<%
}
%>
<div id='calendar'></div>

