package com.example.thesis.object.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.handler.StringHandler;
import com.example.thesis.object.Word;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WordAdapter extends ArrayAdapter<Word> {

	private static Context context;
	
	private static class ViewHolder {
		TextView txtWordAdapterWord;
		TextView txtWordAdapterPronunciation;
		TextView txtWordAdapterDefinition;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			txtWordAdapterWord = (TextView) v.findViewById(R.id.txtWordAdapterWord);
			txtWordAdapterPronunciation = (TextView) v.findViewById(R.id.txtWordAdapterPronunciation);
			txtWordAdapterDefinition = (TextView) v.findViewById(R.id.txtWordAdapterDefinition);
		}
	}
	
	private static List<Word> words = new ArrayList<Word>();
	
	public WordAdapter(Context context, List<Word> words) {
		super(context, 0, words);
		WordAdapter.context = context;
		
		if (WordAdapter.words != null && !WordAdapter.words.isEmpty()) {
			WordAdapter.words.clear();
		}
		WordAdapter.words = words;
	}

	@Override 
	public View getView(int position, View convertView, ViewGroup parent) {
		View listView = convertView;
		ViewHolder holder = null;
		
		if (listView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// SET item on grid view one by one
			listView = inflater.inflate(R.layout.row_single_word, parent, false);
			
			holder = new ViewHolder(listView);
			
			listView.setTag(holder);
		} else {
			holder = (ViewHolder) listView.getTag();
		}
		
		holder.txtWordAdapterWord.setText(words.get(position).getWord());
		holder.txtWordAdapterPronunciation.setText(words.get(position).getPronunciation());
		holder.txtWordAdapterDefinition.setText(words.get(position).getType() + ": " + StringHandler.getFirstDefinition(words.get(position).getDefinition()));
		
		return listView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return words.size();
	}

	@Override
	public Word getItem(int position) {
		// TODO Auto-generated method stub
		return words.get(position);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}
}
