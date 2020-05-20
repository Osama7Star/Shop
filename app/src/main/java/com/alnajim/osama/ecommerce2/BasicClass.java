package com.alnajim.osama.ecommerce2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class BasicClass
{
    public static int basketItemCounts;
    public static int whishListItemCounts;
    static NavigationController navigationController ;
    Context context ;
    SQLiteHandler sqLiteHandler ;

    PageNavigationView tab ;

    public BasicClass(Context context)
    {
        this.context = context;
        sqLiteHandler = new SQLiteHandler(context);

    }
    public BasicClass() {

    }


    ////// CHECK INTERNET CONNECTION
        public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }


    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
    //// TOAST MESSAGE PRINT
    public static void PrintMessage (Context context , String Message)
    {
        Toast.makeText(context,Message, Toast.LENGTH_SHORT).show();

    }




    ////////////// ADD ITEM TO BASKET AND WISHLIST
    public static int getBasketItemCounts(SQLiteHandler sqLiteHandler)
    {
        basketItemCounts = sqLiteHandler.GetBasketItemsCount();

        return basketItemCounts;
    }

    public void setBasketItemCounts() {
    }

    public static int getWhishListItemCounts(SQLiteHandler sqLiteHandler)
    {
        whishListItemCounts = sqLiteHandler.GetWishListItemsCount();

        return whishListItemCounts;
    }

    public void setWhishListItemCounts() {
    }





    public static void addToBasket(SQLiteHandler sqLiteHandler, String prodcutId, String date )
    {
        sqLiteHandler.addToBasket(prodcutId,date);
        navigationController.setMessageNumber(2, BasicClass.getBasketItemCounts(sqLiteHandler));


    }
    public static void addToWishList(SQLiteHandler sqLiteHandler, String prodcutId, String date )
    {
        sqLiteHandler.addToWishList(prodcutId,date);
        navigationController.setMessageNumber(3, BasicClass.getWhishListItemCounts(sqLiteHandler));


    }


    ////////////////////////////////////////////////////////////////////
    //////////// NAVIGATION BOTTOMS
    public void setNavigationBottom(PageNavigationView tab)
    {

        navigationController = tab.custom()
                .addItem(newItem(R.drawable.empty, R.drawable.empty, ""))
                .addItem(newItem(R.drawable.ic_home_black_24dp, R.drawable.ic_home_black_24dp, context.getResources().getString(R.string.home)))
                .addItem(newItem(R.drawable.ic_add_shopping_cart_black_24dp, R.drawable.ic_add_shopping_cart_black_24dp, context.getResources().getString(R.string.basket)))
                .addItem(newItem(R.drawable.ic_favorite_black_24dp, R.drawable.ic_favorite_black_24dp, context.getResources().getString(R.string.wishList)))
                .addItem(newItem(R.drawable.ic_account_circle_black_24dp, R.drawable.ic_account_circle_black_24dp, context.getResources().getString(R.string.account)))
                .addItem(newItem(R.drawable.empty, R.drawable.empty, ""))

                .build();

        navigationController.setMessageNumber(2, BasicClass.getBasketItemCounts(sqLiteHandler));
        navigationController.setMessageNumber(3, BasicClass.getWhishListItemCounts(sqLiteHandler));

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener()
        {
            @Override
            public void onSelected(int index, int old)
            {

                navigationController.setSelect(0,false);

                switch(index)
                {

                    case 1:
                        Intent intent0 = new Intent(context,MainActivity.class);
                        Log.i("MainPage","MainIsClicked");
                        context.startActivity(intent0);

                        break;
                    case 2:
                        Intent intent1 = new Intent(context, BasketsProducts.class);
                        intent1.putExtra("FromWhere","Basket");
                        navigationController.setSelect(0,false);
                        context.startActivity(intent1);


                        break;
                    case 3:
                        Intent intent2 = new Intent(context, BasketsProducts.class);
                        intent2.putExtra("FromWhere","Wishlist");
                        navigationController.setSelect(0,false);
                        context.startActivity(intent2);


                        break;
                    case 4:
                        Intent intent3 = new Intent(context, UserInformations.class);
                        navigationController.setSelect(0,false);
                        context.startActivity(intent3);



                }
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    private BaseTabItem newItem(int drawable, int checkedDrawable, String text)
    {
        NormalItemView normalItemView = new NormalItemView(context);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        normalItemView.setBackgroundColor(Color.WHITE);
        normalItemView.setTextCheckedColor(Color.BLACK);




        return normalItemView;
    }


    /////////////////////////////////////////////////////////////////////////////
    /////// GET CURRENT DATE

    public static String date ()
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static int  CalculateDiscountRate(String price,String oldprice)
    {
        double currentPrice = Double.parseDouble(price);

        double oldPrice     = Double.parseDouble(oldprice);


        if (oldPrice>currentPrice)
        return (int) Math.round(100 - ((currentPrice*100)/oldPrice) );
        else
            return  0;

    }


}
