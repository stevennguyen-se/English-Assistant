package com.example.thesis.game.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.database.adapter.GameDatabaseAdapter;
import com.example.thesis.facebook.DialogError;
import com.example.thesis.facebook.Facebook;
import com.example.thesis.facebook.Facebook.DialogListener;
import com.example.thesis.facebook.FacebookError;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.object.Word;
//import com.facebook.android.DialogError;
//import com.facebook.android.Facebook;
//import com.facebook.android.FacebookError;
//import com.facebook.android.Facebook.DialogListener;





import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class NewRecordActivity extends Activity implements OnClickListener {

	// Facebook
	String APP_ID;

	Facebook fb;
	
	ImageView btnFacebookShare;
	
	// Save Login information
	SharedPreferences sp;
	
	// Attribute
	private static String currentGameName;
	private static int currentGameScore;
	
	private static String databaseGameName;
	private static int databaseGameScore;
	
	private static int countScore;
	
	private static List<Word> selectedWords;
	
	// Database
	private static GameDatabaseAdapter gameDBAdapter;
	
	// View
	private static TextView txtRecordNumber;
	private static TextView txtPlayerScore;
	private static TextView txtPlayerScoreNumber;
	
	private static ImageView imgBackgroundHighScore;
	private static ImageView imgBestScoreMedal;
	private static ImageView imgNewRecordNext;
	
	// AsyncTask
	private static ShowScore showScore;
	
	// Media
	private static MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_record);
		
		// Facebook
		APP_ID = getString(R.string.APP_ID);

		fb = new Facebook(APP_ID);
		
		// Shared Preferences
		sp = getPreferences(MODE_PRIVATE);
		String access_token = sp.getString("access_token", null);
		long expires = sp.getLong("access_expires", 0);
		
		if (access_token != null) {
			fb.setAccessToken(access_token);
		}
		if (expires != 0) {
			fb.setAccessExpires(expires);
		}
		
		// Get bundle
		if (savedInstanceState != null) {
			currentGameName = savedInstanceState.getString(GlobalString.GAME_NAME);
			currentGameScore = savedInstanceState.getInt(GlobalString.GAME_SCORE);
		} else {
			Bundle bundle = new Bundle();
			bundle = getIntent().getExtras();
			
			if (bundle != null) {
				currentGameName = bundle.getString(GlobalString.GAME_NAME);
				currentGameScore = bundle.getInt(GlobalString.GAME_SCORE);
				selectedWords = bundle.getParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE);
			} 
		}
		
		// Database
		final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
		gameDBAdapter = globalVariable.getGameDBAdapter();
		
		databaseGameName = currentGameName;
		databaseGameScore = gameDBAdapter.getGameScoreByName(databaseGameName);
		
		// Set ID
		txtRecordNumber = (TextView) findViewById(R.id.txtRecordNumber);
		txtPlayerScore = (TextView) findViewById(R.id.txtPlayerScore);
		txtPlayerScoreNumber = (TextView) findViewById(R.id.txtPlayerScoreNumber);
		
		imgBackgroundHighScore = (ImageView) findViewById(R.id.imgBackgroundHighScore);
		imgBestScoreMedal = (ImageView) findViewById(R.id.imgBestScoreMedal);
		imgNewRecordNext = (ImageView) findViewById(R.id.imgNewRecordNext);
		
		btnFacebookShare = (ImageView) findViewById(R.id.btnFacebookShare);
		
		// Set on click listener
		imgNewRecordNext.setOnClickListener(this);
		
		btnFacebookShare.setOnClickListener(this);
	}
	
	@Override 
	protected void onResume() {
		super.onResume();
		// initial
		countScore = 0;
		
		txtRecordNumber.setText(String.valueOf(databaseGameScore));
		txtPlayerScore.setText("Player Score");
		txtPlayerScoreNumber.setText("0");
		
		// Media
		if (mp!=null){
			mp.release();
		}
		mp = MediaPlayer.create(this, R.raw.says_yes);
		
		// Show Score
		if (showScore != null) {
			if (!showScore.isCancelled()) {
				showScore.cancel(true);
			}
		}
		showScore = new ShowScore();
		showScore.execute();
		
		// Set image
		imgBackgroundHighScore.setImageResource(R.drawable.background_highscore);
	};
	
	@Override 
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString(GlobalString.GAME_NAME, currentGameName);
		outState.putInt(GlobalString.GAME_SCORE, currentGameScore);
	};
	
	@Override 
	protected void onPause() {
		super.onPause();
		if (showScore != null) {
			if (!showScore.isCancelled()) {
				showScore.cancel(true);
			}
		}
		
		// Media
		if (mp!=null){
			mp.release();
		}
	};
	
	@Override 
	protected void onStop() {
		// Set image
		imgBackgroundHighScore.setImageResource(android.R.color.transparent);
		super.onStop();
	};
	
	@Override 
	protected void onDestroy() {
		if (selectedWords != null && !selectedWords.isEmpty()) {
			selectedWords.clear();
		}
		super.onDestroy();
	};
	
	private class ShowScore extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			while (countScore <= currentGameScore) {
				publishProgress(countScore);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				++countScore;
			}
			
			return null;
		}
		
		
		protected void onProgressUpdate(Integer... values) {
			txtPlayerScoreNumber.setText(String.valueOf(values[0]));
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (currentGameScore > databaseGameScore) {
				txtPlayerScore.setText(R.string.best_score);
				txtRecordNumber.setText(String.valueOf(currentGameScore));
				imgBestScoreMedal.setVisibility(View.VISIBLE);
				mp.start();
				gameDBAdapter.upgradeDataByName(currentGameName, currentGameScore);
			}
			imgNewRecordNext.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.imgNewRecordNext:
			{
				Intent intent = new Intent(NewRecordActivity.this, ChooseGamesActivity.class);
				intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
			break;
			case R.id.btnFacebookShare:
			{
				if (fb.isSessionValid()) {
					// logging in: close session
					// Let continue to share
					// Post
					post();
				} else {
					// not login yet:
					fb.authorize(NewRecordActivity.this, new DialogListener() {

						@Override
						public void onFacebookError(FacebookError arg0) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onError(DialogError arg0) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onComplete(Bundle arg0) {
							// TODO Auto-generated method stub
							Editor editor = sp.edit();
							editor.putString("access_token", fb.getAccessToken());
							editor.putLong("access_expires", fb.getAccessExpires());
							editor.commit();
							
							// Post
							post();
						}

						@Override
						public void onCancel() {
							// TODO Auto-generated method stub
						}
					});
				}
			}
			break;
			default:
				break;
		}
	}
	
	private void post() {
		// post
		Bundle params = new Bundle();
		params.putString("name", "I played " + currentGameName + " and got " + currentGameScore +" point(s). How about you?");
		params.putString("caption", getString(R.string.app_name));
		params.putString("description", convertSelectedWordsToString(selectedWords));
		params.putString("link", "https://www.dropbox.com/s/c05i7sryq0w3kca/Thesis.apk?dl=0");
		params.putString("picture", "https://photos-1.dropbox.com/t/2/AAAm86F2aNoZpJkVWDoLT8UCl2hFwfQsp5i2idxMQsUnZQ/12/405677077/png/32x32/1/_/1/2/ic_logo.png/EKfKsZ8DGLerAiABIAIoAg/0iIeKhxU2MzV8MaTVIW8mR4ozzOcNTLjn7Nr4hOUvkg?size=1280x960&size_mode=2");

	    
	    fb.dialog(NewRecordActivity.this, "feed", params, new DialogListener() {
			
			@Override
			public void onFacebookError(FacebookError arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(DialogError arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(Bundle arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private String convertSelectedWordsToString(List<Word> selectedWords) {
		if (selectedWords == null || selectedWords.isEmpty()) {
			return null;
		}
		
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append("+ ");
		for (int i = 0; i < selectedWords.size() - 1; ++i) {
			strBuffer.append(selectedWords.get(i).getWord() + ", ");
		}
		strBuffer.append(selectedWords.get(selectedWords.size() - 1).getWord() + ".");
		
		return strBuffer.toString();
	}
}
