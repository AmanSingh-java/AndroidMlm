package com.finnotive.mlm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dashboad extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboad);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Dashboard");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        auth();
        NavigationUI.setupWithNavController(navigationView, navController);
        Log.d("MyApp", SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.token));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboad, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), setting.class));
                return true;
            case R.id.helpdesk:
                startActivity(new Intent(getApplicationContext(), helpdesk.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(), ViewProfile.class));
                break;
            case R.id.logout:
                SharedpreferenceUtility.getInstance(getApplicationContext()).putBoolean("isLogin", false);
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
            case R.id.payhistory:
                startActivity(new Intent(getApplicationContext(), PaymentHistory.class));
                break;
            case R.id.withhistory:
                startActivity(new Intent(getApplicationContext(), WithrawHistory.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void auth() {

        try {
            Intent i = getIntent();
            no = i.getStringExtra("mobile");
            if (no.contains(null)) {
                no = SharedpreferenceUtility.getInstance(getApplicationContext()).getString("primaryNumber");

            } else if (no.isEmpty()) {
                no = SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.userId);

            }
        }
        catch (Exception e){
           // Toast.makeText(getApplicationContext(),"Network is too week",Toast.LENGTH_LONG).show();
            no = SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.userId);

        }

        try {


            StringRequest sr = new StringRequest(Request.Method.GET, Constrains.userdetails + no + "/", (String response) -> {

                //  ProgressBarUtil.dismiss();
                //  Toast.makeText(getApplicationContext(), "responce " + response, Toast.LENGTH_SHORT).show();
                Log.d("MyApp", "response " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String emailid = jsonObject.getString("email");
                    String number = jsonObject.getString("number");
                    String refercode = jsonObject.getString("refercode");
                    SharedpreferenceUtility.getInstance(getApplicationContext()).putString(Constrains.email, emailid);
                    SharedpreferenceUtility.getInstance(getApplicationContext()).putString(Constrains.userId, number);
                    SharedpreferenceUtility.getInstance(getApplicationContext()).putString(Constrains.refercode, refercode);


                } catch (Exception e) {
                    Log.d("MyApp", "status" + e.toString());
                    //Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

            }, error -> {
                //  ProgressBarUtil.dismiss();
                Log.d("MyApp", error.toString());
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,getActivity().MODE_PRIVATE);
                    params.put("Authorization", "Token " + SharedpreferenceUtility.getInstance(getApplicationContext()).getString(Constrains.token));

                    return params;
                }


            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            sr.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(sr);
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


    @Override
    protected void onStop() {
        finish();
        super.onStop();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
