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

public class ExampleAdapter extends ArrayAdapter<String>{
	
	private static Context context;
	
	private static List<String> examples = new ArrayList<String>();
	
	private static class ViewHolder {
		TextView txtExampleAdapterExample;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			txtExampleAdapterExample = (TextView) v.findViewById(R.id.txtExampleAdapterExample);
		}
	}
	
	// CONSTRUCTOR
	public ExampleAdapter(Context context, List<String> examples) {
		super(context, 0, examples);
		ExampleAdapter.context = context;
		
		if (ExampleAdapter.examples!= null && ! ExampleAdapter.examples.isEmpty()) {
			ExampleAdapter.examples.clear();
		}
		ExampleAdapter.examples = examples;
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
			listView = inflater.inflate(R.layout.row_single_example, parent, false);
			
			holder = new ViewHolder(listView);
			
			listView.setTag(holder);
		} else {
			holder = (ViewHolder) listView.getTag();
		}
			
		holder.txtExampleAdapterExample.setText(examples.get(position));

		return listView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return examples.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return examples.get(position);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}
	
}
