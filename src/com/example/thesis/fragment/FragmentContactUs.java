package com.example.thesis.fragment;

import com.example.thesis.R;
import com.example.thesis.global.GlobalString;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragmentContactUs extends Fragment {

	private static EditText etSubject;
	private static EditText etMessage;
	
	public FragmentContactUs() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
		
		// Set ID
		etSubject = (EditText) rootView.findViewById(R.id.etSubject);
		etMessage = (EditText) rootView.findViewById(R.id.etMessage);
		
		return rootView;
	}

	public void sendMail() {
		String subject = etSubject.getText().toString();
		String message = etMessage.getText().toString();

		Intent email = new Intent(Intent.ACTION_SEND);
		
		// Intent.EXTRA_CC
		// Intent.EXTRA_BCC
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{ GlobalString.CONTACT_US_EMAIL});
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, message);

		// need this to prompts email client only
		email.setType("message/rfc822");

		startActivity(Intent.createChooser(email, "Choose an Email client :"));
	}
}
