package com.example.thesis.activity.application;

import com.example.thesis.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AboutUsDeveloperActivity extends Activity implements OnClickListener{

	private static ImageButton btnAboutUsDeveloperBackward;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us_developer);
		
		btnAboutUsDeveloperBackward = (ImageButton) findViewById(R.id.btnAboutUsDeveloperBackward);
		
		btnAboutUsDeveloperBackward.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnAboutUsDeveloperBackward:
			{
				onBackPressed();
			}
			break;
			default:
				break;
		}
	}
}
