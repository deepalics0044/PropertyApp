package com.radius.propertyapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Facility extends RealmObject {
    private String facility_id;
    private String facility_name;
    private String name;
    private String icon;
    @PrimaryKey
    private String id;

    public void setData(String facility_id,String facility_name,String name,String icon,String id) {
        this.facility_id = facility_id;
        this.facility_name = facility_name;
        this.name = name;
        this.icon = icon;
        this.id = id;
    }

    public String getFacility_id() {
        return this.facility_id;
    }
    public String getFacility_name() {
        return this.facility_name;
    }
    public String getName() {
        return this.name;
    }
    public int getIcon() {
        if(this.icon.equals("apartment")) {
            return R.drawable.apartment;
        }
        else if(this.icon.equals("condo")) {
            return R.drawable.condo;
        }
        else if(this.icon.equals("boat")) {
            return R.drawable.boat;
        }
        else if(this.icon.equals("land")) {
            return R.drawable.land;
        }
        else if(this.icon.equals("rooms")) {
            return R.drawable.rooms;
        }
        else if(this.icon.equals("no-room")) {
            return R.drawable.no_room;
        }
        else if(this.icon.equals("swimming")) {
            return R.drawable.swimming;
        }
        else if(this.icon.equals("garden")) {
            return R.drawable.garden;
        }
        else if(this.icon.equals("garage")) {
            return R.drawable.garage;
        }
        else {
            return 0;
        }
    }
    public String getId() {
        return this.id;
    }
}
