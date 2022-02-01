package com.boktotaxi.boktotaxi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by USER on 4/6/2560.
 */

public class Detail extends RealmObject implements RealmModel {

    @PrimaryKey
    @SerializedName("IDReview")
    @Expose
    private String iDReview;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Carlicense")
    @Expose
    private String carlicense;
    @SerializedName("Ratting")
    @Expose
    private String ratting;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("Review")
    @Expose
    private String review;
    @SerializedName("DesFrom")
    @Expose
    private String desFrom;
    @SerializedName("DesTo")
    @Expose
    private String desTo;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Userpic")
    @Expose
    private String userpic;

    public String getiDReview() {
        return iDReview;
    }

    public void setiDReview(String iDReview) {
        this.iDReview = iDReview;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCarlicense() {
        return carlicense;
    }

    public void setCarlicense(String carlicense) {
        this.carlicense = carlicense;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDesFrom() {
        return desFrom;
    }

    public void setDesFrom(String desFrom) {
        this.desFrom = desFrom;
    }

    public String getDesTo() {
        return desTo;
    }

    public void setDesTo(String desTo) {
        this.desTo = desTo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }
}
