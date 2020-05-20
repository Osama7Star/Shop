package com.alnajim.osama.ecommerce2.Retrofit;

import com.alnajim.osama.ecommerce2.Model.mCategory;
import com.alnajim.osama.ecommerce2.Model.mProducts;
import com.alnajim.osama.ecommerce2.Model.mSlider;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface
{

        @GET("slider?access-token=test")
        Call<List<mSlider>> getSlider();

        @GET("products?access-token=test")
        Call<List<mProducts>> getProducts();


        @GET("category?access-token=test")
        Call<List<mCategory>> GetAllCateogry();

        @GET("products?access-token=test&categoryId=")
        Call<List<mProducts>> GetAllProductsInCategory (@Query("categoryId") String categoryId);


        @GET("product?access-token=test&productId=")
        Call<List<mProducts>> getProductById1 (@Query("productId") String productId);

        @GET("category?access-token=test")
        Call<List<mCategory>> getCategoryInfo();


        @GET("category?access-token=test")
        Call<List<mProducts>> Search (@Query("text") String text);



        @GET("newProducts?access-token=test")
        Call<List<mProducts>> getNewProducts ();


        @GET("mostSold?access-token=test")
        Call<List<mProducts>> getMostSoldProudcts ();

        @GET("mostSold?access-token=test")
        Call<List<mProducts>> getDiscountedProducts ();

        // Server user login url
                public static String URL_LOGIN = "http://192.168.0.102/android_login_api/login.php";

                // Server user register url
                public static String URL_REGISTER = "http://192.168.0.102/android_login_api/register.php";








}
