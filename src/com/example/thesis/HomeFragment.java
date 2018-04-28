package com.example.thesis;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.thesis.R;
import com.example.thesis.activity.application.WordDetailActivity;
import com.example.thesis.database.adapter.DictionaryDatabaseAdapter;
import com.example.thesis.endlesslistview.WordsEndlessListview;
import com.example.thesis.endlesslistview.WordsEndlessListview.EndlessListener;
import com.example.thesis.global.GlobalNumber;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.message.Message;
import com.example.thesis.object.Word;
import com.example.thesis.object.adapter.TypeAdapter;
import com.example.thesis.object.adapter.WordAdapter;
import edu.sfsu.cs.orange.ocr.CaptureActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements EndlessListener, OnClickListener,
		OnItemClickListener, OnItemSelectedListener {

	private static String word;
	private static String type;
	private static int wordPosition;
	private static int totalNumberOfWords;
	
	
	private static List<Word> words;
	private static List<Word> subWords;
	private static List<String> types;

	private static String[] typesTemp;
	
	private static WordsEndlessListview wordList;
	private static Spinner spinnerHomeWordType;

	// Adapter
	private static TypeAdapter typeAdapter;
	private static WordAdapter wordAdapter;

	// database attributes
	private static DictionaryDatabaseAdapter dictionaryDBAdapter;

	// View attribute
	private static TextView txtHomeWordNotFound;

	private static EditText etHomeWord;

	private static ImageButton btnHomeRemove;
	private static ImageButton btnCaptureImage;
	private static ImageButton btnCaptureVoice;

	// Constructor
	public HomeFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);

		// Initial
		wordPosition = 0;
		totalNumberOfWords = 0;
		
		word = "";
		
		words = new ArrayList<Word>();
		subWords = new ArrayList<Word>();
		types = new ArrayList<String>();
		
		// create database
		final GlobalVariable globalVariable = (GlobalVariable) getActivity()
				.getApplicationContext();
		dictionaryDBAdapter = globalVariable.getDictionaryDBAdapter();

		// Set ID
		wordList = (WordsEndlessListview) rootView.findViewById(R.id.listWords);
		wordList.setLoadingView(R.layout.loading_words);
		wordList.setListener(this);
		
		txtHomeWordNotFound = (TextView) rootView.findViewById(R.id.txtHomeWordNotFound);

		etHomeWord = (EditText) rootView.findViewById(R.id.etHomeWord);

		btnHomeRemove = (ImageButton) rootView.findViewById(R.id.btnHomeRemove);
		btnCaptureVoice = (ImageButton) rootView.findViewById(R.id.btnCaptureVoice);
		btnCaptureImage = (ImageButton) rootView.findViewById(R.id.btnCaptureImage);

		spinnerHomeWordType = (Spinner) rootView.findViewById(R.id.spinnerHomeWordType);

		// Set edit text watcher
		editTextWatcher();
		
		// Set type
		typesTemp = getResources().getStringArray(R.array.word_types);
		
		for (int i = 0; i < typesTemp.length; ++i) {
			types.add(typesTemp[i]);
		}
		
		// Spinner
		typeAdapter = new TypeAdapter(getActivity().getApplicationContext(), R.layout.row_single_type, types);

		// Set Adapter
		spinnerHomeWordType.setAdapter(typeAdapter);
		
		// Set on click listener
		btnHomeRemove.setOnClickListener(this);
		btnCaptureVoice.setOnClickListener(this);
		btnCaptureImage.setOnClickListener(this);

		// Set on item click listener
		wordList.setOnItemClickListener(this);

		// Set on item selected listener
		spinnerHomeWordType.setOnItemSelectedListener(this);

		return rootView;
	}
	
	private void loadWords() {
		wordPosition = 0;
		totalNumberOfWords = 100;
		
		word = etHomeWord.getText().toString().toLowerCase();
		
		if (word.contains("'")) {
			Message.message(getActivity().getApplicationContext(), "Invalid Word");
			return;
		}

		// Set words
		subWords.clear();
		words.clear();
		
		subWords = createSubWords();
		
		words.addAll(subWords);

		if (words.size() == 0) {
			txtHomeWordNotFound.setVisibility(View.VISIBLE);
			wordList.setVisibility(View.GONE);
			
			if (wordAdapter != null) {
				wordAdapter.notifyDataSetChanged();
			}
			
			
		} else {
			txtHomeWordNotFound.setVisibility(View.GONE);
			wordList.setVisibility(View.VISIBLE);

			if (wordAdapter != null) {
				wordAdapter.clear();
			}
			wordAdapter = new WordAdapter(getActivity().getApplicationContext(), subWords);
			
			wordList.setTotalNumberOfWords(totalNumberOfWords);
			wordList.setWordAdapter(wordAdapter);
		}
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
		return dictionaryDBAdapter.getAlphabeticalDataFromTo(word, type, wordPosition, wordPosition + GlobalNumber.WORD_PER_REQUEST);
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
	
	private void editTextWatcher() {
		etHomeWord.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				loadWords();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (etHomeWord.getText().toString().length() == 0) {
					btnHomeRemove.setVisibility(View.INVISIBLE);
				} else {
					btnHomeRemove.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnHomeRemove: {
			etHomeWord.setText("");
		}
			break;
		case R.id.btnCaptureVoice: {
			promptSpeechInput();
		}
			break;
		case R.id.btnCaptureImage: {
			Intent intent = new Intent(getActivity(), CaptureActivity.class);
			startActivityForResult(intent,
					GlobalNumber.REQUEST_CODE_HOME_CAPTURE_ACTIVITY);
		}

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
			Toast.makeText(getActivity().getApplicationContext(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();

		bundle.putString(GlobalString.TAG_ID, words.get(position).getId());
		bundle.putString(GlobalString.TAG_WORD, words.get(position).getWord());
		bundle.putString(GlobalString.TAG_PRONUNCIATION, words.get(position)
				.getPronunciation());
		bundle.putString(GlobalString.TAG_TYPE, words.get(position).getType());
		bundle.putString(GlobalString.TAG_DEFINITION, words.get(position)
				.getDefinition());

		Intent intent = new Intent(getActivity().getApplicationContext(), WordDetailActivity.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtras(bundle);

		startActivity(intent);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		loadWords();
	}
	
	@Override 
	public void onPause() {
		super.onPause();
		if (words != null) {
			words.clear();
		}
		if (wordAdapter != null) {
			wordAdapter.clear();
		}
		
	};

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		type = types.get(position);

		loadWords();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == GlobalNumber.REQUEST_CODE_HOME_CAPTURE_ACTIVITY) {
			if (resultCode == GlobalNumber.RESULT_OK) {
				// Write your code if there's a result

				Bundle bundle = data.getExtras();
				String resultText = bundle.getString(GlobalString.CAPTURE_TEXT);

				etHomeWord.setText(resultText);

				loadWords();
			}
			if (resultCode == GlobalNumber.RESULT_CANCEL) {
				// Write your code if there's no result
			}
		}
		if (requestCode == GlobalNumber.REQUEST_CODE_SPEECH_INPUT) {

			if (null != data) {
				ArrayList<String> result = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				
				etHomeWord.setText(result.get(0));
				
				loadWords();
			}
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		GlobalVariable.updateUserStatisticDatabase();
	}
}
