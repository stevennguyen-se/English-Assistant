package com.example.thesis.dashbroad.activity;

import com.example.thesis.R;
import com.example.thesis.fragment.FragmentFavoriteChartMonthCombine;
import com.example.thesis.global.GlobalString;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class DashboardFavoriteMonthActivity extends FragmentActivity implements OnClickListener {

	// Fragment Attribute
	private static FragmentManager manager;
	private static FragmentTransaction transaction;
		
	// View
	private static ImageButton btnDashboardMonthBackward;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_favorite_month);
		
		// Set ID
		btnDashboardMonthBackward = (ImageButton) findViewById(R.id.btnDashboardMonthBackward);
		
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		
		FragmentFavoriteChartMonthCombine fFavoriteChartCombine = (FragmentFavoriteChartMonthCombine) manager.findFragmentByTag(GlobalString.TAG_FRAGMENT_DASHBOARD_FAVORITE_CHART);
		
		if (fFavoriteChartCombine == null)
		{
			fFavoriteChartCombine = new FragmentFavoriteChartMonthCombine();
			transaction.add(R.id.fragmentDashboardFavoriteMonthChart, fFavoriteChartCombine, GlobalString.TAG_FRAGMENT_DASHBOARD_FAVORITE_CHART);
			// send information by bundle
			transaction.commit();
		}
		
		// Set on click listener
		btnDashboardMonthBackward.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnDashboardMonthBackward:
			{
				onBackPressed();
			}
			break;
			default:
				break;
		}
	}
}
