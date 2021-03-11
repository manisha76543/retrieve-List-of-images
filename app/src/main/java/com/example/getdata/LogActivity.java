package com.example.getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.getdata.Model.logModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {


    RecyclerView logRecyclerView;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("logs");
    private ArrayList<logModel> pModel;
    LogAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        logRecyclerView = (RecyclerView) findViewById(R.id.logRecycle);
        logRecyclerView.setHasFixedSize(true);
        logRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        pModel = new ArrayList<>();

        adapter =  new LogAdapter(this,pModel);
        logRecyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    logModel pMdlName = new logModel() ;

                    pMdlName.setImg(dataSnapshot.child("img").getValue().toString());
                    pMdlName.setArrival_time(dataSnapshot.child("arrival_time").getValue().toString());
                    pMdlName.setIs_known(dataSnapshot.child("is_known").getValue().toString());
                    pMdlName.setAudio_message(dataSnapshot.child("audio_message").getValue().toString());


                    pModel.add(pMdlName);

                   // System.out.println("arrival name = " + pMdlName.getArrival_time());


                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }
}