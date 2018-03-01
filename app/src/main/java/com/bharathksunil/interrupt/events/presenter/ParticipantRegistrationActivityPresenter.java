package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.events.model.EventRegistrations;

/**
 * This presenter is responsible for registering a participant to the Event
 *
 * @author Bharath on 01-03-2018.
 */

public interface ParticipantRegistrationActivityPresenter {
    interface View extends BaseView {
        void loadEventBanner(String url);

        void setEventNameText(String eventName);

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

        String getTeamMembers();

        void onNameFieldError(FormErrorType errorType);

        void onEmailFieldError(FormErrorType errorType);

        void onPhoneNumberFieldError(FormErrorType errorType);

        void onUSNFieldError(FormErrorType errorType);

        void onDepartmentFieldError(FormErrorType errorType);

        void onSectionFieldError(FormErrorType errorType);

        void onSemFieldError(FormErrorType errorType);

        void userRegisteredSuccessfully();

        void showUserAlreadyRegisteredError();

        void resetAllUserData();

        void sendEmail(String emailID, String subject, String body);
    }

    interface Repository {
        interface RegistrationCompleteCallback {
            void userRegisteredSuccessfully();

            void userRegistrationFailed();

            void userAlreadyRegistered();
        }

        void registerParticipant(@NonNull EventRegistrations participant, RegistrationCompleteCallback callback);
    }

    void setView(@Nullable View view);

    void onCancelButtonPressed();

    void onRegisterButtonPressed();

}
