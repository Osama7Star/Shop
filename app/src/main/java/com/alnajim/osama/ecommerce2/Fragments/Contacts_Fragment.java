package com.alnajim.osama.ecommerce2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alnajim.osama.ecommerce2.AddUserAddress;
import com.alnajim.osama.ecommerce2.AddUserContactInformations;
import com.alnajim.osama.ecommerce2.BasicClass;
import com.alnajim.osama.ecommerce2.R;

public class Contacts_Fragment extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.usercontacts, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (true )
        {
            Intent intent = new Intent(getActivity(), AddUserContactInformations.class);
            startActivity(intent);
            getActivity().finish();
        }
        else
        {

        }

    }

    public void SendPersonalInfo(View view )
    {
        EditText name, email , password , phoneNumber2 ;
        String nameStr, emailStr , passwordStr , phoneNumber2Str ;
        name   =    getView().findViewById(R.id.etName);
        email  =   getView().findViewById(R.id.etEmail);
        password      =   getView().findViewById(R.id.etPassword);
        phoneNumber2  =   getView().findViewById(R.id.etPhoneNumber2);


        nameStr = name.getText().toString() ;
        emailStr = email.getText().toString() ;
        passwordStr = password.getText().toString();
        phoneNumber2Str = phoneNumber2.getText().toString();

        BasicClass.PrintMessage(getActivity(),nameStr+" - "+emailStr+" - "+passwordStr+" - "+phoneNumber2Str);
    }

}
