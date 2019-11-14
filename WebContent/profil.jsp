<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.ArrayList,org.model.Employe,org.model.Team,org.model.Demand"%>

<%
	Employe currentUser = (Employe)request.getSession().getAttribute("currentUser");
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Profil</title>
	</head>
	<body>
		<h1>Mon Profil</h1>
		<div>
			<label><%=currentUser.getFirstName() + " " + currentUser.getSurname()%></label>
		</div>
		<div>
			<label><%=currentUser.getBirthDate()%></label>
		</div>
		<div>
			<% if(currentUser.isRH() && currentUser.isLeader()){ %>
				<label>Leader Responsable RH de l'équipe <%=currentUser.getNbTeam() %></label>
			<% } else if (currentUser.isRH()) { %>
				<label>Responsable RH de l'équipe <%=currentUser.getNbTeam() %></label>
			<% } else if (currentUser.isLeader()) { %>
				<label>Leader de l'équipe <%=currentUser.getNbTeam() %></label>
			<% } else { %>
				<label>Employé dans l'équipe <%=currentUser.getNbTeam() %></label>
			<% } %>
		</div>
		
		<div>
			<label>Equipe</label>
		</div>
		
		<div>
			<label>Changer votre mot de passe</label>
			<label for="old_password">Ancien mot de passe</label>
			<input type="text" id="old_password" name="old_password" required size="30">
			<label for="new_password">Nouveau mot de passe</label>
			<input type="text" id="new_password" name="new_password" required size="30">
			<label for="re_new_password">Re-Nouveau mot de passe</label>
			<input type="text" id="re_new_password" name="re_new_password" required size="30">
		</div>
		
		<div>
			<label><%=currentUser.getMail()%></label>
		</div>
		
		<div>
			<label for="addr">Adresse postale</label>
			<input type="text" id="addr" name="addr" required size="30" value="<%=currentUser.getAddress()%>">
		</div>
	</body>
</html>