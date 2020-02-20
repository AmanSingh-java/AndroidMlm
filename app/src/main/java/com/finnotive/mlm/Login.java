package com.finnotive.mlm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class Login extends AppCompatActivity implements View.OnClickListener, Constrains {
    private String Tag = "Login";
    private RegistrationModel registrationModel;
    private EditText mobile, password;
    private Button _login;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(Tag, "Login Activity Started");
        Boolean login = SharedpreferenceUtility.getInstance(getApplicationContext()).getBoolean("isLogin");
        if (login) {

            Intent i = new Intent(getApplicationContext(), Dashboad.class);
            i.setAction(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        _login = findViewById(R.id.login);
        TextView _signup = findViewById(R.id.sign_up);
        registrationModel = new RegistrationModel();
        password = findViewById(R.id.loginpassword);
        builder = new AlertDialog.Builder(this);
        mobile = findViewById(R.id.usernamelogin);

        try {
            _login.setOnClickListener(this);
            _signup.setOnClickListener(this);
        }
        catch (Exception e){

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up:
                startActivity(new Intent(this, Signup.class));
                Log.d(Tag, "Login Button Clicked");
                finish();
                break;
            case R.id.login:
                if (check()) {
                    Login();
                    SharedpreferenceUtility.getInstance(Login.this.getApplicationContext()).putString("primaryNumber", registrationModel.getMobile());

                }
                // startActivity(new Intent(this,Dashboad.class));
        }
    }

    Boolean check() {
        registrationModel.setMobile(mobile.getText().toString());
        registrationModel.setPassword(password.getText().toString());
        if (TextUtils.isEmpty(registrationModel.getMobile())) {
            mobile.setError("please enter mobile number");
            return false;
        }
        if (registrationModel.getMobile().length() != 10) {
            mobile.setError("mobile number must be 10 digit long");
            return false;
        }

        if (TextUtils.isEmpty(registrationModel.getPassword())) {
            password.setError("please enter a password");
            return false;
        }
        if (registrationModel.getPassword().length() <= 6) {
            password.setError("Password must be of 8 digit");
            return false;
        }

        return true;
    }

    void Login() {
        //  Toast.makeText(this.getApplication(), "please wait.....", Toast.LENGTH_SHORT).show();

        ProgressBarUtil.show(Login.this, "Wait....", false);
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.login, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    ProgressBarUtil.dismiss();
                    //    Toast.makeText(Login.this, "responce " + response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("MyApp", "response " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String token = jsonObject.getString("token");
                        if (!response.contains("token")) {
                            msg("Mobile Number or Password does not exist. Do you want to retry ?");

                        } else {

                            SharedpreferenceUtility.getInstance(Login.this.getApplicationContext()).putString(Constrains.token, token);
                            SharedpreferenceUtility.getInstance(Login.this.getApplicationContext()).putString(Constrains.userId, registrationModel.getMobile());
                            //  Toast.makeText(Login.this.getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                            SharedpreferenceUtility.getInstance(Login.this.getApplicationContext()).putString("primaryNumber", registrationModel.getMobile());

                            Intent i = new Intent(Login.this.getApplicationContext(), Dashboad.class);
                            i.putExtra("mobile", registrationModel.getMobile());
                            i.setAction(Intent.ACTION_MAIN);
                            i.addCategory(Intent.CATEGORY_HOME);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }

                    } catch (Exception e) {
                        Log.d("MyApp", "status" + e.toString());
                        msg("An Exception Occur");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ProgressBarUtil.dismiss();
                    msg("internal server error");
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            Log.d("MyApp", res);
                            JSONObject obj = new JSONObject(res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                        }
                    }
                    Log.d("MyApp", "error " + error.toString());
                    Log.d("MyApp", "error" + error.getLocalizedMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("username", registrationModel.getMobile());
                    Log.d("MyApp", registrationModel.getMobile() + " " + registrationModel.getPassword());

                    params.put("password", registrationModel.getPassword());
                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            sr.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(sr);
        } catch (Exception e) {
            Log.d("MyApp", "error" + e.getLocalizedMessage());
        }
    }

    void msg(String msg) {
        builder.setMessage(msg).setTitle("login fail");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Login Fails");
        alert.show();
    }

}
