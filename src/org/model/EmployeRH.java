package org.model;

import java.util.ArrayList;
import java.util.List;

public class EmployeRH  extends Employe{
	private List<Employe> team;
	
	public EmployeRH(String mail, String firstName, String surname, String address, int nbDays) {
		super(mail, firstName, surname, address, nbDays);
	}

}