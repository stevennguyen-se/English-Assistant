package com.example.thesis.dashbroad.activity;

import com.example.thesis.R;
import com.example.thesis.fragment.FragmentFavoriteChartWeekCombine;
import com.example.thesis.global.GlobalString;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class DashboardFavoriteWeekActivity extends FragmentActivity implements OnClickListener{

	// Fragment Attribute
	private static FragmentManager manager;
	private static FragmentTransaction transaction;
		
	// View
	private static ImageButton btnDashboardWeekBackward;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_favorite_week);
		
		// Set ID
		btnDashboardWeekBackward = (ImageButton) findViewById(R.id.btnDashboardWeekBackward);
		
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		
		FragmentFavoriteChartWeekCombine fFavoriteChartCombine = (FragmentFavoriteChartWeekCombine) manager.findFragmentByTag(GlobalString.TAG_FRAGMENT_DASHBOARD_FAVORITE_CHART);
		
		if (fFavoriteChartCombine == null)
		{
			fFavoriteChartCombine = new FragmentFavoriteChartWeekCombine();
			transaction.add(R.id.fragmentDashboardFavoriteWeekChart, fFavoriteChartCombine, GlobalString.TAG_FRAGMENT_DASHBOARD_FAVORITE_CHART);
			// send information by bundle
			transaction.commit();
		}
		
		// Set on click listener
		btnDashboardWeekBackward.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnDashboardWeekBackward:
			{
				onBackPressed();
			}
			break;
			default:
				break;
		}
	}
}
