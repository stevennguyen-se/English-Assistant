package com.example.thesis.activity.application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

import com.example.thesis.R;
import com.example.thesis.fragment.FragmentCalanderNumber;
import com.example.thesis.global.GlobalNumber;
import com.example.thesis.global.GlobalString;
import com.example.thesis.viewpager.VerticalViewPager;
import com.wt.calendarcard.CalendarCard;
import com.wt.calendarcard.CardGridItem;
import com.wt.calendarcard.OnCellItemClick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FavoritesCalanderActivity extends FragmentActivity implements
		OnClickListener {

	private static CalendarCard favoriteCalander;

	private static VerticalViewPager viewpagerMonth;
	private static VerticalViewPager viewpagerYear;

	private static int numberOfMonth;
	private static int numberOfYear;
	private int currentMonthPage;
	private int currentYearPage;

	private static PageMonthListener pageMonthListener;
	private static PageYearListener pageYearListener;

	// View
	private static LinearLayout linearLayoutCalendarBackground;
	
	private static ImageView imgFavoriteCalanderMonthUp;
	private static ImageView imgFavoriteCalanderYearUp;
	private static ImageView imgFavoriteCalanderMonthDown;
	private static ImageView imgFavoriteCalanderYearDown;
	
	private static ImageButton btnFavoriteCalanderBackward;

	// Calander
	private static Calendar dateDisplay;
	private static Calendar currentHour;
	
	private static int am_pm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites_calander);

		// Initial
		numberOfMonth = 12;
		numberOfYear = 6;
		pageMonthListener = new PageMonthListener();
		pageYearListener = new PageYearListener();
		
		currentHour = Calendar.getInstance();
		am_pm = currentHour.get(Calendar.AM_PM);
		
		// Set ID
		favoriteCalander = (CalendarCard) findViewById(R.id.favoriteCalander);

		viewpagerMonth = (VerticalViewPager) findViewById(R.id.viewpagerMonth);
		viewpagerYear = (VerticalViewPager) findViewById(R.id.viewpagerYear);

		
		linearLayoutCalendarBackground = (LinearLayout) findViewById(R.id.linearLayoutCalendarBackground);
		if (am_pm != 1) {
			linearLayoutCalendarBackground.setBackgroundResource(R.drawable.background_calendar_morning);
		} else {
			linearLayoutCalendarBackground.setBackgroundResource(R.drawable.background_calendar_noon);
		}
		
		imgFavoriteCalanderMonthUp = (ImageView) findViewById(R.id.imgFavoriteCalanderMonthUp);
		imgFavoriteCalanderYearUp = (ImageView) findViewById(R.id.imgFavoriteCalanderYearUp);
		imgFavoriteCalanderMonthDown = (ImageView) findViewById(R.id.imgFavoriteCalanderMonthDown);
		imgFavoriteCalanderYearDown = (ImageView) findViewById(R.id.imgFavoriteCalanderYearDown);

		btnFavoriteCalanderBackward = (ImageButton) findViewById(R.id.btnFavoriteCalanderBackward);
		
		// Set view pager adapter
		viewpagerMonth.setAdapter(new MonthPagerAdapter(
				getSupportFragmentManager()));
		viewpagerYear.setAdapter(new YearPagerAdapter(
				getSupportFragmentManager()));

		// Set on page change listener
		viewpagerMonth.setOnPageChangeListener(pageMonthListener);
		viewpagerYear.setOnPageChangeListener(pageYearListener);

		// Set on cell item click
		favoriteCalander.setOnCellItemClick(new OnCellItemClick() {
			@Override
			public void onCellClick(View v, CardGridItem item) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(),
						FavoritesDetailDayActivity.class);
				
				Bundle bundle = new Bundle();
				
				String fullDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(item.getDate().getTime());
				String day = fullDate.substring(0, 2);
				String month = fullDate.substring(3, 5);
				String year = fullDate.substring(6);;
				
				bundle.putInt(GlobalString.TAG_DAY, Integer.parseInt(day));
				bundle.putInt(GlobalString.TAG_MONTH, Integer.parseInt(month));
				bundle.putInt(GlobalString.TAG_YEAR, Integer.parseInt(year));
				
				bundle.putString(GlobalString.TAG_FULL_DAY, fullDate);
				
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

		// Set on click listener
		imgFavoriteCalanderMonthUp.setOnClickListener(this);
		imgFavoriteCalanderYearUp.setOnClickListener(this);
		imgFavoriteCalanderMonthDown.setOnClickListener(this);
		imgFavoriteCalanderYearDown.setOnClickListener(this);
		
		btnFavoriteCalanderBackward.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		displayMonthYearBasedOnCalendar();
	};

	@Override 
	protected void onDestroy() {
		linearLayoutCalendarBackground.setBackgroundResource(android.R.color.transparent);
		
		super.onDestroy();
	};
	
	private void displayMonthYearBasedOnCalendar() {
		dateDisplay = favoriteCalander.getDateDisplay();
		int month = dateDisplay.get(Calendar.MONTH) + 1;
		int year = dateDisplay.get(Calendar.YEAR);

		while (month > convertCurrentMonthPageToMonth()) {
			++currentMonthPage;
			viewpagerMonth.setCurrentItem(currentMonthPage);
		}
		while (month < convertCurrentMonthPageToMonth()) {
			--currentMonthPage;
			viewpagerMonth.setCurrentItem(currentMonthPage);
		}
		while (year > convertCurrentYearPageToYear()) {
			++currentYearPage;
			viewpagerMonth.setCurrentItem(currentYearPage);
		}
		while (year < convertCurrentYearPageToYear()) {
			--currentYearPage;
			viewpagerMonth.setCurrentItem(currentYearPage);
		}
	}

	// Month view pager adapter
	private class MonthPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments;

		public MonthPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fragments = getMonthFragments();
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}

	private List<Fragment> getMonthFragments() {

		List<Fragment> fList = new ArrayList<Fragment>();

		for (int i = 0; i < numberOfMonth; ++i) {
			Bundle bundle = new Bundle();
			bundle.putInt(GlobalString.TAG_CALENDAR_NUMBER, i + 1);
			bundle.putInt(GlobalString.TAG_CALENDAR_TEXTSIZE, GlobalNumber.CALENDAR_MONTH_TEXTSIZE);
			
			FragmentCalanderNumber fCalendarNumber = new FragmentCalanderNumber();
			fCalendarNumber.setArguments(bundle);
			
			fList.add(fCalendarNumber);
		}
		return fList;
	}

	// Year view pager adapter
	private class YearPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments;

		public YearPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fragments = getYearFragments();
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}

	private List<Fragment> getYearFragments() {

		List<Fragment> fList = new ArrayList<Fragment>();

		for (int i = 0; i < numberOfYear; ++i) {
			Bundle bundle = new Bundle();
			bundle.putInt(GlobalString.TAG_CALENDAR_NUMBER, i + 2015);
			bundle.putInt(GlobalString.TAG_CALENDAR_TEXTSIZE, GlobalNumber.CALENDAR_YEAR_TEXTSIZE);
			
			FragmentCalanderNumber fCalendarNumber = new FragmentCalanderNumber();
			fCalendarNumber.setArguments(bundle);
			
			fList.add(fCalendarNumber);
		}
		return fList;

	}

	private class PageMonthListener extends SimpleOnPageChangeListener {
		public void onPageSelected(int position) {
			currentMonthPage = position;
			// set month
			dateDisplay.set(Calendar.MONTH, currentMonthPage);
			// set year
			dateDisplay.set(Calendar.YEAR, currentYearPage + 2015);
			// set date display
			favoriteCalander.setDateDisplay(dateDisplay);
			// upgrade cell
			favoriteCalander.updateCells();
		}
	}

	private class PageYearListener extends SimpleOnPageChangeListener {
		public void onPageSelected(int position) {
			currentYearPage = position;
			// set month
			dateDisplay.set(Calendar.MONTH, currentMonthPage);
			// set year
			dateDisplay.set(Calendar.YEAR, currentYearPage + 2015);
			// set date display
			favoriteCalander.setDateDisplay(dateDisplay);
			// upgrade cell
			favoriteCalander.updateCells();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.imgFavoriteCalanderMonthUp: {
				if (currentMonthPage < numberOfMonth - 1) {
					viewpagerMonth.setCurrentItem(currentMonthPage + 1);
				}
			}
				break;
			case R.id.imgFavoriteCalanderYearUp: {
				if (currentYearPage < numberOfYear - 1) {
					viewpagerYear.setCurrentItem(currentYearPage + 1);
				}
			}
				break;
			case R.id.imgFavoriteCalanderMonthDown: {
				if (currentMonthPage > 0) {
					viewpagerMonth.setCurrentItem(currentMonthPage - 1);
				}
			}
				break;
			case R.id.imgFavoriteCalanderYearDown: {
				if (currentYearPage > 0) {
					viewpagerYear.setCurrentItem(currentYearPage - 1);
				}
			}
				break;
			case R.id.btnFavoriteCalanderBackward:
			{
				onBackPressed();
			}
			default:
				break;
		}
		// set month
		dateDisplay.set(Calendar.MONTH, currentMonthPage);
		// set year
		dateDisplay.set(Calendar.YEAR, currentYearPage + 2015);
		// set date display
		favoriteCalander.setDateDisplay(dateDisplay);
		// upgrade cell
		favoriteCalander.updateCells();

	}

	private int convertCurrentMonthPageToMonth() {
		int month = currentMonthPage + 1;
		return month;
	}

	private int convertCurrentYearPageToYear() {
		int year = currentYearPage + 2015;
		return year;
	}
}
