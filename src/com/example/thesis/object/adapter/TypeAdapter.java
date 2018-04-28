package com.example.thesis.object.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TypeAdapter extends ArrayAdapter<String>{
	
	private static Context context;
	
	private static List<String> types = new ArrayList<String>();
	
	private static class ViewHolder {
		TextView txtTypeAdapterWord;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			txtTypeAdapterWord = (TextView) v.findViewById(R.id.txtTypeAdapterWord);
		}
	}
	
	// CONSTRUCTOR
	public TypeAdapter(Context context, int txtViewResourceId, List<String> types) {
		super(context, txtViewResourceId, types);
		TypeAdapter.context = context;
		
		if (TypeAdapter.types != null && !TypeAdapter.types.isEmpty()) {
			TypeAdapter.types.clear();
		}
		TypeAdapter.types = types;
	}

	// OVERRIDE FUNCTION
	@Override 
	public View getDropDownView(int position, View cnvtView, ViewGroup prnt) { 
		return getCustomView(position, cnvtView, prnt); 
	} 
	
	@Override 
	public View getView(int pos, View cnvtView, ViewGroup prnt) { 
		return getCustomView(pos, cnvtView, prnt); 
	}
	
	public View getCustomView(final int position, View convertView, ViewGroup parent) {
		View listView = convertView;
		ViewHolder holder = null;
		
		if (listView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// SET item on grid view one by one
			listView = inflater.inflate(R.layout.row_single_type, parent, false);
			
			holder = new ViewHolder(listView);
			
			listView.setTag(holder);
		} else {
			holder = (ViewHolder) listView.getTag();
		}
			
		holder.txtTypeAdapterWord.setText(types.get(position));

		return listView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return types.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return types.get(position);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}
	
}
