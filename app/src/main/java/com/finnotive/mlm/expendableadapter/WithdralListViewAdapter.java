package com.finnotive.mlm.expendableadapter;


import android.content.Context;
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
import com.finnotive.mlm.WithdralHistoryPojo;

import java.util.ArrayList;
import java.util.List;

public class WithdralListViewAdapter extends ArrayAdapter<WithdralHistoryPojo> {

    private List<WithdralHistoryPojo> groupList;
    private Context context;

    public WithdralListViewAdapter(@NonNull Context context, int resource, ArrayList arraylist) {
        super(context, resource);
    }


    public WithdralListViewAdapter(@NonNull Context context, @NonNull List<WithdralHistoryPojo> objects) {
        super(context, R.layout.custom_withdral, objects);
        this.groupList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(R.layout.custom_withdral, null, false);

        TextView bank=view.findViewById(R.id.bank);
        TextView date=view.findViewById(R.id.date);
        TextView account=view.findViewById(R.id.account);
        TextView amount=view.findViewById(R.id.amount);
        TextView paymentid=view.findViewById(R.id.paymentid);
        TextView status=view.findViewById(R.id.status);
        TextView ifsc=view.findViewById(R.id.ifsc);

        bank.setText(groupList.get(position).getBank());
        date.setText(groupList.get(position).getDate());
        account.setText(groupList.get(position).getAccount());
        amount.setText(groupList.get(position).getAmount());
        paymentid.setText(groupList.get(position).getPaymentid());
        status.setText(groupList.get(position).getStatus());
        ifsc.setText(groupList.get(position).getIfsc());

        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
