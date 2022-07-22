package com.userdoctor.ui.common.model;

public class ClinikListData {




    String lat,lng,avg_rating,id,category_id,dr_name,profile_img,contact_no,distance,clinic_name,clinic_address,type;


    public ClinikListData(String id, String category_id, String dr_name, String profile_img, String contact_no, String distance,String clinic_name,String clinic_address ,String avg_rating,String lat,String lng,String type) {
        this.id = id;
        this.category_id = category_id;
        this.dr_name = dr_name;
        this.profile_img = profile_img;
        this.contact_no = contact_no;
        this.distance = distance;
        this.clinic_name = clinic_name;
        this.clinic_address = clinic_address;
        this.avg_rating=avg_rating;
        this.lat=lat;
        this.lng=lng;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(String avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getClinic_address() {
        return clinic_address;
    }

    public void setClinic_address(String clinic_address) {
        this.clinic_address = clinic_address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getDr_name() {
        return dr_name;
    }

    public void setDr_name(String dr_name) {
        this.dr_name = dr_name;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }






}
