package com.boktotaxi.boktotaxi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.boktotaxi.boktotaxi.Network.APIService;
import com.boktotaxi.boktotaxi.Network.ApiClient;
import com.boktotaxi.boktotaxi.Network.MSG;
import com.boktotaxi.boktotaxi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class Register extends AppCompatActivity {


    private static final String TAG = "RegiterActivity";
    private ProgressDialog PDialog;
    @Bind(R.id.regisUsername) EditText _Edtusername;
    @Bind(R.id.regisEmail) EditText _Edtemail;
    @Bind(R.id.regisPass) EditText _Edtpassword;
    @Bind(R.id.btnRegissub) Button _Btnregis;
    @Bind(R.id.btnregislog) Button _BtnLinklog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        _Btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        _BtnLinklog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.animator.push_right_in,R.animator.push_right_out);
            }
        });
    }

    public void register(){
        Log.d(TAG, "Regiter");

        if (validate() == false){
            onRegisFailed();
            return;
        }
        PDialog = new ProgressDialog(Register.this, R.style.AppTheme);
        PDialog.setIndeterminate(true);
        PDialog.setMessage("Create Account....");
        PDialog.setCancelable(false);
        PDialog.show();
        savetoSeverDB();
    }
    public void onRegisSuccess(){
        _Btnregis.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onRegisFailed(){
        Toast.makeText(getBaseContext(),"Register failed", Toast.LENGTH_LONG).show();
        _Btnregis.setEnabled(true);
    }

    public boolean validate(){
        boolean valid = true;

        String username = _Edtusername.getText().toString();
        String email = _Edtemail.getText().toString();
        String password = _Edtpassword.getText().toString();

        if (username.isEmpty() || username.length() < 3){
            _Edtusername.setError("username least 3 characters");
            valid = false;
        }else {
            _Edtusername.setError(null);
        }
//|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isEmpty() ){
            _Edtemail.setError("enter a valid email address");
            valid = false;
        }else {
            _Edtemail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() >10){
            _Edtpassword.setError("enter Password between 4 and 10 characters");
            valid = false;
        }else {
            _Edtpassword.setError(null);
        }

        return valid;
    }

    private void savetoSeverDB(){

        String username = _Edtusername.getText().toString().trim();
        String email = _Edtemail.getText().toString();
        String password = _Edtpassword.getText().toString();


        APIService service = ApiClient.getClient().create(APIService.class);
        service.userSignUp(username, email, password)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<MSG>(){
                               @Override
                               public void call(MSG response) {
                                   if(response.getSuccess() == 1){
                                       PDialog.dismiss();
                                       startActivity(new Intent(Register.this, Login.class));
                                       finish();
                                   }else {
                                       PDialog.dismiss();
                                       Toast.makeText(Register.this, "" + response.getMessage(),Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        PDialog.dismiss();
                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                    }
                });

    }

    private void showPDialog(){
        if (!PDialog.isShowing())
            PDialog.show();
    }
    private void hidePDialog(){
        if (PDialog.isShowing())
            PDialog.dismiss();
    }

}
