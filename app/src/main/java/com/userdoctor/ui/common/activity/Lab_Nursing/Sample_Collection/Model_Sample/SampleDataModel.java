
package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SampleDataModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<TestList> testlist = null;

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

    public List<TestList> gettestlist() {
        return testlist;
    }

    public void settestlist(List<TestList> testlist) {
        this.testlist = testlist;
    }

}
