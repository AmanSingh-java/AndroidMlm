package com.finnotive.mlm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.finnotive.mlm.expendableadapter.CustomListViewAdapter;
import com.finnotive.mlm.expendableadapter.ShareListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewShare extends AppCompatActivity {
private String groupid;
    private ListView listView;
    List<SharePojo> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_share);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        groupid=i.getStringExtra("groupId");
        listView=findViewById(R.id.listshare);
        getShare();

    }
    void getShare(){

        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.viewshare, response -> {

                //  ProgressBarUtil.dismiss();
              //  Toast.makeText(getApplicationContext(), "responce " + response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("MyApp", "response " + response);
                try {
                    JSONObject json=new JSONObject(response);
                    JSONArray jsonArray = json.getJSONArray("ShareGroupTable");
                    Log.d("MyApp", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        SharePojo runingGruopPojo = new SharePojo();

                                runingGruopPojo.setGroupid(jsonObject.getString("group_id_id"));
                                runingGruopPojo.setStartdate(jsonObject.getString("created_date").substring(0,10));
                                runingGruopPojo.setEnddate(jsonObject.getString("s_status"));
                               runingGruopPojo.setShareid(jsonObject.getString("share_id"));
                               Log.d("MyApp","share view "+runingGruopPojo);
                                list.add(runingGruopPojo);


                    }
                    listView.setAdapter(new ShareListViewAdapter(getApplicationContext(), list));


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
                    params.put("GroupId", groupid);

                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
            sr.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(sr);
        } catch (Exception e) {
           // Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(getApplicationContext(),Dashboad.class);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}