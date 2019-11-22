package org.databaseManage;

import java.util.ArrayList;
import java.util.List;

import org.model.Demand;

public class DemandService {

	// choose the DAO data source : DB or Mock
	private DemandDAOImpl demandDao = new DemandDAOImpl();
	private ReasonDAOImpl reasonDao = new ReasonDAOImpl();
	
	public List<Demand> getAllDemands() {
		List<Demand> listDemands = demandDao.findAll();
		return listDemands;
	}
	
	public boolean insertIntoDemand(String mail, String fromDate, String toDate, String reason, String duration) {
		return demandDao.insertIntoDemand(mail, fromDate, toDate, reason, duration);
	}
	
	public List<Demand> getAllDemandsButRH() {
		List<Demand> listDemands = demandDao.findAllButRH();
		return listDemands;
	}

	public List<Demand> getTeamDemands(String email) {
		return demandDao.findAllDemandeFromTeam(email);
	}

	public List<Demand> getEmployeDemand(String mail) {
		return demandDao.findAllDemandeFromEmploye(mail);
	}
	
	public List<Demand> getFilteredDemand(String status,String mail, String team, Boolean RH) {
		return demandDao.findFilteredDemand(status,mail, team, RH);
	}
	
	public ArrayList<String> getStatus(){
		return (ArrayList<String>) demandDao.findAllStatus();
	}
	
	public boolean changeDemandStatus(String string,String status) {
		return this.demandDao.setDemandStatus(string,status);
	}
	
	public boolean changeDemandStatus(String string,String status,String comment) {
		return this.demandDao.setDemandStatus(string,status,comment);
	}
	
	public boolean changeDemandReason(String id,String reason) {
		return this.demandDao.setDemandReason(id,reason);
	}

	public List<String> getAllReasons(){
		return this.reasonDao.findAllReasons();
	}
	
	public boolean changeDemand(String id, String dateFrom, String dateTo , String nbDays ,String reason) {
		return this.demandDao.changeDemand(id, dateFrom, dateTo, nbDays, reason);
	}

	public boolean deleteDemand(String idDemand) {
		return this.demandDao.deleteDemand(idDemand);
		
	}
}
