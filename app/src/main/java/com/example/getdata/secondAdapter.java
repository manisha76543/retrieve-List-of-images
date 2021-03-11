package com.example.getdata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class secondAdapter extends RecyclerView.Adapter<secondAdapter.MyViewHolder>{

    private ArrayList<String> mList2;
    private Context context;
    public secondAdapter(Context context, ArrayList<String> mList2)
    {
        this.context = context;
        this.mList2 = mList2;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.seconditem,parent,false);
        //return new secondAdapter.MyViewHolder(v);
        return  new MyViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(position == mList2.size())
        {
           // holder.imageView.setMaxWidth(100);
            //holder.imageView.setMaxHeight(100);
            holder.imageView2.setImageResource(R.drawable.ic_baseline_add_24);
            holder.imageView2.setBackgroundColor(R.color.colorPrimary);
            holder.text_add.setText("Add more images");

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, UploadActivity.class);
                    context.startActivity(intent);

                }
            });

        }
        else {
          //  Glide.with(holder.imageView2.getContext()).load(mList2).into(holder.imageView2);
            Glide.with(holder.imageView.getContext()).load(mList2.get(position)).into(holder.imageView);

            holder.text_add.setText("");

        }
    }

    @Override
    public int getItemCount() {
        return mList2.size() + 1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_add;
        ImageView imageView,imageView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.m_image);
             imageView2 = itemView.findViewById(R.id.m_image2);
             text_add = itemView.findViewById(R.id.textAdd);

        }
    }
}