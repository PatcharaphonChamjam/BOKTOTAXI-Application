package com.boktotaxi.boktotaxi.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 27/4/2560.
 */

public class ReviewModel {
    @SerializedName("carnum")
    private String carnum;
    @SerializedName("rate")
    private String rate;
    @SerializedName("review")
    private String review;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;


    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ReviewModel(){
        this.carnum = carnum;
        this.rate = rate;
        this.review = review;
        this.from = from;
        this.to = to;
    }
}
