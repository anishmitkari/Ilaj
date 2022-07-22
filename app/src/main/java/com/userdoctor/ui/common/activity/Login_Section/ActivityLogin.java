package com.userdoctor.ui.common.activity.Login_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.activity.OTHER_Activity.ActivitySignUp;
import com.userdoctor.ui.common.model.UserData;
import com.userdoctor.ui.common.utils.Constant;
import com.userdoctor.ui.common.utils.DialogUtil;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.SharedPrefManager;
import com.userdoctor.ui.common.utils.URLs;
import com.userdoctor.ui.common.utils.VolleyMultipartRequest;
import com.userdoctor.ui.common.utils.VolleySingleton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import static com.userdoctor.ui.common.utils.URLs.User_Reset_Password;
import static com.userdoctor.ui.common.utils.Utils.isValidEmailId;

public class ActivityLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private Button btn_login;
    EditText Ed_Email, Ed_password;
    TextView tv_forgot;
    Context context;
    ProgressDialog progressDialog;
    private static final String TAG = "ActivityLogin";
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    String name, email;
    String idToken;
    String image;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NetworkUtil.isNetworkConnected(ActivityLogin.this);
        initView();
        clickListner();

        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();


        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }


    private void initView() {
        context = ActivityLogin.this;
        Ed_Email = findViewById(R.id.et_email);
        Ed_password = findViewById(R.id.et_password);
        tv_forgot = findViewById(R.id.tv_forgot);
    }


    public void clickListner() {
        findViewById(R.id.tv_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivitySignUp.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.iv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFunCallLogin();
            }
        });

        findViewById(R.id.tv_forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Forgot_Password_Dialoge();
            }
        });

findViewById(R.id.btn_google).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,RC_SIGN_IN);

    }
});
    }


    private void Forgot_Password_Dialoge() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.forgot_password_dialoge);
        final EditText Et_Email = dialog.findViewById(R.id.email);

        Button dialogButton = (Button) dialog.findViewById(R.id.update_btn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStrEmail = Et_Email.getText().toString();
                if (!mStrEmail.isEmpty()) {
                    if (isValidEmailId(Et_Email.getText().toString().trim())) {
                        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, User_Reset_Password,
                                new com.android.volley.Response.Listener<NetworkResponse>() {
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
                                                Toast.makeText(getApplicationContext(), "Your Otp is send to your E-mail Id Please Check ", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                                Intent intent = new Intent(ActivityLogin.this, ForgotPasswordActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), " " + msg, Toast.LENGTH_LONG).show();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new com.android.volley.Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("jklm", error.toString());
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                params.put("email", mStrEmail);

                                return params;
                            }


                        };

                        Volley.newRequestQueue(context).add(volleyMultipartRequest);

                    } else {
                        Toast.makeText(getApplicationContext(), "InValid Email Address.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "enter your email id", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }

    public void mFunCallLogin() {

        final String StrEmail = Ed_Email.getText().toString().trim();
        final String StrPassword = Ed_password.getText().toString().trim();

        if (TextUtils.isEmpty(StrEmail)) {
            Ed_Email.setError("Please enter username");
            Ed_Email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(StrPassword)) {
            Ed_password.setError("Please enter password");
            Ed_password.requestFocus();
            return;
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(StrEmail).matches()) {
            Ed_Email.setError("Enter a valid email");
            Ed_Email.requestFocus();
            return;
        }


        DialogUtil.show(ActivityLogin.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "UserLogin",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtil.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean res = obj.getBoolean("result");
                            if (res == true) {
                                Log.e("check login data",obj.toString());

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


                                UserData user = new UserData(
                                        data.getInt("id"),
                                        data.getString("fname"),
                                        data.getString("contact_no"),
                                        data.getString("email"),
                                        data.getString("image")
                                );

                                Log.e("check login image",mStrImage);




                                SharedPrefManager.getInstance(ActivityLogin.this).userLogin(user);
                                Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
                                startActivity(intent);
                                finish();


                            } else {
                                String msg = obj.getString("msg");
                                Toast.makeText(ActivityLogin.this, "invalide emai id or password", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("email", StrEmail);
                params.put("password", StrPassword);
                params.put("fcm_id", Constant.fcm);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            Log.e(TAG, "Login Unsuccessful. "+result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            idToken = account.getIdToken();
            name = account.getDisplayName();
            email = account.getEmail();
            image= String.valueOf(account.getPhotoUrl());
            Log.e("check uri ",image.toString());
            googleLogin(idToken,name,email);
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuthWithGoogle(credential);
        }else{
            Log.e(TAG, "Login Unsuccessful. "+result);
            System.out.println("==unsucessfull===="+result.getStatus());
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    public void googleLogin(final String mstridToken,final String mstrname,final String mstremail)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GOOGLE_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            System.out.println("checkl"+response);
                            System.out.println("checkl"+obj);
                            Log.e("check response ",response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            JSONObject obj1 = obj.getJSONObject("data");


                            System.out.println("json object"+obj1);

                            if (result.equals("true")) {


                                System.out.println("data response"+obj1);
                                Log.e("Check login ", String.valueOf(obj1));

                                int id = obj1.getInt("id");
                                String name = obj1.getString("fname");
                                String email = obj1.getString("email");
                                String image = obj1.getString("image");
                                System.out.println("checkl"+name+" "+id);

                                Log.e("Check login ",String.valueOf(id));



                                UserData user = new UserData(
                                        obj1.getInt("id"),
                                        obj1.getString("fname"),
                                        obj1.getString("email"),
                                        obj1.getString("image")
                                );

                                SharedPrefManager.getInstance(ActivityLogin.this).userLogin(user);

                            } else {
                                Toast.makeText(ActivityLogin.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println("exe "+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("===exe===== "+error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", mstremail);
                params.put("name",mstrname );
                params.put("social_id",mstridToken);
                params.put("fcm_id",Constant.fcm);


                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void firebaseAuthWithGoogle(AuthCredential credential){

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){
                            Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();

                        }else{
                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



















}
