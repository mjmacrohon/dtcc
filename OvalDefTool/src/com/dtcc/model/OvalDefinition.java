package com.dtcc.model;

import com.dtcc.model.oval.Reference;

public class OvalDefinition {
	String definitionId;
	String definitionClass;
	String title;
	String lastModified;
	String definitionUrl;
	String version;
	String description;
	Reference reference;
	
	public String getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	public String getDefinitionUrl() {
		return definitionUrl;
	}
	public void setDefinitionUrl(String definitionUrl) {
		this.definitionUrl = definitionUrl;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Reference getReference() {
		return reference;
	}
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	public String getDefinitionClass() {
		return definitionClass;
	}
	public void setDefinitionClass(String definitionClass) {
		this.definitionClass = definitionClass;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	
	
	
	
}
