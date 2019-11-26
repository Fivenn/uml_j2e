package org.model;

//Classe gérant les demandes, faisant le lien avec la base de données
public class Demand {
	private int id;
	private Employe employe;
	private String status;
	private String startDate;
	private String endDate;
	private String requestDate;
	private String motif;
	private int nbDays;
	private String commentary;
	
	//Deux controleurs afin de pouvoir créer la demande avec les paramètres dont on a besoin
	public Demand(int id,String status, String startDate,String endDate,String requestDate, String motif, int nbDays, String commentary) {
		this.setStatus(status);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setRequestDate(requestDate);
		this.setMotif(motif);
		this.setNbDays(nbDays);
		this.setEmploye(employe);
		this.setCommentary(commentary);
	}
	
	
	public Demand(int id, Employe employe,String status, String startDate,String endDate,String requestDate, String motif, int nbDays, String commentary) {
		this.setStatus(status);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setRequestDate(requestDate);
		this.setMotif(motif);
		this.setNbDays(nbDays);
		this.setEmploye(employe);
		this.setId(id);
		this.setCommentary(commentary);
	}
	
	//Getter et setters pour les différents paramètres.
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
	
	public String getCommentary() {
		return commentary;
	}
	
	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
	
}