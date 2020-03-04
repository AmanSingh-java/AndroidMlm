package com.finnotive.mlm;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.finnotive.mlm.expendableadapter.CustomListViewAdapter;
import com.finnotive.mlm.expendableadapter.CustomResultListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultBoardFregment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultBoardFregment extends Fragment {

    private ListView listView;
    private LinearLayout linearLayout;
    List<RuningGruopPojo> list=new ArrayList<>();
    public ResultBoardFregment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ResultBoardFregment newInstance() {
        ResultBoardFregment fragment = new ResultBoardFregment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_result_board_fregment, container, false);
        listView=v.findViewById(R.id.list);
        linearLayout=v.findViewById(R.id.view);
        ImageButton join = v.findViewById(R.id.joingroup);
        join.setOnClickListener(v12 -> startActivity(new Intent(getContext(), GroupJoining.class)));
        getData();
        return v;
    }
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
                                runingGruopPojo.setLuckydrawdate(jsonObject.getString("luckyDrawDate"));
                                list.add(runingGruopPojo);
                                System.out.println(response.toString());
                            }
                        }
                    }
                    listView.setAdapter(new CustomResultListViewAdapter(Objects.requireNonNull(getContext()), list));


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
