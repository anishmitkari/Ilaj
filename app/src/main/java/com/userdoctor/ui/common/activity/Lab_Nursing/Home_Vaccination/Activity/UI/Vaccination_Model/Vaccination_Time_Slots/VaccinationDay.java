
package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Time_Slots;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VaccinationDay {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("routin")
    @Expose
    private String routin;



    public VaccinationDay(String select, String s) {
        this.routin=select;
        this.id=s;

    }

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
