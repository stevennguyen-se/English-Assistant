package com.example.thesis;

import com.example.thesis.R;
import com.example.thesis.database.adapter.DictionaryDatabaseAdapter;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.database.adapter.GameDatabaseAdapter;
import com.example.thesis.database.adapter.IrregularVerbsDatabaseAdapter;
import com.example.thesis.database.adapter.UserStatisticDatabaseAdapter;
import com.example.thesis.global.GlobalVariable;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	// Global Variable
	GlobalVariable globalVariable;
	
	// AsyncTask
	DatabaseInitial dbInitial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Initial Database
		if (dbInitial != null) {
			if (!dbInitial.isCancelled()) {
				dbInitial.cancel(true);
			}
		}
		dbInitial = new DatabaseInitial();
		dbInitial.execute();
		
		// View
		final String PREFS_NAME = "MyPrefsFile";

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		if (settings.getBoolean("my_first_time", true)) {
		    //the app is being launched for first time, do something        
		    Log.d("Comments", "First time");

		    // first time task
		    Intent intent = new Intent(MainActivity.this, FirstIntroductionActivity.class);
		    startActivity(intent);

		    // record the fact that the app has been started at least once
		    settings.edit().putBoolean("my_first_time", false).commit(); 
		} else {
			Intent intent = new Intent(MainActivity.this, ActionBarActivity.class);
		    startActivity(intent);
		}
	}
	
	private class DatabaseInitial extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			// Set global variable
			globalVariable = (GlobalVariable) getApplicationContext();

			globalVariable.setDictionaryDBAdapter(new DictionaryDatabaseAdapter(getApplicationContext()));
			globalVariable.setFavoriteDBAdapter(new FavoriteDatabaseAdapter(getApplicationContext()));
			globalVariable.setIrDBAdapter(new IrregularVerbsDatabaseAdapter(getApplicationContext()));
			globalVariable.setUserStatisticDBAdapter(new UserStatisticDatabaseAdapter(getApplicationContext()));
			globalVariable.setGameDBAdapter(new GameDatabaseAdapter(getApplicationContext()));
			
			return null;
		}
		
	}
}
