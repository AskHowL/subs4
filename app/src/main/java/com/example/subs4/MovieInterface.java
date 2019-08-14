package com.example.subs4;

import com.example.subs4.Model.Movie;

import java.util.ArrayList;

public interface MovieInterface {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
