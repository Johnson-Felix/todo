package com.john.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PageViewerAdapter extends FragmentPagerAdapter {

    ArrayList<TabModel> tabs = new ArrayList<>();

    public PageViewerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment(TabModel tab){
        tabs.add(tab);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).fragment;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).title;
    }
}
