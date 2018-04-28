package com.example.thesis.object.adapter;

import java.util.HashMap;
import java.util.List;

import com.example.thesis.R;
import com.example.thesis.object.SingleRowWithImage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ExpandableListWithImageAdapter extends BaseExpandableListAdapter{

	private static Context _context;
	
	// header titles
    private List<String> _listDataHeader;
    // child data in format of header title, child title
    private HashMap<String, List<SingleRowWithImage>> _listDataChild;
 
    public ExpandableListWithImageAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<SingleRowWithImage>> listChildData) {
        ExpandableListWithImageAdapter._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
	
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
    	final int childImage = _listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition).getResid();
        final String childText = _listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition).getText();
        
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) ExpandableListWithImageAdapter._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_with_image, null);
        }
 
        ImageView imgListChildImage = (ImageView) convertView
                .findViewById(R.id.lblListImage);
        TextView txtListChildText = (TextView) convertView
                .findViewById(R.id.lblListText);
 
        imgListChildImage.setImageResource(childImage);
        txtListChildText.setText(childText);
        
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) ExpandableListWithImageAdapter._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
