package com.boktotaxi.boktotaxi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.boktotaxi.boktotaxi.R;

public class BottomActionBar extends AppCompatActivity {
    FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new Listview_reviewFragment());
                    ft.commit();
                    return true;
                case R.id.navigation_raing:
//                    Intent intent = new Intent(getApplicationContext(),WriteReview.class);
//                    startActivity(intent);
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new Write_reviewFragment());
                    ft.commit();
                    return true;
                case R.id.navigation_profile:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new Menu_Fragment());
                    ft.commit();
                    return true;
            }
//
            return false;
        }

    };
//

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int Page = 1;

        if (Page == 1){
            Page = 0;
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, new Listview_reviewFragment());
            ft.commit();
        }

        setContentView(R.layout.activity_bottom_action_bar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        navigation.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBackg));
//        navigation.setItemTextColor(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.bootstrap_gray_dark)));
//        navigation.setItemIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.bootstrap_gray_dark));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.buttonactionbar, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchTaxiActivity.class);
                startActivity(intent);
                return true;

        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        Log.e( "onBackPressed: ","done" );
        AlertDialog.Builder builder = new AlertDialog.Builder(BottomActionBar.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Out of application");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.show();
    }
}

