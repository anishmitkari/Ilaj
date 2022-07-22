package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.VaccinationDetail_Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vac_Banner {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("banner")
    @Expose
    private String banner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

}
