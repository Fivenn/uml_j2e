<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,org.model.Employe,org.model.Team"%>
<%
	ArrayList<Team> teamsList = (ArrayList<Team>)request.getAttribute("teamsList");
	ArrayList<Employe> employesList  = (ArrayList<Employe>)request.getAttribute("employesList");
%>
<h1>
	C'est la table des employés
</h1>

<a href="CreateUser">Créer un nouvel employé</a>