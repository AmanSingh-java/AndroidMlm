package com.finnotive.mlm;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ticket_history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), helpdesk.class));
        finish();
    }
}
