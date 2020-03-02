package com.bharathksunil.interrupt.dashboard.presenter;

import androidx.annotation.Nullable;

import com.bharathksunil.interrupt.auth.model.UserManager;

/**
 * This is the presenter implementation of the Dashboard Activity.
 *
 * @author Bharath on 13-02-2018.
 */

public class DashboardActivityPresenterImplementation implements DashboardActivityPresenter {

    private enum TAB {
        PROFILE,
        ABOUT,
        SCHEDULES,
        EVENTS,
        CR,
        COORDINATOR,
        ORGANISERS,
        ADMIN
    }

    private UserManager userManager;
    private DashboardActivityPresenter.View viewInstance;
    private TAB activeTabTag;

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
            activeTabTag = TAB.ABOUT;
            view.loadAboutAppPage();
            setDashboardTabsAccordingToUser();
            viewInstance.setSettingsButtonEnabled(false);
        }
    }

    /**
     * This method controls the tabs in the dashboard and sets them visible according to the user type
     */
    private void setDashboardTabsAccordingToUser() {
        if (viewInstance == null)
            return;
        //Event Coordinator Tab
        if (userManager.isUserAEventsCoordinator())
            viewInstance.setCoordinatorTabVisibility(android.view.View.VISIBLE);
        else
            viewInstance.setCoordinatorTabVisibility(android.view.View.GONE);
        //Class Representative tab
        if (userManager.isUserAClassRepresentative())
            viewInstance.setCRTabVisibility(android.view.View.VISIBLE);
        else
            viewInstance.setCRTabVisibility(android.view.View.GONE);
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onUserProfileTabPressed() {
        if (viewInstance != null) {
            activeTabTag = TAB.PROFILE;
            viewInstance.loadUserInfoPage();
            viewInstance.setSettingsButtonEnabled(false);
        }
    }

    /**
     * To be Called by the view when the Event button is pressed
     */
    @Override
    public void onEventsTabPressed() {
        if (viewInstance != null) {
            activeTabTag = TAB.EVENTS;
            viewInstance.loadAllEventCategoriesPage();
            viewInstance.setSettingsButtonEnabled(false);
        }
    }

    /**
     * To be Called by the view when the Schedules button is pressed
     */
    @Override
    public void onSchedulesTabPressed() {
        if (viewInstance != null) {
            activeTabTag = TAB.SCHEDULES;
            viewInstance.loadSchedulesInfoPage();
            viewInstance.setSettingsButtonEnabled(false);
        }
    }

    /**
     * To be Called by the view when the Class Representatives button is pressed
     */
    @Override
    public void onClassRepsTabPressed() {
        if (viewInstance != null && userManager.isUserAClassRepresentative()) {
            activeTabTag = TAB.CR;
            viewInstance.loadClassRepsDashboard();
            viewInstance.setSettingsButtonEnabled(false);
        }
    }

    /**
     * To be Called by the view when the Coordinators button is pressed
     */
    @Override
    public void onEventCoordinatorsTabPressed() {
        if (viewInstance == null)
            return;

        viewInstance.setSettingsButtonEnabled(false);
        if (userManager.isUserAEventsCoordinator()) {
            activeTabTag = TAB.COORDINATOR;
            viewInstance.loadCoordinatorsEventsInfoPage();
        }
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onEventOrganisersTabPressed() {
        if (viewInstance == null)
            return;
        activeTabTag = TAB.ORGANISERS;
        if (userManager.isUserAnOrganiser() || userManager.isUserAnAdministrator())
            viewInstance.setSettingsButtonEnabled(true);
        else
            viewInstance.setSettingsButtonEnabled(false);
        viewInstance.loadOrganisersInfoPage();
    }

    /**
     * To be Called by the view when the profile button is pressed
     */
    @Override
    public void onAdministratorTabPressed() {
        if (viewInstance == null)
            return;
        activeTabTag = TAB.ADMIN;
        if (userManager.isUserAnAdministrator())
            viewInstance.setSettingsButtonEnabled(true);
        else
            viewInstance.setSettingsButtonEnabled(false);

        viewInstance.loadAdministratorsInfoPage();
    }

    @Override
    public void onAboutTabPressed() {
        if (viewInstance != null) {
            activeTabTag = TAB.ABOUT;
            viewInstance.loadAboutAppPage();
            viewInstance.setSettingsButtonEnabled(false);
        }
    }

    /**
     * The Settings Button was pressed by the user
     */
    @Override
    public void onSettingsButtonPressed() {
        if (viewInstance == null)
            return;
        switch (activeTabTag) {
            case ADMIN:
                if (userManager.isUserAnAdministrator())
                    viewInstance.loadAdministratorsDashboard();
                else
                    viewInstance.setSettingsButtonEnabled(false);
                break;
            case ORGANISERS:
                if (userManager.isUserAnOrganiser() || userManager.isUserAnAdministrator())
                    viewInstance.loadOrganisersDashboard();
                else
                    viewInstance.setSettingsButtonEnabled(false);
                break;
            case CR:
                //We are not supporting this feature now, hence hide
                viewInstance.setSettingsButtonEnabled(false);
                viewInstance.setCRTabVisibility(android.view.View.GONE);
                break;
            default:
                viewInstance.setSettingsButtonEnabled(false);
        }

    }
}
