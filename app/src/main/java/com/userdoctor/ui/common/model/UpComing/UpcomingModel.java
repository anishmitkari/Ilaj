package com.userdoctor.ui.common.model.UpComing;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingModel {

        @SerializedName("result")
        @Expose
        private String result;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("data")
        @Expose
        private List<UpcomingData> upcomingDataList = null;

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

        public List<UpcomingData> getUpcomingDataList() {
            return upcomingDataList;
        }

        public void setUpcomingDataList(List<UpcomingData> upcomingDataList) {
            this.upcomingDataList = upcomingDataList;
        }

    }

