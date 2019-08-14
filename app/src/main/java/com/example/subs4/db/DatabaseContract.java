package com.example.subs4.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract() {
    }

    static final class MovieColumn implements BaseColumns {
        static final String TABLE_MOVIE = "favmovie";
        static final String ID = "id";
        static final String TITLE = "title";
        static final String DESCRIPTION = "description";
        static final String PIC = "pic";
        static final String IS_MOVIE = "is_movie";
    }
}
