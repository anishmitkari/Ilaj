package com.userdoctor.ui.common.Interface;

import com.userdoctor.ui.common.activity.Doctors.Home_Doctor_list.Model.DocotrRating.DoctorRatingModel;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Nursing.Model.NursingDetailRequest;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Post_Vaccination_Request.PostVaccinationRequest;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Days_Model.VaccinationDays;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.VaccinationCollection;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.VaccinationDetail;
import com.userdoctor.ui.common.activity.Lab_Nursing.Home_Vaccination.Activity.UI.Vaccination_Model.Vaccination_Time_Slots.VaccinationTimeSlots;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.Current_Date_Model.DateModel;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.DetailPage;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.GetDate_TimeModel.GetdayWisetime;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.SampleCollection;
import com.userdoctor.ui.common.activity.Lab_Nursing.Sample_Collection.Model_Sample.SampleDataModel;
import com.userdoctor.ui.common.activity.Home_Activity.Model.DRCompleteModel;
import com.userdoctor.ui.common.activity.Home_Activity.Model.DrUpcomingModel;
import com.userdoctor.ui.common.model.CompleteAppointment.CompleteModel;
import com.userdoctor.ui.common.model.UpComing.UpcomingModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.userdoctor.ui.common.Interface.Api.BASE_URL2;

public interface Api_Call {

    @GET("test_list")
    Call<SampleDataModel> getTestList();


    @FormUrlEncoded
    @POST("get_test_sub_cate_detail")
    Call<DetailPage> getDtail(
            @Field("id") String prod_id);


    @FormUrlEncoded
    @POST("booking_list_by_user_id")
    Call<UpcomingModel> getUpComingHistory(
            @Field("user_id") String user_id);

    /////////////////////////////////////////////
    @FormUrlEncoded
    @POST("doctor_booking_list_by_user_id")
    Call<DrUpcomingModel> getDRUpComingHistory(
            @Field("user_id") String user_id);
    ///////////////////////////////////////////

    ///dr complete list


    @FormUrlEncoded
    @POST("user_complete_booking_list")
    Call<DRCompleteModel> getDRCompleteList(
            @Field("user_id") String user_id);


//////////////////////////////////////////

    @FormUrlEncoded
    @POST("complete_booking")
    Call<CompleteModel> getCompleteBookings(
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("sample_collection")
    Call<SampleCollection> getSampleCollection(
            @Field("sub_cate_id") String sub_cate_id,
            @Field("user_id") String user_id,
            @Field("patient_name") String patient_name,
            @Field("investigation") String investigation,
            @Field("patient_age") String patient_age,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("landmark") String landmark,
            @Field("patient_gender") String patient_gender,
            @Field("from_date") String date,
            @Field("times") String time,
            @Field("lat") String lat,
            @Field("lng") String lng
    );


    @GET("get_dates")
    Call<DateModel> getDate();


    @GET("get_vaccination_list")
    Call<VaccinationCollection> getVaccinationList();


    @FormUrlEncoded
    @POST("get_vaccination_detail")
    Call<VaccinationDetail> getVaccinationDetail(
            @Field("id") String id);


    @FormUrlEncoded
    @POST("get_vaccination_courses")
    Call<VaccinationDays> getVaccinationDays(
            @Field("id") String id);


    @FormUrlEncoded
    @POST("post_vaccination_request")
    Call<PostVaccinationRequest> PostVaccinationRequest(
            @Field("course_id") String course_id,
            @Field("user_id") String user_id,
            @Field("patient_name") String patient_name,
            @Field("patient_age") String patient_age,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("landmark") String landmark,
            @Field("from_date") String from_date,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("patient_gender") String patient_gender,
            @Field("time_interval_id") String time_interval_id,
            @Field("vaccination_type_id") String vaccination_type_id,
            @Field("to_date") String to_date,
            @Field("times") String times);


    @FormUrlEncoded
    @POST("get_day_wise_time")
    Call<GetdayWisetime> getDayWiseTime(
            @Field("date") String id);


    @GET("get_days_variations")
    Call<VaccinationTimeSlots> getCurrentTime();


    @FormUrlEncoded
    @POST("post_home_nursing")
    Call<NursingDetailRequest> getNursingRequest(
            @Field("user_id") String user_id,
            @Field("patient_name") String patient_name,
            @Field("investigation") String investigation,
            @Field("patient_age") String patient_age,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("landmark") String landmark,
            @Field("from_date") String from_date,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("patient_gender") String patient_gender,
            /* @Field("to_date") String to_date,*/
            @Field("times") String times
    );


    ///////////////////doctore rating///////////


    @FormUrlEncoded
    @POST(BASE_URL2 + "doctor_rating_review_list")
    Call<DoctorRatingModel> DRRatingList(
            @Field("dr_id") String s);


}
