package com.finnotive.mlm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private EditText name;
    private EditText last;
    private EditText pancard;
    private EditText adharcard;
    private EditText altnumber;
    private Button update;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);
        init();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateprofile();
            }
        });
    }

    void init() {
        name = findViewById(R.id.name);
        last = findViewById(R.id.lastname);
        adharcard = findViewById(R.id.adhar);
        pancard = findViewById(R.id.pancard);
        altnumber = findViewById(R.id.altnumber);
        update = findViewById(R.id.confirm);
        Log.d("MyApp", "First Name +" + SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.firstName));
        Log.d("MyApp", "last Name +" + SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.lastName));
        Log.d("MyApp", " Name +" + SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.adharCard));
        Log.d("MyApp", "pancard Name +" + SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.pancard));

        name.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.firstName));
        last.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.lastName));
        adharcard.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.adharCard));
        pancard.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.pancard));
        altnumber.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.altNumber));


    }

    void updateprofile() {

        Toast.makeText(getApplicationContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.profile, response -> {

                //  ProgressBarUtil.dismiss();
                Toast.makeText(getApplicationContext(), "responce " + response, Toast.LENGTH_SHORT).show();
                Log.d("MyApp", "response " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // String number = jsonObject.getString("number");
                    String first_name = jsonObject.getString("first_name");
                    String last_name = jsonObject.getString("last_name");
                    String AdharCard = jsonObject.getString("AdharCard");
                    String Pancard = jsonObject.getString("Pancard");
                    String AltMobileNumber = jsonObject.getString("AltMobileNumber");
                    Log.d("MyApp", "fst" + first_name);
                    Log.d("MyApp", "AdharCard" + AdharCard);
                    msg("You have  successfully Updated profile");
                    Log.d("MyApp", "Pancard" + Pancard);
                    Log.d("MyApp", "AltMobileNumber" + AltMobileNumber);
                    SharedpreferenceUtility.getInstance(getApplicationContext()).putString(Constrains.firstName, first_name);
                    SharedpreferenceUtility.getInstance(getApplicationContext()).putString(Constrains.lastName, last_name);
                    SharedpreferenceUtility.getInstance(getApplicationContext()).putString(Constrains.adharCard, AdharCard);
                    SharedpreferenceUtility.getInstance(getApplicationContext()).putString(Constrains.pancard, Pancard);
                    SharedpreferenceUtility.getInstance(getApplicationContext()).putString(Constrains.altNumber, AltMobileNumber);

                } catch (Exception e) {
                    Log.d("MyApp", "status" + e.toString());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  ProgressBarUtil.dismiss();
                    Log.d("MyApp", error.toString());
                    msg("Unexpected response ");
//                      Log.d("MyApp", error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("user_id", SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber"));
                    params.put("first_name", name.getText().toString());
                    params.put("last_name", last.getText().toString());
                    params.put("AdharCard", adharcard.getText().toString());
                    params.put("Pancard", pancard.getText().toString());
                    params.put("AltMobileNumber", altnumber.getText().toString());
                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            sr.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(sr);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    void msg(String msg) {
        builder.setMessage("Thanks").setTitle("Transfered");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedpreferenceUtility.getInstance(getApplicationContext()).putBoolean("update",true);
                        startActivity(new Intent(getApplicationContext(), ViewProfile.class));
                        finish();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Success");
        alert.show();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), ViewProfile.class));
        finish();
    }

}
