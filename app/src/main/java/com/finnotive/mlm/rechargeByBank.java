package com.finnotive.mlm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class rechargeByBank extends AppCompatActivity {
    private EditText tran_type, name, amount, payment_mode;
    private Button send_reachargebybank;
    private RegistrationModel registrationModel;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_by_bank);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        registrationModel = new RegistrationModel();
        builder = new AlertDialog.Builder(this);

        tran_type = findViewById(R.id.trans_type);
        name = findViewById(R.id.name_reachargebybank);
        amount = findViewById(R.id.amount_reachargebybank);
        payment_mode = findViewById(R.id.payment_mode);
        send_reachargebybank = findViewById(R.id.send_reachargebybank);
        send_reachargebybank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check()) {
                    getdata();
                }
            }
        });
    }


    Boolean check() {
        registrationModel.setMobile(tran_type.getText().toString());
        registrationModel.setMobile(name.getText().toString());
        registrationModel.setPassword(amount.getText().toString());
        registrationModel.setMobile(payment_mode.getText().toString());

        if (TextUtils.isEmpty(registrationModel.getMobile())) {
            tran_type.setError("please enter tran_type");
            return false;
        }

        if (TextUtils.isEmpty(registrationModel.getPassword())) {
            payment_mode.setError("please enter payment_mode");
            return false;
        }
        if (TextUtils.isEmpty(registrationModel.getMobile())) {
            name.setError("please enter  name");
            return false;
        }

        if (TextUtils.isEmpty(registrationModel.getPassword())) {
            amount.setError("please enter amount");
            return false;
        }


        return true;
    }


    void getdata() {


        Toast.makeText(getApplicationContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.rechargeByBank, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //  ProgressBarUtil.dismiss();
                    //   Toast.makeText(getApplicationContext(), "responce " + response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("MyApp", "response " + response);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        if(!jsonObject.getString("tran_type").isEmpty())
                        {
                            msg("You have successfully submit the bank receipt . Within 24hrs amount will be reflected in the wallet");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  ProgressBarUtil.dismiss();
                    Log.d("MyApp", error.toString());
                    msg("Due to some network issue  we are unable to process your request");
                    //   Log.d("MyApp", error.getMessage());

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("user_id", SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber"));

                    params.put("tran_type", tran_type.getText().toString());
                    params.put("name", name.getText().toString());
                    params.put("amount", amount.getText().toString());
                    params.put("payment_mode", payment_mode.getText().toString());


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
        builder.setMessage("").setTitle("Alert");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getApplicationContext(), Dashboad.class));
                        finish();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert");
        alert.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(getApplicationContext(), Dashboad.class);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
