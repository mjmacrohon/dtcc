package com.dtcc.model;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.model.oval.Criteria;
import com.dtcc.model.oval.State;
import com.dtcc.model.oval.Test;

public class OvaDef {
	String productName;
	String productVersion;
	String schemaVersion;
	String timestamp;
	
	String refId;
	String refUrl;
	String source;
	String ovalClass;
	String ovalId;
	String ovalVersion;
	
	String title;
	String description;
	String submitted;
	String family;
	String platform;
	String product;
	
	List<Criteria> criterias=new ArrayList<>();
	
	List<Test> tests=new ArrayList<Test>();
	
	List<State> states=new ArrayList<State>();
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	public String getSchemaVersion() {
		return schemaVersion;
	}
	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRefUrl() {
		return refUrl;
	}
	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getOvalClass() {
		return ovalClass;
	}
	public void setOvalClass(String ovalClass) {
		this.ovalClass = ovalClass;
	}
	public String getOvalId() {
		return ovalId;
	}
	public void setOvalId(String ovalId) {
		this.ovalId = ovalId;
	}
	public String getOvalVersion() {
		return ovalVersion;
	}
	public void setOvalVersion(String ovalVersion) {
		this.ovalVersion = ovalVersion;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubmitted() {
		return submitted;
	}
	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public List<Criteria> getCriterias() {
		return criterias;
	}
	public void setCriterias(List<Criteria> criterias) {
		this.criterias = criterias;
	}
	public List<Test> getTests() {
		return tests;
	}
	public void setTests(List<Test> tests) {
		this.tests = tests;
	}
	public List<State> getStates() {
		return states;
	}
	public void setStates(List<State> states) {
		this.states = states;
	}
	
	
	
	
}
