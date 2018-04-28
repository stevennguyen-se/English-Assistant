package com.example.thesis;

import com.example.thesis.R;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.game.activity.BeginGameActivity;
import com.example.thesis.global.GlobalVariable;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GameFragment extends Fragment {
	
	private static TextView txtPleaseAddFavoriteWords;
	
	private static ImageView imgLoading;
	
	// Image ID
	private static int[] loadingIds = new int[] {
			R.drawable.loading_1,
			R.drawable.loading_2,
			R.drawable.loading_3,
			R.drawable.loading_4};
	
	// AsyncTask
	private static Loading loading;
	
	// Favorite database
	private static FavoriteDatabaseAdapter favoriteDBAdapter;
	
	public GameFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_game, container, false);
		
		// Database
		final GlobalVariable globalVariable = (GlobalVariable) getActivity().getApplicationContext();
		favoriteDBAdapter = globalVariable.getFavoriteDBAdapter();
		
		// Set ID
		txtPleaseAddFavoriteWords = (TextView) rootView.findViewById(R.id.txtPleaseAddFavoriteWords);
		
		imgLoading = (ImageView) rootView.findViewById(R.id.imgLoading);
		
		return rootView;
	}

	@Override 
	public void onResume() {
		super.onResume();
		
		// check favorite
		if (favoriteDBAdapter.getNumberOfRows("") == 0) {
			txtPleaseAddFavoriteWords.setVisibility(View.VISIBLE);
		} else {
			if (loading != null) {
				loading.cancel(true);
			}
			loading = new Loading();
			loading.execute();
			
			imgLoading.setVisibility(View.VISIBLE);
		}
	};
	
	@Override 
	public void onPause() {
		super.onPause();
		
		if (loading != null) {
			if (!loading.isCancelled()) {
				loading.cancel(true);
			}
		}
		
		imgLoading.setBackgroundResource(android.R.color.transparent);
		
		txtPleaseAddFavoriteWords.setVisibility(View.INVISIBLE);
	};
	
	private class Loading extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			for (int i = 0; i < loadingIds.length; ++i) {
				publishProgress(i);
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return null;
		}
		
		protected void onProgressUpdate(Integer... values) {
			imgLoading.setImageResource(loadingIds[values[0]]);
			
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			Intent intent = new Intent(getActivity(), BeginGameActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			
		}
	}
}
