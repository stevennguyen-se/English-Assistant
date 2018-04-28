package com.example.thesis.object;

public class GameInfo {
	private int ID;
	private String name;
	private int score;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public GameInfo() {
		// TODO Auto-generated constructor stub
		this.ID = 0;
		this.name = null;
		this.score = 0;
	}
}
