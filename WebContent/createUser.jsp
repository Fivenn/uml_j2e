<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.model.Employe,java.util.ArrayList"%>

<%
	String createTeam = request.getAttribute("teamToCreate") != null
			? (String) request.getAttribute("teamToCreate")
			: "";
	Employe employe = request.getAttribute("employe") != null
			? (Employe) request.getAttribute("employe")
			: new Employe("", "", "", "", "", 25, false, false, 0);
	String verifMail = request.getAttribute("verifMail") != null
			? (String) request.getAttribute("verifMail")
			: "";
	String verifNameTeam = request.getAttribute("verifNameTeam") != null
			? (String) request.getAttribute("verifNameTeam")
			: "";
	ArrayList<String> listLeader = (ArrayList<String>) request.getAttribute("listLeader");
	ArrayList<Employe> employesList = (ArrayList<Employe>) request.getAttribute("employesList");
%>
<div
	style="display: flex; flex-wrap: wrap; border-bottom: 1px solid #110133; margin-bottom: 2em; justify-content: space-between;">
	<h3>
		<%
			if (request.getAttribute("employe") == null && createTeam.equals("")) {
		%>
		Création d'un employé
		<%
			} else if (createTeam.equals("yes") && request.getAttribute("employe") == null) {
		%>
		Création d'une team
		<%
			} else if (request.getAttribute("employe") != null) {
		%>
		Modification d'un employé
		<%
			}
		%>
	</h3>
	<nav>
		<form class="form-inline" action="ManageUser" method="post">
			<button class="nav-button" type="submit">Retour</button>
		</form>
	</nav>
</div>
<%
	if (createTeam.equals("yes") && request.getAttribute("employe") == null) {
%>
<form action="CreateUser" method="post">
	<div
		style="display: flex; flex-wrap: wrap; flex-direction = row; justify-content: space-between;">
		<div class="card"
			style="max-height: min-content; /*! flex-grow: inherit; */ flex-wrap: nowrap; height: max-content;">
			<fieldset class="card-body"
				style="max-height: min-content; flex-grow: inherit;">
				<legend>Informations Team</legend>
				<label for="nameTeam">Nom:</label> <input
					class="form-control mb-2 mr-sm-2 mb-sm-0" id="nameTeam" type="text"
					name="nameTeam"> <label for="description">Description:</label>
				<input class="form-control mb-2 mr-sm-2 mb-sm-0" id="description"
					type="text" name="description"> <label for="teamLeader">TeamLeader</label>
				<select class="form-control" name="teamLeader">
					<%
						for (Employe e : employesList) {
					%>
					<option value="<%=e.getMail()%>"><%=e.getSurname()%>
					<%=e.getFirstName()%></option>
					<%
						}
					%>
				</select>

			</fieldset>
			<button type="submit" class="btn btn-primary text-center"
				name="createTeam">Ajouter</button>
		</div>
	</div>
</form>
<%
	} else {
%>
<form action="CreateUser" method="post">
	<div
		style="display: flex; flex-wrap: wrap; flex-direction = row; justify-content: space-between;">

		<div class="card" style="display: flex;">
			<fieldset class="card-body"
				style="max-height: min-content; flex-grow: inherit;">
				<legend>Identité</legend>
				<label for="firstName">Prenom:</label><input
					class="form-control mb-0 mr-sm-0 mb-sm-0" id="firstName"
					type="text" name="prenom" value="<%=employe.getFirstName()%>">
				<label for="surName"></label> Nom: <input
					class="form-control mb-2 mr-sm-2 mb-sm-0" id="surName" type="text"
					name="nom" value="<%=employe.getSurname()%>"> <label
					for="birthdate">Date de naissance:</label> <input
					class="form-control mb-2 mr-sm-2 mb-sm-0" id="birthDate"
					type="text" name="naissance" value="<%=employe.getBirthDate()%>">
			</fieldset>
			<fieldset class="card-body"
				style="max-height: min-content; flex-grow: inherit;">
				<legend>Métier</legend>
				<label for="function">Poste:</label><input
					class="form-control mb-2 mr-sm-2 mb-sm-0" id="function" type="text"
					name="poste"> <label for="nbteam">Equipe:</label><input
					class="form-control mb-2 mr-sm-2 mb-sm-0" id="nbteam" type="number"
					name="equipe" value="<%=employe.getNbTeam()%>"> <label>Chef
					d'equipe</label> <select class="form-control" name="chef">
					<option value="oui">oui</option>
					<option value="non">non</option>
				</select>
			</fieldset>
		</div>

		<div class="card"
			style="max-height: min-content; /*! flex-grow: inherit; */ flex-wrap: nowrap; height: max-content;">
			<fieldset class="card-body"
				style="max-height: min-content; flex-grow: inherit;">
				<legend>Coordonnées</legend>
				<label for="mail">mail:</label> <input placeholder="dofm@example.dofm" type="text" class="form-control" id="mail" type="text"
					name="mail" value="<%=employe.getMail()%>"> <label
					for="address">Adresse:</label> <input
					class="form-control mb-2 mr-sm-2 mb-sm-0" id="address"
					name="adresse" value="<%=employe.getAddress()%>">
			</fieldset>
			<%
				if (request.getAttribute("employe") == null) {
			%>
			<button type="submit" class="btn btn-primary text-center"
				name="create">Ajouter</button>
			<%
				} else {
			%>
			<button type="submit" class="btn btn-primary text-center"
				name="update">Modifier</button>
			<%
				}
			%>
		</div>
	</div>
</form>

<%
	}
%>

<script>
	
<%if (verifMail.equals("err")) {%>
	alert("Insérez un mail valide");
<%}%>
	
<%if (verifMail.equals("no")) {%>
	alert("Ce mail existe déjà");
<%}%>
	
<%if (verifNameTeam.equals("err")) {%>
	alert("Veuillez insérer un nom de team");
<%}%>

<%if (verifNameTeam.equals("no")) {%>
alert("Cette team existe déjà");
<%}%>

</script>
