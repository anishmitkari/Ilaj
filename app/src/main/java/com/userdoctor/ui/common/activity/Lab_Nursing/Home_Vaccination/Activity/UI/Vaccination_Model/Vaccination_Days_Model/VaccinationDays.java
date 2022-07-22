
package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Days_Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VaccinationDays {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<Vaccination_Days_Datum> data = null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Vaccination_Days_Datum> getData() {
        return data;
    }

    public void setData(List<Vaccination_Days_Datum> data) {
        this.data = data;
    }

}
