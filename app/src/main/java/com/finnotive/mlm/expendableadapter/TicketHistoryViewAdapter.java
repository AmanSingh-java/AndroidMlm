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
import com.finnotive.mlm.TicketHistory;
import com.finnotive.mlm.ViewShare;

import java.util.ArrayList;
import java.util.List;

public class TicketHistoryViewAdapter extends ArrayAdapter<TicketHistory> {

private List<TicketHistory> groupList;
private Context context;
    public TicketHistoryViewAdapter(@NonNull Context context, int resource , ArrayList arraylist) {
        super(context, resource);
    }



    public TicketHistoryViewAdapter(@NonNull Context context, @NonNull List<TicketHistory> objects) {
        super(context,R.layout.custom_ticket,objects);
        this.groupList=objects;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(R.layout.custom_ticket, null, false);

        TextView ticket=view.findViewById(R.id.ticket);
        TextView status=view.findViewById(R.id.status);
        TextView issue=view.findViewById(R.id.issue);
        TextView desc=view.findViewById(R.id.desc);

        ticket.setText(groupList.get(position).getTicket_id());
        status.setText(groupList.get(position).getStatus());
        issue.setText(groupList.get(position).getIssue());
        desc.setText(groupList.get(position).getDescription());
        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
