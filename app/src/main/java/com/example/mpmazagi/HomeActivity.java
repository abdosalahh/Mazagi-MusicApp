package com.example.mpmazagi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private AppDatabase appDatabase = new AppDatabase(this);

    ListView optionsListView;
    ArrayList<Song> songs;


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            super.onBackPressed();

        } else {
            getSupportFragmentManager().popBackStack();
            Toast.makeText(this, "YOU ARE AT HOME!", Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        optionsListView = findViewById(R.id.optionsListView);
        ArrayList<OptionsMenu> menu = new ArrayList<>();
        appDatabase = new AppDatabase(this);

        menu.add(new OptionsMenu(R.drawable.ic_ofline_songs, "From Device"));
        menu.add(new OptionsMenu(R.drawable.ic_online_songs, "Online Songs"));
        menu.add(new OptionsMenu(R.drawable.ic_favorite_songs, "Favorites"));
        menu.add(new OptionsMenu(R.drawable.ic_mostplayed_songs, "Most Played"));
        menu.add(new OptionsMenu(R.drawable.ic_recently_played, "Recently Played"));




        //ACCORDING TO THE USERS CHOICE WE WILL PASS THE SUITABLE ARRAY LIST THAT WE WILL GET FROM THE DATABASE TO THE ONLINE SONGS ACTIVITY
        optionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {

                    case 0:
                        startActivity(new Intent(getApplicationContext(), OflineSongs.class));
                        break;

                    case 1:
                        songs = appDatabase.returnAllSongsFromDataBase();
                        if (songs.size()==0){
                            Toast.makeText(HomeActivity.this, "No Songs", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(getApplicationContext(), OnLineSongs.class)
                                .putExtra("songs", songs));
                        break;


                    case 2:
                        songs = appDatabase.returnFavoriteSongsFromDataBase();
                        if (songs.size()==0){
                            Toast.makeText(HomeActivity.this, "No Songs Added Yet", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(getApplicationContext(), OnLineSongs.class)
                                .putExtra("songs", songs));
                        break;


                    case 3:
                        songs = appDatabase.returnMostPlayed();
                        if (songs.size()==0){
                            Toast.makeText(HomeActivity.this, "No Songs Yet", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(getApplicationContext(), OnLineSongs.class)
                                .putExtra("songs", songs));
                        break;

                    case 4:
                        songs = appDatabase.returnRecentlyPlayed();
                        if (songs.size()==0){
                            Toast.makeText(HomeActivity.this, "No Songs Played Yet", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(getApplicationContext(), OnLineSongs.class)
                                .putExtra("songs", songs));
                        break;

                }
            }
        });



        OptionsMenuAdapter optionsMenuAdapter = new OptionsMenuAdapter(this, 0, menu);
        optionsListView.setAdapter(optionsMenuAdapter);


    }
}