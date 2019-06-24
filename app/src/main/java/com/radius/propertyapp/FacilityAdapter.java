package com.radius.propertyapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FacilityAdapter extends ArrayAdapter<Facility> {
    public FacilityAdapter(Context context, ArrayList<Facility> facility) {
        super(context, 0,facility);
    }
    private ProgressDialog block_dialog;
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Facility item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.facility_item, parent, false);
        }
        // Lookup view for data population
        ImageView facilityImage = (ImageView)convertView.findViewById(R.id.facility_icon);
        TextView facilityTypeName = (TextView)convertView.findViewById(R.id.facility_type_name);
        LinearLayout facilityButton = (LinearLayout)convertView.findViewById(R.id.facility_item_button);
        Drawable drawable = getContext().getDrawable(item.getIcon());
        facilityImage.setImageDrawable(drawable);
        facilityTypeName.setText(item.getName());

        facilityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Constants.lastFacility = item;
                Constants.facilityList.add(item);
                if (Constants.TOTAL_FACILITY_NUMBER >= Constants.FACILITY_NUMBER) {
                    Constants.FACILITY_PREVIOUS_NUMBER = Constants.FACILITY_NUMBER;
                    Constants.FACILITY_NUMBER++;
                    Intent myIntent = new Intent(getContext(), SelectFacility.class);
                    getContext().startActivity(myIntent);
                } else {
                    Constants.FACILITY_PREVIOUS_NUMBER = Constants.FACILITY_NUMBER;
                    Constants.FACILITY_NUMBER++;
                    Intent myIntent = new Intent(getContext(), FinalActivity.class);
                    getContext().startActivity(myIntent);
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
