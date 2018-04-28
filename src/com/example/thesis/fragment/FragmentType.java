package com.example.thesis.fragment;

import com.example.thesis.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragmentType extends Fragment {

	private String type;
	private String definition;
	
	private static TextView txtFragmentWordTypeType;
	private static TextView txtFragmentWordTypeDefinition;
	
	public FragmentType() {
		// Required empty public constructor
	}
	
	public FragmentType(String type, String definition) {
		this.type = type;
		this.definition = definition;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_type,
				container, false);
		
		txtFragmentWordTypeType = (TextView) view.findViewById(R.id.txtFragmentWordTypeType);
		txtFragmentWordTypeDefinition = (TextView) view.findViewById(R.id.txtFragmentWordTypeDefinition);
		
		txtFragmentWordTypeType.setText(type);
		txtFragmentWordTypeDefinition.setText(definition);
		
		return view;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		if (this.type != null) {
			this.type = null;
		}
		if (this.definition != null) {
			this.definition = null;
		}
		super.onDetach();
	}
}
