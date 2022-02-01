package com.boktotaxi.boktotaxi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boktotaxi.boktotaxi.Model.taxidetail;

import java.util.ArrayList;

/**
 * Created by USER on 4/4/2560.
 */

public class Adapter_Searchtaxi extends BaseAdapter {
    Context mContext;
    ArrayList<taxidetail> TaxiArrayList;

    public Adapter_Searchtaxi(Context list, ArrayList<taxidetail> TaxiArrayList) {
        this.mContext = list;

        this.TaxiArrayList = TaxiArrayList;

    }

    @Override
    public int getCount() {
//        return count();
        return TaxiArrayList.size();
//        return  (TaxiArrayList == null) ? 0 : TaxiArrayList.size();
    }

    @Override
    public taxidetail getItem(int position) {
        return TaxiArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater mInfrater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;
        if (view == null) {
//            view = mInfrater.inflate(R.layout.resultsearch, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        taxidetail taxi = TaxiArrayList.get(position);
        holder.textresult.setText(taxi.getCarlicense());


        return view;
    }

    private class ViewHolder {


        TextView textresult;


        public ViewHolder(View view) {

//            textresult = (TextView) view.findViewById(R.id.textresult);
        }
    }
}
