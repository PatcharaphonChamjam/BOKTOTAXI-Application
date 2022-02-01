package com.boktotaxi.boktotaxi.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditUsernameActivity extends AppCompatActivity {

    @Bind(R.id.editUsername)EditText _edtusername;

    @Bind(R.id.editsaveUS)Button _btsave;
    @Bind(R.id.editcancelUS)Button _btcancel;
    @Bind(R.id.textuserid)TextView _userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_username);
        ButterKnife.bind(this);

        SharedPreferences sp = getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
        String username = sp.getString("Username", "");
        String userid = sp.getString("UserID","");

        _edtusername.setText(username);
        _userid.setText(userid);

      _btsave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              AlertDialog.Builder builder = new AlertDialog.Builder(EditUsernameActivity.this);
              builder.setMessage("Are you sure want to save?");
              builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {

                      Changusername();
                  }
              });
              builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      //dialog.dismiss();
                  }
              });
              builder.show();


          }
      });



        _btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditUsernameActivity.this, edit_profile.class);
                startActivity(intent);
            }
        });

    }

    private void Changusername(){
        String username = _edtusername.getText().toString().trim();
        String userid = _userid.getText().toString();

        APIService service = ApiClient.getClient().create(APIService.class);
        service.UDUsername(username, userid)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<MSG>() {
                    @Override
                    public void call(MSG response) {
                        if (response.getSuccess() == 1) {

                            Intent intent = new Intent(EditUsernameActivity.this, Login.class);
                            startActivity(intent);

                            SharedPreferences sp = getBaseContext().getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.apply();

                            Toast.makeText(getBaseContext(),"Change Username successfull",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(getBaseContext(), "" + response.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                    }
                });
    }
}
