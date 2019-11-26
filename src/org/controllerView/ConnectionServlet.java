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
//Classe permettant de gérer la connection d'un utilisateur avec la jsp connection
public class ConnectionServlet extends HttpServlet{
	
	private EmployeService employeService = new EmployeService();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doProcess(req, resp);
	}
	//Fonction qui gère lorsque l'utilisateur essaie de se connecter
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Si l'utilisateur n'a pas un email valide
		if(!this.isValidEmail(this.getInputValue(req, "email"))) {	
			req.setAttribute("connectionNotValid", "Email non valide"); //on le previent
			this.doProcess(req,resp);
		}else if(!this.isValidPassword(this.getInputValue(req, "password"))){ //Si le mot de passe est valide
			req.setAttribute("connectionNotValid", "Veuillez entrer un mot de passe valide"+this.getInputValue(req, "password"));
			this.doProcess(req,resp);
		}else{
			//Sinon on vérifie les identifiants
			Employe user = this.employeService.checkCredentials(this.getInputValue(req, "email"), this.getInputValue(req, "password"));
			if(user != null) { // Si l'employé a bien été trouvé, il est retourné, sinon il est null
				this.goHome(req, resp, user); // ON redirige alors l'utilisateur à l'accueil.
			}else {
				req.setAttribute("connectionNotValid", "Veuillez vérifier vos identifiants"); // Sinon on le previent
				this.doProcess(req, resp);
			}
			
		}
		
	}
	
	
	//Pour aller à l'accueil
	private void goHome(HttpServletRequest request, HttpServletResponse response, Employe user) {
		String pageName="/DaysOffManager/Calendar"; // Page choisie comme accueil
		
        HttpSession session = request.getSession();

		session.setAttribute("currentUser", user);

		try {
			response.sendRedirect(pageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Process lors d'un get ou d'un post 
	private void doProcess(HttpServletRequest request, HttpServletResponse response) {
		//Permet d'ouvrir la jsp de connection
		String pageName="/connection.jsp";

		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

		try {

			rd.forward(request, response);

		} catch (ServletException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
	//Si l'email correspond au regex d'un email
	private boolean isValidEmail(String email){
		return email != null && email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");
	}
	//SI le mot de passe est bien entré
	private boolean isValidPassword(String pwd){
		return pwd != null && pwd.trim().length() > 0;
	}
	//Afin de récupérer proprement le contenu des champs
	private String getInputValue(HttpServletRequest request, String name) {
		String val = request.getParameter(name);
		
		return (val==null || val.trim().length()==0)?null:val;
	}
}
