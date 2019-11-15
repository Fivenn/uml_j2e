<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.ArrayList,org.model.Employe,org.model.Team,org.model.Demand"%>

<%
	Employe currentUser = (Employe)request.getSession().getAttribute("currentUser");
%>

<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
      	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
      	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
      	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>	
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Profil</title>
	</head>
	<body>
		<h1>Profil de <%=currentUser.getFirstName() + " " + currentUser.getSurname()%></h1>
		<div style="display: flex;justify-content: space-between;">
			<div style="display: flex;align-items: center;flex-direction: column;justify-content: center;">
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
					<label><%=currentUser.getMail()%></label>
				</div>
				<div style="display: flex;align-items: center;flex-direction: column;justify-content: center;">
					<label for="addr">Adresse postale</label>
					<input type="text" id="addr" name="addr" required size="30" value="<%=currentUser.getAddress()%>">
				</div>
			</div>
			<div style="display: flex;flex-direction: column;">
				<div class="card">
                	<div class="card-body">
                		<form action="Connection" method="post">
	                        <h5>Changer votre mot de passe</h5>
	                        <div class="form-group">
	                           <input placeholder="Nouveau mot de passe" type="password" class="form-control" name="new_pwd">
	                        </div>
	                        <div class="form-group">
	                           <input placeholder="Re-Nouveau mot de passe" type="password" class="form-control" name="re_new_pwd">
	                        </div>
	                        <center><button type="submit" class="btn btn-primary text-center">Envoyer</button></center>
	                     </form>
					</div>
				</div>	
			</div>
		</div>
	</body>
</html>