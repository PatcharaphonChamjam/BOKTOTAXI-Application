package com.boktotaxi.boktotaxi.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boktotaxi.boktotaxi.R;


/**
 * Created by USER on 2/5/2560.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mUsername;
    public TextView mDatetime;
    public RatingBar mRatting;
    public TextView mcarlicense;
    public TextView mReview;
    public TextView mFrom;
    public TextView mTo;
    public TextView mUserid;
    public LinearLayout layClick;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        layClick = (LinearLayout) itemView.findViewById(R.id.layClick);
        mUsername = (TextView) itemView.findViewById(R.id.textuser);
        mDatetime = (TextView) itemView.findViewById(R.id.dateandtime);
        mRatting = (RatingBar)itemView.findViewById(R.id.rating);
        mcarlicense = (TextView) itemView.findViewById(R.id.Edtcar);
        mReview = (TextView) itemView.findViewById(R.id.Edtreveiw);
        mFrom = (TextView) itemView.findViewById(R.id.wfrom);
        mTo = (TextView) itemView.findViewById(R.id.wto);
        mUserid = (TextView) itemView.findViewById(R.id.IDuser);
//        mRatting.setClickable(false);
    }

    @Override
    public void onClick(View view) {

    }
}
