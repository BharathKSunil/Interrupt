package com.bharathksunil.interrupt.dashboard.presenter;

import android.support.annotation.Nullable;

/**
 * This is the presenter for the Dashboard Activity
 *
 * @author Bharath on 12-02-2018.
 */

public interface DashboardActivityPresenter {
    //We are not extending BaseView as there are no long running process here
    interface View {

        /**
         * The user is a Class Representative, load the Dashboard
         */
        void loadClassRepsDashboard();

        /**
         * The user is an Administrator, load the Dashboard
         */
        void loadAdministratorsInfoPage();

        /**
         * Load the fragment which displays the events the user is a coordinator for
         */
        void loadCoordinatorsEventsInfoPage();

        /**
         * The user is a organiser, load the Organiser's Dashboard
         */
        void loadOrganisersInfoPage();

        /**
         * Load the user information fragment
         */
        void loadUserInfoPage();

        /**
         * Load the Events fragment
         */
        void loadAllEventCategoriesPage();

        /**
         * Load the Schedules Fragment
         */
        void loadSchedulesInfoPage();

        /**
         * To load the fragment explaining about the app
         */
        void loadAboutAppPage();

        void setCRTabVisibility(int visibility);

        void setCoordinatorTabVisibility(int visibility);

        /**
         * Show or hide the Floating Action Button here.
         *
         * @param isEnabled the visibility of the button
         */
        void setSettingsButtonEnabled(boolean isEnabled);

        /**
         * Load the Organisers Dashboard
         */
        void loadOrganisersDashboard();

        /**
         * Load the Administrators Dashboard
         */
        void loadAdministratorsDashboard();
    }

    /**
     * Called by the view whn it starts and stops
     *
     * @param view the view instance for interaction
     */
    void setView(@Nullable View view);

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onUserProfileTabPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onEventsTabPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onSchedulesTabPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onClassRepsTabPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onEventCoordinatorsTabPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onEventOrganisersTabPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onAdministratorTabPressed();

    /**
     * The Settings Button was pressed by the user
     */
    void onSettingsButtonPressed();
}
