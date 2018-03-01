package com.bharathksunil.interrupt.dashboard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.UnderConstructionFragment;
import com.bharathksunil.interrupt.admin.ui.AdminDashboardActivity;
import com.bharathksunil.interrupt.admin.ui.fragments.AdminInfoFragment;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.ui.LauncherActivity;
import com.bharathksunil.interrupt.auth.ui.fragments.UserInfoFragment;
import com.bharathksunil.interrupt.dashboard.presenter.DashboardActivityPresenter;
import com.bharathksunil.interrupt.dashboard.presenter.DashboardActivityPresenterImplementation;
import com.bharathksunil.interrupt.dashboard.ui.fragments.AboutFragment;
import com.bharathksunil.interrupt.events.ui.activities.OrganisersDashboardActivity;
import com.bharathksunil.interrupt.events.ui.fragments.CoordinatorsEventsInfoFragment;
import com.bharathksunil.interrupt.events.ui.fragments.EventCategoriesFragment;
import com.bharathksunil.interrupt.events.ui.fragments.OrganisersInfoFragment;
import com.bharathksunil.interrupt.events.ui.fragments.SchedulesFragment;
import com.bharathksunil.interrupt.util.ViewUtils;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainDashboardActivity extends AppCompatActivity implements
        DashboardActivityPresenter.View, UserInfoFragment.Interactor {

    private UnderConstructionFragment underConstructionFragment;
    private Unbinder unbinder;
    private DashboardActivityPresenter presenter;
    private boolean backPressedTwice;

    @BindString(R.string.snack_back_press_twice)
    String snack_backPress;
    @BindColor(R.color.tab_selected_color)
    int tab_selectedColor;
    @BindColor(R.color.transparent)
    int tab_disSelectedColor;

    /**
     * These are the constants which are used for getting the tabs from the ButterKnife tabsViewList;
     */
    private static final int TAB_PROFILE = 0, TAB_EVENTS = 1, TAB_SCHEDULES = 2, TAB_CLASS_REPS = 3,
            TAB_COORDINATORS = 4, TAB_ORGANISERS = 5, TAB_ADMIN = 6, TAB_ABOUT = 7;
    @BindViews({R.id.tab_profile, R.id.tab_events, R.id.tab_schedule, R.id.tab_class_reps,
            R.id.tab_coordinators, R.id.tab_organisers, R.id.tab_admin, R.id.tab_about})
    List<View> tabsViewList;
    @BindView(R.id.fab_settings)
    FloatingActionButton fabSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_activity_dashboard);
        unbinder = ButterKnife.bind(this);

        underConstructionFragment = new UnderConstructionFragment();

        presenter = new DashboardActivityPresenterImplementation(UserManager.getInstance());
        presenter.setView(this);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTwice) {
            super.onBackPressed();
            return;
        }
        this.backPressedTwice = true;

        //DISPLAY THE CUSTOM SNACKBAR MESSAGE
        ViewUtils.snackBar(snack_backPress, this);
        //WAIT TILL THE SNACKBAR GETS DISPLAYED THEN RESET
        //threshold is =SnackBar.LENGTH_LONG, this can be changed as per requirement
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backPressedTwice = false;
            }
        }, 2750);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.setView(null);
    }

    @OnClick(R.id.tab_profile)
    public void onProfileTabClicked() {
        presenter.onUserProfileTabPressed();
    }

    @OnClick(R.id.tab_events)
    public void onEventsTabClicked() {
        presenter.onEventsTabPressed();
    }

    @OnClick(R.id.tab_schedule)
    public void onSchedulesTabClicked() {
        presenter.onSchedulesTabPressed();
    }

    @OnClick(R.id.tab_class_reps)
    public void onClassRepsTabClicked() {
        presenter.onClassRepsTabPressed();
    }

    @OnClick(R.id.tab_coordinators)
    public void onEventsCoordinatorTabClicked() {
        presenter.onEventCoordinatorsTabPressed();
    }

    @OnClick(R.id.tab_organisers)
    public void onEventsOrganisersTabClicked() {
        presenter.onEventOrganisersTabPressed();
    }

    @OnClick(R.id.tab_admin)
    public void onAdministratorTabClicked() {
        presenter.onAdministratorTabPressed();
    }

    @OnClick(R.id.tab_about)
    public void onAboutTabClicked() {
        presenter.onAboutTabPressed();
    }

    @OnClick(R.id.fab_settings)
    public void onSettingsFabPressed() {
        presenter.onSettingsButtonPressed();
    }

    /**
     * The user is a Participant, load the Participant Dashboard
     */
    @Override
    public void loadAboutAppPage() {
        setTabActive(TAB_ABOUT);
        loadFragment(new AboutFragment());
    }

    @Override
    public void setCRTabVisibility(int visibility) {
        findViewById(R.id.tab_class_reps).setVisibility(visibility);
    }

    @Override
    public void setCoordinatorTabVisibility(int visibility) {
        findViewById(R.id.tab_coordinators).setVisibility(visibility);
    }

    /**
     * Show or hide the Floating Action Button here.
     *
     * @param isEnabled the visibility of the button
     */
    @Override
    public void setSettingsButtonEnabled(boolean isEnabled) {
        if (isEnabled)
            fabSettings.show();
        else
            fabSettings.hide();
    }

    /**
     * Load the Organisers Dashboard
     */
    @Override
    public void loadOrganisersDashboard() {
        startActivity(new Intent(this, OrganisersDashboardActivity.class));
    }

    /**
     * Load the Administrators Dashboard
     */
    @Override
    public void loadAdministratorsDashboard() {
        startActivity(new Intent(this, AdminDashboardActivity.class));
    }

    /**
     * The user is a Class Representative, load the Dashboard
     */
    @Override
    public void loadClassRepsDashboard() {
        setTabActive(TAB_CLASS_REPS);
        loadFragment(underConstructionFragment);
    }

    /**
     * The user is an Administrator, load the Dashboard
     */
    @Override
    public void loadAdministratorsInfoPage() {
        setTabActive(TAB_ADMIN);
        loadFragment(new AdminInfoFragment());
    }

    /**
     * The user is a Class Representative, load the Organiser's Dashboard
     */
    @Override
    public void loadCoordinatorsEventsInfoPage() {
        setTabActive(TAB_COORDINATORS);
        loadFragment(new CoordinatorsEventsInfoFragment());
    }

    /**
     * The user is a organiser, load the Organiser's Dashboard
     */
    @Override
    public void loadOrganisersInfoPage() {
        setTabActive(TAB_ORGANISERS);
        loadFragment(new OrganisersInfoFragment());
    }

    /**
     * Load the user information fragment
     */
    @Override
    public void loadUserInfoPage() {
        setTabActive(TAB_PROFILE);
        loadFragment(new UserInfoFragment());
    }

    /**
     * Load the Events fragment
     */
    @Override
    public void loadAllEventCategoriesPage() {
        setTabActive(TAB_EVENTS);
        loadFragment(new EventCategoriesFragment());
    }

    /**
     * Load the Schedules Fragment
     */
    @Override
    public void loadSchedulesInfoPage() {
        setTabActive(TAB_SCHEDULES);
        loadFragment(new SchedulesFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    /**
     * This sets the background colour of the tab that is currently selected
     *
     * @param activeTabID position of the tab in the tabsViewList
     */
    private void setTabActive(int activeTabID) {
        for (View view : tabsViewList) {
            view.setBackgroundColor(tab_disSelectedColor);
        }
        tabsViewList.get(activeTabID).setBackgroundColor(tab_selectedColor);
    }

    @Override
    public void userLoggedOutLoadLauncherActivity() {
        startActivity(new Intent(this, LauncherActivity.class));
        finish();
    }

    @Override
    public void loadEventsViewerActivityForTheEvent(String eventId) {
        //unimplemented
    }
}
