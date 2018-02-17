package com.bharathksunil.interrupt.dashboard.presenter;

import android.support.annotation.Nullable;

/**
 * This is the presenter for the Dashboard Activity
 *
 * @author Bharath on 12-02-2018.
 */

public interface DashboardActivityPresenter {
    //We are not extending BaseView as there are no long running process here
    interface View{

        /**
         * The user is a Class Representative, load the Dashboard
         */
        void loadClassRepsDashboard();

        /**
         * The user is an Administrator, load the Dashboard
         */
        void loadAdministratorDashboard();

        /**
         * Load the Administrator Information fragment
         */
        void loadAdministratorInfoFragment();

        /**
         * The user is a Class Representative, load the Organiser's Dashboard
         */
        void loadEventsCoordinatorDashboard();

        /**
         * Load the fragment which shows all the event's coordinators contact information
         */
        void loadEventCoordinatorContactsInfo();

        /**
         * The user is a organiser, load the Organiser's Dashboard
         */
        void loadOrganisersDashboard();

        /**
         * Load the fragment which shows all the core organisers of interrupt
         */
        void loadOrganisersContactInfo();

        /**
         * Load the user information fragment
         */
        void loadUserInfoFragment();

        /**
         * Load the Events fragment
         */
        void loadAllEventCategoriesFragment();

        /**
         * Load the Schedules Fragment
         */
        void loadSchedulesInfoFragment();

        /**
         * To load the fragment explaining about the app
         */
        void loadAboutAppFragment();
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
    void onUserProfileImagePressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onEventsButtonPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onSchedulesButtonPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onClassRepsButtonPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onEventCoordinatorsButtonPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onEventOrganisersButtonPressed();

    /**
     * To be Called by the view when the profile button is pressed
     */
    void onAdministratorButtonPressed();
}
