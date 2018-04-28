package com.example.thesis.object.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thesis.R;

public class GameWDWordAdapter extends ArrayAdapter<String> {
	
	private Context context;
	
	private int selectedPosition;
	
	List<String> words = new ArrayList<String>();
	List<String> types = new ArrayList<String>();
	
	private class ViewHolder {
		LinearLayout layoutGameWDWord;
		TextView txtGameAdapterWord;
		TextView txtGameAdapterType;
	}
	
	// CONSTRUCTOR
	public GameWDWordAdapter(Context context, List<String> words, List<String> types) {
		super(context, 0);
		this.context = context;
		this.words = words;
		this.types = types;
		this.selectedPosition = Integer.MAX_VALUE;
	}

	// OVERRIDE FUNCTION
	@Override 
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// SET item on grid view one by one
			convertView = inflater.inflate(R.layout.row_single_game_wd_word, parent, false);
			
			holder = new ViewHolder();
			
			holder.layoutGameWDWord = (LinearLayout) convertView.findViewById(R.id.layoutGameWDWord);
			holder.txtGameAdapterWord = (TextView) convertView.findViewById(R.id.txtGameAdapterWord);
			holder.txtGameAdapterType = (TextView) convertView.findViewById(R.id.txtGameAdapterType);
			
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (selectedPosition == position) {
			holder.layoutGameWDWord.setBackgroundColor(Color.rgb(64, 222, 37));
		} else {
			holder.layoutGameWDWord.setBackgroundColor(Color.TRANSPARENT);
		}
		
		holder.txtGameAdapterWord.setText(words.get(position));
		holder.txtGameAdapterType.setText(types.get(position));
		
		// Set focusable
		holder.txtGameAdapterWord.setFocusable(false);
		holder.txtGameAdapterType.setFocusable(false);
		holder.layoutGameWDWord.setFocusable(false);
		
        holder.txtGameAdapterWord.setFocusableInTouchMode(false);
        holder.txtGameAdapterType.setFocusableInTouchMode(false);
        holder.layoutGameWDWord.setFocusableInTouchMode(false);
		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return words.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return words.get(position);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	// FUNCTIONS ===========================================================
	public int getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
	}
	
	
	// =====================================================================
}
