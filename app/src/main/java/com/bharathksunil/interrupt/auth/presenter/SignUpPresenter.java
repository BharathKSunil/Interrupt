package com.bharathksunil.interrupt.auth.presenter;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.auth.repository.User;

/**
 * This interface is to be used for signing Up a new user into the app
 *
 * @author Bharath on 11-02-2018.
 */

public interface SignUpPresenter {
    interface View extends BaseView {
        String getNameField();

        String getEmailField();

        String getPhoneNumberField();

        String getUSNField();

        String getDepartmentField();

        String getPasswordField();

        String getSectionField();

        String getSemesterField();

        Uri getCompressedProfileImagePath();

        void onNameFieldError(FormErrorType errorType);

        void onEmailFieldError(FormErrorType errorType);

        void onPhoneNumberFieldError(FormErrorType errorType);

        void onUSNFieldError(FormErrorType errorType);

        void onDepartmentFieldError(FormErrorType errorType);

        void onPasswordFieldError(FormErrorType errorType);

        void onSectionFieldError(FormErrorType errorType);

        void onSemFieldError(FormErrorType errorType);

        void onProfilePathError(FormErrorType errorType);

        void onUserSuccessfullySignedUp();

    }

    interface Repository {
        interface SignUpCallbacks {
            void onUserSignedUpSuccessfully();

            void onUserAlreadySignedUp();

            void onUserSignUpFailed();
        }

        interface ProfileUploadCallback {
            void onProfileUploaded(String profileUrl);

            void onProfileUploadFailed();
        }

        interface UserUploadCallback {
            void onUserInfoUploadedSuccessfully();

            void onUserInfoUploadFailed();
        }

        void signUpWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                        @NonNull SignUpCallbacks signUpCallbacks);

        void uploadProfilePicture(@NonNull Uri profilePath, @NonNull ProfileUploadCallback callback);

        void uploadUserInfo(@NonNull User user, @NonNull UserUploadCallback callback);
    }

    void setView(View view);

    void onSignUpButtonClicked();
}
