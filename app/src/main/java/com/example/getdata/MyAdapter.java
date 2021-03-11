package com.example.getdata;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder1> {
    private Context context;
    private ArrayList<PersonWiseImagesModel> mList;
   public MyAdapter(Context context, ArrayList<PersonWiseImagesModel> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        holder.personName.setText(mList.get(position).getPersonName());

       secondAdapter secondAdpt = new secondAdapter(context,mList.get(position).getImageUrl());
        holder.PersonRecycle.setHasFixedSize(true);
        holder.PersonRecycle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
       holder.PersonRecycle.setAdapter(secondAdpt);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {

        TextView personName;
        public RecyclerView PersonRecycle;
        public RecyclerView.LayoutManager manager;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);

            personName = itemView.findViewById(R.id.personNameText);
            manager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL,false);
            PersonRecycle = itemView.findViewById(R.id.recyclerViewNew);
            PersonRecycle.setLayoutManager(manager);
        }
    }
}

