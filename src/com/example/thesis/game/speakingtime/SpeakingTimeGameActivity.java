package com.example.thesis.game.speakingtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.thesis.R;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.game.activity.NewRecordActivity;
import com.example.thesis.global.GlobalNumber;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.object.Word;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SpeakingTimeGameActivity extends Activity implements OnClickListener, TextToSpeech.OnInitListener {

	// Media
	private static MediaPlayer mp_yes;
	private static MediaPlayer mp_no;
	
	// Text to speech
	private TextToSpeech textToSpeech;
	
	// Attribute
	private static List<Word> selectedWords;
	private static List<Word> selectedWordsTemp;
	
	private static int timer;
	private static int score;
	private static int wordPosition;
	
	// View
	private static TextView txtSpeakingTimeGameTime;
	private static TextView txtSpeakingTimeGameWord;
	private static TextView txtSpeakingTimeGamePronunciation;
	
	private static ImageView imgSpeakingTimeBackward;
	private static ImageView imgSpeakingTimeForward;
	private static ImageView imgSpeakingTimeGameMic;
	private static ImageView imgSpeakingTimeGameSpeaker;
	
	// AsyncTask
	private static ShowTime showTime;
	
	// Database
	private static FavoriteDatabaseAdapter favoriteDBAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speaking_time_game);
		
		// Initial
		if (textToSpeech != null) {
			textToSpeech = null;
		}
		textToSpeech = new TextToSpeech(getApplicationContext(), this);
		
		// Database
		final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
		favoriteDBAdapter = globalVariable.getFavoriteDBAdapter();
		
		// Get bundle
		if (savedInstanceState != null) {
			selectedWords = savedInstanceState.getParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE);
		} else {
			selectedWords = getIntent().getParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE);
		}
		
		// Set ID
		txtSpeakingTimeGameTime = (TextView) findViewById(R.id.txtSpeakingTimeGameTime);
		txtSpeakingTimeGameWord = (TextView) findViewById(R.id.txtSpeakingTimeGameWord);
		txtSpeakingTimeGamePronunciation = (TextView) findViewById(R.id.txtSpeakingTimeGamePronunciation);
		
		imgSpeakingTimeBackward = (ImageView) findViewById(R.id.imgSpeakingTimeBackward);
		imgSpeakingTimeForward = (ImageView) findViewById(R.id.imgSpeakingTimeForward);
		imgSpeakingTimeGameMic = (ImageView) findViewById(R.id.imgSpeakingTimeGameMic);
		imgSpeakingTimeGameSpeaker = (ImageView) findViewById(R.id.imgSpeakingTimeGameSpeaker);
		
		// Set on click listener
		imgSpeakingTimeBackward.setOnClickListener(this);
		imgSpeakingTimeForward.setOnClickListener(this);
		imgSpeakingTimeGameMic.setOnClickListener(this);
		imgSpeakingTimeGameSpeaker.setOnClickListener(this);
	}
	
	@Override 
	protected void onStart() {
		super.onStart();
		// Initial
		selectedWordsTemp = new ArrayList<Word>();
		selectedWordsTemp.addAll(selectedWords);
		timer = selectedWordsTemp.size() * GlobalNumber.TIME_PER_WORD_ST;
		score = 0;
		wordPosition = 0;
		
		// Set image
		imgSpeakingTimeBackward.setImageResource(R.drawable.titlebar_backward);
		imgSpeakingTimeForward.setImageResource(R.drawable.titlebar_forward);
		imgSpeakingTimeGameMic.setImageResource(R.drawable.ic_microphone);
		imgSpeakingTimeGameSpeaker.setImageResource(R.drawable.ic_speaker);
		
		// Set text
		if (selectedWordsTemp != null && selectedWordsTemp.size() != 0) {
			txtSpeakingTimeGameWord.setText(selectedWordsTemp.get(wordPosition).getWord());
			txtSpeakingTimeGamePronunciation.setText(selectedWordsTemp.get(wordPosition).getPronunciation());
		}
		
		// Media
		if (mp_yes != null) {
			mp_yes.release();
		}
		if (mp_no != null) {
			mp_no.release();
		}
		
		mp_yes = MediaPlayer.create(this, R.raw.says_yes);
		mp_no = MediaPlayer.create(this, R.raw.says_no);
		
		// AsyncTask
		if (showTime != null) {
			if (!showTime.isCancelled()) {
				showTime.cancel(true);
			}
		}
		showTime = new ShowTime();
		showTime.execute();
	};
	
	@Override 
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
	};
	
	@Override 
	protected void onStop() {
		super.onStop();
		// Media
		if (null != mp_yes) {
			mp_yes.release();
		}
		if (null != mp_no) {
			mp_no.release();
		}
		
		// AsyncTask
		if (showTime != null) {
			if (!showTime.isCancelled()) {
				showTime.cancel(true);
			}
		}
		
		// Database
		favoriteDBAdapter.upgradeWords(selectedWords);
		
		// Image
		imgSpeakingTimeBackward.setImageResource(android.R.color.transparent);
		imgSpeakingTimeForward.setImageResource(android.R.color.transparent);
		imgSpeakingTimeGameMic.setImageResource(android.R.color.transparent);
		imgSpeakingTimeGameSpeaker.setImageResource(android.R.color.transparent);
		
		// set game reset
		gameReset();
	};
	
	@Override 
	protected void onDestroy() {
		super.onDestroy();
		// Release
		if (selectedWords != null && ! selectedWords.isEmpty()) {
			selectedWords.clear();
		}
		if (selectedWordsTemp != null && selectedWordsTemp.isEmpty()) {
			selectedWordsTemp.clear();
		}
		
		// Don't forget to shutdown!
		if (textToSpeech != null) {
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
	};
	
	private class ShowTime extends AsyncTask<Void, Integer, Void> {
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
			txtSpeakingTimeGameTime.setText(String.valueOf(timer));
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			gameOver();
		}
	}
	
	private void gameReset() {
		score = timer;
		timer = 0;
	}
	
	private void gameOver() {
		Intent intent = new Intent(SpeakingTimeGameActivity.this, NewRecordActivity.class);
		intent.putExtra(GlobalString.GAME_NAME, "Speaking Time");
		intent.putExtra(GlobalString.GAME_SCORE, score);
		intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.imgSpeakingTimeBackward:
			{
				// Set text
				if (wordPosition > 0) {
					--wordPosition;
					txtSpeakingTimeGameWord.setText(selectedWordsTemp.get(wordPosition).getWord());
					txtSpeakingTimeGamePronunciation.setText(selectedWordsTemp.get(wordPosition).getPronunciation());
				}
			}
			break;
			case R.id.imgSpeakingTimeForward:
			{
				if (wordPosition < selectedWordsTemp.size() - 1) {
					++wordPosition;
					txtSpeakingTimeGameWord.setText(selectedWordsTemp.get(wordPosition).getWord());
					txtSpeakingTimeGamePronunciation.setText(selectedWordsTemp.get(wordPosition).getPronunciation());
				}
			}
			break;
			case R.id.imgSpeakingTimeGameMic:
			{
				promptSpeechInput();
			}
			break;
			case R.id.imgSpeakingTimeGameSpeaker:
			{
				speakOut();
			}
			break;
			default:
				break;
		}
	}
	
	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, GlobalNumber.REQUEST_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(this,
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub

		if (status == TextToSpeech.SUCCESS) {

			int result = textToSpeech.setLanguage(Locale.US);

			// tts.setPitch(5); // set pitch level

			// tts.setSpeechRate(2); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				imgSpeakingTimeGameSpeaker.setEnabled(true);
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}

	private void speakOut() {

		String mWord = txtSpeakingTimeGameWord.getText().toString();

		if (mWord.contains("/")) {
			int end = mWord.indexOf("/");
			mWord = mWord.substring(0, end);
		}
		
		textToSpeech.speak(mWord, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == GlobalNumber.REQUEST_CODE_SPEECH_INPUT) {

			if (null != data) {
				// Do something
				ArrayList<String> result = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				String speakingWord = result.get(0);
				
				if (selectedWordsTemp.get(wordPosition).getWord().equalsIgnoreCase(speakingWord)) {
					mp_yes.start();
					selectedWords.get(wordPosition).increaseNumberOfGuessingWrongWord(1);
					
					selectedWordsTemp.remove(wordPosition);
					if (wordPosition > 0) {
						--wordPosition;
					}
					
					if (selectedWordsTemp.size() > 0) {
						txtSpeakingTimeGameWord.setText(selectedWordsTemp.get(wordPosition).getWord());
						txtSpeakingTimeGamePronunciation.setText(selectedWordsTemp.get(wordPosition).getPronunciation());
					} else {
						gameReset();
					}
					
				} else {
					mp_no.start();
					selectedWords.get(wordPosition).decreaseNumberOfGuessingWrongWord();
				}
				
				
				
			}
		}
	}
}
