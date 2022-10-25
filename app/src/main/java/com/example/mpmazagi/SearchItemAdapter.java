package com.example.mpmazagi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Song> searchList;








    public SearchItemAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context=context;
        this.searchList=new ArrayList<>(objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            LayoutInflater i =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=i.inflate(R.layout.search_item,null);
        }
        if (searchList.size()>0){


            Song song=searchList.get(position);

            TextView txtSongName_search=convertView.findViewById(R.id.txtSongName_search);


            txtSongName_search.setText(searchList.get(position).getSongName());

            ImageView imgSong_search=convertView.findViewById(R.id.imgSong_search);

            Glide.with(context)
                    .asBitmap()
                    .load(searchList.get(position).getSongImage())
                    .into(imgSong_search);

        }
        return convertView;
    }





}
