<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List,org.model.Employe"%>
<%@page import="java.util.List,org.model.TeamLeader"%>

<%
	TeamLeader currentUser = (TeamLeader)request.getSession().getAttribute("currentUser");
%>

<h1>
	C'est la teaaam
</h1>

<table border="1" cellpadding="5" cellspacing="0">
	<tr>
		<th>Nom</th>
		<th>Prénom</th>
	</tr>
	<%
	for (Employe e: currentUser.getTeam()) {
	%>
		<tr>
			<td><%=e.getSurname()%></td>
			<td><%=e.getFirstName() %></td>
		</tr>
	<%
	}
	%>
</table>
