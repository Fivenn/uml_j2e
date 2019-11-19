<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.ArrayList,org.model.Employe,org.model.Team,org.model.Demand"%>

<%
	ArrayList<String> daysOffPerTeam = (ArrayList<String>)request.getAttribute("daysOffPerTeam");
	ArrayList<String> daysOffPerReason = (ArrayList<String>)request.getAttribute("daysOffPerReason");
	ArrayList<String> daysOffPerMonth = (ArrayList<String>)request.getAttribute("daysOffPerMonth");
	ArrayList<String> daysOffPerJob = (ArrayList<String>)request.getAttribute("daysOffPerJob");
	Employe currentUser = (Employe)request.getSession().getAttribute("currentUser");
%>

<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
      	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
      	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
      	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>	
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Statistiques de vos équipes</title>
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	    <script type="text/javascript">
	      google.charts.load("current", {packages:["corechart"]});
	      google.charts.setOnLoadCallback(drawChartPerMonth);
	      google.charts.setOnLoadCallback(drawChartPerTeam);
	      google.charts.setOnLoadCallback(drawChartPerReason);
	      var dataDaysOffPerMonth = [['Month','NbDays']];
	      var dataDaysOffPerTeam = [['Team','NbDays']];
	      var dataDaysOffPerReason = [['Reason','NbDays']];
	      
	      function drawChartPerMonth() {
	        var data = google.visualization.arrayToDataTable(getDataPerMonth(dataDaysOffPerMonth));
	        var options = {
	          title: 'Nombre de congés par mois (toutes équipes confondues)',
	          is3D: true,
	        };
	
	        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
	        chart.draw(data, options);
	      }
	      
	      function getDataPerMonth(dataPerMonth){
		      for(let i of <%=daysOffPerMonth%>){
		    	 dataPerMonth.push([getMonth(i[0]), i[1]]);
		      }
		      return dataPerMonth;
	      }
	      
	      function getDataPerJob(dataPerJob){
		      for(let i of <%=daysOffPerJob%>){
		    	 dataPerJob.push([i[0].toString(), i[1]]);
		      }
		      return dataPerJob;
	      }
	      
	      function drawChartPerReason() {
		        var data = google.visualization.arrayToDataTable(getDataPerReason(dataDaysOffPerReason));
		        var options = {
		          title: 'reason',
		          is3D: true,
		        };
		
		        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d_3'));
		        chart.draw(data, options);
		      }
	      
	      function getDataPerReason(dataPerReason){
		      for(let i of <%=daysOffPerReason%>){
		    	 dataPerReason.push([i[0].toString(), i[1]]);
		      }
		      return dataPerReason;
	      }
	      
	      function drawChartPerTeam() {
	        var data = google.visualization.arrayToDataTable(getDataPerTeam(dataDaysOffPerTeam));
	        var options = {
	          title: 'team',
	          is3D: true,
	        };
	
	        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d_2'));
	        chart.draw(data, options);
	      }
	      
	      function getDataPerTeam(dataPerTeam){
		      for(let i of <%=daysOffPerTeam%>){
		    	 dataPerTeam.push([i[0].toString(), i[1]]);
		      }
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
		<div id="piechart_3d" style="width: 700px; height: 500px;"></div>
		<div id="piechart_3d_2" style="width: 700px; height: 500px;"></div>
		<div id="piechart_3d_3" style="width: 700px; height: 500px;"></div>
	</body>
</html>