package com.example.thesis.activity.application;

import com.example.thesis.R;
import com.example.thesis.fragment.FragmentContactUs;
import com.example.thesis.global.GlobalString;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AboutUsContactActivity extends FragmentActivity implements OnClickListener{

	private static ImageView imgAboutUsContactSendMail;
	private static ImageButton btnAboutUsContactBackward;
		
	// Fragment Attribute
	private FragmentManager manager;
	private FragmentTransaction transaction;
		
	private static FragmentContactUs fContactUs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us_contact);

		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		
		// Set ID
		imgAboutUsContactSendMail = (ImageView) findViewById(R.id.imgAboutUsContactSendMail);
		btnAboutUsContactBackward = (ImageButton) findViewById(R.id.btnAboutUsContactBackward);
		
		// Set on click listener
		btnAboutUsContactBackward.setOnClickListener(this);
		imgAboutUsContactSendMail.setOnClickListener(this);
		
		// Add fragment
		fContactUs = (FragmentContactUs) manager.findFragmentByTag(GlobalString.TAG_FRAGMENT_CONTACT_US);
		
		if (fContactUs == null)
		{
			fContactUs = new FragmentContactUs();
			transaction.add(R.id.fragmentAboutUsContact, fContactUs, GlobalString.TAG_FRAGMENT_CONTACT_US);
			// send information by bundle
			transaction.commit();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnAboutUsContactBackward:
			{
				onBackPressed();
			}
			break;
			case R.id.imgAboutUsContactSendMail:
			{
				fContactUs.sendMail();
			}
			break;
			default:
				break;
		}
	}

}
