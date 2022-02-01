package com.boktotaxi.boktotaxi.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boktotaxi.boktotaxi.Activity.ShowTaxiActivity;
import com.boktotaxi.boktotaxi.Activity.Utils;
import com.boktotaxi.boktotaxi.Network.APIService;
import com.boktotaxi.boktotaxi.Network.ApiClient;
import com.boktotaxi.boktotaxi.Network.RSG;
import com.boktotaxi.boktotaxi.R;

import java.util.ArrayList;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Oclemmy on 4/2/2016 for ProgrammingWizards Channel.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<String> names;
    ArrayList<String>  ratings;

    public MyAdapter(Context c, ArrayList<String> names, ArrayList<String> rattings) {
        this.c = c;
        this.names = names;
        this.ratings = rattings;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.nameTxt.setText(names.get(position));
        holder.rattingTxt.setText(ratings.get(position));
        holder.setRecyclerOnItemClickListener(new RecyclerOnItemClickListener() {
            @Override
            public void onItemClick(final View childView, int position) {

//                String userid = itemHolder.mUserid.getText().toString();
                String carnum = holder.nameTxt.getText().toString();

                APIService service = ApiClient.getClient().create(APIService.class);
                service.showtaxi(carnum)
                        .asObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                        .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                        .subscribe(new Action1<RSG>() {
                            @Override
                            public void call(RSG response) {

                                Log.e("RealmTest: ", String.valueOf(response.getTaxi().size()));

                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();

                                realm.copyToRealmOrUpdate(response.getTaxi());
                                realm.commitTransaction();

                                c = childView.getContext();
                                String carnum;
                                Intent intent = new Intent(c, ShowTaxiActivity.class);

                                Bundle bundle = new Bundle();
                                bundle.putString("carnum", holder.nameTxt.getText().toString());
                                bundle.putString("ratting", holder.rattingTxt.getText().toString());
                                intent.putExtras(bundle);
//                String message = r;
//                intent.putExtra(EXTRA_MESSAGE, message);
                                c.startActivity(intent);

                            }

                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

//                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                            }
                        });


            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
