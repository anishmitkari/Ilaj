
package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Current_Date_Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("current_date")
    @Expose
    private CurrentDate currentDate;
    @SerializedName("dates")
    @Expose
    private List<Date> dates = null;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public CurrentDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(CurrentDate currentDate) {
        this.currentDate = currentDate;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
