

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ 
page import="java.util.List,org.model.Employe, org.model.Demand,java.util.ArrayList"
 %>
 <%
 String employeDemandsList = (String) request.getAttribute("employeDemandsList");
 System.out.println(employeDemandsList);
 %>

<div id='calendar'></div>

