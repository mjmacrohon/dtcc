package com.dtcc.model;

import java.util.ArrayList;
import java.util.List;

public class Article {
	String product;
	String platform;
	String article;
	String downloadTitle;
	String downloadUrl;
	
	List<String> fileVersion=new ArrayList<>();
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getDownloadTitle() {
		return downloadTitle;
	}
	public void setDownloadTitle(String downloadTitle) {
		this.downloadTitle = downloadTitle;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public List<String> getFileVersion() {
		return fileVersion;
	}
	public void setFileVersion(List<String> fileVersion) {
		this.fileVersion = fileVersion;
	}
	
	
	
}
