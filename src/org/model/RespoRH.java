package org.model;

import java.util.ArrayList;
import java.util.List;

public class RespoRH  extends TeamLeader implements humanRessources{
	private List<Employe> team;
	
	public RespoRH(String mail, String firstName, String surname, String address, int nbDays) {
		super(mail, firstName, surname, address, nbDays);
	}
	
	@Override
	public String getFullName() {
		return "Ressources humaines " + super.getFullName();
	}
}