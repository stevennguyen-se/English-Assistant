package com.example.thesis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.thesis.R;
import com.example.thesis.object.SingleRowWithImage;
import com.example.thesis.object.adapter.ExpandableListWithImageAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class HelpFragment extends Fragment {

	private static ExpandableListWithImageAdapter listAdapter;
	private static ExpandableListView expListView;
	private static List<String> listDataHeader;
	private static HashMap<String, List<SingleRowWithImage>> listDataChild;
	
	private static int[] resIdButton = new int[] {
		R.drawable.ic_search_help,
		R.drawable.ic_camera_help,
		R.drawable.ic_microphone,
		R.drawable.favorite_true,
		R.drawable.ic_speaker,
		R.drawable.icon_calendar,
	};
	
	private static int[] resIdTips = new int[] {
		R.drawable.ic_no_one,
		R.drawable.ic_no_two,
	};
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_help, container, false);
    	
    	// Set ID
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
    	
        return rootView;
    }
    
    @Override 
	public void onResume() {
		super.onResume();
		// preparing list data
        prepareListData();
 
        // Create adapter
        listAdapter = new ExpandableListWithImageAdapter(getActivity().getApplicationContext(), listDataHeader, listDataChild);
 
        // Set list adapter
        expListView.setAdapter(listAdapter);
		
		// Set on click listener
	};
	
	@Override 
	public void onPause() {
		super.onPause();
		if (listDataHeader != null && !listDataHeader.isEmpty()) {
			listDataHeader.clear();
		}
		if (listDataChild != null && !listDataChild.isEmpty()) {
			listDataChild.clear();
		}
	};
	
	/*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<SingleRowWithImage>>();
 
        String[] childString;
        
        // Adding child data
        listDataHeader.add("Button Help");
        listDataHeader.add("Tip");
 
        // Adding child data
        SingleRowWithImage singleRowWithImage;
        
        List<SingleRowWithImage> helpButton = new ArrayList<SingleRowWithImage>();
        
        childString = getResources().getStringArray(R.array.help_button);
        
        for (int i = 0; i < childString.length; ++i) {
        	singleRowWithImage = new SingleRowWithImage();
        	singleRowWithImage.setResid(resIdButton[i]);
        	singleRowWithImage.setText(childString[i]);
        	
        	helpButton.add(singleRowWithImage);
        }
        
        
        List<SingleRowWithImage> helpTips = new ArrayList<SingleRowWithImage>();
        
        childString = getResources().getStringArray(R.array.help_tips);
        
        for (int i = 0; i < childString.length; ++i) {
        	singleRowWithImage = new SingleRowWithImage();
        	singleRowWithImage.setResid(resIdTips[i]);
        	singleRowWithImage.setText(childString[i]);
        	
        	helpTips.add(singleRowWithImage);
        }
        
        listDataChild.put(listDataHeader.get(0), helpButton);
        listDataChild.put(listDataHeader.get(1), helpTips);
    }
}
