package com.example.shoppercart;


import com.fragmment.adapter.*;
import com.fragmment.adapter.ListFragment;

import mylibs.testshop;
import android.annotation.TargetApi;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater; 
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener, TabListener {
	//explicitly importing the specific ListFragment.java class
	//since there's also another ListFragment for support library v4
    com.fragmment.adapter.ListFragment list = new com.fragmment.adapter.ListFragment();
	public ViewPager viewPager;
	private tabAdapter mAdapter;
	private android.app.ActionBar actionBar;
	ListFragment listFr; //Instance of ListFragment used to call shareList()
	// Tab titles
	private String[] tabs = { "ADD TO LIST", "SHOPPING LIST", "ALERTS" };

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initilization-
		viewPager = (ViewPager) findViewById(R.id.fragment_pager);
		actionBar = getActionBar();
		mAdapter = new tabAdapter(getSupportFragmentManager());
		listFr = new ListFragment();
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
	 
	
	viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
		 
        @Override
        public void onPageSelected(int position) {
            // on changing the page
            // make respected tab selected
            actionBar.setSelectedNavigationItem(position);
            
            
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    });
}
	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		   // TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		 viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(android.support.v7.app.ActionBar.Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(android.support.v7.app.ActionBar.Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(android.support.v7.app.ActionBar.Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	  } 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override 
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId())  {
		case R.id.action_share:
			String sharetext = listFr.shareList();
		     Intent sharing  = new Intent(android.content.Intent.ACTION_SEND);
		     sharing.setType("text/plain");
		     sharing.putExtra(android.content.Intent.EXTRA_TEXT, sharetext);
		     startActivity(Intent.createChooser(sharing, "Share List"));
		break;
		}
		return true;
	}
}