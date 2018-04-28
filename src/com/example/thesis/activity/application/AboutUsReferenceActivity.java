package com.example.thesis.activity.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.object.adapter.ExpandableListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

public class AboutUsReferenceActivity extends Activity implements OnClickListener{

	private static ImageButton btnAboutUsReferenceBackward;
	
	private static ExpandableListAdapter listAdapter;
	private static ExpandableListView expListView;
	private static List<String> listDataHeader;
	private static HashMap<String, List<String>> listDataChild;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us_reference);
		
		// Set ID
		btnAboutUsReferenceBackward = (ImageButton) findViewById(R.id.btnAboutUsReferenceBackward);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        
	}
	
	@Override 
	protected void onResume() {
		super.onResume();
		// preparing list data
        prepareListData();
 
        // Create adapter
        listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
 
        // Set list adapter
        expListView.setAdapter(listAdapter);
		
		// Set on click listener
		btnAboutUsReferenceBackward.setOnClickListener(this);
	};
	
	@Override 
	protected void onPause() {
		super.onPause();
		if (listDataHeader != null && !listDataHeader.isEmpty()) {
			listDataHeader.clear();
		}
		if (listDataChild != null && !listDataChild.isEmpty()) {
			listDataChild.clear();
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.btnAboutUsReferenceBackward:
		{
			onBackPressed();
		}
		break;
		default:
			break;
		}
	}
	
	/*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        String[] childString;
        
        // Adding child data
        listDataHeader.add("Database");
        listDataHeader.add("Library");
        listDataHeader.add("Images");
        listDataHeader.add("Music");
 
        // Adding child data
        List<String> database = new ArrayList<String>();
        childString = getResources().getStringArray(R.array.reference_database_array);
        for (int i = 0; i < childString.length; ++i) {
        	database.add(childString[i]);
        }
        
        List<String> library = new ArrayList<String>();
        childString = getResources().getStringArray(R.array.reference_library_array);
        for (int i = 0; i < childString.length; ++i) {
        	library.add(childString[i]);
        }
        
        List<String> images = new ArrayList<String>();
        childString = getResources().getStringArray(R.array.reference_image_array);
        for (int i = 0; i < childString.length; ++i) {
        	images.add(childString[i]);
        }
        
        List<String> music = new ArrayList<String>();
        childString = getResources().getStringArray(R.array.reference_music_array);
        for (int i = 0; i < childString.length; ++i) {
        	music.add(childString[i]);
        }
        
        
        listDataChild.put(listDataHeader.get(0), database);
        listDataChild.put(listDataHeader.get(1), library);
        listDataChild.put(listDataHeader.get(2), images);
        listDataChild.put(listDataHeader.get(3), music);
    }
}
