package com.example.thesis.fragment;

import com.example.thesis.R;
import com.example.thesis.object.adapter.MajorAdapter;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragmentTypeMajorExample extends Fragment {

	private static final String[] CONTENT = new String[] { "Type", "Major", "Example", "Synonym"};
	private static final int[] ICONS = new int[] {
		R.drawable.word_detail_tab_all,
		R.drawable.word_detail_tab_all,
		R.drawable.word_detail_tab_all,
		R.drawable.word_detail_tab_all,
	};
	
	private String word;
	private String type;
	private String definition;

	private MajorAdapter majorAdapter;
	
	private static ViewPager viewPager;
	
	public FragmentTypeMajorExample() {
		// Required empty public constructor
	}
	
	public FragmentTypeMajorExample(String word, String type, String definition, MajorAdapter majorAdapter) {
		// Required empty public constructor
		this.majorAdapter = majorAdapter;
		this.word = word;
		this.type = type;
		this.definition = definition;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// declare a contextThemeWrapper to store a theme
		final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Theme_PageIndicatorWordDetail);
				
		// Set theme inside local inflater
		LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
		
		// Inflate the layout for this fragment
		View view = localInflater.inflate(R.layout.fragment_type_and_major,
				container, false); 
		
		viewPager = (ViewPager) view.findViewById(R.id.pagerWordDetail);
		viewPager.setAdapter(new FragmentWordDetailAdapter(getFragmentManager()));
		
		TabPageIndicator indicator = (TabPageIndicator) view.findViewById(R.id.indicatorWordDetail);
        indicator.setViewPager(viewPager);
		
		return view;
	}
	
	@Override 
	public void onDestroy() {
		if (this.word != null) {
			this.word = null;
		}
		if (this.type != null) {
			this.type = null;
		}
		if (this.definition != null) {
			this.definition = null;
		}
		if (this.majorAdapter != null && !this.majorAdapter.isEmpty()) {
			this.majorAdapter.clear();
		}
		super.onDestroy();
	};
	
	class FragmentWordDetailAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

		// get the Fragment manager
		public FragmentWordDetailAdapter(FragmentManager fm) {
			super(fm);
		}
	
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment fragment = null;
				// move to the related Fragment
			if (arg0 == 0) {
				fragment = new FragmentType(type, definition);
			} else if (arg0 == 1) {
				fragment = new FragmentMajor(majorAdapter);
			} else if (arg0 == 2) {
				fragment = new FragmentExamples(word);
			} else if (arg0 == 3) {
				fragment = new FragmentSynonym(word);
			} 
			return fragment;
		}
		
		@Override
	    public CharSequence getPageTitle(int position) {
	        return CONTENT[position % CONTENT.length];
	    }
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
				// we have only 3 fragment, so the number is 3
			return CONTENT.length;
		}

		@Override
		public int getIconResId(int index) {
			// TODO Auto-generated method stub
			return ICONS[index];
		}
	
	}


}
