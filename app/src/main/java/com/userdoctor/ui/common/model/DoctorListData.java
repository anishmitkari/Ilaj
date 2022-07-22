package com.userdoctor.ui.common.model;

/**
 * Created by Ravindra Birla on 25/09/2019.
 */
public class DoctorListData {

String id,category_id,dr_name,profile_img,contact_no,dr_email,lab_name,lab_address,hospital_name,hospital_address,
        mci_member,national_id,gender,degree_id,passing_year,experience,phone_no,clinic_fee,home_fee,emergency_fee,
        address,countryId,stateId,city_id,home_availability,emergency,lat,lng,registration_proof,qualification_proof,
        id_proof,status,clinic_open_hours,clinic_close_hours,delete_status,type,abut_self,avg_rating,distance;


    public DoctorListData(String id, String category_id, String dr_name, String profile_img, String contact_no, String dr_email, String lab_name, String lab_address, String hospital_name, String hospital_address, String mci_member, String national_id, String gender, String degree_id, String passing_year, String experience, String phone_no, String clinic_fee, String home_fee, String emergency_fee, String address, String countryId, String stateId, String city_id, String home_availability, String emergency, String lat, String lng, String registration_proof, String qualification_proof, String id_proof, String status, String clinic_open_hours, String clinic_close_hours, String delete_status) {
        this.id = id;
        this.category_id = category_id;
        this.dr_name = dr_name;
        this.profile_img = profile_img;
        this.contact_no = contact_no;
        this.dr_email = dr_email;
        this.lab_name = lab_name;
        this.lab_address = lab_address;
        this.hospital_name = hospital_name;
        this.hospital_address = hospital_address;
        this.mci_member = mci_member;
        this.national_id = national_id;
        this.gender = gender;
        this.degree_id = degree_id;
        this.passing_year = passing_year;
        this.experience = experience;
        this.phone_no = phone_no;
        this.clinic_fee = clinic_fee;
        this.home_fee = home_fee;
        this.emergency_fee = emergency_fee;
        this.address = address;
        this.countryId = countryId;
        this.stateId = stateId;
        this.city_id = city_id;
        this.home_availability = home_availability;
        this.emergency = emergency;
        this.lat = lat;
        this.lng = lng;
        this.registration_proof = registration_proof;
        this.qualification_proof = qualification_proof;
        this.id_proof = id_proof;
        this.status = status;
        this.clinic_open_hours = clinic_open_hours;
        this.clinic_close_hours = clinic_close_hours;
        this.delete_status = delete_status;


    }
    public DoctorListData(String id, String category_id, String dr_name, String profile_img, String contact_no, String dr_email, String lab_name, String lab_address, String hospital_name, String hospital_address, String mci_member, String national_id, String gender, String degree_id, String passing_year, String experience, String phone_no, String clinic_fee, String home_fee, String emergency_fee, String address, String countryId, String stateId, String city_id, String home_availability, String emergency, String lat, String lng, String registration_proof, String qualification_proof, String id_proof, String status, String clinic_open_hours, String clinic_close_hours, String delete_status,String avg_rating,String distance,String type) {
        this.id = id;
        this.category_id = category_id;
        this.dr_name = dr_name;
        this.profile_img = profile_img;
        this.contact_no = contact_no;
        this.dr_email = dr_email;
        this.lab_name = lab_name;
        this.lab_address = lab_address;
        this.hospital_name = hospital_name;
        this.hospital_address = hospital_address;
        this.mci_member = mci_member;
        this.national_id = national_id;
        this.gender = gender;
        this.degree_id = degree_id;
        this.passing_year = passing_year;
        this.experience = experience;
        this.phone_no = phone_no;
        this.clinic_fee = clinic_fee;
        this.home_fee = home_fee;
        this.emergency_fee = emergency_fee;
        this.address = address;
        this.countryId = countryId;
        this.stateId = stateId;
        this.city_id = city_id;
        this.home_availability = home_availability;
        this.emergency = emergency;
        this.lat = lat;
        this.lng = lng;
        this.registration_proof = registration_proof;
        this.qualification_proof = qualification_proof;
        this.id_proof = id_proof;
        this.status = status;
        this.clinic_open_hours = clinic_open_hours;
        this.clinic_close_hours = clinic_close_hours;
        this.delete_status = delete_status;
        this.avg_rating=avg_rating;
        this.distance=distance;
        this.type=type;
    }


    public String getDistance() {
        return distance;
    }


    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(String avg_rating) {
        this.avg_rating = avg_rating;
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

    public String getDr_email() {
        return dr_email;
    }

    public void setDr_email(String dr_email) {
        this.dr_email = dr_email;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public String getLab_address() {
        return lab_address;
    }

    public void setLab_address(String lab_address) {
        this.lab_address = lab_address;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }

    public String getMci_member() {
        return mci_member;
    }

    public void setMci_member(String mci_member) {
        this.mci_member = mci_member;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDegree_id() {
        return degree_id;
    }

    public void setDegree_id(String degree_id) {
        this.degree_id = degree_id;
    }

    public String getPassing_year() {
        return passing_year;
    }

    public void setPassing_year(String passing_year) {
        this.passing_year = passing_year;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getClinic_fee() {
        return clinic_fee;
    }

    public void setClinic_fee(String clinic_fee) {
        this.clinic_fee = clinic_fee;
    }

    public String getHome_fee() {
        return home_fee;
    }

    public void setHome_fee(String home_fee) {
        this.home_fee = home_fee;
    }

    public String getEmergency_fee() {
        return emergency_fee;
    }

    public void setEmergency_fee(String emergency_fee) {
        this.emergency_fee = emergency_fee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getHome_availability() {
        return home_availability;
    }

    public void setHome_availability(String home_availability) {
        this.home_availability = home_availability;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
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

    public String getRegistration_proof() {
        return registration_proof;
    }

    public void setRegistration_proof(String registration_proof) {
        this.registration_proof = registration_proof;
    }

    public String getQualification_proof() {
        return qualification_proof;
    }

    public void setQualification_proof(String qualification_proof) {
        this.qualification_proof = qualification_proof;
    }

    public String getId_proof() {
        return id_proof;
    }

    public void setId_proof(String id_proof) {
        this.id_proof = id_proof;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClinic_open_hours() {
        return clinic_open_hours;
    }

    public void setClinic_open_hours(String clinic_open_hours) {
        this.clinic_open_hours = clinic_open_hours;
    }

    public String getClinic_close_hours() {
        return clinic_close_hours;
    }

    public void setClinic_close_hours(String clinic_close_hours) {
        this.clinic_close_hours = clinic_close_hours;
    }

    public String getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(String delete_status) {
        this.delete_status = delete_status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbut_self() {
        return abut_self;
    }

    public void setAbut_self(String abut_self) {
        this.abut_self = abut_self;
    }
}
