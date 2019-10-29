<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String connectionNotValid = (String)request.getAttribute("connectionNotValid");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Connection page</title>
		<link type="text/css" rel="stylesheet" href="style.css" />
	</head>
	<body>
		<h1>Bienvenue sur Days Off Manager.</h1>
		<div class="container-form">
			<% if (connectionNotValid != null ){ %>
				<p> <%=connectionNotValid%> </p>
			<% }%>
			<form class="form-co" action="Connection" method="post">
			  <label>Email</label>
			  <input type="text" name="email">
			  <label>Mot de passe</label>
			  <input type="password" name="password">
			  
			  <button type="submit">Envoyer</button>
			</form>
		</div>
	</body>
</html>