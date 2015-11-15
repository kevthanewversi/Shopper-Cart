package com.fragmment.adapter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import com.example.shoppercart.R;

public class AlarmService extends Service
{
      
   private NotificationManager mManager;
 
    @Override
    public IBinder onBind(Intent arg0)
    {
       // TODO Auto-generated method stub
        return null;
    }
 
    @Override
    public void onCreate()
    {
       // TODO Auto-generated method stub 
       super.onCreate();
    }
 
   @SuppressWarnings("static-access")
   @Override
   public void onStart(Intent intent, int startId)
   {
       super.onStart(intent, startId);
      
       mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
       Intent intent1 = new Intent(this.getApplicationContext(),AlertFragment.class);
     
       Notification notification = new Notification(R.drawable.ic_launcher,"You need to go Shopping", System.currentTimeMillis());
       intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
 
       PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
       notification.flags |= Notification.FLAG_AUTO_CANCEL;
       notification.setLatestEventInfo(this.getApplicationContext(), "Shopper  Cart", "You need to go Shopping", pendingNotificationIntent);
 
       mManager.notify(0, notification);
        
       Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

       if(alert == null){
           // alert is null, using backup
           alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

           // I can't see this ever being null (as always have a default notification)
           // but just incase
           if(alert == null) {  
               // alert backup is null, using 2nd backup
               alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);                
           }
       }
       Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alert);
       r.play();//need to issue dialog box to user to stop alarm
    }
 
    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
 
}