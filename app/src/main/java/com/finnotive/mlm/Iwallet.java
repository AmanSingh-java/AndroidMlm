package com.finnotive.mlm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Iwallet extends AppCompatActivity {
private String amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityiwallet);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button withraw=findViewById(R.id.withdraw);
        TextView txtamout=findViewById(R.id.amount);
        Intent i=getIntent();
        amount=i.getStringExtra("iwallet");
        txtamout.setText(amount);
        Toast.makeText(getApplicationContext(),amount,Toast.LENGTH_LONG).show();
        Button sendtoewallet=findViewById(R.id.sendtoe);
        withraw.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), com.finnotive.mlm.withraw.class));

        });

        sendtoewallet.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), com.finnotive.mlm.sendtoewallet.class).putExtra("amount",txtamout.getText().toString())));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(getApplicationContext(),Dashboad.class);
        startActivity(a);
    }
}

