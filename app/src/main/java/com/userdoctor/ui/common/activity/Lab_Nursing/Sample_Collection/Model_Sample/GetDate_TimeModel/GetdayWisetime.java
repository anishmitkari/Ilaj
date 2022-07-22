package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.GetDate_TimeModel;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetdayWisetime {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Datum> getDatum() {
        return data;
    }

    public void setDatum(List<Datum> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
