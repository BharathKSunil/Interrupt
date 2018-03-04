package com.bharathksunil.interrupt.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bharathksunil.interrupt.auth.ui.fragments.SignInFragment;
import com.bharathksunil.interrupt.auth.ui.fragments.SplashScreenFragment;
import com.bharathksunil.interrupt.dashboard.ui.MainDashboardActivity;

import com.bharathksunil.interrupt.R;

import com.bharathksunil.interrupt.auth.ui.fragments.SignUpFragment;

public class LauncherActivity extends AppCompatActivity implements
        SplashScreenFragment.Interactor, SignInFragment.Interactor, SignUpFragment.Interactor {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity_launcher);
        loadSplashScreenFragment();
    }

    public void loadSplashScreenFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new SplashScreenFragment(), SplashScreenFragment.TAG);
        fragmentTransaction.commit();
    }

    /**
     * Load the SignInFragment here
     */
    @Override
    public void loadSignInFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new SignInFragment(), SignInFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void onUserSignedUp() {
        loadSplashScreenFragment();
    }

    /**
     * Load the SignUpFragment
     */
    @Override
    public void loadSignUpFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(SignUpFragment.TAG);
        fragmentTransaction.replace(R.id.frame, new SignUpFragment(), SignUpFragment.TAG);
        fragmentTransaction.commit();
    }

    /**
     * Load the permissions Fragment
     */
    @Override
    public void loadPermissionsFragment() {
        //UnImplemented
    }

    /**
     * Called when the authentication is over
     */
    @Override
    public void launchDashboardActivity() {
        startActivity(new Intent(this, MainDashboardActivity.class));
        finish();
    }

    /**
     * The user is disabled to use the app, load the fragment to show the same
     */
    @Override
    public void loadForbiddenUserFragment() {
        //todo: Create a fragment explaining why the user is restricted access
    }

    @Override
    public void userSignedIn() {
        loadSplashScreenFragment();
    }
}
