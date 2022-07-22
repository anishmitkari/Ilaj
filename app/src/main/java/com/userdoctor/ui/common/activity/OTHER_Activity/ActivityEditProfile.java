package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.userdoctor.R;
import com.userdoctor.ui.common.model.UserData;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleyMultipartRequest;
import com.userdoctor.ui.common.utils.VolleySingleton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.userdoctor.ui.common.utils.URLs.User_Change_Password;

public class ActivityEditProfile extends AppCompatActivity {
    CircleImageView profile_image;
    EditText et_phone, et_location, et_email, et_gender, et_blood;
    Button btn_update;
    Bitmap bitmap;
    private static final String IMAGE_DIRECTORY = "/UrDoctor";
    private int GALLERY = 1, CAMERA = 2;
    SharedPrefManager sharedPrefManager;
    String user_firstname, id, user_age, user_contact, user_image,user_location, user_email, user_gender, user_bloodgroup;
    int user_id;
    TextView chnagepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager != null) {
            user_id = sharedPrefManager.getUser().getId();
            user_firstname = sharedPrefManager.getUser().getFname();
            user_age = sharedPrefManager.getUser().getAge();
            user_contact = sharedPrefManager.getUser().getContact_no();
            user_location = sharedPrefManager.getUser().getLocation();
            user_email = sharedPrefManager.getUser().getEmail();
            user_image = sharedPrefManager.getUser().getImage();
        }

        id = String.valueOf(user_id);
        NetworkUtil.isNetworkConnected(ActivityEditProfile.this);

        initview();
        requestMultiplePermissions();
        mFunProfileApiCall();
        clicklistners();


    }



    public void mFunProfileApiCall() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "get_userProfile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            boolean res = obj.getBoolean("result");
                            if (res == true) {

                                JSONObject data = obj.getJSONObject("data");
                                String id = data.getString("id");
                                String mStrFirstName = data.getString("fname");
                                String mStrContactNo = data.getString("contact_no");
                                String mStrEmail = data.getString("email");
                                String mStrPassword = data.getString("password");
                                String mStrImage = data.getString("image");
                                String mStrGender = data.getString("gender");
                                String mStrAge = data.getString("age");
                                String mStrBloodGroup = data.getString("blood_group");
                                String mStrLocation = data.getString("location");
                                String mStrCityId = data.getString("city_id");
                                String mStrLattitude = data.getString("lat");
                                String mStrLongitude = data.getString("lng");
                                et_phone.setText(mStrContactNo);
//                                mTextUserage.setText("Age " + mStrAge + " yrs");
                                et_location.setText(mStrLocation);
                                et_email.setText(mStrEmail);
                                et_gender.setText(mStrGender);
                                et_blood.setText(mStrBloodGroup);


                                Log.e("check error", mStrImage);

                                if (mStrImage != null && !mStrImage.isEmpty()) {
                                Picasso.with(ActivityEditProfile.this).load(URLs.IMAGE_BASE_URL+mStrImage).into(profile_image);
                                }
                                else
                                {
                                    Toast.makeText(ActivityEditProfile.this, "ms"+mStrImage, Toast.LENGTH_SHORT).show();

                                }





                            } else {
                                String msg = obj.getString("msg");
                                Toast.makeText(ActivityEditProfile.this, msg, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("check error", e.getMessage().toString());
                            System.out.print("check" + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    public void initview() {
        et_phone = findViewById(R.id.et_phone);
        et_location = findViewById(R.id.et_location);
        et_email = findViewById(R.id.et_email);
        et_gender = findViewById(R.id.et_gender);
        et_blood = findViewById(R.id.et_blood);
        profile_image = findViewById(R.id.profile_image);
        chnagepass = findViewById(R.id.changepass);

        chnagepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Password_Dialoge();
            }
        });
    }
    private void Change_Password_Dialoge() {
        final Dialog dialog = new Dialog(ActivityEditProfile.this);
        dialog.setContentView(R.layout.user_change_password_dialoge);
        final EditText old_password = dialog.findViewById(R.id.old_password);
        final EditText new_password = dialog.findViewById(R.id.new_password);
        final EditText confirm_password = dialog.findViewById(R.id.confirm_password);
        Button dialogButton = (Button) dialog.findViewById(R.id.update_btn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStroldpassword = old_password.getText().toString();
                final String mStrnewPassword = new_password.getText().toString();
                final String mStrConfirmPassword = confirm_password.getText().toString();


                if (!mStroldpassword.isEmpty() && !mStrnewPassword.isEmpty() && !mStrConfirmPassword.isEmpty()) {

                    if (mStrnewPassword.equals(mStrConfirmPassword)) {

                        System.out.println("new " + mStrnewPassword);
                        System.out.println("old " + mStrConfirmPassword);

                        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, User_Change_Password,
                                new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        Log.e("mmi", response.toString());
                                        System.out.println("check response " + response.toString());
                                        try {
                                            //getting the whole json object from the response
                                            JSONObject obj = new JSONObject(new String(response.data));
                                            String result = obj.getString("result");
                                            String msg = obj.getString("msg");

                                            if (result.equalsIgnoreCase("true")) {
                                                Toast.makeText(getApplicationContext(), " Your Password Successfully Changed", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            } else {

                                                Toast.makeText(getApplicationContext(), " Your Old Password is Wrong", Toast.LENGTH_LONG).show();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("jklm", error.toString());
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("id", id);
                                params.put("oldpassword", mStroldpassword);
                                params.put("newpassword", mStrnewPassword);
                                return params;
                            }


                        };

                        Volley.newRequestQueue(ActivityEditProfile.this).add(volleyMultipartRequest);

                    } else {
                        Toast.makeText(ActivityEditProfile.this, "Confirm Password is not matched with New Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityEditProfile.this, "all fields are requreid", Toast.LENGTH_SHORT).show();
                }


                //  Dr_ChangePassword(mStroldpassword,mStrnewPassword);
                // dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void clicklistners() {

        findViewById(R.id.profile_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadBitmap(bitmap);
            }
        });
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void uploadBitmap(final Bitmap bitmap) {

        final String mStrPhone = et_phone.getText().toString();
        final String mStrLocation = et_location.getText().toString();
        final String mStrEmail = et_email.getText().toString();
        final String mStrGender = et_gender.getText().toString();
        final String mStrBlood = et_blood.getText().toString();


        DialogUtil.show(ActivityEditProfile.this);

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.BASE_URL + "Update_UserProfile",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {

                            DialogUtil.dismiss();
                            JSONObject obj = new JSONObject(new String(response.data));

                            String mStrUpdateid = obj.getString("id");
                            String mStrUpdatefname = obj.getString("fname");
                            String mStrUpdatecontact_no = obj.getString("contact_no");
                            String mStrUpdateemail = obj.getString("email");
                            String mStrUpdateimage = obj.getString("image");
                            String mStrUpdategender = obj.getString("gender");
                            String mStrUpdateage = obj.getString("age");
                            String mStrUpdateblood_group = obj.getString("blood_group");
                            String mStrUpdatelocation = obj.getString("location");
                            String mStrUpdatecity_id = obj.getString("city_id");

                            UserData user = new UserData(
                                    obj.getInt("id"),
                                    obj.getString("fname"),
                                    obj.getString("contact_no"),
                                    obj.getString("email"),
                                    obj.getString("image"),
                                    obj.getString("gender"),
                                    obj.getString("age"),
                                    obj.getString("blood_group"),
                                    obj.getString("location"),
                                    obj.getString("city_id")

                            );





                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(ActivityEditProfile.this).userLogin(user);
                          /*  Intent intent = new Intent(ActivityEditProfile.this, ActivityHome.class);
                            startActivity(intent);
                          */
                            Toast.makeText(ActivityEditProfile.this, "Your Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.dismiss();
                        Log.e("check gallery error",error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("fname", user_firstname);
                params.put("email", mStrEmail);
                params.put("contact_no", mStrPhone);
                params.put("location", mStrLocation);
                params.put("gender", mStrGender);
                params.put("blood_group", mStrBlood);
                params.put("age", user_age);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if (bitmap!=null) {
                    params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }

                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
/**
 * adding request to queue
 */
        Volley.newRequestQueue(this).add(volleyMultipartRequest);


        //adding the request to volley
        //Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                  Bitmap  bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    // Toast.makeText(ActivityEditProfile.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profile_image.setImageBitmap(bitmap1);

                    bitmap=bitmap1;

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ActivityEditProfile.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
           Bitmap bitmap2 = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(bitmap);
            bitmap=bitmap2;


                }
    }


    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


}





