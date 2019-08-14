package com.example.subs4;

import com.example.subs4.Model.Movie;
import com.example.subs4.Model.TV;

import java.util.ArrayList;

public interface TVInterface {
    void preExecute();
    void postExecute(ArrayList<TV> notes);
}
