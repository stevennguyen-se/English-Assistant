package com.example.thesis.object;

public class WordWrong {
	private int ID;
	private String word;
	private int numberOfGuessingWrong;
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getNumberOfGuessingWrong() {
		return numberOfGuessingWrong;
	}
	public void setNumberOfGuessingWrong(int numberOfGuessingWrong) {
		this.numberOfGuessingWrong = numberOfGuessingWrong;
	}
	
	// Constructor
	public WordWrong() {
		this.ID = 0;
		this.word = null;
		this.numberOfGuessingWrong = 0;
	}
	
}
