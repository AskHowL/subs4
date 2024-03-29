package com.example.subs4.Activity;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.example.subs4.Adapter.BottomNavPagerAdapter;
import com.example.subs4.Adapter.ViewPagerAdapter;
import com.example.subs4.Fragment.FragmentFav;
import com.example.subs4.Fragment.FragmentFavMovie;
import com.example.subs4.Fragment.FragmentFavTV;
import com.example.subs4.Fragment.FragmentMovie;
import com.example.subs4.Fragment.FragmentPopular;
import com.example.subs4.Fragment.FragmentTV;
import com.example.subs4.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Hide the activity toolbar
        //getSupportActionBar().hide();

        //Initializing viewPager
        viewPager = findViewById(R.id.viewpager_id);
        setupViewPager(viewPager);


        final BottomNavigationView navigation = findViewById(R.id.bottomNav_id);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigation.setSelectedItemId(R.id.pop_menu);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.fav_menu);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.pop_menu:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.fav_menu:
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }

    };


    private void setupViewPager(ViewPager viewPager) {

        BottomNavPagerAdapter adapter = new BottomNavPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentPopular());
        adapter.addFragment(new FragmentFav());
        viewPager.setAdapter(adapter);
    }


}
