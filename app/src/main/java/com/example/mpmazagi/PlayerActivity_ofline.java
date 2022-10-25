package com.example.mpmazagi;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity_ofline extends AppCompatActivity {

    Button play_btn, prev_btn, next_btn, fastfor_btn, fastback_btn;
    TextView txtSong, txtStart, txtStop;
    SeekBar seekBar;
    String song_Name;
    ImageView imgPlaySong;


   private static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySongs;

    Thread updateSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_player);

        play_btn = findViewById(R.id.play_btn);
        prev_btn = findViewById(R.id.prev_btn);
        next_btn = findViewById(R.id.next_btn);
        fastfor_btn = findViewById(R.id.fastfor_btn);
        fastback_btn = findViewById(R.id.fastback_btn);

        txtSong = findViewById(R.id.txtSong);
        txtStart = findViewById(R.id.txtStart);
        txtStop = findViewById(R.id.txtStop);

        imgPlaySong=findViewById(R.id.imgPlaySong);

        seekBar = findViewById(R.id.seekBar);



        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        //GETTING THE DATA FROM THE PREVIOUS ACTIVITY
        //*********************************************************

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = i.getStringExtra("songname");
        position = bundle.getInt("position", 0);

        //**********************************************************
        txtSong.setSelected(true);

        Uri uri = Uri.parse(mySongs.get(position).toString());

        song_Name = mySongs.get(position).getName();           //EXPECTED ERROR HERE(snname-song_Name)

        txtSong.setText(song_Name);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

        if(mediaPlayer.isPlaying()){

        mediaPlayer.reset();
        mediaPlayer.start();
        }else{
            mediaPlayer.start();
        }

        updateSeekBar = new Thread() {
            @Override
            public void run() {

                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException | IllegalMonitorStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        seekBar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_IN);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
        txtStop.setText(endTime);


        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                txtStart.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);


        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    play_btn.setBackgroundResource(R.drawable.play_btn);
                    mediaPlayer.pause();
                } else {
                    play_btn.setBackgroundResource(R.drawable.pause_btn);
                    mediaPlayer.start();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next_btn.performClick();
            }
        });


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position + 1) % mySongs.size());
                Uri path = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), path);
                song_Name = mySongs.get(position).getName();
                txtSong.setText(song_Name);
                mediaPlayer.start();
                play_btn.setBackgroundResource(R.drawable.pause_btn);
                startAnimation(imgPlaySong);
            }
        });


        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if ((position - 1) < 0) {
                    position = (mySongs.size() - 1);
                } else {
                    position = position - 1;
                }
                Uri path = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), path);
                song_Name = mySongs.get(position).getName();
                txtSong.setText(song_Name);
                mediaPlayer.start();
                play_btn.setBackgroundResource(R.drawable.pause_btn);
                startAnimation(imgPlaySong);
            }
        });

        fastfor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
            }
        });

        fastback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mediaPlayer.getCurrentPosition() - 10000) < 0) {
                    mediaPlayer.seekTo(0);
                } else {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
                }
            }
        });




    }



    public void startAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgPlaySong , "rotation" ,0f , 360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }


    public String createTime(int duration) {
        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        time += min + ":";

        if (sec < 10) {
            time += "0";
        }

        time += sec;

        return time;
    }


}