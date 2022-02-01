package com.boktotaxi.boktotaxi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by USER on 19/6/2560.
 */

public class Search extends RealmObject implements RealmModel {

    @SerializedName("0")
    @Expose
    private String _0;
    @SerializedName("1")
    @Expose
    private String _1;
    @SerializedName("2")
    @Expose
    private String _2;
    @SerializedName("CarID")
    @Expose
    private String carID;
    @SerializedName("Carlicense")
    @Expose
    private String carlicense;
    @SerializedName("arg_ratting")
    @Expose
    private String argRatting;

    public String get_0() {
        return _0;
    }

    public void set_0(String _0) {
        this._0 = _0;
    }

    public String get_1() {
        return _1;
    }

    public void set_1(String _1) {
        this._1 = _1;
    }

    public String get_2() {
        return _2;
    }

    public void set_2(String _2) {
        this._2 = _2;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarlicense() {
        return carlicense;
    }

    public void setCarlicense(String carlicense) {
        this.carlicense = carlicense;
    }

    public String getArgRatting() {
        return argRatting;
    }

    public void setArgRatting(String argRatting) {
        this.argRatting = argRatting;
    }
}
