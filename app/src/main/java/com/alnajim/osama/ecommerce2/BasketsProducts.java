package com.alnajim.osama.ecommerce2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alnajim.osama.ecommerce2.Adapter.UserSelectionsAdapter;
import com.alnajim.osama.ecommerce2.Model.mProducts;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitInterface;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasketsProducts extends Activity
{
    ArrayList<String> productIds ;
    SQLiteHandler sqLiteHandler ;
    PageNavigationView tab ;
    RecyclerView rvBasketProducts;
    TextView tvItemsNumber,tvTotalMoney, tvIsEmpty,tvNumberOfProducts;
    Button btnKeepShopping;
    LinearLayout llBuyAllProducts;
    List<mProducts> product1 = new ArrayList<>();
    Boolean wishList = false ;
    ProgressBar progressbar;
    LinearLayout llEmpty;
    ImageView imgIsEmpty;



    Double totalMoney = 0.0 ;

    static ArrayList<mProducts> basketProducts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baskets_products);

        productIds = new ArrayList<>();
        sqLiteHandler = new SQLiteHandler(this);


        rvBasketProducts    = findViewById(R.id.rvBasketProducts);
        tvItemsNumber = findViewById(R.id.tvItemsNumber);
        tvTotalMoney  = findViewById(R.id.tvTotalMoney);
        tvIsEmpty     = findViewById(R.id.tvIsEmpty);
        tvNumberOfProducts = findViewById(R.id.tvNumberOfProducts);
        imgIsEmpty         = findViewById(R.id.imgIsEmpty);
        llBuyAllProducts = findViewById(R.id.llBuyAllProducts);
        btnKeepShopping  = findViewById(R.id.btnKeepShopping);
        progressbar      = findViewById(R.id.progressbar);
        llEmpty          = findViewById(R.id.llEmpty);
        tab = findViewById(R.id.tab);
        BasicClass basicClass = new BasicClass( this);
        basicClass.setNavigationBottom(tab);
        progressbar.setVisibility(View.VISIBLE);
        String FromWhere = getIntent().getStringExtra("FromWhere");

        if (FromWhere.equals("Basket"))
        {
            productIds = sqLiteHandler.getAllBasketItem();
            tvNumberOfProducts.setText(productIds.size() +" "+ getResources().getString(R.string.itemCountsBasket));
            llBuyAllProducts.setVisibility(View.VISIBLE);

        } else if (FromWhere.equals("Wishlist")) {
            productIds = sqLiteHandler.getAllWishlistItems();
            tvNumberOfProducts.setText(productIds.size() +" " +getResources().getString(R.string.itemCountsWishlist));
            wishList = true ;

        }


        String productId;
        if (productIds.size()==0)
        {
            tvNumberOfProducts.setVisibility(View.GONE);
            llBuyAllProducts.setVisibility(View.INVISIBLE);
            llBuyAllProducts.setVisibility(View.GONE);

            progressbar.setVisibility(View.INVISIBLE);
            llEmpty.setVisibility(View.VISIBLE);
            if (FromWhere.equals("Basket")) {
                tvIsEmpty.setText(getResources().getString(R.string.basketEmpty));

                imgIsEmpty.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_shopping_cart_black_24dp));
            }
               else if (FromWhere.equals("Wishlist"))
               {
                   tvIsEmpty.setText(getResources().getString(R.string.wishListEmpty));

                   imgIsEmpty.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
            }
        }
        for (int i = 0; i < productIds.size(); i++)
        {
            productId = productIds.get(i);

            getProductById(productId,wishList);
            Log.i("productSize", productId + " ");
        }



        btnKeepShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    startActivity(new Intent(BasketsProducts.this,MainActivity.class));

            }
        });
    }





    public void getProductById(String productId, final Boolean wishList)
    {
        RetrofitClass retrofitClass = new RetrofitClass();

        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);
        Call<List<mProducts>>call = retrofitInterface.getProductById1(productId);

        call.enqueue(new Callback<List<mProducts>>()
        {
            @Override
            public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response)
            {

                progressbar.setVisibility(View.INVISIBLE);

                Log.i("InsideBasket","InsideBasketInsideBasketInsideBasketInsideBasketInsideBasket");

                List<mProducts> product  = response.body();
                product1.add(product.get(0));

                IntitRecycleview(product1,rvBasketProducts,wishList);
               // initList(product);
                totalMoney+= Double.parseDouble(product.get(0).getProductPrice());
                tvTotalMoney.setText(totalMoney+"€");



            }

            @Override
            public void onFailure(Call<List<mProducts>> call, Throwable t)
            {
                Log.i("NoDataFromServerAgain2","This is failed Again2");
            }



        });


    }


    public void IntitRecycleview(List<mProducts> productsList , RecyclerView recyclerView, Boolean wishList){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        UserSelectionsAdapter adapter = new UserSelectionsAdapter(this,productsList,wishList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    ///////////////
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(BasketsProducts.this,MainActivity.class));
        finish();
    }

    void initList(List<mProducts> list )
    {
        basketProducts.add(list.get(0));
        Log.i("insidethelist",list.get(0).getProductName());
    }

    public void CompleterSelling(View v)
    {
        Intent intent = new Intent(BasketsProducts.this,CompleteBuying.class);
        startActivity(intent);

    }
    public void KeepShopping(View view )
    { Intent intent = new Intent(BasketsProducts.this,MainActivity.class);
        startActivity(intent);}

    public void AddToBasket(View view )
    {
        BasicClass.PrintMessage(this,"Added To Basket") ;
//        BasicClass.addToBasket(sqLiteHandler,productId,"20-20-2020");
    }


    public void back(View view){startActivity(new Intent(BasketsProducts.this,MainActivity.class));}

}

