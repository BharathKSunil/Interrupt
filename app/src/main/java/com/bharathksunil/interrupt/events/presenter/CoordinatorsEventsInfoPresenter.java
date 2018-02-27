package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.events.model.Events;

import java.util.List;

/**
 * This presenter is responsible for loading the events the user is the coordinator for
 *
 * @author Bharath on 28-02-2018.
 */

public interface CoordinatorsEventsInfoPresenter {

    interface View extends BaseView {
        void showNoEventsCoordinating();

        void hideNoEventsCoordinating();

        void loadCoordinatorsEventsRecyclerView(List<Events> events);

        void loadEventsDashboard();

        void showPermissionDenied();
    }

    interface Repository {
        interface DataLoadedCallback {
            void onDataLoadSuccessful(List<Events> event);

            void onDataLoadFailed();
        }

        void loadUsersCoordinatingEventsInfo(@NonNull DataLoadedCallback callback);
    }

    void setView(@Nullable View view);

    void onRecyclerViewItemPressed(int position);
}
