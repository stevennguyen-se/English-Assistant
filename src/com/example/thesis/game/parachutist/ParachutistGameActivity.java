package com.example.thesis.game.parachutist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.thesis.R;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.game.activity.NewRecordActivity;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.handler.StringHandler;
import com.example.thesis.object.Word;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ParachutistGameActivity extends Activity implements OnClickListener{

	private static MediaPlayer mp;
	
	private static MediaPlayer mp_yes;
	private static MediaPlayer mp_no;
	
	// Attribute
	private static String word;
	private static List<Word> selectedWords;
	private static int score;
	private static int countHeart;
	
	private static int countWord;
	private static int maxWord;
	private static int countLetter;
	private static int countLetterCorrectClick;
	private static int maxLetter;
	private static boolean isCompletedWord;
	
	private static int countTab1;
	private static int countTab2;
	private static int countTab3;
	private static int countTab4;
	private static int countTab5;
	
	private static Random rand;
	
	// Layout
	private static RelativeLayout relativeLayoutSoldier1;
	private static RelativeLayout relativeLayoutSoldier2;
	private static RelativeLayout relativeLayoutSoldier3;
	private static RelativeLayout relativeLayoutSoldier4;
	private static RelativeLayout relativeLayoutSoldier5;
	
	private static TextView txtCorrectWord;
	private static TextView txtIncorrectWord;
	private static TextView txtTime;
	private static TextView txtScore;
	
	private static List<TextView> txtLetters;
	
	private static TextView txtLetter1;
	private static TextView txtLetter2;
	private static TextView txtLetter3;
	private static TextView txtLetter4;
	private static TextView txtLetter5;
	
	private static ImageView imgSoldier1;
	private static ImageView imgSoldier2;
	private static ImageView imgSoldier3;
	private static ImageView imgSoldier4;
	private static ImageView imgSoldier5;
	
	private static ImageView imgHeart1;
	private static ImageView imgHeart2;
	private static ImageView imgHeart3;
	private static ImageView imgHeart4;
	private static ImageView imgHeart5;
	
	private static ImageView imgParachutistGameWave11;
	private static ImageView imgParachutistGameWave12;
	
	private static ImageView imgParachutistGameShark;
	private static ImageView imgParachutistGamePlane;
	
	// Animation
	private static TranslateAnimation moveToptoBottom;
	
	private static TranslateAnimation moveWave11;
	private static TranslateAnimation moveWave12;
	
	private static TranslateAnimation moveShark;
	private static TranslateAnimation movePlane;
	
	// Timer
	private static int fallTime;
	private static int totalTime;
	private static int timer;
	
	// AsyncTask
	private static FallDown falldown;
	
	// Database
	private static FavoriteDatabaseAdapter favoriteDBAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parachutist_game);
		
		// Database
		final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
		favoriteDBAdapter = globalVariable.getFavoriteDBAdapter();
		
		// get bundle
		if (savedInstanceState != null) {
			selectedWords = savedInstanceState.getParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE);
			fallTime = savedInstanceState.getInt(GlobalString.GAME_MODE);
		} else {
			// Fetching data from a parcelable object passed from MainActivity
	        selectedWords = getIntent().getParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE);
	        fallTime = getIntent().getIntExtra(GlobalString.GAME_MODE, 8000);
		}
		
		// Set ID
		relativeLayoutSoldier1 = (RelativeLayout) findViewById(R.id.linearLayoutSoldier1);
		relativeLayoutSoldier2 = (RelativeLayout) findViewById(R.id.linearLayoutSoldier2);
		relativeLayoutSoldier3 = (RelativeLayout) findViewById(R.id.linearLayoutSoldier3);
		relativeLayoutSoldier4 = (RelativeLayout) findViewById(R.id.linearLayoutSoldier4);
		relativeLayoutSoldier5 = (RelativeLayout) findViewById(R.id.linearLayoutSoldier5);
		
		txtCorrectWord = (TextView) findViewById(R.id.txtCorrectWord);
		txtIncorrectWord = (TextView) findViewById(R.id.txtIncorrectWord);
		txtTime = (TextView) findViewById(R.id.txtTime);
		txtScore = (TextView) findViewById(R.id.txtScore);;
		
		txtLetter1 = (TextView) findViewById(R.id.txtLetter1);
		txtLetter2 = (TextView) findViewById(R.id.txtLetter2);
		txtLetter3 = (TextView) findViewById(R.id.txtLetter3);
		txtLetter4 = (TextView) findViewById(R.id.txtLetter4);
		txtLetter5 = (TextView) findViewById(R.id.txtLetter5);
		
		
		imgSoldier1 = (ImageView) findViewById(R.id.imgSoldier1);
		imgSoldier2 = (ImageView) findViewById(R.id.imgSoldier2);
		imgSoldier3 = (ImageView) findViewById(R.id.imgSoldier3);
		imgSoldier4 = (ImageView) findViewById(R.id.imgSoldier4);
		imgSoldier5 = (ImageView) findViewById(R.id.imgSoldier5);
		
		imgHeart1 = (ImageView) findViewById(R.id.imgHeart1);
		imgHeart2 = (ImageView) findViewById(R.id.imgHeart2);
		imgHeart3 = (ImageView) findViewById(R.id.imgHeart3);
		imgHeart4 = (ImageView) findViewById(R.id.imgHeart4);
		imgHeart5 = (ImageView) findViewById(R.id.imgHeart5);
		
		imgParachutistGameWave11 = (ImageView) findViewById(R.id.imgParachutistGameWave11);
		imgParachutistGameWave12 = (ImageView) findViewById(R.id.imgParachutistGameWave12);
		
		imgParachutistGameShark = (ImageView) findViewById(R.id.imgParachutistGameShark);
		imgParachutistGamePlane = (ImageView) findViewById(R.id.imgParachutistGamePlane);
		
		// Set on click listener
		txtLetter1.setOnClickListener(this);
		txtLetter2.setOnClickListener(this);
		txtLetter3.setOnClickListener(this);
		txtLetter4.setOnClickListener(this);
		txtLetter5.setOnClickListener(this);
		
		imgSoldier1.setOnClickListener(this);
		imgSoldier2.setOnClickListener(this);
		imgSoldier3.setOnClickListener(this);
		imgSoldier4.setOnClickListener(this);
		imgSoldier5.setOnClickListener(this);
		
		relativeLayoutSoldier1.setOnClickListener(this);
		relativeLayoutSoldier2.setOnClickListener(this);
		relativeLayoutSoldier3.setOnClickListener(this);
		relativeLayoutSoldier4.setOnClickListener(this);
		relativeLayoutSoldier5.setOnClickListener(this);
	}
	
	@Override 
	protected void onStart() {
		super.onStart();
		// Media
		if (mp != null) {
			mp.release();
		}
		if (mp_yes != null) {
			mp_yes.release();
		}
		if (mp_no != null) {
			mp_no.release();
		}
		
		mp = MediaPlayer.create(this, R.raw.background_game_parachutist);
		mp.setLooping(true);
		
		mp_yes = MediaPlayer.create(this, R.raw.says_yes);
		mp_no = MediaPlayer.create(this, R.raw.says_no);
		
		// Random
		rand = new Random();
		
		// Set time
		totalTime = StringHandler.getTimeForSelectedWords(selectedWords, 5, fallTime);
		// create move
			// parachute
		moveToptoBottom = new TranslateAnimation(0, 0, -500, 1000);
		moveToptoBottom.setDuration(fallTime);
		moveToptoBottom.setFillAfter(true);
		moveToptoBottom.setRepeatCount(Animation.INFINITE);
			// wave
		moveWave11 = new TranslateAnimation(0, 1730, 0, 0);
		moveWave11.setDuration(totalTime*1000);
		moveWave11.setFillAfter(true);
		moveWave11.setRepeatCount(Animation.INFINITE);
		
		moveWave12 = new TranslateAnimation(-1730, 0, 0, 0);
		moveWave12.setDuration(totalTime*1000);
		moveWave12.setFillAfter(true);
		moveWave12.setRepeatCount(Animation.INFINITE);
		
			// shark
		moveShark = new TranslateAnimation(-500, 4100, 0, 0);
		moveShark.setDuration(totalTime*2000);
		moveShark.setFillAfter(true);
		moveShark.setRepeatCount(Animation.INFINITE);
		
			// plane
		movePlane = new TranslateAnimation(2000, -6000, 0, -200);
		movePlane.setDuration(totalTime*2000);
		movePlane.setFillAfter(true);
		movePlane.setRepeatCount(Animation.INFINITE);
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// Letter list
		setLettersView();
		
		mp.start();
		
		// Initial
		score = 0;
		countHeart = 0;
		// count number of used word
		countWord = 0;
		// count number of used letter
		countLetter = 0;
		// count number of correct letter selected by user
		countLetterCorrectClick = 0;
		// total word of selected words
		maxWord = selectedWords.size();
		// total letter of selected word
		maxLetter = selectedWords.get(0).getWord().length();
		// check for completed a word
		isCompletedWord = true;
		
		countTab1 = 0;
		countTab2 = 0;
		countTab3 = 0;
		countTab4 = 0;
		countTab5 = 0;
		
		// Game Option
		timer = totalTime;
		
		imgParachutistGameWave11.startAnimation(moveWave11);
		imgParachutistGameWave12.startAnimation(moveWave12);
		
		txtLetter1.startAnimation(moveToptoBottom);
		txtLetter2.startAnimation(moveToptoBottom);
		txtLetter3.startAnimation(moveToptoBottom);
		txtLetter4.startAnimation(moveToptoBottom);
		txtLetter5.startAnimation(moveToptoBottom);
		
		imgSoldier1.startAnimation(moveToptoBottom);
		imgSoldier2.startAnimation(moveToptoBottom);
		imgSoldier3.startAnimation(moveToptoBottom);
		imgSoldier4.startAnimation(moveToptoBottom);
		imgSoldier5.startAnimation(moveToptoBottom);
		
		imgParachutistGameShark.startAnimation(moveShark);
		imgParachutistGamePlane.startAnimation(movePlane);
		
		// Animation
		if (falldown != null) {
			falldown.cancel(true);
		}
		falldown = new FallDown();
		falldown.execute();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
		outState.putInt(GlobalString.GAME_MODE, fallTime);
	};
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		// Media
		super.onPause();
		if (null != mp) {
			mp.release();
		}
		if (null != mp_yes) {
			mp_yes.release();
		}
		if (null != mp_no) {
			mp_no.release();
		}
		
		// Animation
		if (falldown != null) {
			if (!falldown.isCancelled()) {
				falldown.cancel(true);
			}
		}
		
		// Database
		favoriteDBAdapter.upgradeWords(selectedWords);
		
		// ResetGame
		gameReset();
	}
	
	@Override 
	protected void onDestroy() {
		super.onDestroy();
		// Release
		if (selectedWords != null && ! selectedWords.isEmpty()) {
			selectedWords.clear();
		}
	};
	
	private void setLettersView() {
		if (txtLetters == null) {
			txtLetters = new ArrayList<TextView>();
		} else {
			txtLetters.clear();
		}
		
		txtLetters.add(txtLetter1);
		txtLetters.add(txtLetter2);
		txtLetters.add(txtLetter3);
		txtLetters.add(txtLetter4);
		txtLetters.add(txtLetter5);
	}
	
	private void resetCountWord() {
		if (countWord > maxWord) {
			// get 1 word for playing
			countWord = 0;
		} 
	}
	
	private void setNewWord() {
		if (isCompletedWord == true) {
			// get word
			word = selectedWords.get(countWord).getWord();
			// increase count to get the new word later
			++countWord;
			// set word in text view
			txtCorrectWord.setText("");
			txtIncorrectWord.setText(word);
			// get length of the word
			maxLetter = word.length();
			// set count letter correct click
			countLetterCorrectClick = 0;
			// reset count letter
			countLetter = 0;
			// reset completed is false
			isCompletedWord = false;
		}
	}

	private void setLetterToLettersView() {
		
		int positionBox = 0;
		int positionchar = 0;
		
		// choose 1 of 5 box to put letter first
		// choose 1 of 4 box to put letter second
		// choose 1 of 3 box to put letter third
		// ...
		countLetter = countLetterCorrectClick;
		for (int i = 5; i > 0; --i) {
			positionBox = rand.nextInt(i);
			
			// if there is a letter
			if (countLetter < maxLetter)
			{
				txtLetters.get(positionBox).setText(String.valueOf(word.charAt(countLetter)));
				// increase count to get new letter later
				++countLetter;
			} else {
				positionchar = rand.nextInt(122 - 97 + 1) + 97;
				txtLetters.get(positionBox).setText(String.valueOf(((char) positionchar)));
			}
			// remove the random box
			txtLetters.remove(positionBox);
			
		}
	}
	
	private void setEndWord() {
		if (countLetter >= maxLetter)
		{
			isCompletedWord = true;
		}
	}
	
	private void set10SecondGame() {
		setLettersView();
		
		resetCountWord();
		
		setNewWord();
		
		setLetterToLettersView();
		
		setEndWord();
	} 
	
	private void setHeart() {
		switch (countHeart)
		{
		case 0:
		{
			imgHeart5.setImageResource(R.drawable.ic_heart_empty);
			++countHeart;
		}
		break;
		case 1:
		{
			imgHeart4.setImageResource(R.drawable.ic_heart_empty);
			++countHeart;
		}
		break;
		case 2:
		{
			imgHeart3.setImageResource(R.drawable.ic_heart_empty);
			++countHeart;
		}
		break;
		case 3:
		{
			imgHeart2.setImageResource(R.drawable.ic_heart_empty);
			++countHeart;
		}
		break;
		case 4:
		{
			imgHeart1.setImageResource(R.drawable.ic_heart_empty);
			
			gameOver();
		}
		break;
		default:
			break;
		}
	}
	
	private boolean isCorrectChoice(String mChoice) {
		if (countLetterCorrectClick < maxLetter) {
			if (String.valueOf(word.charAt(countLetterCorrectClick)).equalsIgnoreCase(mChoice)) {
				String correct = txtCorrectWord.getText().toString();
				String incorrect = txtIncorrectWord.getText().toString();
				
				txtCorrectWord.setText(correct + mChoice);
				if (incorrect.length() > 1) {
					txtIncorrectWord.setText(incorrect.substring(1));
				} else {
					txtIncorrectWord.setText("");
				}
				
				++countLetterCorrectClick;
				selectedWords.get(countWord - 1).decreaseNumberOfGuessingWrongWord();
				return true;
			} else {
				selectedWords.get(countWord - 1).increaseNumberOfGuessingWrongWord(maxLetter - countLetterCorrectClick);
			}
		}
		return false;
	}
	
	private void gameReset() {
		timer = 0;
		
		txtScore.setText("0");
		
		txtLetter1.clearAnimation();
		txtLetter2.clearAnimation();
		txtLetter3.clearAnimation();
		txtLetter4.clearAnimation();
		txtLetter5.clearAnimation();
		
		imgSoldier1.clearAnimation();
		imgSoldier2.clearAnimation();
		imgSoldier3.clearAnimation();
		imgSoldier4.clearAnimation();
		imgSoldier5.clearAnimation();
		
		txtLetter1.setVisibility(View.GONE);
		txtLetter2.setVisibility(View.GONE);
		txtLetter3.setVisibility(View.GONE);
		txtLetter4.setVisibility(View.GONE);
		txtLetter5.setVisibility(View.GONE);
		
		imgSoldier1.setVisibility(View.GONE);
		imgSoldier2.setVisibility(View.GONE);
		imgSoldier3.setVisibility(View.GONE);
		imgSoldier4.setVisibility(View.GONE);
		imgSoldier5.setVisibility(View.GONE);
	}
	
	private void gameOver() {
		gameReset();
		
		Intent intent = new Intent(ParachutistGameActivity.this, NewRecordActivity.class);
		
		Bundle bundle = new Bundle();
		bundle.putString(GlobalString.GAME_NAME, "Parachutist");
		bundle.putInt(GlobalString.GAME_SCORE, score);
		bundle.putParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if ((v.getId() == R.id.txtLetter1 ||
				v.getId() == R.id.imgSoldier1 ||
				v.getId() == R.id.linearLayoutSoldier1) && countTab1 == 1)
		{
			if (isCorrectChoice(txtLetter1.getText().toString())) {
				imgSoldier1.setBackgroundResource(R.drawable.ic_check_correct_pencil);
				++score;
				txtScore.setText(String.valueOf(score));
				mp_yes.start();
			} else {
				imgSoldier1.setBackgroundResource(R.drawable.ic_check_incorrect_pencil);
				setHeart();
				mp_no.start();
			}
			txtLetter1.setVisibility(View.INVISIBLE);
			txtLetter1.clearAnimation();
			countTab1 = 0;
			return;
		}
		if ((v.getId() == R.id.txtLetter2 ||
				v.getId() == R.id.imgSoldier2 ||
				v.getId() == R.id.linearLayoutSoldier2) && countTab2 == 1)
		{
			if (isCorrectChoice(txtLetter2.getText().toString())) {
				imgSoldier2.setBackgroundResource(R.drawable.ic_check_correct_pencil);
				++score;
				txtScore.setText(String.valueOf(score));
				mp_yes.start();
			} else {
				imgSoldier2.setBackgroundResource(R.drawable.ic_check_incorrect_pencil);
				setHeart();
				mp_no.start();
			}
			txtLetter2.setVisibility(View.INVISIBLE);
			txtLetter2.clearAnimation();
			countTab2 = 0;
			return;
		}
		if ((v.getId() == R.id.txtLetter3 ||
				v.getId() == R.id.imgSoldier3 ||
				v.getId() == R.id.linearLayoutSoldier3) && countTab3 == 1)
		{
			if (isCorrectChoice(txtLetter3.getText().toString())) {
				imgSoldier3.setBackgroundResource(R.drawable.ic_check_correct_pencil);
				++score;
				txtScore.setText(String.valueOf(score));
				mp_yes.start();
			} else {
				imgSoldier3.setBackgroundResource(R.drawable.ic_check_incorrect_pencil);
				setHeart();
				mp_no.start();
			}
			txtLetter3.setVisibility(View.INVISIBLE);
			txtLetter3.clearAnimation();
			countTab3 = 0;
			return;
		}
		if ((v.getId() == R.id.txtLetter4 ||
				v.getId() == R.id.imgSoldier4 ||
				v.getId() == R.id.linearLayoutSoldier4) && countTab4 == 1)
		{
			if (isCorrectChoice(txtLetter4.getText().toString())) {
				imgSoldier4.setBackgroundResource(R.drawable.ic_check_correct_pencil);
				++score;
				txtScore.setText(String.valueOf(score));
				mp_yes.start();
			} else {
				imgSoldier4.setBackgroundResource(R.drawable.ic_check_incorrect_pencil);
				setHeart();
				mp_no.start();
			}
			txtLetter4.setVisibility(View.INVISIBLE);
			txtLetter4.clearAnimation();
			countTab4 = 0;
			return;
		}
		if ((v.getId() == R.id.txtLetter5 ||
				v.getId() == R.id.imgSoldier5 ||
				v.getId() == R.id.linearLayoutSoldier5) && countTab5 == 1)
		{
			if (isCorrectChoice(txtLetter5.getText().toString())) {
				imgSoldier5.setBackgroundResource(R.drawable.ic_check_correct_pencil);
				++score;
				txtScore.setText(String.valueOf(score));
				mp_yes.start();
			} else {
				imgSoldier5.setBackgroundResource(R.drawable.ic_check_incorrect_pencil);
				setHeart();
				mp_no.start();
			}
			txtLetter5.setVisibility(View.INVISIBLE);
			txtLetter5.clearAnimation();
			countTab5 = 0;
			return;
		}
	}

	private class FallDown extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			while (timer >= 0)
			{
				publishProgress(timer);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				--timer;
			} 
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			txtTime.setText(String.valueOf(progress[0]));
			if (progress[0]%(fallTime/1000) == 0 && progress[0] != 0) {
				// Star animation
				countTab1 = 1;
				countTab2 = 1;
				countTab3 = 1;
				countTab4 = 1;
				countTab5 = 1;
				
				txtLetter1.setVisibility(View.VISIBLE);
				txtLetter2.setVisibility(View.VISIBLE);
				txtLetter3.setVisibility(View.VISIBLE);
				txtLetter4.setVisibility(View.VISIBLE);
				txtLetter5.setVisibility(View.VISIBLE);
				
				imgSoldier1.setBackgroundResource(R.drawable.ic_soldier_pencil);
				imgSoldier2.setBackgroundResource(R.drawable.ic_soldier_pencil);
				imgSoldier3.setBackgroundResource(R.drawable.ic_soldier_pencil);
				imgSoldier4.setBackgroundResource(R.drawable.ic_soldier_pencil);
				imgSoldier5.setBackgroundResource(R.drawable.ic_soldier_pencil);
				
				set10SecondGame(); 
				
				txtLetter1.startAnimation(moveToptoBottom);
				txtLetter2.startAnimation(moveToptoBottom);
				txtLetter3.startAnimation(moveToptoBottom);
				txtLetter4.startAnimation(moveToptoBottom);
				txtLetter5.startAnimation(moveToptoBottom);
			}
			
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			gameOver();
		}
	}
	
}
