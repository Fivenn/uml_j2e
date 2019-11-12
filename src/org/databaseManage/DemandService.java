package org.databaseManage;

import java.util.ArrayList;
import java.util.List;

import org.model.Demand;

public class DemandService {

	// choose the DAO data source : DB or Mock
	private DemandDAOImpl demandDao = new DemandDAOImpl();

	public List<Demand> getAllDemands() {
		List<Demand> listDemands = demandDao.findAll();
		return listDemands;
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
	
	public ArrayList<String> getStatus(){
		return (ArrayList<String>) demandDao.findAllStatus();
	}
}
