package com.alnajim.osama.ecommerce2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alnajim.osama.ecommerce2.Model.mCategory;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitInterface;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitRequest;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProdcut extends AppCompatActivity {

    ProgressBar progressBar ;
    RelativeLayout rlayout;
    TextView tvCategoryName,tvNumberOfProducts  ;
    PageNavigationView tab ;
    String categoryId ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoryproducts);

        progressBar    = findViewById(R.id.progressbar);
        rlayout        = findViewById(R.id.rlayout);
        tvCategoryName = findViewById(R.id.tvCategoryName);
        tab            = findViewById(R.id.tab);
        tvNumberOfProducts = findViewById(R.id.tvNumberOfProducts);
        RecyclerView rvCategoryProducts = findViewById(R.id.rvCategoryProducts);

        BasicClass basicClass = new BasicClass( this);
        basicClass.setNavigationBottom(tab);


        Bundle extras = getIntent().getExtras();
        categoryId   = extras.getString("categoryId");

        RetrofitRequest retrofitRequest = new RetrofitRequest(this,progressBar,rlayout);

        Intent intent = getIntent();
        String fromWhere = intent.getStringExtra("FromWhere");
        retrofitRequest.GetAllCategoryProducts1(categoryId, rvCategoryProducts,tvNumberOfProducts);

        if (fromWhere!=null)
        if (fromWhere.equals("Discounted"))
        {
            retrofitRequest.GetDiscountedProducts(rvCategoryProducts,categoryId ,tvNumberOfProducts);
            tvCategoryName.setText(getResources().getString(R.string.discounted));
            return;
        }

            retrofitRequest.GetAllCategoryProducts1(categoryId, rvCategoryProducts,tvNumberOfProducts);
        printCategoryName ();
    }



    public void printCategoryName ()
    {

        RetrofitClass retrofitClass = new RetrofitClass() ;
        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);

        Call<List<mCategory>> call = retrofitInterface.GetAllCateogry();
        call.enqueue(new Callback<List<mCategory>>()
        {
            @Override
            public void onResponse(Call<List<mCategory>> call, Response<List<mCategory>> response)
            {



                List<mCategory> posts;
                posts = response.body();
                printCategoryName(posts);

//                AllCategory(posts,rvAllCategory);
                Log.i("DataFromServer","The size is "+posts.size());

            }

            @Override
            public void onFailure(Call<List<mCategory>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });
    }

    void printCategoryName (List<mCategory>list)
    {
        for (int i = 0 ; i <list.size();i++)
            if(categoryId.equals(list.get(i).getCategoryId()))
            {
                tvCategoryName.setText(list.get(i).getCategoryName());

            }
    }

    public void back(View view )
    {
        finish();
    }


}
