<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List,org.model.Employe"%>


<%
	Employe currentUser = (Employe)request.getSession().getAttribute("currentUser");
	String currentPage = request.getAttribute("currentPage") == null?"coucou":(String)request.getAttribute("currentPage");
%>

<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>   
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link type="text/css" rel="stylesheet" href="style.css" />
		<title>Days Off Manager</title>
	</head>
	<body>
	<% if (currentUser != null ){ %>
		<header>
			<nav class="navbar navbar-light bg-light">
			  <a class="navbar-brand"><%=currentUser.getFullName()%></a>
			  <a class="nav-item nav-link active" href="/DaysOffManager/Calendar">Mon calendrier<span class="sr-only">(current)</span></a>
			  <% if(currentUser.getTitle() == "Leader"){ %>
			  	<a class="nav-item nav-link active" href="/DaysOffManager/Team">Mon équipe<span class="sr-only">(current)</span></a>
			  <% } %>			  
			  <form class="form-inline" action="Home" method="post">
			    <button class="btn btn-outline-success" name="deconnection" type="submit">Déconnexion</button>
			  </form>
			  
			</nav>
		</header>
		<main>
			<% if(currentPage == "calendar"){%>
				<jsp:include page="calendar.jsp">
		            <jsp:param name="year" value="2010"/>
		        </jsp:include>
			<%}else if(currentPage == "team"){%>
				<jsp:include page="team.jsp">
	            	<jsp:param name="year" value="2010"/>
	        	</jsp:include>
			<%}else{%>
				<jsp:include page="calendar.jsp">
		            <jsp:param name="year" value="2010"/>
		        </jsp:include>
			<%}%>
		
		</main>
	<% } %>	
	</body>
</html>