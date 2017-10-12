package com.dtcc.model;

public class Product {
	String name;
	String family;
	String type; //platform, product
	String description;
	String platform;
	
	
	public Product() {
		super();
	}
	
	public Product(String name, String family, String type, String description) {
		super();
		this.name = name;
		this.family = family;
		this.type = type;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	
	
}
