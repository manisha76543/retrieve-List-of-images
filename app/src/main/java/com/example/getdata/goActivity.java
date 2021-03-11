package com.example.getdata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class goActivity extends AppCompatActivity {

    TextView tv;
    Ringtone ringtone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);

        tv = (TextView) findViewById(R.id.text_View);

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        manager.cancelAll();
      //  Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Uri uri = getIntent().getData();

        ringtone = RingtoneManager.getRingtone(goActivity.this,uri);

        if(getIntent().hasExtra("Pick"))
        {
            tv.setText("You picked the call");
            tv.setTextColor(Color.GREEN);
        }
        else
        {
            tv.setText("You canceled the call");
            tv.setTextColor(Color.RED);
        }
        ringtone.stop();

    }
}