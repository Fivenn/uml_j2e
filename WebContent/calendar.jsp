

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ 
page import="java.util.List,org.model.Employe, org.model.Demand,java.util.ArrayList"
 %>
<%
	//Employe currentUser = (Employe)request.getSession().getAttribute("currentUser");
ArrayList<Demand> demandsList = (ArrayList<Demand>) request.getAttribute("demandsList");
for(Demand d: demandsList) {
	System.out.println(d.getEmploye().getMail() + "-" + d.getMotif());
}
%>
<div id='calendar'></div>

