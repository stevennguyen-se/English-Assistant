package com.example.thesis.game.wordvsdefinition;

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

public class WDOptionActivity extends Activity implements OnClickListener{

	private static LinearLayout layoutWDGameOption;
	
	private static ImageButton btnWDStart;
	private static ImageButton btnWDIntroduction;
	private static ImageButton btnWDMenu;
	
	private static MediaPlayer mp;
	
	private static List<Word> selectedWords;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wdoption);
		
		if (savedInstanceState != null) {
			selectedWords = savedInstanceState.getParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE);
		} else {
			// Fetching data from a parcelable object passed from MainActivity
	        selectedWords = getIntent().getParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE);
		}
		
		// Set ID
		layoutWDGameOption = (LinearLayout) findViewById(R.id.layoutWDGameOption);
		
		btnWDStart = (ImageButton) findViewById(R.id.btnWDStart);
		btnWDIntroduction = (ImageButton) findViewById(R.id.btnWDIntroduction);
		btnWDMenu = (ImageButton) findViewById(R.id.btnWDMenu);
		
		btnWDStart.setOnClickListener(this);
		btnWDIntroduction.setOnClickListener(this);
		btnWDMenu.setOnClickListener(this);
	}

	@Override 
	protected void onResume() {
		super.onResume();
		if (mp != null) {
			mp.release();
		}
		mp = MediaPlayer.create(this, R.raw.background_game_word_vs_definition);
		mp.start();
		mp.setLooping(true);
		
		// Set image
		layoutWDGameOption.setBackgroundResource(R.drawable.background_option_wordvsdefinition);
		
		btnWDStart.setBackgroundResource(R.drawable.button_game_start);
		btnWDIntroduction.setBackgroundResource(R.drawable.button_game_intro);
		btnWDMenu.setBackgroundResource(R.drawable.button_game_menu);
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnWDStart:
			{
				Intent intent = new Intent(WDOptionActivity.this, WDGameActivity.class);
				intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
				startActivity(intent);
			}
			break;
			case R.id.btnWDIntroduction:
			{
				Intent intent = new Intent(WDOptionActivity.this, GameIntroductionActivity.class);
				intent.putExtra(GlobalString.GAME_NAME, "Word VS Definition");
				startActivity(intent);
			}
			break;
			case R.id.btnWDMenu:
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
		layoutWDGameOption.setBackgroundResource(android.R.color.transparent);
		
		btnWDStart.setBackgroundResource(android.R.color.transparent);
		btnWDIntroduction.setBackgroundResource(android.R.color.transparent);
		btnWDMenu.setBackgroundResource(android.R.color.transparent);
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
		Intent intent = new Intent(WDOptionActivity.this, ChooseGamesActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
		startActivity(intent);
	}
}
