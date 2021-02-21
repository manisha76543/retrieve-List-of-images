package com.example.getdata;

import java.util.ArrayList;

public class Upload {

    private ArrayList<String> image= new ArrayList<>();

    private String image1;

    public Upload()
    {
    }

    public Upload(String image1)
    {
        this.image1 = image1;
    }

    public String getImage1() {
        image.add(image1);
        return image1;
    }

    public void setImage(String image1) {
        this.image1 = image1;
    }


//    int num=1;

// public Upload(){}
//
//    public ArrayList<String> getImage() {
//
//     return image;
//    }
//
//    public void setImage(ArrayList<String> image) {
//        this.image = image;
//    }

}
