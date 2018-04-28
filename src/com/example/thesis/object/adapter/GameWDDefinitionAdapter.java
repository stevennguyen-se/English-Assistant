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

public class GameWDDefinitionAdapter extends ArrayAdapter<String> {
	
	private Context context;
	
	private int selectedPosition;
	
	List<String> definitions = new ArrayList<String>();
	
	private class ViewHolder {
		LinearLayout layoutGameWDDefinition;
		TextView txtGameAdapterDefinition;
	}
	
	// CONSTRUCTOR
	public GameWDDefinitionAdapter(Context context, List<String> definitions) {
		super(context, 0);
		this.context = context;
		this.definitions = definitions;
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
			convertView = inflater.inflate(R.layout.row_single_game_wd_definition, parent, false);
			
			holder = new ViewHolder();
			
			holder.layoutGameWDDefinition = (LinearLayout) convertView.findViewById(R.id.layoutGameWDDefinition);
			holder.txtGameAdapterDefinition = (TextView) convertView.findViewById(R.id.txtGameAdapterDefinition);
			
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (selectedPosition == position) {
			holder.layoutGameWDDefinition.setBackgroundColor(Color.rgb(64, 222, 37));
		} else {
			holder.layoutGameWDDefinition.setBackgroundColor(Color.TRANSPARENT);
		}
		
		holder.txtGameAdapterDefinition.setText(definitions.get(position));
		
		// Set focusable
		holder.txtGameAdapterDefinition.setFocusable(false);
		holder.layoutGameWDDefinition.setFocusable(false);
		
        holder.txtGameAdapterDefinition.setFocusableInTouchMode(false);
        holder.layoutGameWDDefinition.setFocusableInTouchMode(false);
		
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return definitions.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return definitions.get(position);
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
