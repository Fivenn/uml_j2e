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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public int getNbDays() {
		return nbDays;
	}


	public void setNbDays(int nbDays) {
		this.nbDays = nbDays;
	}
	
}