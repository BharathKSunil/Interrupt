package com.bharathksunil.interrupt;

import android.app.Application;

import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Bharath on 18-02-2018.
 */

public class Interrupt extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DateTimeUtils.setTimeZone("GMT+05:30");
    }
}
