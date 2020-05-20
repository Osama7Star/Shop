package com.alnajim.osama.ecommerce2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alnajim.osama.ecommerce2.Model.mAddress;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;

public class Address extends AppCompatActivity {

    TextView tvAdress;
    SQLiteHandler sqLiteHandler = new SQLiteHandler(this);
    mAddress mAddress ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        mAddress = sqLiteHandler.getAddress();
        if (mAddress==null)
        {
            Log.i("Herreee","Heeerree");
            Intent intent = new Intent(Address.this, AddUserAddress.class);
            intent.putExtra("FromWhere","None");
            startActivity(intent );
            finish();
        }
        else {
            Log.i("notHere","Heeerree");

            tvAdress = findViewById(R.id.tvAdress);
            tvAdress.setText(mAddress.getFullAddress());
        }

    }

    public void Edit(View view )
    {
        Intent intent = new Intent(Address.this, AddUserAddress.class) ;
        intent.putExtra("FromWhere","Edit") ;
        startActivity(intent);
    }

    public void Delete(View view )
    {
        if ( sqLiteHandler.DeleteAddress())
            BasicClass.PrintMessage(this,"Address Deleted Successfully");
        else
        BasicClass.PrintMessage(this,"Not Deleted ");

    }
}
