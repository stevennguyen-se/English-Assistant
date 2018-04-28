package com.example.thesis.database.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.thesis.database.helper.DictionaryHelper;
import com.example.thesis.global.GlobalString;
import com.example.thesis.object.Major;
import com.example.thesis.object.Word;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DictionaryDatabaseAdapter {

	private static DictionaryHelper mHelper;
	
	private static SQLiteDatabase db;
	
	private static Cursor cursor;
	
	public DictionaryDatabaseAdapter(Context context) {
		mHelper = new DictionaryHelper(context);
		
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
	}
	
	public List<Word> getWord(String word) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {DictionaryHelper.ID, DictionaryHelper.WORD, DictionaryHelper.PRONUNCIATION, DictionaryHelper.TYPE, DictionaryHelper.CONTENT};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(false, findTableByWord(word), columns, 
					DictionaryHelper.WORD+" = '"+word+"'", 
					null, null, null, null, null);
		
		
		List<Word> words = new ArrayList<Word>();
		
		try {
			while (cursor.moveToNext()) {
				Word singleWord = new Word();
				
				int mIdIndex = cursor.getColumnIndex(DictionaryHelper.ID);
				int mTitleIndex = cursor.getColumnIndex(DictionaryHelper.WORD);
				int mPronunciationIndex = cursor.getColumnIndex(DictionaryHelper.PRONUNCIATION);
				int mTypeIndex = cursor.getColumnIndex(DictionaryHelper.TYPE);
				int mDefinitionIndex = cursor.getColumnIndex(DictionaryHelper.CONTENT);
				
				String mId = cursor.getString(mIdIndex);
				String mTitle = cursor.getString(mTitleIndex);
				String mPronunciation = cursor.getString(mPronunciationIndex);
				String mType = cursor.getString(mTypeIndex);
				String mDefinition = cursor.getString(mDefinitionIndex);
				
				singleWord.setId(mId);
				singleWord.setWord(mTitle);
				singleWord.setPronunciation(mPronunciation);
				singleWord.setType(mType);
				singleWord.setDefinition(mDefinition);
				
				words.add(singleWord);
				
			}
		} finally {
			if (!cursor.isClosed()) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		
		return words;
	}
	
	public Word getSingleVerb(String word) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {DictionaryHelper.ID, DictionaryHelper.WORD, DictionaryHelper.PRONUNCIATION, DictionaryHelper.TYPE, DictionaryHelper.CONTENT};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(false, findTableByWord(word), columns, 
					DictionaryHelper.WORD+" = '"+word+"' AND ( " + DictionaryHelper.TYPE + " LIKE '%động từ%' OR " + DictionaryHelper.TYPE + " LIKE '%Động từ%' )", 
					null, null, null, null, null);
		
		Word singleWord = new Word();
		
		try {
			if (cursor.moveToNext()) {
				
				int mIdIndex = cursor.getColumnIndex(DictionaryHelper.ID);
				int mTitleIndex = cursor.getColumnIndex(DictionaryHelper.WORD);
				int mPronunciationIndex = cursor.getColumnIndex(DictionaryHelper.PRONUNCIATION);
				int mTypeIndex = cursor.getColumnIndex(DictionaryHelper.TYPE);
				int mDefinitionIndex = cursor.getColumnIndex(DictionaryHelper.CONTENT);
				
				String mId = cursor.getString(mIdIndex);
				String mTitle = cursor.getString(mTitleIndex);
				String mPronunciation = cursor.getString(mPronunciationIndex);
				String mType = cursor.getString(mTypeIndex);
				String mDefinition = cursor.getString(mDefinitionIndex);
				
				singleWord.setId(mId);
				singleWord.setWord(mTitle);
				singleWord.setPronunciation(mPronunciation);
				singleWord.setType(mType);
				singleWord.setDefinition(mDefinition);
			}
		} finally {
			if (!cursor.isClosed()) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return singleWord;
	}
	
	public List<Word> getAlphabeticalData(String word, String type) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {DictionaryHelper.ID, DictionaryHelper.WORD, DictionaryHelper.PRONUNCIATION, DictionaryHelper.TYPE, DictionaryHelper.CONTENT};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		if (type == null || type.equalsIgnoreCase("None")) {
			cursor = db.query(false, findTableByWord(word), columns, 
					DictionaryHelper.WORD+" LIKE '"+word+"%'", null, null, null, null, "10");
		} else {
			cursor = db.query(false, findTableByWord(word), columns, 
					DictionaryHelper.WORD+" LIKE '"+word+"%' AND " + DictionaryHelper.TYPE + " LIKE '"+type+"'", 
					null, null, null, null, "10");
		}
		
		List<Word> words = new ArrayList<Word>();
		
		try {
			while (cursor.moveToNext()) {
				
				Word singleWord = new Word();
				
				int mIdIndex = cursor.getColumnIndex(DictionaryHelper.ID);
				int mTitleIndex = cursor.getColumnIndex(DictionaryHelper.WORD);
				int mPronunciationIndex = cursor.getColumnIndex(DictionaryHelper.PRONUNCIATION);
				int mTypeIndex = cursor.getColumnIndex(DictionaryHelper.TYPE);
				int mDefinitionIndex = cursor.getColumnIndex(DictionaryHelper.CONTENT);
				
				String mId = cursor.getString(mIdIndex);
				String mTitle = cursor.getString(mTitleIndex);
				String mPronunciation = cursor.getString(mPronunciationIndex);
				String mType = cursor.getString(mTypeIndex);
				String mDefinition = cursor.getString(mDefinitionIndex);
				
				singleWord.setId(mId);
				singleWord.setWord(mTitle);
				singleWord.setPronunciation(mPronunciation);
				singleWord.setType(mType);
				singleWord.setDefinition(mDefinition);
				
				words.add(singleWord);
			}
		} finally {
			// TODO: handle exception
			if (!cursor.isClosed()) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return words;
	}

	public List<Word> getAlphabeticalDataFromTo(String word, String type, int from, int to) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {DictionaryHelper.ID, DictionaryHelper.WORD, DictionaryHelper.PRONUNCIATION, DictionaryHelper.TYPE, DictionaryHelper.CONTENT};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		if (type == null || type.equalsIgnoreCase("None")) {
			cursor = db.query(false, findTableByWord(word), columns, 
					DictionaryHelper.WORD+" >= '"+word+"'", null, null, null, null, String.valueOf(to));
		} else {
			cursor = db.query(false, findTableByWord(word), columns, 
					DictionaryHelper.WORD+" >= '"+word+"' AND " + DictionaryHelper.TYPE + " LIKE '"+type+"'", 
					null, null, null, null, String.valueOf(to));
		}
		
		List<Word> words = new ArrayList<Word>();
		
		if (from > 0) {
			cursor.moveToPosition(from - 1);
		}
		
		while (cursor.moveToNext() && from < to) {
			
			Word singleWord = new Word();
			
			int mIdIndex = cursor.getColumnIndex(DictionaryHelper.ID);
			int mTitleIndex = cursor.getColumnIndex(DictionaryHelper.WORD);
			int mPronunciationIndex = cursor.getColumnIndex(DictionaryHelper.PRONUNCIATION);
			int mTypeIndex = cursor.getColumnIndex(DictionaryHelper.TYPE);
			int mDefinitionIndex = cursor.getColumnIndex(DictionaryHelper.CONTENT);
			
			String mId = cursor.getString(mIdIndex);
			String mTitle = cursor.getString(mTitleIndex);
			String mPronunciation = cursor.getString(mPronunciationIndex);
			String mType = cursor.getString(mTypeIndex);
			String mDefinition = cursor.getString(mDefinitionIndex);
			
			singleWord.setId(mId);
			singleWord.setWord(mTitle);
			singleWord.setPronunciation(mPronunciation);
			singleWord.setType(mType);
			singleWord.setDefinition(mDefinition);
			
			words.add(singleWord);
			
			++from;
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return words;
	}
	
	public List<Major> getMajorData(String word) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		List<Major> majors = new ArrayList<Major>();
		
		String[] columns = {DictionaryHelper.CONTENT};
		
		Major singleMajor;
		
		for (int i = 0; i < GlobalString.DEFAULT_MAJORS.length; ++i) {
			cursor = db.query(false,	findTableByType(word, GlobalString.DEFAULT_MAJORS[i]), columns, 
					DictionaryHelper.WORD+"='"+word+"'", null, null, null, null, null);
			
			singleMajor = new Major();
			
			if (cursor.moveToNext()) {
				
				int mDefinitionIndex = cursor.getColumnIndex(DictionaryHelper.CONTENT);
				
				String mDefinition = cursor.getString(mDefinitionIndex);
				
				singleMajor.setMajor(GlobalString.DEFAULT_MAJORS[i]);
				singleMajor.setDefinition(mDefinition);
				
				majors.add(singleMajor);
			}
			
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return majors;
	}
	
	public String findTableByWord(String word) {
		
		if (word == null || word.isEmpty()) {
			return DictionaryHelper.TABLE_NAME_NONE;
		}
		
		char a;
		a = Character.toLowerCase(word.charAt(0));
		
		switch (a) {
			case 'a':
			{
				return DictionaryHelper.TABLE_NAME_A;
			}
			case 'b':
			{
				return DictionaryHelper.TABLE_NAME_B;
			}
			case 'c':
			{
				return DictionaryHelper.TABLE_NAME_C;
			}
			case 'd':
			{
				return DictionaryHelper.TABLE_NAME_D;
			}
			case 'e':
			{
				return DictionaryHelper.TABLE_NAME_E;
			}
			case 'f':
			{
				return DictionaryHelper.TABLE_NAME_F;
			}
			case 'g':
			{
				return DictionaryHelper.TABLE_NAME_G;
			}
			case 'h':
			{
				return DictionaryHelper.TABLE_NAME_H;
			}
			case 'i':
			{
				return DictionaryHelper.TABLE_NAME_I;
			}
			case 'j':
			{
				return DictionaryHelper.TABLE_NAME_J;
			}
			case 'k':
			{
				return DictionaryHelper.TABLE_NAME_K;
			}
			case 'l':
			{
				return DictionaryHelper.TABLE_NAME_L;
			}
			case 'm':
			{
				return DictionaryHelper.TABLE_NAME_M;
			}
			case 'n':
			{
				return DictionaryHelper.TABLE_NAME_N;
			}
			case 'o':
			{
				return DictionaryHelper.TABLE_NAME_O;
			}
			case 'p':
			{
				return DictionaryHelper.TABLE_NAME_P;
			}
			case 'q':
			{
				return DictionaryHelper.TABLE_NAME_Q;
			}
			case 'r':
			{
				return DictionaryHelper.TABLE_NAME_R;
			}
			case 's':
			{
				return DictionaryHelper.TABLE_NAME_S;
			}
			case 't':
			{
				return DictionaryHelper.TABLE_NAME_T;
			}
			case 'u':
			{
				return DictionaryHelper.TABLE_NAME_U;
			}
			case 'v':
			{
				return DictionaryHelper.TABLE_NAME_V;
			}
			case 'w':
			{
				return DictionaryHelper.TABLE_NAME_W;
			}
			case 'x':
			{
				return DictionaryHelper.TABLE_NAME_X;
			}
			case 'y':
			{
				return DictionaryHelper.TABLE_NAME_Y;
			}
			case 'z':
			{
				return DictionaryHelper.TABLE_NAME_Z;
			}
			default:
				return DictionaryHelper.TABLE_NAME_NONE;
		}
	}

	public String findTableByType(String word, String type) {
		if (type == null || type.isEmpty()) {
			return DictionaryHelper.MAJOR_NONE;
		}
		
		switch (type) 
		{
			case "Chuyên ngành kinh tế": {
				if (word == null || word.isEmpty()) {
					return DictionaryHelper.MAJOR_ECONOMY_NONE;
				}
	
				char a;
				a = Character.toLowerCase(word.charAt(0));
	
				switch (a) 
				{
					case 'a': {
						return DictionaryHelper.MAJOR_ECONOMY_A;
					}
					case 'b': {
						return DictionaryHelper.MAJOR_ECONOMY_B;
					}
					case 'c': {
						return DictionaryHelper.MAJOR_ECONOMY_C;
					}
					case 'd': {
						return DictionaryHelper.MAJOR_ECONOMY_D;
					}
					case 'e': {
						return DictionaryHelper.MAJOR_ECONOMY_E;
					}
					case 'f': {
						return DictionaryHelper.MAJOR_ECONOMY_F;
					}
					case 'g': {
						return DictionaryHelper.MAJOR_ECONOMY_G;
					}
					case 'h': {
						return DictionaryHelper.MAJOR_ECONOMY_H;
					}
					case 'i': {
						return DictionaryHelper.MAJOR_ECONOMY_I;
					}
					case 'j': {
						return DictionaryHelper.MAJOR_ECONOMY_J;
					}
					case 'k': {
						return DictionaryHelper.MAJOR_ECONOMY_K;
					}
					case 'l': {
						return DictionaryHelper.MAJOR_ECONOMY_L;
					}
					case 'm': {
						return DictionaryHelper.MAJOR_ECONOMY_M;
					}
					case 'n': {
						return DictionaryHelper.MAJOR_ECONOMY_N;
					}
					case 'o': {
						return DictionaryHelper.MAJOR_ECONOMY_O;
					}
					case 'p': {
						return DictionaryHelper.MAJOR_ECONOMY_P;
					}
					case 'q': {
						return DictionaryHelper.MAJOR_ECONOMY_Q;
					}
					case 'r': {
						return DictionaryHelper.MAJOR_ECONOMY_R;
					}
					case 's': {
						return DictionaryHelper.MAJOR_ECONOMY_S;
					}
					case 't': {
						return DictionaryHelper.MAJOR_ECONOMY_T;
					}
					case 'u': {
						return DictionaryHelper.MAJOR_ECONOMY_U;
					}
					case 'v': {
						return DictionaryHelper.MAJOR_ECONOMY_V;
					}
					case 'w': {
						return DictionaryHelper.MAJOR_ECONOMY_W;
					}
					case 'x': {
						return DictionaryHelper.MAJOR_ECONOMY_X;
					}
					case 'y': {
						return DictionaryHelper.MAJOR_ECONOMY_Y;
					}
					case 'z': {
						return DictionaryHelper.MAJOR_ECONOMY_Z;
					}
					default:
						return DictionaryHelper.MAJOR_ECONOMY_NONE;
				}
			}
			case "Chuyên ngành kỹ thuật": {
				if (word == null || word.isEmpty()) {
					return DictionaryHelper.MAJOR_TECHNOLOGY_NONE;
				}
	
				char a;
				a = Character.toLowerCase(word.charAt(0));
	
				switch (a) 
				{
					case 'a': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_A;
					}
					case 'b': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_B;
					}
					case 'c': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_C;
					}
					case 'd': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_D;
					}
					case 'e': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_E;
					}
					case 'f': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_F;
					}
					case 'g': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_G;
					}
					case 'h': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_H;
					}
					case 'i': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_I;
					}
					case 'j': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_J;
					}
					case 'k': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_K;
					}
					case 'l': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_L;
					}
					case 'm': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_M;
					}
					case 'n': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_N;
					}
					case 'o': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_O;
					}
					case 'p': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_P;
					}
					case 'q': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_Q;
					}
					case 'r': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_R;
					}
					case 's': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_S;
					}
					case 't': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_T;
					}
					case 'u': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_U;
					}
					case 'v': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_V;
					}
					case 'w': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_W;
					}
					case 'x': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_X;
					}
					case 'y': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_Y;
					}
					case 'z': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_Z;
					}
					default:
						return DictionaryHelper.MAJOR_TECHNOLOGY_NONE;
				}
			}
			case "Lĩnh vực: cơ khí & công trình":
			{
				return DictionaryHelper.MAJOR_ENGINEERING_AND_CONSTRUCTION;
			}
			case "Lĩnh vực: dệt may":
			{
				return DictionaryHelper.MAJOR_TEXTILES;
			}
			case "Lĩnh vực: điện":
			{
				return DictionaryHelper.MAJOR_ELECTRONIC;
			}
			case "Lĩnh vực: điện lạnh":
			{
				return DictionaryHelper.MAJOR_REFRIGERATION;
			}
			case "Lĩnh vực: điện tử & viễn thông":
			{
				return DictionaryHelper.MAJOR_ELECTRONICS_AND_TELECOMMUNICATIONS;
			}
			case "Lĩnh vực: đo lư�?ng & đi�?u khiển":
			{
				return DictionaryHelper.MAJOR_MEASUREMENT_AND_CONTROL;
			}
			case "Lĩnh vực: giao thông & vận tải":
			{
				return DictionaryHelper.MAJOR_TRANSPORTATION;
			}
			case "Lĩnh vực: hóa h�?c & vật liệu":
			{
				return DictionaryHelper.MAJOR_CHEMISTRY_AND_MATERIALS;
			}
			case "Lĩnh vực: môi trư�?ng":
			{
				return DictionaryHelper.MAJOR_ENVIRONMENT;
			}
			case "Lĩnh vực: ô tô":
			{
				return DictionaryHelper.MAJOR_CAR;
			}
			case "Lĩnh vực: thtp":
			{
				return DictionaryHelper.MAJOR_THTP;
			}
			case "Lĩnh vực: thực phẩm":
			{
				return DictionaryHelper.MAJOR_FOOD;
			}
			case "Lĩnh vực: toán & tin":
			{
				if (word == null || word.isEmpty()) {
					return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_NONE;
				}

				char a;
				a = Character.toLowerCase(word.charAt(0));

				switch (a) 
				{
					case 'a': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_A;
					}
					case 'b': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_B;
					}
					case 'c': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_C;
					}
					case 'd': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_D;
					}
					case 'e': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_E;
					}
					case 'f': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_F;
					}
					case 'g': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_G;
					}
					case 'h': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_H;
					}
					case 'i': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_I;
					}
					case 'j': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_J;
					}
					case 'k': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_K;
					}
					case 'l': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_L;
					}
					case 'm': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_M;
					}
					case 'n': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_N;
					}
					case 'o': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_O;
					}
					case 'p': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_P;
					}
					case 'q': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_Q;
					}
					case 'r': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_R;
					}
					case 's': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_S;
					}
					case 't': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_T;
					}
					case 'u': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_U;
					}
					case 'v': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_V;
					}
					case 'w': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_W;
					}
					case 'x': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_X;
					}
					case 'y': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_Y;
					}
					case 'z': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_Z;
					}
					default:
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_NONE;
				}
			}
			case "Lĩnh vực: vật lý":
			{
				return DictionaryHelper.MAJOR_PHYSICS;
			}
			case "Lĩnh vực: xây dựng":
			{
				if (word == null || word.isEmpty()) {
					return DictionaryHelper.MAJOR_CONSTRUCTION_NONE;
				}
	
				char a;
				a = Character.toLowerCase(word.charAt(0));
	
				switch (a) 
				{
					case 'a': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_A;
					}
					case 'b': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_B;
					}
					case 'c': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_C;
					}
					case 'd': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_D;
					}
					case 'e': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_E;
					}
					case 'f': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_F;
					}
					case 'g': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_G;
					}
					case 'h': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_H;
					}
					case 'i': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_I;
					}
					case 'j': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_J;
					}
					case 'k': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_K;
					}
					case 'l': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_L;
					}
					case 'm': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_M;
					}
					case 'n': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_N;
					}
					case 'o': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_O;
					}
					case 'p': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_P;
					}
					case 'q': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_Q;
					}
					case 'r': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_R;
					}
					case 's': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_S;
					}
					case 't': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_T;
					}
					case 'u': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_U;
					}
					case 'v': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_V;
					}
					case 'w': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_W;
					}
					case 'x': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_X;
					}
					case 'y': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_Y;
					}
					case 'z': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_Z;
					}
					default:
						return DictionaryHelper.MAJOR_CONSTRUCTION_NONE;
				}
			}
			case "Lĩnh vực: y h�?c":
			{
				return DictionaryHelper.MAJOR_MEDICINE;
			}
			default:
				return DictionaryHelper.MAJOR_NONE;
		}
	}
	
	public String findTableByWordAndType(String word, String type) {
		
		if (type == null || type.isEmpty()) {
			return DictionaryHelper.MAJOR_NONE;
		} else {
			switch (type) {
			case "Chuyên ngành kinh tế": {
				if (word == null || word.isEmpty()) {
					return DictionaryHelper.MAJOR_ECONOMY_NONE;
				}

				char a;
				a = Character.toLowerCase(word.charAt(0));

				switch (a) 
				{
					case 'a': {
						return DictionaryHelper.MAJOR_ECONOMY_A;
					}
					case 'b': {
						return DictionaryHelper.MAJOR_ECONOMY_B;
					}
					case 'c': {
						return DictionaryHelper.MAJOR_ECONOMY_C;
					}
					case 'd': {
						return DictionaryHelper.MAJOR_ECONOMY_D;
					}
					case 'e': {
						return DictionaryHelper.MAJOR_ECONOMY_E;
					}
					case 'f': {
						return DictionaryHelper.MAJOR_ECONOMY_F;
					}
					case 'g': {
						return DictionaryHelper.MAJOR_ECONOMY_G;
					}
					case 'h': {
						return DictionaryHelper.MAJOR_ECONOMY_H;
					}
					case 'i': {
						return DictionaryHelper.MAJOR_ECONOMY_I;
					}
					case 'j': {
						return DictionaryHelper.MAJOR_ECONOMY_J;
					}
					case 'k': {
						return DictionaryHelper.MAJOR_ECONOMY_K;
					}
					case 'l': {
						return DictionaryHelper.MAJOR_ECONOMY_L;
					}
					case 'm': {
						return DictionaryHelper.MAJOR_ECONOMY_M;
					}
					case 'n': {
						return DictionaryHelper.MAJOR_ECONOMY_N;
					}
					case 'o': {
						return DictionaryHelper.MAJOR_ECONOMY_O;
					}
					case 'p': {
						return DictionaryHelper.MAJOR_ECONOMY_P;
					}
					case 'q': {
						return DictionaryHelper.MAJOR_ECONOMY_Q;
					}
					case 'r': {
						return DictionaryHelper.MAJOR_ECONOMY_R;
					}
					case 's': {
						return DictionaryHelper.MAJOR_ECONOMY_S;
					}
					case 't': {
						return DictionaryHelper.MAJOR_ECONOMY_T;
					}
					case 'u': {
						return DictionaryHelper.MAJOR_ECONOMY_U;
					}
					case 'v': {
						return DictionaryHelper.MAJOR_ECONOMY_V;
					}
					case 'w': {
						return DictionaryHelper.MAJOR_ECONOMY_W;
					}
					case 'x': {
						return DictionaryHelper.MAJOR_ECONOMY_X;
					}
					case 'y': {
						return DictionaryHelper.MAJOR_ECONOMY_Y;
					}
					case 'z': {
						return DictionaryHelper.MAJOR_ECONOMY_Z;
					}
					default:
						return DictionaryHelper.MAJOR_ECONOMY_NONE;
				}
			}
			case "Chuyên ngành kỹ thuật": {
				if (word == null || word.isEmpty()) {
					return DictionaryHelper.MAJOR_TECHNOLOGY_NONE;
				}

				char a;
				a = Character.toLowerCase(word.charAt(0));

				switch (a) 
				{
					case 'a': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_A;
					}
					case 'b': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_B;
					}
					case 'c': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_C;
					}
					case 'd': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_D;
					}
					case 'e': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_E;
					}
					case 'f': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_F;
					}
					case 'g': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_G;
					}
					case 'h': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_H;
					}
					case 'i': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_I;
					}
					case 'j': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_J;
					}
					case 'k': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_K;
					}
					case 'l': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_L;
					}
					case 'm': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_M;
					}
					case 'n': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_N;
					}
					case 'o': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_O;
					}
					case 'p': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_P;
					}
					case 'q': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_Q;
					}
					case 'r': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_R;
					}
					case 's': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_S;
					}
					case 't': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_T;
					}
					case 'u': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_U;
					}
					case 'v': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_V;
					}
					case 'w': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_W;
					}
					case 'x': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_X;
					}
					case 'y': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_Y;
					}
					case 'z': {
						return DictionaryHelper.MAJOR_TECHNOLOGY_Z;
					}
					default:
						return DictionaryHelper.MAJOR_TECHNOLOGY_NONE;
				}
			}
			case "Lĩnh vực: cơ khí & công trình":
			{
				return DictionaryHelper.MAJOR_ENGINEERING_AND_CONSTRUCTION;
			}
			case "Lĩnh vực: dệt may":
			{
				return DictionaryHelper.MAJOR_TEXTILES;
			}
			case "Lĩnh vực: điện":
			{
				return DictionaryHelper.MAJOR_ELECTRONIC;
			}
			case "Lĩnh vực: điện lạnh":
			{
				return DictionaryHelper.MAJOR_REFRIGERATION;
			}
			case "Lĩnh vực: điện tử & viễn thông":
			{
				return DictionaryHelper.MAJOR_ELECTRONICS_AND_TELECOMMUNICATIONS;
			}
			case "Lĩnh vực: đo lư�?ng & đi�?u khiển":
			{
				return DictionaryHelper.MAJOR_MEASUREMENT_AND_CONTROL;
			}
			case "Lĩnh vực: giao thông & vận tải":
			{
				return DictionaryHelper.MAJOR_TRANSPORTATION;
			}
			case "Lĩnh vực: hóa h�?c & vật liệu":
			{
				return DictionaryHelper.MAJOR_CHEMISTRY_AND_MATERIALS;
			}
			case "Lĩnh vực: môi trư�?ng":
			{
				return DictionaryHelper.MAJOR_ENVIRONMENT;
			}
			case "Lĩnh vực: ô tô":
			{
				return DictionaryHelper.MAJOR_CAR;
			}
			case "Lĩnh vực: thtp":
			{
				return DictionaryHelper.MAJOR_THTP;
			}
			case "Lĩnh vực: thực phẩm":
			{
				return DictionaryHelper.MAJOR_FOOD;
			}
			case "Lĩnh vực: toán & tin":
			{
				if (word == null || word.isEmpty()) {
					return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_NONE;
				}

				char a;
				a = Character.toLowerCase(word.charAt(0));

				switch (a) 
				{
					case 'a': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_A;
					}
					case 'b': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_B;
					}
					case 'c': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_C;
					}
					case 'd': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_D;
					}
					case 'e': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_E;
					}
					case 'f': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_F;
					}
					case 'g': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_G;
					}
					case 'h': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_H;
					}
					case 'i': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_I;
					}
					case 'j': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_J;
					}
					case 'k': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_K;
					}
					case 'l': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_L;
					}
					case 'm': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_M;
					}
					case 'n': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_N;
					}
					case 'o': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_O;
					}
					case 'p': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_P;
					}
					case 'q': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_Q;
					}
					case 'r': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_R;
					}
					case 's': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_S;
					}
					case 't': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_T;
					}
					case 'u': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_U;
					}
					case 'v': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_V;
					}
					case 'w': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_W;
					}
					case 'x': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_X;
					}
					case 'y': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_Y;
					}
					case 'z': {
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_Z;
					}
					default:
						return DictionaryHelper.MAJOR_MATHEMATICS_AND_INFORMATION_NONE;
				}
			}
			case "Lĩnh vực: vật lý":
			{
				return DictionaryHelper.MAJOR_PHYSICS;
			}
			case "Lĩnh vực: xây dựng":
			{
				if (word == null || word.isEmpty()) {
					return DictionaryHelper.MAJOR_CONSTRUCTION_NONE;
				}

				char a;
				a = Character.toLowerCase(word.charAt(0));

				switch (a) 
				{
					case 'a': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_A;
					}
					case 'b': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_B;
					}
					case 'c': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_C;
					}
					case 'd': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_D;
					}
					case 'e': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_E;
					}
					case 'f': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_F;
					}
					case 'g': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_G;
					}
					case 'h': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_H;
					}
					case 'i': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_I;
					}
					case 'j': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_J;
					}
					case 'k': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_K;
					}
					case 'l': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_L;
					}
					case 'm': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_M;
					}
					case 'n': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_N;
					}
					case 'o': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_O;
					}
					case 'p': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_P;
					}
					case 'q': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_Q;
					}
					case 'r': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_R;
					}
					case 's': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_S;
					}
					case 't': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_T;
					}
					case 'u': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_U;
					}
					case 'v': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_V;
					}
					case 'w': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_W;
					}
					case 'x': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_X;
					}
					case 'y': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_Y;
					}
					case 'z': {
						return DictionaryHelper.MAJOR_CONSTRUCTION_Z;
					}
					default:
						return DictionaryHelper.MAJOR_CONSTRUCTION_NONE;
				}
			}
			case "Lĩnh vực: y h�?c":
			{
				return DictionaryHelper.MAJOR_MEDICINE;
			}
			default: {
				if (word == null || word.isEmpty()) {
					return DictionaryHelper.TABLE_NAME_NONE;
				}

				char a;
				a = Character.toLowerCase(word.charAt(0));

				switch (a) 
				{
					case 'a': {
						return DictionaryHelper.TABLE_NAME_A;
					}
					case 'b': {
						return DictionaryHelper.TABLE_NAME_B;
					}
					case 'c': {
						return DictionaryHelper.TABLE_NAME_C;
					}
					case 'd': {
						return DictionaryHelper.TABLE_NAME_D;
					}
					case 'e': {
						return DictionaryHelper.TABLE_NAME_E;
					}
					case 'f': {
						return DictionaryHelper.TABLE_NAME_F;
					}
					case 'g': {
						return DictionaryHelper.TABLE_NAME_G;
					}
					case 'h': {
						return DictionaryHelper.TABLE_NAME_H;
					}
					case 'i': {
						return DictionaryHelper.TABLE_NAME_I;
					}
					case 'j': {
						return DictionaryHelper.TABLE_NAME_J;
					}
					case 'k': {
						return DictionaryHelper.TABLE_NAME_K;
					}
					case 'l': {
						return DictionaryHelper.TABLE_NAME_L;
					}
					case 'm': {
						return DictionaryHelper.TABLE_NAME_M;
					}
					case 'n': {
						return DictionaryHelper.TABLE_NAME_N;
					}
					case 'o': {
						return DictionaryHelper.TABLE_NAME_O;
					}
					case 'p': {
						return DictionaryHelper.TABLE_NAME_P;
					}
					case 'q': {
						return DictionaryHelper.TABLE_NAME_Q;
					}
					case 'r': {
						return DictionaryHelper.TABLE_NAME_R;
					}
					case 's': {
						return DictionaryHelper.TABLE_NAME_S;
					}
					case 't': {
						return DictionaryHelper.TABLE_NAME_T;
					}
					case 'u': {
						return DictionaryHelper.TABLE_NAME_U;
					}
					case 'v': {
						return DictionaryHelper.TABLE_NAME_V;
					}
					case 'w': {
						return DictionaryHelper.TABLE_NAME_W;
					}
					case 'x': {
						return DictionaryHelper.TABLE_NAME_X;
					}
					case 'y': {
						return DictionaryHelper.TABLE_NAME_Y;
					}
					case 'z': {
						return DictionaryHelper.TABLE_NAME_Z;
					}
	
					default:
						return DictionaryHelper.TABLE_NAME_NONE;
					}
				}
			}
		}
	}
}
