package com.example.thesis.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable{
	private String id;
	private String word;
	private String pronunciation;
	private String type;
	private String definition;
	
	private boolean isSelected;
	
	private int numberOfGuessingWrong;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getPronunciation() {
		return pronunciation;
	}
	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public boolean isSelecetd() {
		return isSelected;
	}
	public void setSelected(boolean isFavorite) {
		this.isSelected = isFavorite;
	}
	public int getNumberOfGuessingWrong() {
		return numberOfGuessingWrong;
	}
	public void setNumberOfGuessingWrong(int numberOfGuessingWrong) {
		this.numberOfGuessingWrong = numberOfGuessingWrong;
	}
	// Constructor ==================================================================
	public Word() {
		super();
		// TODO Auto-generated constructor stub
		this.id = null;
		this.word = null;
		this.pronunciation = null;
		this.type = null;
		this.definition = null;
		this.isSelected = false;
		this.numberOfGuessingWrong = 0;
	}

	// Functions ====================================================================
	public boolean isEmpty() {
		if (this.word == null) {
			return true;
		}
		return false;
	}
	public void increaseNumberOfGuessingWrongWord(int lengthOfWord) {
		this.numberOfGuessingWrong += lengthOfWord;
	}
	public void decreaseNumberOfGuessingWrongWord() {
		if (this.numberOfGuessingWrong > 0) {
			--this.numberOfGuessingWrong;
		}
	}
	@Override
	public Word clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Word newWord = new Word();
		
		newWord.setId(this.id);
		newWord.setWord(this.word);
		newWord.setPronunciation(this.pronunciation);
		newWord.setType(this.type);
		newWord.setDefinition(this.definition);
		newWord.setSelected(this.isSelected);
		newWord.setNumberOfGuessingWrong(this.numberOfGuessingWrong);
		
		return newWord;
	}
	
	// Parcelable function ==========================================================
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
    * Storing the Student data to Parcel object
    **/
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(word);
		dest.writeString(pronunciation);
		dest.writeString(type);
		dest.writeString(definition);
		
		// if isSelected == true, byte == 1
		dest.writeByte((byte) (isSelected ? 1 : 0));
		
		dest.writeInt(numberOfGuessingWrong);
	}
	/**
    * Retrieving Student data from Parcel object
    * This constructor is invoked by the method createFromParcel(Parcel source) of
    * the object CREATOR
    **/
	private Word(Parcel in){
        this.id = in.readString();
        this.word = in.readString();
        this.pronunciation = in.readString();
        this.type = in.readString();
        this.definition = in.readString();
        
        // isSelected == true if byte != 0
        this.isSelected = in.readByte() != 0;
        
        this.numberOfGuessingWrong = in.readInt();
    }
	public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
		 
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }
 
        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
