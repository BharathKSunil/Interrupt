package com.bharathksunil.interrupt.admin.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bharathksunil.interrupt.admin.model.Feedback;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.util.DateUtil;
import com.bharathksunil.interrupt.util.TextUtils;

import java.util.List;

/**
 * This is an implementation of the {@link AdminInfoPresenter}
 *
 * @author Bharath on 24-02-2018.
 */

public class AdminInfoPresenterImplementation implements AdminInfoPresenter {

    @Nullable
    private View viewInstance;
    @NonNull
    private FeedbackPresenter.Repository feedbackRepositoryInstance;
    @NonNull
    private UsersInfoRecyclerPresenter.Repository adminRepositoryInstance;

    public AdminInfoPresenterImplementation(@NonNull FeedbackPresenter.Repository feedbackRepositoryInstance,
                                            @NonNull UsersInfoRecyclerPresenter.Repository adminRepositoryInstance) {
        this.feedbackRepositoryInstance = feedbackRepositoryInstance;
        this.adminRepositoryInstance = adminRepositoryInstance;
    }

    @Override
    public void onSendButtonPressed() {
        if (viewInstance != null) {
            viewInstance.onProcessStarted();
            String feedback = viewInstance.getFeedback();
            if (TextUtils.isEmpty(feedback)) {
                viewInstance.setFeedbackError(FormErrorType.EMPTY);
                viewInstance.onProcessEnded();
                return;
            }
            Feedback userFeedback = new Feedback();
            userFeedback.setName(UserManager.getInstance().getUsersName());
            userFeedback.setEmailId(UserManager.getInstance().getUsersEmailID());
            userFeedback.setTime(DateUtil.getCurrentDateTimeAsString());
            userFeedback.setFeedback(feedback);

            feedbackRepositoryInstance.postFeedback(userFeedback,
                    new FeedbackPresenter.Repository.OnFeedbackPostedCallback() {
                        @Override
                        public void onFeedbackPostedSuccessfully() {
                            if (viewInstance != null) {
                                viewInstance.onProcessEnded();
                                viewInstance.onFeedbackPostSuccessful();
                            }

                        }

                        @Override
                        public void onFeedbackPostFailed() {
                            if (viewInstance != null) {
                                viewInstance.onProcessEnded();
                                viewInstance.onUnexpectedError();
                                viewInstance.onFeedbackPostFailed();
                            }

                        }
                    });
        }
    }

    @Override
    public void setView(@Nullable final View view) {
        viewInstance = view;
        if (viewInstance != null) {
            viewInstance.onProcessStarted();
            adminRepositoryInstance.loadAllAdministratorsInfo(new UsersInfoRecyclerPresenter.Repository.DataLoadedCallback() {
                @Override
                public void onDataLoadedSuccessfully(@NonNull List<Users> data) {
                    if (viewInstance == null)
                        return;
                    viewInstance.onProcessEnded();
                    if (data.size() > 0) {
                        viewInstance.hideNoAdministratorsText();
                        viewInstance.loadAdministratorsRecyclerView(data);
                    } else
                        viewInstance.showNoAdministratorsText();
                }

                @Override
                public void onDataLoadFailed() {
                    if (viewInstance == null)
                        return;
                    viewInstance.onUnexpectedError();
                    viewInstance.showNoAdministratorsText();
                    viewInstance.onProcessEnded();

                }
            });

        }
    }
}
