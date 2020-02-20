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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class sendtoewallet extends AppCompatActivity {
    private EditText amount;
    private Button send;
    int bal;
    private AlertDialog.Builder builder;
    private String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysendtoe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
       balance = i.getStringExtra("iwallet");
        amount = findViewById(R.id.amount);
        send = findViewById(R.id.btnconfirm);
        //bal = Integer.parseInt(amount.getText().toString());
        builder = new AlertDialog.Builder(this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().equalsIgnoreCase(null)) {
                    amount.setError("Please Enter The Amount");
                    amount.requestFocus();
                } else if (Integer.parseInt(amount.getText().toString()) <= Integer.parseInt(balance)) {
                    getData();
                } else {
                    amount.setError("Please Enter Valid Amount");
                    amount.requestFocus();
                }
            }
        });
    }

    void getData() {
        Toast.makeText(getApplicationContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.transferEwallet, response -> {

                //  ProgressBarUtil.dismiss();
                // Toast.makeText(getContext(), "responce " + response.toString(), Toast.LENGTH_SHORT).show();
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

            }, error -> {
                //  ProgressBarUtil.dismiss();
                Log.d("MyApp", error.toString());
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("user_id", SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber"));
                    params.put("amount", amount.getText().toString());
                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
            sr.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(sr);
        } catch (Exception e) {
            //   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Iwallet.class).putExtra("iwallet",balance));
        finish();
    }
}
