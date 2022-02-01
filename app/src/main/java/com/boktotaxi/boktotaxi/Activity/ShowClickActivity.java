package com.boktotaxi.boktotaxi.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.boktotaxi.boktotaxi.Adapter.ClickViewAdapter;
import com.boktotaxi.boktotaxi.Model.Detail;
import com.boktotaxi.boktotaxi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class ShowClickActivity extends AppCompatActivity {

    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    @Bind(R.id.ProUsername2)TextView _username;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_click);
        ButterKnife.bind(this);

//        sp = getSharedPreferences("CLICK", Context.MODE_PRIVATE);
//        String username = sp.getString("usernameclick","");

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            String username = (String) bundle.get("usernameclick");


            _username.setText(username);
        }
        RealmResults<Detail> detail = Realm.getDefaultInstance().where(Detail.class).findAll();

        Log.e("onCreateView: ", String.valueOf(detail.size()));

        recyclerView  = (RecyclerView)findViewById(R.id.rvPro2);
        mLayoutManager = new LinearLayoutManager(ShowClickActivity.this);
        Log.e("recyclerView: ", String.valueOf(recyclerView));
        recyclerView.setLayoutManager(mLayoutManager);

        ClickViewAdapter Adapter = new ClickViewAdapter(getApplicationContext(),detail,true);
        recyclerView.setAdapter(Adapter);
    }
}
