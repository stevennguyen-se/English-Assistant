package com.example.thesis.game.activity;

import com.example.thesis.R;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class BeginGameActivity extends Activity {

	private MediaPlayer mp1;
	private MediaPlayer mp2;

	private int timer3k;
	private int timer2k;
	private int timer800;
	
	private ImageView imgGameGame;
	private ImageView imgGameLogoLeft;
	private ImageView imgGameLogoRight;

	// AsyncTask
	MySound mySound;
	ScreenWait screenWait;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_begin_game);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// Initial
		timer3k = 3000;
		timer2k = 2000;
		timer800 = 800;
		
		// Set Id
		imgGameGame = (ImageView) findViewById(R.id.imgGameGame);
		imgGameLogoLeft = (ImageView) findViewById(R.id.imgGameLogoLeft);
		imgGameLogoRight = (ImageView) findViewById(R.id.imgGameLogoRight);
		
	}
		
	@Override 
	protected void onResume() {
		super.onResume();
		
		imgGameGame.setImageResource(R.drawable.game);
		imgGameLogoLeft.setImageResource(R.drawable.logo_left);
		imgGameLogoRight.setImageResource(R.drawable.logo_right);
		
		imgGameGame.setVisibility(View.INVISIBLE);
		
		// Media
		if (mp1 != null) {
			mp1.release();
		}
		mp1 = MediaPlayer.create(this, R.raw.boom);
		
		if (mp2 != null) {
			mp2.release();
		}
		mp2 = MediaPlayer.create(this, R.raw.sword);
		
		// Animation
		TranslateAnimation logoLeft = new TranslateAnimation(-2000.0f, 0.0f,
				0.0f, 0.0f);
		logoLeft.setDuration(timer2k);
		logoLeft.setFillAfter(true);
		imgGameLogoLeft.startAnimation(logoLeft);
		
		TranslateAnimation logoRight = new TranslateAnimation(2000.0f, 0.0f,
				0.0f, 0.0f);
		logoRight.setDuration(timer2k);
		logoRight.setFillAfter(true);
		imgGameLogoRight.startAnimation(logoRight);
		
		// Thread
		if (mySound != null) {
			mySound.cancel(true);
		}
		
		mySound = new MySound();
		mySound.execute();
	};

	private class MySound extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				Thread.sleep(timer2k);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mp1.start();

			try {
				Thread.sleep(timer2k);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mp1.release();
			mp2.start();
			
			try {
				Thread.sleep(timer800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			imgGameGame.setVisibility(View.VISIBLE);
			imgGameGame.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_in_bottom));
			
			if (screenWait != null) {
				screenWait.cancel(true);
			}
			
			screenWait = new ScreenWait();
			screenWait.execute();
		}
	}

	private class ScreenWait extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(timer3k);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Intent intent = new Intent(getApplicationContext(), ChooseWordsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
		}
	}
	
	@Override 
	protected void onPause() {
		super.onPause();
		// Release Media Player
		if (null != mp1) {
			mp1.release();
		}
		if (null != mp2) {
			mp2.release();
		}
		
		// Cancel Thread
		if (mySound != null) {
			if (!mySound.isCancelled()) {
				mySound.cancel(true);
			}
		}
		if (screenWait != null) {
			if (!screenWait.isCancelled()){
				screenWait.cancel(true);
			}
		}
		
		imgGameGame.setImageResource(android.R.color.transparent);
		imgGameLogoLeft.setImageResource(android.R.color.transparent);
		imgGameLogoRight.setImageResource(android.R.color.transparent);
	};
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
}
