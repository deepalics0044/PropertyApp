package com.radius.propertyapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Exclusions extends RealmObject {
    private String facility_id;
    private String options_id;
    private String pair_id;
    @PrimaryKey
    private String id;

    public void setData(String facility_id,String options_id,String pair_id,String id) {
        this.facility_id = facility_id;
        this.options_id = options_id;
        this.pair_id = pair_id;
        this.id = id;
    }
    public String getPairId() {
        return this.pair_id;
    }
}
