<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h1>
	Création d'un employé
</h1>

<form class="form-co" action="ManageUser" method="post">
	 <form>
        <fieldset>
            <legend>Identité</legend>            
            <label>Prenom: <input type="text" name="Prenom"></label>            
            <label>Nom: <input type="text" name="nom"></label>
            <label>Date de naissance: <input type="text" name="DateDeNaissance"></label>
        </fieldset>
        <fieldset>
            <legend>Métier</legend>            
            <label>Poste: <input type="text" name="Poste"></label>            
            <label>Equipe: <input type="text" name="Equipe"></label>
        </fieldset>
        <fieldset>
        	<legend>Coordonnées</legend>      
            <label>Adresse: <textarea ></textarea></label>
            <label>Tél: <input type="text" name="tel"></label>
        </fieldset>
         <button type="submit" class="btn btn-primary text-center">Envoyer</button>
</form>
