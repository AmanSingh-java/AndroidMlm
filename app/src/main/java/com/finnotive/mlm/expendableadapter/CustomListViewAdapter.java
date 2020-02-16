package com.finnotive.mlm.expendableadapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.finnotive.mlm.R;
import com.finnotive.mlm.RuningGruopPojo;
import com.finnotive.mlm.ViewShare;

import java.util.ArrayList;
import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<RuningGruopPojo> {

private List<RuningGruopPojo> groupList;
private Context context;
    public CustomListViewAdapter(@NonNull Context context, int resource ,ArrayList arraylist) {
        super(context, resource);
    }



    public CustomListViewAdapter(@NonNull Context context, @NonNull List<RuningGruopPojo> objects) {
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
                Intent i=new Intent(context, ViewShare.class);
                i.putExtra("groupId",groupList.get(position).getGruopname());
                context.startActivity(i);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
