package com.boktotaxi.boktotaxi.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boktotaxi.boktotaxi.Adapter.RecyclerViewAdapter;
import com.boktotaxi.boktotaxi.Model.ModelShowReview;
import com.boktotaxi.boktotaxi.Network.APIService;
import com.boktotaxi.boktotaxi.Network.ApiClient;
import com.boktotaxi.boktotaxi.Network.RSG;
import com.boktotaxi.boktotaxi.R;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class Listview_reviewFragment extends Fragment {

    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_listview_review, container, false);

        RealmResults<ModelShowReview> feed = Realm.getDefaultInstance().where(ModelShowReview.class).findAll();

        Log.e("onCreateView: ", String.valueOf(feed.size()));

        recyclerView = (RecyclerView) view.findViewById(R.id.rvReview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        Log.e("recyclerView: ", String.valueOf(recyclerView));
        recyclerView.setLayoutManager(mLayoutManager);

        RecyclerViewAdapter Adapter = new RecyclerViewAdapter(getContext(),feed,true);
        recyclerView.setAdapter(Adapter);



        return view;
    }


    public void onButtonPressed(Uri uri) {



    }
//    private void getUserList() {
//        try {
//            APIService service = ApiClient.getClient().create(APIService.class);
//
//            service.getUserdata().enqueue(new Callback<RSG>() {
//                @Override
//                public void onResponse(Call<RSG> call, Response<RSG> response) {
//                    Log.e("onResponse", String.valueOf(response.body().getFeeds().size()));
//
////                    List<ModelShowReview> userList = response.body();
//
//
//
//
//                }
//
//                @Override
//                public void onFailure(Call<RSG> call, Throwable t) {
//                    Log.e("onFailure",call.toString()+" "+ t.toString());
//                }
//
//            });
//        }catch (Exception e) {
//            Log.e("getUserList: ",e.getMessage() );
//            Log.e("getUserList2: ",e.getLocalizedMessage() );
//        }
//    }



    @Override
    public void onResume() {
        super.onResume();
        getUserList();

    }

    public void getUserList() {

        APIService service = ApiClient.getClient().create(APIService.class);
        service.getUserdata()
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<RSG>() {
                    @Override
                    public void call(RSG response) {

                        Log.e("RealmTest: ", String.valueOf(response.getFeeds().size()));

                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(response.getFeeds());
                        realm.commitTransaction();

//                        PDialog.dismiss();

                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        PDialog.dismiss();
//                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                    }
                });

    }


}
