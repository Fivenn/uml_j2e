package org.databaseManage;

import java.util.ArrayList;
import java.util.List;

import org.model.Demand;

public class DemandService {

	// choose the DAO data source : DB or Mock
	private DemandDAOImpl demandDao = new DemandDAOImpl();
	private ReasonDAOImpl reasonDao = new ReasonDAOImpl();
	
	//Fonction retournant la liste des demandes
	public List<Demand> getAllDemands() {
		List<Demand> listDemands = demandDao.findAll();
		return listDemands;
	}
	
	//Fonction permettant d'insérer une demande
	public boolean insertIntoDemand(String mail, String fromDate, String toDate, String reason, String duration) {
		return demandDao.insertIntoDemand(mail, fromDate, toDate, reason, duration);
	}
	
	//Fonction retournant la liste des demandes sauf celles des RH
	public List<Demand> getAllDemandsButRH() {
		List<Demand> listDemands = demandDao.findAllButRH();
		return listDemands;
	}

	//Fonction retournant la liste des demandes par team selon le leader
	public List<Demand> getTeamDemands(String email) {
		return demandDao.findAllDemandeFromTeam(email);
	}

	//Fonction retournant la liste des demandes d'un employe en fonction du mail
	public List<Demand> getEmployeDemand(String mail) {
		return demandDao.findAllDemandeFromEmploye(mail);
	}
	
	//Fonction retournant la liste des demandes filtrées
	public List<Demand> getFilteredDemand(String status,String mail, String team, Boolean RH) {
		return demandDao.findFilteredDemand(status,mail, team, RH);
	}
	
	//Fonction retournant la liste des status possibles
	public ArrayList<String> getStatus(){
		return (ArrayList<String>) demandDao.findAllStatus();
	}
	
	//Fonction permettant de mettre à jour le statut d'une demande
	public boolean changeDemandStatus(String string,String status) {
		return this.demandDao.setDemandStatus(string,status);
	}
	
	//Fonction permettant de mettre à jour le statut d'une demande et son commentaire
	public boolean changeDemandStatus(String string,String status,String comment) {
		return this.demandDao.setDemandStatus(string,status,comment);
	}
	
	//Fonction permettant de mettre à jour le motif d'une demande
	public boolean changeDemandReason(String id,String reason) {
		return this.demandDao.setDemandReason(id,reason);
	}

	//Fonction permettant de récupérer toutes les raisons
	public List<String> getAllReasons(){
		return this.reasonDao.findAllReasons();
	}
	
	//Fonction permettant de mettre à jour une demande
	public boolean changeDemand(String id, String dateFrom, String dateTo , String nbDays ,String reason) {
		return this.demandDao.changeDemand(id, dateFrom, dateTo, nbDays, reason);
	}

	//Fonction permettant de supprimer une demande
	public boolean deleteDemand(String idDemand) {
		return this.demandDao.deleteDemand(idDemand);
		
	}

	//Fonction permettant de vérifier le nombre de jour d'une demande
	public boolean hasEnoughDays(String idDemand) {
		// TODO Auto-generated method stub
		return this.demandDao.hasEnoughDays(idDemand);
	}
}
