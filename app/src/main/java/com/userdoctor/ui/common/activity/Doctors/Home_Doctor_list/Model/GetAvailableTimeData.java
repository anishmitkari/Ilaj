package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model;

public class GetAvailableTimeData {

    String avail_time_id,day_id,day,avail_start_tym,avail_end_tym;


    public GetAvailableTimeData(String avail_time_id, String day_id, String day, String avail_start_tym, String avail_end_tym) {
        this.avail_time_id = avail_time_id;
        this.day_id = day_id;
        this.day = day;
        this.avail_start_tym = avail_start_tym;
        this.avail_end_tym = avail_end_tym;
    }

    public String getAvail_time_id() {
        return avail_time_id;
    }

    public void setAvail_time_id(String avail_time_id) {
        this.avail_time_id = avail_time_id;
    }

    public String getDay_id() {
        return day_id;
    }

    public void setDay_id(String day_id) {
        this.day_id = day_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAvail_start_tym() {
        return avail_start_tym;
    }

    public void setAvail_start_tym(String avail_start_tym) {
        this.avail_start_tym = avail_start_tym;
    }

    public String getAvail_end_tym() {
        return avail_end_tym;
    }

    public void setAvail_end_tym(String avail_end_tym) {
        this.avail_end_tym = avail_end_tym;
    }
}
