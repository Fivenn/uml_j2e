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
			// TODO: handle exception : main exception should thrown to servlet
			// layer to display error message
			e.printStackTrace();

		} finally {
			// always clean up all resources in finally block
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stat, rs);
			}
		}
		
		
		
		return listDemandes;
	}

	public List<Demand> findAll() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from demand");
	}
	
	public List<Demand> findAllButRH() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from demand where employe NOT IN (select mail from employe where fonction = 'RespoRH' or fonction = 'EmployeRH');");
	}
	
	public List<String> findAllStatus(){
		List<String> status = new ArrayList<String>();
		
		return new ArrayList<String>(Arrays.asList("approved","refused","canceled","pending"));				
	}
	
	public List<Demand> findAllDemandeFromEmploye(String mail) {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from demand where employe='"+mail+"';");
	}

	public List<Demand> findAllDemandeFromTeam(String mail) {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from demand where employe in(select mail from employe where team=(select noTeam from team where leader='"+ mail +"'));" );
	}
	
	public List<Demand> findStatutXDemandeFromTeam(String mail,String status) {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from demand where status ='"+status+"'  and employe in(select mail from employe where team=(select noTeam from team where leader='"+ mail +"'));" );
	}
}
