package com.finnotive.mlm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Iwallet extends AppCompatActivity {
    private String amount;
    private TextView txtamout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityiwallet);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button withraw = findViewById(R.id.withdraw);
        txtamout = findViewById(R.id.amount);
        Intent i = getIntent();
        amount = i.getStringExtra("iwallet");
        Toast.makeText(getApplicationContext(), amount, Toast.LENGTH_LONG).show();
        Button sendtoewallet = findViewById(R.id.sendtoe);
        txtamout.setText(amount);
        withraw.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), com.finnotive.mlm.withraw.class).putExtra("iwallet", txtamout.getText().toString()).putExtra("activity", "wallet").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        });

        sendtoewallet.setOnClickListener(v -> {
            Iwallet.this.startActivity(new Intent(Iwallet.this.getApplicationContext(), com.finnotive.mlm.sendtoewallet.class).putExtra("iwallet", txtamout.getText().toString()).putExtra("activity", "wallet").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(getApplicationContext(), Dashboad.class);
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
    }
}

