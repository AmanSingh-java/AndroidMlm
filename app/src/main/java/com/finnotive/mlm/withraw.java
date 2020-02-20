package com.finnotive.mlm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class withraw extends AppCompatActivity {
    private TextInputEditText enteramount, enterbankname, enteraccountno, reenteraccount, enterholdername, enterifsc;
    private RegistrationModel registrationModel;
    private AlertDialog.Builder builder;
    private String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitywithraw);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button confirm = findViewById(R.id.confirmwithdraw);
        Intent i=getIntent();
        balance=i.getStringExtra("iwallet");
        enteramount = findViewById(R.id.enteramount);
        enterbankname = findViewById(R.id.enterbankname);
        enteraccountno = findViewById(R.id.enteraccountno);
        reenteraccount = findViewById(R.id.reenteraccountno);
        enterifsc = findViewById(R.id.enterifsccode);
        builder = new AlertDialog.Builder(this);
        enterholdername = findViewById(R.id.enterholdername);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    getdata();
                }
            }
        });


    }

    Boolean check() {
        if (enteramount.getText().toString().equalsIgnoreCase(null)) {
            enteramount.setError("please enter The amount");
            enteramount.requestFocus();
            return false;

        }
        if (enterbankname.getText().toString().equalsIgnoreCase(null)) {
            enterbankname.setError("please enter the bank Name");
            enterbankname.requestFocus();
            return false;

        }
        if (enteraccountno.getText().toString().equalsIgnoreCase(null)) {
            enteraccountno.setError("please enter the bank Account");
            enteraccountno.requestFocus();
            return false;

        }
        if (!reenteraccount.getText().toString().equalsIgnoreCase(enteraccountno.getText().toString())) {
            reenteraccount.setError("please enter the bank Account");
            reenteraccount.requestFocus();
            return false;

        }
        if (enterholdername.getText().toString().equalsIgnoreCase(null)) {
            enterholdername.setError("please enter the Account Holder Name");
            enterholdername.requestFocus();
            return false;

        }
        if (enterifsc.getText().toString().equalsIgnoreCase(null)) {
            enterifsc.setError("please enter the ifsc code");
            enterifsc.requestFocus();
            return false;

        }

        return true;
    }

    void getdata() {


        Toast.makeText(getApplicationContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.withdrawalRequest, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //  ProgressBarUtil.dismiss();
                    //   Toast.makeText(getApplicationContext(), "responce " + response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("MyApp", "response " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String res = jsonObject.getString("amount");
                        if (!res.equalsIgnoreCase(null)) {
                            msg();
                        }

                    } catch (Exception e) {
                        Log.d("MyApp", "status" + e.toString());
                        //  Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  ProgressBarUtil.dismiss();
                    Log.d("MyApp", error.toString());
                    //   Log.d("MyApp", error.getMessage());

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("user_id", SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber"));

                    params.put("bank_name", enterbankname.getText().toString());
                    params.put("account_name", enterholdername.getText().toString());
                    params.put("amount", enteramount.getText().toString());
                    params.put("ifsc", enterifsc.getText().toString());
                    params.put("account_no", enteraccountno.getText().toString());


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

    void msg() {
        builder.setMessage("Thanks").setTitle("Transfered");

        //Setting message manually and performing action on button click
        builder.setMessage("You have transfered amount successfully")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getApplicationContext(), Dashboad.class));
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
        startActivity(new Intent(getApplicationContext(), Iwallet.class).putExtra("iwallet",balance));
        finish();
    }
}
