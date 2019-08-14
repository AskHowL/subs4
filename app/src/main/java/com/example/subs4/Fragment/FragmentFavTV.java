package com.example.subs4.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.subs4.Adapter.RecyclerViewAdapter;
import com.example.subs4.Adapter.RecyclerViewAdapterMovie;
import com.example.subs4.Adapter.RecyclerViewAdapterTV;
import com.example.subs4.Model.Movie;
import com.example.subs4.Model.TV;
import com.example.subs4.MovieInterface;
import com.example.subs4.R;
import com.example.subs4.TVInterface;
import com.example.subs4.db.MovieHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FragmentFavTV extends Fragment implements  TVInterface {
    View v;

    private RecyclerView rvMovie;
    private static RecyclerViewAdapterTV adapter;
    private ProgressBar progressBar;

    private static final String EXTRA_STATE = "EXTRA_STATE";
    private MovieHelper movieHelper;
    private boolean shouldRefreshOnResume = false;

   

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_tv,container,false);

        rvMovie = v.findViewById(R.id.recyclertv_id);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);

        movieHelper = MovieHelper.getInstance(getActivity().getApplicationContext());
        movieHelper.open();

        progressBar = v.findViewById(R.id.progressBarTV);

        adapter = new RecyclerViewAdapterTV(getActivity());
        rvMovie.setAdapter(adapter);


        if (savedInstanceState == null) {
            new LoadNotesAsync(movieHelper, this).execute();
        } else {
            ArrayList<TV> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTV(list);
            }
        }

        return v;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTV());
    }


    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
    }

    @Override
    public void postExecute(ArrayList<TV> tv) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListTV(tv);
    }


    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<TV>> {

        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<TVInterface> weakTVInterface;

        private LoadNotesAsync(MovieHelper noteHelper, TVInterface callback) {
            weakMovieHelper = new WeakReference<>(noteHelper);
            weakTVInterface = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakTVInterface.get().preExecute();
        }

        @Override
        protected ArrayList<TV> doInBackground(Void... voids) {

            return weakMovieHelper.get().getAllTVFavorite();
        }

        @Override
        protected void onPostExecute(ArrayList<TV> notes) {
            super.onPostExecute(notes);

            weakTVInterface.get().postExecute(notes);

        }
    }

      

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check should we need to refresh the fragment
        if(shouldRefreshOnResume){
            // refresh fragment
            movieHelper = MovieHelper.getInstance(getActivity().getApplicationContext());
            movieHelper.open();
            movieHelper.getAllTVFavorite();
            adapter = new RecyclerViewAdapterTV(getActivity());
            rvMovie.setAdapter(adapter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }



}
