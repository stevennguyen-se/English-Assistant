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

public class SynonymAdapter extends ArrayAdapter<String>{
	
	private static Context context;
	
	private static List<String> synonyms = new ArrayList<String>();
	
	private static class ViewHolder {
		TextView txtSynonymAdapterSynonym;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			txtSynonymAdapterSynonym = (TextView) v.findViewById(R.id.txtSynonymAdapterSynonym);
		}
	}
	
	// CONSTRUCTOR
	public SynonymAdapter(Context context, List<String> synonyms) {
		super(context, 0, synonyms);
		SynonymAdapter.context = context;
		
		if (SynonymAdapter.synonyms!= null && ! SynonymAdapter.synonyms.isEmpty()) {
			SynonymAdapter.synonyms.clear();
		}
		SynonymAdapter.synonyms = synonyms;
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
			listView = inflater.inflate(R.layout.row_single_synonym, parent, false);
			
			holder = new ViewHolder(listView);
			
			listView.setTag(holder);
		} else {
			holder = (ViewHolder) listView.getTag();
		}
			
		holder.txtSynonymAdapterSynonym.setText(synonyms.get(position));

		return listView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return synonyms.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return synonyms.get(position);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}
	
}
