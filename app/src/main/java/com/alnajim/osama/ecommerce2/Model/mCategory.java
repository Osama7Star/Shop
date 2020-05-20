package com.alnajim.osama.ecommerce2.Model;

import com.google.gson.annotations.SerializedName;

public class mCategory
{
    @SerializedName("id")
    private String categoryId ;
    @SerializedName("name")
    private String categoryName ;
    @SerializedName("image")
    private String categoryImage ;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }



    public mCategory( )
    {

    }
    public mCategory(String categoryId, String categoryName, String categoryImage) {
        this.categoryId     = categoryId;
        this.categoryName   = categoryName;
        this.categoryImage  = categoryImage;
    }

}
