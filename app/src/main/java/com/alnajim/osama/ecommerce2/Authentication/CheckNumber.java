package com.alnajim.osama.ecommerce2.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alnajim.osama.ecommerce2.BasicClass;
import com.alnajim.osama.ecommerce2.MainActivity;
import com.alnajim.osama.ecommerce2.R;
import com.alnajim.osama.ecommerce2.UserInformations;
import com.google.gson.Gson;

import java.time.Instant;

public class CheckNumber extends AppCompatActivity {

    TextView tvCountDown,  tvErrorMessage,tvMessage ,tvphoneNumber,backToRegister;
    Button btnResendCode;
    EditText tvActivationCode;
    String activationCode ="123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number);

        btnResendCode      = findViewById(R.id.btnResendCode) ;
        tvActivationCode   = findViewById(R.id.tvActivationCode);
        tvMessage          = findViewById(R.id.tvMessage);
        tvphoneNumber      = findViewById(R.id.tvphoneNumber);
        backToRegister     = findViewById(R.id.backToRegister);
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("PhoneNumber");
        tvMessage.setText(getResources().getString(R.string.tvMessage));
        backToRegister.setText(getResources().getString(R.string.backToRegister));
        tvphoneNumber.setText(phoneNumber);
        CountDownTimer();
        tvActivationCode.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                tvCountDown.setVisibility(View.GONE);
            }
        });

    }

    void CountDownTimer()
    {
        tvCountDown = findViewById(R.id.tvCountDown) ;
        tvCountDown.setVisibility(View.VISIBLE);
        tvCountDown.setTextColor(getResources().getColor(R.color.white));

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish()
            {
                tvCountDown.setText("done!");
                tvCountDown.setVisibility(View.INVISIBLE);
                btnResendCode.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    public void ResendCode(View view )
    {
        CountDownTimer();
        btnResendCode.setVisibility(View.GONE);


    }

    public void Send(View view )
    {

        String userActicationCode = tvActivationCode.getText().toString() ;

        if (!TextUtils.isEmpty(userActicationCode))
        {
            if (activationCode.equals(userActicationCode))
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
                {
                    tvCountDown.setVisibility(View.VISIBLE);
                    tvCountDown.setText( getResources().getString(R.string.codeerror));
                    tvCountDown.setTextColor(getResources().getColor(R.color.md_red_400));
                    btnResendCode.setVisibility(View.VISIBLE);

                }
        }
        else
            BasicClass.PrintMessage(this,"Enter the Actication Code");
    }

    public void backToRegister(View view )
    {startActivity(new Intent(CheckNumber.this,RegisterActivity.class));}
}
