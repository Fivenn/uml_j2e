<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List,org.model.Employe"%>


<%
	List<Employe> listEmployes = (List<Employe>)request.getAttribute("listEmployes");
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home</title>
	</head>
	<body>
		<h1>Bienvenue les potes</h1>
		<% if (listEmployes== null ){//|| listEmployes.size() == 0) { %>
			<p> no result </p>
		<% } else { %>

		<table border="1" cellpadding="5" cellspacing="0">
			<tr>
				<th>Titre</th>
				<th>Auteur</th>
			</tr>
		<%
			for (Employe e:listEmployes) {
				String surname = e.getSurname();
				String firstname = e.getFirstName();
		%>
			<tr>
				<td><%=surname%></td>
				<td><%=firstname %></td>
			</tr>
		<%
			}
		%>
			</table>
		<% } %>
		
		<form action="HomeServlet" method="post">
			Rechercher un livre dont le titre contient : <input type="text" name="searchText">
			<input type="submit" value="recherche">
		</form>
	</body>
</html>