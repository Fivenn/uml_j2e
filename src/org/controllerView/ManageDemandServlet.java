package org.controllerView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.databaseManage.DemandService;
import org.databaseManage.EmployeService;
import org.databaseManage.StatsService;
import org.databaseManage.TeamService;
import org.model.Demand;
import org.model.Employe;
import org.model.Team;
//Cette classe gére la page de gestion des demandes.
public class ManageDemandServlet extends HttpServlet {
	private EmployeService employeService = new EmployeService();
	private TeamService teamService = new TeamService();
	private DemandService demandService = new DemandService();
	private StatsService statService = new StatsService();
	
	//Tableau permettant de réaliser des filtres et d'afficher les demandes.
	private ArrayList<Employe> employesList;
	private ArrayList<Team> teamsList;
	private ArrayList<String> statusList;
	private ArrayList<Demand> demandsList;
	private ArrayList<String> reasonsList;
	
	//ArrayList des stats :
	//1 : liste de nb de congés par équipe Tab([String, int])
	private ArrayList<List<String>> daysOffPerTeam = new ArrayList<List<String>>();
	//2 : liste de nb congés par motif Tab([String, int])
	private ArrayList<List<String>> daysOffPerReason = new ArrayList<List<String>>();
	//3 : liste de nb congés par mois Tab([String, int])
	private ArrayList<List<String>> daysOffPerMonth = new ArrayList<List<String>>();
	//4 : list nb congés par poste Tab([String, int])
	private ArrayList<List<String>> daysOffPerJob = new ArrayList<List<String>>();
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	//Fonction récupérant le get et le post
	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(req.getSession().getAttribute("currentUser")!=null && ((Employe)req.getSession().getAttribute("currentUser")).isRH()) { //SI l'utilisateur est bien connecté et abilité à voir la page
			if(req.getParameter("approved")!= null) { //Si il a validé une demande
				if(this.demandService.hasEnoughDays(req.getParameter("approved"))) { // Si l'utilisateur a assez de jours
					this.demandService.changeDemandStatus(req.getParameter("approved"), "approved"); //On change le statut de la demande
					req.setAttribute("errorAskingForDays", "Demande approuvée"); 	//On fait savoir ce qu'il se passe à l'utilisateur		
				}else {
					req.setAttribute("errorAskingForDays", "Pas assez de jours disponibles pour accepter");		//On fait savoir ce qu'il se passe à l'utilisateur				
				}
			}else if(req.getParameter("approvedCom")!= null) { // Si l'utilisateur a accepté une demande et a mis un commentaire
				if(this.demandService.hasEnoughDays(req.getParameter("approvedCom"))) { // Si l'utilisateur a assez de jours
					req.setAttribute("errorAskingForDays", "Demande approuvée"); //On fait savoir ce qu'il se passe à l'utilisateur		
					this.demandService.changeDemandStatus(req.getParameter("approvedCom"), "approved",req.getParameter("comment")); //On change le statut de la demande
				}else {
					req.setAttribute("errorAskingForDays", "Pas assez de jours disponibles pour accepter");		//On fait savoir ce qu'il se passe à l'utilisateur					
				}
			}else if(req.getParameter("refused")!= null) { // Si l'utilisateur refuse la demande
				req.setAttribute("errorAskingForDays", "Demande refusée.");		//On fait savoir ce qu'il se passe à l'utilisateur					
				this.demandService.changeDemandStatus(req.getParameter("refused"), "refused",req.getParameter("comment")); // On refuse la demande
			}else if(req.getParameter("changeReason") != null) { // SI l'utilisateur veut changer la raison
				req.setAttribute("errorAskingForDays", "Demande modifiée");		//On fait savoir ce qu'il se passe à l'utilisateur					
				this.demandService.changeDemandReason(req.getParameter("changeReason"),req.getParameter("reasonsList")); //On change la raison
			}
			
			if(req.getParameter("statsDemand")!= null) { // Si la page de stats est appelée
				req.setAttribute("stats", true); //On dit à la page que c'est des stats
				
				this.initStats(); // ON initialise les tableaux 
				
				//On les ajoute à la requète
				req.setAttribute("daysOffPerTeam", this.daysOffPerTeam); 
				req.setAttribute("daysOffPerJob", this.daysOffPerJob);
				req.setAttribute("daysOffPerMonth", this.daysOffPerMonth);
				req.setAttribute("daysOffPerReason", this.daysOffPerReason);
				
			}
			this.doProcess(req, resp);
		}else {
			//Sinon on redirige au home pour qu'il gère le fait que l'utilisateur ne soit pas connecté
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		if(req.getParameter("search")!= null) { // SI l'utilisateur cherche à filtrer les demandes
			this.reloadDemands(((Employe) req.getSession().getAttribute("currentUser")).isLeader(), req.getParameter("status"), req.getParameter("employe"), req.getParameter("team")); //On trie en fonction de l'utilisateur et ed ses désirs
			//On renvoie les paramètres de la demande
			req.setAttribute("mail", req.getParameter("employe"));
			req.setAttribute("status", req.getParameter("status"));
			req.setAttribute("team", req.getParameter("team"));
		}else {
			//Sinon on initialise avec tout
			this.initLists(((Employe) req.getSession().getAttribute("currentUser")).isLeader());
			req.setAttribute("mail", "all");
			req.setAttribute("status", "all");
			req.setAttribute("team", "all");
		}
		
		// On ajoute tout les tableaux à la requète
		req.setAttribute("employesList", this.employesList);
		req.setAttribute("teamsList", this.teamsList);
		req.setAttribute("statusList", this.statusList);
		req.setAttribute("demandsList", this.demandsList);
		req.setAttribute("reasonsList", this.reasonsList);
		
		//On modifie la page actuelle et on confirme le mode RH
		req.setAttribute("currentPage", "manageDemand");
		req.setAttribute("currentMode", "RH");
		
		try {
            this.getServletContext().getRequestDispatcher("/Home").forward(req, resp); // ON renvoie au home pour qu'il créé le template
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	//Fonction permettant d'initialiser les tableaux de données
	private void initLists(boolean isRespoRH) {
		
		if(isRespoRH) { // SI l'utilisteur est Respo RH on peut lui confier la liste de toutes les demandes de tous les employés
			this.employesList = (ArrayList<Employe>) this.employeService.getAllEmployes();
			this.teamsList = (ArrayList<Team>) this.teamService.getAllTeams();
			this.demandsList = (ArrayList<Demand>) this.demandService.getAllDemands();
		}else { // Sinon les demandes RH ne sont pas affichées
			this.employesList = (ArrayList<Employe>) this.employeService.getAllEmployesButRH();	
			this.teamsList = (ArrayList<Team>) this.teamService.getAllTeamsButRH();
			this.demandsList = (ArrayList<Demand>) this.demandService.getAllDemandsButRH();
		}
		
		this.employesList = (ArrayList<Employe>) this.employeService.getAllEmployes();
		
		this.statusList = this.demandService.getStatus();
		this.reasonsList = (ArrayList<String>) this.demandService.getAllReasons();
	}
	// FOnction permettant d'initialiser les tableaux de stats
	private void initStats() {
		this.daysOffPerJob = this.statService.getDaysOffPerJob();
		this.daysOffPerMonth = this.statService.getDaysOffPerMonth();
		this.daysOffPerReason = this.statService.getDaysOffPerReasons();
		this.daysOffPerTeam = this.statService.getDaysOffPerTeam();
	}
	//Fonction qui met à jour les demandes en fonction de la recherche.
	private void reloadDemands(boolean isRespoRH, String status, String mail, String team) {
		this.demandsList = (ArrayList<Demand>) this.demandService.getFilteredDemand(status, mail, team, isRespoRH);
	}
}
