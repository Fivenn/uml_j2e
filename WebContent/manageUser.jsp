<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,org.model.Employe"%>
<%
	ArrayList<Employe> employesList  = (ArrayList<Employe>)request.getAttribute("employesList");
%>
<h1>
	C'est la table des employés
</h1>

<a href="CreateUser">Créer un nouvel employé</a>
<%if(employesList != null){ %>
<table class="table table-bordered table-striped">
	  <thead>
	    <tr>
	      <th scope="col">Nom</th>
	      <th scope="col">Prenom</th>
	      <th scope="col">Mail</th>
	      <th scope="col">Equipe</th>
	      <th scope="col">Poste</th>
	    </tr>
	  </thead>
	  <tbody>
	    <% for(Employe e: employesList){ %>
	      <tr>
	        <td><%=e.getSurname()%></td>
	        <td><%=e.getFirstName()%></td>
	        <td><%=e.getMail()%></td>
	        <%if(e.getNbTeam() == 0){ %>
	        	<td>aucune</td>
	        <%} else { %>
	        	<td><%=e.getNbTeam()%></td>
	        <%} %>
	        <%if(e.isRH() && e.isLeader()){ %>
	        <td>RespoRH</td>
	        <%} %>
	        <%if(!e.isRH() && e.isLeader()){ %>
	        <td>TeamLeader</td>
	        <%} %>
	        <%if(e.isRH() && !e.isLeader()){ %>
	        <td>EmployeRH</td>
	        <%} %>
	         <%if(!e.isRH() && !e.isLeader()){ %>
	        <td>Employe</td>
	        <%} %>
	        <td style="display: flex !important;justify-content: space-around !important;"></td>
	      </tr>
	    <%}%>	
	  </tbody>
	</table>
	<% }%>