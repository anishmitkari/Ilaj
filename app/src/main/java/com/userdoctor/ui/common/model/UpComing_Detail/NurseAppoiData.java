package com.userdoctor.ui.common.model.UpComing_Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NurseAppoiData {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("patient_id")
    @Expose
    private String patient_id;

    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("investigation")
    @Expose
    private String investigation;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("distance")
    @Expose
    private String distance;

    @SerializedName("date_time")
    @Expose
    private String date_time;

    public NurseAppoiData(String id, String patient_id, String patientName, String investigation, String address, String status, String distance,String date_time) {
        this.id = id;
        this.patient_id=patient_id;
        this.patientName = patientName;
        this.investigation = investigation;
        this.address = address;
        this.status = status;
        this.distance = distance;
        this.date_time = date_time;
    }


    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getInvestigation() {
        return investigation;
    }

    public void setInvestigation(String investigation) {
        this.investigation = investigation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
