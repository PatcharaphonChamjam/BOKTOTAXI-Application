package com.boktotaxi.boktotaxi.Network;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by USER on 20/4/2560.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("Taxi/app/Register.php")
    Observable<MSG> userSignUp(@Field("Username") String username,
                               @Field("Email") String email,
                               @Field("Password") String password);

    @FormUrlEncoded
    @POST("Taxi/app/Login.php")
    Observable<USG> userLogIn(@Field("Username") String username,
                              @Field("Password") String password);


    @FormUrlEncoded
    @POST("Taxi/app/mixinsert.php")
    Observable<MSG> mixinsert(@Field("Carlicense") String carnum,
                                 @Field("Ratting") String rate,
                                 @Field("Review") String review,
                                 @Field("DesFrom") String from,
                                 @Field("DesTo") String to,
                                 @Field("Username") String username,
                                 @Field("UserID") String userid);

    @GET("Taxi/app/allReview.php")
    Observable<RSG> getUserdata();


    @FormUrlEncoded
    @POST("Taxi/app/Logout.php")
    Observable<MSG> logoutuser(@Field("Username") String username);

    @FormUrlEncoded
    @POST("Taxi/app/deletereview.php")
    Observable<MSG> deleteshow(@Field("Username") String username,
                               @Field("UserID") String userid,
                               @Field("Carlicense") String carnum);


    @FormUrlEncoded
    @POST("Taxi/app/updatereview.php")
    Observable<MSG> updaterv(@Field("Username") String username,
                                    @Field("UserID") String userid,
                                    @Field("Carlicense") String carnum,
                                    @Field("Ratting") String rate,
                                    @Field("Review") String review,
                                    @Field("DesFrom") String from,
                                    @Field("DesTo") String to);


    @FormUrlEncoded
    @POST("Taxi/app/showperone.php")
    Observable<RSG> showoneper(@Field("UserID") String userid);

    @FormUrlEncoded
    @POST("Taxi/app/UDUsername.php")
    Observable<MSG> UDUsername(@Field("Username") String username,
                               @Field("UserID") String userid);

    @FormUrlEncoded
    @POST("Taxi/app/showclick.php")
    Observable<RSG> showclick(@Field("UserID") String userid);

    @FormUrlEncoded
    @POST("Taxi/app/showtaxi.php")
    Observable<RSG> showtaxi(@Field("Carlicense") String carnum);

}
