package com.bharathksunil.interrupt.events.presenter;

import androidx.annotation.NonNull;

import com.bharathksunil.interrupt.BaseView;
import com.bharathksunil.interrupt.events.model.Schedules;

import java.util.List;

/**
 * This Loads the Schedules
 * Created by Bharath on 24-02-2018.
 */

public interface SchedulesPresenter {
    interface View extends BaseView{
        void showNoSchedulesFoundText();
        void hideNoSchedulesFoundText();
        void loadSchedulesTimelineRecyclerView(List<Schedules> schedulesList);
    }

    interface Repository{
        interface DataLoadedCallback{
            void onDataLoadSuccessful(List<Schedules> schedulesList);
            void onDataLoadFailed();
        }
        void getEventSchedules(@NonNull DataLoadedCallback callback);
    }

    void setView(View view);
}
