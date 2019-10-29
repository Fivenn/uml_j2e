package org.model;

public class Demande {
	private String status;
	private String startDate;
	private String endDate;
	private String requestDate;
	private String motif;
	private int nbDays;
	
	
	
	public Demande(String status, String startDate,String endDate,String requestDate, String motif, int nbDays) {
		this.setStatus(status);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setRequestDate(requestDate);
		this.setMotif(motif);
		this.setNbDays(nbDays);
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