<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,org.model.Employe"%>
<%
	ArrayList<Employe> employesList  = (ArrayList<Employe>)request.getAttribute("employesList");
%>
<h1>
	C'est la table des employés
</h1>
<p>
	<form action="CreateUser" method="post">
		<button class="btn btn-primary" type="submit" href="CreateUser">Créer un nouvel employé</button>
	</form>
</p>

<%if(employesList != null){ %>
<table class="table table-bordered table-striped">
	  <thead>
	    <tr>
	      <th scope="col">Nom</th>
	      <th scope="col">Prenom</th>
	      <th scope="col">Mail</th>
	      <th scope="col">Equipe</th>
	      <th scope="col">Poste</th>
	      <th scope="col">Modifier</th>
	      <th scope="col">Supprimer</th>
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
	        <td>
	        <div style="display: flex !important;justify-content: space-around !important;">
	        <form action="CreateUser" method="post">
		        <button class="btn btn-success" type="submit" name="modifier" style="font-size:12px;" value="<%=e.getMail()%>"><i class="fa fa-edit" aria-hidden="true"></i></button>
		    </form>
		    </div>
		    </td>
		    <td>
		    <div style="display: flex !important;justify-content: space-around !important;">
		    <form class="form-row align-items-center" action="ManageUser" method="post" onsubmit="return confirm('Are you sure you want to delete <%=e.getFullName()%>?');">
		        <button class="btn btn-danger" type="submit" name="delete" value="<%=e.getMail()%>"><i class="fa fa-window-close" style="font-size:12px;" aria-hidden="true"></i></button>
		    </form>
		    </div>
		    </td>
	      </tr>
	    <%}%>	
	  </tbody>
	</table>
	<% }%>