package com.example.getdata;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class NotificationIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public NotificationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        switch (intent.getAction()) {
            case "DECLINE":
                android.os.Handler leftHandler = new android.os.Handler(Looper.getMainLooper());
                leftHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                "You clicked the left button", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case "ANSWER":
                android.os.Handler rightHandler = new android.os.Handler(Looper.getMainLooper());
                rightHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "You clicked the right button", Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
