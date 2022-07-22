package com.userdoctor.ui.common.model.UpComing_Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NurseAppointmentModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<NurseAppoiData> nurseAppoiData = null;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<NurseAppoiData> getNurseAppoiData() {
        return nurseAppoiData;
    }

    public void setNurseAppoiData(List<NurseAppoiData> nurseAppoiData) {
        this.nurseAppoiData = nurseAppoiData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}