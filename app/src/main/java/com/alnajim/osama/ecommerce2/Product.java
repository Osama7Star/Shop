package com.alnajim.osama.ecommerce2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alnajim.osama.ecommerce2.Model.mProducts;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitInterface;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitRequest;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity {

    TextView tvNoInternet ;
    RelativeLayout rlProducts;
    LinearLayout llNoInternet;
    Button btnReload;
    ImageButton imgBtnAddtoBasket;
    ImageView imgBtnAddtoWishList  ;

    ProgressBar progressBar;
    RelativeLayout rlCheckInternt ;
    TextView productName,productPrice,productSmallDesc,productDesc,productOldPrice,txtDiscountRate;

    String productId ;
    ImageView image;

    RelativeLayout rerlativeLayoutOldprice;
    LinearLayout llSimilarProducts;


    RecyclerView rvSimilarProducts ;
    PageNavigationView tab ;

    SQLiteHandler sqLiteHandler = new SQLiteHandler(this ) ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        rlProducts        = findViewById(R.id.rlProducts);
        productName       = findViewById(R.id.tvProductName) ;
        productPrice      = findViewById(R.id.tvProductPrice) ;
        txtDiscountRate   = findViewById(R.id.txtDiscountRate);
        productDesc       = findViewById(R.id.tvProductDesc);
        productSmallDesc  = findViewById(R.id.tvProductSmallDesc);
        productOldPrice   = findViewById(R.id.txtOldrice);
        imgBtnAddtoBasket = findViewById(R.id.imgBtnAddtoBasket);
        imgBtnAddtoWishList       = findViewById(R.id.imgBtnAddtoWishList);
        rvSimilarProducts         = findViewById(R.id.rvSimilaritem);
        rerlativeLayoutOldprice   = findViewById(R.id.rerlativeLayoutOldprice);
        rlCheckInternt            = findViewById(R.id.rlCheckInternt);
        llSimilarProducts         = findViewById(R.id.llSimilarProducts);
        progressBar               = findViewById(R.id.progressbar);
        tab                       = findViewById(R.id.tab);
        image                     = findViewById(R.id.image);
        Intent intent = getIntent();
        productId = intent.getStringExtra("ProductId");
         Log.i("ProductIdProductId",productId);
        getProductById(productId);


        BasicClass basicClass = new BasicClass( this);
        basicClass.setNavigationBottom(tab);

        final boolean inBasket   =  sqLiteHandler.getProductByIdBasket(productId);
        final boolean inWishList =  sqLiteHandler.getProductByIdWishList(productId);
        if (inBasket || inWishList)
        {
            imgBtnAddtoBasket.setEnabled(false);
            imgBtnAddtoBasket.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
            imgBtnAddtoWishList.setEnabled(false);
            imgBtnAddtoWishList.setImageResource(R.drawable.love1);
        }


    }

    public void AddToBasket(View view )
    {
        BasicClass.PrintMessage(this,"Added To Basket") ;
        BasicClass.addToBasket(sqLiteHandler,productId,"20-20-2020");
        imgBtnAddtoBasket.setEnabled(false);
        imgBtnAddtoBasket.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
        imgBtnAddtoWishList.setEnabled(false);
        imgBtnAddtoWishList.setImageResource(R.drawable.love1);
    }
    public void AddToWishList(View view )
    {
        BasicClass.PrintMessage(this,"Added To WishList") ;
        BasicClass.addToWishList(sqLiteHandler,productId,"20-20-2020");
        imgBtnAddtoBasket.setEnabled(false);
        imgBtnAddtoBasket.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
        imgBtnAddtoWishList.setEnabled(false);
        imgBtnAddtoWishList.setImageResource(R.drawable.love1);
    }

    public void GetData (List<mProducts> mproducts ){



        RelativeLayout re = findViewById(R.id.mymymymmy);
        re.requestFocus();

        ////////////////////
        /// SET DATA
        while (productName.getText()=="")
        {

            rlCheckInternt.setVisibility(View.VISIBLE);
            imgBtnAddtoBasket.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            rlCheckInternt.setVisibility(View.GONE);

            productName.setText(mproducts.get(0).getProductName());
            productPrice.setText(mproducts.get(0).getProductPrice()+"€");

            String productDescription = mproducts.get(0).getProductSmallDesc();
            if (!productDescription.equals("")) {
                productDesc.setVisibility(View.VISIBLE);
                productDesc.setText(mproducts.get(0).getProductSmallDesc());

                // TODO productDesc.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
            productSmallDesc.setText(mproducts.get(0).getProductDesc());

            Picasso.with(this).load(RetrofitClass.BASE_IMAGE_URL+mproducts.get(0).getProductImage()).error(R.drawable.defaultproductimage).into(image);

            /// PRODUCTS IS DISCOUNT
            if (!mproducts.get(0).getIsDiscount().equals("0"))
            {
                rerlativeLayoutOldprice.setVisibility(View.VISIBLE);
                 //mproducts.get(0).getProductOldPrice()+
                String currentPrice = mproducts.get(0).getProductPrice();
                String oldPrice     = mproducts.get(0).getProductOldPrice() ;
                int disCountRate    = BasicClass.CalculateDiscountRate(currentPrice,oldPrice);
                productOldPrice.setText(oldPrice+"€");

                txtDiscountRate.setText(disCountRate+"%");

            }


            //////GET SIMILAR PRODUCTS
            RetrofitRequest retrofitRequest = new RetrofitRequest(this);
            retrofitRequest.GetAllCategoryProducts(mproducts.get(0).getProductCategory(),rvSimilarProducts);

        }






    }



    public  void  getProductById( String productId)
    {

        RetrofitClass retrofitClass = new RetrofitClass() ;
        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);

        Call<List<mProducts>> call = retrofitInterface.getProductById1(productId);
        call.enqueue(new Callback<List<mProducts>>()
        {
            @Override
            public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response)
            {

                List<mProducts> product;
                product = response.body();
                GetData(product);

            }

            @Override
            public void onFailure(Call<List<mProducts>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
