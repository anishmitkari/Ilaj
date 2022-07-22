package com.userdoctor.ui.common.model.CompleteAppointment;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<CompleteData> completeData = null;

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

    public List<CompleteData> getCompleteData() {
        return completeData;
    }

    public void setCompleteData(List<CompleteData> completeData) {
        this.completeData = completeData;
    }

}
