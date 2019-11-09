package org.databaseManage;

import java.util.List;

import org.model.Team;

public class TeamService {
	
	// choose the DAO data source : DB or Mock
	private TeamDAOImpl teamDao = new TeamDAOImpl();  

	public List<Team> getAllTeams() {
		List<Team> listTeams = teamDao.findAllTeams();
		return listTeams;
	}
	
	public List<Team> getAllTeamsButRH() {
		List<Team> listTeams = teamDao.findAllTeamsButRH();
		return listTeams;
	}
	
	public List<Team> getLeaderTeams(String email) {
		return teamDao.findByLeader(email);
	}
	
	public Team getEmployeTeam(String mail){
		return teamDao.findEmployeTeam(mail);
	}
	
}
