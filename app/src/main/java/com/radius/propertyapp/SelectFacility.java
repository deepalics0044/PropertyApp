package com.radius.propertyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;

public class SelectFacility extends AppCompatActivity {


    public FacilityListAdapter adapter;
    public ArrayList<Facility> facilityItemList;
    public Integer inclusions = 0;
    public Boolean exclusionFound = false;
    public Integer ItemIndex = 0;
    public Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_facility);
        getSupportActionBar().setTitle("Select Facilities");
        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.TOTAL_FACILITY_NUMBER >= Constants.FACILITY_NUMBER) {
                    Constants.FACILITY_PREVIOUS_NUMBER = Constants.FACILITY_NUMBER;
                    Constants.FACILITY_NUMBER++;
                    startActivity(new Intent(getApplicationContext(), SelectFacility.class));
                } else {
                    Constants.FACILITY_PREVIOUS_NUMBER = Constants.FACILITY_NUMBER;
                    Constants.FACILITY_NUMBER++;
                    startActivity(new Intent(getApplicationContext(), FinalActivity.class));
                }
            }
        });
        ArrayList<FacilityList> arrayOfFacilities = new ArrayList<FacilityList>();
        adapter = new FacilityListAdapter(this, arrayOfFacilities);
        ListView listView = (ListView) findViewById(R.id.facilities);
        listView.setAdapter(adapter);

        adapter.clear();
        FacilityList facilityList = new FacilityList();
        ArrayList<Facility> facilityItemList = new ArrayList<Facility>();
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Exclusions> property_exclusions = new ArrayList<Exclusions>();
        for (int m = Constants.facilityList.size() - 1; m >= 0; m--) {
            final RealmResults<Exclusions> exclusions = realm.where(Exclusions.class).equalTo("facility_id", Constants.facilityList.get(m).getFacility_id()).equalTo("options_id", Constants.facilityList.get(m).getId()).findAll();
            for(int n = 0; n < exclusions.size(); n++) {
                property_exclusions.add(exclusions.get(n));
            }
        }

        final Facility facility = realm.where(Facility.class).equalTo("facility_id", Integer.toString(Constants.FACILITY_NUMBER)).findFirst();

        final RealmResults<Facility> results = realm.where(Facility.class)
                .equalTo("facility_id", Integer.toString(Constants.FACILITY_NUMBER))
                .findAll();
        final OrderedRealmCollection<Facility> posts = results.createSnapshot(); // <-- snapshot
        inclusions = 0;
        for (int j = 0; j < results.size(); j++) {
            exclusionFound = false;
            if (Constants.FACILITY_NUMBER > 1) {
                    final RealmResults<Exclusions> exclusions2 = realm.where(Exclusions.class).equalTo("facility_id", results.get(j).getFacility_id()).equalTo("options_id", results.get(j).getId()).findAll();
                    for (int k = 0; k < property_exclusions.size(); k++) {
                        for (int l = 0; l < exclusions2.size(); l++) {
                            if (property_exclusions.get(k).getPairId().equals(exclusions2.get(l).getPairId())) {
                                exclusionFound = true;
                            }
                        }
                    }
                    if (!exclusionFound) {
                        facilityItemList.add(results.get(j));
                        nextButton.setEnabled(false);
                        inclusions++;
                    }

            } else {
                    facilityItemList.add(results.get(j));
                    nextButton.setEnabled(false);
                    inclusions++;
                }
            }
            if (inclusions == 0) {
                nextButton.setEnabled(true);
            }
            if (inclusions != 0) {
                facilityList.setData(facility.getFacility_name(), facilityItemList);
                adapter.add(facilityList);
            }
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.FACILITY_NUMBER--;
        if(Constants.facilityList.size() > 0) {
            Constants.facilityList.remove(Constants.facilityList.size() - 1);
        }
    }
}
