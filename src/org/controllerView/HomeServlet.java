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

public class HomeServlet extends HttpServlet {
	private EmployeService employeService = new EmployeService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println(req.getSession().getAttribute("currentUser"));
		if(req.getParameter("deconnection")!=null) {
			this.deconnection(req, resp);
		}else if(req.getSession().getAttribute("currentUser")!=null) {
			if(req.getAttribute("currentPage") == null) {
				req.setAttribute("currentPage", "calendar");
			}
			this.doProcess(req, resp);	
		}else{
			this.redirectConnection(req,resp);
		}
	}
	private void doProcess(HttpServletRequest request, HttpServletResponse response) {

		List<Employe> listEmployes = employeService.getAllEmployes();
		
		request.setAttribute("listEmployes", listEmployes);
		String pageName="/home.jsp";

		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

		try {

			rd.forward(request, response);

		} catch (ServletException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
	private void redirectConnection(HttpServletRequest request, HttpServletResponse response) {
		String pageName="/Connection";

		RequestDispatcher rd = getServletContext().getRequestDispatcher(pageName);

		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void deconnection(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("currentUser");
        request.getSession().invalidate();
        System.out.println(request.getSession());
        this.redirectConnection(request, response);
	}
}

