package com.example.getdata;

public class error {

String image2,image1,image3;

    public  error(){}

    public error(String s1, String s2, String s3)
    {
        this.image2 = s1;
        this.image1 = s2;
        this.image3=s3;

    }

    public String getImage2() {
        return image2;
    }

    public void setIamag1(String img1) {
        this.image2 = img1;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }
}
