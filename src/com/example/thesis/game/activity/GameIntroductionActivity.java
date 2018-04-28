package com.example.thesis.game.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.fragment.FragmentGameIntro;
import com.example.thesis.global.GlobalString;
import com.example.thesis.viewpager.GameIntroViewPager;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class GameIntroductionActivity extends FragmentActivity implements OnClickListener {

	// Attribute
	private static String gameNames;
	
	private static MediaPlayer mp;
	private static List<Integer> gameIconIds;
	
	// View
	private static ImageView btnGameIntroOK;
	
	// ViewPager
	private static GameIntroViewPager viewpagerGameIntro;
	private static PageIndicator pageIndicator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_introduction);
		
		// Initial
		gameIconIds = new ArrayList<Integer>();
		
		// save instance state
		if (savedInstanceState != null) {
			gameNames = savedInstanceState.getString(GlobalString.GAME_NAME);
		} else {
			// Fetching data from a parcelable object passed from MainActivity
			gameNames = getIntent().getStringExtra(GlobalString.GAME_NAME);
		}
		
		// Set ID
		viewpagerGameIntro = (GameIntroViewPager) findViewById(R.id.viewpagerGameIntro);
		pageIndicator = (CirclePageIndicator) findViewById(R.id.indicatorGameIntro);
		btnGameIntroOK = (ImageView) findViewById(R.id.btnGameIntroOK);
		
		// Set on click listener
		btnGameIntroOK.setOnClickListener(this);
	}
	
	@Override 
	protected void onStart() {
		super.onStart();
		
		initial(gameNames);
		
	};
	
	@Override 
	protected void onResume() {
		super.onResume();
		
		// Set view pager adapter
		viewpagerGameIntro.setAdapter(new GamePagerAdapter(getSupportFragmentManager()));
		pageIndicator.setViewPager(viewpagerGameIntro);
		
		mp.start();
	};
	
	@Override 
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(GlobalString.GAME_NAME, gameNames);
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
	protected void onDestroy() {
		if (viewpagerGameIntro != null) {
			viewpagerGameIntro = null;
		}
		if (gameIconIds != null && !gameIconIds.isEmpty()) {
			gameIconIds.clear();
		}
		super.onDestroy();
	};
	
	private void initial(String gameName) {
		switch (gameName)
		{
			case "Parachutist":
			{
				gameIconIds.add(R.drawable.intro_parachutist_1);
				gameIconIds.add(R.drawable.intro_parachutist_2);
				gameIconIds.add(R.drawable.intro_parachutist_3);
				gameIconIds.add(R.drawable.intro_parachutist_4);
				gameIconIds.add(R.drawable.intro_parachutist_5);
				
				if (mp != null) {
					mp.release();
				}
				mp = MediaPlayer.create(getApplicationContext(), R.raw.background_game_parachutist);
			}
			break;
			case "Word VS Definition":
			{
				gameIconIds.add(R.drawable.intro_wd_1);
				gameIconIds.add(R.drawable.intro_wd_2);
				gameIconIds.add(R.drawable.intro_wd_3);
				
				if (mp != null) {
					mp.release();
				}
				mp = MediaPlayer.create(getApplicationContext(), R.raw.background_game_word_vs_definition);
			}
			break;
			case "Speaking Time":
			{
				gameIconIds.add(R.drawable.intro_speaking_game_1);
				gameIconIds.add(R.drawable.intro_speaking_game_2);
				gameIconIds.add(R.drawable.intro_speaking_game_3);
				gameIconIds.add(R.drawable.intro_speaking_game_4);
				
				if (mp != null) {
					mp.release();
				}
				mp = MediaPlayer.create(getApplicationContext(), R.raw.background_game_speaking_time);
			}
			break;
		}
	}
	
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

		for (int i = 0; i < gameIconIds.size(); ++i) {
			fList.add(new FragmentGameIntro(gameIconIds.get(i)));
		}
		return fList;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnGameIntroOK:
			{
				onBackPressed();
			}
			break;
			default:
				break;
		}
	}
}
