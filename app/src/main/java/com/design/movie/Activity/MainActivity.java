package com.design.movie.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.design.movie.Listener.CustomListener;
import com.design.movie.R;
import com.design.movie.Adapter.TabPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(),setCustomListener());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout=getTabLayout();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayoutListener(tabLayout,viewPager);
        //viewPager.setCurrentItem(0);

    }


    private CustomListener setCustomListener(){
        return new CustomListener() {
            @Override
            public void openDetailMovie(Bundle bundle) {
                Intent intent=new Intent(MainActivity.this,MovieDetail.class);
                intent.putExtra(getString(R.string.bundle_extra_key),bundle);
                startActivity(intent);
            }
        };

    }
    private TabLayout getTabLayout(){
        TabLayout tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setBackgroundColor(getColor(R.color.colorTabMenu));
        tabLayout.setTabTextColors(getColor(R.color.colorNormalTabText),getResources().getColor(R.color.colorSelectedTabText));
        tabLayout.addTab(tabLayout.newTab().setText("ACTION"));
        tabLayout.addTab(tabLayout.newTab().setText("ADVENTURE"));
        tabLayout.addTab(tabLayout.newTab().setText("COMEDY"));
        return tabLayout;
    }
    private void tabLayoutListener(TabLayout tabLayout,final ViewPager viewPager){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
