package com.bharathksunil.interrupt.events.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.ui.fragments.EventInfoFragment;
import com.bharathksunil.interrupt.events.ui.fragments.NewParticipantRegistrationFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EventDashboardActivity extends AppCompatActivity implements EventInfoFragment.Interactor{

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity_dashboard);
        unbinder = ButterKnife.bind(this);
        loadFragment(new EventInfoFragment(), null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void loadParticipantRegistrationFragment() {
        loadFragment(new NewParticipantRegistrationFragment(), NewParticipantRegistrationFragment.class.getName());
    }

    @Override
    public void loadEventRegistrationsFragment() {
        //todo: Create an Events Registration Fragment to view the registrations
    }

    private void loadFragment(Fragment fragment, @Nullable String name) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (name != null)
            transaction.addToBackStack(name);
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
}
