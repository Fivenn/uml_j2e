<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,org.model.Employe,org.model.Team"%>

<%
	ArrayList<Team> teamsList = (ArrayList<Team>)request.getAttribute("teamsList");
	ArrayList<Employe> employesList  = (ArrayList<Employe>)request.getAttribute("employesList");
  	ArrayList<String> statusList  = (ArrayList<String>)request.getAttribute("statusList");
%>
<h1>
	C'est la gestion des demandes
</h1>

<form class="form-co" action="ManageDemand" method="post">
	<div class="form-row">
		<div class="form-group col-md-6">
		    <select class="" name="employe">
		      <%
		    	for (Employe e: employesList) {
		    	%>
		    		<option value="<%=e.getSurname()%>"><%=e.getSurname()%> <%=e.getFirstName()%></option>
		    	<%
		    	}
		    	%>
		    </select>
  		</div>
	  	<div class="form-group col-md-6">
	      <select class="" name="team">
	        <%
	        for (Team t: teamsList) {
	        %>
	          <option value="<%=t.getNoTeam()%>"><%=t.getName()%></option>
	        <%
	        }
	        %>
	      </select>
	  	</div>
	    <div class="form-group col-md-6">
	      <select class="" name="statut">
	        <%
	        for (String s: statusList) {
	        %>
	          <option value="<%=s%>">s%></option>
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
