package com.alnajim.osama.ecommerce2.Retrofit;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alnajim.osama.ecommerce2.Adapter.AllCategoriesAdapter;
import com.alnajim.osama.ecommerce2.Adapter.NewItemAdapter;
import com.alnajim.osama.ecommerce2.Adapter.ProductsAdapter;
import com.alnajim.osama.ecommerce2.Adapter.SalesAdapter;
import com.alnajim.osama.ecommerce2.MainActivity;
import com.alnajim.osama.ecommerce2.Model.mCategory;
import com.alnajim.osama.ecommerce2.Model.mProducts;
import com.alnajim.osama.ecommerce2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitRequest  {


    Context context ;
    Boolean isDiscount = false ;
    ProgressBar progressBar ;
    RelativeLayout relativeLayout ;
    public static ArrayList<mCategory> categoryInfo  = new ArrayList<>();

    public RetrofitRequest(Context context, ProgressBar progressBar , RelativeLayout relativeLayout)
    {
        this.context        = context;
        this.progressBar    = progressBar ;
        this.relativeLayout = relativeLayout ;

    }

    public RetrofitRequest(Context context)
    {
        this.context        = context;


    }




    public  void  getProducts(final  RecyclerView recyclerView)
    {

        RetrofitClass retrofitClass = new RetrofitClass() ;
        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);

        Call<List<mProducts>> call = retrofitInterface.getProducts();
        call.enqueue(new Callback<List<mProducts>>()
        {
            @Override
            public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response)
            {


                progressBar.setVisibility(View.INVISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                List<mProducts> posts;
                posts = response.body();




                IntitRecycleview(posts,recyclerView);

            }

            @Override
            public void onFailure(Call<List<mProducts>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });
    }
    public void IntitRecycleview(List<mProducts> productsList , RecyclerView recyclerView){


        initRecycler(recyclerView , isDiscount,context , productsList );
    }
    private void initRecycler(RecyclerView recyclerView , Boolean isDiscount, Context context , List<mProducts> productsList)
    {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        SalesAdapter adapter = new SalesAdapter(productsList, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
////////////////////////////
public void IntitRecycleview1(List<mProducts> productsList , RecyclerView recyclerView){


    initRecycler1(recyclerView , isDiscount,context , productsList );
}
    private void initRecycler1(RecyclerView recyclerView , Boolean isDiscount, Context context , List<mProducts> productsList)
    {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        SalesAdapter adapter = new SalesAdapter(productsList, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////// GET ALL CATEOGRY    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void getAllCateogry (final  RecyclerView rvAllCategory)
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




                AllCategory(posts,rvAllCategory);
                Log.i("DataFromServer","The size is "+posts.size());

            }

            @Override
            public void onFailure(Call<List<mCategory>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });
    }

    void AllCategory (List<mCategory> arr, RecyclerView rvAllCategory)
    {



        AllCategoriesAdapter myAdapter = new AllCategoriesAdapter(context,arr);
        rvAllCategory.setLayoutManager(new GridLayoutManager(context,2));
        rvAllCategory.setAdapter(myAdapter);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////// GET ALL PRODUCT IN CATEGORY    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void GetAllCategoryProducts(String categoryId, final RecyclerView recyclerView)
    {

        RetrofitClass retrofitClass = new RetrofitClass() ;

        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);
        Call<List<mProducts>> call = retrofitInterface.GetAllProductsInCategory(categoryId);
        call.enqueue(new Callback<List<mProducts>>()
        {
            @Override
            public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response) {


                try
                {

                    List<mProducts> posts;
                    posts = response.body();
                 //   allCategoryProduct(posts,recyclerView);
                    IntitRecycleview(posts,recyclerView);

                } catch (Exception e)
                {

                }




            }

            @Override
            public void onFailure(Call<List<mProducts>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });
    }
////////////////////// FOR PRODUCTS CATEGORY
public void GetAllCategoryProducts1(String categoryId, final RecyclerView recyclerView,final TextView productsNumber)
{

    RetrofitClass retrofitClass = new RetrofitClass() ;

    retrofitClass.getRetrofitInstance();
    RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);
    Call<List<mProducts>> call = retrofitInterface.GetAllProductsInCategory(categoryId);
    call.enqueue(new Callback<List<mProducts>>()
    {
        @Override
        public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response) {


            try
            {
                progressBar.setVisibility(View.INVISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                List<mProducts> posts;

                posts = response.body();
                Log.i("ProductInfo",posts.get(0).getProductId());
                 allCategoryProduct(posts,recyclerView);
                productsNumber.setText(posts.size()+" "+context.getResources().getString(R.string.products));

            } catch (Exception e)
            {
                Log.i("ExceptionCategory","Exception in Category Product");
            }




        }

        @Override
        public void onFailure(Call<List<mProducts>> call, Throwable t)
        {
            Log.i("NoDataFromServer","This is failed");
        }


    });
}
    private void allCategoryProduct(List<mProducts> categorProdcuts, RecyclerView recyclerView1)
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        ProductsAdapter adapter = new ProductsAdapter(categorProdcuts, context);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager( new GridLayoutManager(context,2));


    }


    public void GatCatgoriesIds (final RecyclerView recyclerView)
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

                GetAllProductsInAllCategories(posts,recyclerView);


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



        public void GetAllProductsInAllCategories(List<mCategory> categoriesIds , RecyclerView recyclerView)
    {
        String categoryId ;
        for (int i = 0 ; i <categoriesIds.size();i++)
        {
            categoryId = categoriesIds.get(i).getCategoryId();
            GetAllCategoryProducts(categoryId,recyclerView);

        }
    }




    //////////////////////////////////
    ///////// GET ALL CATEGORY INFO



    public void GetCategoriesInfos(final RecyclerView recyclerView1, final  RecyclerView recyclerView2, final RecyclerView recyclerView3,
                                   final TextView tv1,final TextView tv2,final TextView tv3 )
    {

            RetrofitClass retrofitClass = new RetrofitClass() ;
            retrofitClass.getRetrofitInstance();
            RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);

            Call<List<mCategory>> call = retrofitInterface.getCategoryInfo();
            call.enqueue(new Callback<List<mCategory>>()
            {
                @Override
                public void onResponse(Call<List<mCategory>> call, Response<List<mCategory>> response)
                {



                    List<mCategory> posts;
                    posts = response.body();
    //                    GetAllProductsInAllCategories(posts,recyclerView);
                    tv1.setText(posts.get(0).getCategoryName());
                    tv2.setText(posts.get(1).getCategoryName());
                    tv3.setText(posts.get(3).getCategoryName());

                    GetAllCategoryProducts(posts.get(0).getCategoryId(),recyclerView1);
                    GetAllCategoryProducts(posts.get(1).getCategoryId(),recyclerView2);
                    GetAllCategoryProducts(posts.get(2).getCategoryId(),recyclerView3);

                    Log.i("insideMycategoryinfo","The size is "+categoryInfo.size());


                }

                @Override
                public void onFailure(Call<List<mCategory>> call, Throwable t)
                {
                    Log.i("NoDataFromServer","This is failed");
                }


            });

        }


        //// GET MOST SOLD ITEMS
        public void GetMostSoldProducts(String categoryId , final RecyclerView recyclerView,final TextView tvNumberOfProducts)
        {
            RetrofitClass retrofitClass = new RetrofitClass() ;

            retrofitClass.getRetrofitInstance();
            RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);
            Call<List<mProducts>> call = retrofitInterface.GetAllProductsInCategory(categoryId);
            call.enqueue(new Callback<List<mProducts>>()
            {
                @Override
                public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response) {


                    try
                    {
                        progressBar.setVisibility(View.INVISIBLE);


                        List<mProducts> posts;
                        posts = response.body();
                        //   allCategoryProduct(posts,recyclerView);
                        MostSoldInit(posts,recyclerView);
                        tvNumberOfProducts.setText(posts.size()+" "+context.getResources().getString(R.string.products));

                    } catch (Exception e)
                    {

                    }




                }

                @Override
                public void onFailure(Call<List<mProducts>> call, Throwable t)
                {
                    Log.i("NoDataFromServer","This is failed");
                }


            });
        }
    //// GET MOST SOLD ITEMS
    public void GetMostSoldProducts1(String categoryId , final RecyclerView recyclerView)
    {
        RetrofitClass retrofitClass = new RetrofitClass() ;

        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);
        Call<List<mProducts>> call = retrofitInterface.GetAllProductsInCategory(categoryId);
        call.enqueue(new Callback<List<mProducts>>()
        {
            @Override
            public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response) {


                try
                {
                    progressBar.setVisibility(View.INVISIBLE);


                    List<mProducts> posts;
                    posts = response.body();
                    //   allCategoryProduct(posts,recyclerView);
                    MostSoldInit(posts,recyclerView);

                } catch (Exception e)
                {

                }




            }

            @Override
            public void onFailure(Call<List<mProducts>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });
    }
    void MostSoldInit (List<mProducts> arr, RecyclerView rvAllCategory)
    {



        NewItemAdapter myAdapter = new NewItemAdapter(context,arr);
        rvAllCategory.setLayoutManager(new GridLayoutManager(context,3));
        rvAllCategory.setAdapter(myAdapter);

    }

    /// GET NEW PRODUCTS
    public void GetNewProducts(String categoryId , final RecyclerView recyclerView,final TextView tvNumberOfProducts )
    {
        RetrofitClass retrofitClass = new RetrofitClass() ;

        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);
        Call<List<mProducts>> call = retrofitInterface.GetAllProductsInCategory(categoryId);
        call.enqueue(new Callback<List<mProducts>>()
        {
            @Override
            public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response) {


                try
                {
                    progressBar.setVisibility(View.INVISIBLE);

                    List<mProducts> posts;
                    posts = response.body();
                    //   allCategoryProduct(posts,recyclerView);
                    MostSoldInit(posts,recyclerView);
                    tvNumberOfProducts.setText(posts.size()+" "+context.getResources().getString(R.string.products));

                } catch (Exception e)
                {

                }




            }

            @Override
            public void onFailure(Call<List<mProducts>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });
    }
    // GET NEW PRODUCTS
    public void GetNewProducts1(String categoryId , final RecyclerView recyclerView)
    {
        RetrofitClass retrofitClass = new RetrofitClass() ;

        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);
        Call<List<mProducts>> call = retrofitInterface.GetAllProductsInCategory(categoryId);
        call.enqueue(new Callback<List<mProducts>>()
        {
            @Override
            public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response) {


                try
                {
                    progressBar.setVisibility(View.INVISIBLE);

                    List<mProducts> posts;
                    posts = response.body();
                    //   allCategoryProduct(posts,recyclerView);
                    MostSoldInit(posts,recyclerView);

                } catch (Exception e)
                {

                }




            }

            @Override
            public void onFailure(Call<List<mProducts>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });
    }


    public void GetDiscountedProducts ( final RecyclerView recyclerView ,String  categoryId,final TextView tvNumberOfProducts)
    {
        RetrofitClass retrofitClass = new RetrofitClass() ;

        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);
        Call<List<mProducts>> call = retrofitInterface.GetAllProductsInCategory(categoryId);
        call.enqueue(new Callback<List<mProducts>>()
        {
            @Override
            public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response) {

                Log.i("hereiserrors","This is failed");

                try
                {
                    progressBar.setVisibility(View.INVISIBLE);

                    List<mProducts> posts;
                    posts = response.body();
                    //   allCategoryProduct(posts,recyclerView);
                    MostSoldInit(posts,recyclerView);
                    tvNumberOfProducts.setText(posts.size()+" "+context.getResources().getString(R.string.products));

                } catch (Exception e)
                {

                }




            }

            @Override
            public void onFailure(Call<List<mProducts>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });
    }




}
