package com.example.mpmazagi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class OnLineSongs extends AppCompatActivity {




    private RecyclerView songsRecView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_songs);


        songsRecView= (RecyclerView) findViewById(R.id.songsRecView);



        //WE SHOULD GIVE THIS LIST THE REDIRECTED LIST FROM THE HOME ACTIVITY
        ArrayList<Song> songs;
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        songs = (ArrayList) bundle.getParcelableArrayList("songs");
        SongsRecViewAdapter adapter=new SongsRecViewAdapter(this);
        adapter.setSongs(songs);
        songsRecView.setAdapter(adapter);
        songsRecView.setLayoutManager(new GridLayoutManager(this,2));

    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.search_menu) {


            Toast.makeText(this, "Search For Your Favorite Song Or Artist ", Toast.LENGTH_SHORT).show();
            Intent goToRegistrationActivity = new Intent(MyApplication.getContext(),SearchActivity.class);
            startActivity(goToRegistrationActivity);

            return true;
        }else  {
                return super.onOptionsItemSelected(item);
        }
    }


    public long convertDateToLongNumber(String date) {


        long integerDate = Long.parseLong(date.replace("-",""));

        return integerDate;
    }
}