<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,org.model.Employe,org.model.Team"%>
<%
	ArrayList<Employe> employesList = (ArrayList<Employe>) request.getAttribute("employesList");
	ArrayList<Team> teamsList = (ArrayList<Team>) request.getAttribute("teamsList");
	ArrayList<String> posteList = (ArrayList<String>) request.getAttribute("posteList");

	String mail = request.getAttribute("mail") != null ? (String) request.getAttribute("mail") : "all";
	int team = request.getAttribute("team") != null && !request.getAttribute("team").equals("all")
			? Integer.parseInt(((String) request.getAttribute("team")))
			: -1;
	String poste = request.getAttribute("poste") != null ? (String) request.getAttribute("poste") : "all";
%>

<div
	style="display: flex; border-bottom: 1px solid #110133; margin-bottom: 2em; justify-content: space-between;">
	<h3>Gestion des employés</h3>
	<nav>
		<form action="CreateUser" method="post">
			<button class="nav-button" type="submit">Créer un nouvel
				employé</button>
		</form>
		<form action="CreateUser" method="post">
			<button class="nav-button" type="submit" name="createNewTeam">Créer
				une nouvelle team</button>
		</form>
	</nav>
</div>




<%
	if (employesList != null) {
%>

<form class="form-co" action="ManageUser" method="post">
	<div class="form-row form-group">
		<i
			style="align-self: center; font-size: 2em; display: inline-block; color: primary;"
			class="far fa-question-circle" data-toggle="tooltip"
			data-placement="top"
			title="Il est possible de ne choisir qu'un employé ou une team. Si les deux sont sélectionnés, l'employé est choisi. Pour modifier le motif d'une demande, choisir le motif et cliquer sur modifier."></i>

		<div class="col-md-3">
			<select class="form-control" name="team">
				<option value="all">Toutes les équipes</option>
				<%
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
				if (e.isRH() && e.isLeader()) {
			%>
			<td>RespoRH</td>
			<%
				}
			%>
			<%
				if (!e.isRH() && e.isLeader()) {
			%>
			<td>TeamLeader</td>
			<%
				}
			%>
			<%
				if (e.isRH() && !e.isLeader()) {
			%>
			<td>EmployeRH</td>
			<%
				}
			%>
			<%
				if (!e.isRH() && !e.isLeader()) {
			%>
			<td>Employe</td>
			<%
				}
			%>
			<td>
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