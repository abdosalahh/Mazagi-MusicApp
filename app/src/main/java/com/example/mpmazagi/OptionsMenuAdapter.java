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

import java.util.ArrayList;
import java.util.List;

public class OptionsMenuAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<OptionsMenu> optionsMenusList;








    public OptionsMenuAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context=context;
        this.optionsMenusList=new ArrayList<>(objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            LayoutInflater i =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=i.inflate(R.layout.item_home,null);
        }
        if (optionsMenusList.size()>0){
            OptionsMenu optionsMenu=optionsMenusList.get(position);
            TextView optionName=convertView.findViewById(R.id.optionName);
            optionName.setText(optionsMenusList.get(position).name);
            ImageView optionImage=convertView.findViewById(R.id.optionImage);
            optionImage.setImageResource(optionsMenusList.get(position).image);

        }
        return convertView;
    }





}
