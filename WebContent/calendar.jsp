

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ 
   page import="java.util.List, org.model.Employe, org.model.Demand, java.util.ArrayList, org.json.simple.JSONArray"
    %>
<%
   JSONArray employeDemandsList = (JSONArray) request.getAttribute("employeDemandsList");
   ArrayList<String> reasonsList  = request.getAttribute("reasonsList") != null ?(ArrayList<String>)request.getAttribute("reasonsList"): null;
   String errorAskingForDays = request.getAttribute("errorAskingForDays")!= null ? (String)request.getAttribute("errorAskingForDays") : "";
   %>
<% if(reasonsList != null){ %>
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
<div>
   <i style="align-self: center; font-size: 2em; display: inline-block; color: primary;" class="far fa-question-circle" data-toggle="tooltip" data-placement="top" title="En positionant la souris sur un event du calendrier, il est possible d'y voir le commentaire associé."></i>
</div>
<div>
   <a class="btn btn-primary" data-toggle="collapse" href="#collapseForm" role="button" aria-expanded="false" aria-controls="collapseForm">
   <i class="fas fa-plus"></i>
   </a>
   <p style="color: DC3545;"><%=errorAskingForDays %></p>
</div>
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
         <div>
            <button class="btn btn-primary my-2 my-sm-0" type="submit" name="askDaysOff">Faire la demande</button>
         </div>
      </div>
   </form>
</div>
<div id='calendar'></div>
<%} %>

