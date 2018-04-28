package com.example.thesis.database.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.thesis.database.helper.UserStatisticHelper;
import com.example.thesis.object.UserStatistic;
import com.example.thesis.object.UserStatisticGeneral;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserStatisticDatabaseAdapter {

	private static UserStatisticHelper mHelper;
	
	private static SQLiteDatabase db;
	
	private static Cursor cursor;
	
	private static ContentValues contentValues;
	
	public UserStatisticDatabaseAdapter(Context context) {
		mHelper = new UserStatisticHelper(context);
		
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
	
	public boolean insertUserData(String days_of_week, int weeks_of_year, int day, int month, int year, 
							int total_num_of_view_word_detail, 
							int total_num_of_favorite_word,
							int total_num_of_adding_favorite_word,
							int total_num_of_removing_favorite_word,
							int total_num_of_hearing_voice_of_word) {
		if (findDate(day, month, year)) {
			if (upgradeData(day, month, year, 
					total_num_of_view_word_detail, 
					total_num_of_favorite_word, 
					total_num_of_adding_favorite_word, 
					total_num_of_removing_favorite_word,
					total_num_of_hearing_voice_of_word) < 0) {
				return false;
			}
		} else {
			if (insertData(days_of_week, weeks_of_year, day, month, year, 
					total_num_of_view_word_detail, 
					total_num_of_favorite_word, 
					total_num_of_adding_favorite_word,
					total_num_of_removing_favorite_word,
					total_num_of_hearing_voice_of_word) < 0) {
				return false;
			}
		}
		return true;
	}
	
	public long insertData(	String days_of_week, int weeks_of_year, int day, int month, int year, 
							int total_num_of_view_word_detail, 
							int total_num_of_favorite_word,
							int total_num_of_adding_favorite_word,
							int total_num_of_removing_favorite_word,
							int total_num_of_hearing_voice_of_word) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		contentValues = new ContentValues();
		contentValues.put(UserStatisticHelper.DAYS_OF_WEEK, days_of_week);
		contentValues.put(UserStatisticHelper.WEEKS_OF_YEAR, weeks_of_year);
		contentValues.put(UserStatisticHelper.DAY, day);
		contentValues.put(UserStatisticHelper.MONTH, month);
		contentValues.put(UserStatisticHelper.YEAR, year);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_VIEW_WORD_DETAIL, total_num_of_view_word_detail);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_FAVORITE_WORD, total_num_of_favorite_word);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_ADDING_FAVORITE_WORD, total_num_of_adding_favorite_word);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_REMOVING_FAVORITE_WORD, total_num_of_removing_favorite_word);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_HEARING_VOICE_OF_WORD, total_num_of_hearing_voice_of_word);
		
		long id = db.insert(UserStatisticHelper.TABLE_NAME, null, contentValues);
		
		if (contentValues != null) {
			contentValues.clear();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return id;
	}
	
	public UserStatistic getUserStatisticAttribute(int day, int month, int year) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.TOTAL_NUM_OF_VIEW_WORD_DETAIL, 
							UserStatisticHelper.TOTAL_NUM_OF_FAVORITE_WORD, 
							UserStatisticHelper.TOTAL_NUM_OF_ADDING_FAVORITE_WORD, 
							UserStatisticHelper.TOTAL_NUM_OF_REMOVING_FAVORITE_WORD,
							UserStatisticHelper.TOTAL_NUM_OF_HEARING_VOICE_OF_WORD};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		String[] selectionArgs = {String.valueOf(day), String.valueOf(month), String.valueOf(year)};
		
		cursor = db.query(false, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.DAY + " =? AND "+ UserStatisticHelper.MONTH + " = ? AND " + UserStatisticHelper.YEAR + " = ?", selectionArgs, null, null, null, "10");
		
		UserStatistic userAttribute = new UserStatistic();
		
		try {
			while (cursor.moveToNext()) {
				
				int mViewWordDetailIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_VIEW_WORD_DETAIL);
				int mFavoriteWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_FAVORITE_WORD);
				int mAddingFavoriteWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_ADDING_FAVORITE_WORD);
				int mRemovingFavoriteWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_REMOVING_FAVORITE_WORD);
				int mHearingVoiceOfWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_HEARING_VOICE_OF_WORD);
				
				int mViewWordDetail = cursor.getInt(mViewWordDetailIndex);
				int mFavoriteWord = cursor.getInt(mFavoriteWordIndex);
				int mAddingFavoriteWord = cursor.getInt(mAddingFavoriteWordIndex);
				int mRemovingFavoriteWord = cursor.getInt(mRemovingFavoriteWordIndex);
				int mHearingVoiceOfWOrd = cursor.getInt(mHearingVoiceOfWordIndex);
				
				userAttribute.setTotal_num_of_view_word_detail(mViewWordDetail);
				userAttribute.setTotal_num_of_favorite_word(mFavoriteWord);
				userAttribute.setTotal_num_of_adding_favorite_word(mAddingFavoriteWord);
				userAttribute.setTotal_num_of_removing_favorite_word(mRemovingFavoriteWord);
				userAttribute.setTotal_num_of_hearing_voice_of_word(mHearingVoiceOfWOrd);
				
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return userAttribute;
	}
	
	private int getMaxYear() {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.YEAR};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(true, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.YEAR + " = (SELECT MAX("+ UserStatisticHelper.YEAR +") FROM "+ UserStatisticHelper.TABLE_NAME + ")", null, null, null, null, null);
		
		int maxYear = 0;
		
		try {
			if (cursor.moveToNext()) {
				
				int mYearIndex = cursor.getColumnIndex(UserStatisticHelper.YEAR);
				
				int mYear = cursor.getInt(mYearIndex);

				maxYear = mYear;
			}

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return maxYear;
	}
	
	private int getMaxWeekOfYear(int year) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.WEEKS_OF_YEAR,
							UserStatisticHelper.YEAR};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(true, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.WEEKS_OF_YEAR + " = (SELECT MAX("+ UserStatisticHelper.WEEKS_OF_YEAR +") FROM "+ UserStatisticHelper.TABLE_NAME + ") AND " + UserStatisticHelper.YEAR + " = '"+ year +"'", 
				null, null, null, null, null);
		
		int maxWeekOfYear = 0;
		
		try {
			if (cursor.moveToNext()) {
				
				int mWeekOfYearIndex = cursor.getColumnIndex(UserStatisticHelper.WEEKS_OF_YEAR);
				
				int mMaxWeekOfYear = cursor.getInt(mWeekOfYearIndex);

				maxWeekOfYear = mMaxWeekOfYear;
			}

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return maxWeekOfYear;
	}
	
	private int getMaxMonthOfYear(int year) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.MONTH,
							UserStatisticHelper.YEAR};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(true, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.MONTH + " = (SELECT MAX("+ UserStatisticHelper.MONTH +") FROM "+ UserStatisticHelper.TABLE_NAME + ") AND " + UserStatisticHelper.YEAR + " = '"+year+"'" , null, null, null, null, null);
		
		int maxMonth = 0;
		
		try {
			if (cursor.moveToNext()) {
				
				int mMonthIndex = cursor.getColumnIndex(UserStatisticHelper.MONTH);
				
				int mMonth = cursor.getInt(mMonthIndex);

				maxMonth = mMonth;
			}

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return maxMonth;
	}
	
	private String getMaxDaysOfWeek(int week, int year) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.DAYS_OF_WEEK,
							UserStatisticHelper.WEEKS_OF_YEAR,
							UserStatisticHelper.YEAR};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(true, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.WEEKS_OF_YEAR + " = '"+week+"' AND " + UserStatisticHelper.YEAR + " = '"+ year +"'", 
				null, null, null, null, null);
		
		String maxDayOfWeek = null;
		
		try {
			if (cursor.moveToLast()) {
				
				int mDayOfWeekIndex = cursor.getColumnIndex(UserStatisticHelper.DAYS_OF_WEEK);
				
				String mMaxDayOfWeek = cursor.getString(mDayOfWeekIndex);

				maxDayOfWeek = mMaxDayOfWeek;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return maxDayOfWeek;
	}
	
	private String getMinDaysOfWeek(int week, int year) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.DAYS_OF_WEEK,
							UserStatisticHelper.WEEKS_OF_YEAR,
							UserStatisticHelper.YEAR};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(true, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.WEEKS_OF_YEAR + " = '"+week+"' AND " + UserStatisticHelper.YEAR + " = '"+ year +"'", 
				null, null, null, null, null);
		
		String minDayOfWeek = null;
		
		try {
			if (cursor.moveToNext()) {
				
				int mDayOfWeekIndex = cursor.getColumnIndex(UserStatisticHelper.DAYS_OF_WEEK);
				
				String mMinDayOfWeek = cursor.getString(mDayOfWeekIndex);

				minDayOfWeek = mMinDayOfWeek;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return minDayOfWeek;
	}
	
	private int getMinMonthOfYear(int year) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.MONTH,
							UserStatisticHelper.YEAR};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(true, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.MONTH + " = (SELECT MIN("+ UserStatisticHelper.MONTH +") FROM "+ UserStatisticHelper.TABLE_NAME + ") AND " + UserStatisticHelper.YEAR + " = '"+year+"'" , null, null, null, null, null);
		
		int minMonth = 0;
		
		try {
			if (cursor.moveToNext()) {
				
				int mMonthIndex = cursor.getColumnIndex(UserStatisticHelper.MONTH);
				
				int mMonth = cursor.getInt(mMonthIndex);

				minMonth = mMonth;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return minMonth;
	}
	
	private UserStatisticGeneral getUserStatisticAttributeByMonth(int month, int year) {
		if (month <= 0 || month > 12) {
			return new UserStatisticGeneral();
		}
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.ID,
							UserStatisticHelper.MONTH,
							UserStatisticHelper.YEAR,
							UserStatisticHelper.TOTAL_NUM_OF_VIEW_WORD_DETAIL, 
							UserStatisticHelper.TOTAL_NUM_OF_FAVORITE_WORD, 
							UserStatisticHelper.TOTAL_NUM_OF_ADDING_FAVORITE_WORD, 
							UserStatisticHelper.TOTAL_NUM_OF_REMOVING_FAVORITE_WORD,
							UserStatisticHelper.TOTAL_NUM_OF_HEARING_VOICE_OF_WORD};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(false, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.MONTH +" = '"+month+"' AND " + UserStatisticHelper.YEAR + " = '"+year+"'", 
				null, null, null, null, null);
		
		UserStatisticGeneral userAttributeStatistic = new UserStatisticGeneral();
		
		try {
			while (cursor.moveToNext()) {
				
				int mViewWordDetailIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_VIEW_WORD_DETAIL);
				int mFavoriteWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_FAVORITE_WORD);
				int mAddingFavoriteWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_ADDING_FAVORITE_WORD);
				int mRemovingFavoriteWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_REMOVING_FAVORITE_WORD);
				int mHearingVoiceOfWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_HEARING_VOICE_OF_WORD);
				
				int mViewWordDetail = cursor.getInt(mViewWordDetailIndex);
				int mFavoriteWord = cursor.getInt(mFavoriteWordIndex);
				int mAddingFavoriteWord = cursor.getInt(mAddingFavoriteWordIndex);
				int mRemovingFavoriteWord = cursor.getInt(mRemovingFavoriteWordIndex);
				int mHearingVoiceOfWOrd = cursor.getInt(mHearingVoiceOfWordIndex);
				
				
				userAttributeStatistic.setTotal_num_of_view_word_detail(userAttributeStatistic.getTotal_num_of_view_word_detail() + mViewWordDetail);
				userAttributeStatistic.setTotal_num_of_favorite_word(userAttributeStatistic.getTotal_num_of_favorite_word() + mFavoriteWord);
				userAttributeStatistic.setTotal_num_of_adding_favorite_word(userAttributeStatistic.getTotal_num_of_adding_favorite_word() + mAddingFavoriteWord);
				userAttributeStatistic.setTotal_num_of_removing_favorite_word(userAttributeStatistic.getTotal_num_of_removing_favorite_word() + mRemovingFavoriteWord);
				userAttributeStatistic.setTotal_num_of_hearing_voice_of_word(userAttributeStatistic.getTotal_num_of_hearing_voice_of_word() + mHearingVoiceOfWOrd);
				
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return userAttributeStatistic;
	}
	
	private UserStatisticGeneral getUserStatisticAttributeByWeek(String day, int week, int year) {
		if (day == null || day.isEmpty()) {
			return new UserStatisticGeneral();
		}
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.ID,
							UserStatisticHelper.DAYS_OF_WEEK,
							UserStatisticHelper.WEEKS_OF_YEAR,
							UserStatisticHelper.YEAR,
							UserStatisticHelper.TOTAL_NUM_OF_VIEW_WORD_DETAIL, 
							UserStatisticHelper.TOTAL_NUM_OF_FAVORITE_WORD, 
							UserStatisticHelper.TOTAL_NUM_OF_ADDING_FAVORITE_WORD, 
							UserStatisticHelper.TOTAL_NUM_OF_REMOVING_FAVORITE_WORD,
							UserStatisticHelper.TOTAL_NUM_OF_HEARING_VOICE_OF_WORD};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(false, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.DAYS_OF_WEEK +" = '"+day+"' AND " + UserStatisticHelper.WEEKS_OF_YEAR +" = '"+week+"' AND " + UserStatisticHelper.YEAR + " = '"+year+"'", 
				null, null, null, null, null);
		
		UserStatisticGeneral userAttributeStatistic = new UserStatisticGeneral();
		
		try {
			if (cursor.moveToNext()) {
				
				int mViewWordDetailIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_VIEW_WORD_DETAIL);
				int mFavoriteWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_FAVORITE_WORD);
				int mAddingFavoriteWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_ADDING_FAVORITE_WORD);
				int mRemovingFavoriteWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_REMOVING_FAVORITE_WORD);
				int mHearingVoiceOfWordIndex = cursor.getColumnIndex(UserStatisticHelper.TOTAL_NUM_OF_HEARING_VOICE_OF_WORD);
				
				int mViewWordDetail = cursor.getInt(mViewWordDetailIndex);
				int mFavoriteWord = cursor.getInt(mFavoriteWordIndex);
				int mAddingFavoriteWord = cursor.getInt(mAddingFavoriteWordIndex);
				int mRemovingFavoriteWord = cursor.getInt(mRemovingFavoriteWordIndex);
				int mHearingVoiceOfWOrd = cursor.getInt(mHearingVoiceOfWordIndex);
				
				
				userAttributeStatistic.setTotal_num_of_view_word_detail(userAttributeStatistic.getTotal_num_of_view_word_detail() + mViewWordDetail);
				userAttributeStatistic.setTotal_num_of_favorite_word(userAttributeStatistic.getTotal_num_of_favorite_word() + mFavoriteWord);
				userAttributeStatistic.setTotal_num_of_adding_favorite_word(userAttributeStatistic.getTotal_num_of_adding_favorite_word() + mAddingFavoriteWord);
				userAttributeStatistic.setTotal_num_of_removing_favorite_word(userAttributeStatistic.getTotal_num_of_removing_favorite_word() + mRemovingFavoriteWord);
				userAttributeStatistic.setTotal_num_of_hearing_voice_of_word(userAttributeStatistic.getTotal_num_of_hearing_voice_of_word() + mHearingVoiceOfWOrd);
				
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return userAttributeStatistic;
	}

	// number = 0: current week
	// number = 1: last 1 week
	// number = 2: last 2 week
	// number = 3: last 3 week
	public List<UserStatisticGeneral> getUserStatisticAttributeByWeek(int num) {
		List<UserStatisticGeneral> listUserAttributeStatistic = new ArrayList<UserStatisticGeneral>();
		
		int year = getMaxYear();
		
		if (year != 0) {
			
			int week = getMaxWeekOfYear(year) - num;
			
			if (week != 0) {
				String minDay = getMinDaysOfWeek(week, year);
				String maxDay = getMaxDaysOfWeek(week, year);
				
				if (minDay != null && maxDay != null) {
					String[] mWeek = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
					
					int min = 0;
					int max = 0;
					
					for (int i = 0; i < mWeek.length; ++i) {
						if (mWeek[i].equalsIgnoreCase(minDay)) {
							min = i;
						}
						if (mWeek[i].equalsIgnoreCase(maxDay)) {
							max = i;
							break;
						}
					}
					
					for (int i = min; i <= max; ++i) {
						UserStatisticGeneral userAttributeStatistic = getUserStatisticAttributeByWeek(mWeek[i], week, year);
						userAttributeStatistic.setDate(mWeek[i]);
						
						listUserAttributeStatistic.add(userAttributeStatistic);
					}
				}
			}
		}
		
		return listUserAttributeStatistic;
	}
	
	// number = 0: current year
	// number = 1: last 1 year
	public List<UserStatisticGeneral> getUserStatisticAttributeByMonth(int num) {
		List<UserStatisticGeneral> listUserAttributeStatistic = new ArrayList<UserStatisticGeneral>();
		
		int year = getMaxYear() - num;
		if (year != 0) {
			int min = getMinMonthOfYear(year);
			int max = getMaxMonthOfYear(year);
			
			if (min != 0 && max != 0) {
				if (min !=0 || max !=0) {
					String[] mMonth = new String[] {"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
					
					for (int i = min; i <= max; ++i) {
						UserStatisticGeneral userAttributeStatistic = getUserStatisticAttributeByMonth(i, year);
						userAttributeStatistic.setDate(mMonth[i]);
						
						listUserAttributeStatistic.add(userAttributeStatistic);
					}
				}
			}
		}
		
		return listUserAttributeStatistic;
	}
	
	public List<Integer> getDaysHaveFavoriteWords(int month, int year) {
		if (month <= 0 || month > 12) {
			return new ArrayList<Integer>();
		}
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.DAY,
							UserStatisticHelper.MONTH,
							UserStatisticHelper.YEAR};
		
		// Selection parameter: TITLE='word'
		// Order by: DECS
		cursor = db.query(false, UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.MONTH +" = '"+month+"' AND " + UserStatisticHelper.YEAR + " = '"+year+"'", 
				null, null, null, null, null);
		
		List<Integer> daysHaveFavoriteWords = new ArrayList<Integer>();
		
		try {
			while (cursor.moveToNext()) {
				
				int mDayIndex = cursor.getColumnIndex(UserStatisticHelper.DAY);
				
				int mDay = cursor.getInt(mDayIndex);
				
				daysHaveFavoriteWords.add(mDay);
				
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
		}
		return daysHaveFavoriteWords;
	}
	
	private boolean findDate(int day, int month, int year) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		String[] columns = {UserStatisticHelper.DAY, UserStatisticHelper.MONTH, UserStatisticHelper.YEAR};
		String[] selectionArgs = {String.valueOf(day), String.valueOf(month), String.valueOf(year)};
		
		// DESC and ASC
		cursor = db.query(	UserStatisticHelper.TABLE_NAME, columns, 
				UserStatisticHelper.DAY + " =? AND " + UserStatisticHelper.MONTH + " =? AND " + UserStatisticHelper.YEAR + " =?", 
									selectionArgs, null, null, null, null);
		
		if (cursor.moveToNext()) {
			if (cursor != null) {
				cursor.close();
			}
			if (db.isOpen()) {
				db.close();
			}
			mHelper.close();
			return true;
		}
		
		if (cursor != null) {
			cursor.close();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return false;
	}
	
	public int upgradeData(	int day, int month, int year, 
			int total_num_of_view_word_detail, 
			int total_num_of_favorite_word,
			int total_num_of_adding_favorite_word,
			int total_num_of_removing_favorite_word,
			int total_num_of_hearing_voice_of_word) {
		mHelper.openDataBase();
		
		db = mHelper.getWritableDatabase();
		
		contentValues = new ContentValues();
		contentValues.put(UserStatisticHelper.DAY, day);
		contentValues.put(UserStatisticHelper.MONTH, month);
		contentValues.put(UserStatisticHelper.YEAR, year);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_VIEW_WORD_DETAIL, total_num_of_view_word_detail);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_FAVORITE_WORD, total_num_of_favorite_word);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_ADDING_FAVORITE_WORD, total_num_of_adding_favorite_word);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_REMOVING_FAVORITE_WORD, total_num_of_removing_favorite_word);
		contentValues.put(UserStatisticHelper.TOTAL_NUM_OF_HEARING_VOICE_OF_WORD, total_num_of_hearing_voice_of_word);
		
		String[] whereArgs = {String.valueOf(day), String.valueOf(month), String.valueOf(year)};
		
		int count = db.update(UserStatisticHelper.TABLE_NAME, contentValues, 
				UserStatisticHelper.DAY + " = ? AND " + UserStatisticHelper.MONTH + " = ? AND " + UserStatisticHelper.YEAR + " = ?", whereArgs);
		
		if (contentValues != null) {
			contentValues.clear();
		}
		if (db.isOpen()) {
			db.close();
		}
		mHelper.close();
		return count;
	}
}
