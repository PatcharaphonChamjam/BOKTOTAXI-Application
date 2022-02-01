package com.boktotaxi.boktotaxi.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class ShowReview extends AppCompatActivity {

    @Bind(R.id.textuser2)TextView _username;
    @Bind(R.id.rating2)RatingBar _ratting;
    @Bind(R.id.Edtcar2)EditText _carnum;
    @Bind(R.id.Edtreveiw2)EditText _review;
    @Bind(R.id.wfrom2)TextView _desfrom;
    @Bind(R.id.wto2)TextView _desto;
    @Bind(R.id.id) TextView _id;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review);
        ButterKnife.bind(this);

        sp = getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
        String username = sp.getString("Username","");
        String userid = sp.getString("UserID","");

        _username.setText(username);
        _id.setText(userid);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String review =(String) bundle.get("review");
            String rate = (String) bundle.get("ratting");
            String carnum =(String) bundle.get("carnum");
            String desfrom=(String) bundle.get("desfrom");
            String desto=(String) bundle.get("desto");

            _review.setText(review);
            _ratting.setRating(Float.parseFloat(rate));
            _carnum.setText(carnum);
            _desfrom.setText(desfrom);
            _desto.setText(desto);
            }


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delshowrv, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Delete:
                delete();
                return true;
            case R.id.Edit:
                edit();
                return true;
//            case R.id.home:
//                this.finish();
//                return true;
        }return false;

    }

    private void delete(){

        AlertDialog.Builder buider = new AlertDialog.Builder(this);
        buider.setTitle("Delete Review");
        buider.setMessage("Delete this review?");
        buider.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String username = _username.getText().toString();
                String userid = _id.getText().toString();
                String carnum = _carnum.getText().toString();

                APIService service = ApiClient.getClient().create(APIService.class);
                service.deleteshow(username, userid, carnum)
                        .asObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                        .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                        .subscribe(new Action1<MSG>() {
                            @Override
                            public void call(MSG response) {
                                if (response.getSuccess()==1) {

                                    Intent intent = new Intent(ShowReview.this, BottomActionBar.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(getBaseContext(), "Delete Successfull", Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getBaseContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                                Toast.makeText(getBaseContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
//                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                            }
                        });
            }
        });
        buider.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        buider.show();

    }

    public void edit (){

        String carnum = _carnum.getText().toString().replaceAll("\\s+", "");
        String rate = String.valueOf(_ratting.getRating());
        String review = _review.getText().toString();
        String from = _desfrom.getText().toString();
        String to = _desto.getText().toString();


        Intent intent = new Intent(this, Edit_reviewActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("carnum2", carnum);
        bundle.putString("ratting2", rate);
        bundle.putString("review2", review);
        bundle.putString("desfrom2", from);
        bundle.putString("desto2", to);
        intent.putExtras(bundle);
        startActivity(intent);


    }

    //        ReviewModel ReviewModel = (ReviewModel) getIntent().getSerializableExtra("ReviewModel");
}
