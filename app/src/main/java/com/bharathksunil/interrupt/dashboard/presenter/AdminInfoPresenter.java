package com.bharathksunil.interrupt.dashboard.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;

import java.util.List;

/**
 * This presenter Presents the Administrators info
 * @author Bharath on 19-02-2018.
 */

public interface AdminInfoPresenter {
    interface View extends BaseView{
        void loadAdministratorsRecyclerView(@NonNull List<Users> admins);
        void showNoAdministratorsText();
        void hideNoAdministratorsText();
        String getFeedback();
        void setFeedbackError(FormErrorType errorType);
        void onFeedbackPostSuccessful();
        void onFeedbackPostFailed();
    }

    void onSendButtonPressed();
    void setView(@Nullable View view);
}
