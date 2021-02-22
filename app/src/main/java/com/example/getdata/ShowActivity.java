package com.example.getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    ImageView plusImg;
    Context context;

    public int i = 1;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("known_images/");

    public ArrayList<ArrayList<String>> All;
    private ArrayList<String> store;
    private ArrayList<String> storeName;
    private MyAdapter adapter1;

    int num=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        plusImg = (ImageView) findViewById(R.id.PlusView);
        plusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShowActivity.this, UploadActivity.class));


            }
        });

        store = new ArrayList<String>();
        storeName= new ArrayList<String>();

       // adapter1 = new MyAdapter(this,storeName);

       adapter1 = new MyAdapter(this,storeName,store);


       mRecyclerview.setAdapter(adapter1);


//---------its working images----------
/*
      int num=1;

       for(int i=1;i<10;i++) {

           root.child("Person" + num++).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {

                   for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                       //String er = dataSnapshot.getValue(String.class);
                       //System.out.println(er);
                       //store.add(er);

                       //  System.out.println(dataSnapshot.getValue());

                       store.add(dataSnapshot.getValue().toString());
                      // storeName.add(dataSnapshot.getKey());
                       // String url = dataSnapshot.getKey();
                      // System.out.println("pass in store = " + dataSnapshot.getValue().toString());
                       // System.out.println("Person name is here = " + url);
                       // System.out.println("All images are here =" + dataSnapshot.getValue());

                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
       }
*/

//-------------------its working name ----------
        /*
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

*/

        root.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               int i;

               for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                   storeName.add(dataSnapshot.getKey());


                   root.child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener()
                   {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {

                           int c=0;
                           for (DataSnapshot ds : snapshot.getChildren()) {
                               store.add(ds.getValue().toString());
                               c++;

                           }
                           System.out.println("count each person images = " +c);
                           System.out.println("hello");

                        //   adapter1 = new MyAdapter(context,storeName,store);
                           adapter1.notifyDataSetChanged();



                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {
                       }
                   });


//All.add(store);
                  // adapter1.notifyDataSetChanged();

               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
           }
       });

    }

}