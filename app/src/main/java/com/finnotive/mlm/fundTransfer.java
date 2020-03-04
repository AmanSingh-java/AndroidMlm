package com.finnotive.mlm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class fundTransfer extends AppCompatActivity {
    private EditText recipient_id, amount;
    private RegistrationModel registrationModel;
    private Button sendmoney;
    private AlertDialog.Builder builder;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundtransfer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        activity = i.getStringExtra("activity");
        registrationModel = new RegistrationModel();
        recipient_id = findViewById(R.id.recipient_id);
        amount = findViewById(R.id.amount);
        builder = new AlertDialog.Builder(this);
        sendmoney = findViewById(R.id.sendmoney);
        sendmoney.setOnClickListener(v -> {

            if (check()) {
                getdata();
            }
        });

    }


    Boolean check() {
        registrationModel.setMobile(recipient_id.getText().toString());
        registrationModel.setPassword(amount.getText().toString());
        if (TextUtils.isEmpty(registrationModel.getMobile())) {
            recipient_id.setError("please enter recipient number");
            return false;
        }


        if (TextUtils.isEmpty(registrationModel.getPassword())) {
            amount.setError("please enter a amount");
            return false;
        }

        return true;
    }

    void getdata() {
        Toast.makeText(getApplicationContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {
            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.fundTransfer, response -> {

                Log.d("MyApp", "response " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String res = jsonObject.getString("amount");
                    if (!res.equalsIgnoreCase(null)) {
                        msg("Amount Transferred successfully", "Success");
                    } else if (jsonObject.getString("Messasg").contains("Wallet does not have sufficient amount ")) {
                        msg("Wallet does not have sufficient amount or  recipient id does not exist", "Fail");
                    }

                } catch (Exception e) {
                    Log.d("MyApp", "status" + e.toString());
                    //  Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

            }, error -> {
                Log.d("MyApp", error.toString());
                msg("Wallet does not have sufficient amount ", "Fail");

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber"));
                    params.put("recipient_id", recipient_id.getText().toString());
                    params.put("amount", amount.getText().toString());

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

    void msg(String msg, String title) {
        builder.setMessage("Thanks").setTitle("Transfered");

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
        alert.setTitle(title);
        alert.show();
    }

    @Override
    public void onBackPressed() {
        if (activity.contains("wallet")) {
            startActivity(new Intent(getApplicationContext(), Ewallet.class).putExtra("ewallet", SharedpreferenceUtility.getInstance(this).getString("ewallet")));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), Dashboad.class));
            finish();
        }
    }

}



