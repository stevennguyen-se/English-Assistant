package com.example.thesis.object;

public class UserStatisticFavorite {
	private String Date;
	private int totalFavoriteWord;
	
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public int getTotalFavoriteWord() {
		return totalFavoriteWord;
	}
	public void setTotalFavoriteWord(int totalFavoriteWord) {
		this.totalFavoriteWord = totalFavoriteWord;
	}
	
	public UserStatisticFavorite() {
		// TODO Auto-generated constructor stub
		this.Date = null;
		this.totalFavoriteWord = 0;
	}
}
