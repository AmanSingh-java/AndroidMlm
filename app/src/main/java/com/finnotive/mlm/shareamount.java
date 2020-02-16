package com.finnotive.mlm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class shareamount extends AppCompatActivity {
    private TextInputEditText sharedamount, totalamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareamount);
        sharedamount = findViewById(R.id.sharedamount);
        totalamount = findViewById(R.id.totalamount);
    }
}
