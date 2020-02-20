package com.finnotive.mlm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OpenGroup extends Fragment {

    public static OpenGroup newInstance() {
        return new OpenGroup();
    }

    private TextView groupid;
    private TextView opengroupresponse;
    private TextView enddate;
    private TextView startdate;
    private AlertDialog.Builder builder;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.open_group_fragment, container, false);
        ImageButton join = v.findViewById(R.id.joingroup);
        builder = new AlertDialog.Builder(getContext());
        ImageButton opengroupshares = v.findViewById(R.id.viewshare);

        groupid = v.findViewById(R.id.groupid);
        opengroupresponse = v.findViewById(R.id.opengroupresponse);

        opengroupshares.setOnClickListener(v13 -> {
            if (Integer.parseInt(groupid.getText().toString()) == 0) {
                msg();
            } else {
                Intent i = new Intent(getContext(), ViewShare.class);
                i.putExtra("groupId", groupid.getText().toString());
                getActivity().startActivity(i);
            }
        });
        startdate = v.findViewById(R.id.efdate);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GroupJoining.class));
            }
        });
        getData();
        return v;

    }


    void getData() {
        //Toast.makeText(getContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.runninggroup, response -> {

                //  ProgressBarUtil.dismiss();
                // Toast.makeText(getContext(), "responce " + response.toString(), Toast.LENGTH_SHORT).show();
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
                                opengroupresponse.setText("Open Group Id");
                                startdate.setText(date(jsonObject.getString("EffectiveStartDate")));
                                enddate.setText(date(jsonObject.getString("EffectiveEndDateEffectiveEndDate")));


                            }
                        }
                    }


                } catch (Exception e) {
                    Log.d("MyApp", "status" + e.toString());
                    //  Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

            }, error -> {
                //  ProgressBarUtil.dismiss();
                Log.d("MyApp", error.toString());
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("UserId", SharedpreferenceUtility.getInstance(getContext()).getString("primaryNumber"));

                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
            sr.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(sr);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    String date(String str){
        String[] arrOfStr = str.split("-");
        String a="";
        for(int i=0;i<3;i++){
            a=a+arrOfStr[2-i]+"-";
        }
        System.out.println(a);
        String result = null;
        if ((a != null) && (a.length() > 0)) {
            result = a.substring(0, str.length() );
        }
        return result;


    }

    void msg() {
        //Setting message manually and performing action on button click
        builder.setMessage("You have not joined any group yet")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert");
        alert.show();
    }
}