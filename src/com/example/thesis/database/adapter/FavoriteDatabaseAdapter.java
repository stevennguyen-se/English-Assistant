package com.example.thesis.database.adapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.thesis.database.helper.FavoriteHelper;
import com.example.thesis.object.UserStatisticFavorite;
import com.example.thesis.object.Word;
import com.example.thesis.object.WordWrong;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

@SuppressLint("SimpleDateFormat")
public class FavoriteDatabaseAdapter {

	private static FavoriteHelper mHelper;
	
	private static SQLiteDatabase db;
	
	private static Cursor cursor;
	
	private static ContentValues contentValues;
	
	public FavoriteDatabaseAdapter(Context context) {
		mHelper = new FavoriteHelper(context);
		
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
	
	public long insertData(String Id, String word, String pronunciation, String type, String content) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		Date date = new Date();
		
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));
		
		String days_of_week = new SimpleDateFormat("E").format(date);
		
		int weeks_of_year = Integer.parseInt(new SimpleDateFormat("w").format(date));
		
		contentValues = new ContentValues();
		contentValues.put(FavoriteHelper.ID, Id);
		contentValues.put(FavoriteHelper.DAYS_OF_WEEK, days_of_week);
		contentValues.put(FavoriteHelper.WEEKS_OF_YEAR, weeks_of_year);
		contentValues.put(FavoriteHelper.DAY, day);
		contentValues.put(FavoriteHelper.MONTH, month);
		contentValues.put(FavoriteHelper.YEAR, year);
		contentValues.put(FavoriteHelper.WORD, word);
		contentValues.put(FavoriteHelper.PRONUNCIATION, pronunciation);
		contentValues.put(FavoriteHelper.TYPE, type);
		contentValues.put(FavoriteHelper.CONTENT, content);
		contentValues.put(FavoriteHelper.NUMBER_OF_GUESSING_WRONG, 0);
		
		long id = db.insert(FavoriteHelper.TABLE_NAME, null, contentValues);
		
		if (contentValues != null) {
			contentValues.clear();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return id;
	}
	
	public int getNumberOfRows(String word) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.ID, FavoriteHelper.WORD};
		
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
				FavoriteHelper.WORD + " LIKE '"+word+"%'", null, null, null, null);
		
		int count = cursor.getCount();
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	public int getNumberOfWordBasedOnDate(String day, String month, String year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.DAY,FavoriteHelper.MONTH,FavoriteHelper.YEAR};
		String[] selectionArgs = {day, month, year};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.DAY + " =? AND " + FavoriteHelper.MONTH + " =? AND " + FavoriteHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		int count = cursor.getCount();
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	public int getNumberOfWordBasedOnDaysOfWeek(String days_of_week, String week, String year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.DAYS_OF_WEEK,FavoriteHelper.WEEKS_OF_YEAR,FavoriteHelper.YEAR};
		String[] selectionArgs = {days_of_week, week, year};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.DAYS_OF_WEEK + " =? AND " + FavoriteHelper.WEEKS_OF_YEAR + " =? AND " + FavoriteHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		int count = cursor.getCount();
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	public int getNumberOfWordBasedOnWeeksOfYear(String week, String year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.WEEKS_OF_YEAR,FavoriteHelper.YEAR};
		String[] selectionArgs = {week, year};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.WEEKS_OF_YEAR + " =? AND " + FavoriteHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		int count = cursor.getCount();
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	public int getNumberOfWordBasedOnMonth(String month, String year) {
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.MONTH,FavoriteHelper.YEAR};
		String[] selectionArgs = {month, year};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.MONTH + " =? AND " + FavoriteHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		int count = cursor.getCount();
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	public int getNumberOfWordBasedOnYear(String year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.MONTH,FavoriteHelper.YEAR};
		String[] selectionArgs = {year};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		int count = cursor.getCount();
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	private String getMinDaysOfWeek(String week, String year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.DAYS_OF_WEEK,FavoriteHelper.WEEKS_OF_YEAR,FavoriteHelper.YEAR};
		String[] selectionArgs = {week, year};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.WEEKS_OF_YEAR + " =? AND " + FavoriteHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		String daysOfWeek = null;
		
		if (cursor.moveToNext()) {
			int mDaysOfWeekIndex = cursor.getColumnIndex(FavoriteHelper.DAYS_OF_WEEK);
			
			String mDaysOfWeek = cursor.getString(mDaysOfWeekIndex);
			
			daysOfWeek = mDaysOfWeek;
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return daysOfWeek;
	}
	
	private int getMinMonthOfYear(String year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.MONTH,FavoriteHelper.YEAR};
		String[] selectionArgs = {year};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
				FavoriteHelper.MONTH + " = (SELECT MIN("+ FavoriteHelper.MONTH +") FROM "+ FavoriteHelper.TABLE_NAME + ") AND " + FavoriteHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		int minMonth = 0;
		
		if (cursor.moveToNext()) {
			int mMonthIndex = cursor.getColumnIndex(FavoriteHelper.MONTH);
			
			int mMonth = cursor.getInt(mMonthIndex);
			
			minMonth = mMonth;
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return minMonth;
	}
	
	private String getMaxDaysOfWeek(String week, String year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.DAYS_OF_WEEK,FavoriteHelper.WEEKS_OF_YEAR,FavoriteHelper.YEAR};
		String[] selectionArgs = {week, year};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.WEEKS_OF_YEAR + " =? AND " + FavoriteHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		String daysOfWeek = null;
		
		if (cursor.moveToLast()) {
			int mDaysOfWeekIndex = cursor.getColumnIndex(FavoriteHelper.DAYS_OF_WEEK);
			
			String mDaysOfWeek = cursor.getString(mDaysOfWeekIndex);
			
			daysOfWeek = mDaysOfWeek;
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return daysOfWeek;
	}
	
	private int getMaxMonthOfYear(String year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.MONTH,FavoriteHelper.YEAR};
		String[] selectionArgs = {year};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
				FavoriteHelper.MONTH + " = (SELECT MAX("+ FavoriteHelper.MONTH +") FROM "+ FavoriteHelper.TABLE_NAME + ") AND " + FavoriteHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		int maxMonth = 0;
		
		if (cursor.moveToNext()) {
			int mMonthIndex = cursor.getColumnIndex(FavoriteHelper.MONTH);
			
			int mMonth = cursor.getInt(mMonthIndex);
			
			maxMonth = mMonth;
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return maxMonth;
	}
	
	private int getMaxYear() {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.YEAR};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(true, FavoriteHelper.TABLE_NAME, columns, 
				FavoriteHelper.YEAR + " = (SELECT MAX("+ FavoriteHelper.YEAR +") FROM "+ FavoriteHelper.TABLE_NAME + ")", null, null, null, null, null);
		
		int maxYear = 0;
		
		if (cursor.moveToNext()) {
			
			int mYearIndex = cursor.getColumnIndex(FavoriteHelper.YEAR);
			
			int mYear = cursor.getInt(mYearIndex);

			maxYear = mYear;
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return maxYear;
	}
	
	private int getMaxWeeksOfYear(int year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.WEEKS_OF_YEAR, FavoriteHelper.YEAR};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(true, FavoriteHelper.TABLE_NAME, columns, 
				FavoriteHelper.WEEKS_OF_YEAR + " = (SELECT MAX("+ FavoriteHelper.WEEKS_OF_YEAR +") FROM "+ FavoriteHelper.TABLE_NAME + ") AND " + FavoriteHelper.YEAR + " = '"+ year +"'", null, null, null, null, null);
		
		int maxWeek = 0;
		
		if (cursor.moveToNext()) {
			
			int mWeekIndex = cursor.getColumnIndex(FavoriteHelper.WEEKS_OF_YEAR);
			
			int mWeek = cursor.getInt(mWeekIndex);

			maxWeek = mWeek;
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return maxWeek;
	}
	
	// number = 0: current week
	// number = 1: last 1 week
	// number = 2: last 2 week
	// number = 3: last 3 week
	public List<UserStatisticFavorite> getFavoriteWordsWeek(int num) {
		List<UserStatisticFavorite> totalFavoriteWords = new ArrayList<UserStatisticFavorite>();
		
		int year = getMaxYear();
		if (year != 0) {
			int week = getMaxWeeksOfYear(year) - num;
			if (week != 0) {
				String minDaysOfWeek = getMinDaysOfWeek(String.valueOf(week), String.valueOf(year));
				String maxDaysOfWeek = getMaxDaysOfWeek(String.valueOf(week), String.valueOf(year));
				
				if (minDaysOfWeek != null && maxDaysOfWeek != null) {
					String[] mWeek = new String[] {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
					
					int min = 0;
					int max = 0;
					
					for (int i = 0; i < mWeek.length; ++i) {
						if (mWeek[i].equalsIgnoreCase(minDaysOfWeek)) {
							min = i;
						}
						if (mWeek[i].equalsIgnoreCase(maxDaysOfWeek)) {
							max = i;
							break;
						}
					}
					for (int i = min; i <= max; ++i) {
						UserStatisticFavorite userStatisticFavorite = new UserStatisticFavorite();
						userStatisticFavorite.setDate(mWeek[i]);
						userStatisticFavorite.setTotalFavoriteWord(getNumberOfWordBasedOnDaysOfWeek(mWeek[i], String.valueOf(week), String.valueOf(year)));
						
						totalFavoriteWords.add(userStatisticFavorite);
					}
				}
			}
		}
		
		return totalFavoriteWords;
	}

	// number = 0: current year
	// number = 1: last year
	public List<UserStatisticFavorite> getFavoriteWordsMonth(int num) {
		List<UserStatisticFavorite> totalFavoriteWords = new ArrayList<UserStatisticFavorite>();
		
		int year = getMaxYear() - num;
		if (year != 0) {
			int minMonth = getMinMonthOfYear(String.valueOf(year));
			int maxMonth = getMaxMonthOfYear(String.valueOf(year));
			
			if (minMonth != 0 || maxMonth != 0) {
				String[] mMonth = new String[] {"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
				
				for (int i = minMonth; i <= maxMonth; ++i) {
					UserStatisticFavorite userStatisticFavorite = new UserStatisticFavorite();
					
					userStatisticFavorite.setDate(mMonth[i]);
					userStatisticFavorite.setTotalFavoriteWord(getNumberOfWordBasedOnMonth(String.valueOf(i),String.valueOf(year)));
					
					totalFavoriteWords.add(userStatisticFavorite);
				}
			}
		}
		
		return totalFavoriteWords;
	}
	
	/*
	 * From begin with 0;
	 */
	public List<Word> getAllWords(int from, int to) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.ID, FavoriteHelper.WORD, FavoriteHelper.PRONUNCIATION, FavoriteHelper.TYPE, FavoriteHelper.CONTENT};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									null, null, null, null, null);
		
		List<Word> words = new ArrayList<Word>();
		
		if (from > 0) {
			cursor.moveToPosition(from - 1);
		}
		
		while (cursor.moveToNext() && from < to) {
			
			Word singleWord = new Word();
			
			int mIdIndex = cursor.getColumnIndex(FavoriteHelper.ID);
			int mTitleIndex = cursor.getColumnIndex(FavoriteHelper.WORD);
			int mPronunciationIndex = cursor.getColumnIndex(FavoriteHelper.PRONUNCIATION);
			int mTypeIndex = cursor.getColumnIndex(FavoriteHelper.TYPE);
			int mDefinitionIndex = cursor.getColumnIndex(FavoriteHelper.CONTENT);
			
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
			
			words.add(0, singleWord);
			
			++ from;
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
	
	public List<Word> getWords(String word, int from, int to) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.ID, FavoriteHelper.DAY, FavoriteHelper.MONTH, FavoriteHelper.YEAR, FavoriteHelper.WORD, FavoriteHelper.PRONUNCIATION, FavoriteHelper.TYPE, FavoriteHelper.CONTENT, FavoriteHelper.NUMBER_OF_GUESSING_WRONG};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.WORD + " LIKE '"+word+"%'", null, null, null, 
									FavoriteHelper.NUMBER_OF_GUESSING_WRONG + " DESC");
		
		List<Word> words = new ArrayList<Word>();
		
		if (from > 0) {
			cursor.moveToPosition(from - 1);
		}
		
		while (cursor.moveToNext() && from < to) {
			
			Word singleWord = new Word();
			
			int mIdIndex = cursor.getColumnIndex(FavoriteHelper.ID);
			int mTitleIndex = cursor.getColumnIndex(FavoriteHelper.WORD);
			int mPronunciationIndex = cursor.getColumnIndex(FavoriteHelper.PRONUNCIATION);
			int mTypeIndex = cursor.getColumnIndex(FavoriteHelper.TYPE);
			int mDefinitionIndex = cursor.getColumnIndex(FavoriteHelper.CONTENT);
			int mNumberOfGuessingWrongWordIndex = cursor.getColumnIndex(FavoriteHelper.NUMBER_OF_GUESSING_WRONG);
			
			String mId = cursor.getString(mIdIndex);
			String mTitle = cursor.getString(mTitleIndex);
			String mPronunciation = cursor.getString(mPronunciationIndex);
			String mType = cursor.getString(mTypeIndex);
			String mDefinition = cursor.getString(mDefinitionIndex);
			int mNumberOfGuessingWrongWord = cursor.getInt(mNumberOfGuessingWrongWordIndex);
			
			singleWord.setId(mId);
			singleWord.setWord(mTitle);
			singleWord.setPronunciation(mPronunciation);
			singleWord.setType(mType);
			singleWord.setDefinition(mDefinition);
			singleWord.setNumberOfGuessingWrong(mNumberOfGuessingWrongWord);;
			
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
	
	/*
	 * Get list of words based on day, month, year
	 */
	public List<Word> getWordsOfDate(int day, int month, int year) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.ID,
				FavoriteHelper.DAY, FavoriteHelper.MONTH, FavoriteHelper.YEAR,
				FavoriteHelper.WORD, FavoriteHelper.PRONUNCIATION, FavoriteHelper.TYPE, FavoriteHelper.CONTENT};
		
		String[] selectionArgs = {String.valueOf(day), String.valueOf(month), String.valueOf(year)};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.DAY + " = ? AND " + FavoriteHelper.MONTH + " = ? AND " + FavoriteHelper.YEAR + " = ?",
									selectionArgs, null, null, null);
		
		List<Word> words = new ArrayList<Word>();
		
		while (cursor.moveToNext()) {
			
			Word singleWord = new Word();
			
			int mIdIndex = cursor.getColumnIndex(FavoriteHelper.ID);
			int mTitleIndex = cursor.getColumnIndex(FavoriteHelper.WORD);
			int mPronunciationIndex = cursor.getColumnIndex(FavoriteHelper.PRONUNCIATION);
			int mTypeIndex = cursor.getColumnIndex(FavoriteHelper.TYPE);
			int mDefinitionIndex = cursor.getColumnIndex(FavoriteHelper.CONTENT);
			
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
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return words;
	}
	
	public List<WordWrong> get10WordsMaxWrong() {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.WORD, FavoriteHelper.NUMBER_OF_GUESSING_WRONG};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
				null, null, null, null, FavoriteHelper.NUMBER_OF_GUESSING_WRONG + " DESC", "10");
		
		List<WordWrong> wordWrong = new ArrayList<WordWrong>();
		
		for (int i = 0; cursor.moveToNext(); ++i) {
			
			WordWrong singleWord = new WordWrong();
			
			int mWordIndex = cursor.getColumnIndex(FavoriteHelper.WORD);
			int mNumberOfGuessingWrongIndex = cursor.getColumnIndex(FavoriteHelper.NUMBER_OF_GUESSING_WRONG);
			
			String mWord = cursor.getString(mWordIndex);
			int mNumberOfGuessingWrong = cursor.getInt(mNumberOfGuessingWrongIndex);
			
			singleWord.setID(i);
			singleWord.setWord(mWord);
			singleWord.setNumberOfGuessingWrong(mNumberOfGuessingWrong);
			
			wordWrong.add(singleWord);
		}
		
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return wordWrong;
	}
	
	public boolean findWord(String id, String word) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] columns = {FavoriteHelper.ID, FavoriteHelper.WORD};
		String[] selectionArgs = {id, word};
		
		// DESC and ASC
		cursor = db.query(	FavoriteHelper.TABLE_NAME, columns, 
									FavoriteHelper.ID + " =? AND " + FavoriteHelper.WORD + " =?", 
									selectionArgs, null, null, null, null);
		
		if (cursor.moveToNext()) {
			if (!cursor.isClosed()) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			return true;
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return false;
	}
	
	public int upgradeWord(String id, String word, int numberOfGuessingWrongWord) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		contentValues = new ContentValues();
		contentValues.put(FavoriteHelper.ID, id);
		contentValues.put(FavoriteHelper.WORD, word);
		contentValues.put(FavoriteHelper.NUMBER_OF_GUESSING_WRONG, numberOfGuessingWrongWord);
		
		String[] whereArgs = {String.valueOf(id), String.valueOf(word)};
		
		int count = db.update(FavoriteHelper.TABLE_NAME, contentValues, 
				FavoriteHelper.ID + " = ? AND " + FavoriteHelper.WORD + " = ?", whereArgs);
		
		if (contentValues != null) {
			contentValues.clear();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	public int deleteWord(String id, String word) {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		String[] whereArgs = {id, word};
		
		int count = db.delete(FavoriteHelper.TABLE_NAME, FavoriteHelper.ID + " =? AND " + FavoriteHelper.WORD + " =?", whereArgs);
		
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
	
	public boolean upgradeWords(List<Word> selectedWords) {
		if (selectedWords == null) {
			return true;
		}
		
		for (int i = 0; i < selectedWords.size(); ++i) {
			if (upgradeWord(selectedWords.get(i).getId(), selectedWords.get(i).getWord(), selectedWords.get(i).getNumberOfGuessingWrong()) < 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean deleteWords(List<Word> selectedWords) {
		if (selectedWords == null) {
			return true;
		}
		
		for (int i = 0; i < selectedWords.size(); ++i) {
			if (deleteWord(selectedWords.get(i).getId(), selectedWords.get(i).getWord()) < 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean deletedAll() {
		mHelper.openDataBase();
		db = mHelper.getWritableDatabase();
		
		int count = db.delete(FavoriteHelper.TABLE_NAME, null, null);

		if (count < 0) {
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
			return false;
		} else {
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
			return true;
		}
	}
}
