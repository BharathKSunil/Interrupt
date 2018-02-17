package com.bharathksunil.interrupt.dashboard.presenter;

import android.support.annotation.Nullable;

/**
 * This presenter is responsible for presenting the user info
 *
 * @author Bharath on 13-02-2018.
 */

public interface UserInfoPresenter {

    interface View{
        /**
         * Download the profile image from the url and load it to the image view
         * *
         * @param profileUrl the url to the profile image
         */
        void setUserImage(String profileUrl);

        void setUserName(String userName);

        void setUserDesignation(String designation);

        void setUserEmailID(String emailID);

        void setUserPhoneNumber(String phoneNumber);

        void setUserUSN(String usn);

        void setUserCollege(String college);

        void setUserSemester(String semester);

        void setUserSection(String section);

        void setUserDepartment(String department);

        void loadProfileEditView();

        void loadLauncherActivity();
    }

    void setView(@Nullable View view);

    void onSignOutButtonPressed();

    void onEditUserInfoButtonPressed();
}
