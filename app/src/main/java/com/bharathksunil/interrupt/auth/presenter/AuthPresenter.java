package com.bharathksunil.interrupt.auth.presenter;

import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.google.firebase.database.DataSnapshot;

/**
 * This presenter authenticates the user to use the app and also downloads the user information
 * This is to be used in the LauncherActivity
 */

public interface AuthPresenter {
    interface View extends BaseView {
        /**
         * User isn't signed in, load the sign in fragment
         */
        void loadSignInFragmentToSignInUser();

        /**
         * User isn't Registered, load the Sign Up Fragment
         */
        void loadSignUpFragmentToRegisterUser();

        /**
         * The user is disabled to use the app, so, contact admin
         */
        void loadUserNotAllowedToUserAppFragment();

        /**
         * Load the permissions fragment to get all permissions
         */
        void loadPermissionsFragment();

        /**
         * Authentication is successful, load the dashboard
         */
        void loadDashboard();
    }

    interface Repository {
        interface DataLoadedCallback {
            void onDataLoaded(DataSnapshot snapshot);

            void onDataLoadFailed();
        }

        /**
         * Checks if the user is signed in or not
         *
         * @return true, if the user is signed in
         */
        boolean isSignedIn();

        /**
         * Downloads the user data from the repository
         *
         * @param dataLoadedCallback callback to receive data asynchronously
         */
        void getUserData(DataLoadedCallback dataLoadedCallback);

        /**
         * Downloads the users Access data from the repository
         *
         * @param dataLoadedCallback callback to receive data asynchronously
         */
        void getAccessData(DataLoadedCallback dataLoadedCallback);

        /**
         * This method is used to set the user access for a participant user
         */
        void setUserAsParticipant(DataLoadedCallback dataLoadedCallback);
    }

    /**
     * To be called when the app starts.
     * Implement the startup sequence and authentication.
     */
    void appStarted(@Nullable View view);
}
