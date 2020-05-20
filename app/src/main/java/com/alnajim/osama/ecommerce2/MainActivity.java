package com.alnajim.osama.ecommerce2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.alnajim.osama.ecommerce2.Authentication.RegisterActivity;
import com.alnajim.osama.ecommerce2.Model.mCategory;
import com.alnajim.osama.ecommerce2.Model.mSlider;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitClass;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitInterface;
import com.alnajim.osama.ecommerce2.Retrofit.RetrofitRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import java.util.List;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//////// FOR DRAWER

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    /// TOOLBAR
    Toolbar toolbar ;
    ImageView imgSearch , imgBack;
    /// SLIDER
    private SliderLayout mDemoSlider;
    /// DRAWER
    private Drawer result = null;
    private AccountHeader headerResult = null;

    ///RecyclerView
    RecyclerView reDiscounts ,rvCategory1, rvCategory2,rvCategory3,rvAllCategory,rvMostSoldProducts,rvNewProducts ;
    TextView tv1,tv2,tv3;
    FloatingActionButton floatGoUp ;
    ScrollView scrollview;

    RelativeLayout rlMain , rlMainActivty;






    /// Bottom Navigation
     PageNavigationView tab ;
     ProgressBar progressbar;



   // ImageButton tvSubCategoryName2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = findViewById(R.id.mytoolbar);
         setSupportActionBar(toolbar);



        InitViews();
        /// SHOW THE SEARCH LOGO IF WE IN THE MAIN ACTIVITY
        imgBack.setVisibility(View.INVISIBLE);
        progressbar.setVisibility(View.VISIBLE);


      // sqLiteHandler.addUser("","","","");

        ///////////////////////////////
        ////// GET SLIDER DATA
        getSliderData( savedInstanceState);

        ///////////////////////////////
        ////// GET CATEGORY PRODUCT

//        sqLiteHandler.deleteBasket();
//        sqLiteHandler.deleteWish();
        RetrofitRequest retrofitRequest = new RetrofitRequest(this,progressbar,rlMainActivty);
        retrofitRequest.getProducts(reDiscounts);
        retrofitRequest.GetCategoriesInfos(rvCategory1,rvCategory2,rvCategory3,tv1,tv2,tv3);

        // TODO
        retrofitRequest.GetMostSoldProducts1("1",rvMostSoldProducts);
        retrofitRequest.GetNewProducts1("1",rvNewProducts);
        //retrofitRequest.GatCatgoriesIds(rvCategory1);

        ///////////////////////////////
        ////// GET ALL CATEGORIES
        retrofitRequest.getAllCateogry(rvAllCategory);



        tabDelecration ( ) ;
        BasicClass basicClass = new BasicClass( this);
        basicClass.setNavigationBottom ( tab) ;









    }// End onCreate

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
       //Toast.makeText(this,slider.getBundle().get("extra") .toString(), Toast.LENGTH_SHORT).show();

       Intent intent = new Intent(MainActivity.this, Product.class);
        //intent.putExtra("prodcutId",slider.getBundle().get("extra").toString() );
       //startActivity(intent);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("mSlider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}


      void ImageSlider(List<mSlider> Slider )

    {


        mDemoSlider  = findViewById(R.id.slider);



        for(mSlider slider :Slider){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(slider.getText() + " | ")
                    .image(RetrofitClass.BASE_IMAGE_URL+slider.getImagePath())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            Log.i("ImagePath",RetrofitClass.BASE_IMAGE_URL+slider.getImagePath());
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",slider.getText());
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Tablet);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
//        ListView l = (ListView)findViewById(R.id.transformers);
//        l.setAdapter(new TransformerAdapter(this));
//        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
//                Toast.makeText(MainActivity.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////  NAVIGATION DRAWER
    public void addDrawer(Bundle savedInstanceState )
    {
        final IProfile profile;
        profile = new ProfileDrawerItem().withName("Osama").withEmail("Osama.alnajm128@gmail.com").withTag("RKS");
        /// GET USER INFO - IMAGE - NAME - EMAIL
        /// IF USER IS LOGgED PUT THE USER INFO ELSE PUT DEFAULT INFORMATION

        /// IF THE USER IS LOGGED SHOW THE PROFILE SECTION
        if (true)
        {

//        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(0).withName("Adarsh");

            final Intent i = new Intent(this, MainActivity.class);
            final Intent r = new Intent(this, RegisterActivity.class);

            /// FOR THE HEAD OF THE USER PROFILE IN THE DRAWER
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withTranslucentStatusBar(true)
                    .withHeaderBackground(R.drawable.profile_bg)
                    .addProfiles(

                            profile,
                            new ProfileSettingDrawerItem().withName("Register Now").withDescription("Add new RKS Account").withTag("REGISTER"),
                            new ProfileSettingDrawerItem().withName("Testing Again").withDescription("Add new RKSAccount ").withTag("REGISTER")


                    )
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener()
                    {


                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                            //sample usage of the onProfileChanged listener
                            //if the clicked item has the identifier 1 add a new profile ;)
                            if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getTag().equals("CUSTOMER")) {
//                            headerResult.removeProfile(profile);
                                startActivity(i);
                            } else if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getTag().equals("REGISTER")) {
                                startActivity(r);

                            }
                            //false if you have not consumed the event and it should close the drawer
                            return false;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .build();

        }

        //// IF THE USER IS NOT LOGGED DON'T SHOW THE PROFILE SECTION
        else
        {

        }
        ///////////////////////////////////////////////////////////
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

                AddExpandableItem ( posts);



            }

            @Override
            public void onFailure(Call<List<mCategory>> call, Throwable t)
            {
                Log.i("NoDataFromServer","This is failed");
            }


        });

        //////////////////////////////////////////////////////////

        new DrawerBuilder().withActivity(this).build();
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems()
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener()
                {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem)
                    {

                        if (drawerItem != null)
                        {

                            Intent intent = new Intent(MainActivity.this,CategoryProdcut.class);
                            intent.putExtra("categoryId",drawerItem.getTag().toString());
                            intent.putExtra("categoryName",drawerItem.getType());


                            startActivity(intent);


                        }

                        return false;
                    }

                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .withShowDrawerUntilDraggedOpened(true)
                .build();


    }


    void addHeaderProfile (Bundle savedInstanceState)
    {

    }


    void AddExpandableItem ( List<mCategory> list)
    {

        for (int i = 0 ; i <list.size();i++) {
            ExpandableDrawerItem item1 = new ExpandableDrawerItem().withName(list.get(i).getCategoryName()).withTag(list.get(i).getCategoryId());

            result.addItem(item1);



        }



       // initImages();



    }










    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////// GO TO TOP
    public void GoUP ( View view )
    {
        scrollview.fullScroll(ScrollView.FOCUS_UP);

    }

    public void Search ( View view )
    {
        startActivity(new Intent(MainActivity.this, Search.class));
    }





    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////// GET THE SLIDER
    public List<mSlider> getSliderData(final Bundle savedInstanceState)
    {


        RetrofitClass retrofitClass = new RetrofitClass();
        retrofitClass.getRetrofitInstance();
        RetrofitInterface retrofitInterface = RetrofitClass.retrofitClass.create(RetrofitInterface.class);

        Call<List<mSlider>> call = retrofitInterface.getSlider();
        call.enqueue(new Callback<List<mSlider>>()
        {
            @Override
            public void onResponse(Call<List<mSlider>> call, Response<List<mSlider>> response)
            {



                List<mSlider> posts;
                posts = response.body();

                ImageSlider(posts);
                addDrawer(savedInstanceState);

            }

            @Override
            public void onFailure(Call<List<mSlider>> call, Throwable t) {
            }
        });
        return null;
    }


    public  void tabDelecration ( )
    {
        tab = findViewById(R.id.tab);

    }

    /////// GO TO MOST SOLD PRODUCTS WHEN CLICK VIEW ALL
    public void GoToMostSold(View view)
    {

        Intent intent = new Intent(MainActivity.this,NewMostSold.class);
        intent.putExtra("FromWhere","MostSold");
        startActivity(intent);
    }

    /////// GO TO NEW  PRODUCTS WHEN CLICK VIEW ALL
    public void GoToNewProduct(View view)
    {

        Intent intent = new Intent(MainActivity.this,NewMostSold.class);
        intent.putExtra("FromWhere","NEWPRODUCTS");
        startActivity(intent);
    }

    public void GoDiscounted(View view  )
    {
        Intent intent = new Intent(MainActivity.this,CategoryProdcut.class);
        intent.putExtra("FromWhere","Discounted");
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void InitViews()
    {
        //// Search Button
        imgSearch   =  findViewById(R.id.imgSearch);
        imgBack     =  findViewById(R.id.imgBack);
        reDiscounts =  findViewById(R.id.rvDiscounts);
        rvCategory1 =  findViewById(R.id.rvCategory1);
        rvCategory2 =  findViewById(R.id.rvCategory2);
        rvCategory3 =  findViewById(R.id.rvCategory3);
        tv1         =  findViewById(R.id.tv1);
        tv2         =  findViewById(R.id.tv2);
        tv3         =  findViewById(R.id.tv3);

        rvMostSoldProducts = findViewById(R.id.rvMostSoldProducts);
        rvNewProducts      = findViewById(R.id.rvNewProducts);
        rvAllCategory      =  findViewById(R.id.rvAllCategory);
        //  tvSubCategoryName2 = findViewById(R.id.tvSubCategoryName2);
        mDemoSlider =  findViewById(R.id.slider);
        floatGoUp   = findViewById(R.id.floatGoUp) ;
        scrollview  = findViewById(R.id.scrollview) ;
        rlMain      = findViewById(R.id.rlMain);
        rlMainActivty = findViewById(R.id.rlMainActivty);
        progressbar   = findViewById(R.id.progressbar);

    }

}



