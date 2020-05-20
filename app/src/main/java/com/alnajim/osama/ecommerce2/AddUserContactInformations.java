package com.alnajim.osama.ecommerce2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddUserContactInformations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_contact_informations);
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
        if(nameStr.equals("")||emailStr.equals("")||passwordStr.equals("")||phoneNumber2Str.equals(""))
         BasicClass.PrintMessage(this,getResources().getString(R.string.inputsError));
        else
        {         BasicClass.PrintMessage(this,"Good Thank you ");
        }
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(AddUserContactInformations.this,UserInformations.class));
    }

}
