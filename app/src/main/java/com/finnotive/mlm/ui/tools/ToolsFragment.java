package com.finnotive.mlm.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.finnotive.mlm.R;

public class ToolsFragment extends Fragment {

   // private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // toolsViewModel =
               // ViewModelProviders.of(this).get(ToolsViewModel.class);
        View v = inflater.inflate(R.layout.fragment_tools, container, false);
        Button addnew=v.findViewById(R.id.addnew);
        Button mycommunity=v.findViewById(R.id.mycommu);
        Button referallist=v.findViewById(R.id.reflist);
        Button mybusiness=v.findViewById(R.id.mybusiness);
        mybusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),mybusiness.class));
            }
        });
        mycommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),mycommunity.class));
            }
        });
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Main2Activityaddnew.class));
            }
        });
        referallist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Referrallist.class));
            }
        });

       // final TextView textView = root.findViewById(R.id.text_tools);
       // toolsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return v;
    }
}