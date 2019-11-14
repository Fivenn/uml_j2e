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
							listEmployes.add(new Employe(rs.getString("mail"),rs.getString("firstName"),rs.getString("surname"),rs.getString("birthDate"),rs.getString("address"),rs.getInt("nbDays"),true,false,rs.getInt("team")));												
							break;
						case "RespoRH":
							listEmployes.add(new Employe(rs.getString("mail"),rs.getString("firstName"),rs.getString("surname"),rs.getString("birthDate"),rs.getString("address"),rs.getInt("nbDays"),true,true,rs.getInt("team")));																			
							break;
						case "TeamLeader":
							listEmployes.add(new Employe(rs.getString("mail"),rs.getString("firstName"),rs.getString("surname"),rs.getString("birthDate"),rs.getString("address"),rs.getInt("nbDays"),false,true,rs.getInt("team")));						
							break;
						default:
							listEmployes.add(new Employe(rs.getString("mail"),rs.getString("firstName"),rs.getString("surname"),rs.getString("birthDate"),rs.getString("address"),rs.getInt("nbDays"),false,false,rs.getInt("team")));
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
		List<Employe> listEmployes = findBy("select * from employe where mail = '" + email + "'");
		return listEmployes != null?listEmployes.get(0):null;
	}
	
	public Employe checkCredentials(String email, String password) {
		List<Employe> listEmployes = findBy("select * from employe where mail = '" + email + "' and pwd = '"+ password+"'");
		return listEmployes.size()!=0?listEmployes.get(0):null;
	}
	
	public List<Employe> findByAll() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from employe");
	}
	
	public List<Employe> findByAllButRH() {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("select * from employe where fonction != 'EmployeRH' && fonction != 'RespoRH'");
	}

	public List<Employe> findMyTeam(String mail) {
		// avoid select * queries because of performance issues,
		// only query the columns you need
		return findBy("	select * from employe where team=(select noTeam from team where leader ='"+mail+"');");
	}
	
	//Ajout d'un employe
	public void addEmploye(Employe emp) {
		//Ajout du password par d�faut
		String query = "";
		String fonction = emp.getTitle();
		if(emp.isLeader() && !emp.isRH()) {
			fonction = "TeamLeader";
		}
		else if(emp.isLeader() && emp.isRH()) {
			fonction = "RespoRH";
		}
		else if(!emp.isLeader() && emp.isRH()) {
			fonction = "EmployeRH";
		}
		String pwd = emp.getFirstName().toLowerCase().charAt(0) +""+ emp.getSurname().toLowerCase().charAt(0) ;
		System.out.println("Le password par d�faut est:"+pwd);
		if(emp.getNbTeam()== 0) {
			query = "INSERT INTO employe (mail, firstName, surname, birthDate, address, nbDays, fonction, pwd) VALUES ("+"'"+emp.getMail()+"'"+","+"'"+ emp.getFirstName()+"'"+","+"'"+emp.getSurname()+"'"+","+"'"+emp.getBirthDate()+"'"+","+"'"+emp.getAddress()+"'"+","+"'"+emp.getNbDays()+"'"+","+"'"+fonction+"'"+","+"'"+pwd+"'"+");";
		}
		else {
			query = "INSERT INTO employe (mail, firstName, surname, birthDate, address, nbDays, fonction, pwd,team) VALUES ("+"'"+emp.getMail()+"'"+","+"'"+ emp.getFirstName()+"'"+","+"'"+emp.getSurname()+"'"+","+"'"+emp.getBirthDate()+"'"+","+"'"+emp.getAddress()+"'"+","+"'"+emp.getNbDays()+"'"+","+"'"+fonction+"'"+","+"'"+pwd+"'"+","+"'"+emp.getNbTeam()+"'"+");";
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
}
