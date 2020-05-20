package com.alnajim.osama.ecommerce2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alnajim.osama.ecommerce2.BasicClass;
import com.alnajim.osama.ecommerce2.CategoryProdcut;
import com.alnajim.osama.ecommerce2.Model.mCategory;

import com.alnajim.osama.ecommerce2.R;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.MyViewHolder> {

    private Context mContext ;
    private List<mCategory> Categories  ;
    private Context mcontext;


    public AllCategoriesAdapter(Context mContext, List<mCategory> Categories) {
        this.mContext    = mContext;
        this.Categories  = Categories;
        this.mcontext    = mContext ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.allcategories,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final String categoryId    = Categories.get(position).getCategoryId() ;
        final String categoryName  = Categories.get(position).getCategoryName() ;
        final String categoryImage = Categories.get(position).getCategoryImage() ;
        final String imagePath = RetrofitClass.BASE_IMAGE_URL+categoryImage;
        Log.i("Thesizeofcategoryi",Categories.size()+"Size") ;
        holder.tvSubCategories.setText(categoryName);
//        Glide.with(mcontext)
//                .asBitmap()
//                .load(imagePath+categoryImage)
//                .into(holder.imgSubCategories);
        Picasso.with(mcontext).load(imagePath).error(R.drawable.defaultcategory).into(holder.imgSubCategories);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, CategoryProdcut.class);
                Bundle extras = new Bundle();

                // passing data to the CategoryProdcut activity

                extras.putString("categoryId",categoryId);
                extras.putString("categoryName",categoryName);
                intent.putExtras(extras);
                // start the activity
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvSubCategories;
        ImageView imgSubCategories;
        CardView  cardView ;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            tvSubCategories       = (TextView)  itemView.findViewById(R.id.tvCategoryName) ;
            imgSubCategories      = (ImageView) itemView.findViewById(R.id.imageCategory);
            cardView              = (CardView)  itemView.findViewById(R.id.cardview_idCategory);




        }
    }


}
