package org.databaseManage;

import java.util.ArrayList;
import java.util.List;

import org.model.Demand;
////Classe permettant de récupérer les tableaux des stats
public class StatsService {

	// choose the DAO data source : DB or Mock
	private DemandDAOImpl demandDao = new DemandDAOImpl();
	private ReasonDAOImpl reasonDao = new ReasonDAOImpl();
	private TeamDAOImpl teamDao = new TeamDAOImpl();
	private EmployeDAOImpl employeDao = new EmployeDAOImpl();
	
	//Fonction permettant de récupérer le nombre de jours posés par mois
	public ArrayList<List<String>> getDaysOffPerMonth() {
		return this.demandDao.getDaysOffPerMonth();
	}
	//Fonction permettant de récupérer le nombre de jours posés par équipe
	public ArrayList<List<String>>  getDaysOffPerTeam(){
		return this.demandDao.getDaysOffPerTeam();
	}
	//Fonction permettant de récupérer le nombre de jours posés par raisons
	public ArrayList<List<String>>  getDaysOffPerReasons(){
		return this.demandDao.getDaysOffPerReason();	}
	//Fonction permettant de récupérer le nombre de jours posés par métier
	public ArrayList<List<String>> getDaysOffPerJob(){
		return this.demandDao.getDaysOffPerJob();	
	}
}
