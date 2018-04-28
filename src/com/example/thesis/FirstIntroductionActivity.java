package com.example.thesis;

import com.example.thesis.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FirstIntroductionActivity extends FragmentActivity implements OnClickListener{
	private static final int[] resource = new int[] { 
		R.drawable.welcome1,
		R.drawable.welcome2, 
		R.drawable.welcome3, 
		R.drawable.welcome4 };

	ImageView imgFirstIntroductionOK;
	
	// View Pager
	ViewPager viewPager;
	PageIndicator pageIndicator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_introduction);
		
		// Set ID:
		imgFirstIntroductionOK = (ImageView) findViewById(R.id.imgFirstIntroductionOK);
		
		MyFragmentStatePager adpter = new MyFragmentStatePager(
				getSupportFragmentManager());
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		pageIndicator = (CirclePageIndicator) findViewById(R.id.indicatorFirstIntroduction);
		
		viewPager.setAdapter(adpter);
		/**
		 * First: You need call this method after you set the Viewpager adpter;
		 * Second: setmViewPager(ViewPager mViewPager,Object object int count,
		 * int... colors) so,you can set any length colors to make the animation
		 * more cool! Third: If you call this method like below, make the colors
		 * no data, it will create a change color by default.
		 * */
		
		pageIndicator.setViewPager(viewPager);
		pageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
						Log.e("TAG", "onPageScrolled");
					}

					@Override
					public void onPageSelected(int position) {
						Log.e("TAG", "onPageSelected");
						if (position == resource.length - 1) {
							imgFirstIntroductionOK.setVisibility(View.VISIBLE);
						} else {
							imgFirstIntroductionOK.setVisibility(View.GONE);
						}
					}

					@Override
					public void onPageScrollStateChanged(int state) {
						Log.e("TAG", "onPageScrollStateChanged");
					}
				});
		
		imgFirstIntroductionOK.setOnClickListener(this);
	}

	public class MyFragmentStatePager extends FragmentStatePagerAdapter {

		public MyFragmentStatePager(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return new MyFragment(position);
		}

		@Override
		public int getCount() {
			return resource.length;
		}
	}

	@SuppressLint("ValidFragment")
	public class MyFragment extends Fragment {
		private int position;

		public MyFragment(int position) {
			this.position = position;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setImageResource(resource[position]);
			return imageView;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.imgFirstIntroductionOK:
			{
				Intent intent = new Intent(FirstIntroductionActivity.this, ActionBarActivity.class);
			    startActivity(intent);
			}
			break;
			default:
				break;
		}
	}
}
