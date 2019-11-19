package org.databaseManage;
import org.model.Demand;
import org.model.Employe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.databaseManage.EmployeService;


public class DemandDAOImpl {
	/**
	 * common method used to query DB
	 * 
	 * @param query
	 *            the SQL query to use
	 * @return a list of Demandes built from the SQL query
	 */
	private List<Demand> findBy(String query) {
		Connection conn = null;
		List<Demand> listDemandes = new ArrayList<Demand>();
		
		Statement stat = null;
		ResultSet rs = null;
		EmployeService employeService = new EmployeService();
		
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					Employe emp = employeService.getEmploye(rs.getString("employe"));
					
					listDemandes.add(new Demand(rs.getInt("id"),emp,rs.getString("status"),rs.getString("beginDate"),rs.getString("endDate"),rs.getString("demandDate"),rs.getString("reason"),rs.getInt("duration")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// always clean up all resources in finally block
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stat, rs);
			}
		}
		
		
		
		return listDemandes;
	}

	public boolean setDemandStatus(String string, String status) {
		return updateDemand("update demand set status='"+status+"' where id='"+string+"';");
	}
	
	private boolean updateDemand(String query) {
		Connection conn = null;
		ResultSet rs = null;
		Statement stat = null;
		int a = 0;
		
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				a = stat.executeUpdate(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stat,rs);
			}
		}
		return a != 0;
	}
	
	public ArrayList<List<String>> findNbDemandPerX(String query,String what,String perWhat){
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		ArrayList<List<String>> daysOffPerMonth = new ArrayList<List<String>>();
		
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					List<String> list = new ArrayList<String>();
					list.add(rs.getString(perWhat));
					list.add(rs.getString(what));
					daysOffPerMonth.add(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stat, rs);
			}
		}

		return daysOffPerMonth;
	}
	
	public boolean insertIntoDemand(String mail, String fromDate, String toDate, String reason, String duration) {
		return updateDemand("Insert into demand(employe, beginDate, endDate, reason, duration) values ('"+mail+"','"+fromDate+"','"+toDate+"','"+reason+"','"+duration+"');");
	}
	
	public List<Demand> findAll() {
		return findBy("select * from demand");
	}
	
	public List<Demand> findAllButRH() {
		return findBy("select * from demand where employe NOT IN (select mail from employe where fonction = 'RespoRH' or fonction = 'EmployeRH');");
	}
	
	public List<String> findAllStatus(){
		List<String> status = new ArrayList<String>();		
		return new ArrayList<String>(Arrays.asList("approved","refused","canceled","pending"));				
	}
	
	public List<Demand> findAllDemandeFromEmploye(String mail) {
		return findBy("select * from demand where employe='"+mail+"';");
	}

	public List<Demand> findAllDemandeFromTeam(String mail) {

		return findBy("select * from demand where employe in(select mail from employe where team=(select noTeam from team where leader='"+ mail +"'));" );
	}
	
	public List<Demand> findStatutXDemandeFromTeam(String mail,String status) {
		return findBy("select * from demand where status ='"+status+"'  and employe in(select mail from employe where team=(select noTeam from team where leader='"+ mail +"'));" );
	}
	
	public List<Demand> findFilteredDemand(String status,String mail, String team, Boolean RH) {
		String requete = "select * from demand where ";
		if(!status.equals("all")) {
			requete += "status ='"+status+"'";
			if(!mail.equals("all")||!team.equals("all"))requete += " and ";
		}
		if(!mail.equals("all"))requete += "employe ='"+mail+"'";
		if(!team.equals("all") && mail.equals("all"))requete += "employe in(select mail from employe where team='"+ team +"')";
		
		if(requete.equals("select * from demand where ")){
			return RH?findAll():findAllButRH();
		}
		System.out.println(requete);
		return this.findBy(requete + "order by status;");
	}
	
	
	public ArrayList<List<String>> getDaysOffPerTeam(){
		return this.findNbDemandPerX("SELECT team, SUM(duration) AS nbDays FROM demand NATURAL JOIN employe GROUP BY team;","nbDays","team");
	}
	//to do
	public ArrayList<List<String>> getDaysOffPerMonth(){
		return this.findNbDemandPerX("SELECT MONTH(beginDate) AS month, SUM(duration) AS nbDays  FROM demand GROUP BY MONTH(beginDate);","nbDays","month");
	}
	
	public ArrayList<List<String>> getDaysOffPerReason(){
		return this.findNbDemandPerX("SELECT reason, SUM(duration) AS nbDays  FROM demand GROUP BY reason;","nbDays","reason");
	}
	
	public ArrayList<List<String>> getDaysOffPerJob(){
		return this.findNbDemandPerX("SELECT fonction, SUM(duration) AS nbDays FROM demand NATURAL JOIN employe GROUP BY fonction;","nbDays","fonction");
	}
}
