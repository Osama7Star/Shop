package com.alnajim.osama.ecommerce2.Model;

import com.google.gson.annotations.SerializedName;

public class mProducts
{
    @SerializedName("id")
    private String productId ;
    @SerializedName("name")
    private String productName ;
    @SerializedName("image")
    private String productImage ;
    @SerializedName("current_price")
    private String productPrice ;
    @SerializedName("category_id")
    private String productCategory;
    @SerializedName("old_price")
    private String productOldPrice ;
    @SerializedName("date")
    private String productDate;
    @SerializedName("small_desc")
    private String productSmallDesc ;
    @SerializedName("desc")
    private String productDesc ;
    @SerializedName("indirim")
    private String isDiscount ;

    public String getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }



    public String getProductSmallDesc() {
        return productSmallDesc;
    }

    public void setProductSmallDesc(String productSmallDesc) {
        this.productSmallDesc = productSmallDesc;
    }




    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }




    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductOldPrice() {
        return productOldPrice;
    }

    public void setProductOldPrice(String productIndirim) {
        this.productOldPrice = productIndirim;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }


    public mProducts(String productId, String productName, String productImage, String productPrice, String category, String productOldPrice, String date) {

        this.productId    = productId;
        this.productName  = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productCategory      = category;
        this.productOldPrice      = productOldPrice;
        this.productDate          = date;
    }





    public mProducts(String productId, String productName, String productImage, String productPrice) {
        this.productId    = productId;
        this.productName  = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }
    public mProducts(){
        this.productName  = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
