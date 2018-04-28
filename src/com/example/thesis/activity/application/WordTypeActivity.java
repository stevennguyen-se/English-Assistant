package com.example.thesis.activity.application;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.global.GlobalString;
import com.example.thesis.object.Word;
import com.example.thesis.object.adapter.WordAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class WordTypeActivity extends Activity implements OnItemClickListener, OnClickListener {

	private List<Word> words;
	
	// List view
	private static ListView listViewWordType;
	
	// Adapter
	private WordAdapter wordAdapter;
	
	// View
	private static ImageButton btnWordTypeBackward;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_type);
		
		if (savedInstanceState != null) {
			words = savedInstanceState.getParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE);
		} else {
			words = getIntent().getParcelableArrayListExtra(GlobalString.TAG_WORD_PARCELABLE);
		}
		
		// Set ID
		listViewWordType = (ListView) findViewById(R.id.listViewWordType);
		btnWordTypeBackward = (ImageButton) findViewById(R.id.btnWordTypeBackward);
		
		
		// Create adapter
		wordAdapter = new WordAdapter(getApplicationContext(), words);
		
		// Set adapter
		listViewWordType.setAdapter(wordAdapter);
		
		// Set on item click listener
		listViewWordType.setOnItemClickListener(this);
		
		// Set on click listener
		btnWordTypeBackward.setOnClickListener(this);
	}
	
	@Override 
	protected void onDestroy() {
		super.onDestroy();
		if (words != null && !words.isEmpty()) {
			words.clear();
		}
		if (wordAdapter != null && !wordAdapter.isEmpty()) {
			wordAdapter.clear();
		}
		Log.v("Word Type", "onDestroy");
	};
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList(GlobalString.TAG_WORD_PARCELABLE, (ArrayList<? extends Parcelable>) words);
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

		Intent intent = new Intent(WordTypeActivity.this, WordDetailActivity.class);

		intent.putExtras(bundle);

		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnWordTypeBackward:
			{
				onBackPressed();
			}
			break;
			default:
				break;
		}
	}
}
