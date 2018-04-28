package com.example.thesis;

import com.example.thesis.R;
import com.example.thesis.dashbroad.activity.DashboardFavoriteMonthActivity;
import com.example.thesis.dashbroad.activity.DashboardFavoriteWeekActivity;
import com.example.thesis.dashbroad.activity.DashboardGeneralMonthActivity;
import com.example.thesis.dashbroad.activity.DashboardGeneralWeekActivity;
import com.example.thesis.dashbroad.activity.DashboardWordWrongActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DashBoardFragment extends Fragment implements OnClickListener{
	
	private static ImageView imgDashboardOverviewWeek;
	private static ImageView imgDashboardOverviewMonth;
	private static ImageView imgDashboardFavoriteWeek;
	private static ImageView imgDashboardFavoriteMonth;
	private static ImageView imgDashboardGame;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);
		
		imgDashboardOverviewWeek = (ImageView) rootView.findViewById(R.id.imgDashboardOverviewWeek);
		imgDashboardOverviewMonth = (ImageView) rootView.findViewById(R.id.imgDashboardOverviewMonth);
		imgDashboardFavoriteWeek = (ImageView) rootView.findViewById(R.id.imgDashboardFavoriteWeek);
		imgDashboardFavoriteMonth = (ImageView) rootView.findViewById(R.id.imgDashboardFavoriteMonth);
		imgDashboardGame = (ImageView) rootView.findViewById(R.id.imgDashboardGame);
		
		// Set on click listener
		imgDashboardOverviewWeek.setOnClickListener(this);
		imgDashboardOverviewMonth.setOnClickListener(this);
		imgDashboardFavoriteWeek.setOnClickListener(this);
		imgDashboardFavoriteMonth.setOnClickListener(this);
		imgDashboardGame.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.imgDashboardOverviewWeek:
			{
				Intent intent = new Intent(getActivity(), DashboardGeneralWeekActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
			break;
			case R.id.imgDashboardOverviewMonth:
			{
				Intent intent = new Intent(getActivity(), DashboardGeneralMonthActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
			break;
			case R.id.imgDashboardFavoriteWeek:
			{
				Intent intent = new Intent(getActivity(), DashboardFavoriteWeekActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
			break;
			case R.id.imgDashboardFavoriteMonth:
			{
				Intent intent = new Intent(getActivity(), DashboardFavoriteMonthActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
			break;
			case R.id.imgDashboardGame:
			{
				Intent intent = new Intent(getActivity(), DashboardWordWrongActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
			default:
				break;
		}
	}
}
