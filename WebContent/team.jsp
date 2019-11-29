

<%@page
	import="org.json.simple.JSONArray, java.util.ArrayList, org.model.Employe, org.model.Demand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	/**
	* Liste des demandes des employés d'une équipe au format JSONArray
	* pour le script du calendrier.
	*/
	JSONArray teamDemandsList = (JSONArray) request.getAttribute("teamDemandsList");
	/**
	* Liste des employés d'une équipe.
	*/
	ArrayList<Employe> employeesList = (ArrayList<Employe>) request.getAttribute("employeesList");
	/**
	* Chaine de caractères contenant soit le mail d'une personne d'une équipe
	* soit la chaine "all" pour tous les membre d'une équipe.
	*/
	String mail = request.getAttribute("mail") != null ? (String) request.getAttribute("mail") : "all";
	/**
	* Booléen permettant de savoir sur quelle vue on se situe (calendrier ou tableau).
	*/
	Boolean table = request.getAttribute("table") != null ? (Boolean) request.getAttribute("table") : false;
	/**
	* Liste des demandes des employés d'une équipe.
	*/
	ArrayList<Demand> demandsList = request.getAttribute("demandsList") != null
			? (ArrayList<Demand>) request.getAttribute("demandsList")
			: null;
%>
<div
	style="display: flex; border-bottom: 1px solid #110133; margin-bottom: 2em; justify-content: space-between;">
	
	<!-- Si on ne situe pas sur la vue du tableau d'équipe -->
	<%
		if (!table) {
	%>
	<!-- Menu de la page d'équipe -->
	<!--  On affiche le bouton "Filter par employé" dans le menu de la vue. -->
	<div style="display: flex; justify-content: space-around;">
		<i
			style="align-self: center; font-size: 2em; display: inline-block; color: primary;"
			class="far fa-question-circle" data-toggle="tooltip"
			data-placement="top"
			title="En positionant la souris sur un event du calendrier, il est possible d'y voir le commentaire associé."></i>
		<a style="align-self: center; font-size: 1em;" class="btn btn-primary"
			data-toggle="collapse" href="#collapseForm" role="button"
			aria-expanded="false" aria-controls="collapseForm"> <i
			class="fas">Filter par employé</i>
		</a>
	</div>
	<!--  Fin on affiche le bouton "Filter par employé" dans le menu de la vue. -->
	<%
		}
	%>
	<nav>
		<form class="form-inline" action="Team" method="post">
			<button class="nav-button" type="submit">Calendrier</button>
			<button class="nav-button" name="tableTeamDemand" type="submit">Tableau</button>
		</form>
	</nav>
</div>
<!-- Fin menu de la page d'équipe -->

<!-- Filtrage employé pour calendrier -->
<div class="collapse" id="collapseForm">
	<form class="form-co" action="Team" method="post">
		<div class="form-row form-group" style="align-items: baseline;margin-left:3em;">
				<label for="reason"> Employé : </label> 
				<select class="form-control" name="employe" style="width: 50%;">
					<option value="all">Tous les employés</option>
					<%
						for (Employe e : employeesList) {
					%>
					<option value="<%=e.getMail()%>"
						<%if (e.getMail().equals(mail)) {%> selected <%}%>>
						<%=e.getSurname()%>
						<%=e.getFirstName()%>
					</option>
					<%
						}
					%>
				</select>
			<div>
				<button class="btn btn-primary my-2 my-sm-0" type="submit"
					name="filter">Filter</button>
			</div>
		</div>
	</form>
</div>
<!-- Filtrage employé pour calendrier -->
<%
	if (!table) {
%>
<!-- Affichage et configuration du calendrier avec les événements d'un employé -->
<div id='calendar'></div>
<script>
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0');
	var yyyy = today.getFullYear();

	today = yyyy + '-' + mm + '-' + dd;

	document.addEventListener('DOMContentLoaded', function() {
		var calendarEl = document.getElementById('calendar');

		var calendar = new FullCalendar.Calendar(calendarEl, {
			plugins : [ 'dayGrid' ],
			header : {
				left : 'prevYear,prev,next,nextYear today',
				center : 'title',
				right : 'dayGridMonth,dayGridWeek'
			},
			defaultDate : today,
			navLinks : false, // can click day/week names to navigate views
			editable : false,
			eventLimit : true, // allow "more" link when too many events
			eventRender : function(info) {
				$(info.el).tooltip({
					title : info.event.extendedProps.description || '',
					placement : 'top',
					trigger : 'hover',
					container : 'body'
				});
			},
			events :
<%=teamDemandsList%>
	});
		calendar.render();
		console.log(
<%=teamDemandsList%>
	);
	});
</script>
<!-- Fin affichage et configuration du calendrier avec les événements d'un employé -->
<%
	} else {
%>
<!-- Affichage du tableau des demandes des employés d'une équipe -->
<table class="table table-bordered table-striped">
	<thead>
		<tr>
			<th scope="col">Nom Prénom</th>
			<th scope="col">Départ</th>
			<th scope="col">Retour</th>
			<th scope="col">Durée</th>
			<th scope="col">Motif</th>
			<th scope="col">Status</th>
		</tr>
	</thead>
	<tbody>
		<%
			for (Demand d : demandsList) {
		%>
		<tr>
			<td><%=d.getEmploye().getSurname()%> <%=d.getEmploye().getFirstName()%>
			<td><%=d.getStartDate()%></td>
			<td><%=d.getEndDate()%></td>
			<td><%=d.getNbDays()%></td>
			<td><%=d.getMotif()%></td>
			<td><% if(d.getStatus().equals("approved")){%>
			      Accepté
			    <%}else if(d.getStatus().equals("refused")){%>
			      Refusé
			    <%}%>
			</td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>
<!-- Fin affichage du tableau des demandes des employés d'une équipe -->
<%
	}
%>

