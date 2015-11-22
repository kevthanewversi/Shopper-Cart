package com.fragmment.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LeReceiver extends BroadcastReceiver {
	 @Override
	    public void onReceive(Context context, Intent intent)
	    {
	    	Log.d(getResultData(),"Time Up");
	       Intent service1 = new Intent(context, AlarmService.class);
	       context.startService(service1);
	    }}
