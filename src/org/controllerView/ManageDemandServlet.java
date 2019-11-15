package org.controllerView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.databaseManage.DemandService;
import org.databaseManage.EmployeService;
import org.databaseManage.TeamService;
import org.model.Demand;
import org.model.Employe;
import org.model.Team;

public class ManageDemandServlet extends HttpServlet {
	private EmployeService employeService = new EmployeService();
	private TeamService teamService = new TeamService();
	private DemandService demandService = new DemandService();
	
	private ArrayList<Employe> employesList;
	private ArrayList<Team> teamsList;
	private ArrayList<String> statusList;
	private ArrayList<Demand> demandsList;
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(req.getSession().getAttribute("currentUser")!=null && ((Employe)req.getSession().getAttribute("currentUser")).isRH()) {
			if(req.getParameter("approved")!= null) {
				this.demandService.changeDemandStatus(req.getParameter("approved"), "approved");
			}else if(req.getParameter("refused")!= null) {
				this.demandService.changeDemandStatus(req.getParameter("refused"), "refused");
			}
			if(req.getParameter("statsDemand")!= null) {
				req.setAttribute("stats", true);
			}
			this.doProcess(req, resp);
		}else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		if(req.getParameter("search")!= null) {
			this.reloadDemands(((Employe) req.getSession().getAttribute("currentUser")).isLeader(), req.getParameter("status"), req.getParameter("employe"), req.getParameter("team"));
			req.setAttribute("mail", req.getParameter("employe"));
			req.setAttribute("status", req.getParameter("status"));
			req.setAttribute("team", req.getParameter("team"));
		}else {
			this.initLists(((Employe) req.getSession().getAttribute("currentUser")).isLeader());
			req.setAttribute("mail", "all");
			req.setAttribute("status", "all");
			req.setAttribute("team", "all");
		}
		
		req.setAttribute("employesList", this.employesList);
		req.setAttribute("teamsList", this.teamsList);
		req.setAttribute("statusList", this.statusList);
		req.setAttribute("demandsList", this.demandsList);
		
		req.setAttribute("currentPage", "manageDemand");
		req.setAttribute("currentMode", "RH");
		
		try {
            this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	
	private void initLists(boolean isRespoRH) {

		if(isRespoRH) {
			this.employesList = (ArrayList<Employe>) this.employeService.getAllEmployes();
			this.teamsList = (ArrayList<Team>) this.teamService.getAllTeams();
			this.demandsList = (ArrayList<Demand>) this.demandService.getAllDemands();
		}else {
			this.employesList = (ArrayList<Employe>) this.employeService.getAllEmployesButRH();	
			this.teamsList = (ArrayList<Team>) this.teamService.getAllTeamsButRH();
			this.demandsList = (ArrayList<Demand>) this.demandService.getAllDemandsButRH();
		}
		
		this.employesList = (ArrayList<Employe>) this.employeService.getAllEmployes();
		
		this.statusList = this.demandService.getStatus();
	}
	
	private void reloadDemands(boolean isRespoRH, String status, String mail, String team) {
		System.out.println(status + mail + team);
		this.demandsList = (ArrayList<Demand>) this.demandService.getFilteredDemand(status, mail, team, isRespoRH);
	}
}
