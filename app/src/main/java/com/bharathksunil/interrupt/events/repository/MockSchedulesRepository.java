package com.bharathksunil.interrupt.events.repository;

import android.support.annotation.NonNull;

import com.bharathksunil.interrupt.dashboard.presenter.SchedulesPresenter;
import com.bharathksunil.interrupt.events.model.Schedules;

import java.util.ArrayList;
import java.util.List;

/**
 * This mocks the Schedules tree in the Repository
 * Created by Bharath on 24-02-2018.
 */

public class MockSchedulesRepository implements SchedulesPresenter.Repository {
    @Override
    public void getEventSchedules(@NonNull DataLoadedCallback callback) {
        List<Schedules> schedulesList = new ArrayList<>();

        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "Sat Feb 24 14:45:48 GMT+05:30 2018",
                "JPN Lab"
        ));
        schedulesList.add(new Schedules(
                "Something Crazy",
                "Sat Feb 24 14:55:48 GMT+05:30 2018",
                "BBL Lab"
        ));
        schedulesList.add(new Schedules(
                "Something Good",
                "Sat Feb 24 14:59:48 GMT+05:30 2018",
                "Room C558"
        ));
        schedulesList.add(new Schedules(
                "Gala Item",
                "Sat Feb 24 15:45:48 GMT+05:30 2018",
                "C668"
        ));
        schedulesList.add(new Schedules(
                "Dance Masters",
                "Sat Feb 24 16:45:48 GMT+05:30 2018"
                , "D444"
        ));
        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "Sat Feb 24 17:45:48 GMT+05:30 2018",
                "JPN Lab"
        ));
        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "Sat Feb 24 18:45:48 GMT+05:30 2018",
                "JPN Lab"
        ));
        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "Sat Feb 24 19:45:48 GMT+05:30 2018",
                "JPN Lab"
        ));
        schedulesList.add(new Schedules(
                "Rapid Fire Coding",
                "Sat Feb 24 12:45:48 GMT+05:30 2018",
                "JPN Lab"
        ));

        callback.onDataLoadSuccessful(schedulesList);

    }
}
