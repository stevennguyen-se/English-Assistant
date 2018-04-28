package com.example.thesis.fragment;

import com.example.thesis.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragmentGameIcon extends Fragment {

	private static View rootView;
	
	private ImageView imgGameIcon;
	
	private int resid;
	
	public FragmentGameIcon() {
		// Required empty public constructor
	}
	
	public FragmentGameIcon(int resid) {
		// Required empty public constructor
		this.resid = resid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_game_icon, container, false);
		
		
		
		return rootView;
	}
	
	@Override 
	public void onResume() {
		super.onResume();
		imgGameIcon = (ImageView) rootView.findViewById(R.id.imgGameIcon);
		imgGameIcon.setBackgroundResource(resid);
	};
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		imgGameIcon.setBackgroundResource(android.R.color.transparent);
	}

}
