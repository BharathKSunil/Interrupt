package com.bharathksunil.interrupt.admin.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.UnderConstructionFragment;
import com.bharathksunil.interrupt.admin.ui.fragments.AdminFunctionsFragment;
import com.bharathksunil.interrupt.admin.ui.fragments.FeedbackViewerFragment;
import com.bharathksunil.interrupt.auth.model.UserManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AdminDashboardActivity extends AppCompatActivity implements AdminFunctionsFragment.Interactor {

    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_dashboard);
        unbinder = ButterKnife.bind(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frame, new AdminFunctionsFragment());
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.fab_admin)
    public void onAddNewButtonPressed() {
        if (UserManager.getInstance().canUserModifyAllOrganisersData()) {
            loadNewOrganiserFragment();
        }
    }

    @Override
    public void loadUserManagementFragment() {
        loadFragment(new UnderConstructionFragment(), UnderConstructionFragment.class.getName());
    }

    @Override
    public void loadOrganisersManagementFragment() {
        loadFragment(new UnderConstructionFragment(), UnderConstructionFragment.class.getName());
    }

    @Override
    public void loadUserFeedbackFragment() {
        loadFragment(new FeedbackViewerFragment(), FeedbackViewerFragment.class.getName());
    }

    public void loadNewOrganiserFragment() {

    }

    private void loadFragment(Fragment fragment, String name) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.addToBackStack(name);
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
}
