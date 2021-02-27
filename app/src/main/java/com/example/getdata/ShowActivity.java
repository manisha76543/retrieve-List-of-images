package com.example.getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    FirebaseDatabase database;
    RecyclerView mRecyclerview;
    ImageView plusImg;
    Context context;
    public int i = 1;
    MyAdapter adapter;
    secondAdapter adapter1;

    RecyclerView.LayoutManager manager;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("known_images/");
    private ArrayList<PersonWiseImagesModel> pModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        pModel = new ArrayList<>();

        adapter = new MyAdapter(this,pModel);
        mRecyclerview.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                   PersonWiseImagesModel pMdlName = new PersonWiseImagesModel();
                   pMdlName.setPersonName(dataSnapshot.getKey());

                   ArrayList<String> astring = new ArrayList<>();
                   for(DataSnapshot ds :dataSnapshot.getChildren()) {
                      astring.add(ds.getValue().toString());
                   }

                   pMdlName.setImageUrl(astring);
                   pModel.add(pMdlName);

                //  mRecyclerview.setAdapter(adapter);

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
           }
       });

     //   adapter = new MyAdapter(this,pModel);
        adapter.notifyDataSetChanged();
        }
}