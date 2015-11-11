package com.example.shoppercart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mylibs.testshop;

import com.example.shoppercart.R;
import com.example.shoppercart.R.id;
import com.example.shoppercart.R.layout;
import com.fragmment.adapter.ListFragment;
import com.fragmment.adapter.StringClass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	ListFragment list = new ListFragment();
	private Context _context;
	private List<StringClass> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;// child text
	

	public ExpandableListAdapter(Context context, ArrayList<StringClass> listDataHeader,
			HashMap<String, List<String>> listChildData) {

		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}

	public ExpandableListAdapter(FragmentActivity activity,
			ArrayList<StringClass> listDataHeader,
			HashMap<String, List<String>> listDataChild) {
		// TODO Auto-generated constructor stub
		this._context = activity;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listDataChild;
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

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);

		txtListChild.setText(childText);
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
		final int rowId = groupPosition;
		//String headerTitle = (String) getGroup(groupPosition);
		
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		final TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(_listDataHeader.get(groupPosition).getname());
		
		final TextView itemname = (TextView) convertView
				.findViewById(R.id.itemsize);
		itemname.setTypeface(null, Typeface.BOLD);
		itemname.setText(_listDataHeader.get(groupPosition).getsize());
		
		CheckBox chkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
		//chkBox.setFocusable(false);
		chkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (buttonView.isChecked()) { 
					 lblListHeader.setPaintFlags(lblListHeader.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					} 
				else{}
			}
        });
	
		Button delButton = (Button) convertView.findViewById(R.id.delButton);
		delButton.setFocusable(false); // remove focus from the button
		delButton.setOnClickListener( // del onclick listener
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
							
						String ab = null;
						testshop.deleteRow(testshop.TABLE_NAME,  
								testshop.TABLE_ROW_ID, ab); //cursorAdapter.getItemId(positionInListView  delete from database Can't use method
						Toast.makeText(_context, "Sucessfully Deleted"  + _listDataHeader.get(rowId).getname(),
						Toast.LENGTH_SHORT).show();
						_listDataHeader.remove(_listDataHeader.get(rowId));
						notifyDataSetChanged();//notify of data change in array
				       
					
			}
					
				});
		      

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
