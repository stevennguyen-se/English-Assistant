package com.example.thesis.dashbroad.activity;

import com.example.thesis.R;
import com.example.thesis.fragment.FragmentGeneralChartMonthCombine;
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
public class DashboardGeneralMonthActivity extends FragmentActivity implements OnClickListener{

	// Fragment Attribute
	private static FragmentManager manager;
	private static FragmentTransaction transaction;
		
	// View
	private static ImageButton btnDashboardMonthBackward;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_general_month);
		
		// Set ID
		btnDashboardMonthBackward = (ImageButton) findViewById(R.id.btnDashboardMonthBackward);
				
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		
		FragmentGeneralChartMonthCombine fGeneralChartCombine = (FragmentGeneralChartMonthCombine) manager.findFragmentByTag(GlobalString.TAG_FRAGMENT_DASHBOARD_GENERAL_CHART);
		
		if (fGeneralChartCombine == null)
		{
			fGeneralChartCombine = new FragmentGeneralChartMonthCombine();
			transaction.add(R.id.fragmentDashboardGeneralMonthChart, fGeneralChartCombine, GlobalString.TAG_FRAGMENT_DASHBOARD_GENERAL_CHART);
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
