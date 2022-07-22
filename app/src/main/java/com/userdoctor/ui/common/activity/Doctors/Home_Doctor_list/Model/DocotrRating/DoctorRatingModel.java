package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model.DocotrRating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorRatingModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<DoctorRatingModelData> doctorRatingModelData = null;

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

    public List<DoctorRatingModelData> getDoctorRatingModelData() {
        return doctorRatingModelData;
    }

    public void setDoctorRatingModelData(List<DoctorRatingModelData> data) {
        this.doctorRatingModelData = data;
    }

}
