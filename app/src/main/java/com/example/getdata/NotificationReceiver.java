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
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NotificationReceiver extends BroadcastReceiver {

    Context mContext;
    private Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(0,0,0,0,
               //ActionBar.LayoutParams.MATCH_PARENT,
             //   ActionBar.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT
        );

        params.screenBrightness = 20f;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.TOP;
       // LinearLayout ly = new LinearLayout(context);
        //ly.setBackgroundColor(Color.RED);
        //ly.setOrientation(LinearLayout.VERTICAL);


       // RelativeLayout overlay = (RelativeLayout) inflater.inflate(R.layout.player_sheet,null);
     //   wm.addView(overlay,params);

      //  wm.addView(ly,params);


        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_call_notification, null);
       // ImageView button = dialogView.findViewById(R.id.);
        builder.setView(dialogView);
        final AlertDialog alert = builder.create();
        alert.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        alert.setCanceledOnTouchOutside(true);
        alert.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = alert.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setGravity(Gravity.TOP);
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //close the service and remove the from from the window
//                alert.dismiss();
//            }
//        });








        String message = intent.getStringExtra("message");
       // showNotification(context,message);

        this.mContext=context;
//        if (intent != null && intent.getExtras() != null) {
//
//            String action ="";
//            action=intent.getStringExtra("ACTION_TYPE");
//
//            if (action != null&& !action.equalsIgnoreCase("")) {
//              //  performClickAction(context, action);
//            }
//
//            // Close the notification after the click action is performed.
//            Intent iclose = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//            context.sendBroadcast(iclose);
//            context.stopService(new Intent(context, NotificationService.class));
//
//        }


    }


    private void showNotification(Context context,String message) {

      //  Notification notification = showNotification();;
        //startForeground(1, notification);

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


        RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.custom_call_notification);
        collapsedView.setTextViewText(R.id.name, DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        collapsedView.setTextViewText(R.id.callType, message);


        collapsedView.setOnClickPendingIntent(R.id.btnAnswer, PendingIntent.getService(context, 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        // collapsedView.setOnClickPendingIntent(R.id.btnDecline, PendingIntent.getService(this, 1, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        collapsedView.setOnClickPendingIntent(R.id.btnDecline,p_button_intent);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(context,uri);
       // ringtone.play();



        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, goActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,"n")
                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                        .setContentTitle("My notification")
                        .setCustomContentView(collapsedView)
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



    }



/*
    private void performClickAction(Context context, String action) {
        if(action.equalsIgnoreCase("ANSWER")) {

            if (checkAppPermissions()) {
                Intent intentCallReceive = new Intent(mContext, goActivity.class);
                intentCallReceive.putExtra("Call", "incoming");
                intentCallReceive.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intentCallReceive);
            }
            else{
                Intent intent = new Intent(mContext, goActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("CallFrom","call from push");
                mContext.startActivity(intent);

            }
        }
        else if(action.equalsIgnoreCase("DECLINE")){

            // show ringing activity when phone is locked
            Intent intent = new Intent(mContext, goActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        }

        else {
            context.stopService(new Intent(context, NotificationService.class));
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        }
    }
    */

    private Boolean checkAppPermissions() {
        return hasReadPermissions() && hasWritePermissions() && hasCameraPermissions() && hasAudioPermissions();
    }

    private boolean hasAudioPermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }
    private boolean hasCameraPermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }




}
