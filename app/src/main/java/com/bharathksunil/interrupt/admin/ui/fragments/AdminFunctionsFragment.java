package com.bharathksunil.interrupt.admin.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.admin.presenter.AdminFunctionsPresenter;
import com.bharathksunil.interrupt.admin.presenter.AdminFunctionsPresenterImplementation;
import com.bharathksunil.interrupt.events.ui.activities.EventDashboardActivity;
import com.bharathksunil.interrupt.util.ViewUtils;

import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFunctionsFragment extends Fragment implements AdminFunctionsPresenter.View {

    private Interactor interactor;
    private AdminFunctionsPresenter presenter;
    private Unbinder unbinder;

    public AdminFunctionsFragment() {
        // Required empty public constructor
    }

    @BindString(R.string.err_permission_denied)
    String err_permissionDenied;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interactor)
            interactor = (Interactor) context;
        else
            throw new RuntimeException("To use this fragment the activity must implement the interactor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_functions, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new AdminFunctionsPresenterImplementation();
        presenter.setView(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.setView(null);
    }


    @OnClick(R.id.admin_card_user_management)
    public void onUserManagementCardPressed() {
        presenter.onUserManagementSelected();
    }

    @OnClick(R.id.admin_card_organisers_management)
    public void onOrganiserManagementCardPressed() {
        presenter.onOrganiserManagementSelected();
    }

    @OnClick(R.id.admin_card_feedback_management)
    public void onFeedbackManagementCardPressed() {
        presenter.onFeedbackManagementSelected();
    }
    @OnClick(R.id.admin_card_event_management)
    public void onEventManagementCardPressed(){
        presenter.onEventManagementSelected();
    }

    @Override
    public void showPermissionDeniedMessage() {
        ViewUtils.errorBar(err_permissionDenied, getActivity());
    }

    @Override
    public void loadUserManagementPage() {
        interactor.loadUserManagementFragment();
    }

    @Override
    public void loadOrganiserManagementPage() {
        interactor.loadOrganisersManagementFragment();
    }

    @Override
    public void loadFeedbackViewerPage() {
        interactor.loadUserFeedbackFragment();
    }

    @Override
    public void loadNewEventsActivity() {
        startActivity(new Intent(getActivity(), EventDashboardActivity.class));
    }

    public interface Interactor {
        void loadUserManagementFragment();

        void loadOrganisersManagementFragment();

        void loadUserFeedbackFragment();
    }

}
