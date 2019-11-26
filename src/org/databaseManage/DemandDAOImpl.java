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
					Employe emp = employeService.getEmploye(rs.getString("employe")); //Construction d'un employe a ajouter dans la demande
					//Création d'une demande et ajout de celle-ci dans la liste
					listDemandes.add(new Demand(rs.getInt("id"),emp,rs.getString("status"),rs.getString("beginDate"),rs.getString("endDate"),rs.getString("demandDate"),rs.getString("reason"),rs.getInt("duration"),rs.getString("comment")));
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
	//Fonction permettant de changer le statut d'une demande
	public boolean setDemandStatus(String string, String status) {
		return updateDemand("update demand set status='"+status+"' where id='"+string+"';");
	}
	
	//Fonction permettant de changer le statut d'une demande et d'ajouter un commentaire
	public boolean setDemandStatus(String string, String status,String comment) {
		return updateDemand("update demand set status='"+status+"', comment='"+comment+"' where id='"+string+"';");
	}
	
	//Fonction permettant de changer le motif d'une demande
	public boolean setDemandReason(String id, String reason) {
		return updateDemand("update demand set reason='"+reason+"' where id='"+id+"';");
	}
	
	//Fonction permettant de changer entièrement une demande
	public boolean changeDemand(String id, String dateFrom, String dateTo, String nbDays ,String reason) {
		return updateDemand("update demand set reason='"+reason+"', beginDate='"+dateFrom+"', endDate='"+dateTo+"', duration='"+nbDays+"' where id='"+id+"';");
	}
	//Fonction permettant de supprimer une demande
	public boolean deleteDemand(String idDemand) {
		return updateDemand("delete from demand where id='"+idDemand+"';");
	}
	//Fonction permettant d'acceder à la base de données et de mettre à jour une demande
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
	
	//Fonction permettant de retourner les stats.
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
	
	//Fonction permettant d'ajouter une demande
	public boolean insertIntoDemand(String mail, String fromDate, String toDate, String reason, String duration) {
		return updateDemand("Insert into demand(employe, beginDate, endDate, reason, duration) values ('"+mail+"','"+fromDate+"','"+toDate+"','"+reason+"','"+duration+"');");
	}
	
	//Fonction permettant de trouver les demandes
	public List<Demand> findAll() {
		return findBy("select * from demand");
	}
	
	//Fonction permettant de trouver toutes les demandes sauf les RH
	public List<Demand> findAllButRH() {
		return findBy("select * from demand where employe NOT IN (select mail from employe where fonction = 'RespoRH' or fonction = 'EmployeRH');");
	}
	
	//Fonction permettant de trouver toutes les demandes et leur statut
	public List<String> findAllStatus(){
		List<String> status = new ArrayList<String>();		
		return new ArrayList<String>(Arrays.asList("approved","refused","pending"));				
	}
	
	//Fonction permettant de trouver toutes les demandes sauf les RH
	public List<Demand> findAllDemandeFromEmploye(String mail) {
		return findBy("select * from demand where employe='"+mail+"';");
	}

	//Fonction permettant de trouver toutes les demandes d'une team
	public List<Demand> findAllDemandeFromTeam(String mail) {

		return findBy("select * from demand where employe in(select mail from employe where team=(select noTeam from team where leader='"+ mail +"'));" );
	}
	
	
	//Fonction permettant de trouver toutes les demandes d'une team avec leur statut
	public List<Demand> findStatutXDemandeFromTeam(String mail,String status) {
		return findBy("select * from demand where status ='"+status+"'  and employe in(select mail from employe where team=(select noTeam from team where leader='"+ mail +"'));" );
	}
	
	//Fonction permettant de trouver toutes les demandes selon les paramètres suivant
	public List<Demand> findFilteredDemand(String status,String mail, String team, Boolean RH) {
		String requete = "select * from demand where ";
		if(!status.equals("all")) { // Si le statut a pas changé
			requete += "status ='"+status+"'";
			if(!mail.equals("all")||!team.equals("all"))requete += " and "; //Si il y a un autre argument, on ajoute le and
		}
		if(!mail.equals("all"))requete += "employe ='"+mail+"'"; //On ajoute l'employé dans la requète si il est filtré
		//Sinon on ajoute la team
		if(!team.equals("all") && mail.equals("all"))requete += "employe in(select mail from employe where team='"+ team +"')";
		
		//On trie en fonction du rh ou non respo rh
		if(requete.equals("select * from demand where ")){
			return RH?findAll():findAllButRH();
		}
		System.out.println(requete);
		return this.findBy(requete + "order by status;"); //On lance la requete
	}
	
	//Fonction permettant de trouver les stats de team

	public ArrayList<List<String>> getDaysOffPerTeam(){
		return this.findNbDemandPerX("SELECT team.name AS team, SUM(demand.duration) AS nbDays FROM (demand JOIN employe ON demand.employe = employe.mail) LEFT JOIN team ON employe.team WHERE demand.status = 'approved' GROUP BY employe.team;","nbDays","team");
	}
	
	//Fonction permettant de trouver les stats des mois

	public ArrayList<List<String>> getDaysOffPerMonth(){
		return this.findNbDemandPerX("SELECT MONTH(beginDate) AS month, SUM(duration) AS nbDays  FROM demand WHERE demand.status = 'approved' GROUP BY MONTH(beginDate);","nbDays","month");
	}
	
	//Fonction permettant de trouver les stats des motifs
	public ArrayList<List<String>> getDaysOffPerReason(){
		return this.findNbDemandPerX("SELECT reason, SUM(duration) AS nbDays  FROM demand WHERE demand.status = 'approved' GROUP BY reason;","nbDays","reason");
	}
	
	//Fonction permettant de trouver les stats des emplois
	public ArrayList<List<String>> getDaysOffPerJob(){
		return this.findNbDemandPerX("SELECT fonction, SUM(duration) AS nbDays FROM demand JOIN employe ON demand.employe = employe.mail WHERE demand.status = 'approved' GROUP BY fonction;","nbDays","fonction");
	}

	//Fonction permettant de vérifier si l'utilisateur a suffisament de jours
	public boolean hasEnoughDays(String idDemand) {
		return this.getBoolean("SELECT surname FROM employe JOIN demand ON employe.mail = demand.employe AND demand.id='"+ idDemand +"' AND employe.nbDays>=demand.duration;");
	}

	//Fonction qui retourne un boolean si le result set n'est pas vide
	private boolean getBoolean(String query) {
		Connection conn = null;
		List<Demand> listDemandes = new ArrayList<Demand>();
		
		boolean bool = false;
		
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stat = conn.createStatement();
				rs = stat.executeQuery(query);
				while (rs.next()) {
					bool = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stat, rs);
			}
		}
				
		return bool;
	}
}
