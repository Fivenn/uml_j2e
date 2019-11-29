Cette application a été réalisée par Maëlle Heyrendt, Clélia Le van, Nicolas Sueur et Mickaël Tisserand dans le cadre du Projet JEE en IMR2 à l'Enssat.

Elle sert à déposer des congés et à les gérer en tant que différents profils utilisateur.

Afin de l'installer, il faut commencer par créer une base de données selon la configuration suivante :
	Dans le fichier config :
		User : root
		Password : hola
		Database name : DaysOffManager

	Pour créer la base de données  : 
		se placer à la racine du projet, ouvrir mysql et faire la commande : source ./BDD/createDatabase.sql
		
Pour lancer l'application, il suffit de compiler le projet, de le lancer 
puis d'aller à l'adresse http://localhost:8080/DaysOffManager/Connection sur votre navigateur.

Des utilisateurs sont créés à l'instantiation de la base.
Afin de tester les différents rôles, vous pouvez vous connecter avec ses couples user/pwd:
	Respo RH : clevan@enssat.fr / cv
	Employe RH : c@c.c / c
	Employe : a@a.a / a
	Leader : mheyrend@enssat.fr / mh
	
Un fichier WAR est présent à la racine du projet (optimisé pour Tomcat 9)

Une page d'aide résumant les droit de chacun est disponible dans le footer de l'application. Chaque page est aussi décrite dans le rapport.

	
