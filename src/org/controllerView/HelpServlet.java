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

//Classe permettant d'afficher l'aide
public class HelpServlet extends HttpServlet {
HttpServlet httpServlet;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	//Fonction permettant d'acceder à la page en vérifiant que la session est bien en cours
	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("currentUser")!=null) {
			this.doProcess(req, resp);
		}else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("currentPage", "help");
		try {
            this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}

