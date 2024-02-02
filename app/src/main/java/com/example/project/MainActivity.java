package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.ViewPager);
        tabLayout.setupWithViewPager(viewPager);
        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragmentAdapter.addFragment(new createActivity(),"Create ");
        fragmentAdapter.addFragment(new ExistingActivity(),"Existing ");
        fragmentAdapter.addFragment(new IssuedActivity(),"Issued ");
        fragmentAdapter.addFragment(new PendingActivity(),"Pending ");
        fragmentAdapter.addFragment(new InspectorReview(),"Inspector Review");
        viewPager.setAdapter(fragmentAdapter);
    }
}