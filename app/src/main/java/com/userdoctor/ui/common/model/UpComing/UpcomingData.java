package com.userdoctor.ui.common.model.UpComing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingData {

    @SerializedName("user_booking_id")
    @Expose
    private String userBookingId;

    @SerializedName("type")
    @Expose
    private String type;


    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("nurse_id")
    @Expose
    private String nurseId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("ns_name")
    @Expose
    private String nsName;
    @SerializedName("ns_contact_no")
    @Expose
    private String nsContactNo;
    @SerializedName("hospital_name")
    @Expose
    private String hospitalName;
    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("home_sample_collection")
    @Expose
    private String home_sample_collection;

    @SerializedName("status")
    @Expose

    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("times")
    @Expose
    private String times;



    public String getUserBookingId() {
        return userBookingId;
    }


    public UpcomingData(String userBookingId,
                        String userId,
                        String nurseId,
                        String image,
                        String nsName,
                        String nsContactNo,
                        String hospitalName,
                        String address,
                        String home_sample_collection,
                        String status,String date,String type) {

        this.userBookingId = userBookingId;
        this.userId = userId;
        this.nurseId = nurseId;
        this.image = image;
        this.nsName = nsName;
        this.nsContactNo = nsContactNo;
        this.hospitalName = hospitalName;
        this.address = address;
        this.home_sample_collection=home_sample_collection;
        this.status=status;
        this.date = date;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserBookingId(String userBookingId) {
        this.userBookingId = userBookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNsName() {
        return nsName;
    }

    public void setNsName(String nsName) {
        this.nsName = nsName;
    }

    public String getNsContactNo() {
        return nsContactNo;
    }

    public void setNsContactNo(String nsContactNo) {
        this.nsContactNo = nsContactNo;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getHome_sample_collection() {
        return home_sample_collection;
    }

    public void setHome_sample_collection(String home_sample_collection) {
        this.home_sample_collection = home_sample_collection;
    }

    public String getDate() {
        return date;
    }





    public void setDate(String date) {
        this.date = date;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}


