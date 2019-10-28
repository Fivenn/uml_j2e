package org.model;

public class Employe {
	private String mail;
	private String firstName;
	private String surname;
	private String address;
	private int nbDays;
	
	
	public Employe(String mail, String firstName,String surname,String address, int nbDays) {
		this.setMail(mail);
		this.setFirstName(firstName);
		this.setSurname(surname);
		this.setAddress(address);
		this.setNbDays(nbDays);
	}


	private String getAddress() {
		return address;
	}


	private void setAddress(String address) {
		this.address = address;
	}


	private String getMail() {
		return mail;
	}


	private void setMail(String mail) {
		this.mail = mail;
	}


	private String getFirstName() {
		return firstName;
	}


	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	private String getSurname() {
		return surname;
	}


	private void setSurname(String surname) {
		this.surname = surname;
	}


	private int getNbDays() {
		return nbDays;
	}


	private void setNbDays(int nbDays) {
		this.nbDays = nbDays;
	}
	
}
