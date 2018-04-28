package com.example.thesis.fragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.thesis.R;
import com.example.thesis.activity.application.WordDetailActivity;
import com.example.thesis.object.adapter.ExampleAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class FragmentExamples extends Fragment implements OnItemClickListener, TextToSpeech.OnInitListener {

	private String url = "http://www.oxforddictionaries.com/definition/english/";
	private String word;
	private String fullURL;
	
	// Text to speech
	private TextToSpeech textToSpeech;
	
	// Web
	private Document doc;
	private Elements examples;
	
	private List<String> mExamples;
	// AsyncTask
	private ReadURL readURL;
	
	// View
	private static TextView txtFragmentExamplePleaseWait;
	
	// List
	private static ListView listViewFragmentExampleExamples;
	
	private ExampleAdapter exampleAdapter;
	
	public FragmentExamples() {
		// Required empty public constructor
	}
	
	public FragmentExamples(String word) {
		// Required empty public constructor
		this.word = word;
		this.fullURL = url + word;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_examples, container, false);
		
		// Set ID
		listViewFragmentExampleExamples = (ListView) rootView.findViewById(R.id.listViewFragmentExampleExamples);
		txtFragmentExamplePleaseWait = (TextView) rootView.findViewById(R.id.txtFragmentExamplePleaseWait);
		
		// Set text
		txtFragmentExamplePleaseWait.setText("Please Wait");
		
		// Set on item click listener
		listViewFragmentExampleExamples.setOnItemClickListener(this);
		
		return rootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		// Initial
		textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), this);
		
		if (readURL != null) {
			if (!readURL.isCancelled()) {
				readURL.cancel(true);
			}
		}
		readURL = new ReadURL((WordDetailActivity) getActivity());
		readURL.execute();
		
		Log.v("onResume", "onResume called");
	};
	
	@Override
	public void onDestroy() {
		Log.v("onDestroy", "onDestroy called");
		// Cancel AsyncTask
		if (readURL != null) {
			if (!readURL.isCancelled()) {
				readURL.cancel(true);
			}
		}
		
		// Don't forget to shutdown!
		if (textToSpeech != null) {
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
		super.onDestroy();
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		
		Log.v("onDetach", "onDetach called");
		
		// Release memory
		if (exampleAdapter != null) {
			exampleAdapter.clear();
		}
		if (examples != null && !examples.isEmpty()) {
			examples.clear();
		}
		if (mExamples != null && !mExamples.isEmpty()) {
			mExamples.clear();
		}
		if (doc != null) {
			doc = null;
		}
		if (word != null) {
			word = null;
		}
		super.onDetach();
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
				examples = doc.select("em");
				
				if (examples != null && !examples.isEmpty()) {
					mExamples = new ArrayList<String>();
					
					for (int i = 0; i < examples.size(); ++i) {
						String example = (Jsoup.parse(examples.get(i).getElementsByClass("example").html())).body().text();
						if (example != null && example.length() != 0) {
							mExamples.add(example);
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
            	if (mExamples != null && mExamples.size() != 0) {
    				txtFragmentExamplePleaseWait.setText(R.string.oxford);
    				
    				if (exampleAdapter != null) {
    					exampleAdapter.clear();
    				}
    				exampleAdapter = new ExampleAdapter(activity.getApplicationContext(), mExamples);
    				
    				listViewFragmentExampleExamples.setAdapter(exampleAdapter);
    			} else {
    				txtFragmentExamplePleaseWait.setText("Examples are not found");
    			}
            }
			
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		String mWord = mExamples.get(position);
		
		if (mWord.contains("/")) {
			int end = mWord.indexOf("/");
			mWord = mWord.substring(0, end);
		}
		
		textToSpeech.speak(mWord, TextToSpeech.QUEUE_FLUSH, null);
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
				listViewFragmentExampleExamples.setEnabled(true);
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}
}
