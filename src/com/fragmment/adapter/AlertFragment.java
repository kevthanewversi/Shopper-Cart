package com.fragmment.adapter;
import java.util.Calendar;

import com.example.shoppercart.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

 
public class AlertFragment extends Fragment implements OnItemSelectedListener
{  
  Context c;
    private PendingIntent pendingIntent;
    TimePicker tp;
    int period;
    Object value;
    AlarmManager alarmManager;
     String [] shopperiod= new String[] {"1 week","2 weeks","3 weeks","1 month","1.5 months","2 months"};
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.alert_view, container, false);
         tp = (TimePicker)rootView.findViewById(R.id.timePicker1);
         Spinner spinner = (Spinner)rootView.findViewById(R.id.spinner1);
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, shopperiod);
    
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(adapter);
         spinner.setOnItemSelectedListener(this);
     
         Button tButton = (Button)rootView.findViewById(R.id.toggleButton1);
 tButton.setOnClickListener(	new View.OnClickListener()
	{
		@Override 
		public void onClick(View v) { {
		Log.d(getTag(), "M" + period);	
		setAlarm();
 }}});
         Button cancel = (Button)rootView.findViewById(R.id.CancelButton);
         cancel.setOnClickListener(new View.OnClickListener(){
     		@Override 
    		public void onClick(View v) { {
    	CancelAlarm();
     }}});
      return rootView;}
    
	@Override //spinnner with a switch to get the period the users selects
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		 
		 switch(position){  
		 case 0: period =1;
		      break;
		 case 1: period =2;
			 break;
		 case 2: period =21;
			 break;
		 case 3: period =28;
			 break;
		 case 4: period = 42;
			 break;
		 case 5: period = 56;
			 break;
		 }
		 
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}
	 public void setAlarm(){
		  
	    	
	    	Calendar calendar = Calendar.getInstance();
	   
	        calendar.set(Calendar.HOUR_OF_DAY,tp.getCurrentHour());
	        calendar.set(Calendar.MINUTE, tp.getCurrentMinute());
	        calendar.set(Calendar.SECOND, 0);
	    	Intent myIntent = new Intent(getActivity(), LeReceiver.class);
	        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent,0); //System.currentTimeMillis() + period*24*60*60*1000
	   
	  	 alarmManager = (AlarmManager)getActivity().getSystemService(FragmentActivity.ALARM_SERVICE);
	  	 alarmManager.setRepeating(alarmManager.RTC_WAKEUP, calendar.getTimeInMillis() +  period*60*1000, period*60*1000, pendingIntent);
	  	 Toast.makeText(getActivity(),"Next Shopping alert in" + period + "days", Toast.LENGTH_SHORT).show();
	    }

    public void CancelAlarm(){
    	alarmManager.cancel(pendingIntent);//cancelling the alarm
    	Toast.makeText(getActivity(),"Alarm cancelled", Toast.LENGTH_SHORT).show();
    }
}