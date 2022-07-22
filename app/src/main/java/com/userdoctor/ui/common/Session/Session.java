package com.userdoctor.ui.common.Session;


import android.content.Context;
import android.content.SharedPreferences;

public class Session extends Object {

    private static final String TAG = Session.class.getSimpleName();
    private static final String PREF_NAME = "Dr_pref";
    private static final String PREF_NAME2 = "Dr_pref2";
    private static final String IS_LOGGEDIN = "isLoggedIn";
    //private static final String IS_LOGIN = "IsLoggedIn";
    private static final String FAV = "fav";
    private static final String Mobile = "mobile";
    private static final String LOGIN_TYPE = "login_type";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String USER_ID = "user_id";
    private static final String DR_ID = "dr_id";
    private static final String Email = "email";
    private static final String Token_Id = "token";
    private static final String Profile_Image ="profile_img";
    private static final String Medi_ID = "medi_id";
    private static final String Lab_ID = "lab_id";
    private static final String BookingDate = "bkdt";
    private static final String AdBookingType = "bktyp";

    private static SharedPreferences sharedPreferences;
    private static Context _context;
    private SharedPreferences mypref, mypref2;
    private SharedPreferences.Editor editor, editor2, editor3;
    public Session(Context context) {

        this._context = context;
        mypref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mypref.edit();
        editor.apply();
        mypref2 = _context.getSharedPreferences(PREF_NAME2, Context.MODE_PRIVATE);
        editor2 = mypref2.edit();
        editor2.apply();

    }



    public void setMobile(String mobile, String email) {
        editor2.putString(Mobile, mobile);
        editor2.putString(Email, email);
        editor2.apply();
        editor2.commit();
    }

    public String getMobile() {
        return mypref2.getString(Mobile, "");

    }

    public String getEmail() {
        return mypref2.getString(Email, "");

    }
   public String getDrId(){
       return mypref2.getString(DR_ID, "");
   }
   public void setDrId(String id){
       editor2.putString(DR_ID, id);
       this.editor2.apply();
   }

    public String getProfile_Image(){
        return mypref2.getString(Profile_Image, "");
    }
    public void setProfile_Image(String profile_img){
        editor2.putString(Profile_Image, profile_img);
        this.editor2.apply();
    }



    public void saveToken(String token) {
        editor2.putString(Token_Id, token);
        editor2.apply();
        editor2.commit();
    }

    public String getTokenId() {
        return mypref2.getString(Token_Id, "");
    }
    public void setLogin(boolean isLoggedIn) {
        editor2.putBoolean(IS_LOGIN, isLoggedIn);
        editor2.commit();
    }
    public boolean isLoggedIn() {
        return mypref2.getBoolean(IS_LOGIN, false);
    }

    public void setLoginType(String loginType) {
        editor2.putString(LOGIN_TYPE, loginType);
        this.editor2.apply();
    }

    public String getLoginType() {
        return mypref2.getString(LOGIN_TYPE, "");
    }



  public void setUserId(String userId) {
        editor2.putString(USER_ID, userId);
        this.editor2.apply();
    }

    public String getUserId() {
        return mypref2.getString(USER_ID, "");
    }


    /*public void setPassword(String password) {
        editor2.putString("userpassword", password);
        this.editor2.apply();
    }

    public void setReminderState(boolean isset) {
        editor2.putBoolean("ReminderState", isset);
        this.editor2.apply();
    }


//

    public boolean getReminderState() {
        return mypref2.getBoolean("ReminderState", true);
    }


    public String getPassword() {
        return mypref2.getString("userPassword", "");
    }

    public void rememberMe(String user, String password) {
        editor2.putString("email", getEmailPhone());
        editor2.putString("pass", getPassword());
        editor2.apply();
    }*/

    public  void logout() {


        SharedPreferences sharedPreferences = _context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();



    }

   /* public boolean isLoggedIn() {
        //return mypref.getBoolean(IS_LOGGEDIN, false);
        return mypref.getBoolean("IsLogin", false);
    }*/


    public void rememberMe(String email, String password) {
        editor2.putString("rem_email", email);
        editor2.putString("rem_password", password);
        editor2.apply();
    }

    public String getRemEmail() {
        return mypref2.getString("rem_email", "");
    }

    public String getRemPassword() {
        return mypref2.getString("rem_password", "");
    }

    public void setPriceRangerValue(String product_name, String quantity, String minValue, String maxValue) {
        editor.putString("product_name", product_name);
        editor.putString("quantity", quantity);
        editor.putString("minPrice", minValue);
        editor.putString("maxPrice", maxValue);
        editor.apply();
    }

    public void setBookingDate(String mobile) {
        editor2.putString(BookingDate, mobile);

        editor2.apply();
        editor2.commit();
    }

    public String getBookingDate() {
        return mypref2.getString(BookingDate, "");

    }






    public void setAdBookingType(String mobile) {
        editor2.putString(AdBookingType, mobile);

        editor2.apply();
        editor2.commit();
    }

    public String getAdBookingType() {
        return mypref2.getString(AdBookingType, "");

    }



    public String getMinValue() {
        return mypref.getString("minPrice", "0");
    }

    public String getMaxValue() {
        return mypref.getString("maxPrice", "500");
    }

    public String getProductName() {
        return mypref.getString("product_name", "");
    }

    public String getProductQuantity() {
        return mypref.getString("quantity", "");
    }
}
