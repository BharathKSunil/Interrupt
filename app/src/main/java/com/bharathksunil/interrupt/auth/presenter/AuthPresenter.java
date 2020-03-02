package com.bharathksunil.interrupt.auth.presenter;

import androidx.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.google.firebase.database.DataSnapshot;

/**
 * This presenter authenticates the user to use the app and also downloads the user information
 * This is to be used in the LauncherActivity
 */

public interface AuthPresenter {
    /**
     * The View wanting to use this presenter must implement this methods
     */
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

    /**
     * The repository you want to use must implement this interface
     */
    interface Repository {
        /**
         * Interface callbacks when fetching of data is done asynchronously
         */
        interface DataLoadedCallback {
            /**
             * Called when the data was fetched successfully from the Repository
             *
             * @param snapshot the data
             */
            void onDataLoaded(DataSnapshot snapshot);

            /**
             * The data fetch failed as there was an exception
             */
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
        void getUserAccessData(DataLoadedCallback dataLoadedCallback);

        void getUserPermissionsData(DataLoadedCallback dataLoadedCallback);

        /**
         * This method is used to set the user access for a participant user
         */
        void setUserAsParticipant(DataLoadedCallback dataLoadedCallback);

        /**
         * This method is used to set the user permissions to the default.
         *
         * @param dataLoadedCallback callback to presenter
         */
        void setUserPermissionsToDefault(DataLoadedCallback dataLoadedCallback);
    }

    /**
     * To be called when the app starts.
     * Implement the startup sequence and authentication.
     */
    void appStarted(@Nullable View view);
}
