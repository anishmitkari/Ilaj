
package com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Time_Slots;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VaccinationTimeDatum {

    @SerializedName("sentinel")
    @Expose
    private String sentinel;
    @SerializedName("time_slots")
    @Expose
    private List<TimeSlot_Vacc> timeSlotVaccs = null;

    public String getSentinel() {
        return sentinel;
    }

    public void setSentinel(String sentinel) {
        this.sentinel = sentinel;
    }

    public List<TimeSlot_Vacc> getTimeSlotVaccs() {
        return timeSlotVaccs;
    }

    public void setTimeSlotVaccs(List<TimeSlot_Vacc> timeSlotVaccs) {
        this.timeSlotVaccs = timeSlotVaccs;
    }

}
