<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,org.model.Employe,org.model.Team"%>
<%
	//Liste des employés, teams et postes qui permettent de filtrer la liste des employés à afficher
	ArrayList<Employe> employesList = (ArrayList<Employe>) request.getAttribute("employesList");
	ArrayList<Team> teamsList = (ArrayList<Team>) request.getAttribute("teamsList");
	ArrayList<String> posteList = (ArrayList<String>) request.getAttribute("posteList");

	//Ces attributs permettent de garder les mêmes valeurs affichées lorsqu'une recherche a été effectuée
	String mail = request.getAttribute("mail") != null ? (String) request.getAttribute("mail") : "all";
	int team = request.getAttribute("team") != null && !request.getAttribute("team").equals("all")
			? Integer.parseInt(((String) request.getAttribute("team")))
			: -1;
	String poste = request.getAttribute("poste") != null ? (String) request.getAttribute("poste") : "all";

//formulaires de redirection vers la création d'un employé ou d'une team
%>
<div
	style="display: flex; border-bottom: 1px solid #110133; margin-bottom: 2em; justify-content: space-between;">
	<h3>Gestion des employés</h3>
	<nav>
		<form action="CreateUser" method="post">
			<button class="nav-button" type="submit">Créer un nouvel
				employé</button>
			<button class="nav-button" type="submit" name="createNewTeam">Créer
				une nouvelle team</button>
		</form>
	</nav>
</div>




<%
	//On affiche les éléments html suivants si la liste des employés n'est pas vide
	if (employesList != null) {
//On affiche le formulaire de recherche d'un employé
%>
<form class="form-co" action="ManageUser" method="post">
	<div class="form-row form-group">
		<i
			style="align-self: center; font-size: 2em; display: inline-block; color: primary;"
			class="far fa-question-circle" data-toggle="tooltip"
			data-placement="top"
			title="Il est possible de ne choisir qu'un employé, une team ou un poste. Si les trois sont sélectionnés, l'employé est choisi. Pour modifier un employé, cliquez sur l'icône modifier et remplissez le formulaire. Vous pouvez également supprimer un employé via le bouton supprimer"></i>

		<div class="col-md-3">
			<select class="form-control" name="team">
				<option value="all">Toutes les équipes</option>
				<%
					//On défini chaque élément de la liste team en option possible
					for (Team t : teamsList) {
				%>
				<option value="<%=t.getNoTeam()%>" <%if (t.getNoTeam() == team) {%>
					selected <%}%>><%=t.getName()%></option>
				<%
					}
				%>
			</select>
		</div>
		<div class="col-md-3">
			<select class="form-control" name="poste">
				<option value="all">Tous les postes</option>
				<%
					//On défini chaque élément de la liste team en option possible
					for (String s : posteList) {
				%>
				<option value="<%=s%>" <%if (s.equals(poste)) {%> selected <%}%>><%=s%></option>
				<%
					}
				%>
			</select>
		</div>
		<div class="col-md-3">
			<select class="form-control" name="employe">
				<option value="all">Tous les employés</option>
				<%
					//On défini chaque élément de la liste team en option possible
					for (Employe e : employesList) {
				%>
				<option value="<%=e.getMail()%>" <%if (e.getMail().equals(mail)) {%>
					selected <%}%>><%=e.getSurname()%>
					<%=e.getFirstName()%></option>
				<%
					}
				%>
			</select>
		</div>
		<div>
			<button class="btn btn-primary my-2 my-sm-0" type="submit"
				name="search">Rechercher</button>
		</div>
	</div>
</form>

<!-- Ce tableau permet d'afficher une liste d'employés qui peut dépendre de la recherche effectuée-->
<table class="table table-bordered table-striped">
	<thead>
		<tr>
			<th scope="col">Nom</th>
			<th scope="col">Prenom</th>
			<th scope="col">Mail</th>
			<th scope="col">Equipe</th>
			<th scope="col">Poste</th>
			<th scope="col">Modifier</th>
			<th scope="col">Supprimer</th>
		</tr>
	</thead>
	<tbody>
		<%
			for (Employe e : employesList) {
		%>
		<tr>
			<td><%=e.getSurname()%></td>
			<td><%=e.getFirstName()%></td>
			<td><%=e.getMail()%></td>
			<%
				//Cas où l'employé n'a pas de team, sinon afficher le numéro de la team
				if (e.getNbTeam() == 0) {
			%>
			<td>aucune</td>
			<%
				} else {
			%>
			<td><%=e.getNbTeam()%></td>
			<%
				}
			%>
			<%
				//Si l'employé est Responsable RH
				if (e.isRH() && e.isLeader()) {
			%>
			<td>RespoRH</td>
			<%
				}
			%>
			<%
				//Si l'employé est chef d'équipe
				if (!e.isRH() && e.isLeader()) {
			%>
			<td>TeamLeader</td>
			<%
				}
			%>
			<%
				//Si c'est un employé RH
				if (e.isRH() && !e.isLeader()) {
			%>
			<td>EmployeRH</td>
			<%
				}
			%>
			<%
				//Si c'est un employé
				if (!e.isRH() && !e.isLeader()) {
			%>
			<td>Employe</td>
			<%
				}
			%>
			<td>
			<!-- Formulaire de demande de modification d'un employé -->
				<div
					style="display: flex !important; justify-content: space-around !important;">
					<form class="form form-inline" action="CreateUser" method="post">
						<button class="btn btn-success" type="submit" name="modifier"
							style="font-size: 12px;" value="<%=e.getMail()%>">
							<i class="fa fa-edit" aria-hidden="true"></i>
						</button>
					</form>
				</div>
			</td>
			<td>
			<!-- Formulaire de demande de suppression d'un employé -->
				<div
					style="display: flex !important; justify-content: space-around !important;">
					<form class="form-inline" action="ManageUser" method="post"
						onsubmit="return confirm('Are you sure you want to delete <%=e.getFullName()%>?');">
						<button class="btn btn-danger" type="submit" name="delete"
							value="<%=e.getMail()%>">
							<i class="fa fa-window-close" style="font-size: 12px;"
								aria-hidden="true"></i>
						</button>
					</form>
				</div>
			</td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>
<%
	}
%>