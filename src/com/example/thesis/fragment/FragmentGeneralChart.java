package com.example.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.thesis.R;
import com.example.thesis.object.UserStatisticGeneral;

import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragmentGeneralChart extends Fragment {

	private static View rootView;	
	private static View mChart;

	private int totalWordView;
	private int totalAddFavorite;
	private int totalRemoveFavorite;
	private int totalHearVoice;
	
	private String legendViewWord;
	private String legendAddFavoriteWord;
	private String legendRemoveFavoriteWord;
	private String legendHearVoiceWord;
	
	private String titleChart;
	private String titleX;
	private String titleY;
	
	// Database
	private List<UserStatisticGeneral> listUserAttributeStatistic;
	
	// View
	private TextView txtDashboardGeneralViewWord;
	private TextView txtDashboardGeneralAddFavorite;
	private TextView txtDashboardGeneralRemoveFavorite;
	private TextView txtDashboardGeneralHearVoice;
	
	public FragmentGeneralChart() {
		// Required empty public constructor
	}
	
	public FragmentGeneralChart(List<UserStatisticGeneral> listUserAttributeStatistic,
			String titleChart, String titleX, String titleY) {
		
		// Initial
		legendViewWord = "View Word";
		legendAddFavoriteWord = "Add Favorite";
		legendRemoveFavoriteWord = "Remove Favorite";
		legendHearVoiceWord = "Hear Voice";
		
		this.listUserAttributeStatistic = listUserAttributeStatistic;
		this.titleChart = titleChart;
		this.titleX = titleX;
		this.titleY = titleY;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_general_chart, container,
				false);
		
		txtDashboardGeneralViewWord = (TextView) rootView.findViewById(R.id.txtDashboardGeneralViewWord);
		txtDashboardGeneralAddFavorite = (TextView) rootView.findViewById(R.id.txtDashboardGeneralAddFavorite);
		txtDashboardGeneralRemoveFavorite = (TextView) rootView.findViewById(R.id.txtDashboardGeneralRemoveFavorite);
		txtDashboardGeneralHearVoice = (TextView) rootView.findViewById(R.id.txtDashboardGeneralHearVoice);
		
		// Draw Chart
		openChart(listUserAttributeStatistic, titleChart, titleX, titleY);
		
		// Set text
		txtDashboardGeneralViewWord.setText(String.valueOf(totalWordView));
		txtDashboardGeneralAddFavorite.setText(String.valueOf(totalAddFavorite));
		txtDashboardGeneralRemoveFavorite.setText(String.valueOf(totalRemoveFavorite));
		txtDashboardGeneralHearVoice.setText(String.valueOf(totalHearVoice));
		
		return rootView;
	}
	
	@Override 
	public void onDestroy() {
		super.onDestroy();
		if (listUserAttributeStatistic != null && !listUserAttributeStatistic.isEmpty()) {
			listUserAttributeStatistic.clear();
		}
	};

	private void openChart(
			List<UserStatisticGeneral> listUserAttributeStatistic,
			String chartTitle, String XTitle, String YTitle) {

		this.totalWordView = 0;
		this.totalAddFavorite = 0;
		this.totalRemoveFavorite = 0;
		this.totalHearVoice = 0;
		
		List<String> mXLabels = new ArrayList<String>();
		for (int i = 0; i < listUserAttributeStatistic.size(); ++i) {
			mXLabels.add(listUserAttributeStatistic.get(i).getDate());
		}

		int[] x = new int[listUserAttributeStatistic.size()];

		List<Integer> viewWord = new ArrayList<Integer>();
		List<Integer> addFavorite = new ArrayList<Integer>();
		List<Integer> removeFavorite = new ArrayList<Integer>();
		List<Integer> hearVoice = new ArrayList<Integer>();

		int maxValue = 0;

		for (int i = 0; i < listUserAttributeStatistic.size(); ++i) {
			this.totalWordView += listUserAttributeStatistic.get(i).getTotal_num_of_view_word_detail();
			this.totalAddFavorite += listUserAttributeStatistic.get(i).getTotal_num_of_adding_favorite_word();
			this.totalRemoveFavorite += listUserAttributeStatistic.get(i).getTotal_num_of_removing_favorite_word();
			this.totalHearVoice += listUserAttributeStatistic.get(i).getTotal_num_of_hearing_voice_of_word();
			
			if (listUserAttributeStatistic.get(i)
					.getTotal_num_of_view_word_detail() > maxValue) {
				maxValue = listUserAttributeStatistic.get(i)
						.getTotal_num_of_view_word_detail();
			}
			if (listUserAttributeStatistic.get(i)
					.getTotal_num_of_adding_favorite_word() > maxValue) {
				maxValue = listUserAttributeStatistic.get(i)
						.getTotal_num_of_adding_favorite_word();
			}
			if (listUserAttributeStatistic.get(i)
					.getTotal_num_of_removing_favorite_word() > maxValue) {
				maxValue = listUserAttributeStatistic.get(i)
						.getTotal_num_of_removing_favorite_word();
			}
			if (listUserAttributeStatistic.get(i)
					.getTotal_num_of_hearing_voice_of_word() > maxValue) {
				maxValue = listUserAttributeStatistic.get(i)
						.getTotal_num_of_hearing_voice_of_word();
			}

			viewWord.add(listUserAttributeStatistic.get(i)
					.getTotal_num_of_view_word_detail());
			addFavorite.add(listUserAttributeStatistic.get(i)
					.getTotal_num_of_adding_favorite_word());
			removeFavorite.add(listUserAttributeStatistic.get(i)
					.getTotal_num_of_removing_favorite_word());
			hearVoice.add(listUserAttributeStatistic.get(i)
					.getTotal_num_of_hearing_voice_of_word());
		}

		// Make max value divisible by 10
		maxValue = ((maxValue / 10) * 10) + 10;

		// Creating an XYSeries for view word detail
		XYSeries viewWordSeries = new XYSeries(legendViewWord);
		// Creating an XYSeries for add favorite
		XYSeries addFavoriteSeries = new XYSeries(legendAddFavoriteWord);
		// Creating an XYSeries for remove favorite
		XYSeries removeFavoriteSeries = new XYSeries(legendRemoveFavoriteWord);
		// Creating an XYSeries for hear voice
		XYSeries hearVoiceSeries = new XYSeries(legendHearVoiceWord);

		// Adding data to Income and Expense Series

		for (int i = 0; i < x.length; i++) {
			viewWordSeries.add(i, viewWord.get(i));
			addFavoriteSeries.add(i, addFavorite.get(i));
			removeFavoriteSeries.add(i, removeFavorite.get(i));
			hearVoiceSeries.add(i, hearVoice.get(i));
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(viewWordSeries);
		dataset.addSeries(addFavoriteSeries);
		dataset.addSeries(removeFavoriteSeries);
		dataset.addSeries(hearVoiceSeries);

		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer viewWordRenderer = createRenderer(Color.RED,
				PointStyle.CIRCLE);
		XYSeriesRenderer addFavoriteRenderer = createRenderer(Color.GREEN,
				PointStyle.CIRCLE);
		XYSeriesRenderer removeFavoriteRenderer = createRenderer(Color.BLUE,
				PointStyle.CIRCLE);
		XYSeriesRenderer hearVoiceRenderer = createRenderer(Color.BLACK,
				PointStyle.CIRCLE);

		// Disable legend
		viewWordRenderer.setShowLegendItem(false);
		addFavoriteRenderer.setShowLegendItem(false);
		removeFavoriteRenderer.setShowLegendItem(false);
		hearVoiceRenderer.setShowLegendItem(false);
		
		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

		// Set Title
		multiRenderer.setChartTitle(chartTitle);
		multiRenderer.setXTitle(XTitle);
		multiRenderer.setYTitle(YTitle);

		// Set Labels
		// Set color of chart name, X name, and Y name
		multiRenderer.setLabelsColor(Color.BLACK);

		// X have no number label
		multiRenderer.setXLabels(0);
		// X text labels
		for (int i = 0; i < x.length; i++) {
			multiRenderer.addXTextLabel(i, mXLabels.get(i));
		}
		// Set X labels color
		multiRenderer.setXLabelsColor(Color.BLACK);
		// setting used to move the graph on x axis to .5 to the right
		multiRenderer.setXAxisMin(-0.5);
		// setting max number of labels
		multiRenderer.setXAxisMax(listUserAttributeStatistic.size());
		// setting x axis label align
		multiRenderer.setXLabelsAlign(Align.CENTER);
		// move X labels to the right
		multiRenderer.setXLabelsPadding(30f);

		// Set Y labels
		multiRenderer.setYLabels(listUserAttributeStatistic.size() + 2);
		// Set Y labels color
		multiRenderer.setYLabelsColor(0, Color.BLACK);
		// setting min number of labels
		multiRenderer.setYAxisMin(0);
		// setting max number of labels
		multiRenderer.setYAxisMax(maxValue);
		// setting y axis label to align
		multiRenderer.setYLabelsAlign(Align.LEFT);
		// move Y labels to the right
		multiRenderer.setYLabelsPadding(80f);

		// General setting
		multiRenderer.setAxesColor(Color.BLACK);
		multiRenderer.setGridColor(Color.TRANSPARENT);

		/***
		 * Customizing graphs
		 */
		// setting text size of the title
		multiRenderer.setChartTitleTextSize(60);
		// setting text size of the axis title
		multiRenderer.setAxisTitleTextSize(40);
		// setting text size of the graph lable
		multiRenderer.setLabelsTextSize(30);
		// setting text size of the legend
		multiRenderer.setLegendTextSize(50f);

		// setting zoom buttons visiblity
		multiRenderer.setZoomButtonsVisible(false);
		// setting pan enablity which uses graph to move on both axis
		multiRenderer.setPanEnabled(false, false);
		// setting click false on graph
		multiRenderer.setClickEnabled(false);
		// setting zoom to false on both axis
		multiRenderer.setZoomEnabled(false, false);
		// setting lines to display on y axis
		multiRenderer.setShowGridY(false);
		// setting lines to display on x axis
		multiRenderer.setShowGridX(false);
		// setting legend to fit the screen size
		multiRenderer.setFitLegend(true);
		// setting displaying line on grid
		multiRenderer.setShowGrid(true);
		// setting zoom to false
		multiRenderer.setZoomEnabled(false);
		// setting external zoom functions to false
		multiRenderer.setExternalZoomEnabled(false);
		// setting displaying lines on graph to be formatted(like using
		// graphics)
		multiRenderer.setAntialiasing(true);
		// setting to in scroll to false
		multiRenderer.setInScroll(false);
		// setting to set legend height of the graph
		multiRenderer.setLegendHeight(30);
		// setting text style
		multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);

		// Setting background color of the graph to transparent
		multiRenderer.setBackgroundColor(Color.TRANSPARENT);
		// Setting margin color of the graph to transparent
		multiRenderer.setMarginsColor(getResources().getColor(
				R.color.transparent_background));
		multiRenderer.setApplyBackgroundColor(true);

		// setting x axis point size
		multiRenderer.setPointSize(4f);
		// setting the margin size for the graph in the order top, left, bottom,
		// right
		multiRenderer.setMargins(new int[] { 150, 170, 150, 30 });

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(viewWordRenderer);
		multiRenderer.addSeriesRenderer(addFavoriteRenderer);
		multiRenderer.addSeriesRenderer(removeFavoriteRenderer);
		multiRenderer.addSeriesRenderer(hearVoiceRenderer);

		// this part is used to display graph on the xml
		RelativeLayout chartContainer = (RelativeLayout) rootView.findViewById(R.id.dashboardGeneralChart);
		// remove any views before u paint the chart
		chartContainer.removeAllViews();
		// drawing bar chart
		mChart = ChartFactory.getLineChartView(getActivity(),
				dataset, multiRenderer);
		// adding the view to the linearlayout
		chartContainer.addView(mChart);

	}

	private XYSeriesRenderer createRenderer(int color, PointStyle pointStyle) {
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(color);
		renderer.setFillPoints(true);
		renderer.setLineWidth(5f);
		renderer.setDisplayChartValues(true);
		// setting chart value size
		renderer.setChartValuesTextSize(30f);
		// setting line graph point style to circle
		renderer.setPointStyle(pointStyle);
		// setting stroke of the line chart to solid
		renderer.setStroke(BasicStroke.SOLID);

		return renderer;
	}
}
