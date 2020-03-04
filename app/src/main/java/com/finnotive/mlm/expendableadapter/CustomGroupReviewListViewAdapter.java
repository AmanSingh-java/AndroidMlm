package com.finnotive.mlm.expendableadapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.finnotive.mlm.R;
import com.finnotive.mlm.RuningGruopPojo;
import com.finnotive.mlm.ViewShare;

import java.util.ArrayList;
import java.util.List;

public class CustomGroupReviewListViewAdapter extends ArrayAdapter<RuningGruopPojo> {

private List<RuningGruopPojo> groupList;

private Context context;
    public CustomGroupReviewListViewAdapter(@NonNull Context context, int resource , ArrayList arraylist) {
        super(context, resource);
    }



    public CustomGroupReviewListViewAdapter(@NonNull Context context, @NonNull List<RuningGruopPojo> objects) {
        super(context,R.layout.list_group,objects);
        this.groupList=objects;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(R.layout.list_group, null, false);

        TextView groupid=view.findViewById(R.id.groupid);
        TextView enddate=view.findViewById(R.id.enddate);
        TextView startdate=view.findViewById(R.id.startdate);

        groupid.setText(groupList.get(position).getGruopname());
        enddate.setText(groupList.get(position).getEffectiveEndDateEffectiveEndDate());
        Log.d("MyApp","listview"+groupList.get(position).getGruopname());
        startdate.setText(groupList.get(position).getEffectiveStartDate());
        LinearLayout linearLayout=view.findViewById(R.id.viewshare);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // msg();
                Toast.makeText(getContext(),"Wait For Next Renew Date ",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    void msg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);;
        //Setting message manually and performing action on button click
        builder.setMessage("There is no group to renew")
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
