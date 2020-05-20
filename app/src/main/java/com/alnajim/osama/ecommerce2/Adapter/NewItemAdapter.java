package com.alnajim.osama.ecommerce2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alnajim.osama.ecommerce2.BasicClass;
import com.alnajim.osama.ecommerce2.Model.mProducts;
import com.alnajim.osama.ecommerce2.Product;
import com.alnajim.osama.ecommerce2.R;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class NewItemAdapter extends RecyclerView.Adapter<NewItemAdapter.MyViewHolder> {

    private Context mContext ;
    private List<mProducts> Categories  ;
    private static Context mcontext;
    SQLiteHandler sqLiteHandler;


    public NewItemAdapter(Context mContext, List<mProducts> Categories) {
        this.mContext    = mContext;
        this.Categories  = Categories;
        this.mcontext    = mContext ;
        sqLiteHandler = new SQLiteHandler(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.newitemview,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final String productIdId    = Categories.get(position).getProductId() ;
        final String productName    = Categories.get(position).getProductName() ;
        final String productPrice   = Categories.get(position).getProductPrice() ;
        final String isDiscounts    = Categories.get(position).getIsDiscount();
        final String productOldPrice= Categories.get(position).getProductOldPrice();
        final String imagePath      = RetrofitClass.BASE_IMAGE_URL+Categories.get(position).getProductImage();
        holder.tvNewItemName.setText(productName);
        holder.tvNewItemPrice.setText(productPrice+"€");

        Picasso.with(mcontext).load(imagePath).error(R.drawable.defaultproductimage).into(holder.imNewItemImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Product.class);

                // passing data to the CategoryProdcut activity
                intent.putExtra("ProductId",productIdId);

                // start the activity
                mContext.startActivity(intent);

            }
        });

        if (isDiscounts.equals("1"))
        {
            long productDiscountRate =   BasicClass.CalculateDiscountRate(productPrice,productOldPrice) ;

            holder.rerlativeLayoutOldprice.setVisibility(View.VISIBLE);
            holder.txtDiscountRate.setVisibility(View.VISIBLE);
            holder.txtDiscountRate.setText(productDiscountRate+"%");

            holder.txtOldPrice.setVisibility(View.VISIBLE);
            holder.txtOldPrice.setText(productOldPrice+"€");

            holder.rerlativeLayoutOldprice.setVisibility(View.VISIBLE);


        }
        final boolean inBasket =  sqLiteHandler.getProductByIdBasket(productIdId);
        final boolean inWishList = sqLiteHandler.getProductByIdWishList(productIdId);
        if (inBasket || inWishList)
        {
            holder.imgBtnAddtoBasket.setEnabled(false);
            holder.imgBtnAddtoBasket.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
            holder.imgBtnAddtoWishList.setEnabled(false);
        }



        holder.imgBtnAddtoBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                BasicClass.PrintMessage(mcontext, mContext.getResources().getString(R.string.addedToBasket));
                BasicClass.addToBasket(sqLiteHandler,productIdId,BasicClass.date());
                holder.imgBtnAddtoBasket.setEnabled(false);
                holder.imgBtnAddtoWishList.setEnabled(false);

            }
        });
        holder.imgBtnAddtoWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                BasicClass.PrintMessage(mcontext,mContext.getResources().getString(R.string.addedToWishList)) ;
                BasicClass.addToWishList(sqLiteHandler,productIdId,BasicClass.date());
                holder.imgBtnAddtoBasket.setEnabled(false);
                holder.imgBtnAddtoWishList.setEnabled(false);

            }
        });



    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvNewItemName,tvNewItemPrice,txtDiscountRate,txtOldPrice;
        ImageView imNewItemImage;
        CardView  cardView ;
        ImageButton imgBtnAddtoWishList , imgBtnAddtoBasket;
        RelativeLayout rerlativeLayoutOldprice;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            tvNewItemName       =   itemView.findViewById(R.id.tvNewItemName) ;
            tvNewItemPrice      =   itemView.findViewById(R.id.tvNewItemPrice) ;
            imNewItemImage      =   itemView.findViewById(R.id.imNewItemImage);
            imgBtnAddtoWishList =   itemView.findViewById(R.id.imgBtnAddtoWishList);
            imgBtnAddtoBasket   =   itemView.findViewById(R.id.imgBtnAddtoBasket);
            cardView            =   itemView.findViewById(R.id.cvNewItem);
            rerlativeLayoutOldprice  = itemView.findViewById(R.id.rerlativeLayoutOldprice);
            txtDiscountRate     =   itemView.findViewById(R.id.txtDiscountRate);
            txtOldPrice         =   itemView.findViewById(R.id.txtOldrice);


        }
    }


}
