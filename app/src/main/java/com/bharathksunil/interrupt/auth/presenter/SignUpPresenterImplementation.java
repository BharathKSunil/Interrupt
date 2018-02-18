package com.bharathksunil.interrupt.auth.presenter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.util.TextUtils;
import com.bharathksunil.interrupt.auth.repository.User;

import java.io.File;

import static com.bharathksunil.interrupt.auth.presenter.FormErrorType.EMPTY;
import static com.bharathksunil.interrupt.auth.presenter.FormErrorType.INCORRECT;
import static com.bharathksunil.interrupt.auth.presenter.FormErrorType.INVALID;

/**
 * This is the presenter implementation to sign up the user
 *
 * @author Bharath on 11-02-2018.
 */

public class SignUpPresenterImplementation implements SignUpPresenter {

    private String name, email, phoneNo, usn, department, section, password, semester, profileUrl;
    private Uri profilePath;

    @Nullable
    private SignUpPresenter.View viewInstance;
    @NonNull
    private SignUpPresenter.Repository repositoryInstance;

    public SignUpPresenterImplementation(@NonNull Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
    }

    @Override
    public void setView(@Nullable View view) {
        viewInstance = view;
    }

    @Override
    public void onSignUpButtonClicked() {
        if (viewInstance == null)
            return;
        //load all the responses from the view
        viewInstance.onProcessStarted();
        name = viewInstance.getNameField();
        email = viewInstance.getEmailField();
        phoneNo = viewInstance.getPhoneNumberField();
        usn = viewInstance.getUSNField();
        department = viewInstance.getDepartmentField();
        section = viewInstance.getSectionField();
        password = viewInstance.getPasswordField();
        semester = viewInstance.getSemesterField();
        profilePath = viewInstance.getCompressedProfileImagePath();
        //check if any field is left empty or is invalid, if not then sign up the user
        if (checkIfAnyUserInfoFieldIsEmpty() && checkForAnyInvalidField()) {
            repositoryInstance.signUpWithEmailAndPassword(email, password,
                    new Repository.SignUpCallbacks() {
                        //once signed up, upload the profile image if the user has set one or upload the user information
                        @Override
                        public void onUserSignedUpSuccessfully() {
                            if (profilePath != null)
                                uploadProfileImage();
                            else
                                uploadUserInfo();
                        }

                        @Override
                        public void onUserAlreadySignedUp() {
                            //if the user is already signed up then show the error to the user
                            if (viewInstance != null) {
                                viewInstance.onProcessEnded();
                                viewInstance.onEmailFieldError(INCORRECT);
                            }
                        }

                        @Override
                        public void onUserSignUpFailed() {
                            if (viewInstance != null) {
                                viewInstance.onProcessEnded();
                                viewInstance.onUnexpectedError();
                            }
                        }
                    });
        } else
            viewInstance.onProcessEnded();
    }

    private boolean checkIfAnyUserInfoFieldIsEmpty() {
        if (viewInstance == null)
            return false;

        if (TextUtils.isEmpty(name))
            viewInstance.onNameFieldError(EMPTY);
        else if (TextUtils.isEmpty(email))
            viewInstance.onEmailFieldError(EMPTY);
        else if (TextUtils.isEmpty(phoneNo))
            viewInstance.onPhoneNumberFieldError(EMPTY);
        else if (TextUtils.isEmpty(usn))
            viewInstance.onUSNFieldError(EMPTY);
        else if (TextUtils.isEmpty(department))
            viewInstance.onDepartmentFieldError(EMPTY);
        else if (TextUtils.isEmpty(section))
            viewInstance.onSectionFieldError(EMPTY);
        else if (TextUtils.isEmpty(semester))
            viewInstance.onSemFieldError(EMPTY);
        else if (TextUtils.isEmpty(password))
            viewInstance.onPasswordFieldError(EMPTY);
        else
            return true;

        return false;
    }

    private boolean checkForAnyInvalidField() {
        if (viewInstance == null)
            return false;

        if (name.length() < 4)
            viewInstance.onNameFieldError(INVALID);
        else if (!TextUtils.isEmailValid(email))
            viewInstance.onEmailFieldError(INVALID);
        else if (!TextUtils.isPhoneNumberValid(phoneNo))
            viewInstance.onPhoneNumberFieldError(INVALID);
        else if (!TextUtils.isPasswordStrong(password))
            viewInstance.onPasswordFieldError(FormErrorType.INVALID);
        else if (department.length() < 3)
            viewInstance.onDepartmentFieldError(INVALID);
        else if (!TextUtils.isPasswordStrong(password))
            viewInstance.onPasswordFieldError(INVALID);
        else if (profilePath != null && !new File(profilePath.getPath()).exists())
            viewInstance.onProfilePathError(INVALID);
        else
            return true;
        return false;
    }

    private void uploadProfileImage() {
        if (viewInstance == null)
            return;
        repositoryInstance.uploadProfilePicture(profilePath,
                new Repository.ProfileUploadCallback() {
                    @Override
                    public void onProfileUploaded(String url) {
                        profileUrl = url;
                        uploadUserInfo();
                    }

                    @Override
                    public void onProfileUploadFailed() {
                        if (viewInstance == null)
                            return;
                        viewInstance.onProcessEnded();
                        viewInstance.onProfilePathError(INCORRECT);
                    }
                });
    }

    private void uploadUserInfo() {
        if (viewInstance == null)
            return;
        User user = new User(name, phoneNo, usn, department, semester, section, email, profileUrl);
        repositoryInstance.uploadUserInfo(user, new Repository.UserUploadCallback() {
            @Override
            public void onUserInfoUploadedSuccessfully() {
                if (viewInstance == null)
                    return;
                viewInstance.onProcessEnded();
                viewInstance.onUserSuccessfullySignedUp();
            }

            @Override
            public void onUserInfoUploadFailed() {
                if (viewInstance == null)
                    return;
                viewInstance.onProcessEnded();
                viewInstance.onUnexpectedError();
            }
        });
    }
}
