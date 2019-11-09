package org.databaseManage;
import org.model.Demand;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					listDemandes.add(new Demand(rs.getString("status"),rs.getString("startDate"),rs.getString("endDate"),rs.getString("requestDate"),rs.getString("motif"),rs.getInt("nbDays")));
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
	
	public List<String> findAllStatus(){
		List<String> status = new ArrayList<String>();
		
		return new ArrayList<String>(Arrays.asList("approuved","refused","canceled","pending"));				
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
	
	public List<Demand> findStatutXDemandeFromTeam(String mail,String statut) {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from demand where statut ='"+statut+"'  and employe in(select mail from employe where team=(select noTeam from team where leader='"+ mail +"'));" );
	}
}
