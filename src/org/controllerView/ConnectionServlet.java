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

public class ConnectionServlet extends HttpServlet{
	
	private EmployeService employeService = new EmployeService();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!this.isValidEmail(this.getInputValue(req, "email"))) {	
			req.setAttribute("connectionNotValid", "Email non valide");
			this.doProcess(req,resp);
		}else if(!this.isValidPassword(this.getInputValue(req, "password"))){
			req.setAttribute("connectionNotValid", "Veuillez entrer un mot de passe valide"+this.getInputValue(req, "password"));
			this.doProcess(req,resp);
		}else{
			Employe user = this.employeService.checkCredentials(this.getInputValue(req, "email"), this.getInputValue(req, "password"));
			if(user != null) {
				this.goHome(req, resp, user);
			}else {
				req.setAttribute("connectionNotValid", "Veuillez vérifier vos identifiants");
				this.doProcess(req, resp);
			}
			
		}
		
	}
	
	
	//TO GO HOME
	private void goHome(HttpServletRequest request, HttpServletResponse response, Employe user) {
		String pageName="/Home";
		
        HttpSession session = request.getSession();

		//Employe currentUser = employeService.getEmploye(this.getInputValue(request, "email"));
		
		session.setAttribute("currentUser", user);

		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

		try {

			rd.forward(request, response);

		} catch (ServletException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) {
		//request.setAttribute("listEmployes", listEmployes);
		
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
	
	private boolean isValidEmail(String email){
		return email != null && email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");
	}
	
	private boolean isValidPassword(String pwd){
		return pwd != null && pwd.trim().length() > 0;
	}
	
	private String getInputValue(HttpServletRequest request, String name) {
		String val = request.getParameter(name);
		
		return (val==null || val.trim().length()==0)?null:val;
	}
}
