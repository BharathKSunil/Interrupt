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
    /**
     * The view interface to interact with the View. The view must implement this interface to use
     * this presenter
     */
    interface View extends BaseView {
        /**
         * Extract the Name from the view input field
         *
         * @return the name of the user
         */
        String getNameField();

        /**
         * Extract the Email from the view input field
         *
         * @return the email id of the user
         */
        String getEmailField();

        /**
         * Extract the Phone No from the view input field
         *
         * @return the Phone No of the user
         */
        String getPhoneNumberField();

        /**
         * Extract the USN from the view input field
         *
         * @return the USN of the user
         */
        String getUSNField();

        /**
         * Extract the Department from the view input field
         *
         * @return the Department of the user
         */
        String getDepartmentField();

        /**
         * Extract the Password from the view input field
         *
         * @return the Password of the user
         */
        String getPasswordField();

        /**
         * Extract the Section from the view input field
         *
         * @return the Section to which the user belongs
         */
        String getSectionField();

        /**
         * The semester of from the view input filed
         *
         * @return the section to which th user belongs to
         */
        String getSemesterField();

        /**
         * The path to the profile picture of the user
         *
         * @return the uri to the profile path
         */
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

    /**
     * The repository interface to interact to the various Repositories. A new Repository type must
     * use implement this to use the presenter
     */
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

    /**
     * Called when the view gets attached and detached to the presenter
     *
     * @param view the {@link View}
     */
    void setView(View view);

    /**
     * Called when the user presses the signUp button
     */
    void onSignUpButtonClicked();
}
