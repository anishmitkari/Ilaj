package com.userdoctor.ui.common.model;

public class AmbulanceModel {

    String id,amb_no,amb_owner,emailamb_contact_no,driving_license
            ,national_id,emrgncy_availability,amb_img,experience,
            address,status,distance,email,amb_contact_no;
double lat,lng;
    //String amb_img,
    public AmbulanceModel(String id, String amb_no,String email, String amb_owner,String amb_contact_no, String driving_license, String national_id, String emrgncy_availability,  String experience, String address, double lat, double lng, String status, String distance) {
        this.id = id;
        this.amb_no = amb_no;
        this.email=email;
        this.amb_owner = amb_owner;
        this.amb_contact_no = amb_contact_no;
        this.driving_license = driving_license;
        this.national_id = national_id;
        this.emrgncy_availability = emrgncy_availability;
       // this.amb_img = amb_img;
        this.experience = experience;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
        this.distance = distance;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmb_no() {
        return amb_no;
    }

    public void setAmb_no(String amb_no) {
        this.amb_no = amb_no;
    }

    public String getAmb_owner() {
        return amb_owner;
    }

    public void setAmb_owner(String amb_owner) {
        this.amb_owner = amb_owner;
    }

    public String getEmailamb_contact_no() {
        return emailamb_contact_no;
    }

    public void setEmailamb_contact_no(String emailamb_contact_no) {
        this.emailamb_contact_no = emailamb_contact_no;
    }

    public String getDriving_license() {
        return driving_license;
    }

    public void setDriving_license(String driving_license) {
        this.driving_license = driving_license;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getEmrgncy_availability() {
        return emrgncy_availability;
    }

    public void setEmrgncy_availability(String emrgncy_availability) {
        this.emrgncy_availability = emrgncy_availability;
    }

    public String getAmb_img() {
        return amb_img;
    }

    public void setAmb_img(String amb_img) {
        this.amb_img = amb_img;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
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
