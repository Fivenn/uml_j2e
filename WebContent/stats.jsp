<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.ArrayList,java.util.List,org.model.Employe,org.model.Team,org.model.Demand"%>

<%
	//Array de données pour les statistiques.
	ArrayList<List<String>>daysOffPerTeam = (ArrayList<List<String>>)request.getAttribute("daysOffPerTeam");
	ArrayList<List<String>> daysOffPerReason = (ArrayList<List<String>>)request.getAttribute("daysOffPerReason");
	ArrayList<List<String>> daysOffPerMonth = (ArrayList<List<String>>)request.getAttribute("daysOffPerMonth");
	ArrayList<List<String>> daysOffPerJob = (ArrayList<List<String>>)request.getAttribute("daysOffPerJob");
	Employe currentUser = (Employe)request.getSession().getAttribute("currentUser");
%>

<head>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
   		//Script permettant d'afficher les différents google chart, qui sont des diagrammes représentant les stats.
     google.charts.load("current", {packages:["corechart"]});
     google.charts.setOnLoadCallback(drawChartPerMonth);
     google.charts.setOnLoadCallback(drawChartPerTeam);
     google.charts.setOnLoadCallback(drawChartPerReason);
     google.charts.setOnLoadCallback(drawChartPerJob);
     
     //Différents tableaux de données pour les stats, initialisés avec les labels.
     var dataDaysOffPerMonth = [['Month','NbDays']];
     var dataDaysOffPerTeam = [['Team','NbDays']];
     var dataDaysOffPerReason = [['Reason','NbDays']];
     var dataDaysOffPerJob = [['Fonction','NbDays']];
     
     //Fonction permettant de construire le diagramme des mois
     function drawChartPerMonth() {
       var data = google.visualization.arrayToDataTable(getDataPerMonth(dataDaysOffPerMonth));
       var options = {
      		title: 'Nombre de congés par mois (*)',
           width: 600,
           height: 400,
           legend: { position: 'bottom' },
       };

       var chart = new google.visualization.ColumnChart(document.getElementById('chartPerMonth'));
       chart.draw(data, options);
     }
     
     //Fonction permettant de mettre en forme les données pour les mois
     function getDataPerMonth(dataPerMonth){
      for(let i of <%=daysOffPerMonth%>){
    	 dataPerMonth.push([getMonth(i[0]), i[1]]);
      }
      return dataPerMonth;
     }
     
     //Fonction permettant de construire le diagramme des métiers
     function drawChartPerJob() {
        var data = google.visualization.arrayToDataTable(getDataPerJob(dataDaysOffPerJob));
        var options = {
        	title: 'Nombre de congés par catégorie (*)',
        	width: 600,
            height: 400,
          	pieHole: 0.4,
          	legend: { position: 'bottom' },
        };

        var chart = new google.visualization.PieChart(document.getElementById('chartPerJob'));
        chart.draw(data, options);
      }
     
     //Fonction permettant de mettre en forme les données pour les métiers
     function getDataPerJob(dataPerJob){
      <% for(int i=0;i<daysOffPerJob.size();i++){%>
    	 dataPerJob.push([getFonction("<%=daysOffPerJob.get(i).get(0).toString()%>"),<%=daysOffPerJob.get(i).get(1)%>]);
      <%}%>
    	 //dataPerJob.push([getFonction(i[0]), i[1]]);
    	return dataPerJob;
     }
     
     //Fonction permettant d'afficher le graph pour les motifs
     function drawChartPerReason() {
        var data = google.visualization.arrayToDataTable(getDataPerReason(dataDaysOffPerReason));
        var options = {
          title: 'Nombre de congés par motif (*)',
          width: 600,
          height: 400,
          pieHole: 0.4,
          legend: { position: 'bottom' },
        };

        var chart = new google.visualization.PieChart(document.getElementById('chartPerReason'));
        chart.draw(data, options);
      }
     
     //Fonction permettant de mettre en forme les données pour les motifs
     function getDataPerReason(dataPerReason){
      <% for(int i=0;i<daysOffPerReason.size();i++){%>
      //Cast obligatoire pour ne pas que le js interprète en tant que variable.
    	 dataPerReason.push(["<%=daysOffPerReason.get(i).get(0).toString()%>",<%=daysOffPerReason.get(i).get(1)%>]);
      <%}%>
      return dataPerReason;
     }
     
   	//Fonction permettant d'afficher le graph pour les motifs
     function drawChartPerTeam() {
       var data = google.visualization.arrayToDataTable(getDataPerTeam(dataDaysOffPerTeam));
       var options = {
         title: 'Nombre de congés par équipe (*)',
         width: 600,
         height: 400,
         is3D: true,
         legend: { position: 'bottom' },
       };

       var chart = new google.visualization.PieChart(document.getElementById('chartPerTeam'));
       chart.draw(data, options);
     }
     
     //Fonction permettant de mettre en forme les données pour les équipes
     function getDataPerTeam(dataPerTeam){
      <% for(int i=0;i<daysOffPerTeam.size();i++){%>
      //Cast obligatoire pour ne pas que le js interprète en tant que variable.
      	dataPerTeam.push(["<%=daysOffPerTeam.get(i).get(0)!= null ? daysOffPerTeam.get(i).get(0).toString() : "Sans team"%>",<%=daysOffPerTeam.get(i).get(1)%>]);
      <%}%>
      return dataPerTeam;
     }
     
     //Fonction qui renvoie le mois en français 
     function getMonth(numMois){
   	  let b = "";
   	  switch(numMois){
           case 1: b = "Janvier";break;
           case 2: b = "Fevrier";break;
           case 3: b = "Mars";break;
           case 4: b = "Avril";break;
           case 5: b = "Mai";break;
           case 6: b = "Juin"; break;
           case 7: b = "Juillet";break;
           case 8: b = "Aout";break;
           case 9: b = "Septembre";break;
           case 10: b = "Octobre";break;
           case 11: b = "Novembre";break;
           case 12: b = "Decembre";break;
         }
   	  return b;
     }
     //Fonction qui renvoie le poste de l'utilisateur
     function getFonction(fonction){
   	  let b = "";
   	  switch(fonction){
           case "TeamLeader": b = "Chef d'équipe";break;
           case "RespoRH": b = "Responsable RH";break;
           case "EmployeRH": b = "Employé RH";break;
           case "Employe": b = "Employe";break;
         }
   	  return b;
     }
     
   </script>
</head>
<body>
	<div>
		<h5><%=currentUser.getFirstName()%>, retrouvez ci-après les statistiques de vos équipes.</h5>
		<p>
		Analysez la prise de congés de vos équipes. Retrouvez les motifs de congés les plus fréquents, les périodes de congés les plus demandées ou encore
		la moyenne de congés par équipe.
		</p>
	</div>
	<div style="display: flex;justify-content: center;">
		<div>
			<div class="card">
	              	<div class="card-body">
					<div id="chartPerMonth"></div>
					<p>(*) Ces nombres de congés ont été calculés pour toutes équipes confondues.</p>
				</div>
			</div>
			<div class="card">
	              	<div class="card-body">
					<div id="chartPerJob"></div>
					<p>(*) Ces nombres de congés ont été calculés pour toutes équipes confondues.</p>
				</div>
			</div>
		</div>
		<div>
			<div class="card">
	              	<div class="card-body">
					<div id="chartPerTeam"></div>
					<p>(*) Ces nombres de congés ont été calculés pour tous postes confondus.</p>
				</div>
			</div>
			<div class="card">
	              	<div class="card-body">
					<div id="chartPerReason"></div>
					<p>(*) Ces nombres de congés ont été calculés pour toutes équipes confondues.</p>
				</div>
			</div>
		</div>
	</div>
</body>