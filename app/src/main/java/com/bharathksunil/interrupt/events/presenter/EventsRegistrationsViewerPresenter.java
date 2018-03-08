package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.events.model.EventRegistrations;

import java.util.List;

/**
 * This presenter is responsible to present the event registrations for the event
 *
 * @author Bharath on 05-03-2018.
 */

public interface EventsRegistrationsViewerPresenter {
    interface View extends BaseView {
        void setEventName(String name);

        void showNoRegistrationsText();

        void hideNoRegistrationsText();

        void setTotalRegistrationsCount(@NonNull String count);

        void setTotalRegistrationsAmount(@NonNull String amount);

        void loadRecyclerView(@NonNull List<EventRegistrations> registrationsList);
    }

    interface Repository {
        interface OnDataLoadedCallback {
            void onDataLoadedSuccessfully(@NonNull List<EventRegistrations> eventRegistrations);

            void onDataLoadFailed();
        }

        void loadEventRegistrations(@NonNull OnDataLoadedCallback callback);
    }

    void setView(@Nullable View view);
}
