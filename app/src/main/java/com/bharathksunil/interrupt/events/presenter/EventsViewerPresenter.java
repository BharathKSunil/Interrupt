package com.bharathksunil.interrupt.events.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.events.model.Events;

import java.util.List;

/**
 * The presenter interface to view the events
 *
 * @author Bharath on 20-02-2018.
 */

public interface EventsViewerPresenter {
    interface View extends BaseView {
        void showEditButton();

        void hideEditButton();

        void loadEventsDashboardActivity();

        void showNoPermissionsMessage();

        void showNoEventsInCategoryText();

        void hideNoEventsInCategoryText();

        void loadRecyclerView(@NonNull List<String> imageUrls);

        void initialiseEventDataForFirstCard(@NonNull Events events);

        void setActiveSlide(int pos, @NonNull Events event);

        void makeACallToNumber(String number);
    }

    interface Repository {
        interface DataLoadedCallback {
            void onDataLoadedSuccessful(@NonNull List<Events> events);

            void onDataLoadFailed();
        }

        void loadEventsDataForCategoryGivenByID(String id, DataLoadedCallback callback);
    }

    void setView(@Nullable View view);

    void onActiveCardChange(int activePosition);

    void onCallButtonPressed();

    void onEditEventButtonPressed();
}
