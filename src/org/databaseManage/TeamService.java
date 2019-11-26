package org.databaseManage;

import java.util.List;

import org.model.Team;

public class TeamService {
	
	// choose the DAO data source : DB or Mock
	private TeamDAOImpl teamDao = new TeamDAOImpl();  

	//Fonction permettant de trouver les teams
	public List<Team> getAllTeams() {
		List<Team> listTeams = teamDao.findAllTeams();
		return listTeams;
	}
	
	//Fonction permettant de trouver les teams sauf la rh
	public List<Team> getAllTeamsButRH() {
		List<Team> listTeams = teamDao.findAllTeamsButRH();
		return listTeams;
	}
	
	//Fonction permettant d'ajouter une team
	public void ajoutTeam(String name, String description, String leader) {
		teamDao.addTeam(name,description,leader);
	}
	
	//Fonction permettant de trouver les leaders de teams
	public List<Team> getLeaderTeams(String email) {
		return teamDao.findByLeader(email);
	}
	
	//Fonction permettant de trouver la team d'un employé
	public Team getEmployeTeam(String mail){
		return teamDao.findEmployeTeam(mail);
	}
	
	//Fonction permettant de trouver les noms des teams
	public List<String> getAllNameTeam(){
		return teamDao.findAllNameTeam();
	}
	
}
