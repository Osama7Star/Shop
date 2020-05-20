package com.alnajim.osama.ecommerce2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alnajim.osama.ecommerce2.R;

public class AllCategory extends AppCompatActivity {


    TextView id , categroy ;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        id      = findViewById(R.id.tvCategoryId) ;
        image   =  findViewById(R.id.imCategoryImage) ;
        categroy    =  findViewById(R.id.tvName);

        Intent intent = getIntent();
        String id1 = intent.getExtras().getString("Title");
        String category1 = intent.getExtras().getString("Description");
        int image1 = intent.getExtras().getInt("Thumbnail") ;

        // Setting values


        id.setText(id1+" - ");
        image.setImageResource(image1);
        categroy.setText(category1);



    }
}
