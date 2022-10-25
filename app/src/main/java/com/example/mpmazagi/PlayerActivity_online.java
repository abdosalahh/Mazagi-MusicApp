package com.example.mpmazagi;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PlayerActivity_online extends AppCompatActivity {

    Button play_btn_online, prev_btn_online, next_btn_online, fastfor_btn_online, fastback_btn_online;
    TextView txtSong_online, txtStart_online, txtStop_online, txtArtist_online;
    SeekBar seekBar_online;
    ImageView imgPlaySong_online;
    ImageView favoritePlayer;

    private static MediaPlayer mediaPlayer;
    int position;
    String songURL;
    String songName, artistName, songImage;

    Thread updateSeekBar;

    //TO SET THE DATE WHEN THE USER CLICK ON THE SONG



   private ArrayList<Song> mySongs ;
   AppDatabase appDatabase=new AppDatabase(MyApplication.getContext());


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            mediaPlayer.pause();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_online);

        play_btn_online = (Button) findViewById(R.id.play_btn_online);
        txtStart_online = (TextView) findViewById(R.id.txtStart_online);
        txtArtist_online = (TextView) findViewById(R.id.txtArtist_online);
        txtSong_online = (TextView) findViewById(R.id.txtSong_online);
        txtStop_online = (TextView) findViewById(R.id.txtStop_online);
        seekBar_online = (SeekBar) findViewById(R.id.seekBar_online);
        imgPlaySong_online = (ImageView) findViewById(R.id.imgPlaySong_online);
        prev_btn_online=findViewById(R.id.prev_btn_online);
        next_btn_online=findViewById(R.id.next_btn_online);
        fastfor_btn_online=findViewById(R.id.fastfor_btn_online);
        fastback_btn_online=findViewById(R.id.fastback_btn_online);
        favoritePlayer=findViewById(R.id.favoritePlayer);


        mediaPlayer = new MediaPlayer();

//GETTING THE DATA FROM THE PREVIOUS ACTIVITY
//**********************************************************************
        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        position = bundle.getInt("position", 0);
        songURL = mySongs.get(position).getSongURL();
        songName =  mySongs.get(position).getSongName();
        artistName = mySongs.get(position).getArtistName();
        songImage = mySongs.get(position).getSongImage();
//**********************************************************************


        txtSong_online.setText(songName);
        txtArtist_online.setText(artistName);

        Glide.with(this)
                .asBitmap()
                .load(songImage)
                .into(imgPlaySong_online);




        play_btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mediaPlayer.isPlaying()) {
                    play_btn_online.setBackgroundResource(R.drawable.play_btn);
                    mediaPlayer.pause();
                } else {
                    play_btn_online.setBackgroundResource(R.drawable.pause_btn);
                    mediaPlayer.start();
                }
            }
        });



        prepareMediaPlayer(mySongs.get(position).getSongURL());



        updateSeekBar = new Thread() {
            @Override
            public void run() {
                super.run();
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar_online.setProgress(currentPosition);
                    } catch (InterruptedException | IllegalMonitorStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        seekBar_online.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        seekBar_online.getProgressDrawable().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.MULTIPLY);
        seekBar_online.getThumb().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_IN);

        seekBar_online.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        txtStop_online.setText(endTime);


        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                txtStart_online.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);




        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next_btn_online.performClick();
            }
        });


        next_btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                mediaPlayer.reset();


                position = ((position + 1) % mySongs.size());
                String url=mySongs.get(position).getSongURL();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();

                    txtSong_online.setText(mySongs.get(position).getSongName());
                    txtArtist_online.setText(mySongs.get(position).getArtistName());

                    txtStop_online.setText(createTime(mediaPlayer.getDuration()));

                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(mySongs.get(position).getSongImage())
                            .into(imgPlaySong_online);


                    appDatabase.updatePlayingCounter(mySongs.get(position).getSongName());
                    appDatabase.updateTimeOfPlaying(mySongs.get(position).getSongName(),convertDateToLongNumber(getCurrentDate()));


                }catch (Exception e){
                    Toast.makeText(PlayerActivity_online.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                    startAnimation(imgPlaySong_online);

            }
        });


        prev_btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.reset();


                    if ((position - 1) < 0) {
                        position = (mySongs.size() - 1);
                    } else {
                        position = position - 1;
                    }
                    String url=mySongs.get(position).getSongURL();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    txtSong_online.setText(mySongs.get(position).getSongName());
                    txtArtist_online.setText(mySongs.get(position).getArtistName());
                    txtStop_online.setText(createTime(mediaPlayer.getDuration()));
                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(mySongs.get(position).getSongImage())
                            .into(imgPlaySong_online);

                    appDatabase.updatePlayingCounter(mySongs.get(position).getSongName());
                    appDatabase.updateTimeOfPlaying(mySongs.get(position).getSongName(),convertDateToLongNumber(getCurrentDate()));


                }catch (Exception e){
                    Toast.makeText(PlayerActivity_online.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                startAnimation(imgPlaySong_online);
            }
        });



        fastfor_btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
            }
        });


        fastback_btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mediaPlayer.getCurrentPosition() - 10000) < 0) {
                    mediaPlayer.seekTo(0);
                } else {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
                }
            }
        });


        if (mySongs.get(position).isFavorite()){
            favoritePlayer.setImageResource(R.drawable.favorite_player);
        }else{
            favoritePlayer.setImageResource(R.drawable.unfavorite_player);


        }


        favoritePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try{
                    if (appDatabase.isFavorite(mySongs.get(position).getSongName())){
                        appDatabase.removeFromFavorites(mySongs.get(position).getSongName());
                        favoritePlayer.setImageResource(R.drawable.unfavorite_player);
                        Toast.makeText(PlayerActivity_online.this, "Removed From Favorites", Toast.LENGTH_SHORT).show();
                        mySongs.get(position).setFavorite(false);
                    }else
                    {
                        appDatabase.addToFavorites(mySongs.get(position).getSongName());
                        favoritePlayer.setImageResource(R.drawable.favorite_player);
                        Toast.makeText(PlayerActivity_online.this, "Added To Favorites", Toast.LENGTH_SHORT).show();

                        mySongs.get(position).setFavorite(true);
                    }
                }catch (Exception e){

                    Toast.makeText(MyApplication.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        });


    }


    public void startAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgPlaySong_online , "rotation" ,0f , 360f);
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


    private void prepareMediaPlayer(String URL) {
        try {
            mediaPlayer.setDataSource(URL);
            mediaPlayer.prepare();
            mediaPlayer.start();
            //txtStop_online.setText(melliSecToTimer(mediaPlayer.getDuration()));

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }




    public long convertDateToLongNumber(String date) {

        long integerDate = Long.parseLong(date.replace("-",""));
        return integerDate;
    }

    public String getCurrentDate (){

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date=simpleDateFormat.format(calendar.getTime());


        return date;
    }


}

