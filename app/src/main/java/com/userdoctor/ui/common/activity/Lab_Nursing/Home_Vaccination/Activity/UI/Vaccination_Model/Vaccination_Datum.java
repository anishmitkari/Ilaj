
package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vaccination_Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("vaccinations_types")
    @Expose
    private List<VaccinationsType> vaccinationsTypes = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VaccinationsType> getVaccinationsTypes() {
        return vaccinationsTypes;
    }

    public void setVaccinationsTypes(List<VaccinationsType> vaccinationsTypes) {
        this.vaccinationsTypes = vaccinationsTypes;
    }

}
