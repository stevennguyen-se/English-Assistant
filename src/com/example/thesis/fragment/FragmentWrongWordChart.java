package com.example.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.thesis.R;
import com.example.thesis.object.WordWrong;

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
public class FragmentWrongWordChart extends Fragment {

	private static View rootView;	
	private static View mChart;

	private int totalWrongWord;
	
	private String legendWrongWord;
	
	private String titleChart;
	private String titleX;
	private String titleY;
	
	// Database
	private List<WordWrong> listWordWrong;
	
	// View
	private static TextView txtDashboardWordWrondSingleWord;
	private static TextView txtDashboardWordWrondSingleWordNumber;
	private static TextView txtDashboardWordWrondWord;
	
	public FragmentWrongWordChart() {
		// Required empty public constructor
	}
	
	public FragmentWrongWordChart(List<WordWrong> listWordWrong,
			String titleChart, String titleX, String titleY) {
		
		// Initial
		legendWrongWord = "Wrong Word";
		
		this.listWordWrong = listWordWrong;
		this.titleChart = titleChart;
		this.titleX = titleX;
		this.titleY = titleY;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_wrong_word_chart, container, false);
		
		// Set ID
		txtDashboardWordWrondSingleWord = (TextView) rootView.findViewById(R.id.txtDashboardWordWrondSingleWord);
		txtDashboardWordWrondSingleWordNumber = (TextView) rootView.findViewById(R.id.txtDashboardWordWrondSingleWordNumber);
		txtDashboardWordWrondWord = (TextView) rootView.findViewById(R.id.txtDashboardWordWrondWord);
		
		// Set text Color
		txtDashboardWordWrondWord.setTextColor(Color.RED);
		
		// Draw Chart
		openChart(listWordWrong, titleChart, titleX, titleY);
		
		// Set words
		StringBuffer strBufferWord = new StringBuffer();
		StringBuffer strBufferNumber = new StringBuffer();
		
		for (int i = 0; i < listWordWrong.size(); ++i) {
			strBufferWord.append(String.valueOf(i+1) + ") " + listWordWrong.get(i).getWord() + ": \n");
			strBufferNumber.append(String.valueOf(listWordWrong.get(i).getNumberOfGuessingWrong()) + "\n");
		}
		
		// Set text
		txtDashboardWordWrondWord.setText(String.valueOf(totalWrongWord));
		txtDashboardWordWrondSingleWord.setText(strBufferWord.toString());
		txtDashboardWordWrondSingleWordNumber.setText(strBufferNumber.toString());
		
		return rootView;
	}
	
	@Override 
	public void onDestroy() {
		super.onDestroy();
		if (listWordWrong != null && !listWordWrong.isEmpty()) {
			listWordWrong.clear();
		}
	};

	private void openChart(
			List<WordWrong> listWrongWord,
			String chartTitle, String XTitle, String YTitle) {

		this.totalWrongWord = 0;
		
		List<String> mXLabels = new ArrayList<String>();
		for (int i = 0; i < listWrongWord.size(); ++i) {
			mXLabels.add(String.valueOf(listWrongWord.get(i).getID() + 1));
		}
		
		int[] x = new int[listWrongWord.size()];

		List<Integer> wrongWord = new ArrayList<Integer>();
		
		int maxValue = 0;
		
		for (int i = 0; i < listWrongWord.size(); ++i) {
			totalWrongWord += listWrongWord.get(i).getNumberOfGuessingWrong();
			
			if (listWrongWord.get(i).getNumberOfGuessingWrong() > maxValue) {
				maxValue = listWrongWord.get(i).getNumberOfGuessingWrong();
			}
			
			wrongWord.add(listWrongWord.get(i)
					.getNumberOfGuessingWrong());
		}
		
		// Make max value divisible by 10
		maxValue = ((maxValue / 10) * 10) + 10;

		// Creating an XYSeries for view word detail
		XYSeries favoriteWordSeries = new XYSeries(legendWrongWord);

		// Adding data to Income and Expense Series

		for (int i = 0; i < x.length; i++) {
			favoriteWordSeries.add(i, wrongWord.get(i));
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(favoriteWordSeries);

		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer favoriteWordRenderer = createRenderer(Color.RED,
				PointStyle.CIRCLE);

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
		multiRenderer.setXAxisMax(listWrongWord.size());
		// setting x axis label align
		multiRenderer.setXLabelsAlign(Align.CENTER);
		// move X labels to the right
		multiRenderer.setXLabelsPadding(30f);

		// Set Y labels
		multiRenderer.setYLabels(listWrongWord.size() + 2);
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

		//setting bar size or space between two bars
		multiRenderer.setBarSpacing(0.5);

		
		// Setting background color of the graph to transparent
		multiRenderer.setBackgroundColor(Color.TRANSPARENT);
		// Setting margin color of the graph to transparent
		multiRenderer.setMarginsColor(getResources().getColor(
				R.color.transparent_background));
		multiRenderer.setApplyBackgroundColor(true);

		// setting x axis point size
//		multiRenderer.setPointSize(4f);
		// setting the margin size for the graph in the order top, left, bottom,
		// right
		multiRenderer.setMargins(new int[] { 150, 170, 150, 30 });

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(favoriteWordRenderer);

		// this part is used to display graph on the xml
		RelativeLayout chartContainer = (RelativeLayout) rootView
				.findViewById(R.id.dashboardWordWrongChart);
		// remove any views before u paint the chart
		chartContainer.removeAllViews();
		// drawing bar chart
//		mChart = ChartFactory.getLineChartView(getActivity(), dataset,
//				multiRenderer);
		mChart = ChartFactory.getBarChartView(getActivity(), dataset, multiRenderer, Type.DEFAULT);
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
//		renderer.setPointStyle(pointStyle);
		// setting stroke of the line chart to solid
//		renderer.setStroke(BasicStroke.SOLID);

		return renderer;
	}
}
