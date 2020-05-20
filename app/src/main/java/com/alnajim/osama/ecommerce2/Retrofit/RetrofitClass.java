package com.alnajim.osama.ecommerce2.Retrofit;

import android.widget.ProgressBar;

import com.alnajim.osama.ecommerce2.Model.mProducts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass
{
     public static Retrofit retrofitClass;
     public RetrofitInterface retrofitInterface ;
     private static final String BASE_URL       = "http://clinic.htc-nablus.com/api/web/v1/recipes/";
     public  static final String BASE_IMAGE_URL = "http://clinic.htc-nablus.com/api/web/";
     ProgressBar progressBar ;


    public  Retrofit getRetrofitInstance()
    {
        if (retrofitClass == null) {
            retrofitClass = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            retrofitInterface = retrofitClass.create(RetrofitInterface.class);

        }
        return retrofitClass;
    }


    public Call<List<mProducts>> getProductById1(String prdouctId){
        return retrofitInterface.getProductById1(prdouctId);
    }
}
