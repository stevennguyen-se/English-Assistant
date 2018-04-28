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
public class FragmentGameIntro extends Fragment {

	private static View rootView;
	
	private ImageView imgGameIntro;
	
	private int resid;
	
	public FragmentGameIntro() {
		// Required empty public constructor
	}
	
	public FragmentGameIntro(int resid) {
		// Required empty public constructor
		this.resid = resid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_game_intro, container, false);
		
		return rootView;
	}

	@Override 
	public void onResume() {
		super.onResume();
		imgGameIntro = (ImageView) rootView.findViewById(R.id.imgGameIntro);
		imgGameIntro.setBackgroundResource(resid);
	};
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		imgGameIntro.setBackgroundResource(android.R.color.transparent);
	}
}
