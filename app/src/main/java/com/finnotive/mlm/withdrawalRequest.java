package com.finnotive.mlm;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class withdrawalRequest extends AppCompatActivity {
    private EditText account_no, bankname, amount, account_name, ifsc;
    private Button send_withdrawlrequest;
    private RegistrationModel registrationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_request);
        registrationModel = new RegistrationModel();
        account_no = findViewById(R.id.account_no_withdrawlrequest);
        account_name = findViewById(R.id.accountname_withdrawlrequest);
        amount = findViewById(R.id.amount_withdrawlrequest);
        ifsc = findViewById(R.id.ifsc_withdrawlrequest);
        bankname = findViewById(R.id.bankname_withdrawlrequest);
        send_withdrawlrequest = findViewById(R.id.send_withdrawlrequest);
        send_withdrawlrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check()) {
                    getdata();
                }
            }
        });


    }


    Boolean check() {
        registrationModel.setMobile(account_no.getText().toString());
        registrationModel.setMobile(bankname.getText().toString());
        registrationModel.setPassword(account_name.getText().toString());
        registrationModel.setMobile(amount.getText().toString());
        registrationModel.setPassword(ifsc.getText().toString());
        if (TextUtils.isEmpty(registrationModel.getMobile())) {
            account_name.setError("please enter account name");
            return false;
        }

        if (TextUtils.isEmpty(registrationModel.getPassword())) {
            account_name.setError("please enter account name");
            return false;
        }
        if (TextUtils.isEmpty(registrationModel.getMobile())) {
            bankname.setError("please enter bank name");
            return false;
        }

        if (TextUtils.isEmpty(registrationModel.getPassword())) {
            amount.setError("please enter amount");
            return false;
        }
        if (TextUtils.isEmpty(registrationModel.getPassword())) {
            ifsc.setError("please enter ifsc code");
            return false;
        }


        return true;
    }


    void getdata() {


        Toast.makeText(getApplicationContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.withdrawalRequest, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //  ProgressBarUtil.dismiss();
                    //   Toast.makeText(getApplicationContext(), "responce " + response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("MyApp", "response " + response);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  ProgressBarUtil.dismiss();
                    Log.d("MyApp", error.toString());
                    //   Log.d("MyApp", error.getMessage());

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("UserId", SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber"));

                    params.put("account_name", account_name.getText().toString());
                    params.put("bank_name", bankname.getText().toString());
                    params.put("amount", amount.getText().toString());
                    params.put("ifsc", ifsc.getText().toString());
                    params.put("account_no", account_no.getText().toString());


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
}
