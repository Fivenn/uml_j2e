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
		//Cas o� l'utilisateur souhaite cr�er une team
		if (req.getParameter("createTeam") != null) {
			if (verifTeam(req, resp)) {
				this.createTeam(req.getParameter("nameTeam"), req.getParameter("description"),
						req.getParameter("teamLeader"));
				pageVerif = true;
			}
		}
		//Cas o� l'utilisateur souhaite modifier un employ�
		if (req.getParameter("modifier") != null) {			
			employe = employeService.getEmploye((String) req.getParameter("modifier"));
			this.mailBefore = employe.getMail();
			req.setAttribute("employe", this.employe);
			req.setAttribute("verifMail", "");
			//Cas o� l'utilisateur souhaite cr�er un employ�
		} else if (req.getParameter("create") != null) {
			//On v�rifie la validit� du mail
			if (verifMail(req, resp)) {
				//On cr�� le nouvel employ�
				this.createUser(req.getParameter("prenom"), req.getParameter("nom"), req.getParameter("naissance"),
						req.getParameter("poste"), req.getParameter("equipe"), req.getParameter("mail"),req.getParameter("adresse"));
				pageVerif = true;
			}
		}
		//L'utilisateur souhaite envoyer le formulaire pour la modification de l'employ�
		else if (req.getParameter("update") != null) {
			pageVerif = false;
			//On v�rifie la validit� du mail
			if (verifMail(req, resp)) {
				//On modifie l'employ�
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
		//R�direction vers la page manageUser si le d�roulement de la modification ou de la cr�ation de team est correct
		if (pageVerif == true && (req.getParameter("modifier") == null || req.getParameter("createNewTeam") == null)) {
			pageVerif = false;
			initLists(true);
			req.setAttribute("employesList", this.employesList);
			req.setAttribute("teamsList", this.teamsList);
			req.setAttribute("posteList", this.posteList);
			req.setAttribute("currentPage", "manageUser");
		
		} else if (req.getParameter("createNewTeam") != null) {//Appel du formulaire de cr�ation de team
			req.setAttribute("teamToCreate", "yes");
			initLists(true);
			req.setAttribute("employesList", this.employesList);
			req.setAttribute("currentPage", "createUser");
		} else {
			System.out.println(pageVerif);
			initLists(true);
			if(req.getParameter("createTeam") != null) { //On reste sur la page de cr�ation de team (en cas de mauvaises informations)
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
	//Cette m�thode permet de cr�er un utilisateur dans la base de donn�es
	protected void createUser(String firstName, String Surname, String BirthDate, String poste, String team,
			String mail,String address) {
		int nbteam;
		boolean RH = false;
		boolean TL = false;
		//Le poste concern� est-il un poste rh?
		if (poste.contains("RH") || poste.contains("rh") || poste.contains("Rh") || poste.contains("rH")) {
			RH = true;
		}
		if (team.equals("0")) {//L'employ� n'appartient � aucune team
			nbteam = 0;

		} else {
			nbteam = Integer.parseInt(team); 
		}
		Employe emp = new Employe(mail, firstName, Surname, BirthDate, address, 25, RH, TL, nbteam);
		//Ajout de l'employ� dans la base de donn�es
		this.employeService.ajoutEmploye(emp);

	}
	//Cette m�thode permet de cr�er un utilisateur dans la base de donn�es
	protected void modifyEmploye(String firstName, String surName, String birthDate, String function, String team,
			String mail, String teamleader, String address, String mailBefore) {
		int nbteam;
		boolean RH = false;
		boolean TL = false;
		//Le nouveau poste concern� est-il un poste rh?
		if (function.contains("RH") || function.contains("rh") || function.contains("Rh") || function.contains("rH")) {
			RH = true;
		}
		//L'employ� est-il un chef d'�quipe
		if (teamleader.equals("oui")) {
			TL = true;
		}
		if (team.equals("")) {//L'employ� n'appartient � aucune team
			nbteam = 0;

		} else {
			nbteam = Integer.parseInt(team);
		}
		Employe emp = new Employe(mail, firstName, surName, birthDate, address, 25, RH, TL, nbteam);
		//Modification de l'employ� dans la base de donn�es
		this.employeService.updateEmploye(emp, mailBefore);

	}
	//Cette m�thode permet d'initialiser les listes des employ�s, des team et des postes
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
	//Cette m�thode permet de v�rifier la validit� du mail re�u dans les formulaires de modification et cr�ation d'un employ�
	private boolean verifMail(HttpServletRequest req, HttpServletResponse resp) {
		boolean verif = true;
		String mail = req.getParameter("mail");
		if (mail.equals("") || isValidEmail(mail) == false ) {//Le champ mail est-il vide ou est-il valide?
			req.setAttribute("verifMail", "err");
			verif = false;
		}else {//Le mail existe t-il dans d�j� dans la base de donn�es?
			mailList = (ArrayList<String>) this.employeService.getAllMail();
			if (req.getParameter("update") != null) { //Cas de v�rification pour la modification d'un employ�
				if (mailList.size() != 0) {
					for (int i = 0; i < mailList.size(); i++) {
						if (mail.contentEquals(mailList.get(i)) && !this.mailBefore.contentEquals(mail)) {
							verif = false;
							req.setAttribute("verifMail", "no");
						}
					}
				}
			} else { //Cas de cr�ation d'un employ�
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
	//Cette m�thode permet de cr�er une team et de l'ajouter dans la base de donn�es
	private void createTeam(String name, String description, String teamleader) {
		this.teamService.ajoutTeam(name, description, teamleader);
	}
	//Cette m�thode permet de v�rifier la validit� du nom de la team
	private boolean verifTeam(HttpServletRequest req, HttpServletResponse resp) {
		boolean verif = true;
		String name = req.getParameter("nameTeam");
		if (name.equals("")) { //La team n'a pas de nom
			req.setAttribute("verifNameTeam", "err");
			verif = false;
		} else {//Le nom de la team existe t-il dans la base de donn�es?
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
	//Cette m�thode permet de v�rifier la syntaxe de l'email re�u dans les formulaires de cr�ation et de modification d'employ�s.
	private boolean isValidEmail(String email){
		return email != null && email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");
	}
}
