
package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vaccination_Detail_Data {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("vaccination_type")
    @Expose
    private String vaccinationType;

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("price")
    @Expose
    private String price;


    @SerializedName("description")
    @Expose
    private String description;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVaccinationType() {
        return vaccinationType;
    }

    public void setVaccinationType(String vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
