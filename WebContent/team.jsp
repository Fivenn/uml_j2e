<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,org.model.Employe"%>

<%
	//il faut faire une requete bdd
	ArrayList<Employe> team = (ArrayList<Employe>)request.getAttribute("currentTeam");
%>
<% if(team!=null){%>

<h1>
	C'est la teaaam
</h1>

<table border="1" cellpadding="5" cellspacing="0">
	<tr>
		<th>Nom</th>
		<th>Prénom</th>
	</tr>
	<%
	for (Employe e: team) {
	%>
		<tr>
			<td><%=e.getSurname()%></td>
			<td><%=e.getFirstName() %></td>
		</tr>
	<%
	}
	%>
</table>
	<%
	}
	%>