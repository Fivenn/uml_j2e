package org.model;

import java.util.ArrayList;
import java.util.List;

public class TeamLeader extends Employe{
	private List<Employe> team;
	
	public TeamLeader(String mail, String firstName, String surname, String address, int nbDays) {
		super(mail, firstName, surname, address, nbDays);
	}
	
	public void initTeam() {
		this.team = new ArrayList<Employe>();
	}
}
