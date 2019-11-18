

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ 
page import="java.util.List, org.model.Employe, org.model.Demand, java.util.ArrayList, org.json.simple.JSONArray"
 %>
 <%
 JSONArray employeDemandsList = (JSONArray) request.getAttribute("employeDemandsList");
 System.out.println("calendar.jsp");
 System.out.println(employeDemandsList);
 %>

<%
 	ArrayList<String> reasonsList  = (ArrayList<String>)request.getAttribute("reasonsList");
%>

<script>
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: ['dayGrid'],
        header: {
            left: 'prevYear,prev,next,nextYear today',
            center: 'title',
            right: 'dayGridMonth,dayGridWeek'
        },
        defaultDate: '2019-11-13',
        navLinks: false, // can click day/week names to navigate views
        editable: false,
        eventLimit: true, // allow "more" link when too many events
        events: <%=employeDemandsList%>
    });
    console.log(<%=employeDemandsList%>);
    
    calendar.render();
});
</script>


<p>
  <a class="btn btn-primary" data-toggle="collapse" href="#collapseForm" role="button" aria-expanded="false" aria-controls="collapseForm">
    <i class="fas fa-plus"></i>
  </a>
</p>
<div class="collapse" id="collapseForm">
  	<form class="form-co" action="Calendar" method="post">
		<div class="form-row form-group">
			<div class="col-md-2">
				<label for="fromDate">Du : </label>
				<input class="form-control" id="fromDate" name="fromDate" type="date"></input>
			</div>
			<div class="col-md-2">
				<label for="toDate"> au : </label>
				<input class="form-control" id="toDate" name="toDate" type="date"></input>
	  		</div>
	  		<div class="col-md-2">
		      <select class="form-control" name="reason">
		        <% for (String s: reasonsList) { %>
		          <option value="<%=s%>"><%=s%></option>
		        <%}%>
		      </select>
		    </div>
		    <div class="col-md-2">
		      	<label for="nbDays"> Durée : </label>
				<input class="form-control" id="nbDays" name="nbDays" type="text" placeholder="Durée en jours"></input>
		    </div>
	    	<div>
		  		<button class="btn btn-primary my-2 my-sm-0" type="submit" name="askDaysOff">Faire la demande</button>
		  	</div>
		</div>
	</form>
</div>

<div id='calendar'></div>

