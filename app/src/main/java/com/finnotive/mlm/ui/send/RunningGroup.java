package com.finnotive.mlm.ui.send;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.finnotive.mlm.Constrains;
import com.finnotive.mlm.GroupJoining;
import com.finnotive.mlm.R;
import com.finnotive.mlm.RuningGruopPojo;
import com.finnotive.mlm.SharedpreferenceUtility;
import com.finnotive.mlm.expendableadapter.CustomListViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RunningGroup extends Fragment {

    private SendViewModel sendViewModel;
    private ListView listView;
    private LinearLayout linearLayout;
    List<RuningGruopPojo> list=new ArrayList<>();
    public static RunningGroup newInstance() {
        RunningGroup fragment = new RunningGroup();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
         listView=root.findViewById(R.id.list);
         linearLayout=root.findViewById(R.id.view);
        ImageButton join = root.findViewById(R.id.joingroup);
        join.setOnClickListener(v12 -> startActivity(new Intent(getContext(), GroupJoining.class)));
getData();

        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void getData() {
        Toast.makeText(getContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.runninggroup, response -> {

                //  ProgressBarUtil.dismiss();
             //   Toast.makeText(getContext(), "responce " + response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("MyApp", "response " + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("MyApp", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        RuningGruopPojo runingGruopPojo = new RuningGruopPojo();
                        if (Integer.parseInt(jsonObject.getString("state")) == 0) {
                            if (Integer.parseInt(jsonObject.getString("g_status")) == 1) {
                                runingGruopPojo.setGruopname(jsonObject.getString("group_id"));
                                linearLayout.setVisibility(View.GONE);
                                runingGruopPojo.setEffectiveStartDate(jsonObject.getString("EffectiveStartDate"));
                                runingGruopPojo.setEffectiveEndDateEffectiveEndDate(jsonObject.getString("EffectiveEndDateEffectiveEndDate"));
                                Log.d("MyApp", "runing group response" + runingGruopPojo.getGruopname());
                                list.add(runingGruopPojo);
                            }
                        }
                    }
                    listView.setAdapter(new CustomListViewAdapter(getContext(), list));


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
            //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}