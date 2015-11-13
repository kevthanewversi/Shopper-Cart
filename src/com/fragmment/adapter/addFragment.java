package com.fragmment.adapter;
 

import com.example.shoppercart.MainActivity;
import com.example.shoppercart.R;
import mylibs.testshop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 

public class addFragment extends Fragment {
	// the text fields that users input new data into
	EditText 	textFieldOne, textFieldTwo ;
    
	// the buttons that listen for the user to select an action
	Button 		addButton;
 
	// the class that opens or creates the database and makes sql calls to it
	testshop db;
	ListFragment list = new ListFragment();
    tabAdapter tab = new tabAdapter(getFragmentManager());
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
         
        
        addFragment context = this;
    	
    	
    	setupViews(rootView);
    	 
        /** Called when the activity is first created. */
       
        	
    	      
     
    	        // create the database manager object
    	        db = new testshop(getActivity());
     
    	        // make the buttons clicks perform actions
    	        addButtonListeners();

    	        testshop.db.close();
        	
    	        return rootView;}
       
     
 
    public  void setupViews(View v) {
    	textFieldOne = (EditText)v.findViewById(R.id.item_name);
        textFieldTwo = (EditText)v.findViewById(R.id.item_amount);
 
        // THE BUTTON
         addButton = 		(Button)v.findViewById(R.id.add_button); 
 }

	/**
     * adds listeners to the button and sets it to call relevant method/s
     */
    private void addButtonListeners()
    {
        addButton.setOnClickListener
    	(
    		new View.OnClickListener()
	    	{ 
				@Override public void onClick(View v) {
					if(textFieldOne.getText().toString().equals("")){
		    			Toast.makeText(getActivity(), "Enter an item name", Toast.LENGTH_SHORT).show();
		    		}
					else{	
					addRow();}
					
					  }
			}
    	);}
 
 
 
 
    /**
     * adds a row to the database based on information contained in the
     * add row fields.
     */
    private void addRow()
    {
    	try
    	{
    		// ask the database manager to add a row given the two strings
    		db.addRow
    		(  
    		      textFieldOne.getText().toString(),     
    				textFieldTwo.getText().toString()
    		);
 
    		// request the table be updated
	    	//updateTable();
    		CloseMainandShowList();
			// remove all user input from the Activity
    		emptyFormFields();
    	}
    	catch (Exception e)
    	{
    		Log.e("Add Error", e.toString());
    		e.printStackTrace();
    	}
    }
 
    private void CloseMainandShowList() {
 ((MainActivity)getActivity()).getSupportActionBar().setSelectedNavigationItem(1);
  list.showShoppinglist();
  tab.notifyDataSetChanged(); 

/*Fragment Defrag = null;
 Defrag = getFragmentManager().findFragmentById(R.id.ListFragment);
 if (Defrag == null ){
	     Log.d(getTag(),"Duuuuuu");  
 }
 final FragmentTransaction Ft = getFragmentManager().beginTransaction();
 //String Tag1 = "TAG2";
//Ft.add(list,Tag1);
 Ft.detach(Defrag);
 Ft.attach(Defrag);
 Ft.commit();*/
 
	}
 
 
 
    /**
     * deletes a row from the database with the id number in the corresponding 
     * user entry field
     */
    public  void deleteRow(String tableName,String compareColumn,String itemId)
    {
    	try
    	{  testshop.db = testshop.helper.getWritableDatabase();
    		// ask the database manager to delete the row with the give rowID. 
    	testshop.deleteRow(tableName,compareColumn, itemId);
 
    		// request the table be updated
    		//updateTable();
 
			// remove all user input from the Activity
    		//emptyFormFields();
    	}
    	catch (Exception e)
    	{
    		Log.e("Delete Error", e.toString());
    		e.printStackTrace();
    	}
    }
 
 
 
    /**
     * helper method to empty all the fields in all the forms.
     */
    private void emptyFormFields()
    {
        textFieldOne.setText("");
        textFieldTwo.setText("");
    }

   
	}
    
