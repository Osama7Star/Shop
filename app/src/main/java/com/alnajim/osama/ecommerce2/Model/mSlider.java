package com.alnajim.osama.ecommerce2.Model;

import com.google.gson.annotations.SerializedName;

public class mSlider
{
    @SerializedName("id")
    private String id;

    @SerializedName("image")
    private String imagePath ;

    private String text ;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }



}
