package org.databaseManage;
import org.model.Employe;
import org.model.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeamDAOImpl {
	/**
	 * common method used to query DB
	 * 
	 * @param query
	 *            the SQL query to use
	 * @return a list of Employes built from the SQL query
	 */
	private List<Team> findBy(String query) {
		Connection conn = null;
		List<Team> listTeams = new ArrayList<Team>();
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					listTeams.add(new Team(rs.getInt("noTeam"),rs.getString("name"),rs.getString("leader"),rs.getString("description")));												
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
		return listTeams;
	}

	public List<Team> findByLeader(String email) {
		List<Team> listTeam = findBy("select * from team where leader = '" + email + "'");
		return listTeam;
	}
	
	public List<Team> findAllTeams() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from team");
	}

	public List<Team> findAllTeamsButRH() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from team where leader NOT IN (select mail from employe where fonction = 'RespoRH');");
	}
	
	public Team findEmployeTeam(String mail) {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		List<Team> listTeams= findBy("select * from team where noTeam=(select noTeam from employe where mail ='"+mail+"');");
		return listTeams.size()!=0?listTeams.get(0):null;
	}
	
	public List<String> findAllNameTeam() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		List<String> listTeams = new ArrayList <String>();
		String query = "SELECT name FROM team";
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					listTeams.add(rs.getString("name"));
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
		return listTeams;
	}
	
	public void addTeam(String name, String description, String leader) {
		// add a default password
		String query = "";
		query = "INSERT INTO team (name,description,leader) VALUES ('"+name+"','"+description+"','"+leader+"');";
		System.out.println(query);
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				stat.executeUpdate(query);

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// always clean up all resources in finally block
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stat, rs);
			}
		}
	}
	
}
