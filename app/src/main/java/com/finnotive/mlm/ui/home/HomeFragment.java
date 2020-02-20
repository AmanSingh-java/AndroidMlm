package com.finnotive.mlm.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.finnotive.mlm.DashboardFragment;
import com.finnotive.mlm.OpenGroup;
import com.finnotive.mlm.R;
import com.finnotive.mlm.SharedpreferenceUtility;
import com.finnotive.mlm.ui.send.RunningGroup;
import com.finnotive.mlm.ui.share.CloseGroupFregment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        SharedpreferenceUtility.getInstance(getContext()).putBoolean("isLogin", true);
        initComponent(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initComponent(View v) {
        ViewPager view_pager = v.findViewById(R.id.view_pager);
        setupViewPager(view_pager);
        TabLayout tab_layout = v.findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager());
        adapter.addFragment(DashboardFragment.newInstance(), "Home");
        adapter.addFragment(OpenGroup.newInstance(), "Open Group");
        adapter.addFragment(RunningGroup.newInstance(), "Active Group");
        adapter.addFragment(CloseGroupFregment.newInstance(), "Close Group");
        adapter.addFragment(RunningGroup.newInstance(), "Result Board");
        viewPager.setAdapter(adapter);
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}