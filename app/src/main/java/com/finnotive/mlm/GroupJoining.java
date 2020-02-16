package com.finnotive.mlm;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GroupJoining extends AppCompatActivity implements View.OnClickListener {
    private Button confirmpayment;
    private TextView date;
    private EditText share;
    private TextView amount;
    private String value;
    private TextView groupid;
    private TextView enddate;
    private TextView startdate;
    int a;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_joining);
        share = findViewById(R.id.no_of_share);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        date = findViewById(R.id.efdate);
        amount = findViewById(R.id.amount);
        amount.setImeOptions(EditorInfo.IME_ACTION_DONE);
        groupid = findViewById(R.id.groupid);
        enddate = findViewById(R.id.enddate);
        startdate = findViewById(R.id.efdate);
        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                value = share.getText().toString();
                int a = 1000 * Integer.parseInt(value);
                amount.setText(String.valueOf(a));
            }
        });
        confirmpayment = findViewById(R.id.confirm);


        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

                value = share.getText().toString();
                try {


                    if (value.equals(null)) {

                    } else {
                        a = 1000 * Integer.parseInt(value);
                        amount.setText(String.valueOf(a));
                    }
                } catch (Exception e) {

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };

        share.addTextChangedListener(inputTextWatcher);

        String date1 = String.valueOf(new Date().getDate());
        confirmpayment.setOnClickListener(this::onClick);
        date.setText("Date " + date1 + " /" + new Date().getMonth() + "  dd/mm");

        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                value = share.getText().toString();
                a = 1000 * Integer.parseInt(value);
                amount.setText(String.valueOf(a));
                Intent i = new Intent(getApplicationContext(), PaymentConfirmation.class);
                i.putExtra("share", share.getText().toString());
                i.putExtra("amount", String.valueOf(a));
                startActivity(i);


        }
    }

    void getData() {
       // Toast.makeText(getApplicationContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.runninggroup, response -> {

                //  ProgressBarUtil.dismiss();
            //    Toast.makeText(getApplicationContext(), "responce " + response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("MyApp", "response " + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("MyApp", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        RuningGruopPojo runingGruopPojo = new RuningGruopPojo();
                        if (Integer.parseInt(jsonObject.getString("state")) == 1) {
                            if (Integer.parseInt(jsonObject.getString("g_status")) == 0) {
                                groupid.setText(jsonObject.getString("group_id"));
                                startdate.setText(jsonObject.getString("EffectiveStartDate"));
                                enddate.setText(jsonObject.getString("EffectiveEndDateEffectiveEndDate"));
                            }
                        }
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
                    params.put("UserId", SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber"));

                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
            sr.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(sr);
        } catch (Exception e) {
          //  Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent a = new Intent(getApplicationContext(),Dashboad.class);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);
//    }
}
