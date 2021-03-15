package com.example.getdata;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.nio.channels.Channel;
import java.util.List;

public class NotificationService extends Service {

    private Ringtone ringtone;
    FirebaseDatabase database;
    DatabaseReference reference;
    Context context;
    public Handler handler = null;
    public static Runnable runnable = null;

/*
    private static class MyAsyncTask  extends AsyncTask{

        WeakReference<NotificationService> notificationServiceWeakReference;

        public MyAsyncTask(NotificationService notificationService)
        {
            this.notificationServiceWeakReference = new WeakReference<>(notificationService);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
*/

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("Tag message", "OnCreate called");
/*
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                Toast.makeText(context,"Service is still running", Toast.LENGTH_LONG).show();
                handler.postDelayed(runnable,10000);
            }
        };
        handler.postDelayed(runnable,15000);
*/

        reference = database.getInstance().getReference().child("ABC");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int channelId= 123;
                String channelId_string = "123";
                int NOTIFICATION_ID = 1000;
                String s = snapshot.getValue().toString();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


               // if(!s.equals("") || s.length()!=0)
                {
                    System.out.println("String = " + s);

//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "n")
//                            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
//                            .setContentTitle("My notification")
//                            .setContentText("Much longer text that cannot fit one line...")
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                        notificationManager.notify(1234,builder.build());
//
//                   startForeground(1,builder.build());


        // adding action to left button


        Intent leftIntent = new Intent(getApplicationContext(), goActivity.class);
        leftIntent.setAction("DECLINE");
      //  Intent rightIntent = new Intent(this, NotificationIntentService.class);
        Intent rightIntent = new Intent(getApplicationContext(), goActivity.class);
        rightIntent.setAction("ANSWER");
        Intent button_intent = new Intent("Button clicked");
        PendingIntent p_button_intent = PendingIntent.getService(getApplicationContext(),123,button_intent,0);


        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.custom_call_notification);
        collapsedView.setTextViewText(R.id.name, DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        collapsedView.setTextViewText(R.id.callType, s);


       // collapsedView.setOnClickPendingIntent(R.id.btnAnswer, PendingIntent.getService(getApplicationContext(), 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));
     // collapsedView.setOnClickPendingIntent(R.id.btnDecline, PendingIntent.getService(this, 1, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        //collapsedView.setOnClickPendingIntent(R.id.btnDecline,p_button_intent);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
     //   ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
       // ringtone.play();
                    MediaPlayer mp = MediaPlayer. create (getApplicationContext(), uri);
                    mp.start();

          // Intent activityIntent = new Intent(getApplicationContext(),MainActivity.class);
         //  PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,activityIntent,0);
            //PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),NotificationReceiver.class),0);

                        Intent broadCastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
                        broadCastIntent.putExtra("toastMessage", s);
                        broadCastIntent.putExtra("audio_uri", uri);
                        broadCastIntent.putExtra("id", NOTIFICATION_ID);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


       NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"n")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("title")
                .setContentText(s)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
               .setPriority(Notification.PRIORITY_DEFAULT)

              // .setOnlyAlertOnce(true)
               //.setColor(Color.RED)
               //.addAction(R.id.btnDecline,"Decline",p_button_intent)
               //.setColor(Color.GREEN)
               //.addAction(R.id.btnAnswer,"Answer",pendingIntent)
               //.setSound(uri)

               .setChannelId("n")
               .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), NotificationReceiver.class), 0))
                .setCustomContentView(collapsedView)
        .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        builder.build().flags|=Notification.FLAG_AUTO_CANCEL;

                    collapsedView.setOnClickPendingIntent(R.id.btnDecline,pendingIntent);
                    collapsedView.setOnClickPendingIntent(R.id.btnAnswer,pendingIntent);

       // NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);


                    if(!s.equals("") || s.length()!=0)
                    {
                        startForeground(NOTIFICATION_ID,builder.build());
                       // builder.setSound(uri);
                       // builder.build().flags|=Notification.FLAG_AUTO_CANCEL;
                       // notificationManager.notify(1,builder.build());
       //                 ringtone.play();
                    }
                    else
                    {
                       // notificationManager.cancel(Integer.parseInt(channelId));
                       // notificationManager.cancel(NOTIFICATION_ID);
                        notificationManager.cancelAll();

                    }

                   //  notificationManager.notify(0, builder.build());
                   // startForeground(1,builder.build());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


/*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
       // Notification notification = showNotification();;
        //startForeground(1, notification);


        Log.d("Tag message", "OnStartCommand called");
       // stopSelf();
        return START_STICKY;

    }

*/
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder
    {
        NotificationService getService()
        {
            return NotificationService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       // return null;
        return binder;

    }

    /*
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);

    }
    */
}
