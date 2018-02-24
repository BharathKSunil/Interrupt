package com.bharathksunil.interrupt.admin.presenter;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.admin.model.Feedback;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;

import java.util.List;

/**
 * This interface is use to interact with the Feedback Repository and store and retrieve the feedback
 * @author Bharath on 24-02-2018.
 */

public interface FeedbackPresenter {
    interface View extends BaseView{
        void loadFeedbackRecyclerView(List<Feedback> feedbacks);
        void showNoFeedbackText();
        void hideNoFeedbackText();
        String getFeedbackFromField();
        void setFeedbackFieldError(FormErrorType error);
        void showFeedbackSuccessfullyPosted();
        void showFeedbackPostFailed();
    }
    interface Repository{
        interface DataLoadedCallback{
            void onDataLoadedSuccessfully(@NonNull List<Feedback> feedbacks);
            void onDataLoadFailed();
        }
        interface OnFeedbackPostedCallback{
            void onFeedbackPostedSuccessfully();
            void onFeedbackPostFailed();
        }

        void postFeedback(@NonNull Feedback feedback, @NonNull OnFeedbackPostedCallback callback);
        void getAllFeedbacks(@NonNull DataLoadedCallback dataLoadedCallback);
    }

    void setView(View view);
    void onSendFeedbackButtonPressed();
    void loadAllFeedback();
}
