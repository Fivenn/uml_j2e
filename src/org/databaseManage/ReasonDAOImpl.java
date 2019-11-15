package org.databaseManage;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.databaseManage.EmployeService;


public class ReasonDAOImpl {
	/**
	 * common method used to query DB
	 * 
	 * @param query
	 *            the SQL query to use
	 * @return a list of Demandes built from the SQL query
	 */
	public List<String> findAllReasons() {
		Connection conn = null;
		List<String> listReason = new ArrayList<String>();
		
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery("Select * from reason;");
				while (rs.next()) {
					listReason.add(rs.getString("name"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stat, rs);
			}
		}

		return listReason;
	}
	
}
