package com.boktotaxi.boktotaxi.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boktotaxi.boktotaxi.Model.Show;
import com.boktotaxi.boktotaxi.R;
import com.boktotaxi.boktotaxi.ViewHolder.RecyclerViewHolder;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by USER on 16/5/2560.
 */

public class ShowRVadapter extends RealmRecyclerViewAdapter<Show,RecyclerView.ViewHolder> {
    private List<Show> item;
    Context mContext;


    public ShowRVadapter(@NonNull Context context, @Nullable OrderedRealmCollection data, boolean autoUpdate) {
        super(context, data, autoUpdate);

        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_review,null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Show get = getData().get(position);
        RecyclerViewHolder itemHolder = (RecyclerViewHolder)holder;
        itemHolder.mUsername.setText(get.getUsername());
        itemHolder.mDatetime.setText(get.getDatetime());
        itemHolder.mRatting.setRating(Float.parseFloat(get.getRatting()));
        itemHolder.mcarlicense.setText(get.getCarlicense());
        itemHolder.mReview.setText(get.getReview());
        itemHolder.mFrom.setText(get.getDesFrom());
        itemHolder.mTo.setText(get.getDesTo());

        String username = itemHolder.mUsername.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("username", username);


    }

    @Override
    public int getItemCount() {
         return getData().size();
    }
}
