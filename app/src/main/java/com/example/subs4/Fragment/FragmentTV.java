package com.example.subs4.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.subs4.Adapter.TVRecyclerViewAdapter;
import com.example.subs4.Model.TV;
import com.example.subs4.R;
import com.example.subs4.ViewModel.TVViewModel;

import java.util.ArrayList;

public class FragmentTV extends Fragment {


    View v;
    private RecyclerView rvMovie;
    private TVViewModel tvViewModel;
    private TVRecyclerViewAdapter adapter;
    private ProgressBar progressBar;


    public FragmentTV() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvViewModel = ViewModelProviders.of(this).get(TVViewModel.class);
        tvViewModel.getListTV().observe(this, getTV);
        tvViewModel.setListTV();

        adapter = new TVRecyclerViewAdapter();
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tv,container,false);
        rvMovie = v.findViewById(R.id.recyclertv_id);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(adapter);

        progressBar = v.findViewById(R.id.progressBarTV);
        showLoading(true);
        return v;
    }

    private Observer<ArrayList<TV>> getTV = new Observer<ArrayList<TV>>() {
        @Override
        public void onChanged(ArrayList<TV> tvItems) {
            if (tvItems != null) {
                adapter.setDataTV(tvItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


}
