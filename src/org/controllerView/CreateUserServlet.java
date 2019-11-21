package org.controllerView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.ToIntFunction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.databaseManage.EmployeService;
import org.model.Employe;

public class CreateUserServlet extends HttpServlet {
HttpServlet httpServlet;
private EmployeService employeService = new EmployeService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//R�cup�ration des donn�es du formulaire de la page createUser.jsp
		if(req.getParameter("modifier")!=null) {
			req.setAttribute("employe", this.employeService.getEmploye((String)req.getParameter("modifier")));
			
		}else if(req.getParameter("create")!=null) {
			this.createUser(req.getParameter("prenom"),req.getParameter("nom"),req.getParameter("naissance"),req.getParameter("poste"),req.getParameter("equipe"),req.getParameter("mail"),req.getParameter("chef"),req.getParameter("adresse"));
		
		}else if(req.getParameter("update")!=null) {
			this.modifyEmploye(req.getParameter("prenom"),req.getParameter("nom"),req.getParameter("naissance"),req.getParameter("poste"),req.getParameter("equipe"),req.getParameter("mail"),req.getParameter("chef"),req.getParameter("adresse"));
		}
		
		this.doGetOrPost(req, resp);
		
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		
		req.setAttribute("currentMode", "RH");
		req.setAttribute("currentPage", "createUser");

		try {
            this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("currentUser")!=null && ((Employe)req.getSession().getAttribute("currentUser")).isRH()) {
			this.doProcess(req, resp);
		}else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}
	
	protected void createUser(String firstName,String Surname, String BirthDate,String poste, String team, String mail, String teamleader, String address) {
			int nbteam;
			boolean RH = false;
			boolean TL = false;			
			if(poste.contains("RH")||poste.contains("rh")||poste.contains("Rh")||poste.contains("rH")) {
				RH = true;
			}
			if(teamleader.equals("oui")) {
				TL = true;
			}
			if(team.equals("")) {
				nbteam = 0;
				
			}
			else {
				nbteam = Integer.parseInt(team);
			}
			Employe emp = new Employe(mail, firstName,Surname,BirthDate,address, 25, RH,TL,nbteam);
			employeService.ajoutEmploye(emp);
	
		}
		protected void modifyEmploye(String firstName,String surName,String birthDate,String function,String team, String mail,String teamleader,String address) {
			int nbteam;
			boolean RH = false;
			boolean TL = false;			
			if(function.contains("RH")||function.contains("rh")||function.contains("Rh")||function.contains("rH")) {
				RH = true;
			}
			if(teamleader.equals("oui")) {
				TL = true;
			}
			if(team.equals("")) {
				nbteam = 0;
				
			}
			else {
				nbteam = Integer.parseInt(team);
			}
			Employe emp = new Employe(mail, firstName,surName,birthDate,address, 25, RH,TL,nbteam);
			employeService.updateEmploye(emp);
			
		}
}

