package com.boktotaxi.boktotaxi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.boktotaxi.boktotaxi.Search.SenderReceiver;


public class Search_Fragment extends Fragment {



    RecyclerView recyclerView;
//    SearchView sv;
    LinearLayoutManager mLayoutManager;
    ImageView noDataImg,noNetworkImg;

    String urlAddress="http://192.168.0.82/Taxi/app/testtaxi.php";




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView= (RecyclerView) view.findViewById(R.id.rv2);
        noDataImg= (ImageView) view.findViewById(R.id.nodataImg);
        noNetworkImg= (ImageView) view.findViewById(R.id.noserver);

//        mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        sv= (SearchView) view.findViewById(R.id.sv);

//        search=(SearchView) myFragmentView.findViewById(R.id.searchbar);




//
//        Log.e("recyclerView: ", String.valueOf(recyclerView));
//        recyclerView.setLayoutManager(mLayoutManager);
//
//        RecyclerViewAdapter Adapter = new RecyclerViewAdapter(getContext(),feed,true);
//        recyclerView.setAdapter(Adapter);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.search_option_menu, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = new SearchView(((MainActivity) mContext).getSupportActionBar().getThemedContext());
//        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
//        MenuItemCompat.setActionView(item, searchView);
//
        Log.e("fuck1","yes");
        inflater.inflate(R.menu.buttonactionbar, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SenderReceiver sr=new SenderReceiver(getActivity(),query,urlAddress,recyclerView,noDataImg,noNetworkImg);
                sr.execute();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                SenderReceiver sr=new SenderReceiver(getActivity(),query,urlAddress,recyclerView,noDataImg,noNetworkImg);
                sr.execute();

                return false;
            }
        });
    }
}


