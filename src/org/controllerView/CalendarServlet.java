package org.controllerView;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.databaseManage.DemandService;
import org.databaseManage.EmployeService;
import org.model.Demand;


public class CalendarServlet extends HttpServlet{
	
	private DemandService demandService = new DemandService();
	
	private ArrayList<Demand> demandsList;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Mode : "  + req.getParameter("goToRHMode"));
		if(req.getParameter("goToRHMode")!= null) {
			req.setAttribute("currentMode", "employe");
		}
		this.doProcess(req, resp);
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		this.initDemand();
		
		req.setAttribute("demandsList", this.demandsList);
		req.setAttribute("currentPage", "calendar");
		
		try {
            this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	private void initDemand() {
		this.demandsList = (ArrayList<Demand>) this.demandService.getAllDemands();
	}
}
