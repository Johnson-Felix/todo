package com.john.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TabLayout tabView;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        else {

            setContentView(R.layout.activity_main);

            tabView = (TabLayout) findViewById(R.id.tab_view);
            viewPager = (ViewPager) findViewById(R.id.view_pager);

            initFragments(viewPager);
            tabView.setupWithViewPager(viewPager);
        }

    }

    private void initFragments(ViewPager viewPager) {
        PageViewerAdapter viewerAdapter = new PageViewerAdapter(getSupportFragmentManager());

        ArrayList<TabModel> tabs = new ArrayList<>();

        tabs.add(new TabModel("All",new AllFragment()));
        tabs.add(new TabModel("Remaining",new CompletedFragment()));
        tabs.add(new TabModel("Completed",new RemaningFragment()));

        for(int i=0; i<tabs.size(); i++){
            viewerAdapter.addFragment(tabs.get(i));
        }

        viewPager.setAdapter(viewerAdapter);

    }
}