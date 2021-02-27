package com.example.getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class UploadAudioActivity extends AppCompatActivity {

    ImageButton recordImageOfAudio;
  //  EditText recordinText;
  Button btnPlay,btnStop;

    MediaPlayer mediaPlayer;
    String AudiofilePath,recordFile;
    Boolean Recording = false;
    MediaRecorder mediaRecorder;
    //private int PERMISSION_CODE = 21;
    final int REQUEST_PERMISSION_CODE = 1000;
    StorageReference root;

    private Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_audio);

/*

        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();
                    }
                }).check();
*/

        recordImageOfAudio = (ImageButton) findViewById(R.id.imageView_record);
        //recordinText = (EditText) findViewById(R.id.tab_to_record);
        timer = (Chronometer) findViewById(R.id.record_timer);
        btnPlay = (Button) findViewById(R.id.buttonPlay);
        btnStop = (Button) findViewById(R.id.stopPlay);


        recordImageOfAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Recording == false) {

                  //  if (checkPermissionFromDevice())
                    {
                        recordAudio();
                    }
//                    else
//                    {
//                        if(Build.VERSION.SDK_INT >= 22)
//                           // requestPermission();
//                    }


                } else {
                    stopAudio();
                }

                Recording = !Recording;
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudiofilePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(UploadAudioActivity.this, "Playing...", Toast.LENGTH_SHORT).show();
                }catch (IOException e)
                {
                    e.printStackTrace();
                    System.out.println("error in play " + e.getMessage());
                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer != null)
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();

                }

            }
        });


    }

    public void recordAudio()
    {
      //  Recording = true;
        timer.setBase(SystemClock.elapsedRealtime());

        SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CANADA);
        Date now = new Date();

        AudiofilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.3gp";
        recordFile = "recording_" + formater.format(now) + ".mp3";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.reset();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
      //  mediaRecorder.setOutputFile(AudiofilePath + recordFile);
        mediaRecorder.setOutputFile(AudiofilePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {

            mediaRecorder.prepare();
            mediaRecorder.start();
            timer.start();
            Toast.makeText(UploadAudioActivity.this, "Recording...", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("SEE ERROR " + e.getMessage());
        }
    }

    public void stopAudio()
    {
        if(mediaRecorder != null) {
            try {
                timer.stop();
                mediaRecorder.stop();
                mediaRecorder.reset();
                mediaRecorder.release();
               // uploadAudio();
//                mediaRecorder=null;
                Toast.makeText(UploadAudioActivity.this, " Stop Recording", Toast.LENGTH_SHORT).show();

            } catch (IllegalStateException ise) {
                ise.printStackTrace();
                System.out.println("Stop error " + ise.getMessage());

            }
        }
    }

/*
    private void uploadAudio()
    {
        Uri uri = Uri.fromFile(new File(AudiofilePath));
        StorageReference filePath = root.child("Upload Audio").child(uri.getLastPathSegment());
        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

               // startActivity(new Intent(UploadAudioActivity.this,MainActivity.class));

            }
        });
    }
*/

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_PERMISSION_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "permission granted",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UploadAudioActivity.this, UploadAudioActivity.class));
                }
            }
            break;
        }
    }
*/

/*
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO

        }, REQUEST_PERMISSION_CODE);
    }


    private boolean checkPermissionFromDevice()
    {
        int Write_external_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        int Read_external_storage = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        return Write_external_storage == PackageManager.PERMISSION_GRANTED && record_audio_result  == PackageManager.PERMISSION_GRANTED
                &&  Read_external_storage == PackageManager.PERMISSION_GRANTED;
    }

*/




}