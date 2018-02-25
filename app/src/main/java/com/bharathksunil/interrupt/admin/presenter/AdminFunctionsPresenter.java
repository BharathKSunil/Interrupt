package com.bharathksunil.interrupt.admin.presenter;

/**
 * This presenter represents the functions the app administrator can perform
 *
 * @author Bharath on 26-02-2018.
 */

public interface AdminFunctionsPresenter {
    interface View {
        void showPermissionDeniedMessage();

        void loadUserManagementPage();

        void loadOrganiserManagementPage();

        void loadFeedbackViewerPage();
    }

    void setView(View view);

    void onUserManagementSelected();

    void onOrganiserManagementSelected();

    void onFeedbackManagementSelected();
}
