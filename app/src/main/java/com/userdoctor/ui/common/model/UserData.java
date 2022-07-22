package com.userdoctor.ui.common.model;

public class UserData {

    private boolean result;
    private int id;
    private String fname, lname, contact_no,email,password
           ,image, gender,age,blood_group,location,city_id,lat,lng,fcm_id,social_id,status,type;

///////for google login
    public UserData(int id, String fname,String email,String image) {
        this.id = id;
        this.fname = fname;
        this.email = email;
        this.image=image;
    }


  /*  public UserData(int id, String fname,  String contact_no, String email) {
        this.id = id;
        this.fname = fname;
        this.contact_no = contact_no;
        this.email = email;
    }*/




    public UserData(int id, String fname,  String contact_no, String email,String image) {
        this.id = id;
        this.fname = fname;
        this.contact_no = contact_no;
        this.email = email;
        this.image = image;
    }

    public UserData(int id, String fname,  String contact_no, String email,String image,String gender,String age,String blood_group,String location,String city_id) {
        this.id = id;
        this.fname = fname;
        this.contact_no = contact_no;
        this.email = email;
        this.image=image;
        this.gender=gender;
        this.age=age;
        this.blood_group=blood_group;
        this.location=location;
        this.city_id=city_id;



    }




    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }



    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
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

    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
