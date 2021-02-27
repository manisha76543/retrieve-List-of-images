package com.example.getdata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    private Spinner spinner;
    public int i=1;
    private static final int pic_id=123;
    int num1 = 2;

    private ImageView imageView;
    private ProgressBar progressBar;

    ImageView plusBtn;
    private CardView card1Upload;

    private Bitmap img;
    private EditText mEditText;

    private ArrayList<String> imageAdd= new ArrayList<>();
   // private Uri imageUri;

    private ArrayList<String> trackId = new ArrayList<>();

    String NAME;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("known_images");
    private StorageReference reference = FirebaseStorage.getInstance().getReference("known_images");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mEditText = findViewById(R.id.editText);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        progressBar.setVisibility(View.INVISIBLE);
        spinner = findViewById(R.id.spinner);
        plusBtn = findViewById(R.id.btnplus);
        card1Upload = (CardView) findViewById(R.id.cardViewUpload);

        root = FirebaseDatabase.getInstance().getReference();
        loadData();

        plusBtn.setOnClickListener(new View.OnClickListener() {

            boolean visible;

            @Override
            public void onClick(View v) {

                visible =! visible;
                mEditText.setVisibility(visible ? View.VISIBLE: View.GONE);
                spinner.setVisibility(visible ? View.INVISIBLE: View.VISIBLE);

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position)
                {
                    case 0:
                       // upload(spinner.getItemAtPosition(position).toString());
                        break;
                    case 1:
                        upload(spinner.getItemAtPosition(position).toString());
                       break;

                    default:
                        upload(spinner.getItemAtPosition(position).toString());
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // Toast.makeText(UploadActivity.this, "Please select person ", Toast.LENGTH_LONG).show();
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // num1++;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

            }
        });

        card1Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root = FirebaseDatabase.getInstance().getReference("known_images/");
                upload();

            }

        });

    }


    private void upload() {

        if (img != null) {

            progressBar.setVisibility(View.VISIBLE);


            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                 img.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            final String random = UUID.randomUUID().toString();
            StorageReference imageRef = reference.child("image/" + random);

            //  StorageReference imageRef = reference.child("known_images" + random);

            byte[] b = stream.toByteArray();
            imageRef.putBytes(b)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUri = uri;
                                    String download_url = uri.toString();
                                    imageAdd.add(download_url);


                                    NAME = mEditText.getText().toString();

                                    Upload upload = new Upload(download_url);

//                                    for(int i=0;i<imageAdd.size();i++)
//                                    {
//                                        Upload upload = new Upload(imageAdd.get(i));
//                                    }

                                    String uploadId = "Person" + i;
                                    trackId.add(uploadId);
//                                    String ii = "";
//                                    int flag = 0;
//
//                                    for (int i = 0; i < trackId.size(); i++) {
//                                        if (NAME == trackId.get(i)) {
//                                            ii = trackId.get(i);
//                                            flag = 1;
//                                            break;
//                                        }
//                                    }

                                    int num=1;
//                                    if (flag == 1) {
//                                        root.child(ii).child("image" + num).setValue(upload.getImageUrl());
//                                    }
//                                    else {
                                        root.child(NAME).child("image" + num++).setValue(upload.getImage1());
                                        i++;
                                   // }

                                }
                            });

                            Toast.makeText(UploadActivity.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            Toast.makeText(UploadActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
            imageView  = null;//empty image after uploading
        }//if close here
        else
        {
            Toast.makeText(UploadActivity.this, "Please click image before uploading",Toast.LENGTH_LONG).show();
        }
    }

    private void upload(String selectedFolder) {

        if (img != null) {
            progressBar.setVisibility(View.VISIBLE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
             img.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            final String random = UUID.randomUUID().toString();
            StorageReference imageRef = reference.child("images/*" + random);

            byte[] b = stream.toByteArray();
            imageRef.putBytes(b)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUri = uri;
                                    String download_url = uri.toString();

                                    imageAdd.add(download_url);
                                    NAME = selectedFolder;

                                    Upload upload = new Upload(download_url);

                                    root.child("known_images").child(NAME).child("image" + num1++).setValue(upload.getImage1());
                                   // num1++;

                                    // root.child("known_images").child(NAME).setValue(upload.getImage1()).getResult();
                                    // String key = root.child(NAME).getKey();
                                    //DatabaseReference key2 = root.child(NAME).getParent();

                                }
                            });

                            Toast.makeText(UploadActivity.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            Toast.makeText(UploadActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
            img  = null;
            imageView.setImageBitmap(img);
           // imageView.setImageResource(findViewById(R.id.imageView));
            imageView.setImageResource(R.drawable.ic_baseline_add_a_photo_24);


        }
        else
        {
            Toast.makeText(UploadActivity.this, "Image is not found",Toast.LENGTH_LONG).show();
        }
    }


    public void loadData () {

        final List<user> nameList = new ArrayList<>();
        // databaseReference = FirebaseDatabase.getInstance().getReference();
        root.child("known_images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String id = ds.getKey();
                        String uploadId = root.child("imageUrl").push().getKey();
                        nameList.add(new user(id, uploadId));

                    }
                    ArrayAdapter<user> arrayAdapter = new ArrayAdapter<>(UploadActivity.this, android.R.layout.simple_dropdown_item_1line, nameList);
                    arrayAdapter.notifyDataSetChanged();
                    spinner.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        //if(requestCode==2 && resultCode == RESULT_OK && data!=null)
        if (requestCode == 0 && resultCode == RESULT_OK) {
            img = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(img);
           // imageUri = data.getData();

        }
    }
}