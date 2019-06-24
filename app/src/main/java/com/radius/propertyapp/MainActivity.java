package com.radius.propertyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextView homePageErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        Constants.FACILITY_NUMBER = 1;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Retrieving Data");
        homePageErrorText = (TextView)findViewById(R.id.home_page_error_text);
        check_internet();
    }

    private boolean check_internet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            load_api_data();
        }
        else {
            connected = false;
            show_error();
        }
        return connected;
    }


    private void load_api_data() {
        progressDialog.show();
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                                JSONObject obj = new JSONObject(response);
                                Log.d("Json",obj.toString());
                                JSONArray new_array = obj.getJSONArray("facilities");
                                Log.d("Json Array Facilities",new_array.toString());

                                //Storing Facilities
                                for(int k = 0;k < new_array.length();++k) {
                                    JSONObject facility_object = new_array.getJSONObject(k);
                                    JSONArray facility_array = facility_object.getJSONArray("options");
                                    int facility_array_len = facility_array.length();
                                    for(int i= 0;i < facility_array_len;++i) {
                                        JSONObject facilities_object = facility_array.getJSONObject(i);
                                        Realm realm = Realm.getDefaultInstance();
                                        realm.beginTransaction();
                                        Facility facility = new Facility();
                                        facility.setData(facility_object.getString("facility_id"),facility_object.getString("name"),facilities_object.getString("name"),facilities_object.getString("icon"),facilities_object.getString("id"));
                                        realm.copyToRealmOrUpdate(facility);
                                        realm.commitTransaction();
                                    }
                                    Constants.TOTAL_FACILITY_NUMBER = k;
                                }

                                //Done Storing Facilties

                                //Now Storing Exclusions
                                JSONArray new_array_2 = obj.getJSONArray("exclusions");
                                int exclusions_array_len = new_array_2.length();
                                int id_gen = 0;
                                for(int i = 0; i < exclusions_array_len; ++i) {
                                    JSONArray exclusions_pair_array = new_array_2.getJSONArray(i);
                                    int exclusions_pair_array_len = exclusions_pair_array.length();
                                    for(int j =0; j < exclusions_pair_array_len;++j) {
                                        JSONObject exclusion_pair_individual = exclusions_pair_array.getJSONObject(j);
                                        Realm realm = Realm.getDefaultInstance();
                                        realm.beginTransaction();
                                        Exclusions exclusions = new Exclusions();
                                        exclusions.setData(exclusion_pair_individual.getString("facility_id"),exclusion_pair_individual.getString("options_id"),Integer.toString(i),Integer.toString(id_gen));
                                        realm.copyToRealmOrUpdate(exclusions);
                                        realm.commitTransaction();
                                        id_gen++;
                                    }
                                }
                                //Done Storing Exclusions


                            Realm realm = Realm.getDefaultInstance();
                            final RealmResults<Exclusions> exclusions = realm.where(Exclusions.class).findAll();
                            Log.d("Exclusions",exclusions.toString());
                            finish();
                            startActivity(new Intent(getApplicationContext(),SelectFacility.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        homePageErrorText.setText("There was an error in retrieving the data. Please try again after sometime.");
                        //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("request","DATA_RETRIEVAL_REQUEST");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void show_error() {
     homePageErrorText.setText("Please check your internet connection and try again.");
    }
}
