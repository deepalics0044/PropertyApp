package com.radius.propertyapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FacilityList {
    public String facility_name;
    public ArrayList<Facility> facilityArrayList;

    public void setData(String facility_name, ArrayList<Facility> facilityArrayList) {
        this.facility_name = facility_name;
        this.facilityArrayList = facilityArrayList;
    }
}
