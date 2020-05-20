package com.alnajim.osama.ecommerce2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alnajim.osama.ecommerce2.Adapter.ProductsAdapter;
import com.alnajim.osama.ecommerce2.Model.mProducts;
import com.alnajim.osama.ecommerce2.R;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitInterface;

import java.util.Base64;
import java.util.List;
import java.util.Locale;

import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {


    ImageView imgBack;
    EditText etSearch;
    RecyclerView rvSearchResult ;
    ProgressBar progressbar;
    PageNavigationView tab ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setVisibility(View.VISIBLE);
        etSearch = findViewById(R.id.etSearch);
        rvSearchResult = findViewById(R.id.rvSearchResult);
        progressbar  = findViewById(R.id.progressbar);
        tab = findViewById(R.id.tab);
        etSearch.requestFocus();


        /////////////// BOTTOM NAVIGATION VIEW
        BasicClass basicClass = new BasicClass( this);
        basicClass.setNavigationBottom(tab);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            progressbar.setVisibility(View.VISIBLE);

                            Search1("1");
                        }
                    }

                return false;
            }
        });

    }

    public void goBack(View view)
    {
        finish();
    }



    public  void  Search1( String text)
    {

        RetrofitClass retrofitClass = new RetrofitClass() ;
        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);

        Call<List<mProducts>> call = retrofitInterface.GetAllProductsInCategory(text);
        call.enqueue(new Callback<List<mProducts>>()
        {
            @Override
            public void onResponse(Call<List<mProducts>> call, Response<List<mProducts>> response)
            {

                progressbar.setVisibility(View.INVISIBLE);
                rvSearchResult.setVisibility(View.VISIBLE);
                List<mProducts> product;
                product = response.body();
                allCategoryProduct(product,rvSearchResult);


                closeKeyboard();

                // Log.i("productDate",response.body().size()+" - ") ;


                Log.i("InsideProducts","InsideProductsInsideProductsInsideProducts");



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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ProductsAdapter adapter = new ProductsAdapter(categorProdcuts, this);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager( new GridLayoutManager(this,1));


    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
