package com.example.getdata;

public class Audiofetch {

    String title,url,audioName;

    public Audiofetch()
    {

    }

    public Audiofetch(String title,String url,String audioName)
    {
        this.title = title;
        this.url = url;
        this.audioName=audioName;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
