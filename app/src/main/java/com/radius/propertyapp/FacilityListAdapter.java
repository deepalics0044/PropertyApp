package com.radius.propertyapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;

public class FacilityListAdapter extends ArrayAdapter<FacilityList> {

    public FacilityAdapter adapter;
    private Integer height;

    public FacilityListAdapter(Context context, ArrayList<FacilityList> facilities) {
        super(context, 0,facilities);
    }
    private ProgressDialog block_dialog;
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final FacilityList item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.facility_list_item, parent, false);
        }
        // Lookup view for data population
        TextView facilityName = (TextView)convertView.findViewById(R.id.facility_name);
        facilityName.setText(item.facility_name);
        ArrayList<Facility> arrayOfFacilities = new ArrayList<Facility>();
        adapter = new FacilityAdapter(getContext(), arrayOfFacilities);
        ListView listView = (ListView) convertView.findViewById(R.id.facility_list);
        listView.setAdapter(adapter);

        for(int i=0; i< item.facilityArrayList.size(); i++) {
             adapter.add(item.facilityArrayList.get(i));
            height = i;
        }

        height++;

        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams((int)(LinearLayout.LayoutParams.MATCH_PARENT),(int)(dpToPx(height*100)));
        listView.setLayoutParams(mParam);

        return convertView;
    }

    public int dpToPx(int dp) {
        float density = getContext().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}
