<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.model.Employe"%>
    
  <%
  	Employe employe = request.getAttribute("employe") != null ? (Employe)request.getAttribute("employe") : new Employe("","","","","",25,false,false,0);
  %>  

<h1>
	Création d'un employé
</h1>

<form class="form-co" action="CreateUser" method="post">
	 <form>
        <fieldset>
            <legend>Identité</legend>            
            <label for="firstName">Prenom:</label><input id="firstName" type="text" name="prenom" value="<%=employe.getFirstName() %>">          
            <label for="surName"></label> Nom: <input id="surName" type="text" name="nom" value="<%=employe.getSurname() %>">
            <label for="birthdate" >Date de naissance:</label> <input id="birthDate" type="text" name="naissance" value="<%=employe.getBirthDate()%>" >
        </fieldset>
        <fieldset>
            <legend>Métier</legend>            
            <label for="function">Poste:</label><input id="function" type="text" name="poste">        
            <label for="nbteam">Equipe:</label><input id="nbteam" type="number" name="equipe" value="<%=employe.getNbTeam()%>">
            <label>Chef d'equipe</label>
            <select name="chef">
            	<option value="oui">oui</option>
            	<option value="non">non</option>
            </select>
        </fieldset>
        <fieldset>
        	<legend>Coordonnées</legend>
        	<label for="mail">mail:</label>   <input id="mail" type="text" name="mail" value="<%=employe.getMail()%>">   
            <label for="address">Adresse:</label> <input id="address" name="adresse" value="<%=employe.getAddress()%>"></input>
        </fieldset>
        
        <% if(request.getAttribute("employe") == null){ %>
         <button type="submit" class="btn btn-primary text-center" name="create">Ajouter</button>
        <%}else{ %>
         <button type="submit" class="btn btn-primary text-center" name="update">Modifier</button>
        <%} %>
</form>
<a href="ManageUser">Retour</a>

