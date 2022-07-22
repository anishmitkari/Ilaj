package com.userdoctor.ui.common.activity.Home_Activity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrUpcomingModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<DrUpcomingModelData> DrUpcomingModelData = null;

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

    public List<DrUpcomingModelData> getDrUpcomingModelData() {
        return DrUpcomingModelData;
    }

    public void setDrUpcomingModelData(List<DrUpcomingModelData> drUpcomingModelData) {
        this.DrUpcomingModelData = drUpcomingModelData;
    }
}
