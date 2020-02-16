package com.finnotive.mlm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity implements View.OnClickListener, Constrains {
    private TextInputEditText edt_email, edt_mobile, edt_password, edt_conpassword, edt_reffercode;
    private CheckBox acceptterm;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button signup;
    private RegistrationModel registrationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        registrationModel = new RegistrationModel();

        edt_email = findViewById(R.id.email);
        edt_mobile = findViewById(R.id.mobile);
        edt_password = findViewById(R.id.password);
        edt_conpassword = findViewById(R.id.reenterpassword);
        edt_reffercode = findViewById(R.id.reffercode);
        acceptterm = findViewById(R.id.accept_term);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(this);
    }

    Boolean check() {

        registrationModel.setEmail(edt_email.getText().toString());
        registrationModel.setMobile(edt_mobile.getText().toString());
        registrationModel.setPassword(edt_password.getText().toString());
        registrationModel.setConpassword(edt_conpassword.getText().toString());
        registrationModel.setReffercode(edt_reffercode.getText().toString());

        if (TextUtils.isEmpty(registrationModel.getEmail())) {
            edt_email.setError("please enter an email address");
            edt_email.requestFocus();
            return false;
        }
        if (!registrationModel.getEmail().matches(emailPattern)) {
            edt_email.setError("please enter a valid email address");
            edt_email.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(registrationModel.getMobile())) {
            edt_mobile.setError("please enter mobile number");
            edt_mobile.requestFocus();
            return false;
        }
        if (registrationModel.getMobile().length() != 10) {
            edt_mobile.setError("mobile number must be 10 digit long");
            edt_mobile.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(registrationModel.getReffercode())) {
            edt_reffercode.setError("please enter a refercode");
            edt_reffercode.requestFocus();
            return false;
        }
        if (registrationModel.getReffercode().length() == 5) {
            edt_reffercode.setError("refercode must be of 6 digit");
            edt_reffercode.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(registrationModel.getPassword())) {
            edt_password.setError("please enter a password");
            edt_password.requestFocus();
            return false;
        }
        if (registrationModel.getPassword().length() <= 6) {
            edt_password.setError("Password must be of 8 digit");
            edt_password.requestFocus();
            return false;
        }

        if (!registrationModel.getPassword().equals(registrationModel.getConpassword())) {
            edt_conpassword.setError("confirm password does not match");
            edt_conpassword.requestFocus();
            return false;
        }
        if (!acceptterm.isChecked()) {
            acceptterm.setError("accept term and conditions");
            return false;
        }

        return true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                if (check()) {
                   // Toast.makeText(getApplicationContext(), "validating", Toast.LENGTH_LONG).show();
                    Register();
                }
        }

    }

    void Register() {
       // Toast.makeText(getApplicationContext(), "please wait.....", Toast.LENGTH_SHORT).show();

        ProgressBarUtil.show(Signup.this, "Wait....", false);
        try {
            //  Toast.makeText(getApplicationContext(), "processing.....", Toast.LENGTH_SHORT).show();


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.register, response -> {
                //  Toast.makeText(getApplicationContext(), "getting data.....", Toast.LENGTH_SHORT).show();
                Log.d("MyApp", "response " + response);
                ProgressBarUtil.dismiss();
                try {
                    String refercoderes = "";
                    response.trim().replace("[", "");
                    response.trim().replace("]", "");
                    JSONObject jsonObject = new JSONObject(response.trim());
                    Log.d("MyApp", "response " + response);
                    //  String status = jsonObject.getString("status");




                    if (response.contains("email")) {
                        String emailid = jsonObject.getString("email");
                        if (emailid.contains("my user with this email address already exists")) {
                            message("my user with this email address already exists");
                            Log.d("MyApp", "email is not exist");

                        } else {
                            SharedpreferenceUtility.getInstance(Signup.this.getApplicationContext()).putString(Constrains.email, emailid);

                        }
                    }
                    if (response.contains("number")) {
                        String number = jsonObject.getString("number");
                        if (number.contains("my user with this number already exists")) {
                            message("this number already exists");

                        } else {
                            SharedpreferenceUtility.getInstance(Signup.this.getApplicationContext()).putString(Constrains.username, number);

                        }
                    }
                    refercoderes = jsonObject.getString("refercode");
                    if (response.contains("refercode")) {

                        if (response.contains("Refercode")) {
                            String refer = jsonObject.getString("Refercode");
                            message("Refercode is not exist");
                            if (refer.contains("Refercode is not exist")) {
                                message("Refercode is not exist");
                                Log.d("MyApp", "Refercode is not exist");
                            }
                        }
                        if (refercoderes.contains("Refercode is not exist")) {
                            message("Refercode is not exist");
                            Log.d("MyApp", "Refercode   54 is not exist");
                        } else {
                            SharedpreferenceUtility.getInstance(Signup.this.getApplicationContext()).putString(Constrains.refercode, refercoderes);
                        }
                    }


                    Signup.this.startActivity(new Intent(Signup.this.getApplicationContext(), Login.class));
                    finish();

                } catch (Exception e) {
                    Log.d("MyApp", "status" + e.toString());
                    Log.d("MyApp", e.getMessage());
                    message("Refercode is not exist");
                  //  Toast.makeText(Signup.this.getApplicationContext(), "refercode does not exist", Toast.LENGTH_LONG).show();

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ProgressBarUtil.dismiss();
                    //   Log.d("MyApp",error.getMessage());


                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            Log.d("MyApp", "erro responce " + res);
                            //   JSONObject obj = new JSONObject(res);
                            res.trim().replace("[", "");
                            res.trim().replace("]", "");
                            JSONObject jsonObject = new JSONObject(res.trim());
                            Log.d("MyApp", "new Error Responce" + res);
                            //  String status = jsonObject.getString("status");
                            if (res.contains("email")) {
                                String emailid = jsonObject.getString("email");
                                if (emailid.contains("my user with this email address already exists")) {
                                    message("my user with this email address already exists");
                                    Log.d("MyApp", "email is not exist");
                                }
                            }
                            if (res.contains("number")) {
                                String number = jsonObject.getString("number");
                                if (number.contains("my user with this number already exists")) {
                                    message("this number already exists");
                                }
                            }
                            if (res.contains("Refercode")) {
                                String refercode = jsonObject.getString("Refercode");

                                if (refercode.contains("Refercode is not exist")) {
                                    message("Refercode is not exist");
                                    Log.d("MyApp", "Refercode is not exist");
                                }
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        } catch (UnsupportedEncodingException ex) {
                            ex.printStackTrace();
                        }
                    }
                }


            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("email", edt_email.getText().toString());
                    params.put("number", edt_mobile.getText().toString());
                    params.put("password", edt_password.getText().toString());
                    params.put("refercode", edt_reffercode.getText().toString());

                    return params;

                }


            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            sr.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(sr);
        } catch (Exception e) {
           // Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            Log.d("MyApp", "error m " + e.getLocalizedMessage());
        }
    }

    void message(String msg) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        builder.setMessage("Mobile Number or Password does not exist").setTitle("login fail");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(true)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Signup Fails");
        alert.show();
    }


}
