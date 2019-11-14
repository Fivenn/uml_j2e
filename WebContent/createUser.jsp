<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h1>
	Création d'un employé
</h1>

<form class="form-co" method="post">
	 <form>
        <fieldset>
            <legend>Identité</legend>            
            <label>Prenom: <input type="text" name="prenom"></label>            
            <label>Nom: <input type="text" name="nom"></label>
        </fieldset>
        <fieldset>
            <legend>Métier</legend>            
            <label>Poste: <input type="text" name="poste"></label>            
            <label>Equipe: <input type="text" name="equipe"></label>
        </fieldset>
        <fieldset>
        	<legend>Coordonnées</legend>
        	<label>mail: <input type="text" name="mail"></label>     
            <label>Adresse: <textarea name="adresse"></textarea></label>
            <label>Tél: <input type="text" name="tel"></label>
        </fieldset>
         <button type="submit" class="btn btn-primary text-center">Envoyer</button>
</form>
<a href="ManageUser">Retour</a>

