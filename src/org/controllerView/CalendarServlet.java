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
import org.model.Employe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class CalendarServlet extends HttpServlet{
	
	private DemandService demandService = new DemandService();
	private EmployeService employeService = new EmployeService();
	
	private ArrayList<Demand> demandsList;
	
	private JSONObject employeDemand = new JSONObject();
	private JSONArray employeDemandsList = new JSONArray();
		
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
		this.demandsList = (ArrayList<Demand>) this.demandService.getEmployeDemand(((Employe)req.getSession().getAttribute("currentUser")).getMail());
		
		// Build JSON
		for(Demand d: demandsList) {
			employeDemand.put("title", ((Employe)req.getSession().getAttribute("currentUser")).getMail() + " - " + d.getMotif());
			employeDemand.put("start", d.getStartDate());
			employeDemand.put("end", d.getEndDate());
			employeDemandsList.add(employeDemand.toString());
		}
		
		System.out.println(employeDemandsList);
							
		req.setAttribute("currentPage", "calendar");
		req.setAttribute("employeDemandsList", this.employeDemandsList);
		
		try {
            this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}
