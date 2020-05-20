package com.alnajim.osama.ecommerce2;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.alnajim.osama.ecommerce2.Fragments.Address_Fragment;
import com.alnajim.osama.ecommerce2.Model.mAddress;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;

import java.util.Timer;
import java.util.TimerTask;


public class AddUserAddress extends AppCompatActivity {
    EditText city, area ,district, nighberhood ,street , buildingNumber, houseNumber ,fullAdress ;

    String cityStr, areaStr , nighberhoodStr , districtStr ,streetStr , buildingNumberStr, houseNumberStr , fullAdressStr ;
    SQLiteHandler sqLiteHandler = new SQLiteHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        city            = findViewById(R.id.etCity);
        area            = findViewById(R.id.etArea);
        district        = findViewById(R.id.etDiscrit);
        nighberhood     = findViewById(R.id.etneighbourhood);
        buildingNumber  = findViewById(R.id.etBuildingNumber);
        houseNumber     = findViewById(R.id.etHouseNumber);
        fullAdress      = findViewById(R.id.etFullAdress);
        String FromWhere = getIntent().getStringExtra("FromWhere");
        if (FromWhere.equals("Edit"))
        {

            try {
                mAddress mAddress = sqLiteHandler.getAddress() ;

                city.setText(mAddress.getCity());
                area.setText(mAddress.getArea());
                district.setText(mAddress.getDiscrit());
                nighberhood.setText(mAddress.getNeighborhood());
                buildingNumber.setText(mAddress.getBuildingNumber());
                houseNumber.setText(mAddress.getHouseNumber());
                fullAdress.setText(mAddress.getFullAddress());
            }
            catch (Exception e )
            {}

        }


        houseNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s)
            {
                cityStr         = city.getText().toString() ;
                areaStr         = area.getText().toString();
                districtStr     = district.getText().toString();
                nighberhoodStr  = nighberhood.getText().toString(); ;
                buildingNumberStr = buildingNumber.getText().toString();
                houseNumberStr  = houseNumber.getText().toString();
                fullAdressStr   = fullAdress.getText().toString();
                fullAdress.setText(cityStr+" - "+areaStr+" - "+districtStr+" - "+nighberhoodStr+" - "+buildingNumberStr+" - "+houseNumberStr);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });


    }

    public void SendPersonalInfo(View view )
    {
        EditText name, email , password , phoneNumber2 ;
        String nameStr, emailStr , passwordStr , phoneNumber2Str ;
        name   =   findViewById(R.id.etName);
        email  =  findViewById(R.id.etEmail);
        password      =  findViewById(R.id.etPassword);
        phoneNumber2  =  findViewById(R.id.etPhoneNumber2);


        nameStr = name.getText().toString() ;
        emailStr = email.getText().toString() ;
        passwordStr = password.getText().toString();
        phoneNumber2Str = phoneNumber2.getText().toString();

        BasicClass.PrintMessage(this,nameStr+" - "+emailStr+" - "+passwordStr+" - "+phoneNumber2Str);
    }

    public void SendAddressInfo(View view )
    {

        city  = findViewById(R.id.etCity);
        area  = findViewById(R.id.etArea);
        district = findViewById(R.id.etDiscrit);
        nighberhood  = findViewById(R.id.etneighbourhood);
        buildingNumber  = findViewById(R.id.etBuildingNumber);
        houseNumber     = findViewById(R.id.etHouseNumber);
        fullAdress      = findViewById(R.id.etFullAdress);



        cityStr         = city.getText().toString() ;
        areaStr         = area.getText().toString();
        districtStr     = district.getText().toString();
        nighberhoodStr  = nighberhood.getText().toString(); ;
        buildingNumberStr = buildingNumber.getText().toString();
        houseNumberStr  = houseNumber.getText().toString();
        fullAdressStr   = fullAdress.getText().toString();

        if(cityStr.equals("")||areaStr.equals("")||districtStr.equals("")||nighberhoodStr.equals("")||buildingNumberStr.equals("")||houseNumberStr.equals("")||fullAdressStr.equals(""))
        {
            BasicClass.PrintMessage(this,getResources().getString(R.string.inputsError));

        }
        else {
            sqLiteHandler.AddAddress(cityStr, areaStr, districtStr, nighberhoodStr, buildingNumberStr, houseNumberStr, fullAdressStr);
            BasicClass.PrintMessage(this, cityStr + " - " + areaStr + " - " + nighberhoodStr + " - " + buildingNumberStr + " - " + houseNumberStr + " - " + fullAdressStr);
            startActivity(new Intent(AddUserAddress.this, UserInformations.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(AddUserAddress.this,UserInformations.class));
    }
}
