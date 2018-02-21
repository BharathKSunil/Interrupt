package com.bharathksunil.interrupt.dashboard.presenter;

import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.auth.model.UserManager;

/**
 * This is the presenter implementation of the Dashboard Activity.
 *
 * @author Bharath on 13-02-2018.
 */

public class DashboardActivityPresenterImplementation implements DashboardActivityPresenter {

    private UserManager userManager;
    private DashboardActivityPresenter.View viewInstance;

    public DashboardActivityPresenterImplementation(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Called by the view whn it starts and stops
     *
     * @param view the view instance for interaction
     */
    @Override
    public void setView(@Nullable View view) {
        viewInstance = view;
        if (view != null) {
            view.loadAboutAppFragment();
            if (userManager.isUserAEventsCoordinator())
                view.setCoordinatorTabVisibility(android.view.View.VISIBLE);
            else
                view.setCoordinatorTabVisibility(android.view.View.GONE);
            if (userManager.isUserAClassRepresentative())
                view.setCRTabVisibility(android.view.View.VISIBLE);
            else
                view.setCRTabVisibility(android.view.View.GONE);
        }
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onUserProfileTabPressed() {
        if (viewInstance != null)
            viewInstance.loadUserInfoFragment();
    }

    /**
     * To be Called by the view when the Event button is pressed
     */
    @Override
    public void onEventsTabPressed() {
        if (viewInstance != null)
            viewInstance.loadAllEventCategoriesFragment();
    }

    /**
     * To be Called by the view when the Schedules button is pressed
     */
    @Override
    public void onSchedulesTabPressed() {
        if (viewInstance != null)
            viewInstance.loadSchedulesInfoFragment();
    }

    /**
     * To be Called by the view when the Class Representatives button is pressed
     */
    @Override
    public void onClassRepsTabPressed() {
        if (viewInstance != null && (userManager.isUserAClassRepresentative()))
            viewInstance.loadClassRepsDashboard();
    }

    /**
     * To be Called by the view when the Coordinators button is pressed
     */
    @Override
    public void onEventCoordinatorsTabPressed() {
        if (viewInstance == null)
            return;

        if (userManager.isUserAEventsCoordinator()) {
            viewInstance.loadEventsCoordinatorDashboard();
        }
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onEventOrganisersTabPressed() {
        if (viewInstance == null)
            return;
        viewInstance.loadOrganisersDashboard();
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onAdministratorTabPressed() {
        if (viewInstance == null)
            return;

        viewInstance.loadAdministratorDashboard();
    }
}
