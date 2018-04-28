package com.example.thesis.fragment;

import com.example.thesis.R;
import com.example.thesis.global.GlobalString;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragmentCalanderNumber extends Fragment {

	private TextView txtNumber;
	
	private int number;
	private int textSize;
	
	public FragmentCalanderNumber() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_calander_number, container, false);
		
		Bundle bundle = this.getArguments();
		
		if (bundle != null) {
			number = bundle.getInt(GlobalString.TAG_CALENDAR_NUMBER);
			textSize = bundle.getInt(GlobalString.TAG_CALENDAR_TEXTSIZE);
		}
		
		txtNumber = (TextView) rootView.findViewById(R.id.txtNumber);
		txtNumber.setText(convertMonthNumberToString(number));
		txtNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		
		return rootView;
	}

	private String convertMonthNumberToString (int number) {
		switch (number) {
		case 1:
		{
			return "Jan";
		}
		case 2:
		{
			return "Feb";
		}
		case 3:
		{
			return "Mar";
		}
		case 4:
		{
			return "Apr";
		}
		case 5:
		{
			return "May";
		}
		case 6:
		{
			return "Jun";
		}
		case 7:
		{
			return "Jul";
		}
		case 8:
		{
			return "Aug";
		}
		case 9:
		{
			return "Sep";
		}
		case 10:
		{
			return "Oct";
		}
		case 11:
		{
			return "Nov";
		}
		case 12:
		{
			return "Dec";
		}
		default:
			return String.valueOf(number);
		}
	}
}
