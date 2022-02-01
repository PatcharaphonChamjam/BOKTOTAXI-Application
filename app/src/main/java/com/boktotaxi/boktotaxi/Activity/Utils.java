package com.boktotaxi.boktotaxi.Activity;

import android.content.Context;

import com.boktotaxi.boktotaxi.Model.Contextor;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by USER on 9/5/2560.
 */

public class Utils {
    private static Utils ourInstance;
    private Context mContext;
    private Scheduler defaultSubscribeScheduler;

    private Utils() {
        mContext = Contextor.getInstance().getContext();
    }

    public static Utils getInstance() {
        if (ourInstance == null) {
            ourInstance = new Utils();
        }
        return ourInstance;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }
    public void onHoneyToast(String s) {
//        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
    }
}
