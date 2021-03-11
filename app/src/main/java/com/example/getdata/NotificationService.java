package com.example.getdata;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
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

import java.nio.channels.Channel;
import java.util.List;

public class NotificationService extends Service {

    private Ringtone ringtone;
    FirebaseDatabase database;
    DatabaseReference reference;
    Context context;
    public Handler handler = null;
    public static Runnable runnable = null;

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

                String s = snapshot.getValue().toString();

                if(!s.equals("") || s.length()!=0) {
                    System.out.println("String = " + s);

/*
                    NotificationCompat.Builder notif = new NotificationCompat.Builder(context,"n");
                    Notification notification = notif.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                           .setContentTitle("hello")
                            .setCategory(NotificationCompat.CATEGORY_SERVICE)
                            .setContentText("what is text ")
                           .build();


//                    NotificationManager mNotificationManager =
//                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                   mNotificationManager.notify(1, notification.build());

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
                    managerCompat.notify(999,notif.build());

                    startForeground(1, notification);*/


                  //  createNotificationChannel();

                    /*
                    Notification notification = new NotificationCompat.Builder(context, "NOTIFICATION_CHANNEL")
                            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                            .setContentTitle("Title")
                            .setContentText("Content").build();

                    startForeground(1001, notification);*/


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "n")
                            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                            .setContentTitle("My notification")
                            .setContentText("Much longer text that cannot fit one line...")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setChannelId("n");

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                  //  NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        String channelId = "channel";
                        NotificationChannel ChannelN = new NotificationChannel(channelId, "title",NotificationManager.IMPORTANCE_HIGH);
                        notificationManager.createNotificationChannel(ChannelN);
                        builder.setChannelId(channelId);
                    }


                   notificationManager.notify(0, builder.build());

                   startForeground(1,builder.build());

                 //  Boolean isServiceRunning = isServiceRunning(context,NotificationService.class);


                  //  Log.d("Tag last ", "TAG LAST");

                }

            }

            private Boolean isServiceRunning(Context applicationContext, Class<NotificationService> notificationServiceClass) {


                final ActivityManager activityManager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
                final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

                for(ActivityManager.RunningServiceInfo runningServiceInfo : services)
                {
                    Log.d("Tag new",String.format("Service:%s", runningServiceInfo.service.getClassName()));
                    if(runningServiceInfo.service.getClassName().equals(notificationServiceClass.getName()))
                    {
                        return true;
                    }
                }
                return false;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            public void createNotificationChannel() {
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    CharSequence name = "Channel name";
                    String description = "Description";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel("NOTIFICATION_CHANNEL", name, importance);
                    channel.setDescription(description);
                    NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }
            }

        });



    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
       // Notification notification = showNotification();;
        //startForeground(1, notification);


        Log.d("Tag message", "OnStartCommand called");
       // stopSelf();
        return START_STICKY;

    }


    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder
    {
        NotificationService getService()
        {
            return NotificationService.this;
        }
    }


/*
    private Notification showNotification(Context context, String message) {

        // adding action to left button
        Intent leftIntent = new Intent(context, goActivity.class);
        leftIntent.setAction("DECLINE");
        // leftIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        // adding action to right button
        //  Intent rightIntent = new Intent(context, NotificationService.class);
        Intent rightIntent = new Intent(context, goActivity.class);
        rightIntent.setAction("ANSWER");


        Intent button_intent = new Intent("Button clicked");

        PendingIntent p_button_intent = PendingIntent.getService(context,123,button_intent,0);


     //   RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.custom_call_notification);
       // collapsedView.setTextViewText(R.id.name, DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        //collapsedView.setTextViewText(R.id.callType, message);


        //collapsedView.setOnClickPendingIntent(R.id.btnAnswer, PendingIntent.getService(context, 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        // collapsedView.setOnClickPendingIntent(R.id.btnDecline, PendingIntent.getService(this, 1, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        //collapsedView.setOnClickPendingIntent(R.id.btnDecline,p_button_intent);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(context,uri);
        // ringtone.play();



        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, goActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,"n")
                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                        .setContentTitle("My notification")
                    //    .setCustomContentView(collapsedView)
                        .setFullScreenIntent(contentIntent,true)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setContentText(message)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setOngoing(true)
                ;

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());


        return mBuilder.build();

    }
*/


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
       // return binder;

    }

    /*
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);

    }
    */
}
