<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.ArrayList,java.util.List,org.model.Employe,org.model.Team,org.model.Demand"%>

<%
	ArrayList<List<String>>daysOffPerTeam = (ArrayList<List<String>>)request.getAttribute("daysOffPerTeam");
	ArrayList<List<String>> daysOffPerReason = (ArrayList<List<String>>)request.getAttribute("daysOffPerReason");
	ArrayList<List<String>> daysOffPerMonth = (ArrayList<List<String>>)request.getAttribute("daysOffPerMonth");
	ArrayList<List<String>> daysOffPerJob = (ArrayList<List<String>>)request.getAttribute("daysOffPerJob");
	Employe currentUser = (Employe)request.getSession().getAttribute("currentUser");
%>

<head>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
     google.charts.load("current", {packages:["corechart"]});
     google.charts.setOnLoadCallback(drawChartPerMonth);
     google.charts.setOnLoadCallback(drawChartPerTeam);
     google.charts.setOnLoadCallback(drawChartPerReason);
     google.charts.setOnLoadCallback(drawChartPerJob);
     var dataDaysOffPerMonth = [['Month','NbDays']];
     var dataDaysOffPerTeam = [['Team','NbDays']];
     var dataDaysOffPerReason = [['Reason','NbDays']];
     var dataDaysOffPerJob = [['Fonction','NbDays']];
     
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
     
     function getDataPerMonth(dataPerMonth){
      for(let i of <%=daysOffPerMonth%>){
    	 dataPerMonth.push([getMonth(i[0]), i[1]]);
      }
      return dataPerMonth;
     }
     
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
     
     function getDataPerJob(dataPerJob){
      <% for(int i=0;i<daysOffPerJob.size();i++){%>
    	 dataPerJob.push([getFonction("<%=daysOffPerJob.get(i).get(0).toString()%>"),<%=daysOffPerJob.get(i).get(1)%>]);
      <%}%>
    	 //dataPerJob.push([getFonction(i[0]), i[1]]);
    return dataPerJob;
     }
     
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
     
     function getDataPerReason(dataPerReason){
      <% for(int i=0;i<daysOffPerReason.size();i++){%>
    	 dataPerReason.push(["<%=daysOffPerReason.get(i).get(0).toString()%>",<%=daysOffPerReason.get(i).get(1)%>]);
      <%}%>
      return dataPerReason;
     }
     
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
     
     function getDataPerTeam(dataPerTeam){
      <% for(int i=0;i<daysOffPerTeam.size();i++){%>
      	dataPerTeam.push(["<%=daysOffPerTeam.get(i).get(0)!= null ? daysOffPerTeam.get(i).get(0).toString() : "Sans team"%>",<%=daysOffPerTeam.get(i).get(1)%>]);
      <%}%>
      return dataPerTeam;
     }
     
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