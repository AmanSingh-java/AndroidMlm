package com.finnotive.mlm.expendableadapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.finnotive.mlm.R;
import com.finnotive.mlm.SharePojo;

import java.util.ArrayList;
import java.util.List;

public class ShareListViewAdapter extends ArrayAdapter<SharePojo> {

    private List<SharePojo> groupList;
    private Context context;

    public ShareListViewAdapter(@NonNull Context context, int resource, ArrayList arraylist) {
        super(context, resource);
    }


    public ShareListViewAdapter(@NonNull Context context, @NonNull List<SharePojo> objects) {
        super(context, R.layout.custom_share, objects);
        this.groupList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(R.layout.custom_share, null, false);

        TextView groupid = view.findViewById(R.id.gid);
        TextView enddate = view.findViewById(R.id.enddate);
        TextView startdate = view.findViewById(R.id.startdate);
        TextView shareid = view.findViewById(R.id.shareid);
        groupid.setText(groupList.get(position).getGroupid());
        shareid.setText(groupList.get(position).getShareid());
        startdate.setText(groupList.get(position).getStartdate());
        enddate.setText(groupList.get(position).getEnddate());

        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
