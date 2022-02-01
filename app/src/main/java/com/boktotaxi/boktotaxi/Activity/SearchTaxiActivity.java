package com.boktotaxi.boktotaxi.Activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.boktotaxi.boktotaxi.R;
import com.boktotaxi.boktotaxi.Search.SenderReceiver;

public class SearchTaxiActivity extends AppCompatActivity {

    RecyclerView rv;
    SearchView sv;
    ImageView noDataImg,noNetworkImg;

    String urlAddress="http://192.168.43.53/Taxi/app/testtaxi.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_taxi);

        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        sv= (SearchView) findViewById(R.id.sv);
        noDataImg= (ImageView) findViewById(R.id.nodataImg);
        noNetworkImg= (ImageView) findViewById(R.id.noserver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setIconified(false);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SenderReceiver sr=new SenderReceiver(SearchTaxiActivity.this,query,urlAddress,rv,noDataImg,noNetworkImg);
                sr.execute();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                SenderReceiver sr=new SenderReceiver(SearchTaxiActivity.this,query,urlAddress,rv,noDataImg,noNetworkImg);
                sr.execute();

                return false;
            }
        });
    }
}
