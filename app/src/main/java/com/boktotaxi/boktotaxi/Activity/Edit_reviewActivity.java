package com.boktotaxi.boktotaxi.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.boktotaxi.boktotaxi.Network.APIService;
import com.boktotaxi.boktotaxi.Network.ApiClient;
import com.boktotaxi.boktotaxi.Network.MSG;
import com.boktotaxi.boktotaxi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class Edit_reviewActivity extends AppCompatActivity {

    @Bind(R.id.WriteEdtcar3) EditText _carnum;
    @Bind(R.id.WriteEdtReview3) EditText _review;
    @Bind(R.id.WriteEdtFrom3) EditText _from;
    @Bind(R.id.WriteEdtTo3) EditText _to;
    @Bind(R.id.Writeratting3) RatingBar _ratingBar;
    @Bind(R.id.us)TextView _username;
    @Bind(R.id.id)TextView _id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);
        ButterKnife.bind(this);

        SharedPreferences sp = getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
        String username = sp.getString("Username","Null");
        String userid = sp.getString("UserID","Null");

        _username.setText(username);
        _id.setText(userid);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String review = (String) bundle.get("review2");
            String rate = (String) bundle.get("ratting2");
            String carnum = (String) bundle.get("carnum2");
            String desfrom = (String) bundle.get("desfrom2");
            String desto = (String) bundle.get("desto2");

            _review.setText(review);
            _ratingBar.setRating(Float.parseFloat(rate));
            _carnum.setText(carnum);
            _from.setText(desfrom);
            _to.setText(desto);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.updaterv, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
                Updatereview();
                return true;
        }return false;
    }

    public void Updatereview(){

        if (validate() == false){
            onUpdateReviewFailed();
            return;
        }
        Update();
    }

    public void onUpdateReviewFailed(){
        Toast.makeText(getBaseContext(),"Review failed", Toast.LENGTH_LONG).show();

    }

    public boolean validate(){
        boolean valid = true;

        String rate = String.valueOf(_ratingBar.getRating());
        String review = _review.getText().toString();
        String from = _from.getText().toString();
        String to = _to.getText().toString();


//        String ra = _ratingBar.toString();
//        String.valueOf(rating)

        if (review.isEmpty() || review.length() < 10){
            _review.setError("review least 10 characters");
            valid = false;
        }else {
            _review.setError(null);
        }

        if (rate.isEmpty()){
            Toast.makeText(getBaseContext(),"please enter Ratting",Toast.LENGTH_SHORT).show();
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

    private void Update(){

        String username = _username.getText().toString();
        String userid = _id.getText().toString();
        String carnum = _carnum.getText().toString();
        String rate = String.valueOf(_ratingBar.getRating());
        String review = _review.getText().toString();
        String from = _from.getText().toString();
        String to = _to.getText().toString();

//        Log.e("Update1: ",username );
//        Log.e("Update2: ",userid );
//        Log.e("Update3: ",carnum );
//        Log.e("Update4: ",rate );
//        Log.e("Update5: ",review );
//        Log.e("Update6: ",from );
//        Log.e("Update7: ",to );


        APIService service = ApiClient.getClient().create(APIService.class);
        service.updaterv(username, userid, carnum, rate, review, from, to)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<MSG>() {
                    @Override
                    public void call(MSG response) {
                        if (response.getSuccess() == 1){
                            Log.e("Update: ","Done" );
                            Intent intent = new Intent(Edit_reviewActivity.this, BottomActionBar.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getBaseContext(), "update Successfull", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getBaseContext(), "Failed update", Toast.LENGTH_LONG).show();
                            Log.e("Update: ","NotDone" );
                        }
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("Update: ","NotRespon" );
                        Toast.makeText(getBaseContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
//                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                    }
                });




    }
}
