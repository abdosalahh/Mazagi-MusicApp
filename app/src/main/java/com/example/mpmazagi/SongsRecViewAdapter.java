package com.example.mpmazagi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SongsRecViewAdapter extends RecyclerView.Adapter<SongsRecViewAdapter.ViewHolder> {

    //TO SET THE DATE WHEN THE USER CLICK ON THE SONG
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String date;



    private Context context;

    private   AppDatabase  appDatabase=new AppDatabase(MyApplication.getContext());
    private  ArrayList<Song> songs=new ArrayList<>();

    //you should initialize this arrayList to avoid getting a nullPtr exception!!
    //and as it is private you also should make a setter for it to access it in the main activity!!

    public SongsRecViewAdapter(Context context) {

        this.context=context;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
        //this method is responsible for making the adapter always fit the data as it maybe changed
        //in the dataBase.
        //it works as it refresh the recycler view with the new data.
    }

    @NonNull
    @Override
    //it is responsible for creating an instance of the ViewHoler class for every item in the recycler view.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onlin_songs_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    //THE MOST IMPORTANT METHOD IN THE ADAPTER CLASS!!
    //controlling the properties or functionalities of the views of the holder class.
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtName.setText(songs.get(position).getSongName());
        holder.txtArtist.setText(songs.get(position).getArtistName());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "You are now listening to "+ songs.get(position).getSongName(), Toast.LENGTH_SHORT).show();

                //THE PLAY SONG FUNCTION SHOULD BE CALLED HERE
                //PASSING THE DATA TO 'PlayerActivity_online'


               context.startActivity(new Intent(context.getApplicationContext(), PlayerActivity_online.class)
                        .putExtra("songs",songs)
                        .putExtra("position",position));

               //SETTING THE PLAYING_COUNTER TO INCREASE IT BY ONE
                appDatabase.updatePlayingCounter(songs.get(position).getSongName());

//**********************************DATE AND TIME ************************************************
                calendar=Calendar.getInstance();
                simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                date=simpleDateFormat.format(calendar.getTime());

                appDatabase.updateTimeOfPlaying(songs.get(position).getSongName(),convertDateToLongNumber(date));


            }
        });

        //RESPONSIBLE FOR SHOWING THE IMAGE BY USING ITS URL
        //WE WILL USE IT IN THE PLAYER CLASS

        Glide.with(context)
                .asBitmap()
                .load(songs.get(position).getSongImage())
                .into(holder.image);


        //isFavorite ***************************************************
        //WE WILL SEARCH IN THE DATABASE AND CHECK IF THIS SONG IS FAVORITE OR NOT
        //SO WE NEED TO CALL 'isFavorite' FUNCTION FIRST.
        //AND THEN DECIDE WHAT IS THE INITIAL SRC OF THE IMAGE VIEW AT THE BEGINNING OF THE ACTIVITY RUN
        if (songs.get(position).isFavorite()){
            holder.isFavorite.setImageResource(R.drawable.favorite);
        }else{
            holder.isFavorite.setImageResource(R.drawable.unfavorite);
        }


      holder.isFavorite.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              //WE WILL SEARCH IS THE DATABASE AND CHECK IF THIS SONG IS FAVORITE OR NOT
              //SO WE NEED TO CALL 'isFavorite' FUNCTION FIRST.
              //**************************************ADD YOUR CODE HERE*******************************************
              //THIS BUTTON SHOULD CHANGE THE BOOLEAN VALUE OF 'isFavorite' OF THE CHECKED ITEM IN THE DATABASE TO TRUE
              //SO WE WILL CALL 'addToFavorites' FUNCTION HERE AND GIVE IT THE NAME OF THE SELECTED SONG
              //**************************************ADD YOUR CODE HERE*******************************************
              //IT ALSO CHANGES ITS SRC ACCORDING TO ITS STATE

             try{
                 if (appDatabase.isFavorite(songs.get(position).getSongName())){
                     appDatabase.removeFromFavorites(songs.get(position).getSongName());
                     holder.isFavorite.setImageResource(R.drawable.unfavorite);
                     songs.get(position).setFavorite(false);
                     Toast.makeText(context, "Removed From Favorites", Toast.LENGTH_SHORT).show();
                 }else
                 {
                     appDatabase.addToFavorites(songs.get(position).getSongName());
                     holder.isFavorite.setImageResource(R.drawable.favorite);
                     songs.get(position).setFavorite(true);
                     Toast.makeText(context, "Added To Favorites", Toast.LENGTH_SHORT).show();
                 }
             }catch (Exception e){

                 Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
              }



          }
      });

    }

    @Override
    //it returns the number of items in the list
    public int getItemCount() {
        return songs.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{
        //this is responsible for creating a block of the 'songs_list_item.xml' layout for every item in the songs list.


        private CardView parent;
        private TextView txtName, txtArtist;
        private ImageView image;
        private ImageView isFavorite;


        //to control the view in the 'songs_list_item.xml' layout ...and you can control other views if exist
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initializing the views
            parent= itemView.findViewById(R.id.parent);
            txtName=itemView.findViewById(R.id.txtName);
            txtName.setSelected(true);
            txtArtist=itemView.findViewById(R.id.txtArtist);
            txtArtist.setSelected(true);

            image=itemView.findViewById(R.id.image);
            isFavorite= itemView.findViewById(R.id.isFavorite);

        }
    }



    public long convertDateToLongNumber(String date) {

        long integerDate = Long.parseLong(date.replace("-",""));
        return integerDate;
    }


}
