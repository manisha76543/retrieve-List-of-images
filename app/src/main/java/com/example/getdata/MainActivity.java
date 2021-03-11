package com.example.getdata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import okhttp3.internal.Util;

public class MainActivity extends AppCompatActivity {


    private CardView uploadCard,viewCard,uploadAudio,retrieveAudioCard,logCard;

    final int REQUEST_PERMISSION_CODE = 1000;
    RemoteViews remoteViews;

    FirebaseDatabase database;
    DatabaseReference reference;
    private Ringtone ringtone;
    NotificationService localService;
    private boolean isBound = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference = database.getInstance().getReference().child("ABC");

        /*

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists())
//                {
//                    int id = (int) snapshot.getChildrenCount();
//
//                }
                String s = snapshot.getValue().toString();

                if(s!=null)
                {
                    notification(s);
                  //  ContextCompat.startForegroundService(this,notification(s));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

               // notification(snapshot.getValue().toString());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

*/


        Intent i = new Intent(this,NotificationService.class);
        startService(i);

        if(i != null) {
//            startActivity(i);
        //    startActivityForResult(i, 0);
        }

        Log.d("Tag main i = ", i.toString());

        stopService(i);

     //   bindService(i,connection,Context.BIND_AUTO_CREATE);
      //  unbindService(connection);




        uploadCard = (CardView) findViewById(R.id.Upload_card);
        viewCard = (CardView) findViewById(R.id.View_card);
        uploadAudio = (CardView) findViewById(R.id.upload_audio);
        retrieveAudioCard = (CardView) findViewById(R.id.Retrieve_audio);
        logCard = (CardView) findViewById(R.id.logs);


        uploadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, UploadActivity.class));

            }
        });

        viewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, ShowActivity.class));

            }
        });

        logCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, LogActivity.class));

            }
        });

        retrieveAudioCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, RetrieveAudioActivity.class));

            }
        });
/*
        uploadAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkPermissionFromDevice())
                {
                    startActivity(new Intent(MainActivity.this, UploadAudioActivity.class));
                }
                else
                {
                    if(Build.VERSION.SDK_INT >= 22)
                    requestPermission();
                }

            }
        });
*/

        uploadAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, PickAudioActivity.class));

            }
        });
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

           NotificationService.LocalBinder binder = (NotificationService.LocalBinder) service;
            localService = binder.getService();
            isBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            isBound = false;

        }
    };


/*
    public void notification(String message)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("n","n",NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_call_notification);
         //   remoteViews.setImageViewResource(R.id.btnAnswer,R.id.btnAnswer);
            remoteViews.setImageViewResource(R.drawable.ic_baseline_music_note_24, R.id.btnAnswer);
            remoteViews.setTextViewText(R.id.name,"Notification new");
            remoteViews.setTextViewText(R.id.callType, "Incoming Notification");
          //  setListener(remoteViews);




//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .build();
//
//            String CHANNEL_ID = BuildConfig.APPLICATION_ID.concat("notification_id");
//            String CHANNEL_NAME = BuildConfig.APPLICATION_ID.concat("notification_name");
//
//            assert manager != null;


        }

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Intent intent1 = new Intent(MainActivity.this,goActivity.class);

     //   intent1.setAction("left");

        intent1.putExtra("yes",true);
        intent1.putExtra("uri",uri);

        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
        | Intent.FLAG_ACTIVITY_NEW_TASK);




        PendingIntent pendingIntent1 = PendingIntent.getActivity(MainActivity.this,0,intent1,PendingIntent.FLAG_ONE_SHOT);
   //     remoteViews.setOnClickPendingIntent(R.id.btnAnswer,pendingIntent1);
        Intent intent2 = new Intent(MainActivity.this,goActivity.class);

        intent2.putExtra("No",false);
        intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent2 = PendingIntent.getActivity(MainActivity.this,0,intent2,PendingIntent.FLAG_ONE_SHOT);



        ringtone = RingtoneManager.getRingtone(MainActivity.this,uri);
            ringtone.play();


 //       remoteViews.setOnClickPendingIntent(R.id.btnAnswer,pendingIntent1);

        NotificationCompat.Builder builder =
               new NotificationCompat.Builder(this,"n");
//        builder.setContentTitle("Notification")
//                            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
//                                   .setAutoCancel(true)
//                                   .setContentText(message)
//                                   .setSound(uri)
//                                   .setAutoCancel(true)
//                                   .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                   .setCategory(NotificationCompat.CATEGORY_CALL)
//                                   //.addAction(R.drawable.ic_launcher_foreground,"Pick",pendingIntent1)
//                                   //.addAction(R.drawable.ic_launcher_background,"Cancel",pendingIntent2)
//                                   .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
//                                     .setCustomContentView(remoteViews)
//                                    .setContent(remoteViews)
//                                     .setTicker("Tiker Text")
//                               .setCustomBigContentView(remoteViews)
//                               .setColor(R.drawable.bg1);
//
//
//                       builder.setContentIntent(pendingIntent1)
//                .setFullScreenIntent(pendingIntent1,true)
//                ;


        builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setTicker("Tiker Text")
                .setContentText(message)
                .setContent(remoteViews);
      //  remoteViews.setOnClickPendingIntent(R.id.btnAnswer,pendingIntent1);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());

    }
*/

/*
    public void notification(String message)
    {

        Intent i = new Intent(this,NotificationReceiver.class);
       i.putExtra("message",message);
//        PendingIntent pi = PendingIntent.getBroadcast(this.getApplicationContext(),0,i,0);
        sendBroadcast(i);

    //   Intent iService = new Intent(this,NotificationService.class);
      //  ContextCompat.startForegroundService(this,iService);


/*
        // adding action to left button
        Intent leftIntent = new Intent(this, goActivity.class);
        leftIntent.setAction("DECLINE");
        leftIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        // adding action to right button
      //  Intent rightIntent = new Intent(this, NotificationIntentService.class);
        Intent rightIntent = new Intent(this, goActivity.class);
        rightIntent.setAction("ANSWER");


        Intent button_intent = new Intent("Button clicked");

        PendingIntent p_button_intent = PendingIntent.getService(this,123,button_intent,0);


        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.custom_call_notification);
        collapsedView.setTextViewText(R.id.name, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        collapsedView.setTextViewText(R.id.callType, message);


        collapsedView.setOnClickPendingIntent(R.id.btnAnswer, PendingIntent.getService(this, 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));
     // collapsedView.setOnClickPendingIntent(R.id.btnDecline, PendingIntent.getService(this, 1, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        collapsedView.setOnClickPendingIntent(R.id.btnDecline,p_button_intent);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(MainActivity.this,uri);
        ringtone.play();

      //  Notification foregroundNote;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                // these are the three things a NotificationCompat.Builder object requires at a minimum
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("title")
                .setContentText(message)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                // notification will be dismissed when tapped
                .setAutoCancel(true)
                // tapping notification will open MainActivity
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, goActivity.class), 0))
                // setting the custom collapsed views
                .setCustomContentView(collapsedView)
        .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        // retrieves android.app.NotificationManager
        NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());


    }
*/





    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                 Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO

        },REQUEST_PERMISSION_CODE);
/*
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSION_CODE);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSION_CODE);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now
           // startActivity(new Intent(MainActivity.this, UploadAudioActivity.class));
        }
*/


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_PERMISSION_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "permission granted",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, UploadAudioActivity.class));
                }
            }
            break;
        }
    }


    private boolean checkPermissionFromDevice()
    {
        int Write_external_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        int Read_external_storage = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        return Write_external_storage == PackageManager.PERMISSION_GRANTED && record_audio_result  == PackageManager.PERMISSION_GRANTED
                &&  Read_external_storage == PackageManager.PERMISSION_GRANTED;
    }
}