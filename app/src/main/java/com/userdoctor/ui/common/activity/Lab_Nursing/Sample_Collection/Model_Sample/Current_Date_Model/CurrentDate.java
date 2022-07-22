
package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Current_Date_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentDate {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("formated")
    @Expose
    private String formated;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormated() {
        return formated;
    }

    public void setFormated(String formated) {
        this.formated = formated;
    }

}
