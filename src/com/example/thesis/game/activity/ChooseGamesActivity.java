package com.example.thesis.game.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.database.adapter.GameDatabaseAdapter;
import com.example.thesis.fragment.FragmentGameIcon;
import com.example.thesis.game.parachutist.ParachutistOptionActivity;
import com.example.thesis.game.speakingtime.SpeakingTimeOptionActivity;
import com.example.thesis.game.wordvsdefinition.WDOptionActivity;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.message.Message;
import com.example.thesis.object.GameInfo;
import com.example.thesis.object.Word;
import com.example.thesis.viewpager.ChooseGameViewPager;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseGamesActivity extends FragmentActivity implements OnClickListener {

	private MediaPlayer mp;
	
	private static int[] gameIconIds = {
			R.drawable.frame_parachutist_pencil,
			R.drawable.frame_wd,
			R.drawable.frame_speaking_time};
	
	// View
	private TextView txtChooseGameGameName;
	private TextView txtChooseGameHighScore;
	
	private ImageView imgChooseGamesBackward;
	private ImageView imgChooseGamesForward;
	
	// List
	private List<Word> selectedWords;
	private List<GameInfo> gameInfo;
	
	// Database
	private GameDatabaseAdapter gameDBAdapter;
	
	// ViewPager
	private ChooseGameViewPager viewpagerGames;
	private PageIndicator pageIndicator;
	
	// ViewPager listener
	private PageListener pageListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_games);
		
		// Database
		final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
		gameDBAdapter = globalVariable.getGameDBAdapter();
		
		// Initial
		gameInfo = gameDBAdapter.getAllData();
		pageListener = new PageListener();
		
		if (savedInstanceState != null) {
			selectedWords = savedInstanceState.getParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE);
		} else {
			// Fetching data from a parcelable object passed from MainActivity
			selectedWords = getIntent().getParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE);
		}
		
		// Set ID
		txtChooseGameGameName = (TextView) findViewById(R.id.txtChooseGameGameName);
		txtChooseGameHighScore = (TextView) findViewById(R.id.txtChooseGameHighScore);
		imgChooseGamesBackward = (ImageView) findViewById(R.id.imgChooseGamesBackward);
		imgChooseGamesForward = (ImageView) findViewById(R.id.imgChooseGamesForward);
		
		viewpagerGames = (ChooseGameViewPager) findViewById(R.id.viewpagerGames);
		pageIndicator = (CirclePageIndicator) findViewById(R.id.indicatorChooseGames);
		
		// Set text
		if (gameInfo != null && gameInfo.size() != 0) {
			txtChooseGameGameName.setText(gameInfo.get(0).getName());
			txtChooseGameHighScore.setText(String.valueOf(gameInfo.get(0).getScore()));
		}
		
	    // Set on click listener 
		imgChooseGamesBackward.setOnClickListener(this);
		imgChooseGamesForward.setOnClickListener(this);
		
		// Set view pager adapter
		viewpagerGames.setAdapter(new GamePagerAdapter(getSupportFragmentManager()));
		
		// Set View Pager
		pageIndicator.setViewPager(viewpagerGames);
		pageIndicator.setOnPageChangeListener(pageListener);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mp != null) {
			mp.release();
		}
		mp = MediaPlayer.create(this, R.raw.background_music_choose_game);
		mp.start();
		mp.setLooping(true);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (null != mp) {
			mp.release();
		}
		super.onPause();
	}
	
	@Override 
	protected void onDestroy() {
		if (selectedWords != null && !selectedWords.isEmpty()) {
			selectedWords.clear();
		}
		if (gameInfo != null && !gameInfo.isEmpty()) {
			gameInfo.clear();
		}
		super.onDestroy();
	};
	
	// Month view pager adapter
	private class GamePagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments;

		public GamePagerAdapter(FragmentManager fm) {
			super(fm);
			this.fragments = getGameFragments();
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}

	private List<Fragment> getGameFragments() {

		List<Fragment> fList = new ArrayList<Fragment>();

		for (int i = 0; i < gameInfo.size() - 1; ++i) {
			fList.add(new FragmentGameIcon(gameIconIds[i]));
		}
		return fList;
	}
	
	private class PageListener extends SimpleOnPageChangeListener {
		public void onPageSelected(int position) {
			txtChooseGameGameName.setText(gameInfo.get(position).getName());
			txtChooseGameHighScore.setText(String.valueOf(gameInfo.get(position).getScore()));
		}
	}
	
	@Override 
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.imgChooseGamesBackward:
			{
				onBackPressed();
			}
			break;
			case R.id.imgChooseGamesForward:
			{
				String gameName = txtChooseGameGameName.getText().toString();
				
				switch (gameName)
				{
					case "Parachutist":
					{
						Intent intent = new Intent(ChooseGamesActivity.this, ParachutistOptionActivity.class);
						intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
						startActivity(intent);
					}
					break;
					case "Word VS Definition":
					{
						Intent intent = new Intent(ChooseGamesActivity.this, WDOptionActivity.class);
						intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
						startActivity(intent);
					}
					break;
					case "Speaking Time":
					{
						Intent intent = new Intent(ChooseGamesActivity.this, SpeakingTimeOptionActivity.class);
						intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
						startActivity(intent);
					}
					break;
					default:
					{
						Message.message(this, "PLease Choose A Game");
					}
					break;
				}
			}
			break;
			default:
				break;
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(ChooseGamesActivity.this, ChooseWordsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
