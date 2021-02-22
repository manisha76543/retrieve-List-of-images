package com.example.getdata;

import android.content.Context;
import android.util.AttributeSet;
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

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder> {

    private ArrayList<String> mList;
    private Context context;
    private ArrayList<String> mImgList;

    int num=1;
    // public MyAdapter(Context context,ArrayList<String> mList,ArrayList<String> mImgList)
    public MyAdapter(Context context, ArrayList<String> mList, ArrayList<String> mImgList)
    {

        this.context = context;
        this.mList = mList;
        this.mImgList = mImgList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.personName.setText(mList.get(position));

        secondAdapter secondAdpt = new secondAdapter(context,mImgList);
        holder.PersonRecycle.setHasFixedSize(true);
        holder.PersonRecycle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
       // holder.PersonRecycle.setLayoutManager(new GridLayoutManager(context,9));
       holder.PersonRecycle.setAdapter(secondAdpt);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView personName;
        public RecyclerView PersonRecycle;
        public RecyclerView.LayoutManager manager;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            personName = itemView.findViewById(R.id.personNameText);

            manager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL,false);
            PersonRecycle = itemView.findViewById(R.id.recyclerViewNew);
            PersonRecycle.setLayoutManager(manager);

        }
    }
}
