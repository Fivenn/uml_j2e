<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h1>
	C'est la gestion des demandes
</h1>

<form class="form-co" action="ManageDemand" method="post">
	<div class="form-row">
	<div class="form-group col-md-6">
    <select class="employe" name="">
      <%
    	for (Employe e: currentUser.getTeam()) {
    	%>
    		<option value=e.getSurname()><%=e.getSurname()%> <%=e.getFirstName()%></option>
    	<%
    	}
    	%>
    </select>
  	</div>
  	<div class="form-group col-md-6">
      <select class="team" name="">
        <%
        for ( e: currentUser.getTeam()) {
        %>
          <option value=e.getSurname()><%=e.getSurname()%> <%=e.getFirstName()%></option>
        <%
        }
        %>
      </select>
  	</div>
  		<button class="btn btn-primary my-2 my-sm-0" type="submit">Rechercher</button>
  	</div>
</form>
