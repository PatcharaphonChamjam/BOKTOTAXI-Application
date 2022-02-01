package com.boktotaxi.boktotaxi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.boktotaxi.boktotaxi.Adapter.Adapter_Searchtaxi;
import com.boktotaxi.boktotaxi.Model.taxidetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SearchListview extends AppCompatActivity {
    private ListView jsonListview;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_listview);

        jsonListview = (ListView) findViewById(R.id.listviewsearch);
//        final ArrayList<Food> exData =new ArrayList<>();


        new AsyncTask<Void, Void, ArrayList<taxidetail>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(SearchListview.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Dowload........");
                progressDialog.show();
            }

            @Override
            protected ArrayList<taxidetail> doInBackground(Void... voids) {
                try {
                    java.net.URL url = new URL("http://192.168.1.4/Taxi/app/showtaxi.php");
                    URLConnection urlConnection = url.openConnection();
                    HttpURLConnection htppURLConnection = (HttpURLConnection) urlConnection;
                    htppURLConnection.setAllowUserInteraction(false);
                    htppURLConnection.setInstanceFollowRedirects(true);
                    htppURLConnection.setRequestMethod("GET");
                    htppURLConnection.connect();

                    InputStream inputStream = null;

                    if (htppURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                        inputStream = htppURLConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);

                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    inputStream.close();
                    Log.d("Json Result", stringBuilder.toString());

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());

                    JSONArray exArray = jsonObject.getJSONArray("feeds");
                    ArrayList<taxidetail> exData = new ArrayList<>();


                    for (int i = 0; i < exArray.length(); i++) {

                        JSONObject jsonObj = exArray.getJSONObject(i);

                        final taxidetail taxi1 = new taxidetail();
                        taxi1.setCarlicense(jsonObj.getString("Carlicense"));


//                        exData.add("Foodname :" + jsonObj.getString("created_at"));
//                        exData.add("Foodprice :" + jsonObj.getString("entry_id"));
//                        exData.add("Foodplace :" + jsonObj.getString("field2"));


                        exData.add(taxi1);

                    }
                    return exData;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<taxidetail> TaxiArrayList) {
                super.onPostExecute(TaxiArrayList
                );
//                ArrayAdapter<String>myAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.
//                        simple_list_item_1, android.R.id.text1, list);
                Adapter_Searchtaxi adapter = new Adapter_Searchtaxi(getApplicationContext(), TaxiArrayList);
                ListView listView = (ListView) findViewById(R.id.listviewsearch);

//                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.
//                        simple_list_item_1, android.R.id.text1, list);
                jsonListview.setAdapter(adapter);
                progressDialog.dismiss();
            }
        }.execute();
    }
}
