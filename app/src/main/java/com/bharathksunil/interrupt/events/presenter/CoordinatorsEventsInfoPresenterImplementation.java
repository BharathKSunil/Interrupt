package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.auth.model.UserManager;
import com.bharathksunil.interrupt.events.model.Categories;
import com.bharathksunil.interrupt.events.model.Events;
import com.bharathksunil.interrupt.events.model.EventsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the {@link CoordinatorsEventsInfoPresenter}
 *
 * @author Bharath on 28-02-2018.
 */

public class CoordinatorsEventsInfoPresenterImplementation implements CoordinatorsEventsInfoPresenter {
    @Nullable
    private View viewInstance;
    @NonNull
    private Repository repositoryInstance;
    @NonNull
    private List<Events> eventsList;
    @NonNull
    private EventsManager eventsManager;
    @NonNull
    private UserManager userManager;

    public CoordinatorsEventsInfoPresenterImplementation(@NonNull Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
        this.eventsList = new ArrayList<>();
        this.eventsManager = EventsManager.getInstance();
        this.userManager = UserManager.getInstance();
    }

    @Override
    public void setView(@Nullable View view) {
        viewInstance = view;
        if (viewInstance == null)
            return;
        viewInstance.onProcessStarted();

        if (!userManager.isUserAEventsCoordinator()) {
            viewInstance.showPermissionDenied();
            viewInstance.onProcessEnded();
            return;
        }
        repositoryInstance.loadUsersCoordinatingEventsInfo(new Repository.DataLoadedCallback() {
            @Override
            public void onDataLoadSuccessful(List<Events> events) {
                if (viewInstance == null)
                    return;
                viewInstance.onProcessEnded();
                eventsList = events;
                if (events.size() > 0) {
                    viewInstance.hideNoEventsCoordinating();
                    viewInstance.loadCoordinatorsEventsRecyclerView(events);
                } else {
                    viewInstance.showNoEventsCoordinating();
                }
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance == null)
                    return;
                viewInstance.onProcessEnded();
                viewInstance.onUnexpectedError();
            }
        });
    }

    @Override
    public void onRecyclerViewItemPressed(int position) {
        if (viewInstance == null)
            return;
        eventsManager.loadEvents(eventsList.get(position));
        eventsManager.loadCategories(new Categories(
                eventsList.get(position).getCategoryID(),
                eventsList.get(position).getCategory(),
                null,
                null
        ));
        viewInstance.loadEventsDashboard();
    }

}
