package com.finnotive.mlm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class DashboardFragment extends Fragment implements View.OnClickListener {
    private ImageButton ewallet, iwallet, profile, joingroup;
    private String ewalletamount, iwalletamount;
    private ImageButton rechargebybank;
    private ImageButton topup;
    private ImageButton itoe;
    private ImageButton withdraw;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView name = v.findViewById(R.id.name);
        TextView invite = v.findViewById(R.id.share);
        ewallet = v.findViewById(R.id.ewallet);
        iwallet = v.findViewById(R.id.iwallet);
        profile = v.findViewById(R.id.profile);
        joingroup = v.findViewById(R.id.view);
        rechargebybank=v.findViewById(R.id.rechagebybank);
        topup=v.findViewById(R.id.topup);
        itoe=v.findViewById(R.id.itoe);
        withdraw=v.findViewById(R.id.withdraw);
        ewallet.setOnClickListener(this);
        iwallet.setOnClickListener(this);
        invite.setOnClickListener(this);
        profile.setOnClickListener(this);
        joingroup.setOnClickListener(this);
        rechargebybank.setOnClickListener(this);
        topup.setOnClickListener(this);
        withdraw.setOnClickListener(this);
        itoe.setOnClickListener(this);

        getData();
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iwallet:
                getActivity().startActivity(new Intent(getContext(), Iwallet.class).putExtra("iwallet", iwalletamount).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                Toast.makeText(getContext(), iwalletamount, Toast.LENGTH_LONG).show();
                getActivity().finish();

                Log.d("MyApp", "iwallet" + iwalletamount);
                break;
            case R.id.ewallet:
                getActivity().startActivity(new Intent(getContext(), Ewallet.class).putExtra("ewallet", ewalletamount).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                Toast.makeText(getContext(), iwalletamount, Toast.LENGTH_LONG).show();
                getActivity().finish();
                Log.d("MyApp", "ewalletamount" + ewalletamount);
                break;
            case R.id.share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Finnotive");
                share.putExtra(Intent.EXTRA_TEXT, "playstore url");

                startActivity(Intent.createChooser(share, "Share link!"));
                getActivity().finish();
                break;
            case R.id.view:
                getActivity().startActivity(new Intent(getContext(), GroupJoining.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                getActivity().finish();
                break;
            case R.id.profile:

                getActivity().startActivity(new Intent(getContext(), RenewGroup.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                getActivity().finish();
                break;
            case R.id.rechagebybank:
                getActivity().startActivity(new Intent(getContext(), rechargeByBank.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                getActivity().finish();
                break;
            case R.id.topup:
                getActivity().startActivity(new Intent(getContext(), fundTransfer.class).putExtra("activity", "dashbord").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                getActivity().finish();
                break;
            case R.id.withdraw:
                getActivity().startActivity(new Intent(getContext(), withraw.class).putExtra("amount", iwalletamount).putExtra("activity", "dashbord").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                getActivity().finish();
                break;
            case R.id.itoe:
                getActivity().startActivity(new Intent(getContext(), sendtoewallet.class).putExtra("iwallet", iwalletamount).putExtra("activity", "dashbord").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                getActivity().finish();
                break;
        }
    }

    void getData() {
        // Toast.makeText(getContext(), "please wait.....", Toast.LENGTH_SHORT).show();
        try {


            StringRequest sr = new StringRequest(Request.Method.POST, Constrains.wallet, response -> {

                //  ProgressBarUtil.dismiss();
                // Toast.makeText(getContext(), "responce " + response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("MyApp", "response " + response);
                try {
                    JSONObject jsonArray = new JSONObject(response);
                    Log.d("MyApp", jsonArray.toString());
                    JSONObject jsonObject = jsonArray.getJSONObject("fields");
                    Log.d("MyApp", jsonObject.getString("ewallet"));
                    Log.d("MyApp", jsonObject.getString("ewallet"));
                    ewalletamount = jsonObject.getString("ewallet");
                    iwalletamount = jsonObject.getString("iwallet");
                    SharedpreferenceUtility.getInstance(getContext()).putString("iwallet",iwalletamount);

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
                    params.put("user_id", SharedpreferenceUtility.getInstance(getContext()).getString("primaryNumber"));

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
