package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.events.repository.Events;

import java.util.List;

/**
 * The presenter interface to view the events
 *
 * @author Bharath on 20-02-2018.
 */

public interface EventsViewerPresenter {
    interface View extends BaseView {
        void showNoEventsInCategoryText();

        void hideNoEventsInCategoryText();

        void loadRecyclerView(@NonNull List<String> imageUrls);

        void initialiseEventDataForFirstCard(@NonNull Events events);

        void setActiveSlide(int pos, @NonNull Events event);
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
}