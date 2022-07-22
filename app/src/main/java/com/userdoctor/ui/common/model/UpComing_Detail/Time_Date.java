package com.userdoctor.ui.common.model.UpComing_Detail;

public class Time_Date {
    String from_time,to_time,sentinel;


    public Time_Date(String from_time, String to_time, String sentinel) {
        this.from_time = from_time;
        this.to_time = to_time;
        this.sentinel = sentinel;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getSentinel() {
        return sentinel;
    }

    public void setSentinel(String sentinel) {
        this.sentinel = sentinel;
    }
}
