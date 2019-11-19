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
import org.model.Demand;
import org.model.Employe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class CalendarServlet extends HttpServlet {

    private DemandService demandService = new DemandService();

    private ArrayList < Demand > demandsList;
    private ArrayList < String > reasonsList = (ArrayList < String > ) demandService.getAllReasons();

    private JSONArray employeDemandsList = new JSONArray();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Mode : " + req.getParameter("goToRHMode"));
        if (req.getParameter("goToRHMode") != null) {
            req.setAttribute("currentMode", "employe");
        } else if (req.getParameter("askDaysOff") != null) {
            this.demandService.insertIntoDemand(((Employe) req.getSession().getAttribute("currentUser")).getMail(), req.getParameter("fromDate"), req.getParameter("toDate"), req.getParameter("reason"), req.getParameter("nbDays"));
        }
        this.doProcess(req, resp);
    }

    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) {
        this.demandsList = (ArrayList < Demand > ) this.demandService.getEmployeDemand(((Employe) req.getSession().getAttribute("currentUser")).getMail());

        // Build JSON
        for (Demand d: demandsList) {
            JSONObject employeDemand = new JSONObject();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String endDate = d.getEndDate();
            String status = d.getStatus();
            
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
                employeDemand.put("color", "#aab7b8");

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
                employeDemand.put("start", d.getStartDate());
                employeDemand.put("end", endDate.toString());
                employeDemand.put("color", "#3498db");

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
                employeDemand.put("start", d.getStartDate());
                employeDemand.put("end", endDate.toString());
                employeDemand.put("color", "#3498db");

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
}