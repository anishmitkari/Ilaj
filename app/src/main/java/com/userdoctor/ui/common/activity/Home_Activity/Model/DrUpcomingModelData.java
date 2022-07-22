package com.userdoctor.ui.common.activity.Home_Activity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrUpcomingModelData {
    @SerializedName("user_booking_id")
    @Expose
    private String userBookingId;
    @SerializedName("appoint_ID")
    @Expose
    private String appointID;
    @SerializedName("dr_id")
    @Expose
    private String drId;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("dr_name")
    @Expose
    private String drName;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("dr_email")
    @Expose
    private String drEmail;
    @SerializedName("hospital_name")
    @Expose
    private String hospitalName;
    @SerializedName("hospital_address")
    @Expose
    private String hospitalAddress;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("degree_id")
    @Expose
    private String degreeId;
    @SerializedName("doctor_status")
    @Expose
    private String doctorStatus;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("times")
    @Expose
    private String times;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("recipt")
    @Expose
    private String recipt;


    @SerializedName("rating")
    @Expose
    private String rating;


    @SerializedName("booking_type")
    @Expose
    private String booking_type;




    @SerializedName("reviws")
    @Expose
    private String reviws;



    public String getUserBookingId() {
        return userBookingId;
    }

    public void setUserBookingId(String userBookingId) {
        this.userBookingId = userBookingId;
    }

    public String getAppointID() {
        return appointID;
    }

    public void setAppointID(String appointID) {
        this.appointID = appointID;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDrEmail() {
        return drEmail;
    }

    public void setDrEmail(String drEmail) {
        this.drEmail = drEmail;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }

    public String getDoctorStatus() {
        return doctorStatus;
    }

    public void setDoctorStatus(String doctorStatus) {
        this.doctorStatus = doctorStatus;
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




    public String getBooking_type() {
        return booking_type;
    }

    public void setBooking_type(String reviws) {
        this.booking_type = reviws;
    }

}
