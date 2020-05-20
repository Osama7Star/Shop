package com.alnajim.osama.ecommerce2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alnajim.osama.ecommerce2.BasketsProducts;
import com.alnajim.osama.ecommerce2.BasicClass;
import com.alnajim.osama.ecommerce2.Model.mProducts;
import com.alnajim.osama.ecommerce2.Product;
import com.alnajim.osama.ecommerce2.R;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;
import com.squareup.picasso.Picasso;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class UserSelectionsAdapter extends RecyclerView.Adapter<UserSelectionsAdapter.MyViewHolder> {

    private Context mContext ;
    private List<mProducts> userProdcuts  ;
    private static Context mcontext;
    private SQLiteHandler sqLiteHandler ;
    private static    Double   totalMoney = 0.0 ;
    private Boolean wishList ;

    public UserSelectionsAdapter(Context mContext, List<mProducts> userProdcuts, Boolean wishlist) {
        this.mContext      = mContext;
        this.userProdcuts  = userProdcuts;
        this.mcontext      = mContext ;
        this.sqLiteHandler = new SQLiteHandler(mcontext) ;
        this.wishList      = wishlist;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.userselections,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final String productId    = userProdcuts.get(position).getProductId() ;
        final String productName  = userProdcuts.get(position).getProductName() ;
        final String oldPrice     = userProdcuts.get(position).getProductOldPrice();

        final String productImage = RetrofitClass.BASE_IMAGE_URL+userProdcuts.get(position).getProductImage() ;
        final String productPrice = userProdcuts.get(position).getProductPrice();
        final String isDiscounts  = userProdcuts.get(position).getIsDiscount() ;

            holder.tvProductName.setText(productName);
            holder.tvProductPrice.setText(productPrice + "€");
        //   Picasso.with(mcontext).load(productImage).error(R.drawable.defaultproductimage).into(holder.imgProductImage);
        List<String> dataset = new LinkedList<>(Arrays.asList("1", "2", "3", "4", "4"));
       holder.niceSpinner.attachDataSource(dataset);
        if (isDiscounts.equals("1"))
        {
            int disCountsRate = BasicClass.CalculateDiscountRate(productPrice,oldPrice);

            holder.txtDiscountRate.setVisibility(View.VISIBLE);
            holder.txtDiscountRate.setText(disCountsRate+"%");
            holder.rerlativeLayoutOldprice.setVisibility(View.VISIBLE);
            holder.tvOldPrice.setText(oldPrice + "€");

        }

        totalMoney += Double.parseDouble( productPrice);
//        holder.tvTotalMoney.setText(totalMoney+"$$");
       Log.i("FuckTest",productName);

        holder.cardview_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Product.class);

                // passing data to the CategoryProdcut activity
                intent.putExtra("ProductId",productId);
                mcontext.startActivity(intent);
                Log.i("Clicked","You Clicked Me " + productId) ;
                // start the activity
                mContext.startActivity(intent);

            }
        });
        holder.imgDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (wishList)
                {
                    if (sqLiteHandler.deleteWishListProductById(productId))
                    {
                        Intent intent = new Intent(mcontext,BasketsProducts.class);
                        intent.putExtra("FromWhere","Wishlist");
                        mContext.startActivity(intent);
                    }
                    else
                        BasicClass.PrintMessage(mcontext, "Not Deleted  " + productId);



                   }
                else
                    {
                    if (sqLiteHandler.deleteBasketProductById(productId)) {
                        Intent intent = new Intent(mcontext,BasketsProducts.class);
                        intent.putExtra("FromWhere","Basket");
                        mContext.startActivity(intent);

                    } else
                        BasicClass.PrintMessage(mcontext, "Not Deleted  "+ productId);


                }

            }
        });


    if(wishList)
    {
        holder.imgBtnAddtoBasket11.setVisibility(View.VISIBLE);


        final boolean inBasket = sqLiteHandler.getProductByIdBasket(productId);
        if (inBasket)
        {
            holder.imgBtnAddtoBasket11.setEnabled(false);
            holder.imgBtnAddtoBasket11.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
        }
    }

        holder.imgBtnAddtoBasket11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                BasicClass.PrintMessage(mcontext,mcontext.getResources().getString(R.string.addedToBasket)) ;
                BasicClass.addToBasket(sqLiteHandler,productId,BasicClass.date());
                holder.imgBtnAddtoBasket11.setEnabled(false);
                holder.imgBtnAddtoBasket11.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
                sqLiteHandler.deleteWishListProductById(productId);

                Intent intent = new Intent(mContext,BasketsProducts.class);
                intent.putExtra("FromWhere","Wishlist");
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userProdcuts.size() ;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvProductPrice , tvOldPrice , txtDiscountRate;
        TextView tvProductName;
        TextView tvTotalMoney ;
        TextView tvProductSmallDesc;
        ImageView imgProductImage;
        ImageView imgDeleteProduct ;
        ImageButton imgBtnAddtoBasket11;
        RelativeLayout parent_layout ,rerlativeLayoutOldprice;
        CardView cardview_id;
        NiceSpinner niceSpinner ;


        public MyViewHolder(View itemView)
        {
            super(itemView);

            tvProductPrice   =   itemView.findViewById(R.id.txtCurrentPrice) ;
            tvOldPrice       =   itemView.findViewById(R.id.tvOldPrice);
            txtDiscountRate  =   itemView.findViewById(R.id.txtDiscountRate);
            tvProductName    =   itemView.findViewById(R.id.tvProductName) ;
            tvTotalMoney     =   itemView.findViewById(R.id.tvTotalMoney);
            imgProductImage  =   itemView.findViewById(R.id.imgProductImage);
            imgDeleteProduct =   itemView.findViewById(R.id.imgDeleteProduct);
            imgBtnAddtoBasket11     = itemView.findViewById(R.id.imgBtnAddtoBasket11);
            rerlativeLayoutOldprice = itemView.findViewById(R.id.rerlativeLayoutOldprice);
            parent_layout    = itemView.findViewById(R.id.parent_layout1);
            cardview_id      = itemView.findViewById(R.id.cardview_id);
            niceSpinner      = itemView.findViewById(R.id.nice_spinner);
        }
    }


}
