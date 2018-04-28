package com.example.thesis;

import com.example.thesis.R;
import com.example.thesis.activity.application.AboutUsContactActivity;
import com.example.thesis.activity.application.AboutUsDeveloperActivity;
import com.example.thesis.activity.application.AboutUsReferenceActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsFragment extends Fragment implements OnClickListener {

	private static TextView txtAboutUsDeveloper;
	private static TextView txtAboutUsReference;
	private static TextView txtAboutUsContact;
	
	private static ImageView imgAboutUsDeveloper;
	private static ImageView imgAboutUsReference;
	private static ImageView imgAboutUsContact;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
		
		// Set ID
		txtAboutUsDeveloper = (TextView) rootView.findViewById(R.id.txtAboutUsDeveloper);
		txtAboutUsReference = (TextView) rootView.findViewById(R.id.txtAboutUsReference);
		txtAboutUsContact = (TextView) rootView.findViewById(R.id.txtAboutUsContact);
		
		imgAboutUsDeveloper = (ImageView) rootView.findViewById(R.id.imgAboutUsDeveloper);
		imgAboutUsReference = (ImageView) rootView.findViewById(R.id.imgAboutUsReference);
		imgAboutUsContact = (ImageView) rootView.findViewById(R.id.imgAboutUsContact);
		
		// Set on click listener
		txtAboutUsDeveloper.setOnClickListener(this);
		txtAboutUsReference.setOnClickListener(this);
		txtAboutUsContact.setOnClickListener(this);
		
		imgAboutUsDeveloper.setOnClickListener(this);
		imgAboutUsReference.setOnClickListener(this);
		imgAboutUsContact.setOnClickListener(this);
		
		return rootView; 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent intent = null;
		if (v.getId() == R.id.txtAboutUsDeveloper || v.getId() == R.id.imgAboutUsDeveloper) {
			 intent = new Intent(getActivity().getApplicationContext(), AboutUsDeveloperActivity.class);
		} else {
			if (v.getId() == R.id.txtAboutUsReference || v.getId() == R.id.imgAboutUsReference) {
				 intent = new Intent(getActivity().getApplicationContext(), AboutUsReferenceActivity.class);
			} else {
				if (v.getId() == R.id.txtAboutUsContact || v.getId() == R.id.imgAboutUsContact) {
					 intent = new Intent(getActivity().getApplicationContext(), AboutUsContactActivity.class);
				}
			}
		}
		if (intent != null) {
			startActivity(intent);
		}
		
	}

}
