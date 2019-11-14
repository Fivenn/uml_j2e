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
		this.doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//R�cup�ration des donn�es du formulaire de la page createUser.jsp
		int numeroEquipe;
		String prenom = req.getParameter("prenom");
		String nom = req.getParameter("nom");
		String naissance = req.getParameter("naissance");
		String poste = req.getParameter("poste");
		String equipe = req.getParameter("equipe");
		String mail = req.getParameter("mail");
		String chef = req.getParameter("chef");
		String adresse = req.getParameter("adresse");
		String tel = req.getParameter("tel");
		boolean RH = false;
		boolean TL = false;
		
		if(poste.contains("RH")||poste.contains("rh")||poste.contains("Rh")||poste.contains("rH")) {
			RH = true;
		}
		if(chef.contains("oui")) {
			TL = true;
		}
		if(equipe == "") {
			numeroEquipe = 0;
			
		}
		else {
			numeroEquipe = Integer.parseInt(equipe);
		}
		Employe emp = new Employe(mail, prenom,nom,naissance,adresse, 25, RH,TL,numeroEquipe);
		employeService.ajoutEmploye(emp);
		System.out.println(RH+" "+TL);
		
		String pageName="/createUser.jsp";

		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

		try {

			rd.forward(req, resp);

		} catch (ServletException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
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
}

