package com.example.thesis.database.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.thesis.database.helper.GameHelper;
import com.example.thesis.object.GameInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class GameDatabaseAdapter {
	private static GameHelper mHelper;
	
	private static SQLiteDatabase db;
	
	private static Cursor cursor;
	
	private static ContentValues contentValues;
	
	public GameDatabaseAdapter(Context context) {
		mHelper = new GameHelper(context);
		
		try {

			mHelper.createDataBase();

		} catch (IOException e) {

			throw new Error("Unable to create database");

		}

		try {

			mHelper.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}
		mHelper.close();
		
		db = null;
		
		cursor = null;
		
		contentValues = null;
	}
	
	public long insertData(String name, int score) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		contentValues = new ContentValues();
		contentValues.put(GameHelper.NAME, name);
		contentValues.put(GameHelper.SCORE, score);
		
		long id = db.insert(GameHelper.TABLE_NAME, null, contentValues);
		
		if (contentValues != null) {
			contentValues.clear();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return id;
	}
	
	public List<GameInfo> getAllData() {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {GameHelper.ID, 
							GameHelper.NAME, 
							GameHelper.SCORE};
		
		// DESC and ASC
		cursor = db.query(	GameHelper.TABLE_NAME, columns, 
									null, null, null, null, null);
		
		List<GameInfo> gameInfo = new ArrayList<GameInfo>();
		
		while (cursor.moveToNext()) {
			
			GameInfo singleGameInfo = new GameInfo();
			
			int mIdIndex = cursor.getColumnIndex(GameHelper.ID);
			int mNameIndex = cursor.getColumnIndex(GameHelper.NAME);
			int mScoreIndex = cursor.getColumnIndex(GameHelper.SCORE);
			
			int mId = cursor.getInt(mIdIndex);
			String mName = cursor.getString(mNameIndex);
			int mScore = cursor.getInt(mScoreIndex);
			
			singleGameInfo.setID(mId);
			singleGameInfo.setName(mName);
			singleGameInfo.setScore(mScore);
			
			gameInfo.add(singleGameInfo);
		}
		
		if (cursor != null) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return gameInfo;
	}
	
	public List<String> getAllGameNames() {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {GameHelper.NAME};
		
		// DESC and ASC
		cursor = db.query(	GameHelper.TABLE_NAME, columns, 
									null, null, null, null, null);
		
		List<String> gameNames = new ArrayList<String>();
		
		while (cursor.moveToNext()) {
			
			String singleGameName = new String();
			
			int mNameIndex = cursor.getColumnIndex(GameHelper.NAME);
			
			String mName = cursor.getString(mNameIndex);
			
			singleGameName = mName;
			
			gameNames.add(singleGameName);
		}
		
		if (cursor != null) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return gameNames;
	}
	
	public int getGameScoreByName(String name) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {GameHelper.NAME, GameHelper.SCORE};
		
		// DESC and ASC
		cursor = db.query(	GameHelper.TABLE_NAME, columns, 
									GameHelper.NAME + " = '"+name+"'", null, null, null, null);
		
		int gameScore = 0;
		
		if (cursor.moveToNext()) {
			
			int mScoreIndex = cursor.getColumnIndex(GameHelper.SCORE);
			
			int mScore = cursor.getInt(mScoreIndex);
			
			gameScore = mScore;
			
		}
		
		if (cursor != null) {
			cursor.close();
		}
		if (db.isOpen()) {
			db .close();
		}
		mHelper.close();
		return gameScore;
	}
	
	public int upgradeDataByName(String name, int score) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		contentValues = new ContentValues();
		contentValues.put(GameHelper.NAME, name);
		contentValues.put(GameHelper.SCORE, score);
		
		String[] whereArgs = {name};
		
		int count = db.update(GameHelper.TABLE_NAME, contentValues,	GameHelper.NAME + " = ?", whereArgs);
		
		if (contentValues != null) {
			contentValues.clear();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	public int upgradeDataRow(int ID, String name, int score) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		contentValues = new ContentValues();
		contentValues.put(GameHelper.ID, ID);
		contentValues.put(GameHelper.NAME, name);
		contentValues.put(GameHelper.SCORE, score);
		
		String[] whereArgs = {String.valueOf(ID)};
		
		int count = db.update(GameHelper.TABLE_NAME, contentValues,	GameHelper.ID + " = ?", whereArgs);
		
		if (contentValues != null) {
			contentValues.clear();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	public boolean upgradeData(List<GameInfo> gameInfor) {
		
		for (int i = 0; i < gameInfor.size(); ++i) {
			if (upgradeDataRow(gameInfor.get(i).getID(), gameInfor.get(i).getName(), gameInfor.get(i).getScore()) < 0)
			{
				return false;
			}
		}
		return true;
	}
}
