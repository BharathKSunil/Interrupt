package com.bharathksunil.interrupt.events.ui.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.events.ui.fragments.EventInfoFragment;
import com.bharathksunil.interrupt.events.ui.fragments.EventRegistrationsViewerFragment;
import com.bharathksunil.interrupt.events.ui.fragments.NewParticipantRegistrationFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EventDashboardActivity extends AppCompatActivity implements EventInfoFragment.Interactor {

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
        loadFragment(new EventRegistrationsViewerFragment(), EventRegistrationsViewerFragment.class.getName());
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
