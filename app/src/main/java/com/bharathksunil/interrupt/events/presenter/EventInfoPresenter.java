package com.bharathksunil.interrupt.events.presenter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.admin.model.Users;
import com.bharathksunil.interrupt.auth.presenter.FormErrorType;
import com.bharathksunil.interrupt.events.model.Events;

import java.util.List;

/**
 * This Presenter loads the event dashboard items
 *
 * @author Bharath on 28-02-2018.
 */

public interface EventInfoPresenter {
    interface View extends BaseView {
        void setEventName(String eventName);

        void setEventNameFieldEnabled(boolean isEnabled);

        void loadEventCategories(List<String> categories);

        void setEventCategoryFieldEnabled(boolean isEnabled);

        void setEventDescription(String descriptions);

        void setEventDescriptionFieldEnabled(boolean isEnabled);

        void setEventPrice(String price);

        void setEventPriceFieldEnabled(boolean isEnabled);

        void setEventCoordinatorName(String name);

        void setEventCoordinatorNameFieldEnabled(boolean isEnabled);

        void setEventCoordinatorEmail(String email);

        void setEventCoordinatorEmailFieldEnabled(boolean isEnabled);

        void setEventCoordinatorPhoneNo(String phoneNo);

        void setEventCoordinatorPhoneNoFieldEnabled(boolean isEnabled);

        void setTimeButtonText(String time);

        void setEventTimeButtonEnabled(boolean isEnabled);

        void setEventVenue(String eventVenue);

        void setEventVenueFieldEnabled(boolean isEnabled);

        void showEventRegistrationsButton();

        void hideEventRegistrationsButton();

        void loadEventBanner(String url);

        void setEventBannerImageEnabled(boolean isEnabled);

        void showBannerImageChooser();

        void loadNewParticipantRegistrationPage();

        void loadEventRegistrationsViewer();

        void showComingSoonMessage();

        void showDateTimeChooser();

        void showPermissionDeniedMessage();

        void exitActivity();

        void setSubmitButtonText(String text);

        void hideSubmitButton();

        void showSubmitButton();

        void hideCancelButton();

        void showCancelButton();

        void showEditButton();

        void hideEditButton();

        void showAddRegistrationButton();

        void hideAddRegistrationButton();

        void showDownloadRegistrationButton();

        void hideDownloadRegistrationButton();

        String getEventName();

        void setNameFieldError(FormErrorType errorType);

        int getEventCategoryPosition();

        String getEventDescription();

        void setDescriptionFieldError(FormErrorType errorType);

        String getEventTime();

        void setTimeFieldError();

        String getEventVenue();

        void setVenueFieldError(FormErrorType errorType);

        String getCoordinatorName();

        void setCoordinatorNameError(FormErrorType errorType);

        String getCoordinatorEmail();

        void setCoordinatorEmailError(FormErrorType errorType);

        String getCoordinatorPhone();

        void setCoordinatorPhoneError(FormErrorType errorType);

        String getPrice();

        void setPriceError(FormErrorType errorType);

        Uri getEventBanner();

        void showProcessFinishedMessage(String message);
    }

    interface Repository {
        interface OnEventRegistrationCallback {
            void onEventAddedSuccessfully();

            void onEventUpdatedSuccessfully();

            void onEventUpdateFailed();

            void onEventAddFailed();
        }

        void addNewEvent(@NonNull Events event, @NonNull Users coordinator, @Nullable Uri bannerImage,
                         @NonNull OnEventRegistrationCallback callback);

        void updateEvent(@NonNull Events event, @Nullable Uri bannerImage,
                         @NonNull OnEventRegistrationCallback callback);
    }

    void setView(@Nullable View view);

    void onBannerImagePressed();

    void onSetDateButtonPressed();

    void onEditButtonPressed();

    void onAddRegistrationButtonPressed();

    void onDownloadEventRegistrationButtonPressed();

    void onViewRegistrationsButtonPressed();

    void onSubmitButtonPressed();

    void onCancelButtonPressed();
}
