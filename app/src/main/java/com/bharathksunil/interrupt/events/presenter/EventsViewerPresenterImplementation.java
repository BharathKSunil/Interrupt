package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.events.model.EventsManager;
import com.bharathksunil.interrupt.events.repository.Events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bharath on 20-02-2018.
 */

public class EventsViewerPresenterImplementation implements EventsViewerPresenter {
    @Nullable
    private View viewInstance;
    @NonNull
    private Repository repositoryInstance;
    @NonNull
    private List<Events> data;
    private EventsManager eventsManager;

    public EventsViewerPresenterImplementation(@NonNull Repository repository) {
        this.repositoryInstance = repository;
        data = new ArrayList<>();
        eventsManager = EventsManager.getInstance();
    }

    @Override
    public void setView(@Nullable final View view) {
        viewInstance = view;
        if (viewInstance != null) {
            viewInstance.onProcessStarted();
            repositoryInstance.loadEventsDataForCategoryGivenByID(eventsManager.getCurrentCategoryID(),
                    new Repository.DataLoadedCallback() {
                        @Override
                        public void onDataLoadedSuccessful(@NonNull List<Events> events) {
                            if (viewInstance != null) {
                                data = events;
                                viewInstance.onProcessEnded();
                                if (data.size() == 0) {
                                    viewInstance.showNoEventsInCategoryText();
                                    return;
                                }
                                viewInstance.hideNoEventsInCategoryText();
                                List<String> urls = new ArrayList<>();
                                for (Events event : data) {
                                    urls.add(event.getBannerUrl());
                                }
                                viewInstance.loadRecyclerView(urls);
                                //load the first event
                                EventsManager.getInstance().loadEvents(data.get(0));
                                viewInstance.initialiseEventDataForFirstCard(data.get(0));
                            }
                        }

                        @Override
                        public void onDataLoadFailed() {
                            if (viewInstance != null) {
                                viewInstance.onProcessEnded();
                                viewInstance.showNoEventsInCategoryText();
                                viewInstance.onUnexpectedError();
                            }
                        }
                    });
        }
    }

    @Override
    public void onActiveCardChange(int activePosition) {
        if (viewInstance != null) {
            eventsManager.loadEvents(data.get(activePosition));
            viewInstance.setActiveSlide(activePosition, data.get(activePosition));
        }
    }
}
