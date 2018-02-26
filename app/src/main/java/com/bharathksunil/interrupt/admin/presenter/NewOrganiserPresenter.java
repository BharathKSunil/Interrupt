package com.bharathksunil.interrupt.admin.presenter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.auth.model.UserPermissions;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;

import java.util.List;

/**
 * This presenter adds a new organiser with his user access.
 *
 * @author Bharath on 26-02-2018.
 */

public interface NewOrganiserPresenter {

    interface View extends BaseView {
        void setHeadingText(@NonNull String text);

        void setSubmitButtonText(@NonNull String text);

        void loadDesignations(@NonNull List<String> designations);

        void loadUserData(@NonNull String name, @NonNull String email, @NonNull String PhoneNo,
                          @NonNull String Roles, @NonNull String designation, @NonNull List<String> permissions);

        @NonNull
        String getOrganiserName();

        @NonNull
        String getOrganiserEmail();

        @NonNull
        String getOrganiserPhoneNo();

        @NonNull
        String getOrganiserRoles();

        Uri getProfileImageUri();

        @NonNull
        UserPermissions getPermissionsSelected();

        @NonNull
        String getUserDesignation();

        void setNameFieldError(FormErrorType type);

        void setEmailIDFieldError(FormErrorType type);

        void setPhoneNoFieldError(FormErrorType type);

        void setRolesFieldError(FormErrorType type);

        void showNoPermissionsAssignedAlert();

        void showOperationSuccessfulMessage(@NonNull String message);

        void exitPage();
    }

    interface Repository {
        interface OnAddedCallback {
            void onAddSuccessful();

            void onAddFailed();
        }

        interface OnProfileUploadedCallback {
            void onProfileUploadedSuccessfully(String url);

            void onProfileUploadFailed();
        }

        void uploadUserImage(Uri uri, @NonNull OnProfileUploadedCallback callback);

        void addOrganiser(Users users, UserPermissions userPermissions, OnAddedCallback callback);
    }

    void setView(@Nullable View view);

    void onSubmitOrganiserButtonPressed();

    void loadOrganiserWithEmail(@NonNull String email);

    void onProceedWithDefaultPermissionsButtonPressed();
}
