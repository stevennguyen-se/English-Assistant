package com.example.thesis.object.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.object.WordIrregular;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IrregularWordAdapter extends ArrayAdapter<WordIrregular>{
	
	private static Context context;
	
	private static List<WordIrregular> words = new ArrayList<WordIrregular>();
	
	private static class ViewHolder {
		TextView txtWordV1;
		TextView txtWordV2;
		TextView txtWordV3;
	}
	
	// CONSTRUCTOR
	public IrregularWordAdapter(Context context, List<WordIrregular> words) {
		super(context, 0, words);
		IrregularWordAdapter.context = context;
		
		if (IrregularWordAdapter.words != null && !IrregularWordAdapter.words.isEmpty()) {
			IrregularWordAdapter.words.clear();
		}
		IrregularWordAdapter.words = words;
	}

	// OVERRIDE FUNCTION
	@Override 
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// SET item on grid view one by one
			convertView = inflater.inflate(R.layout.row_single_irregular_word, parent, false);
			
			holder = new ViewHolder();
			
			holder.txtWordV1 = (TextView) convertView.findViewById(R.id.txtWordV1);
			holder.txtWordV2 = (TextView) convertView.findViewById(R.id.txtWordV2);
			holder.txtWordV3 = (TextView) convertView.findViewById(R.id.txtWordV3);
						
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		WordIrregular word = words.get(position);
		
		holder.txtWordV1.setText(word.getWordV1());
		holder.txtWordV2.setText(word.getWordV2());
		holder.txtWordV3.setText(word.getWordV3());
		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return words.size();
	}

	@Override
	public WordIrregular getItem(int position) {
		// TODO Auto-generated method stub
		return words.get(position);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}
}
