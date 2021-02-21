package com.example.getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.model.Model;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import okhttp3.internal.cache.DiskLruCache;

public class ShowActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    ImageView plusImg;
    RecyclerView mRecyclerview2;

    public int i = 1;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("known_images/");
    private ArrayList<String> store;
    private ArrayList<String> storeName;
    private MyAdapter adapter,adapterNew;
    private secondAdapter adapter1;

    int num=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

       // mRecyclerview2 = findViewById(R.id.recyclerViewNew);
       // mRecyclerview2.setHasFixedSize(true);
        //mRecyclerview2.setLayoutManager(new LinearLayoutManager(this));

        plusImg = (ImageView) findViewById(R.id.PlusView);

        plusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShowActivity.this, UploadActivity.class));


            }
        });


      /*  list = new ArrayList<>();
        adapter = new MyAdapter(this,list);
        mRecyclerview.setAdapter(adapter);


       root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    list.add(upload);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

*/


        store = new ArrayList<String>();
        storeName= new ArrayList<String>();

        adapter = new MyAdapter(this,store);
        adapter1 = new secondAdapter(this,storeName);



       mRecyclerview.setAdapter(adapter);
       mRecyclerview.setAdapter(adapter1);

      int num=1;

       for(int i=1;i<10;i++) {

           root.child("Person" + num++).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {

                   for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                       //  Upload upload = dataSnapshot.getValue(Upload.class);
                       //list.add(upload);
                       //System.out.println(upload);

                       //String er = dataSnapshot.getValue(String.class);
                       //System.out.println(er);
                       //store.add(er);

                       //  System.out.println(dataSnapshot.getValue());

                       store.add(dataSnapshot.getValue().toString());
                      // storeName.add(dataSnapshot.getKey());

                       // String url = dataSnapshot.getKey();
                      // System.out.println("pass in store = " + dataSnapshot.getValue().toString());
                       // System.out.println("Person name is here = " + url);

                       //  System.out.println("child of person url = " + dataSnapshot.getRef());
                       // System.out.println("All images are here =" + dataSnapshot.getValue());

                   }
                   adapter.notifyDataSetChanged();
                   //adapter1.notifyDataSetChanged();

               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
       }


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    storeName.add(dataSnapshot.getKey());

                    // System.out.println("Person name is here = " +  dataSnapshot.getKey());

                }
                adapter1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


/*
        root.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               int i;

               for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                   storeName.add(dataSnapshot.getKey());

                   // System.out.println("Person name is here = " +  dataSnapshot.getKey());


               //  adapter1.notifyDataSetChanged();




               for (i = 1; i < 10;) {

                   root.child("Person" + num++).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {

                           for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                               store.add(dataSnapshot.getValue().toString());

                           }
                           adapter.notifyDataSetChanged();
                       }


                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
                   i++;
               }

                   adapterNew.notifyDataSetChanged();
           }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
*/

    }

}