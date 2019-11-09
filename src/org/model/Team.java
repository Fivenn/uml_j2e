package org.model;

public class Team {
	int noTeam;
	String name;
	String leader;
	String description;
	
	public Team(int noTeam, String name, String leader, String description) {
		// TODO Auto-generated constructor stub
		this.setDescription(description);
		this.setLeader(leader);
		this.setName(name);
		this.setNoTeam(noTeam);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLeader() {
		return leader;
	}
	public String getName() {
		return name;
	}
	public int getNoTeam() {
		return noTeam;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNoTeam(int noTeam) {
		this.noTeam = noTeam;
	}
	
}
