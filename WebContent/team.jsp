

<%@page import="org.json.simple.JSONArray, java.util.ArrayList, org.model.Employe"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
   JSONArray teamDemandsList = (JSONArray) request.getAttribute("teamDemandsList");
   ArrayList<Employe> employeesList = (ArrayList<Employe>) request.getAttribute("employeesList");
   
   String mail = request.getAttribute("mail") != null ? (String)request.getAttribute("mail") : "all";
   %>
<div>
   <i style="align-self: center; font-size: 2em; display: inline-block; color: primary;" class="far fa-question-circle" data-toggle="tooltip" data-placement="top" title="En positionant la souris sur un event du calendrier, il est possible d'y voir le commentaire associé."></i>
</div>
<div>
   <a class="btn btn-primary" data-toggle="collapse" href="#collapseForm" role="button" aria-expanded="false" aria-controls="collapseForm">
   <i class="fas">Filter par employé</i>
   </a>
</div>
<div class="collapse" id="collapseForm">
   <form class="form-co" action="Team" method="post">
      <div class="form-row form-group">
         <div class="col-md-2">
            <label for="reason"> Motif : </label>
            <select class="form-control" name="employe">
            <option value="all">Tous les employés</option>
               <% for (Employe e: employeesList) { %>
               <option value="<%=e.getMail()%>" <%if(e.getMail().equals(mail)){%>selected<%}%>>
                  <%=e.getSurname()%> <%=e.getFirstName()%>
               </option>
               <%}%>
            </select>
         </div>
         <div>
            <button class="btn btn-primary my-2 my-sm-0" type="submit" name="filter">Filter</button>
         </div>
      </div>
   </form>
</div>
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
           navLinks: false, // can click day/week names to navigate views
           editable: false,
           eventLimit: true, // allow "more" link when too many events
           eventRender: function(info) {
               $(info.el).tooltip({
                   title: info.event.extendedProps.description || '',
                   placement: 'top',
                   trigger: 'hover',
                   container: 'body'
               });
           },
           events: <%= teamDemandsList %>
       });
       calendar.render();
       console.log(<%= teamDemandsList %>);
   });
</script>

