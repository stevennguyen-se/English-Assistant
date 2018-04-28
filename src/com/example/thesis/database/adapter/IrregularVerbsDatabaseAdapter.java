package com.example.thesis.database.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.thesis.database.helper.IrregularVerbsHelper;
import com.example.thesis.object.WordIrregular;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class IrregularVerbsDatabaseAdapter {
	private static IrregularVerbsHelper mHelper;
	
	private static SQLiteDatabase db;
	
	private static Cursor cursor;
	
	private static ContentValues contentValues;
	
	public IrregularVerbsDatabaseAdapter(Context context) {
		mHelper = new IrregularVerbsHelper(context);
		
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
	
	public long insertData(String word_v1, String word_v2, String word_v3) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		contentValues = new ContentValues();
		contentValues.put(IrregularVerbsHelper.WORDV1, word_v1);
		contentValues.put(IrregularVerbsHelper.WORDV2, word_v2);
		contentValues.put(IrregularVerbsHelper.WORDV3, word_v3);
		
		long id = db.insert(IrregularVerbsHelper.TABLE_NAME, null, contentValues);
		
		if (contentValues != null) {
			contentValues.clear();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return id;
	}
	
	public List<WordIrregular> getAllData() {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {IrregularVerbsHelper.ID, 
							IrregularVerbsHelper.WORDV1, 
							IrregularVerbsHelper.WORDV2, 
							IrregularVerbsHelper.WORDV3};
		
		// DESC and ASC
		cursor = db.query(	IrregularVerbsHelper.TABLE_NAME, columns, 
									null, null, null, null, null);
		
		List<WordIrregular> words = new ArrayList<WordIrregular>();
		
		while (cursor.moveToNext()) {
			
			WordIrregular singleWord = new WordIrregular();
			
			int mIdIndex = cursor.getColumnIndex(IrregularVerbsHelper.ID);
			int mWordV1Index = cursor.getColumnIndex(IrregularVerbsHelper.WORDV1);
			int mWordV2Index = cursor.getColumnIndex(IrregularVerbsHelper.WORDV2);
			int mWordV3Index = cursor.getColumnIndex(IrregularVerbsHelper.WORDV3);
			
			String mId = cursor.getString(mIdIndex);
			String mWordV1 = cursor.getString(mWordV1Index);
			String mWordV2 = cursor.getString(mWordV2Index);
			String mWordV3 = cursor.getString(mWordV3Index);
			
			singleWord.setID(mId);
			singleWord.setWordV1(mWordV1);
			singleWord.setWordV2(mWordV2);
			singleWord.setWordV3(mWordV3);
			
			words.add(singleWord);
		}
		
		if (cursor != null) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return words;
	}
	
	public List<WordIrregular> getData(String word) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {IrregularVerbsHelper.ID, 
							IrregularVerbsHelper.WORDV1, 
							IrregularVerbsHelper.WORDV2, 
							IrregularVerbsHelper.WORDV3};
		
		// DESC and ASC
		cursor = db.query(false, IrregularVerbsHelper.TABLE_NAME, columns, 
				IrregularVerbsHelper.WORDV1+" LIKE '"+word+"%'", null, null, null, null, null);
		
		List<WordIrregular> words = new ArrayList<WordIrregular>();
		
		while (cursor.moveToNext()) {
			
			WordIrregular singleWord = new WordIrregular();
			
			int mIdIndex = cursor.getColumnIndex(IrregularVerbsHelper.ID);
			int mWordV1Index = cursor.getColumnIndex(IrregularVerbsHelper.WORDV1);
			int mWordV2Index = cursor.getColumnIndex(IrregularVerbsHelper.WORDV2);
			int mWordV3Index = cursor.getColumnIndex(IrregularVerbsHelper.WORDV3);
			
			String mId = cursor.getString(mIdIndex);
			String mWordV1 = cursor.getString(mWordV1Index);
			String mWordV2 = cursor.getString(mWordV2Index);
			String mWordV3 = cursor.getString(mWordV3Index);
			
			singleWord.setID(mId);
			singleWord.setWordV1(mWordV1);
			singleWord.setWordV2(mWordV2);
			singleWord.setWordV3(mWordV3);
			
			words.add(singleWord);
		}
		
		if (cursor != null) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return words;
	}
}
