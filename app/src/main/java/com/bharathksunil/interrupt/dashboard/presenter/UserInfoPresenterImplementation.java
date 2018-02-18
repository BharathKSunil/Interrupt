package com.bharathksunil.interrupt.dashboard.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.presenter.SignInPresenter;

/**
 * This Presenter presents the user info and also interacts with the repository to signOut the user.
 *
 * @author Bharath on 13-02-2018.
 */

public class UserInfoPresenterImplementation implements UserInfoPresenter {

    @Nullable
    private View viewInstance;
    @NonNull
    private SignInPresenter.Repository repositoryInstance;
    @NonNull
    private UserManager userManager;

    public UserInfoPresenterImplementation(@NonNull SignInPresenter.Repository repository,
                                           @NonNull UserManager userManager) {
        repositoryInstance = repository;
        this.userManager = userManager;
    }

    @Override
    public void setView(@Nullable View view) {
        viewInstance = view;
        if (view != null) {
            viewInstance.setUserName(userManager.getUsersName());
            viewInstance.setUserDesignation(userManager.getUsersDesignation());
            viewInstance.setUserPhoneNumber(userManager.getUsersPhoneNo());
            viewInstance.setUserEmailID(userManager.getUsersEmailID());
            viewInstance.setUserUSN(userManager.getUsersUSN());
            viewInstance.setUserCollege(userManager.getUsersCollege());
            viewInstance.setUserSemester(userManager.getUsersSemester());
            viewInstance.setUserSection(userManager.getUsersSection());
            viewInstance.setUserDepartment(userManager.getUsersDepartment());
            viewInstance.loadUserImage(userManager.getUsersProfileImageUrl());

        }
    }

    @Override
    public void onSignOutButtonPressed() {
        if (viewInstance == null)
            return;
        if (userManager.isUserSignedIn()) {
            repositoryInstance.signOut();
            viewInstance.loadLauncherActivity();
        }
    }

    @Override
    public void onEditUserInfoButtonPressed() {
        if (viewInstance != null)
            viewInstance.loadProfileEditView();
    }
}
