package org.model;

public class Employe {
	private String mail;
	private String firstName;
	private String surname;
	private String birthDate;
	private String address;
	private boolean RH;
	private boolean leader;
	private int nbDays;
	private int nbTeam;
	
	
	public Employe(String mail, String firstName,String surname,String birthDate, String address, int nbDays, boolean RH, boolean leader,int nbTeam) {
		this.setMail(mail);
		this.setFirstName(firstName);
		this.setSurname(surname);
		this.setBirthDate(birthDate);
		this.setAddress(address);
		this.setNbDays(nbDays);
		this.setRH(RH);
		this.setLeader(leader);
		this.setNbTeam(nbTeam);
	}
	
	
	public int getNbTeam() {
		return nbTeam;
	}


	public void setNbTeam(int nbTeam) {
		this.nbTeam = nbTeam;
	}


	public String getBirthDate() {
		return birthDate;
	}



	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}



	public boolean isLeader() {
		return leader;
	}


	public void setLeader(boolean leader) {
		this.leader = leader;
	}


	public String getTitle() {
		return "Employe";
	}
	
	public String getFullName(){
		return this.getTitle() + " " + this.getFirstName()+ " " +this.getSurname();
	};
	
	public boolean isRH() {
		return RH;
	}
	
	public void setRH(boolean rH) {
		RH = rH;
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
