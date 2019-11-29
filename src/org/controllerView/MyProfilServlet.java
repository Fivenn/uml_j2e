package org.controllerView;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.databaseManage.EmployeService;
import org.model.Employe;
//Fonction permettant d'afficher le profil
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
			
			//Si l'utilisateur souhaite changer de mdp
			if(req.getParameter("submit_pwd") != null) {
				if (req.getParameter("new_pwd").equals("") || req.getParameter("re_new_pwd").equals("") || req.getParameter("old_pwd").equals("")) {
					req.setAttribute("errorUpdatingPwd", "Veuillez saisir votre mot de passe."); //On previent l'utilisateur qu'un des mdp n'est pas saisi
				}else if (!req.getParameter("new_pwd").equals(req.getParameter("re_new_pwd"))) {
		    		req.setAttribute("errorUpdatingPwd", "Les deux mots de passe saisis ne concordent pas."); //Sinon on previent que les deux sont différents
			    }else if(this.employeService.checkCredentials(((Employe)req.getSession().getAttribute("currentUser")).getMail(),req.getParameter("old_pwd"))!= null) {
					this.employeService.updatePassword(((Employe)req.getSession().getAttribute("currentUser")).getMail(), req.getParameter("new_pwd")); // On change le mot de passe si tout va bien
			    	req.setAttribute("errorUpdatingPwd", "Mot de passe modifié"); //On previent l'utilisateur que le mdp est modifié				
			    }else {
					req.setAttribute("errorUpdatingPwd", "L'ancien mot de passe est faux"); //On previent l'utilisateur l'ancien mdp est faux
				}
			}
			
			//Si il souhaite changer d'adresse on la change directement
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

