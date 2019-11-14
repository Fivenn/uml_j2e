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



<table class="table table-bordered table-striped">
  <thead>
    <tr>
      <th scope="col">Nom</th>
      <th scope="col">Prenom</th>
      <th scope="col">Mail</th>
    </tr>
  </thead>
  <tbody>
    <% for(Employe e : employesList){ %>
      <tr>
        <td><%=e.getSurname()%></td>
        <td><%=e.getFirstName()%></td>
        <td><%=e.getMail()%></td>
        <%if(e.isRH()){ %>
        	<td>RH</td>
        <%} %>	
        <%else if(!e.isRH) %>
        	<td><%e.getTitle()%></td>
        <%} %>
      </tr>
    <%}%>