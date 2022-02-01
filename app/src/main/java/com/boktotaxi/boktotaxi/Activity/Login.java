package com.boktotaxi.boktotaxi.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.boktotaxi.boktotaxi.Network.APIService;
import com.boktotaxi.boktotaxi.Network.ApiClient;
import com.boktotaxi.boktotaxi.Network.RSG;
import com.boktotaxi.boktotaxi.Network.USG;
import com.boktotaxi.boktotaxi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class Login extends AppCompatActivity {


    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGHUP = 0;
    private ProgressDialog PDialog;

    @Bind(R.id.logusername)
    EditText _Edtusername;
    @Bind(R.id.logpassword)
    EditText _EdtPassword;
    @Bind(R.id.btnlogsub)
    Button _Btnlogin;
    @Bind(R.id.btnlogregis)
    Button _BtnLInkRegis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //hide action bar
        getSupportActionBar().hide();
        ButterKnife.bind(this);



        _Btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _BtnLInkRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivityForResult(intent, REQUEST_SIGHUP);
                finish();
                overridePendingTransition(R.animator.push_left_in, R.animator.push_left_out);
            }
        });

    }


    public void login() {
        Log.d(TAG, "Login");
        if (validate() == false) {
            onLoginFailed();
            return;
        }
        PDialog = new ProgressDialog(Login.this, R.style.AppTheme);
        PDialog.setIndeterminate(true);
        PDialog.setMessage("Login Loading...");
        PDialog.setCancelable(false);
        PDialog.show();
        loginByServer();
    }


    public void loginByServer() {

        String username = _Edtusername.getText().toString();
        String password = _EdtPassword.getText().toString();



        APIService service = ApiClient.getClient().create(APIService.class);
        service.userLogIn(username, password)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<USG>() {
                    @Override
                    public void call(USG response) {
                        if (response.getSuccess() == 1) {
//                    sharePreference();
                            String userid = response.getUserID().toString();
                            String username = _Edtusername.getText().toString();

                            SharedPreferences sp = getBaseContext().getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();

//                            String userid = sp.getString("UserID","Null");

                            editor.putInt("KEY_LOGIN", 1);
                            editor.putString("Username", username);
                            editor.putString("UserID", userid);
                            editor.apply();

                            getUserList();

                            Log.e( "UserID", response.getUserID().toString());

                        } else {
                            PDialog.dismiss();
                            Toast.makeText(Login.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
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
                        realm.copyToRealmOrUpdate(response.getFeeds());
                        realm.commitTransaction();

                        PDialog.dismiss();

                        Intent intent = new Intent(Login.this, BottomActionBar.class);
                        intent.putExtra("Page",1);
                        startActivity(intent);
                        finish();
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        PDialog.dismiss();
//                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                    }
                });

    }

    //    public void sharePreference(){
//        String username = _Edtusername.getText().toString();
//
//        SharedPreferences sp = getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt("KEY_LOGIN", 1);
//        editor.putString("Username", username);
//        editor.apply();
//
//    }


    private void showPDialog() {
        if (!PDialog.isShowing())
            PDialog.show();
    }

    private void hidePDialog() {
        if (PDialog.isShowing())
            PDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGHUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    public void onLoginSuccess() {
        _Btnlogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _Btnlogin.setEnabled(true);
    }

    public boolean validate() {

        boolean valid = true;

        String username = _Edtusername.getText().toString();
        String password = _EdtPassword.getText().toString();

        if (username.isEmpty()) {
            _Edtusername.setError("Enter a valid Username");
            requeseFocus(_Edtusername);
            valid = false;
        } else {
            _Edtusername.setError(null);
        }

        if (password.isEmpty()) {
            _EdtPassword.setError("Enter a valid Password");
            requeseFocus(_EdtPassword);
            valid = false;
        } else {
            _EdtPassword.setError(null);
        }
        return valid;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requeseFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



}
