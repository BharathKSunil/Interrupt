package com.bharathksunil.interrupt.dashboard.ui.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharathksunil.interrupt.util.CircleTransform;
import com.squareup.picasso.Picasso;

import com.bharathksunil.interrupt.R;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.repository.FirebaseSignInRepositoryImplementation;
import com.bharathksunil.interrupt.dashboard.presenter.UserInfoPresenter;
import com.bharathksunil.interrupt.dashboard.presenter.UserInfoPresenterImplementation;
import com.bharathksunil.interrupt.util.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass for showing the user information and the events the user has
 * registered for in interrupt.
 */
public class UserInfoFragment extends Fragment implements UserInfoPresenter.View {

    public interface Interactor {
        void userLoggedOutLoadLauncherActivity();
    }

    public UserInfoFragment() {
        // Required empty public constructor
    }

    private UserInfoPresenter presenter;
    private Unbinder unbinder;
    private Interactor interactor;
    @BindView(R.id.iv_user_profile)
    ImageView iv_profile;
    @BindViews({R.id.tv_user_name, R.id.tv_user_type, R.id.tv_user_email, R.id.tv_user_phone
            , R.id.tv_user_usn, R.id.tv_user_college, R.id.tv_user_sem, R.id.tv_user_section
            , R.id.tv_user_dept})
    List<TextView> userInfoTextViewList;

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
        presenter.setView(this);
        return view;
    }

    @OnClick(R.id.btn_logout)
    public void onSignOutButtonPressed() {
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
    }

    @OnClick(R.id.btn_edit)
    public void onUserInfoButtonPressed() {
        //todo: Launch the Profile Info edit Button
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.setView(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    /**
     * Download the profile image from the url and load it to the image view
     *
     * @param profileUrl the url to the profile image
     */
    @Override
    public void setUserImage(String profileUrl) {
        Picasso.with(getActivity()).load(profileUrl)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .transform(new CircleTransform()).into(iv_profile);
    }

    @Override
    public void setUserName(String userName) {
        userInfoTextViewList.get(0).setText(userName);
    }

    @Override
    public void setUserDesignation(String designation) {
        userInfoTextViewList.get(1).setText(designation);
    }

    @Override
    public void setUserEmailID(String emailID) {
        userInfoTextViewList.get(2).setText(emailID);
    }

    @Override
    public void setUserPhoneNumber(String phoneNumber) {
        userInfoTextViewList.get(3).setText(phoneNumber);
    }

    @Override
    public void setUserUSN(String usn) {
        userInfoTextViewList.get(4).setText(usn);
    }

    @Override
    public void setUserCollege(String college) {
        userInfoTextViewList.get(5).setText(college);
    }

    @Override
    public void setUserSemester(String semester) {
        userInfoTextViewList.get(6).setText(semester);
    }

    @Override
    public void setUserSection(String section) {
        userInfoTextViewList.get(7).setText(section);
    }

    @Override
    public void setUserDepartment(String department) {
        userInfoTextViewList.get(8).setText(department);
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
