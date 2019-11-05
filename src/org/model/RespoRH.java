package org.model;

public class RespoRH  extends TeamLeader implements humanRessources{
	
	public RespoRH(String mail, String firstName, String surname, String address, int nbDays) {
		super(mail, firstName, surname, address, nbDays);
	}
	
	@Override
	public String getFullName() {
		return "Ressources humaines " + super.getFullName();
	}

}