package org.model;

import java.util.List;

public class TeamLeader extends Employe{
	private List<Employe> team;
	
	public TeamLeader(String mail, String firstName, String surname, String address, int nbDays) {
		super(mail, firstName, surname, address, nbDays);
	}
	
	public void initTeam(List<Employe> team) {
		this.team = team;
	}
	public List<Employe> getTeam() {
		return team;
	}
	@Override
	public String getFullName() {
		return this.getTitle() + " " + super.getFullName();
	}
	
	public String getTitle() {
		return "Leader";
	}
}
