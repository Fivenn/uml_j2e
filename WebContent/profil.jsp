<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Profil</title>
	</head>
	<body>
		<h1>Mon Profil</h1>
		<div>
			<label>Nom</label>
			<label>Prénom</label>
		</div>
		<div>
			<label>Date de naissance</label>
		</div>
		<div>
			<label>Poste</label>
			<label>Equipe</label>
		</div>
		
		<div>
			<label>Changer votre mot de passe</label>
			<label for="old_password">Ancien mot de passe</label>
			<input type="text" id="old_password" name="old_password" required size="30">
			<label for="new_password">Nouveau mot de passe</label>
			<input type="text" id="new_password" name="new_password" required size="30">
			<label for="re_new_password">Re-Nouveau mot de passe</label>
			<input type="text" id="re_new_password" name="re_new_password" required size="30">
		</div>
		
		<div>
			<label for="addr">Adresse</label>
			<input type="text" id="addr" name="addr" required size="30">
			<label for="addr2">Complément d'adresse</label>
			<input type="text" id="addr2" name="addr2" required size="30">
		</div>
		<div>
			<label for="cp">Code Postal</label>
			<input type="text" id="cp" name="cp" required size="10">
			<label for="city">Ville</label>
			<input type="text" id="city" name="city" required size="20">
		</div>
		<div>
			<label for="tel">Téléphone portable</label>
			<input type="text" id="tel" name="tel" required size="30">
		</div>
	</body>
</html>