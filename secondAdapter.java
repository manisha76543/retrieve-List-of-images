package com.example.getdata;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class secondAdapter extends RecyclerView.Adapter<secondAdapter.MyViewHolder>{

    private ArrayList<String> mList2;
    private Context context;

    public secondAdapter(Context context,ArrayList<String> mList2)
    {
        this.context = context;
        this.mList2 = mList2;

    }

    @NonNull
    @Override
    public secondAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.seconditem,parent,false);

        return new secondAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyAdapter myAdapter = new MyAdapter(context,mList2);

        holder.personName.setText(mList2.get(position));
        holder.PersonRecycle.setHasFixedSize(true);
        holder.PersonRecycle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        holder.PersonRecycle.setAdapter(myAdapter);
    }



    @Override
    public int getItemCount() {
        return mList2.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView personName;
        public RecyclerView PersonRecycle;
        public RecyclerView.LayoutManager manager;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            manager = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.VERTICAL,false);

            personName = itemView.findViewById(R.id.personNameText);

            PersonRecycle = itemView.findViewById(R.id.recyclerViewNew);
            PersonRecycle.setLayoutManager(manager);

        }
    }

}
