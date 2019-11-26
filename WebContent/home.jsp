

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@page import="org.model.Employe"%>
<%
  //Attribut représentant l'utilisateur actuel du site
   	Employe currentUser = (Employe)request.getSession().getAttribute("currentUser");
   	//Attribut représentant la page actuelle
	String currentPage = request.getAttribute("currentPage") == null?"calendar":(String)request.getAttribute("currentPage");
   	//Attribut permettant de savoir si l'utilisateur est entré en mode RH ou non
   	String currentMode = (String)request.getAttribute("currentMode");
   %>
<html>
   <head>
   		<!-- Feuille de style css + bootstrap -->
      <link type="text/css" rel="stylesheet" href="style.css" />
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
      <link href='ressources/fullcalendar/core/main.css' rel='stylesheet' />
      <link href='ressources/fullcalendar/daygrid/main.css' rel='stylesheet' />
      <link href="ressources/fontawesome/css/all.css" rel="stylesheet">
      <!-- Script importé bootstrap + calendar -->
      <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
      <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
      <script src='ressources/fullcalendar/core/main.js'></script>
      <script src='ressources/fullcalendar/daygrid/main.js'></script>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>Days Off Manager</title>
   </head>
   <body>
      <% 
      	//Si l'utilisateur est bien connecté
      	if (currentUser != null ){ 
      %>
      <header>
      <!--  Affichage du menu nav en fonction du role de l'utilisateur -->
         <nav class="navbar navbar-light bg-light">
            <a class="navbar-brand">
            <%=currentUser.getFullName()%>
            </a>
            <% if(currentMode != "RH"){ %>
            <a class="nav-item nav-link active" href="/DaysOffManager/Calendar">Mon calendrier</a>
            <% if(currentUser.isLeader()){ %>
            <a class="nav-item nav-link active" href="/DaysOffManager/Team">Mon équipe</a>
            <% } %>
            <a class="nav-item nav-link active" href="/DaysOffManager/MyProfil">Profil</a>
            <% }else{ %>
            <a class="nav-item nav-link active" href="/DaysOffManager/ManageDemand">Gestion des demandes</a>
            <a class="nav-item nav-link active" href="/DaysOffManager/ManageUser">Gestion des employés</a>
            <% } %>
            <% if(currentUser.isRH()){ %>
	            <%if(currentMode == "RH"){%>
	            <form class="form-inline" action="Calendar" method="post">
	               <button class="btn btn-outline-primary" name="goToRHMode" type="submit">Mode Employé</button>
	            </form>
	            <%}else{%>
	            <form class="form-inline" action="ManageDemand" method="post">
	               <button class="btn btn-outline-primary" name="goToRHMode" type="submit">Mode RH</button>
	            </form>
	            <%} %>
            <% } %>
            <form class="form-inline" action="Home" method="post">
               <button class="btn btn-outline-success" name="deconnection" type="submit">Déconnexion</button>
            </form>
         </nav>
      </header>
      <main>
      <!--  Affichage de la page intérieur en fonction de la page selectionnée, le paramétre passé n'a pas vraiment d'importance mais nous n'avons pas réussi à fonctionner sans. -->
         <% if(currentPage == "calendar"){%>
         <jsp:include page="calendar.jsp">
            <jsp:param name="year" value="2010" />
         </jsp:include>
         <%}else if(currentPage == "team"){%>
         <jsp:include page="team.jsp">
            <jsp:param name="year" value="2010" />
         </jsp:include>
         <%}else if(currentPage == "help"){%>
         <jsp:include page="help.jsp">
            <jsp:param name="year" value="2010" />
         </jsp:include>
         <%}else if(currentPage == "createUser"){%>
         <jsp:include page="createUser.jsp">
            <jsp:param name="year" value="2010" />
         </jsp:include>
         <%}else if(currentPage == "manageDemand"){%>
         <jsp:include page="manageDemand.jsp">
            <jsp:param name="year" value="2010" />
         </jsp:include>
         <%}else if(currentPage == "manageUser"){%>
         <jsp:include page="manageUser.jsp">
            <jsp:param name="year" value="2010" />
         </jsp:include>
         <%}else if(currentPage == "profil"){%>
         <jsp:include page="profil.jsp">
            <jsp:param name="year" value="2010" />
         </jsp:include>
         <%}else if(currentPage == "stats"){%>
         <jsp:include page="stats.jsp">
            <jsp:param name="year" value="2010" />
         </jsp:include>
         <%}else{%>
         <jsp:include page="calendar.jsp">
            <jsp:param name="year" value="2010" />
         </jsp:include>
         <%}%>
      </main>
      <footer>
         <p style="margin: 0px; margin-left: 1em;"><i class="far fa-copyright"></i> Days Off Manager est un projet réalisé par Maëlle Heyrendt, Clélia Le van, Nicolas Sueur et Mickaël Tisserand dans le cadre du Projet JEE en IMR2 à l'Enssat.</p>
         <a style="margin-right: 1em; font-size: 1.5em; " href="/DaysOffManager/Help"><i class="far fa-question-circle"></i> Aide</a>
      </footer>
      <% } %>
   </body>
</html>

