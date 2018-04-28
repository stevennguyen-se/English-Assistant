package com.example.thesis;

import java.util.List;
import com.example.thesis.R;
import com.example.thesis.activity.application.WordDetailActivity;
import com.example.thesis.database.adapter.DictionaryDatabaseAdapter;
import com.example.thesis.database.adapter.IrregularVerbsDatabaseAdapter;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.object.Word;
import com.example.thesis.object.WordIrregular;
import com.example.thesis.object.adapter.IrregularWordAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class IrregularVerbsFragment extends Fragment implements OnClickListener, OnItemClickListener {

	private static String word;
	
	private static ListView listIrregularWords;
	
	private static IrregularWordAdapter irWordAdapter;
	
	private static DictionaryDatabaseAdapter dictionaryDBAdapter;
	private static IrregularVerbsDatabaseAdapter irDatabaseAdapter;
	
	private static List<WordIrregular> words;
	
	// View
	private static EditText etIrregularWord;
	
	private static ImageView btnIrregularRemove;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_irregular_verb, container, false);
        
        // Implement Database
        final GlobalVariable globalVariable = (GlobalVariable) getActivity().getApplicationContext();
        dictionaryDBAdapter = globalVariable.getDictionaryDBAdapter();
        irDatabaseAdapter = globalVariable.getIrDBAdapter();
        
        // Set ID
        listIrregularWords = (ListView) rootView.findViewById(R.id.listIrregularWords);
        
        etIrregularWord = (EditText) rootView.findViewById(R.id.etIrregularWord);
        
        btnIrregularRemove = (ImageView) rootView.findViewById(R.id.btnIrregularRemove);
        
        // Set on click listener
        btnIrregularRemove.setOnClickListener(this);
        
        // Set on item click listener
        listIrregularWords.setOnItemClickListener(this);
        
        return rootView;
	}
	
	@Override 
	public void onResume() {
		super.onResume();
		// Get data
        words = irDatabaseAdapter.getAllData();
        
		irWordAdapter = new IrregularWordAdapter(getActivity().getApplicationContext(), words);

        listIrregularWords.setAdapter(irWordAdapter);
        
        // Text watcher
        editTextWatcher();
	};

	@Override 
	public void onPause() {
		super.onPause();
		if (words != null && !words.isEmpty()) {
			words.clear();
		}
		if (irWordAdapter != null && !irWordAdapter.isEmpty()) {
			irWordAdapter.clear();
		}
	};
	private void editTextWatcher() {
		etIrregularWord.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				word = etIrregularWord.getText().toString();

				if (words != null) {
					words.clear();
				}
				words = irDatabaseAdapter.getData(word);
				
				if (irWordAdapter != null) {
					irWordAdapter.clear();
				}
				irWordAdapter = new IrregularWordAdapter(getActivity().getApplicationContext(), words);

		        listIrregularWords.setAdapter(irWordAdapter);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (etIrregularWord.getText().toString().length() == 0) {
					btnIrregularRemove.setVisibility(View.INVISIBLE);
				} else {
					btnIrregularRemove.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnIrregularRemove:
			{
				etIrregularWord.setText("");
			}
			break;
			default:
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Word singleWord = dictionaryDBAdapter.getSingleVerb(words.get(position).getWordV1());
		
		Bundle bundle = new Bundle();

		bundle.putString(GlobalString.TAG_ID, singleWord.getId());
		bundle.putString(GlobalString.TAG_WORD, singleWord.getWord());
		bundle.putString(GlobalString.TAG_PRONUNCIATION, singleWord.getPronunciation());
		bundle.putString(GlobalString.TAG_TYPE, singleWord.getType());
		bundle.putString(GlobalString.TAG_DEFINITION, singleWord.getDefinition());

		Intent intent = new Intent(getActivity().getApplicationContext(), WordDetailActivity.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtras(bundle);

		startActivity(intent);
	}
}
