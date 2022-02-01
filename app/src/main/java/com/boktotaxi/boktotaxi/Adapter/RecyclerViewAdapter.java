package com.boktotaxi.boktotaxi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boktotaxi.boktotaxi.Activity.ShowClickActivity;
import com.boktotaxi.boktotaxi.Activity.Utils;
import com.boktotaxi.boktotaxi.Model.Detail;
import com.boktotaxi.boktotaxi.Model.ModelShowReview;
import com.boktotaxi.boktotaxi.Network.APIService;
import com.boktotaxi.boktotaxi.Network.ApiClient;
import com.boktotaxi.boktotaxi.Network.RSG;
import com.boktotaxi.boktotaxi.R;
import com.boktotaxi.boktotaxi.ViewHolder.RecyclerViewHolder;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by USER on 2/5/2560.
 */

public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<ModelShowReview,RecyclerView.ViewHolder> {
    private List<ModelShowReview>item;

    Context mContext;

    public RecyclerViewAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<ModelShowReview> data, boolean autoUpdate) {
        super(context, data, autoUpdate);

        mContext = context;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_review, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ModelShowReview get =  getData().get(position);

        final RecyclerViewHolder itemHolder = (RecyclerViewHolder) holder;

        itemHolder.mUsername.setText(get.getUsername());
        itemHolder.mDatetime.setText(get.getDatetime());
        itemHolder.mRatting.setRating(Float.parseFloat(get.getRatting()));
        itemHolder.mcarlicense.setText(get.getCarlicense());
        itemHolder.mReview.setText(get.getReview());
        itemHolder.mFrom.setText(get.getDesFrom());
        itemHolder.mTo.setText(get.getDesTo());
        itemHolder.mUserid.setText(get.getUserID());
//        itemHolder.layClick.setBackground(Color.parseColor("#ffffff"));
        itemHolder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userid = itemHolder.mUserid.getText().toString();

                APIService service = ApiClient.getClient().create(APIService.class);
                service.showclick(userid)
                        .asObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                        .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                        .subscribe(new Action1<RSG>() {
                            @Override
                            public void call(RSG response) {

                                Log.e("RealmTest: ", String.valueOf(response.getDetail().size()));

                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                realm.delete(Detail.class);
                                realm.copyToRealmOrUpdate(response.getDetail());
                                realm.commitTransaction();

//                                Intent intent = new Intent(mContext, ShowClickActivity.class);
//                                mContext.startActivity(intent);
//
                                Log.e("fuck: ","Done" );
                                Intent intent = new Intent(mContext, ShowClickActivity.class);
                                intent.putExtra("usernameclick",get.getUsername());

                                mContext.startActivity(intent);
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

    public void getlist(){

    }

    @Override
    public int getItemCount() {
        return getData().size();
    }
}
