package com.bharathksunil.interrupt.admin.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.auth.model.UserPermissions;
import com.bharathksunil.interrupt.auth.model.UserType;
import com.bharathksunil.interrupt.util.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bharathksunil.interrupt.auth.presenter.FormErrorType.EMPTY;
import static com.bharathksunil.interrupt.auth.presenter.FormErrorType.INVALID;

/**
 * This is the implementation of the {@link NewOrganiserPresenter}.
 * Created by Bharath on 26-02-2018.
 */

public class NewOrganiserPresenterImplementation implements NewOrganiserPresenter {

    @Nullable
    private View viewInstance;
    @NonNull
    private Repository repositoryInstance;
    private String profileUrl;

    public NewOrganiserPresenterImplementation(@NonNull Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
    }

    @Override
    public void setView(@Nullable View view) {
        viewInstance = view;
        if (viewInstance != null) {
            viewInstance.setHeadingText("New Organiser");
            List<String> designations = new ArrayList<>();
            designations.add(UserType.CORE_TEAM.name());
            designations.add(UserType.TECH_TEAM.name());
            designations.add(UserType.DESIGN_TEAM.name());
            designations.add(UserType.EVENT_TEAM.name());
            designations.add(UserType.OFF_STAGE_TEAM.name());
            designations.add(UserType.CULTURAL_TEAM.name());
            viewInstance.loadDesignations(designations);
        }
    }

    @Override
    public void onSubmitOrganiserButtonPressed() {
        if (viewInstance == null)
            return;
        viewInstance.onProcessStarted();
        if (validateFields())
            if (viewInstance.getProfileImageUri() != null)
                uploadProfileImage();
            else
                storeDataOnObjects();

    }

    private void uploadProfileImage() {
        if (viewInstance != null)
            repositoryInstance.uploadUserImage(viewInstance.getProfileImageUri(), new Repository.OnProfileUploadedCallback() {
                @Override
                public void onProfileUploadedSuccessfully(String url) {
                    profileUrl = url;
                    storeDataOnObjects();
                }

                @Override
                public void onProfileUploadFailed() {
                    profileUrl = null;
                    storeDataOnObjects();
                }
            });
    }

    private void storeDataOnObjects() {
        if (viewInstance == null)
            return;
        UserPermissions access = viewInstance.getPermissionsSelected();
        List<String> roles = new ArrayList<>();
        roles.addAll(Arrays.asList(viewInstance.getOrganiserRoles().split(",")));
        Users user = new Users(
                viewInstance.getOrganiserName(),
                viewInstance.getUserDesignation(),
                viewInstance.getOrganiserPhoneNo(),
                viewInstance.getOrganiserEmail(),
                profileUrl,
                roles
        );
        repositoryInstance.addOrganiser(user, access, new Repository.OnAddedCallback() {
            @Override
            public void onAddSuccessful() {
                if (viewInstance == null)
                    return;
                viewInstance.onProcessEnded();
                viewInstance.showOperationSuccessfulMessage("Organiser Added Successfully");
                viewInstance.exitPage();
            }

            @Override
            public void onAddFailed() {
                if (viewInstance == null)
                    return;
                viewInstance.onProcessEnded();
                viewInstance.onUnexpectedError();
            }
        });
    }

    private boolean validateFields() {
        if (viewInstance == null)
            return false;
        if (TextUtils.isEmpty(viewInstance.getOrganiserName())) {
            viewInstance.setNameFieldError(EMPTY);
            return false;
        } else if (TextUtils.isEmpty(viewInstance.getOrganiserEmail())) {
            viewInstance.setEmailIDFieldError(EMPTY);
            return false;
        } else if (!TextUtils.isEmailValid(viewInstance.getOrganiserEmail())) {
            viewInstance.setEmailIDFieldError(INVALID);
            return false;
        } else if (TextUtils.isEmpty(viewInstance.getOrganiserPhoneNo())) {
            viewInstance.setPhoneNoFieldError(EMPTY);
            return false;
        } else if (!TextUtils.isPhoneNumberValid(viewInstance.getOrganiserPhoneNo())) {
            viewInstance.setPhoneNoFieldError(INVALID);
            return false;
        } else if (TextUtils.isEmpty(viewInstance.getOrganiserRoles())) {
            viewInstance.setRolesFieldError(EMPTY);
            return false;
        }
        return true;
    }

    @Override
    public void loadOrganiserWithEmail(@NonNull String email) {

    }

    @Override
    public void onProceedWithDefaultPermissionsButtonPressed() {

    }
}
