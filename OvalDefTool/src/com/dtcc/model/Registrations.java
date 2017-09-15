package com.dtcc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Registrations {
	
	
	private String id;
	@JsonProperty("salutation")
	private String salutatioin;
	@JsonProperty("title")
	private String title;
	@JsonProperty("first-name")
	private String firstName;
	@JsonProperty("last-name")
	private String lastName;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("mobile")
	private String mobile;
	@JsonProperty("email")
	private String email;
	@JsonProperty("professional-function")
	private String professionalFunction;
	@JsonProperty("bank-name")
	private String bankName;
	@JsonProperty("bank-iban")
	private String bankIban;
	@JsonProperty("is-tac-accepted")
	private boolean isTacAccepted;
	@JsonProperty("tac-link-url")
	private String tacLinkUrl;
	@JsonProperty("privacy-link-url")
	private String privacyLinkUrl;
	@JsonProperty("billing-name1")
	private String billingName1;
	@JsonProperty("billing-name2")
	private String billingName2;
	@JsonProperty("billing-address1")
	private String billingAddress1;
	@JsonProperty("billing-address2")
	private String billingAddress2;
	@JsonProperty("billing-zip-code")
	private String billingZipCode;
	@JsonProperty("billing-region")
	private String billingRegion;
	@JsonProperty("billing-city")
	private String billingCity;
	@JsonProperty("billing-country")
	private String billingCountry;
	@JsonProperty("billing-phone")
	private String billingPhone;
	@JsonProperty("delivery-name1")
	private String deliveryName1;
	@JsonProperty("delivery-name2")
	private String deliveryName2;
	@JsonProperty("delivery-address1")
	private String deliveryAddress1;
	@JsonProperty("delivery-address2")
	private String deliveryAddress2;
	@JsonProperty("delivery-zip-code")
	private String deliveryZipCode;
	@JsonProperty("delivery-region")
	private String deliveryRegion;
	@JsonProperty("delivery-city")
	private String deliveryCity;
	@JsonProperty("delivery-country")
	private String deliveryCountry;
	
	private String deliveryCountryCode;
	
	@JsonProperty("delivery-phone")
	private String deliveryPhone;
	
	public String getBillingName1() {
		return billingName1;
	}
	public void setBillingName1(String billingName1) {
		this.billingName1 = billingName1;
	}
	public String getBillingName2() {
		return billingName2;
	}
	public void setBillingName2(String billingName2) {
		this.billingName2 = billingName2;
	}
	public String getBillingAddress1() {
		return billingAddress1;
	}
	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = billingAddress1;
	}
	public String getBillingAddress2() {
		return billingAddress2;
	}
	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}
	public String getBillingZipCode() {
		return billingZipCode;
	}
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}
	public String getBillingRegion() {
		return billingRegion;
	}
	public void setBillingRegion(String billingRegion) {
		this.billingRegion = billingRegion;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingCountry() {
		return billingCountry;
	}
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	public String getBillingPhone() {
		return billingPhone;
	}
	public void setBillingPhone(String billingPhone) {
		this.billingPhone = billingPhone;
	}
	public String getDeliveryName1() {
		return deliveryName1;
	}
	public void setDeliveryName1(String deliveryName1) {
		this.deliveryName1 = deliveryName1;
	}
	public String getDeliveryName2() {
		return deliveryName2;
	}
	public void setDeliveryName2(String deliveryName2) {
		this.deliveryName2 = deliveryName2;
	}
	public String getDeliveryAddress1() {
		return deliveryAddress1;
	}
	public void setDeliveryAddress1(String deliveryAddress1) {
		this.deliveryAddress1 = deliveryAddress1;
	}
	public String getDeliveryAddress2() {
		return deliveryAddress2;
	}
	public void setDeliveryAddress2(String deliveryAddress2) {
		this.deliveryAddress2 = deliveryAddress2;
	}
	public String getDeliveryZipCode() {
		return deliveryZipCode;
	}
	public void setDeliveryZipCode(String deliveryZipCode) {
		this.deliveryZipCode = deliveryZipCode;
	}
	public String getDeliveryRegion() {
		return deliveryRegion;
	}
	public void setDeliveryRegion(String deliveryRegion) {
		this.deliveryRegion = deliveryRegion;
	}
	public String getDeliveryCity() {
		return deliveryCity;
	}
	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}
	public String getDeliveryCountry() {
		return deliveryCountry;
	}
	public void setDeliveryCountry(String deliveryCountry) {
		this.deliveryCountry = deliveryCountry;
	}
	public String getDeliveryCountryCode() {
		return deliveryCountryCode;
	}
	public void setDeliveryCountryCode(String deliveryCountryCode) {
		this.deliveryCountryCode = deliveryCountryCode;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	public String getSalutatioin() {
		return salutatioin;
	}
	public void setSalutatioin(String salutatioin) {
		this.salutatioin = salutatioin;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProfessionalFunction() {
		return professionalFunction;
	}
	public void setProfessionalFunction(String professionalFunction) {
		this.professionalFunction = professionalFunction;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankIban() {
		return bankIban;
	}
	public void setBankIban(String bankIban) {
		this.bankIban = bankIban;
	}
	public boolean isTacAccepted() {
		return isTacAccepted;
	}
	public void setTacAccepted(boolean isTacAccepted) {
		this.isTacAccepted = isTacAccepted;
	}
	public String getTacLinkUrl() {
		return tacLinkUrl;
	}
	public void setTacLinkUrl(String tacLinkUrl) {
		this.tacLinkUrl = tacLinkUrl;
	}
	public String getPrivacyLinkUrl() {
		return privacyLinkUrl;
	}
	public void setPrivacyLinkUrl(String privacyLinkUrl) {
		this.privacyLinkUrl = privacyLinkUrl;
	}
}
