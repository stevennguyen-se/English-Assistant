package com.example.thesis;

import java.util.ArrayList;
import java.util.List;
import com.example.thesis.R;
import com.example.thesis.activity.application.FavoritesCalanderActivity;
import com.example.thesis.activity.application.WordDetailActivity;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.endlesslistview.FavoriteWordsEndlessListview;
import com.example.thesis.endlesslistview.FavoriteWordsEndlessListview.EndlessListener;
import com.example.thesis.global.GlobalNumber;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.message.Message;
import com.example.thesis.object.Word;
import com.example.thesis.object.adapter.FavoriteWordAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoritesFragment extends Fragment implements EndlessListener, OnClickListener {
	
	private static int wordPosition;
	private static int totalNumberOfWords;
	
	private static String word;
	// context
	private static Context context;
	
	// extends ListView
	private static FavoriteWordsEndlessListview wordList;

	// extends ArrayAdapter<UserFollow>
	private static FavoriteWordAdapter favoriteWordAdapter;

	// database attributes
	private static FavoriteDatabaseAdapter favoriteDBAdapter;

	// List
	private static List<Word> words;
	private static List<Word> subWords;
	private static List<Word> selectedWords;
	
	// View attribute
	private static EditText etFavoriteWord;

	private static ImageView imgFavoriteRemove;
	private static ImageView imgFavoriteRecycle;
	private static ImageView imgFavoriteCalander;
	
	public FavoritesFragment() {
		wordPosition = 0;
		totalNumberOfWords = 0;
		
		word = "";
		
		words = new ArrayList<Word>();
		subWords = new ArrayList<Word>();
		selectedWords = new ArrayList<Word>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        
		context = getActivity().getApplicationContext();
		
		// create database
		final GlobalVariable globalVariable = (GlobalVariable) getActivity().getApplicationContext();
		favoriteDBAdapter = globalVariable.getFavoriteDBAdapter();
		
		// Set ID
		wordList = (FavoriteWordsEndlessListview) rootView.findViewById(R.id.listFavoriteWords);
		wordList.setLoadingView(R.layout.loading_words);
		wordList.setListener(this);
		
		etFavoriteWord = (EditText) rootView.findViewById(R.id.etFavoriteWord);
		
		imgFavoriteRemove = (ImageView) rootView.findViewById(R.id.imgFavoriteRemove);
		imgFavoriteRecycle = (ImageView) rootView.findViewById(R.id.imgFavoriteRecycle);
		imgFavoriteCalander = (ImageView) rootView.findViewById(R.id.imgFavoriteCalander);
		
		// Set on item click listener
		wordList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();

				bundle.putString(GlobalString.TAG_ID, words.get(position).getId());
				bundle.putString(GlobalString.TAG_WORD, words.get(position).getWord());
				bundle.putString(GlobalString.TAG_PRONUNCIATION, words.get(position).getPronunciation());
				bundle.putString(GlobalString.TAG_TYPE, words.get(position).getType());
				bundle.putString(GlobalString.TAG_DEFINITION, words.get(position).getDefinition());

				Intent intent = new Intent(getActivity().getApplicationContext(), WordDetailActivity.class);

				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				intent.putExtras(bundle);

				startActivity(intent);
			}
		});
		
		imgFavoriteRemove.setOnClickListener(this);
		imgFavoriteRecycle.setOnClickListener(this);
		imgFavoriteCalander.setOnClickListener(this);
		
		
        return rootView;
	}
	
	@Override 
	public void onResume() {
		super.onResume();
		// Set TextWatcher
		// Because the text is not change, onTextChanged function is not called
		editTextWatcher();
		
		reloadFavoriteList();
	};
	
	@Override 
	public void onPause() {
		super.onPause();
		
		if (words != null) {
			words.clear();
		}
		if (subWords != null) {
			subWords.clear();
		}
		if (selectedWords != null) {
			selectedWords.clear();
		}
		if (favoriteWordAdapter != null && !favoriteWordAdapter.isEmpty()) {
			favoriteWordAdapter.clear();
		}
	};
	
	private void editTextWatcher() {
		etFavoriteWord.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				word = etFavoriteWord.getText().toString();
				
				reloadFavoriteList();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (etFavoriteWord.getText().toString().length() == 0) {
					imgFavoriteRemove.setVisibility(View.INVISIBLE);
				} else {
					imgFavoriteRemove.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
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
		favoriteWordAdapter = new FavoriteWordAdapter(context, subWords);
		
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
			case R.id.imgFavoriteRecycle:
			{
				try {
					selectedWords = favoriteWordAdapter.getSelectedWord();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				openAlert(v);
			
			}
			break;
			case R.id.imgFavoriteRemove:
			{
				etFavoriteWord.setText("");
			}
			break;
			case R.id.imgFavoriteCalander:
			{
				Intent intent = new Intent(getActivity(), FavoritesCalanderActivity.class);
				startActivity(intent);
			}
			break;
			default:
				break;
		}
	}
	@SuppressLint("InflateParams")
	private void openAlert(View v) {

		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_custom_titile, null);
		TextView title = (TextView) dialogView.findViewById(R.id.myTitle);

		title.setText("ALERT");
		builder.setCustomTitle(dialogView);
		
		if (selectedWords.size() == 0) {
			builder.setMessage("Are you sure you want to remove all favorite words");
		} else {
			builder.setMessage("Are you sure you want to remove " + selectedWords.size() + " words");
		}
		

		builder.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (selectedWords.size() == 0) {
							boolean isSuccess = favoriteDBAdapter.deletedAll();
							if (isSuccess) {
								Message.message(context,
										"Remove all successful");
								reloadFavoriteList();
							} else {
								Message.message(context, "Cannot remove all");
								reloadFavoriteList();
							}
						} else {
							boolean isSuccess = favoriteDBAdapter
									.deleteWords(selectedWords);

							if (isSuccess) {
								Message.message(context,
										"Remove selected words successful");
								reloadFavoriteList();
							} else {
								Message.message(context,
										"Cannot remove all selected words");
								reloadFavoriteList();
							}
						}
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		// AlertDialog alert = builder.create();
		// alert.show();
		AlertDialog dialog = builder.show();
		// Change the title divider
		Resources res = getResources();
		int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
		View titleDivider = dialog.findViewById(titleDividerId);
		titleDivider.setBackgroundColor(res.getColor(R.color.line));
		Button a = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		if (a != null) {
			a.setTextSize(20);
			a.setTextColor(getResources().getColor(R.color.white));
			a.setBackgroundColor(getResources().getColor(R.color.red));
		}
		Button b = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
		if (b != null) {
			b.setTextSize(20);
			b.setTextColor(getResources().getColor(R.color.white));
			b.setBackgroundColor(getResources().getColor(R.color.middle_line));
		}

	}
}
