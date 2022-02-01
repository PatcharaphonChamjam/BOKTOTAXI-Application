package com.boktotaxi.boktotaxi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boktotaxi.boktotaxi.Adapter.ShowTaxiAdapter;
import com.boktotaxi.boktotaxi.Model.Taxi;
import com.boktotaxi.boktotaxi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class ShowTaxiActivity extends AppCompatActivity {
    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    @Bind(R.id.SearchEdtcar)TextView _edtcar;
    @Bind(R.id.Searchrating)RatingBar _ratting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_taxi);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            String carnum = (String) bundle.get("carnum");
            String ratting = (String) bundle.get("ratting");
            _edtcar.setText(carnum);
            _ratting.setRating(Float.parseFloat(ratting));
        }

        RealmResults<Taxi> taxi = Realm.getDefaultInstance().where(Taxi.class).findAll();

        Log.e("onCreateView: ", String.valueOf(taxi.size()));

        recyclerView  = (RecyclerView)findViewById(R.id.Rvtaxi);
        mLayoutManager = new LinearLayoutManager(ShowTaxiActivity.this);
        Log.e("recyclerView: ", String.valueOf(recyclerView));
        recyclerView.setLayoutManager(mLayoutManager);

        ShowTaxiAdapter Adapter = new ShowTaxiAdapter(getApplicationContext(),taxi,true);
        recyclerView.setAdapter(Adapter);


    }
}
