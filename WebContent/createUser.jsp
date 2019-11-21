<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.model.Employe"%>
    
  <%
  	Employe employe = request.getAttribute("employe") != null ? (Employe)request.getAttribute("employe") : new Employe("","","","","",25,false,false,0);
  	String verifMail = request.getAttribute("verifMail") != null ? (String)request.getAttribute("verifMail") : "";
  %>  
<div style="display: flex;border-bottom: 1px solid #110133;margin-bottom: 2em;justify-content: space-between;">
<h3>
<% if(request.getAttribute("employe") == null){ %>
	Création d'un employé
<%}else{ %>
	Modification d'un employé
<%} %>
</h3>
			<nav>
			<form class="form-inline" action="ManageUser" method="post">
				<button class="nav-button" type="submit">Retour</button>
			</form>
			</nav>
</div>

<form action="CreateUser" method="post">
		<div class="form-group">
        <fieldset>        
            <legend>Identité</legend>            
            <label for="firstName">Prenom:</label><input class="form-control mb-2 mr-sm-2 mb-sm-0" id="firstName" type="text" name="prenom" value="<%=employe.getFirstName() %>">          
            <label for="surName"></label> Nom: <input class="form-control mb-2 mr-sm-2 mb-sm-0" id="surName" type="text" name="nom" value="<%=employe.getSurname() %>">
            <label for="birthdate" >Date de naissance:</label> <input class="form-control mb-2 mr-sm-2 mb-sm-0" id="birthDate" type="text" name="naissance" value="<%=employe.getBirthDate()%>" >
        </fieldset>
		</div>
		<div class="form-group">
        <fieldset>
            <legend>Métier</legend>            
            <label for="function">Poste:</label><input class="form-control mb-2 mr-sm-2 mb-sm-0" id="function" type="text" name="poste">        
            <label  for="nbteam">Equipe:</label><input class="form-control mb-2 mr-sm-2 mb-sm-0" id="nbteam" type="number" name="equipe" value="<%=employe.getNbTeam()%>">
            <label >Chef d'equipe</label>
            <select class="form-control" name="chef">
            	<option value="oui">oui</option>
            	<option value="non">non</option>
            </select>
        </fieldset>
        </div>
		<div class="form-group">
        <fieldset>
        	<legend>Coordonnées</legend>
        	<label for="mail">mail:</label>   <input class="form-control mb-2 mr-sm-2 mb-sm-0" id="mail" type="text" name="mail" value="<%=employe.getMail()%>"> 
            <label  for="address">Adresse:</label> <input class="form-control mb-2 mr-sm-2 mb-sm-0" id="address" name="adresse" value="<%=employe.getAddress()%>">
        </fieldset>
        </div>
        <% if(request.getAttribute("employe") == null){ %>
         <button type="submit" class="btn btn-primary text-center" name="create">Ajouter</button>
        <%}else{ %>
         <button type="submit" class="btn btn-primary text-center" name="update">Modifier</button>
        <%} %>
</form>



<script>
<%if(verifMail.equals("err")){ %>
	alert("Insérez un mail valide");
<%} %>
<%if(verifMail.equals("no")){ %>
	alert("Ce mail existe deja");
<%} %>
</script>
