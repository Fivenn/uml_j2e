package org.databaseManage;
import org.model.Employe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeDAOImpl {
	/**
	 * common method used to query DB
	 * 
	 * @param query
	 *            the SQL query to use
	 * @return a list of Employes built from the SQL query
	 */
	private List<Employe> findBy(String query) {
		Connection conn = null;
		List<Employe> listEmployes = new ArrayList<Employe>();
//		List<Employe> listEmployes2 = new ArrayList<Employe>();
//		listEmployes2.add(new Employe("u","u","u","u",25));
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					listEmployes.add(new Employe(rs.getString("mail"),rs.getString("firstName"),rs.getString("surname"),rs.getString("address"),rs.getInt("nbDays")));
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
		return listEmployes;
	}

	public Employe findByEmail(String email) {
		List<Employe> listEmployes = findBy("select * from employe where mail = '" + email + "'");
		System.out.println(listEmployes);
		return listEmployes != null?listEmployes.get(0):null;
	}
	
	public boolean checkCredentials(String email, String password) {
		System.out.println(email);
		System.out.println(password);
		return findBy("select * from employe where mail = '" + email + "' and pwd = '"+ password+"'").size()>0 ;
	}
	
	public List<Employe> findByAll() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from employe");
	}

	public List<Employe> findByTitle(String searchText) {
		// watch out : this query is case sensitive. use upper function on title
		// and searchText to make it case insensitive
		return findBy("select * from employe where name like '%" + searchText + "%'");

	}
	
}
