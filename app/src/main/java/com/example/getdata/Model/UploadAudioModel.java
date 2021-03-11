package com.example.getdata.Model;

import com.google.firebase.database.Exclude;

public class UploadAudioModel {

    public String songTitle,songDuration,songLink,mKey;

    public UploadAudioModel(String songTitle, String songDuration, String songLink, String mKey) {

        if(songTitle.trim().equals(""))
        {
            songTitle = "No title";
        }

        this.songTitle = songTitle;
        this.songDuration = songDuration;
        this.songLink = songLink;
        this.mKey = mKey;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
