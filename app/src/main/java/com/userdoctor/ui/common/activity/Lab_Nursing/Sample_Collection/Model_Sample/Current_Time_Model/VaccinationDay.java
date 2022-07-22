
package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Current_Time_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VaccinationDay {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("routin")
    @Expose
    private String routin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoutin() {
        return routin;
    }

    public void setRoutin(String routin) {
        this.routin = routin;
    }

}
