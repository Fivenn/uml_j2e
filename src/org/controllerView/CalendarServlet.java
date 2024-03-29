package org.controllerView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.databaseManage.DemandService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.model.Demand;
import org.model.Employe;

/**
 * Calendar Servlet est la classe représentant le controlleur
 *  de la page d'accueil du site (calendrier).
 */
public class CalendarServlet extends HttpServlet {

	/**
	 * Service permettant d'effectuer différentes requêtes
	 * relatives à la base de données.
	 * 
	 * @see DemandService
	 */
	private DemandService demandService = new DemandService();
	/**
	 * Liste des demandes d'un employé.
	 * 
	 * @see Demand
	 */
	private ArrayList<Demand> demandsList;
	/*
	 * Liste des différents types de congés possibles
	 */
	private ArrayList<String> reasonsList = (ArrayList<String>) demandService.getAllReasons();
	/**
	 * Liste des différents types de status possible 
	 * pour une demande de congés.
	 */
	private ArrayList<String> statusList = (ArrayList<String>) demandService.getStatus(); 
	/**
	 * JSONArray comportant les différentes demandes
	 * d'un employé à passer au script JavaScript
	 * du calendrier.
	 */
	private JSONArray employeDemandsList = new JSONArray(); 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Mode : " + req.getParameter("goToRHMode"));
		if (req.getParameter("goToRHMode") != null) { 
			req.setAttribute("currentMode", "employe");
		} else if (req.getParameter("askDaysOff") != null) {
			req.setAttribute("errorAskingForDays", this.isDateValid(req, true));
		}
		this.doGetOrPost(req, resp);
	}

	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * Si un employé est connecté sur la plateforme.
		 */
		if (req.getSession().getAttribute("currentUser") != null) { 
			if (req.getParameter("update") != null) {
				req.setAttribute("table", true); // sélectionne la vue de la page d'accueil sur le tableau.
				req.setAttribute("errorAskingForDays", this.isDateValid(req, false));
				this.demandsList = (ArrayList<Demand>) this.demandService
						.getEmployeDemand(((Employe) req.getSession().getAttribute("currentUser")).getMail());
			/*
			 * Si on appuie sur le bouton supprimer du tableau des demandes.
			 */
			} else if (req.getParameter("delete") != null) {
				req.setAttribute("table", true);
				this.demandService.deleteDemand(req.getParameter("delete"));
		        this.demandsList = (ArrayList <Demand>) this.demandService.getEmployeDemand(((Employe) req.getSession().getAttribute("currentUser")).getMail());
		        /*
		         * Si on appuie sur le bouton rechercher du tableau des demandes.
		         */
			} else if(req.getParameter("search") != null) {
				req.setAttribute("table", true);
				/*
				 * On filtre les demandes d'un employé en fonction du type choisi.
				 */
				this.demandsList = (ArrayList<Demand>) this.demandService.getFilteredDemand(
						req.getParameter("statusSearch"),
						((Employe) req.getSession().getAttribute("currentUser")).getMail(), "all", false);
			} else {
				/*
				 * On affiche toutes les demandes de l'employé.
				 */
				this.demandsList = (ArrayList<Demand>) this.demandService
						.getEmployeDemand(((Employe) req.getSession().getAttribute("currentUser")).getMail());
			}

			if (req.getParameter("tableDemand") != null) {
				req.setAttribute("table", true);
			}

			this.doProcess(req, resp);
		} else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}

	@SuppressWarnings("unchecked")
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		for (Demand d : demandsList) {
			/*
			 * Variable contenant le pattern de la date de fin
			 */
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			/*
			 * Instance de la classe Calendar
			 */
			Calendar c = Calendar.getInstance();
			/*
			 * On récupère la date de fin d'une demande
			 * au format d'une chaine de caractère.
			 */
			String endDate = d.getEndDate();
			/**
			 * JSONObject contenant une demande d'un employé.
			 */
			JSONObject employeDemand = new JSONObject();
			/**
			 * String contenant le status de la demande.
			 */
			String status = d.getStatus();

			/*
			 * On modifie la vue d'une demande dans le calendrier 
			 * en fonction de son status.
			 */
			switch (status) {
			case "pending":
				/*
				 * On ajoute un jour à la date de fin 
				 * pour palier au problème du dernier jour
				 * compté comme travaillé.
				 */
				try {
					c.setTime(sdf.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.add(Calendar.DATE, 1);
				endDate = sdf.format(c.getTime());

				/*
				 * Le titre d'un événement est une concaténation entre l'email, 
				 * la raison (getMotif) 
				 * et le status d'une demande.
				 */
				employeDemand.put("title", ((Employe) req.getSession().getAttribute("currentUser")).getMail() + " - "
						+ d.getMotif() + " - " + d.getStatus());
				employeDemand.put("start", d.getStartDate()); // date de début saisi par l'employé
				employeDemand.put("end", endDate); // date de fin saisi par l'employé
				employeDemand.put("color", "#007bff"); // colore l'événement en bleu.
				employeDemand.put("textColor", "#FFFFFF"); // couleur du texte en blanc

				employeDemandsList.add(employeDemand); // ajout du JSONObject dans la JSONArray
				break;
			case "approved":
				/*
				 * On ajoute un jour à la date de fin 
				 * pour palier au problème du dernier jour
				 * compté comme travaillé.
				 */
				try {
					c.setTime(sdf.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.add(Calendar.DATE, 1);
				endDate = sdf.format(c.getTime());
				
				/*
				 * Le titre d'un événement est une concaténation entre l'email, 
				 * la raison (getMotif) 
				 * et le status d'une demande.
				 */
				employeDemand.put("title", ((Employe) req.getSession().getAttribute("currentUser")).getMail() + " - "
						+ d.getMotif() + " - " + d.getStatus());
				employeDemand.put("description", d.getCommentary()); // commentaire optionnel et relatif à la demande
				employeDemand.put("start", d.getStartDate()); // date de début saisi par l'employé
				employeDemand.put("end", endDate); // date de fin saisi par l'employé
				employeDemand.put("color", "#28a745"); // colore l'événement en vert.
				employeDemand.put("textColor", "#FFFFFF"); // couleur du texte en blanc

				employeDemandsList.add(employeDemand); // ajout du JSONObject dans la JSONArray
				break;
			case "refused":
				/*
				 * On ajoute un jour à la date de fin 
				 * pour palier au problème du dernier jour
				 * compté comme travaillé.
				 */
				try {
					c.setTime(sdf.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.add(Calendar.DATE, 1);
				endDate = sdf.format(c.getTime());
				
				/*
				 * Le titre d'un événement est une concaténation entre l'email, 
				 * la raison (getMotif) 
				 * et le status d'une demande.
				 */
				employeDemand.put("title", ((Employe) req.getSession().getAttribute("currentUser")).getMail() + " - "
						+ d.getMotif() + " - " + d.getStatus());
				employeDemand.put("description", d.getCommentary()); // commentaire optionnel et relatif à la demande
				employeDemand.put("start", d.getStartDate()); // date de début saisi par l'employé
				employeDemand.put("end", endDate); // date de fin saisi par l'employé
				employeDemand.put("color", "#dc3545"); // colore l'événement en rouge.
				employeDemand.put("textColor", "#FFFFFF"); // couleur du texte en blanc

				employeDemandsList.add(employeDemand); // ajout du JSONObject dans la JSONArray
				break;
			}
		}

		/*
		 * Envoie des différents attributs à la page JSP.
		 */
		req.setAttribute("currentPage", "calendar");
		req.setAttribute("reasonsList", this.reasonsList);
		req.setAttribute("statusList", this.statusList);
		req.setAttribute("demandsList", this.demandsList);
		req.setAttribute("employeDemandsList", this.employeDemandsList);

		try {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

		/*
		 * On vide la JSONArray pour éviter des problèmes de
		 * duplication d'objets.
		 */
		employeDemandsList.clear();
	}

	/**
	 * Cette fonction permet de vérifier que la date de début et de fin d'une demande sont cohérentes
	 * ainsi que de vérifier que l'employé possède encore assez de jours de congés.
	 * 
	 * @param req Un objet HttpServletRequest qui contient la requête que le client a faite depuis la servlet.

	 * @param insert Un booléen permettant de savoir si la demande doit être ajoutée ou modifiée.
	 * @return Une String permettant d'informer l'employé de l'état de sa requête.
	 */
	private String isDateValid(HttpServletRequest req, boolean insert) {
		String message = "";
		try {
			if (((Employe) req.getSession().getAttribute("currentUser")).getNbDays() < Integer
					.parseInt(req.getParameter("nbDays"))) { // si le nombre de jours d'un employé est inférieur au nombre de jour que l'on souhaite poser
				message = "Le nombre de jours est insuffisant, il n'en reste que "
						+ ((Employe) req.getSession().getAttribute("currentUser")).getNbDays() + "."; // On notifie l'employé qu'il ne possède pas assez de jours de congés

			} else if (new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("fromDate"))
					.after(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("toDate")))) { // Sinon si la date de début est après la date de fin
				message = "La première date doit être inférieure à la deuxième."; // On notifie l'employé que les dates de fin et de début ne sont pas cohérentes

				/*
				 * Si l'utilisateur fait une demande de congés avec une date de début inférieure à deux jours
				 * par rapport à la date actuelle et que la raison de la demande est différente
				 * de "Enfants malades" ou "Raisons familiales"
				 */
			} else if (LocalDate.parse(req.getParameter("fromDate")).isBefore(LocalDate.now().plusDays(2)) 
					&& !req.getParameter("reason").equals("Enfants malades")
					&& !req.getParameter("reason").equals("Raisons familiales")) {
				message = "Il faut au minimum 48h pour poser des congés."; // On notifie l'employé 
			}

			/*
			 * S'il n'y a pas de condition de refus
			 */
			if (message.contentEquals("")) {
				if (insert) { // Si on insère une demande dans la table
					/*
					 * On exécute la requête d'insertion d'une demande dans la base de données
					 */
					if (this.demandService.insertIntoDemand(
							((Employe) req.getSession().getAttribute("currentUser")).getMail(),
							req.getParameter("fromDate"), req.getParameter("toDate"), req.getParameter("reason"),
							req.getParameter("nbDays"))) {
						message = "Demande envoyée au service RH. Elle sera traitée dans les meilleurs délais."; // On notifie l'employé
					}
					/*
					 * On modifie la requête 
					 */
				} else { // Sinon on modifie une demande dans la base de données
					if (this.demandService.changeDemand(req.getParameter("update"), req.getParameter("fromDate"),
							req.getParameter("toDate"), req.getParameter("nbDays"), req.getParameter("reason"))) {
						message = "Demande modifiée"; // On notifie l'employé
					}
				}
			}
		} catch (Exception e) {
			message = "Demande non valide.";
		}
		return message;

	}

}