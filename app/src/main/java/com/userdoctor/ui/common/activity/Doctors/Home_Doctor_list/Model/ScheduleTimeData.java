package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model;

public class ScheduleTimeData {

    String  id,dr_id,day_id,day,day_shedule;

    public ScheduleTimeData(String id, String dr_id, String day_id, String day, String day_shedule) {
        this.id = id;
        this.dr_id = dr_id;
        this.day_id = day_id;
        this.day = day;
        this.day_shedule = day_shedule;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDr_id() {
        return dr_id;
    }

    public void setDr_id(String dr_id) {
        this.dr_id = dr_id;
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

    public String getDay_shedule() {
        return day_shedule;
    }

    public void setDay_shedule(String day_shedule) {
        this.day_shedule = day_shedule;
    }
}
