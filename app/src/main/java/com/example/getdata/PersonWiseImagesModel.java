package com.example.getdata;

import android.net.Uri;

import java.util.ArrayList;

public class PersonWiseImagesModel {


    private String PersonName;

    private ArrayList<String> imageUrl;

    public PersonWiseImagesModel(){}

    public PersonWiseImagesModel(String Personname, ArrayList<String> imageurl)
    {
        this.PersonName = Personname;
        this.imageUrl = imageurl;
    }

    public PersonWiseImagesModel(ArrayList<String> imageUrl)
    {
        this.imageUrl = imageUrl;

    }

    public boolean PersonWiseImageModel(ArrayList<String> imageUrl)
    {
        return true;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

}
