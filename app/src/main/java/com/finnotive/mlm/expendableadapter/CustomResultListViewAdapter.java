package com.finnotive.mlm.expendableadapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.finnotive.mlm.DemoWinner;
import com.finnotive.mlm.R;
import com.finnotive.mlm.RuningGruopPojo;
import com.finnotive.mlm.ViewShare;
import com.finnotive.mlm.Winner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CustomResultListViewAdapter extends ArrayAdapter<RuningGruopPojo> {

    private List<RuningGruopPojo> groupList;
    private Context context;
    private AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    private int num,Min,Hr24,sec;
    private CountDownTimer ct;
    int counter=0;

    public CustomResultListViewAdapter(@NonNull Context context, int resource, ArrayList arraylist) {
        super(context, resource);
    }


    public CustomResultListViewAdapter(@NonNull Context context, @NonNull List<RuningGruopPojo> objects) {
        super(context, R.layout.list_groupresult, objects);
        this.groupList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(R.layout.list_groupresult, null, false);

        TextView groupid = view.findViewById(R.id.groupid);
//        TextView enddate = view.findViewById(R.id.enddate);
//        TextView startdate = view.findViewById(R.id.startdate);
        TextView days = view.findViewById(R.id.days);
        TextView hours = view.findViewById(R.id.hours);
        TextView min = view.findViewById(R.id.min);
        TextView second=view.findViewById(R.id.sec);
        groupid.setText(groupList.get(position).getGruopname());
     //   enddate.setText(groupList.get(position).getEffectiveEndDateEffectiveEndDate());
        Log.d("MyApp", "listview" + groupList.get(position).getGruopname());
      //  startdate.setText(groupList.get(position).getEffectiveStartDate());
        LinearLayout linearLayout = view.findViewById(R.id.viewshare);
        try {
            Calendar c = Calendar.getInstance();

             Hr24 = c.get(Calendar.HOUR_OF_DAY);
             Min = c.get(Calendar.MINUTE);
            hours.setText(String.valueOf(24-Hr24));
            min.setText(String.valueOf(60-Min));
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            System.out.println(date);
            Date start = new SimpleDateFormat("yyyy-mm-dd").parse(date.toString());

            Log.d("MyApp","date start"+start);
            Date end = new SimpleDateFormat("yyyy-mm-dd").parse(groupList.get(position).getLuckydrawdate());
             num = getDifferenceDays(start, end);
             if(num>=0) {
                 Log.d("MyApp", "date end" + end);
                 days.setText(String.valueOf(getDifferenceDays(start, end) - 1));
                 Timer timer = new Timer();
                 hours.setText(String.valueOf(23 - Hr24) + " Hour");

              ct=   new CountDownTimer(60000, 1000) {

                     @RequiresApi(api = Build.VERSION_CODES.N)
                     public void onTick(long millisUntilFinished) {
                         // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                         Calendar c = Calendar.getInstance();

                         counter++;
                         second.setText(String.valueOf(60-c.get(Calendar.SECOND)));
                         if (counter == 60) {
                             counter = 0;
                         }
                     }

                     public void onFinish() {
                         Calendar c = Calendar.getInstance();

                         min.setText(String.valueOf(60 - Min));
                         second.setText(String.valueOf(c.get(Calendar.SECOND)));
                         ct.cancel();
                     }
                 }.start();

                 ct.cancel();
//Set the schedule function
//                 timer.scheduleAtFixedRate(new TimerTask() {
//                                               @Override
//                                               public void run() {
//                                                   Calendar c = Calendar.getInstance();
//
//                                                   sec = c.get(Calendar.SECOND);
//
//                                                   min.setText(String.valueOf(60 - Min) + " Min");
//                                                   second.setText(String.valueOf(60-sec));
//                                               }
//                                           },
//                         0, 1000);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.d("MyApp", "Date Error" + e.getMessage());
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num == 0) {
//                    if (Hr24 == 0) {
//                        if (Min == 0) {
                    ct.cancel();
                                context.startActivity(new Intent(getContext(), Winner.class).putExtra("groupid",groupList.get(position).getGruopname()));

//                        }
//                    }
                }
                else {
                    ct.cancel();
                    Toast.makeText(getContext(),"Wait For The Lucky Draw",Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(getContext(), DemoWinner.class));


                }
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


    public int getDifferenceDays(Date d1, Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
        daysdiff = (int) diffDays;
        return daysdiff;
    }
    void msg() {
        //Setting message manually and performing action on button click
        builder.setMessage("Please wait untill Lucky-draw released")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog .cancel();



                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert");
        alert.show();
    }
}
