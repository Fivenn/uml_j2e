<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Dépôt demande de congés</title>
	</head>
	<body>
		<h1>Bienvenue !</h1>
		<div>
			<form>
			  <label>Date de début</label><br>
			  <input type="date" name="begin" value="2019-11-01" min="2019-11-01" max="2031-12-31"><br>
			  <label>Date de fin</label><br>
			  <input type="date" name="end" value="2019-11-01" min="2019-11-01" max="2031-12-31"><br>
			  <label>Durée en jours</label><br>
			  <input type="text" name="period"><br>
			  <label>Motif</label><br>
			  <input type="text" name="justification"><br>
			  <button type="submit">Envoyer</button>
			</form>
		</div>
	</body>
</html>