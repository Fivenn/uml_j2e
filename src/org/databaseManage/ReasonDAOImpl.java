package org.databaseManage;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.databaseManage.EmployeService;

//Fonction permettant de récupérer la liste des motifs ( rtt, ... )
public class ReasonDAOImpl {
	/**
	 * common method used to query DB
	 * 
	 * @param query
	 *            the SQL query to use
	 * @return a list of Demandes built from the SQL query
	 */
	//Fonction faisant une requète à la base de données donnant toutes les raisons
	public List<String> findAllReasons() {
		Connection conn = null;
		List<String> listReason = new ArrayList<String>();
		
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getInstance().getConnection(); //Connection à la BDD
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery("Select * from reason;"); //Sélectionner toutes les raisons
				while (rs.next()) {
					listReason.add(rs.getString("name")); //Les ajouter à la liste
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
