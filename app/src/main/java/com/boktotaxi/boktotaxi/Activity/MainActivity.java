package com.boktotaxi.boktotaxi.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.boktotaxi.boktotaxi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btnlogin)
    Button _login;
    @Bind(R.id.btnregis)
    Button _regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ButterKnife.bind(this);

        //check already login or not in first activity
        SharedPreferences sp = getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
        int keyLogin = sp.getInt("KEY_LOGIN", 0);
        if (keyLogin == 1) {
            Intent intent = new Intent(MainActivity.this, BottomActionBar.class);
            startActivity(intent);
        }

        _login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        _regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

    }


}
