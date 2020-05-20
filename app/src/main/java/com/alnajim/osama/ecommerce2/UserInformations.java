package com.alnajim.osama.ecommerce2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.alnajim.osama.ecommerce2.Authentication.RegisterActivity;
import com.alnajim.osama.ecommerce2.Fragments.Address_Fragment;
import com.alnajim.osama.ecommerce2.Fragments.Contacts_Fragment;
import com.alnajim.osama.ecommerce2.Model.mAddress;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.majiajie.pagerbottomtabstrip.PageNavigationView;

public class UserInformations extends AppCompatActivity {
    PageNavigationView tab ;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_informations);

        BottomNavigationView bottomNav = findViewById(R.id.fragmentNavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        tab = findViewById(R.id.tab);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setVisibility(View.VISIBLE);
        BasicClass basicClass = new BasicClass( this);
        basicClass.setNavigationBottom(tab);
//        String FromWhere = getIntent().getStringExtra("FromWhere");
//        if (FromWhere==null )
//        {
//            startActivity(new Intent (UserInformations.this, RegisterActivity.class));
//            finish();
//        }
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Address_Fragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.addressInfo: {

                                selectedFragment = new Address_Fragment();
                                break;

                        }
                        case R.id.personalInfo:
                            selectedFragment = new Contacts_Fragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserInformations.this,MainActivity.class));
        }
}
