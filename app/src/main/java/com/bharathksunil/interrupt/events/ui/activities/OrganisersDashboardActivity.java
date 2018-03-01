package com.bharathksunil.interrupt.events.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.UnderConstructionFragment;

public class OrganisersDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity_organisers_dashboard);
        loadFragment(new UnderConstructionFragment(), UnderConstructionFragment.class.getName());
    }

    private void loadFragment(Fragment fragment, String name) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
}
