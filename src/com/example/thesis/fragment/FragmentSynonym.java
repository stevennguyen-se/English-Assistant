package com.example.thesis.fragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.thesis.R;
import com.example.thesis.activity.application.WordDetailActivity;
import com.example.thesis.activity.application.WordTypeActivity;
import com.example.thesis.database.adapter.DictionaryDatabaseAdapter;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.message.Message;
import com.example.thesis.object.Word;
import com.example.thesis.object.adapter.SynonymAdapter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragmentSynonym extends Fragment implements OnItemClickListener{

	private static String url = "http://www.oxforddictionaries.com/definition/english-thesaurus/";
	private String word;
	private String fullURL;
	
	// Web
	private Document doc;
	private Elements synonyms;
	
	private List<String> mSynonyms;
	// AsyncTask
	private ReadURL readURL;
	
	// View
	private static TextView txtFragmentSynonymPleaseWait;
	
	// List
	private static ListView listViewFragmentSynonymExamples;
	
	private SynonymAdapter synonymAdapter;
	
	// Word
	private String selectedWord;
	private List<Word> words;
	
	// Database
	private static DictionaryDatabaseAdapter dictionaryDBAdapter;
	
	public FragmentSynonym() {
		// Required empty public constructor
	}
	
	public FragmentSynonym(String word) {
		// Required empty public constructor
		this.word = word;
		this.fullURL = url + word;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_synonym, container, false);
		
		// Database
		final GlobalVariable globalVariable = (GlobalVariable) rootView.getContext().getApplicationContext();
		dictionaryDBAdapter = globalVariable.getDictionaryDBAdapter();
		
		listViewFragmentSynonymExamples = (ListView) rootView.findViewById(R.id.listViewFragmentSynonymExamples);
		txtFragmentSynonymPleaseWait = (TextView) rootView.findViewById(R.id.txtFragmentSynonymPleaseWait);
		
		txtFragmentSynonymPleaseWait.setText("Please Wait");
		
		// Set on item click listener
		listViewFragmentSynonymExamples.setOnItemClickListener(this);
		
		return rootView;
	}
	
	@Override 
	public void onStart() {
		super.onStart();
		if (readURL != null) {
			if (!readURL.isCancelled()) {
				readURL.cancel(true);
			}
		}
		readURL = new ReadURL((WordDetailActivity) getActivity());
		readURL.execute();
	};
	
	@Override
	public void onDestroy() {
		// Cancel AsyncTask
		if (readURL != null) {
			if (!readURL.isCancelled()) {
				readURL.cancel(true);
			}
		}
		
		// Release memory
		if (synonymAdapter != null) {
			synonymAdapter.clear();
		}
		if (synonyms != null && !synonyms.isEmpty()) {
			synonyms.clear();
		}
		if (mSynonyms != null && !mSynonyms.isEmpty()) {
			mSynonyms.clear();
		}
		if (doc != null) {
			doc = null;
		}
		if (word != null) {
			word = null;
		}
		if (selectedWord != null) {
			selectedWord = null;
		}
		super.onDestroy();
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		
		if (words != null && !words.isEmpty()) {
			words.clear();
		}
	}

	private class ReadURL extends AsyncTask<Void, Void, Void> {

		private WeakReference<WordDetailActivity> mRef;

        public ReadURL(WordDetailActivity activity) {
            mRef = new WeakReference<WordDetailActivity>(activity);
        }
        
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try {
				doc = Jsoup.connect(fullURL).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (doc != null) {
				synonyms = doc.select("a");
				
				if (synonyms != null && !synonyms.isEmpty()) {
					mSynonyms = new ArrayList<String>();
					
					for (int i = 0; i < synonyms.size(); ++i) {
						String example = (Jsoup.parse(synonyms.get(i).getElementsByClass("syn").html())).body().text();
						if (example != null && example.length() != 0) {
							mSynonyms.add(example);
						}
					}
				}
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			WordDetailActivity activity = mRef.get();
            if (activity == null) {
                // the activity reference was cleared, 
                // lets forget about it
            }
            else {
                // lets update the activity with the results
                // of the task
            }
			
			if (mSynonyms != null && !mSynonyms.isEmpty()) {
				txtFragmentSynonymPleaseWait.setText(R.string.oxford);
				
				if (synonymAdapter != null) {
					synonymAdapter.clear();
				}
				
				synonymAdapter = new SynonymAdapter(activity.getApplicationContext(), mSynonyms);
				
				listViewFragmentSynonymExamples.setAdapter(synonymAdapter);
			} else {
				txtFragmentSynonymPleaseWait.setText("Synonyms are not found");
			}
			
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		selectedWord = mSynonyms.get(position);
		
		words = dictionaryDBAdapter.getWord(selectedWord);
		
		if (words.size() == 0) {
			Message.message(getActivity(), "Word not found");
		} else {
			if (words.size() > 1) {
				Intent intent = new Intent(getActivity(), WordTypeActivity.class);
				intent.putParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) words);
				startActivity(intent);
			}
			
			if (words.size() == 1) {
				Bundle bundle = new Bundle();

				bundle.putString(GlobalString.TAG_ID, words.get(0).getId());
				bundle.putString(GlobalString.TAG_WORD, words.get(0).getWord());
				bundle.putString(GlobalString.TAG_PRONUNCIATION, words.get(0)
						.getPronunciation());
				bundle.putString(GlobalString.TAG_TYPE, words.get(0).getType());
				bundle.putString(GlobalString.TAG_DEFINITION, words.get(0)
						.getDefinition());

				Intent intent = new Intent(getActivity(), WordDetailActivity.class);
				intent.putExtras(bundle);

				startActivity(intent);
			}
		}
		
		
	}
}
