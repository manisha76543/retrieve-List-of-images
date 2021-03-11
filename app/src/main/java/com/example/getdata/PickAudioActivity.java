package com.example.getdata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getdata.Model.UploadAudioModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PickAudioActivity extends AppCompatActivity {

    Button pick,upload;
    ProgressBar p;
    Uri audioUri;
    TextView textViewIm;
    EditText editTextTitle;

    StorageReference mStorageRef;
    StorageTask mUploadTask;
    DatabaseReference reference;

  //  final int resultCode = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_audio);

        pick = (Button) findViewById(R.id.pickAudio);
        upload = (Button) findViewById(R.id.upload_audio);
        p = (ProgressBar) findViewById(R.id.progressBarAudio);
        textViewIm = (TextView) findViewById(R.id.txtFileSelected);
        editTextTitle = (EditText) findViewById(R.id.titleName);

        reference = FirebaseDatabase.getInstance().getReference().child("Song");
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Song");


    }


    public void openAudioFile(View v)
    {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        startActivityForResult(i,101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK && data.getData()!=null)
        {
            audioUri = data.getData();
            String fileName = getFileName(audioUri);
            textViewIm.setText(fileName);

        }
    }

    private String getFileName(Uri uri)
    {
        String result = null;
        if(uri.getScheme().equals("content"))
        {
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try {
                if(cursor != null && cursor.moveToFirst())
                {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
            finally {
                cursor.close();
            }
        }

        if(result == null)
        {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut != -1)
            {
                result = result.substring(cut + 1);

            }
        }
        return result;

    }


    public void uploadAudio(View v)
    {
        if(textViewIm.getText().toString().equals("No file selected"))
        {
            Toast.makeText(getApplicationContext(),"Please choose audio",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(mUploadTask != null && mUploadTask.isInProgress())
            {
                Toast.makeText(getApplicationContext(),"Uploading is already in progress ",Toast.LENGTH_SHORT).show();
            }
            else
            UploadFile();
        }
    }


    private void UploadFile()
    {
        if(audioUri != null)
        {
            String durationTxt;
            Toast.makeText(getApplicationContext(),"Uploading please wait ...",Toast.LENGTH_SHORT).show();
            p.setVisibility(View.VISIBLE);

            StorageReference storageReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(audioUri));

            int durationInMillis = findSongDuration(audioUri);

            if(durationInMillis == 0)
            {
                durationTxt = "NA";

            }

            durationTxt = getDurationFromMilli(durationInMillis);

            final String finalDurationTxt = durationTxt;
   /*         mUploadTask = storageReference.putFile(audioUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            UploadTask.TaskSnapshot ts = taskSnapshot;
                            String download = ts.getTask().toString();
                            System.out.println("Download = " + download);
                            UploadAudioModel uploadAudioModel = new UploadAudioModel(editTextTitle.getText().toString(),finalDurationTxt,audioUri.toString(),null);

                            String uploadId = reference.push().getKey();

                            reference.child(uploadId).setValue(uploadAudioModel);

                          //  reference.child(uploadId).setValue(uploadAudioModel.getSongLink());

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            p.setProgress((int)progress);

                        }
                    });*/


            storageReference.putFile(audioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;

                            System.out.println("Url = " + downloadUrl);

                           // UploadAudioModel uploadAudioModel = new UploadAudioModel(editTextTitle.getText().toString(),finalDurationTxt,audioUri.toString(),null);
                            UploadAudioModel uploadAudioModel = new UploadAudioModel(editTextTitle.getText().toString(),finalDurationTxt,downloadUrl.toString(),null);
                            String uploadId = reference.push().getKey();

                            reference.child(uploadId).setValue(uploadAudioModel);



                        }
                    });

                }
            });


            p.setVisibility(View.INVISIBLE);

        }
        else {
            Toast.makeText(getApplicationContext(),"No file selected to upload", Toast.LENGTH_SHORT).show();
        }
    }

    private String getDurationFromMilli(int durationMillis)
    {
        Date date = new Date(durationMillis);
        SimpleDateFormat simple = new SimpleDateFormat("mm:ss", Locale.getDefault());
        String myTime = simple.format(date);
        return myTime;

    }

    private int findSongDuration(Uri uri)
    {
        int timeInMillisec = 0;
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(this,audioUri);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            timeInMillisec = Integer.parseInt(time);

            retriever.release();
            return timeInMillisec;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(audioUri));
    }

}