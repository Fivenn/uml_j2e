package org.controllerView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

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

public class CreateUserServlet extends HttpServlet {
	HttpServlet httpServlet;
	private ArrayList<Employe> employesList;
	private ArrayList<Team> teamsList;
	private ArrayList<String> posteList;
	private ArrayList<String> mailList;

	private TeamService teamService = new TeamService();
	private EmployeService employeService = new EmployeService();
	private Employe employe;
	private String mailBefore = "";
	private boolean pageVerif;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("createTeam") != null) {
			if (verifTeam(req, resp)) {
				this.createTeam(req.getParameter("nameTeam"), req.getParameter("description"),
						req.getParameter("teamLeader"));
				pageVerif = true;
			}
		}
		if (req.getParameter("modifier") != null) {
			employe = employeService.getEmploye((String) req.getParameter("modifier"));
			this.mailBefore = employe.getMail();
			req.setAttribute("employe", this.employe);
			req.setAttribute("verifMail", "");

		} else if (req.getParameter("create") != null) {
			if (verifMail(req, resp)) {
				this.createUser(req.getParameter("prenom"), req.getParameter("nom"), req.getParameter("naissance"),
						req.getParameter("poste"), req.getParameter("equipe"), req.getParameter("mail"),req.getParameter("adresse"));
				pageVerif = true;
			}
		}

		else if (req.getParameter("update") != null) {
			// Does an employee with the same email exist?
			pageVerif = false;
			if (verifMail(req, resp)) {
				this.modifyEmploye(req.getParameter("prenom"), req.getParameter("nom"), req.getParameter("naissance"),
						req.getParameter("poste"), req.getParameter("equipe"), req.getParameter("mail"),
						req.getParameter("chef"), req.getParameter("adresse"), this.mailBefore);
				pageVerif = true;
			} else {
				req.setAttribute("employe", this.employe);
			}
		}

		this.doGetOrPost(req, resp);

	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {

		if (pageVerif == true && (req.getParameter("modifier") == null || req.getParameter("createNewTeam") == null)) {
			pageVerif = false;
			initLists(true);
			req.setAttribute("employesList", this.employesList);
			req.setAttribute("teamsList", this.teamsList);
			req.setAttribute("posteList", this.posteList);
			req.setAttribute("currentPage", "manageUser");
			
		} else if (req.getParameter("createNewTeam") != null) {
			req.setAttribute("teamToCreate", "yes");
			initLists(true);
			req.setAttribute("employesList", this.employesList);
			req.setAttribute("currentPage", "createUser");
		} else {
			System.out.println(pageVerif);
			initLists(true);
			if(req.getParameter("createTeam") != null) {
				req.setAttribute("teamToCreate", "yes");
				req.setAttribute("employesList", this.employesList);
			}
			req.setAttribute("currentPage", "createUser");
			req.setAttribute("teamsList", this.teamsList);
		}
		
		
		
		req.setAttribute("currentMode", "RH");

		try {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("currentUser") != null
				&& ((Employe) req.getSession().getAttribute("currentUser")).isRH()) {
			this.doProcess(req, resp);
		} else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}

	protected void createUser(String firstName, String Surname, String BirthDate, String poste, String team,
			String mail,String address) {
		int nbteam;
		boolean RH = false;
		boolean TL = false;
		if (poste.contains("RH") || poste.contains("rh") || poste.contains("Rh") || poste.contains("rH")) {
			RH = true;
		}
		if (team.equals("0")) {
			nbteam = 0;

		} else {
			nbteam = Integer.parseInt(team);
		}
		Employe emp = new Employe(mail, firstName, Surname, BirthDate, address, 25, RH, TL, nbteam);
		this.employeService.ajoutEmploye(emp);

	}

	protected void modifyEmploye(String firstName, String surName, String birthDate, String function, String team,
			String mail, String teamleader, String address, String mailBefore) {
		int nbteam;
		boolean RH = false;
		boolean TL = false;
		if (function.contains("RH") || function.contains("rh") || function.contains("Rh") || function.contains("rH")) {
			RH = true;
		}
		if (teamleader.equals("oui")) {
			TL = true;
		}
		if (team.equals("")) {
			nbteam = 0;

		} else {
			nbteam = Integer.parseInt(team);
		}
		Employe emp = new Employe(mail, firstName, surName, birthDate, address, 25, RH, TL, nbteam);
		this.employeService.updateEmploye(emp, mailBefore);

	}

	private void initLists(boolean isRespoRH) {
		// TODO Auto-generated method stub
		if (isRespoRH) {
			this.employesList = (ArrayList<Employe>) this.employeService.getAllEmployes();
			this.teamsList = (ArrayList<Team>) this.teamService.getAllTeams();
			this.posteList = new ArrayList<String>();
			this.posteList.add("Employe");
			this.posteList.add("EmployeRH");
			this.posteList.add("TeamLeader");
			this.posteList.add("RespoRH");
		}
	}

	private boolean verifMail(HttpServletRequest req, HttpServletResponse resp) {
		boolean verif = true;
		String mail = req.getParameter("mail");
		if (mail.equals("") || isValidEmail(mail) == false ) {
			req.setAttribute("verifMail", "err");
			verif = false;
		}else {
			mailList = (ArrayList<String>) this.employeService.getAllMail();
			if (req.getParameter("update") != null) {
				if (mailList.size() != 0) {
					for (int i = 0; i < mailList.size(); i++) {
						if (mail.contentEquals(mailList.get(i)) && !this.mailBefore.contentEquals(mail)) {
							verif = false;
							req.setAttribute("verifMail", "no");
						}
					}
				}
			} else {
				for (int i = 0; i < mailList.size(); i++) {
					if (mail.contentEquals(mailList.get(i))) {
						verif = false;
						req.setAttribute("verifMail", "no");
					}
				}
			}

		}
		return verif;
	}

	private void createTeam(String name, String description, String teamleader) {
		this.teamService.ajoutTeam(name, description, teamleader);
	}

	private boolean verifTeam(HttpServletRequest req, HttpServletResponse resp) {
		boolean verif = true;
		String name = req.getParameter("nameTeam");
		if (name.equals("")) {
			req.setAttribute("verifNameTeam", "err");
			verif = false;
		} else {
			List<String> listeNomTeam = this.teamService.getAllNameTeam();
			for (String l : listeNomTeam) {
				if (l.equals(name)) {
					req.setAttribute("verifNameTeam", "no");
					verif = false;
				}
			}
		}
		return verif;
	}
	
	private boolean isValidEmail(String email){
		return email != null && email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");
	}
}
