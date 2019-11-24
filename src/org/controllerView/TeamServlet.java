package org.controllerView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.databaseManage.DemandService;
import org.databaseManage.EmployeService;
import org.databaseManage.TeamService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.model.Demand;
import org.model.Employe;

public class TeamServlet extends HttpServlet {

	private DemandService demandService = new DemandService();
	private EmployeService employeService = new EmployeService();
	private TeamService teamService = new TeamService();
	private ArrayList<Demand> demandsList;
	private ArrayList<Employe> employeesList;
	private ArrayList<String> statusList;
	private ArrayList<String> reasonsList;
	private JSONArray teamDemandsList = new JSONArray();
	HttpServlet httpServlet;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGetOrPost(req, resp);
	}

	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("currentUser") != null) {
			if (req.getParameter("filter") != null) {
				this.demandsList = (ArrayList<Demand>) this.demandService.getEmployeDemand(req.getParameter("employe"));
				req.setAttribute("mail", req.getParameter("employe"));
			} else {
				this.demandsList = (ArrayList<Demand>) this.demandService
						.getTeamDemands(((Employe) req.getSession().getAttribute("currentUser")).getMail());
				req.setAttribute("mail", "all");
			}

			if (req.getParameter("tableTeamDemand") != null) {
				req.setAttribute("table", true);
			}
			this.doProcess(req, resp);
		} else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		this.employeesList = (ArrayList<Employe>) this.employeService
				.getMyTeam(((Employe) req.getSession().getAttribute("currentUser")).getMail());
		this.statusList = (ArrayList<String>) demandService.getStatus();
		this.reasonsList = (ArrayList<String>) demandService.getAllReasons();

		// Build JSON
		for (Demand d : demandsList) {
			JSONObject teamDemand = new JSONObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			String endDate = d.getEndDate();
			String status = d.getStatus();

			switch (status) {
			case "pending":
				// add one day to the end date
				try {
					c.setTime(sdf.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.add(Calendar.DATE, 1);
				endDate = sdf.format(c.getTime());

				teamDemand.put("title", d.getEmploye().getMail() + " - " + d.getMotif() + " - " + d.getStatus());
				teamDemand.put("start", d.getStartDate());
				teamDemand.put("end", endDate.toString());
				teamDemand.put("color", "#007bff");
				teamDemand.put("textColor", "#FFFFFF");

				teamDemandsList.add(teamDemand);
				break;
			case "approved":
				// add one day to the end date
				try {
					c.setTime(sdf.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.add(Calendar.DATE, 1);
				endDate = sdf.format(c.getTime());

				teamDemand.put("title", d.getEmploye().getMail() + " - " + d.getMotif() + " - " + d.getStatus());
				teamDemand.put("description", d.getCommentary());
				teamDemand.put("start", d.getStartDate());
				teamDemand.put("end", endDate.toString());
				teamDemand.put("color", "#28a745");
				teamDemand.put("textColor", "#FFFFFF");

				teamDemandsList.add(teamDemand);
				break;
			case "refused":
				// add one day to the end date
				try {
					c.setTime(sdf.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.add(Calendar.DATE, 1);
				endDate = sdf.format(c.getTime());

				teamDemand.put("title", d.getEmploye().getMail() + " - " + d.getMotif() + " - " + d.getStatus());
				teamDemand.put("description", d.getCommentary());
				teamDemand.put("start", d.getStartDate());
				teamDemand.put("end", endDate.toString());
				teamDemand.put("color", "#dc3545");
				teamDemand.put("textColor", "#FFFFFF");

				teamDemandsList.add(teamDemand);
				break;
			}
		}
		
		req.setAttribute("currentPage", "team");
		req.setAttribute("reasonsList", this.reasonsList);
		req.setAttribute("statusList", this.statusList);
		req.setAttribute("demandsList", this.demandsList);
		req.setAttribute("employeesList", this.employeesList);
		req.setAttribute("teamDemandsList", this.teamDemandsList);

		try {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

		teamDemandsList.clear();
	}
}
