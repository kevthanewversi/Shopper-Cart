package com.fragmment.adapter;
 
import com.example.shoppercart.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mylibs.testshop;

import com.example.shoppercart.ExpandableListAdapter;
import com.example.shoppercart.MainActivity;
import com.example.shoppercart.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
 
public class ListFragment extends Fragment {
	testshop shop;
	String item, type;
	//int IdCounter = 0;
	MainActivity mainAct;
	Fragment context = this;
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	Button delButton;
	ArrayList<StringClass> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	

 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.shopping_list, container, false);
        testshop.db = testshop.helper.getWritableDatabase();
		mainAct = new MainActivity();
		showShoppinglist();
		// get the listview
        expListView = (ExpandableListView)rootView.findViewById(R.id.lvExp);
        delButton = (Button)rootView.findViewById(R.id.delButton);
        // preparing list data
        
 
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setGroupIndicator(null);
        //mainAct.viewPager.getAdapter().notifyDataSetChanged();//updating the fragment each and everytime the listfragment is selected/swiped to
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                //Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
	// Listview Group expanded listener
    expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

        @Override
        public void onGroupExpand(int groupPosition) {
            /*Toast.makeText(this,
                    listDataHeader.get(groupPosition) + " Expanded",
                    Toast.LENGTH_SHORT).show();*/
        }
    });

    // Listview Group collasped listener
    expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

        @Override
        public void onGroupCollapse(int groupPosition) {
           /* Toast.makeText(getApplicationContext(),
                    listDataHeader.get(groupPosition) + " Collapsed",
                    Toast.LENGTH_SHORT).show();*/

        }
    });

    // Listview on child click listener
    expListView.setOnChildClickListener(new OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                int groupPosition, int childPosition, long id) {
            
            /*Toast.makeText(
                    getApplicationContext(),
                    listDataHeader.get(groupPosition)
                            + " : "
                            + listDataChild.get(
                                    listDataHeader.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT)
                    .show();*/
            return false;
        }
    });
         
        return rootView;
    }
    public void showShoppinglist() {
		String[] columns = new String[] { testshop.TABLE_ROW_ID,
				testshop.TABLE_ROW_ONE, testshop.TABLE_ROW_TWO };
		Cursor cursor = testshop.db.query(testshop.TABLE_NAME, columns, null, null,
				null, null, null);
		  listDataHeader = new ArrayList<StringClass>();
		    listDataChild = new HashMap<String, List<String>>();
		cursor.moveToFirst();//move the cursor to the first item
		if (!cursor.isAfterLast()) {
			do {
				prepareListData(cursor);
				
				
			} while (cursor.moveToNext());
		}
		//cursor.close(); // closing of the cursor
		 // closing the database
	}
	/*
	 * Preparing the list data
	 */
	
	
	
	public void prepareListData(final Cursor items) {
	  
		StringClass c = new StringClass();
	    // Adding child data
	    c.setname((items.getString(1)));
	    c.setsize((items.getString(2)));
	  listDataHeader.add(c);
	    // Adding child data
	    List<String> top250 = new ArrayList<String>();
	    top250.add("Born Sinner");

	    //listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
	   
	}
   
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser){//saved my life
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			testshop.db = testshop.helper.getWritableDatabase();
			showShoppinglist();
			listAdapter.notifyDataSetChanged();
			Log.d(getTag(), "WORKS LIKE A CHARM");
			
			}
		else{}
	}
	
  public  String shareList(){
	  testshop.db = testshop.helper.getWritableDatabase();
	  String[] columns = new String[] { testshop.TABLE_ROW_ID,
				testshop.TABLE_ROW_ONE, testshop.TABLE_ROW_TWO };
		Cursor listcursor= testshop.db.query(testshop.TABLE_NAME, columns, null, null,
				null, null, null);
		listcursor.moveToFirst();//move the cursor to the first item
           String d = "";
		if (!listcursor.isAfterLast()) {
			do {
			String a = listcursor.getString(1);
			String b = listcursor.getString(2);
			String c = a + "   " + b;
			 d = d + "  \n" + c;
	         
			} while (listcursor.moveToNext());
			
		}
		//Log.d(getTag(), d );
	 return d;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		showShoppinglist();
		setUserVisibleHint(true);
		listAdapter.notifyDataSetChanged();
		}

}