package com.alnajim.osama.ecommerce2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alnajim.osama.ecommerce2.Retrofit.RetrofitRequest;

import me.majiajie.pagerbottomtabstrip.PageNavigationView;

public class NewMostSold extends AppCompatActivity {
    ProgressBar progressBar ;
    RelativeLayout rlayout;
    PageNavigationView tab ;
    TextView tvNumberOfProducts;

    TextView tvCategoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_most_sold);
        progressBar    = findViewById(R.id.progressbar);
        rlayout        = findViewById(R.id.rlayout);
        tvCategoryName =  findViewById(R.id.tvCategoryName);
        tvNumberOfProducts = findViewById(R.id.tvNumberOfProducts);
        tab            = findViewById(R.id.tab);

//        if(BasicClass.isInternetAvailable() ) {
        RecyclerView rvCategoryProducts = findViewById(R.id.rvMostNewProducts);

        BasicClass basicClass = new BasicClass( this);
        basicClass.setNavigationBottom(tab);

        RetrofitRequest retrofitRequest = new RetrofitRequest(this,progressBar,rlayout);
        Intent intent = getIntent();

        String fromWhere = intent.getStringExtra("FromWhere");

        if (fromWhere.equals("MostSold")) {
            retrofitRequest.GetMostSoldProducts("1", rvCategoryProducts,tvNumberOfProducts);
            tvCategoryName.setText(getResources().getString(R.string.mostSoldProducts));
        }

        else if (fromWhere.equals("NEWPRODUCTS")) {
            retrofitRequest.GetNewProducts("1", rvCategoryProducts,tvNumberOfProducts);
            tvCategoryName.setText(getResources().getString(R.string.newProduts));

        }
        else {
            BasicClass.PrintMessage(this,"Error");
        }



    }

    public void back(View view){startActivity(new Intent(NewMostSold.this,MainActivity.class));}
}
