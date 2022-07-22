package com.userdoctor.ui.common.activity.Home_Activity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DRCompleteModel {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<DRCompleteData> drCompleteDataList = null;

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

    public List<DRCompleteData> getDrCompleteDataList() {
        return drCompleteDataList;
    }

    public void setData(List<DRCompleteData> drCompleteDataList) {
        this.drCompleteDataList = drCompleteDataList;
    }
}
