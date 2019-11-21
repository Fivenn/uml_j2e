package org.controllerView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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


public class CalendarServlet extends HttpServlet {

    private DemandService demandService = new DemandService();
    private EmployeService employeService = new EmployeService();
    
    private ArrayList < Demand > demandsList;
    private ArrayList < String > reasonsList = (ArrayList < String > ) demandService.getAllReasons();

    private JSONArray employeDemandsList = new JSONArray();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGetOrPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Mode : " + req.getParameter("goToRHMode"));
        if (req.getParameter("goToRHMode") != null) {
            req.setAttribute("currentMode", "employe");
        } else if (req.getParameter("askDaysOff") != null) {
    		req.setAttribute("errorAskingForDays", this.isDateValid(req));
        }
        this.doGetOrPost(req, resp);
    }

    
	protected void doGetOrPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("currentUser")!=null && ((Employe)req.getSession().getAttribute("currentUser")).isRH()) {
			this.doProcess(req, resp);
		}else {
			this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
		}
	}
	

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("blblbl");
        this.demandsList = (ArrayList < Demand > ) this.demandService.getEmployeDemand(((Employe) req.getSession().getAttribute("currentUser")).getMail());

        // Build JSON
        for (Demand d: demandsList) {
            JSONObject employeDemand = new JSONObject();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String endDate = d.getEndDate();
            String status = d.getStatus();
            
            System.out.println(d.getMotif());

                        
            switch(status) {
            case "pending":
                // add one day to the end date
                try {
                    c.setTime(sdf.parse(endDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, 1);
                endDate = sdf.format(c.getTime());

                employeDemand.put("title", ((Employe) req.getSession().getAttribute("currentUser")).getMail() + " - " + d.getMotif() + " - " + d.getStatus());
                employeDemand.put("start", d.getStartDate());
                employeDemand.put("end", endDate.toString());
                employeDemand.put("color", "#007bff");
                employeDemand.put("textColor", "#FFFFFF");
                
                employeDemandsList.add(employeDemand);
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

                employeDemand.put("title", ((Employe) req.getSession().getAttribute("currentUser")).getMail() + " - " + d.getMotif() + " - " + d.getStatus());
                employeDemand.put("description", d.getCommentary());
                employeDemand.put("start", d.getStartDate());
                employeDemand.put("end", endDate.toString());
                employeDemand.put("color", "#28a745");
                employeDemand.put("textColor", "#FFFFFF");
                
                employeDemandsList.add(employeDemand);
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

                employeDemand.put("title", ((Employe) req.getSession().getAttribute("currentUser")).getMail() + " - " + d.getMotif() + " - " + d.getStatus());
                employeDemand.put("description", d.getCommentary());
                employeDemand.put("start", d.getStartDate());
                employeDemand.put("end", endDate.toString());
                employeDemand.put("color", "#dc3545");
                employeDemand.put("textColor", "#FFFFFF");

                employeDemandsList.add(employeDemand);
            	break;
            }
        }

        req.setAttribute("currentPage", "calendar");
        req.setAttribute("reasonsList", this.reasonsList);
        req.setAttribute("employeDemandsList", this.employeDemandsList);

        try {
            this.getServletContext().getRequestDispatcher("/Home").forward(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        employeDemandsList.clear();
    }
	
	private String isDateValid(HttpServletRequest req) {
		// TODO Auto-generated method stub
    	String message = "";
    	try {
        	if(((Employe) req.getSession().getAttribute("currentUser")).getNbDays()<Integer.parseInt(req.getParameter("nbDays"))){
        		message = "Le nombre de jours est insuffisant, il n'en reste que "+ ((Employe) req.getSession().getAttribute("currentUser")).getNbDays() +".";
        	
        	}else if(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("fromDate")).after(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("toDate")))) {
        		message = "La première date doit être inférieure à la deuxième.";
        	
        	}else if(LocalDate.parse(req.getParameter("fromDate")).isBefore(LocalDate.now().plusDays(2)) && !req.getParameter("reason").equals("Enfants malades") && !req.getParameter("reason").equals("Raisons familiales")) {
        		message = "Il faut au minimum 48h pour poser des congés.";
        	}
        	
        	if(message.contentEquals("")) {
        		if(this.demandService.insertIntoDemand(((Employe) req.getSession().getAttribute("currentUser")).getMail(), req.getParameter("fromDate"), req.getParameter("toDate"), req.getParameter("reason"), req.getParameter("nbDays"))) {
            		message = "Demande envoyée au service RH. Elle sera traitée dans les meilleurs délais.";
        		}
        	}
    	}catch(Exception e){
    		message = "Demande non valide.";
        }
    	return message;
		
	}
    
}