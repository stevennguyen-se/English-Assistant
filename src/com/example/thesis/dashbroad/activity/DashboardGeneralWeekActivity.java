package com.example.thesis.dashbroad.activity;

import com.example.thesis.R;
import com.example.thesis.fragment.FragmentGeneralChartWeekCombine;
import com.example.thesis.global.GlobalString;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

@SuppressLint("Recycle")
public class DashboardGeneralWeekActivity extends FragmentActivity implements OnClickListener{

	// Fragment Attribute
	private static FragmentManager manager;
	private static FragmentTransaction transaction;
		
	// View
	private static ImageButton btnDashboardWeekBackward;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_general_week);
		
		// Set ID
		btnDashboardWeekBackward = (ImageButton) findViewById(R.id.btnDashboardWeekBackward);
		
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		
		FragmentGeneralChartWeekCombine fGeneralChartCombine = (FragmentGeneralChartWeekCombine) manager.findFragmentByTag(GlobalString.TAG_FRAGMENT_DASHBOARD_GENERAL_CHART);
		
		if (fGeneralChartCombine == null)
		{
			fGeneralChartCombine = new FragmentGeneralChartWeekCombine();
			transaction.add(R.id.fragmentDashboardGeneralWeekChart, fGeneralChartCombine, GlobalString.TAG_FRAGMENT_DASHBOARD_GENERAL_CHART);
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
