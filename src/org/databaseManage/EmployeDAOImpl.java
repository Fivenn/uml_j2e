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
	 * @param query the SQL query to use
	 * @return a list of Employes built from the SQL query
	 */
	private List<Employe> findBy(String query) {
		Connection conn = null;
		List<Employe> listEmployes = new ArrayList<Employe>();
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					switch (rs.getString("fonction")) {
					case "EmployeRH":
						listEmployes.add(new Employe(rs.getString("mail"), rs.getString("firstName"),
								rs.getString("surname"), rs.getString("birthDate"), rs.getString("address"),
								rs.getInt("nbDays"), true, false, rs.getInt("team")));
						break;
					case "RespoRH":
						listEmployes.add(new Employe(rs.getString("mail"), rs.getString("firstName"),
								rs.getString("surname"), rs.getString("birthDate"), rs.getString("address"),
								rs.getInt("nbDays"), true, true, rs.getInt("team")));
						break;
					case "TeamLeader":
						listEmployes.add(new Employe(rs.getString("mail"), rs.getString("firstName"),
								rs.getString("surname"), rs.getString("birthDate"), rs.getString("address"),
								rs.getInt("nbDays"), false, true, rs.getInt("team")));
						break;
					default:
						listEmployes.add(new Employe(rs.getString("mail"), rs.getString("firstName"),
								rs.getString("surname"), rs.getString("birthDate"), rs.getString("address"),
								rs.getInt("nbDays"), false, false, rs.getInt("team")));
						break;
					}
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
		return listEmployes;
	}

	public Employe findByEmail(String email) {
		List<Employe> listEmployes = findBy("select * from employe where mail = '" + email + "';");
		return listEmployes != null ? listEmployes.get(0) : null;
	}

	public Employe checkCredentials(String email, String password) {
		List<Employe> listEmployes = findBy(
				"select * from employe where mail = '" + email + "' and pwd = '" + password + "';");
		return listEmployes.size() != 0 ? listEmployes.get(0) : null;
	}

	public List<Employe> findByAll() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from employe;");
	}

	public List<Employe> findByAllButRH() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from employe where fonction != 'EmployeRH' && fonction != 'RespoRH'");
	}

	public List<Employe> findMyTeam(String mail) {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("	select * from employe where team=(select noTeam from team where leader ='" + mail + "');");
	}

	// Add an employee
	public void addEmploye(Employe emp) {
		// add a default password
		String query = "";
		String fonction = emp.getTitle();
		if (emp.isLeader() && !emp.isRH()) {
			fonction = "TeamLeader";
		} else if (emp.isLeader() && emp.isRH()) {
			fonction = "RespoRH";
		} else if (!emp.isLeader() && emp.isRH()) {
			fonction = "EmployeRH";
		}
		String pwd = emp.getFirstName().toLowerCase().charAt(0) + "" + emp.getSurname().toLowerCase().charAt(0);
		System.out.println("Le password par d�faut est:" + pwd);
		if (emp.getNbTeam() == 0) {
			query = "INSERT INTO employe (mail, firstName, surname, birthDate, address, nbDays, fonction, pwd) VALUES ("
					+ "'" + emp.getMail() + "'" + "," + "'" + emp.getFirstName() + "'" + "," + "'" + emp.getSurname()
					+ "'" + "," + "'" + emp.getBirthDate() + "'" + "," + "'" + emp.getAddress() + "'" + "," + "'"
					+ emp.getNbDays() + "'" + "," + "'" + fonction + "'" + "," + "'" + pwd + "'" + ");";
		} else {
			query = "INSERT INTO employe (mail, firstName, surname, birthDate, address, nbDays, fonction, pwd,team) VALUES ("
					+ "'" + emp.getMail() + "'" + "," + "'" + emp.getFirstName() + "'" + "," + "'" + emp.getSurname()
					+ "'" + "," + "'" + emp.getBirthDate() + "'" + "," + "'" + emp.getAddress() + "'" + "," + "'"
					+ emp.getNbDays() + "'" + "," + "'" + fonction + "'" + "," + "'" + pwd + "'" + "," + "'"
					+ emp.getNbTeam() + "'" + ");";
		}
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

	public List<String> findAllMail(){
		List<String> mailList = new ArrayList<String>();
		String query = "Select mail FROM employe;";
		System.out.println(query);
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					mailList.add(rs.getString("mail"));
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
		return mailList;
	}
	
	public void modifyEmploye(Employe emp,String mail) {
		String function = emp.getTitle();
		if (emp.isLeader() && !emp.isRH()) {
			function = "TeamLeader";
		} else if (emp.isLeader() && emp.isRH()) {
			function = "RespoRH";
		} else if (!emp.isLeader() && emp.isRH()) {
			function = "EmployeRH";
		}
		String query = "UPDATE employe SET mail='"+ emp.getMail()+ "'"+",firstName=" + "'" + emp.getFirstName() + "'" + ", surname=" + "'"
				+ emp.getSurname() + "'" + ",birthDate=" + "'" + emp.getBirthDate() + "'" + ",address=" + "'"
				+ emp.getAddress() + "'" + ",nbDays=" + "'" + emp.getNbDays() + "'" + ",fonction=" + "'" + function
				+ "'" + ",team=" + "'" + emp.getNbTeam() + "'" + "WHERE mail =" + "'" + mail + "'" + ";";

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

	public void eraseEmploye(String mail) {

		String query = "DELETE FROM employe WHERE mail=" + "'" + mail + "';";
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
	
	public List<Employe> findFilteredEmploye(String poste ,String team,String mail){
		String requete = "select * from employe where ";
		if(!mail.equals("all")) {
			requete += "mail ='"+mail+"'";
			if(!poste.equals("all")||!team.equals("all"))requete += " and ";
		}
		if(!poste.equals("all"))requete += "fonction ='"+poste+"'";
		if(!team.equals("all") && mail.equals("all"))requete += " and mail in(select mail from employe where team='"+ team +"')";
		
		if(requete.equals("select * from employe where ")){
			return (ArrayList<Employe>) findByAll();
		}
		System.out.println(requete);
		return this.findBy(requete + "order by surname;");
	}
	
	public void updatePassword(String employe, String pwd) {
		String query = "UPDATE employe SET pwd = '" + pwd + "' where mail = '" + employe + "';";

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
	
	public void updateAddress(String employe, String addr) {
		String query = "UPDATE employe SET address = '" + addr + "' where mail = '" + employe + "';";

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
	
	public List<String> findAllTeamLeader(){
		ArrayList<String> employeList = new ArrayList<String>();
		String query = "select mail from employe where fonction=TeamLeader;";
		System.out.println(query);
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					employeList.add(rs.getString("mail"));
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
		return employeList;
	}
	
}
