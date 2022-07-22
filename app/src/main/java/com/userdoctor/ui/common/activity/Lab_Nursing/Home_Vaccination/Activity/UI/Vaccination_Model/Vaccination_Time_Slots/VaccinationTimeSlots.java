
package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Time_Slots;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VaccinationTimeSlots {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<VaccinationTimeDatum> vaccinationTimeDatum = null;
    @SerializedName("vaccination_days")
    @Expose
    private List<VaccinationDay> vaccinationDays = null;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<VaccinationTimeDatum> getVaccinationTimeDatum() {
        return vaccinationTimeDatum;
    }

    public void setVaccinationTimeDatum(List<VaccinationTimeDatum> data) {
        this.vaccinationTimeDatum = data;
    }

    public List<VaccinationDay> getVaccinationDays() {
        return vaccinationDays;
    }

    public void setVaccinationDays(List<VaccinationDay> vaccinationDays) {
        this.vaccinationDays = vaccinationDays;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
