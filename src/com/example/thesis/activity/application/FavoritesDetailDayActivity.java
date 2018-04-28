package com.example.thesis.activity.application;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thesis.R;
import com.example.thesis.database.adapter.FavoriteDatabaseAdapter;
import com.example.thesis.global.GlobalString;
import com.example.thesis.global.GlobalVariable;
import com.example.thesis.message.Message;
import com.example.thesis.object.Word;
import com.example.thesis.object.adapter.FavoriteWordAdapter;

public class FavoritesDetailDayActivity extends Activity implements OnClickListener, OnItemClickListener{
	
	private static List<Word> selectedWords;
	
	// extends ListView
	private static ListView wordList;
	
	private static FavoriteDatabaseAdapter favoriteDBAdapter;
	
	private static List<Word> favoriteWordsOnThisDate;
	
	private static FavoriteWordAdapter favoriteWordAdapter;
	
	// View
	private static TextView txtFavoriteDetailDayDate;
	private static ImageView imgFavoriteDetailDayRecycle;
	private static ImageButton btnFavoriteDetailDayBackward;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites_detail_day);
		
		txtFavoriteDetailDayDate = (TextView) findViewById(R.id.txtFavoriteDetailDayDate);
		
		Bundle bundle = getIntent().getExtras();
		int day = bundle.getInt(GlobalString.TAG_DAY);
		int month = bundle.getInt(GlobalString.TAG_MONTH);
		int year = bundle.getInt(GlobalString.TAG_YEAR);
		String fullDate = bundle.getString(GlobalString.TAG_FULL_DAY);
		
		txtFavoriteDetailDayDate.setText(fullDate);
		
		// Set ID
		wordList = (ListView) findViewById(R.id.listFavoriteWordsOnDetailDate);
		imgFavoriteDetailDayRecycle = (ImageView) findViewById(R.id.imgFavoriteDetailDayRecycle);		
		btnFavoriteDetailDayBackward = (ImageButton) findViewById(R.id.btnFavoriteDetailDayBackward);
		
		// Database
		final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
		favoriteDBAdapter = globalVariable.getFavoriteDBAdapter();
		
		favoriteWordsOnThisDate = favoriteDBAdapter.getWordsOfDate(day, month, year);

		// Create adapter
		favoriteWordAdapter = new FavoriteWordAdapter(getApplicationContext(), favoriteWordsOnThisDate);

		// Set adapter
		wordList.setAdapter(favoriteWordAdapter);
		
		// Set on click listener
		imgFavoriteDetailDayRecycle.setOnClickListener(this);
		btnFavoriteDetailDayBackward.setOnClickListener(this);
		
		// Set on item click listener
		wordList.setOnItemClickListener(this);
	}

	@Override 
	protected void onDestroy() {
		if (selectedWords != null && !selectedWords.isEmpty()) {
			selectedWords.clear();
		}
		if (favoriteWordsOnThisDate != null && !favoriteWordsOnThisDate.isEmpty()) {
			favoriteWordsOnThisDate.clear();
		}
		if (favoriteWordAdapter != null && !favoriteWordAdapter.isEmpty()) {
			favoriteWordAdapter.clear();
		}
		super.onDestroy();
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btnFavoriteDetailDayBackward:
			{
				onBackPressed();
			}
			break;
			case R.id.imgFavoriteDetailDayRecycle:
			{
				try {
					selectedWords = favoriteWordAdapter.getSelectedWord();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				openAlert(v);
			}
			break;
			default:
				break;
		}
	}
	
	private void removeFavoriteWordsOfThisDay() {
		for (int i = 0; i < favoriteWordsOnThisDate.size(); ++i) {
			if (favoriteWordsOnThisDate.get(i).isSelecetd()) {
				for (int j = i; j < favoriteWordsOnThisDate.size();) {
					if (favoriteWordsOnThisDate.get(j).isSelecetd()) {
						favoriteWordsOnThisDate.remove(j);
					} else {
						break;
					}
				}
			}
		}
	}
	
	@SuppressLint("InflateParams")
	private void openAlert(View v) {

		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

		LayoutInflater inflater = getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_custom_titile, null);
		TextView title = (TextView) dialogView.findViewById(R.id.myTitle);

		title.setText("ALERT");
		builder.setCustomTitle(dialogView);
		
		if (selectedWords.size() == 0) {
			builder.setMessage("Are you sure you want to remove all favorite words");
		} else {
			builder.setMessage("Are you sure you want to remove " + selectedWords.size() + " words");
		}
		

		builder.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (selectedWords.size() == 0) {
							boolean isSuccess = favoriteDBAdapter.deletedAll();
							if (isSuccess) {
								Message.message(getApplicationContext(),
										"Remove all successful");

								// update list view
								favoriteWordsOnThisDate.clear();
								favoriteWordAdapter.notifyDataSetChanged();
							} else {
								Message.message(getApplicationContext(), "Cannot remove all");
							}
						} else {
							boolean isSuccess = favoriteDBAdapter
									.deleteWords(selectedWords);

							if (isSuccess) {
								Message.message(getApplicationContext(),
										"Remove selected words successful");
								removeFavoriteWordsOfThisDay();
								favoriteWordAdapter.notifyDataSetChanged();
							} else {
								Message.message(getApplicationContext(),
										"Cannot remove all selected words");
							}
						}
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		// AlertDialog alert = builder.create();
		// alert.show();
		AlertDialog dialog = builder.show();
		// Change the title divider
		Resources res = getResources();
		int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
		View titleDivider = dialog.findViewById(titleDividerId);
		titleDivider.setBackgroundColor(res.getColor(R.color.line));
		Button a = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		if (a != null) {
			a.setTextSize(20);
			a.setTextColor(getResources().getColor(R.color.white));
			a.setBackgroundColor(getResources().getColor(R.color.red));
		}
		Button b = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
		if (b != null) {
			b.setTextSize(20);
			b.setTextColor(getResources().getColor(R.color.white));
			b.setBackgroundColor(getResources().getColor(R.color.middle_line));
		}

	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();

		bundle.putString(GlobalString.TAG_ID, favoriteWordsOnThisDate.get(position).getId());
		bundle.putString(GlobalString.TAG_WORD, favoriteWordsOnThisDate.get(position).getWord());
		bundle.putString(GlobalString.TAG_PRONUNCIATION, favoriteWordsOnThisDate.get(position).getPronunciation());
		bundle.putString(GlobalString.TAG_TYPE, favoriteWordsOnThisDate.get(position).getType());
		bundle.putString(GlobalString.TAG_DEFINITION, favoriteWordsOnThisDate.get(position).getDefinition());

		Intent intent = new Intent(FavoritesDetailDayActivity.this, WordDetailActivity.class);

		intent.putExtras(bundle);

		startActivity(intent);
	}
}
