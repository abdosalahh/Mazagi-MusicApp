package com.example.mpmazagi;
import java.io.Serializable;

public class Song implements Serializable {

    private String songName;
    private  String songImage;
    private  String songURL;
    private String artistName;
    private boolean isFavorite;
    private int playingCounter;
    private int timeOfPlaying;

    public Song(String songName, String songImage, String songURL, String artistName, boolean isFavorite, int playingCounter, int timeOfPlaying) {
        this.songName = songName;
        this.songImage = songImage;
        this.songURL = songURL;
        this.artistName = artistName;
        this.isFavorite = isFavorite;
        this.playingCounter = playingCounter;
        this.timeOfPlaying = timeOfPlaying;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongImage() {
        return songImage;
    }

    public String getSongURL() {
        return songURL;
    }

    public String getArtistName() {
        return artistName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getPlayingCounter() {
        return playingCounter;
    }

    public int getTimeOfPlaying() {
        return timeOfPlaying;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setPlayingCounter(int playingCounter) {
        this.playingCounter = playingCounter;
    }

    public void setTimeOfPlaying(int timeOfPlaying) {
        this.timeOfPlaying = timeOfPlaying;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songName='" + songName + '\'' +
                ", songImage='" + songImage + '\'' +
                ", songURL='" + songURL + '\'' +
                ", artistName='" + artistName + '\'' +
                ", isFavorite=" + isFavorite +
                ", playingCounter=" + playingCounter +
                ", timeOfPlaying=" + timeOfPlaying +
                '}';
    }




}
