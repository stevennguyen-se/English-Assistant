package com.example.thesis.object.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.handler.StringHandler;
import com.example.thesis.object.Word;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class FavoriteWordAdapter extends ArrayAdapter<Word> {

	private static Context context;
	
	private static List<Word> words = new ArrayList<Word>();
	
	private static class ViewHolder {
		TextView txtFavoriteAdapterWord;
		TextView txtFavoriteAdapterPronunciation;
		TextView txtFavoriteAdapterDefinition;
		
		CheckBox cbFavoriteAdapter;
	}
	
	// CONSTRUCTOR
	public FavoriteWordAdapter(Context context, List<Word> words) {
		super(context, 0, words);
		FavoriteWordAdapter.context = context;
		
		if (FavoriteWordAdapter.words != null && !FavoriteWordAdapter.words.isEmpty()) {
			FavoriteWordAdapter.words.clear();
		}
		FavoriteWordAdapter.words = words;
	}

	// OVERRIDE FUNCTION
	@Override 
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// SET item on grid view one by one
			convertView = inflater.inflate(R.layout.row_single_favorite_word, parent, false);
			
			holder = new ViewHolder();
			
			holder.txtFavoriteAdapterWord = (TextView) convertView.findViewById(R.id.txtFavoriteAdapterWord);
			holder.txtFavoriteAdapterPronunciation = (TextView) convertView.findViewById(R.id.txtFavoriteAdapterPronunciation);
			holder.txtFavoriteAdapterDefinition = (TextView) convertView.findViewById(R.id.txtFavoriteAdapterDefinition);
			holder.cbFavoriteAdapter = (CheckBox) convertView.findViewById(R.id.cbFavoriteAdapter);
			
			convertView.setTag(holder);
			
			holder.cbFavoriteAdapter.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CheckBox cb = (CheckBox) v;
					Word word = (Word) cb.getTag(); 
					if (word.isSelecetd()) {
						word.setSelected(false);
					} else {
						word.setSelected(true);
					}
					
				}
			});
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Word word = words.get(position);
		
		holder.txtFavoriteAdapterWord.setText(word.getWord());
		holder.txtFavoriteAdapterPronunciation.setText(word.getPronunciation());
		holder.txtFavoriteAdapterDefinition.setText(word.getType() + ": " + StringHandler.getFirstDefinition(word.getDefinition()));
		holder.cbFavoriteAdapter.setChecked(word.isSelecetd());
		holder.cbFavoriteAdapter.setTag(word);
		
		// Set focusable
		holder.txtFavoriteAdapterWord.setFocusable(false);
		holder.txtFavoriteAdapterPronunciation.setFocusable(false);
		holder.txtFavoriteAdapterDefinition.setFocusable(false);
		holder.cbFavoriteAdapter.setFocusable(false);
		
        holder.txtFavoriteAdapterWord.setFocusableInTouchMode(false);
        holder.txtFavoriteAdapterPronunciation.setFocusableInTouchMode(false);
        holder.txtFavoriteAdapterDefinition.setFocusableInTouchMode(false);
        holder.cbFavoriteAdapter.setFocusableInTouchMode(false);
		
		return convertView;
	}

	// FUNCTIONS============================================================
	public List<Word> getSelectedWord() throws CloneNotSupportedException {
		
		Word singleWord = null;
		List<Word> selectedWord = new ArrayList<Word>();
		
		for (int i = 0; i < words.size(); ++i) {
			if (words.get(i).isSelecetd()) {
				singleWord = words.get(i).clone();
				selectedWord.add(singleWord);
			}
		}
		
		return selectedWord;
	}
	
	// =====================================================================	
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
