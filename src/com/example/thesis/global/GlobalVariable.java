package com.example.thesis.global;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.thesis.database.adapter.DictionaryDatabaseAdapter;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.database.adapter.GameDatabaseAdapter;
import com.example.thesis.database.adapter.IrregularVerbsDatabaseAdapter;
import com.example.thesis.database.adapter.UserStatisticDatabaseAdapter;
import com.example.thesis.object.UserStatistic;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class GlobalVariable extends Application {
	private static UserStatistic usersStatisticAttribute;
	
	private static DictionaryDatabaseAdapter dictionaryDBAdapter;
	private static FavoriteDatabaseAdapter favoriteDBAdapter;
	private static IrregularVerbsDatabaseAdapter irDBAdapter;
	private static UserStatisticDatabaseAdapter userStatisticDBAdapter;
	private static GameDatabaseAdapter gameDBAdapter;
	
	public GameDatabaseAdapter getGameDBAdapter() {
		return gameDBAdapter;
	}
	public void setGameDBAdapter(GameDatabaseAdapter gameDBAdapter) {
		GlobalVariable.gameDBAdapter = gameDBAdapter;
	}
	public UserStatistic getUsersStatisticAttribute() {
		return usersStatisticAttribute;
	}
	public void setUsersStatisticAttribute(
			UserStatistic usersStatisticAttribute) {
		GlobalVariable.usersStatisticAttribute = usersStatisticAttribute;
	}
	public UserStatisticDatabaseAdapter getUserStatisticDBAdapter() {
		return userStatisticDBAdapter;
	}
	public void setUserStatisticDBAdapter(
			UserStatisticDatabaseAdapter userStatisticDBAdapter) {
		GlobalVariable.userStatisticDBAdapter = userStatisticDBAdapter;
		
		Date date = new Date();
		
		String year = new SimpleDateFormat("yyyy").format(date);
		String month = new SimpleDateFormat("MM").format(date);
		String day = new SimpleDateFormat("dd").format(date);
		
		Log.v("Global Variable", "setUserStatisticDBAdapter begin");
		GlobalVariable.usersStatisticAttribute = GlobalVariable.userStatisticDBAdapter.getUserStatisticAttribute(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
		Log.v("Global Variable", "setUserStatisticDBAdapter end");
	}
	public FavoriteDatabaseAdapter getFavoriteDBAdapter() {
		return favoriteDBAdapter;
	}
	public void setFavoriteDBAdapter(FavoriteDatabaseAdapter favoriteDBAdapter) {
		GlobalVariable.favoriteDBAdapter = favoriteDBAdapter;
	}
	public IrregularVerbsDatabaseAdapter getIrDBAdapter() {
		return irDBAdapter;
	}
	public void setIrDBAdapter(IrregularVerbsDatabaseAdapter irDBAdapter) {
		GlobalVariable.irDBAdapter = irDBAdapter;
	}
	public DictionaryDatabaseAdapter getDictionaryDBAdapter() {
		return dictionaryDBAdapter;
	}
	public void setDictionaryDBAdapter(DictionaryDatabaseAdapter dictionaryDBAdapter) {
		GlobalVariable.dictionaryDBAdapter = dictionaryDBAdapter;
	}
	
	// Functions
	public static boolean updateUserStatisticDatabase() {
		Date date = new Date();
		
		String year = new SimpleDateFormat("yyyy").format(date);
		String month = new SimpleDateFormat("MM").format(date);
		String day = new SimpleDateFormat("dd").format(date);
		String days_of_week = new SimpleDateFormat("E").format(date);
		String weeks_of_year = new SimpleDateFormat("w").format(date);
		
		GlobalVariable.userStatisticDBAdapter.insertUserData(days_of_week, Integer.parseInt(weeks_of_year), Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year), 
												usersStatisticAttribute.getTotal_num_of_view_word_detail(), 
												usersStatisticAttribute.getTotal_num_of_favorite_word(),
												usersStatisticAttribute.getTotal_num_of_adding_favorite_word(), 
												usersStatisticAttribute.getTotal_num_of_removing_favorite_word(), 
												usersStatisticAttribute.getTotal_num_of_hearing_voice_of_word());
		return true;
	}
}
