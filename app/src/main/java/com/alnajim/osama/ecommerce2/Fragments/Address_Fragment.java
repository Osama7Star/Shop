package com.alnajim.osama.ecommerce2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alnajim.osama.ecommerce2.AddUserAddress;
import com.alnajim.osama.ecommerce2.Address;
import com.alnajim.osama.ecommerce2.BasicClass;
import com.alnajim.osama.ecommerce2.Model.mAddress;
import com.alnajim.osama.ecommerce2.R;
import com.alnajim.osama.ecommerce2.UserInformations;
import com.alnajim.osama.ecommerce2.sqlite.SQLiteHandler;

public class Address_Fragment extends Fragment
{

    Button deleteAddress , updateAddress , addAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        {

            final SQLiteHandler sqLiteHandler = new SQLiteHandler(getActivity());

            View view =  inflater.inflate(R.layout.useraddress, container, false);

            deleteAddress = view.findViewById(R.id.btnDeleteAddress);
            updateAddress = view.findViewById(R.id.btnUpdateAddress);
            addAddress    = view.findViewById(R.id.btnAddAddress);

            updateAddress.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    Intent intent = new Intent(getActivity(), AddUserAddress.class) ;
                    intent.putExtra("FromWhere","Edit") ;
                    startActivity(intent);
                }
            });
            deleteAddress.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if ( sqLiteHandler.DeleteAddress()) {
                        BasicClass.PrintMessage(getActivity(), "Address Deleted Successfully");
                        Intent intent = new Intent(getActivity(), UserInformations.class) ;
                        intent.putExtra("FromWhere","Edit") ;
                        startActivity(intent);
                    }
                    else
                        BasicClass.PrintMessage(getActivity(),"Not Deleted ");
                }
            });

            addAddress.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Herreee", "Heeerree");
                    Intent intent = new Intent(getActivity(), AddUserAddress.class);
                    intent.putExtra("FromWhere", "None");
                    startActivity(intent);
                    getActivity().finish();

                }
            });
            return view ;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SQLiteHandler sqLiteHandler = new SQLiteHandler(getActivity());
        com.alnajim.osama.ecommerce2.Model.mAddress mAddress = sqLiteHandler.getAddress();
        if (mAddress == null)
        {
            addAddress.setVisibility(View.VISIBLE);
            updateAddress.setVisibility(View.INVISIBLE);
            deleteAddress.setVisibility(View.INVISIBLE);
        } else
        {
            Log.i("notHere", "Heeerree");

            TextView tvAdress = getView().findViewById(R.id.tvAddressFragment);
            tvAdress.setText(mAddress.getFullAddress());
            updateAddress.setVisibility(View.VISIBLE);
            deleteAddress.setVisibility(View.VISIBLE);
            addAddress.setVisibility(View.INVISIBLE);

        }

    }

}