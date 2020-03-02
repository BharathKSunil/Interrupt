package com.bharathksunil.interrupt.auth.ui.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.repository.FirebaseSignInRepositoryImplementation;
import com.bharathksunil.interrupt.auth.presenter.UserInfoPresenter;
import com.bharathksunil.interrupt.auth.presenter.UserInfoPresenterImplementation;
import com.bharathksunil.interrupt.events.presenter.RegisteredEventsRecyclerPresenter;
import com.bharathksunil.interrupt.events.presenter.RegisteredEventsRecyclerPresenterImplementation;
import com.bharathksunil.interrupt.events.repository.FirebaseRegisteredEventsRepositoryImplementation;
import com.bharathksunil.interrupt.events.ui.RegisteredEventsRecyclerAdapter;
import com.bharathksunil.interrupt.util.CircleTransform;
import com.bharathksunil.interrupt.util.Utils;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass for showing the user information and the events the user has
 * registered for in interrupt.
 */
@SuppressWarnings("FieldCanBeLocal")
public class UserInfoFragment extends Fragment implements UserInfoPresenter.View,
        RegisteredEventsRecyclerPresenter.View {

    public interface Interactor {
        void userLoggedOutLoadLauncherActivity();

        void loadEventsViewerActivityForTheEvent(String eventId);
    }

    public UserInfoFragment() {
        // Required empty public constructor
    }

    private UserInfoPresenter presenter;
    private Unbinder unbinder;
    private Interactor interactor;
    private RegisteredEventsRecyclerPresenter recyclerPresenter;
    @BindView(R.id.iv_user_profile)
    ImageView iv_profile;
    @BindString(R.string.err_offline)
    String err_offline;
    private final int USERNAME = 0, DESIGNATION = 1, EMAIL = 2, PHONE = 3, USN = 4, COLLEGE = 5, SEM = 6,
            SECTION = 7, DEPARTMENT = 8, EMPTY_PROMPT = 9;
    @BindViews({R.id.tv_user_name, R.id.tv_user_type, R.id.tv_user_email, R.id.tv_user_phone
            , R.id.tv_user_usn, R.id.tv_user_college, R.id.tv_user_sem, R.id.tv_user_section
            , R.id.tv_user_dept, R.id.tv_empty_prompt})
    List<TextView> userInfoTextViewList;
    @BindView(R.id.events_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interactor) {
            interactor = (Interactor) context;
        } else
            throw new RuntimeException("The Activity must Implement the Interactor for UserInfoFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dash_fragment_user_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new UserInfoPresenterImplementation(new FirebaseSignInRepositoryImplementation()
                , UserManager.getInstance());
        recyclerPresenter = new RegisteredEventsRecyclerPresenterImplementation(
                new FirebaseRegisteredEventsRepositoryImplementation(),
                UserManager.getInstance().getUsersEmailID());
        recyclerPresenter.setView(this);
        presenter.setView(this);
        return view;
    }

    @OnClick(R.id.btn_logout)
    public void onSignOutButtonPressed() {
        if (Utils.isConnected(getActivity()))
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.prompt_sign_out_title).setMessage(R.string.prompt_sign_out_message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.onSignOutButtonPressed();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create()
                    .show();
        else
            ViewUtils.errorBar(err_offline, getActivity());
    }

    @OnClick(R.id.btn_edit)
    public void onUserInfoButtonPressed() {
        //todo: Launch the Profile Info edit Button
        ViewUtils.snackBar("Coming Soon", getActivity());
    }

    @OnClick(R.id.iv_refresh)
    public void onRefreshButtonPressed() {
        recyclerPresenter.refreshRecyclerData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.setView(null);
        recyclerPresenter.setView(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    /**
     * Called when the presenter starts its work
     * enable any dialog-box or progress bars
     */
    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
    }

    /**
     * Called when the presenter has finished its work
     * disable any dialog-box or progress bars
     */
    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
    }

    /**
     * Called whenever there was an unexpected error in the process
     * Display any toast or messages to the user indicating that there was an error
     */
    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar("Failed to load Registered Events", getActivity());
    }

    @Override
    public void showNoRegisteredEventsText() {
        ViewUtils.setVisible(userInfoTextViewList.get(EMPTY_PROMPT));
    }

    @Override
    public void hideNoRegisteredEventsText() {
        ViewUtils.setGone(userInfoTextViewList.get(EMPTY_PROMPT));
    }

    @Override
    public void loadRegisteredEventsRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        RegisteredEventsRecyclerAdapter adapter =
                new RegisteredEventsRecyclerAdapter(recyclerPresenter, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void loadEventsViewerForTheEventGivenBy(String eventID) {
        interactor.loadEventsViewerActivityForTheEvent(eventID);
    }

    @Override
    public void showDataUpdatedMessage() {
        ViewUtils.snackBar("Registration Data Updated", getActivity());
    }

    /**
     * Download the profile image from the url and load it to the image view
     *
     * @param profileUrl the url to the profile image
     */
    @Override
    public void loadUserImage(String profileUrl) {
        Picasso.get().load(profileUrl)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .transform(new CircleTransform()).into(iv_profile);
    }

    @Override
    public void setUserName(String userName) {
        userInfoTextViewList.get(USERNAME).setText(userName);
    }

    @Override
    public void setUserDesignation(String designation) {
        userInfoTextViewList.get(DESIGNATION).setText(designation);
    }

    @Override
    public void setUserEmailID(String emailID) {
        userInfoTextViewList.get(EMAIL).setText(emailID);
    }

    @Override
    public void setUserPhoneNumber(String phoneNumber) {
        userInfoTextViewList.get(PHONE).setText(phoneNumber);
    }

    @Override
    public void setUserUSN(String usn) {
        userInfoTextViewList.get(USN).setText(usn);
    }

    @Override
    public void setUserCollege(String college) {
        userInfoTextViewList.get(COLLEGE).setText(college);
    }

    @Override
    public void setUserSemester(String semester) {
        userInfoTextViewList.get(SEM).setText(semester);
    }

    @Override
    public void setUserSection(String section) {
        userInfoTextViewList.get(SECTION).setText(section);
    }

    @Override
    public void setUserDepartment(String department) {
        userInfoTextViewList.get(DEPARTMENT).setText(department);
    }

    @Override
    public void loadProfileEditView() {
        ViewUtils.snackBar("Coming Soon", getActivity());
    }

    @Override
    public void loadLauncherActivity() {
        interactor.userLoggedOutLoadLauncherActivity();
    }
}
