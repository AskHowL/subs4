package com.example.subs4.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.subs4.Adapter.RecyclerViewAdapter;
import com.example.subs4.Adapter.ViewPagerAdapter;
import com.example.subs4.Model.Movie;
import com.example.subs4.R;
import com.example.subs4.ViewModel.MovieViewModel;

import java.util.ArrayList;

public class FragmentPopular extends Fragment  {

    private MovieViewModel movieViewModel;
    private RecyclerViewAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager firstViewPager;


    public FragmentPopular() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_bottom_nav, container, false);

        firstViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_id);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout_id);

        // ambil tab
        setupViewPager(firstViewPager);
        tabLayout.setupWithViewPager(firstViewPager);

        return rootView;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.AddMovieFragment(new FragmentMovie(), getString(R.string.tab_text_1));
        adapter.AddMovieFragment(new FragmentTV(), getString(R.string.tab_text_2));
        viewPager.setAdapter(adapter);
    }



}
