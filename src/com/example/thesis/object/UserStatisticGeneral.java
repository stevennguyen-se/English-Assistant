package com.example.thesis.object;

public class UserStatisticGeneral extends UserStatistic {
	private String date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public UserStatisticGeneral() {
		// TODO Auto-generated constructor stub
		super();
		
		this.date = null;
	}
}
