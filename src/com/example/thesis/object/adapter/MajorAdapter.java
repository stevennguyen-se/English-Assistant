package com.example.thesis.object.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.object.Major;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MajorAdapter extends ArrayAdapter<Major>{
	
	private static Context context;
	
	private static List<Major> majors = new ArrayList<Major>();
	
	private class ViewHolder {
		TextView txtMajorAdapterWord;
		TextView txtMajorAdapterDefinition;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			txtMajorAdapterWord = (TextView) v.findViewById(R.id.txtMajorAdapterWord);
			txtMajorAdapterDefinition = (TextView) v.findViewById(R.id.txtMajorAdapterDefinition);
		}
	}
	
	// CONSTRUCTOR
	public MajorAdapter(Context context, List<Major> majors) {
		super(context, 0, majors);
		MajorAdapter.context = context;
		
		if (MajorAdapter.majors != null && !MajorAdapter.majors.isEmpty()) {
			MajorAdapter.majors.clear();
		}
		MajorAdapter.majors = majors;
	}

	// OVERRIDE FUNCTION
	@Override 
	public View getView(final int position, View convertView, ViewGroup parent) {
		View listView = convertView;
		ViewHolder holder = null;
		
		if (listView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// SET item on grid view one by one
			listView = inflater.inflate(R.layout.row_single_major, parent, false);
			
			holder = new ViewHolder(listView);
			
			listView.setTag(holder);
		} else {
			holder = (ViewHolder) listView.getTag();
		}
			
		holder.txtMajorAdapterWord.setText(majors.get(position).getMajor());
		holder.txtMajorAdapterDefinition.setText(majors.get(position).getDefinition());

		return listView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return majors.size();
	}

	@Override
	public Major getItem(int position) {
		// TODO Auto-generated method stub
		return majors.get(position);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}
	
}
