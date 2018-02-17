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
        if (view != null)
            view.loadAboutAppFragment();
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onUserProfileImagePressed() {
        if (viewInstance != null)
            viewInstance.loadUserInfoFragment();
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onEventsButtonPressed() {
        if (viewInstance != null)
            viewInstance.loadAllEventCategoriesFragment();
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onSchedulesButtonPressed() {
        if (viewInstance != null)
            viewInstance.loadSchedulesInfoFragment();
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onClassRepsButtonPressed() {
        if (viewInstance != null)
            viewInstance.loadClassRepsDashboard();
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onEventCoordinatorsButtonPressed() {
        if (viewInstance == null)
            return;

        if (userManager.isUserAEventsCoordinator() || userManager.isUserAnOrganiser() || userManager.isUserAnAdministrator()) {
            viewInstance.loadEventsCoordinatorDashboard();
        } else {
            viewInstance.loadEventCoordinatorContactsInfo();
        }
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onEventOrganisersButtonPressed() {
        if (viewInstance == null)
            return;

        if (userManager.isUserAnOrganiser() || userManager.isUserAnAdministrator()) {
            viewInstance.loadOrganisersDashboard();
        } else {
            viewInstance.loadOrganisersContactInfo();
        }
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onAdministratorButtonPressed() {
        if (viewInstance == null)
            return;

        if (userManager.isUserAnAdministrator())
            viewInstance.loadAdministratorDashboard();
        else
            viewInstance.loadAdministratorInfoFragment();
    }
}
