package com.boktotaxi.boktotaxi.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.boktotaxi.boktotaxi.Model.ModelShowReview;
import com.boktotaxi.boktotaxi.Network.APIService;
import com.boktotaxi.boktotaxi.Network.ApiClient;
import com.boktotaxi.boktotaxi.Network.MSG;
import com.boktotaxi.boktotaxi.Network.RSG;
import com.boktotaxi.boktotaxi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 */
public class Write_reviewFragment extends Fragment{

    private static final String TAG = "ReviewActivity";
    private ProgressDialog PDialog;
    @Bind(R.id.WriteEdtcar) EditText _carnum;
    @Bind(R.id.WriteEdtReview) EditText _review;
    @Bind(R.id.WriteEdtFrom) EditText _from;
    @Bind(R.id.WriteEdtTo) EditText _to;
    @Bind(R.id.Writeratting) RatingBar _ratingBar;



    public Write_reviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_write_review, container, false);


        ButterKnife.bind(this,view);
        addListenerOnRatingBar(view);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.WriteAddreview);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getUserList();
                review();
            }
        });


        return view;
    }
    public void review(){
        Log.d(TAG, "ReviewActivity");

        if (validate() == false){
            onReviewFailed();
            return;
        }
        PDialog = new ProgressDialog(getActivity(), R.style.AppTheme);
        PDialog.setIndeterminate(true);
        PDialog.setMessage("Loading....");
        PDialog.setCancelable(false);
        PDialog.show();
        saveReviewtoDB();
    }

    public void onReviewFailed(){
        Toast.makeText(getActivity().getBaseContext(),"Review failed", Toast.LENGTH_LONG).show();
//        _Btnregis.setEnabled(true);
    }

    public boolean validate(){
        boolean valid = true;

        String carnum = _carnum.getText().toString();
        String rate = String.valueOf(_ratingBar.getRating());
        String review = _review.getText().toString();
        String from = _from.getText().toString();
        String to = _to.getText().toString();


//        String ra = _ratingBar.toString();
//        String.valueOf(rating)
        if(carnum.isEmpty()){
            _carnum.setError("enter a valid Carlicense");
            valid = false;
        }else {
            _carnum.setError(null);
        }

        if (review.isEmpty() || review.length() < 10){
            _review.setError("review least 10 characters");
            valid = false;
        }else {
            _review.setError(null);
        }

        if (rate.isEmpty()){
            Toast.makeText(getActivity(),"please enter Ratting",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (from.isEmpty()){
            _from.setError("enter your destination from");
            valid = false;
        }else {
            _from.setError(null);
        }

        if (to.isEmpty()) {
            _to.setError("enter your destinatio to");
            valid = false;
        }else {
            _to.setError(null);
        }

        return valid;
    }

    private void saveReviewtoDB() {
        showPDialog();
        final String carnum = _carnum.getText().toString().trim().replaceAll("\\s+","");
        final String rate = String.valueOf(_ratingBar.getRating());
        final String review = _review.getText().toString();
        final String from = _from.getText().toString();
        final String to = _to.getText().toString();

        SharedPreferences sp = getContext().getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
        String username = sp.getString("Username","Null");
        String userid = sp.getString("UserID","Null");

        APIService service = ApiClient.getClient().create(APIService.class);
        service.mixinsert(carnum, rate, review, from, to, username, userid)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<MSG>() {
                    @Override
                    public void call(MSG response) {
                        if (response.getSuccess() == 0 || response.getSuccess() == 1) {

                            Log.e( "Response ", String.valueOf(response));
                            Log.e("test", "mixinsert: 1");

                            Intent intent = new Intent(getActivity(), ShowReview.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("carnum", carnum);
                            bundle.putString("ratting", rate);
                            bundle.putString("review", review);
                            bundle.putString("desfrom", from);
                            bundle.putString("desto", to);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                          else {
                            Toast.makeText(getContext(),response.getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        PDialog.dismiss();
                        Log.e("test", "mixinsert: 1");
                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                    }
                });
    }








//



//        APIService service = ApiClient.getClient().create(APIService.class);
//        //User user = new User(name, email, password);
//        Call<MSG> userCall = service.userSignUp(username, email, password);
//        userCall.enqueue(new Callback<MSG>() {
//            @Override
//            public void onResponse(Call<MSG> call, Response<MSG> response) {
//                Log.d("onResponse", "" + response.body().getMessage());
//
//                if(response.body().getSuccess() == 1){
//                    hidePDialog();
//                    startActivity(new Intent(Register.this, BottomActionBar.class));
//                    finish();
//                }else {
//                    Toast.makeText(Register.this, "" + response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MSG> call, Throwable t) {
//                hidePDialog();
//                Log.d("onFailure", t.toString());
//
//            }
//        });


    private void showPDialog(){
        if (!PDialog.isShowing())
            PDialog.show();
    }
    private void hidePDialog(){
        if (PDialog.isShowing())
            PDialog.dismiss();
    }



//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case Writeratting:
//                String rating=String.valueOf(ratingbar.getRating());
//                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
//                break;
//
//        }
//    }

    public void addListenerOnRatingBar(View v) {



        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        _ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

//                String rating=String.valueOf(ratingBar.getRating());
//                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(),"Your Ratting : "+String.valueOf(rating),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getUserList() {

        APIService service = ApiClient.getClient().create(APIService.class);
        service.getUserdata()
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<RSG>() {
                    @Override
                    public void call(RSG response) {

                        Log.e("RealmTest: ", String.valueOf(response.getFeeds().size()));

                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.delete(ModelShowReview.class);
                        realm.copyToRealmOrUpdate(response.getFeeds());
                        realm.commitTransaction();

//                        PDialog.dismiss();

                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        PDialog.dismiss();
//                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                    }
                });

    }
}
