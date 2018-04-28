package com.example.thesis.game.wordvsdefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.thesis.R;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.game.activity.NewRecordActivity;
import com.example.thesis.global.GlobalNumber;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.handler.StringHandler;
import com.example.thesis.object.Word;
import com.example.thesis.object.adapter.GameWDDefinitionAdapter;
import com.example.thesis.object.adapter.GameWDWordAdapter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class WDGameActivity extends Activity {

	private static List<Word> selectedWords;
	
	private static List<String> words;
	private static List<String> types;
	private static List<String> definitions;
	
	private static String word;
	private static String type;
	private static String definition;
	
	private static int size;
	
	private static int countWordClick;
	private static int countDefinitionClick;
	
	private static int positionWord;
	private static int positionDefinition;
	
	private static int score;
	
	// ListView
	private static ListView listWDGameWord;
	private static ListView listWDGameDefinition;
	
	private static TextView txtWDGameTime;
	
	private static GameWDWordAdapter gameWDWordAdapter;
	private static GameWDDefinitionAdapter gameWDDefinitionAdapter;
	
	// Media
	private static MediaPlayer mp;
	
	// Game Attribute
	private static int timer;
	
	// AsyncTask
	private static ShowTime showTime;
	
	// Database
	private static FavoriteDatabaseAdapter favoriteDBAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wdgame);
		
		// Database
		final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
		favoriteDBAdapter = globalVariable.getFavoriteDBAdapter();
		
		// get bundle
		if (savedInstanceState != null) {
			selectedWords = savedInstanceState.getParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE);
		} else {
			selectedWords = getIntent().getParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE);
		}
		
		// set size
		size = selectedWords.size();
		
		// Create word and definition list
		createWordAndDefinitionList();
		
		// Set ID
		listWDGameWord = (ListView) findViewById(R.id.listWDGameWord);
		listWDGameDefinition = (ListView) findViewById(R.id.listWDGameDefinition);

		txtWDGameTime = (TextView) findViewById(R.id.txtWDGameTime);
		
	}
	
	private void createWordAndDefinitionList() {
		// new word and definition
		words = new ArrayList<String>();
		types = new ArrayList<String>();
		definitions = new ArrayList<String>();
		
		// random position;
		int positionLeft = 0;
		int positionRight = 0;
		
		// new Random variable
		Random rand = new Random();
		
		// Create list in order
		for (int i = 0; i < size; ++i) {
			// add word and type list
			words.add(selectedWords.get(i).getWord());
			types.add(selectedWords.get(i).getType());
			// add definition list
			definitions.add(StringHandler.getAllDefinition(selectedWords.get(i).getDefinition()));
		}
		
		// Reorder the lists
		for (int middle = 1; middle < size; ++middle) {
			// add word list
			positionLeft = rand.nextInt(middle);
			positionRight = rand.nextInt(((size -1) - middle) + 1) + middle;
			words.add(positionLeft, words.get(positionRight));
			words.remove(positionRight + 1);
			types.add(positionLeft, types.get(positionRight));
			types.remove(positionRight + 1);
			
			// add definition list
			positionLeft = rand.nextInt(middle);
			positionRight = rand.nextInt(((size -1) - middle) + 1) + middle;
			definitions.add(positionLeft, definitions.get(positionRight));
			definitions.remove(positionRight + 1);
		}
	}
	
	private void setAdapter() {
		if (gameWDWordAdapter != null) {
			gameWDWordAdapter.clear();
		}
		if (gameWDDefinitionAdapter != null) {
			gameWDDefinitionAdapter.clear();
		}

		gameWDWordAdapter = new GameWDWordAdapter(getApplicationContext(), words, types);
		gameWDDefinitionAdapter = new GameWDDefinitionAdapter(getApplicationContext(), definitions);
		
		listWDGameWord.setAdapter(gameWDWordAdapter);
		listWDGameDefinition.setAdapter(gameWDDefinitionAdapter);
	}
	
	private boolean isMatchWordDefinition(String word, String type, String definition) {
		for (int i = 0; i < selectedWords.size(); ++i) {
			if (selectedWords.get(i).getWord().equalsIgnoreCase(word) && selectedWords.get(i).getType().equalsIgnoreCase(type)) {
				if (StringHandler.getAllDefinition(selectedWords.get(i).getDefinition()).equalsIgnoreCase(definition)) {
					selectedWords.get(i).decreaseNumberOfGuessingWrongWord();
					return true;
				}
				selectedWords.get(i).increaseNumberOfGuessingWrongWord(1);
				return false;
			}
		}
		return false;
	}
	
	private boolean compareWordAndDefinition() {
		if (isMatchWordDefinition(word, type, definition)) {
			words.remove(positionWord);
			types.remove(positionWord);
			definitions.remove(positionDefinition);
			if (words.size() == 0) {
				gameOver();
			}
			
			gameWDWordAdapter.setSelectedPosition(Integer.MAX_VALUE);
			gameWDDefinitionAdapter.setSelectedPosition(Integer.MAX_VALUE);
			
			gameWDWordAdapter.notifyDataSetChanged();
			gameWDDefinitionAdapter.notifyDataSetChanged();
			
			countWordClick = 0;
			countDefinitionClick = 0;
			return true;
		} else {
			gameWDWordAdapter.setSelectedPosition(Integer.MAX_VALUE);
			gameWDDefinitionAdapter.setSelectedPosition(Integer.MAX_VALUE);
			
			gameWDWordAdapter.notifyDataSetChanged();
			gameWDDefinitionAdapter.notifyDataSetChanged();
			
			countWordClick = 0;
			countDefinitionClick = 0;
			
			return false;
		}
	}
	
	private void setListviewItemCLick() {
		listWDGameWord.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				gameWDWordAdapter.setSelectedPosition(position);
				gameWDWordAdapter.notifyDataSetChanged();
				
				word = words.get(position);
				type = types.get(position);
				positionWord = position;
				
				if (countWordClick == 0) {
					++countWordClick;
					
					if (countDefinitionClick == 1) {
						// Compare word and definition
						if (!compareWordAndDefinition()) {
							timer -= GlobalNumber.TIME_PER_WORD_WD;
						}
					}
				}
				
				
			}
		});
		
		listWDGameDefinition.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				gameWDDefinitionAdapter.setSelectedPosition(position);
				gameWDDefinitionAdapter.notifyDataSetChanged();
				
				definition = definitions.get(position);
				positionDefinition = position;
				
				if (countDefinitionClick == 0) {
					++countDefinitionClick;
					
					if (countWordClick == 1) {
						// Compare word and definition
						if (!compareWordAndDefinition()) {
							timer -= GlobalNumber.TIME_PER_WORD_WD;
						}
					}
				}
			}
		});
	}
	
	@Override 
	protected void onResume() {
		super.onResume();
		if (mp != null) {
			mp.release();
		}
		mp = MediaPlayer.create(this, R.raw.background_game_word_vs_definition);
		mp.start();
		
		// Initial
		timer = selectedWords.size() * GlobalNumber.TIME_PER_WORD_WD;
		countWordClick = 0;
		countDefinitionClick = 0;
		
		// Set Text
		txtWDGameTime.setText(String.valueOf(timer));
		
		// AsyncTask
		if (showTime != null) {
			if (!showTime.isCancelled()) {
				showTime.cancel(true);
			}
		}
		showTime = new ShowTime();
		showTime.execute();
		
		// Set adapter
		setAdapter();
		
		// Set on item click listener
		setListviewItemCLick();
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
			txtWDGameTime.setText(String.valueOf(timer));
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
		gameReset();
		
		Intent intent = new Intent(WDGameActivity.this, NewRecordActivity.class);
		
		Bundle bundle = new Bundle();
		bundle.putString(GlobalString.GAME_NAME, "Word VS Definition");
		bundle.putInt(GlobalString.GAME_SCORE, score);
		bundle.putParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// Media
		if (mp != null) {
			mp.release();
		}
		
		// AsyncTask
		if (showTime != null) {
			showTime.cancel(true);
		}
		
		// Database
		favoriteDBAdapter.upgradeWords(selectedWords);
		
		// game reset
		gameReset();
	}
	
	@Override 
	protected void onDestroy() {
		super.onDestroy();
		// Release
		if (gameWDWordAdapter != null && !gameWDWordAdapter.isEmpty()) {
			gameWDWordAdapter.clear();
		}
		if (gameWDDefinitionAdapter != null && !gameWDDefinitionAdapter.isEmpty()) {
			gameWDDefinitionAdapter.clear();
		}
		
		if (selectedWords != null && ! selectedWords.isEmpty()) {
			selectedWords.clear();
		}
		if (words != null && words.isEmpty()) {
			words.clear();
		}
		if (types != null && types.isEmpty()) {
			types.clear();
		}
		if (definitions != null && definitions.isEmpty()) {
			definitions.clear();
		}
		if (word != null) {
			word = null;
		}
		if (type != null) {
			type = null;
		}
		if (definition != null) {
			definition = null;
		}
	};
}
