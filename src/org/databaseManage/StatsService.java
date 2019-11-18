package org.databaseManage;

import java.util.ArrayList;
import java.util.List;

import org.model.Demand;

public class StatsService {

	// choose the DAO data source : DB or Mock
	private DemandDAOImpl demandDao = new DemandDAOImpl();
	private ReasonDAOImpl reasonDao = new ReasonDAOImpl();
	private TeamDAOImpl teamDao = new TeamDAOImpl();
	private EmployeDAOImpl employeDao = new EmployeDAOImpl();
	
	public ArrayList<List<String>> getDaysOffPerMonth() {
		return this.demandDao.getDaysOffPerMonth();
	}
	
	public ArrayList<List<String>>  getDaysOffPerTeam(){
		return this.demandDao.getDaysOffPerTeam();
	}
	
	public ArrayList<List<String>>  getDaysOffPerReasons(){
		return this.demandDao.getDaysOffPerReason();	}
	
	public ArrayList<List<String>> getDaysOffPerJob(){
		return this.demandDao.getDaysOffPerJob();	
	}
}
