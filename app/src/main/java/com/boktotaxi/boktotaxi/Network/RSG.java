package com.boktotaxi.boktotaxi.Network;

import com.boktotaxi.boktotaxi.Model.Detail;
import com.boktotaxi.boktotaxi.Model.ModelShowReview;
import com.boktotaxi.boktotaxi.Model.Show;
import com.boktotaxi.boktotaxi.Model.Taxi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by USER on 5/5/2560.
 */

public class RSG{
    @SerializedName("feeds")
    @Expose
    private List<ModelShowReview> feeds = null;

    @SerializedName("Show")
    @Expose
    private List<Show> show = null;

    @SerializedName("detail")
    @Expose
    private List<Detail> detail = null;

    @SerializedName("taxi")
    @Expose
    private List<Taxi> taxi = null;


    public List<ModelShowReview> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<ModelShowReview> feeds) {
        this.feeds = feeds;
    }


    public List<Show> getShow() {
        return show;
    }

    public void setShow(List<Show> show) {
        this.show = show;
    }


    public List<Detail> getDetail() {return detail;}

    public void setDetail(List<Detail> detail) {this.detail = detail;}


    public List<Taxi> getTaxi() {return taxi;}

    public void setTaxi(List<Taxi> taxi) {this.taxi = taxi;}
}
