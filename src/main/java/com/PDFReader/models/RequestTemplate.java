package com.PDFReader.models;

public class RequestTemplate {

	private String billingAccountNumber;
	private double billAmount;
	private String query;
	public String getBillingAccountNumber() {
		return billingAccountNumber;
	}
	public void setBillingAccountNumber(String billingAccountNumber) {
		this.billingAccountNumber = billingAccountNumber;
	}
	public double getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	@Override
	public String toString() {
		return "Template [billingAccountNumber=" + billingAccountNumber + ", billAmount=" + billAmount + ", query="
				+ query + "]";
	}
	
	
	
	
}
