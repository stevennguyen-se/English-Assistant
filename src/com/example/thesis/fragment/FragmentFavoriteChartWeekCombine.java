package com.example.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.object.UserStatisticFavorite;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
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
public class FragmentFavoriteChartWeekCombine extends Fragment {

	private static final String[] CONTENT = new String[] {"Last 3 Week","Last 2 Week","Last 1 Week","This Week"};
	private static final int[] ICONS = new int[] {
		R.drawable.word_detail_tab_all,
		R.drawable.word_detail_tab_all,
		R.drawable.word_detail_tab_all,
		R.drawable.word_detail_tab_all,
	};
	
	// Database
	private FavoriteDatabaseAdapter favoriteDatabaseAdapter;

	private List<UserStatisticFavorite> listUserAttributeStatistic;
	
	private ViewPager viewPager;
	
	public FragmentFavoriteChartWeekCombine() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Theme_PageIndicatorWordDetail);
		
		// Set theme inside local inflater
		LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
		
		// Inflate the layout for this fragment
		View view = localInflater.inflate(R.layout.fragment_favorite_chart_week_combine,
				container, false);
		
		// Database
		final GlobalVariable globalVariable = new GlobalVariable();
		favoriteDatabaseAdapter = globalVariable.getFavoriteDBAdapter();

//		listUserAttributeStatistic = new ArrayList<UserStatisticFavorite>();
		
		viewPager = (ViewPager) view.findViewById(R.id.pagerDashboardFavorite);
		viewPager.setAdapter(new FragmentFavoriteChartCombineAdapter(getFragmentManager()));
		
		TitlePageIndicator indicator = (TitlePageIndicator) view.findViewById(R.id.indicatorDashboardFavorite);
        indicator.setViewPager(viewPager);
        indicator.setCurrentItem(CONTENT.length - 1);
        indicator.setSelectedColor(Color.rgb(45, 163, 51));
        indicator.setTextColor(Color.rgb(64, 222, 73));
        
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		listUserAttributeStatistic = new ArrayList<UserStatisticFavorite>();
	};
	
	@Override 
	public void onDestroy() {
		super.onDestroy();
		if (listUserAttributeStatistic != null && !listUserAttributeStatistic.isEmpty()) {
			listUserAttributeStatistic.clear();
		}
	};

	class FragmentFavoriteChartCombineAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

		// get the Fragment manager
		public FragmentFavoriteChartCombineAdapter(FragmentManager fm) {
			super(fm);
		}
	
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment fragment = null;
				// move to the related Fragment
			if (arg0 == 0) {
				listUserAttributeStatistic = favoriteDatabaseAdapter.getFavoriteWordsWeek(3);
				fragment = new FragmentFavoriteChart(listUserAttributeStatistic, "Favorite Words Chart", "Days Of Week", "Number of Words");
			} else if (arg0 == 1) {
				listUserAttributeStatistic = favoriteDatabaseAdapter.getFavoriteWordsWeek(2);
				fragment = new FragmentFavoriteChart(listUserAttributeStatistic, "Favorite Words Chart", "Days Of Week", "Number of Words");
			} else if (arg0 == 2) {
				listUserAttributeStatistic = favoriteDatabaseAdapter.getFavoriteWordsWeek(1);
				fragment = new FragmentFavoriteChart(listUserAttributeStatistic, "Favorite Words Chart", "Days Of Week", "Number of Words");
			} else if (arg0 == 3) {
				listUserAttributeStatistic = favoriteDatabaseAdapter.getFavoriteWordsWeek(0);
				fragment = new FragmentFavoriteChart(listUserAttributeStatistic, "Favorite Words Chart", "Days Of Week", "Number of Words");
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
