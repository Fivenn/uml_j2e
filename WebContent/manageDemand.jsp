<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,org.model.Employe,org.model.Team,org.model.Demand"%>

<%
	ArrayList<Team> teamsList = (ArrayList<Team>)request.getAttribute("teamsList");
	ArrayList<Employe> employesList  = (ArrayList<Employe>)request.getAttribute("employesList");
 	 ArrayList<String> statusList  = (ArrayList<String>)request.getAttribute("statusList");
 	 ArrayList<Demand> demandsList  = (ArrayList<Demand>)request.getAttribute("demandsList");
%>
<%
	if(teamsList != null){
%>
	<h1>
		C'est la gestion des demandes
	</h1>
	
	<form class="form-co" action="ManageDemand" method="post">
		<div class="form-row">
			<div class="form-group col-md-3">
			    <select class="form-control" name="employe">
			      <%
			    	for (Employe e: employesList) {
			    	%>
			    		<option value="<%=e.getSurname()%>"><%=e.getSurname()%> <%=e.getFirstName()%></option>
			    	<%
			    	}
			    	%>
			    </select>
	  		</div>
		  	<div class="form-group col-md-3">
		      <select class="form-control" name="team">
		        <%
		        for (Team t: teamsList) {
		        %>
		          <option value="<%=t.getNoTeam()%>"><%=t.getName()%></option>
		        <%
		        }
		        %>
		      </select>
		  	</div>
		    <div class="form-group col-md-3">
		      <select class="form-control" name="statut">
		        <%
		        for (String s: statusList) {
		        %>
		          <option value="<%=s%>"><%=s%></option>
		        <%
		        }
		        %>
		      </select>
		    </div>
		    <div>
		  		<button class="btn btn-primary my-2 my-sm-0" type="submit">Rechercher</button>
		  	</div>
		</div>
	</form>
	
	<table class="table table-bordered table-striped">
	  <thead>
	    <tr>
	      <th scope="col">Date</th>
	      <th scope="col">Nom</th>
	      <th scope="col">Prenom</th>
	      <th scope="col">Départ</th>
	      <th scope="col">Retour</th>
	      <th scope="col">Durée</th>
	      <th scope="col">Motif</th>
	      <th scope="col">Status</th>
	    </tr>
	  </thead>
	  <tbody>
	    <% for(Demand d : demandsList){ %>
	      <tr>
	      	<td><%=d.getRequestDate()%></td>
	        <td><%=d.getEmploye().getSurname()%></td>
	        <td><%=d.getEmploye().getFirstName()%></td>
	        <td><%=d.getStartDate()%></td>
	        <td><%=d.getEndDate()%></td>
	        <td><%=d.getNbDays()%></td>
	        <td><%=d.getMotif()%></td>
	        <td class="td-status">
	          <% if(d.getStatus().equals("pending")){ %>
	            <form class="form form-inline button-square approved" action="ManageDemand" method="post">
	              <button class="btn btn-primary" type="submit" name="approved" value="<%=d.getId()%>"><i class="fas fa-check" aria-hidden="true"></i></button>
	            </form>
	            <form class="form form-inline button-square refused" action="ManageDemand" method="post">
	              <button class="btn btn-primary" type="submit" name="refused" value="<%=d.getId()%>"><i class="fas fa-times" aria-hidden="true"></i></button>
	            </form>
	          <%}else{%>
	            <%=d.getStatus()%>
	          <%}%>
	        </td>
	      </tr>
	    <%}%>
	
	  </tbody>
	</table>
<%} %>
