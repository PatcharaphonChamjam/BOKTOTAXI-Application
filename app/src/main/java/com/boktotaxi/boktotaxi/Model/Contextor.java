package com.boktotaxi.boktotaxi.Model;

import android.content.Context;

/**
 * Created by USER on 9/5/2560.
 */

public class Contextor {
    private static Contextor instance;
    private Context mConext;

    public Contextor(){}

    public  static Contextor getInstance(){
        if(instance == null){
            instance = new Contextor();
        }
        return instance;
    }

    public void init(Context context){
        mConext = context;
    }

    public  Context getContext(){
        return mConext;
    }
}
