package org.model;

public class Motif {
	private String name;
	private String description;
	
	public Motif(String name, String description) {
		this.setDescription(description);
		this.setName(name);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
