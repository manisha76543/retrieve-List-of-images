package com.example.getdata;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.getdata.Model.logModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter <LogAdapter.MyViewHolder1> {

    private Context context;
    private ArrayList<logModel> mList;
    MediaPlayer mediaPlayer;
    boolean on = false;


    public LogAdapter(Context context, ArrayList<logModel> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public LogAdapter.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.logitem,parent,false);
        return new LogAdapter.MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LogAdapter.MyViewHolder1 holder, int position) {


      //  Glide.with(holder.image.getContext()).load(mList.get(position).getImg()).into(holder.image);
        Glide.with(context).load(mList.get(position).getImg()).into(holder.image);

        holder.ArrivalTime.setText(mList.get(position).getArrival_time());
        holder.AudioMessage.setText(mList.get(position).getAudio_message());
        holder.IsKnown.setText(mList.get(position).getIs_known());
        holder.AudioMessage.setVisibility(View.INVISIBLE);


        System.out.println("Audio message is available or not " + holder.AudioMessage.getText().toString());

      // holder.label1.setText("Arrival time");
        //holder.label2.setText("New User");


        if(holder.AudioMessage.getText().toString() == "No"){

            holder.fbPlay.setVisibility(View.INVISIBLE);
            holder.checkAudiO.setText("No Audio Message");

        }
        else
        {
            holder.fbPlay.setVisibility(View.VISIBLE);
            holder.checkAudiO.setText("Audio message ");
        }


        holder.fbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            logModel model = mList.get(position);
                String audioFile = model.getAudio_message();

                //   System.out.println("URL is = " + audioFile);

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(audioFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {

        TextView ArrivalTime,IsKnown,AudioMessage,label1,label2,checkAudiO;
        ImageView image;
        FloatingActionButton fbPlay;


        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);

           image = (ImageView) itemView.findViewById(R.id.imgLog);
           ArrivalTime = (TextView) itemView.findViewById(R.id.ArrivalT);
           IsKnown = (TextView) itemView.findViewById(R.id.isKnown);
           AudioMessage = (TextView) itemView.findViewById(R.id.audio);
           label1 = (TextView) itemView.findViewById(R.id.textView4);
           label2 = (TextView) itemView.findViewById(R.id.textView5);
           checkAudiO = (TextView) itemView.findViewById(R.id.checkAudio);


           fbPlay = (FloatingActionButton) itemView.findViewById(R.id.FbPlayAudio);


        }
    }

}
