package com.bharathksunil.interrupt.admin.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.auth.model.UserManager;

/**
 * This is an implementation of the {@link AdminFunctionsPresenter}
 *
 * @author Bharath on 26-02-2018.
 */

public class AdminFunctionsPresenterImplementation implements AdminFunctionsPresenter {

    @Nullable
    private View viewInstance;
    @NonNull
    private UserManager userManager;

    public AdminFunctionsPresenterImplementation() {
        userManager = UserManager.getInstance();
    }

    @Override
    public void setView(View view) {
        viewInstance = view;
    }

    @Override
    public void onUserManagementSelected() {
        if (viewInstance != null) {
            if (userManager.canUserViewAllUserData())
                viewInstance.loadUserManagementPage();
            else
                viewInstance.showPermissionDeniedMessage();
        }
    }

    @Override
    public void onOrganiserManagementSelected() {
        if (viewInstance != null) {
            if (userManager.canUserModifyAllOrganisersData())
                viewInstance.loadOrganiserManagementPage();
            else
                viewInstance.showPermissionDeniedMessage();
        }
    }

    @Override
    public void onFeedbackManagementSelected() {
        if (viewInstance != null) {
            if (userManager.canUserViewAllFeedback())
                viewInstance.loadFeedbackViewerPage();
            else
                viewInstance.showPermissionDeniedMessage();
        }
    }
}
