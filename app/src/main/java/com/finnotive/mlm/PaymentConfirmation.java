package com.finnotive.mlm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentConfirmation extends AppCompatActivity implements View.OnClickListener {
    private String amount;
    private String share;
    private Button pay;
    private TextView txtpay;
    private TextView txtamount;
    private CheckBox aggree;
    private int statusCode;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
        Intent i = getIntent();
        txtamount = findViewById(R.id.totalamount);
        txtpay = findViewById(R.id.paymentamount);
        pay = findViewById(R.id.pay);
        builder=new AlertDialog.Builder(this);
        aggree = findViewById(R.id.accept_term);
        share = i.getStringExtra("share");
        amount = i.getStringExtra("amount");
        pay.setOnClickListener(this::onClick);
        txtpay.setText(amount);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay:
                if (aggree.isChecked()) {
                    getdata();

                } else {
                    Toast.makeText(getApplicationContext(), SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber"), Toast.LENGTH_LONG).show();
                }

        }
    }

    void getdata() {


        Toast.makeText(getApplicationContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.joingroup, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("MyApp", "response " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (statusCode != 400) {
                            String number = jsonObject.getString("NumberOfShares");
                            msg("You have Successfully purchased");
                            Toast.makeText(PaymentConfirmation.this.getApplicationContext(), "You have Successfully purchased ", Toast.LENGTH_LONG).show();

                        } else if (statusCode == 400) {
                            msg("You Have Insufficiant Balance");
                            Toast.makeText(PaymentConfirmation.this.getApplicationContext(), "You Have Insufficiant Balance ", Toast.LENGTH_LONG).show();
                        }


                    } catch (Exception e) {
                        Log.d("MyApp", "status" + e.toString());
                        Toast.makeText(PaymentConfirmation.this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        if (statusCode == 400) {
                            Toast.makeText(PaymentConfirmation.this.getApplicationContext(), "You Have Insufficiant Balance " + statusCode, Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }, error -> {
                //  ProgressBarUtil.dismiss();
                Log.d("MyApp", error.toString());
                //   Log.d("MyApp", error.getMessage());
                if (statusCode == 400) {
                    msg("You Have Insufficiant Balance");
                    Toast.makeText(getApplicationContext(), "You Have Insufficiant Balance " + statusCode, Toast.LENGTH_LONG).show();
                }
                msg("You Have Insufficiant Balance");
                Toast.makeText(getApplicationContext(), "You Have Insufficiant Balance " + statusCode, Toast.LENGTH_LONG).show();

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("UserId", SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber"));
                    params.put("TotalAmount", amount);
                    params.put("NumberOfShares", share);

                    return params;

                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    statusCode = response.statusCode;
                    if (statusCode == 400) {
                        Toast.makeText(getApplicationContext(), "You Have Insufficiant Balance " + statusCode, Toast.LENGTH_LONG).show();
                    }
                    return super.parseNetworkResponse(response);
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
        builder.setMessage("Alert").setTitle("Success");

        //Setting message manually and performing action on button click
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getApplicationContext(),Dashboad.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
finish();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert");
        alert.show();
    }

}
