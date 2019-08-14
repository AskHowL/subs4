package com.example.subs4.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.subs4.Adapter.RecyclerViewAdapterMovie;
import com.example.subs4.Model.Movie;
import com.example.subs4.MovieInterface;
import com.example.subs4.R;
import com.example.subs4.ViewModel.MovieViewModel;
import com.example.subs4.db.DatabaseContract;
import com.example.subs4.db.DatabaseHelper;
import com.example.subs4.db.MovieHelper;

import android.database.sqlite.SQLiteDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FragmentFavMovie extends Fragment implements   MovieInterface {
    View v;

    private RecyclerView rvMovie;
    private static RecyclerViewAdapterMovie adapter;
    private ProgressBar progressBar;

    private static final String EXTRA_STATE = "EXTRA_STATE";
    private MovieHelper movieHelper;
    private boolean shouldRefreshOnResume = false;
   

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Fragment fragmentA = new FragmentFavMovie();
        getFragmentManager().beginTransaction()
                .add(R.id.recyclermovie_id,fragmentA,"FavMovie").commit();*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_movie,container,false);
        rvMovie = v.findViewById(R.id.recyclermovie_id);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);

        movieHelper = MovieHelper.getInstance(getActivity().getApplicationContext());
        movieHelper.open();

        progressBar = v.findViewById(R.id.progressBarMovie);

        adapter = new RecyclerViewAdapterMovie(getActivity());
        rvMovie.setAdapter(adapter);


        if (savedInstanceState == null) {
            new LoadNotesAsync(movieHelper, this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovie(list);
            }
        }

        return v;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovie());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
    public void postExecute(ArrayList<Movie> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListMovie(notes);
    }


    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<MovieInterface> weakMovieInterface;

        private LoadNotesAsync(MovieHelper movieHelper, MovieInterface movieInterface) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakMovieInterface = new WeakReference<>(movieInterface);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakMovieInterface.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {

            return weakMovieHelper.get().getAllMovieFavorite();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            weakMovieInterface.get().postExecute(movies);
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
            movieHelper.getAllMovieFavorite();
            adapter = new RecyclerViewAdapterMovie(getActivity());
            rvMovie.setAdapter(adapter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }


}
