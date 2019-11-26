package org.controllerView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.databaseManage.EmployeService;
import org.model.Employe;
//Cette classe sert à gérer la session utilisateur et le template de toute les pages de l'application ( excepté la connexion )
public class HomeServlet extends HttpServlet {
	private EmployeService employeService = new EmployeService();
	
	
	//Methode appelée lors d'un get
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	//Methode appelée lors d'un post
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	//Methode appelée lors d'un get et d'un post, permet la redirection de l'utilisateur selon son état de connection.
	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) {
		if(req.getParameter("deconnection")!=null) { // Si l'utilisateur demande une déconnexion
			this.deconnection(req, resp); //
		}else if(req.getSession().getAttribute("currentUser")!=null) { // Si l'utilisateur est bien connecté
			if(req.getAttribute("currentPage") == null) { //Si il n'y a pas de page selectionnée
				req.setAttribute("currentPage", "calendar"); //Alors la page par défaut est le calendrier 
			}
			this.doProcess(req, resp);	// On va sur la page
		}else{
			this.redirectConnection(req,resp); //Sinon on redirige l'utilisateur vers la connection.
		}
	}
	
	//Cette méthode permet de rediriger l'utilisateur vers la page de son choix.
	private void doProcess(HttpServletRequest request, HttpServletResponse response) {

		List<Employe> listEmployes = employeService.getAllEmployes();
		
		
		
		request.setAttribute("listEmployes", listEmployes);
		String pageName="/home.jsp"; // La page template 

		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

		try {

			rd.forward(request, response); // On redirige 

		} catch (ServletException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
	//Cette fonction permet de rediriger l'utilsiateur lorsqu'il n'est plus connecté vers la page de connexion.
	private void redirectConnection(HttpServletRequest request, HttpServletResponse response) {
		String pageName="/DaysOffManager/Connection";

		try {
			response.sendRedirect(pageName);
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}
	//Cette fonction deconnecte l'utilisateur
	private void deconnection(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("currentUser"); //supprime la session.
        request.getSession().invalidate();
        this.redirectConnection(request, response); //Redirection vers la page de connection.
	}
}

