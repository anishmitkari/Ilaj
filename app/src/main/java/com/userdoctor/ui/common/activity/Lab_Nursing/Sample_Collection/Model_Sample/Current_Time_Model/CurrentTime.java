
package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Current_Time_Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentTime {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
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
