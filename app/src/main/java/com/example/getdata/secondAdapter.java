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

import com.bumptech.glide.Glide;

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

         Glide.with(context).load(mList2.get(position)).into(holder.imageView);
//         holder.imageView.setImageResource(Integer.parseInt(mList2.get(position)));

    }


    @Override
    public int getItemCount() {
        return mList2.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

             imageView = itemView.findViewById(R.id.m_image);

        }
    }

}
