

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ 
   page import="java.util.List, org.model.Employe, org.model.Demand, java.util.ArrayList, org.json.simple.JSONArray"
    %>
<%
	/**
	* Liste des demandes d'un employé au format JSONArray
	* pour le script du calendrier.
	*/
   	JSONArray employeDemandsList = (JSONArray) request.getAttribute("employeDemandsList");
	/**
	* Liste des status possibles pour une demande.
	*/
	ArrayList<String> statusList  = request.getAttribute("statusList") != null ?(ArrayList<String>)request.getAttribute("statusList"): null;
	/**
	* Liste des raisons possibles pour une demande.
	*/
	ArrayList<String> reasonsList  = request.getAttribute("reasonsList") != null ?(ArrayList<String>)request.getAttribute("reasonsList"): null;
	/**
	* Message d'erreur dans le cadre d'une demande de congés invalide.
	*/
   	String errorAskingForDays = request.getAttribute("errorAskingForDays")!= null ? (String)request.getAttribute("errorAskingForDays") : "";
	/**
	* Booleén permettant de savoir sur quelle vue on se situe (calendrier ou tableau).
	*/
   	Boolean table= request.getAttribute("table")!=null?(Boolean)request.getAttribute("table"):false;
	/**
	* Liste des demandes d'un employé.
	*/
	ArrayList<Demand> demandsList  = request.getAttribute("demandsList") != null ?(ArrayList<Demand>)request.getAttribute("demandsList"): null;
%>
<!--  Si la liste de raison n'est pas vide -->
<% if(reasonsList != null){ %>

	<!-- Menu de la page d'accueil -->
	<div style="display: flex;border-bottom: 1px solid #110133;margin-bottom: 2em;justify-content: space-between;">
		<div style="display: flex;justify-content: space-around;">
			<i style="align-self: center; font-size: 2em; display: inline-block; color: primary;" class="far fa-question-circle" data-toggle="tooltip" data-placement="top" title="En positionant la souris sur un event du calendrier, il est possible d'y voir le commentaire associé."></i>
			<a style="align-self: center; font-size: 1em;" class="btn btn-primary" data-toggle="collapse" href="#collapseForm" role="button" aria-expanded="false" aria-controls="collapseForm">
	   			<i class="fas fa-plus"></i>
	   		</a>
	   		<p style="color: DC3545;"><%=errorAskingForDays %></p>	
		</div>	
		<nav>
			<form class="form-inline" action="Calendar" method="post">
             	<button class="nav-button" type="submit">Calendrier</button>
             	<button class="nav-button" name="tableDemand" type="submit">Tableau</button>            
          	</form>	
		</nav>
	</div>
	<!-- Fin menu de la page d'accueil -->
	
	<!-- Formulaire de création d'une demande de congés -->
	<div class="collapse" id="collapseForm">
	   <form class="form-co" action="Calendar" method="post">
	      <div class="form-row form-group">
	         <div class="col-md-2">
	            <label for="fromDate">Du : </label>
	            <input class="form-control" id="fromDate" name="fromDate" type="date" required></input>
	         </div>
	         <div class="col-md-2">
	            <label for="toDate"> au : </label>
	            <input class="form-control" id="toDate" name="toDate" type="date" required></input>
	         </div>
	         <div class="col-md-2">
	            <label for="reason"> Motif : </label>
	            <select class="form-control" name="reason" id="reason" required>
	               <% for (String s: reasonsList) { %>
	               <option value="<%=s%>"><%=s%></option>
	               <%}%>
	            </select>
	         </div>
	         <div class="col-md-2">
	            <label for="nbDays"> Durée : </label>
	            <input class="form-control" id="nbDays" name="nbDays" type="text" placeholder="Durée en jours" required></input>
	         </div>
	         <div class="col-md-3">
	         	<label for="askDaysOff"> Valider :  </label>
	            <button id="askDaysOff" class="btn btn-primary my-2 my-sm-0" type="submit" name="askDaysOff">Faire la demande</button>
	         </div>
	      </div>
	   </form>
	</div>
	<!-- FIn du formulaire de création d'une demande de congés -->
	
	<!-- Si on est sur la partie calendrier -->
	<% if(!table){ %>
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
		           plugins: ['dayGrid'],
		           header: {
		               left: 'prevYear,prev,next,nextYear today',
		               center: 'title',
		               right: 'dayGridMonth,dayGridWeek'
		           },
		           defaultDate: today,
		           navLinks: false,
		           editable: false,
		           eventLimit: true,
		           eventRender: function(info) {
		               $(info.el).tooltip({
		                   title: info.event.extendedProps.description || '',
		                   placement: 'top',
		                   trigger: 'hover',
		                   container: 'body'
		               });
		           },
		           events: <%= employeDemandsList %>
		       });
		       console.log(<%= employeDemandsList %>);
		       calendar.render();
		   });
		</script>
		<!-- Fin affichage et configuration du calendrier avec les événements d'un employé -->
		
		<!-- Dans le cas où l'on est sur la partie tableau de la page d'accueil -->
	<%}else{ %>
		<!-- Affichage du tableau des demandes d'un empoyé -->
		<form class="form-co" action="Calendar" method="post">
			<div class="form-row form-group">
			    <div class="col-md-3">
			      <select class="form-control" name="statusSearch">
			      	<option value="all">Tous les statuts</option>
			        <% for (String s: statusList) { %>
			          <option value="<%=s%>"><%=s%></option>
			        <% }%>
			      </select>
			    </div>
			    <div>
			  		<button class="btn btn-primary my-2 my-sm-0" type="submit" name="search">Rechercher</button>
			  	</div>
			</div>
		</form>
		
		<table class="table table-bordered table-striped">
		  <thead>
		    <tr>
		      <th scope="col">Départ</th>
		      <th scope="col">Retour</th>
		      <th scope="col">Durée</th>
		      <th scope="col">Motif</th>
		      <th scope="col">Status</th>
		    </tr>
		  </thead>
		  <tbody>
		  <!-- Pour chaque demande de la liste des demandes d'un employé, on l'affiche dans le tableau.
		  Si une ou plusieurs demandes sont dans l'état "pending", alors on rend éditable les différents champs
		  d'une demande. -->
		    <% for(Demand d : demandsList){ %>
		      <tr>
		      	<form class="form form-inline" action="Calendar" method="post">
			        <td>
			        	<% if(d.getStatus().equals("pending")){ %>
			        		<input class="form-control" id="fromDate" name="fromDate" type="date" required value= "<%=d.getStartDate() %>"></input>
			        	<%} else{ %>
			        		<%=d.getStartDate()%>
			        	<%}%>
			        </td>
			        <td>
			        	<% if(d.getStatus().equals("pending")){ %>
		            		<input class="form-control" id="toDate" name="toDate" type="date" required value = "<%=d.getEndDate() %>"></input>
			        	<%} else{ %>
			        		<%=d.getEndDate()%>
			        	<%}%>
			        </td>
			        <td>
			        	<% if(d.getStatus().equals("pending")){ %>
		            		<input class="form-control" id="nbDays" name="nbDays" type="text" value="<%= d.getNbDays() %>" required></input>
			        	<%} else{ %>
			        		<%=d.getNbDays()%>
			        	<%}%>
			        	
			        </td>
			        <td>
			        	<% if(d.getStatus().equals("pending")){ %> 	        	
		        			<select class="form-control" name="reason">
				       	 	<% for (String s: reasonsList) { %>
				        	  	<option value="<%=s%>" <%if(s.equals(d.getMotif())){%>selected<%}%>><%=s%></option>
				       	 	<%}%>
			    			</select> 
			    		<%} else{ %>
			        		<%=d.getMotif()%>
			        	<%}%>
			        </td>
			        <td>
			          <% if(d.getStatus().equals("pending")){ %>
			              <div style="display: flex !important;justify-content: space-around !important;">
			              		<button class="btn btn-danger" type="submit" name="update" value="<%=d.getId()%>">
				    				<i class="fa fa-edit" aria-hidden="true"></i>
				    			</button>
			              		<button class="btn btn-success" type="submit" name="delete" value="<%=d.getId()%>">
			              			<i class="fas fa-times" aria-hidden="true"></i>
			              		</button>
			              </div>
			          <%}else{%>
			            <%=d.getStatus()%>
			          <%}%>
			        </td>
			   </form>
		      </tr>
		    <%}%>
		  </tbody>
		
		</table>
		<!-- Fin affichage du tableau des demandes d'un empoyé -->
	<%} %>
<%} %>

