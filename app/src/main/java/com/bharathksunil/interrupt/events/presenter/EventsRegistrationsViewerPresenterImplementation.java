package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.events.model.EventRegistrations;
import com.bharathksunil.interrupt.events.model.EventsManager;

import java.util.List;

/**
 * This method implements the {@link EventsRegistrationsViewerPresenter}.
 *
 * @author Bharath on 05-03-2018.
 */

public class EventsRegistrationsViewerPresenterImplementation implements EventsRegistrationsViewerPresenter {
    @Nullable
    private View viewInstance;
    @NonNull
    private Repository repositoryInstance;
    @NonNull
    private EventsManager eventsManager;

    public EventsRegistrationsViewerPresenterImplementation(@NonNull Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
        this.eventsManager = EventsManager.getInstance();
    }

    @Override
    public void setView(@Nullable View view) {
        viewInstance = view;
        if (viewInstance == null)
            return;
        viewInstance.onProcessStarted();

        repositoryInstance.loadEventRegistrations(new Repository.OnDataLoadedCallback() {
            @Override
            public void onDataLoadedSuccessfully(@NonNull List<EventRegistrations> eventRegistrations) {
                if (viewInstance == null)
                    return;
                viewInstance.onProcessEnded();
                viewInstance.setEventName(eventsManager.getEventName());
                if (eventRegistrations.size() > 0)
                    viewInstance.hideNoRegistrationsText();
                else
                    viewInstance.showNoRegistrationsText();
                int amount = 0;
                try {
                    amount = Integer.parseInt(eventsManager.getEventPrice()) * eventRegistrations.size();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                viewInstance.loadRecyclerView(eventRegistrations);
                viewInstance.setTotalRegistrationsAmount(Integer.toString(amount));
                viewInstance.setTotalRegistrationsCount(Integer.toString(eventRegistrations.size()));
            }

            @Override
            public void onDataLoadFailed() {
                if (viewInstance == null)
                    return;
                viewInstance.onProcessEnded();
                viewInstance.setTotalRegistrationsCount("0");
                viewInstance.setTotalRegistrationsAmount("0");
                viewInstance.showNoRegistrationsText();

            }
        });

    }
}
