package com.tiaa.test.branch_cash_tally.json;

public class Branch {

	private String location;
	private float totalcollection;
	private String locationid;
	private float sumoforder;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getTotalcollection() {
		return totalcollection;
	}

	public void setTotalcollection(float totalcollection) {
		this.totalcollection = totalcollection;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public float getSumoforder() {
		return sumoforder;
	}

	public void setSumoforder(float sumoforder) {
		this.sumoforder = sumoforder;
	}

}
