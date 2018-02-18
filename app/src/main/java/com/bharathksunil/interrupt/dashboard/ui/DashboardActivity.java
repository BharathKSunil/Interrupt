package com.bharathksunil.interrupt.dashboard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bharathksunil.interrupt.UnderConstructionFragment;

import com.bharathksunil.interrupt.R;

import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.ui.LauncherActivity;
import com.bharathksunil.interrupt.dashboard.presenter.DashboardActivityPresenter;
import com.bharathksunil.interrupt.dashboard.presenter.DashboardActivityPresenterImplementation;
import com.bharathksunil.interrupt.dashboard.ui.fragments.AboutFragment;
import com.bharathksunil.interrupt.dashboard.ui.fragments.UserInfoFragment;
import com.bharathksunil.interrupt.util.Debug;
import com.bharathksunil.interrupt.util.ViewUtils;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashboardActivity extends AppCompatActivity implements
        DashboardActivityPresenter.View, UserInfoFragment.Interactor {

    private Unbinder unbinder;
    private DashboardActivityPresenter presenter;

    @BindString(R.string.snack_back_press_twice)
    String snack_backPress;
    @BindColor(R.color.accent)
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
    List<ImageView> tabsViewList;

    private boolean backPressedTwice;
    private UnderConstructionFragment underConstructionFragment;
    private UserInfoFragment userInfoFragment;
    private AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_activity_dashboard);
        unbinder = ButterKnife.bind(this);

        underConstructionFragment = new UnderConstructionFragment();
        userInfoFragment = new UserInfoFragment();
        aboutFragment = new AboutFragment();
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
        presenter.onUserProfileImagePressed();
    }

    @OnClick(R.id.tab_events)
    public void onEventsTabClicked() {
        presenter.onEventsButtonPressed();
    }

    @OnClick(R.id.tab_schedule)
    public void onSchedulesTabClicked() {
        presenter.onSchedulesButtonPressed();
    }

    @OnClick(R.id.tab_class_reps)
    public void onClassRepsTabClicked() {
        presenter.onClassRepsButtonPressed();
    }

    @OnClick(R.id.tab_coordinators)
    public void onEventsCoordinatorTabClicked() {
        presenter.onEventCoordinatorsButtonPressed();
    }

    @OnClick(R.id.tab_organisers)
    public void onEventsOrganisersTabClicked() {
        presenter.onEventOrganisersButtonPressed();
    }

    @OnClick(R.id.tab_admin)
    public void onAdministratorTabClicked() {
        presenter.onAdministratorButtonPressed();
    }

    @OnClick(R.id.tab_about)
    public void onAboutTabClicked() {
        loadAboutAppFragment();
    }

    /**
     * The user is a Participant, load the Participant Dashboard
     */
    @Override
    public void loadAboutAppFragment() {
        setTabActive(TAB_ABOUT);
        loadFragment(aboutFragment);
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
    public void loadAdministratorDashboard() {
        setTabActive(TAB_ADMIN);
        loadFragment(underConstructionFragment);
    }

    /**
     * Load the Administrator Information fragment
     */
    @Override
    public void loadAdministratorInfoFragment() {
        setTabActive(TAB_ADMIN);
        loadFragment(underConstructionFragment);
    }

    /**
     * The user is a Class Representative, load the Organiser's Dashboard
     */
    @Override
    public void loadEventsCoordinatorDashboard() {
        setTabActive(TAB_COORDINATORS);
        loadFragment(underConstructionFragment);
    }

    /**
     * Load the fragment which shows all the event's coordinators contact information
     */
    @Override
    public void loadEventCoordinatorContactsInfo() {
        setTabActive(TAB_COORDINATORS);
        loadFragment(underConstructionFragment);
    }

    /**
     * The user is a organiser, load the Organiser's Dashboard
     */
    @Override
    public void loadOrganisersDashboard() {
        setTabActive(TAB_ORGANISERS);
        loadFragment(underConstructionFragment);
    }

    /**
     * Load the fragment which shows all the core organisers of interrupt
     */
    @Override
    public void loadOrganisersContactInfo() {
        setTabActive(TAB_ORGANISERS);
        loadFragment(underConstructionFragment);
    }

    /**
     * Load the user information fragment
     */
    @Override
    public void loadUserInfoFragment() {
        setTabActive(TAB_PROFILE);
        loadFragment(userInfoFragment);
    }

    /**
     * Load the Events fragment
     */
    @Override
    public void loadAllEventCategoriesFragment() {
        setTabActive(TAB_EVENTS);
        loadFragment(underConstructionFragment);
    }

    /**
     * Load the Schedules Fragment
     */
    @Override
    public void loadSchedulesInfoFragment() {
        setTabActive(TAB_SCHEDULES);
        loadFragment(underConstructionFragment);
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
        for (ImageView view : tabsViewList) {
            view.setBackgroundColor(tab_disSelectedColor);
        }
        tabsViewList.get(activeTabID).setBackgroundColor(tab_selectedColor);
    }

    /**
     * This is a temporary function called as the desired fragment is still being constructed
     */
    @SuppressWarnings("unused")
    private void loadUnderConstructionFragment() {

    }

    @Override
    public void userLoggedOutLoadLauncherActivity() {
        startActivity(new Intent(this, LauncherActivity.class));
        finish();
    }

    @Override
    public void loadEventsViewerActivityForTheEvent(String eventId) {
        //todo: Load the Events Viewer for the event id
        Debug.i("Load the Events Activity Viewer for the Event:"+eventId);
    }
}
