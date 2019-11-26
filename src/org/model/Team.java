package org.model;

/**
 * Modèle de données permettant de définir
 * une équipe dans le système de gestion
 * de congés
 */
public class Team {
	/**
	 * ID unique d'une équipe.
	 */
	int noTeam;
	/**
	 * Nom d'une équipe.
	 */
	String name;
	/**
	 * Chef d'une équipe.
	 */
	String leader;
	/**
	 * Description d'une équipe.
	 */
	String description;
	
	/**
	 * Constructeur de la classe Team.
	 * 
	 * @param noTeam ID unique d'une équipe.
	 * @param name Nom d'une équipe.
	 * @param leader Chef d'équipe.
	 * @param description Description d'une équipe.
	 */
	public Team(int noTeam, String name, String leader, String description) {
		this.setDescription(description);
		this.setLeader(leader);
		this.setName(name);
		this.setNoTeam(noTeam);
	}
	/**
	 * @return La description d'une équipe.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Permet d'ajouter/ modifier une description d'une équipe.
	 * @param description Nouvelle description d'une équipe.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Le nom du chef d'une équipe.
	 */
	public String getLeader() {
		return leader;
	}
	/**
	 * 
	 * @return Le nom d'une équipe.
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return L'identifiant unique d'une équipe.
	 */
	public int getNoTeam() {
		return noTeam;
	}
	/**
	 * Permet de modifier le chef d'équipe d'une équipe.
	 * @param leader Le nom du nouveau chef d'équipe.
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}
	/**
	 * Permet de modifier le nom d'une équipe
	 * @param name Le nouveau nom d'une équipe.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Permet de changer l'identifiant unique d'une équipe.
	 * @param noTeam Nouvel identifiant d'une équipe.
	 */
	public void setNoTeam(int noTeam) {
		this.noTeam = noTeam;
	}
	
}
