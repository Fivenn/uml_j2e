package org.controllerView;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.databaseManage.EmployeService;
import org.model.Employe;

public class MyProfilServlet extends HttpServlet {
HttpServlet httpServlet;
	
	private EmployeService employeService = new EmployeService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}
	
	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("currentUser")!=null) {
			
			if (req.getParameter("new_pwd") != null && req.getParameter("re_new_pwd") != null && (req.getParameter("new_pwd").equals("") || req.getParameter("re_new_pwd").equals(""))) {
	    		req.setAttribute("errorUpdatingPwd", "Veuillez saisir votre mot de passe.");
			} else if (req.getParameter("new_pwd") != null && req.getParameter("re_new_pwd") != null && req.getParameter("new_pwd")==req.getParameter("re_new_pwd")) {
				this.employeService.updatePassword(((Employe)req.getSession().getAttribute("currentUser")).getMail(), req.getParameter("new_pwd"));
	        } else if (req.getParameter("new_pwd") != null && req.getParameter("re_new_pwd") != null && !req.getParameter("new_pwd").equals(req.getParameter("re_new_pwd"))) {
	    		req.setAttribute("errorUpdatingPwd", "Les deux mots de passe saisis ne concordent pas.");
	        }
			
			if (req.getParameter("addr")!=null) {
				this.employeService.updateAddress(((Employe)req.getSession().getAttribute("currentUser")).getMail(), req.getParameter("addr"));
			}
			
			this.doProcess(req, resp);
		}else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("currentPage", "profil");
		try {
            this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}

