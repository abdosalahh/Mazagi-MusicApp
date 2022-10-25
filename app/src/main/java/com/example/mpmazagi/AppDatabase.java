package com.example.mpmazagi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AppDatabase extends SQLiteOpenHelper {

    public final static String dataBaseName = "MazagiDB";
    public static final String SONGS_TABLE = "SONGS_TABLE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String SONG_NAME_COLUMN = "SONG_NAME";
    public static final String SONG_IMAGE_URL_COLUMN = "SONG_IMAGE_URL";
    public static final String SONG_URL_COLUMN = "SONG_URL";
    public static final String ARTIST_NAME_COLUMN = "ARTIST_NAME";
    public static final String IS_FAVORITE_COLUMN = "IS_FAVORITE";
    public static final String PLAYING_COUNTER_COLUMN = "PLAYING_COUNTER";
    public static final String TIME_OF_PLAYING_COLUMN = "TIME_OF_PLAYING";
    public static final String USER_ID_COLUMN = "USER_ID";
    public static final String USER_EMAIL_COLUMN = "USER_EMAIL";
    public static final String PROFILE_IMAGE_URL_COLUMN = "PROFILE_IMAGE_URL";
    public static final String PASSWORD_COLUMN = "PASSWORD";


    public SQLiteDatabase appDatabase;


    public AppDatabase(@Nullable Context context) {
        super(context, dataBaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SONGS_TABLE + "(" +
                SONG_NAME_COLUMN + " TEXT PRIMARY KEY," +
                SONG_IMAGE_URL_COLUMN + " TEXT," +
                SONG_URL_COLUMN + " TEXT," +
                ARTIST_NAME_COLUMN + " TEXT," +
                IS_FAVORITE_COLUMN + " BOOLEAN," +
                PLAYING_COUNTER_COLUMN + " INTEGER," +
                TIME_OF_PLAYING_COLUMN + " INTEGER)");


        db.execSQL("CREATE TABLE " + USER_TABLE + "(" +
                USER_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_EMAIL_COLUMN + " TEXT," +
                PROFILE_IMAGE_URL_COLUMN + " TEXT," +
                PASSWORD_COLUMN + " TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //1
//WILL BE USED IN THE REGISTER ACTIVITY
    public void addUserToDataBase(String email,String pass) {

        ContentValues row = new ContentValues();
        row.put(USER_EMAIL_COLUMN, email);
       // row.put(PROFILE_IMAGE_URL_COLUMN, user.getImage());
        row.put(PASSWORD_COLUMN, pass);

        appDatabase = getWritableDatabase();
        appDatabase.insert(USER_TABLE, null, row);
        appDatabase.close();

    }

    //2
    public boolean checkPassword(String email, String password) {


        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE TRIM(USER_EMAIL)" +  " = '" + email + "' AND TRIM(PASSWORD) ='"+password+"'";
        appDatabase = getReadableDatabase();
        Cursor cursor = appDatabase.rawQuery(queryString, null);

        boolean state=false;

          if(cursor!=null && cursor.getCount()>0){
                cursor.close();
                appDatabase.close();
                state=true;
            }else {


                cursor.close();
                appDatabase.close();
                state= false;

              }

        return state;
    }

    //3
//FOR US TO INSERT THE DEFAULT ONLINE SONGS ONLY
    public void addSongToDataBase(String songName, String songImageURL, String songURL, String artistName) {
        //INSERTING ROW IN DATABASE
        ContentValues row = new ContentValues();
        row.put(SONG_NAME_COLUMN, songName);
        row.put(SONG_IMAGE_URL_COLUMN, songImageURL);
        row.put(SONG_URL_COLUMN, songURL);
        row.put(ARTIST_NAME_COLUMN, artistName);
        row.put(IS_FAVORITE_COLUMN, false);
        row.put(PLAYING_COUNTER_COLUMN, 0);
        row.put(TIME_OF_PLAYING_COLUMN, 0);

        appDatabase = getWritableDatabase();
        appDatabase.insert(SONGS_TABLE, null, row);
        appDatabase.close();

    }

    //4
    public ArrayList<Song> returnAllSongsFromDataBase() {
        ArrayList<Song> returnedList = new ArrayList<>();

        //getting the songs from the database
        String queryString = "SELECT * FROM " + SONGS_TABLE;
        appDatabase = getReadableDatabase();
        Cursor cursor = appDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //loop through the cursor and create new customer obj with the values in the row and then add it to the list
            do {
                String songName = cursor.getString(0);
                String songImage = cursor.getString(1);
                String songURL = cursor.getString(2);
                String artistName = cursor.getString(3);
                boolean isFavorite = cursor.getInt(4) == 1 ? true : false;
                int playingCounter = cursor.getInt(5);
                int numberOfPlaying = cursor.getInt(6);


                Song newSong = new Song(songName, songImage, songURL, artistName, isFavorite, playingCounter, numberOfPlaying);
                returnedList.add(newSong);

            } while (cursor.moveToNext());
        } else {
            //do not add anything
        }

        cursor.close();
        appDatabase.close();


        return returnedList;
    }

    //5
    public ArrayList<Song> searchForSong(String string) {
        ArrayList<Song> resultList = new ArrayList<>();

        char[] arrayFirstThreeLetters={string.charAt(0),string.charAt(1),string.charAt(2)};
        String firstThreeLetters=String.valueOf(arrayFirstThreeLetters);

        String queryString = "SELECT * FROM " + SONGS_TABLE + " WHERE SONG_NAME " + " LIKE '" + string +
                             "' OR ARTIST_NAME " + " LIKE '" + string+
                             "' OR ARTIST_NAME " + " LIKE '" + firstThreeLetters +"%"+
                             "' OR SONG_NAME " + " LIKE '" + firstThreeLetters +"%'";



        appDatabase = getReadableDatabase();
        Cursor cursor = appDatabase.rawQuery(queryString, null);



        if (cursor.moveToFirst()) {
            //loop through the cursor and create new customer obj with the values in the row and then add it to the list
            do {
                String songName = cursor.getString(0);
                String songImage = cursor.getString(1);
                String songURL = cursor.getString(2);
                String artistName = cursor.getString(3);
                boolean isFavorite = cursor.getInt(4) == 1 ? true : false;
                int playingCounter = cursor.getInt(5);
                int numberOfPlaying = cursor.getInt(6);


                Song newSong = new Song(songName, songImage, songURL, artistName, isFavorite, playingCounter, numberOfPlaying);
                resultList.add(newSong);

            } while (cursor.moveToNext());
        } else {
            //do not add anything
        }

        cursor.close();
        appDatabase.close();


        return resultList;
    }

    //6
    public void addToFavorites(String songName) {
        appDatabase = getWritableDatabase();

        String addToFavorites = "UPDATE " + SONGS_TABLE + " SET " + IS_FAVORITE_COLUMN + " =1 WHERE TRIM(SONG_NAME) " + " = '"+ songName+"'";
        appDatabase.execSQL(addToFavorites);
        appDatabase.close();

    }

    //7
    public void removeFromFavorites(String songName) {
        appDatabase = getWritableDatabase();

        String removeFromFavorites = "UPDATE " + SONGS_TABLE + " SET " + IS_FAVORITE_COLUMN + " =0 WHERE TRIM(SONG_NAME) " + " = '"+ songName+"'";
        appDatabase.execSQL(removeFromFavorites);
        appDatabase.close();

    }

    //8
    public boolean isFavorite(String songName) {

        appDatabase = getReadableDatabase();

        String getTheStateOfFavorite = "SELECT " + IS_FAVORITE_COLUMN + " FROM " + SONGS_TABLE + " WHERE TRIM(SONG_NAME) " + " = '"+ songName+"'";

        boolean state = false;
        Cursor cursor = appDatabase.rawQuery(getTheStateOfFavorite, null);

        if (cursor.moveToFirst()) {
            state = cursor.getInt(0) == 1 ? true : false;

        }
        cursor.close();
        appDatabase.close();
        return state;
    }

    public ArrayList<Song> returnFavoriteSongsFromDataBase() {
        ArrayList<Song> returnedList = new ArrayList<>();

        //getting the songs from the database
        String queryString = "SELECT * FROM " + SONGS_TABLE +" WHERE "+IS_FAVORITE_COLUMN+" = 1";
        appDatabase = getReadableDatabase();
        Cursor cursor = appDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //loop through the cursor and create new customer obj with the values in the row and then add it to the list
            do {
                String songName = cursor.getString(0);
                String songImage = cursor.getString(1);
                String songURL = cursor.getString(2);
                String artistName = cursor.getString(3);
                boolean isFavorite = cursor.getInt(4) == 1 ? true : false;
                int playingCounter = cursor.getInt(5);
                int numberOfPlaying = cursor.getInt(6);


                Song newSong = new Song(songName, songImage, songURL, artistName, isFavorite, playingCounter, numberOfPlaying);
                returnedList.add(newSong);

            } while (cursor.moveToNext());
        } else {
            //do not add anything
        }

        cursor.close();
        appDatabase.close();


        return returnedList;
    }

    //9
//WE WILL USE IT WHEN WE PLAY THE SONG
    public void updatePlayingCounter(String songName) {
        appDatabase = getReadableDatabase();
        String selectCounterQuery = "SELECT " + PLAYING_COUNTER_COLUMN + " FROM " + SONGS_TABLE + "  WHERE TRIM(SONG_NAME) " + " = '"+ songName+"'";

        int oldCounter = 0;
        Cursor cursor = appDatabase.rawQuery(selectCounterQuery, null);

        if (cursor.moveToFirst()) {
            oldCounter = cursor.getInt(0);
        }
        cursor.close();
        appDatabase.close();


        appDatabase = getWritableDatabase();
        String updateCounter = "UPDATE SONGS_TABLE SET PLAYING_COUNTER = " + (oldCounter + 1) + " WHERE TRIM(SONG_NAME) " + " = '"+ songName+"'";
        appDatabase.execSQL(updateCounter);
        appDatabase.close();

    }

    //10
    public ArrayList<Song> returnMostPlayed() {
        ArrayList<Song> resultList = new ArrayList<>();

        String queryString = "SELECT * FROM " + SONGS_TABLE + " WHERE " + PLAYING_COUNTER_COLUMN + " >=3 ";
        appDatabase = getReadableDatabase();
        Cursor cursor = appDatabase.rawQuery(queryString, null);


        if (cursor.moveToFirst()) {
            //loop through the cursor and create new customer obj with the values in the row and then add it to the list
            do {
                String songName = cursor.getString(0);
                String songImage = cursor.getString(1);
                String songURL = cursor.getString(2);
                String artistName = cursor.getString(3);
                boolean isFavorite = cursor.getInt(4) == 1 ? true : false;
                int playingCounter = cursor.getInt(5);
                int numberOfPlaying = cursor.getInt(6);


                Song newSong = new Song(songName, songImage, songURL, artistName, isFavorite, playingCounter, numberOfPlaying);
                resultList.add(newSong);

            } while (cursor.moveToNext());
        } else {
            //do not add anything
        }

        cursor.close();
        appDatabase.close();


        Collections.sort(resultList, new Comparator<Song>() {
            @Override
            public int compare(Song s1, Song s2) {
                return Integer.valueOf(s2.getPlayingCounter()).compareTo(s1.getPlayingCounter());

            }
        });
        return resultList;
    }


    //11
    public void updateTimeOfPlaying(String songName, long time) {
        appDatabase = getWritableDatabase();
        String updateTime = "UPDATE " + SONGS_TABLE + " SET " + TIME_OF_PLAYING_COLUMN + " = " + time + " WHERE TRIM(SONG_NAME) " + " = '"+ songName+"'";
        appDatabase.execSQL(updateTime);
        appDatabase.close();
    }


    //12
    public ArrayList<Song> returnRecentlyPlayed() {
        ArrayList<Song> resultList = new ArrayList<>();
//2022-08-20-12:00:00
        String queryString = "SELECT * FROM " + SONGS_TABLE + " WHERE " + TIME_OF_PLAYING_COLUMN + " >0 ";
        appDatabase = getReadableDatabase();
        Cursor cursor = appDatabase.rawQuery(queryString, null);


        if (cursor.moveToFirst()) {
            //loop through the cursor and create new customer obj with the values in the row and then add it to the list
            do {
                String songName = cursor.getString(0);
                String songImage = cursor.getString(1);
                String songURL = cursor.getString(2);
                String artistName = cursor.getString(3);
                boolean isFavorite = cursor.getInt(4) == 1 ? true : false;
                int playingCounter = cursor.getInt(5);
                int numberOfPlaying = cursor.getInt(6);


                Song newSong = new Song(songName, songImage, songURL, artistName, isFavorite, playingCounter, numberOfPlaying);
                resultList.add(newSong);

            } while (cursor.moveToNext());
        } else {
            //do not add anything
        }
        Collections.sort(resultList, new Comparator<Song>() {
            @Override
            public int compare(Song s1, Song s2) {
                return Integer.valueOf(s2.getTimeOfPlaying()).compareTo(s1.getTimeOfPlaying());

            }
        });
        cursor.close();
        appDatabase.close();
        return resultList;
    }




}

