package com.example.subs4.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.subs4.Adapter.RecyclerViewAdapter;
import com.example.subs4.Adapter.ViewPagerAdapter;
import com.example.subs4.R;
import com.example.subs4.ViewModel.MovieViewModel;

public class FragmentFav extends Fragment  {

    private MovieViewModel movieViewModel;
    private RecyclerViewAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager firstViewPager;


    public FragmentFav() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_bottom_nav, container, false);

        firstViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_id);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout_id);
        tabLayout.setupWithViewPager(firstViewPager);

        setupViewPager(firstViewPager);
        return rootView;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.AddMovieFragment(new FragmentFavMovie(), "Fav Mov");
        adapter.AddMovieFragment(new FragmentFavTV(), "Fav TV" );
        viewPager.setAdapter(adapter);
    }



}
