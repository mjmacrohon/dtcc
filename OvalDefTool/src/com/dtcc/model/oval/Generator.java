package com.dtcc.model.oval;

import java.util.ArrayList;
import java.util.List;

import com.dtcc.model.OvalDefinition;
import com.dtcc.model.Product;

public class Generator {
	/******generator********/
	String product_name;
	String product_version;
	String schema_version;
	String timestamp;
	
	/******definition*********/
	OvalDefinition definition;
	String title;
	String family;
	List<Product> affected=new ArrayList<>();
	
	List<Criteria> criterias=new ArrayList<>();
	
	List<Test> tests=new ArrayList<Test>();
	
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_version() {
		return product_version;
	}
	public void setProduct_version(String product_version) {
		this.product_version = product_version;
	}
	public String getSchema_version() {
		return schema_version;
	}
	public void setSchema_version(String schema_version) {
		this.schema_version = schema_version;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public OvalDefinition getDefinition() {
		return definition;
	}
	public void setDefinition(OvalDefinition definition) {
		this.definition = definition;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public List<Product> getAffected() {
		return affected;
	}
	public void setAffected(List<Product> affected) {
		this.affected = affected;
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
	
	
}
