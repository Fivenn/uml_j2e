package org.controllerView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.databaseManage.DemandService;
import org.databaseManage.EmployeService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.model.Demand;
import org.model.Employe;

/**
 * Team Servlet est la classe représentant le controlleur de la page "mon équipe"
 * de l'application.
 *
 */
public class TeamServlet extends HttpServlet {

	/**
	 * Service permettant d'effectuer différentes requêtes sur les demandes
	 * reatives à la base de données.
	 * 
	 * @see DemandService
	 */
	private DemandService demandService = new DemandService();
	/**
	 * Service permettant d'effectuer différentes requêtes sur les employés
	 * relatives à la base de données.
	 */
	private EmployeService employeService = new EmployeService();
	/**
	 * Liste des demandes d'un/des employé(s).
	 * 
	 * @see Demand
	 */
	private ArrayList<Demand> demandsList;
	/**
	 * Liste des employés d'une équipe.
	 * 
	 * @see Employe
	 */
	private ArrayList<Employe> employeesList;
	/**
	 * Liste des différents status possibles pour une demande.
	 */
	private ArrayList<String> statusList;
	/**
	 * Liste des différentes raisons possibles pour une demande.
	 */
	private ArrayList<String> reasonsList;
	/**
	 * JSONArray comportant les différentes demandes
	 * des employés d'une team à passer au script JavaScript.
	 */
	private JSONArray teamDemandsList = new JSONArray();
	HttpServlet httpServlet;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}

	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * Si un employé est connecté sur la plateforme.
		 */
		if (req.getSession().getAttribute("currentUser") != null) {
			/*
			 * Si un filtre est sélectionné.
			 */
			if (req.getParameter("filter") != null) {
				/*
				 * Affichage des demande de l'employé sélectionné parmis
				 * ceux d'une équipe.
				 */
				this.demandsList = (ArrayList<Demand>) this.demandService.getEmployeDemand(req.getParameter("employe"));
				req.setAttribute("mail", req.getParameter("employe"));
			} else {
				/*
				 * Sinon affichage de l'ensemble des demandes des employés
				 * d'une équipe.
				 */
				this.demandsList = (ArrayList<Demand>) this.demandService
						.getTeamDemands(((Employe) req.getSession().getAttribute("currentUser")).getMail());
				req.setAttribute("mail", "all");
			}

			if (req.getParameter("tableTeamDemand") != null) {
				req.setAttribute("table", true);
			}
			this.doProcess(req, resp);
		} else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		/*
		 * On récupère la liste des employés
		 * d'une équipe.
		 */
		this.employeesList = (ArrayList<Employe>) this.employeService
				.getMyTeam(((Employe) req.getSession().getAttribute("currentUser")).getMail());
		/*
		 * On récupère les différents status  d'une demande.
		 */
		this.statusList = (ArrayList<String>) demandService.getStatus();
		/*
		 * On récupère les différentes raisons d'une demande.
		 */
		this.reasonsList = (ArrayList<String>) demandService.getAllReasons();

		// Build JSON
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
			/*
			 * JSONObject contenant les demandes des employés
			 * d'une équipe.
			 */
			JSONObject teamDemand = new JSONObject();
			
			/*
			 * String contenant le status de la demande.
			 */
			String status = d.getStatus();

			/*
			 * On modifie la vue d'une demande dans le calendrier
			 * en fonction de son status
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
				teamDemand.put("title", d.getEmploye().getMail() + " - " + d.getMotif() + " - " + d.getStatus());
				teamDemand.put("start", d.getStartDate()); // date de début saisi par l'employé
				teamDemand.put("end", endDate); // date de fin saisi par l'employé
				teamDemand.put("color", "#007bff"); // colore l'événement en bleu
				teamDemand.put("textColor", "#FFFFFF"); // couleur du texte en blanc

				teamDemandsList.add(teamDemand); // ajout du JSONObject dans la JSONArray
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
				teamDemand.put("title", d.getEmploye().getMail() + " - " + d.getMotif() + " - " + d.getStatus());
				teamDemand.put("description", d.getCommentary());
				teamDemand.put("start", d.getStartDate()); // date de début saisi par l'employé
				teamDemand.put("end", endDate); // date de fin saisi par l'employé
				teamDemand.put("color", "#28a745"); // colore l'événement en vert
				teamDemand.put("textColor", "#FFFFFF"); // couleur du texte en blanc

				teamDemandsList.add(teamDemand); // ajout du JSONObject dans la JSONArray
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
				teamDemand.put("title", d.getEmploye().getMail() + " - " + d.getMotif() + " - " + d.getStatus());
				teamDemand.put("description", d.getCommentary());
				teamDemand.put("start", d.getStartDate()); // date de début saisi par l'employé
				teamDemand.put("end", endDate); // date de fin saisi par l'employé
				teamDemand.put("color", "#dc3545"); // colore de l'événement en rouge
				teamDemand.put("textColor", "#FFFFFF"); // couleur du texte en blanc

				teamDemandsList.add(teamDemand); // ajout du JSONObject dans la JSONArray
				break;
			}
		}
		
		/*
		 * Envoie des différents attributs à la page JSP
		 */
		req.setAttribute("currentPage", "team");
		req.setAttribute("reasonsList", this.reasonsList);
		req.setAttribute("statusList", this.statusList);
		req.setAttribute("demandsList", this.demandsList);
		req.setAttribute("employeesList", this.employeesList);
		req.setAttribute("teamDemandsList", this.teamDemandsList);

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
		teamDemandsList.clear();
	}
}
