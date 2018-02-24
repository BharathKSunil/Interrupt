package com.bharathksunil.interrupt.dashboard.ui.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.admin.repository.FirebaseFeedbackRepository;
import com.bharathksunil.interrupt.admin.repository.MockUsersInfoRepositoryImplementation;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.dashboard.presenter.AdminInfoPresenter;
import com.bharathksunil.interrupt.dashboard.presenter.AdminInfoPresenterImplementation;
import com.bharathksunil.interrupt.organiser.ui.OrganiserInfoRecyclerAdapter;
import com.bharathksunil.interrupt.util.ViewUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass for showing the Admin and sending feedback
 */
public class AdminInfoFragment extends Fragment implements AdminInfoPresenter.View {


    private Unbinder unbinder;
    private AdminInfoPresenter presenter;

    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView loadingIndicatorView;
    @BindView(R.id.tv_empty_prompt)
    TextView tv_empty_prompt;
    @BindView(R.id.rv_admin)
    RecyclerView rv_admin;
    @BindView(R.id.til_feedback)
    TextInputLayout til_feedback;
    @BindView(R.id.btn_submit)
    Button btn_send;

    @BindString(R.string.err_unexpected_error)
    String err_unexpectedError;
    @BindString(R.string.err_empty_field)
    String err_emptyField;
    @BindString(R.string.err_feedback_post_failed)
    String err_feedbackFailed;
    @BindString(R.string.snack_feedback_post_successful)
    String snack_feedbackSuccessful;

    public AdminInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dash_fragment_admin_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new AdminInfoPresenterImplementation(new FirebaseFeedbackRepository(),
                new MockUsersInfoRepositoryImplementation());
        presenter.setView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            til_feedback.setNestedScrollingEnabled(true);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.setView(null);
    }

    @OnClick(R.id.btn_submit)
    public void onSubmitButtonPressed() {
        til_feedback.setErrorEnabled(false);
        presenter.onSendButtonPressed();
    }

    /**
     * Called when the presenter starts its work
     * enable any dialog-box or progress bars
     */
    @Override
    public void onProcessStarted() {
        loadingIndicatorView.smoothToShow();
        ViewUtils.setDisabled(btn_send, til_feedback);
    }

    /**
     * Called when the presenter has finished its work
     * disable any dialog-box or progress bars
     */
    @Override
    public void onProcessEnded() {
        loadingIndicatorView.smoothToHide();
        ViewUtils.setEnabled(btn_send, til_feedback);
    }

    /**
     * Called whenever there was an unexpected error in the process
     * Display any toast or messages to the user indicating that there was an error
     */
    @Override
    public void onUnexpectedError() {
        ViewUtils.errorBar(err_unexpectedError, getActivity());
    }

    @Override
    public void loadAdministratorsRecyclerView(@NonNull List<Users> admins) {
        rv_admin.setAdapter(new OrganiserInfoRecyclerAdapter(getActivity(), admins));
        rv_admin.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_admin.setItemAnimator(new DefaultItemAnimator());
        rv_admin.setNestedScrollingEnabled(false);
        rv_admin.setHasFixedSize(true);
    }

    @Override
    public void showNoAdministratorsText() {
        ViewUtils.setVisible(tv_empty_prompt);
    }

    @Override
    public void hideNoAdministratorsText() {
        ViewUtils.setGone(tv_empty_prompt);
    }

    @Override
    public String getFeedback() {
        if (til_feedback.getEditText() != null)
            return til_feedback.getEditText().getText().toString();
        else
            return "";
    }

    @Override
    public void setFeedbackError(FormErrorType errorType) {
        if (errorType == FormErrorType.EMPTY) {
            til_feedback.setErrorEnabled(true);
            til_feedback.setError(err_emptyField);
        }
    }

    @Override
    public void onFeedbackPostSuccessful() {
        ViewUtils.snackBar(snack_feedbackSuccessful, getActivity());
        if (til_feedback.getEditText() != null)
            til_feedback.getEditText().setText("");
    }

    @Override
    public void onFeedbackPostFailed() {
        ViewUtils.errorBar(err_feedbackFailed, getActivity());
    }
}
