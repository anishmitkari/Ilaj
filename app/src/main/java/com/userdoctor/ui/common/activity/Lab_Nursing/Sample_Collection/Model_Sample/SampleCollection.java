
package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SampleCollection {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private EnterData data;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public EnterData getData() {
        return data;
    }

    public void setData(EnterData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
