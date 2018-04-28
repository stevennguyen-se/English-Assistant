package com.example.thesis.activity.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.thesis.R;
import com.example.thesis.database.adapter.DictionaryDatabaseAdapter;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.fragment.FragmentTypeMajorExample;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.handler.StringHandler;
import com.example.thesis.message.Message;
import com.example.thesis.object.Major;
import com.example.thesis.object.UserStatistic;
import com.example.thesis.object.Word;
import com.example.thesis.object.adapter.MajorAdapter;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("Recycle")
public class WordDetailActivity extends FragmentActivity implements OnClickListener, TextToSpeech.OnInitListener{

	private Word word;
	private boolean isFavorite;
	
	private TextToSpeech textToSpeech;
	
	private static TextView txtWordDetailWord;
	private static TextView txtWordDetailPronunciation;
	
	private static ImageView imgWordDetailSpeaker;
	private static ImageView imgWordDetailFavorite;
	
	private static ImageButton btnWordDetailBackward;
	
	private static DictionaryDatabaseAdapter dictionaryDBAdapter;
	private static FavoriteDatabaseAdapter favoriteDBAdapter;
	
	private List<Major> majors;
	
	private MajorAdapter majorAdapter;
	
	// Fragment Attribute
	private FragmentManager manager;
	private FragmentTransaction transaction;
	
	// User statistic
	private static UserStatistic userStatisticAttribute;
	
	// AsyncTask
	private AddFragment addFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_detail);
		
		textToSpeech = new TextToSpeech(getApplicationContext(), this);
		
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		
		// Set database
		final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
		dictionaryDBAdapter = globalVariable.getDictionaryDBAdapter();
		favoriteDBAdapter = globalVariable.getFavoriteDBAdapter();
		userStatisticAttribute = globalVariable.getUsersStatisticAttribute();
		
		// Increase number of view word detail
		userStatisticAttribute.increase_total_num_of_view_word_detail();
		
		// get information
		word = new Word();
		
		Bundle bundle = getIntent().getExtras();
		
		word.setId(bundle.getString(GlobalString.TAG_ID));
		word.setWord(bundle.getString(GlobalString.TAG_WORD));
		word.setPronunciation(bundle.getString(GlobalString.TAG_PRONUNCIATION));
		word.setType(bundle.getString(GlobalString.TAG_TYPE));
		word.setDefinition(bundle.getString(GlobalString.TAG_DEFINITION));
		
		// Set ID
		txtWordDetailWord = (TextView) findViewById(R.id.txtWordDetailWord);
		txtWordDetailPronunciation = (TextView) findViewById(R.id.txtWordDetailPronunciation);
		imgWordDetailSpeaker = (ImageView) findViewById(R.id.imgWordDetailSpeaker);
		imgWordDetailFavorite = (ImageView) findViewById(R.id.imgWordDetailFavorite);
		btnWordDetailBackward = (ImageButton) findViewById(R.id.btnWordDetailBackward);
		
		
		// set text
		txtWordDetailWord.setText(word.getWord());
		txtWordDetailPronunciation.setText(word.getPronunciation());
		
		// set click listener
		imgWordDetailSpeaker.setOnClickListener(this);
		imgWordDetailFavorite.setOnClickListener(this);
		btnWordDetailBackward.setOnClickListener(this);
	}
	
	@Override 
	protected void onStart() {
		super.onStart();
		isFavorite = favoriteDBAdapter.findWord(word.getId(), word.getWord());
		
		// set images
		if (isFavorite == true) {
			imgWordDetailFavorite.setBackgroundResource(R.drawable.favorite_true);
		} else {
			imgWordDetailFavorite.setBackgroundResource(R.drawable.favorite_false);
		}
		
		// Add fragment
		if (addFragment != null) {
			addFragment.cancel(true);
		}
		addFragment = new AddFragment();
		addFragment.execute();
	};
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.imgWordDetailSpeaker:
			{
				// add speak function
				userStatisticAttribute.increase_total_num_of_hearing_voice_of_word();
				speakOut();
			}
			break;
			case R.id.imgWordDetailFavorite:
			{
				if (isFavorite) {
					imgWordDetailFavorite.setBackgroundResource(R.drawable.favorite_false);
					favoriteDBAdapter.deleteWord(word.getId(), word.getWord());
					isFavorite = false;
					userStatisticAttribute.decrease_total_num_of_favorite_word();
					userStatisticAttribute.increase_total_num_of_removing_favorite_word();
					Message.message(v.getContext(), "Successfullly Removed The Word To Favorite");
				} else {
					imgWordDetailFavorite.setBackgroundResource(R.drawable.favorite_true);
					
					favoriteDBAdapter.insertData(word.getId(), word.getWord(), word.getPronunciation(), word.getType(), word.getDefinition());
					isFavorite = true;
					userStatisticAttribute.increase_total_num_of_favorite_word();
					userStatisticAttribute.increase_total_num_of_adding_favorite_word();
					Message.message(v.getContext(), "Successfullly Added The Word To Favorite");
				}
			}
			break;
			case R.id.btnWordDetailBackward:
			{
				onBackPressed();
			}
			break;
			default:
				break;
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
				imgWordDetailSpeaker.setEnabled(true);
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}

	private void speakOut() {

		String mWord = word.getWord().toString();

		if (mWord.contains("/")) {
			int end = mWord.indexOf("/");
			mWord = mWord.substring(0, end);
		}
		
		textToSpeech.speak(mWord, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	private class AddFragment extends AsyncTask<Void, Void, Void>{
		protected Void doInBackground(Void... params) {
			majors = new ArrayList<Major>();
			majors = dictionaryDBAdapter.getMajorData(word.getWord());
			for (int i = 0; i < majors.size(); ++i) {
				majors.get(i).setDefinition(StringHandler.getAllDefinition(majors.get(i).getDefinition()));
			}
			
			majorAdapter = new MajorAdapter(getApplicationContext(), majors);
			
			return null;
	     }

	     protected void onPostExecute(Void result) {
	    	FragmentTypeMajorExample fWordMajorAndType = (FragmentTypeMajorExample) manager.findFragmentByTag(GlobalString.TAG_FRAGMENT_WORD_TYPE_AND_MAJOR);
			
			if (fWordMajorAndType == null)
			{
				fWordMajorAndType = new FragmentTypeMajorExample(word.getWord(), word.getType(), StringHandler.getAllDefinition(word.getDefinition()), majorAdapter);
				transaction.add(R.id.fragmentWordDetail, fWordMajorAndType, GlobalString.TAG_FRAGMENT_WORD_TYPE_AND_MAJOR);
				// send information by bundle
				transaction.commit();
			}
	     }
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		GlobalVariable.updateUserStatisticDatabase();
	}
	
	@Override
	public void onDestroy() {
		if (word != null) {
			word = null;
		}
		if (majors != null && !majors.isEmpty()) {
			majors.clear();
		}
		if (majorAdapter != null && !majorAdapter.isEmpty()) {
			majorAdapter.clear();
		}
		
		if (addFragment != null) {
			if (!addFragment.isCancelled()) {
				addFragment.cancel(true);
			}
		}
		
		// Don't forget to shutdown!
		if (textToSpeech != null) {
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
		super.onDestroy();
	}
}
