package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NursingDetailRequest {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private NursingData nursingData;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public NursingData getNursingData() {
        return nursingData;
    }

    public void setNursingData(NursingData data) {
        this.nursingData = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
