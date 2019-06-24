package com.radius.propertyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class FinalActivity extends AppCompatActivity {

    public TextView finalText;
    public TextView finalTextHeading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        finalText = (TextView)findViewById(R.id.finalText);
        finalTextHeading = (TextView)findViewById(R.id.finalTextHeading);
        for(int i = 0; i < Constants.facilityList.size(); i++) {
            finalText.append(Constants.facilityList.get(i).getFacility_name()+": \n"+Constants.facilityList.get(i).getName()+"\n\n");
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
