package com.example.thesis.dashbroad.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.fragment.FragmentWrongWordChart;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.object.WordWrong;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class DashboardWordWrongActivity extends FragmentActivity implements OnClickListener {

	// Fragment Attribute
	private FragmentManager manager;
	private FragmentTransaction transaction;
		
	// View
	private static ImageButton btnDashboardWordWrongBackward;
		
	// Database
	private static FavoriteDatabaseAdapter favoriteDBAdapter;
	
	// List
	List<WordWrong> listWrongWord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_word_wrong);
		
		// Database
		final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
		favoriteDBAdapter = globalVariable.getFavoriteDBAdapter();
		
		// Initial
		listWrongWord = new ArrayList<WordWrong>();
		listWrongWord = favoriteDBAdapter.get10WordsMaxWrong();
		
		// Set ID
		btnDashboardWordWrongBackward = (ImageButton) findViewById(R.id.btnDashboardWordWrongBackward);
		
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		
		FragmentWrongWordChart fWrongWordChart = (FragmentWrongWordChart) manager.findFragmentByTag(GlobalString.TAG_FRAGMENT_DASHBOARD_WORD_WRONG_CHART);
		
		if (fWrongWordChart == null)
		{
			fWrongWordChart = new FragmentWrongWordChart(listWrongWord, "Choosing Wrong Word in Game", "Words", "Number of Wrong Choice");
			transaction.add(R.id.fragmentDashboardWordWrongChart, fWrongWordChart, GlobalString.TAG_FRAGMENT_DASHBOARD_WORD_WRONG_CHART);
			// send information by bundle
			transaction.commit();
		}
		
		// Set on click listener
		btnDashboardWordWrongBackward.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnDashboardWordWrongBackward:
			{
				onBackPressed();
			}
			break;
			default:
				break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (listWrongWord != null && !listWrongWord.isEmpty()) {
			listWrongWord.clear();
		}
		super.onDestroy();
	}
}
