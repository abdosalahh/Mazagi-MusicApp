package com.example.mpmazagi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {

    EditText searchBox;
    ListView listViewSongs;
    private   ArrayList<Song> songs ;
    Button searchBtn;
    AppDatabase appDatabase=new AppDatabase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBtn=findViewById(R.id.searchBtn);
        searchBox=findViewById(R.id.searchBox);
        listViewSongs = findViewById(R.id.listViewSongs);



        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        songs=appDatabase.searchForSong(searchBox.getText().toString());
        SearchItemAdapter searchItemAdapter = new SearchItemAdapter(MyApplication.getContext(), 0, songs);
        listViewSongs.setAdapter(searchItemAdapter);
            }
        });


        listViewSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                startActivity(new Intent(getApplicationContext(), PlayerActivity_online.class)
                        .putExtra("songs",songs)
                        .putExtra("position",i));

                appDatabase.updatePlayingCounter(songs.get(i).getSongName());
                appDatabase.updateTimeOfPlaying(songs.get(i).getSongName(),convertDateToLongNumber(getCurrentDate()));
            }
        });


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