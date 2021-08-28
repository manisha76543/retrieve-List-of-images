package com.example.getdata;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;


/*
public class ViewHolderAudio extends RecyclerView.ViewHolder  {

View mView;

    public ViewHolderAudio(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setAudioTitle(String audioTitle)
    {
        TextView AudioTitle = (TextView) mView.findViewById(R.id.tvAudioName);
        AudioTitle.setText(audioTitle);
    }


    public void setAudioUrl(String audioUrl)
    {
        TextView AudioUrl = (TextView) mView.findViewById(R.id.tvAudioUrl);
        AudioUrl.setText(audioUrl);
    }

    public void setAudio(Context context, String AudioName)
    {
        FloatingActionButton playBtn = (FloatingActionButton) mView.findViewById(R.id.floatingActionButton);

    }

}


*/

public class ViewHolderAudio extends RecyclerView.Adapter <ViewHolderAudio.MyViewHolder1> {

    private Context context;
    private ArrayList<Audiofetch> mList;
    boolean on = false;
    MediaPlayer mediaPlayer;
    long totalDuration;

     Runnable runnable;
     Handler mHandler = new Handler();


    TextView totalTimeOfAudio,startTime;
    SeekBar seekBar1;
    private TimeAlgo timeAlgo;
    View dialogView;


    public ViewHolderAudio(Context context, ArrayList<Audiofetch> mList) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.audioitem,parent,false);
        return new MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {

        Audiofetch model = mList.get(position);

        holder.songUrl.setText(model.getTitle());
        holder.songName.setText(model.getUrl());

        timeAlgo = new TimeAlgo();


   //-----------------woking fine--------------------

        holder.fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.player_sheet,null);

                //AlertDialog.Builder builder = new AlertDialog.Builder(context);
              //  View dialogView = LayoutInflater.from(context).inflate(R.layout.player_sheet,null);

                final EditText input = new EditText(context);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                ImageButton pause1,back,forw;
              //  TextView totalTimeOfAudio,startTime;
                TextView playFileName;

                SeekBar seekBar1;
                pause1 = dialogView.findViewById(R.id.player_play_btn);
                back = dialogView.findViewById(R.id.imageView4);
                forw = dialogView.findViewById(R.id.imageView5);
                seekBar1 = dialogView.findViewById(R.id.seekB);
                pause1.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                back.setImageResource(R.drawable.ic_baseline_fast_rewind_24);
                forw.setImageResource(R.drawable.ic_baseline_fast_forward_24);
                totalTimeOfAudio = dialogView.findViewById(R.id.totalTime);
                startTime = dialogView.findViewById(R.id.startTime);
                playFileName = dialogView.findViewById(R.id.player_fileName);

                builder.setView(playFileName);
                builder.setView(totalTimeOfAudio);
                builder.setView(startTime);
                builder.setView(pause1);
                builder.setView(back);
                builder.setView(forw);
                builder.setView(input);
                builder.setView(seekBar1);

                builder.setView(dialogView);
                builder.show();

                //--------------------
                pause1.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);

                String audioFile = model.getUrl();

             //   System.out.println("URL is = " + audioFile);

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(audioFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    playFileName.setText(holder.songUrl.getText());
                    seekBar1.setProgress(0);
                    seekBar1.setMax(100);

                   // totalDuration = mediaPlayer.getDuration();
                   //   totalTimeOfAudio.setText("" + timeAlgo.milliSecondsToTimer(totalDuration));

                  //  updateProgressBar();
                  //  mHandler.postDelayed(mUpdateTimeTask,100);

                  //   Runnable mUpdateTimeTask = new Runnable()
                   mHandler.postDelayed(new Runnable()
                     {
                        @Override
                        public void run() {

                            long totalDuration = mediaPlayer.getDuration();
                            long currentDuration = mediaPlayer.getCurrentPosition();

                            totalTimeOfAudio.setText("" + timeAlgo.milliSecondsToTimer(totalDuration));

                            startTime.setText("" + timeAlgo.milliSecondsToTimer(currentDuration));

                            int progress = (int) (timeAlgo.getProgressPercentage(currentDuration,totalDuration));

                          //  System.out.println("Progress= " + progress);

                            seekBar1.setProgress(progress);

                           mHandler.postDelayed(this::run,100);//remove

                        }
                         }, 100);



                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                            if(!mediaPlayer.isPlaying())
                            {
                                pause1.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                                on=false;
                            }


                        }
                    });



                    if(startTime == totalTimeOfAudio)
                    {
                        pause1.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    }


                    seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//                                mediaPlayer.seekTo(progress);
//                                seekBar.setProgress(progress);
//                                int y = (int) Math.ceil(progress/1000f);
//
//                            if (y == 0 && mediaPlayer != null && !mediaPlayer.isPlaying())
//                            {
//                               // clearMediaPlayer();
//                            }
//                          //  progressBar.setProgress(progress);

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                          //  mHandler.removeCallbacks(mUpdateTimeTask);//remove
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                       //     mHandler.removeCallbacks(mUpdateTimeTask); //remove
                            int totalDuration = mediaPlayer.getDuration();
                            int currentPosition = TimeAlgo.progressToTimer(seekBar1.getProgress(),totalDuration);

                            mediaPlayer.seekTo(currentPosition);
                           // updateProgressBar();
                            if(startTime== totalTimeOfAudio)
                            {
                                pause1.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                            }


                        }
                    });

                    if(startTime== totalTimeOfAudio)
                    {
                        pause1.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    }



                }
                catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalStateException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("What is error " + e.getMessage());

                }

                on = true;

                //--------start with play

                pause1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                        if(mediaPlayer != null && mediaPlayer.isPlaying())
//                        {
//                            mediaPlayer.pause();
//                        }

                        if(on==false)
                        {
                            pause1.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);

                            String audioFile = model.getUrl();
                          //  System.out.println("URL is = " + audioFile);

                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            try {
                                mediaPlayer.setDataSource(audioFile);
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                //seekBar1.setProgress(0);
                              //  seekBar1.setMax(100);

                             //   updateProgressBar();


                                mHandler.postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        long totalDuration = mediaPlayer.getDuration();
                                        long currentDuration = mediaPlayer.getCurrentPosition();

                                        totalTimeOfAudio.setText("" + timeAlgo.milliSecondsToTimer(totalDuration));

                                        startTime.setText("" + timeAlgo.milliSecondsToTimer(currentDuration));

                                        int progress = (int) (timeAlgo.getProgressPercentage(currentDuration,totalDuration));

                                        //  System.out.println("Progress= " + progress);

                                        seekBar1.setProgress(progress);

                                        mHandler.postDelayed(this::run,100);//remove

                                    }
                                }, 100);












                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("What is error " + e.getMessage());

                            }

                            on = true;

                        }
                        else
                            {
                            pause1.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                            builder.setTitle("Not Playing");
                            mediaPlayer.pause();
                           // mediaPlayer.release();
                            //mediaPlayer = null;


                            on=false;
                        }

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {

                                if(!mediaPlayer.isPlaying())
                                {
                                    pause1.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                                    on=false;
                                }


                            }
                        });

                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


   // public void updateProgressBar()
    //{
      //  mHandler.postDelayed(mUpdateTimeTask,100); //remove

//        mHandler.postDelayed(new Runnable()
//        {
//            @Override
//            public void run() {
//                handlerThread.quitSafely();
//            }
//        },100);
  //  }

//    private Runnable mUpdateTimeTask = new Runnable() {
//        @Override
//        public void run() {
//
//           // SeekBar seekBar1;
//
//         //   seekBar1 = dialogView.findViewById(R.id.seekB);
//
//             totalDuration = mediaPlayer.getDuration();
//            long currentDuration = mediaPlayer.getCurrentPosition();
//
//            totalTimeOfAudio.setText("" + timeAlgo.milliSecondsToTimer(totalDuration));
//
//            startTime.setText("" + timeAlgo.milliSecondsToTimer(currentDuration));
//
//            int progress = (int) (timeAlgo.getProgressPercentage(currentDuration,totalDuration));
//
//            System.out.println("Progress= " + progress);
//
//           seekBar1.setProgress(progress);
//
//            mHandler.postDelayed(this,100);//remove
//
//        }
//    };



    /*
    public void  run(SeekBar seekBar1)
    {

         int currentPosition = mediaPlayer.getCurrentPosition();
         int total = mediaPlayer.getDuration();

         while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPosition < total)
         {
             try {
                 Thread.sleep(1000);
                 currentPosition = mediaPlayer.getCurrentPosition();
             }
             catch (InterruptedException  e)
             {
                 return;
             }
             catch (Exception e)
             {
                 return;
             }
             seekBar1.setProgress(currentPosition);
         }
    }

*/

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {

        TextView songName,songUrl,playerHeaderTitle;
        FloatingActionButton fb;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);

            songName = itemView.findViewById(R.id.tvAudioName);
            songUrl = itemView.findViewById(R.id.tvAudioUrl);
            fb = itemView.findViewById(R.id.floatingActionButton);


        }

    }

}

