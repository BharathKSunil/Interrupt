package com.bharathksunil.interrupt.events.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bharathksunil.interrupt.events.model.Schedules;

import java.util.List;

/**
 * This is the implementation of {@link SchedulesPresenter}
 * This presenter gets the list of schedules data sends it to the view
 *
 * @author Bharath on 24-02-2018.
 */

public class SchedulesPresenterImplementation implements SchedulesPresenter {

    @Nullable
    private View viewInstance;

    @NonNull
    private Repository repositoryInstance;

    public SchedulesPresenterImplementation(@NonNull Repository repositoryInstance) {
        this.repositoryInstance = repositoryInstance;
    }

    @Override
    public void setView(View view) {
        viewInstance = view;
        if (viewInstance != null) {
            viewInstance.onProcessStarted();
            repositoryInstance.getEventSchedules(new Repository.DataLoadedCallback() {
                @Override
                public void onDataLoadSuccessful(List<Schedules> schedulesList) {
                    if (viewInstance == null)
                        return;
                    viewInstance.onProcessEnded();
                    if (schedulesList.size() > 0) {
                        viewInstance.hideNoSchedulesFoundText();
                        viewInstance.loadSchedulesTimelineRecyclerView(schedulesList);
                    } else
                        viewInstance.showNoSchedulesFoundText();
                }

                @Override
                public void onDataLoadFailed() {
                    if (viewInstance == null)
                        return;
                    viewInstance.onProcessEnded();
                    viewInstance.showNoSchedulesFoundText();
                    viewInstance.onUnexpectedError();
                }
            });
        }
    }
}
