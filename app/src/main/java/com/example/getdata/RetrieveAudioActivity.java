package com.example.getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class RetrieveAudioActivity extends AppCompatActivity {

    RecyclerView retrieveRecycle;
    DatabaseReference reference;
    ViewHolderAudio adapter;
    private ConstraintLayout playerSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    Handler mHandler = new Handler();


    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Upload_Audio");

    private ArrayList<Audiofetch> pModel;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_audio);

        reference = FirebaseDatabase.getInstance().getReference().child("Upload_Audio");

        retrieveRecycle = (RecyclerView) findViewById(R.id.fetchAudioRecycler);

        playerSheet = findViewById(R.id.player_sheet);


/*
        bottomSheetBehavior = BottomSheetBehavior.from(playerSheet);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if(newState == BottomSheetBehavior.STATE_HIDDEN)
                {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        */

        retrieveRecycle.setHasFixedSize(true);
        retrieveRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

           pModel = new ArrayList<>();

        adapter = new ViewHolderAudio(this,pModel);
        retrieveRecycle.setAdapter(adapter);



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {

                   // String audio_key = dataSnapshot.getRef().getKey();

                  //  Audiofetch pMdlName = new Audiofetch();

                    Audiofetch pMdlName = dataSnapshot.getValue(Audiofetch.class);

                 //   pMdlName.setTitle(dataSnapshot.getKey());
                   // pMdlName.setAudioName(dataSnapshot.getValue().toString());
                   // pMdlName.setUrl(dataSnapshot.getValue().toString());

                    pModel.add(pMdlName);
                  //  System.out.println("url = " + pMdlName.getUrl());

                    String audioUrl = pMdlName.getUrl();



                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


//    @Override
//    public void onBackPressed() {
//       // super.onBackPressed();
//
//        dialog.dismiss();
//
//    }

    /*
    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Audiofetch> options =
                new FirebaseRecyclerOptions.Builder<Audiofetch>()
                        .setQuery(reference, Audiofetch.class).build();

        FirebaseRecyclerAdapter<Audiofetch, ViewHolderAudio> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Audiofetch, ViewHolderAudio>(options) {
            @Override
            protected void onBindViewHolder(ViewHolderAudio viewHolderAudio, int i, Audiofetch audiofetch) {
                String audio_key = getRef(i).getKey();

                viewHolderAudio.setAudioTitle(audiofetch.getTitle());
              //  viewHolderAudio.setAudio(audiofetch.getAudioName());
                viewHolderAudio.setAudio(RetrieveAudioActivity.this,audiofetch.getUrl());


                System.out.println("Song title = " + audiofetch.getTitle());
                System.out.println("Song title = " + audiofetch.getAudioName());
                System.out.println("Song uri = " + audiofetch.getUrl());
                System.out.println("Song key = " + audio_key);

                viewHolderAudio.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        reference.child(audio_key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String audioFile = (String) snapshot.child("url").getValue();
                                mediaPlayer = new MediaPlayer();
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                try {
                                    mediaPlayer.setDataSource(audioFile);
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("What is error " + e.getMessage());

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

            }

            @NonNull
            @Override
            public ViewHolderAudio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.audioitem, parent, false);
                return new ViewHolderAudio(view);

            }
        };
        retrieveRecycle.setAdapter(firebaseRecyclerAdapter);

    }*/
}