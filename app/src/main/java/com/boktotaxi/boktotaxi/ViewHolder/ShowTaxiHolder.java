package com.boktotaxi.boktotaxi.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boktotaxi.boktotaxi.R;

/**
 * Created by USER on 18/6/2560.
 */

public class ShowTaxiHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mUsername;
    public TextView mDatetime;
    public RatingBar mRatting;
    public TextView mcarlicense;
    public TextView mReview;
    public TextView mFrom;
    public TextView mTo;
    public TextView mUserid;
    public TextView mARGratting;
    public LinearLayout layClick;

    public ShowTaxiHolder(View itemView) {
        super(itemView);
        layClick = (LinearLayout) itemView.findViewById(R.id.layClick);
        mUsername = (TextView) itemView.findViewById(R.id.textuser);
        mDatetime = (TextView) itemView.findViewById(R.id.dateandtime);
        mRatting = (RatingBar)itemView.findViewById(R.id.rating);
        mcarlicense = (TextView) itemView.findViewById(R.id.Edtcar);
        mReview = (TextView) itemView.findViewById(R.id.Edtreveiw);
        mFrom = (TextView) itemView.findViewById(R.id.wfrom);
        mTo = (TextView) itemView.findViewById(R.id.wto);
        mUserid = (TextView) itemView.findViewById(R.id.IDuser);
        mARGratting = (TextView) itemView.findViewById(R.id.ARGratting);
    }

    @Override
    public void onClick(View view) {

    }
}
