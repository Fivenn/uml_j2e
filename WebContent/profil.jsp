<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.ArrayList,org.model.Employe,org.model.Team,org.model.Demand"%>

<%
	//user en cours d'utilisation de l'application
	Employe currentUser = (Employe)request.getSession().getAttribute("currentUser");
	//Message d'erreur initialisé uniquement si besoin
	String errorUpdatingPwd = request.getAttribute("errorUpdatingPwd")!= null ? (String)request.getAttribute("errorUpdatingPwd") : "";
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Profil</title>
	</head>
	<body class="profil-image">
		<h1 style="border-radius: 1em; text-align: center; padding: 0.5em; background: white; margin: 0 4em 1em 4em;">Profil de <%=currentUser.getFirstName() + " " + currentUser.getSurname()%></h1>
		<div style="align-items: center; display: flex;justify-content: space-around;">
			<div style="display: flex;align-items: center;flex-direction: column;justify-content: center;">
				<div class="card">
	                <div class="card-body">
						<div>
							<!--  Affichage du rôle et de l'equipe du user -->
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
							<label><i style="font-weight: bold;">Mail : </i><%=currentUser.getMail()%></label>
						</div>
						<div>
							<label><i style="font-weight: bold;">Né(e) le : </i><%=currentUser.getBirthDate()%></label>
						</div>
						<div>
							<label><i style="font-weight: bold;">Nombre de jours : </i><%=currentUser.getNbDays()%></label>
						</div>
						<div>
						<!--  Form permettant de modifier l'adresse postale -->
							<form action="MyProfil" method="post">
								<label><i style="font-weight: bold;">Adresse postale : </i></label>
								<div class="form-group">
									<input type="text" id="addr" name="addr" required size="30" value="<%=currentUser.getAddress()%>">
		                        </div>
								<center><button type="submit" class="btn btn-primary text-center">Envoyer</button></center>
							</form>
						</div>
					</div>
				</div>	
			</div>
			<div style="display: flex;flex-direction: column;">
				<div class="card">
                	<div class="card-body">
                		<!-- Form permettant de modifier le mot de passe -->
                		<form action="MyProfil" method="post">
	                        <h5>Changer votre mot de passe</h5>
	                        <div class="form-group">
	                           <input placeholder="Nouveau mot de passe" type="password" class="form-control" name="new_pwd">
	                        </div>
	                        <div class="form-group">
	                           <input placeholder="Re-Nouveau mot de passe" type="password" class="form-control" name="re_new_pwd">
	                        </div>
	                        <center><button type="submit" class="btn btn-primary text-center">Envoyer</button></center>
	                        <p style="color: DC3545;"><%=errorUpdatingPwd %></p>
	                     </form>
					</div>
				</div>	
			</div>
		</div>
	</body>
</html>