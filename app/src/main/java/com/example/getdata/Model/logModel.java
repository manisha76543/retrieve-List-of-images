package com.example.getdata.Model;

public class logModel {

String img,arrival_time,is_known,audio_message;


public logModel(){}

    public logModel(String img, String arrival_time, String is_known, String audio_message) {
        this.img = img;
        this.arrival_time = arrival_time;
        this.is_known = is_known;
        this.audio_message = audio_message;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getIs_known() {
        return is_known;
    }

    public void setIs_known(String is_known) {
        this.is_known = is_known;
    }

    public String getAudio_message() {
        return audio_message;
    }

    public void setAudio_message(String audio_message) {
        this.audio_message = audio_message;
    }
}
