package com.bharathksunil.interrupt;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * @author Bharath on 18-02-2018.
 */

public class Interrupt extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //todo: Remove this during Release
        Picasso.with(this).setIndicatorsEnabled(true);
    }
}
