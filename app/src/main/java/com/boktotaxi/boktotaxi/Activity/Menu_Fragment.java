package com.boktotaxi.boktotaxi.Activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.boktotaxi.boktotaxi.Model.Show;
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
public class Menu_Fragment extends Fragment {

    @Bind(R.id.MenuUsername)TextView _MenuUser;
    @Bind(R.id.MenuLogout)TextView _MenuLogout;
    @Bind(R.id.userid)TextView _userid;

    public Menu_Fragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        SharedPreferences sp = this.getActivity().getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
        String username = sp.getString("Username", "");
        String userid = sp.getString("UserID","");

        _userid.setText(userid);
        _MenuUser.setText(username);

        _MenuUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showprofile();
            }
        });


        _MenuLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Log out now?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//                        Log.e("onResponse", "Yes");
//                        SharedPreferences sp = getContext().getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.clear();
//                        editor.apply();
//
//                        Intent intent = new Intent(getActivity(), Login.class);
//                            startActivity(intent);

                        Logout();
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


    }

    private void Logout() {

        String username = _MenuUser.getText().toString();


        APIService service = ApiClient.getClient().create(APIService.class);
        service.logoutuser(username)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<MSG>() {
                    @Override
                    public void call(MSG response) {
                        if (response.getSuccess() == 1) {
                            Intent intent = new Intent(getActivity(), Login.class);
                            startActivity(intent);

                            SharedPreferences sp = getContext().getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.apply();
                            Toast.makeText(getContext(), "Already Logout.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("onResponse", "Yes");
                            Intent intent = new Intent(getActivity(), Login.class);
                            startActivity(intent);

                            SharedPreferences sp = getContext().getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.apply();
                            Toast.makeText(getContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                    }
                });

    }

    public void showprofile() {

        String userid = _userid.getText().toString();

        APIService service = ApiClient.getClient().create(APIService.class);
        service.showoneper(userid)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<RSG>() {
                    @Override
                    public void call(RSG response) {

                        Log.e("RealmTest: ", String.valueOf(response.getShow().size()));

                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.delete(Show.class);
                        realm.copyToRealmOrUpdate(response.getShow());
                        realm.commitTransaction();

                        Intent intent = new Intent(getActivity(), edit_profile.class);
                        startActivity(intent);

                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(getActivity(), throwable.getMessage(),Toast.LENGTH_SHORT).show();
//                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());
                    }
                });

    }

//    private void editprofile(){
//
//    }


}
