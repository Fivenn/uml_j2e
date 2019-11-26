package org.controllerView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.databaseManage.EmployeService;
import org.databaseManage.TeamService;
import org.model.Employe;
import org.model.Team;

public class ManageUserServlet extends HttpServlet {
HttpServlet httpServlet;
private EmployeService employeService = new EmployeService();
private TeamService teamService = new TeamService();

private ArrayList<Employe> employesList;
private ArrayList<Team> teamsList;
private ArrayList<String> posteList;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		this.doGetOrPost(req, resp);		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Cas où l'utilisateur demande la suppression d'un employé
		if(req.getParameter("delete")!=null){
			//Suppression de l'employé en récupérant la valeur du paramètre "delete" (mail de l'employé)
			this.employeService.deleteEmploye((String)req.getParameter("delete"));			
		}
		this.doGetOrPost(req, resp);
	}
	
	
	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Si l'employé RH est bien connecté à la page
		if(req.getSession().getAttribute("currentUser")!=null && ((Employe)req.getSession().getAttribute("currentUser")).isRH()) {
			this.doProcess(req, resp);
		}else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		this.initLists(true);
		//Si l'utilisateur effectue une recherche
		if(req.getParameter("search")!= null) {
			this.reloadEmployes( req.getParameter("poste"), req.getParameter("team"), req.getParameter("employe"));
			//Affectation des attributs à la page manageUser.jsp pour garder les options de la recherche
			req.setAttribute("mail", req.getParameter("employe"));
			req.setAttribute("poste", req.getParameter("poste"));
			req.setAttribute("team", req.getParameter("team"));
		}
		
		req.setAttribute("currentMode", "RH");
		req.setAttribute("currentPage", "manageUser");
		//On fournit les listes des employés, des teams et des postes aux attributs de la page manageUser.jsp
		req.setAttribute("employesList", this.employesList);
		req.setAttribute("teamsList", this.teamsList);
		req.setAttribute("posteList", this.posteList);
		try {
            this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//Cette fonction permet d'initialiser les listes d'employés, des teams et des postes
	private void initLists(boolean isRespoRH) {
		// TODO Auto-generated method stub
		if(isRespoRH) {
			this.employesList = (ArrayList<Employe>) this.employeService.getAllEmployes();
			this.teamsList = (ArrayList<Team>) this.teamService.getAllTeams();
			this.posteList = new ArrayList<String>();
			this.posteList.add("Employe");
			this.posteList.add("EmployeRH");
			this.posteList.add("TeamLeader");
			this.posteList.add("RespoRH");
		}
	}
	//Permet de récupérer la liste des employés avec les options de recherche correspondant
	private void reloadEmployes(String poste, String team, String mail) {
		System.out.println(poste + mail + team);
		this.employesList = (ArrayList<Employe>) this.employeService.getFilteredEmploye(poste,team,mail);
	}
	
}

