<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,org.model.Employe,org.model.Team,org.model.Demand"%>

<%
	ArrayList<Team> teamsList = (ArrayList<Team>)request.getAttribute("teamsList");
	ArrayList<Employe> employesList  = (ArrayList<Employe>)request.getAttribute("employesList");
 	ArrayList<String> statusList  = (ArrayList<String>)request.getAttribute("statusList");
 	ArrayList<String> reasonsList  = (ArrayList<String>)request.getAttribute("reasonsList");
 	ArrayList<Demand> demandsList  = (ArrayList<Demand>)request.getAttribute("demandsList");
 	 
 	String mail = request.getAttribute("mail")!= null ? (String)request.getAttribute("mail") : "all";
 	int team = request.getAttribute("team")!=null &&  !request.getAttribute("team").equals("all")?Integer.parseInt(((String) request.getAttribute("team"))):-1;
 	String status = request.getAttribute("status")!=null?(String)request.getAttribute("status"):"all";
 	
 	Boolean stats = request.getAttribute("stats")!=null?(Boolean)request.getAttribute("stats"):false;
%>
<%
	if(teamsList != null){
%>
		<div style="display: flex;border-bottom: 1px solid #110133;margin-bottom: 2em;justify-content: space-between;">
			<h3>
				Gestion des demandes
			</h3>	
			<nav>
				<form class="form-inline" action="ManageDemand" method="post">
	               <button class="nav-button" type="submit">Liste des demandes</button>
	               <button class="nav-button" name="statsDemand" type="submit">Statistiques</button>            
	            </form>	
			</nav>
		</div>
	
	
	<% if(!stats){ %>
		<form class="form-co" action="ManageDemand" method="post">
			<div class="form-row form-group">
				<i style="align-self: center; font-size: 2em; display: inline-block; color: primary;" class="far fa-question-circle" data-toggle="tooltip" data-placement="top" title="Il est possible de ne choisir qu'un employé ou une team. Si les deux sont sélectionnés, l'employé est choisi."></i>
				<div class="col-md-3">
				    <select class="form-control" name="employe">
				    	<option value="all">Tous les employés</option>
				      <%
				    	for (Employe e: employesList) {
				    	%>
				    		<option value="<%=e.getMail()%>" <%if(e.getMail().equals(mail)){%>selected<%}%>><%=e.getSurname()%> <%=e.getFirstName()%></option>
				    	<%
				    	}
				    	%>
				    </select>
		  		</div>
			  	<div class="col-md-3">
			      <select class="form-control" name="team">
			      	<option value="all">Toutes les équipes</option>
			        <%
			        for (Team t: teamsList) {
			        %>
			          <option value="<%=t.getNoTeam()%>" <%if(t.getNoTeam() == team){%>selected<%}%>><%=t.getName()%></option>
			        <%
			        }
			        %>
			      </select>
			  	</div>
			    <div class="col-md-3">
			      <select class="form-control" name="status">
			      	<option value="all">Tous les statuts</option>
			        <%
			        for (String s: statusList) {
			        %>
			          <option value="<%=s%>" <%if(s.equals(status)){%>selected<%}%> ><%=s%></option>
			        <%
			        }
			        %>
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
		      <th scope="col">Date</th>
		      <th scope="col">Nom</th>
		      <th scope="col">Prenom</th>
		      <th scope="col">Départ</th>
		      <th scope="col">Retour</th>
		      <th scope="col">Durée</th>
		      <th scope="col">Motif</th>
		      <th scope="col">Status</th>
		    </tr>
		  </thead>
		  <tbody>
		    <% for(Demand d : demandsList){ %>
		      <tr>
		      	<td><%=d.getRequestDate().substring(0,10)%></td>
		        <td><%=d.getEmploye().getSurname()%></td>
		        <td><%=d.getEmploye().getFirstName()%></td>
		        <td><%=d.getStartDate()%></td>
		        <td><%=d.getEndDate()%></td>
		        <td><%=d.getNbDays()%></td>
		        <td> 
		        	<form>
		        		<select class="form-control" name="<%="status" + d.getId()%>">
				       	 <% for (String s: statusList) { %>
				        	  <option value="<%=s%>" <%if(s.equals(d.getMotif())){%>selected<%}%>><%=s%></option>
				       	 <%}%>
			    		</select> 
		        	</form>   
		        </td>
		        <td>
		          <% if(d.getStatus().equals("pending")){ %>
		              <div style="display: flex !important;justify-content: space-around !important;">
		              	<form class="form form-inline" action="ManageDemand" method="post">
		              		<button class="btn btn-success" type="submit" name="approved" value="<%=d.getId()%>"><i class="fas fa-check" aria-hidden="true"></i></button>
						</form>
						<a class="btn btn-primary" data-toggle="collapse" href="#collapseComment<%=d.getId()%>" role="button" aria-expanded="false" aria-controls="collapseForm">
						   <i class="fas fa-comment" aria-hidden="true"></i>
						</a>
						<a class="btn btn-danger" data-toggle="collapse" href="#collapseRefused<%=d.getId()%>" role="button" aria-expanded="false" aria-controls="collapseForm">
						   <i class="fas fa-times" aria-hidden="true"></i>
						</a>
		              </div>
		              
					  <div class="collapse" id="collapseComment<%=d.getId()%>">
					  	<form class="form form-inline" action="ManageDemand" method="post" style="display: flex !important;justify-content: space-around !important;">  
							<input class="form-control" name="comment" type="text" placeholder="Laisser un commentaire" required></input>
		              		<button class="btn btn-primary" style="color:#fff;" type="submit" name="approvedCom" value="<%=d.getId()%>"><i class="fas fa-comment" aria-hidden="true"></i></button>
					  	</form>
					  </div>
					  <div class="collapse" id="collapseRefused<%=d.getId()%>">
					  	<form class="form form-inline" action="ManageDemand" method="post" style="display: flex !important;justify-content: space-around !important;">  
					  		<input class="form-control" id="nbDays" name="comment" type="text" placeholder="Laisser un commentaire" required></input>  
		              		<button class="btn btn-danger" type="submit" name="refused" value="<%=d.getId()%>"><i class="fas fa-times" aria-hidden="true"></i></button>
					  	</form>
					  </div>
		            
		          <%}else{%>
		            <%=d.getStatus()%>
		          <%}%>
		        </td>
		      </tr>
		    <%}%>
		
		  </tbody>
		</table>
	<%} else {%>
		<jsp:include page="stats.jsp">
		   <jsp:param name="year" value="2010" />
		</jsp:include>
	<%} 
}%>