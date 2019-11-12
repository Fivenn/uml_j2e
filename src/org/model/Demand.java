package org.model;

public class Demand {
	private int id;
	private Employe employe;
	private String status;
	private String startDate;
	private String endDate;
	private String requestDate;
	private String motif;
	private int nbDays;

	public Demand(int id,String status, String startDate,String endDate,String requestDate, String motif, int nbDays) {
		this.setStatus(status);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setRequestDate(requestDate);
		this.setMotif(motif);
		this.setNbDays(nbDays);
		this.setEmploye(employe);
	}
	
	
	public Demand(int id, Employe employe,String status, String startDate,String endDate,String requestDate, String motif, int nbDays) {
		this.setStatus(status);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setRequestDate(requestDate);
		this.setMotif(motif);
		this.setNbDays(nbDays);
		this.setEmploye(employe);
		this.setId(id);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Employe getEmploye() {
		return employe;
	}
	
	public void setEmploye(Employe employe) {
		this.employe = employe;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getRequestDate() {
		return requestDate;
	}
	
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	
	public String getMotif() {
		return motif;
	}
	
	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	public int getNbDays() {
		return nbDays;
	}
	
	public void setNbDays(int nbDays) {
		this.nbDays = nbDays;
	}
	
	
}