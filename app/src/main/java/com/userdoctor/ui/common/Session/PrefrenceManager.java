package com.userdoctor.ui.common.Session;

import android.content.Context;
import android.content.SharedPreferences;

/**
     * Created by admin1 on 23/5/18.
     */

    public class PrefrenceManager {

        /*shareprefence for storing data*/
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
       public static final String MyPREFERENCES = "MyPrefss";
        private static String FORMII_PREF = "com.illaj.ui.common.utils";
        private static final String IS_LOGIN = "IsLoggedIn";
        private static String USER_ID="com.illaj.ui.common.utils.USER_ID";
        private static String FirstName="com.illaj.ui.common.utils.FirstName";
        private static String LastName="com.illaj.ui.common.utils.LastName";
        private static String ContactNo="com.illaj.ui.common.utils.ContactNo";
        private static String Email="com.illaj.ui.common.utils.Email";
        private static String Lattitude="com.illaj.ui.common.utils.Lattitude";
        private static String Longitude="com.illaj.ui.common.utils.Longitude";
        private static String ILAJ_ISLOGIN = "com.illaj.ui.common.utils.ILAJ_ISLOGIN";
        private static String SAMPLE_ID_ITEM="com.ilaj.ui.common.utils.ILAJ_SAMPLE_ID_ITEM";
        private static String SAMPLE_TEST_NAME_ITEM="com.ilaj.ui.common.utils.SAMPLE_TEST_NAME_ITEM";
        private static String Vaccination_Main_Category_ID="vaccination_main_category_id";
        private static String VACCINATION_NAME_ITEM="com.ilaj.ui.common.utils.VACCINATION_NAME_ITEM";
        private static String VACCINATION_ID_ITEM="com.ilaj.ui.common.utils.VACCINATION_ID_ITEM";
        private static String VACCINATION_DAYS_ID="com.ilaj.ui.common.utils.VACCINATION_DAYS_ID";


    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        editor.commit();
        }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public PrefrenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
        editor = pref.edit();
        editor = pref.edit();

    }


    public static String getVaccinationDaysId(Context context) {
        return VACCINATION_DAYS_ID;
    }

    public static void setVaccinationDaysId(Context context,String vaccinationDaysId) {
        VACCINATION_DAYS_ID = vaccinationDaysId;
    }

    public static String getVaccination_Main_Category_ID(Context context) {
        return Vaccination_Main_Category_ID;
    }

    public static void setVaccination_Main_Category_ID(Context context,String vaccination_Main_Category_ID) {
        Vaccination_Main_Category_ID = vaccination_Main_Category_ID;
    }





    public static String getVaccinationNameItem(Context context) {
        return VACCINATION_NAME_ITEM;
    }

    public static void setVaccinationNameItem(Context context,String vaccinationNameItem) {
        VACCINATION_NAME_ITEM = vaccinationNameItem;
    }

    public static String getVaccinationIdItem(Context context) {
        return VACCINATION_ID_ITEM;
    }

    public static void setVaccinationIdItem(Context context,String vaccinationIdItem) {
        VACCINATION_ID_ITEM = vaccinationIdItem;
    }

    public static String getSampleTestNameItem(Context context) {
        return SAMPLE_TEST_NAME_ITEM;
    }

    public static void setSampleTestNameItem(Context context,String sampleTestNameItem) {
        SAMPLE_TEST_NAME_ITEM = sampleTestNameItem;
    }

    public static String getSampleIdItem(Context context) {
        return SAMPLE_ID_ITEM;
    }

    public static void setSampleIdItem(Context context,String sampleIdItem) {
        SAMPLE_ID_ITEM = sampleIdItem;
    }

    public static String getIlajIslogin(Context context) {
            return ILAJ_ISLOGIN;
        }

        public static void setIlajIslogin(Context context,String value) {
            ILAJ_ISLOGIN = value;
        }

        public static SharedPreferences getPrefs(Context context) {
            return context.getSharedPreferences(FORMII_PREF, Context.MODE_PRIVATE);
        }


        public static String getUserId(Context context) {
            return USER_ID;
        }

        public static void setUserId(Context context,String userId) {
            USER_ID = userId;
        }

        public static String getFirstName(Context context) {
            return FirstName;
        }

        public static void setFirstName(Context context,String firstName) {
            FirstName = firstName;
        }

        public static String getLastName(Context context) {
            return LastName;
        }

        public static void setLastName(Context context,String lastName) {
            LastName = lastName;
        }

        public static String getContactNo(Context context) {
            return ContactNo;
        }

        public static void setContactNo(Context context,String contactNo) {
            ContactNo = contactNo;
        }

        public static String getEmail(Context context) {
            return Email;
        }

        public static void setEmail(Context context,String email) {
            Email = email;
        }

        public static String getLattitude(Context context) {
            return Lattitude;
        }

        public static void setLattitude(Context context,String lattitude) {
            Lattitude = lattitude;
        }

        public static String getLongitude(Context context) {
            return Longitude;
        }

        public static void setLongitude(Context context,String longitude) {
            Longitude = longitude;
        }










    }
