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

import androidx.recyclerview.widget.RecyclerView;

import com.alnajim.osama.ecommerce2.MainActivity;
import com.alnajim.osama.ecommerce2.Product;
import com.alnajim.osama.ecommerce2.BasicClass;
import com.alnajim.osama.ecommerce2.Model.mProducts;
import com.alnajim.osama.ecommerce2.R;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder>
{

    private ArrayList<mProducts> productsList = new ArrayList<>();
    private Context mcontext;
    private SQLiteHandler sqLiteHandler ;

    public SalesAdapter(List<mProducts> productsList, Context mcontext)
    {
        this.productsList = (ArrayList<mProducts>) productsList;
        this.mcontext     = mcontext;
        this.sqLiteHandler = new SQLiteHandler(mcontext) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int position)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salesview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

            final String prodcutId    = productsList.get(position).getProductId();
            String productImageUrl    = RetrofitClass.BASE_IMAGE_URL+productsList.get(position).getProductImage();
            String productName        = productsList.get(position).getProductName() ;
            String productSmallDesc   = productsList.get(position).getProductSmallDesc();
            String productPrice       = productsList.get(position).getProductPrice() ;
            String productOldPrice    = productsList.get(position).getProductOldPrice();
            String isDiscounted       = productsList.get(position).getIsDiscount();

             Picasso.with(mcontext).load(productImageUrl).error(R.drawable.defaultproductimage).into(holder.productImage);


            holder.txtProductName.setText(productName +"");
            holder.txtCurrentPrice.setText(productPrice+"€");
            holder.txtProductDesc.setText(productSmallDesc);
            if (!isDiscounted.equals("0"))
            {
                long productDiscountRate = BasicClass.CalculateDiscountRate(productPrice,productOldPrice) ;

                holder.rerlativeLayoutOldprice.setVisibility(View.VISIBLE);
                holder.txtDiscountRate.setVisibility(View.VISIBLE);
                holder.txtDiscountRate.setText(productDiscountRate+"%");

                holder.txtOldPrice.setVisibility(View.VISIBLE);
                holder.txtOldPrice.setText(productOldPrice+"€");
                holder.viewOldPriceStroke.setVisibility(View.VISIBLE);

                holder.rerlativeLayoutOldprice.setVisibility(View.VISIBLE);


            }
            final boolean inBasket   =  sqLiteHandler.getProductByIdBasket(prodcutId);
            final boolean inWishList =  sqLiteHandler.getProductByIdWishList(prodcutId);
            if (inBasket || inWishList)
            {
                holder.imgBtnAddtoBasket.setEnabled(false);
                holder.imgBtnAddtoBasket.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
                holder.imgBtnAddtoWishList.setEnabled(false);
                holder.imgBtnAddtoWishList.setImageResource(R.drawable.love1);
            }

            holder.parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    Intent intent = new Intent();
                    intent.putExtra("ProductId",prodcutId) ;
                    intent.setClass(mcontext, Product.class);
                    mcontext.startActivity(intent);
                }
            });

            holder.imgBtnAddtoBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    BasicClass.PrintMessage(mcontext,mcontext.getResources().getString(R.string.addedToBasket)) ;
                    BasicClass.addToBasket(sqLiteHandler,prodcutId,BasicClass.date());
                    holder.imgBtnAddtoBasket.setEnabled(false);
                    holder.imgBtnAddtoWishList.setEnabled(false);
                    holder.imgBtnAddtoWishList.setImageResource(R.drawable.love1);
                   // mcontext.startActivity(new Intent(mcontext, MainActivity.class));

                }
            });
            holder.imgBtnAddtoWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    BasicClass.PrintMessage(mcontext,mcontext.getResources().getString(R.string.addedToWishList)) ;
                    BasicClass.addToWishList(sqLiteHandler,prodcutId,BasicClass.date());
                    holder.imgBtnAddtoBasket.setEnabled(false);
                    holder.imgBtnAddtoWishList.setEnabled(false);
                    holder.imgBtnAddtoWishList.setImageResource(R.drawable.love1);
                    //mcontext.startActivity(new Intent(mcontext, MainActivity.class));

                }
            });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView txtOldPrice,txtCurrentPrice,txtDiscountRate,txtProductName ,txtProductDesc;
        ImageButton imgBtnAddtoBasket;
        ImageView imgBtnAddtoWishList;
        View viewOldPriceStroke ;
        RelativeLayout rerlativeLayoutOldprice;



        RelativeLayout parent_layout;


        public ViewHolder( View itemView)
        {
            super(itemView);
            productImage    = itemView.findViewById(R.id.image);
            txtOldPrice     = itemView.findViewById(R.id.txtOldrice);
            txtCurrentPrice = itemView.findViewById(R.id.txtCurrentPrice);
            txtDiscountRate = itemView.findViewById(R.id.txtDiscountRate);
            txtProductName  = itemView.findViewById(R.id.txtProductName);
            txtProductDesc  = itemView.findViewById(R.id.txtProductDesc);
            imgBtnAddtoBasket   = itemView.findViewById(R.id.imgBtnAddtoBasket);
            imgBtnAddtoWishList = itemView.findViewById(R.id.imgBtnAddtoWishList);

            parent_layout   = itemView.findViewById(R.id.parent_layout);
            viewOldPriceStroke = itemView.findViewById(R.id.viewOldPriceStroke);
            rerlativeLayoutOldprice         = itemView.findViewById(R.id.rerlativeLayoutOldprice);
        }
    }

    @Override
    public int getItemCount()
    {

        try {
            return productsList.size();

        }
        catch (Exception e)
        {

        }

        return 0 ;


    }




}
