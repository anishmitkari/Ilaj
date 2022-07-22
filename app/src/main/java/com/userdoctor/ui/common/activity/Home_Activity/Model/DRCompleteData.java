package com.userdoctor.ui.common.activity.Home_Activity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DRCompleteData {




    @SerializedName("user_booking_id")
    @Expose
    private String userBookingId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("dr_id")
    @Expose
    private String drId;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("dr_name")
    @Expose
    private String drName;
    @SerializedName("dr_email")
    @Expose
    private String drEmail;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("hospital_name")
    @Expose
    private String hospitalName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("recipt")
    @Expose
    private String recipt;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("reviws")
    @Expose
    private String reviws;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("times")
    @Expose
    private String times;

    public String getUserBookingId() {
        return userBookingId;
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

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrEmail() {
        return drEmail;
    }

    public void setDrEmail(String drEmail) {
        this.drEmail = drEmail;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecipt() {
        return recipt;
    }

    public void setRecipt(String recipt) {
        this.recipt = recipt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviws() {
        return reviws;
    }

    public void setReviws(String reviws) {
        this.reviws = reviws;
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
