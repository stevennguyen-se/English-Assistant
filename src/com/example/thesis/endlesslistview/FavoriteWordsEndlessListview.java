package com.example.thesis.endlesslistview;

import java.util.List;

import com.example.thesis.object.Word;
import com.example.thesis.object.adapter.FavoriteWordAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class FavoriteWordsEndlessListview extends ListView implements OnScrollListener {
	// progress
	private static View footer;

	private boolean isLoading;
	private EndlessListener listener;
	private FavoriteWordAdapter adapterFavoriteWord;

	private int totalNumberOfWords;

	// CONSTRUCTORS
	public FavoriteWordsEndlessListview(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.setOnScrollListener(this);
	}

	public FavoriteWordsEndlessListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnScrollListener(this);
	}

	public FavoriteWordsEndlessListview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (getAdapter() == null)
			return;

		if (getAdapter().getCount() == 0)
			return;

		int l = visibleItemCount + firstVisibleItem;
		if (l >= totalItemCount && !isLoading
				&& totalItemCount < totalNumberOfWords) {
			// It is time to add new data. We call the listener
			this.addFooterView(footer);
			isLoading = true;
			listener.loadData();
		}
	}

	// SET GET functions
	// ------------------------------------------------------------------
	public void setTotalNumberOfWords(int totalNumberOfFollowers) {
		this.totalNumberOfWords = totalNumberOfFollowers;
	}

	public void setListener(EndlessListener listener) {
		this.listener = listener;
	}

	public void setLoadingView(int resId) {
		LayoutInflater inflater = (LayoutInflater) super.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		footer = (View) inflater.inflate(resId, null);

		if (isLoading) {
			this.addFooterView(footer);
		}

	}

	public void setFavoriteWordAdapter(FavoriteWordAdapter adapter) {
		super.setAdapter(adapter);
		this.adapterFavoriteWord = adapter;
		try {
			this.removeFooterView(footer);
		} catch (Exception e) {
			
		}
	}

	// ADD Data
	// ----------------------------------------------------------------------------
	public void addNewData(List<Word> data) {

		try {
			this.removeFooterView(footer);
		} catch (Exception e) {
			
		}
		
		adapterFavoriteWord.addAll(data);
		adapterFavoriteWord.notifyDataSetChanged();
		isLoading = false;
	}

	public EndlessListener getListener() {
		return listener;
	}

	public static interface EndlessListener {
		public void loadData();
	}

}
