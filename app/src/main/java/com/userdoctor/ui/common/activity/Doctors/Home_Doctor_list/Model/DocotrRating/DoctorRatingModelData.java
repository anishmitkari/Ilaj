package com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model.DocotrRating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorRatingModelData {

    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("appoint_ID")
    @Expose
    private String appointID;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("reviws")
    @Expose
    private String reviws;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("email")
    @Expose
    private String email;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getAppointID() {
        return appointID;
    }

    public void setAppointID(String appointID) {
        this.appointID = appointID;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
