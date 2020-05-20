package com.alnajim.osama.ecommerce2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alnajim.osama.ecommerce2.Product;
import com.alnajim.osama.ecommerce2.BasicClass;
import com.alnajim.osama.ecommerce2.Model.mProducts;
import com.alnajim.osama.ecommerce2.R;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";



    private List<mProducts> productlist;
    private Context mContext;
    private Boolean isDiscount ;
    private String prodcutId  = "500"  ;
    private SQLiteHandler sqLiteHandler ;

    public ProductsAdapter(List<mProducts> productlist, Context mcontext)
    {

        this.mContext    = mcontext;
        this.productlist = productlist ;
        this.isDiscount  = isDiscount ;
        this.sqLiteHandler = new SQLiteHandler(mcontext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int position)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        final String productId     = productlist.get(position).getProductId();
        String baseImageUrl        = RetrofitClass.BASE_IMAGE_URL;
        String imageUrl            = baseImageUrl+productlist.get(position).getProductImage();
        String productName         = productlist.get(position).getProductName() ;
        String productPrice        = productlist.get(position).getProductPrice() ;
        String oldPrice            = productlist.get(position).getProductOldPrice();
        String isDiscounts         = productlist.get(position).getIsDiscount();
        Picasso.with(mContext).load(imageUrl).error(R.drawable.defaultproductimage).into(holder.productImage);

        holder.txtProductName.setText(productName);
        holder.txtCurrentPrice.setText(productPrice+"€");

        if (isDiscounts.equals("1"))
        {
            int discountRate = BasicClass.CalculateDiscountRate(productPrice,oldPrice);
            holder.txtDiscountRate.setVisibility(View.VISIBLE);
            holder.txtDiscountRate.setText(discountRate+"%");
            holder.rerlativeLayoutOldprice.setVisibility(View.VISIBLE);
            holder.txtOldPrice.setText(oldPrice+"€");

        }

        final boolean inBasket =  sqLiteHandler.getProductByIdBasket(productId);
        final boolean inWishList = sqLiteHandler.getProductByIdWishList(productId);
        if (inBasket || inWishList)
        {
            holder.imgBtnAddtoBasket.setEnabled(false);
            holder.imgBtnAddtoBasket.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
            holder.imgBtnAddtoWishList.setEnabled(false);
            holder.imgBtnAddtoWishList.setImageResource(R.drawable.love1);
        }
        Log.i("ProductIDDDD",productId);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent();
                intent.putExtra("ProductId",productId) ;
                intent.setClass(mContext, Product.class);
                mContext.startActivity(intent);
            }
        });



        holder.imgBtnAddtoBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                BasicClass.PrintMessage(mContext,mContext.getResources().getString(R.string.addedToBasket)) ;
                BasicClass.addToBasket(sqLiteHandler,productId,"20-20-2020");
                holder.imgBtnAddtoBasket.setEnabled(false);
                holder.imgBtnAddtoBasket.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
                holder.imgBtnAddtoWishList.setEnabled(false);
                holder.imgBtnAddtoWishList.setImageResource(R.drawable.love1);      }
        });

        holder.imgBtnAddtoWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                BasicClass.PrintMessage(mContext,mContext.getResources().getString(R.string.addedToWishList)) ;
                BasicClass.addToWishList(sqLiteHandler,productId,"20-20-2020");
                holder.imgBtnAddtoBasket.setEnabled(false);
                holder.imgBtnAddtoBasket.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
                holder.imgBtnAddtoWishList.setEnabled(false);
                holder.imgBtnAddtoWishList.setImageResource(R.drawable.love1);     }
        });
    }

    @Override
    public int getItemCount()
    {
        ///TODO THERE ARE A PROBLEM HERE TRY TO SOLVE IT
        try {
            return productlist.size();

        }
        catch (Exception e )
        {

        }
        return 0 ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView txtOldPrice;
        TextView txtCurrentPrice;
        TextView txtDiscountRate;
        TextView txtProductName;
        View viewOldPriceStroke;
        RelativeLayout rerlativeLayoutOldprice;
        LinearLayout llDiscountss;
        ImageButton imgBtnAddtoBasket;
        ImageView imgBtnAddtoWishList;
        TextView tvNumberOfProducts;


        RelativeLayout parent_layout;


        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.image);
            txtCurrentPrice = itemView.findViewById(R.id.txtCurrentPrice);
            txtOldPrice = itemView.findViewById(R.id.txtOldrice);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtDiscountRate = itemView.findViewById(R.id.txtDiscountRate);
            imgBtnAddtoBasket = itemView.findViewById(R.id.imgBtnAddtoBasket);
            imgBtnAddtoWishList = itemView.findViewById(R.id.imgBtnAddtoWishList);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            viewOldPriceStroke = itemView.findViewById(R.id.viewOldPriceStroke);
            rerlativeLayoutOldprice = itemView.findViewById(R.id.rerlativeLayoutOldprice);
        }

    }




}
