package com.nadeesh.letsbefriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.nadeesh.letsbefriends.fragments.TabAdapter;
import com.nadeesh.letsbefriends.fragments.fragment_tab1;
import com.nadeesh.letsbefriends.fragments.fragment_tab2;
import com.nadeesh.letsbefriends.fragments.fragment_tab3;
import com.nadeesh.letsbefriends.fragments.fragment_tab4;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabAdapter.addFrgment(new fragment_tab1(), "Find New Friend");
        tabAdapter.addFrgment(new fragment_tab2(), "Requests");
        tabAdapter.addFrgment(new fragment_tab3(), "Chat Room");
        tabAdapter.addFrgment(new fragment_tab4(), "Profile");
        viewPager.setAdapter(tabAdapter);
    }
}