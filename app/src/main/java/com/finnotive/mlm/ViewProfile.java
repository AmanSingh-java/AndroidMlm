package com.finnotive.mlm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class ViewProfile extends AppCompatActivity {
    private TextView name;
    private TextView last;
    private TextView pancard;
    private TextView adharcard;
    private TextView altnumber;
    private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                finish();
            }
        });
    }

    void init(){
        name=findViewById(R.id.name);
        last=findViewById(R.id.lastname);
        adharcard=findViewById(R.id.adhar);
        pancard=findViewById(R.id.pancard);
        altnumber=findViewById(R.id.altnumber);
        update=findViewById(R.id.confirm);
        Log.d("MyApp","First Name +"+SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.firstName));
        Log.d("MyApp","last Name +"+SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.lastName));
        Log.d("MyApp"," Name +"+SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.adharCard));
        Log.d("MyApp","pancard Name +"+SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.pancard));
        name.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.firstName));
        last.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.lastName));
        adharcard.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.adharCard));
        pancard.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.pancard));
        altnumber.setText(SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.altNumber));


    }


}
