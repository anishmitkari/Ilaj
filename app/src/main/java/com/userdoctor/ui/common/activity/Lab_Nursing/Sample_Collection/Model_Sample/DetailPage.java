
package com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailPage {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private DetailData data;
    @SerializedName("banners")
    @Expose
    private List<Banner> banners = null;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DetailData getData() {
        return data;
    }

    public void setData(DetailData data) {
        this.data = data;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
