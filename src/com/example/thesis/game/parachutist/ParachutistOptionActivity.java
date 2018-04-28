package com.example.thesis.game.parachutist;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.game.activity.ChooseGamesActivity;
import com.example.thesis.game.activity.GameIntroductionActivity;
import com.example.thesis.global.GlobalNumber;
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

public class ParachutistOptionActivity extends Activity implements OnClickListener {

	private ImageButton btnParachutistStart;
	private ImageButton btnParachutistIntroduction;
	private ImageButton btnParachutistMenu;
	
	private ImageButton btnParachutistEasy;
	private ImageButton btnParachutistNormal;
	private ImageButton btnParachutistHard;
	private ImageButton btnParachutistExpert;
	
	private MediaPlayer mp;
	
	private List<Word> selectedWords;
	private LinearLayout layoutParachutistGameOption;
	private LinearLayout layoutOption;
	private LinearLayout layoutOptionMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parachutist_option);
		
		if (savedInstanceState != null) {
			selectedWords = savedInstanceState.getParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE);
		} else {
			// Fetching data from a parcelable object passed from MainActivity
	        selectedWords = getIntent().getParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE);
		}
		
		// Set ID
		layoutParachutistGameOption = (LinearLayout) findViewById(R.id.layoutParachutistGameOption);
		layoutOption = (LinearLayout) findViewById(R.id.layoutOption);
		layoutOptionMode = (LinearLayout) findViewById(R.id.layoutOptionMode);
		
		btnParachutistStart = (ImageButton) findViewById(R.id.btnParachutistStart);
		btnParachutistIntroduction = (ImageButton) findViewById(R.id.btnParachutistIntroduction);
		btnParachutistMenu = (ImageButton) findViewById(R.id.btnParachutistMenu);
		
		btnParachutistEasy = (ImageButton) findViewById(R.id.btnParachutistEasy);
		btnParachutistNormal = (ImageButton) findViewById(R.id.btnParachutistNormal);
		btnParachutistHard = (ImageButton) findViewById(R.id.btnParachutistHard);
		btnParachutistExpert = (ImageButton) findViewById(R.id.btnParachutistExpert);
		
		// Set on click listener
		btnParachutistStart.setOnClickListener(this);
		btnParachutistIntroduction.setOnClickListener(this);
		btnParachutistMenu.setOnClickListener(this);
		
		btnParachutistEasy.setOnClickListener(this);
		btnParachutistNormal.setOnClickListener(this);
		btnParachutistHard.setOnClickListener(this);
		btnParachutistExpert.setOnClickListener(this);
	}

	@Override 
	protected void onResume() {
		super.onResume();
		if (mp != null) {
			mp.release();
		}
		mp = MediaPlayer.create(this, R.raw.background_game_parachutist);
		mp.start();
		mp.setLooping(true);
		
		layoutParachutistGameOption.setBackgroundResource(R.drawable.background_option_parachutist_pencil);
		
		btnParachutistStart.setBackgroundResource(R.drawable.button_game_start);
		btnParachutistIntroduction.setBackgroundResource(R.drawable.button_game_intro);
		btnParachutistMenu.setBackgroundResource(R.drawable.button_game_menu);
		
		btnParachutistEasy.setBackgroundResource(R.drawable.button_game_easy);
		btnParachutistNormal.setBackgroundResource(R.drawable.button_game_normal);
		btnParachutistHard.setBackgroundResource(R.drawable.button_game_hard);
		btnParachutistExpert.setBackgroundResource(R.drawable.button_game_expert);
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnParachutistStart:
			{
				layoutOption.setVisibility(View.GONE);
				layoutOptionMode.setVisibility(View.VISIBLE);
			}
			break;
			case R.id.btnParachutistIntroduction:
			{
				Intent intent = new Intent(ParachutistOptionActivity.this, GameIntroductionActivity.class);
				intent.putExtra(GlobalString.GAME_NAME, "Parachutist");
				startActivity(intent);
			}
			break;
			case R.id.btnParachutistMenu:
			{
				onBackPressed();
			}
			break;
			case R.id.btnParachutistEasy:
			{
				Intent intent = new Intent(ParachutistOptionActivity.this, ParachutistGameActivity.class);
				intent.putExtra(GlobalString.GAME_MODE, GlobalNumber.EASY);
				intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
				startActivity(intent);
			}
			break;
			case R.id.btnParachutistNormal:
			{
				Intent intent = new Intent(ParachutistOptionActivity.this, ParachutistGameActivity.class);
				intent.putExtra(GlobalString.GAME_MODE, GlobalNumber.NORMAL);
				intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
				startActivity(intent);
			}
			break;
			case R.id.btnParachutistHard:
			{
				Intent intent = new Intent(ParachutistOptionActivity.this, ParachutistGameActivity.class);
				intent.putExtra(GlobalString.GAME_MODE, GlobalNumber.HARD);
				intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
				startActivity(intent);
			}
			break;
			case R.id.btnParachutistExpert:
			{
				Intent intent = new Intent(ParachutistOptionActivity.this, ParachutistGameActivity.class);
				intent.putExtra(GlobalString.GAME_MODE, GlobalNumber.EXPERT);
				intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
				startActivity(intent);
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
		layoutParachutistGameOption.setBackgroundResource(android.R.color.transparent);
		
		btnParachutistStart.setBackgroundResource(android.R.color.transparent);
		btnParachutistIntroduction.setBackgroundResource(android.R.color.transparent);
		btnParachutistMenu.setBackgroundResource(android.R.color.transparent);
		
		btnParachutistEasy.setBackgroundResource(android.R.color.transparent);
		btnParachutistNormal.setBackgroundResource(android.R.color.transparent);
		btnParachutistHard.setBackgroundResource(android.R.color.transparent);
		btnParachutistExpert.setBackgroundResource(android.R.color.transparent);
		
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
		if (layoutOptionMode.getVisibility() == View.VISIBLE) {
			layoutOptionMode.setVisibility(View.GONE);
			layoutOption.setVisibility(View.VISIBLE);
		} else {
			Intent intent = new Intent(ParachutistOptionActivity.this, ChooseGamesActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
			startActivity(intent);
		}
	}
}
