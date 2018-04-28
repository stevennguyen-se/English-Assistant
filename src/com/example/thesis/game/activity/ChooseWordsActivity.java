package com.example.thesis.game.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.ActionBarActivity;
import com.example.thesis.R;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.endlesslistview.FavoriteWordsEndlessListview;
import com.example.thesis.endlesslistview.FavoriteWordsEndlessListview.EndlessListener;
import com.example.thesis.global.GlobalNumber;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.message.Message;
import com.example.thesis.object.Word;
import com.example.thesis.object.adapter.FavoriteWordAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ChooseWordsActivity extends Activity implements EndlessListener, OnClickListener {

	private int wordPosition;
	private int totalNumberOfWords;
	
	private String word;
	
	// Endless list view
	private FavoriteWordsEndlessListview wordList;

	// extends ArrayAdapter<UserFollow>
	private FavoriteWordAdapter favoriteWordAdapter;

	// database attributes
	private FavoriteDatabaseAdapter favoriteDBAdapter;

	// List
	private List<Word> words;
	private List<Word> subWords;
	private List<Word> selectedWords;
	
	// View
	private ImageView imgChooseWordsBackward;
	private ImageView imgChooseWordsForward;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_words);
		
		// Initial
		wordPosition = 0;
		totalNumberOfWords = 0;
		
		word = "";
		
		words = new ArrayList<Word>();
		subWords = new ArrayList<Word>();
		selectedWords = new ArrayList<Word>();
		
		// Create database
		final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
		favoriteDBAdapter = globalVariable.getFavoriteDBAdapter();
		
		// Set ID
		wordList = (FavoriteWordsEndlessListview) findViewById(R.id.listChooseWordWords);
		
		imgChooseWordsBackward = (ImageView) findViewById(R.id.imgChooseWordsBackward);
		imgChooseWordsForward = (ImageView) findViewById(R.id.imgChooseWordsForward);
		
		// Set Endless listener
		wordList.setLoadingView(R.layout.loading_words);
	    wordList.setListener(this);
	    
	    // Set on click listener
	    imgChooseWordsBackward.setOnClickListener(this);
	    imgChooseWordsForward.setOnClickListener(this);
	}
	
	@Override 
	public void onResume() {
		super.onResume();
		reloadFavoriteList();
	};
	
	@Override 
	protected void onDestroy() {
		if (favoriteDBAdapter != null) {
			favoriteDBAdapter = null;
		}
		if (favoriteWordAdapter != null && !favoriteWordAdapter.isEmpty()) {
			favoriteWordAdapter.clear();
		}
		if (words != null && !words.isEmpty()) {
			words.clear();
		}
		if (subWords != null && !subWords.isEmpty()) {
			subWords.clear();
		}
		if (selectedWords != null && !selectedWords.isEmpty()) {
			selectedWords.clear();
		}
		super.onDestroy();
	};
	
	private void reloadFavoriteList () {
		
		// Set list view data
		wordPosition = 0;
		
		totalNumberOfWords = favoriteDBAdapter.getNumberOfRows(word);
		
		subWords.clear();
		words.clear();
		
		subWords = createSubWords();
		
		words.addAll(subWords);
		
		if (favoriteWordAdapter != null) {
			favoriteWordAdapter.clear();
		}
		favoriteWordAdapter = new FavoriteWordAdapter(getApplicationContext(), subWords);
		
		wordList.setTotalNumberOfWords(totalNumberOfWords);
		wordList.setFavoriteWordAdapter(favoriteWordAdapter);
	}
	
	private class WordLoader extends AsyncTask<Word, Void, List<Word>> {

		@Override
		protected List<Word> doInBackground(Word... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			// Create new sub user follower
			// return as a result
			return createSubWords();
		}
		
		@Override
		protected void onPostExecute(List<Word> result) {			
			super.onPostExecute(result);
			// get the result from doInbackground add in UserFollowersEndlessListview
			wordList.addNewData(result);
			words.addAll(result);
		}
		
	}
	
	// create USER_PER_REQUEST (20 users) each time
	private List<Word> createSubWords() {
		return favoriteDBAdapter.getWords(word, wordPosition, wordPosition + GlobalNumber.WORD_PER_REQUEST);
	}
	
	
	@Override
	public void loadData() {
		System.out.println("Load data");
		
		// update user position for the next create sub user follower
		wordPosition += GlobalNumber.WORD_PER_REQUEST;
		
		// We load more data here
		WordLoader fl = new WordLoader();
		fl.execute(new Word[]{});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.imgChooseWordsBackward:
			{
				onBackPressed();
			}
			break;
			case R.id.imgChooseWordsForward:
			{
				try {
					selectedWords = favoriteWordAdapter.getSelectedWord();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (selectedWords !=null && selectedWords.size() != 0) {
					Intent intent = new Intent(ChooseWordsActivity.this, ChooseGamesActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					
					intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) selectedWords);
					
					startActivity(intent);
				} else {
					Message.message(this, "Please Choose Some Words For Playing");
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
		Intent intent = new Intent(ChooseWordsActivity.this, ActionBarActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
