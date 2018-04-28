package com.example.thesis.game.speakingtime;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.game.activity.ChooseGamesActivity;
import com.example.thesis.game.activity.GameIntroductionActivity;
import com.example.thesis.global.GlobalString;
import com.example.thesis.object.Word;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SpeakingTimeOptionActivity extends Activity implements OnClickListener{

	private static LinearLayout layoutSpeakingTimeGameOption;
	
	private static ImageButton btnSpeakingTimeStart;
	private static ImageButton btnSpeakingTimeIntroduction;
	private static ImageButton btnSpeakingTimeMenu;
	
	private static MediaPlayer mp;
	
	private static List<Word> selectedWords;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speaking_time_option);
		
		if (savedInstanceState != null) {
			selectedWords = savedInstanceState.getParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE);
		} else {
			// Fetching data from a parcelable object passed from MainActivity
	        selectedWords = getIntent().getParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE);
		}
		
		// Set ID
		layoutSpeakingTimeGameOption = (LinearLayout) findViewById(R.id.layoutSpeakingTimeGameOption);
		
		btnSpeakingTimeStart = (ImageButton) findViewById(R.id.btnSpeakingTimeStart);
		btnSpeakingTimeIntroduction = (ImageButton) findViewById(R.id.btnSpeakingTimeIntroduction);
		btnSpeakingTimeMenu = (ImageButton) findViewById(R.id.btnSpeakingTimeMenu);
		
		// Set on click listener
		btnSpeakingTimeStart.setOnClickListener(this);
		btnSpeakingTimeIntroduction.setOnClickListener(this);
		btnSpeakingTimeMenu.setOnClickListener(this);
	}
	
	@Override 
	protected void onResume() {
		super.onResume();
		if (mp != null) {
			mp.release();
		}
		mp = MediaPlayer.create(this, R.raw.background_game_speaking_time);
		mp.start();
		mp.setLooping(true);
		
		// Set image
		layoutSpeakingTimeGameOption.setBackgroundResource(R.drawable.background_option_speaking_time);
		
		btnSpeakingTimeStart.setBackgroundResource(R.drawable.button_game_start);
		btnSpeakingTimeIntroduction.setBackgroundResource(R.drawable.button_game_intro);
		btnSpeakingTimeMenu.setBackgroundResource(R.drawable.button_game_menu);
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnSpeakingTimeStart:
			{
				Intent intent = new Intent(SpeakingTimeOptionActivity.this, SpeakingTimeGameActivity.class);
				intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
				startActivity(intent);
			}
			break;
			case R.id.btnSpeakingTimeIntroduction:
			{
				Intent intent = new Intent(SpeakingTimeOptionActivity.this, GameIntroductionActivity.class);
				intent.putExtra(GlobalString.GAME_NAME, "Speaking Time");
				startActivity(intent);
			}
			break;
			case R.id.btnSpeakingTimeMenu:
			{
				onBackPressed();
			}
			break;
			default:
				break;
		}
	}
	
	@Override 
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
	};
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (null != mp) {
			mp.release();
		}
	}
	
	@Override 
	protected void onStop() {
		super.onStop();
		// Set image
		layoutSpeakingTimeGameOption.setBackgroundResource(android.R.color.transparent);
		
		btnSpeakingTimeStart.setBackgroundResource(android.R.color.transparent);
		btnSpeakingTimeIntroduction.setBackgroundResource(android.R.color.transparent);
		btnSpeakingTimeMenu.setBackgroundResource(android.R.color.transparent);
	};
	
	@Override 
	protected void onDestroy() {
		super.onDestroy();
		// Release
		if (selectedWords != null && ! selectedWords.isEmpty()) {
			selectedWords.clear();
		}
	};
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SpeakingTimeOptionActivity.this, ChooseGamesActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
		startActivity(intent);
	}
}
