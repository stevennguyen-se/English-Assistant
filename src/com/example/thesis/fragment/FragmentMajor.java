package com.example.thesis.fragment;

import com.example.thesis.R;
import com.example.thesis.object.adapter.MajorAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragmentMajor extends Fragment {

	private static ListView listMajors;
	
	private MajorAdapter majorAdapter;
	
	public FragmentMajor() {
		// Required empty public constructor
	}
	
	// use getApplicationContext();
	public FragmentMajor(MajorAdapter majorAdapter) {
		this.majorAdapter = majorAdapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_major,
				container, false);
		
		// set ID
		listMajors = (ListView) view.findViewById(R.id.listFragmentWordMajor);
		
		listMajors.setAdapter(majorAdapter);
		
		return view;
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		if (this.majorAdapter != null && !this.majorAdapter.isEmpty()) {
			this.majorAdapter.clear();
		}
		super.onDetach();
	}
}
