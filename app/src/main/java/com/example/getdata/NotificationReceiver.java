package com.example.getdata;

import android.Manifest;
import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationReceiver extends BroadcastReceiver {

    Context mContext;
    private Ringtone ringtone;
    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
       context.startService(new Intent(context,NotificationService.class));

       String message = intent.getStringExtra("toastMessage");

       if(message != null)
       {
           Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

           Uri uri = intent.getParcelableExtra("audio_uri");
 //          ringtone = RingtoneManager.getRingtone(context,uri);
   //        ringtone.stop();
           MediaPlayer mp = MediaPlayer. create (context, uri);
           mp.stop();

           int Notificatin_id = intent.getIntExtra("id",0);
           //notificationManager.cancel(Notificatin_id);
     //      notificationManager.cancelAll();
       }


      //  ringtone = (Ringtone) intent.getSerializableExtra("test");
      // System.out.println("id = " + Notificatin_id);

      //  Toast.makeText(context,"Toast is working in receiver",Toast.LENGTH_SHORT).show();
    }
}