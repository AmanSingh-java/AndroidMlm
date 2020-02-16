package com.finnotive.mlm.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.finnotive.mlm.Ewallet;
import com.finnotive.mlm.Iwallet;
import com.finnotive.mlm.R;

public class SlideshowFragment extends Fragment {

   // private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      //  slideshowViewModel =
               // ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        Button ewallet=view.findViewById(R.id.ewallet);
        Button iwallet=view.findViewById(R.id.iwallet);
        ewallet.setOnClickListener(v -> startActivity(new Intent(getContext(), Ewallet.class)));
        iwallet.setOnClickListener(v -> startActivity(new Intent(getContext(), Iwallet.class)));


       // final TextView textView = root.findViewById(R.id.text_slideshow);
       // slideshowViewModel.getText().observe(this, new Observer<String>() {
          //  @Override
          //  public void onChanged(@Nullable String s) {
              //  textView.setText(s);
           // }
       // });
        return view;
    }
}