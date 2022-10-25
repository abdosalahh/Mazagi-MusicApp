package com.example.mpmazagi;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class OflineSongs extends AppCompatActivity {


    ListView listViewSongs;
    public  String [] items;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ofline_songs);

        listViewSongs = (ListView) findViewById(R.id.listViewSongs);



        runtimePermission();





    }


    public  void runtimePermission(){

        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        displaySongs();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }



        //THIS FUNCTION TAKES A FILE AND SEE IF THERE IS A mp3 FILE OR NOT ..IF THE IS IT WILL ADD IT TO THE LIST ELSE IT WILL OPEN EACH
        //SUBDIRECTORY AN SEARCH RECURSIVELY.
    public ArrayList<File> findSong(File file){

        ArrayList<File> arrayList=new ArrayList<>();
        File[] files =file.listFiles();


        for (File singleFile :files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findSong(singleFile));

            }else {
                if (singleFile.getName().endsWith(".mp3")|| singleFile.getName().endsWith("wav")){
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }

    public void displaySongs(){
        //HERE WE WILL PATH THE WHOLE 'EXTERNAL STORAGE' FILE TO THE findSong FUNCTION TO GET ALL mp3 FILES IN THE DEVISE.
        final ArrayList<File> mySongs=findSong(Environment.getExternalStorageDirectory());
         items=new String[mySongs.size()];
        for (int i =0; i<mySongs.size();i++){
            //STORING NAMES OF THE SONGS WE HAVE GOT FROM THE DEVISE IN items ARRAY .
            items[i]=mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","");
            System.out.println(items[i]);
        }
//        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
//        listView.setAdapter(myAdapter);
        CustomAdapter customAdapter =new CustomAdapter();
        listViewSongs.setAdapter(customAdapter);

        listViewSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName=(String) listViewSongs.getItemAtPosition(i);
                startActivity(new Intent(getApplicationContext(), PlayerActivity_ofline.class)
                        .putExtra("songs",mySongs)
                        .putExtra("songname",songName)
                        .putExtra("position",i));
            }
        });

    }


    public class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View myView = getLayoutInflater().inflate(R.layout.ofline_songs_list_item,null);
            TextView textSong = myView.findViewById(R.id.txtSongName);
            textSong.setSelected(true);
            textSong.setText(items[i]);

            return myView;
        }
    }




}

