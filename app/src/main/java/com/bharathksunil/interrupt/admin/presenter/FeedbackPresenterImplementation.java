package com.bharathksunil.interrupt.admin.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.admin.model.Feedback;
import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.util.TextUtils;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.Calendar;
import java.util.List;

/**
 * This is the implementation of the {@link FeedbackPresenter} to send and get all the user feedback
 *
 * @author Bharath on 24-02-2018.
 */

public class FeedbackPresenterImplementation implements FeedbackPresenter {

    @Nullable
    private View viewInstance;
    @NonNull
    private Repository repositoryInstance;

    public FeedbackPresenterImplementation(@NonNull Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
    }

    @Override
    public void setView(@Nullable View view) {
        viewInstance = view;
    }

    @Override
    public void onSendFeedbackButtonPressed() {
        if (viewInstance != null) {
            String feedback = viewInstance.getFeedbackFromField();
            if (TextUtils.isEmpty(feedback)) {
                viewInstance.setFeedbackFieldError(FormErrorType.EMPTY);
                return;
            }
            Feedback userFeedback = new Feedback();
            userFeedback.setName(UserManager.getInstance().getUsersName());
            userFeedback.setEmailId(UserManager.getInstance().getUsersEmailID());
            userFeedback.setTime(DateTimeUtils.formatTime(Calendar.getInstance().getTime()));
            userFeedback.setFeedback(feedback);

            repositoryInstance.postFeedback(userFeedback, new Repository.OnFeedbackPostedCallback() {
                @Override
                public void onFeedbackPostedSuccessfully() {
                    if (viewInstance != null) {
                        viewInstance.onProcessEnded();
                        viewInstance.showFeedbackSuccessfullyPosted();
                    }

                }

                @Override
                public void onFeedbackPostFailed() {
                    if (viewInstance != null) {
                        viewInstance.onProcessEnded();
                        viewInstance.onUnexpectedError();
                        viewInstance.showFeedbackPostFailed();
                    }

                }
            });
        }

    }

    @Override
    public void loadAllFeedback() {
        if (viewInstance != null)
            viewInstance.onProcessStarted();
        repositoryInstance.getAllFeedbacks(new Repository.DataLoadedCallback() {
            @Override
            public void onDataLoadedSuccessfully(@NonNull List<Feedback> feedbacks) {
                if (viewInstance != null) {
                    viewInstance.onProcessEnded();
                    if (feedbacks.size() > 1) {
                        viewInstance.loadFeedbackRecyclerView(feedbacks);
                        viewInstance.hideNoFeedbackText();
                    } else
                        viewInstance.showNoFeedbackText();
                }

            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance != null) {
                    viewInstance.onProcessEnded();
                    viewInstance.onUnexpectedError();
                }
            }
        });

    }
}
