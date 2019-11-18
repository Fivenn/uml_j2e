package org.databaseManage;

import java.util.ArrayList;
import java.util.List;

import org.model.Demand;

public class StatsService {

	// choose the DAO data source : DB or Mock
	private DemandDAOImpl demandDao = new DemandDAOImpl();
	private ReasonDAOImpl reasonDao = new ReasonDAOImpl();
	
	public List<Demand> getAllDemands() {
		List<Demand> listDemands = demandDao.findAll();
		return listDemands;
	}
	
//	public List<String> getAllReasons(){
//		return this.reasonDao.findAllReasons();
//	}
//	
//	public List<String> getAllReasons(){
//		return this.reasonDao.findAllReasons();
//	}
}
