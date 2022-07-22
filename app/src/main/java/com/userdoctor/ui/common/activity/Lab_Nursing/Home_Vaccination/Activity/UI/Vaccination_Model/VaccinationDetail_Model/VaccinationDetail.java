
package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.VaccinationDetail_Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VaccinationDetail {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("banners")
    @Expose
    private List<Vac_Banner> banners = null;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Vac_Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Vac_Banner> banners) {
        this.banners = banners;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
