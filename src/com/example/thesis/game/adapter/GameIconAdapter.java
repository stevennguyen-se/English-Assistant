package com.example.thesis.game.adapter;

import com.example.thesis.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class GameIconAdapter extends ArrayAdapter<Integer>{

	private Context context;
	
	int[] gameIconIds;
	
	private class ViewHolder {
		ImageView imgGameIconAdapterIcon;
	}
	
	// CONSTRUCTOR
	public GameIconAdapter(Context context, int[] gameIconIds) {
		super(context, 0);
		this.context = context;
		this.gameIconIds = gameIconIds;
	}
	
	// OVERRIDE FUNCTION
		@Override 
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			
			if (convertView == null) {
				
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				// SET item on grid view one by one
				convertView = inflater.inflate(R.layout.frame_single_game, parent, false);
				
				holder = new ViewHolder();
				
				holder.imgGameIconAdapterIcon = (ImageView) convertView.findViewById(R.id.imgGameIconAdapterIcon);
							
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.imgGameIconAdapterIcon.setBackgroundResource(gameIconIds[position]);
			
			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return gameIconIds.length;
		}

		@Override
		public Integer getItem(int position) {
			// TODO Auto-generated method stub
			return gameIconIds[position];
		}

		@Override
		public long getItemId(int i) {
			// TODO Auto-generated method stub
			return i;
		}
}
