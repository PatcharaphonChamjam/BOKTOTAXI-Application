package com.boktotaxi.boktotaxi.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boktotaxi.boktotaxi.Adapter.ShowRVadapter;
import com.boktotaxi.boktotaxi.Model.Show;
import com.boktotaxi.boktotaxi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class edit_profile extends AppCompatActivity {

    @Bind(R.id.Propicuser)ImageView _picpro;
    @Bind(R.id.ProUsername)TextView _username;
    @Bind(R.id.Proedit)TextView _edit;
    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        SharedPreferences sp = getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
        String username = sp.getString("Username", "");
        String userid = sp.getString("UserID","");
//        String username = getIntent().getExtras().getString("username");

        _username.setText(username);

        RealmResults<Show> show = Realm.getDefaultInstance().where(Show.class).findAll();

        Log.e("onCreateView: ", String.valueOf(show.size()));

        recyclerView  = (RecyclerView)findViewById(R.id.rvPro);
        mLayoutManager = new LinearLayoutManager(edit_profile.this);
        Log.e("recyclerView: ", String.valueOf(recyclerView));
        recyclerView.setLayoutManager(mLayoutManager);

        ShowRVadapter Adapter = new ShowRVadapter(getApplicationContext(),show,true);
        recyclerView.setAdapter(Adapter);




        _picpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowpicPopup(view);
            }
        });

        _edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edit_profile.this, EditUsernameActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ShowpicPopup(View view){

        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.picmenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.Gallery){

                    return true;
                }else if (i == R.id.view){

                    return true;
                }
                return false;
            }
        });
    }
}
